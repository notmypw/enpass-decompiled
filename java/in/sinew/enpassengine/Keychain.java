package in.sinew.enpassengine;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.dropbox.core.android.AuthActivity;
import com.github.clans.fab.BuildConfig;
import in.sinew.enpassengine.Card.DBValidationResult;
import in.sinew.enpassengine.CardField.CardFieldType;
import in.sinew.enpassengine.IKeychainDelegate.KeychainChangeType;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabase.CursorFactory;
import net.sqlcipher.database.SQLiteDatabaseHook;
import net.sqlcipher.database.SQLiteException;
import net.sqlcipher.database.SQLiteOpenHelper;
import net.sqlcipher.database.SQLiteStatement;
import org.json.JSONException;
import org.json.JSONObject;

public class Keychain {
    public static int ANDROID_WATCH_PIN_CODE = 14;
    public static int APPLE_WATCH_PIN_CODE = 13;
    private static int LOCAL_REMOTE = 1;
    private static int PIN = 7;
    private static final String TAG = "KEYCAHIN";
    public static final String WATCH_FOLDER_UUID = "watch-folder-uuid";
    public static int WEBDAV_REMOTE_USERNAME = 10;
    public static int WEBDAW_REMOTE_PASSWORD = 11;
    private static String dbSignature = "WalletxDb";
    public static int dbVersion = 5;
    static String mRandomPassword = null;
    private Crypto mCrypto;
    private SQLiteDatabase mDatabase;
    private String mDbFilename;
    private DbHelper mDbHelper;
    private byte[] mHashData = new byte[16];
    private byte[] mIv = new byte[16];
    IKeychainDelegate mKeychainDelegate = null;
    private byte[] mSalt = new byte[16];
    private int mVersion = dbVersion;

    private static class DbHelper extends SQLiteOpenHelper {
        static SQLiteDatabaseHook hook = new 1();

        public DbHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version, hook);
        }

        public void onCreate(SQLiteDatabase aDatabase) {
            createIdentityTable(aDatabase);
            createCardsTable(aDatabase);
            createFavoriteTable(aDatabase);
            createFoldersTable(aDatabase);
            createPoolTable(aDatabase);
            createPasswordHistoryTable(aDatabase);
            createAttachmentTable(aDatabase);
        }

        public void onUpgrade(SQLiteDatabase aDatabase, int oldVersion, int newVersion) {
        }

        public SQLiteDatabase openDb(char[] password) {
            SQLiteDatabase writeableDb;
            Cursor c = null;
            try {
                writeableDb = getWritableDatabase(password);
                c = writeableDb.rawQuery("SELECT count(*) FROM sqlite_master", null);
                c.getCount();
                if (c != null) {
                    c.close();
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
                writeableDb = null;
                if (c != null) {
                    c.close();
                }
            } catch (Throwable th) {
                if (c != null) {
                    c.close();
                }
            }
            return writeableDb;
        }

        private void createIdentityTable(SQLiteDatabase aDatabase) {
            aDatabase.execSQL("CREATE TABLE IF NOT EXISTS Identity (ID INTEGER PRIMARY KEY AUTOINCREMENT CHECK(ID ==1), Version INTEGER, Signature TEXT, Sync_UUID TEXT, Hash TEXT, Info BLOB);");
        }

        private void createCardsTable(SQLiteDatabase aDatabase) {
            aDatabase.execSQL("CREATE TABLE IF NOT EXISTS Cards (ID INTEGER PRIMARY KEY AUTOINCREMENT,SubTitle TEXT, Title TEXT, Type TEXT, Category TEXT, IconID INTEGER, CustomIconId TEXT, UpdateTime INTEGER, UUID TEXT UNIQUE NOT NULL, Data BLOB, Trashed INTEGER,Deleted INTEGER, Urls TEXT, FormFields TEXT);");
        }

        private void createFavoriteTable(SQLiteDatabase aDatabase) {
            aDatabase.execSQL("CREATE TABLE IF NOT EXISTS Favorites (ID INTEGER PRIMARY KEY AUTOINCREMENT,CardUUID TEXT UNIQUE NOT NULL, UpdateTime INTEGER, Trashed INTEGER);");
        }

        private void createFoldersTable(SQLiteDatabase aDatabase) {
            aDatabase.execSQL("CREATE TABLE IF NOT EXISTS Folders (ID INTEGER PRIMARY KEY AUTOINCREMENT,Title TEXT, UpdateTime INTEGER, UUID TEXT UNIQUE NOT NULL, Parent TEXT, Trashed INTEGER, IconID INTEGER);");
            aDatabase.execSQL("CREATE TABLE IF NOT EXISTS Folder_Cards (ID INTEGER PRIMARY KEY AUTOINCREMENT,FolderUUID TEXT NOT NULL, CardUUID TEXT NOT NULL, UpdateTime INTEGER, Trashed INTEGER);");
        }

        private void createPoolTable(SQLiteDatabase aDatabase) {
            aDatabase.execSQL("CREATE TABLE IF NOT EXISTS Pool (UID INTEGER PRIMARY KEY ,Data BLOB);");
        }

        private void createPasswordHistoryTable(SQLiteDatabase aDatabase) {
            aDatabase.execSQL("CREATE TABLE IF NOT EXISTS Password_History (ID INTEGER PRIMARY KEY AUTOINCREMENT , Password Text,Timestamp INTEGER, Domain Text); ");
        }

        private void createAttachmentTable(SQLiteDatabase aDatabase) {
            aDatabase.execSQL("CREATE TABLE IF NOT EXISTS Attachment (ID INTEGER PRIMARY KEY AUTOINCREMENT , UUID TEXT UNIQUE NOT NULL ,CardUUID TEXT NOT NULL,MetaData TEXT,Data BLOB,Trashed INTEGER,Timestamp INTEGER); ");
        }
    }

    static class ValidationResultHelper extends SQLiteOpenHelper {
        static SQLiteDatabaseHook hook = new 1();

        public ValidationResultHelper(Context context, String filePath, CursorFactory factory, int version) {
            super(context, filePath, factory, version, hook);
        }

        public void onCreate(SQLiteDatabase arg0) {
        }

        public void onUpgrade(SQLiteDatabase aDatabase, int oldVersion, int newVersion) {
        }

        public SQLiteDatabase open(char[] password) {
            SQLiteDatabase externalDb = null;
            try {
                externalDb = getWritableDatabase(password);
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            return externalDb;
        }
    }

    public static void loadCipherLibs(Context context) {
        SQLiteDatabase.loadLibs(context);
    }

    public Keychain(Context aContext) {
    }

    public void setDelegate(IKeychainDelegate aDelegate) {
        this.mKeychainDelegate = aDelegate;
    }

    public static DBValidationResult isValidDatabase(Context context, String filePath, char[] password) {
        ValidationResultHelper helper = new ValidationResultHelper(context, filePath, null, dbVersion);
        DBValidationResult result = DBValidationResult.DBIsInvalid;
        String[] result_columns = new String[]{"Version", "Signature"};
        String where = "ID=1";
        String exSign = BuildConfig.FLAVOR;
        Cursor c = null;
        try {
            SQLiteDatabase externalDb = helper.open(password);
            if (externalDb != null) {
                c = externalDb.rawQuery("SELECT count(*) FROM sqlite_master", null);
                if (c.getCount() >= 1) {
                    result = DBValidationResult.DBResultPasswordOk;
                }
                Cursor selectedRow = externalDb.query("Identity", result_columns, where, null, null, null, null);
                if (selectedRow.moveToFirst()) {
                    int exVersion = selectedRow.getInt(selectedRow.getColumnIndexOrThrow("Version"));
                    exSign = selectedRow.getString(selectedRow.getColumnIndexOrThrow("Signature"));
                    selectedRow.close();
                    if (!exSign.equals(dbSignature)) {
                        result = DBValidationResult.DBIsInvalid;
                    } else if (exVersion > dbVersion) {
                        result = DBValidationResult.DBIsAdvanced;
                    } else if (exVersion == 1) {
                        result = DBValidationResult.DBIsTooOld;
                    } else if (exVersion < dbVersion) {
                        result = DBValidationResult.DBIsOlder;
                    }
                    externalDb.close();
                }
            } else {
                result = DBValidationResult.CipherDBIsInValidOrPasswordMissmatch;
            }
            if (c != null) {
                c.close();
            }
        } catch (SQLiteException e) {
            result = DBValidationResult.DBIsInvalid;
            e.printStackTrace();
            if (c != null) {
                c.close();
            }
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
        return result;
    }

    public static Keychain openOrCreate(Context aContext, String aFilename, char[] password) {
        Keychain keychain = new Keychain(aContext);
        keychain.mDbFilename = aFilename;
        keychain.mDbHelper = new DbHelper(aContext, aFilename, null, dbVersion);
        keychain.mDatabase = keychain.mDbHelper.openDb(password);
        if (keychain.mDatabase == null) {
            return null;
        }
        if (!keychain.restoreIdentities()) {
            keychain.createIdentities();
        }
        try {
            keychain.mDbHelper.createAttachmentTable(keychain.mDatabase);
            keychain.mCrypto = new Crypto(mRandomPassword, keychain.mSalt, keychain.mIv, keychain.mVersion);
            keychain.setPoolDataForRow(LOCAL_REMOTE, new String(password).getBytes("UTF-8"));
            return keychain;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return keychain;
        }
    }

    private long createIdentities() {
        byte[] infoData = new byte[48];
        new SecureRandom().nextBytes(infoData);
        restoreCryptoVars(infoData);
        mRandomPassword = generateRandomPassword();
        String uuid = Utils.generateUUID();
        ContentValues newValues = new ContentValues();
        newValues.put("ID", Integer.valueOf(1));
        newValues.put("Version", Integer.valueOf(dbVersion));
        newValues.put("Signature", dbSignature);
        newValues.put("Sync_UUID", uuid);
        newValues.put("Hash", mRandomPassword);
        newValues.put("Info", infoData);
        return this.mDatabase.insert("Identity", null, newValues);
    }

    private boolean restoreIdentities() {
        boolean recordExist = false;
        String[] result_columns = new String[]{"Version", "Info", "Hash"};
        Cursor selectedRow = this.mDatabase.query("Identity", result_columns, "ID=1", null, null, null, null);
        if (selectedRow.moveToFirst()) {
            byte[] cryptoInfo = selectedRow.getBlob(selectedRow.getColumnIndexOrThrow("Info"));
            this.mVersion = selectedRow.getInt(selectedRow.getColumnIndexOrThrow("Version"));
            mRandomPassword = selectedRow.getString(selectedRow.getColumnIndexOrThrow("Hash"));
            restoreCryptoVars(cryptoInfo);
            recordExist = true;
        }
        selectedRow.close();
        return recordExist;
    }

    public static Keychain upgrade(Context context, String fileName, char[] password) {
        DBValidationResult result = Keychain4.isValidDatabase(context, fileName);
        if (result == DBValidationResult.DBIsValid || result == DBValidationResult.DBIsOlder) {
            Keychain4 keychain = Keychain4.openOrCreate(context, fileName, new String(password));
            if (keychain == null) {
                return null;
            }
            String mDbFilename = fileName;
            String newKeychainName = mDbFilename + ".upgrade";
            Utils.delete(newKeychainName, context);
            Keychain newKeychain = openOrCreate(context, newKeychainName, password);
            if (keychain.mVersion == 2) {
                for (Card card : keychain.getAllCards()) {
                    card.upgradeCard();
                    card.clearAllLabel();
                    newKeychain.addCard(card);
                    card.wipe();
                }
                keychain.close();
                newKeychain.close();
                Utils.restoreDbFromFile(mDbFilename, newKeychainName, true, context);
            } else if (keychain.mVersion == 3) {
                for (Card card2 : keychain.getAllCards()) {
                    card2.upgradeCard();
                    card2.clearAllLabel();
                    newKeychain.addCard(card2);
                    card2.wipe();
                }
                keychain.mDatabase.execSQL("alter table Folders add column IconID INTEGER default 1008");
                upgradeFavoriteFolderFolderCardsTable(keychain, newKeychain);
                upgradePoolTable(keychain, newKeychain);
                keychain.close();
                newKeychain.close();
                Utils.restoreDbFromFile(mDbFilename, newKeychainName, true, context);
            } else if (keychain.mVersion == 4) {
                for (Card card22 : keychain.getAllCards()) {
                    card22.upgradeCard();
                    newKeychain.addCard(card22);
                    card22.wipe();
                }
                upgradeFavoriteFolderFolderCardsTable(keychain, newKeychain);
                upgradePoolTable(keychain, newKeychain);
                keychain.close();
                newKeychain.close();
                Utils.restoreDbFromFile(mDbFilename, newKeychainName, true, context);
            }
        }
        return openOrCreate(context, fileName, password);
    }

    private static void upgradePoolTable(Keychain4 oldKeychain, Keychain newKeychain) {
        for (int rowId = 1; rowId <= 5; rowId++) {
            String pass = oldKeychain.getPoolDataForRow(rowId);
            if (!TextUtils.isEmpty(pass)) {
                try {
                    newKeychain.setPoolDataForRow(rowId, pass.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void upgradeFavoriteFolderFolderCardsTable(Keychain4 oldKeychain, Keychain newKeychain) {
        for (String uuid : oldKeychain.getAllFavoriteCardUuids()) {
            newKeychain.addUpgradedFavorite(oldKeychain.getFavoriteData(uuid));
        }
        for (String uuid2 : oldKeychain.getAllFolderUuids()) {
            newKeychain.addUpgradedFolder(oldKeychain.getFolderDataForUuid(uuid2));
        }
        for (FolderCardData folderCard : oldKeychain.getAllFolderCardsData()) {
            newKeychain.addUpgradedFolderCard(folderCard);
        }
    }

    private long addUpgradedFavorite(FavoriteData aFavoriteCard) {
        String cardUuid = aFavoriteCard.getUuid();
        double timeInSecs = (double) aFavoriteCard.mTimestamp;
        ContentValues newValues = new ContentValues();
        newValues.put("CardUUID", cardUuid);
        newValues.put("UpdateTime", Double.valueOf(timeInSecs));
        newValues.put("Trashed", Boolean.valueOf(aFavoriteCard.getTrashed()));
        return this.mDatabase.insert("Favorites", null, newValues);
    }

    private long addUpgradedFolderCard(FolderCardData aFolderCardData) {
        double updateTime = aFolderCardData.mUpdateTime;
        String aFolderUuid = aFolderCardData.getFolderUuid();
        String aCardUuid = aFolderCardData.getCardUuid();
        ContentValues newValues = new ContentValues();
        newValues.put("FolderUUID", aFolderUuid);
        newValues.put("CardUUID", aCardUuid);
        newValues.put("UpdateTime", Double.valueOf(updateTime));
        newValues.put("Trashed", Boolean.valueOf(aFolderCardData.getTrashed()));
        return this.mDatabase.insert("Folder_Cards", null, newValues);
    }

    private long addUpgradedFolder(FolderData aFolderData) {
        String folderUuid = aFolderData.getUuid();
        double updateTime = aFolderData.mUpdateTime;
        String title = aFolderData.getTitle();
        String parent = aFolderData.getParent();
        boolean trashed = aFolderData.getTrashed();
        int iconId = aFolderData.getIconId();
        ContentValues newValues = new ContentValues();
        newValues.put("UUID", folderUuid);
        newValues.put("Title", title);
        newValues.put("UpdateTime", Double.valueOf(updateTime));
        newValues.put("Parent", parent);
        newValues.put("Trashed", Boolean.valueOf(trashed));
        newValues.put("IconID", Integer.valueOf(iconId));
        return this.mDatabase.insert("Folders", null, newValues);
    }

    private void restoreCryptoVars(byte[] aInfo) {
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

    public Object getPoolDataForRow(int aRowID) {
        Object tempData = null;
        Cursor selectedRow = null;
        try {
            selectedRow = this.mDatabase.query("Pool", new String[]{"Data"}, "UID = '" + aRowID + "'", null, null, null, null);
            if (selectedRow.moveToFirst()) {
                tempData = this.mCrypto.decrypt(selectedRow.getBlob(selectedRow.getColumnIndexOrThrow("Data")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (selectedRow != null) {
            selectedRow.close();
        }
        if (aRowID > ANDROID_WATCH_PIN_CODE) {
            return tempData;
        }
        if (tempData == null) {
            return new char[0];
        }
        return new String(tempData).toCharArray();
    }

    public void setPoolDataForRow(int aRowID, byte[] aPassword) {
        boolean exist = false;
        String[] result_columns = new String[]{"Data"};
        String where = "UID = '" + aRowID + "'";
        Cursor selectedRow = null;
        try {
            selectedRow = this.mDatabase.query("Pool", result_columns, where, null, null, null, null);
            if (selectedRow.moveToFirst()) {
                exist = true;
            }
            if (selectedRow != null) {
                selectedRow.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (selectedRow != null) {
                selectedRow.close();
            }
        } catch (Throwable th) {
            if (selectedRow != null) {
                selectedRow.close();
            }
        }
        byte[] encPasswordData = this.mCrypto.encrypt(aPassword);
        ContentValues newValues = new ContentValues();
        newValues.put("Data", encPasswordData);
        newValues.put(AuthActivity.EXTRA_UID, Integer.valueOf(aRowID));
        if (exist) {
            this.mDatabase.update("Pool", newValues, where, null);
        } else {
            this.mDatabase.insert("Pool", null, newValues);
        }
        Arrays.fill(aPassword, (byte) 0);
    }

    public void close() {
        this.mDatabase.close();
        this.mCrypto.destroyCrypto();
    }

    private String generateRandomPassword() {
        StringBuilder randomStringBuilder = new StringBuilder();
        String specialValues = "012345678901234567890123456789~!@#$%^&*()_-+={[}]\\;:<,>.?/~!@#$%^&*()_-+={[}]\\;:<,>.?/ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijkmnpqrstuvwxyzabcdefghijkmnpqrstuvwxyz";
        SecureRandom rand = new SecureRandom();
        for (int MAX_LENGTH = 32; MAX_LENGTH > 0; MAX_LENGTH--) {
            randomStringBuilder = randomStringBuilder.append(specialValues.charAt(rand.nextInt(1000) % specialValues.length()));
        }
        return randomStringBuilder.toString();
    }

    public long addCard(Card aCard) {
        long rowId = -1;
        try {
            double timeInSecs = Utils.timestampToTicks(aCard.getTimestamp());
            String aFormFields = aCard.getFormFields();
            byte[] encCardData = this.mCrypto.encrypt(aCard.write().toString().getBytes("UTF-8"));
            ContentValues newValues = new ContentValues();
            newValues.put("SubTitle", aCard.getSubTitle());
            newValues.put("Title", aCard.getName());
            newValues.put("Type", aCard.getTemplateType());
            newValues.put("Category", aCard.getCardCategory());
            newValues.put("IconId", Integer.valueOf(aCard.getIconId()));
            newValues.put("CustomIconId", aCard.getCustomIconId());
            newValues.put("UpdateTime", Double.valueOf(timeInSecs));
            newValues.put("UUID", aCard.getUuid());
            newValues.put("Data", encCardData);
            newValues.put("Trashed", Boolean.valueOf(aCard.isTrashed()));
            newValues.put("Deleted", Boolean.valueOf(aCard.isDeleted()));
            newValues.put("Urls", aCard.getUrl());
            newValues.put("FormFields", aFormFields);
            rowId = this.mDatabase.insert("Cards", null, newValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowId;
    }

    private int deleteCard(String cardUuid) {
        Card card = getCardWithUuid(cardUuid);
        card.setTimestamp(new Date());
        card.setTrashed(true);
        card.setDeleted(true);
        card.clearRealData();
        int effectedRows = updateCard(card);
        addOrRemoveFavorite(cardUuid, false);
        removeCardFromAllFolders(cardUuid);
        deleteAllAttachmentsOfCard(cardUuid);
        card.wipe();
        return effectedRows;
    }

    public int updateCard(Card aCard) {
        String aFormFields = aCard.getFormFields();
        double timeInSecs = Utils.timestampToTicks(aCard.getTimestamp());
        int effectedRows = 0;
        try {
            byte[] encCardData = this.mCrypto.encrypt(aCard.write().toString().getBytes("UTF-8"));
            ContentValues newValues = new ContentValues();
            newValues.put("SubTitle", aCard.getSubTitle());
            newValues.put("Title", aCard.getName());
            newValues.put("Type", aCard.getTemplateType());
            newValues.put("Category", aCard.getCardCategory());
            newValues.put("IconId", Integer.valueOf(aCard.getIconId()));
            newValues.put("CustomIconId", aCard.getCustomIconId());
            newValues.put("UpdateTime", Double.valueOf(timeInSecs));
            newValues.put("UUID", aCard.getUuid());
            newValues.put("Data", encCardData);
            newValues.put("Trashed", Boolean.valueOf(aCard.isTrashed()));
            newValues.put("Deleted", Boolean.valueOf(aCard.isDeleted()));
            newValues.put("Urls", aCard.getUrl());
            newValues.put("FormFields", aFormFields);
            String[] args = new String[]{aCard.getUuid()};
            effectedRows = this.mDatabase.update("Cards", newValues, "UUID = ?", args);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return effectedRows;
    }

    public List<IDisplayItem> getAllCards() {
        Cursor cursor = this.mDatabase.rawQuery("Select Title , IconID , UUID , SubTitle From Cards where Trashed = 0", null);
        ArrayList<IDisplayItem> list = new ArrayList();
        if (cursor == null || !cursor.moveToFirst()) {
            cursor.close();
            return list;
        }
        do {
            list.add(new CardMeta(cursor.getString(cursor.getColumnIndex("UUID")), Integer.parseInt(cursor.getString(cursor.getColumnIndex("IconID"))), cursor.getString(cursor.getColumnIndex("Title")), cursor.getString(cursor.getColumnIndex("SubTitle"))));
        } while (cursor.moveToNext());
        cursor.close();
        return list;
    }

    public Card getCardWithUuid(String aUUID) {
        String[] result_columns = new String[]{"Data", "Trashed", "CustomIconId", "Category", "Deleted", "FormFields"};
        byte[] encCardData = null;
        String[] args = new String[]{aUUID};
        boolean trash = false;
        boolean deleted = false;
        String customIconId = BuildConfig.FLAVOR;
        String category = null;
        String formFields = null;
        Cursor selectedRow = this.mDatabase.query("Cards", result_columns, "UUID = ?", args, null, null, null);
        if (selectedRow.moveToFirst()) {
            encCardData = selectedRow.getBlob(selectedRow.getColumnIndexOrThrow("Data"));
            trash = selectedRow.getInt(selectedRow.getColumnIndexOrThrow("Trashed")) != 0;
            deleted = selectedRow.getInt(selectedRow.getColumnIndexOrThrow("Deleted")) != 0;
            customIconId = selectedRow.getString(selectedRow.getColumnIndexOrThrow("CustomIconId"));
            category = selectedRow.getString(selectedRow.getColumnIndexOrThrow("Category"));
            formFields = selectedRow.getString(selectedRow.getColumnIndexOrThrow("FormFields"));
        }
        selectedRow.close();
        if (encCardData == null) {
            return null;
        }
        byte[] cardData = this.mCrypto.decrypt(encCardData);
        Card card = new Card();
        try {
            card.read(new JSONObject(new String(cardData)));
            card.setCustomIconId(customIconId);
            card.setCardCategory(category);
            card.setTrashed(trash);
            card.setDeleted(deleted);
            card.setFormFields(formFields);
            return card;
        } catch (JSONException e) {
            e.printStackTrace();
            return card;
        }
    }

    public IDisplayItem getCardMetaForIdentifier(String uuid) {
        String[] result_columns = new String[]{"IconID", "Title", "SubTitle"};
        String[] args = new String[]{uuid};
        int iconId = 0;
        String title = null;
        String subTitle = null;
        Cursor selectedRow = this.mDatabase.query("Cards", result_columns, "UUID = ?", args, null, null, null);
        if (selectedRow.moveToFirst()) {
            iconId = selectedRow.getInt(selectedRow.getColumnIndexOrThrow("IconID"));
            title = selectedRow.getString(selectedRow.getColumnIndexOrThrow("Title"));
            subTitle = selectedRow.getString(selectedRow.getColumnIndexOrThrow("SubTitle"));
        }
        selectedRow.close();
        return new CardMeta(uuid, iconId, title, subTitle);
    }

    public int markCardUntrashed(Card card) {
        double timeInSecs = Utils.timestampToTicks(new Date());
        String aUuid = card.getUuid();
        ContentValues newValues = new ContentValues();
        newValues.put("Trashed", Integer.valueOf(0));
        newValues.put("UpdateTime", Double.valueOf(timeInSecs));
        String[] args = new String[]{aUuid};
        return this.mDatabase.update("Cards", newValues, "UUID = ?", args);
    }

    public List<IDisplayItem> getCardsMetaForCategory(String aCategory) {
        Cursor cursor = this.mDatabase.rawQuery("SELECT Title  ,IconID , UUID, SubTitle From Cards where Category = ? and Trashed = 0", new String[]{aCategory});
        ArrayList<IDisplayItem> list = new ArrayList();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(new CardMeta(cursor.getString(cursor.getColumnIndex("UUID")), Integer.parseInt(cursor.getString(cursor.getColumnIndex("IconID"))), cursor.getString(cursor.getColumnIndex("Title")), cursor.getString(cursor.getColumnIndex("SubTitle"))));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

    private void addOrRemoveFavorite(String cardUuid, boolean isFavorite) {
        int trashed;
        if (isFavorite) {
            trashed = 0;
        } else {
            trashed = 1;
        }
        double timeInSecs = Utils.timestampToTicks(new Date());
        ContentValues newValues = new ContentValues();
        newValues.put("CardUUID", cardUuid);
        newValues.put("UpdateTime", Double.valueOf(timeInSecs));
        newValues.put("Trashed", Integer.valueOf(trashed));
        if (this.mDatabase.insert("Favorites", null, newValues) == -1) {
            String[] args = new String[]{cardUuid};
            this.mDatabase.update("Favorites", newValues, "CardUUID = ?", args);
        }
    }

    public List<IDisplayItem> getUnfavoriesCards() {
        Cursor cursor = this.mDatabase.rawQuery("Select Title , IconID , UUID , SubTitle From Cards where UUID not in (Select CardUUID from Favorites where Trashed = 0) and Trashed = 0", null);
        ArrayList<IDisplayItem> list = new ArrayList();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(new CardMeta(cursor.getString(cursor.getColumnIndex("UUID")), Integer.parseInt(cursor.getString(cursor.getColumnIndex("IconID"))), cursor.getString(cursor.getColumnIndex("Title")), cursor.getString(cursor.getColumnIndex("SubTitle"))));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

    public boolean isFavorite(String aUuid) {
        boolean result = false;
        for (IDisplayItem card : getFavoriteCardsMeta()) {
            if (card.getDisplayIdentifier().equals(aUuid)) {
                return true;
            }
            result = false;
        }
        return result;
    }

    public List<IDisplayItem> getFavoriteCardsMeta() {
        Cursor cursor = this.mDatabase.rawQuery("Select Cards.IconID , Cards.UUID , Cards.Title, Cards.SubTitle from Cards , Favorites where Cards.UUID = Favorites.CardUUID and Favorites.Trashed = 0 and Cards.Trashed=0", null);
        ArrayList<IDisplayItem> list = new ArrayList();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(new CardMeta(cursor.getString(cursor.getColumnIndex("UUID")), Integer.parseInt(cursor.getString(cursor.getColumnIndex("IconID"))), cursor.getString(cursor.getColumnIndex("Title")), cursor.getString(cursor.getColumnIndex("SubTitle"))));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

    private long addFolder(Folder aFolder) {
        double timeInSecs = Utils.timestampToTicks(new Date());
        ContentValues newValues = new ContentValues();
        newValues.put("Title", aFolder.getDisplayName());
        newValues.put("UpdateTime", Double.valueOf(timeInSecs));
        newValues.put("UUID", aFolder.getDisplayIdentifier());
        newValues.put("Parent", aFolder.getParent());
        newValues.put("Trashed", Integer.valueOf(0));
        newValues.put("IconID", Integer.valueOf(aFolder.getDisplayIconId()));
        return this.mDatabase.insert("Folders", null, newValues);
    }

    private int updateFolder(Folder aFolder) {
        double timeInSecs = Utils.timestampToTicks(new Date());
        ContentValues newValues = new ContentValues();
        newValues.put("Title", aFolder.getDisplayName());
        newValues.put("UpdateTime", Double.valueOf(timeInSecs));
        newValues.put("UUID", aFolder.getDisplayIdentifier());
        newValues.put("Parent", aFolder.getParent());
        newValues.put("IconID", Integer.valueOf(aFolder.getDisplayIconId()));
        return this.mDatabase.update("Folders", newValues, "UUID = '" + aFolder.getDisplayIdentifier() + "'", null);
    }

    private int deleteFolder(String aUuid) {
        double timeInSecs = Utils.timestampToTicks(new Date());
        ContentValues newValues = new ContentValues();
        newValues.put("Trashed", Integer.valueOf(1));
        newValues.put("UpdateTime", Double.valueOf(timeInSecs));
        String where = "UUID = '" + aUuid + "'";
        String where_Folder_Cards = "FolderUUID = '" + aUuid + "'";
        int effectedFolderCount = this.mDatabase.update("Folders ", newValues, where, null);
        int effectedFolderCardsCount = this.mDatabase.update("Folder_Cards", newValues, where_Folder_Cards, null);
        for (IDisplayItem folder : getSubFolders(aUuid)) {
            deleteFolder(folder.getDisplayIdentifier());
        }
        return effectedFolderCount;
    }

    public IDisplayItem getFolderForIdentifier(String uuid) {
        Cursor cursor = this.mDatabase.rawQuery("Select Title ,Parent, IconID from Folders where UUID = ?", new String[]{uuid});
        Folder folder = null;
        try {
            if (cursor.moveToFirst()) {
                folder = new Folder(cursor.getString(cursor.getColumnIndex("Title")), cursor.getString(cursor.getColumnIndex("Parent")), cursor.getInt(cursor.getColumnIndex("IconID")), uuid);
            }
            cursor.close();
            return folder;
        } catch (Throwable th) {
            cursor.close();
        }
    }

    public long getSubFolderCount(String aFolderUuid) {
        SQLiteStatement statement = this.mDatabase.compileStatement("SELECT COUNT(*) FROM Folders WHERE Trashed=0 and Parent = '" + aFolderUuid + "'");
        long count = statement.simpleQueryForLong();
        statement.close();
        return count;
    }

    public long getChildCardsCount(String aFolderUuid) {
        SQLiteStatement statement = this.mDatabase.compileStatement("SELECT COUNT(*) FROM Cards,Folder_Cards WHERE Folder_Cards.Trashed=0 and Cards.Trashed=0 and Cards.UUID=Folder_Cards.CardUUID and Folder_Cards.FolderUUID =  '" + aFolderUuid + "'");
        long count = statement.simpleQueryForLong();
        statement.close();
        return count;
    }

    private void addOrRemoveCardToFolder(String aCardUuid, String aFolderUuid, boolean isAdd) {
        double timeInSecs = Utils.timestampToTicks(new Date());
        ContentValues newValues;
        if (isAdd) {
            Cursor cursor = this.mDatabase.rawQuery("Select * from Folder_Cards where FolderUUID = ? and CardUUID = ? ", new String[]{aFolderUuid, aCardUuid});
            newValues = new ContentValues();
            newValues.put("UpdateTime", Double.valueOf(timeInSecs));
            newValues.put("FolderUUID", aFolderUuid);
            newValues.put("CardUUID", aCardUuid);
            newValues.put("Trashed", Integer.valueOf(0));
            if (cursor.getCount() <= 0) {
                this.mDatabase.insert("Folder_Cards", null, newValues);
            } else {
                this.mDatabase.update("Folder_Cards", newValues, "FolderUUID = '" + aFolderUuid + "' and CardUUID = '" + aCardUuid + "'", null);
            }
            if (cursor != null) {
                cursor.close();
                return;
            }
            return;
        }
        newValues = new ContentValues();
        newValues.put("Trashed", Integer.valueOf(1));
        newValues.put("UpdateTime", Double.valueOf(timeInSecs));
        this.mDatabase.update("Folder_Cards", newValues, "CardUUID = '" + aCardUuid + "' AND FolderUUID = '" + aFolderUuid + "'", null);
    }

    private int removeCardFromAllFolders(String aUuid) {
        double timeInSecs = Utils.timestampToTicks(new Date());
        ContentValues newValues = new ContentValues();
        newValues.put("Trashed", Integer.valueOf(1));
        newValues.put("UpdateTime", Double.valueOf(timeInSecs));
        return this.mDatabase.update("Folder_Cards", newValues, "CardUUID = '" + aUuid + "'", null);
    }

    public List<IDisplayItem> getChildCards(String aFolderUuid) {
        Cursor cursor = this.mDatabase.rawQuery("Select Cards.IconID , Cards.UUID , Cards.Title, Cards.SubTitle from Cards,  Folder_Cards where Cards.UUID = Folder_Cards.CardUUID and Cards.Trashed = 0 and Folder_Cards.FolderUUID = ? and Folder_Cards.Trashed = 0", new String[]{aFolderUuid});
        ArrayList<IDisplayItem> list = new ArrayList();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(new CardMeta(cursor.getString(cursor.getColumnIndex("UUID")), Integer.parseInt(cursor.getString(cursor.getColumnIndex("IconID"))), cursor.getString(cursor.getColumnIndex("Title")), cursor.getString(cursor.getColumnIndex("SubTitle"))));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

    public List<IDisplayItem> getCardNotInFolder(String aFolderUUID) {
        Cursor cursor = this.mDatabase.rawQuery("Select Cards.Title , Cards.IconID , Cards.UUID, Cards.SubTitle From Cards where Cards.UUID not in (Select CardUUID from Folder_Cards where Trashed = 0 and FolderUUID = ?) and Trashed = 0", new String[]{aFolderUUID});
        ArrayList<IDisplayItem> list = new ArrayList();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(new CardMeta(cursor.getString(cursor.getColumnIndex("UUID")), Integer.parseInt(cursor.getString(cursor.getColumnIndex("IconID"))), cursor.getString(cursor.getColumnIndex("Title")), cursor.getString(cursor.getColumnIndex("SubTitle"))));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

    public List<IDisplayItem> getSubFolders(String aParent) {
        Cursor cursor = this.mDatabase.rawQuery("Select Title , UUID, IconID from Folders where Parent = ? and Trashed = 0", new String[]{aParent});
        ArrayList<IDisplayItem> list = new ArrayList();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String uuid = cursor.getString(cursor.getColumnIndex("UUID"));
                    Folder subFolder = new Folder(cursor.getString(cursor.getColumnIndex("Title")), aParent, cursor.getInt(cursor.getColumnIndex("IconID")), uuid);
                    long subFoldersCount = getSubFolderCount(uuid);
                    subFolder.setChildcardsCount(getChildCardsCount(uuid));
                    subFolder.setSubFoldersCount(subFoldersCount);
                    list.add(subFolder);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

    public List<String> getAllCardsWithSameDomainName(String domainname) {
        String name = domainname + ",";
        Cursor cursor = this.mDatabase.rawQuery("select UUID from Cards where Urls like ? and Trashed = 0 ", new String[]{"%" + name + "%"});
        List<String> list = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex("UUID")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<String> getAllCardsWithSameHostname(Uri uri) {
        String hostName = uri.getHost();
        List<String> matchingDomainCardsUuids = getAllCardsWithSameDomainName(GetDomain.GetDomainFromUrl(uri));
        List<String> matchingHostCardsUuids = new ArrayList();
        for (String uuid : matchingDomainCardsUuids) {
            Iterator it = getCardWithUuid(uuid).getFields().iterator();
            while (it.hasNext()) {
                CardField field = (CardField) it.next();
                if (!field.isDeleted() && field.getType().equals(Card.mCardFieldTypeMap.get(CardFieldType.CardFieldTypeUrl))) {
                    String urlValue = field.getValue().toString();
                    if (!urlValue.startsWith("http")) {
                        urlValue = "https://" + urlValue;
                    }
                    if (urlValue != null) {
                        String host = Uri.parse(urlValue).getHost();
                        if (host.contains("www.")) {
                            host = host.replace("www.", BuildConfig.FLAVOR);
                        }
                        if (hostName.contains("www.")) {
                            hostName = hostName.replace("www.", BuildConfig.FLAVOR);
                        }
                        if (host != null && host.equals(hostName)) {
                            matchingHostCardsUuids.add(uuid);
                        }
                    }
                }
            }
        }
        return matchingHostCardsUuids;
    }

    public List<String> getAllCreditCardsAndBankAccounts() {
        Cursor cursor = this.mDatabase.rawQuery("select UUID from Cards where Type = ? or Type = ? and Trashed = 0 ", new String[]{"creditcard.default"});
        List<String> list = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex("UUID")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void addCardNotified(Card aCard) {
        addCard(aCard);
        CardMeta cardMeta = new CardMeta(aCard.getUuid(), aCard.getIconId(), aCard.getName(), aCard.getSubTitle());
        if (this.mKeychainDelegate != null) {
            this.mKeychainDelegate.keychainChanged(KeychainChangeType.KeychainCardAdded, cardMeta, null);
        }
    }

    public void removeCardNotified(String aUuid) {
        deleteCard(aUuid);
        IDisplayItem item = getCardMetaForIdentifier(aUuid);
        if (this.mKeychainDelegate != null) {
            this.mKeychainDelegate.keychainChanged(KeychainChangeType.KeychainCardRemoved, item, null);
        }
    }

    public void updateCardNotified(Card aCard) {
        updateCard(aCard);
        IDisplayItem item = getCardMetaForIdentifier(aCard.getUuid());
        if (this.mKeychainDelegate != null) {
            this.mKeychainDelegate.keychainChanged(KeychainChangeType.KeychainCardChanged, item, null);
        }
    }

    public void changeCardCategoryNotified(Card aCard) {
        updateCard(aCard);
        IDisplayItem item = getCardMetaForIdentifier(aCard.getUuid());
        if (this.mKeychainDelegate != null) {
            this.mKeychainDelegate.keychainChanged(KeychainChangeType.KeychainCardCategoryChanged, item, null);
        }
    }

    public void updateCardNotifiedAfterShare(Card aCard) {
        updateCard(aCard);
        IDisplayItem item = getCardMetaForIdentifier(aCard.getUuid());
        if (this.mKeychainDelegate != null) {
            this.mKeychainDelegate.keychainChanged(KeychainChangeType.KeychainCardAdded, item, null);
        }
    }

    public void addFavoriteNotified(String uuid) {
        CardMeta aCardMeta = (CardMeta) getCardMetaForIdentifier(uuid);
        addOrRemoveFavorite(aCardMeta.getDisplayIdentifier(), true);
        CardMeta cardMeta = new CardMeta(aCardMeta.getDisplayIdentifier(), aCardMeta.getDisplayIconId(), aCardMeta.getDisplayName(), aCardMeta.getSubTitle());
        if (this.mKeychainDelegate != null) {
            this.mKeychainDelegate.keychainChanged(KeychainChangeType.KeychainFavoriteAdded, cardMeta, null);
        }
    }

    public void removeFavoriteNotified(String uuid) {
        CardMeta aCardMeta = (CardMeta) getCardMetaForIdentifier(uuid);
        addOrRemoveFavorite(aCardMeta.getDisplayIdentifier(), false);
        CardMeta cardMeta = new CardMeta(aCardMeta.getDisplayIdentifier(), aCardMeta.getDisplayIconId(), aCardMeta.getDisplayName(), aCardMeta.getSubTitle());
        if (this.mKeychainDelegate != null) {
            this.mKeychainDelegate.keychainChanged(KeychainChangeType.KeychainFavoriteRemoved, cardMeta, null);
        }
    }

    public void addFolderNotified(Folder aFolder) {
        addFolder(aFolder);
        if (this.mKeychainDelegate != null) {
            this.mKeychainDelegate.keychainChanged(KeychainChangeType.KeychainFolderAdded, aFolder, null);
        }
    }

    public void removeFolderNotified(String aUuid) {
        deleteFolder(aUuid);
        IDisplayItem item = getFolderForIdentifier(aUuid);
        if (this.mKeychainDelegate != null) {
            this.mKeychainDelegate.keychainChanged(KeychainChangeType.KeychainFolderRemoved, item, null);
        }
    }

    public void updateFolderNotified(String aNewName, String aUuid, int iconId) {
        Folder item = (Folder) getFolderForIdentifier(aUuid);
        updateFolder(new Folder(aNewName, item.getParent(), iconId, aUuid));
        if (this.mKeychainDelegate != null) {
            this.mKeychainDelegate.keychainChanged(KeychainChangeType.KeychainFolderChanged, item, aNewName);
        }
    }

    public void FolderCardAddedNotified(String aCardUuid, String aFolderUuid) {
        addOrRemoveCardToFolder(aCardUuid, aFolderUuid, true);
        IDisplayItem item = getCardMetaForIdentifier(aCardUuid);
        if (this.mKeychainDelegate != null) {
            this.mKeychainDelegate.keychainChanged(KeychainChangeType.KeychainFolderCardAdded, item, aFolderUuid);
        }
    }

    public void addCardFromChrome(final Card aCard) {
        addCard(aCard);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                CardMeta cardMeta = new CardMeta(aCard.getUuid(), aCard.getIconId(), aCard.getName(), aCard.getSubTitle());
                if (Keychain.this.mKeychainDelegate != null) {
                    Keychain.this.mKeychainDelegate.keychainChanged(KeychainChangeType.KeychainCardAdded, cardMeta, null);
                }
            }
        });
    }

    public void updateCardFromChrome(final Card aCard) {
        updateCard(aCard);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                IDisplayItem item = Keychain.this.getCardMetaForIdentifier(aCard.getUuid());
                if (Keychain.this.mKeychainDelegate != null) {
                    Keychain.this.mKeychainDelegate.keychainChanged(KeychainChangeType.KeychainCardChanged, item, null);
                }
            }
        });
    }

    public void FolderCardRemoveNotified(String aCardUuid, String aFolderUuid) {
        addOrRemoveCardToFolder(aCardUuid, aFolderUuid, false);
        IDisplayItem item = getCardMetaForIdentifier(aCardUuid);
        if (this.mKeychainDelegate != null) {
            this.mKeychainDelegate.keychainChanged(KeychainChangeType.KeychainFolderCardRemoved, item, aFolderUuid);
        }
    }

    public void RemoveCardFromAllFolderNotified(String aCardUuid) {
        removeCardFromAllFolders(aCardUuid);
        IDisplayItem item = getCardMetaForIdentifier(aCardUuid);
        if (this.mKeychainDelegate != null) {
            this.mKeychainDelegate.keychainChanged(KeychainChangeType.KeychainFolderCardRemoved, item, null);
        }
    }

    public void changePassword(Context context, char[] aNewPassword) {
        try {
            String str = new String(aNewPassword);
            this.mDatabase.rawExecSQL("PRAGMA rekey  = '" + str.replaceAll("'", "''") + "';");
            setPoolDataForRow(LOCAL_REMOTE, str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Utils.wipeCharArray(aNewPassword);
    }

    public int getCardsCount() {
        Cursor cursor = this.mDatabase.rawQuery("Select * From Cards where Trashed = 0", null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public ArrayList<String> getAllUuids() {
        Cursor allRows = this.mDatabase.rawQuery("SELECT UUID FROM Cards", null);
        ArrayList<String> uuids = new ArrayList();
        if (allRows.moveToFirst()) {
            do {
                uuids.add(allRows.getString(allRows.getColumnIndexOrThrow("UUID")));
            } while (allRows.moveToNext());
        }
        allRows.close();
        return uuids;
    }

    public ArrayList<String> getAllFavoriteCardUuids() {
        Cursor cursor = this.mDatabase.rawQuery("Select CardUUID from favorites", null);
        ArrayList<String> list = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex("CardUUID")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<String> getAllFolderUuids() {
        Cursor cursor = this.mDatabase.rawQuery("Select UUID from folders", null);
        ArrayList<String> list = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex("UUID")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<FolderCardData> getAllFolderCardsUuids() {
        Cursor cursor = this.mDatabase.rawQuery("Select FolderUUID, CardUUID, UpdateTime, Trashed from Folder_Cards", null);
        ArrayList<FolderCardData> list = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                list.add(new FolderCardData(cursor.getString(cursor.getColumnIndex("FolderUUID")), cursor.getString(cursor.getColumnIndex("CardUUID")), (double) ((long) cursor.getInt(cursor.getColumnIndexOrThrow("UpdateTime"))), cursor.getInt(cursor.getColumnIndexOrThrow("Trashed")) != 0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public Date getUpdateTimeForCard(String aUUID) {
        String[] result_columns = new String[]{"UpdateTime"};
        long uptime = 0;
        String[] args = new String[]{aUUID};
        Cursor selectedRow = this.mDatabase.query("Cards", result_columns, "UUID = ? ", args, null, null, null);
        if (selectedRow.moveToFirst()) {
            uptime = (long) selectedRow.getDouble(selectedRow.getColumnIndexOrThrow("UpdateTime"));
        }
        selectedRow.close();
        return new Date(1000 * uptime);
    }

    public FavoriteData getFavoriteDataForUuid(String aUuid) {
        boolean trash = false;
        Cursor selectedRow = this.mDatabase.rawQuery("Select * from favorites where CardUUID = ?", new String[]{aUuid});
        long updateTime = 0;
        if (selectedRow.moveToFirst()) {
            updateTime = (long) selectedRow.getDouble(selectedRow.getColumnIndexOrThrow("UpdateTime"));
            if (selectedRow.getInt(selectedRow.getColumnIndexOrThrow("Trashed")) == 0) {
                trash = false;
            } else {
                trash = true;
            }
        }
        selectedRow.close();
        return new FavoriteData(aUuid, updateTime, trash);
    }

    public void addFavoriteData(FavoriteData aFavoriteCard) {
        String cardUuid = aFavoriteCard.getUuid();
        double timeInSecs = (double) aFavoriteCard.mTimestamp;
        ContentValues newValues = new ContentValues();
        newValues.put("CardUUID", cardUuid);
        newValues.put("UpdateTime", Double.valueOf(timeInSecs));
        newValues.put("Trashed", Boolean.valueOf(aFavoriteCard.getTrashed()));
        this.mDatabase.insert("Favorites", null, newValues);
    }

    public void updateFavoriteData(FavoriteData aFavoriteCard) {
        String cardUuid = aFavoriteCard.getUuid();
        double timeInSecs = (double) aFavoriteCard.mTimestamp;
        ContentValues newValues = new ContentValues();
        newValues.put("CardUUID", cardUuid);
        newValues.put("UpdateTime", Double.valueOf(timeInSecs));
        newValues.put("Trashed", Boolean.valueOf(aFavoriteCard.getTrashed()));
        String[] args = new String[]{cardUuid};
        this.mDatabase.update("Favorites", newValues, "CardUUID = ?", args);
    }

    public FolderData getFolderDataForUuid(String aUuid) {
        Cursor selectedRow = this.mDatabase.rawQuery("select * from folders where UUID = ?", new String[]{aUuid});
        boolean trash = false;
        String title = BuildConfig.FLAVOR;
        long updateTime = 0;
        String parent = BuildConfig.FLAVOR;
        int iconId = 0;
        if (selectedRow.moveToFirst()) {
            title = selectedRow.getString(selectedRow.getColumnIndexOrThrow("Title"));
            updateTime = (long) selectedRow.getInt(selectedRow.getColumnIndexOrThrow("UpdateTime"));
            parent = selectedRow.getString(selectedRow.getColumnIndexOrThrow("Parent"));
            if (selectedRow.getInt(selectedRow.getColumnIndexOrThrow("Trashed")) == 0) {
                trash = false;
            } else {
                trash = true;
            }
            iconId = selectedRow.getInt(selectedRow.getColumnIndexOrThrow("IconID"));
        }
        selectedRow.close();
        return new FolderData(title, (double) updateTime, aUuid, parent, trash, iconId);
    }

    public void addFolderData(FolderData aFolderData) {
        String folderUuid = aFolderData.getUuid();
        double updateTime = aFolderData.mUpdateTime;
        String title = aFolderData.getTitle();
        String parent = aFolderData.getParent();
        boolean trashed = aFolderData.getTrashed();
        int iconId = aFolderData.getIconId();
        ContentValues newValues = new ContentValues();
        newValues.put("UUID", folderUuid);
        newValues.put("Title", title);
        newValues.put("UpdateTime", Double.valueOf(updateTime));
        newValues.put("Parent", parent);
        newValues.put("Trashed", Boolean.valueOf(trashed));
        newValues.put("IconID", Integer.valueOf(iconId));
        this.mDatabase.insert("Folders", null, newValues);
    }

    public void updateFolderData(FolderData aFolderData) {
        String folderUuid = aFolderData.getUuid();
        double updateTime = aFolderData.mUpdateTime;
        String title = aFolderData.getTitle();
        String parent = aFolderData.getParent();
        boolean trashed = aFolderData.getTrashed();
        int iconId = aFolderData.getIconId();
        ContentValues newValues = new ContentValues();
        newValues.put("UUID", folderUuid);
        newValues.put("Title", title);
        newValues.put("UpdateTime", Double.valueOf(updateTime));
        newValues.put("Parent", parent);
        newValues.put("Trashed", Boolean.valueOf(trashed));
        newValues.put("IconID", Integer.valueOf(iconId));
        String[] args = new String[]{folderUuid};
        this.mDatabase.update("Folders", newValues, "UUID = ?", args);
    }

    public void updateFolderCard(FolderCardData aFolderCardData) {
        double updateTime = aFolderCardData.mUpdateTime;
        String aFolderUuid = aFolderCardData.getFolderUuid();
        String aCardUuid = aFolderCardData.getCardUuid();
        ContentValues newValues = new ContentValues();
        newValues.put("UpdateTime", Double.valueOf(updateTime));
        newValues.put("Trashed", Boolean.valueOf(aFolderCardData.getTrashed()));
        String[] args = new String[]{aFolderUuid, aCardUuid};
        this.mDatabase.update("Folder_Cards", newValues, "FolderUUID = ? and CardUUID = ?", args);
    }

    public FolderCardData getFolderCardDataForUuid(String aFolderUuid, String aCardUuid) {
        Cursor selectedRow = this.mDatabase.rawQuery("select * from Folder_Cards where FolderUUID = ? and CardUUID = ?", new String[]{aFolderUuid, aCardUuid});
        boolean trash = false;
        long updateTime = 0;
        if (selectedRow.moveToFirst()) {
            updateTime = (long) selectedRow.getInt(selectedRow.getColumnIndexOrThrow("UpdateTime"));
            if (selectedRow.getInt(selectedRow.getColumnIndexOrThrow("Trashed")) == 0) {
                trash = false;
            } else {
                trash = true;
            }
        }
        selectedRow.close();
        return new FolderCardData(aFolderUuid, aCardUuid, (double) updateTime, trash);
    }

    public void addFolderCard(FolderCardData aFolderCardData) {
        double updateTime = aFolderCardData.mUpdateTime;
        String aFolderUuid = aFolderCardData.getFolderUuid();
        String aCardUuid = aFolderCardData.getCardUuid();
        ContentValues newValues = new ContentValues();
        newValues.put("FolderUUID", aFolderUuid);
        newValues.put("CardUUID", aCardUuid);
        newValues.put("UpdateTime", Double.valueOf(updateTime));
        newValues.put("Trashed", Boolean.valueOf(aFolderCardData.getTrashed()));
        this.mDatabase.insert("Folder_Cards", null, newValues);
    }

    public ArrayList<GeneratedPassword> getAllPasswordsFromHistory() {
        Cursor cursor = this.mDatabase.rawQuery("Select * from Password_History order by Timestamp DESC", null);
        ArrayList<GeneratedPassword> list = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                list.add(new GeneratedPassword(cursor.getString(cursor.getColumnIndex("Password")), ((long) cursor.getInt(cursor.getColumnIndexOrThrow("Timestamp"))) * 1000, cursor.getString(cursor.getColumnIndex("Domain"))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void saveGeneratedPassword(String password, String domain) {
        Cursor cursor = this.mDatabase.rawQuery("select * FROM Password_History", null);
        int count = cursor.getCount();
        cursor.close();
        double timeTicksInSeconds = Utils.timestampToTicks(new Date());
        ContentValues values = new ContentValues();
        values.put("Password", password);
        values.put("Domain", domain);
        values.put("Timestamp", Double.valueOf(timeTicksInSeconds));
        if (count < 5) {
            this.mDatabase.insert("Password_History", null, values);
            return;
        }
        this.mDatabase.update("Password_History", values, "Timestamp = (SELECT min(Timestamp) FROM Password_History)", null);
    }

    public String getFormFieldsForUuid(String aUUID) {
        String formFields = BuildConfig.FLAVOR;
        Cursor selectedRow = this.mDatabase.rawQuery("select FormFields from Cards where UUID = ?", new String[]{aUUID});
        while (selectedRow.moveToNext()) {
            formFields = selectedRow.getString(selectedRow.getColumnIndexOrThrow("FormFields"));
        }
        selectedRow.close();
        return formFields;
    }

    public boolean isWatchFolderExist() {
        if (getFolderForIdentifier(WATCH_FOLDER_UUID) != null) {
            return true;
        }
        return false;
    }

    public long addWatchFolderNotified(Folder aFolder) {
        long effectedRows = addFolder(aFolder);
        if (this.mKeychainDelegate != null) {
            this.mKeychainDelegate.keychainChanged(KeychainChangeType.KeychainFolderAdded, aFolder, null);
        }
        return effectedRows;
    }

    public long updateWatchFolderNotified(Folder folder) {
        long effectedRows = updateWatchFolder(folder);
        if (this.mKeychainDelegate != null) {
            this.mKeychainDelegate.keychainChanged(KeychainChangeType.KeychainFolderChanged, folder, folder.getDisplayName());
        }
        return effectedRows;
    }

    public long updateWatchFolder(Folder aFolder) {
        double timeInSecs = Utils.timestampToTicks(new Date());
        ContentValues newValues = new ContentValues();
        newValues.put("Title", aFolder.getDisplayName());
        newValues.put("UpdateTime", Double.valueOf(timeInSecs));
        newValues.put("UUID", aFolder.getDisplayIdentifier());
        newValues.put("Parent", aFolder.getParent());
        newValues.put("Trashed", Integer.valueOf(0));
        newValues.put("IconID", Integer.valueOf(aFolder.getDisplayIconId()));
        return (long) this.mDatabase.update("Folders", newValues, "UUID = '" + aFolder.getDisplayIdentifier() + "'", null);
    }

    public boolean checkDatabaseIntegrity() {
        Cursor cursor = this.mDatabase.rawQuery("PRAGMA integrity_check;", null);
        boolean isDatabaseOk = false;
        String result = null;
        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndexOrThrow("integrity_check"));
        }
        if (result.equalsIgnoreCase("ok")) {
            isDatabaseOk = true;
        }
        cursor.close();
        return isDatabaseOk;
    }

    public IDisplayItem getCardMetaForIdentifierWithoutTrash(String uuid) {
        String[] result_columns = new String[]{"IconID", "Title", "SubTitle"};
        String[] args = new String[]{uuid};
        int iconId = 0;
        String title = null;
        String subTitle = null;
        Cursor selectedRow = this.mDatabase.query("Cards", result_columns, "UUID = ? and Trashed = 0", args, null, null, null);
        if (selectedRow.moveToFirst()) {
            iconId = selectedRow.getInt(selectedRow.getColumnIndexOrThrow("IconID"));
            title = selectedRow.getString(selectedRow.getColumnIndexOrThrow("Title"));
            subTitle = selectedRow.getString(selectedRow.getColumnIndexOrThrow("SubTitle"));
        }
        selectedRow.close();
        return new CardMeta(uuid, iconId, title, subTitle);
    }

    public ArrayList<String> getAllAttachmentsUuids() {
        Cursor cursor = this.mDatabase.rawQuery("Select UUID from Attachment", null);
        ArrayList<String> list = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex("UUID")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public long addAttachment(Attachment attachment) {
        long rowId = -1;
        try {
            double timeInSecs = Utils.timestampToTicks(attachment.getTimestamp());
            ContentValues newValues = new ContentValues();
            newValues.put("Timestamp", Double.valueOf(timeInSecs));
            newValues.put("UUID", attachment.getUuid());
            newValues.put("CardUUID", attachment.getCardUuid());
            newValues.put("MetaData", attachment.getMetadata());
            newValues.put("Data", attachment.getData());
            newValues.put("Trashed", Boolean.valueOf(attachment.isTrashed()));
            rowId = this.mDatabase.insert("Attachment", null, newValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowId;
    }

    public List<Attachment> getAttachmentsForCardUUId(String cardUuid) {
        Cursor selectedRow = this.mDatabase.query("Attachment", new String[]{"UUID", "MetaData", "Data", "Trashed", "Timestamp"}, "CardUUID = ? AND Trashed = 0", new String[]{cardUuid}, null, null, null);
        ArrayList<Attachment> attachmentList = new ArrayList();
        String uuid;
        if (selectedRow == null || !selectedRow.moveToFirst()) {
            uuid = null;
        } else {
            uuid = null;
            do {
                attachmentList.add(new Attachment(selectedRow.getString(selectedRow.getColumnIndexOrThrow("UUID")), cardUuid, selectedRow.getString(selectedRow.getColumnIndexOrThrow("MetaData")), selectedRow.getBlob(selectedRow.getColumnIndexOrThrow("Data")), selectedRow.getInt(selectedRow.getColumnIndexOrThrow("Trashed")) != 0, new Date(1000 * ((long) selectedRow.getInt(selectedRow.getColumnIndexOrThrow("Timestamp"))))));
            } while (selectedRow.moveToNext());
        }
        selectedRow.close();
        return attachmentList;
    }

    public Attachment getAttachmentForUUId(String attachmentUuid) {
        Cursor selectedRow = this.mDatabase.query("Attachment", new String[]{"CardUUID", "MetaData", "Data", "Trashed", "Timestamp"}, "UUID = ?", new String[]{attachmentUuid}, null, null, null);
        Attachment attachment = null;
        if (selectedRow == null || !selectedRow.moveToFirst()) {
            String str = null;
        } else {
            attachment = new Attachment(attachmentUuid, selectedRow.getString(selectedRow.getColumnIndexOrThrow("CardUUID")), selectedRow.getString(selectedRow.getColumnIndexOrThrow("MetaData")), selectedRow.getBlob(selectedRow.getColumnIndexOrThrow("Data")), selectedRow.getInt(selectedRow.getColumnIndexOrThrow("Trashed")) != 0, new Date(1000 * ((long) selectedRow.getInt(selectedRow.getColumnIndexOrThrow("Timestamp")))));
        }
        selectedRow.close();
        return attachment;
    }

    public long updateAttachment(Attachment attachment) {
        try {
            double timeInSecs = Utils.timestampToTicks(attachment.getTimestamp());
            ContentValues newValues = new ContentValues();
            newValues.put("Timestamp", Double.valueOf(timeInSecs));
            newValues.put("UUID", attachment.getUuid());
            newValues.put("CardUUID", attachment.getCardUuid());
            newValues.put("MetaData", attachment.getMetadata());
            newValues.put("Data", attachment.getData());
            newValues.put("Trashed", Boolean.valueOf(attachment.isTrashed()));
            String[] args = new String[]{attachment.getUuid()};
            return (long) this.mDatabase.update("Attachment", newValues, "UUID = ?", args);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public long deleteAttachment(String attachmentUuid) {
        Attachment attachment = getAttachmentForUUId(attachmentUuid);
        attachment.setData(new byte[0]);
        attachment.setMetadata(BuildConfig.FLAVOR);
        attachment.setTimestamp(new Date());
        attachment.setTrashed(true);
        return updateAttachment(attachment);
    }

    void deleteAllAttachmentsOfCard(String cardUuid) {
        for (Attachment attachment : getAttachmentsForCardUUId(cardUuid)) {
            deleteAttachment(attachment.getUuid());
        }
    }

    public Date getUpdateTimeForAttachment(String aUUID) {
        String[] result_columns = new String[]{"Timestamp"};
        long uptime = 0;
        String[] args = new String[]{aUUID};
        Cursor selectedRow = this.mDatabase.query("Attachment", result_columns, "UUID = ? ", args, null, null, null);
        if (selectedRow.moveToFirst()) {
            uptime = (long) selectedRow.getDouble(selectedRow.getColumnIndexOrThrow("Timestamp"));
        }
        selectedRow.close();
        return new Date(1000 * uptime);
    }

    void performVacuum() {
        this.mDatabase.execSQL("VACUUM");
    }
}
