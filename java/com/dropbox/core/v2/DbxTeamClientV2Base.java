package com.dropbox.core.v2;

import com.dropbox.core.v2.team.DbxTeamTeamRequests;

public class DbxTeamClientV2Base {
    protected final DbxRawClientV2 _client;
    private final DbxTeamTeamRequests team;

    protected DbxTeamClientV2Base(DbxRawClientV2 _client) {
        this._client = _client;
        this.team = new DbxTeamTeamRequests(_client);
    }

    public DbxTeamTeamRequests team() {
        return this.team;
    }
}
