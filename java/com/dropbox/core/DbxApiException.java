package com.dropbox.core;

public class DbxApiException extends DbxException {
    private static final long serialVersionUID = 0;
    private final LocalizedText userMessage;

    public DbxApiException(String requestId, LocalizedText userMessage, String message) {
        super(requestId, message);
        this.userMessage = userMessage;
    }

    public DbxApiException(String requestId, LocalizedText userMessage, String message, Throwable cause) {
        super(requestId, message, cause);
        this.userMessage = userMessage;
    }

    public LocalizedText getUserMessage() {
        return this.userMessage;
    }

    protected static String buildMessage(String routeName, LocalizedText userMessage) {
        return buildMessage(routeName, userMessage, null);
    }

    protected static String buildMessage(String routeName, LocalizedText userMessage, Object errorValue) {
        StringBuilder sb = new StringBuilder();
        sb.append("Exception in ").append(routeName);
        if (errorValue != null) {
            sb.append(": ").append(errorValue);
        }
        if (userMessage != null) {
            sb.append(" (user message: ").append(userMessage).append(")");
        }
        return sb.toString();
    }
}
