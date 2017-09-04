package com.dropbox.core.v2.paper;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.paper.AddPaperDocUser.Builder;
import java.util.List;

public class DocsUsersAddBuilder {
    private final Builder _builder;
    private final DbxUserPaperRequests _client;

    DocsUsersAddBuilder(DbxUserPaperRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public DocsUsersAddBuilder withCustomMessage(String customMessage) {
        this._builder.withCustomMessage(customMessage);
        return this;
    }

    public DocsUsersAddBuilder withQuiet(Boolean quiet) {
        this._builder.withQuiet(quiet);
        return this;
    }

    public List<AddPaperDocUserMemberResult> start() throws DocLookupErrorException, DbxException {
        return this._client.docsUsersAdd(this._builder.build());
    }
}
