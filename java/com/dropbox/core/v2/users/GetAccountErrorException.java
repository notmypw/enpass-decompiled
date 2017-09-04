package com.dropbox.core.v2.users;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class GetAccountErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final GetAccountError errorValue;

    public GetAccountErrorException(String routeName, String requestId, LocalizedText userMessage, GetAccountError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
