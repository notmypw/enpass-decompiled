package com.box.androidsdk.content.utils;

import com.box.androidsdk.content.listeners.ProgressListener;
import java.io.IOException;
import java.io.InputStream;

public class ProgressInputStream extends InputStream {
    private final ProgressListener listener;
    private int progress;
    private final InputStream stream;
    private long total;
    private long totalRead;

    public ProgressInputStream(InputStream stream, ProgressListener listener, long total) {
        this.stream = stream;
        this.listener = listener;
        this.total = total;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void close() throws IOException {
        this.stream.close();
    }

    public int read() throws IOException {
        int read = this.stream.read();
        this.totalRead++;
        this.listener.onProgressChanged(this.totalRead, this.total);
        return read;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int read = this.stream.read(b, off, len);
        this.totalRead += (long) read;
        this.listener.onProgressChanged(this.totalRead, this.total);
        return read;
    }
}
