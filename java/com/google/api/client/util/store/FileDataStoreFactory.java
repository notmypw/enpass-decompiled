package com.google.api.client.util.store;

import com.google.api.client.util.IOUtils;
import com.google.api.client.util.Maps;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.logging.Logger;

public class FileDataStoreFactory extends AbstractDataStoreFactory {
    private static final Logger LOGGER = Logger.getLogger(FileDataStoreFactory.class.getName());
    private final File dataDirectory;

    static class FileDataStore<V extends Serializable> extends AbstractMemoryDataStore<V> {
        private final File dataFile;

        FileDataStore(FileDataStoreFactory dataStore, File dataDirectory, String id) throws IOException {
            super(dataStore, id);
            this.dataFile = new File(dataDirectory, id);
            if (IOUtils.isSymbolicLink(this.dataFile)) {
                throw new IOException("unable to use a symbolic link: " + this.dataFile);
            } else if (this.dataFile.createNewFile()) {
                this.keyValueMap = Maps.newHashMap();
                save();
            } else {
                this.keyValueMap = (HashMap) IOUtils.deserialize(new FileInputStream(this.dataFile));
            }
        }

        void save() throws IOException {
            IOUtils.serialize(this.keyValueMap, new FileOutputStream(this.dataFile));
        }

        public FileDataStoreFactory getDataStoreFactory() {
            return (FileDataStoreFactory) super.getDataStoreFactory();
        }
    }

    public FileDataStoreFactory(File dataDirectory) throws IOException {
        dataDirectory = dataDirectory.getCanonicalFile();
        this.dataDirectory = dataDirectory;
        if (IOUtils.isSymbolicLink(dataDirectory)) {
            throw new IOException("unable to use a symbolic link: " + dataDirectory);
        } else if (dataDirectory.exists() || dataDirectory.mkdirs()) {
            setPermissionsToOwnerOnly(dataDirectory);
        } else {
            throw new IOException("unable to create directory: " + dataDirectory);
        }
    }

    public final File getDataDirectory() {
        return this.dataDirectory;
    }

    protected <V extends Serializable> DataStore<V> createDataStore(String id) throws IOException {
        return new FileDataStore(this, this.dataDirectory, id);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void setPermissionsToOwnerOnly(java.io.File r10) throws java.io.IOException {
        /*
        r5 = java.io.File.class;
        r6 = "setReadable";
        r7 = 2;
        r7 = new java.lang.Class[r7];	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r8 = 0;
        r9 = java.lang.Boolean.TYPE;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r7[r8] = r9;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r8 = 1;
        r9 = java.lang.Boolean.TYPE;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r7[r8] = r9;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r3 = r5.getMethod(r6, r7);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = java.io.File.class;
        r6 = "setWritable";
        r7 = 2;
        r7 = new java.lang.Class[r7];	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r8 = 0;
        r9 = java.lang.Boolean.TYPE;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r7[r8] = r9;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r8 = 1;
        r9 = java.lang.Boolean.TYPE;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r7[r8] = r9;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r4 = r5.getMethod(r6, r7);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = java.io.File.class;
        r6 = "setExecutable";
        r7 = 2;
        r7 = new java.lang.Class[r7];	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r8 = 0;
        r9 = java.lang.Boolean.TYPE;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r7[r8] = r9;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r8 = 1;
        r9 = java.lang.Boolean.TYPE;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r7[r8] = r9;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r2 = r5.getMethod(r6, r7);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = 2;
        r5 = new java.lang.Object[r5];	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r6 = 0;
        r7 = 0;
        r7 = java.lang.Boolean.valueOf(r7);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5[r6] = r7;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r6 = 1;
        r7 = 0;
        r7 = java.lang.Boolean.valueOf(r7);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5[r6] = r7;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = r3.invoke(r10, r5);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = (java.lang.Boolean) r5;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = r5.booleanValue();	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        if (r5 == 0) goto L_0x009c;
    L_0x005e:
        r5 = 2;
        r5 = new java.lang.Object[r5];	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r6 = 0;
        r7 = 0;
        r7 = java.lang.Boolean.valueOf(r7);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5[r6] = r7;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r6 = 1;
        r7 = 0;
        r7 = java.lang.Boolean.valueOf(r7);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5[r6] = r7;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = r4.invoke(r10, r5);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = (java.lang.Boolean) r5;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = r5.booleanValue();	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        if (r5 == 0) goto L_0x009c;
    L_0x007d:
        r5 = 2;
        r5 = new java.lang.Object[r5];	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r6 = 0;
        r7 = 0;
        r7 = java.lang.Boolean.valueOf(r7);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5[r6] = r7;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r6 = 1;
        r7 = 0;
        r7 = java.lang.Boolean.valueOf(r7);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5[r6] = r7;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = r2.invoke(r10, r5);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = (java.lang.Boolean) r5;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = r5.booleanValue();	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        if (r5 != 0) goto L_0x00b4;
    L_0x009c:
        r5 = LOGGER;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r6 = new java.lang.StringBuilder;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r6.<init>();	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r7 = "unable to change permissions for everybody: ";
        r6 = r6.append(r7);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r6 = r6.append(r10);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r6 = r6.toString();	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5.warning(r6);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
    L_0x00b4:
        r5 = 2;
        r5 = new java.lang.Object[r5];	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r6 = 0;
        r7 = 1;
        r7 = java.lang.Boolean.valueOf(r7);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5[r6] = r7;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r6 = 1;
        r7 = 1;
        r7 = java.lang.Boolean.valueOf(r7);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5[r6] = r7;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = r3.invoke(r10, r5);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = (java.lang.Boolean) r5;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = r5.booleanValue();	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        if (r5 == 0) goto L_0x0111;
    L_0x00d3:
        r5 = 2;
        r5 = new java.lang.Object[r5];	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r6 = 0;
        r7 = 1;
        r7 = java.lang.Boolean.valueOf(r7);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5[r6] = r7;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r6 = 1;
        r7 = 1;
        r7 = java.lang.Boolean.valueOf(r7);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5[r6] = r7;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = r4.invoke(r10, r5);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = (java.lang.Boolean) r5;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = r5.booleanValue();	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        if (r5 == 0) goto L_0x0111;
    L_0x00f2:
        r5 = 2;
        r5 = new java.lang.Object[r5];	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r6 = 0;
        r7 = 1;
        r7 = java.lang.Boolean.valueOf(r7);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5[r6] = r7;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r6 = 1;
        r7 = 1;
        r7 = java.lang.Boolean.valueOf(r7);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5[r6] = r7;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = r2.invoke(r10, r5);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = (java.lang.Boolean) r5;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5 = r5.booleanValue();	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        if (r5 != 0) goto L_0x0129;
    L_0x0111:
        r5 = LOGGER;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r6 = new java.lang.StringBuilder;	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r6.<init>();	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r7 = "unable to change permissions for owner: ";
        r6 = r6.append(r7);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r6 = r6.append(r10);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r6 = r6.toString();	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
        r5.warning(r6);	 Catch:{ InvocationTargetException -> 0x012a, NoSuchMethodException -> 0x013a, SecurityException -> 0x015e, IllegalAccessException -> 0x015c, IllegalArgumentException -> 0x015a }
    L_0x0129:
        return;
    L_0x012a:
        r1 = move-exception;
        r0 = r1.getCause();
        r5 = java.io.IOException.class;
        com.google.api.client.util.Throwables.propagateIfPossible(r0, r5);
        r5 = new java.lang.RuntimeException;
        r5.<init>(r0);
        throw r5;
    L_0x013a:
        r1 = move-exception;
        r5 = LOGGER;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "Unable to set permissions for ";
        r6 = r6.append(r7);
        r6 = r6.append(r10);
        r7 = ", likely because you are running a version of Java prior to 1.6";
        r6 = r6.append(r7);
        r6 = r6.toString();
        r5.warning(r6);
        goto L_0x0129;
    L_0x015a:
        r5 = move-exception;
        goto L_0x0129;
    L_0x015c:
        r5 = move-exception;
        goto L_0x0129;
    L_0x015e:
        r5 = move-exception;
        goto L_0x0129;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.api.client.util.store.FileDataStoreFactory.setPermissionsToOwnerOnly(java.io.File):void");
    }
}
