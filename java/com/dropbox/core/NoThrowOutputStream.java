package com.dropbox.core;

import java.io.IOException;
import java.io.OutputStream;

public final class NoThrowOutputStream extends OutputStream {
    private long bytesWritten = 0;
    private final OutputStream underlying;

    public static final class HiddenException extends RuntimeException {
        public static final long serialVersionUID = 0;
        public final NoThrowOutputStream owner;

        public HiddenException(NoThrowOutputStream owner, IOException underlying) {
            super(underlying);
            this.owner = owner;
        }

        public IOException getCause() {
            return (IOException) super.getCause();
        }
    }

    public NoThrowOutputStream(OutputStream underlying) {
        this.underlying = underlying;
    }

    public void close() {
        throw new UnsupportedOperationException("don't call close()");
    }

    public void flush() {
        try {
            this.underlying.flush();
        } catch (IOException ex) {
            throw new HiddenException(this, ex);
        }
    }

    public void write(byte[] b, int off, int len) {
        try {
            this.bytesWritten += (long) len;
            this.underlying.write(b, off, len);
        } catch (IOException ex) {
            throw new HiddenException(this, ex);
        }
    }

    public void write(byte[] b) {
        try {
            this.bytesWritten += (long) b.length;
            this.underlying.write(b);
        } catch (IOException ex) {
            throw new HiddenException(this, ex);
        }
    }

    public void write(int b) {
        try {
            this.bytesWritten++;
            this.underlying.write(b);
        } catch (IOException ex) {
            throw new HiddenException(this, ex);
        }
    }

    public long getBytesWritten() {
        return this.bytesWritten;
    }
}
