package com.dropbox.core;

public class InvalidAccessTokenException extends DbxException {
    private static final long serialVersionUID = 0;

    public InvalidAccessTokenException(String requestId, String message) {
        super(requestId, message);
    }
}
