package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.sharing.AddFolderMemberArg.Builder;

public class AddFolderMemberBuilder {
    private final Builder _builder;
    private final DbxUserSharingRequests _client;

    AddFolderMemberBuilder(DbxUserSharingRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public AddFolderMemberBuilder withQuiet(Boolean quiet) {
        this._builder.withQuiet(quiet);
        return this;
    }

    public AddFolderMemberBuilder withCustomMessage(String customMessage) {
        this._builder.withCustomMessage(customMessage);
        return this;
    }

    public void start() throws AddFolderMemberErrorException, DbxException {
        this._client.addFolderMember(this._builder.build());
    }
}
