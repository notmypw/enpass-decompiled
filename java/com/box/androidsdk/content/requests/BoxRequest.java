package com.box.androidsdk.content.requests;

import android.text.TextUtils;
import com.box.androidsdk.content.BoxConstants;
import com.box.androidsdk.content.BoxException;
import com.box.androidsdk.content.BoxException.RateLimitAttemptsExceeded;
import com.box.androidsdk.content.BoxException.RefreshFailure;
import com.box.androidsdk.content.BoxFutureTask;
import com.box.androidsdk.content.auth.BoxAuthentication.BoxAuthenticationInfo;
import com.box.androidsdk.content.listeners.ProgressListener;
import com.box.androidsdk.content.models.BoxArray;
import com.box.androidsdk.content.models.BoxJsonObject;
import com.box.androidsdk.content.models.BoxObject;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.models.BoxSharedLinkSession;
import com.box.androidsdk.content.utils.BoxLogUtils;
import com.box.androidsdk.content.utils.SdkUtils;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.github.clans.fab.BuildConfig;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.http.UrlEncodedParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import net.sqlcipher.database.SQLiteDatabase;

public abstract class BoxRequest<T extends BoxObject, R extends BoxRequest<T, R>> {
    public static final String JSON_OBJECT = "json_object";
    protected LinkedHashMap<String, Object> mBodyMap = new LinkedHashMap();
    Class<T> mClazz;
    protected ContentTypes mContentType = ContentTypes.JSON;
    protected LinkedHashMap<String, String> mHeaderMap = new LinkedHashMap();
    private String mIfMatchEtag;
    private String mIfNoneMatchEtag;
    protected ProgressListener mListener;
    protected HashMap<String, String> mQueryMap = new HashMap();
    BoxRequestHandler mRequestHandler;
    protected Methods mRequestMethod;
    protected String mRequestUrlString;
    protected BoxSession mSession;
    private String mStringBody;
    private int mTimeout;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$box$androidsdk$content$requests$BoxRequest$ContentTypes = new int[ContentTypes.values().length];

        static {
            try {
                $SwitchMap$com$box$androidsdk$content$requests$BoxRequest$ContentTypes[ContentTypes.JSON.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$box$androidsdk$content$requests$BoxRequest$ContentTypes[ContentTypes.URL_ENCODED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$box$androidsdk$content$requests$BoxRequest$ContentTypes[ContentTypes.JSON_PATCH.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public static class BoxRequestHandler<R extends BoxRequest> {
        protected static final int DEFAULT_NUM_RETRIES = 1;
        protected static final int DEFAULT_RATE_LIMIT_WAIT = 20;
        public static final String OAUTH_ERROR_HEADER = "error";
        public static final String OAUTH_INVALID_TOKEN = "invalid_token";
        public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
        protected int mNumRateLimitRetries = 0;
        protected R mRequest;

        public BoxRequestHandler(R request) {
            this.mRequest = request;
        }

        public boolean isResponseSuccess(BoxHttpResponse response) {
            int responseCode = response.getResponseCode();
            return (responseCode >= HttpStatusCodes.STATUS_CODE_OK && responseCode < HttpStatusCodes.STATUS_CODE_MULTIPLE_CHOICES) || responseCode == BoxConstants.HTTP_STATUS_TOO_MANY_REQUESTS;
        }

        public <T extends BoxObject> T onResponse(Class<T> clazz, BoxHttpResponse response) throws IllegalAccessException, InstantiationException, BoxException {
            if (response.getResponseCode() == BoxConstants.HTTP_STATUS_TOO_MANY_REQUESTS) {
                return retryRateLimited(response);
            }
            String contentType = response.getContentType();
            BoxObject entity = (BoxObject) clazz.newInstance();
            if (!(entity instanceof BoxJsonObject) || !contentType.contains(ContentTypes.JSON.toString())) {
                return entity;
            }
            ((BoxJsonObject) entity).createFromJson(response.getStringBody());
            return entity;
        }

        protected <T extends BoxObject> T retryRateLimited(BoxHttpResponse response) throws BoxException {
            if (this.mNumRateLimitRetries < DEFAULT_NUM_RETRIES) {
                this.mNumRateLimitRetries += DEFAULT_NUM_RETRIES;
                try {
                    Thread.sleep((long) getRetryAfterFromResponse(response, ((int) (10.0d * Math.random())) + DEFAULT_RATE_LIMIT_WAIT));
                    return this.mRequest.send();
                } catch (Throwable e) {
                    throw new BoxException(e.getMessage(), e);
                }
            }
            throw new RateLimitAttemptsExceeded("Max attempts exceeded", this.mNumRateLimitRetries, response);
        }

        public boolean onException(BoxRequest request, BoxHttpResponse response, BoxException ex) throws RefreshFailure {
            BoxSession session = request.getSession();
            if (oauthExpired(response)) {
                try {
                    BoxResponse<BoxSession> refreshResponse = (BoxResponse) session.refresh().get();
                    if (refreshResponse.isSuccess()) {
                        return true;
                    }
                    if (refreshResponse.getException() != null) {
                        if (!(refreshResponse.getException() instanceof RefreshFailure)) {
                            return false;
                        }
                        throw ((RefreshFailure) refreshResponse.getException());
                    }
                } catch (InterruptedException e) {
                    BoxLogUtils.e("oauthRefresh", "Interrupted Exception", e);
                } catch (ExecutionException e1) {
                    BoxLogUtils.e("oauthRefresh", "Interrupted Exception", e1);
                }
            } else if (authFailed(response)) {
                session.getAuthInfo().setUser(null);
                try {
                    session.authenticate().get();
                    if (session.getUser() == null) {
                        return false;
                    }
                    return true;
                } catch (Exception e2) {
                }
            }
            return false;
        }

        protected static int getRetryAfterFromResponse(BoxHttpResponse response, int defaultSeconds) {
            int retryAfterSeconds = defaultSeconds;
            String value = response.getHttpURLConnection().getHeaderField("Retry-After");
            if (!SdkUtils.isBlank(value)) {
                try {
                    retryAfterSeconds = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                }
                if (retryAfterSeconds <= 0) {
                    retryAfterSeconds = DEFAULT_NUM_RETRIES;
                }
            }
            return retryAfterSeconds * 1000;
        }

        private boolean authFailed(BoxHttpResponse response) {
            return response != null && response.getResponseCode() == HttpStatusCodes.STATUS_CODE_UNAUTHORIZED;
        }

        private boolean oauthExpired(BoxHttpResponse response) {
            if (response == null || HttpStatusCodes.STATUS_CODE_UNAUTHORIZED != response.getResponseCode()) {
                return false;
            }
            String header = response.mConnection.getHeaderField(WWW_AUTHENTICATE);
            if (SdkUtils.isEmptyString(header)) {
                return false;
            }
            String[] authStrs = header.split(",");
            int length = authStrs.length;
            for (int i = 0; i < length; i += DEFAULT_NUM_RETRIES) {
                if (isInvalidTokenError(authStrs[i])) {
                    return true;
                }
            }
            return false;
        }

        private boolean isInvalidTokenError(String str) {
            String[] parts = str.split("=");
            if (parts.length == 2 && parts[0] != null && parts[DEFAULT_NUM_RETRIES] != null && OAUTH_ERROR_HEADER.equalsIgnoreCase(parts[0].trim()) && OAUTH_INVALID_TOKEN.equalsIgnoreCase(parts[DEFAULT_NUM_RETRIES].replace("\"", BuildConfig.FLAVOR).trim())) {
                return true;
            }
            return false;
        }
    }

    public enum ContentTypes {
        JSON("application/json"),
        URL_ENCODED(UrlEncodedParser.CONTENT_TYPE),
        JSON_PATCH("application/json-patch+json");
        
        private String mName;

        private ContentTypes(String name) {
            this.mName = name;
        }

        public String toString() {
            return this.mName;
        }
    }

    public enum Methods {
        GET,
        POST,
        PUT,
        DELETE,
        OPTIONS
    }

    public BoxRequest(Class<T> clazz, String requestUrl, BoxSession session) {
        this.mClazz = clazz;
        this.mRequestUrlString = requestUrl;
        this.mSession = session;
        setRequestHandler(new BoxRequestHandler(this));
    }

    protected BoxRequest(BoxRequest request) {
        this.mSession = request.getSession();
        this.mClazz = request.mClazz;
        this.mRequestHandler = request.getRequestHandler();
        this.mRequestMethod = request.mRequestMethod;
        this.mContentType = request.mContentType;
        this.mIfMatchEtag = request.getIfMatchEtag();
        this.mListener = request.mListener;
        this.mRequestUrlString = request.mRequestUrlString;
        this.mIfNoneMatchEtag = request.getIfNoneMatchEtag();
        this.mTimeout = request.mTimeout;
        this.mStringBody = request.mStringBody;
        importRequestContentMapsFrom(request);
    }

    protected void importRequestContentMapsFrom(BoxRequest source) {
        this.mQueryMap = new HashMap(source.mQueryMap);
        this.mBodyMap = new LinkedHashMap(source.mBodyMap);
    }

    public BoxSession getSession() {
        return this.mSession;
    }

    public BoxRequestHandler getRequestHandler() {
        return this.mRequestHandler;
    }

    public R setRequestHandler(BoxRequestHandler handler) {
        this.mRequestHandler = handler;
        return this;
    }

    public R setContentType(ContentTypes contentType) {
        this.mContentType = contentType;
        return this;
    }

    public R setTimeOut(int timeOut) {
        this.mTimeout = timeOut;
        return this;
    }

    public T send() throws BoxException {
        T onResponse;
        IOException e;
        Throwable th;
        InstantiationException e2;
        IllegalAccessException e3;
        BoxException e4;
        BoxRequestHandler requestHandler = getRequestHandler();
        BoxHttpResponse response = null;
        HttpURLConnection connection = null;
        try {
            connection = createHttpRequest().getUrlConnection();
            if (this.mTimeout > 0) {
                connection.setConnectTimeout(this.mTimeout);
                connection.setReadTimeout(this.mTimeout);
            }
            BoxHttpResponse response2 = new BoxHttpResponse(connection);
            try {
                response2.open();
                logDebug(response2);
                if (requestHandler.isResponseSuccess(response2)) {
                    onResponse = requestHandler.onResponse(this.mClazz, response2);
                    if (connection != null) {
                        connection.disconnect();
                    }
                    response = response2;
                    return onResponse;
                }
                throw new BoxException("An error occurred while sending the request", response2);
            } catch (IOException e5) {
                e = e5;
                response = response2;
                try {
                    onResponse = handleSendException(requestHandler, response, e);
                    if (connection != null) {
                        connection.disconnect();
                    }
                    return onResponse;
                } catch (Throwable th2) {
                    th = th2;
                    if (connection != null) {
                        connection.disconnect();
                    }
                    throw th;
                }
            } catch (InstantiationException e6) {
                e2 = e6;
                response = response2;
                onResponse = handleSendException(requestHandler, response, e2);
                if (connection != null) {
                    connection.disconnect();
                }
                return onResponse;
            } catch (IllegalAccessException e7) {
                e3 = e7;
                response = response2;
                onResponse = handleSendException(requestHandler, response, e3);
                if (connection != null) {
                    connection.disconnect();
                }
                return onResponse;
            } catch (BoxException e8) {
                e4 = e8;
                response = response2;
                onResponse = handleSendException(requestHandler, response, e4);
                if (connection != null) {
                    connection.disconnect();
                }
                return onResponse;
            } catch (Throwable th3) {
                th = th3;
                response = response2;
                if (connection != null) {
                    connection.disconnect();
                }
                throw th;
            }
        } catch (IOException e9) {
            e = e9;
            onResponse = handleSendException(requestHandler, response, e);
            if (connection != null) {
                connection.disconnect();
            }
            return onResponse;
        } catch (InstantiationException e10) {
            e2 = e10;
            onResponse = handleSendException(requestHandler, response, e2);
            if (connection != null) {
                connection.disconnect();
            }
            return onResponse;
        } catch (IllegalAccessException e11) {
            e3 = e11;
            onResponse = handleSendException(requestHandler, response, e3);
            if (connection != null) {
                connection.disconnect();
            }
            return onResponse;
        } catch (BoxException e12) {
            e4 = e12;
            onResponse = handleSendException(requestHandler, response, e4);
            if (connection != null) {
                connection.disconnect();
            }
            return onResponse;
        }
    }

    private T handleSendException(BoxRequestHandler requestHandler, BoxHttpResponse response, Exception ex) throws BoxException {
        if (!(ex instanceof BoxException)) {
            BoxException e = new BoxException("Couldn't connect to the Box API due to a network error.", (Throwable) ex);
            requestHandler.onException(this, response, e);
            throw e;
        } else if (requestHandler.onException(this, response, (BoxException) ex)) {
            return send();
        } else {
            throw ((BoxException) ex);
        }
    }

    public BoxFutureTask<T> toTask() {
        return new BoxFutureTask(this.mClazz, this);
    }

    protected BoxHttpRequest createHttpRequest() throws IOException, BoxException {
        BoxHttpRequest httpRequest = new BoxHttpRequest(buildUrl(), this.mRequestMethod, this.mListener);
        setHeaders(httpRequest);
        setBody(httpRequest);
        return httpRequest;
    }

    protected URL buildUrl() throws MalformedURLException, UnsupportedEncodingException {
        if (TextUtils.isEmpty(createQuery(this.mQueryMap))) {
            return new URL(this.mRequestUrlString);
        }
        return new URL(String.format(Locale.ENGLISH, "%s?%s", new Object[]{this.mRequestUrlString, createQuery(this.mQueryMap)}));
    }

    protected String createQuery(Map<String, String> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        String queryPattern = "%s=%s";
        boolean first = true;
        for (Entry<String, String> pair : map.entrySet()) {
            sb.append(String.format(Locale.ENGLISH, queryPattern, new Object[]{URLEncoder.encode((String) pair.getKey(), "UTF-8"), URLEncoder.encode((String) pair.getValue(), "UTF-8")}));
            if (first) {
                queryPattern = "&" + queryPattern;
                first = false;
            }
        }
        return sb.toString();
    }

    protected void setHeaders(BoxHttpRequest request) {
        this.mHeaderMap.clear();
        BoxAuthenticationInfo info = this.mSession.getAuthInfo();
        if (!SdkUtils.isEmptyString(info == null ? null : info.accessToken())) {
            this.mHeaderMap.put("Authorization", String.format(Locale.ENGLISH, "Bearer %s", new Object[]{info == null ? null : info.accessToken()}));
        }
        this.mHeaderMap.put("User-Agent", this.mSession.getUserAgent());
        this.mHeaderMap.put("Accept-Encoding", "gzip");
        this.mHeaderMap.put("Accept-Charset", "utf-8");
        this.mHeaderMap.put("Content-Type", this.mContentType.toString());
        if (this.mIfMatchEtag != null) {
            this.mHeaderMap.put("If-Match", this.mIfMatchEtag);
        }
        if (this.mIfNoneMatchEtag != null) {
            this.mHeaderMap.put("If-None-Match", this.mIfNoneMatchEtag);
        }
        if (this.mSession instanceof BoxSharedLinkSession) {
            String shareLinkHeader = String.format(Locale.ENGLISH, "shared_link=%s", new Object[]{this.mSession.getSharedLink()});
            if (this.mSession.getPassword() != null) {
                shareLinkHeader = shareLinkHeader + String.format(Locale.ENGLISH, "&shared_link_password=%s", new Object[]{slSession.getPassword()});
            }
            this.mHeaderMap.put("BoxApi", shareLinkHeader);
        }
        for (Entry<String, String> h : this.mHeaderMap.entrySet()) {
            request.addHeader((String) h.getKey(), (String) h.getValue());
        }
    }

    protected R setIfMatchEtag(String etag) {
        this.mIfMatchEtag = etag;
        return this;
    }

    protected String getIfMatchEtag() {
        return this.mIfMatchEtag;
    }

    protected R setIfNoneMatchEtag(String etag) {
        this.mIfNoneMatchEtag = etag;
        return this;
    }

    protected String getIfNoneMatchEtag() {
        return this.mIfNoneMatchEtag;
    }

    protected void setBody(BoxHttpRequest request) throws IOException {
        if (!this.mBodyMap.isEmpty()) {
            request.setBody(new ByteArrayInputStream(getStringBody().getBytes("UTF-8")));
        }
    }

    public String getStringBody() throws UnsupportedEncodingException {
        if (this.mStringBody != null) {
            return this.mStringBody;
        }
        switch (AnonymousClass1.$SwitchMap$com$box$androidsdk$content$requests$BoxRequest$ContentTypes[this.mContentType.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                JsonObject jsonBody = new JsonObject();
                for (Entry<String, Object> entry : this.mBodyMap.entrySet()) {
                    parseHashMapEntry(jsonBody, entry);
                }
                this.mStringBody = jsonBody.toString();
                break;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                HashMap<String, String> stringMap = new HashMap();
                for (Entry<String, Object> entry2 : this.mBodyMap.entrySet()) {
                    stringMap.put(entry2.getKey(), (String) entry2.getValue());
                }
                this.mStringBody = createQuery(stringMap);
                break;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                this.mStringBody = ((BoxArray) this.mBodyMap.get(JSON_OBJECT)).toJson();
                break;
        }
        return this.mStringBody;
    }

    protected void parseHashMapEntry(JsonObject jsonBody, Entry<String, Object> entry) {
        Object obj = entry.getValue();
        if (obj instanceof BoxJsonObject) {
            jsonBody.add((String) entry.getKey(), parseJsonObject(obj));
        } else if (obj instanceof Double) {
            jsonBody.add((String) entry.getKey(), Double.toString(((Double) obj).doubleValue()));
        } else if ((obj instanceof Enum) || (obj instanceof Boolean)) {
            jsonBody.add((String) entry.getKey(), obj.toString());
        } else if (obj instanceof JsonArray) {
            jsonBody.add((String) entry.getKey(), (JsonArray) obj);
        } else {
            jsonBody.add((String) entry.getKey(), (String) entry.getValue());
        }
    }

    protected JsonValue parseJsonObject(Object obj) {
        return JsonValue.readFrom(((BoxJsonObject) obj).toJson());
    }

    protected void logDebug(BoxHttpResponse response) throws BoxException {
        logRequest();
        BoxLogUtils.i(BoxConstants.TAG, String.format(Locale.ENGLISH, "Response (%s):  %s", new Object[]{Integer.valueOf(response.getResponseCode()), response.getStringBody()}));
    }

    protected void logRequest() {
        String urlString;
        String queryString = null;
        try {
            queryString = createQuery(this.mQueryMap);
        } catch (UnsupportedEncodingException e) {
        }
        if (SdkUtils.isBlank(queryString)) {
            urlString = this.mRequestUrlString;
        } else {
            urlString = String.format(Locale.ENGLISH, "%s?%s", new Object[]{this.mRequestUrlString, queryString});
        }
        BoxLogUtils.i(BoxConstants.TAG, String.format(Locale.ENGLISH, "Request (%s):  %s", new Object[]{this.mRequestMethod, urlString}));
        BoxLogUtils.i(BoxConstants.TAG, "Request Header", this.mHeaderMap);
        switch (AnonymousClass1.$SwitchMap$com$box$androidsdk$content$requests$BoxRequest$ContentTypes[this.mContentType.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                if (!SdkUtils.isBlank(this.mStringBody)) {
                    BoxLogUtils.i(BoxConstants.TAG, String.format(Locale.ENGLISH, "Request JSON:  %s", new Object[]{this.mStringBody}));
                    return;
                }
                return;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                HashMap<String, String> stringMap = new HashMap();
                for (Entry<String, Object> entry : this.mBodyMap.entrySet()) {
                    stringMap.put(entry.getKey(), (String) entry.getValue());
                }
                BoxLogUtils.i(BoxConstants.TAG, "Request Form Data", stringMap);
                return;
            default:
                return;
        }
    }
}
