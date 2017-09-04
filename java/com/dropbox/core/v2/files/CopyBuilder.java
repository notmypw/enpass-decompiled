package com.dropbox.core.v2.files;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.RelocationArg.Builder;

public class CopyBuilder {
    private final Builder _builder;
    private final DbxUserFilesRequests _client;

    CopyBuilder(DbxUserFilesRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public CopyBuilder withAllowSharedFolder(Boolean allowSharedFolder) {
        this._builder.withAllowSharedFolder(allowSharedFolder);
        return this;
    }

    public CopyBuilder withAutorename(Boolean autorename) {
        this._builder.withAutorename(autorename);
        return this;
    }

    public Metadata start() throws RelocationErrorException, DbxException {
        return this._client.copy(this._builder.build());
    }
}
