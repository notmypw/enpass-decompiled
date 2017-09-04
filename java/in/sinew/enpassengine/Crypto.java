package in.sinew.enpassengine;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
    private Cipher decryptor = null;
    private Cipher encryptor = null;
    private int mKeySize;

    public static void hexdump(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            sb.append(String.format("%02X ", new Object[]{Byte.valueOf(bytes[i])}));
        }
        System.out.println(sb.toString());
    }

    public Crypto(String aPassword, byte[] aSalt, byte[] aIv, int aVersion) {
        int numItr = 2048;
        if (2 == aVersion) {
            this.mKeySize = 16;
        } else if (3 == aVersion) {
            this.mKeySize = 32;
        } else if (4 == aVersion) {
            this.mKeySize = 32;
        } else if (aVersion >= 5) {
            this.mKeySize = 32;
            numItr = 2;
        }
        initializeCryptorsWithPassword(aPassword, aSalt, aIv, numItr);
    }

    public Crypto(String aPassword, byte[] aIv) {
        initializeCryptorsOnlyWithPassword(aPassword, aIv);
    }

    private void initializeCryptorsWithPassword(String aPassword, byte[] aSalt, byte[] aIv, int numItr) {
        SecretKeySpec skeySpec = new SecretKeySpec(generateKey(aPassword, aSalt, numItr), "AES");
        try {
            this.encryptor = Cipher.getInstance("AES/CBC/PKCS5Padding");
            this.encryptor.init(1, skeySpec, new IvParameterSpec(aIv));
            this.decryptor = Cipher.getInstance("AES/CBC/PKCS5Padding");
            this.decryptor.init(2, skeySpec, new IvParameterSpec(aIv));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("\n***********Init Cipher Excption=" + e);
        }
    }

    private void initializeCryptorsOnlyWithPassword(String aPassword, byte[] aIv) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(aPassword.getBytes("UTF-8"), "AES");
            this.encryptor = Cipher.getInstance("AES/CBC/PKCS5Padding");
            this.encryptor.init(1, skeySpec, new IvParameterSpec(aIv));
            this.decryptor = Cipher.getInstance("AES/CBC/PKCS5Padding");
            this.decryptor.init(2, skeySpec, new IvParameterSpec(aIv));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("\n***********Init Cipher Excption=" + e);
        }
    }

    public void destroyCrypto() {
    }

    public byte[] encrypt(byte[] aData) {
        try {
            return this.encryptor.doFinal(aData);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            return null;
        } catch (BadPaddingException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public byte[] decrypt(byte[] aData) {
        try {
            return this.decryptor.doFinal(aData);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            return null;
        } catch (BadPaddingException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static byte[] hash(byte[] aData) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            System.out.print("\n***********Hash Excption=" + e);
        }
        md.update(aData);
        return md.digest();
    }

    private byte[] generateKey(String aPassword, byte[] aSalt, int numItr) {
        byte[] keyFromPass = null;
        try {
            keyFromPass = PBKDF2(aPassword.getBytes("UTF-8"), aSalt, numItr, this.mKeySize);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return keyFromPass;
    }

    public byte[] PBKDF2(byte[] P, byte[] S, int c, int dkLen) {
        Mac mac = null;
        int hLen = 0;
        try {
            mac = Mac.getInstance("HMACSHA256");
            hLen = mac.getMacLength();
            mac.init(new SecretKeySpec(P, "HMACSHA256"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int l = ceil(dkLen, hLen);
        int r = dkLen - ((l - 1) * hLen);
        byte[] T = new byte[(l * hLen)];
        int ti_offset = 0;
        for (int i = 1; i <= l; i++) {
            _F(T, ti_offset, mac, S, c, i);
            ti_offset += hLen;
        }
        if (r >= hLen) {
            return T;
        }
        byte[] DK = new byte[dkLen];
        System.arraycopy(T, 0, DK, 0, dkLen);
        return DK;
    }

    private int ceil(int a, int b) {
        int m = 0;
        if (a % b > 0) {
            m = 1;
        }
        return (a / b) + m;
    }

    private void _F(byte[] dest, int offset, Mac mac, byte[] S, int c, int blockIndex) {
        int hLen = mac.getMacLength();
        byte[] U_r = new byte[hLen];
        byte[] U_i = new byte[(S.length + 4)];
        System.arraycopy(S, 0, U_i, 0, S.length);
        INT(U_i, S.length, blockIndex);
        for (int i = 0; i < c; i++) {
            U_i = mac.doFinal(U_i);
            xor(U_r, U_i);
        }
        System.arraycopy(U_r, 0, dest, offset, hLen);
    }

    private void xor(byte[] dest, byte[] src) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = (byte) (dest[i] ^ src[i]);
        }
    }

    private void INT(byte[] dest, int offset, int i) {
        dest[offset + 0] = (byte) (i / 16777216);
        dest[offset + 1] = (byte) (i / 65536);
        dest[offset + 2] = (byte) (i / 256);
        dest[offset + 3] = (byte) i;
    }
}
