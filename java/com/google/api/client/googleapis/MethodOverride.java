package com.google.api.client.googleapis;

import com.google.api.client.http.EmptyContent;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.UrlEncodedContent;
import java.io.IOException;

public final class MethodOverride implements HttpExecuteInterceptor, HttpRequestInitializer {
    public static final String HEADER = "X-HTTP-Method-Override";
    static final int MAX_URL_LENGTH = 2048;
    private final boolean overrideAllMethods;

    public static final class Builder {
        private boolean overrideAllMethods;

        public MethodOverride build() {
            return new MethodOverride(this.overrideAllMethods);
        }

        public boolean getOverrideAllMethods() {
            return this.overrideAllMethods;
        }

        public Builder setOverrideAllMethods(boolean overrideAllMethods) {
            this.overrideAllMethods = overrideAllMethods;
            return this;
        }
    }

    public MethodOverride() {
        this(false);
    }

    MethodOverride(boolean overrideAllMethods) {
        this.overrideAllMethods = overrideAllMethods;
    }

    public void initialize(HttpRequest request) {
        request.setInterceptor(this);
    }

    public void intercept(HttpRequest request) throws IOException {
        if (overrideThisMethod(request)) {
            Object requestMethod = request.getRequestMethod();
            request.setRequestMethod(HttpMethods.POST);
            request.getHeaders().set(HEADER, requestMethod);
            if (requestMethod.equals(HttpMethods.GET)) {
                request.setContent(new UrlEncodedContent(request.getUrl().clone()));
                request.getUrl().clear();
            } else if (request.getContent() == null) {
                request.setContent(new EmptyContent());
            }
        }
    }

    private boolean overrideThisMethod(HttpRequest request) throws IOException {
        String requestMethod = request.getRequestMethod();
        if (requestMethod.equals(HttpMethods.POST)) {
            return false;
        }
        if (requestMethod.equals(HttpMethods.GET)) {
            if (request.getUrl().build().length() > MAX_URL_LENGTH) {
                return true;
            }
        } else if (this.overrideAllMethods) {
            return true;
        }
        if (request.getTransport().supportsMethod(requestMethod)) {
            return false;
        }
        return true;
    }
}
