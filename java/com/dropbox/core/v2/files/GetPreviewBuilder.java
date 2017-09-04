package com.dropbox.core.v2.files;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxDownloadStyleBuilder;
import java.util.regex.Pattern;

public class GetPreviewBuilder extends DbxDownloadStyleBuilder<FileMetadata> {
    private final DbxUserFilesRequests _client;
    private final String path;
    private String rev;

    GetPreviewBuilder(DbxUserFilesRequests _client, String path) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        this.path = path;
        this.rev = null;
    }

    public GetPreviewBuilder withRev(String rev) {
        if (rev != null) {
            if (rev.length() < 9) {
                throw new IllegalArgumentException("String 'rev' is shorter than 9");
            } else if (!Pattern.matches("[0-9a-f]+", rev)) {
                throw new IllegalArgumentException("String 'rev' does not match pattern");
            }
        }
        this.rev = rev;
        return this;
    }

    public DbxDownloader<FileMetadata> start() throws PreviewErrorException, DbxException {
        return this._client.getPreview(new PreviewArg(this.path, this.rev), getHeaders());
    }
}
