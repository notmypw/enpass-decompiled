package com.dropbox.core;

import com.dropbox.core.util.IOUtil;
import java.io.IOException;
import java.io.OutputStream;

public abstract class DbxStreamReader<E extends Throwable> {

    public static final class ByteArrayCopier extends DbxStreamReader<RuntimeException> {
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

        public void read(NoThrowInputStream in) {
            in.read(this.data, this.offset, this.length);
        }
    }

    public static final class OutputStreamCopier extends DbxStreamReader<IOException> {
        private final OutputStream dest;

        public OutputStreamCopier(OutputStream dest) {
            this.dest = dest;
        }

        public void read(NoThrowInputStream source) throws IOException {
            IOUtil.copyStreamToStream(source, this.dest);
        }
    }

    public abstract void read(NoThrowInputStream noThrowInputStream) throws Throwable;
}
