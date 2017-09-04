package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.sharing.AddFileMemberArgs.Builder;
import java.util.List;

public class AddFileMemberBuilder {
    private final Builder _builder;
    private final DbxUserSharingRequests _client;

    AddFileMemberBuilder(DbxUserSharingRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public AddFileMemberBuilder withCustomMessage(String customMessage) {
        this._builder.withCustomMessage(customMessage);
        return this;
    }

    public AddFileMemberBuilder withQuiet(Boolean quiet) {
        this._builder.withQuiet(quiet);
        return this;
    }

    public AddFileMemberBuilder withAccessLevel(AccessLevel accessLevel) {
        this._builder.withAccessLevel(accessLevel);
        return this;
    }

    public AddFileMemberBuilder withAddMessageAsComment(Boolean addMessageAsComment) {
        this._builder.withAddMessageAsComment(addMessageAsComment);
        return this;
    }

    public List<FileMemberActionResult> start() throws AddFileMemberErrorException, DbxException {
        return this._client.addFileMember(this._builder.build());
    }
}
