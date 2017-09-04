package com.dropbox.core.v2.team;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class ListMembersDevicesErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final ListMembersDevicesError errorValue;

    public ListMembersDevicesErrorException(String routeName, String requestId, LocalizedText userMessage, ListMembersDevicesError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
