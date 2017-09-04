package com.dropbox.core.v2.files;

import com.dropbox.core.DbxUploader;
import com.dropbox.core.DbxWrappedException;
import com.dropbox.core.http.HttpRequestor.Uploader;
import com.dropbox.core.stone.StoneSerializers;

public class UploadSessionAppendV2Uploader extends DbxUploader<Void, UploadSessionLookupError, UploadSessionLookupErrorException> {
    public UploadSessionAppendV2Uploader(Uploader httpUploader) {
        super(httpUploader, StoneSerializers.void_(), Serializer.INSTANCE);
    }

    protected UploadSessionLookupErrorException newException(DbxWrappedException error) {
        return new UploadSessionLookupErrorException("2/files/upload_session/append_v2", error.getRequestId(), error.getUserMessage(), (UploadSessionLookupError) error.getErrorValue());
    }
}
