package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.listeners.ProgressListener;
import com.box.androidsdk.content.requests.BoxRequest.Methods;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class BoxHttpRequest {
    protected final ProgressListener mListener;
    protected final HttpURLConnection mUrlConnection;

    public BoxHttpRequest(URL url, Methods method, ProgressListener listener) throws IOException {
        this.mUrlConnection = (HttpURLConnection) url.openConnection();
        this.mUrlConnection.setRequestMethod(method.toString());
        this.mListener = listener;
    }

    public BoxHttpRequest addHeader(String key, String value) {
        this.mUrlConnection.addRequestProperty(key, value);
        return this;
    }

    public BoxHttpRequest setBody(InputStream body) throws IOException {
        this.mUrlConnection.setDoOutput(true);
        OutputStream output = this.mUrlConnection.getOutputStream();
        int b = body.read();
        while (b != -1) {
            output.write(b);
            b = body.read();
        }
        output.close();
        return this;
    }

    public HttpURLConnection getUrlConnection() {
        return this.mUrlConnection;
    }
}
