package com.dropbox.core.v2.files;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.SearchArg.Builder;

public class SearchBuilder {
    private final Builder _builder;
    private final DbxUserFilesRequests _client;

    SearchBuilder(DbxUserFilesRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public SearchBuilder withStart(Long start) {
        this._builder.withStart(start);
        return this;
    }

    public SearchBuilder withMaxResults(Long maxResults) {
        this._builder.withMaxResults(maxResults);
        return this;
    }

    public SearchBuilder withMode(SearchMode mode) {
        this._builder.withMode(mode);
        return this;
    }

    public SearchResult start() throws SearchErrorException, DbxException {
        return this._client.search(this._builder.build());
    }
}
