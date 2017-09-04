package com.google.api.client.googleapis.compute;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.OAuth2Utils;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Collection;

@Beta
public class ComputeCredential extends Credential {
    public static final String TOKEN_SERVER_ENCODED_URL = (OAuth2Utils.getMetadataServerUrl() + "/computeMetadata/v1/instance/service-accounts/default/token");

    @Beta
    public static class Builder extends com.google.api.client.auth.oauth2.Credential.Builder {
        public Builder(HttpTransport transport, JsonFactory jsonFactory) {
            super(BearerToken.authorizationHeaderAccessMethod());
            setTransport(transport);
            setJsonFactory(jsonFactory);
            setTokenServerEncodedUrl(ComputeCredential.TOKEN_SERVER_ENCODED_URL);
        }

        public ComputeCredential build() {
            return new ComputeCredential(this);
        }

        public Builder setTransport(HttpTransport transport) {
            return (Builder) super.setTransport((HttpTransport) Preconditions.checkNotNull(transport));
        }

        public Builder setClock(Clock clock) {
            return (Builder) super.setClock(clock);
        }

        public Builder setJsonFactory(JsonFactory jsonFactory) {
            return (Builder) super.setJsonFactory((JsonFactory) Preconditions.checkNotNull(jsonFactory));
        }

        public Builder setTokenServerUrl(GenericUrl tokenServerUrl) {
            return (Builder) super.setTokenServerUrl((GenericUrl) Preconditions.checkNotNull(tokenServerUrl));
        }

        public Builder setTokenServerEncodedUrl(String tokenServerEncodedUrl) {
            return (Builder) super.setTokenServerEncodedUrl((String) Preconditions.checkNotNull(tokenServerEncodedUrl));
        }

        public Builder setClientAuthentication(HttpExecuteInterceptor clientAuthentication) {
            Preconditions.checkArgument(clientAuthentication == null);
            return this;
        }

        public Builder setRequestInitializer(HttpRequestInitializer requestInitializer) {
            return (Builder) super.setRequestInitializer(requestInitializer);
        }

        public Builder addRefreshListener(CredentialRefreshListener refreshListener) {
            return (Builder) super.addRefreshListener(refreshListener);
        }

        public Builder setRefreshListeners(Collection<CredentialRefreshListener> refreshListeners) {
            return (Builder) super.setRefreshListeners(refreshListeners);
        }
    }

    public ComputeCredential(HttpTransport transport, JsonFactory jsonFactory) {
        this(new Builder(transport, jsonFactory));
    }

    protected ComputeCredential(Builder builder) {
        super((com.google.api.client.auth.oauth2.Credential.Builder) builder);
    }

    protected TokenResponse executeRefreshToken() throws IOException {
        HttpRequest request = getTransport().createRequestFactory().buildGetRequest(new GenericUrl(getTokenServerEncodedUrl()));
        request.setParser(new JsonObjectParser(getJsonFactory()));
        request.getHeaders().set("Metadata-Flavor", (Object) "Google");
        return (TokenResponse) request.execute().parseAs(TokenResponse.class);
    }
}
