package com.dropbox.core;

import java.io.IOException;

public class NetworkIOException extends DbxException {
    private static final long serialVersionUID = 0;

    public NetworkIOException(IOException cause) {
        super(cause.getMessage(), (Throwable) cause);
    }

    public IOException getCause() {
        return (IOException) super.getCause();
    }
}
