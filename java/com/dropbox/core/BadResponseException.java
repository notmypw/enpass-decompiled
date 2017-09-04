package com.dropbox.core;

public class BadResponseException extends ProtocolException {
    private static final long serialVersionUID = 0;

    public BadResponseException(String requestId, String message) {
        super(requestId, message);
    }

    public BadResponseException(String requestId, String message, Throwable cause) {
        super(requestId, message, cause);
    }
}
