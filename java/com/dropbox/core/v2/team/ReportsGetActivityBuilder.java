package com.dropbox.core.v2.team;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.team.DateRange.Builder;
import java.util.Date;

public class ReportsGetActivityBuilder {
    private final Builder _builder;
    private final DbxTeamTeamRequests _client;

    ReportsGetActivityBuilder(DbxTeamTeamRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public ReportsGetActivityBuilder withStartDate(Date startDate) {
        this._builder.withStartDate(startDate);
        return this;
    }

    public ReportsGetActivityBuilder withEndDate(Date endDate) {
        this._builder.withEndDate(endDate);
        return this;
    }

    public GetActivityReport start() throws DateRangeErrorException, DbxException {
        return this._client.reportsGetActivity(this._builder.build());
    }
}
