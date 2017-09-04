package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.BrowserClientRequestUrl;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.util.Collection;

public class GoogleBrowserClientRequestUrl extends BrowserClientRequestUrl {
    @Key("approval_prompt")
    private String approvalPrompt;

    public GoogleBrowserClientRequestUrl(String clientId, String redirectUri, Collection<String> scopes) {
        super(GoogleOAuthConstants.AUTHORIZATION_SERVER_URL, clientId);
        setRedirectUri(redirectUri);
        setScopes((Collection) scopes);
    }

    public GoogleBrowserClientRequestUrl(GoogleClientSecrets clientSecrets, String redirectUri, Collection<String> scopes) {
        this(clientSecrets.getDetails().getClientId(), redirectUri, (Collection) scopes);
    }

    public final String getApprovalPrompt() {
        return this.approvalPrompt;
    }

    public GoogleBrowserClientRequestUrl setApprovalPrompt(String approvalPrompt) {
        this.approvalPrompt = approvalPrompt;
        return this;
    }

    public GoogleBrowserClientRequestUrl setResponseTypes(Collection<String> responseTypes) {
        return (GoogleBrowserClientRequestUrl) super.setResponseTypes((Collection) responseTypes);
    }

    public GoogleBrowserClientRequestUrl setRedirectUri(String redirectUri) {
        return (GoogleBrowserClientRequestUrl) super.setRedirectUri(redirectUri);
    }

    public GoogleBrowserClientRequestUrl setScopes(Collection<String> scopes) {
        Preconditions.checkArgument(scopes.iterator().hasNext());
        return (GoogleBrowserClientRequestUrl) super.setScopes((Collection) scopes);
    }

    public GoogleBrowserClientRequestUrl setClientId(String clientId) {
        return (GoogleBrowserClientRequestUrl) super.setClientId(clientId);
    }

    public GoogleBrowserClientRequestUrl setState(String state) {
        return (GoogleBrowserClientRequestUrl) super.setState(state);
    }

    public GoogleBrowserClientRequestUrl set(String fieldName, Object value) {
        return (GoogleBrowserClientRequestUrl) super.set(fieldName, value);
    }

    public GoogleBrowserClientRequestUrl clone() {
        return (GoogleBrowserClientRequestUrl) super.clone();
    }
}
