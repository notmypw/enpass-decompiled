package com.dropbox.core.http;

import com.dropbox.core.http.HttpRequestor.Header;
import com.dropbox.core.http.HttpRequestor.Response;
import com.dropbox.core.http.HttpRequestor.Uploader;
import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.FetchOptions.Builder;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleAppEngineRequestor extends HttpRequestor {
    private final FetchOptions options;
    private final URLFetchService service;

    private static class FetchServiceUploader extends Uploader {
        private final ByteArrayOutputStream body;
        private HTTPRequest request;
        private final URLFetchService service;

        public FetchServiceUploader(URLFetchService service, HTTPRequest request, ByteArrayOutputStream body) {
            this.service = service;
            this.request = request;
            this.body = body;
        }

        public OutputStream getBody() {
            return this.body;
        }

        public void close() {
            if (this.request != null) {
                try {
                    this.body.close();
                } catch (IOException e) {
                }
                this.request = null;
            }
        }

        public void abort() {
            if (this.request == null) {
                throw new IllegalStateException("Uploader already closed.");
            }
            close();
        }

        public Response finish() throws IOException {
            if (this.request == null) {
                throw new IllegalStateException("Uploader already closed.");
            }
            this.request.setPayload(this.body.toByteArray());
            Response requestorResponse = GoogleAppEngineRequestor.toRequestorResponse(this.service.fetch(this.request));
            close();
            return requestorResponse;
        }
    }

    public GoogleAppEngineRequestor() {
        this(newDefaultOptions());
    }

    public GoogleAppEngineRequestor(FetchOptions options) {
        this(options, URLFetchServiceFactory.getURLFetchService());
    }

    public GoogleAppEngineRequestor(FetchOptions options, URLFetchService service) {
        if (options == null) {
            throw new NullPointerException("options");
        } else if (service == null) {
            throw new NullPointerException("service");
        } else {
            this.options = options;
            this.service = service;
        }
    }

    public FetchOptions getOptions() {
        return this.options;
    }

    public URLFetchService getService() {
        return this.service;
    }

    public Response doGet(String url, Iterable<Header> headers) throws IOException {
        return toRequestorResponse(this.service.fetch(newRequest(url, HTTPMethod.GET, headers)));
    }

    public Uploader startPost(String url, Iterable<Header> headers) throws IOException {
        return new FetchServiceUploader(this.service, newRequest(url, HTTPMethod.POST, headers), new ByteArrayOutputStream());
    }

    public Uploader startPut(String url, Iterable<Header> headers) throws IOException {
        return new FetchServiceUploader(this.service, newRequest(url, HTTPMethod.POST, headers), new ByteArrayOutputStream());
    }

    private HTTPRequest newRequest(String url, HTTPMethod method, Iterable<Header> headers) throws IOException {
        HTTPRequest request = new HTTPRequest(new URL(url), method, this.options);
        for (Header header : headers) {
            request.addHeader(new HTTPHeader(header.getKey(), header.getValue()));
        }
        return request;
    }

    public static FetchOptions newDefaultOptions() {
        return Builder.withDefaults().validateCertificate().doNotFollowRedirects().disallowTruncate().setDeadline(Double.valueOf(((double) (DEFAULT_CONNECT_TIMEOUT_MILLIS + DEFAULT_READ_TIMEOUT_MILLIS)) / 1000.0d));
    }

    private static Response toRequestorResponse(HTTPResponse response) {
        Map<String, List<String>> headers = new HashMap();
        for (HTTPHeader header : response.getHeadersUncombined()) {
            List<String> existing = (List) headers.get(header.getName());
            if (existing == null) {
                existing = new ArrayList();
                headers.put(header.getName(), existing);
            }
            existing.add(header.getValue());
        }
        return new Response(response.getResponseCode(), new ByteArrayInputStream(response.getContent()), headers);
    }
}
