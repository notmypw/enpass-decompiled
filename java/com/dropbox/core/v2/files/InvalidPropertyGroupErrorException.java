package com.dropbox.core.v2.files;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class InvalidPropertyGroupErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final InvalidPropertyGroupError errorValue;

    public InvalidPropertyGroupErrorException(String routeName, String requestId, LocalizedText userMessage, InvalidPropertyGroupError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
