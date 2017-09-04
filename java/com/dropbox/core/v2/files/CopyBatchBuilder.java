package com.dropbox.core.v2.files;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.RelocationBatchArg.Builder;

public class CopyBatchBuilder {
    private final Builder _builder;
    private final DbxUserFilesRequests _client;

    CopyBatchBuilder(DbxUserFilesRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public CopyBatchBuilder withAllowSharedFolder(Boolean allowSharedFolder) {
        this._builder.withAllowSharedFolder(allowSharedFolder);
        return this;
    }

    public CopyBatchBuilder withAutorename(Boolean autorename) {
        this._builder.withAutorename(autorename);
        return this;
    }

    public RelocationBatchLaunch start() throws DbxApiException, DbxException {
        return this._client.copyBatch(this._builder.build());
    }
}
