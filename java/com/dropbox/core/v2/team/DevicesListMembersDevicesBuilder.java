package com.dropbox.core.v2.team;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.team.ListMembersDevicesArg.Builder;

public class DevicesListMembersDevicesBuilder {
    private final Builder _builder;
    private final DbxTeamTeamRequests _client;

    DevicesListMembersDevicesBuilder(DbxTeamTeamRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public DevicesListMembersDevicesBuilder withCursor(String cursor) {
        this._builder.withCursor(cursor);
        return this;
    }

    public DevicesListMembersDevicesBuilder withIncludeWebSessions(Boolean includeWebSessions) {
        this._builder.withIncludeWebSessions(includeWebSessions);
        return this;
    }

    public DevicesListMembersDevicesBuilder withIncludeDesktopClients(Boolean includeDesktopClients) {
        this._builder.withIncludeDesktopClients(includeDesktopClients);
        return this;
    }

    public DevicesListMembersDevicesBuilder withIncludeMobileClients(Boolean includeMobileClients) {
        this._builder.withIncludeMobileClients(includeMobileClients);
        return this;
    }

    public ListMembersDevicesResult start() throws ListMembersDevicesErrorException, DbxException {
        return this._client.devicesListMembersDevices(this._builder.build());
    }
}
