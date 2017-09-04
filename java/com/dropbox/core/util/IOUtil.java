package com.dropbox.core.util;

import com.github.clans.fab.BuildConfig;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.CharacterCodingException;

public class IOUtil {
    public static final OutputStream BlackHoleOutputStream = new OutputStream() {
        public void write(int b) {
        }

        public void write(byte[] data) {
        }

        public void write(byte[] data, int off, int len) {
        }
    };
    public static final int DEFAULT_COPY_BUFFER_SIZE = 16384;
    public static final InputStream EmptyInputStream = new InputStream() {
        public int read() {
            return -1;
        }

        public int read(byte[] data) {
            return -1;
        }

        public int read(byte[] data, int off, int len) {
            return -1;
        }
    };

    private static final class LimitInputStream extends FilterInputStream {
        private long left;

        public LimitInputStream(InputStream in, long limit) {
            super(in);
            if (in == null) {
                throw new NullPointerException("in");
            } else if (limit < 0) {
                throw new IllegalArgumentException("limit must be non-negative");
            } else {
                this.left = limit;
            }
        }

        public int available() throws IOException {
            return (int) Math.min((long) this.in.available(), this.left);
        }

        public synchronized void reset() throws IOException {
            throw new IOException("mark not supported");
        }

        public boolean markSupported() {
            return false;
        }

        public int read() throws IOException {
            if (this.left == 0) {
                return -1;
            }
            int read = this.in.read();
            if (read == -1) {
                return read;
            }
            this.left--;
            return read;
        }

        public int read(byte[] b, int off, int len) throws IOException {
            if (this.left == 0) {
                return -1;
            }
            int read = this.in.read(b, off, (int) Math.min((long) len, this.left));
            if (read == -1) {
                return read;
            }
            this.left -= (long) read;
            return read;
        }

        public long skip(long n) throws IOException {
            long skipped = this.in.skip(Math.min(n, this.left));
            this.left -= skipped;
            return skipped;
        }
    }

    public static abstract class WrappedException extends IOException {
        private static final long serialVersionUID = 0;

        public WrappedException(String message, IOException underlying) {
            super(message + ": " + underlying.getMessage(), underlying);
        }

        public WrappedException(IOException underlying) {
            super(underlying);
        }

        public IOException getCause() {
            return (IOException) super.getCause();
        }

        public String getMessage() {
            String m = super.getCause().getMessage();
            if (m == null) {
                return BuildConfig.FLAVOR;
            }
            return m;
        }
    }

    public static final class ReadException extends WrappedException {
        private static final long serialVersionUID = 0;

        public ReadException(String message, IOException underlying) {
            super(message, underlying);
        }

        public ReadException(IOException underlying) {
            super(underlying);
        }
    }

    public static final class WriteException extends WrappedException {
        private static final long serialVersionUID = 0;

        public WriteException(String message, IOException underlying) {
            super(message, underlying);
        }

        public WriteException(IOException underlying) {
            super(underlying);
        }
    }

    public static Reader utf8Reader(InputStream in) {
        return new InputStreamReader(in, StringUtil.UTF8.newDecoder());
    }

    public static Writer utf8Writer(OutputStream out) {
        return new OutputStreamWriter(out, StringUtil.UTF8.newEncoder());
    }

    public static String toUtf8String(InputStream in) throws ReadException, CharacterCodingException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            copyStreamToStream(in, out);
            return StringUtil.utf8ToString(out.toByteArray());
        } catch (WriteException ex) {
            throw new RuntimeException("impossible", ex);
        }
    }

    public static void copyStreamToStream(InputStream in, OutputStream out) throws ReadException, WriteException {
        copyStreamToStream(in, out, (int) DEFAULT_COPY_BUFFER_SIZE);
    }

    public static void copyStreamToStream(InputStream in, OutputStream out, byte[] copyBuffer) throws ReadException, WriteException {
        while (true) {
            try {
                int count = in.read(copyBuffer);
                if (count != -1) {
                    try {
                        out.write(copyBuffer, 0, count);
                    } catch (IOException ex) {
                        throw new WriteException(ex);
                    }
                }
                return;
            } catch (IOException ex2) {
                throw new ReadException(ex2);
            }
        }
    }

    public static void copyStreamToStream(InputStream in, OutputStream out, int copyBufferSize) throws ReadException, WriteException {
        copyStreamToStream(in, out, new byte[copyBufferSize]);
    }

    public static byte[] slurp(InputStream in, int byteLimit) throws IOException {
        return slurp(in, byteLimit, new byte[DEFAULT_COPY_BUFFER_SIZE]);
    }

    public static byte[] slurp(InputStream in, int byteLimit, byte[] slurpBuffer) throws IOException {
        if (byteLimit < 0) {
            throw new RuntimeException("'byteLimit' must be non-negative: " + byteLimit);
        }
        OutputStream baos = new ByteArrayOutputStream();
        copyStreamToStream(in, baos, slurpBuffer);
        return baos.toByteArray();
    }

    public void copyFileToStream(File fin, OutputStream out) throws ReadException, WriteException {
        copyFileToStream(fin, out, DEFAULT_COPY_BUFFER_SIZE);
    }

    public void copyFileToStream(File fin, OutputStream out, int copyBufferSize) throws ReadException, WriteException {
        try {
            InputStream in = new FileInputStream(fin);
            try {
                copyStreamToStream(in, out, copyBufferSize);
            } finally {
                closeInput(in);
            }
        } catch (IOException ex) {
            throw new ReadException(ex);
        }
    }

    public void copyStreamToFile(InputStream in, File fout) throws ReadException, WriteException {
        copyStreamToFile(in, fout, DEFAULT_COPY_BUFFER_SIZE);
    }

    public void copyStreamToFile(InputStream in, File fout, int copyBufferSize) throws ReadException, WriteException {
        try {
            OutputStream out = new FileOutputStream(fout);
            try {
                copyStreamToStream(in, out, copyBufferSize);
            } finally {
                try {
                    out.close();
                } catch (IOException ex) {
                    throw new WriteException(ex);
                }
            }
        } catch (IOException ex2) {
            throw new WriteException(ex2);
        }
    }

    public static void closeInput(InputStream in) {
        try {
            in.close();
        } catch (IOException e) {
        }
    }

    public static void closeInput(Reader in) {
        try {
            in.close();
        } catch (IOException e) {
        }
    }

    public static void closeQuietly(Closeable obj) {
        if (obj != null) {
            try {
                obj.close();
            } catch (IOException e) {
            }
        }
    }

    public static InputStream limit(InputStream in, long limit) {
        return new LimitInputStream(in, limit);
    }
}
