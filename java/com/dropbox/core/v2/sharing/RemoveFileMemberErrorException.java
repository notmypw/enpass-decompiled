package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class RemoveFileMemberErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final RemoveFileMemberError errorValue;

    public RemoveFileMemberErrorException(String routeName, String requestId, LocalizedText userMessage, RemoveFileMemberError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
