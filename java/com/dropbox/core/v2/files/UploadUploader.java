package com.dropbox.core.v2.files;

import com.dropbox.core.DbxUploader;
import com.dropbox.core.DbxWrappedException;
import com.dropbox.core.http.HttpRequestor.Uploader;

public class UploadUploader extends DbxUploader<FileMetadata, UploadError, UploadErrorException> {
    public UploadUploader(Uploader httpUploader) {
        super(httpUploader, Serializer.INSTANCE, Serializer.INSTANCE);
    }

    protected UploadErrorException newException(DbxWrappedException error) {
        return new UploadErrorException("2/files/upload", error.getRequestId(), error.getUserMessage(), (UploadError) error.getErrorValue());
    }
}
