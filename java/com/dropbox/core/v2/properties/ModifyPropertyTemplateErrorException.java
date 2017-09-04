package com.dropbox.core.v2.properties;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class ModifyPropertyTemplateErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final ModifyPropertyTemplateError errorValue;

    public ModifyPropertyTemplateErrorException(String routeName, String requestId, LocalizedText userMessage, ModifyPropertyTemplateError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
