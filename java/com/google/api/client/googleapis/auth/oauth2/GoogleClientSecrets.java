package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public final class GoogleClientSecrets extends GenericJson {
    @Key
    private Details installed;
    @Key
    private Details web;

    public static final class Details extends GenericJson {
        @Key("auth_uri")
        private String authUri;
        @Key("client_id")
        private String clientId;
        @Key("client_secret")
        private String clientSecret;
        @Key("redirect_uris")
        private List<String> redirectUris;
        @Key("token_uri")
        private String tokenUri;

        public String getClientId() {
            return this.clientId;
        }

        public Details setClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public String getClientSecret() {
            return this.clientSecret;
        }

        public Details setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public List<String> getRedirectUris() {
            return this.redirectUris;
        }

        public Details setRedirectUris(List<String> redirectUris) {
            this.redirectUris = redirectUris;
            return this;
        }

        public String getAuthUri() {
            return this.authUri;
        }

        public Details setAuthUri(String authUri) {
            this.authUri = authUri;
            return this;
        }

        public String getTokenUri() {
            return this.tokenUri;
        }

        public Details setTokenUri(String tokenUri) {
            this.tokenUri = tokenUri;
            return this;
        }

        public Details set(String fieldName, Object value) {
            return (Details) super.set(fieldName, value);
        }

        public Details clone() {
            return (Details) super.clone();
        }
    }

    public Details getInstalled() {
        return this.installed;
    }

    public GoogleClientSecrets setInstalled(Details installed) {
        this.installed = installed;
        return this;
    }

    public Details getWeb() {
        return this.web;
    }

    public GoogleClientSecrets setWeb(Details web) {
        this.web = web;
        return this;
    }

    public Details getDetails() {
        boolean z;
        boolean z2 = true;
        if (this.web == null) {
            z = true;
        } else {
            z = false;
        }
        if (z == (this.installed == null)) {
            z2 = false;
        }
        Preconditions.checkArgument(z2);
        return this.web == null ? this.installed : this.web;
    }

    public GoogleClientSecrets set(String fieldName, Object value) {
        return (GoogleClientSecrets) super.set(fieldName, value);
    }

    public GoogleClientSecrets clone() {
        return (GoogleClientSecrets) super.clone();
    }

    public static GoogleClientSecrets load(JsonFactory jsonFactory, Reader reader) throws IOException {
        return (GoogleClientSecrets) jsonFactory.fromReader(reader, GoogleClientSecrets.class);
    }
}
