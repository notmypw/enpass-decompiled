package com.google.api.client.googleapis.testing.services.json;

import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Beta;

@Beta
public class MockGoogleJsonClient extends AbstractGoogleJsonClient {

    @Beta
    public static class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {
        public Builder(HttpTransport transport, JsonFactory jsonFactory, String rootUrl, String servicePath, HttpRequestInitializer httpRequestInitializer, boolean legacyDataWrapper) {
            super(transport, jsonFactory, rootUrl, servicePath, httpRequestInitializer, legacyDataWrapper);
        }

        public MockGoogleJsonClient build() {
            return new MockGoogleJsonClient(this);
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

    protected MockGoogleJsonClient(Builder builder) {
        super(builder);
    }

    public MockGoogleJsonClient(HttpTransport transport, JsonFactory jsonFactory, String rootUrl, String servicePath, HttpRequestInitializer httpRequestInitializer, boolean legacyDataWrapper) {
        this(new Builder(transport, jsonFactory, rootUrl, servicePath, httpRequestInitializer, legacyDataWrapper));
    }
}
