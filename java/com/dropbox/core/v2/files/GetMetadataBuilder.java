package com.dropbox.core.v2.files;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.GetMetadataArg.Builder;

public class GetMetadataBuilder {
    private final Builder _builder;
    private final DbxUserFilesRequests _client;

    GetMetadataBuilder(DbxUserFilesRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public GetMetadataBuilder withIncludeMediaInfo(Boolean includeMediaInfo) {
        this._builder.withIncludeMediaInfo(includeMediaInfo);
        return this;
    }

    public GetMetadataBuilder withIncludeDeleted(Boolean includeDeleted) {
        this._builder.withIncludeDeleted(includeDeleted);
        return this;
    }

    public GetMetadataBuilder withIncludeHasExplicitSharedMembers(Boolean includeHasExplicitSharedMembers) {
        this._builder.withIncludeHasExplicitSharedMembers(includeHasExplicitSharedMembers);
        return this;
    }

    public Metadata start() throws GetMetadataErrorException, DbxException {
        return this._client.getMetadata(this._builder.build());
    }
}
