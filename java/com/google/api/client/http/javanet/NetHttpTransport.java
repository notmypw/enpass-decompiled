package com.google.api.client.http.javanet;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.SecurityUtils;
import com.google.api.client.util.SslUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Arrays;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public final class NetHttpTransport extends HttpTransport {
    private static final String[] SUPPORTED_METHODS = new String[]{HttpMethods.DELETE, HttpMethods.GET, HttpMethods.HEAD, HttpMethods.OPTIONS, HttpMethods.POST, HttpMethods.PUT, HttpMethods.TRACE};
    private final ConnectionFactory connectionFactory;
    private final HostnameVerifier hostnameVerifier;
    private final SSLSocketFactory sslSocketFactory;

    public static final class Builder {
        private ConnectionFactory connectionFactory;
        private HostnameVerifier hostnameVerifier;
        private Proxy proxy;
        private SSLSocketFactory sslSocketFactory;

        public Builder setProxy(Proxy proxy) {
            this.proxy = proxy;
            return this;
        }

        public Builder setConnectionFactory(ConnectionFactory connectionFactory) {
            this.connectionFactory = connectionFactory;
            return this;
        }

        public Builder trustCertificatesFromJavaKeyStore(InputStream keyStoreStream, String storePass) throws GeneralSecurityException, IOException {
            KeyStore trustStore = SecurityUtils.getJavaKeyStore();
            SecurityUtils.loadKeyStore(trustStore, keyStoreStream, storePass);
            return trustCertificates(trustStore);
        }

        public Builder trustCertificatesFromStream(InputStream certificateStream) throws GeneralSecurityException, IOException {
            KeyStore trustStore = SecurityUtils.getJavaKeyStore();
            trustStore.load(null, null);
            SecurityUtils.loadKeyStoreFromCertificates(trustStore, SecurityUtils.getX509CertificateFactory(), certificateStream);
            return trustCertificates(trustStore);
        }

        public Builder trustCertificates(KeyStore trustStore) throws GeneralSecurityException {
            SSLContext sslContext = SslUtils.getTlsSslContext();
            SslUtils.initSslContext(sslContext, trustStore, SslUtils.getPkixTrustManagerFactory());
            return setSslSocketFactory(sslContext.getSocketFactory());
        }

        @Beta
        public Builder doNotValidateCertificate() throws GeneralSecurityException {
            this.hostnameVerifier = SslUtils.trustAllHostnameVerifier();
            this.sslSocketFactory = SslUtils.trustAllSSLContext().getSocketFactory();
            return this;
        }

        public SSLSocketFactory getSslSocketFactory() {
            return this.sslSocketFactory;
        }

        public Builder setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
            this.sslSocketFactory = sslSocketFactory;
            return this;
        }

        public HostnameVerifier getHostnameVerifier() {
            return this.hostnameVerifier;
        }

        public Builder setHostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.hostnameVerifier = hostnameVerifier;
            return this;
        }

        public NetHttpTransport build() {
            return this.proxy == null ? new NetHttpTransport(this.connectionFactory, this.sslSocketFactory, this.hostnameVerifier) : new NetHttpTransport(this.proxy, this.sslSocketFactory, this.hostnameVerifier);
        }
    }

    static {
        Arrays.sort(SUPPORTED_METHODS);
    }

    public NetHttpTransport() {
        this((ConnectionFactory) null, null, null);
    }

    NetHttpTransport(Proxy proxy, SSLSocketFactory sslSocketFactory, HostnameVerifier hostnameVerifier) {
        this(new DefaultConnectionFactory(proxy), sslSocketFactory, hostnameVerifier);
    }

    NetHttpTransport(ConnectionFactory connectionFactory, SSLSocketFactory sslSocketFactory, HostnameVerifier hostnameVerifier) {
        if (connectionFactory == null) {
            connectionFactory = new DefaultConnectionFactory();
        }
        this.connectionFactory = connectionFactory;
        this.sslSocketFactory = sslSocketFactory;
        this.hostnameVerifier = hostnameVerifier;
    }

    public boolean supportsMethod(String method) {
        return Arrays.binarySearch(SUPPORTED_METHODS, method) >= 0;
    }

    protected NetHttpRequest buildRequest(String method, String url) throws IOException {
        Preconditions.checkArgument(supportsMethod(method), "HTTP method %s not supported", method);
        HttpURLConnection connection = this.connectionFactory.openConnection(new URL(url));
        connection.setRequestMethod(method);
        if (connection instanceof HttpsURLConnection) {
            HttpsURLConnection secureConnection = (HttpsURLConnection) connection;
            if (this.hostnameVerifier != null) {
                secureConnection.setHostnameVerifier(this.hostnameVerifier);
            }
            if (this.sslSocketFactory != null) {
                secureConnection.setSSLSocketFactory(this.sslSocketFactory);
            }
        }
        return new NetHttpRequest(connection);
    }
}
