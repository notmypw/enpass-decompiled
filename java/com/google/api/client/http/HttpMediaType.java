package com.google.api.client.http;

import com.google.api.client.util.Preconditions;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HttpMediaType {
    private static final Pattern FULL_MEDIA_TYPE_REGEX;
    private static final Pattern PARAMETER_REGEX;
    private static final Pattern TOKEN_REGEX = Pattern.compile("[\\p{ASCII}&&[^\\p{Cntrl} ;/=\\[\\]\\(\\)\\<\\>\\@\\,\\:\\\"\\?\\=]]+");
    private static final Pattern TYPE_REGEX = Pattern.compile("[\\w!#$&.+\\-\\^_]+|[*]");
    private String cachedBuildResult;
    private final SortedMap<String, String> parameters = new TreeMap();
    private String subType = "octet-stream";
    private String type = "application";

    static {
        String typeOrKey = "[^\\s/=;\"]+";
        FULL_MEDIA_TYPE_REGEX = Pattern.compile("\\s*(" + typeOrKey + ")/(" + typeOrKey + ")" + "\\s*(" + ";.*" + ")?", 32);
        PARAMETER_REGEX = Pattern.compile("\\s*;\\s*(" + typeOrKey + ")" + "=(" + ("\"([^\"]*)\"" + "|" + "[^\\s;\"]*") + ")");
    }

    public HttpMediaType(String type, String subType) {
        setType(type);
        setSubType(subType);
    }

    public HttpMediaType(String mediaType) {
        fromString(mediaType);
    }

    public HttpMediaType setType(String type) {
        Preconditions.checkArgument(TYPE_REGEX.matcher(type).matches(), "Type contains reserved characters");
        this.type = type;
        this.cachedBuildResult = null;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public HttpMediaType setSubType(String subType) {
        Preconditions.checkArgument(TYPE_REGEX.matcher(subType).matches(), "Subtype contains reserved characters");
        this.subType = subType;
        this.cachedBuildResult = null;
        return this;
    }

    public String getSubType() {
        return this.subType;
    }

    private HttpMediaType fromString(String combinedType) {
        Matcher matcher = FULL_MEDIA_TYPE_REGEX.matcher(combinedType);
        Preconditions.checkArgument(matcher.matches(), "Type must be in the 'maintype/subtype; parameter=value' format");
        setType(matcher.group(1));
        setSubType(matcher.group(2));
        String params = matcher.group(3);
        if (params != null) {
            matcher = PARAMETER_REGEX.matcher(params);
            while (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(3);
                if (value == null) {
                    value = matcher.group(2);
                }
                setParameter(key, value);
            }
        }
        return this;
    }

    public HttpMediaType setParameter(String name, String value) {
        if (value == null) {
            removeParameter(name);
        } else {
            Preconditions.checkArgument(TOKEN_REGEX.matcher(name).matches(), "Name contains reserved characters");
            this.cachedBuildResult = null;
            this.parameters.put(name.toLowerCase(), value);
        }
        return this;
    }

    public String getParameter(String name) {
        return (String) this.parameters.get(name.toLowerCase());
    }

    public HttpMediaType removeParameter(String name) {
        this.cachedBuildResult = null;
        this.parameters.remove(name.toLowerCase());
        return this;
    }

    public void clearParameters() {
        this.cachedBuildResult = null;
        this.parameters.clear();
    }

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(this.parameters);
    }

    static boolean matchesToken(String value) {
        return TOKEN_REGEX.matcher(value).matches();
    }

    private static String quoteString(String unquotedString) {
        return "\"" + unquotedString.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }

    public String build() {
        if (this.cachedBuildResult != null) {
            return this.cachedBuildResult;
        }
        StringBuilder str = new StringBuilder();
        str.append(this.type);
        str.append('/');
        str.append(this.subType);
        if (this.parameters != null) {
            for (Entry<String, String> entry : this.parameters.entrySet()) {
                String value = (String) entry.getValue();
                str.append("; ");
                str.append((String) entry.getKey());
                str.append("=");
                if (!matchesToken(value)) {
                    value = quoteString(value);
                }
                str.append(value);
            }
        }
        this.cachedBuildResult = str.toString();
        return this.cachedBuildResult;
    }

    public String toString() {
        return build();
    }

    public boolean equalsIgnoreParameters(HttpMediaType mediaType) {
        return mediaType != null && getType().equalsIgnoreCase(mediaType.getType()) && getSubType().equalsIgnoreCase(mediaType.getSubType());
    }

    public static boolean equalsIgnoreParameters(String mediaTypeA, String mediaTypeB) {
        return (mediaTypeA == null && mediaTypeB == null) || !(mediaTypeA == null || mediaTypeB == null || !new HttpMediaType(mediaTypeA).equalsIgnoreParameters(new HttpMediaType(mediaTypeB)));
    }

    public HttpMediaType setCharsetParameter(Charset charset) {
        setParameter("charset", charset == null ? null : charset.name());
        return this;
    }

    public Charset getCharsetParameter() {
        String value = getParameter("charset");
        return value == null ? null : Charset.forName(value);
    }

    public int hashCode() {
        return build().hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof HttpMediaType)) {
            return false;
        }
        HttpMediaType otherType = (HttpMediaType) obj;
        if (equalsIgnoreParameters(otherType) && this.parameters.equals(otherType.parameters)) {
            return true;
        }
        return false;
    }
}
