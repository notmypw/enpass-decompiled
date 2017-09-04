package com.dropbox.core.v2.team;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.team.DateRange.Builder;
import java.util.Date;

public class ReportsGetStorageBuilder {
    private final Builder _builder;
    private final DbxTeamTeamRequests _client;

    ReportsGetStorageBuilder(DbxTeamTeamRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public ReportsGetStorageBuilder withStartDate(Date startDate) {
        this._builder.withStartDate(startDate);
        return this;
    }

    public ReportsGetStorageBuilder withEndDate(Date endDate) {
        this._builder.withEndDate(endDate);
        return this;
    }

    public GetStorageReport start() throws DateRangeErrorException, DbxException {
        return this._client.reportsGetStorage(this._builder.build());
    }
}
