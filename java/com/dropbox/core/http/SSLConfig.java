package com.dropbox.core.http;

import com.dropbox.core.util.IOUtil;
import com.dropbox.core.util.LangUtil;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class SSLConfig {
    private static final HashSet<String> ALLOWED_CIPHER_SUITES = new HashSet(Arrays.asList(new String[]{"TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384", "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", "TLS_ECDHE_RSA_WITH_RC4_128_SHA", "TLS_DHE_RSA_WITH_AES_256_GCM_SHA384", "TLS_DHE_RSA_WITH_AES_256_CBC_SHA256", "TLS_DHE_RSA_WITH_AES_256_CBC_SHA", "TLS_DHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_DHE_RSA_WITH_AES_128_CBC_SHA256", "TLS_DHE_RSA_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_GCM_SHA384", "TLS_RSA_WITH_AES_256_CBC_SHA256", "TLS_RSA_WITH_AES_256_CBC_SHA", "TLS_RSA_WITH_AES_128_GCM_SHA256", "TLS_RSA_WITH_AES_128_CBC_SHA256", "TLS_RSA_WITH_AES_128_CBC_SHA", "ECDHE-RSA-AES256-GCM-SHA384", "ECDHE-RSA-AES256-SHA384", "ECDHE-RSA-AES256-SHA", "ECDHE-RSA-AES128-GCM-SHA256", "ECDHE-RSA-AES128-SHA256", "ECDHE-RSA-AES128-SHA", "ECDHE-RSA-RC4-SHA", "DHE-RSA-AES256-GCM-SHA384", "DHE-RSA-AES256-SHA256", "DHE-RSA-AES256-SHA", "DHE-RSA-AES128-GCM-SHA256", "DHE-RSA-AES128-SHA256", "DHE-RSA-AES128-SHA", "AES256-GCM-SHA384", "AES256-SHA256", "AES256-SHA", "AES128-GCM-SHA256", "AES128-SHA256", "AES128-SHA"}));
    private static CipherSuiteFilterationResults CACHED_CIPHER_SUITE_FILTERATION_RESULTS = null;
    private static final int MAX_CERT_LENGTH = 10240;
    private static final String[] PROTOCOL_LIST_TLS_V1 = new String[]{"TLSv1"};
    private static final String[] PROTOCOL_LIST_TLS_V1_0 = new String[]{"TLSv1.0"};
    private static final String[] PROTOCOL_LIST_TLS_V1_2 = new String[]{"TLSv1.2"};
    private static final String ROOT_CERTS_RESOURCE = "/trusted-certs.raw";
    private static final SSLSocketFactory SSL_SOCKET_FACTORY = createSSLSocketFactory();
    private static final X509TrustManager TRUST_MANAGER = createTrustManager();

    private static final class CipherSuiteFilterationResults {
        private final String[] enabled;
        private final String[] supported;

        public CipherSuiteFilterationResults(String[] supported, String[] enabled) {
            this.supported = supported;
            this.enabled = enabled;
        }

        public String[] getSupported() {
            return this.supported;
        }

        public String[] getEnabled() {
            return this.enabled;
        }
    }

    public static final class LoadException extends Exception {
        private static final long serialVersionUID = 0;

        public LoadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private static final class SSLSocketFactoryWrapper extends SSLSocketFactory {
        private final SSLSocketFactory mBase;

        public SSLSocketFactoryWrapper(SSLSocketFactory base) {
            this.mBase = base;
        }

        public String[] getDefaultCipherSuites() {
            return this.mBase.getDefaultCipherSuites();
        }

        public String[] getSupportedCipherSuites() {
            return this.mBase.getSupportedCipherSuites();
        }

        public Socket createSocket(String host, int port) throws IOException {
            Socket socket = this.mBase.createSocket(host, port);
            SSLConfig.limitProtocolsAndCiphers((SSLSocket) socket);
            return socket;
        }

        public Socket createSocket(InetAddress host, int port) throws IOException {
            Socket socket = this.mBase.createSocket(host, port);
            SSLConfig.limitProtocolsAndCiphers((SSLSocket) socket);
            return socket;
        }

        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
            Socket socket = this.mBase.createSocket(host, port, localHost, localPort);
            SSLConfig.limitProtocolsAndCiphers((SSLSocket) socket);
            return socket;
        }

        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
            Socket socket = this.mBase.createSocket(address, port, localAddress, localPort);
            SSLConfig.limitProtocolsAndCiphers((SSLSocket) socket);
            return socket;
        }

        public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
            Socket socket = this.mBase.createSocket(s, host, port, autoClose);
            SSLConfig.limitProtocolsAndCiphers((SSLSocket) socket);
            return socket;
        }
    }

    public static void apply(HttpsURLConnection conn) throws SSLException {
        conn.setSSLSocketFactory(SSL_SOCKET_FACTORY);
    }

    public static X509TrustManager getTrustManager() {
        return TRUST_MANAGER;
    }

    public static SSLSocketFactory getSSLSocketFactory() {
        return SSL_SOCKET_FACTORY;
    }

    private static void limitProtocolsAndCiphers(SSLSocket socket) throws SSLException {
        String[] supportedProtocols = socket.getSupportedProtocols();
        int length = supportedProtocols.length;
        int i = 0;
        while (i < length) {
            String protocol = supportedProtocols[i];
            if (protocol.equals("TLSv1.2")) {
                socket.setEnabledProtocols(PROTOCOL_LIST_TLS_V1_2);
            } else if (protocol.equals("TLSv1.0")) {
                socket.setEnabledProtocols(PROTOCOL_LIST_TLS_V1_0);
            } else if (protocol.equals("TLSv1")) {
                socket.setEnabledProtocols(PROTOCOL_LIST_TLS_V1);
            } else {
                i++;
            }
            socket.setEnabledCipherSuites(getFilteredCipherSuites(socket.getSupportedCipherSuites()));
            return;
        }
        throw new SSLException("Socket doesn't support protocols \"TLSv1.2\", \"TLSv1.0\" or \"TLSv1\".");
    }

    private static String[] getFilteredCipherSuites(String[] supportedCipherSuites) {
        CipherSuiteFilterationResults cached = CACHED_CIPHER_SUITE_FILTERATION_RESULTS;
        if (cached != null && Arrays.equals(cached.supported, supportedCipherSuites)) {
            return cached.enabled;
        }
        ArrayList<String> enabled = new ArrayList(ALLOWED_CIPHER_SUITES.size());
        for (String supported : supportedCipherSuites) {
            if (ALLOWED_CIPHER_SUITES.contains(supported)) {
                enabled.add(supported);
            }
        }
        String[] filteredArray = (String[]) enabled.toArray(new String[enabled.size()]);
        CACHED_CIPHER_SUITE_FILTERATION_RESULTS = new CipherSuiteFilterationResults(supportedCipherSuites, filteredArray);
        return filteredArray;
    }

    private static X509TrustManager createTrustManager() {
        return createTrustManager(loadKeyStore(ROOT_CERTS_RESOURCE));
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        return new SSLSocketFactoryWrapper(createSSLContext(new TrustManager[]{TRUST_MANAGER}).getSocketFactory());
    }

    private static SSLContext createSSLContext(TrustManager[] trustManagers) {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            try {
                sslContext.init(null, trustManagers, null);
                return sslContext;
            } catch (KeyManagementException ex) {
                throw LangUtil.mkAssert("Couldn't initialize SSLContext", ex);
            }
        } catch (NoSuchAlgorithmException ex2) {
            throw LangUtil.mkAssert("Couldn't create SSLContext", ex2);
        }
    }

    private static X509TrustManager createTrustManager(KeyStore trustedCertKeyStore) {
        try {
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
            try {
                tmf.init(trustedCertKeyStore);
                TrustManager[] trustManagers = tmf.getTrustManagers();
                if (trustManagers.length != 1) {
                    throw new AssertionError("More than 1 TrustManager created.");
                } else if (trustManagers[0] instanceof X509TrustManager) {
                    return (X509TrustManager) trustManagers[0];
                } else {
                    throw new AssertionError("TrustManager not of type X509: " + trustManagers[0].getClass());
                }
            } catch (KeyStoreException ex) {
                throw LangUtil.mkAssert("Unable to initialize TrustManagerFactory with key store", ex);
            }
        } catch (NoSuchAlgorithmException ex2) {
            throw LangUtil.mkAssert("Unable to create TrustManagerFactory", ex2);
        }
    }

    private static KeyStore loadKeyStore(String certFileResource) {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, new char[0]);
            InputStream in = SSLConfig.class.getResourceAsStream(certFileResource);
            if (in == null) {
                throw new AssertionError("Couldn't find resource \"" + certFileResource + "\"");
            }
            try {
                loadKeyStore(keyStore, in);
                IOUtil.closeInput(in);
                return keyStore;
            } catch (KeyStoreException ex) {
                throw LangUtil.mkAssert("Error loading from \"" + certFileResource + "\"", ex);
            } catch (LoadException ex2) {
                throw LangUtil.mkAssert("Error loading from \"" + certFileResource + "\"", ex2);
            } catch (IOException ex3) {
                throw LangUtil.mkAssert("Error loading from \"" + certFileResource + "\"", ex3);
            } catch (Throwable th) {
                IOUtil.closeInput(in);
            }
        } catch (KeyStoreException ex4) {
            throw LangUtil.mkAssert("Couldn't initialize KeyStore", ex4);
        } catch (CertificateException ex5) {
            throw LangUtil.mkAssert("Couldn't initialize KeyStore", ex5);
        } catch (NoSuchAlgorithmException ex6) {
            throw LangUtil.mkAssert("Couldn't initialize KeyStore", ex6);
        } catch (IOException ex32) {
            throw LangUtil.mkAssert("Couldn't initialize KeyStore", ex32);
        }
    }

    private static void loadKeyStore(KeyStore keyStore, InputStream in) throws IOException, LoadException, KeyStoreException {
        try {
            try {
                for (X509Certificate cert : deserializeCertificates(CertificateFactory.getInstance("X.509"), in)) {
                    try {
                        keyStore.setCertificateEntry(cert.getSubjectX500Principal().getName(), cert);
                    } catch (KeyStoreException ex) {
                        throw new LoadException("Error loading certificate: " + ex.getMessage(), ex);
                    }
                }
            } catch (CertificateException ex2) {
                throw new LoadException("Error loading certificate: " + ex2.getMessage(), ex2);
            }
        } catch (CertificateException ex22) {
            throw LangUtil.mkAssert("Couldn't initialize X.509 CertificateFactory", ex22);
        }
    }

    private static List<X509Certificate> deserializeCertificates(CertificateFactory x509CertFactory, InputStream in) throws IOException, LoadException, CertificateException {
        List<X509Certificate> certs = new ArrayList();
        DataInputStream din = new DataInputStream(in);
        byte[] data = new byte[MAX_CERT_LENGTH];
        while (true) {
            int length = din.readUnsignedShort();
            if (length == 0) {
                break;
            } else if (length > MAX_CERT_LENGTH) {
                throw new LoadException("Invalid length for certificate entry: " + length, null);
            } else {
                din.readFully(data, 0, length);
                certs.add((X509Certificate) x509CertFactory.generateCertificate(new ByteArrayInputStream(data, 0, length)));
            }
        }
        if (din.read() < 0) {
            return certs;
        }
        throw new LoadException("Found data after after zero-length header.", null);
    }
}
