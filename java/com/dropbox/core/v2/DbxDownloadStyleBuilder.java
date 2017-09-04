package com.dropbox.core.v2;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.http.HttpRequestor.Header;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class DbxDownloadStyleBuilder<R> {
    private Long length = null;
    private Long start = null;

    public abstract DbxDownloader<R> start() throws DbxException;

    protected DbxDownloadStyleBuilder() {
    }

    protected List<Header> getHeaders() {
        if (this.start == null) {
            return Collections.emptyList();
        }
        List<Header> headers = new ArrayList();
        String rangeValue = String.format("bytes=%d-", new Object[]{Long.valueOf(this.start.longValue())});
        if (this.length != null) {
            rangeValue = rangeValue + Long.toString((this.start.longValue() + this.length.longValue()) - 1);
        }
        headers.add(new Header("Range", rangeValue));
        return headers;
    }

    public DbxDownloadStyleBuilder<R> range(long start, long length) {
        if (start < 0) {
            throw new IllegalArgumentException("start must be non-negative");
        } else if (length < 1) {
            throw new IllegalArgumentException("length must be positive");
        } else {
            this.start = Long.valueOf(start);
            this.length = Long.valueOf(length);
            return this;
        }
    }

    public DbxDownloadStyleBuilder<R> range(long start) {
        if (start < 0) {
            throw new IllegalArgumentException("start must be non-negative");
        }
        this.start = Long.valueOf(start);
        this.length = null;
        return this;
    }

    public R download(OutputStream out) throws DbxException, IOException {
        return start().download(out);
    }
}
