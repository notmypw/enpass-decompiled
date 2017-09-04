package com.google.api.client.testing.http;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

@Beta
public class MockHttpTransport extends HttpTransport {
    private MockLowLevelHttpRequest lowLevelHttpRequest;
    private MockLowLevelHttpResponse lowLevelHttpResponse;
    private Set<String> supportedMethods;

    @Beta
    public static class Builder {
        MockLowLevelHttpRequest lowLevelHttpRequest;
        MockLowLevelHttpResponse lowLevelHttpResponse;
        Set<String> supportedMethods;

        public MockHttpTransport build() {
            return new MockHttpTransport(this);
        }

        public final Set<String> getSupportedMethods() {
            return this.supportedMethods;
        }

        public final Builder setSupportedMethods(Set<String> supportedMethods) {
            this.supportedMethods = supportedMethods;
            return this;
        }

        public final Builder setLowLevelHttpRequest(MockLowLevelHttpRequest lowLevelHttpRequest) {
            Preconditions.checkState(this.lowLevelHttpResponse == null, "Cannnot set a low level HTTP request when a low level HTTP response has been set.");
            this.lowLevelHttpRequest = lowLevelHttpRequest;
            return this;
        }

        public final MockLowLevelHttpRequest getLowLevelHttpRequest() {
            return this.lowLevelHttpRequest;
        }

        public final Builder setLowLevelHttpResponse(MockLowLevelHttpResponse lowLevelHttpResponse) {
            Preconditions.checkState(this.lowLevelHttpRequest == null, "Cannot set a low level HTTP response when a low level HTTP request has been set.");
            this.lowLevelHttpResponse = lowLevelHttpResponse;
            return this;
        }

        MockLowLevelHttpResponse getLowLevelHttpResponse() {
            return this.lowLevelHttpResponse;
        }
    }

    protected MockHttpTransport(Builder builder) {
        this.supportedMethods = builder.supportedMethods;
        this.lowLevelHttpRequest = builder.lowLevelHttpRequest;
        this.lowLevelHttpResponse = builder.lowLevelHttpResponse;
    }

    public boolean supportsMethod(String method) throws IOException {
        return this.supportedMethods == null || this.supportedMethods.contains(method);
    }

    public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        Preconditions.checkArgument(supportsMethod(method), "HTTP method %s not supported", method);
        if (this.lowLevelHttpRequest != null) {
            return this.lowLevelHttpRequest;
        }
        LowLevelHttpRequest request = new MockLowLevelHttpRequest(url);
        if (this.lowLevelHttpResponse == null) {
            return request;
        }
        request.setResponse(this.lowLevelHttpResponse);
        return request;
    }

    public final Set<String> getSupportedMethods() {
        return this.supportedMethods == null ? null : Collections.unmodifiableSet(this.supportedMethods);
    }

    public final MockLowLevelHttpRequest getLowLevelHttpRequest() {
        return this.lowLevelHttpRequest;
    }

    @Deprecated
    public static Builder builder() {
        return new Builder();
    }
}
