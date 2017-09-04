package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.sharing.ListFolderMembersArgs.Builder;
import java.util.List;

public class ListFolderMembersBuilder {
    private final Builder _builder;
    private final DbxUserSharingRequests _client;

    ListFolderMembersBuilder(DbxUserSharingRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public ListFolderMembersBuilder withActions(List<MemberAction> actions) {
        this._builder.withActions((List) actions);
        return this;
    }

    public ListFolderMembersBuilder withLimit(Long limit) {
        this._builder.withLimit(limit);
        return this;
    }

    public SharedFolderMembers start() throws SharedFolderAccessErrorException, DbxException {
        return this._client.listFolderMembers(this._builder.build());
    }
}
