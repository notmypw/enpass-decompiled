package com.google.api.client.auth.oauth2;

import com.box.androidsdk.content.models.BoxSharedLink;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.util.Collection;

public class PasswordTokenRequest extends TokenRequest {
    @Key
    private String password;
    @Key
    private String username;

    public PasswordTokenRequest(HttpTransport transport, JsonFactory jsonFactory, GenericUrl tokenServerUrl, String username, String password) {
        super(transport, jsonFactory, tokenServerUrl, BoxSharedLink.FIELD_PASSWORD);
        setUsername(username);
        setPassword(password);
    }

    public PasswordTokenRequest setRequestInitializer(HttpRequestInitializer requestInitializer) {
        return (PasswordTokenRequest) super.setRequestInitializer(requestInitializer);
    }

    public PasswordTokenRequest setTokenServerUrl(GenericUrl tokenServerUrl) {
        return (PasswordTokenRequest) super.setTokenServerUrl(tokenServerUrl);
    }

    public PasswordTokenRequest setScopes(Collection<String> scopes) {
        return (PasswordTokenRequest) super.setScopes(scopes);
    }

    public PasswordTokenRequest setGrantType(String grantType) {
        return (PasswordTokenRequest) super.setGrantType(grantType);
    }

    public PasswordTokenRequest setClientAuthentication(HttpExecuteInterceptor clientAuthentication) {
        return (PasswordTokenRequest) super.setClientAuthentication(clientAuthentication);
    }

    public final String getUsername() {
        return this.username;
    }

    public PasswordTokenRequest setUsername(String username) {
        this.username = (String) Preconditions.checkNotNull(username);
        return this;
    }

    public final String getPassword() {
        return this.password;
    }

    public PasswordTokenRequest setPassword(String password) {
        this.password = (String) Preconditions.checkNotNull(password);
        return this;
    }

    public PasswordTokenRequest set(String fieldName, Object value) {
        return (PasswordTokenRequest) super.set(fieldName, value);
    }
}
