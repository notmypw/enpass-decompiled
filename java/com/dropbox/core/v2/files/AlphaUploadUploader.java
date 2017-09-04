package com.dropbox.core.v2.files;

import com.dropbox.core.DbxUploader;
import com.dropbox.core.DbxWrappedException;
import com.dropbox.core.http.HttpRequestor.Uploader;

public class AlphaUploadUploader extends DbxUploader<FileMetadata, UploadErrorWithProperties, UploadErrorWithPropertiesException> {
    public AlphaUploadUploader(Uploader httpUploader) {
        super(httpUploader, Serializer.INSTANCE, Serializer.INSTANCE);
    }

    protected UploadErrorWithPropertiesException newException(DbxWrappedException error) {
        return new UploadErrorWithPropertiesException("2/files/alpha/upload", error.getRequestId(), error.getUserMessage(), (UploadErrorWithProperties) error.getErrorValue());
    }
}
