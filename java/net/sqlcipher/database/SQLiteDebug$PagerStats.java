package net.sqlcipher.database;

import java.util.ArrayList;
import net.sqlcipher.database.SQLiteDebug.DbStats;

public class SQLiteDebug$PagerStats {
    @Deprecated
    public long databaseBytes;
    public ArrayList<DbStats> dbStats;
    public int largestMemAlloc;
    public int memoryUsed;
    @Deprecated
    public int numPagers;
    public int pageCacheOverflo;
    @Deprecated
    public long referencedBytes;
    @Deprecated
    public long totalBytes;
}
