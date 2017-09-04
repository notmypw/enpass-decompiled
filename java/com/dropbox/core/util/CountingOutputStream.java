package com.dropbox.core.util;

import java.io.IOException;
import java.io.OutputStream;

public class CountingOutputStream extends OutputStream {
    private long bytesWritten = 0;
    private final OutputStream out;

    public CountingOutputStream(OutputStream out) {
        this.out = out;
    }

    public long getBytesWritten() {
        return this.bytesWritten;
    }

    public void write(int b) throws IOException {
        this.bytesWritten++;
        this.out.write(b);
    }

    public void write(byte[] b) throws IOException {
        this.bytesWritten += (long) b.length;
        this.out.write(b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        this.bytesWritten += (long) len;
        this.out.write(b, off, len);
    }

    public void flush() throws IOException {
        this.out.flush();
    }

    public void close() throws IOException {
        throw new UnsupportedOperationException("You aren't allowed to call close() on this object.");
    }
}
