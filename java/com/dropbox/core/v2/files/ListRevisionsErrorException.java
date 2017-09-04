package com.dropbox.core.v2.files;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class ListRevisionsErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final ListRevisionsError errorValue;

    public ListRevisionsErrorException(String routeName, String requestId, LocalizedText userMessage, ListRevisionsError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
