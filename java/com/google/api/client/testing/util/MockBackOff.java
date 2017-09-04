package com.google.api.client.testing.util;

import com.google.api.client.util.BackOff;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Preconditions;
import java.io.IOException;

@Beta
public class MockBackOff implements BackOff {
    private long backOffMillis;
    private int maxTries = 10;
    private int numTries;

    public void reset() throws IOException {
        this.numTries = 0;
    }

    public long nextBackOffMillis() throws IOException {
        if (this.numTries >= this.maxTries || this.backOffMillis == -1) {
            return -1;
        }
        this.numTries++;
        return this.backOffMillis;
    }

    public MockBackOff setBackOffMillis(long backOffMillis) {
        boolean z = backOffMillis == -1 || backOffMillis >= 0;
        Preconditions.checkArgument(z);
        this.backOffMillis = backOffMillis;
        return this;
    }

    public MockBackOff setMaxTries(int maxTries) {
        Preconditions.checkArgument(maxTries >= 0);
        this.maxTries = maxTries;
        return this;
    }

    public final int getMaxTries() {
        return this.numTries;
    }

    public final int getNumberOfTries() {
        return this.numTries;
    }
}
