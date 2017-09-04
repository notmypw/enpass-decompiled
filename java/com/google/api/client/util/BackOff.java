package com.google.api.client.util;

import java.io.IOException;

public interface BackOff {
    public static final long STOP = -1;
    public static final BackOff STOP_BACKOFF = new BackOff() {
        public void reset() throws IOException {
        }

        public long nextBackOffMillis() throws IOException {
            return BackOff.STOP;
        }
    };
    public static final BackOff ZERO_BACKOFF = new BackOff() {
        public void reset() throws IOException {
        }

        public long nextBackOffMillis() throws IOException {
            return 0;
        }
    };

    long nextBackOffMillis() throws IOException;

    void reset() throws IOException;
}
