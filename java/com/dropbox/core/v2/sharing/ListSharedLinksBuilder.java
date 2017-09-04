package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.sharing.ListSharedLinksArg.Builder;

public class ListSharedLinksBuilder {
    private final Builder _builder;
    private final DbxUserSharingRequests _client;

    ListSharedLinksBuilder(DbxUserSharingRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public ListSharedLinksBuilder withPath(String path) {
        this._builder.withPath(path);
        return this;
    }

    public ListSharedLinksBuilder withCursor(String cursor) {
        this._builder.withCursor(cursor);
        return this;
    }

    public ListSharedLinksBuilder withDirectOnly(Boolean directOnly) {
        this._builder.withDirectOnly(directOnly);
        return this;
    }

    public ListSharedLinksResult start() throws ListSharedLinksErrorException, DbxException {
        return this._client.listSharedLinks(this._builder.build());
    }
}
