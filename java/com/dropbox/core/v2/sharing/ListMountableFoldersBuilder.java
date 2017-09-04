package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.sharing.ListFoldersArgs.Builder;
import java.util.List;

public class ListMountableFoldersBuilder {
    private final Builder _builder;
    private final DbxUserSharingRequests _client;

    ListMountableFoldersBuilder(DbxUserSharingRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public ListMountableFoldersBuilder withLimit(Long limit) {
        this._builder.withLimit(limit);
        return this;
    }

    public ListMountableFoldersBuilder withActions(List<FolderAction> actions) {
        this._builder.withActions(actions);
        return this;
    }

    public ListFoldersResult start() throws DbxApiException, DbxException {
        return this._client.listMountableFolders(this._builder.build());
    }
}
