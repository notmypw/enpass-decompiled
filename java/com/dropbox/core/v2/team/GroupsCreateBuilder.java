package com.dropbox.core.v2.team;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.team.GroupCreateArg.Builder;
import com.dropbox.core.v2.teamcommon.GroupManagementType;

public class GroupsCreateBuilder {
    private final Builder _builder;
    private final DbxTeamTeamRequests _client;

    GroupsCreateBuilder(DbxTeamTeamRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public GroupsCreateBuilder withGroupExternalId(String groupExternalId) {
        this._builder.withGroupExternalId(groupExternalId);
        return this;
    }

    public GroupsCreateBuilder withGroupManagementType(GroupManagementType groupManagementType) {
        this._builder.withGroupManagementType(groupManagementType);
        return this;
    }

    public GroupFullInfo start() throws GroupCreateErrorException, DbxException {
        return this._client.groupsCreate(this._builder.build());
    }
}
