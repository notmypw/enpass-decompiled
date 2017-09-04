package in.sinew.enpass;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import com.box.androidsdk.content.models.BoxFileVersion;
import com.microsoft.live.LiveAuthClient;
import com.microsoft.live.LiveConnectClient;
import com.microsoft.live.LiveConnectSession;
import com.microsoft.live.LiveDownloadOperation;
import com.microsoft.live.LiveOperation;
import com.microsoft.live.LiveOperationListener;
import com.microsoft.live.LiveStatus;
import in.sinew.enpassengine.Utils;
import io.enpass.app.R;
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;

public class OneDriveRemote implements IRemoteStorage {
    private static final String ENABLE_ONE_DRIVE_PREF = "one_drive_pref";
    public static final String FILE_MODIFIED_TIME_ON_ONEDRIVE = "lastModifiedTimeOnOneDrive";
    public static final String LOGGED_OUT = "The user has is logged out.";
    public static final String ONEDRIVE_DIRTY_PREFERENCE = "oneDriveDirty";
    public static final String ONEDRIVE_PASSWORD_CHANGE_PENDING_PREFERENCE = "onedrivechangePending";
    private String FILE_ON_ONEDRIVE = "sync_default.walletx";
    private String FOLDER_ON_ONEDRIVE = "Enpass";
    String TAG = "OneDriveRemote";
    private LiveAuthClient auth;
    private boolean isFileExistOncloud = true;
    private LiveConnectClient mClient;
    Context mContext;
    boolean mDirty;
    LiveDownloadOperation mDownloadRequest;
    String mFileId;
    long mFileUpdateTimeOnDrive;
    boolean mFolderNotExist = false;
    boolean mLatestUploaded = true;
    String mOneDriveFolderId;
    OneDriveState mOneDriveState = OneDriveState.Idle;
    private boolean mPasswordChangePending = false;
    IRemoteStorageDelegate mRemoteStorageDelegate = null;
    ILiveStatusCallbackHandler mStatusCallbackHandler;
    LiveOperation mUploadRequest;

    public OneDriveRemote(Context context) {
        this.mContext = context;
        if (EnpassApplication.getInstance().getAppSettings().getRemote() == 5) {
            initializeClient(null);
        }
    }

    void initializeClient(LiveConnectSession session) {
        if (session == null) {
            Iterable<String> scopes = Arrays.asList(new String[]{"wl.signin", "wl.skydrive_update", "wl.offline_access"});
            this.auth = new LiveAuthClient(this.mContext, "0000000048111D9C");
            this.auth.initialize(scopes, new 1(this));
            return;
        }
        this.mClient = new LiveConnectClient(session);
        if (this.mStatusCallbackHandler != null) {
            this.mStatusCallbackHandler.status(LiveStatus.CONNECTED);
        }
    }

    public boolean isDirty() {
        this.mDirty = EnpassApplication.getInstance().getSharedPreferences(ONEDRIVE_DIRTY_PREFERENCE, 0).getBoolean("onedrive_dirty", false);
        return this.mDirty;
    }

    public boolean isLatestUpload() {
        return this.mLatestUploaded;
    }

    public void setDelegate(IRemoteStorageDelegate aDelegate) {
        this.mRemoteStorageDelegate = aDelegate;
    }

    public void requestLatest() {
        checkMetaData();
    }

    public boolean isFileExistOnCloud() {
        return this.isFileExistOncloud;
    }

    void checkMetaData() {
        this.isFileExistOncloud = true;
        this.mOneDriveState = OneDriveState.Metadata;
        getMetaData();
    }

    void getMetaData() {
        try {
            this.mClient.getAsync("me/skydrive/files/", new 2(this));
        } catch (IllegalStateException e) {
            e.printStackTrace();
            if (e.getMessage().equals(LOGGED_OUT)) {
                this.mRemoteStorageDelegate.latestRequestError(this, String.format(this.mContext.getResources().getString(R.string.not_connect_odrive), new Object[0]) + " Error Code :-113");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            this.mRemoteStorageDelegate.latestRequestError(this, String.format(this.mContext.getResources().getString(R.string.not_connect_odrive), new Object[0]) + " Error Code :-113");
        }
    }

    void downloadFile(String fileId) {
        String path = fileId + "/content";
        new DownloadFileFromOneDrive(this, null).execute(new String[]{path});
    }

    void uploadFileOnOneDrive() {
        if (this.mFolderNotExist) {
            createFolder();
        } else {
            new UploadOnOneDrive(this, null).execute(new Void[0]);
        }
    }

    void createFolder() {
        LiveOperationListener opListener = new 3(this);
        try {
            JSONObject body = new JSONObject();
            body.put(BoxFileVersion.FIELD_NAME, this.FOLDER_ON_ONEDRIVE);
            this.mClient.postAsync("me/skydrive", body, opListener);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    void checkFile(String folderId) {
        try {
            this.mClient.getAsync(folderId + "/files", new 4(this));
        } catch (IllegalStateException e) {
            e.printStackTrace();
            if (e.getMessage().equals(LOGGED_OUT)) {
                this.mRemoteStorageDelegate.latestRequestError(this, String.format(this.mContext.getResources().getString(R.string.not_connect_odrive), new Object[0]) + " Error Code :-113");
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    public void uploadLatest() {
        this.mOneDriveState = OneDriveState.Uploading;
        this.mLatestUploaded = false;
        uploadFileOnOneDrive();
    }

    public void setLatestUploaded(boolean value) {
        this.mLatestUploaded = value;
    }

    public String getLatestFile() {
        Utils.delete("onedrive.db.sync", this.mContext);
        if (Utils.isFileExist("onedrive.db", this.mContext)) {
            return Utils.copySyncDbFile("onedrive.db", this.mContext);
        }
        storeModifiedTime(0);
        return null;
    }

    public void abort() {
        try {
            if (this.mUploadRequest != null) {
                this.mUploadRequest.cancel();
            }
            if (this.mDownloadRequest != null) {
                this.mDownloadRequest.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDirty(boolean value) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(ONEDRIVE_DIRTY_PREFERENCE, 0).edit();
        edit.putBoolean("onedrive_dirty", value);
        edit.commit();
        this.mDirty = value;
    }

    public int getIdentifier() {
        return 5;
    }

    public void clearState() {
        this.mLatestUploaded = false;
    }

    public String getFileName() {
        return "onedrive.db";
    }

    public void setPasswordChangePending(boolean value) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(ONEDRIVE_PASSWORD_CHANGE_PENDING_PREFERENCE, 0).edit();
        edit.putBoolean("passwordChange", value);
        edit.commit();
        this.mPasswordChangePending = value;
    }

    public boolean getPasswordChangePending() {
        this.mPasswordChangePending = EnpassApplication.getInstance().getSharedPreferences(ONEDRIVE_PASSWORD_CHANGE_PENDING_PREFERENCE, 0).getBoolean("passwordChange", false);
        return this.mPasswordChangePending;
    }

    long restoreLastModofiedTimeStamp() {
        return EnpassApplication.getInstance().getSharedPreferences(FILE_MODIFIED_TIME_ON_ONEDRIVE, 0).getLong("timeInSec", 0);
    }

    void storeModifiedTime(long modifiedTime) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(FILE_MODIFIED_TIME_ON_ONEDRIVE, 0).edit();
        edit.putLong("timeInSec", modifiedTime);
        edit.commit();
    }

    void clear() {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(ENABLE_ONE_DRIVE_PREF, 0).edit();
        edit.putBoolean("isOneDriveEnable", false);
        edit.commit();
        EnpassApplication.getInstance().getSharedPreferences(ONEDRIVE_PASSWORD_CHANGE_PENDING_PREFERENCE, 0).edit().clear().commit();
        storeModifiedTime(0);
        this.mFileUpdateTimeOnDrive = 0;
        setDirty(false);
        this.mLatestUploaded = true;
        Utils.delete("onedrive.db", this.mContext);
        if (this.auth != null) {
            this.auth.logout(new 5(this));
        }
        if (this.auth != null) {
            this.auth = null;
        }
        if (this.mClient != null) {
            this.mClient = null;
        }
    }

    void clearLastSaveTime() {
        storeModifiedTime(0);
        this.mFileId = null;
        this.mOneDriveFolderId = null;
        this.mFileUpdateTimeOnDrive = 0;
        if (this.mClient != null) {
            this.mClient = null;
        }
    }

    public void setOneDriveDelegate(ILiveStatusCallbackHandler handler) {
        this.mStatusCallbackHandler = handler;
    }
}
