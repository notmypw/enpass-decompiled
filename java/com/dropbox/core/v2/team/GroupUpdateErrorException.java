package com.dropbox.core.v2.team;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class GroupUpdateErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final GroupUpdateError errorValue;

    public GroupUpdateErrorException(String routeName, String requestId, LocalizedText userMessage, GroupUpdateError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
