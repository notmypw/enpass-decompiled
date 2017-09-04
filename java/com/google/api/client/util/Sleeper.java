package com.google.api.client.util;

public interface Sleeper {
    public static final Sleeper DEFAULT = new Sleeper() {
        public void sleep(long millis) throws InterruptedException {
            Thread.sleep(millis);
        }
    };

    void sleep(long j) throws InterruptedException;
}
