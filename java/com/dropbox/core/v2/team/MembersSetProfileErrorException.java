package com.dropbox.core.v2.team;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class MembersSetProfileErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final MembersSetProfileError errorValue;

    public MembersSetProfileErrorException(String routeName, String requestId, LocalizedText userMessage, MembersSetProfileError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
