package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.JsonToken;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.SecurityUtils;
import com.google.api.client.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Beta
public class GooglePublicKeysManager {
    private static final Pattern MAX_AGE_PATTERN = Pattern.compile("\\s*max-age\\s*=\\s*(\\d+)\\s*");
    private static final long REFRESH_SKEW_MILLIS = 300000;
    private final Clock clock;
    private long expirationTimeMilliseconds;
    private final JsonFactory jsonFactory;
    private final Lock lock;
    private final String publicCertsEncodedUrl;
    private List<PublicKey> publicKeys;
    private final HttpTransport transport;

    @Beta
    public static class Builder {
        Clock clock = Clock.SYSTEM;
        final JsonFactory jsonFactory;
        String publicCertsEncodedUrl = GoogleOAuthConstants.DEFAULT_PUBLIC_CERTS_ENCODED_URL;
        final HttpTransport transport;

        public Builder(HttpTransport transport, JsonFactory jsonFactory) {
            this.transport = (HttpTransport) Preconditions.checkNotNull(transport);
            this.jsonFactory = (JsonFactory) Preconditions.checkNotNull(jsonFactory);
        }

        public GooglePublicKeysManager build() {
            return new GooglePublicKeysManager(this);
        }

        public final HttpTransport getTransport() {
            return this.transport;
        }

        public final JsonFactory getJsonFactory() {
            return this.jsonFactory;
        }

        public final String getPublicCertsEncodedUrl() {
            return this.publicCertsEncodedUrl;
        }

        public Builder setPublicCertsEncodedUrl(String publicCertsEncodedUrl) {
            this.publicCertsEncodedUrl = (String) Preconditions.checkNotNull(publicCertsEncodedUrl);
            return this;
        }

        public final Clock getClock() {
            return this.clock;
        }

        public Builder setClock(Clock clock) {
            this.clock = (Clock) Preconditions.checkNotNull(clock);
            return this;
        }
    }

    public GooglePublicKeysManager(HttpTransport transport, JsonFactory jsonFactory) {
        this(new Builder(transport, jsonFactory));
    }

    protected GooglePublicKeysManager(Builder builder) {
        this.lock = new ReentrantLock();
        this.transport = builder.transport;
        this.jsonFactory = builder.jsonFactory;
        this.clock = builder.clock;
        this.publicCertsEncodedUrl = builder.publicCertsEncodedUrl;
    }

    public final HttpTransport getTransport() {
        return this.transport;
    }

    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }

    public final String getPublicCertsEncodedUrl() {
        return this.publicCertsEncodedUrl;
    }

    public final Clock getClock() {
        return this.clock;
    }

    public final List<PublicKey> getPublicKeys() throws GeneralSecurityException, IOException {
        this.lock.lock();
        try {
            if (this.publicKeys == null || this.clock.currentTimeMillis() + REFRESH_SKEW_MILLIS > this.expirationTimeMilliseconds) {
                refresh();
            }
            List<PublicKey> list = this.publicKeys;
            return list;
        } finally {
            this.lock.unlock();
        }
    }

    public final long getExpirationTimeMilliseconds() {
        return this.expirationTimeMilliseconds;
    }

    public GooglePublicKeysManager refresh() throws GeneralSecurityException, IOException {
        JsonParser parser;
        this.lock.lock();
        try {
            this.publicKeys = new ArrayList();
            CertificateFactory factory = SecurityUtils.getX509CertificateFactory();
            HttpResponse certsResponse = this.transport.createRequestFactory().buildGetRequest(new GenericUrl(this.publicCertsEncodedUrl)).execute();
            this.expirationTimeMilliseconds = this.clock.currentTimeMillis() + (getCacheTimeInSec(certsResponse.getHeaders()) * 1000);
            parser = this.jsonFactory.createJsonParser(certsResponse.getContent());
            JsonToken currentToken = parser.getCurrentToken();
            if (currentToken == null) {
                currentToken = parser.nextToken();
            }
            Preconditions.checkArgument(currentToken == JsonToken.START_OBJECT);
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                parser.nextToken();
                this.publicKeys.add(((X509Certificate) factory.generateCertificate(new ByteArrayInputStream(StringUtils.getBytesUtf8(parser.getText())))).getPublicKey());
            }
            this.publicKeys = Collections.unmodifiableList(this.publicKeys);
            parser.close();
            this.lock.unlock();
            return this;
        } catch (Throwable th) {
            this.lock.unlock();
        }
    }

    long getCacheTimeInSec(HttpHeaders httpHeaders) {
        long cacheTimeInSec = 0;
        if (httpHeaders.getCacheControl() != null) {
            for (String arg : httpHeaders.getCacheControl().split(",")) {
                Matcher m = MAX_AGE_PATTERN.matcher(arg);
                if (m.matches()) {
                    cacheTimeInSec = Long.valueOf(m.group(1)).longValue();
                    break;
                }
            }
        }
        if (httpHeaders.getAge() != null) {
            cacheTimeInSec -= httpHeaders.getAge().longValue();
        }
        return Math.max(0, cacheTimeInSec);
    }
}
