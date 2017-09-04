package com.dropbox.core;

@Deprecated
public class DbxWebAuthNoRedirect {
    private final DbxWebAuth auth;

    public DbxWebAuthNoRedirect(DbxRequestConfig requestConfig, DbxAppInfo appInfo) {
        this.auth = new DbxWebAuth(requestConfig, appInfo);
    }

    public String start() {
        return this.auth.authorize(DbxWebAuth.newRequestBuilder().withNoRedirect().build());
    }

    public DbxAuthFinish finish(String code) throws DbxException {
        return this.auth.finishFromCode(code);
    }
}
