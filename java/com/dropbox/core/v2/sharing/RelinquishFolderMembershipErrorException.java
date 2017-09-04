package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class RelinquishFolderMembershipErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final RelinquishFolderMembershipError errorValue;

    public RelinquishFolderMembershipErrorException(String routeName, String requestId, LocalizedText userMessage, RelinquishFolderMembershipError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
