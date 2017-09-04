package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class ListFileMembersContinueErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final ListFileMembersContinueError errorValue;

    public ListFileMembersContinueErrorException(String routeName, String requestId, LocalizedText userMessage, ListFileMembersContinueError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
