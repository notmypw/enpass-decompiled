package com.dropbox.core.v2.paper;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.paper.ListPaperDocsArgs.Builder;

public class DocsListBuilder {
    private final Builder _builder;
    private final DbxUserPaperRequests _client;

    DocsListBuilder(DbxUserPaperRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public DocsListBuilder withFilterBy(ListPaperDocsFilterBy filterBy) {
        this._builder.withFilterBy(filterBy);
        return this;
    }

    public DocsListBuilder withSortBy(ListPaperDocsSortBy sortBy) {
        this._builder.withSortBy(sortBy);
        return this;
    }

    public DocsListBuilder withSortOrder(ListPaperDocsSortOrder sortOrder) {
        this._builder.withSortOrder(sortOrder);
        return this;
    }

    public DocsListBuilder withLimit(Integer limit) {
        this._builder.withLimit(limit);
        return this;
    }

    public ListPaperDocsResponse start() throws DbxApiException, DbxException {
        return this._client.docsList(this._builder.build());
    }
}
