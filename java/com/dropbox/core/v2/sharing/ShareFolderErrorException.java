package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class ShareFolderErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final ShareFolderError errorValue;

    public ShareFolderErrorException(String routeName, String requestId, LocalizedText userMessage, ShareFolderError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
