package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class CreateSharedLinkErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final CreateSharedLinkError errorValue;

    public CreateSharedLinkErrorException(String routeName, String requestId, LocalizedText userMessage, CreateSharedLinkError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
