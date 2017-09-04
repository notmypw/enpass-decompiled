package com.dropbox.core;

public class BadResponseCodeException extends BadResponseException {
    private static final long serialVersionUID = 0;
    private final int statusCode;

    public BadResponseCodeException(String requestId, String message, int statusCode) {
        super(requestId, message);
        this.statusCode = statusCode;
    }

    public BadResponseCodeException(String requestId, String message, int statusCode, Throwable cause) {
        super(requestId, message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
