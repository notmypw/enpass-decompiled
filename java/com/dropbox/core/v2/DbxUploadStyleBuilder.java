package com.dropbox.core.v2;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxUploader;
import java.io.IOException;
import java.io.InputStream;

public abstract class DbxUploadStyleBuilder<R, E, X extends DbxApiException> {
    public abstract DbxUploader<R, E, X> start() throws DbxException;

    public R uploadAndFinish(InputStream in) throws DbxApiException, DbxException, IOException {
        return start().uploadAndFinish(in);
    }

    public R uploadAndFinish(InputStream in, long limit) throws DbxApiException, DbxException, IOException {
        return start().uploadAndFinish(in, limit);
    }
}
