package com.google.api.client.googleapis.services;

import com.github.clans.fab.BuildConfig;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Strings;
import java.io.IOException;
import java.util.logging.Logger;

public abstract class AbstractGoogleClient {
    static final Logger LOGGER = Logger.getLogger(AbstractGoogleClient.class.getName());
    private final String applicationName;
    private final GoogleClientRequestInitializer googleClientRequestInitializer;
    private final ObjectParser objectParser;
    private final HttpRequestFactory requestFactory;
    private final String rootUrl;
    private final String servicePath;
    private boolean suppressPatternChecks;
    private boolean suppressRequiredParameterChecks;

    public static abstract class Builder {
        String applicationName;
        GoogleClientRequestInitializer googleClientRequestInitializer;
        HttpRequestInitializer httpRequestInitializer;
        final ObjectParser objectParser;
        String rootUrl;
        String servicePath;
        boolean suppressPatternChecks;
        boolean suppressRequiredParameterChecks;
        final HttpTransport transport;

        public abstract AbstractGoogleClient build();

        protected Builder(HttpTransport transport, String rootUrl, String servicePath, ObjectParser objectParser, HttpRequestInitializer httpRequestInitializer) {
            this.transport = (HttpTransport) Preconditions.checkNotNull(transport);
            this.objectParser = objectParser;
            setRootUrl(rootUrl);
            setServicePath(servicePath);
            this.httpRequestInitializer = httpRequestInitializer;
        }

        public final HttpTransport getTransport() {
            return this.transport;
        }

        public ObjectParser getObjectParser() {
            return this.objectParser;
        }

        public final String getRootUrl() {
            return this.rootUrl;
        }

        public Builder setRootUrl(String rootUrl) {
            this.rootUrl = AbstractGoogleClient.normalizeRootUrl(rootUrl);
            return this;
        }

        public final String getServicePath() {
            return this.servicePath;
        }

        public Builder setServicePath(String servicePath) {
            this.servicePath = AbstractGoogleClient.normalizeServicePath(servicePath);
            return this;
        }

        public final GoogleClientRequestInitializer getGoogleClientRequestInitializer() {
            return this.googleClientRequestInitializer;
        }

        public Builder setGoogleClientRequestInitializer(GoogleClientRequestInitializer googleClientRequestInitializer) {
            this.googleClientRequestInitializer = googleClientRequestInitializer;
            return this;
        }

        public final HttpRequestInitializer getHttpRequestInitializer() {
            return this.httpRequestInitializer;
        }

        public Builder setHttpRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
            this.httpRequestInitializer = httpRequestInitializer;
            return this;
        }

        public final String getApplicationName() {
            return this.applicationName;
        }

        public Builder setApplicationName(String applicationName) {
            this.applicationName = applicationName;
            return this;
        }

        public final boolean getSuppressPatternChecks() {
            return this.suppressPatternChecks;
        }

        public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
            this.suppressPatternChecks = suppressPatternChecks;
            return this;
        }

        public final boolean getSuppressRequiredParameterChecks() {
            return this.suppressRequiredParameterChecks;
        }

        public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
            this.suppressRequiredParameterChecks = suppressRequiredParameterChecks;
            return this;
        }

        public Builder setSuppressAllChecks(boolean suppressAllChecks) {
            return setSuppressPatternChecks(true).setSuppressRequiredParameterChecks(true);
        }
    }

    protected AbstractGoogleClient(Builder builder) {
        this.googleClientRequestInitializer = builder.googleClientRequestInitializer;
        this.rootUrl = normalizeRootUrl(builder.rootUrl);
        this.servicePath = normalizeServicePath(builder.servicePath);
        if (Strings.isNullOrEmpty(builder.applicationName)) {
            LOGGER.warning("Application name is not set. Call Builder#setApplicationName.");
        }
        this.applicationName = builder.applicationName;
        this.requestFactory = builder.httpRequestInitializer == null ? builder.transport.createRequestFactory() : builder.transport.createRequestFactory(builder.httpRequestInitializer);
        this.objectParser = builder.objectParser;
        this.suppressPatternChecks = builder.suppressPatternChecks;
        this.suppressRequiredParameterChecks = builder.suppressRequiredParameterChecks;
    }

    public final String getRootUrl() {
        return this.rootUrl;
    }

    public final String getServicePath() {
        return this.servicePath;
    }

    public final String getBaseUrl() {
        return this.rootUrl + this.servicePath;
    }

    public final String getApplicationName() {
        return this.applicationName;
    }

    public final HttpRequestFactory getRequestFactory() {
        return this.requestFactory;
    }

    public final GoogleClientRequestInitializer getGoogleClientRequestInitializer() {
        return this.googleClientRequestInitializer;
    }

    public ObjectParser getObjectParser() {
        return this.objectParser;
    }

    protected void initialize(AbstractGoogleClientRequest<?> httpClientRequest) throws IOException {
        if (getGoogleClientRequestInitializer() != null) {
            getGoogleClientRequestInitializer().initialize(httpClientRequest);
        }
    }

    public final BatchRequest batch() {
        return batch(null);
    }

    public final BatchRequest batch(HttpRequestInitializer httpRequestInitializer) {
        BatchRequest batch = new BatchRequest(getRequestFactory().getTransport(), httpRequestInitializer);
        batch.setBatchUrl(new GenericUrl(getRootUrl() + "batch"));
        return batch;
    }

    public final boolean getSuppressPatternChecks() {
        return this.suppressPatternChecks;
    }

    public final boolean getSuppressRequiredParameterChecks() {
        return this.suppressRequiredParameterChecks;
    }

    static String normalizeRootUrl(String rootUrl) {
        Preconditions.checkNotNull(rootUrl, "root URL cannot be null.");
        if (rootUrl.endsWith("/")) {
            return rootUrl;
        }
        return rootUrl + "/";
    }

    static String normalizeServicePath(String servicePath) {
        Preconditions.checkNotNull(servicePath, "service path cannot be null");
        if (servicePath.length() == 1) {
            Preconditions.checkArgument("/".equals(servicePath), "service path must equal \"/\" if it is of length 1.");
            return BuildConfig.FLAVOR;
        } else if (servicePath.length() <= 0) {
            return servicePath;
        } else {
            if (!servicePath.endsWith("/")) {
                servicePath = servicePath + "/";
            }
            if (servicePath.startsWith("/")) {
                return servicePath.substring(1);
            }
            return servicePath;
        }
    }
}
