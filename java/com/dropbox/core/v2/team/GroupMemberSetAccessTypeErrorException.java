package com.dropbox.core.v2.team;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class GroupMemberSetAccessTypeErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final GroupMemberSetAccessTypeError errorValue;

    public GroupMemberSetAccessTypeErrorException(String routeName, String requestId, LocalizedText userMessage, GroupMemberSetAccessTypeError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
