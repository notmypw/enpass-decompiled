package com.dropbox.core;

import com.dropbox.core.util.IOUtil;
import java.io.IOException;
import java.io.InputStream;

public abstract class DbxStreamWriter<E extends Throwable> {

    public static final class ByteArrayCopier extends DbxStreamWriter<RuntimeException> {
        private final byte[] data;
        private final int length;
        private final int offset;

        public ByteArrayCopier(byte[] data, int offset, int length) {
            if (data == null) {
                throw new IllegalArgumentException("'data' can't be null");
            } else if (offset < 0 || offset >= data.length) {
                throw new IllegalArgumentException("'offset' is out of bounds");
            } else if (offset + length < offset || offset + length > data.length) {
                throw new IllegalArgumentException("'offset+length' is out of bounds");
            } else {
                this.data = data;
                this.offset = offset;
                this.length = length;
            }
        }

        public ByteArrayCopier(byte[] data) {
            this(data, 0, data.length);
        }

        public void write(NoThrowOutputStream out) {
            out.write(this.data, this.offset, this.length);
        }
    }

    public static final class InputStreamCopier extends DbxStreamWriter<IOException> {
        private final InputStream source;

        public InputStreamCopier(InputStream source) {
            this.source = source;
        }

        public void write(NoThrowOutputStream out) throws IOException {
            IOUtil.copyStreamToStream(this.source, out);
        }
    }

    public abstract void write(NoThrowOutputStream noThrowOutputStream) throws Throwable;
}
