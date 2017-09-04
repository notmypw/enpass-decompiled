package com.dropbox.core.v2.files;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class SearchErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final SearchError errorValue;

    public SearchErrorException(String routeName, String requestId, LocalizedText userMessage, SearchError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
