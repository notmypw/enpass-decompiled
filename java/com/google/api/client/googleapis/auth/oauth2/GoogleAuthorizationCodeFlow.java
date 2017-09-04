package com.google.api.client.googleapis.auth.oauth2;

import com.github.clans.fab.BuildConfig;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow.CredentialCreatedListener;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential.AccessMethod;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.CredentialStore;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import java.io.IOException;
import java.util.Collection;

public class GoogleAuthorizationCodeFlow extends AuthorizationCodeFlow {
    private final String accessType;
    private final String approvalPrompt;

    public static class Builder extends com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder {
        String accessType;
        String approvalPrompt;

        public Builder(HttpTransport transport, JsonFactory jsonFactory, String clientId, String clientSecret, Collection<String> scopes) {
            super(BearerToken.authorizationHeaderAccessMethod(), transport, jsonFactory, new GenericUrl(GoogleOAuthConstants.TOKEN_SERVER_URL), new ClientParametersAuthentication(clientId, clientSecret), clientId, GoogleOAuthConstants.AUTHORIZATION_SERVER_URL);
            setScopes((Collection) scopes);
        }

        public Builder(HttpTransport transport, JsonFactory jsonFactory, GoogleClientSecrets clientSecrets, Collection<String> scopes) {
            super(BearerToken.authorizationHeaderAccessMethod(), transport, jsonFactory, new GenericUrl(GoogleOAuthConstants.TOKEN_SERVER_URL), new ClientParametersAuthentication(clientSecrets.getDetails().getClientId(), clientSecrets.getDetails().getClientSecret()), clientSecrets.getDetails().getClientId(), GoogleOAuthConstants.AUTHORIZATION_SERVER_URL);
            setScopes((Collection) scopes);
        }

        public GoogleAuthorizationCodeFlow build() {
            return new GoogleAuthorizationCodeFlow(this);
        }

        public Builder setDataStoreFactory(DataStoreFactory dataStore) throws IOException {
            return (Builder) super.setDataStoreFactory(dataStore);
        }

        public Builder setCredentialDataStore(DataStore<StoredCredential> typedDataStore) {
            return (Builder) super.setCredentialDataStore(typedDataStore);
        }

        public Builder setCredentialCreatedListener(CredentialCreatedListener credentialCreatedListener) {
            return (Builder) super.setCredentialCreatedListener(credentialCreatedListener);
        }

        @Beta
        @Deprecated
        public Builder setCredentialStore(CredentialStore credentialStore) {
            return (Builder) super.setCredentialStore(credentialStore);
        }

        public Builder setRequestInitializer(HttpRequestInitializer requestInitializer) {
            return (Builder) super.setRequestInitializer(requestInitializer);
        }

        public Builder setScopes(Collection<String> scopes) {
            Preconditions.checkState(!scopes.isEmpty());
            return (Builder) super.setScopes(scopes);
        }

        public Builder setMethod(AccessMethod method) {
            return (Builder) super.setMethod(method);
        }

        public Builder setTransport(HttpTransport transport) {
            return (Builder) super.setTransport(transport);
        }

        public Builder setJsonFactory(JsonFactory jsonFactory) {
            return (Builder) super.setJsonFactory(jsonFactory);
        }

        public Builder setTokenServerUrl(GenericUrl tokenServerUrl) {
            return (Builder) super.setTokenServerUrl(tokenServerUrl);
        }

        public Builder setClientAuthentication(HttpExecuteInterceptor clientAuthentication) {
            return (Builder) super.setClientAuthentication(clientAuthentication);
        }

        public Builder setClientId(String clientId) {
            return (Builder) super.setClientId(clientId);
        }

        public Builder setAuthorizationServerEncodedUrl(String authorizationServerEncodedUrl) {
            return (Builder) super.setAuthorizationServerEncodedUrl(authorizationServerEncodedUrl);
        }

        public Builder setClock(Clock clock) {
            return (Builder) super.setClock(clock);
        }

        public Builder addRefreshListener(CredentialRefreshListener refreshListener) {
            return (Builder) super.addRefreshListener(refreshListener);
        }

        public Builder setRefreshListeners(Collection<CredentialRefreshListener> refreshListeners) {
            return (Builder) super.setRefreshListeners(refreshListeners);
        }

        public Builder setApprovalPrompt(String approvalPrompt) {
            this.approvalPrompt = approvalPrompt;
            return this;
        }

        public final String getApprovalPrompt() {
            return this.approvalPrompt;
        }

        public Builder setAccessType(String accessType) {
            this.accessType = accessType;
            return this;
        }

        public final String getAccessType() {
            return this.accessType;
        }
    }

    public GoogleAuthorizationCodeFlow(HttpTransport transport, JsonFactory jsonFactory, String clientId, String clientSecret, Collection<String> scopes) {
        this(new Builder(transport, jsonFactory, clientId, clientSecret, scopes));
    }

    protected GoogleAuthorizationCodeFlow(Builder builder) {
        super(builder);
        this.accessType = builder.accessType;
        this.approvalPrompt = builder.approvalPrompt;
    }

    public GoogleAuthorizationCodeTokenRequest newTokenRequest(String authorizationCode) {
        return new GoogleAuthorizationCodeTokenRequest(getTransport(), getJsonFactory(), getTokenServerEncodedUrl(), BuildConfig.FLAVOR, BuildConfig.FLAVOR, authorizationCode, BuildConfig.FLAVOR).setClientAuthentication(getClientAuthentication()).setRequestInitializer(getRequestInitializer()).setScopes(getScopes());
    }

    public GoogleAuthorizationCodeRequestUrl newAuthorizationUrl() {
        return new GoogleAuthorizationCodeRequestUrl(getAuthorizationServerEncodedUrl(), getClientId(), BuildConfig.FLAVOR, getScopes()).setAccessType(this.accessType).setApprovalPrompt(this.approvalPrompt);
    }

    public final String getApprovalPrompt() {
        return this.approvalPrompt;
    }

    public final String getAccessType() {
        return this.accessType;
    }
}
