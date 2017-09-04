package com.google.api.client.http.javanet;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

public class DefaultConnectionFactory implements ConnectionFactory {
    private final Proxy proxy;

    public DefaultConnectionFactory() {
        this(null);
    }

    public DefaultConnectionFactory(Proxy proxy) {
        this.proxy = proxy;
    }

    public HttpURLConnection openConnection(URL url) throws IOException {
        return (HttpURLConnection) (this.proxy == null ? url.openConnection() : url.openConnection(this.proxy));
    }
}
