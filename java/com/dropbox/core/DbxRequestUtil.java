package com.dropbox.core;

import com.box.androidsdk.content.BoxConstants;
import com.box.androidsdk.content.models.BoxSharedLink;
import com.dropbox.core.http.HttpRequestor.Header;
import com.dropbox.core.http.HttpRequestor.Response;
import com.dropbox.core.http.HttpRequestor.Uploader;
import com.dropbox.core.json.JsonReadException;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.util.IOUtil;
import com.dropbox.core.util.LangUtil;
import com.dropbox.core.util.StringUtil;
import com.github.clans.fab.BuildConfig;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.util.ExponentialBackOff;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public final class DbxRequestUtil {
    private static final Random RAND = new Random();

    public static abstract class ResponseHandler<T> {
        public abstract T handle(Response response) throws DbxException;
    }

    public static abstract class RequestMaker<T, E extends Throwable> {
        public abstract T run() throws DbxException, Throwable;
    }

    public static String encodeUrlParam(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw LangUtil.mkAssert("UTF-8 should always be supported", ex);
        }
    }

    public static String buildUrlWithParams(String userLocale, String host, String path, String[] params) {
        return buildUri(host, path) + "?" + encodeUrlParams(userLocale, params);
    }

    static String[] toParamsArray(Map<String, String> params) {
        String[] arr = new String[(params.size() * 2)];
        int i = 0;
        for (Entry<String, String> entry : params.entrySet()) {
            arr[i] = (String) entry.getKey();
            arr[i + 1] = (String) entry.getValue();
            i += 2;
        }
        return arr;
    }

    public static String buildUri(String host, String path) {
        try {
            return new URI("https", host, "/" + path, null).toASCIIString();
        } catch (URISyntaxException ex) {
            throw LangUtil.mkAssert("URI creation failed, host=" + StringUtil.jq(host) + ", path=" + StringUtil.jq(path), ex);
        }
    }

    private static String encodeUrlParams(String userLocale, String[] params) {
        StringBuilder buf = new StringBuilder();
        String sep = BuildConfig.FLAVOR;
        if (userLocale != null) {
            buf.append("locale=").append(userLocale);
            sep = "&";
        }
        if (params != null) {
            if (params.length % 2 != 0) {
                throw new IllegalArgumentException("'params.length' is " + params.length + "; expecting a multiple of two");
            }
            for (int i = 0; i < params.length; i += 2) {
                String key = params[i];
                String value = params[i + 1];
                if (key == null) {
                    throw new IllegalArgumentException("params[" + i + "] is null");
                }
                if (value != null) {
                    buf.append(sep);
                    sep = "&";
                    buf.append(encodeUrlParam(key));
                    buf.append("=");
                    buf.append(encodeUrlParam(value));
                }
            }
        }
        return buf.toString();
    }

    public static List<Header> addAuthHeader(List<Header> headers, String accessToken) {
        if (accessToken == null) {
            throw new NullPointerException("accessToken");
        }
        if (headers == null) {
            headers = new ArrayList();
        }
        headers.add(new Header("Authorization", "Bearer " + accessToken));
        return headers;
    }

    public static List<Header> addSelectUserHeader(List<Header> headers, String memberId) {
        if (memberId == null) {
            throw new NullPointerException("memberId");
        }
        if (headers == null) {
            headers = new ArrayList();
        }
        headers.add(new Header("Dropbox-API-Select-User", memberId));
        return headers;
    }

    public static List<Header> addBasicAuthHeader(List<Header> headers, String username, String password) {
        if (username == null) {
            throw new NullPointerException("username");
        } else if (password == null) {
            throw new NullPointerException(BoxSharedLink.FIELD_PASSWORD);
        } else {
            if (headers == null) {
                headers = new ArrayList();
            }
            headers.add(new Header("Authorization", "Basic " + StringUtil.base64Encode(StringUtil.stringToUtf8(username + ":" + password))));
            return headers;
        }
    }

    public static List<Header> addUserAgentHeader(List<Header> headers, DbxRequestConfig requestConfig, String sdkUserAgentIdentifier) {
        if (headers == null) {
            headers = new ArrayList();
        }
        headers.add(buildUserAgentHeader(requestConfig, sdkUserAgentIdentifier));
        return headers;
    }

    public static List<Header> addUserLocaleHeader(List<Header> headers, DbxRequestConfig requestConfig) {
        if (requestConfig.getUserLocale() != null) {
            if (headers == null) {
                headers = new ArrayList();
            }
            headers.add(new Header("Dropbox-API-User-Locale", requestConfig.getUserLocale()));
        }
        return headers;
    }

    public static Header buildUserAgentHeader(DbxRequestConfig requestConfig, String sdkUserAgentIdentifier) {
        return new Header("User-Agent", requestConfig.getClientIdentifier() + " " + sdkUserAgentIdentifier + "/" + DbxSdkVersion.Version);
    }

    public static Response startGet(DbxRequestConfig requestConfig, String accessToken, String sdkUserAgentIdentifier, String host, String path, String[] params, List<Header> headers) throws NetworkIOException {
        headers = addAuthHeader(addUserAgentHeader(copyHeaders(headers), requestConfig, sdkUserAgentIdentifier), accessToken);
        try {
            return requestConfig.getHttpRequestor().doGet(buildUrlWithParams(requestConfig.getUserLocale(), host, path, params), headers);
        } catch (IOException ex) {
            throw new NetworkIOException(ex);
        }
    }

    public static Uploader startPut(DbxRequestConfig requestConfig, String accessToken, String sdkUserAgentIdentifier, String host, String path, String[] params, List<Header> headers) throws NetworkIOException {
        headers = addAuthHeader(addUserAgentHeader(copyHeaders(headers), requestConfig, sdkUserAgentIdentifier), accessToken);
        try {
            return requestConfig.getHttpRequestor().startPut(buildUrlWithParams(requestConfig.getUserLocale(), host, path, params), headers);
        } catch (IOException ex) {
            throw new NetworkIOException(ex);
        }
    }

    public static Response startPostNoAuth(DbxRequestConfig requestConfig, String sdkUserAgentIdentifier, String host, String path, String[] params, List<Header> headers) throws NetworkIOException {
        byte[] encodedParams = StringUtil.stringToUtf8(encodeUrlParams(requestConfig.getUserLocale(), params));
        headers = copyHeaders(headers);
        headers.add(new Header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
        return startPostRaw(requestConfig, sdkUserAgentIdentifier, host, path, encodedParams, headers);
    }

    public static Response startPostRaw(DbxRequestConfig requestConfig, String sdkUserAgentIdentifier, String host, String path, byte[] body, List<Header> headers) throws NetworkIOException {
        Uploader uploader;
        String uri = buildUri(host, path);
        headers = addUserAgentHeader(copyHeaders(headers), requestConfig, sdkUserAgentIdentifier);
        headers.add(new Header("Content-Length", Integer.toString(body.length)));
        try {
            uploader = requestConfig.getHttpRequestor().startPost(uri, headers);
            uploader.upload(body);
            Response finish = uploader.finish();
            uploader.close();
            return finish;
        } catch (IOException ex) {
            throw new NetworkIOException(ex);
        } catch (Throwable th) {
            uploader.close();
        }
    }

    private static List<Header> copyHeaders(List<Header> headers) {
        if (headers == null) {
            return new ArrayList();
        }
        return new ArrayList(headers);
    }

    public static byte[] loadErrorBody(Response response) throws NetworkIOException {
        if (response.getBody() == null) {
            return new byte[0];
        }
        try {
            return IOUtil.slurp(response.getBody(), 4096);
        } catch (IOException ex) {
            throw new NetworkIOException(ex);
        }
    }

    public static String parseErrorBody(String requestId, int statusCode, byte[] body) throws BadResponseException {
        try {
            return StringUtil.utf8ToString(body);
        } catch (CharacterCodingException e) {
            throw new BadResponseException(requestId, "Got non-UTF8 response body: " + statusCode + ": " + e.getMessage());
        }
    }

    public static DbxException unexpectedStatus(Response response) throws NetworkIOException, BadResponseException {
        String requestId = getRequestId(response);
        String message = parseErrorBody(requestId, response.getStatusCode(), loadErrorBody(response));
        switch (response.getStatusCode()) {
            case 400:
                return new BadRequestException(requestId, message);
            case HttpStatusCodes.STATUS_CODE_UNAUTHORIZED /*401*/:
                return new InvalidAccessTokenException(requestId, message);
            case BoxConstants.HTTP_STATUS_TOO_MANY_REQUESTS /*429*/:
                try {
                    return new RateLimitException(requestId, message, (long) Integer.parseInt(getFirstHeader(response, "Retry-After")), TimeUnit.SECONDS);
                } catch (NumberFormatException e) {
                    return new BadResponseException(requestId, "Invalid value for HTTP header: \"Retry-After\"");
                }
            case ExponentialBackOff.DEFAULT_INITIAL_INTERVAL_MILLIS /*500*/:
                return new ServerException(requestId, message);
            case HttpStatusCodes.STATUS_CODE_SERVICE_UNAVAILABLE /*503*/:
                String retryAfter = getFirstHeaderMaybe(response, "Retry-After");
                if (retryAfter != null) {
                    try {
                        if (!retryAfter.trim().isEmpty()) {
                            return new RetryException(requestId, message, (long) Integer.parseInt(retryAfter), TimeUnit.SECONDS);
                        }
                    } catch (NumberFormatException e2) {
                        return new BadResponseException(requestId, "Invalid value for HTTP header: \"Retry-After\"");
                    }
                }
                return new RetryException(requestId, message);
            default:
                return new BadResponseCodeException(requestId, "unexpected HTTP status code: " + response.getStatusCode() + ": " + message, response.getStatusCode());
        }
    }

    public static <T> T readJsonFromResponse(JsonReader<T> reader, Response response) throws BadResponseException, NetworkIOException {
        try {
            return reader.readFully(response.getBody());
        } catch (JsonReadException ex) {
            throw new BadResponseException(getRequestId(response), "error in response JSON: " + ex.getMessage(), ex);
        } catch (IOException ex2) {
            throw new NetworkIOException(ex2);
        }
    }

    public static <T> T doGet(DbxRequestConfig requestConfig, String accessToken, String sdkUserAgentIdentifier, String host, String path, String[] params, List<Header> headers, ResponseHandler<T> handler) throws DbxException {
        final DbxRequestConfig dbxRequestConfig = requestConfig;
        final String str = accessToken;
        final String str2 = sdkUserAgentIdentifier;
        final String str3 = host;
        final String str4 = path;
        final String[] strArr = params;
        final List<Header> list = headers;
        final ResponseHandler<T> responseHandler = handler;
        return runAndRetry(requestConfig.getMaxRetries(), new RequestMaker<T, DbxException>() {
            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public T run() throws com.dropbox.core.DbxException {
                /*
                r9 = this;
                r0 = r1;
                r1 = r2;
                r2 = r3;
                r3 = r4;
                r4 = r5;
                r5 = r6;
                r6 = r7;
                r8 = com.dropbox.core.DbxRequestUtil.startGet(r0, r1, r2, r3, r4, r5, r6);
                r0 = r8;	 Catch:{ all -> 0x0027 }
                r0 = r0.handle(r8);	 Catch:{ all -> 0x0027 }
                r1 = r8.getBody();	 Catch:{ IOException -> 0x0020 }
                r1.close();	 Catch:{ IOException -> 0x0020 }
                return r0;
            L_0x0020:
                r7 = move-exception;
                r0 = new com.dropbox.core.NetworkIOException;
                r0.<init>(r7);
                throw r0;
            L_0x0027:
                r0 = move-exception;
                r1 = r8.getBody();	 Catch:{ IOException -> 0x0030 }
                r1.close();	 Catch:{ IOException -> 0x0030 }
                throw r0;
            L_0x0030:
                r7 = move-exception;
                r0 = new com.dropbox.core.NetworkIOException;
                r0.<init>(r7);
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.dropbox.core.DbxRequestUtil.1.run():T");
            }
        });
    }

    public static <T> T doPost(DbxRequestConfig requestConfig, String accessToken, String sdkUserAgentIdentifier, String host, String path, String[] params, List<Header> headers, ResponseHandler<T> handler) throws DbxException {
        return doPostNoAuth(requestConfig, sdkUserAgentIdentifier, host, path, params, addAuthHeader(copyHeaders(headers), accessToken), handler);
    }

    public static <T> T doPostNoAuth(DbxRequestConfig requestConfig, String sdkUserAgentIdentifier, String host, String path, String[] params, List<Header> headers, ResponseHandler<T> handler) throws DbxException {
        final DbxRequestConfig dbxRequestConfig = requestConfig;
        final String str = sdkUserAgentIdentifier;
        final String str2 = host;
        final String str3 = path;
        final String[] strArr = params;
        final List<Header> list = headers;
        final ResponseHandler<T> responseHandler = handler;
        return runAndRetry(requestConfig.getMaxRetries(), new RequestMaker<T, DbxException>() {
            public T run() throws DbxException {
                return DbxRequestUtil.finishResponse(DbxRequestUtil.startPostNoAuth(dbxRequestConfig, str, str2, str3, strArr, list), responseHandler);
            }
        });
    }

    public static <T> T finishResponse(Response response, ResponseHandler<T> handler) throws DbxException {
        try {
            T handle = handler.handle(response);
            return handle;
        } finally {
            IOUtil.closeInput(response.getBody());
        }
    }

    public static String getFirstHeader(Response response, String name) throws BadResponseException {
        List<String> values = (List) response.getHeaders().get(name);
        if (values != null && !values.isEmpty()) {
            return (String) values.get(0);
        }
        throw new BadResponseException(getRequestId(response), "missing HTTP header \"" + name + "\"");
    }

    public static String getFirstHeaderMaybe(Response response, String name) {
        List<String> values = (List) response.getHeaders().get(name);
        if (values == null || values.isEmpty()) {
            return null;
        }
        return (String) values.get(0);
    }

    public static String getRequestId(Response response) {
        return getFirstHeaderMaybe(response, "X-Dropbox-Request-Id");
    }

    public static <T, E extends Throwable> T runAndRetry(int maxRetries, RequestMaker<T, E> requestMaker) throws DbxException, Throwable {
        long backoff;
        DbxException thrown;
        int numRetries = 0;
        while (true) {
            try {
                return requestMaker.run();
            } catch (DbxException ex) {
                backoff = ex.getBackoffMillis();
                thrown = ex;
            } catch (DbxException ex2) {
                backoff = 0;
                thrown = ex2;
            }
            numRetries++;
        }
        if (numRetries >= maxRetries) {
            throw thrown;
        }
        backoff += (long) RAND.nextInt(1000);
        if (backoff > 0) {
            try {
                Thread.sleep(backoff);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        numRetries++;
    }
}
