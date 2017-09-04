package com.dropbox.core.v2.team;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class ListMembersAppsErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final ListMembersAppsError errorValue;

    public ListMembersAppsErrorException(String routeName, String requestId, LocalizedText userMessage, ListMembersAppsError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
