package com.dropbox.core;

import com.dropbox.core.http.HttpRequestor;
import com.dropbox.core.http.StandardHttpRequestor;
import com.github.clans.fab.BuildConfig;
import java.util.Locale;

public class DbxRequestConfig {
    private final String clientIdentifier;
    private final HttpRequestor httpRequestor;
    private final int maxRetries;
    private final String userLocale;

    public static final class Builder {
        private final String clientIdentifier;
        private HttpRequestor httpRequestor;
        private int maxRetries;
        private String userLocale;

        private Builder(String clientIdentifier, String userLocale, HttpRequestor httpRequestor, int maxRetries) {
            this.clientIdentifier = clientIdentifier;
            this.userLocale = userLocale;
            this.httpRequestor = httpRequestor;
            this.maxRetries = maxRetries;
        }

        private Builder(String clientIdentifier) {
            this.clientIdentifier = clientIdentifier;
            this.userLocale = null;
            this.httpRequestor = StandardHttpRequestor.INSTANCE;
            this.maxRetries = 0;
        }

        public Builder withUserLocale(String userLocale) {
            this.userLocale = userLocale;
            return this;
        }

        public Builder withUserLocaleFromPreferences() {
            this.userLocale = null;
            return this;
        }

        public Builder withUserLocaleFrom(Locale userLocale) {
            this.userLocale = DbxRequestConfig.toLanguageTag(userLocale);
            return this;
        }

        public Builder withHttpRequestor(HttpRequestor httpRequestor) {
            if (httpRequestor == null) {
                throw new NullPointerException("httpRequestor");
            }
            this.httpRequestor = httpRequestor;
            return this;
        }

        public Builder withAutoRetryEnabled() {
            return withAutoRetryEnabled(3);
        }

        public Builder withAutoRetryDisabled() {
            this.maxRetries = 0;
            return this;
        }

        public Builder withAutoRetryEnabled(int maxRetries) {
            if (maxRetries <= 0) {
                throw new IllegalArgumentException("maxRetries must be positive");
            }
            this.maxRetries = maxRetries;
            return this;
        }

        public DbxRequestConfig build() {
            return new DbxRequestConfig(this.clientIdentifier, this.userLocale, this.httpRequestor, this.maxRetries);
        }
    }

    private DbxRequestConfig(String clientIdentifier, String userLocale, HttpRequestor httpRequestor, int maxRetries) {
        if (clientIdentifier == null) {
            throw new NullPointerException("clientIdentifier");
        } else if (httpRequestor == null) {
            throw new NullPointerException("httpRequestor");
        } else if (maxRetries < 0) {
            throw new IllegalArgumentException("maxRetries");
        } else {
            this.clientIdentifier = clientIdentifier;
            this.userLocale = toLanguageTag(userLocale);
            this.httpRequestor = httpRequestor;
            this.maxRetries = maxRetries;
        }
    }

    public DbxRequestConfig(String clientIdentifier) {
        this(clientIdentifier, null);
    }

    @Deprecated
    public DbxRequestConfig(String clientIdentifier, String userLocale) {
        this(clientIdentifier, userLocale, StandardHttpRequestor.INSTANCE);
    }

    @Deprecated
    public DbxRequestConfig(String clientIdentifier, String userLocale, HttpRequestor httpRequestor) {
        this(clientIdentifier, userLocale, httpRequestor, 0);
    }

    public String getClientIdentifier() {
        return this.clientIdentifier;
    }

    public String getUserLocale() {
        return this.userLocale;
    }

    public HttpRequestor getHttpRequestor() {
        return this.httpRequestor;
    }

    public boolean isAutoRetryEnabled() {
        return this.maxRetries > 0;
    }

    public int getMaxRetries() {
        return this.maxRetries;
    }

    public Builder copy() {
        return new Builder(this.clientIdentifier, this.userLocale, this.httpRequestor, this.maxRetries);
    }

    public static Builder newBuilder(String clientIdentifier) {
        if (clientIdentifier != null) {
            return new Builder(clientIdentifier);
        }
        throw new NullPointerException("clientIdentifier");
    }

    private static String toLanguageTag(Locale locale) {
        if (locale == null) {
            return null;
        }
        StringBuilder tag = new StringBuilder();
        tag.append(locale.getLanguage().toLowerCase());
        if (!locale.getCountry().isEmpty()) {
            tag.append("-");
            tag.append(locale.getCountry().toUpperCase());
        }
        return tag.toString();
    }

    private static String toLanguageTag(String locale) {
        if (locale == null) {
            return null;
        }
        if (!locale.contains("_") || locale.startsWith("_")) {
            return locale;
        }
        String[] parts = locale.split("_", 3);
        return toLanguageTag(new Locale(parts[0], parts[1], parts.length == 3 ? parts[2] : BuildConfig.FLAVOR));
    }
}
