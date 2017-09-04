package com.dropbox.core.v2.files;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxDownloadStyleBuilder;
import com.dropbox.core.v2.files.ThumbnailArg.Builder;

public class GetThumbnailBuilder extends DbxDownloadStyleBuilder<FileMetadata> {
    private final Builder _builder;
    private final DbxUserFilesRequests _client;

    GetThumbnailBuilder(DbxUserFilesRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public GetThumbnailBuilder withFormat(ThumbnailFormat format) {
        this._builder.withFormat(format);
        return this;
    }

    public GetThumbnailBuilder withSize(ThumbnailSize size) {
        this._builder.withSize(size);
        return this;
    }

    public DbxDownloader<FileMetadata> start() throws ThumbnailErrorException, DbxException {
        return this._client.getThumbnail(this._builder.build(), getHeaders());
    }
}
