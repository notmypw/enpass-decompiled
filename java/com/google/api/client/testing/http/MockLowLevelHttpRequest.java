package com.google.api.client.testing.http;

import com.github.clans.fab.BuildConfig;
import com.google.api.client.http.HttpMediaType;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Charsets;
import com.google.api.client.util.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

@Beta
public class MockLowLevelHttpRequest extends LowLevelHttpRequest {
    private final Map<String, List<String>> headersMap = new HashMap();
    private MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
    private String url;

    public MockLowLevelHttpRequest(String url) {
        this.url = url;
    }

    public void addHeader(String name, String value) throws IOException {
        name = name.toLowerCase();
        List<String> values = (List) this.headersMap.get(name);
        if (values == null) {
            values = new ArrayList();
            this.headersMap.put(name, values);
        }
        values.add(value);
    }

    public LowLevelHttpResponse execute() throws IOException {
        return this.response;
    }

    public String getUrl() {
        return this.url;
    }

    public Map<String, List<String>> getHeaders() {
        return Collections.unmodifiableMap(this.headersMap);
    }

    public String getFirstHeaderValue(String name) {
        List<String> values = (List) this.headersMap.get(name.toLowerCase());
        return values == null ? null : (String) values.get(0);
    }

    public List<String> getHeaderValues(String name) {
        List<String> values = (List) this.headersMap.get(name.toLowerCase());
        return values == null ? Collections.emptyList() : Collections.unmodifiableList(values);
    }

    public MockLowLevelHttpRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getContentAsString() throws IOException {
        if (getStreamingContent() == null) {
            return BuildConfig.FLAVOR;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        getStreamingContent().writeTo(out);
        String contentEncoding = getContentEncoding();
        if (contentEncoding != null && contentEncoding.contains("gzip")) {
            InputStream contentInputStream = new GZIPInputStream(new ByteArrayInputStream(out.toByteArray()));
            out = new ByteArrayOutputStream();
            IOUtils.copy(contentInputStream, out);
        }
        String contentType = getContentType();
        HttpMediaType mediaType = contentType != null ? new HttpMediaType(contentType) : null;
        Charset charset = (mediaType == null || mediaType.getCharsetParameter() == null) ? Charsets.ISO_8859_1 : mediaType.getCharsetParameter();
        return out.toString(charset.name());
    }

    public MockLowLevelHttpResponse getResponse() {
        return this.response;
    }

    public MockLowLevelHttpRequest setResponse(MockLowLevelHttpResponse response) {
        this.response = response;
        return this;
    }
}
