package com.dropbox.core.http;

import com.dropbox.core.http.HttpRequestor.Header;
import com.dropbox.core.http.HttpRequestor.Uploader;
import com.dropbox.core.http.OkHttpUtil.PipedStream;
import com.google.api.client.http.HttpMethods;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class OkHttp3Requestor extends HttpRequestor {
    private final OkHttpClient client;

    public static final class AsyncCallback implements Callback {
        private IOException error;
        private Response response;

        private AsyncCallback() {
            this.error = null;
            this.response = null;
        }

        public synchronized Response getResponse() throws IOException {
            while (this.error == null && this.response == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new InterruptedIOException();
                }
            }
            if (this.error != null) {
                throw this.error;
            }
            return this.response;
        }

        public synchronized void onFailure(Call call, IOException ex) {
            this.error = ex;
            notifyAll();
        }

        public synchronized void onResponse(Call call, Response response) throws IOException {
            this.response = response;
            notifyAll();
        }
    }

    private class BufferedUploader extends Uploader {
        private RequestBody body = null;
        private Call call = null;
        private AsyncCallback callback = null;
        private boolean cancelled = false;
        private boolean closed = false;
        private final String method;
        private final Builder request;

        public BufferedUploader(String method, Builder request) {
            this.method = method;
            this.request = request;
        }

        private void assertNoBody() {
            if (this.body != null) {
                throw new IllegalStateException("Request body already set.");
            }
        }

        public OutputStream getBody() {
            if (this.body instanceof PipedRequestBody) {
                return ((PipedRequestBody) this.body).getOutputStream();
            }
            PipedRequestBody pipedBody = new PipedRequestBody();
            setBody(pipedBody);
            this.callback = new AsyncCallback();
            this.call = OkHttp3Requestor.this.client.newCall(this.request.build());
            this.call.enqueue(this.callback);
            return pipedBody.getOutputStream();
        }

        private void setBody(RequestBody body) {
            assertNoBody();
            this.body = body;
            this.request.method(this.method, body);
            OkHttp3Requestor.this.configureRequest(this.request);
        }

        public void upload(File file) {
            setBody(RequestBody.create(null, file));
        }

        public void upload(byte[] body) {
            setBody(RequestBody.create(null, body));
        }

        public void close() {
            if (this.body != null && (this.body instanceof Closeable)) {
                try {
                    ((Closeable) this.body).close();
                } catch (IOException e) {
                }
            }
            this.closed = true;
        }

        public void abort() {
            if (this.call != null) {
                this.call.cancel();
            }
            this.cancelled = true;
            close();
        }

        public HttpRequestor.Response finish() throws IOException {
            if (this.cancelled) {
                throw new IllegalStateException("Already aborted");
            }
            Response response;
            if (this.body == null) {
                upload(new byte[0]);
            }
            if (this.callback != null) {
                try {
                    getBody().close();
                } catch (IOException e) {
                }
                response = this.callback.getResponse();
            } else {
                this.call = OkHttp3Requestor.this.client.newCall(this.request.build());
                response = this.call.execute();
            }
            response = OkHttp3Requestor.this.interceptResponse(response);
            return new HttpRequestor.Response(response.code(), response.body().byteStream(), OkHttp3Requestor.fromOkHttpHeaders(response.headers()));
        }
    }

    private static class PipedRequestBody extends RequestBody implements Closeable {
        private final PipedStream stream = new PipedStream();

        public OutputStream getOutputStream() {
            return this.stream.getOutputStream();
        }

        public void close() {
            this.stream.close();
        }

        public MediaType contentType() {
            return null;
        }

        public long contentLength() {
            return -1;
        }

        public void writeTo(BufferedSink sink) throws IOException {
            this.stream.writeTo(sink);
            close();
        }
    }

    public static OkHttpClient defaultOkHttpClient() {
        return defaultOkHttpClientBuilder().build();
    }

    public static OkHttpClient.Builder defaultOkHttpClientBuilder() {
        return new OkHttpClient.Builder().connectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS).readTimeout(DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS).writeTimeout(DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS).sslSocketFactory(SSLConfig.getSSLSocketFactory(), SSLConfig.getTrustManager());
    }

    public OkHttp3Requestor(OkHttpClient client) {
        if (client == null) {
            throw new NullPointerException("client");
        }
        OkHttpUtil.assertNotSameThreadExecutor(client.dispatcher().executorService());
        this.client = client;
    }

    public OkHttpClient getClient() {
        return this.client;
    }

    protected void configureRequest(Builder request) {
    }

    protected Response interceptResponse(Response response) {
        return response;
    }

    public HttpRequestor.Response doGet(String url, Iterable<Header> headers) throws IOException {
        Builder builder = new Builder().get().url(url);
        toOkHttpHeaders(headers, builder);
        configureRequest(builder);
        Response response = interceptResponse(this.client.newCall(builder.build()).execute());
        return new HttpRequestor.Response(response.code(), response.body().byteStream(), fromOkHttpHeaders(response.headers()));
    }

    public Uploader startPost(String url, Iterable<Header> headers) throws IOException {
        return startUpload(url, headers, HttpMethods.POST);
    }

    public Uploader startPut(String url, Iterable<Header> headers) throws IOException {
        return startUpload(url, headers, HttpMethods.PUT);
    }

    private BufferedUploader startUpload(String url, Iterable<Header> headers, String method) {
        Builder builder = new Builder().url(url);
        toOkHttpHeaders(headers, builder);
        return new BufferedUploader(method, builder);
    }

    private static void toOkHttpHeaders(Iterable<Header> headers, Builder builder) {
        for (Header header : headers) {
            builder.addHeader(header.getKey(), header.getValue());
        }
    }

    private static Map<String, List<String>> fromOkHttpHeaders(Headers headers) {
        Map<String, List<String>> responseHeaders = new HashMap(headers.size());
        for (String name : headers.names()) {
            responseHeaders.put(name, headers.values(name));
        }
        return responseHeaders;
    }
}
