package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.BoxException;
import com.box.androidsdk.content.listeners.ProgressListener;
import com.box.androidsdk.content.utils.ProgressInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.zip.GZIPInputStream;

public class BoxHttpResponse {
    private static final int BUFFER_SIZE = 8192;
    private String mBodyString;
    protected final HttpURLConnection mConnection;
    private String mContentEncoding;
    protected String mContentType;
    private InputStream mInputStream = null;
    protected int mResponseCode;
    private InputStream rawInputStream;

    public BoxHttpResponse(HttpURLConnection connection) {
        this.mConnection = connection;
    }

    public void open() throws IOException {
        this.mConnection.connect();
        this.mContentType = this.mConnection.getContentType();
        this.mResponseCode = this.mConnection.getResponseCode();
        this.mContentEncoding = this.mConnection.getContentEncoding();
    }

    public int getResponseCode() {
        return this.mResponseCode;
    }

    public int getContentLength() {
        return this.mConnection.getContentLength();
    }

    public String getContentType() {
        return this.mContentType;
    }

    public InputStream getBody() throws BoxException {
        return getBody(null);
    }

    public InputStream getBody(ProgressListener listener) throws BoxException {
        if (this.mInputStream != null) {
            return this.mInputStream;
        }
        String contentEncoding = this.mConnection.getContentEncoding();
        try {
            if (this.rawInputStream == null) {
                this.rawInputStream = this.mConnection.getInputStream();
            }
            if (listener == null) {
                this.mInputStream = this.rawInputStream;
            } else {
                this.mInputStream = new ProgressInputStream(this.rawInputStream, listener, (long) getContentLength());
            }
            if (contentEncoding != null && contentEncoding.equalsIgnoreCase("gzip")) {
                this.mInputStream = new GZIPInputStream(this.mInputStream);
            }
            return this.mInputStream;
        } catch (Throwable e) {
            throw new BoxException("Couldn't connect to the Box API due to a network error.", e);
        }
    }

    public void disconnect() throws BoxException {
        try {
            if (this.rawInputStream == null) {
                this.rawInputStream = this.mConnection.getInputStream();
            }
            byte[] buffer = new byte[BUFFER_SIZE];
            int n = this.rawInputStream.read(buffer);
            while (n != -1) {
                n = this.rawInputStream.read(buffer);
            }
            this.rawInputStream.close();
            if (this.mInputStream != null) {
                this.mInputStream.close();
            }
        } catch (Throwable e) {
            throw new BoxException("Couldn't finish closing the connection to the Box API due to a network error or because the stream was already closed.", e);
        }
    }

    public String getStringBody() throws BoxException {
        if (this.mBodyString != null) {
            return this.mBodyString;
        }
        try {
            InputStream stream;
            if (isErrorCode(this.mResponseCode)) {
                stream = this.mConnection.getErrorStream();
            } else {
                stream = this.mConnection.getInputStream();
            }
            this.mBodyString = readStream(stream);
            return this.mBodyString;
        } catch (Throwable e) {
            throw new BoxException("Unable to get string body", e);
        }
    }

    private String readStream(InputStream inputStream) throws IOException, BoxException {
        if (inputStream == null) {
            return null;
        }
        InputStream stream;
        if (this.mContentEncoding == null || !this.mContentEncoding.equalsIgnoreCase("gzip")) {
            stream = inputStream;
        } else {
            stream = new GZIPInputStream(inputStream);
        }
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[BUFFER_SIZE];
        try {
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            for (int read = reader.read(buffer, 0, BUFFER_SIZE); read != -1; read = reader.read(buffer, 0, BUFFER_SIZE)) {
                builder.append(buffer, 0, read);
            }
            reader.close();
            return builder.toString();
        } catch (Throwable e) {
            throw new BoxException("Unable to read stream", e);
        }
    }

    public HttpURLConnection getHttpURLConnection() {
        return this.mConnection;
    }

    private static boolean isErrorCode(int responseCode) {
        return responseCode >= 400;
    }
}
