package com.google.api.client.googleapis.services.json;

import com.box.androidsdk.content.requests.BoxRequest.BoxRequestHandler;
import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public abstract class AbstractGoogleJsonClient extends AbstractGoogleClient {

    public static abstract class Builder extends com.google.api.client.googleapis.services.AbstractGoogleClient.Builder {
        public abstract AbstractGoogleJsonClient build();

        protected Builder(HttpTransport transport, JsonFactory jsonFactory, String rootUrl, String servicePath, HttpRequestInitializer httpRequestInitializer, boolean legacyDataWrapper) {
            Collection asList;
            com.google.api.client.json.JsonObjectParser.Builder builder = new com.google.api.client.json.JsonObjectParser.Builder(jsonFactory);
            if (legacyDataWrapper) {
                asList = Arrays.asList(new String[]{"data", BoxRequestHandler.OAUTH_ERROR_HEADER});
            } else {
                asList = Collections.emptySet();
            }
            super(transport, rootUrl, servicePath, builder.setWrapperKeys(asList).build(), httpRequestInitializer);
        }

        public final JsonObjectParser getObjectParser() {
            return (JsonObjectParser) super.getObjectParser();
        }

        public final JsonFactory getJsonFactory() {
            return getObjectParser().getJsonFactory();
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

    protected AbstractGoogleJsonClient(Builder builder) {
        super(builder);
    }

    public JsonObjectParser getObjectParser() {
        return (JsonObjectParser) super.getObjectParser();
    }

    public final JsonFactory getJsonFactory() {
        return getObjectParser().getJsonFactory();
    }
}
