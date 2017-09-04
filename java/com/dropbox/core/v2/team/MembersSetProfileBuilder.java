package com.dropbox.core.v2.team;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.team.MembersSetProfileArg.Builder;

public class MembersSetProfileBuilder {
    private final Builder _builder;
    private final DbxTeamTeamRequests _client;

    MembersSetProfileBuilder(DbxTeamTeamRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public MembersSetProfileBuilder withNewEmail(String newEmail) {
        this._builder.withNewEmail(newEmail);
        return this;
    }

    public MembersSetProfileBuilder withNewExternalId(String newExternalId) {
        this._builder.withNewExternalId(newExternalId);
        return this;
    }

    public MembersSetProfileBuilder withNewGivenName(String newGivenName) {
        this._builder.withNewGivenName(newGivenName);
        return this;
    }

    public MembersSetProfileBuilder withNewSurname(String newSurname) {
        this._builder.withNewSurname(newSurname);
        return this;
    }

    public MembersSetProfileBuilder withNewPersistentId(String newPersistentId) {
        this._builder.withNewPersistentId(newPersistentId);
        return this;
    }

    public TeamMemberInfo start() throws MembersSetProfileErrorException, DbxException {
        return this._client.membersSetProfile(this._builder.build());
    }
}
