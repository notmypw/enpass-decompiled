package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.sharing.GetSharedLinkMetadataArg.Builder;

public class GetSharedLinkMetadataBuilder {
    private final Builder _builder;
    private final DbxUserSharingRequests _client;

    GetSharedLinkMetadataBuilder(DbxUserSharingRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public GetSharedLinkMetadataBuilder withPath(String path) {
        this._builder.withPath(path);
        return this;
    }

    public GetSharedLinkMetadataBuilder withLinkPassword(String linkPassword) {
        this._builder.withLinkPassword(linkPassword);
        return this;
    }

    public SharedLinkMetadata start() throws SharedLinkErrorException, DbxException {
        return this._client.getSharedLinkMetadata(this._builder.build());
    }
}
