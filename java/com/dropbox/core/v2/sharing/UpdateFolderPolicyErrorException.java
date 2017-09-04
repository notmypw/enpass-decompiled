package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class UpdateFolderPolicyErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final UpdateFolderPolicyError errorValue;

    public UpdateFolderPolicyErrorException(String routeName, String requestId, LocalizedText userMessage, UpdateFolderPolicyError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
