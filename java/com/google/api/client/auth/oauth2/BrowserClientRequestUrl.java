package com.google.api.client.auth.oauth2;

import com.box.androidsdk.content.BoxConstants;
import java.util.Collection;
import java.util.Collections;

public class BrowserClientRequestUrl extends AuthorizationRequestUrl {
    public BrowserClientRequestUrl(String encodedAuthorizationServerUrl, String clientId) {
        super(encodedAuthorizationServerUrl, clientId, Collections.singleton(BoxConstants.KEY_TOKEN));
    }

    public BrowserClientRequestUrl setResponseTypes(Collection<String> responseTypes) {
        return (BrowserClientRequestUrl) super.setResponseTypes(responseTypes);
    }

    public BrowserClientRequestUrl setRedirectUri(String redirectUri) {
        return (BrowserClientRequestUrl) super.setRedirectUri(redirectUri);
    }

    public BrowserClientRequestUrl setScopes(Collection<String> scopes) {
        return (BrowserClientRequestUrl) super.setScopes(scopes);
    }

    public BrowserClientRequestUrl setClientId(String clientId) {
        return (BrowserClientRequestUrl) super.setClientId(clientId);
    }

    public BrowserClientRequestUrl setState(String state) {
        return (BrowserClientRequestUrl) super.setState(state);
    }

    public BrowserClientRequestUrl set(String fieldName, Object value) {
        return (BrowserClientRequestUrl) super.set(fieldName, value);
    }

    public BrowserClientRequestUrl clone() {
        return (BrowserClientRequestUrl) super.clone();
    }
}
