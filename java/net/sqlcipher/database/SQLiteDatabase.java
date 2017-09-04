package net.sqlcipher.database;

import android.content.ContentValues;
import android.content.Context;
import android.os.Debug;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.github.clans.fab.BuildConfig;
import com.google.api.client.util.ExponentialBackOff;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;
import net.sqlcipher.CrossProcessCursorWrapper;
import net.sqlcipher.Cursor;
import net.sqlcipher.CursorWrapper;
import net.sqlcipher.DatabaseErrorHandler;
import net.sqlcipher.DatabaseUtils;
import net.sqlcipher.DefaultDatabaseErrorHandler;
import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDebug.DbStats;

public class SQLiteDatabase extends SQLiteClosable {
    private static final String COMMIT_SQL = "COMMIT;";
    public static final int CONFLICT_ABORT = 2;
    public static final int CONFLICT_FAIL = 3;
    public static final int CONFLICT_IGNORE = 4;
    public static final int CONFLICT_NONE = 0;
    public static final int CONFLICT_REPLACE = 5;
    public static final int CONFLICT_ROLLBACK = 1;
    private static final String[] CONFLICT_VALUES = new String[]{BuildConfig.FLAVOR, " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE "};
    public static final int CREATE_IF_NECESSARY = 268435456;
    private static final Pattern EMAIL_IN_DB_PATTERN = Pattern.compile("[\\w\\.\\-]+@[\\w\\.\\-]+");
    private static final int EVENT_DB_CORRUPT = 75004;
    private static final int EVENT_DB_OPERATION = 52000;
    static final String GET_LOCK_LOG_PREFIX = "GETLOCK:";
    private static final String KEY_ENCODING = "UTF-8";
    private static final int LOCK_ACQUIRED_WARNING_THREAD_TIME_IN_MS = 100;
    private static final int LOCK_ACQUIRED_WARNING_TIME_IN_MS = 300;
    private static final int LOCK_ACQUIRED_WARNING_TIME_IN_MS_ALWAYS_PRINT = 2000;
    private static final int LOCK_WARNING_WINDOW_IN_MS = 20000;
    private static final String LOG_SLOW_QUERIES_PROPERTY = "db.log.slow_query_threshold";
    public static final int MAX_SQL_CACHE_SIZE = 250;
    private static final int MAX_WARNINGS_ON_CACHESIZE_CONDITION = 1;
    public static final String MEMORY = ":memory:";
    public static final int NO_LOCALIZED_COLLATORS = 16;
    public static final int OPEN_READONLY = 1;
    public static final int OPEN_READWRITE = 0;
    private static final int OPEN_READ_MASK = 1;
    private static final int QUERY_LOG_SQL_LENGTH = 64;
    private static final int SLEEP_AFTER_YIELD_QUANTUM = 1000;
    public static final String SQLCIPHER_ANDROID_VERSION = "3.5.4";
    public static final int SQLITE_MAX_LIKE_PATTERN_LENGTH = 50000;
    private static final String TAG = "Database";
    private static WeakHashMap<SQLiteDatabase, Object> sActiveDatabases = new WeakHashMap();
    private static int sQueryLogTimeInMillis = OPEN_READWRITE;
    private int mCacheFullWarnings;
    Map<String, SQLiteCompiledSql> mCompiledQueries;
    private final DatabaseErrorHandler mErrorHandler;
    private CursorFactory mFactory;
    private int mFlags;
    private boolean mInnerTransactionIsSuccessful;
    private long mLastLockMessageTime;
    private String mLastSqlStatement;
    private final ReentrantLock mLock;
    private long mLockAcquiredThreadTime;
    private long mLockAcquiredWallTime;
    private boolean mLockingEnabled;
    private int mMaxSqlCacheSize;
    int mNativeHandle;
    private int mNumCacheHits;
    private int mNumCacheMisses;
    private String mPath;
    private String mPathForLogs;
    private WeakHashMap<SQLiteClosable, Object> mPrograms;
    private final Random mRandom;
    private final int mSlowQueryThreshold;
    private Throwable mStackTrace;
    private final Map<String, SyncUpdateInfo> mSyncUpdateInfo;
    int mTempTableSequence;
    private String mTimeClosed;
    private String mTimeOpened;
    private boolean mTransactionIsSuccessful;
    private SQLiteTransactionListener mTransactionListener;

    public interface CursorFactory {
        Cursor newCursor(SQLiteDatabase sQLiteDatabase, SQLiteCursorDriver sQLiteCursorDriver, String str, SQLiteQuery sQLiteQuery);
    }

    private native void dbclose();

    private native void dbopen(String str, int i);

    private native void enableSqlProfiling(String str);

    private native void enableSqlTracing(String str);

    private native void key(byte[] bArr) throws SQLException;

    private native void key_mutf8(char[] cArr) throws SQLException;

    private native int native_getDbLookaside();

    private native void native_key(char[] cArr) throws SQLException;

    private native void native_rawExecSQL(String str);

    private native void native_rekey(String str) throws SQLException;

    private native int native_status(int i, boolean z);

    private native void rekey(byte[] bArr) throws SQLException;

    public static native int releaseMemory();

    public static native void setICURoot(String str);

    native int lastChangeCount();

    native long lastInsertRow();

    native void native_execSQL(String str) throws SQLException;

    native void native_setLocale(String str, int i);

    public int status(int operation, boolean reset) {
        return native_status(operation, reset);
    }

    public void changePassword(String password) throws SQLiteException {
        if (!isOpen()) {
            throw new SQLiteException("database not open");
        } else if (password != null) {
            byte[] keyMaterial = getBytes(password.toCharArray());
            rekey(keyMaterial);
            byte[] arr$ = keyMaterial;
            int len$ = arr$.length;
            for (int i$ = OPEN_READWRITE; i$ < len$; i$ += OPEN_READ_MASK) {
                byte data = arr$[i$];
            }
        }
    }

    public void changePassword(char[] password) throws SQLiteException {
        if (!isOpen()) {
            throw new SQLiteException("database not open");
        } else if (password != null) {
            byte[] keyMaterial = getBytes(password);
            rekey(keyMaterial);
            byte[] arr$ = keyMaterial;
            int len$ = arr$.length;
            for (int i$ = OPEN_READWRITE; i$ < len$; i$ += OPEN_READ_MASK) {
                byte data = arr$[i$];
            }
        }
    }

    private static void loadICUData(Context context, File workingDir) {
        OutputStream out;
        Exception ex;
        Throwable th;
        OutputStream out2 = null;
        ZipInputStream in = null;
        File icuDir = new File(workingDir, "icu");
        File icuDataFile = new File(icuDir, "icudt46l.dat");
        try {
            if (!icuDir.exists()) {
                icuDir.mkdirs();
            }
            if (!icuDataFile.exists()) {
                ZipInputStream in2 = new ZipInputStream(context.getAssets().open("icudt46l.zip"));
                try {
                    in2.getNextEntry();
                    out = new FileOutputStream(icuDataFile);
                } catch (Exception e) {
                    ex = e;
                    in = in2;
                    try {
                        Log.e(TAG, "Error copying icu dat file", ex);
                        if (icuDataFile.exists()) {
                            icuDataFile.delete();
                        }
                        throw new RuntimeException(ex);
                    } catch (Throwable th2) {
                        th = th2;
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException ioe) {
                                Log.e(TAG, "Error in closing streams IO streams after expanding ICU dat file", ioe);
                                throw new RuntimeException(ioe);
                            }
                        }
                        if (out2 != null) {
                            out2.flush();
                            out2.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    in = in2;
                    if (in != null) {
                        in.close();
                    }
                    if (out2 != null) {
                        out2.flush();
                        out2.close();
                    }
                    throw th;
                }
                try {
                    byte[] buf = new byte[1024];
                    while (true) {
                        int len = in2.read(buf);
                        if (len <= 0) {
                            break;
                        }
                        out.write(buf, OPEN_READWRITE, len);
                    }
                    in = in2;
                    out2 = out;
                } catch (Exception e2) {
                    ex = e2;
                    in = in2;
                    out2 = out;
                    Log.e(TAG, "Error copying icu dat file", ex);
                    if (icuDataFile.exists()) {
                        icuDataFile.delete();
                    }
                    throw new RuntimeException(ex);
                } catch (Throwable th4) {
                    th = th4;
                    in = in2;
                    out2 = out;
                    if (in != null) {
                        in.close();
                    }
                    if (out2 != null) {
                        out2.flush();
                        out2.close();
                    }
                    throw th;
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ioe2) {
                    Log.e(TAG, "Error in closing streams IO streams after expanding ICU dat file", ioe2);
                    throw new RuntimeException(ioe2);
                }
            }
            if (out2 != null) {
                out2.flush();
                out2.close();
            }
        } catch (Exception e3) {
            ex = e3;
            Log.e(TAG, "Error copying icu dat file", ex);
            if (icuDataFile.exists()) {
                icuDataFile.delete();
            }
            throw new RuntimeException(ex);
        }
    }

    public static synchronized void loadLibs(Context context) {
        synchronized (SQLiteDatabase.class) {
            loadLibs(context, context.getFilesDir());
        }
    }

    public static synchronized void loadLibs(Context context, File workingDir) {
        synchronized (SQLiteDatabase.class) {
            System.loadLibrary("sqlcipher");
        }
    }

    void addSQLiteClosable(SQLiteClosable closable) {
        lock();
        try {
            this.mPrograms.put(closable, null);
        } finally {
            unlock();
        }
    }

    void removeSQLiteClosable(SQLiteClosable closable) {
        lock();
        try {
            this.mPrograms.remove(closable);
        } finally {
            unlock();
        }
    }

    protected void onAllReferencesReleased() {
        if (isOpen()) {
            if (SQLiteDebug.DEBUG_SQL_CACHE) {
                this.mTimeClosed = getTime();
            }
            dbclose();
            synchronized (sActiveDatabases) {
                sActiveDatabases.remove(this);
            }
        }
    }

    public void setLockingEnabled(boolean lockingEnabled) {
        this.mLockingEnabled = lockingEnabled;
    }

    void onCorruption() {
        Log.e(TAG, "Calling error handler for corrupt database (detected) " + this.mPath);
        this.mErrorHandler.onCorruption(this);
    }

    void lock() {
        if (this.mLockingEnabled) {
            this.mLock.lock();
            if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING && this.mLock.getHoldCount() == OPEN_READ_MASK) {
                this.mLockAcquiredWallTime = SystemClock.elapsedRealtime();
                this.mLockAcquiredThreadTime = Debug.threadCpuTimeNanos();
            }
        }
    }

    private void lockForced() {
        this.mLock.lock();
        if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING && this.mLock.getHoldCount() == OPEN_READ_MASK) {
            this.mLockAcquiredWallTime = SystemClock.elapsedRealtime();
            this.mLockAcquiredThreadTime = Debug.threadCpuTimeNanos();
        }
    }

    void unlock() {
        if (this.mLockingEnabled) {
            if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING && this.mLock.getHoldCount() == OPEN_READ_MASK) {
                checkLockHoldTime();
            }
            this.mLock.unlock();
        }
    }

    private void unlockForced() {
        if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING && this.mLock.getHoldCount() == OPEN_READ_MASK) {
            checkLockHoldTime();
        }
        this.mLock.unlock();
    }

    private void checkLockHoldTime() {
        long elapsedTime = SystemClock.elapsedRealtime();
        long lockedTime = elapsedTime - this.mLockAcquiredWallTime;
        if ((lockedTime >= 2000 || Log.isLoggable(TAG, CONFLICT_ABORT) || elapsedTime - this.mLastLockMessageTime >= 20000) && lockedTime > 300) {
            int threadTime = (int) ((Debug.threadCpuTimeNanos() - this.mLockAcquiredThreadTime) / 1000000);
            if (threadTime > LOCK_ACQUIRED_WARNING_THREAD_TIME_IN_MS || lockedTime > 2000) {
                this.mLastLockMessageTime = elapsedTime;
                String msg = "lock held on " + this.mPath + " for " + lockedTime + "ms. Thread time was " + threadTime + "ms";
                if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING_STACK_TRACE) {
                    Log.d(TAG, msg, new Exception());
                } else {
                    Log.d(TAG, msg);
                }
            }
        }
    }

    public void beginTransaction() {
        beginTransactionWithListener(null);
    }

    public void beginTransactionWithListener(SQLiteTransactionListener transactionListener) {
        lockForced();
        if (isOpen()) {
            try {
                if (this.mLock.getHoldCount() <= OPEN_READ_MASK) {
                    execSQL("BEGIN EXCLUSIVE;");
                    this.mTransactionListener = transactionListener;
                    this.mTransactionIsSuccessful = true;
                    this.mInnerTransactionIsSuccessful = false;
                    if (transactionListener != null) {
                        transactionListener.onBegin();
                    }
                    if (!true) {
                        unlockForced();
                    }
                } else if (this.mInnerTransactionIsSuccessful) {
                    IllegalStateException e = new IllegalStateException("Cannot call beginTransaction between calling setTransactionSuccessful and endTransaction");
                    Log.e(TAG, "beginTransaction() failed", e);
                    throw e;
                } else if (!true) {
                    unlockForced();
                }
            } catch (RuntimeException e2) {
                execSQL("ROLLBACK;");
                throw e2;
            } catch (Throwable th) {
                if (!false) {
                    unlockForced();
                }
            }
        } else {
            throw new IllegalStateException("database not open");
        }
    }

    public void endTransaction() {
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        } else if (this.mLock.isHeldByCurrentThread()) {
            RuntimeException savedException;
            try {
                if (this.mInnerTransactionIsSuccessful) {
                    this.mInnerTransactionIsSuccessful = false;
                } else {
                    this.mTransactionIsSuccessful = false;
                }
                if (this.mLock.getHoldCount() != OPEN_READ_MASK) {
                    this.mTransactionListener = null;
                    unlockForced();
                    return;
                }
                savedException = null;
                if (this.mTransactionListener != null) {
                    if (this.mTransactionIsSuccessful) {
                        this.mTransactionListener.onCommit();
                    } else {
                        this.mTransactionListener.onRollback();
                    }
                }
                if (this.mTransactionIsSuccessful) {
                    execSQL(COMMIT_SQL);
                } else {
                    try {
                        execSQL("ROLLBACK;");
                        if (savedException != null) {
                            throw savedException;
                        }
                    } catch (SQLException e) {
                        Log.d(TAG, "exception during rollback, maybe the DB previously performed an auto-rollback");
                    }
                }
                this.mTransactionListener = null;
                unlockForced();
            } catch (RuntimeException e2) {
                savedException = e2;
                this.mTransactionIsSuccessful = false;
            } catch (Throwable th) {
                this.mTransactionListener = null;
                unlockForced();
            }
        } else {
            throw new IllegalStateException("no transaction pending");
        }
    }

    public void setTransactionSuccessful() {
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        } else if (!this.mLock.isHeldByCurrentThread()) {
            throw new IllegalStateException("no transaction pending");
        } else if (this.mInnerTransactionIsSuccessful) {
            throw new IllegalStateException("setTransactionSuccessful may only be called once per call to beginTransaction");
        } else {
            this.mInnerTransactionIsSuccessful = true;
        }
    }

    public boolean inTransaction() {
        return this.mLock.getHoldCount() > 0;
    }

    public boolean isDbLockedByCurrentThread() {
        return this.mLock.isHeldByCurrentThread();
    }

    public boolean isDbLockedByOtherThreads() {
        return !this.mLock.isHeldByCurrentThread() && this.mLock.isLocked();
    }

    @Deprecated
    public boolean yieldIfContended() {
        if (isOpen()) {
            return yieldIfContendedHelper(false, -1);
        }
        return false;
    }

    public boolean yieldIfContendedSafely() {
        if (isOpen()) {
            return yieldIfContendedHelper(true, -1);
        }
        return false;
    }

    public boolean yieldIfContendedSafely(long sleepAfterYieldDelay) {
        if (isOpen()) {
            return yieldIfContendedHelper(true, sleepAfterYieldDelay);
        }
        return false;
    }

    private boolean yieldIfContendedHelper(boolean checkFullyYielded, long sleepAfterYieldDelay) {
        if (this.mLock.getQueueLength() == 0) {
            this.mLockAcquiredWallTime = SystemClock.elapsedRealtime();
            this.mLockAcquiredThreadTime = Debug.threadCpuTimeNanos();
            return false;
        }
        setTransactionSuccessful();
        SQLiteTransactionListener transactionListener = this.mTransactionListener;
        endTransaction();
        if (checkFullyYielded && isDbLockedByCurrentThread()) {
            throw new IllegalStateException("Db locked more than once. yielfIfContended cannot yield");
        }
        if (sleepAfterYieldDelay > 0) {
            long remainingDelay = sleepAfterYieldDelay;
            while (remainingDelay > 0) {
                long j;
                if (remainingDelay < 1000) {
                    j = remainingDelay;
                } else {
                    j = 1000;
                }
                try {
                    Thread.sleep(j);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
                remainingDelay -= 1000;
                if (this.mLock.getQueueLength() == 0) {
                    break;
                }
            }
        }
        beginTransactionWithListener(transactionListener);
        return true;
    }

    public Map<String, String> getSyncedTables() {
        HashMap<String, String> tables;
        synchronized (this.mSyncUpdateInfo) {
            tables = new HashMap();
            for (String table : this.mSyncUpdateInfo.keySet()) {
                SyncUpdateInfo info = (SyncUpdateInfo) this.mSyncUpdateInfo.get(table);
                if (info.deletedTable != null) {
                    tables.put(table, info.deletedTable);
                }
            }
        }
        return tables;
    }

    public static SQLiteDatabase openDatabase(String path, String password, CursorFactory factory, int flags) {
        return openDatabase(path, password, factory, flags, null);
    }

    public static SQLiteDatabase openDatabase(String path, char[] password, CursorFactory factory, int flags) {
        return openDatabase(path, password, factory, flags, null, null);
    }

    public static SQLiteDatabase openDatabase(String path, String password, CursorFactory factory, int flags, SQLiteDatabaseHook hook) {
        return openDatabase(path, password, factory, flags, hook, null);
    }

    public static SQLiteDatabase openDatabase(String path, char[] password, CursorFactory factory, int flags, SQLiteDatabaseHook hook) {
        return openDatabase(path, password, factory, flags, hook, null);
    }

    public static SQLiteDatabase openDatabase(String path, String password, CursorFactory factory, int flags, SQLiteDatabaseHook hook, DatabaseErrorHandler errorHandler) {
        return openDatabase(path, password == null ? null : password.toCharArray(), factory, flags, hook, errorHandler);
    }

    public static SQLiteDatabase openDatabase(String path, char[] password, CursorFactory factory, int flags, SQLiteDatabaseHook hook, DatabaseErrorHandler errorHandler) {
        SQLiteDatabaseCorruptException e;
        SQLiteDatabase sqliteDatabase = null;
        DatabaseErrorHandler myErrorHandler = errorHandler != null ? errorHandler : new DefaultDatabaseErrorHandler();
        try {
            SQLiteDatabase sqliteDatabase2 = new SQLiteDatabase(path, factory, flags, myErrorHandler);
            try {
                sqliteDatabase2.openDatabaseInternal(password, hook);
                sqliteDatabase = sqliteDatabase2;
            } catch (SQLiteDatabaseCorruptException e2) {
                e = e2;
                sqliteDatabase = sqliteDatabase2;
                Log.e(TAG, "Calling error handler for corrupt database " + path, e);
                myErrorHandler.onCorruption(sqliteDatabase);
                sqliteDatabase = new SQLiteDatabase(path, factory, flags, myErrorHandler);
                sqliteDatabase.openDatabaseInternal(password, hook);
                if (SQLiteDebug.DEBUG_SQL_STATEMENTS) {
                    sqliteDatabase.enableSqlTracing(path);
                }
                if (SQLiteDebug.DEBUG_SQL_TIME) {
                    sqliteDatabase.enableSqlProfiling(path);
                }
                synchronized (sActiveDatabases) {
                    sActiveDatabases.put(sqliteDatabase, null);
                }
                return sqliteDatabase;
            }
        } catch (SQLiteDatabaseCorruptException e3) {
            e = e3;
            Log.e(TAG, "Calling error handler for corrupt database " + path, e);
            myErrorHandler.onCorruption(sqliteDatabase);
            sqliteDatabase = new SQLiteDatabase(path, factory, flags, myErrorHandler);
            sqliteDatabase.openDatabaseInternal(password, hook);
            if (SQLiteDebug.DEBUG_SQL_STATEMENTS) {
                sqliteDatabase.enableSqlTracing(path);
            }
            if (SQLiteDebug.DEBUG_SQL_TIME) {
                sqliteDatabase.enableSqlProfiling(path);
            }
            synchronized (sActiveDatabases) {
                sActiveDatabases.put(sqliteDatabase, null);
            }
            return sqliteDatabase;
        }
        if (SQLiteDebug.DEBUG_SQL_STATEMENTS) {
            sqliteDatabase.enableSqlTracing(path);
        }
        if (SQLiteDebug.DEBUG_SQL_TIME) {
            sqliteDatabase.enableSqlProfiling(path);
        }
        synchronized (sActiveDatabases) {
            sActiveDatabases.put(sqliteDatabase, null);
        }
        return sqliteDatabase;
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, String password, CursorFactory factory, SQLiteDatabaseHook databaseHook) {
        return openOrCreateDatabase(file, password, factory, databaseHook, null);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, String password, CursorFactory factory, SQLiteDatabaseHook databaseHook) {
        return openDatabase(path, password, factory, (int) CREATE_IF_NECESSARY, databaseHook);
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, String password, CursorFactory factory, SQLiteDatabaseHook databaseHook, DatabaseErrorHandler errorHandler) {
        return openOrCreateDatabase(file == null ? null : file.getPath(), password, factory, databaseHook, errorHandler);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, String password, CursorFactory factory, SQLiteDatabaseHook databaseHook, DatabaseErrorHandler errorHandler) {
        return openDatabase(path, password == null ? null : password.toCharArray(), factory, (int) CREATE_IF_NECESSARY, databaseHook, errorHandler);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, char[] password, CursorFactory factory, SQLiteDatabaseHook databaseHook) {
        return openDatabase(path, password, factory, (int) CREATE_IF_NECESSARY, databaseHook);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, char[] password, CursorFactory factory, SQLiteDatabaseHook databaseHook, DatabaseErrorHandler errorHandler) {
        return openDatabase(path, password, factory, (int) CREATE_IF_NECESSARY, databaseHook, errorHandler);
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, String password, CursorFactory factory) {
        return openOrCreateDatabase(file, password, factory, null);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, String password, CursorFactory factory) {
        return openDatabase(path, password, factory, (int) CREATE_IF_NECESSARY, null);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, char[] password, CursorFactory factory) {
        return openDatabase(path, password, factory, (int) CREATE_IF_NECESSARY, null);
    }

    public static SQLiteDatabase create(CursorFactory factory, String password) {
        return openDatabase(MEMORY, password == null ? null : password.toCharArray(), factory, (int) CREATE_IF_NECESSARY);
    }

    public static SQLiteDatabase create(CursorFactory factory, char[] password) {
        return openDatabase(MEMORY, password, factory, (int) CREATE_IF_NECESSARY);
    }

    public void close() {
        if (isOpen()) {
            lock();
            try {
                closeClosable();
                onAllReferencesReleased();
            } finally {
                unlock();
            }
        }
    }

    private void closeClosable() {
        deallocCachedSqlStatements();
        for (Entry<SQLiteClosable, Object> entry : this.mPrograms.entrySet()) {
            SQLiteClosable program = (SQLiteClosable) entry.getKey();
            if (program != null) {
                program.onAllReferencesReleasedFromContainer();
            }
        }
    }

    public int getVersion() {
        Throwable th;
        SQLiteStatement prog = null;
        lock();
        if (isOpen()) {
            try {
                SQLiteStatement prog2 = new SQLiteStatement(this, "PRAGMA user_version;");
                try {
                    int simpleQueryForLong = (int) prog2.simpleQueryForLong();
                    if (prog2 != null) {
                        prog2.close();
                    }
                    unlock();
                    return simpleQueryForLong;
                } catch (Throwable th2) {
                    th = th2;
                    prog = prog2;
                    if (prog != null) {
                        prog.close();
                    }
                    unlock();
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (prog != null) {
                    prog.close();
                }
                unlock();
                throw th;
            }
        }
        throw new IllegalStateException("database not open");
    }

    public void setVersion(int version) {
        execSQL("PRAGMA user_version = " + version);
    }

    public long getMaximumSize() {
        Throwable th;
        SQLiteStatement prog = null;
        lock();
        if (isOpen()) {
            try {
                SQLiteStatement prog2 = new SQLiteStatement(this, "PRAGMA max_page_count;");
                try {
                    long pageSize = getPageSize() * prog2.simpleQueryForLong();
                    if (prog2 != null) {
                        prog2.close();
                    }
                    unlock();
                    return pageSize;
                } catch (Throwable th2) {
                    th = th2;
                    prog = prog2;
                    if (prog != null) {
                        prog.close();
                    }
                    unlock();
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (prog != null) {
                    prog.close();
                }
                unlock();
                throw th;
            }
        }
        throw new IllegalStateException("database not open");
    }

    public long setMaximumSize(long numBytes) {
        Throwable th;
        SQLiteStatement prog = null;
        lock();
        if (isOpen()) {
            try {
                long pageSize = getPageSize();
                long numPages = numBytes / pageSize;
                if (numBytes % pageSize != 0) {
                    numPages++;
                }
                SQLiteStatement prog2 = new SQLiteStatement(this, "PRAGMA max_page_count = " + numPages);
                try {
                    long simpleQueryForLong = prog2.simpleQueryForLong() * pageSize;
                    if (prog2 != null) {
                        prog2.close();
                    }
                    unlock();
                    return simpleQueryForLong;
                } catch (Throwable th2) {
                    th = th2;
                    prog = prog2;
                    if (prog != null) {
                        prog.close();
                    }
                    unlock();
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (prog != null) {
                    prog.close();
                }
                unlock();
                throw th;
            }
        }
        throw new IllegalStateException("database not open");
    }

    public long getPageSize() {
        Throwable th;
        SQLiteStatement prog = null;
        lock();
        if (isOpen()) {
            try {
                SQLiteStatement prog2 = new SQLiteStatement(this, "PRAGMA page_size;");
                try {
                    long size = prog2.simpleQueryForLong();
                    if (prog2 != null) {
                        prog2.close();
                    }
                    unlock();
                    return size;
                } catch (Throwable th2) {
                    th = th2;
                    prog = prog2;
                    if (prog != null) {
                        prog.close();
                    }
                    unlock();
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (prog != null) {
                    prog.close();
                }
                unlock();
                throw th;
            }
        }
        throw new IllegalStateException("database not open");
    }

    public void setPageSize(long numBytes) {
        execSQL("PRAGMA page_size = " + numBytes);
    }

    public void markTableSyncable(String table, String deletedTable) {
        if (isOpen()) {
            markTableSyncable(table, "_id", table, deletedTable);
            return;
        }
        throw new SQLiteException("database not open");
    }

    public void markTableSyncable(String table, String foreignKey, String updateTable) {
        if (isOpen()) {
            markTableSyncable(table, foreignKey, updateTable, null);
            return;
        }
        throw new SQLiteException("database not open");
    }

    private void markTableSyncable(String table, String foreignKey, String updateTable, String deletedTable) {
        lock();
        try {
            native_execSQL("SELECT _sync_dirty FROM " + updateTable + " LIMIT 0");
            native_execSQL("SELECT " + foreignKey + " FROM " + table + " LIMIT 0");
            SyncUpdateInfo info = new SyncUpdateInfo(updateTable, deletedTable, foreignKey);
            synchronized (this.mSyncUpdateInfo) {
                this.mSyncUpdateInfo.put(table, info);
            }
        } finally {
            unlock();
        }
    }

    void rowUpdated(String table, long rowId) {
        synchronized (this.mSyncUpdateInfo) {
            SyncUpdateInfo info = (SyncUpdateInfo) this.mSyncUpdateInfo.get(table);
        }
        if (info != null) {
            execSQL("UPDATE " + info.masterTable + " SET _sync_dirty=1 WHERE _id=(SELECT " + info.foreignKey + " FROM " + table + " WHERE _id=" + rowId + ")");
        }
    }

    public static String findEditTable(String tables) {
        if (TextUtils.isEmpty(tables)) {
            throw new IllegalStateException("Invalid tables");
        }
        int spacepos = tables.indexOf(32);
        int commapos = tables.indexOf(44);
        if (spacepos > 0 && (spacepos < commapos || commapos < 0)) {
            return tables.substring(OPEN_READWRITE, spacepos);
        }
        if (commapos <= 0) {
            return tables;
        }
        if (commapos < spacepos || spacepos < 0) {
            return tables.substring(OPEN_READWRITE, commapos);
        }
        return tables;
    }

    public SQLiteStatement compileStatement(String sql) throws SQLException {
        lock();
        if (isOpen()) {
            try {
                SQLiteStatement sQLiteStatement = new SQLiteStatement(this, sql);
                return sQLiteStatement;
            } finally {
                unlock();
            }
        } else {
            throw new IllegalStateException("database not open");
        }
    }

    public Cursor query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return queryWithFactory(null, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public Cursor queryWithFactory(CursorFactory cursorFactory, boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        if (isOpen()) {
            return rawQueryWithFactory(cursorFactory, SQLiteQueryBuilder.buildQueryString(distinct, table, columns, selection, groupBy, having, orderBy, limit), selectionArgs, findEditTable(table));
        }
        throw new IllegalStateException("database not open");
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return query(false, table, columns, selection, selectionArgs, groupBy, having, orderBy, null);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return query(false, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return rawQueryWithFactory(null, sql, selectionArgs, null);
    }

    public Cursor rawQueryWithFactory(CursorFactory cursorFactory, String sql, String[] selectionArgs, String editTable) {
        if (isOpen()) {
            long timeStart = 0;
            if (this.mSlowQueryThreshold != -1) {
                timeStart = System.currentTimeMillis();
            }
            SQLiteCursorDriver driver = new SQLiteDirectCursorDriver(this, sql, editTable);
            Cursor cursor = null;
            if (cursorFactory == null) {
                cursorFactory = this.mFactory;
            }
            try {
                cursor = driver.query(cursorFactory, selectionArgs);
                return new CrossProcessCursorWrapper(cursor);
            } finally {
                String str = -1;
                if (this.mSlowQueryThreshold != -1) {
                    int count = -1;
                    if (cursor != null) {
                        count = cursor.getCount();
                    }
                    long duration = System.currentTimeMillis() - timeStart;
                    if (duration >= ((long) this.mSlowQueryThreshold)) {
                        Log.v(TAG, "query (" + duration + " ms): " + driver.toString() + ", args are <redacted>, count is " + count);
                    }
                }
            }
        } else {
            throw new IllegalStateException("database not open");
        }
    }

    public Cursor rawQuery(String sql, String[] selectionArgs, int initialRead, int maxRead) {
        CursorWrapper cursorWrapper = (CursorWrapper) rawQueryWithFactory(null, sql, selectionArgs, null);
        ((SQLiteCursor) cursorWrapper.getWrappedCursor()).setLoadStyle(initialRead, maxRead);
        return cursorWrapper;
    }

    public long insert(String table, String nullColumnHack, ContentValues values) {
        try {
            return insertWithOnConflict(table, nullColumnHack, values, OPEN_READWRITE);
        } catch (SQLException e) {
            Log.e(TAG, "Error inserting <redacted values> into " + table, e);
            return -1;
        }
    }

    public long insertOrThrow(String table, String nullColumnHack, ContentValues values) throws SQLException {
        return insertWithOnConflict(table, nullColumnHack, values, OPEN_READWRITE);
    }

    public long replace(String table, String nullColumnHack, ContentValues initialValues) {
        try {
            return insertWithOnConflict(table, nullColumnHack, initialValues, CONFLICT_REPLACE);
        } catch (SQLException e) {
            Log.e(TAG, "Error inserting <redacted values> into " + table, e);
            return -1;
        }
    }

    public long replaceOrThrow(String table, String nullColumnHack, ContentValues initialValues) throws SQLException {
        return insertWithOnConflict(table, nullColumnHack, initialValues, CONFLICT_REPLACE);
    }

    public long insertWithOnConflict(String table, String nullColumnHack, ContentValues initialValues, int conflictAlgorithm) {
        if (isOpen()) {
            Iterator<Entry<String, Object>> entriesIter;
            StringBuilder sql = new StringBuilder(152);
            sql.append("INSERT");
            sql.append(CONFLICT_VALUES[conflictAlgorithm]);
            sql.append(" INTO ");
            sql.append(table);
            StringBuilder values = new StringBuilder(40);
            Set<Entry<String, Object>> entrySet = null;
            if (initialValues == null || initialValues.size() <= 0) {
                sql.append("(" + nullColumnHack + ") ");
                values.append("NULL");
            } else {
                entrySet = initialValues.valueSet();
                sql.append('(');
                boolean needSeparator = false;
                for (Entry<String, Object> entry : entrySet) {
                    if (needSeparator) {
                        sql.append(", ");
                        values.append(", ");
                    }
                    needSeparator = true;
                    sql.append((String) entry.getKey());
                    values.append('?');
                }
                sql.append(')');
            }
            sql.append(" VALUES(");
            sql.append(values);
            sql.append(");");
            lock();
            SQLiteStatement statement = null;
            try {
                statement = compileStatement(sql.toString());
                if (entrySet != null) {
                    int size = entrySet.size();
                    entriesIter = entrySet.iterator();
                    for (int i = OPEN_READWRITE; i < size; i += OPEN_READ_MASK) {
                        DatabaseUtils.bindObjectToProgram(statement, i + OPEN_READ_MASK, ((Entry) entriesIter.next()).getValue());
                    }
                }
                statement.execute();
                long insertedRowId = lastInsertRow();
                if (insertedRowId == -1) {
                    Log.e(TAG, "Error inserting <redacted values> using <redacted sql> into " + table);
                } else if (Log.isLoggable(TAG, CONFLICT_ABORT)) {
                    Log.v(TAG, "Inserting row " + insertedRowId + " from <redacted values> using <redacted sql> into " + table);
                }
                if (statement != null) {
                    statement.close();
                }
                unlock();
                return insertedRowId;
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            } catch (Throwable th) {
                if (statement != null) {
                    statement.close();
                }
                unlock();
            }
        } else {
            throw new IllegalStateException("database not open");
        }
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        lock();
        if (isOpen()) {
            SQLiteStatement statement = null;
            try {
                statement = compileStatement("DELETE FROM " + table + (!TextUtils.isEmpty(whereClause) ? " WHERE " + whereClause : BuildConfig.FLAVOR));
                if (whereArgs != null) {
                    int numArgs = whereArgs.length;
                    for (int i = OPEN_READWRITE; i < numArgs; i += OPEN_READ_MASK) {
                        DatabaseUtils.bindObjectToProgram(statement, i + OPEN_READ_MASK, whereArgs[i]);
                    }
                }
                statement.execute();
                int lastChangeCount = lastChangeCount();
                if (statement != null) {
                    statement.close();
                }
                unlock();
                return lastChangeCount;
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            } catch (Throwable th) {
                if (statement != null) {
                    statement.close();
                }
                unlock();
            }
        } else {
            throw new IllegalStateException("database not open");
        }
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        return updateWithOnConflict(table, values, whereClause, whereArgs, OPEN_READWRITE);
    }

    public int updateWithOnConflict(String table, ContentValues values, String whereClause, String[] whereArgs, int conflictAlgorithm) {
        if (values == null || values.size() == 0) {
            throw new IllegalArgumentException("Empty values");
        }
        StringBuilder sql = new StringBuilder(120);
        sql.append("UPDATE ");
        sql.append(CONFLICT_VALUES[conflictAlgorithm]);
        sql.append(table);
        sql.append(" SET ");
        Set<Entry<String, Object>> entrySet = values.valueSet();
        Iterator<Entry<String, Object>> entriesIter = entrySet.iterator();
        while (entriesIter.hasNext()) {
            sql.append((String) ((Entry) entriesIter.next()).getKey());
            sql.append("=?");
            if (entriesIter.hasNext()) {
                sql.append(", ");
            }
        }
        if (!TextUtils.isEmpty(whereClause)) {
            sql.append(" WHERE ");
            sql.append(whereClause);
        }
        lock();
        if (isOpen()) {
            SQLiteStatement statement = null;
            try {
                int i;
                statement = compileStatement(sql.toString());
                int size = entrySet.size();
                entriesIter = entrySet.iterator();
                int bindArg = OPEN_READ_MASK;
                for (i = OPEN_READWRITE; i < size; i += OPEN_READ_MASK) {
                    DatabaseUtils.bindObjectToProgram(statement, bindArg, ((Entry) entriesIter.next()).getValue());
                    bindArg += OPEN_READ_MASK;
                }
                if (whereArgs != null) {
                    size = whereArgs.length;
                    for (i = OPEN_READWRITE; i < size; i += OPEN_READ_MASK) {
                        statement.bindString(bindArg, whereArgs[i]);
                        bindArg += OPEN_READ_MASK;
                    }
                }
                statement.execute();
                int numChangedRows = lastChangeCount();
                if (Log.isLoggable(TAG, CONFLICT_ABORT)) {
                    Log.v(TAG, "Updated " + numChangedRows + " rows using <redacted values> and <redacted sql> for " + table);
                }
                if (statement != null) {
                    statement.close();
                }
                unlock();
                return numChangedRows;
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            } catch (SQLException e2) {
                Log.e(TAG, "Error updating <redacted values> using <redacted sql> for " + table);
                throw e2;
            } catch (Throwable th) {
                if (statement != null) {
                    statement.close();
                }
                unlock();
            }
        } else {
            throw new IllegalStateException("database not open");
        }
    }

    public void execSQL(String sql) throws SQLException {
        long timeStart = SystemClock.uptimeMillis();
        lock();
        if (isOpen()) {
            logTimeStat(this.mLastSqlStatement, timeStart, GET_LOCK_LOG_PREFIX);
            try {
                native_execSQL(sql);
                unlock();
                if (sql == COMMIT_SQL) {
                    logTimeStat(this.mLastSqlStatement, timeStart, COMMIT_SQL);
                } else {
                    logTimeStat(sql, timeStart, null);
                }
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            } catch (Throwable th) {
                unlock();
            }
        } else {
            throw new IllegalStateException("database not open");
        }
    }

    public void rawExecSQL(String sql) {
        long timeStart = SystemClock.uptimeMillis();
        lock();
        if (isOpen()) {
            logTimeStat(this.mLastSqlStatement, timeStart, GET_LOCK_LOG_PREFIX);
            try {
                native_rawExecSQL(sql);
                unlock();
                if (sql == COMMIT_SQL) {
                    logTimeStat(this.mLastSqlStatement, timeStart, COMMIT_SQL);
                } else {
                    logTimeStat(sql, timeStart, null);
                }
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            } catch (Throwable th) {
                unlock();
            }
        } else {
            throw new IllegalStateException("database not open");
        }
    }

    public void execSQL(String sql, Object[] bindArgs) throws SQLException {
        if (bindArgs == null) {
            throw new IllegalArgumentException("Empty bindArgs");
        }
        long timeStart = SystemClock.uptimeMillis();
        lock();
        if (isOpen()) {
            SQLiteStatement statement = null;
            try {
                statement = compileStatement(sql);
                if (bindArgs != null) {
                    int numArgs = bindArgs.length;
                    for (int i = OPEN_READWRITE; i < numArgs; i += OPEN_READ_MASK) {
                        DatabaseUtils.bindObjectToProgram(statement, i + OPEN_READ_MASK, bindArgs[i]);
                    }
                }
                statement.execute();
                if (statement != null) {
                    statement.close();
                }
                unlock();
                logTimeStat(sql, timeStart);
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            } catch (Throwable th) {
                if (statement != null) {
                    statement.close();
                }
                unlock();
            }
        } else {
            throw new IllegalStateException("database not open");
        }
    }

    protected void finalize() {
        if (isOpen()) {
            Log.e(TAG, "close() was never explicitly called on database '" + this.mPath + "' ", this.mStackTrace);
            closeClosable();
            onAllReferencesReleased();
        }
    }

    public SQLiteDatabase(String path, char[] password, CursorFactory factory, int flags) {
        this(path, factory, flags, null);
        openDatabaseInternal(password, null);
    }

    public SQLiteDatabase(String path, char[] password, CursorFactory factory, int flags, SQLiteDatabaseHook databaseHook) {
        this(path, factory, flags, null);
        openDatabaseInternal(password, databaseHook);
    }

    private SQLiteDatabase(String path, CursorFactory factory, int flags, DatabaseErrorHandler errorHandler) {
        this.mLock = new ReentrantLock(true);
        this.mLockAcquiredWallTime = 0;
        this.mLockAcquiredThreadTime = 0;
        this.mLastLockMessageTime = 0;
        this.mRandom = new Random();
        this.mLastSqlStatement = null;
        this.mNativeHandle = OPEN_READWRITE;
        this.mTempTableSequence = OPEN_READWRITE;
        this.mPathForLogs = null;
        this.mCompiledQueries = new HashMap();
        this.mMaxSqlCacheSize = MAX_SQL_CACHE_SIZE;
        this.mTimeOpened = null;
        this.mTimeClosed = null;
        this.mStackTrace = null;
        this.mLockingEnabled = true;
        this.mSyncUpdateInfo = new HashMap();
        if (path == null) {
            throw new IllegalArgumentException("path should not be null");
        }
        this.mFlags = flags;
        this.mPath = path;
        this.mSlowQueryThreshold = -1;
        this.mStackTrace = new DatabaseObjectNotClosedException().fillInStackTrace();
        this.mFactory = factory;
        this.mPrograms = new WeakHashMap();
        this.mErrorHandler = errorHandler;
    }

    private void openDatabaseInternal(char[] password, SQLiteDatabaseHook hook) {
        byte[] arr$;
        int len$;
        byte data;
        byte[] keyMaterial = getBytes(password);
        dbopen(this.mPath, this.mFlags);
        int i$;
        try {
            keyDatabase(hook, new 1(this, keyMaterial));
            if (false) {
                dbclose();
                if (SQLiteDebug.DEBUG_SQL_CACHE) {
                    this.mTimeClosed = getTime();
                }
            }
            if (keyMaterial != null && keyMaterial.length > 0) {
                arr$ = keyMaterial;
                len$ = arr$.length;
                for (i$ = OPEN_READWRITE; i$ < len$; i$ += OPEN_READ_MASK) {
                    data = arr$[i$];
                }
            }
        } catch (RuntimeException ex) {
            if (containsNull(password)) {
                keyDatabase(hook, new 2(this, password));
                if (keyMaterial != null && keyMaterial.length > 0) {
                    rekey(keyMaterial);
                }
                if (false) {
                    dbclose();
                    if (SQLiteDebug.DEBUG_SQL_CACHE) {
                        this.mTimeClosed = getTime();
                    }
                }
                if (keyMaterial != null && keyMaterial.length > 0) {
                    arr$ = keyMaterial;
                    len$ = arr$.length;
                    for (i$ = OPEN_READWRITE; i$ < len$; i$ += OPEN_READ_MASK) {
                        data = arr$[i$];
                    }
                    return;
                }
                return;
            }
            throw ex;
        } catch (Throwable th) {
            if (true) {
                dbclose();
                if (SQLiteDebug.DEBUG_SQL_CACHE) {
                    this.mTimeClosed = getTime();
                }
            }
            if (keyMaterial != null && keyMaterial.length > 0) {
                arr$ = keyMaterial;
                len$ = arr$.length;
                for (i$ = OPEN_READWRITE; i$ < len$; i$ += OPEN_READ_MASK) {
                    data = arr$[i$];
                }
            }
        }
    }

    private boolean containsNull(char[] data) {
        if (data == null || data.length <= 0) {
            return false;
        }
        char[] arr$ = data;
        int len$ = arr$.length;
        for (int i$ = OPEN_READWRITE; i$ < len$; i$ += OPEN_READ_MASK) {
            if (arr$[i$] == '\u0000') {
                return true;
            }
        }
        return false;
    }

    private void keyDatabase(SQLiteDatabaseHook databaseHook, Runnable keyOperation) {
        if (databaseHook != null) {
            databaseHook.preKey(this);
        }
        if (keyOperation != null) {
            keyOperation.run();
        }
        if (databaseHook != null) {
            databaseHook.postKey(this);
        }
        if (SQLiteDebug.DEBUG_SQL_CACHE) {
            this.mTimeOpened = getTime();
        }
        try {
            Cursor cursor = rawQuery("select count(*) from sqlite_master;", new String[OPEN_READWRITE]);
            if (cursor != null) {
                cursor.moveToFirst();
                int count = cursor.getInt(OPEN_READWRITE);
                cursor.close();
            }
        } catch (RuntimeException e) {
            Log.e(TAG, e.getMessage(), e);
            throw e;
        }
    }

    private String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ").format(Long.valueOf(System.currentTimeMillis()));
    }

    public boolean isReadOnly() {
        return (this.mFlags & OPEN_READ_MASK) == OPEN_READ_MASK;
    }

    public boolean isOpen() {
        return this.mNativeHandle != 0;
    }

    public boolean needUpgrade(int newVersion) {
        return newVersion > getVersion();
    }

    public final String getPath() {
        return this.mPath;
    }

    void logTimeStat(String sql, long beginMillis) {
        logTimeStat(sql, beginMillis, null);
    }

    void logTimeStat(String sql, long beginMillis, String prefix) {
        this.mLastSqlStatement = sql;
        long durationMillis = SystemClock.uptimeMillis() - beginMillis;
        if (durationMillis != 0 || prefix != GET_LOCK_LOG_PREFIX) {
            if (sQueryLogTimeInMillis == 0) {
                sQueryLogTimeInMillis = ExponentialBackOff.DEFAULT_INITIAL_INTERVAL_MILLIS;
            }
            if (durationMillis < ((long) sQueryLogTimeInMillis)) {
                if (this.mRandom.nextInt(LOCK_ACQUIRED_WARNING_THREAD_TIME_IN_MS) >= ((int) ((100 * durationMillis) / ((long) sQueryLogTimeInMillis))) + OPEN_READ_MASK) {
                    return;
                }
            }
            if (prefix != null) {
                sql = prefix + sql;
            }
            if (sql.length() > QUERY_LOG_SQL_LENGTH) {
                sql = sql.substring(OPEN_READWRITE, QUERY_LOG_SQL_LENGTH);
            }
            if ("unknown" == null) {
                String blockingPackage = BuildConfig.FLAVOR;
            }
        }
    }

    private String getPathForLogs() {
        if (this.mPathForLogs != null) {
            return this.mPathForLogs;
        }
        if (this.mPath == null) {
            return null;
        }
        if (this.mPath.indexOf(QUERY_LOG_SQL_LENGTH) == -1) {
            this.mPathForLogs = this.mPath;
        } else {
            this.mPathForLogs = EMAIL_IN_DB_PATTERN.matcher(this.mPath).replaceAll("XX@YY");
        }
        return this.mPathForLogs;
    }

    public void setLocale(Locale locale) {
        lock();
        try {
            native_setLocale(locale.toString(), this.mFlags);
        } finally {
            unlock();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void addToCompiledQueries(java.lang.String r7, net.sqlcipher.database.SQLiteCompiledSql r8) {
        /*
        r6 = this;
        r2 = r6.mMaxSqlCacheSize;
        if (r2 != 0) goto L_0x002f;
    L_0x0004:
        r2 = net.sqlcipher.database.SQLiteDebug.DEBUG_SQL_CACHE;
        if (r2 == 0) goto L_0x002e;
    L_0x0008:
        r2 = "Database";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "|NOT adding_sql_to_cache|";
        r3 = r3.append(r4);
        r4 = r6.getPath();
        r3 = r3.append(r4);
        r4 = "|";
        r3 = r3.append(r4);
        r3 = r3.append(r7);
        r3 = r3.toString();
        android.util.Log.v(r2, r3);
    L_0x002e:
        return;
    L_0x002f:
        r1 = 0;
        r3 = r6.mCompiledQueries;
        monitor-enter(r3);
        r2 = r6.mCompiledQueries;	 Catch:{ all -> 0x0041 }
        r2 = r2.get(r7);	 Catch:{ all -> 0x0041 }
        r0 = r2;
        r0 = (net.sqlcipher.database.SQLiteCompiledSql) r0;	 Catch:{ all -> 0x0041 }
        r1 = r0;
        if (r1 == 0) goto L_0x0044;
    L_0x003f:
        monitor-exit(r3);	 Catch:{ all -> 0x0041 }
        goto L_0x002e;
    L_0x0041:
        r2 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0041 }
        throw r2;
    L_0x0044:
        r2 = r6.mCompiledQueries;	 Catch:{ all -> 0x0041 }
        r2 = r2.size();	 Catch:{ all -> 0x0041 }
        r4 = r6.mMaxSqlCacheSize;	 Catch:{ all -> 0x0041 }
        if (r2 != r4) goto L_0x008b;
    L_0x004e:
        r2 = r6.mCacheFullWarnings;	 Catch:{ all -> 0x0041 }
        r2 = r2 + 1;
        r6.mCacheFullWarnings = r2;	 Catch:{ all -> 0x0041 }
        r4 = 1;
        if (r2 != r4) goto L_0x0089;
    L_0x0057:
        r2 = "Database";
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0041 }
        r4.<init>();	 Catch:{ all -> 0x0041 }
        r5 = "Reached MAX size for compiled-sql statement cache for database ";
        r4 = r4.append(r5);	 Catch:{ all -> 0x0041 }
        r5 = r6.getPath();	 Catch:{ all -> 0x0041 }
        r4 = r4.append(r5);	 Catch:{ all -> 0x0041 }
        r5 = "; i.e., NO space for this sql statement in cache: ";
        r4 = r4.append(r5);	 Catch:{ all -> 0x0041 }
        r4 = r4.append(r7);	 Catch:{ all -> 0x0041 }
        r5 = ". Please change your sql statements to use '?' for ";
        r4 = r4.append(r5);	 Catch:{ all -> 0x0041 }
        r5 = "bindargs, instead of using actual values";
        r4 = r4.append(r5);	 Catch:{ all -> 0x0041 }
        r4 = r4.toString();	 Catch:{ all -> 0x0041 }
        android.util.Log.w(r2, r4);	 Catch:{ all -> 0x0041 }
    L_0x0089:
        monitor-exit(r3);	 Catch:{ all -> 0x0041 }
        goto L_0x002e;
    L_0x008b:
        r2 = r6.mCompiledQueries;	 Catch:{ all -> 0x0041 }
        r2.put(r7, r8);	 Catch:{ all -> 0x0041 }
        r2 = net.sqlcipher.database.SQLiteDebug.DEBUG_SQL_CACHE;	 Catch:{ all -> 0x0041 }
        if (r2 == 0) goto L_0x0089;
    L_0x0094:
        r2 = "Database";
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0041 }
        r4.<init>();	 Catch:{ all -> 0x0041 }
        r5 = "|adding_sql_to_cache|";
        r4 = r4.append(r5);	 Catch:{ all -> 0x0041 }
        r5 = r6.getPath();	 Catch:{ all -> 0x0041 }
        r4 = r4.append(r5);	 Catch:{ all -> 0x0041 }
        r5 = "|";
        r4 = r4.append(r5);	 Catch:{ all -> 0x0041 }
        r5 = r6.mCompiledQueries;	 Catch:{ all -> 0x0041 }
        r5 = r5.size();	 Catch:{ all -> 0x0041 }
        r4 = r4.append(r5);	 Catch:{ all -> 0x0041 }
        r5 = "|";
        r4 = r4.append(r5);	 Catch:{ all -> 0x0041 }
        r4 = r4.append(r7);	 Catch:{ all -> 0x0041 }
        r4 = r4.toString();	 Catch:{ all -> 0x0041 }
        android.util.Log.v(r2, r4);	 Catch:{ all -> 0x0041 }
        goto L_0x0089;
        */
        throw new UnsupportedOperationException("Method not decompiled: net.sqlcipher.database.SQLiteDatabase.addToCompiledQueries(java.lang.String, net.sqlcipher.database.SQLiteCompiledSql):void");
    }

    private void deallocCachedSqlStatements() {
        synchronized (this.mCompiledQueries) {
            for (SQLiteCompiledSql compiledSql : this.mCompiledQueries.values()) {
                compiledSql.releaseSqlStatement();
            }
            this.mCompiledQueries.clear();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    net.sqlcipher.database.SQLiteCompiledSql getCompiledStatementForSql(java.lang.String r8) {
        /*
        r7 = this;
        r2 = 0;
        r4 = r7.mCompiledQueries;
        monitor-enter(r4);
        r3 = r7.mMaxSqlCacheSize;	 Catch:{ all -> 0x00bb }
        if (r3 != 0) goto L_0x002b;
    L_0x0008:
        r3 = net.sqlcipher.database.SQLiteDebug.DEBUG_SQL_CACHE;	 Catch:{ all -> 0x00bb }
        if (r3 == 0) goto L_0x0028;
    L_0x000c:
        r3 = "Database";
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00bb }
        r5.<init>();	 Catch:{ all -> 0x00bb }
        r6 = "|cache NOT found|";
        r5 = r5.append(r6);	 Catch:{ all -> 0x00bb }
        r6 = r7.getPath();	 Catch:{ all -> 0x00bb }
        r5 = r5.append(r6);	 Catch:{ all -> 0x00bb }
        r5 = r5.toString();	 Catch:{ all -> 0x00bb }
        android.util.Log.v(r3, r5);	 Catch:{ all -> 0x00bb }
    L_0x0028:
        r3 = 0;
        monitor-exit(r4);	 Catch:{ all -> 0x00bb }
    L_0x002a:
        return r3;
    L_0x002b:
        r3 = r7.mCompiledQueries;	 Catch:{ all -> 0x00bb }
        r3 = r3.get(r8);	 Catch:{ all -> 0x00bb }
        r0 = r3;
        r0 = (net.sqlcipher.database.SQLiteCompiledSql) r0;	 Catch:{ all -> 0x00bb }
        r2 = r0;
        if (r2 == 0) goto L_0x00b8;
    L_0x0037:
        r1 = 1;
    L_0x0038:
        monitor-exit(r4);	 Catch:{ all -> 0x00bb }
        if (r1 == 0) goto L_0x00be;
    L_0x003b:
        r3 = r7.mNumCacheHits;
        r3 = r3 + 1;
        r7.mNumCacheHits = r3;
    L_0x0041:
        r3 = net.sqlcipher.database.SQLiteDebug.DEBUG_SQL_CACHE;
        if (r3 == 0) goto L_0x00b5;
    L_0x0045:
        r3 = "Database";
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "|cache_stats|";
        r4 = r4.append(r5);
        r5 = r7.getPath();
        r4 = r4.append(r5);
        r5 = "|";
        r4 = r4.append(r5);
        r5 = r7.mCompiledQueries;
        r5 = r5.size();
        r4 = r4.append(r5);
        r5 = "|";
        r4 = r4.append(r5);
        r5 = r7.mNumCacheHits;
        r4 = r4.append(r5);
        r5 = "|";
        r4 = r4.append(r5);
        r5 = r7.mNumCacheMisses;
        r4 = r4.append(r5);
        r5 = "|";
        r4 = r4.append(r5);
        r4 = r4.append(r1);
        r5 = "|";
        r4 = r4.append(r5);
        r5 = r7.mTimeOpened;
        r4 = r4.append(r5);
        r5 = "|";
        r4 = r4.append(r5);
        r5 = r7.mTimeClosed;
        r4 = r4.append(r5);
        r5 = "|";
        r4 = r4.append(r5);
        r4 = r4.append(r8);
        r4 = r4.toString();
        android.util.Log.v(r3, r4);
    L_0x00b5:
        r3 = r2;
        goto L_0x002a;
    L_0x00b8:
        r1 = 0;
        goto L_0x0038;
    L_0x00bb:
        r3 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x00bb }
        throw r3;
    L_0x00be:
        r3 = r7.mNumCacheMisses;
        r3 = r3 + 1;
        r7.mNumCacheMisses = r3;
        goto L_0x0041;
        */
        throw new UnsupportedOperationException("Method not decompiled: net.sqlcipher.database.SQLiteDatabase.getCompiledStatementForSql(java.lang.String):net.sqlcipher.database.SQLiteCompiledSql");
    }

    public boolean isInCompiledSqlCache(String sql) {
        boolean containsKey;
        synchronized (this.mCompiledQueries) {
            containsKey = this.mCompiledQueries.containsKey(sql);
        }
        return containsKey;
    }

    public void purgeFromCompiledSqlCache(String sql) {
        synchronized (this.mCompiledQueries) {
            this.mCompiledQueries.remove(sql);
        }
    }

    public void resetCompiledSqlCache() {
        synchronized (this.mCompiledQueries) {
            this.mCompiledQueries.clear();
        }
    }

    public synchronized int getMaxSqlCacheSize() {
        return this.mMaxSqlCacheSize;
    }

    public synchronized void setMaxSqlCacheSize(int cacheSize) {
        if (cacheSize > MAX_SQL_CACHE_SIZE || cacheSize < 0) {
            throw new IllegalStateException("expected value between 0 and 250");
        } else if (cacheSize < this.mMaxSqlCacheSize) {
            throw new IllegalStateException("cannot set cacheSize to a value less than the value set with previous setMaxSqlCacheSize() call.");
        } else {
            this.mMaxSqlCacheSize = cacheSize;
        }
    }

    static ArrayList<DbStats> getDbStats() {
        ArrayList<DbStats> dbStatsList = new ArrayList();
        Iterator i$ = getActiveDatabases().iterator();
        while (i$.hasNext()) {
            SQLiteDatabase db = (SQLiteDatabase) i$.next();
            if (db != null && db.isOpen()) {
                int lookasideUsed = db.native_getDbLookaside();
                String path = db.getPath();
                int indx = path.lastIndexOf("/");
                String lastnode = path.substring(indx != -1 ? indx + OPEN_READ_MASK : OPEN_READWRITE);
                ArrayList<Pair<String, String>> attachedDbs = getAttachedDbs(db);
                if (attachedDbs != null) {
                    for (int i = OPEN_READWRITE; i < attachedDbs.size(); i += OPEN_READ_MASK) {
                        String dbName;
                        Pair<String, String> p = (Pair) attachedDbs.get(i);
                        long pageCount = getPragmaVal(db, ((String) p.first) + ".page_count;");
                        if (i == 0) {
                            dbName = lastnode;
                        } else {
                            lookasideUsed = OPEN_READWRITE;
                            dbName = "  (attached) " + ((String) p.first);
                            if (((String) p.second).trim().length() > 0) {
                                int idx = ((String) p.second).lastIndexOf("/");
                                dbName = dbName + " : " + ((String) p.second).substring(idx != -1 ? idx + OPEN_READ_MASK : OPEN_READWRITE);
                            }
                        }
                        if (pageCount > 0) {
                            dbStatsList.add(new DbStats(dbName, pageCount, db.getPageSize(), lookasideUsed));
                        }
                    }
                }
            }
        }
        return dbStatsList;
    }

    private static ArrayList<SQLiteDatabase> getActiveDatabases() {
        ArrayList<SQLiteDatabase> databases = new ArrayList();
        synchronized (sActiveDatabases) {
            databases.addAll(sActiveDatabases.keySet());
        }
        return databases;
    }

    private static long getPragmaVal(SQLiteDatabase db, String pragma) {
        Throwable th;
        if (!db.isOpen()) {
            return 0;
        }
        SQLiteStatement prog = null;
        try {
            SQLiteStatement prog2 = new SQLiteStatement(db, "PRAGMA " + pragma);
            try {
                long val = prog2.simpleQueryForLong();
                if (prog2 == null) {
                    return val;
                }
                prog2.close();
                return val;
            } catch (Throwable th2) {
                th = th2;
                prog = prog2;
                if (prog != null) {
                    prog.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (prog != null) {
                prog.close();
            }
            throw th;
        }
    }

    private static ArrayList<Pair<String, String>> getAttachedDbs(SQLiteDatabase dbObj) {
        if (!dbObj.isOpen()) {
            return null;
        }
        ArrayList<Pair<String, String>> attachedDbs = new ArrayList();
        Cursor c = dbObj.rawQuery("pragma database_list;", null);
        while (c.moveToNext()) {
            attachedDbs.add(new Pair(c.getString(OPEN_READ_MASK), c.getString(CONFLICT_ABORT)));
        }
        c.close();
        return attachedDbs;
    }

    private byte[] getBytes(char[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        ByteBuffer byteBuffer = Charset.forName(KEY_ENCODING).encode(CharBuffer.wrap(data));
        byte[] result = new byte[byteBuffer.limit()];
        byteBuffer.get(result);
        return result;
    }
}
