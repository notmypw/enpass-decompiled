package com.google.api.client.util;

import java.io.IOException;

public class ExponentialBackOff implements BackOff {
    public static final int DEFAULT_INITIAL_INTERVAL_MILLIS = 500;
    public static final int DEFAULT_MAX_ELAPSED_TIME_MILLIS = 900000;
    public static final int DEFAULT_MAX_INTERVAL_MILLIS = 60000;
    public static final double DEFAULT_MULTIPLIER = 1.5d;
    public static final double DEFAULT_RANDOMIZATION_FACTOR = 0.5d;
    private int currentIntervalMillis;
    private final int initialIntervalMillis;
    private final int maxElapsedTimeMillis;
    private final int maxIntervalMillis;
    private final double multiplier;
    private final NanoClock nanoClock;
    private final double randomizationFactor;
    long startTimeNanos;

    public static class Builder {
        int initialIntervalMillis = ExponentialBackOff.DEFAULT_INITIAL_INTERVAL_MILLIS;
        int maxElapsedTimeMillis = ExponentialBackOff.DEFAULT_MAX_ELAPSED_TIME_MILLIS;
        int maxIntervalMillis = ExponentialBackOff.DEFAULT_MAX_INTERVAL_MILLIS;
        double multiplier = ExponentialBackOff.DEFAULT_MULTIPLIER;
        NanoClock nanoClock = NanoClock.SYSTEM;
        double randomizationFactor = ExponentialBackOff.DEFAULT_RANDOMIZATION_FACTOR;

        public ExponentialBackOff build() {
            return new ExponentialBackOff(this);
        }

        public final int getInitialIntervalMillis() {
            return this.initialIntervalMillis;
        }

        public Builder setInitialIntervalMillis(int initialIntervalMillis) {
            this.initialIntervalMillis = initialIntervalMillis;
            return this;
        }

        public final double getRandomizationFactor() {
            return this.randomizationFactor;
        }

        public Builder setRandomizationFactor(double randomizationFactor) {
            this.randomizationFactor = randomizationFactor;
            return this;
        }

        public final double getMultiplier() {
            return this.multiplier;
        }

        public Builder setMultiplier(double multiplier) {
            this.multiplier = multiplier;
            return this;
        }

        public final int getMaxIntervalMillis() {
            return this.maxIntervalMillis;
        }

        public Builder setMaxIntervalMillis(int maxIntervalMillis) {
            this.maxIntervalMillis = maxIntervalMillis;
            return this;
        }

        public final int getMaxElapsedTimeMillis() {
            return this.maxElapsedTimeMillis;
        }

        public Builder setMaxElapsedTimeMillis(int maxElapsedTimeMillis) {
            this.maxElapsedTimeMillis = maxElapsedTimeMillis;
            return this;
        }

        public final NanoClock getNanoClock() {
            return this.nanoClock;
        }

        public Builder setNanoClock(NanoClock nanoClock) {
            this.nanoClock = (NanoClock) Preconditions.checkNotNull(nanoClock);
            return this;
        }
    }

    public ExponentialBackOff() {
        this(new Builder());
    }

    protected ExponentialBackOff(Builder builder) {
        boolean z;
        boolean z2 = true;
        this.initialIntervalMillis = builder.initialIntervalMillis;
        this.randomizationFactor = builder.randomizationFactor;
        this.multiplier = builder.multiplier;
        this.maxIntervalMillis = builder.maxIntervalMillis;
        this.maxElapsedTimeMillis = builder.maxElapsedTimeMillis;
        this.nanoClock = builder.nanoClock;
        Preconditions.checkArgument(this.initialIntervalMillis > 0);
        if (0.0d > this.randomizationFactor || this.randomizationFactor >= 1.0d) {
            z = false;
        } else {
            z = true;
        }
        Preconditions.checkArgument(z);
        if (this.multiplier >= 1.0d) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        if (this.maxIntervalMillis >= this.initialIntervalMillis) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        if (this.maxElapsedTimeMillis <= 0) {
            z2 = false;
        }
        Preconditions.checkArgument(z2);
        reset();
    }

    public final void reset() {
        this.currentIntervalMillis = this.initialIntervalMillis;
        this.startTimeNanos = this.nanoClock.nanoTime();
    }

    public long nextBackOffMillis() throws IOException {
        if (getElapsedTimeMillis() > ((long) this.maxElapsedTimeMillis)) {
            return -1;
        }
        int randomizedInterval = getRandomValueFromInterval(this.randomizationFactor, Math.random(), this.currentIntervalMillis);
        incrementCurrentInterval();
        return (long) randomizedInterval;
    }

    static int getRandomValueFromInterval(double randomizationFactor, double random, int currentIntervalMillis) {
        double delta = randomizationFactor * ((double) currentIntervalMillis);
        double minInterval = ((double) currentIntervalMillis) - delta;
        return (int) (((((((double) currentIntervalMillis) + delta) - minInterval) + 1.0d) * random) + minInterval);
    }

    public final int getInitialIntervalMillis() {
        return this.initialIntervalMillis;
    }

    public final double getRandomizationFactor() {
        return this.randomizationFactor;
    }

    public final int getCurrentIntervalMillis() {
        return this.currentIntervalMillis;
    }

    public final double getMultiplier() {
        return this.multiplier;
    }

    public final int getMaxIntervalMillis() {
        return this.maxIntervalMillis;
    }

    public final int getMaxElapsedTimeMillis() {
        return this.maxElapsedTimeMillis;
    }

    public final long getElapsedTimeMillis() {
        return (this.nanoClock.nanoTime() - this.startTimeNanos) / 1000000;
    }

    private void incrementCurrentInterval() {
        if (((double) this.currentIntervalMillis) >= ((double) this.maxIntervalMillis) / this.multiplier) {
            this.currentIntervalMillis = this.maxIntervalMillis;
        } else {
            this.currentIntervalMillis = (int) (((double) this.currentIntervalMillis) * this.multiplier);
        }
    }
}
