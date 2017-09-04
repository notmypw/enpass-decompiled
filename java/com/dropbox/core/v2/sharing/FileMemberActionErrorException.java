package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class FileMemberActionErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final FileMemberActionError errorValue;

    public FileMemberActionErrorException(String routeName, String requestId, LocalizedText userMessage, FileMemberActionError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
