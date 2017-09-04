package com.google.api.client.googleapis.testing.services;

import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.Beta;
import com.google.api.client.util.ObjectParser;

@Beta
public class MockGoogleClient extends AbstractGoogleClient {

    @Beta
    public static class Builder extends com.google.api.client.googleapis.services.AbstractGoogleClient.Builder {
        public Builder(HttpTransport transport, String rootUrl, String servicePath, ObjectParser objectParser, HttpRequestInitializer httpRequestInitializer) {
            super(transport, rootUrl, servicePath, objectParser, httpRequestInitializer);
        }

        public MockGoogleClient build() {
            return new MockGoogleClient(this);
        }

        public Builder setRootUrl(String rootUrl) {
            return (Builder) super.setRootUrl(rootUrl);
        }

        public Builder setServicePath(String servicePath) {
            return (Builder) super.setServicePath(servicePath);
        }

        public Builder setGoogleClientRequestInitializer(GoogleClientRequestInitializer googleClientRequestInitializer) {
            return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
        }

        public Builder setHttpRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
            return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
        }

        public Builder setApplicationName(String applicationName) {
            return (Builder) super.setApplicationName(applicationName);
        }

        public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
            return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
        }

        public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
            return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
        }

        public Builder setSuppressAllChecks(boolean suppressAllChecks) {
            return (Builder) super.setSuppressAllChecks(suppressAllChecks);
        }
    }

    public MockGoogleClient(HttpTransport transport, String rootUrl, String servicePath, ObjectParser objectParser, HttpRequestInitializer httpRequestInitializer) {
        this(new Builder(transport, rootUrl, servicePath, objectParser, httpRequestInitializer));
    }

    protected MockGoogleClient(Builder builder) {
        super(builder);
    }
}
