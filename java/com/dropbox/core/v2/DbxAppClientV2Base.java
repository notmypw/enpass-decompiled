package com.dropbox.core.v2;

import com.dropbox.core.v2.auth.DbxAppAuthRequests;

public class DbxAppClientV2Base {
    protected final DbxRawClientV2 _client;
    private final DbxAppAuthRequests auth;

    protected DbxAppClientV2Base(DbxRawClientV2 _client) {
        this._client = _client;
        this.auth = new DbxAppAuthRequests(_client);
    }

    public DbxAppAuthRequests auth() {
        return this.auth;
    }
}
