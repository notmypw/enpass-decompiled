package com.dropbox.core.http;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import okio.BufferedSink;
import okio.Okio;

final class OkHttpUtil {

    public static final class PipedStream implements Closeable {
        private static final int BUFFER_SIZE = 5242880;
        private final PipedInputStream in = new PipedInputStream(BUFFER_SIZE);
        private final PipedOutputStream out;

        public PipedStream() {
            try {
                this.out = new PipedOutputStream(this.in);
            } catch (IOException e) {
                throw new IllegalStateException("Unable to create piped stream for async upload request.");
            }
        }

        public OutputStream getOutputStream() {
            return this.out;
        }

        public void close() {
            try {
                this.out.close();
            } catch (IOException e) {
            }
            try {
                this.in.close();
            } catch (IOException e2) {
            }
        }

        public void writeTo(BufferedSink sink) throws IOException {
            sink.writeAll(Okio.source(this.in));
        }
    }

    OkHttpUtil() {
    }

    public static void assertNotSameThreadExecutor(ExecutorService executor) {
        Thread current = Thread.currentThread();
        try {
            if (current.equals((Thread) executor.submit(new Callable<Thread>() {
                public Thread call() {
                    return Thread.currentThread();
                }
            }).get(2, TimeUnit.MINUTES))) {
                throw new IllegalArgumentException("OkHttp dispatcher uses same-thread executor. This is not supported by the SDK and may result in dead-locks. Please configure your Dispatcher to use an ExecutorService that runs tasks on separate threads.");
            }
        } catch (InterruptedException ex) {
            current.interrupt();
            throw new IllegalArgumentException("Unable to verify OkHttp dispatcher executor.", ex);
        } catch (Exception ex2) {
            throw new IllegalArgumentException("Unable to verify OkHttp dispatcher executor.", ex2);
        }
    }
}
