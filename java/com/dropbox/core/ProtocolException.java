package com.dropbox.core;

public abstract class ProtocolException extends DbxException {
    private static final long serialVersionUID = 0;

    public ProtocolException(String requestId, String message) {
        super(requestId, message);
    }

    public ProtocolException(String requestId, String message, Throwable cause) {
        super(requestId, message, cause);
    }
}
