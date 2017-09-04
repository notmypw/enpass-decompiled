package com.dropbox.core.v2.files;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.RelocationBatchArg.Builder;

public class MoveBatchBuilder {
    private final Builder _builder;
    private final DbxUserFilesRequests _client;

    MoveBatchBuilder(DbxUserFilesRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public MoveBatchBuilder withAllowSharedFolder(Boolean allowSharedFolder) {
        this._builder.withAllowSharedFolder(allowSharedFolder);
        return this;
    }

    public MoveBatchBuilder withAutorename(Boolean autorename) {
        this._builder.withAutorename(autorename);
        return this;
    }

    public RelocationBatchLaunch start() throws DbxApiException, DbxException {
        return this._client.moveBatch(this._builder.build());
    }
}
