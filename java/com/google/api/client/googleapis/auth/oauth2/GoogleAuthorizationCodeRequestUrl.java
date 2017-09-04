package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.util.Collection;

public class GoogleAuthorizationCodeRequestUrl extends AuthorizationCodeRequestUrl {
    @Key("access_type")
    private String accessType;
    @Key("approval_prompt")
    private String approvalPrompt;

    public GoogleAuthorizationCodeRequestUrl(String clientId, String redirectUri, Collection<String> scopes) {
        this(GoogleOAuthConstants.AUTHORIZATION_SERVER_URL, clientId, redirectUri, scopes);
    }

    public GoogleAuthorizationCodeRequestUrl(String authorizationServerEncodedUrl, String clientId, String redirectUri, Collection<String> scopes) {
        super(authorizationServerEncodedUrl, clientId);
        setRedirectUri(redirectUri);
        setScopes((Collection) scopes);
    }

    public GoogleAuthorizationCodeRequestUrl(GoogleClientSecrets clientSecrets, String redirectUri, Collection<String> scopes) {
        this(clientSecrets.getDetails().getClientId(), redirectUri, (Collection) scopes);
    }

    public final String getApprovalPrompt() {
        return this.approvalPrompt;
    }

    public GoogleAuthorizationCodeRequestUrl setApprovalPrompt(String approvalPrompt) {
        this.approvalPrompt = approvalPrompt;
        return this;
    }

    public final String getAccessType() {
        return this.accessType;
    }

    public GoogleAuthorizationCodeRequestUrl setAccessType(String accessType) {
        this.accessType = accessType;
        return this;
    }

    public GoogleAuthorizationCodeRequestUrl setResponseTypes(Collection<String> responseTypes) {
        return (GoogleAuthorizationCodeRequestUrl) super.setResponseTypes((Collection) responseTypes);
    }

    public GoogleAuthorizationCodeRequestUrl setRedirectUri(String redirectUri) {
        Preconditions.checkNotNull(redirectUri);
        return (GoogleAuthorizationCodeRequestUrl) super.setRedirectUri(redirectUri);
    }

    public GoogleAuthorizationCodeRequestUrl setScopes(Collection<String> scopes) {
        Preconditions.checkArgument(scopes.iterator().hasNext());
        return (GoogleAuthorizationCodeRequestUrl) super.setScopes((Collection) scopes);
    }

    public GoogleAuthorizationCodeRequestUrl setClientId(String clientId) {
        return (GoogleAuthorizationCodeRequestUrl) super.setClientId(clientId);
    }

    public GoogleAuthorizationCodeRequestUrl setState(String state) {
        return (GoogleAuthorizationCodeRequestUrl) super.setState(state);
    }

    public GoogleAuthorizationCodeRequestUrl set(String fieldName, Object value) {
        return (GoogleAuthorizationCodeRequestUrl) super.set(fieldName, value);
    }

    public GoogleAuthorizationCodeRequestUrl clone() {
        return (GoogleAuthorizationCodeRequestUrl) super.clone();
    }
}
