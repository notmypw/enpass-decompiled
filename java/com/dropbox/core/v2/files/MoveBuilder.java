package com.dropbox.core.v2.files;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.RelocationArg.Builder;

public class MoveBuilder {
    private final Builder _builder;
    private final DbxUserFilesRequests _client;

    MoveBuilder(DbxUserFilesRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public MoveBuilder withAllowSharedFolder(Boolean allowSharedFolder) {
        this._builder.withAllowSharedFolder(allowSharedFolder);
        return this;
    }

    public MoveBuilder withAutorename(Boolean autorename) {
        this._builder.withAutorename(autorename);
        return this;
    }

    public Metadata start() throws RelocationErrorException, DbxException {
        return this._client.move(this._builder.build());
    }
}
