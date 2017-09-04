package com.dropbox.core.v2.async;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class PollErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final PollError errorValue;

    public PollErrorException(String routeName, String requestId, LocalizedText userMessage, PollError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
