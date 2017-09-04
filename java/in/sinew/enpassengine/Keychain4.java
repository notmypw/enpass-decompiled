package in.sinew.enpassengine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.github.clans.fab.BuildConfig;
import in.sinew.enpassengine.Card.DBValidationResult;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Keychain4 {
    static String dbSignature = "WalletxDb";
    static int dbVersion = 4;
    Crypto mCrypto;
    SQLiteDatabase mDatabase;
    public String mDbFilename;
    DbHelper mDbHelper;
    byte[] mHashData = new byte[16];
    byte[] mIv = new byte[16];
    byte[] mSalt = new byte[16];
    int mVersion = dbVersion;

    public Keychain4(Context aContext) {
    }

    public static Keychain4 openOrCreate(Context aContext, String aFilename, String aPassword) {
        Keychain4 keychain = new Keychain4(aContext);
        keychain.mDbFilename = aFilename;
        keychain.mDbHelper = new DbHelper(aContext, aFilename, null, dbVersion);
        keychain.mDatabase = keychain.mDbHelper.getWritableDatabase();
        if (!keychain.restoreIdentities()) {
            keychain.createIdentities(aPassword);
        }
        if (keychain.isValidPassword(aPassword)) {
            keychain.mCrypto = new Crypto(aPassword, keychain.mSalt, keychain.mIv, keychain.mVersion);
            keychain.resetPoolForRow(keychain.mCrypto, 1, aPassword);
            return keychain;
        }
        keychain.close();
        return null;
    }

    public void clearKeychain() {
        this.mDatabase.delete("Cards", null, null);
        this.mDatabase.delete("Folders", null, null);
        this.mDatabase.delete("Folder_Cards", null, null);
        this.mDatabase.delete("Favorites", null, null);
        this.mDatabase.delete("Urls", null, null);
    }

    public void resetPoolForRow(Crypto aOldCrypto, int aRowID, String aPassword) {
        if (aOldCrypto == null) {
            aOldCrypto = this.mCrypto;
        }
        for (int count = 1; count <= 6; count++) {
            if (count != aRowID) {
                setPoolDataForRow(aOldCrypto, count, false, BuildConfig.FLAVOR);
            } else {
                setPoolDataForRow(aOldCrypto, count, true, aPassword);
            }
        }
    }

    public void setPoolDataForRow(Crypto aOldCrypto, int aRowID, boolean aReplace, String aPassword) {
        boolean exist = false;
        String password = BuildConfig.FLAVOR;
        String[] result_columns = new String[]{"Data"};
        String where = "UID = '" + aRowID + "'";
        try {
            Cursor selectedRow = this.mDatabase.query("Pool", result_columns, where, null, null, null, null);
            if (selectedRow.moveToFirst()) {
                password = Utils.unpad(new String(aOldCrypto.decrypt(selectedRow.getBlob(selectedRow.getColumnIndexOrThrow("Data")))));
                exist = true;
            }
            selectedRow.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (aReplace) {
            password = aPassword;
        }
        byte[] encPasswordData = null;
        try {
            encPasswordData = this.mCrypto.encrypt(Utils.pad(password).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        ContentValues newValues = new ContentValues();
        newValues.put("Data", encPasswordData);
        if (exist) {
            this.mDatabase.update("Pool", newValues, where, null);
        } else {
            this.mDatabase.insert("Pool", null, newValues);
        }
    }

    public static DBValidationResult isValidDatabase(Context aContext, String aFilename) {
        SQLiteDatabase externalDb = new DBValidationResultHelper(aContext, aFilename, null, dbVersion).getReadableDatabase();
        DBValidationResult result = DBValidationResult.DBIsInvalid;
        String[] result_columns = new String[]{"Version", "Signature"};
        String where = "ID=1";
        String exSign = BuildConfig.FLAVOR;
        try {
            Cursor selectedRow = externalDb.query("Identity", result_columns, where, null, null, null, null);
            if (selectedRow.moveToFirst()) {
                int exVersion = selectedRow.getInt(selectedRow.getColumnIndexOrThrow("Version"));
                exSign = selectedRow.getString(selectedRow.getColumnIndexOrThrow("Signature"));
                selectedRow.close();
                if (exSign.equals(dbSignature)) {
                    if (exVersion > dbVersion) {
                        result = DBValidationResult.DBIsAdvanced;
                    } else if (exVersion == 1) {
                        result = DBValidationResult.DBIsTooOld;
                    } else if (exVersion < dbVersion) {
                        result = DBValidationResult.DBIsOlder;
                    } else {
                        result = DBValidationResult.DBIsValid;
                    }
                }
                externalDb.close();
            }
        } catch (Exception e) {
        }
        return result;
    }

    void restoreCryptoVars(byte[] aInfo) {
        int count = 0;
        while (count <= 15) {
            this.mHashData[count] = aInfo[count];
            count++;
        }
        int valueCount = 0;
        while (count <= 31) {
            this.mIv[valueCount] = aInfo[count];
            valueCount++;
            count++;
        }
        valueCount = 0;
        while (count <= 47) {
            this.mSalt[valueCount] = aInfo[count];
            valueCount++;
            count++;
        }
    }

    private boolean restoreIdentities() {
        boolean recordExist = false;
        String[] result_columns = new String[]{"Version", "Info"};
        Cursor selectedRow = this.mDatabase.query("Identity", result_columns, "ID=1", null, null, null, null);
        if (selectedRow.moveToFirst()) {
            byte[] cryptoInfo = selectedRow.getBlob(selectedRow.getColumnIndexOrThrow("Info"));
            this.mVersion = selectedRow.getInt(selectedRow.getColumnIndexOrThrow("Version"));
            restoreCryptoVars(cryptoInfo);
            recordExist = true;
        }
        selectedRow.close();
        return recordExist;
    }

    private void createIdentities(String aPassword) {
        byte[] infoData = new byte[48];
        new SecureRandom().nextBytes(infoData);
        restoreCryptoVars(infoData);
        byte[] passwordHash = getHashForPassword(aPassword);
        String uuid = Utils.generateUUID();
        ContentValues newValues = new ContentValues();
        newValues.put("ID", Integer.valueOf(1));
        newValues.put("Version", Integer.valueOf(dbVersion));
        newValues.put("Signature", dbSignature);
        newValues.put("Sync_UUID", uuid);
        newValues.put("Hash", passwordHash);
        newValues.put("Info", infoData);
        this.mDatabase.insert("Identity", null, newValues);
    }

    byte[] getCurrentPasswordHash() {
        String[] result_columns = new String[]{"Hash"};
        byte[] hash = null;
        Cursor selectedRow = this.mDatabase.query("Identity", result_columns, "ID=1", null, null, null, null);
        if (selectedRow.moveToFirst()) {
            hash = selectedRow.getBlob(selectedRow.getColumnIndexOrThrow("Hash"));
        }
        selectedRow.close();
        return hash;
    }

    public boolean isValidPassword(String aPassword) {
        return Arrays.equals(getHashForPassword(aPassword), getCurrentPasswordHash());
    }

    byte[] getHashForPassword(String aPassword) {
        return Crypto.hash(new Crypto(aPassword, this.mSalt, this.mIv, this.mVersion).encrypt(this.mHashData));
    }

    public void close() {
        this.mDatabase.close();
    }

    public List<Card> getAllCards() {
        Cursor cursor = this.mDatabase.rawQuery("Select Data From Cards where Trashed = 0", null);
        byte[] cardData = null;
        ArrayList<Card> list = new ArrayList();
        if (cursor == null || !cursor.moveToFirst()) {
            return list;
        }
        do {
            try {
                cardData = this.mCrypto.decrypt(cursor.getBlob(cursor.getColumnIndexOrThrow("Data")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Card card = new Card();
            try {
                card.read4(cardData);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            list.add(card);
        } while (cursor.moveToNext());
        return list;
    }

    public ArrayList<String> getAllFavoriteCardUuids() {
        Cursor cursor = this.mDatabase.rawQuery("Select CardUUID from favorites where Trashed = 0", null);
        ArrayList<String> list = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex("CardUUID")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public FavoriteData getFavoriteData(String uuid) {
        Cursor cursor = this.mDatabase.rawQuery("Select UpdateTime, Trashed from Cards where UUID = '" + uuid + "'", null);
        if (cursor.moveToFirst()) {
            return new FavoriteData(uuid, (long) cursor.getInt(cursor.getColumnIndex("UpdateTime")), false);
        }
        return null;
    }

    public ArrayList<String> getAllFolderUuids() {
        Cursor cursor = this.mDatabase.rawQuery("Select UUID from folders where Trashed = 0", null);
        ArrayList<String> list = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex("UUID")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public FolderData getFolderDataForUuid(String aUuid) {
        Cursor selectedRow = this.mDatabase.rawQuery("select * from folders where UUID = '" + aUuid + "'", null);
        boolean trash = false;
        String title = BuildConfig.FLAVOR;
        long updateTime = 0;
        String parent = BuildConfig.FLAVOR;
        int iconId = 0;
        if (selectedRow.moveToFirst()) {
            title = selectedRow.getString(selectedRow.getColumnIndexOrThrow("Title"));
            updateTime = (long) selectedRow.getInt(selectedRow.getColumnIndexOrThrow("UpdateTime"));
            parent = selectedRow.getString(selectedRow.getColumnIndexOrThrow("Parent"));
            trash = selectedRow.getInt(selectedRow.getColumnIndexOrThrow("Trashed")) != 0;
            iconId = selectedRow.getInt(selectedRow.getColumnIndexOrThrow("IconID"));
        }
        return new FolderData(title, (double) updateTime, aUuid, parent, trash, iconId);
    }

    public ArrayList<FolderCardData> getAllFolderCardsData() {
        Cursor cursor = this.mDatabase.rawQuery("Select FolderUUID, CardUUID, UpdateTime from Folder_Cards where Trashed = 0", null);
        ArrayList<FolderCardData> list = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                list.add(new FolderCardData(cursor.getString(cursor.getColumnIndex("FolderUUID")), cursor.getString(cursor.getColumnIndex("CardUUID")), (double) ((long) cursor.getInt(cursor.getColumnIndexOrThrow("UpdateTime"))), false));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public String getPoolDataForRow(int aRowID) {
        String password = BuildConfig.FLAVOR;
        try {
            Cursor selectedRow = this.mDatabase.query("Pool", new String[]{"Data"}, "UID = '" + aRowID + "'", null, null, null, null);
            if (selectedRow.moveToFirst()) {
                password = Utils.unpad(new String(this.mCrypto.decrypt(selectedRow.getBlob(selectedRow.getColumnIndexOrThrow("Data")))));
            }
            selectedRow.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }
}
