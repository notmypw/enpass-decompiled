package com.google.api.client.googleapis.batch;

import com.google.api.client.http.AbstractHttpContent;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

class HttpRequestContent extends AbstractHttpContent {
    static final String NEWLINE = "\r\n";
    private final HttpRequest request;

    HttpRequestContent(HttpRequest request) {
        super("application/http");
        this.request = request;
    }

    public void writeTo(OutputStream out) throws IOException {
        Writer writer = new OutputStreamWriter(out, getCharset());
        writer.write(this.request.getRequestMethod());
        writer.write(" ");
        writer.write(this.request.getUrl().build());
        writer.write(NEWLINE);
        HttpHeaders headers = new HttpHeaders();
        headers.fromHttpHeaders(this.request.getHeaders());
        headers.setAcceptEncoding(null).setUserAgent(null).setContentEncoding(null).setContentType(null).setContentLength(null);
        HttpContent content = this.request.getContent();
        if (content != null) {
            headers.setContentType(content.getType());
            long contentLength = content.getLength();
            if (contentLength != -1) {
                headers.setContentLength(Long.valueOf(contentLength));
            }
        }
        HttpHeaders.serializeHeadersForMultipartRequests(headers, null, null, writer);
        writer.write(NEWLINE);
        writer.flush();
        if (content != null) {
            content.writeTo(out);
        }
    }
}
