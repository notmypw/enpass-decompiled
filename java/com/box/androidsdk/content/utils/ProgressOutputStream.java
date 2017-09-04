package com.box.androidsdk.content.utils;

import com.box.androidsdk.content.listeners.ProgressListener;
import java.io.IOException;
import java.io.OutputStream;

public class ProgressOutputStream extends OutputStream {
    private final ProgressListener listener;
    private int progress;
    private final OutputStream stream;
    private long total;
    private long totalWritten;

    public ProgressOutputStream(OutputStream stream, ProgressListener listener, long total) {
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

    public void write(byte[] b) throws IOException {
        this.stream.write(b);
        this.totalWritten += (long) b.length;
        this.listener.onProgressChanged(this.totalWritten, this.total);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        this.stream.write(b, off, len);
        if (len < b.length) {
            this.totalWritten += (long) len;
        } else {
            this.totalWritten += (long) b.length;
        }
        this.listener.onProgressChanged(this.totalWritten, this.total);
    }

    public void write(int b) throws IOException {
        this.stream.write(b);
        this.totalWritten++;
        this.listener.onProgressChanged(this.totalWritten, this.total);
    }
}
