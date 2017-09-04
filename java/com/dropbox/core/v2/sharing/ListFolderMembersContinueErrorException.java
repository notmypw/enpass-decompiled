package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class ListFolderMembersContinueErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final ListFolderMembersContinueError errorValue;

    public ListFolderMembersContinueErrorException(String routeName, String requestId, LocalizedText userMessage, ListFolderMembersContinueError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
