package com.google.api.client.auth.oauth2;

import com.box.androidsdk.content.BoxConstants;
import com.box.androidsdk.content.auth.BoxAuthentication.BoxAuthenticationInfo;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.util.Data;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Map;

public class ClientParametersAuthentication implements HttpExecuteInterceptor, HttpRequestInitializer {
    private final String clientId;
    private final String clientSecret;

    public ClientParametersAuthentication(String clientId, String clientSecret) {
        this.clientId = (String) Preconditions.checkNotNull(clientId);
        this.clientSecret = clientSecret;
    }

    public void initialize(HttpRequest request) throws IOException {
        request.setInterceptor(this);
    }

    public void intercept(HttpRequest request) throws IOException {
        Map<String, Object> data = Data.mapOf(UrlEncodedContent.getContent(request).getData());
        data.put(BoxAuthenticationInfo.FIELD_CLIENT_ID, this.clientId);
        if (this.clientSecret != null) {
            data.put(BoxConstants.KEY_CLIENT_SECRET, this.clientSecret);
        }
    }

    public final String getClientId() {
        return this.clientId;
    }

    public final String getClientSecret() {
        return this.clientSecret;
    }
}
