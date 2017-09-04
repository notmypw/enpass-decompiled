package com.google.api.client.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Clock;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.client.util.Lists;
import com.google.api.client.util.Objects;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Credential implements HttpExecuteInterceptor, HttpRequestInitializer, HttpUnsuccessfulResponseHandler {
    static final Logger LOGGER = Logger.getLogger(Credential.class.getName());
    private String accessToken;
    private final HttpExecuteInterceptor clientAuthentication;
    private final Clock clock;
    private Long expirationTimeMilliseconds;
    private final JsonFactory jsonFactory;
    private final Lock lock;
    private final AccessMethod method;
    private final Collection<CredentialRefreshListener> refreshListeners;
    private String refreshToken;
    private final HttpRequestInitializer requestInitializer;
    private final String tokenServerEncodedUrl;
    private final HttpTransport transport;

    public interface AccessMethod {
        String getAccessTokenFromRequest(HttpRequest httpRequest);

        void intercept(HttpRequest httpRequest, String str) throws IOException;
    }

    public static class Builder {
        HttpExecuteInterceptor clientAuthentication;
        Clock clock = Clock.SYSTEM;
        JsonFactory jsonFactory;
        final AccessMethod method;
        Collection<CredentialRefreshListener> refreshListeners = Lists.newArrayList();
        HttpRequestInitializer requestInitializer;
        GenericUrl tokenServerUrl;
        HttpTransport transport;

        public Builder(AccessMethod method) {
            this.method = (AccessMethod) Preconditions.checkNotNull(method);
        }

        public Credential build() {
            return new Credential(this);
        }

        public final AccessMethod getMethod() {
            return this.method;
        }

        public final HttpTransport getTransport() {
            return this.transport;
        }

        public Builder setTransport(HttpTransport transport) {
            this.transport = transport;
            return this;
        }

        public final Clock getClock() {
            return this.clock;
        }

        public Builder setClock(Clock clock) {
            this.clock = (Clock) Preconditions.checkNotNull(clock);
            return this;
        }

        public final JsonFactory getJsonFactory() {
            return this.jsonFactory;
        }

        public Builder setJsonFactory(JsonFactory jsonFactory) {
            this.jsonFactory = jsonFactory;
            return this;
        }

        public final GenericUrl getTokenServerUrl() {
            return this.tokenServerUrl;
        }

        public Builder setTokenServerUrl(GenericUrl tokenServerUrl) {
            this.tokenServerUrl = tokenServerUrl;
            return this;
        }

        public Builder setTokenServerEncodedUrl(String tokenServerEncodedUrl) {
            this.tokenServerUrl = tokenServerEncodedUrl == null ? null : new GenericUrl(tokenServerEncodedUrl);
            return this;
        }

        public final HttpExecuteInterceptor getClientAuthentication() {
            return this.clientAuthentication;
        }

        public Builder setClientAuthentication(HttpExecuteInterceptor clientAuthentication) {
            this.clientAuthentication = clientAuthentication;
            return this;
        }

        public final HttpRequestInitializer getRequestInitializer() {
            return this.requestInitializer;
        }

        public Builder setRequestInitializer(HttpRequestInitializer requestInitializer) {
            this.requestInitializer = requestInitializer;
            return this;
        }

        public Builder addRefreshListener(CredentialRefreshListener refreshListener) {
            this.refreshListeners.add(Preconditions.checkNotNull(refreshListener));
            return this;
        }

        public final Collection<CredentialRefreshListener> getRefreshListeners() {
            return this.refreshListeners;
        }

        public Builder setRefreshListeners(Collection<CredentialRefreshListener> refreshListeners) {
            this.refreshListeners = (Collection) Preconditions.checkNotNull(refreshListeners);
            return this;
        }
    }

    public Credential(AccessMethod method) {
        this(new Builder(method));
    }

    protected Credential(Builder builder) {
        this.lock = new ReentrantLock();
        this.method = (AccessMethod) Preconditions.checkNotNull(builder.method);
        this.transport = builder.transport;
        this.jsonFactory = builder.jsonFactory;
        this.tokenServerEncodedUrl = builder.tokenServerUrl == null ? null : builder.tokenServerUrl.build();
        this.clientAuthentication = builder.clientAuthentication;
        this.requestInitializer = builder.requestInitializer;
        this.refreshListeners = Collections.unmodifiableCollection(builder.refreshListeners);
        this.clock = (Clock) Preconditions.checkNotNull(builder.clock);
    }

    public void intercept(HttpRequest request) throws IOException {
        this.lock.lock();
        try {
            Long expiresIn = getExpiresInSeconds();
            if (this.accessToken == null || (expiresIn != null && expiresIn.longValue() <= 60)) {
                refreshToken();
                if (this.accessToken == null) {
                    return;
                }
            }
            this.method.intercept(request, this.accessToken);
            this.lock.unlock();
        } finally {
            this.lock.unlock();
        }
    }

    public boolean handleResponse(HttpRequest request, HttpResponse response, boolean supportsRetry) {
        boolean z = true;
        boolean refreshToken = false;
        boolean bearer = false;
        List<String> authenticateList = response.getHeaders().getAuthenticateAsList();
        if (authenticateList != null) {
            for (String authenticate : authenticateList) {
                if (authenticate.startsWith("Bearer ")) {
                    bearer = true;
                    refreshToken = BearerToken.INVALID_TOKEN_ERROR.matcher(authenticate).find();
                    break;
                }
            }
        }
        if (!bearer) {
            refreshToken = response.getStatusCode() == HttpStatusCodes.STATUS_CODE_UNAUTHORIZED;
        }
        if (refreshToken) {
            try {
                this.lock.lock();
                if (Objects.equal(this.accessToken, this.method.getAccessTokenFromRequest(request)) && !refreshToken()) {
                    z = false;
                }
                this.lock.unlock();
                return z;
            } catch (IOException exception) {
                LOGGER.log(Level.SEVERE, "unable to refresh token", exception);
            } catch (Throwable th) {
                this.lock.unlock();
            }
        }
        return false;
    }

    public void initialize(HttpRequest request) throws IOException {
        request.setInterceptor(this);
        request.setUnsuccessfulResponseHandler(this);
    }

    public final String getAccessToken() {
        this.lock.lock();
        try {
            String str = this.accessToken;
            return str;
        } finally {
            this.lock.unlock();
        }
    }

    public Credential setAccessToken(String accessToken) {
        this.lock.lock();
        try {
            this.accessToken = accessToken;
            return this;
        } finally {
            this.lock.unlock();
        }
    }

    public final AccessMethod getMethod() {
        return this.method;
    }

    public final Clock getClock() {
        return this.clock;
    }

    public final HttpTransport getTransport() {
        return this.transport;
    }

    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }

    public final String getTokenServerEncodedUrl() {
        return this.tokenServerEncodedUrl;
    }

    public final String getRefreshToken() {
        this.lock.lock();
        try {
            String str = this.refreshToken;
            return str;
        } finally {
            this.lock.unlock();
        }
    }

    public Credential setRefreshToken(String refreshToken) {
        this.lock.lock();
        if (refreshToken != null) {
            try {
                boolean z = (this.jsonFactory == null || this.transport == null || this.clientAuthentication == null || this.tokenServerEncodedUrl == null) ? false : true;
                Preconditions.checkArgument(z, "Please use the Builder and call setJsonFactory, setTransport, setClientAuthentication and setTokenServerUrl/setTokenServerEncodedUrl");
            } catch (Throwable th) {
                this.lock.unlock();
            }
        }
        this.refreshToken = refreshToken;
        this.lock.unlock();
        return this;
    }

    public final Long getExpirationTimeMilliseconds() {
        this.lock.lock();
        try {
            Long l = this.expirationTimeMilliseconds;
            return l;
        } finally {
            this.lock.unlock();
        }
    }

    public Credential setExpirationTimeMilliseconds(Long expirationTimeMilliseconds) {
        this.lock.lock();
        try {
            this.expirationTimeMilliseconds = expirationTimeMilliseconds;
            return this;
        } finally {
            this.lock.unlock();
        }
    }

    public final Long getExpiresInSeconds() {
        this.lock.lock();
        try {
            if (this.expirationTimeMilliseconds == null) {
                return null;
            }
            Long valueOf = Long.valueOf((this.expirationTimeMilliseconds.longValue() - this.clock.currentTimeMillis()) / 1000);
            this.lock.unlock();
            return valueOf;
        } finally {
            this.lock.unlock();
        }
    }

    public Credential setExpiresInSeconds(Long expiresIn) {
        return setExpirationTimeMilliseconds(expiresIn == null ? null : Long.valueOf(this.clock.currentTimeMillis() + (expiresIn.longValue() * 1000)));
    }

    public final HttpExecuteInterceptor getClientAuthentication() {
        return this.clientAuthentication;
    }

    public final HttpRequestInitializer getRequestInitializer() {
        return this.requestInitializer;
    }

    public final boolean refreshToken() throws IOException {
        boolean statusCode4xx;
        this.lock.lock();
        try {
            TokenResponse tokenResponse = executeRefreshToken();
            if (tokenResponse != null) {
                setFromTokenResponse(tokenResponse);
                for (CredentialRefreshListener refreshListener : this.refreshListeners) {
                    refreshListener.onTokenResponse(this, tokenResponse);
                }
                this.lock.unlock();
                return true;
            }
        } catch (TokenResponseException e) {
            if (400 > e.getStatusCode() || e.getStatusCode() >= ExponentialBackOff.DEFAULT_INITIAL_INTERVAL_MILLIS) {
                statusCode4xx = false;
            } else {
                statusCode4xx = true;
            }
            if (e.getDetails() != null && statusCode4xx) {
                setAccessToken(null);
                setExpiresInSeconds(null);
            }
            for (CredentialRefreshListener refreshListener2 : this.refreshListeners) {
                refreshListener2.onTokenErrorResponse(this, e.getDetails());
            }
            if (statusCode4xx) {
                throw e;
            }
        } catch (Throwable th) {
            this.lock.unlock();
        }
        this.lock.unlock();
        return false;
    }

    public Credential setFromTokenResponse(TokenResponse tokenResponse) {
        setAccessToken(tokenResponse.getAccessToken());
        if (tokenResponse.getRefreshToken() != null) {
            setRefreshToken(tokenResponse.getRefreshToken());
        }
        setExpiresInSeconds(tokenResponse.getExpiresInSeconds());
        return this;
    }

    protected TokenResponse executeRefreshToken() throws IOException {
        if (this.refreshToken == null) {
            return null;
        }
        return new RefreshTokenRequest(this.transport, this.jsonFactory, new GenericUrl(this.tokenServerEncodedUrl), this.refreshToken).setClientAuthentication(this.clientAuthentication).setRequestInitializer(this.requestInitializer).execute();
    }

    public final Collection<CredentialRefreshListener> getRefreshListeners() {
        return this.refreshListeners;
    }
}
