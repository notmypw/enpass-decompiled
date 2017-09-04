package com.dropbox.core.v2.paper;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class ListDocsCursorErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final ListDocsCursorError errorValue;

    public ListDocsCursorErrorException(String routeName, String requestId, LocalizedText userMessage, ListDocsCursorError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
