package com.dropbox.core.v2.team;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.team.ListMemberDevicesArg.Builder;

public class DevicesListMemberDevicesBuilder {
    private final Builder _builder;
    private final DbxTeamTeamRequests _client;

    DevicesListMemberDevicesBuilder(DbxTeamTeamRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public DevicesListMemberDevicesBuilder withIncludeWebSessions(Boolean includeWebSessions) {
        this._builder.withIncludeWebSessions(includeWebSessions);
        return this;
    }

    public DevicesListMemberDevicesBuilder withIncludeDesktopClients(Boolean includeDesktopClients) {
        this._builder.withIncludeDesktopClients(includeDesktopClients);
        return this;
    }

    public DevicesListMemberDevicesBuilder withIncludeMobileClients(Boolean includeMobileClients) {
        this._builder.withIncludeMobileClients(includeMobileClients);
        return this;
    }

    public ListMemberDevicesResult start() throws ListMemberDevicesErrorException, DbxException {
        return this._client.devicesListMemberDevices(this._builder.build());
    }
}
