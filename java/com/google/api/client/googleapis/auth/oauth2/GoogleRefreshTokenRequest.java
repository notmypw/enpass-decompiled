package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.RefreshTokenRequest;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import java.io.IOException;
import java.util.Collection;

public class GoogleRefreshTokenRequest extends RefreshTokenRequest {
    public GoogleRefreshTokenRequest(HttpTransport transport, JsonFactory jsonFactory, String refreshToken, String clientId, String clientSecret) {
        super(transport, jsonFactory, new GenericUrl(GoogleOAuthConstants.TOKEN_SERVER_URL), refreshToken);
        setClientAuthentication(new ClientParametersAuthentication(clientId, clientSecret));
    }

    public GoogleRefreshTokenRequest setRequestInitializer(HttpRequestInitializer requestInitializer) {
        return (GoogleRefreshTokenRequest) super.setRequestInitializer(requestInitializer);
    }

    public GoogleRefreshTokenRequest setTokenServerUrl(GenericUrl tokenServerUrl) {
        return (GoogleRefreshTokenRequest) super.setTokenServerUrl(tokenServerUrl);
    }

    public GoogleRefreshTokenRequest setScopes(Collection<String> scopes) {
        return (GoogleRefreshTokenRequest) super.setScopes((Collection) scopes);
    }

    public GoogleRefreshTokenRequest setGrantType(String grantType) {
        return (GoogleRefreshTokenRequest) super.setGrantType(grantType);
    }

    public GoogleRefreshTokenRequest setClientAuthentication(HttpExecuteInterceptor clientAuthentication) {
        return (GoogleRefreshTokenRequest) super.setClientAuthentication(clientAuthentication);
    }

    public GoogleRefreshTokenRequest setRefreshToken(String refreshToken) {
        return (GoogleRefreshTokenRequest) super.setRefreshToken(refreshToken);
    }

    public GoogleTokenResponse execute() throws IOException {
        return (GoogleTokenResponse) executeUnparsed().parseAs(GoogleTokenResponse.class);
    }

    public GoogleRefreshTokenRequest set(String fieldName, Object value) {
        return (GoogleRefreshTokenRequest) super.set(fieldName, value);
    }
}
