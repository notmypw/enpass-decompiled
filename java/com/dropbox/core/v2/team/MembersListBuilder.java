package com.dropbox.core.v2.team;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.team.MembersListArg.Builder;

public class MembersListBuilder {
    private final Builder _builder;
    private final DbxTeamTeamRequests _client;

    MembersListBuilder(DbxTeamTeamRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public MembersListBuilder withLimit(Long limit) {
        this._builder.withLimit(limit);
        return this;
    }

    public MembersListBuilder withIncludeRemoved(Boolean includeRemoved) {
        this._builder.withIncludeRemoved(includeRemoved);
        return this;
    }

    public MembersListResult start() throws MembersListErrorException, DbxException {
        return this._client.membersList(this._builder.build());
    }
}
