package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.sharing.ListFilesArg.Builder;
import java.util.List;

public class ListReceivedFilesBuilder {
    private final Builder _builder;
    private final DbxUserSharingRequests _client;

    ListReceivedFilesBuilder(DbxUserSharingRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public ListReceivedFilesBuilder withLimit(Long limit) {
        this._builder.withLimit(limit);
        return this;
    }

    public ListReceivedFilesBuilder withActions(List<FileAction> actions) {
        this._builder.withActions(actions);
        return this;
    }

    public ListFilesResult start() throws SharingUserErrorException, DbxException {
        return this._client.listReceivedFiles(this._builder.build());
    }
}
