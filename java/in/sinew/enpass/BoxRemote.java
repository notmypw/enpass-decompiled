package in.sinew.enpass;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.box.androidsdk.content.BoxApiFile;
import com.box.androidsdk.content.BoxApiFolder;
import com.box.androidsdk.content.BoxConfig;
import com.box.androidsdk.content.BoxConstants;
import com.box.androidsdk.content.BoxException;
import com.box.androidsdk.content.models.BoxEntity;
import com.box.androidsdk.content.models.BoxFile;
import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.models.BoxFolder;
import com.box.androidsdk.content.models.BoxJsonObject;
import com.box.androidsdk.content.models.BoxList;
import com.box.androidsdk.content.models.BoxListItems;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.requests.BoxRequestsFile.UploadFile;
import com.box.androidsdk.content.requests.BoxRequestsFile.UploadNewVersion;
import com.github.clans.fab.BuildConfig;
import in.sinew.enpassengine.Utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

public class BoxRemote implements IRemoteStorage {
    public static final String BOX_AUTH_KEY = "box_auth";
    public static final String BOX_DIRTY_PREFERENCE = "boxDirty";
    public static final String BOX_ENABLE_PREFERENCE = "boxEnable";
    public static final String BOX_PREF = "box_pref";
    public static final String CLIENT_ID = "jqvtl0k85tgnt9050z1el6gvzembioyg";
    public static final String CLIENT_SECRET = "XTsP1XaNmdeDHYvG1Y2RwyDq3cIVih4T";
    public static final String FILE_MODIFIED_TIME_ON_BOX = "lastModifiedTimeOnBox";
    public static final String PASSWORD_CHANGE_PENDING_PREFERENCE = "changePending";
    public static final String REDIRECT_URL = "https://www.sinew.in/boxenpass/";
    private final String BOX_FOLDER = "Enpass";
    private final String ENPASS_BOX_FILE = "sync_default.walletx";
    String TAG = "BOXREMOTE";
    int fileUpdatedSuccess = -116;
    private boolean isFileExistOncloud = true;
    BoxState mBoxState = BoxState.Idle;
    Context mContext;
    long mFileModifiedTimeOnCloud = -1;
    boolean mLatestUploaded = true;
    FileOutputStream mOutputStream = null;
    IRemoteStorageDelegate mRemoteStorageDelegate = null;
    BoxSession mSession = null;

    public BoxRemote(Context context) {
        this.mContext = context;
        if (TextUtils.isEmpty(EnpassApplication.getInstance().getSharedPreferences(BOX_PREF, 0).getString(BOX_AUTH_KEY, BuildConfig.FLAVOR))) {
            initialize();
            return;
        }
        EnpassApplication.getInstance().getAppSettings().setBoxDisableAlert(true);
        EnpassApplication.getInstance().getSharedPreferences(BOX_PREF, 0).edit().clear().commit();
        EnpassApplication.getInstance().getAppSettings().setRemote(-1);
        EnpassApplication.getInstance().getAppSettings().setRemoteActive(false);
        EnpassApplication.getInstance().getAppSettings().setSigninId(BuildConfig.FLAVOR);
    }

    public BoxRemote(BoxSession session, Context context) {
        try {
            this.mContext = context;
            this.mSession = new BoxSession(context, session.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        try {
            BoxConfig.CLIENT_ID = CLIENT_ID;
            BoxConfig.CLIENT_SECRET = CLIENT_SECRET;
            this.mSession = new BoxSession(this.mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void checkMetaData() {
        this.isFileExistOncloud = true;
        this.mBoxState = BoxState.Metadata;
        new GetMetaData(this, null).execute(new Void[0]);
    }

    public int uploadOrUpdateFile(File file, String folderId) {
        String existingFileId = BuildConfig.FLAVOR;
        try {
            Collection<BoxJsonObject> collection = (Collection) ((BoxListItems) new BoxApiFolder(this.mSession).getItemsRequest(folderId).send()).getPropertiesAsHashMap().get(BoxList.FIELD_ENTRIES);
            if (collection.size() > 0) {
                for (BoxJsonObject boxObj : collection) {
                    HashMap<String, Object> entriesList = boxObj.getPropertiesAsHashMap();
                    if (((String) entriesList.get(BoxFileVersion.FIELD_NAME)).equals("sync_default.walletx")) {
                        existingFileId = (String) entriesList.get(BoxEntity.FIELD_ID);
                    }
                }
            }
        } catch (BoxException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(existingFileId)) {
            return uploadFile(file, folderId);
        }
        return updateFile(file, existingFileId);
    }

    public int uploadFile(File file, String folderId) {
        String id = BuildConfig.FLAVOR;
        try {
            if (TextUtils.isEmpty(((BoxFile) ((UploadFile) new BoxApiFile(this.mSession).getUploadRequest(file, folderId).setFileName("sync_default.walletx").setProgressListener(new 1(this))).send()).getId())) {
                return 0;
            }
            return 0;
        } catch (BoxException e) {
            e.printStackTrace();
            return -116;
        }
    }

    public int updateFile(File file, String existingFileId) {
        try {
            BoxFile updatedFile = (BoxFile) ((UploadNewVersion) new BoxApiFile(this.mSession).getUploadNewVersionRequest(file, existingFileId).setProgressListener(new 2(this))).send();
            return this.fileUpdatedSuccess;
        } catch (BoxException e) {
            e.printStackTrace();
            return -116;
        }
    }

    public int createFolder(File file) {
        try {
            return uploadFile(file, ((BoxFolder) new BoxApiFolder(this.mSession).getCreateRequest(BoxConstants.ROOT_FOLDER_ID, "Enpass").send()).getId());
        } catch (BoxException e) {
            e.printStackTrace();
            return -117;
        }
    }

    public void requestLatest() {
        checkMetaData();
    }

    public void setDelegate(IRemoteStorageDelegate aDelegate) {
        this.mRemoteStorageDelegate = aDelegate;
    }

    public boolean isDirty() {
        return EnpassApplication.getInstance().getSharedPreferences(BOX_DIRTY_PREFERENCE, 0).getBoolean(BOX_DIRTY_PREFERENCE, false);
    }

    public void setDirty(boolean value) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(BOX_DIRTY_PREFERENCE, 0).edit();
        edit.putBoolean(BOX_DIRTY_PREFERENCE, value);
        edit.commit();
    }

    public boolean isLatestUpload() {
        return this.mLatestUploaded;
    }

    public void uploadLatest() {
        this.mBoxState = BoxState.Uploading;
        this.mLatestUploaded = false;
        new UploadFileOnBox(this, null).execute(new Void[0]);
    }

    public void setLatestUploaded(boolean value) {
        this.mLatestUploaded = value;
    }

    public void clearState() {
        this.mLatestUploaded = false;
    }

    public String getLatestFile() {
        Utils.delete("box.db.sync", this.mContext);
        if (Utils.isFileExist("box.db", this.mContext)) {
            return Utils.copySyncDbFile("box.db", this.mContext);
        }
        storeModifiedTime(0);
        return null;
    }

    public void abort() {
        try {
            if (this.mOutputStream != null) {
                this.mOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public int getIdentifier() {
        return 6;
    }

    public void setPasswordChangePending(boolean value) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(PASSWORD_CHANGE_PENDING_PREFERENCE, 0).edit();
        edit.putBoolean("passwordChange", value);
        edit.commit();
    }

    public boolean getPasswordChangePending() {
        return EnpassApplication.getInstance().getSharedPreferences(PASSWORD_CHANGE_PENDING_PREFERENCE, 0).getBoolean("passwordChange", false);
    }

    public boolean isFileExistOnCloud() {
        return this.isFileExistOncloud;
    }

    public String getFileName() {
        return "box.db";
    }

    void clearLastSaveTime() {
        storeModifiedTime(0);
        this.mFileModifiedTimeOnCloud = 0;
    }

    void clear() {
        EnpassApplication.getInstance().getSharedPreferences(PASSWORD_CHANGE_PENDING_PREFERENCE, 0).edit().clear().commit();
        EnpassApplication.getInstance().getSharedPreferences(BOX_ENABLE_PREFERENCE, 0).edit().clear().commit();
        EnpassApplication.getInstance().getSharedPreferences(BOX_PREF, 0).edit().clear().commit();
        storeModifiedTime(0);
        this.mFileModifiedTimeOnCloud = 0;
        setDirty(false);
        this.mLatestUploaded = true;
        Utils.delete("box.db", this.mContext);
        logoutCurrentSession();
    }

    void logoutCurrentSession() {
        if (this.mSession != null) {
            this.mSession.logout().addOnCompletedListener(new 3(this));
        }
    }

    private long convertDateStringToMillisec(String updateTime) {
        long timeInMillisec = 0;
        try {
            timeInMillisec = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).parse(updateTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMillisec;
    }

    void storeModifiedTime(long modifiedTime) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(FILE_MODIFIED_TIME_ON_BOX, 0).edit();
        edit.putLong("timeInSec", modifiedTime);
        edit.commit();
    }

    long restoreLastModofiedTimeStamp() {
        return EnpassApplication.getInstance().getSharedPreferences(FILE_MODIFIED_TIME_ON_BOX, 0).getLong("timeInSec", 0);
    }
}
