package in.sinew.enpassengine;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import com.github.clans.fab.BuildConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Date;
import java.util.UUID;

public class Utils {
    public static void wipeString(StringBuilder aValue) {
        if (aValue != null) {
            int length = aValue.length();
            for (int counter = 0; counter < length; counter++) {
                aValue.setCharAt(counter, '\u0000');
            }
        }
    }

    public static void wipeCharArray(char[] aValue) {
        if (aValue != null) {
            for (int counter = 0; counter < aValue.length; counter++) {
                aValue[counter] = '\u0000';
            }
        }
    }

    public static double timestampToTicks(Date timestamp) {
        return (double) (timestamp.getTime() / 1000);
    }

    public static StringBuilder removeTabs(StringBuilder str) {
        int noteLength = str.length();
        for (int find = 0; find < noteLength; find++) {
            if (str.charAt(find) == '\t') {
                str.replace(find, find, BuildConfig.FLAVOR);
            }
        }
        return str;
    }

    public static StringBuilder removeWhiteSpace(StringBuilder str) {
        int strLength = str.length();
        for (int find = 0; find < strLength; find++) {
            if (str.charAt(find) == '\n') {
                str.replace(find, find, BuildConfig.FLAVOR);
            }
            if (str.charAt(find) == '\r') {
                str.replace(find, find, BuildConfig.FLAVOR);
            }
        }
        return str;
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String pad(String aText) {
        int textLength = aText.length();
        if (textLength < 100) {
            return getRandomString(99 - textLength) + "," + aText;
        }
        return aText;
    }

    public static String unpad(String aText) {
        int textlength = aText.length();
        int commaPosition = 0;
        for (int count = 1; count <= textlength; count++) {
            if (aText.charAt(count) == ',') {
                commaPosition = count;
                break;
            }
        }
        return aText.substring(commaPosition + 1, textlength);
    }

    public static String getRandomString(int aLength) {
        String baseString = "ABCDEFGHIJKLMNPQRSTUVWXYZabcdefghijklmnpqrstuvwxyz1234567890";
        StringBuilder randomString = new StringBuilder(BuildConfig.FLAVOR);
        SecureRandom rand = new SecureRandom();
        while (aLength > 0) {
            randomString = randomString.append(baseString.charAt(rand.nextInt(1000) % baseString.length()));
            aLength--;
        }
        return randomString.toString();
    }

    public static void delete(String aFilename, Context aContext) {
        String filePath = aContext.getDatabasePath(aFilename).getAbsolutePath();
        if (filePath != null) {
            new File(filePath).delete();
        }
    }

    public static void restoreDbFromFile(String aFilenameTO, String aFilenameFrom, boolean deleteOrig, Context aContext) {
        Exception e;
        Throwable th;
        String filePath = aContext.getDatabasePath(aFilenameFrom).getAbsolutePath();
        String newFilePath = aContext.getDatabasePath(aFilenameTO).getAbsolutePath();
        if (newFilePath != null) {
            new File(newFilePath).delete();
        }
        InputStream in = null;
        OutputStream out = null;
        try {
            InputStream in2 = new FileInputStream(filePath);
            try {
                OutputStream out2 = new FileOutputStream(newFilePath);
                try {
                    byte[] buffer = new byte[1024];
                    while (true) {
                        int len = in2.read(buffer);
                        if (len > 0) {
                            out2.write(buffer, 0, len);
                        } else {
                            try {
                                break;
                            } catch (IOException e2) {
                                e2.printStackTrace();
                                out = out2;
                                in = in2;
                            }
                        }
                    }
                    in2.close();
                    out2.close();
                    out = out2;
                    in = in2;
                } catch (Exception e3) {
                    e = e3;
                    out = out2;
                    in = in2;
                    try {
                        e.printStackTrace();
                        try {
                            in.close();
                            out.close();
                        } catch (IOException e22) {
                            e22.printStackTrace();
                        }
                        if (!deleteOrig) {
                            new File(filePath).delete();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            in.close();
                            out.close();
                        } catch (IOException e222) {
                            e222.printStackTrace();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    out = out2;
                    in = in2;
                    in.close();
                    out.close();
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                in = in2;
                e.printStackTrace();
                in.close();
                out.close();
                if (!deleteOrig) {
                    new File(filePath).delete();
                }
            } catch (Throwable th4) {
                th = th4;
                in = in2;
                in.close();
                out.close();
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            e.printStackTrace();
            in.close();
            out.close();
            if (!deleteOrig) {
                new File(filePath).delete();
            }
        }
        if (!deleteOrig) {
            new File(filePath).delete();
        }
    }

    public static boolean isDbTypeIsSqlCipher(String filePath) {
        IOException e;
        FileNotFoundException e2;
        Throwable th;
        FileInputStream fIn = null;
        StringBuilder s = new StringBuilder();
        try {
            FileInputStream fIn2 = new FileInputStream(filePath);
            int count = 1;
            while (true) {
                try {
                    int len = fIn2.read();
                    if (len != -1 && count <= 6) {
                        count++;
                        s.append((char) len);
                    } else if (fIn2 == null) {
                        try {
                            fIn2.close();
                            fIn = fIn2;
                        } catch (IOException e3) {
                            e3.printStackTrace();
                            fIn = fIn2;
                        }
                    }
                } catch (FileNotFoundException e4) {
                    e2 = e4;
                    fIn = fIn2;
                } catch (IOException e5) {
                    e3 = e5;
                    fIn = fIn2;
                } catch (Throwable th2) {
                    th = th2;
                    fIn = fIn2;
                }
            }
            if (fIn2 == null) {
            } else {
                fIn2.close();
                fIn = fIn2;
            }
        } catch (FileNotFoundException e6) {
            e2 = e6;
            try {
                e2.printStackTrace();
                if (fIn != null) {
                    try {
                        fIn.close();
                    } catch (IOException e32) {
                        e32.printStackTrace();
                    }
                }
                if (s.toString().equalsIgnoreCase("SQLite")) {
                    return true;
                }
                return false;
            } catch (Throwable th3) {
                th = th3;
                if (fIn != null) {
                    try {
                        fIn.close();
                    } catch (IOException e322) {
                        e322.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (IOException e7) {
            e322 = e7;
            e322.printStackTrace();
            if (fIn != null) {
                try {
                    fIn.close();
                } catch (IOException e3222) {
                    e3222.printStackTrace();
                }
            }
            if (s.toString().equalsIgnoreCase("SQLite")) {
                return false;
            }
            return true;
        }
        if (s.toString().equalsIgnoreCase("SQLite")) {
            return false;
        }
        return true;
    }

    public static boolean dbExists(Context aContext, String aFilename) {
        return new File(aContext.getDatabasePath(aFilename).getAbsolutePath()).exists();
    }

    public static void copyFile(String orig, String dest) {
        OutputStream out;
        IOException e;
        Throwable th;
        InputStream in = null;
        OutputStream out2 = null;
        try {
            InputStream in2 = new FileInputStream(orig);
            try {
                out = new FileOutputStream(dest);
            } catch (IOException e2) {
                e = e2;
                in = in2;
                try {
                    e.printStackTrace();
                    if (out2 != null && in != null) {
                        try {
                            out2.flush();
                            out2.close();
                            in.close();
                            return;
                        } catch (IOException e3) {
                            e3.printStackTrace();
                            return;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (!(out2 == null || in == null)) {
                        try {
                            out2.flush();
                            out2.close();
                            in.close();
                        } catch (IOException e32) {
                            e32.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                in = in2;
                out2.flush();
                out2.close();
                in.close();
                throw th;
            }
            try {
                byte[] buf = new byte[1024];
                while (true) {
                    int len = in2.read(buf);
                    if (len <= 0) {
                        break;
                    }
                    out.write(buf, 0, len);
                }
                if (!(out == null || in2 == null)) {
                    try {
                        out.flush();
                        out.close();
                        in2.close();
                    } catch (IOException e322) {
                        e322.printStackTrace();
                        out2 = out;
                        in = in2;
                        return;
                    }
                }
                out2 = out;
                in = in2;
            } catch (IOException e4) {
                e322 = e4;
                out2 = out;
                in = in2;
                e322.printStackTrace();
                if (out2 != null) {
                }
            } catch (Throwable th4) {
                th = th4;
                out2 = out;
                in = in2;
                out2.flush();
                out2.close();
                in.close();
                throw th;
            }
        } catch (IOException e5) {
            e322 = e5;
            e322.printStackTrace();
            if (out2 != null) {
            }
        }
    }

    public static float pixelToDp(float aPixel, Context aContext) {
        return aPixel * aContext.getResources().getDisplayMetrics().density;
    }

    public static String copySyncDbFile(String aFilename, Context aContext) {
        Exception e;
        Throwable th;
        String filePath = aContext.getDatabasePath(aFilename).getAbsolutePath();
        String copyFilePath = aContext.getDatabasePath(aFilename + ".sync").getAbsolutePath();
        if (copyFilePath != null) {
            new File(copyFilePath).delete();
        }
        InputStream in = null;
        OutputStream out = null;
        try {
            InputStream in2 = new FileInputStream(filePath);
            try {
                OutputStream out2 = new FileOutputStream(copyFilePath);
                try {
                    byte[] buffer = new byte[1024];
                    while (true) {
                        int len = in2.read(buffer);
                        if (len <= 0) {
                            break;
                        }
                        out2.write(buffer, 0, len);
                    }
                    if (!(out2 == null || in2 == null)) {
                        try {
                            out2.flush();
                            out2.close();
                            in2.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                            out = out2;
                            in = in2;
                        }
                    }
                    out = out2;
                    in = in2;
                } catch (Exception e3) {
                    e = e3;
                    out = out2;
                    in = in2;
                    try {
                        e.printStackTrace();
                        if (!(out == null || in == null)) {
                            try {
                                out.flush();
                                out.close();
                                in.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                            }
                        }
                        return copyFilePath;
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            out.flush();
                            out.close();
                            in.close();
                        } catch (IOException e222) {
                            e222.printStackTrace();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    out = out2;
                    in = in2;
                    if (!(out == null || in == null)) {
                        out.flush();
                        out.close();
                        in.close();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                in = in2;
                e.printStackTrace();
                out.flush();
                out.close();
                in.close();
                return copyFilePath;
            } catch (Throwable th4) {
                th = th4;
                in = in2;
                out.flush();
                out.close();
                in.close();
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            e.printStackTrace();
            out.flush();
            out.close();
            in.close();
            return copyFilePath;
        }
        return copyFilePath;
    }

    public static boolean isFileExist(String aFilename, Context aContext) {
        return new File(aContext.getDatabasePath(aFilename).getAbsolutePath()).exists();
    }

    public static String getMimeType(String url) {
        String type = BuildConfig.FLAVOR;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (TextUtils.isEmpty(extension) && !url.isEmpty()) {
            int dotPos = url.lastIndexOf(46);
            if (dotPos >= 0) {
                extension = url.substring(dotPos + 1);
            }
        }
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        if (type == null) {
            return extension;
        }
        return type;
    }
}
