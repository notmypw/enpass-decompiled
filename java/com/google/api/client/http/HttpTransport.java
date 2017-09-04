package com.google.api.client.http;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public abstract class HttpTransport {
    static final Logger LOGGER = Logger.getLogger(HttpTransport.class.getName());
    private static final String[] SUPPORTED_METHODS = new String[]{HttpMethods.DELETE, HttpMethods.GET, HttpMethods.POST, HttpMethods.PUT};

    protected abstract LowLevelHttpRequest buildRequest(String str, String str2) throws IOException;

    static {
        Arrays.sort(SUPPORTED_METHODS);
    }

    public final HttpRequestFactory createRequestFactory() {
        return createRequestFactory(null);
    }

    public final HttpRequestFactory createRequestFactory(HttpRequestInitializer initializer) {
        return new HttpRequestFactory(this, initializer);
    }

    HttpRequest buildRequest() {
        return new HttpRequest(this, null);
    }

    public boolean supportsMethod(String method) throws IOException {
        return Arrays.binarySearch(SUPPORTED_METHODS, method) >= 0;
    }

    public void shutdown() throws IOException {
    }
}
