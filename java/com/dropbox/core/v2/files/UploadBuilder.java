package com.dropbox.core.v2.files;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxUploadStyleBuilder;
import com.dropbox.core.v2.files.CommitInfo.Builder;
import java.util.Date;

public class UploadBuilder extends DbxUploadStyleBuilder<FileMetadata, UploadError, UploadErrorException> {
    private final Builder _builder;
    private final DbxUserFilesRequests _client;

    UploadBuilder(DbxUserFilesRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public UploadBuilder withMode(WriteMode mode) {
        this._builder.withMode(mode);
        return this;
    }

    public UploadBuilder withAutorename(Boolean autorename) {
        this._builder.withAutorename(autorename);
        return this;
    }

    public UploadBuilder withClientModified(Date clientModified) {
        this._builder.withClientModified(clientModified);
        return this;
    }

    public UploadBuilder withMute(Boolean mute) {
        this._builder.withMute(mute);
        return this;
    }

    public UploadUploader start() throws UploadErrorException, DbxException {
        return this._client.upload(this._builder.build());
    }
}
