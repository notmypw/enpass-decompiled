package com.dropbox.core.v2.team;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class GroupMembersAddErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final GroupMembersAddError errorValue;

    public GroupMembersAddErrorException(String routeName, String requestId, LocalizedText userMessage, GroupMembersAddError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
