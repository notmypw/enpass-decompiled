package com.dropbox.core;

import java.util.concurrent.TimeUnit;

public class RetryException extends DbxException {
    private static final long serialVersionUID = 0;
    private final long backoffMillis;

    public RetryException(String requestId, String message) {
        this(requestId, message, 0, TimeUnit.MILLISECONDS);
    }

    public RetryException(String requestId, String message, long backoff, TimeUnit unit) {
        super(requestId, message);
        this.backoffMillis = unit.toMillis(backoff);
    }

    public long getBackoffMillis() {
        return this.backoffMillis;
    }
}
