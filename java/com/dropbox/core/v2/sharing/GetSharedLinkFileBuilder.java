package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxDownloadStyleBuilder;
import com.dropbox.core.v2.sharing.GetSharedLinkMetadataArg.Builder;

public class GetSharedLinkFileBuilder extends DbxDownloadStyleBuilder<SharedLinkMetadata> {
    private final Builder _builder;
    private final DbxUserSharingRequests _client;

    GetSharedLinkFileBuilder(DbxUserSharingRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public GetSharedLinkFileBuilder withPath(String path) {
        this._builder.withPath(path);
        return this;
    }

    public GetSharedLinkFileBuilder withLinkPassword(String linkPassword) {
        this._builder.withLinkPassword(linkPassword);
        return this;
    }

    public DbxDownloader<SharedLinkMetadata> start() throws GetSharedLinkFileErrorException, DbxException {
        return this._client.getSharedLinkFile(this._builder.build(), getHeaders());
    }
}
