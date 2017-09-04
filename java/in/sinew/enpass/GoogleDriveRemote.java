package in.sinew.enpass;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import com.github.clans.fab.BuildConfig;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Builder;
import com.google.api.services.drive.DriveScopes;
import in.sinew.enpassengine.Utils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class GoogleDriveRemote implements IRemoteStorage {
    public static final String ACCESS_LAST_MODIFIED_TIME = "lastModifiedTime";
    static final int COMPLETE_AUTHORIZATION_REQUEST_CODE = 2;
    public static final String DRIVE_DIRTY_PREFERENCE = "googleDriveDirty";
    public static final String GD_PASSWORD_CHANGE_PENDING_PREFERENCE = "GDChangePending";
    private static final String GOOGLE_DRIVE_ACC_NAME_PREF = "auth_account";
    static final int REQUEST_ACCOUNT_PICKER = 1;
    String APP_NAME = "in.sinew.enpass";
    GoogleAccountCredential credential = null;
    private boolean isFileExistOncloud = true;
    Context mContext;
    boolean mDirty;
    FileOutputStream mFileOutputStream = null;
    private boolean mGDPasswordChangePending = false;
    GoogleDriveState mGDState = GoogleDriveState.GDIdle;
    long mLastModifiedTimeInSecs;
    boolean mLatestUploaded = true;
    IRemoteStorageDelegate mRemoteStorageDelegate = null;
    Drive mService;

    public GoogleDriveRemote(Context context) {
        this.mContext = context;
        initializeDriveService();
    }

    String getAccountName() {
        return EnpassApplication.getInstance().getSharedPreferences(GOOGLE_DRIVE_ACC_NAME_PREF, 0).getString("accountName", BuildConfig.FLAVOR);
    }

    void initializeDriveService() {
        Context context = this.mContext;
        String[] strArr = new String[REQUEST_ACCOUNT_PICKER];
        strArr[0] = DriveScopes.DRIVE;
        this.credential = GoogleAccountCredential.usingOAuth2(context, Arrays.asList(strArr));
        String accountName = getAccountName();
        if (!accountName.equals(BuildConfig.FLAVOR)) {
            this.credential.setSelectedAccountName(accountName);
            this.mService = new Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), this.credential).setApplicationName(this.APP_NAME).build();
        }
    }

    public boolean isDirty() {
        this.mDirty = EnpassApplication.getInstance().getSharedPreferences(DRIVE_DIRTY_PREFERENCE, 0).getBoolean("gdDirty", false);
        return this.mDirty;
    }

    public boolean isLatestUpload() {
        return this.mLatestUploaded;
    }

    public void setDelegate(IRemoteStorageDelegate aDelegate) {
        this.mRemoteStorageDelegate = aDelegate;
    }

    public void requestLatest() {
        checkGoogleDriveMetaData();
    }

    void checkGoogleDriveMetaData() {
        this.isFileExistOncloud = true;
        this.mGDState = GoogleDriveState.GDMetadata;
        new GetMetaDataTask(this).execute(new Void[0]);
    }

    public void uploadLatest() {
        this.mGDState = GoogleDriveState.GDUploading;
        this.mLatestUploaded = false;
        new UploadFileOnDrive(this).execute(new Void[0]);
    }

    public void setLatestUploaded(boolean value) {
        this.mLatestUploaded = value;
    }

    public String getLatestFile() {
        Utils.delete("googledrive.db.sync", this.mContext);
        if (Utils.isFileExist("googledrive.db", this.mContext)) {
            return Utils.copySyncDbFile("googledrive.db", this.mContext);
        }
        storeModifiedTime(0);
        return null;
    }

    public void abort() {
        try {
            if (this.mFileOutputStream != null) {
                this.mFileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDirty(boolean value) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(DRIVE_DIRTY_PREFERENCE, 0).edit();
        edit.putBoolean("gdDirty", value);
        edit.commit();
        this.mDirty = value;
    }

    public int getIdentifier() {
        return 4;
    }

    public boolean isFileExistOnCloud() {
        return this.isFileExistOncloud;
    }

    public void clearState() {
        this.mLatestUploaded = false;
    }

    public String getFileName() {
        return "googledrive.db";
    }

    public void setPasswordChangePending(boolean value) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(GD_PASSWORD_CHANGE_PENDING_PREFERENCE, 0).edit();
        edit.putBoolean("GDPasswordChange", value);
        edit.commit();
        this.mGDPasswordChangePending = value;
    }

    public boolean getPasswordChangePending() {
        this.mGDPasswordChangePending = EnpassApplication.getInstance().getSharedPreferences(GD_PASSWORD_CHANGE_PENDING_PREFERENCE, 0).getBoolean("GDPasswordChange", false);
        return this.mGDPasswordChangePending;
    }

    long restoreLastModofiedTimeStamp() {
        return EnpassApplication.getInstance().getSharedPreferences(ACCESS_LAST_MODIFIED_TIME, 0).getLong("timeInSec", 0);
    }

    void storeModifiedTime(long modifiedTime) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(ACCESS_LAST_MODIFIED_TIME, 0).edit();
        edit.putLong("timeInSec", modifiedTime);
        edit.commit();
    }

    void clear() {
        Editor driveEdit = EnpassApplication.getInstance().getSharedPreferences(GOOGLE_DRIVE_ACC_NAME_PREF, 0).edit();
        driveEdit.putString("accountName", BuildConfig.FLAVOR);
        driveEdit.commit();
        EnpassApplication.getInstance().getSharedPreferences(GD_PASSWORD_CHANGE_PENDING_PREFERENCE, 0).edit().clear().commit();
        storeModifiedTime(0);
        this.mLastModifiedTimeInSecs = 0;
        setDirty(false);
        this.mLatestUploaded = true;
        Utils.delete("googledrive.db", this.mContext);
        if (this.mService != null) {
            this.mService = null;
        }
    }

    public void clearLastModifiedTime() {
        storeModifiedTime(0);
        this.mLastModifiedTimeInSecs = 0;
    }
}
