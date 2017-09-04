package com.dropbox.core;

public class BadRequestException extends ProtocolException {
    private static final long serialVersionUID = 0;

    public BadRequestException(String requestId, String message) {
        super(requestId, message);
    }
}
