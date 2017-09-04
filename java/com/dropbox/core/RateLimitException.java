package com.dropbox.core;

import java.util.concurrent.TimeUnit;

public class RateLimitException extends RetryException {
    private static final long serialVersionUID = 0;

    public RateLimitException(String requestId, String message, long backoff, TimeUnit unit) {
        super(requestId, message, backoff, unit);
    }
}
