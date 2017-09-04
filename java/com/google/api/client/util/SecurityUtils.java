package com.google.api.client.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.X509TrustManager;

public final class SecurityUtils {
    public static KeyStore getDefaultKeyStore() throws KeyStoreException {
        return KeyStore.getInstance(KeyStore.getDefaultType());
    }

    public static KeyStore getJavaKeyStore() throws KeyStoreException {
        return KeyStore.getInstance("JKS");
    }

    public static KeyStore getPkcs12KeyStore() throws KeyStoreException {
        return KeyStore.getInstance("PKCS12");
    }

    public static void loadKeyStore(KeyStore keyStore, InputStream keyStream, String storePass) throws IOException, GeneralSecurityException {
        try {
            keyStore.load(keyStream, storePass.toCharArray());
        } finally {
            keyStream.close();
        }
    }

    public static PrivateKey getPrivateKey(KeyStore keyStore, String alias, String keyPass) throws GeneralSecurityException {
        return (PrivateKey) keyStore.getKey(alias, keyPass.toCharArray());
    }

    public static PrivateKey loadPrivateKeyFromKeyStore(KeyStore keyStore, InputStream keyStream, String storePass, String alias, String keyPass) throws IOException, GeneralSecurityException {
        loadKeyStore(keyStore, keyStream, storePass);
        return getPrivateKey(keyStore, alias, keyPass);
    }

    public static KeyFactory getRsaKeyFactory() throws NoSuchAlgorithmException {
        return KeyFactory.getInstance("RSA");
    }

    public static Signature getSha1WithRsaSignatureAlgorithm() throws NoSuchAlgorithmException {
        return Signature.getInstance("SHA1withRSA");
    }

    public static Signature getSha256WithRsaSignatureAlgorithm() throws NoSuchAlgorithmException {
        return Signature.getInstance("SHA256withRSA");
    }

    public static byte[] sign(Signature signatureAlgorithm, PrivateKey privateKey, byte[] contentBytes) throws InvalidKeyException, SignatureException {
        signatureAlgorithm.initSign(privateKey);
        signatureAlgorithm.update(contentBytes);
        return signatureAlgorithm.sign();
    }

    public static boolean verify(Signature signatureAlgorithm, PublicKey publicKey, byte[] signatureBytes, byte[] contentBytes) throws InvalidKeyException, SignatureException {
        signatureAlgorithm.initVerify(publicKey);
        signatureAlgorithm.update(contentBytes);
        try {
            return signatureAlgorithm.verify(signatureBytes);
        } catch (SignatureException e) {
            return false;
        }
    }

    public static X509Certificate verify(Signature signatureAlgorithm, X509TrustManager trustManager, List<String> certChainBase64, byte[] signatureBytes, byte[] contentBytes) throws InvalidKeyException, SignatureException {
        try {
            CertificateFactory certificateFactory = getX509CertificateFactory();
            X509Certificate[] certificates = new X509Certificate[certChainBase64.size()];
            int currentCert = 0;
            for (String certBase64 : certChainBase64) {
                try {
                    Certificate cert = certificateFactory.generateCertificate(new ByteArrayInputStream(Base64.decodeBase64(certBase64)));
                    if (!(cert instanceof X509Certificate)) {
                        return null;
                    }
                    int currentCert2 = currentCert + 1;
                    try {
                        certificates[currentCert] = (X509Certificate) cert;
                        currentCert = currentCert2;
                    } catch (CertificateException e) {
                        currentCert = currentCert2;
                    }
                } catch (CertificateException e2) {
                }
            }
            try {
                trustManager.checkServerTrusted(certificates, "RSA");
                if (verify(signatureAlgorithm, certificates[0].getPublicKey(), signatureBytes, contentBytes)) {
                    return certificates[0];
                }
                return null;
            } catch (CertificateException e3) {
                return null;
            }
            return null;
        } catch (CertificateException e4) {
            return null;
        }
    }

    public static CertificateFactory getX509CertificateFactory() throws CertificateException {
        return CertificateFactory.getInstance("X.509");
    }

    public static void loadKeyStoreFromCertificates(KeyStore keyStore, CertificateFactory certificateFactory, InputStream certificateStream) throws GeneralSecurityException {
        int i = 0;
        for (Certificate cert : certificateFactory.generateCertificates(certificateStream)) {
            keyStore.setCertificateEntry(String.valueOf(i), cert);
            i++;
        }
    }

    private SecurityUtils() {
    }
}
