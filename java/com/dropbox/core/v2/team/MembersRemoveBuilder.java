package com.dropbox.core.v2.team;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.async.LaunchEmptyResult;
import com.dropbox.core.v2.team.MembersRemoveArg.Builder;

public class MembersRemoveBuilder {
    private final Builder _builder;
    private final DbxTeamTeamRequests _client;

    MembersRemoveBuilder(DbxTeamTeamRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public MembersRemoveBuilder withWipeData(Boolean wipeData) {
        this._builder.withWipeData(wipeData);
        return this;
    }

    public MembersRemoveBuilder withTransferDestId(UserSelectorArg transferDestId) {
        this._builder.withTransferDestId(transferDestId);
        return this;
    }

    public MembersRemoveBuilder withTransferAdminId(UserSelectorArg transferAdminId) {
        this._builder.withTransferAdminId(transferAdminId);
        return this;
    }

    public MembersRemoveBuilder withKeepAccount(Boolean keepAccount) {
        this._builder.withKeepAccount(keepAccount);
        return this;
    }

    public LaunchEmptyResult start() throws MembersRemoveErrorException, DbxException {
        return this._client.membersRemove(this._builder.build());
    }
}
