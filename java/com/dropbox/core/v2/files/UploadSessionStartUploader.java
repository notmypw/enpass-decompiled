package com.dropbox.core.v2.files;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxUploader;
import com.dropbox.core.DbxWrappedException;
import com.dropbox.core.http.HttpRequestor.Uploader;
import com.dropbox.core.stone.StoneSerializers;

public class UploadSessionStartUploader extends DbxUploader<UploadSessionStartResult, Void, DbxApiException> {
    public UploadSessionStartUploader(Uploader httpUploader) {
        super(httpUploader, Serializer.INSTANCE, StoneSerializers.void_());
    }

    protected DbxApiException newException(DbxWrappedException error) {
        return new DbxApiException(error.getRequestId(), error.getUserMessage(), "Unexpected error response for \"upload_session/start\":" + error.getErrorValue());
    }
}
