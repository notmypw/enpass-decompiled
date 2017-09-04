package com.dropbox.core.v2.paper;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class DocLookupErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final DocLookupError errorValue;

    public DocLookupErrorException(String routeName, String requestId, LocalizedText userMessage, DocLookupError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
