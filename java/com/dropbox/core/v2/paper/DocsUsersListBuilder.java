package com.dropbox.core.v2.paper;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.paper.ListUsersOnPaperDocArgs.Builder;

public class DocsUsersListBuilder {
    private final Builder _builder;
    private final DbxUserPaperRequests _client;

    DocsUsersListBuilder(DbxUserPaperRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public DocsUsersListBuilder withLimit(Integer limit) {
        this._builder.withLimit(limit);
        return this;
    }

    public DocsUsersListBuilder withFilterBy(UserOnPaperDocFilter filterBy) {
        this._builder.withFilterBy(filterBy);
        return this;
    }

    public ListUsersOnPaperDocResponse start() throws DocLookupErrorException, DbxException {
        return this._client.docsUsersList(this._builder.build());
    }
}
