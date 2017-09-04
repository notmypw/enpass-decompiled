package com.dropbox.core.v2.files;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.AlphaGetMetadataArg.Builder;
import java.util.List;

public class AlphaGetMetadataBuilder {
    private final Builder _builder;
    private final DbxUserFilesRequests _client;

    AlphaGetMetadataBuilder(DbxUserFilesRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public AlphaGetMetadataBuilder withIncludeMediaInfo(Boolean includeMediaInfo) {
        this._builder.withIncludeMediaInfo(includeMediaInfo);
        return this;
    }

    public AlphaGetMetadataBuilder withIncludeDeleted(Boolean includeDeleted) {
        this._builder.withIncludeDeleted(includeDeleted);
        return this;
    }

    public AlphaGetMetadataBuilder withIncludeHasExplicitSharedMembers(Boolean includeHasExplicitSharedMembers) {
        this._builder.withIncludeHasExplicitSharedMembers(includeHasExplicitSharedMembers);
        return this;
    }

    public AlphaGetMetadataBuilder withIncludePropertyTemplates(List<String> includePropertyTemplates) {
        this._builder.withIncludePropertyTemplates(includePropertyTemplates);
        return this;
    }

    public Metadata start() throws AlphaGetMetadataErrorException, DbxException {
        return this._client.alphaGetMetadata(this._builder.build());
    }
}
