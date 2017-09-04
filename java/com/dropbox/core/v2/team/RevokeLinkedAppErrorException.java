package com.dropbox.core.v2.team;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class RevokeLinkedAppErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final RevokeLinkedAppError errorValue;

    public RevokeLinkedAppErrorException(String routeName, String requestId, LocalizedText userMessage, RevokeLinkedAppError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
