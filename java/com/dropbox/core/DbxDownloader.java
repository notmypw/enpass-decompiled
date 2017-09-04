package com.dropbox.core;

import com.dropbox.core.util.IOUtil;
import com.dropbox.core.util.IOUtil.WriteException;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DbxDownloader<R> implements Closeable {
    private final InputStream body;
    private boolean closed = false;
    private final R result;

    public DbxDownloader(R result, InputStream body) {
        this.result = result;
        this.body = body;
    }

    public R getResult() {
        return this.result;
    }

    public InputStream getInputStream() {
        assertOpen();
        return this.body;
    }

    public R download(OutputStream out) throws DbxException, IOException {
        try {
            IOUtil.copyStreamToStream(getInputStream(), out);
            close();
            return this.result;
        } catch (WriteException ex) {
            throw ex.getCause();
        } catch (IOException ex2) {
            throw new NetworkIOException(ex2);
        } catch (Throwable th) {
            close();
        }
    }

    public void close() {
        if (!this.closed) {
            IOUtil.closeQuietly(this.body);
            this.closed = true;
        }
    }

    private void assertOpen() {
        if (this.closed) {
            throw new IllegalStateException("This downloader is already closed.");
        }
    }
}
