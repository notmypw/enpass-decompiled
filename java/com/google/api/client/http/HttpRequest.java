package com.google.api.client.http;

import com.dropbox.core.util.IOUtil;
import com.google.api.client.util.Beta;
import com.google.api.client.util.IOUtils;
import com.google.api.client.util.LoggingStreamingContent;
import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import com.google.api.client.util.StreamingContent;
import com.google.api.client.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class HttpRequest {
    public static final int DEFAULT_NUMBER_OF_RETRIES = 10;
    public static final String USER_AGENT_SUFFIX = "Google-HTTP-Java-Client/1.22.0 (gzip)";
    public static final String VERSION = "1.22.0";
    @Beta
    @Deprecated
    private BackOffPolicy backOffPolicy;
    private int connectTimeout = 20000;
    private HttpContent content;
    private int contentLoggingLimit = IOUtil.DEFAULT_COPY_BUFFER_SIZE;
    private boolean curlLoggingEnabled = true;
    private HttpEncoding encoding;
    private HttpExecuteInterceptor executeInterceptor;
    private boolean followRedirects = true;
    private HttpHeaders headers = new HttpHeaders();
    @Beta
    private HttpIOExceptionHandler ioExceptionHandler;
    private boolean loggingEnabled = true;
    private int numRetries = DEFAULT_NUMBER_OF_RETRIES;
    private ObjectParser objectParser;
    private int readTimeout = 20000;
    private String requestMethod;
    private HttpHeaders responseHeaders = new HttpHeaders();
    private HttpResponseInterceptor responseInterceptor;
    @Beta
    @Deprecated
    private boolean retryOnExecuteIOException = false;
    private Sleeper sleeper = Sleeper.DEFAULT;
    private boolean suppressUserAgentSuffix;
    private boolean throwExceptionOnExecuteError = true;
    private final HttpTransport transport;
    private HttpUnsuccessfulResponseHandler unsuccessfulResponseHandler;
    private GenericUrl url;

    HttpRequest(HttpTransport transport, String requestMethod) {
        this.transport = transport;
        setRequestMethod(requestMethod);
    }

    public HttpTransport getTransport() {
        return this.transport;
    }

    public String getRequestMethod() {
        return this.requestMethod;
    }

    public HttpRequest setRequestMethod(String requestMethod) {
        boolean z = requestMethod == null || HttpMediaType.matchesToken(requestMethod);
        Preconditions.checkArgument(z);
        this.requestMethod = requestMethod;
        return this;
    }

    public GenericUrl getUrl() {
        return this.url;
    }

    public HttpRequest setUrl(GenericUrl url) {
        this.url = (GenericUrl) Preconditions.checkNotNull(url);
        return this;
    }

    public HttpContent getContent() {
        return this.content;
    }

    public HttpRequest setContent(HttpContent content) {
        this.content = content;
        return this;
    }

    public HttpEncoding getEncoding() {
        return this.encoding;
    }

    public HttpRequest setEncoding(HttpEncoding encoding) {
        this.encoding = encoding;
        return this;
    }

    @Beta
    @Deprecated
    public BackOffPolicy getBackOffPolicy() {
        return this.backOffPolicy;
    }

    @Beta
    @Deprecated
    public HttpRequest setBackOffPolicy(BackOffPolicy backOffPolicy) {
        this.backOffPolicy = backOffPolicy;
        return this;
    }

    public int getContentLoggingLimit() {
        return this.contentLoggingLimit;
    }

    public HttpRequest setContentLoggingLimit(int contentLoggingLimit) {
        Preconditions.checkArgument(contentLoggingLimit >= 0, "The content logging limit must be non-negative.");
        this.contentLoggingLimit = contentLoggingLimit;
        return this;
    }

    public boolean isLoggingEnabled() {
        return this.loggingEnabled;
    }

    public HttpRequest setLoggingEnabled(boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
        return this;
    }

    public boolean isCurlLoggingEnabled() {
        return this.curlLoggingEnabled;
    }

    public HttpRequest setCurlLoggingEnabled(boolean curlLoggingEnabled) {
        this.curlLoggingEnabled = curlLoggingEnabled;
        return this;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public HttpRequest setConnectTimeout(int connectTimeout) {
        Preconditions.checkArgument(connectTimeout >= 0);
        this.connectTimeout = connectTimeout;
        return this;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public HttpRequest setReadTimeout(int readTimeout) {
        Preconditions.checkArgument(readTimeout >= 0);
        this.readTimeout = readTimeout;
        return this;
    }

    public HttpHeaders getHeaders() {
        return this.headers;
    }

    public HttpRequest setHeaders(HttpHeaders headers) {
        this.headers = (HttpHeaders) Preconditions.checkNotNull(headers);
        return this;
    }

    public HttpHeaders getResponseHeaders() {
        return this.responseHeaders;
    }

    public HttpRequest setResponseHeaders(HttpHeaders responseHeaders) {
        this.responseHeaders = (HttpHeaders) Preconditions.checkNotNull(responseHeaders);
        return this;
    }

    public HttpExecuteInterceptor getInterceptor() {
        return this.executeInterceptor;
    }

    public HttpRequest setInterceptor(HttpExecuteInterceptor interceptor) {
        this.executeInterceptor = interceptor;
        return this;
    }

    public HttpUnsuccessfulResponseHandler getUnsuccessfulResponseHandler() {
        return this.unsuccessfulResponseHandler;
    }

    public HttpRequest setUnsuccessfulResponseHandler(HttpUnsuccessfulResponseHandler unsuccessfulResponseHandler) {
        this.unsuccessfulResponseHandler = unsuccessfulResponseHandler;
        return this;
    }

    @Beta
    public HttpIOExceptionHandler getIOExceptionHandler() {
        return this.ioExceptionHandler;
    }

    @Beta
    public HttpRequest setIOExceptionHandler(HttpIOExceptionHandler ioExceptionHandler) {
        this.ioExceptionHandler = ioExceptionHandler;
        return this;
    }

    public HttpResponseInterceptor getResponseInterceptor() {
        return this.responseInterceptor;
    }

    public HttpRequest setResponseInterceptor(HttpResponseInterceptor responseInterceptor) {
        this.responseInterceptor = responseInterceptor;
        return this;
    }

    public int getNumberOfRetries() {
        return this.numRetries;
    }

    public HttpRequest setNumberOfRetries(int numRetries) {
        Preconditions.checkArgument(numRetries >= 0);
        this.numRetries = numRetries;
        return this;
    }

    public HttpRequest setParser(ObjectParser parser) {
        this.objectParser = parser;
        return this;
    }

    public final ObjectParser getParser() {
        return this.objectParser;
    }

    public boolean getFollowRedirects() {
        return this.followRedirects;
    }

    public HttpRequest setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
        return this;
    }

    public boolean getThrowExceptionOnExecuteError() {
        return this.throwExceptionOnExecuteError;
    }

    public HttpRequest setThrowExceptionOnExecuteError(boolean throwExceptionOnExecuteError) {
        this.throwExceptionOnExecuteError = throwExceptionOnExecuteError;
        return this;
    }

    @Beta
    @Deprecated
    public boolean getRetryOnExecuteIOException() {
        return this.retryOnExecuteIOException;
    }

    @Beta
    @Deprecated
    public HttpRequest setRetryOnExecuteIOException(boolean retryOnExecuteIOException) {
        this.retryOnExecuteIOException = retryOnExecuteIOException;
        return this;
    }

    public boolean getSuppressUserAgentSuffix() {
        return this.suppressUserAgentSuffix;
    }

    public HttpRequest setSuppressUserAgentSuffix(boolean suppressUserAgentSuffix) {
        this.suppressUserAgentSuffix = suppressUserAgentSuffix;
        return this;
    }

    public HttpResponse execute() throws IOException {
        boolean retryRequest;
        IOException e;
        boolean errorHandled;
        Preconditions.checkArgument(this.numRetries >= 0);
        int retriesRemaining = this.numRetries;
        if (this.backOffPolicy != null) {
            this.backOffPolicy.reset();
        }
        HttpResponse response = null;
        Preconditions.checkNotNull(this.requestMethod);
        Preconditions.checkNotNull(this.url);
        do {
            long backOffTime;
            if (response != null) {
                response.ignore();
            }
            response = null;
            IOException executeException = null;
            if (this.executeInterceptor != null) {
                this.executeInterceptor.intercept(this);
            }
            String urlString = this.url.build();
            LowLevelHttpRequest lowLevelHttpRequest = this.transport.buildRequest(this.requestMethod, urlString);
            Logger logger = HttpTransport.LOGGER;
            boolean loggable = this.loggingEnabled && logger.isLoggable(Level.CONFIG);
            StringBuilder logbuf = null;
            StringBuilder curlbuf = null;
            if (loggable) {
                logbuf = new StringBuilder();
                logbuf.append("-------------- REQUEST  --------------").append(StringUtils.LINE_SEPARATOR);
                logbuf.append(this.requestMethod).append(' ').append(urlString).append(StringUtils.LINE_SEPARATOR);
                if (this.curlLoggingEnabled) {
                    curlbuf = new StringBuilder("curl -v --compressed");
                    if (!this.requestMethod.equals(HttpMethods.GET)) {
                        curlbuf.append(" -X ").append(this.requestMethod);
                    }
                }
            }
            String originalUserAgent = this.headers.getUserAgent();
            if (!this.suppressUserAgentSuffix) {
                if (originalUserAgent == null) {
                    this.headers.setUserAgent(USER_AGENT_SUFFIX);
                } else {
                    this.headers.setUserAgent(originalUserAgent + " " + USER_AGENT_SUFFIX);
                }
            }
            HttpHeaders.serializeHeaders(this.headers, logbuf, curlbuf, logger, lowLevelHttpRequest);
            if (!this.suppressUserAgentSuffix) {
                this.headers.setUserAgent(originalUserAgent);
            }
            StreamingContent streamingContent = this.content;
            boolean contentRetrySupported = streamingContent == null || this.content.retrySupported();
            if (streamingContent != null) {
                String contentEncoding;
                long contentLength;
                String contentType = this.content.getType();
                if (loggable) {
                    streamingContent = new LoggingStreamingContent(streamingContent, HttpTransport.LOGGER, Level.CONFIG, this.contentLoggingLimit);
                }
                if (this.encoding == null) {
                    contentEncoding = null;
                    contentLength = this.content.getLength();
                } else {
                    contentEncoding = this.encoding.getName();
                    StreamingContent httpEncodingStreamingContent = new HttpEncodingStreamingContent(streamingContent, this.encoding);
                    contentLength = contentRetrySupported ? IOUtils.computeLength(httpEncodingStreamingContent) : -1;
                    streamingContent = httpEncodingStreamingContent;
                }
                if (loggable) {
                    String header;
                    if (contentType != null) {
                        header = "Content-Type: " + contentType;
                        logbuf.append(header).append(StringUtils.LINE_SEPARATOR);
                        if (curlbuf != null) {
                            curlbuf.append(" -H '" + header + "'");
                        }
                    }
                    if (contentEncoding != null) {
                        header = "Content-Encoding: " + contentEncoding;
                        logbuf.append(header).append(StringUtils.LINE_SEPARATOR);
                        if (curlbuf != null) {
                            curlbuf.append(" -H '" + header + "'");
                        }
                    }
                    if (contentLength >= 0) {
                        logbuf.append("Content-Length: " + contentLength).append(StringUtils.LINE_SEPARATOR);
                    }
                }
                if (curlbuf != null) {
                    curlbuf.append(" -d '@-'");
                }
                lowLevelHttpRequest.setContentType(contentType);
                lowLevelHttpRequest.setContentEncoding(contentEncoding);
                lowLevelHttpRequest.setContentLength(contentLength);
                lowLevelHttpRequest.setStreamingContent(streamingContent);
            }
            if (loggable) {
                logger.config(logbuf.toString());
                if (curlbuf != null) {
                    curlbuf.append(" -- '");
                    curlbuf.append(urlString.replaceAll("'", "'\"'\"'"));
                    curlbuf.append("'");
                    if (streamingContent != null) {
                        curlbuf.append(" << $$$");
                    }
                    logger.config(curlbuf.toString());
                }
            }
            retryRequest = contentRetrySupported && retriesRemaining > 0;
            lowLevelHttpRequest.setTimeout(this.connectTimeout, this.readTimeout);
            LowLevelHttpResponse lowLevelHttpResponse;
            InputStream lowLevelContent;
            try {
                lowLevelHttpResponse = lowLevelHttpRequest.execute();
                HttpResponse httpResponse = new HttpResponse(this, lowLevelHttpResponse);
                if (!true) {
                    try {
                        lowLevelContent = lowLevelHttpResponse.getContent();
                        if (lowLevelContent != null) {
                            lowLevelContent.close();
                        }
                    } catch (IOException e2) {
                        e = e2;
                        response = httpResponse;
                        if (this.retryOnExecuteIOException || (this.ioExceptionHandler != null && this.ioExceptionHandler.handleIOException(this, retryRequest))) {
                            executeException = e;
                            logger.log(Level.WARNING, "exception thrown while executing request", e);
                            if (response != null) {
                                if (!response.isSuccessStatusCode()) {
                                    errorHandled = false;
                                    if (this.unsuccessfulResponseHandler != null) {
                                        errorHandled = this.unsuccessfulResponseHandler.handleResponse(this, response, retryRequest);
                                    }
                                    if (!errorHandled) {
                                        if (handleRedirect(response.getStatusCode(), response.getHeaders())) {
                                            errorHandled = true;
                                        } else if (retryRequest) {
                                            try {
                                                backOffTime = this.backOffPolicy.getNextBackOffMillis();
                                                if (backOffTime != -1) {
                                                    try {
                                                        this.sleeper.sleep(backOffTime);
                                                    } catch (InterruptedException e3) {
                                                    }
                                                    errorHandled = true;
                                                }
                                            } catch (Throwable th) {
                                                if (!(response == null || false)) {
                                                    response.disconnect();
                                                }
                                            }
                                        }
                                    }
                                    retryRequest &= errorHandled;
                                    if (retryRequest) {
                                        response.ignore();
                                    }
                                    retriesRemaining--;
                                    response.disconnect();
                                    continue;
                                    if (!retryRequest) {
                                        if (response == null) {
                                            if (this.responseInterceptor != null) {
                                                this.responseInterceptor.interceptResponse(response);
                                            }
                                            if (this.throwExceptionOnExecuteError) {
                                            }
                                            return response;
                                        }
                                        throw executeException;
                                    }
                                }
                            }
                            retryRequest &= response != null ? 0 : 1;
                            retriesRemaining--;
                            response.disconnect();
                            continue;
                            if (retryRequest) {
                                if (response == null) {
                                    throw executeException;
                                }
                                if (this.responseInterceptor != null) {
                                    this.responseInterceptor.interceptResponse(response);
                                }
                                if (this.throwExceptionOnExecuteError) {
                                }
                                return response;
                            }
                        }
                        throw e;
                    }
                }
                response = httpResponse;
            } catch (IOException e4) {
                e = e4;
            } catch (Throwable th2) {
                if (!false) {
                    lowLevelContent = lowLevelHttpResponse.getContent();
                    if (lowLevelContent != null) {
                        lowLevelContent.close();
                    }
                }
            }
            if (response != null) {
                if (response.isSuccessStatusCode()) {
                    errorHandled = false;
                    if (this.unsuccessfulResponseHandler != null) {
                        errorHandled = this.unsuccessfulResponseHandler.handleResponse(this, response, retryRequest);
                    }
                    if (errorHandled) {
                        if (handleRedirect(response.getStatusCode(), response.getHeaders())) {
                            errorHandled = true;
                        } else if (retryRequest) {
                            if (this.backOffPolicy != null && this.backOffPolicy.isBackOffRequired(response.getStatusCode())) {
                                backOffTime = this.backOffPolicy.getNextBackOffMillis();
                                if (backOffTime != -1) {
                                    this.sleeper.sleep(backOffTime);
                                    errorHandled = true;
                                }
                            }
                        }
                    }
                    retryRequest &= errorHandled;
                    if (retryRequest) {
                        response.ignore();
                    }
                    retriesRemaining--;
                    if (!(response == null || true)) {
                        response.disconnect();
                        continue;
                    }
                }
            }
            if (response != null) {
            }
            retryRequest &= response != null ? 0 : 1;
            retriesRemaining--;
            response.disconnect();
            continue;
        } while (retryRequest);
        if (response == null) {
            throw executeException;
        }
        if (this.responseInterceptor != null) {
            this.responseInterceptor.interceptResponse(response);
        }
        if (this.throwExceptionOnExecuteError || response.isSuccessStatusCode()) {
            return response;
        }
        try {
            throw new HttpResponseException(response);
        } catch (Throwable th3) {
            response.disconnect();
        }
    }

    @Beta
    public Future<HttpResponse> executeAsync(Executor executor) {
        FutureTask<HttpResponse> future = new FutureTask(new Callable<HttpResponse>() {
            public HttpResponse call() throws Exception {
                return HttpRequest.this.execute();
            }
        });
        executor.execute(future);
        return future;
    }

    @Beta
    public Future<HttpResponse> executeAsync() {
        return executeAsync(Executors.newSingleThreadExecutor());
    }

    public boolean handleRedirect(int statusCode, HttpHeaders responseHeaders) {
        String redirectLocation = responseHeaders.getLocation();
        if (!getFollowRedirects() || !HttpStatusCodes.isRedirect(statusCode) || redirectLocation == null) {
            return false;
        }
        setUrl(new GenericUrl(this.url.toURL(redirectLocation)));
        if (statusCode == HttpStatusCodes.STATUS_CODE_SEE_OTHER) {
            setRequestMethod(HttpMethods.GET);
            setContent(null);
        }
        this.headers.setAuthorization((String) null);
        this.headers.setIfMatch((String) null);
        this.headers.setIfNoneMatch((String) null);
        this.headers.setIfModifiedSince((String) null);
        this.headers.setIfUnmodifiedSince((String) null);
        this.headers.setIfRange((String) null);
        return true;
    }

    public Sleeper getSleeper() {
        return this.sleeper;
    }

    public HttpRequest setSleeper(Sleeper sleeper) {
        this.sleeper = (Sleeper) Preconditions.checkNotNull(sleeper);
        return this;
    }
}
