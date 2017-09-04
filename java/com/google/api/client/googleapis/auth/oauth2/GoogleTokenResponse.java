package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.io.IOException;

public class GoogleTokenResponse extends TokenResponse {
    @Key("id_token")
    private String idToken;

    public GoogleTokenResponse setAccessToken(String accessToken) {
        return (GoogleTokenResponse) super.setAccessToken(accessToken);
    }

    public GoogleTokenResponse setTokenType(String tokenType) {
        return (GoogleTokenResponse) super.setTokenType(tokenType);
    }

    public GoogleTokenResponse setExpiresInSeconds(Long expiresIn) {
        return (GoogleTokenResponse) super.setExpiresInSeconds(expiresIn);
    }

    public GoogleTokenResponse setRefreshToken(String refreshToken) {
        return (GoogleTokenResponse) super.setRefreshToken(refreshToken);
    }

    public GoogleTokenResponse setScope(String scope) {
        return (GoogleTokenResponse) super.setScope(scope);
    }

    @Beta
    public final String getIdToken() {
        return this.idToken;
    }

    @Beta
    public GoogleTokenResponse setIdToken(String idToken) {
        this.idToken = (String) Preconditions.checkNotNull(idToken);
        return this;
    }

    @Beta
    public GoogleIdToken parseIdToken() throws IOException {
        return GoogleIdToken.parse(getFactory(), getIdToken());
    }

    public GoogleTokenResponse set(String fieldName, Object value) {
        return (GoogleTokenResponse) super.set(fieldName, value);
    }

    public GoogleTokenResponse clone() {
        return (GoogleTokenResponse) super.clone();
    }
}
