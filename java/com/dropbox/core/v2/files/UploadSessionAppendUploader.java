package com.dropbox.core.v2.files;

import com.dropbox.core.DbxUploader;
import com.dropbox.core.DbxWrappedException;
import com.dropbox.core.http.HttpRequestor.Uploader;
import com.dropbox.core.stone.StoneSerializers;

public class UploadSessionAppendUploader extends DbxUploader<Void, UploadSessionLookupError, UploadSessionLookupErrorException> {
    public UploadSessionAppendUploader(Uploader httpUploader) {
        super(httpUploader, StoneSerializers.void_(), Serializer.INSTANCE);
    }

    protected UploadSessionLookupErrorException newException(DbxWrappedException error) {
        return new UploadSessionLookupErrorException("2/files/upload_session/append", error.getRequestId(), error.getUserMessage(), (UploadSessionLookupError) error.getErrorValue());
    }
}
