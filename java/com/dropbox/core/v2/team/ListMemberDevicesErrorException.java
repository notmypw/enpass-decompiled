package com.dropbox.core.v2.team;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class ListMemberDevicesErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final ListMemberDevicesError errorValue;

    public ListMemberDevicesErrorException(String routeName, String requestId, LocalizedText userMessage, ListMemberDevicesError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
