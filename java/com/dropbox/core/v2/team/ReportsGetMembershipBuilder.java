package com.dropbox.core.v2.team;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.team.DateRange.Builder;
import java.util.Date;

public class ReportsGetMembershipBuilder {
    private final Builder _builder;
    private final DbxTeamTeamRequests _client;

    ReportsGetMembershipBuilder(DbxTeamTeamRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public ReportsGetMembershipBuilder withStartDate(Date startDate) {
        this._builder.withStartDate(startDate);
        return this;
    }

    public ReportsGetMembershipBuilder withEndDate(Date endDate) {
        this._builder.withEndDate(endDate);
        return this;
    }

    public GetMembershipReport start() throws DateRangeErrorException, DbxException {
        return this._client.reportsGetMembership(this._builder.build());
    }
}
