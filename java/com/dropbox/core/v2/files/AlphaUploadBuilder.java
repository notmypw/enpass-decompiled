package com.dropbox.core.v2.files;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxUploadStyleBuilder;
import com.dropbox.core.v2.files.CommitInfoWithProperties.Builder;
import com.dropbox.core.v2.properties.PropertyGroup;
import java.util.Date;
import java.util.List;

public class AlphaUploadBuilder extends DbxUploadStyleBuilder<FileMetadata, UploadErrorWithProperties, UploadErrorWithPropertiesException> {
    private final Builder _builder;
    private final DbxUserFilesRequests _client;

    AlphaUploadBuilder(DbxUserFilesRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public AlphaUploadBuilder withMode(WriteMode mode) {
        this._builder.withMode(mode);
        return this;
    }

    public AlphaUploadBuilder withAutorename(Boolean autorename) {
        this._builder.withAutorename(autorename);
        return this;
    }

    public AlphaUploadBuilder withClientModified(Date clientModified) {
        this._builder.withClientModified(clientModified);
        return this;
    }

    public AlphaUploadBuilder withMute(Boolean mute) {
        this._builder.withMute(mute);
        return this;
    }

    public AlphaUploadBuilder withPropertyGroups(List<PropertyGroup> propertyGroups) {
        this._builder.withPropertyGroups(propertyGroups);
        return this;
    }

    public AlphaUploadUploader start() throws UploadErrorWithPropertiesException, DbxException {
        return this._client.alphaUpload(this._builder.build());
    }
}
