package com.dropbox.core.http;

import com.dropbox.core.http.HttpRequestor.Header;
import com.dropbox.core.http.HttpRequestor.Response;
import com.dropbox.core.util.IOUtil;
import com.google.api.client.http.HttpMethods;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

public class StandardHttpRequestor extends HttpRequestor {
    public static final StandardHttpRequestor INSTANCE = new StandardHttpRequestor(Config.DEFAULT_INSTANCE);
    private static final Logger LOGGER = Logger.getLogger(StandardHttpRequestor.class.getCanonicalName());
    private static volatile boolean certPinningWarningLogged = false;
    private final Config config;

    public static final class Config {
        public static final Config DEFAULT_INSTANCE = builder().build();
        private final long connectTimeoutMillis;
        private final Proxy proxy;
        private final long readTimeoutMillis;

        public static final class Builder {
            private long connectTimeoutMillis;
            private Proxy proxy;
            private long readTimeoutMillis;

            private Builder() {
                this(Proxy.NO_PROXY, HttpRequestor.DEFAULT_CONNECT_TIMEOUT_MILLIS, HttpRequestor.DEFAULT_READ_TIMEOUT_MILLIS);
            }

            private Builder(Proxy proxy, long connectTimeoutMillis, long readTimeoutMillis) {
                this.proxy = proxy;
                this.connectTimeoutMillis = connectTimeoutMillis;
                this.readTimeoutMillis = readTimeoutMillis;
            }

            public Builder withProxy(Proxy proxy) {
                if (proxy == null) {
                    throw new NullPointerException("proxy");
                }
                this.proxy = proxy;
                return this;
            }

            public Builder withNoConnectTimeout() {
                return withConnectTimeout(0, TimeUnit.MILLISECONDS);
            }

            public Builder withConnectTimeout(long timeout, TimeUnit unit) {
                this.connectTimeoutMillis = checkTimeoutMillis(timeout, unit);
                return this;
            }

            public Builder withNoReadTimeout() {
                return withReadTimeout(0, TimeUnit.MILLISECONDS);
            }

            public Builder withReadTimeout(long timeout, TimeUnit unit) {
                this.readTimeoutMillis = checkTimeoutMillis(timeout, unit);
                return this;
            }

            public Config build() {
                return new Config(this.proxy, this.connectTimeoutMillis, this.readTimeoutMillis);
            }

            private static long checkTimeoutMillis(long timeout, TimeUnit unit) {
                if (unit == null) {
                    throw new NullPointerException("unit");
                } else if (timeout < 0) {
                    throw new IllegalArgumentException("timeout must be non-negative");
                } else {
                    long millis = unit.toMillis(timeout);
                    if (2147483647L >= millis) {
                        return millis;
                    }
                    throw new IllegalArgumentException("timeout too large, must be less than: 2147483647");
                }
            }
        }

        private Config(Proxy proxy, long connectTimeoutMillis, long readTimeoutMillis) {
            this.proxy = proxy;
            this.connectTimeoutMillis = connectTimeoutMillis;
            this.readTimeoutMillis = readTimeoutMillis;
        }

        public Proxy getProxy() {
            return this.proxy;
        }

        public long getConnectTimeoutMillis() {
            return this.connectTimeoutMillis;
        }

        public long getReadTimeoutMillis() {
            return this.readTimeoutMillis;
        }

        public Builder copy() {
            return new Builder(this.proxy, this.connectTimeoutMillis, this.readTimeoutMillis);
        }

        public static Builder builder() {
            return new Builder();
        }
    }

    private class Uploader extends com.dropbox.core.http.HttpRequestor.Uploader {
        private HttpURLConnection conn;
        private final OutputStream out;

        public Uploader(HttpURLConnection conn) throws IOException {
            this.conn = conn;
            this.out = StandardHttpRequestor.getOutputStream(conn);
            conn.connect();
        }

        public OutputStream getBody() {
            return this.out;
        }

        public void abort() {
            if (this.conn == null) {
                throw new IllegalStateException("Can't abort().  Uploader already closed.");
            }
            this.conn.disconnect();
            this.conn = null;
        }

        public void close() {
            if (this.conn != null) {
                if (this.conn.getDoOutput()) {
                    try {
                        IOUtil.closeQuietly(this.conn.getOutputStream());
                    } catch (IOException e) {
                    }
                }
                this.conn = null;
            }
        }

        public Response finish() throws IOException {
            if (this.conn == null) {
                throw new IllegalStateException("Can't finish().  Uploader already closed.");
            }
            try {
                Response access$100 = StandardHttpRequestor.this.toResponse(this.conn);
                return access$100;
            } finally {
                this.conn = null;
            }
        }
    }

    public StandardHttpRequestor(Config config) {
        this.config = config;
    }

    private Response toResponse(HttpURLConnection conn) throws IOException {
        InputStream bodyStream;
        int responseCode = conn.getResponseCode();
        if (responseCode >= 400 || responseCode == -1) {
            bodyStream = conn.getErrorStream();
        } else {
            bodyStream = conn.getInputStream();
        }
        interceptResponse(conn);
        return new Response(responseCode, bodyStream, conn.getHeaderFields());
    }

    public Response doGet(String url, Iterable<Header> headers) throws IOException {
        HttpURLConnection conn = prepRequest(url, headers);
        conn.setRequestMethod(HttpMethods.GET);
        conn.connect();
        return toResponse(conn);
    }

    public Uploader startPost(String url, Iterable<Header> headers) throws IOException {
        HttpURLConnection conn = prepRequest(url, headers);
        conn.setRequestMethod(HttpMethods.POST);
        return new Uploader(conn);
    }

    public Uploader startPut(String url, Iterable<Header> headers) throws IOException {
        HttpURLConnection conn = prepRequest(url, headers);
        conn.setRequestMethod(HttpMethods.PUT);
        return new Uploader(conn);
    }

    @Deprecated
    protected void configureConnection(HttpsURLConnection conn) throws IOException {
    }

    protected void configure(HttpURLConnection conn) throws IOException {
    }

    protected void interceptResponse(HttpURLConnection conn) throws IOException {
    }

    private static OutputStream getOutputStream(HttpURLConnection conn) throws IOException {
        conn.setDoOutput(true);
        return conn.getOutputStream();
    }

    private HttpURLConnection prepRequest(String url, Iterable<Header> headers) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection(this.config.getProxy());
        conn.setConnectTimeout((int) this.config.getConnectTimeoutMillis());
        conn.setReadTimeout((int) this.config.getReadTimeoutMillis());
        conn.setUseCaches(false);
        conn.setAllowUserInteraction(false);
        if (conn instanceof HttpsURLConnection) {
            SSLConfig.apply((HttpsURLConnection) conn);
            configureConnection((HttpsURLConnection) conn);
        } else {
            logCertificatePinningWarning();
        }
        configure(conn);
        for (Header header : headers) {
            conn.addRequestProperty(header.getKey(), header.getValue());
        }
        return conn;
    }

    private static void logCertificatePinningWarning() {
        if (!certPinningWarningLogged) {
            certPinningWarningLogged = true;
            LOGGER.warning("Certificate pinning disabled for HTTPS connections. This is likely because your JRE does not return javax.net.ssl.HttpsURLConnection objects for https network connections. Be aware your app may be prone to man-in-the-middle attacks without proper SSL certificate validation. If you are using Google App Engine, please configure DbxRequestConfig to use GoogleAppEngineRequestor.");
        }
    }
}
