package com.dropbox.core.v2.team;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class MembersListErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final MembersListError errorValue;

    public MembersListErrorException(String routeName, String requestId, LocalizedText userMessage, MembersListError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}