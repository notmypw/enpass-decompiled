package com.dropbox.core;

public class DbxException extends Exception {
    private static final long serialVersionUID = 0;
    private final String requestId;

    public DbxException(String message) {
        this(null, message);
    }

    public DbxException(String requestId, String message) {
        super(message);
        this.requestId = requestId;
    }

    public DbxException(String message, Throwable cause) {
        this(null, message, cause);
    }

    public DbxException(String requestId, String message, Throwable cause) {
        super(message, cause);
        this.requestId = requestId;
    }

    public String getRequestId() {
        return this.requestId;
    }
}
