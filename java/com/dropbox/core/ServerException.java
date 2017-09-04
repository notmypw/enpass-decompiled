package com.dropbox.core;

public class ServerException extends DbxException {
    private static final long serialVersionUID = 0;

    public ServerException(String requestId, String message) {
        super(requestId, message);
    }
}
