package com.dropbox.core.util;

import com.github.clans.fab.R;
import com.samsung.android.sdk.pass.SpassFingerprint;
import in.sinew.enpass.IRemoteStorage;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import net.sqlcipher.database.SQLiteDatabase;

public class StringUtil {
    static final /* synthetic */ boolean $assertionsDisabled = (!StringUtil.class.desiredAssertionStatus() ? true : $assertionsDisabled);
    public static final String Base64Digits = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private static final char[] HexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final Charset UTF8 = Charset.forName("UTF-8");
    public static final String UrlSafeBase64Digits = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";

    static {
        if (!$assertionsDisabled && Base64Digits.length() != 64) {
            throw new AssertionError(Base64Digits.length());
        } else if (!$assertionsDisabled && UrlSafeBase64Digits.length() != 64) {
            throw new AssertionError(UrlSafeBase64Digits.length());
        }
    }

    public static char hexDigit(int i) {
        return HexDigits[i];
    }

    public static String utf8ToString(byte[] utf8Data) throws CharacterCodingException {
        return utf8ToString(utf8Data, 0, utf8Data.length);
    }

    public static String utf8ToString(byte[] utf8Data, int offset, int length) throws CharacterCodingException {
        return UTF8.newDecoder().decode(ByteBuffer.wrap(utf8Data, offset, length)).toString();
    }

    public static byte[] stringToUtf8(String s) {
        try {
            return s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw LangUtil.mkAssert("UTF-8 should always be supported", ex);
        }
    }

    public static String javaQuotedLiteral(String value) {
        StringBuilder b = new StringBuilder(value.length() * 2);
        b.append('\"');
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case SQLiteDatabase.OPEN_READWRITE /*0*/:
                    b.append("\\000");
                    break;
                case IRemoteStorage.WEBDAV_REMOTE /*9*/:
                    b.append("\\r");
                    break;
                case IRemoteStorage.WEBDAV_REMOTE_USERNAME /*10*/:
                    b.append("\\n");
                    break;
                case SpassFingerprint.STATUS_USER_CANCELLED_BY_TOUCH_OUTSIDE /*13*/:
                    b.append("\\t");
                    break;
                case R.styleable.FloatingActionMenu_menu_shadowRadius /*34*/:
                    b.append("\\\"");
                    break;
                case '\\':
                    b.append("\\\\");
                    break;
                default:
                    if (c >= ' ' && c <= '~') {
                        b.append(c);
                        break;
                    }
                    int h1 = (c >> 12) & 15;
                    int h2 = (c >> 8) & 15;
                    int h3 = (c >> 4) & 15;
                    int h4 = c & 15;
                    b.append("\\u");
                    b.append(hexDigit(h1));
                    b.append(hexDigit(h2));
                    b.append(hexDigit(h3));
                    b.append(hexDigit(h4));
                    break;
            }
        }
        b.append('\"');
        return b.toString();
    }

    public static String jq(String value) {
        return javaQuotedLiteral(value);
    }

    public static String binaryToHex(byte[] data) {
        return binaryToHex(data, 0, data.length);
    }

    public static String binaryToHex(byte[] data, int offset, int length) {
        if ($assertionsDisabled || (offset < data.length && offset >= 0)) {
            int end = offset + length;
            if ($assertionsDisabled || (end <= data.length && end >= 0)) {
                char[] chars = new char[(length * 2)];
                int j = 0;
                for (int i = offset; i < end; i++) {
                    int b = data[i];
                    int i2 = j + 1;
                    chars[j] = hexDigit((b >>> 4) & 15);
                    j = i2 + 1;
                    chars[i2] = hexDigit(b & 15);
                }
                return new String(chars);
            }
            throw new AssertionError(offset + ", " + length + ", " + data.length);
        }
        throw new AssertionError(offset + ", " + data.length);
    }

    public static boolean secureStringEquals(String a, String b) {
        if (a.length() != b.length()) {
            return $assertionsDisabled;
        }
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        if (result == 0) {
            return true;
        }
        return $assertionsDisabled;
    }

    public static String base64Encode(byte[] data) {
        return base64EncodeGeneric(Base64Digits, data);
    }

    public static String urlSafeBase64Encode(byte[] data) {
        return base64EncodeGeneric(UrlSafeBase64Digits, data);
    }

    public static String base64EncodeGeneric(String digits, byte[] data) {
        if (data == null) {
            throw new IllegalArgumentException("'data' can't be null");
        } else if (digits == null) {
            throw new IllegalArgumentException("'digits' can't be null");
        } else if (digits.length() != 64) {
            throw new IllegalArgumentException("'digits' must be 64 characters long: " + jq(digits));
        } else {
            int i;
            int b1;
            int b2;
            int d2;
            int d3;
            StringBuilder buf = new StringBuilder(((data.length + 2) / 3) * 4);
            int i2 = 0;
            while (i2 + 3 <= data.length) {
                i = i2 + 1;
                b1 = data[i2] & 255;
                i2 = i + 1;
                b2 = data[i] & 255;
                i = i2 + 1;
                int b3 = data[i2] & 255;
                d2 = ((b1 & 3) << 4) | (b2 >>> 4);
                d3 = ((b2 & 15) << 2) | (b3 >>> 6);
                int d4 = b3 & 63;
                buf.append(digits.charAt(b1 >>> 2));
                buf.append(digits.charAt(d2));
                buf.append(digits.charAt(d3));
                buf.append(digits.charAt(d4));
                i2 = i;
            }
            int remaining = data.length - i2;
            if (remaining != 0) {
                if (remaining == 1) {
                    i = i2 + 1;
                    b1 = data[i2] & 255;
                    d2 = (b1 & 3) << 4;
                    buf.append(digits.charAt(b1 >>> 2));
                    buf.append(digits.charAt(d2));
                    buf.append("==");
                    i2 = i;
                } else if (remaining == 2) {
                    i = i2 + 1;
                    b1 = data[i2] & 255;
                    i2 = i + 1;
                    b2 = data[i] & 255;
                    d2 = ((b1 & 3) << 4) | (b2 >>> 4);
                    d3 = (b2 & 15) << 2;
                    buf.append(digits.charAt(b1 >>> 2));
                    buf.append(digits.charAt(d2));
                    buf.append(digits.charAt(d3));
                    buf.append('=');
                } else {
                    throw new AssertionError("data.length: " + data.length + ", i: " + i2);
                }
            }
            return buf.toString();
        }
    }
}
