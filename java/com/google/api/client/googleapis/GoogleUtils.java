package com.google.api.client.googleapis;

import com.github.clans.fab.BuildConfig;
import com.google.api.client.util.SecurityUtils;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

public final class GoogleUtils {
    public static final Integer BUGFIX_VERSION = Integer.valueOf(0);
    public static final Integer MAJOR_VERSION = Integer.valueOf(1);
    public static final Integer MINOR_VERSION = Integer.valueOf(22);
    public static final String VERSION = (MAJOR_VERSION + "." + MINOR_VERSION + "." + BUGFIX_VERSION + BuildConfig.FLAVOR).toString();
    static KeyStore certTrustStore;

    public static synchronized KeyStore getCertificateTrustStore() throws IOException, GeneralSecurityException {
        KeyStore keyStore;
        synchronized (GoogleUtils.class) {
            if (certTrustStore == null) {
                certTrustStore = SecurityUtils.getJavaKeyStore();
                SecurityUtils.loadKeyStore(certTrustStore, GoogleUtils.class.getResourceAsStream("google.jks"), "notasecret");
            }
            keyStore = certTrustStore;
        }
        return keyStore;
    }

    private GoogleUtils() {
    }
}
