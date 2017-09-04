package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class ModifySharedLinkSettingsErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final ModifySharedLinkSettingsError errorValue;

    public ModifySharedLinkSettingsErrorException(String routeName, String requestId, LocalizedText userMessage, ModifySharedLinkSettingsError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
