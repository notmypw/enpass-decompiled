package com.dropbox.core;

import java.io.IOException;
import java.io.InputStream;

public final class NoThrowInputStream extends InputStream {
    private long bytesRead = 0;
    private final InputStream underlying;

    public static final class HiddenException extends RuntimeException {
        private static final long serialVersionUID = 0;

        public HiddenException(IOException underlying) {
            super(underlying);
        }

        public IOException getCause() {
            return (IOException) super.getCause();
        }
    }

    public NoThrowInputStream(InputStream underlying) {
        this.underlying = underlying;
    }

    public void close() {
        throw new UnsupportedOperationException("don't call close()");
    }

    public int read() {
        try {
            this.bytesRead++;
            return this.underlying.read();
        } catch (IOException ex) {
            throw new HiddenException(ex);
        }
    }

    public int read(byte[] b, int off, int len) {
        try {
            int bytesReadNow = this.underlying.read(b, off, len);
            this.bytesRead += (long) bytesReadNow;
            return bytesReadNow;
        } catch (IOException ex) {
            throw new HiddenException(ex);
        }
    }

    public int read(byte[] b) {
        try {
            int bytesReadNow = this.underlying.read(b);
            this.bytesRead += (long) bytesReadNow;
            return bytesReadNow;
        } catch (IOException ex) {
            throw new HiddenException(ex);
        }
    }

    public long getBytesRead() {
        return this.bytesRead;
    }
}
