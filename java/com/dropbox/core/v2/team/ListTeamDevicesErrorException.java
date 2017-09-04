package com.dropbox.core.v2.team;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class ListTeamDevicesErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final ListTeamDevicesError errorValue;

    public ListTeamDevicesErrorException(String routeName, String requestId, LocalizedText userMessage, ListTeamDevicesError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
