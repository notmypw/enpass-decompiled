package com.dropbox.core.http;

import com.dropbox.core.util.IOUtil;
import com.dropbox.core.util.IOUtil.ReadException;
import com.dropbox.core.util.IOUtil.WriteException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public abstract class HttpRequestor {
    public static final long DEFAULT_CONNECT_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(20);
    public static final long DEFAULT_READ_TIMEOUT_MILLIS = TimeUnit.MINUTES.toMillis(2);

    public static abstract class Uploader {
        public abstract void abort();

        public abstract void close();

        public abstract Response finish() throws IOException;

        public abstract OutputStream getBody();

        public void upload(File file) throws IOException {
            try {
                upload(new FileInputStream(file));
            } catch (ReadException ex) {
                throw ex.getCause();
            } catch (WriteException ex2) {
                throw ex2.getCause();
            }
        }

        public void upload(InputStream in, long limit) throws IOException {
            upload(IOUtil.limit(in, limit));
        }

        public void upload(InputStream in) throws IOException {
            OutputStream out = getBody();
            try {
                IOUtil.copyStreamToStream(in, out);
            } finally {
                out.close();
            }
        }

        public void upload(byte[] body) throws IOException {
            OutputStream out = getBody();
            try {
                out.write(body);
            } finally {
                out.close();
            }
        }
    }

    public static final class Header {
        private final String key;
        private final String value;

        public Header(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return this.key;
        }

        public String getValue() {
            return this.value;
        }
    }

    public static final class Response {
        private final InputStream body;
        private final Map<String, List<String>> headers;
        private final int statusCode;

        public Response(int statusCode, InputStream body, Map<String, ? extends List<String>> headers) {
            this.statusCode = statusCode;
            this.body = body;
            this.headers = asUnmodifiableCaseInsensitiveMap(headers);
        }

        public int getStatusCode() {
            return this.statusCode;
        }

        public InputStream getBody() {
            return this.body;
        }

        public Map<String, List<String>> getHeaders() {
            return this.headers;
        }

        private static final Map<String, List<String>> asUnmodifiableCaseInsensitiveMap(Map<String, ? extends List<String>> original) {
            Map<String, List<String>> insensitive = new TreeMap(String.CASE_INSENSITIVE_ORDER);
            for (Entry<String, ? extends List<String>> entry : original.entrySet()) {
                if (!(entry.getKey() == null || ((String) entry.getKey()).trim().length() == 0)) {
                    insensitive.put(entry.getKey(), Collections.unmodifiableList((List) entry.getValue()));
                }
            }
            return Collections.unmodifiableMap(insensitive);
        }
    }

    public abstract Response doGet(String str, Iterable<Header> iterable) throws IOException;

    public abstract Uploader startPost(String str, Iterable<Header> iterable) throws IOException;

    public abstract Uploader startPut(String str, Iterable<Header> iterable) throws IOException;
}
