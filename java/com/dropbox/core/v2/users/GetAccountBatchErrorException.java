package com.dropbox.core.v2.users;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class GetAccountBatchErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final GetAccountBatchError errorValue;

    public GetAccountBatchErrorException(String routeName, String requestId, LocalizedText userMessage, GetAccountBatchError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
