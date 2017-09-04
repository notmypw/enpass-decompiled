package com.dropbox.core.v2.team;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class TeamFolderArchiveErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final TeamFolderArchiveError errorValue;

    public TeamFolderArchiveErrorException(String routeName, String requestId, LocalizedText userMessage, TeamFolderArchiveError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
