package com.dropbox.core.v2.team;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.properties.ModifyPropertyTemplateErrorException;
import com.dropbox.core.v2.properties.PropertyFieldTemplate;
import com.dropbox.core.v2.team.UpdatePropertyTemplateArg.Builder;
import java.util.List;

public class PropertiesTemplateUpdateBuilder {
    private final Builder _builder;
    private final DbxTeamTeamRequests _client;

    PropertiesTemplateUpdateBuilder(DbxTeamTeamRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public PropertiesTemplateUpdateBuilder withName(String name) {
        this._builder.withName(name);
        return this;
    }

    public PropertiesTemplateUpdateBuilder withDescription(String description) {
        this._builder.withDescription(description);
        return this;
    }

    public PropertiesTemplateUpdateBuilder withAddFields(List<PropertyFieldTemplate> addFields) {
        this._builder.withAddFields(addFields);
        return this;
    }

    public UpdatePropertyTemplateResult start() throws ModifyPropertyTemplateErrorException, DbxException {
        return this._client.propertiesTemplateUpdate(this._builder.build());
    }
}
