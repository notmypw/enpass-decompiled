package com.dropbox.core.v2.files;

import com.dropbox.core.DbxUploader;
import com.dropbox.core.DbxWrappedException;
import com.dropbox.core.http.HttpRequestor.Uploader;

public class UploadSessionFinishUploader extends DbxUploader<FileMetadata, UploadSessionFinishError, UploadSessionFinishErrorException> {
    public UploadSessionFinishUploader(Uploader httpUploader) {
        super(httpUploader, Serializer.INSTANCE, Serializer.INSTANCE);
    }

    protected UploadSessionFinishErrorException newException(DbxWrappedException error) {
        return new UploadSessionFinishErrorException("2/files/upload_session/finish", error.getRequestId(), error.getUserMessage(), (UploadSessionFinishError) error.getErrorValue());
    }
}
