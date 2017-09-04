package com.google.api.client.googleapis.services.json;

import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.CommonGoogleClientRequestInitializer;
import java.io.IOException;

public class CommonGoogleJsonClientRequestInitializer extends CommonGoogleClientRequestInitializer {
    public CommonGoogleJsonClientRequestInitializer(String key) {
        super(key);
    }

    public CommonGoogleJsonClientRequestInitializer(String key, String userIp) {
        super(key, userIp);
    }

    public final void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
        super.initialize(request);
        initializeJsonRequest((AbstractGoogleJsonClientRequest) request);
    }

    protected void initializeJsonRequest(AbstractGoogleJsonClientRequest<?> abstractGoogleJsonClientRequest) throws IOException {
    }
}
