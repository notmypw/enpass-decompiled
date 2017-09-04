package com.dropbox.core;

import com.dropbox.core.http.HttpRequestor.Response;
import com.dropbox.core.http.HttpRequestor.Uploader;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.util.IOUtil;
import com.dropbox.core.util.IOUtil.ReadException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.api.client.http.HttpStatusCodes;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class DbxUploader<R, E, X extends DbxApiException> implements Closeable {
    private boolean closed = false;
    private final StoneSerializer<E> errorSerializer;
    private boolean finished = false;
    private final Uploader httpUploader;
    private final StoneSerializer<R> responseSerializer;

    protected abstract X newException(DbxWrappedException dbxWrappedException);

    protected DbxUploader(Uploader httpUploader, StoneSerializer<R> responseSerializer, StoneSerializer<E> errorSerializer) {
        this.httpUploader = httpUploader;
        this.responseSerializer = responseSerializer;
        this.errorSerializer = errorSerializer;
    }

    public R uploadAndFinish(InputStream in) throws DbxApiException, DbxException, IOException {
        try {
            this.httpUploader.upload(in);
            R finish = finish();
            close();
            return finish;
        } catch (ReadException ex) {
            throw ex.getCause();
        } catch (IOException ex2) {
            throw new NetworkIOException(ex2);
        } catch (Throwable th) {
            close();
        }
    }

    public R uploadAndFinish(InputStream in, long limit) throws DbxApiException, DbxException, IOException {
        return uploadAndFinish(IOUtil.limit(in, limit));
    }

    public void close() {
        if (!this.closed) {
            this.httpUploader.close();
            this.closed = true;
        }
    }

    public void abort() {
        this.httpUploader.abort();
    }

    public OutputStream getOutputStream() {
        assertOpenAndUnfinished();
        return this.httpUploader.getBody();
    }

    public R finish() throws DbxApiException, DbxException {
        assertOpenAndUnfinished();
        Response response = null;
        try {
            response = this.httpUploader.finish();
            if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                R deserialize = this.responseSerializer.deserialize(response.getBody());
                if (response != null) {
                    IOUtil.closeQuietly(response.getBody());
                }
                this.finished = true;
                return deserialize;
            } else if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_CONFLICT) {
                throw newException(DbxWrappedException.fromResponse(this.errorSerializer, response));
            } else {
                throw DbxRequestUtil.unexpectedStatus(response);
            }
        } catch (JsonProcessingException ex) {
            throw new BadResponseException(DbxRequestUtil.getRequestId(response), "Bad JSON in response: " + ex, ex);
        } catch (IOException ex2) {
            try {
                throw new NetworkIOException(ex2);
            } catch (Throwable th) {
                if (response != null) {
                    IOUtil.closeQuietly(response.getBody());
                }
                this.finished = true;
            }
        }
    }

    private void assertOpenAndUnfinished() {
        if (this.closed) {
            throw new IllegalStateException("This uploader is already closed.");
        } else if (this.finished) {
            throw new IllegalStateException("This uploader is already finished and cannot be used to upload more data.");
        }
    }
}
