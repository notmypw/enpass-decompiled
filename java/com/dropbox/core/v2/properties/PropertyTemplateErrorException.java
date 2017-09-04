package com.dropbox.core.v2.properties;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.LocalizedText;

public class PropertyTemplateErrorException extends DbxApiException {
    private static final long serialVersionUID = 0;
    public final PropertyTemplateError errorValue;

    public PropertyTemplateErrorException(String routeName, String requestId, LocalizedText userMessage, PropertyTemplateError errorValue) {
        super(requestId, userMessage, DbxApiException.buildMessage(routeName, userMessage, errorValue));
        if (errorValue == null) {
            throw new NullPointerException("errorValue");
        }
        this.errorValue = errorValue;
    }
}
