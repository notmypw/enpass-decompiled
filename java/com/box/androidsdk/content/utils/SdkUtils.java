package com.box.androidsdk.content.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.eclipsesource.json.JsonValue;
import com.github.clans.fab.BuildConfig;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SdkUtils {
    private static final int BUFFER_SIZE = 8192;
    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    public static void copyStream(java.io.InputStream r5, java.io.OutputStream r6) throws java.io.IOException, java.lang.InterruptedException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0044 in list [B:27:0x0041, B:35:0x0034]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:286)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:173)
*/
        /*
        r4 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r0 = new byte[r4];
        r1 = 0;
        r3 = 0;
    L_0x0006:
        r1 = r5.read(r0);	 Catch:{ Exception -> 0x001c, all -> 0x0025 }
        if (r1 <= 0) goto L_0x0034;	 Catch:{ Exception -> 0x001c, all -> 0x0025 }
    L_0x000c:
        r4 = java.lang.Thread.currentThread();	 Catch:{ Exception -> 0x001c, all -> 0x0025 }
        r4 = r4.isInterrupted();	 Catch:{ Exception -> 0x001c, all -> 0x0025 }
        if (r4 == 0) goto L_0x002f;	 Catch:{ Exception -> 0x001c, all -> 0x0025 }
    L_0x0016:
        r2 = new java.lang.InterruptedException;	 Catch:{ Exception -> 0x001c, all -> 0x0025 }
        r2.<init>();	 Catch:{ Exception -> 0x001c, all -> 0x0025 }
        throw r2;	 Catch:{ Exception -> 0x001c, all -> 0x0025 }
    L_0x001c:
        r2 = move-exception;
        r3 = r2;
        r4 = r3 instanceof java.io.IOException;	 Catch:{ Exception -> 0x001c, all -> 0x0025 }
        if (r4 == 0) goto L_0x003d;	 Catch:{ Exception -> 0x001c, all -> 0x0025 }
    L_0x0022:
        r2 = (java.io.IOException) r2;	 Catch:{ Exception -> 0x001c, all -> 0x0025 }
        throw r2;	 Catch:{ Exception -> 0x001c, all -> 0x0025 }
    L_0x0025:
        r4 = move-exception;
        if (r3 != 0) goto L_0x002b;
    L_0x0028:
        r6.flush();
    L_0x002b:
        r5.close();
        throw r4;
    L_0x002f:
        r4 = 0;
        r6.write(r0, r4, r1);	 Catch:{ Exception -> 0x001c, all -> 0x0025 }
        goto L_0x0006;
    L_0x0034:
        if (r3 != 0) goto L_0x0039;
    L_0x0036:
        r6.flush();
    L_0x0039:
        r5.close();
    L_0x003c:
        return;
    L_0x003d:
        r4 = r3 instanceof java.lang.InterruptedException;	 Catch:{ Exception -> 0x001c, all -> 0x0025 }
        if (r4 == 0) goto L_0x0044;	 Catch:{ Exception -> 0x001c, all -> 0x0025 }
    L_0x0041:
        r2 = (java.lang.InterruptedException) r2;	 Catch:{ Exception -> 0x001c, all -> 0x0025 }
        throw r2;	 Catch:{ Exception -> 0x001c, all -> 0x0025 }
    L_0x0044:
        if (r3 != 0) goto L_0x0049;
    L_0x0046:
        r6.flush();
    L_0x0049:
        r5.close();
        goto L_0x003c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.box.androidsdk.content.utils.SdkUtils.copyStream(java.io.InputStream, java.io.OutputStream):void");
    }

    public static String generateStateToken() {
        return UUID.randomUUID().toString();
    }

    public static OutputStream createArrayOutputStream(final OutputStream[] outputStreams) {
        return new OutputStream() {
            public void close() throws IOException {
                for (OutputStream o : outputStreams) {
                    o.close();
                }
                super.close();
            }

            public void flush() throws IOException {
                for (OutputStream o : outputStreams) {
                    o.flush();
                }
                super.flush();
            }

            public void write(int oneByte) throws IOException {
                for (OutputStream o : outputStreams) {
                    o.write(oneByte);
                }
            }

            public void write(byte[] buffer) throws IOException {
                for (OutputStream o : outputStreams) {
                    o.write(buffer);
                }
            }

            public void write(byte[] buffer, int offset, int count) throws IOException {
                for (OutputStream o : outputStreams) {
                    o.write(buffer, offset, count);
                }
            }
        };
    }

    public static boolean isEmptyString(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String sha1(InputStream inputStream) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] bytes = new byte[BUFFER_SIZE];
        while (true) {
            int byteCount = inputStream.read(bytes);
            if (byteCount > 0) {
                md.update(bytes, 0, byteCount);
            } else {
                inputStream.close();
                return new String(encodeHex(md.digest()));
            }
        }
    }

    private static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[(l << 1)];
        int j = 0;
        for (int i = 0; i < l; i++) {
            int i2 = j + 1;
            out[j] = HEX_CHARS[(data[i] & 240) >>> 4];
            j = i2 + 1;
            out[i2] = HEX_CHARS[data[i] & 15];
        }
        return out;
    }

    public static long parseJsonValueToLong(JsonValue value) {
        try {
            return value.asLong();
        } catch (UnsupportedOperationException e) {
            return Long.parseLong(value.asString().replace("\"", BuildConfig.FLAVOR));
        }
    }

    public static long parseJsonValueToInteger(JsonValue value) {
        try {
            return (long) value.asInt();
        } catch (UnsupportedOperationException e) {
            return (long) Integer.parseInt(value.asString().replace("\"", BuildConfig.FLAVOR));
        }
    }

    public static String concatStringWithDelimiter(String[] strings, String delimiter) {
        StringBuilder sbr = new StringBuilder();
        int size = strings.length;
        for (int i = 0; i < size - 1; i++) {
            sbr.append(strings[i]).append(delimiter);
        }
        sbr.append(strings[size - 1]);
        return sbr.toString();
    }

    public static ThreadPoolExecutor createDefaultThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue(), new ThreadFactory() {
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });
    }

    public static <T> T cloneSerializable(T source) {
        ObjectOutputStream oos;
        ByteArrayInputStream bais;
        Throwable th;
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos2 = null;
        ByteArrayInputStream bais2 = null;
        ObjectInputStream ois = null;
        try {
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
            try {
                oos = new ObjectOutputStream(baos2);
                try {
                    oos.writeObject(source);
                    bais = new ByteArrayInputStream(baos2.toByteArray());
                } catch (IOException e) {
                    oos2 = oos;
                    baos = baos2;
                    closeQuietly(baos, oos2, bais2, ois);
                    return null;
                } catch (ClassNotFoundException e2) {
                    oos2 = oos;
                    baos = baos2;
                    closeQuietly(baos, oos2, bais2, ois);
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    oos2 = oos;
                    baos = baos2;
                    closeQuietly(baos, oos2, bais2, ois);
                    throw th;
                }
            } catch (IOException e3) {
                baos = baos2;
                closeQuietly(baos, oos2, bais2, ois);
                return null;
            } catch (ClassNotFoundException e4) {
                baos = baos2;
                closeQuietly(baos, oos2, bais2, ois);
                return null;
            } catch (Throwable th3) {
                th = th3;
                baos = baos2;
                closeQuietly(baos, oos2, bais2, ois);
                throw th;
            }
            try {
                ObjectInputStream ois2 = new ObjectInputStream(bais);
                try {
                    T readObject = ois2.readObject();
                    closeQuietly(baos2, oos, bais, ois2);
                    ois = ois2;
                    bais2 = bais;
                    oos2 = oos;
                    baos = baos2;
                    return readObject;
                } catch (IOException e5) {
                    ois = ois2;
                    bais2 = bais;
                    oos2 = oos;
                    baos = baos2;
                    closeQuietly(baos, oos2, bais2, ois);
                    return null;
                } catch (ClassNotFoundException e6) {
                    ois = ois2;
                    bais2 = bais;
                    oos2 = oos;
                    baos = baos2;
                    closeQuietly(baos, oos2, bais2, ois);
                    return null;
                } catch (Throwable th4) {
                    th = th4;
                    ois = ois2;
                    bais2 = bais;
                    oos2 = oos;
                    baos = baos2;
                    closeQuietly(baos, oos2, bais2, ois);
                    throw th;
                }
            } catch (IOException e7) {
                bais2 = bais;
                oos2 = oos;
                baos = baos2;
                closeQuietly(baos, oos2, bais2, ois);
                return null;
            } catch (ClassNotFoundException e8) {
                bais2 = bais;
                oos2 = oos;
                baos = baos2;
                closeQuietly(baos, oos2, bais2, ois);
                return null;
            } catch (Throwable th5) {
                th = th5;
                bais2 = bais;
                oos2 = oos;
                baos = baos2;
                closeQuietly(baos, oos2, bais2, ois);
                throw th;
            }
        } catch (IOException e9) {
            closeQuietly(baos, oos2, bais2, ois);
            return null;
        } catch (ClassNotFoundException e10) {
            closeQuietly(baos, oos2, bais2, ois);
            return null;
        } catch (Throwable th6) {
            th = th6;
            closeQuietly(baos, oos2, bais2, ois);
            throw th;
        }
    }

    public static String convertSerializableToString(Serializable obj) {
        ObjectOutputStream oos;
        Throwable th;
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos2 = null;
        try {
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
            try {
                oos = new ObjectOutputStream(baos2);
            } catch (IOException e) {
                baos = baos2;
                closeQuietly(baos, oos2);
                closeQuietly(oos2);
                return null;
            } catch (Throwable th2) {
                th = th2;
                baos = baos2;
                closeQuietly(baos, oos2);
                closeQuietly(oos2);
                throw th;
            }
            try {
                oos.writeObject(obj);
                String str = new String(baos2.toByteArray());
                closeQuietly(baos2, oos);
                closeQuietly(oos);
                oos2 = oos;
                baos = baos2;
                return str;
            } catch (IOException e2) {
                oos2 = oos;
                baos = baos2;
                closeQuietly(baos, oos2);
                closeQuietly(oos2);
                return null;
            } catch (Throwable th3) {
                th = th3;
                oos2 = oos;
                baos = baos2;
                closeQuietly(baos, oos2);
                closeQuietly(oos2);
                throw th;
            }
        } catch (IOException e3) {
            closeQuietly(baos, oos2);
            closeQuietly(oos2);
            return null;
        } catch (Throwable th4) {
            th = th4;
            closeQuietly(baos, oos2);
            closeQuietly(oos2);
            throw th;
        }
    }

    public static void closeQuietly(Closeable... closeables) {
        for (Closeable c : closeables) {
            try {
                c.close();
            } catch (Exception e) {
            }
        }
    }

    public static boolean deleteFolderRecursive(File f) {
        int i = 0;
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files == null) {
                return false;
            }
            int length = files.length;
            while (i < length) {
                deleteFolderRecursive(files[i]);
                i++;
            }
        }
        return f.delete();
    }

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo wifi = connectivityManager.getNetworkInfo(1);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(0);
        if (wifi.isConnected() || (mobile != null && mobile.isConnected())) {
            return true;
        }
        return false;
    }

    public static String getAssetFile(Context context, String assetName) {
        IOException e;
        Throwable th;
        AssetManager assetManager = context.getAssets();
        BufferedReader bufferedReader = null;
        try {
            StringBuilder buf = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(assetManager.open(assetName)));
            boolean isFirst = true;
            while (true) {
                try {
                    String str = in.readLine();
                    if (str == null) {
                        break;
                    }
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        buf.append('\n');
                    }
                    buf.append(str);
                } catch (IOException e2) {
                    e = e2;
                    bufferedReader = in;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = in;
                }
            }
            String stringBuilder = buf.toString();
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e3) {
                    BoxLogUtils.e("getAssetFile", assetName, e3);
                }
            }
            bufferedReader = in;
            return stringBuilder;
        } catch (IOException e4) {
            e = e4;
            try {
                BoxLogUtils.e("getAssetFile", assetName, e);
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception e32) {
                        BoxLogUtils.e("getAssetFile", assetName, e32);
                    }
                }
                return null;
            } catch (Throwable th3) {
                th = th3;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception e322) {
                        BoxLogUtils.e("getAssetFile", assetName, e322);
                    }
                }
                throw th;
            }
        }
    }
}
