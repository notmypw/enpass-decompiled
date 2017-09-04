package com.dropbox.core.v2.auth;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class TokenFromOAuth1ErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final TokenFromOAuth1Error errorValue;

    public TokenFromOAuth1ErrorException(String routeName, String requestId, LocalizedText userMessage, TokenFromOAuth1Error errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
