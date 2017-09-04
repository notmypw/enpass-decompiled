package com.dropbox.core.v2.paper;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxDownloadStyleBuilder;

public class DocsDownloadBuilder extends DbxDownloadStyleBuilder<PaperDocExportResult> {
    private final DbxUserPaperRequests _client;
    private final String docId;
    private final ExportFormat exportFormat;

    DocsDownloadBuilder(DbxUserPaperRequests _client, String docId, ExportFormat exportFormat) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        this.docId = docId;
        this.exportFormat = exportFormat;
    }

    public DbxDownloader<PaperDocExportResult> start() throws DocLookupErrorException, DbxException {
        return this._client.docsDownload(new PaperDocExport(this.docId, this.exportFormat), getHeaders());
    }
}
