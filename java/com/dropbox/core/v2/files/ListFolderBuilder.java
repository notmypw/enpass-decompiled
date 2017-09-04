package com.dropbox.core.v2.files;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.ListFolderArg.Builder;

public class ListFolderBuilder {
    private final Builder _builder;
    private final DbxUserFilesRequests _client;

    ListFolderBuilder(DbxUserFilesRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public ListFolderBuilder withRecursive(Boolean recursive) {
        this._builder.withRecursive(recursive);
        return this;
    }

    public ListFolderBuilder withIncludeMediaInfo(Boolean includeMediaInfo) {
        this._builder.withIncludeMediaInfo(includeMediaInfo);
        return this;
    }

    public ListFolderBuilder withIncludeDeleted(Boolean includeDeleted) {
        this._builder.withIncludeDeleted(includeDeleted);
        return this;
    }

    public ListFolderBuilder withIncludeHasExplicitSharedMembers(Boolean includeHasExplicitSharedMembers) {
        this._builder.withIncludeHasExplicitSharedMembers(includeHasExplicitSharedMembers);
        return this;
    }

    public ListFolderResult start() throws ListFolderErrorException, DbxException {
        return this._client.listFolder(this._builder.build());
    }
}
