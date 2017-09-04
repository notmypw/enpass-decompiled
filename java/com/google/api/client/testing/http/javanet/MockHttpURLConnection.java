package com.google.api.client.testing.http.javanet;

import com.google.api.client.util.Beta;
import com.google.api.client.util.Preconditions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Beta
public class MockHttpURLConnection extends HttpURLConnection {
    @Deprecated
    public static final byte[] ERROR_BUF = new byte[5];
    @Deprecated
    public static final byte[] INPUT_BUF = new byte[1];
    private boolean doOutputCalled;
    private InputStream errorStream = null;
    private Map<String, List<String>> headers = new LinkedHashMap();
    private InputStream inputStream = null;
    private OutputStream outputStream = new ByteArrayOutputStream(0);

    public MockHttpURLConnection(URL u) {
        super(u);
    }

    public void disconnect() {
    }

    public boolean usingProxy() {
        return false;
    }

    public void connect() throws IOException {
    }

    public int getResponseCode() throws IOException {
        return this.responseCode;
    }

    public void setDoOutput(boolean dooutput) {
        this.doOutputCalled = true;
    }

    public OutputStream getOutputStream() throws IOException {
        if (this.outputStream != null) {
            return this.outputStream;
        }
        return super.getOutputStream();
    }

    public final boolean doOutputCalled() {
        return this.doOutputCalled;
    }

    public MockHttpURLConnection setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        return this;
    }

    public MockHttpURLConnection setResponseCode(int responseCode) {
        Preconditions.checkArgument(responseCode >= -1);
        this.responseCode = responseCode;
        return this;
    }

    public MockHttpURLConnection addHeader(String name, String value) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(value);
        if (this.headers.containsKey(name)) {
            ((List) this.headers.get(name)).add(value);
        } else {
            List<String> values = new ArrayList();
            values.add(value);
            this.headers.put(name, values);
        }
        return this;
    }

    public MockHttpURLConnection setInputStream(InputStream is) {
        Preconditions.checkNotNull(is);
        if (this.inputStream == null) {
            this.inputStream = is;
        }
        return this;
    }

    public MockHttpURLConnection setErrorStream(InputStream is) {
        Preconditions.checkNotNull(is);
        if (this.errorStream == null) {
            this.errorStream = is;
        }
        return this;
    }

    public InputStream getInputStream() throws IOException {
        if (this.responseCode < 400) {
            return this.inputStream;
        }
        throw new IOException();
    }

    public InputStream getErrorStream() {
        return this.errorStream;
    }

    public Map<String, List<String>> getHeaderFields() {
        return this.headers;
    }

    public String getHeaderField(String name) {
        List<String> values = (List) this.headers.get(name);
        return values == null ? null : (String) values.get(0);
    }
}
