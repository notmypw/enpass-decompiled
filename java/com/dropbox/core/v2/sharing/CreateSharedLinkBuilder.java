package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.sharing.CreateSharedLinkArg.Builder;

public class CreateSharedLinkBuilder {
    private final Builder _builder;
    private final DbxUserSharingRequests _client;

    CreateSharedLinkBuilder(DbxUserSharingRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public CreateSharedLinkBuilder withShortUrl(Boolean shortUrl) {
        this._builder.withShortUrl(shortUrl);
        return this;
    }

    public CreateSharedLinkBuilder withPendingUpload(PendingUploadMode pendingUpload) {
        this._builder.withPendingUpload(pendingUpload);
        return this;
    }

    public PathLinkMetadata start() throws CreateSharedLinkErrorException, DbxException {
        return this._client.createSharedLink(this._builder.build());
    }
}
