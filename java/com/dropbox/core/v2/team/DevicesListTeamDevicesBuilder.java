package com.dropbox.core.v2.team;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.team.ListTeamDevicesArg.Builder;

public class DevicesListTeamDevicesBuilder {
    private final Builder _builder;
    private final DbxTeamTeamRequests _client;

    DevicesListTeamDevicesBuilder(DbxTeamTeamRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public DevicesListTeamDevicesBuilder withCursor(String cursor) {
        this._builder.withCursor(cursor);
        return this;
    }

    public DevicesListTeamDevicesBuilder withIncludeWebSessions(Boolean includeWebSessions) {
        this._builder.withIncludeWebSessions(includeWebSessions);
        return this;
    }

    public DevicesListTeamDevicesBuilder withIncludeDesktopClients(Boolean includeDesktopClients) {
        this._builder.withIncludeDesktopClients(includeDesktopClients);
        return this;
    }

    public DevicesListTeamDevicesBuilder withIncludeMobileClients(Boolean includeMobileClients) {
        this._builder.withIncludeMobileClients(includeMobileClients);
        return this;
    }

    public ListTeamDevicesResult start() throws ListTeamDevicesErrorException, DbxException {
        return this._client.devicesListTeamDevices(this._builder.build());
    }
}
