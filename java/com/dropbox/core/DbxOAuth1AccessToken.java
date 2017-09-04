package com.dropbox.core;

public final class DbxOAuth1AccessToken {
    private final String key;
    private final String secret;

    public DbxOAuth1AccessToken(String key, String secret) {
        this.key = key;
        this.secret = secret;
    }

    public String getKey() {
        return this.key;
    }

    public String getSecret() {
        return this.secret;
    }
}
