package com.google.api.client.googleapis.services;

import java.io.IOException;

public class CommonGoogleClientRequestInitializer implements GoogleClientRequestInitializer {
    private final String key;
    private final String userIp;

    public CommonGoogleClientRequestInitializer() {
        this(null);
    }

    public CommonGoogleClientRequestInitializer(String key) {
        this(key, null);
    }

    public CommonGoogleClientRequestInitializer(String key, String userIp) {
        this.key = key;
        this.userIp = userIp;
    }

    public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
        if (this.key != null) {
            request.put("key", (Object) this.key);
        }
        if (this.userIp != null) {
            request.put("userIp", (Object) this.userIp);
        }
    }

    public final String getKey() {
        return this.key;
    }

    public final String getUserIp() {
        return this.userIp;
    }
}
