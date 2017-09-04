package com.google.api.client.http.apache;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.SecurityUtils;
import com.google.api.client.util.SslUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProxySelector;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpHost;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.ProxySelectorRoutePlanner;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public final class ApacheHttpTransport extends HttpTransport {
    private final HttpClient httpClient;

    public static final class Builder {
        private HttpParams params = ApacheHttpTransport.newDefaultHttpParams();
        private ProxySelector proxySelector = ProxySelector.getDefault();
        private SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();

        public Builder setProxy(HttpHost proxy) {
            ConnRouteParams.setDefaultProxy(this.params, proxy);
            if (proxy != null) {
                this.proxySelector = null;
            }
            return this;
        }

        public Builder setProxySelector(ProxySelector proxySelector) {
            this.proxySelector = proxySelector;
            if (proxySelector != null) {
                ConnRouteParams.setDefaultProxy(this.params, null);
            }
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
            return setSocketFactory(new SSLSocketFactoryExtension(sslContext));
        }

        @Beta
        public Builder doNotValidateCertificate() throws GeneralSecurityException {
            this.socketFactory = new SSLSocketFactoryExtension(SslUtils.trustAllSSLContext());
            this.socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            return this;
        }

        public Builder setSocketFactory(SSLSocketFactory socketFactory) {
            this.socketFactory = (SSLSocketFactory) Preconditions.checkNotNull(socketFactory);
            return this;
        }

        public SSLSocketFactory getSSLSocketFactory() {
            return this.socketFactory;
        }

        public HttpParams getHttpParams() {
            return this.params;
        }

        public ApacheHttpTransport build() {
            return new ApacheHttpTransport(ApacheHttpTransport.newDefaultHttpClient(this.socketFactory, this.params, this.proxySelector));
        }
    }

    public ApacheHttpTransport() {
        this(newDefaultHttpClient());
    }

    public ApacheHttpTransport(HttpClient httpClient) {
        this.httpClient = httpClient;
        HttpParams params = httpClient.getParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        params.setBooleanParameter("http.protocol.handle-redirects", false);
    }

    public static DefaultHttpClient newDefaultHttpClient() {
        return newDefaultHttpClient(SSLSocketFactory.getSocketFactory(), newDefaultHttpParams(), ProxySelector.getDefault());
    }

    static HttpParams newDefaultHttpParams() {
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setStaleCheckingEnabled(params, false);
        HttpConnectionParams.setSocketBufferSize(params, 8192);
        ConnManagerParams.setMaxTotalConnections(params, HttpStatusCodes.STATUS_CODE_OK);
        ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRouteBean(20));
        return params;
    }

    static DefaultHttpClient newDefaultHttpClient(SSLSocketFactory socketFactory, HttpParams params, ProxySelector proxySelector) {
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        registry.register(new Scheme("https", socketFactory, 443));
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(params, registry), params);
        defaultHttpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        if (proxySelector != null) {
            defaultHttpClient.setRoutePlanner(new ProxySelectorRoutePlanner(registry, proxySelector));
        }
        return defaultHttpClient;
    }

    public boolean supportsMethod(String method) {
        return true;
    }

    protected ApacheHttpRequest buildRequest(String method, String url) {
        HttpRequestBase requestBase;
        if (method.equals(HttpMethods.DELETE)) {
            requestBase = new HttpDelete(url);
        } else if (method.equals(HttpMethods.GET)) {
            requestBase = new HttpGet(url);
        } else if (method.equals(HttpMethods.HEAD)) {
            requestBase = new HttpHead(url);
        } else if (method.equals(HttpMethods.POST)) {
            requestBase = new HttpPost(url);
        } else if (method.equals(HttpMethods.PUT)) {
            requestBase = new HttpPut(url);
        } else if (method.equals(HttpMethods.TRACE)) {
            requestBase = new HttpTrace(url);
        } else if (method.equals(HttpMethods.OPTIONS)) {
            requestBase = new HttpOptions(url);
        } else {
            requestBase = new HttpExtensionMethod(method, url);
        }
        return new ApacheHttpRequest(this.httpClient, requestBase);
    }

    public void shutdown() {
        this.httpClient.getConnectionManager().shutdown();
    }

    public HttpClient getHttpClient() {
        return this.httpClient;
    }
}
