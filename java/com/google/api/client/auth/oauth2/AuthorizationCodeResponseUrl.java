package com.google.api.client.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;

public class AuthorizationCodeResponseUrl extends GenericUrl {
    @Key
    private String code;
    @Key
    private String error;
    @Key("error_description")
    private String errorDescription;
    @Key("error_uri")
    private String errorUri;
    @Key
    private String state;

    public AuthorizationCodeResponseUrl(String encodedResponseUrl) {
        boolean z;
        boolean z2 = true;
        super(encodedResponseUrl);
        if (this.code == null) {
            z = true;
        } else {
            z = false;
        }
        if (z == (this.error == null)) {
            z2 = false;
        }
        Preconditions.checkArgument(z2);
    }

    public final String getCode() {
        return this.code;
    }

    public AuthorizationCodeResponseUrl setCode(String code) {
        this.code = code;
        return this;
    }

    public final String getState() {
        return this.state;
    }

    public AuthorizationCodeResponseUrl setState(String state) {
        this.state = state;
        return this;
    }

    public final String getError() {
        return this.error;
    }

    public AuthorizationCodeResponseUrl setError(String error) {
        this.error = error;
        return this;
    }

    public final String getErrorDescription() {
        return this.errorDescription;
    }

    public AuthorizationCodeResponseUrl setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
        return this;
    }

    public final String getErrorUri() {
        return this.errorUri;
    }

    public AuthorizationCodeResponseUrl setErrorUri(String errorUri) {
        this.errorUri = errorUri;
        return this;
    }

    public AuthorizationCodeResponseUrl set(String fieldName, Object value) {
        return (AuthorizationCodeResponseUrl) super.set(fieldName, value);
    }

    public AuthorizationCodeResponseUrl clone() {
        return (AuthorizationCodeResponseUrl) super.clone();
    }
}
