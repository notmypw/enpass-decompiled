package com.google.api.client.http;

import com.github.clans.fab.BuildConfig;
import com.google.api.client.util.Charsets;
import com.google.api.client.util.IOUtils;
import com.google.api.client.util.LoggingInputStream;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

public final class HttpResponse {
    private InputStream content;
    private final String contentEncoding;
    private int contentLoggingLimit;
    private boolean contentRead;
    private final String contentType;
    private boolean loggingEnabled;
    private final HttpMediaType mediaType;
    private final HttpRequest request;
    LowLevelHttpResponse response;
    private final int statusCode;
    private final String statusMessage;

    HttpResponse(HttpRequest request, LowLevelHttpResponse response) throws IOException {
        boolean loggable;
        StringBuilder stringBuilder;
        HttpMediaType httpMediaType = null;
        this.request = request;
        this.contentLoggingLimit = request.getContentLoggingLimit();
        this.loggingEnabled = request.isLoggingEnabled();
        this.response = response;
        this.contentEncoding = response.getContentEncoding();
        int code = response.getStatusCode();
        if (code < 0) {
            code = 0;
        }
        this.statusCode = code;
        String message = response.getReasonPhrase();
        this.statusMessage = message;
        Logger logger = HttpTransport.LOGGER;
        if (this.loggingEnabled && logger.isLoggable(Level.CONFIG)) {
            loggable = true;
        } else {
            loggable = false;
        }
        StringBuilder logbuf = null;
        if (loggable) {
            logbuf = new StringBuilder();
            logbuf.append("-------------- RESPONSE --------------").append(StringUtils.LINE_SEPARATOR);
            String statusLine = response.getStatusLine();
            if (statusLine != null) {
                logbuf.append(statusLine);
            } else {
                logbuf.append(this.statusCode);
                if (message != null) {
                    logbuf.append(' ').append(message);
                }
            }
            logbuf.append(StringUtils.LINE_SEPARATOR);
        }
        HttpHeaders responseHeaders = request.getResponseHeaders();
        if (loggable) {
            stringBuilder = logbuf;
        } else {
            stringBuilder = null;
        }
        responseHeaders.fromHttpResponse(response, stringBuilder);
        String contentType = response.getContentType();
        if (contentType == null) {
            contentType = request.getResponseHeaders().getContentType();
        }
        this.contentType = contentType;
        if (contentType != null) {
            httpMediaType = new HttpMediaType(contentType);
        }
        this.mediaType = httpMediaType;
        if (loggable) {
            logger.config(logbuf.toString());
        }
    }

    public int getContentLoggingLimit() {
        return this.contentLoggingLimit;
    }

    public HttpResponse setContentLoggingLimit(int contentLoggingLimit) {
        Preconditions.checkArgument(contentLoggingLimit >= 0, "The content logging limit must be non-negative.");
        this.contentLoggingLimit = contentLoggingLimit;
        return this;
    }

    public boolean isLoggingEnabled() {
        return this.loggingEnabled;
    }

    public HttpResponse setLoggingEnabled(boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
        return this;
    }

    public String getContentEncoding() {
        return this.contentEncoding;
    }

    public String getContentType() {
        return this.contentType;
    }

    public HttpMediaType getMediaType() {
        return this.mediaType;
    }

    public HttpHeaders getHeaders() {
        return this.request.getResponseHeaders();
    }

    public boolean isSuccessStatusCode() {
        return HttpStatusCodes.isSuccess(this.statusCode);
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public HttpTransport getTransport() {
        return this.request.getTransport();
    }

    public HttpRequest getRequest() {
        return this.request;
    }

    public InputStream getContent() throws IOException {
        Throwable th;
        if (!this.contentRead) {
            InputStream lowLevelResponseContent = this.response.getContent();
            if (lowLevelResponseContent != null) {
                try {
                    InputStream lowLevelResponseContent2;
                    String contentEncoding = this.contentEncoding;
                    if (contentEncoding == null || !contentEncoding.contains("gzip")) {
                        lowLevelResponseContent2 = lowLevelResponseContent;
                    } else {
                        lowLevelResponseContent2 = new GZIPInputStream(lowLevelResponseContent);
                    }
                    try {
                        Logger logger = HttpTransport.LOGGER;
                        if (this.loggingEnabled && logger.isLoggable(Level.CONFIG)) {
                            lowLevelResponseContent = new LoggingInputStream(lowLevelResponseContent2, logger, Level.CONFIG, this.contentLoggingLimit);
                        } else {
                            lowLevelResponseContent = lowLevelResponseContent2;
                        }
                        this.content = lowLevelResponseContent;
                        if (!true) {
                            lowLevelResponseContent.close();
                        }
                    } catch (EOFException e) {
                        lowLevelResponseContent = lowLevelResponseContent2;
                        if (null == null) {
                            lowLevelResponseContent.close();
                        }
                        this.contentRead = true;
                        return this.content;
                    } catch (Throwable th2) {
                        th = th2;
                        lowLevelResponseContent = lowLevelResponseContent2;
                        if (null == null) {
                            lowLevelResponseContent.close();
                        }
                        throw th;
                    }
                } catch (EOFException e2) {
                    if (null == null) {
                        lowLevelResponseContent.close();
                    }
                    this.contentRead = true;
                    return this.content;
                } catch (Throwable th3) {
                    th = th3;
                    if (null == null) {
                        lowLevelResponseContent.close();
                    }
                    throw th;
                }
            }
            this.contentRead = true;
        }
        return this.content;
    }

    public void download(OutputStream outputStream) throws IOException {
        IOUtils.copy(getContent(), outputStream);
    }

    public void ignore() throws IOException {
        InputStream content = getContent();
        if (content != null) {
            content.close();
        }
    }

    public void disconnect() throws IOException {
        ignore();
        this.response.disconnect();
    }

    public <T> T parseAs(Class<T> dataClass) throws IOException {
        if (hasMessageBody()) {
            return this.request.getParser().parseAndClose(getContent(), getContentCharset(), (Class) dataClass);
        }
        return null;
    }

    private boolean hasMessageBody() throws IOException {
        int statusCode = getStatusCode();
        if (!getRequest().getRequestMethod().equals(HttpMethods.HEAD) && statusCode / 100 != 1 && statusCode != HttpStatusCodes.STATUS_CODE_NO_CONTENT && statusCode != HttpStatusCodes.STATUS_CODE_NOT_MODIFIED) {
            return true;
        }
        ignore();
        return false;
    }

    public Object parseAs(Type dataType) throws IOException {
        if (hasMessageBody()) {
            return this.request.getParser().parseAndClose(getContent(), getContentCharset(), dataType);
        }
        return null;
    }

    public String parseAsString() throws IOException {
        InputStream content = getContent();
        if (content == null) {
            return BuildConfig.FLAVOR;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(content, out);
        return out.toString(getContentCharset().name());
    }

    public Charset getContentCharset() {
        return (this.mediaType == null || this.mediaType.getCharsetParameter() == null) ? Charsets.ISO_8859_1 : this.mediaType.getCharsetParameter();
    }
}
