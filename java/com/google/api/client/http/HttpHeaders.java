package com.google.api.client.http;

import com.google.api.client.util.ArrayValueMap;
import com.google.api.client.util.Base64;
import com.google.api.client.util.ClassInfo;
import com.google.api.client.util.Data;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.GenericData.Flags;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import com.google.api.client.util.Throwables;
import com.google.api.client.util.Types;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpHeaders extends GenericData {
    @Key("Accept")
    private List<String> accept;
    @Key("Accept-Encoding")
    private List<String> acceptEncoding = new ArrayList(Collections.singleton("gzip"));
    @Key("Age")
    private List<Long> age;
    @Key("WWW-Authenticate")
    private List<String> authenticate;
    @Key("Authorization")
    private List<String> authorization;
    @Key("Cache-Control")
    private List<String> cacheControl;
    @Key("Content-Encoding")
    private List<String> contentEncoding;
    @Key("Content-Length")
    private List<Long> contentLength;
    @Key("Content-MD5")
    private List<String> contentMD5;
    @Key("Content-Range")
    private List<String> contentRange;
    @Key("Content-Type")
    private List<String> contentType;
    @Key("Cookie")
    private List<String> cookie;
    @Key("Date")
    private List<String> date;
    @Key("ETag")
    private List<String> etag;
    @Key("Expires")
    private List<String> expires;
    @Key("If-Match")
    private List<String> ifMatch;
    @Key("If-Modified-Since")
    private List<String> ifModifiedSince;
    @Key("If-None-Match")
    private List<String> ifNoneMatch;
    @Key("If-Range")
    private List<String> ifRange;
    @Key("If-Unmodified-Since")
    private List<String> ifUnmodifiedSince;
    @Key("Last-Modified")
    private List<String> lastModified;
    @Key("Location")
    private List<String> location;
    @Key("MIME-Version")
    private List<String> mimeVersion;
    @Key("Range")
    private List<String> range;
    @Key("Retry-After")
    private List<String> retryAfter;
    @Key("User-Agent")
    private List<String> userAgent;

    private static class HeaderParsingFakeLevelHttpRequest extends LowLevelHttpRequest {
        private final ParseHeaderState state;
        private final HttpHeaders target;

        HeaderParsingFakeLevelHttpRequest(HttpHeaders target, ParseHeaderState state) {
            this.target = target;
            this.state = state;
        }

        public void addHeader(String name, String value) {
            this.target.parseHeader(name, value, this.state);
        }

        public LowLevelHttpResponse execute() throws IOException {
            throw new UnsupportedOperationException();
        }
    }

    private static final class ParseHeaderState {
        final ArrayValueMap arrayValueMap;
        final ClassInfo classInfo;
        final List<Type> context;
        final StringBuilder logger;

        public ParseHeaderState(HttpHeaders headers, StringBuilder logger) {
            this.context = Arrays.asList(new Type[]{headers.getClass()});
            this.classInfo = ClassInfo.of(clazz, true);
            this.logger = logger;
            this.arrayValueMap = new ArrayValueMap(headers);
        }

        void finish() {
            this.arrayValueMap.setValues();
        }
    }

    public HttpHeaders() {
        super(EnumSet.of(Flags.IGNORE_CASE));
    }

    public HttpHeaders clone() {
        return (HttpHeaders) super.clone();
    }

    public HttpHeaders set(String fieldName, Object value) {
        return (HttpHeaders) super.set(fieldName, value);
    }

    public final String getAccept() {
        return (String) getFirstHeaderValue(this.accept);
    }

    public HttpHeaders setAccept(String accept) {
        this.accept = getAsList(accept);
        return this;
    }

    public final String getAcceptEncoding() {
        return (String) getFirstHeaderValue(this.acceptEncoding);
    }

    public HttpHeaders setAcceptEncoding(String acceptEncoding) {
        this.acceptEncoding = getAsList(acceptEncoding);
        return this;
    }

    public final String getAuthorization() {
        return (String) getFirstHeaderValue(this.authorization);
    }

    public final List<String> getAuthorizationAsList() {
        return this.authorization;
    }

    public HttpHeaders setAuthorization(String authorization) {
        return setAuthorization(getAsList(authorization));
    }

    public HttpHeaders setAuthorization(List<String> authorization) {
        this.authorization = authorization;
        return this;
    }

    public final String getCacheControl() {
        return (String) getFirstHeaderValue(this.cacheControl);
    }

    public HttpHeaders setCacheControl(String cacheControl) {
        this.cacheControl = getAsList(cacheControl);
        return this;
    }

    public final String getContentEncoding() {
        return (String) getFirstHeaderValue(this.contentEncoding);
    }

    public HttpHeaders setContentEncoding(String contentEncoding) {
        this.contentEncoding = getAsList(contentEncoding);
        return this;
    }

    public final Long getContentLength() {
        return (Long) getFirstHeaderValue(this.contentLength);
    }

    public HttpHeaders setContentLength(Long contentLength) {
        this.contentLength = getAsList(contentLength);
        return this;
    }

    public final String getContentMD5() {
        return (String) getFirstHeaderValue(this.contentMD5);
    }

    public HttpHeaders setContentMD5(String contentMD5) {
        this.contentMD5 = getAsList(contentMD5);
        return this;
    }

    public final String getContentRange() {
        return (String) getFirstHeaderValue(this.contentRange);
    }

    public HttpHeaders setContentRange(String contentRange) {
        this.contentRange = getAsList(contentRange);
        return this;
    }

    public final String getContentType() {
        return (String) getFirstHeaderValue(this.contentType);
    }

    public HttpHeaders setContentType(String contentType) {
        this.contentType = getAsList(contentType);
        return this;
    }

    public final String getCookie() {
        return (String) getFirstHeaderValue(this.cookie);
    }

    public HttpHeaders setCookie(String cookie) {
        this.cookie = getAsList(cookie);
        return this;
    }

    public final String getDate() {
        return (String) getFirstHeaderValue(this.date);
    }

    public HttpHeaders setDate(String date) {
        this.date = getAsList(date);
        return this;
    }

    public final String getETag() {
        return (String) getFirstHeaderValue(this.etag);
    }

    public HttpHeaders setETag(String etag) {
        this.etag = getAsList(etag);
        return this;
    }

    public final String getExpires() {
        return (String) getFirstHeaderValue(this.expires);
    }

    public HttpHeaders setExpires(String expires) {
        this.expires = getAsList(expires);
        return this;
    }

    public final String getIfModifiedSince() {
        return (String) getFirstHeaderValue(this.ifModifiedSince);
    }

    public HttpHeaders setIfModifiedSince(String ifModifiedSince) {
        this.ifModifiedSince = getAsList(ifModifiedSince);
        return this;
    }

    public final String getIfMatch() {
        return (String) getFirstHeaderValue(this.ifMatch);
    }

    public HttpHeaders setIfMatch(String ifMatch) {
        this.ifMatch = getAsList(ifMatch);
        return this;
    }

    public final String getIfNoneMatch() {
        return (String) getFirstHeaderValue(this.ifNoneMatch);
    }

    public HttpHeaders setIfNoneMatch(String ifNoneMatch) {
        this.ifNoneMatch = getAsList(ifNoneMatch);
        return this;
    }

    public final String getIfUnmodifiedSince() {
        return (String) getFirstHeaderValue(this.ifUnmodifiedSince);
    }

    public HttpHeaders setIfUnmodifiedSince(String ifUnmodifiedSince) {
        this.ifUnmodifiedSince = getAsList(ifUnmodifiedSince);
        return this;
    }

    public final String getIfRange() {
        return (String) getFirstHeaderValue(this.ifRange);
    }

    public HttpHeaders setIfRange(String ifRange) {
        this.ifRange = getAsList(ifRange);
        return this;
    }

    public final String getLastModified() {
        return (String) getFirstHeaderValue(this.lastModified);
    }

    public HttpHeaders setLastModified(String lastModified) {
        this.lastModified = getAsList(lastModified);
        return this;
    }

    public final String getLocation() {
        return (String) getFirstHeaderValue(this.location);
    }

    public HttpHeaders setLocation(String location) {
        this.location = getAsList(location);
        return this;
    }

    public final String getMimeVersion() {
        return (String) getFirstHeaderValue(this.mimeVersion);
    }

    public HttpHeaders setMimeVersion(String mimeVersion) {
        this.mimeVersion = getAsList(mimeVersion);
        return this;
    }

    public final String getRange() {
        return (String) getFirstHeaderValue(this.range);
    }

    public HttpHeaders setRange(String range) {
        this.range = getAsList(range);
        return this;
    }

    public final String getRetryAfter() {
        return (String) getFirstHeaderValue(this.retryAfter);
    }

    public HttpHeaders setRetryAfter(String retryAfter) {
        this.retryAfter = getAsList(retryAfter);
        return this;
    }

    public final String getUserAgent() {
        return (String) getFirstHeaderValue(this.userAgent);
    }

    public HttpHeaders setUserAgent(String userAgent) {
        this.userAgent = getAsList(userAgent);
        return this;
    }

    public final String getAuthenticate() {
        return (String) getFirstHeaderValue(this.authenticate);
    }

    public final List<String> getAuthenticateAsList() {
        return this.authenticate;
    }

    public HttpHeaders setAuthenticate(String authenticate) {
        this.authenticate = getAsList(authenticate);
        return this;
    }

    public final Long getAge() {
        return (Long) getFirstHeaderValue(this.age);
    }

    public HttpHeaders setAge(Long age) {
        this.age = getAsList(age);
        return this;
    }

    public HttpHeaders setBasicAuthentication(String username, String password) {
        return setAuthorization("Basic " + Base64.encodeBase64String(StringUtils.getBytesUtf8(((String) Preconditions.checkNotNull(username)) + ":" + ((String) Preconditions.checkNotNull(password)))));
    }

    private static void addHeader(Logger logger, StringBuilder logbuf, StringBuilder curlbuf, LowLevelHttpRequest lowLevelHttpRequest, String name, Object value, Writer writer) throws IOException {
        if (value != null && !Data.isNull(value)) {
            String stringValue = toStringValue(value);
            String loggedStringValue = stringValue;
            if (("Authorization".equalsIgnoreCase(name) || "Cookie".equalsIgnoreCase(name)) && (logger == null || !logger.isLoggable(Level.ALL))) {
                loggedStringValue = "<Not Logged>";
            }
            if (logbuf != null) {
                logbuf.append(name).append(": ");
                logbuf.append(loggedStringValue);
                logbuf.append(StringUtils.LINE_SEPARATOR);
            }
            if (curlbuf != null) {
                curlbuf.append(" -H '").append(name).append(": ").append(loggedStringValue).append("'");
            }
            if (lowLevelHttpRequest != null) {
                lowLevelHttpRequest.addHeader(name, stringValue);
            }
            if (writer != null) {
                writer.write(name);
                writer.write(": ");
                writer.write(stringValue);
                writer.write("\r\n");
            }
        }
    }

    private static String toStringValue(Object headerValue) {
        return headerValue instanceof Enum ? FieldInfo.of((Enum) headerValue).getName() : headerValue.toString();
    }

    static void serializeHeaders(HttpHeaders headers, StringBuilder logbuf, StringBuilder curlbuf, Logger logger, LowLevelHttpRequest lowLevelHttpRequest) throws IOException {
        serializeHeaders(headers, logbuf, curlbuf, logger, lowLevelHttpRequest, null);
    }

    public static void serializeHeadersForMultipartRequests(HttpHeaders headers, StringBuilder logbuf, Logger logger, Writer writer) throws IOException {
        serializeHeaders(headers, logbuf, null, logger, null, writer);
    }

    static void serializeHeaders(HttpHeaders headers, StringBuilder logbuf, StringBuilder curlbuf, Logger logger, LowLevelHttpRequest lowLevelHttpRequest, Writer writer) throws IOException {
        HashSet<String> headerNames = new HashSet();
        for (Entry<String, Object> headerEntry : headers.entrySet()) {
            String name = (String) headerEntry.getKey();
            Preconditions.checkArgument(headerNames.add(name), "multiple headers of the same name (headers are case insensitive): %s", name);
            Object value = headerEntry.getValue();
            if (value != null) {
                String displayName = name;
                FieldInfo fieldInfo = headers.getClassInfo().getFieldInfo(name);
                if (fieldInfo != null) {
                    displayName = fieldInfo.getName();
                }
                Class<? extends Object> valueClass = value.getClass();
                if ((value instanceof Iterable) || valueClass.isArray()) {
                    for (Object repeatedValue : Types.iterableOf(value)) {
                        addHeader(logger, logbuf, curlbuf, lowLevelHttpRequest, displayName, repeatedValue, writer);
                    }
                } else {
                    addHeader(logger, logbuf, curlbuf, lowLevelHttpRequest, displayName, value, writer);
                }
            }
        }
        if (writer != null) {
            writer.flush();
        }
    }

    public final void fromHttpResponse(LowLevelHttpResponse response, StringBuilder logger) throws IOException {
        clear();
        ParseHeaderState state = new ParseHeaderState(this, logger);
        int headerCount = response.getHeaderCount();
        for (int i = 0; i < headerCount; i++) {
            parseHeader(response.getHeaderName(i), response.getHeaderValue(i), state);
        }
        state.finish();
    }

    private <T> T getFirstHeaderValue(List<T> internalValue) {
        return internalValue == null ? null : internalValue.get(0);
    }

    private <T> List<T> getAsList(T passedValue) {
        if (passedValue == null) {
            return null;
        }
        List<T> result = new ArrayList();
        result.add(passedValue);
        return result;
    }

    public String getFirstHeaderStringValue(String name) {
        Object value = get(name.toLowerCase());
        if (value == null) {
            return null;
        }
        Class<? extends Object> valueClass = value.getClass();
        if ((value instanceof Iterable) || valueClass.isArray()) {
            Iterator i$ = Types.iterableOf(value).iterator();
            if (i$.hasNext()) {
                return toStringValue(i$.next());
            }
        }
        return toStringValue(value);
    }

    public List<String> getHeaderStringValues(String name) {
        Object value = get(name.toLowerCase());
        if (value == null) {
            return Collections.emptyList();
        }
        Class<? extends Object> valueClass = value.getClass();
        if (!(value instanceof Iterable) && !valueClass.isArray()) {
            return Collections.singletonList(toStringValue(value));
        }
        List<String> values = new ArrayList();
        for (Object repeatedValue : Types.iterableOf(value)) {
            values.add(toStringValue(repeatedValue));
        }
        return Collections.unmodifiableList(values);
    }

    public final void fromHttpHeaders(HttpHeaders headers) {
        try {
            ParseHeaderState state = new ParseHeaderState(this, null);
            serializeHeaders(headers, null, null, null, new HeaderParsingFakeLevelHttpRequest(this, state));
            state.finish();
        } catch (IOException ex) {
            throw Throwables.propagate(ex);
        }
    }

    void parseHeader(String headerName, String headerValue, ParseHeaderState state) {
        List<Type> context = state.context;
        ClassInfo classInfo = state.classInfo;
        ArrayValueMap arrayValueMap = state.arrayValueMap;
        StringBuilder logger = state.logger;
        if (logger != null) {
            logger.append(headerName + ": " + headerValue).append(StringUtils.LINE_SEPARATOR);
        }
        FieldInfo fieldInfo = classInfo.getFieldInfo(headerName);
        if (fieldInfo != null) {
            Type type = Data.resolveWildcardTypeOrTypeVariable(context, fieldInfo.getGenericType());
            if (Types.isArray(type)) {
                Class rawArrayComponentType = Types.getRawArrayComponentType(context, Types.getArrayComponentType(type));
                arrayValueMap.put(fieldInfo.getField(), rawArrayComponentType, parseValue(rawArrayComponentType, context, headerValue));
                return;
            } else if (Types.isAssignableToOrFrom(Types.getRawArrayComponentType(context, type), Iterable.class)) {
                Collection<Object> collection = (Collection) fieldInfo.getValue(this);
                if (collection == null) {
                    collection = Data.newCollectionInstance(type);
                    fieldInfo.setValue(this, collection);
                }
                collection.add(parseValue(type == Object.class ? null : Types.getIterableParameter(type), context, headerValue));
                return;
            } else {
                fieldInfo.setValue(this, parseValue(type, context, headerValue));
                return;
            }
        }
        ArrayList<String> listValue = (ArrayList) get(headerName);
        if (listValue == null) {
            listValue = new ArrayList();
            set(headerName, (Object) listValue);
        }
        listValue.add(headerValue);
    }

    private static Object parseValue(Type valueType, List<Type> context, String value) {
        return Data.parsePrimitiveValue(Data.resolveWildcardTypeOrTypeVariable(context, valueType), value);
    }
}
