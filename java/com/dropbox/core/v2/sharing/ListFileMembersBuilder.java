package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.sharing.ListFileMembersArg.Builder;
import java.util.List;

public class ListFileMembersBuilder {
    private final Builder _builder;
    private final DbxUserSharingRequests _client;

    ListFileMembersBuilder(DbxUserSharingRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public ListFileMembersBuilder withActions(List<MemberAction> actions) {
        this._builder.withActions(actions);
        return this;
    }

    public ListFileMembersBuilder withIncludeInherited(Boolean includeInherited) {
        this._builder.withIncludeInherited(includeInherited);
        return this;
    }

    public ListFileMembersBuilder withLimit(Long limit) {
        this._builder.withLimit(limit);
        return this;
    }

    public SharedFileMembers start() throws ListFileMembersErrorException, DbxException {
        return this._client.listFileMembers(this._builder.build());
    }
}
