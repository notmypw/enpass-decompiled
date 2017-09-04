package com.dropbox.core.v2.team;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.team.GroupUpdateArgs.Builder;
import com.dropbox.core.v2.teamcommon.GroupManagementType;

public class GroupsUpdateBuilder {
    private final Builder _builder;
    private final DbxTeamTeamRequests _client;

    GroupsUpdateBuilder(DbxTeamTeamRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public GroupsUpdateBuilder withReturnMembers(Boolean returnMembers) {
        this._builder.withReturnMembers(returnMembers);
        return this;
    }

    public GroupsUpdateBuilder withNewGroupName(String newGroupName) {
        this._builder.withNewGroupName(newGroupName);
        return this;
    }

    public GroupsUpdateBuilder withNewGroupExternalId(String newGroupExternalId) {
        this._builder.withNewGroupExternalId(newGroupExternalId);
        return this;
    }

    public GroupsUpdateBuilder withNewGroupManagementType(GroupManagementType newGroupManagementType) {
        this._builder.withNewGroupManagementType(newGroupManagementType);
        return this;
    }

    public GroupFullInfo start() throws GroupUpdateErrorException, DbxException {
        return this._client.groupsUpdate(this._builder.build());
    }
}
