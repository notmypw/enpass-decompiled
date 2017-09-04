package com.dropbox.core.v2;

import com.dropbox.core.v2.auth.DbxUserAuthRequests;
import com.dropbox.core.v2.files.DbxUserFilesRequests;
import com.dropbox.core.v2.paper.DbxUserPaperRequests;
import com.dropbox.core.v2.sharing.DbxUserSharingRequests;
import com.dropbox.core.v2.users.DbxUserUsersRequests;

public class DbxClientV2Base {
    protected final DbxRawClientV2 _client;
    private final DbxUserAuthRequests auth;
    private final DbxUserFilesRequests files;
    private final DbxUserPaperRequests paper;
    private final DbxUserSharingRequests sharing;
    private final DbxUserUsersRequests users;

    protected DbxClientV2Base(DbxRawClientV2 _client) {
        this._client = _client;
        this.auth = new DbxUserAuthRequests(_client);
        this.files = new DbxUserFilesRequests(_client);
        this.paper = new DbxUserPaperRequests(_client);
        this.sharing = new DbxUserSharingRequests(_client);
        this.users = new DbxUserUsersRequests(_client);
    }

    public DbxUserAuthRequests auth() {
        return this.auth;
    }

    public DbxUserFilesRequests files() {
        return this.files;
    }

    public DbxUserPaperRequests paper() {
        return this.paper;
    }

    public DbxUserSharingRequests sharing() {
        return this.sharing;
    }

    public DbxUserUsersRequests users() {
        return this.users;
    }
}
