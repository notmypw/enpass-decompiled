package com.dropbox.core.v2.team;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class MembersSuspendErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final MembersSuspendError errorValue;

    public MembersSuspendErrorException(String routeName, String requestId, LocalizedText userMessage, MembersSuspendError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
