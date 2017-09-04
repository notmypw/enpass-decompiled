package in.sinew.enpass;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.http.OkHttp3Requestor;
import com.dropbox.core.v2.DbxClientV2;
import com.github.clans.fab.BuildConfig;
import in.sinew.enpassengine.Utils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DropboxRemote implements IRemoteStorage {
    private static final String ACCESS_KEY_NAME = "ACCESS_KEY";
    public static final String ACCESS_LAST_REMOTE_REV = "revision";
    private static final String ACCESS_SECRET_NAME = "ACCESS_SECRET";
    private static final String APP_KEY = "997mhkgbyipy0ti";
    private static final String APP_SECRET = "qih0u1ncaua2i06";
    private static final String DROPBOX_ACCESS_TOKEN = "dropbox_token";
    private static final String DROPBOX_ACCOUNT_PREFS_NAME = "sync_prefs";
    public static final String DROPBOX_DIRTY_PREFERENCE = "dropboxDirty";
    public static final String DROPBOX_ENABLE_PREFERENCE = "dropboxEnable";
    public static final String PASSWORD_CHANGE_PENDING_PREFERENCE = "changePending";
    private static DbxClientV2 sDbxClient;
    private boolean isFileExistOncloud = true;
    Context mContext;
    boolean mDirty;
    DropboxState mDropboxState = DropboxState.Idle;
    FileInputStream mFileUploadStream = null;
    String mLastRevision = BuildConfig.FLAVOR;
    boolean mLatestUploaded = true;
    FileOutputStream mOutputStream = null;
    private boolean mPasswordChangePending = false;
    IRemoteStorageDelegate mRemoteStorageDelegate = null;
    String mRevision = BuildConfig.FLAVOR;

    public DropboxRemote(Context aContext) {
        this.mContext = aContext;
        String token = getDropboxToken();
        if (token != null) {
            setDropboxToken(token);
            this.mContext.getSharedPreferences(DROPBOX_ACCOUNT_PREFS_NAME, 0).edit().clear().commit();
        }
        if (hasToken()) {
            init(EnpassApplication.getInstance().getSharedPreferences("dropbox_token_pref", 0).getString("access-token", BuildConfig.FLAVOR));
        }
    }

    private void init(String accessToken) {
        sDbxClient = new DbxClientV2(DbxRequestConfig.newBuilder("enpass-v2-api").withHttpRequestor(new OkHttp3Requestor(OkHttp3Requestor.defaultOkHttpClient())).build(), accessToken);
    }

    public DbxClientV2 getClient() {
        if (sDbxClient == null) {
            init(this.mContext.getSharedPreferences("dropbox_token_pref", 0).getString("access-token", BuildConfig.FLAVOR));
        }
        return sDbxClient;
    }

    public void disposeClient() {
        if (sDbxClient != null) {
            sDbxClient = null;
        }
    }

    private String getDropboxToken() {
        return this.mContext.getSharedPreferences(DROPBOX_ACCOUNT_PREFS_NAME, 0).getString(DROPBOX_ACCESS_TOKEN, null);
    }

    public boolean isDirty() {
        this.mDirty = this.mContext.getSharedPreferences(DROPBOX_DIRTY_PREFERENCE, 0).getBoolean(DROPBOX_DIRTY_PREFERENCE, false);
        return this.mDirty;
    }

    public void setDirty(boolean value) {
        Editor edit = this.mContext.getSharedPreferences(DROPBOX_DIRTY_PREFERENCE, 0).edit();
        edit.putBoolean(DROPBOX_DIRTY_PREFERENCE, value);
        edit.commit();
        this.mDirty = value;
    }

    public void requestLatest() {
        checkMetaData();
    }

    public boolean isLatestUpload() {
        return this.mLatestUploaded;
    }

    public void setLatestUploaded(boolean value) {
        this.mLatestUploaded = value;
    }

    public void uploadLatest() {
        this.mDropboxState = DropboxState.Uploading;
        this.mLatestUploaded = false;
        new uploadFileOnDropbox(this, null).execute(new Void[0]);
    }

    public void setDelegate(IRemoteStorageDelegate aDelegate) {
        this.mRemoteStorageDelegate = aDelegate;
    }

    void checkMetaData() {
        this.isFileExistOncloud = true;
        this.mDropboxState = DropboxState.Metadata;
        new GetMetaData(this, null).execute(new Void[0]);
    }

    public String getLatestFile() {
        Utils.delete("dropbox.db.sync", this.mContext);
        if (Utils.isFileExist("dropbox.db", this.mContext)) {
            return Utils.copySyncDbFile("dropbox.db", this.mContext);
        }
        storeRevision(BuildConfig.FLAVOR);
        return null;
    }

    public void abort() {
        try {
            if (this.mFileUploadStream != null) {
                this.mFileUploadStream.close();
            }
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
        return 2;
    }

    public boolean isFileExistOnCloud() {
        return this.isFileExistOncloud;
    }

    public void clearState() {
        this.mLatestUploaded = false;
    }

    public String getFileName() {
        return "dropbox.db";
    }

    void clear() {
        new SignoutFromDropbox(this).execute(new Void[0]);
        EnpassApplication.getInstance().getSharedPreferences(PASSWORD_CHANGE_PENDING_PREFERENCE, 0).edit().clear().commit();
        EnpassApplication.getInstance().getSharedPreferences(DROPBOX_ENABLE_PREFERENCE, 0).edit().clear().commit();
        EnpassApplication.getInstance().getSharedPreferences(DROPBOX_ACCOUNT_PREFS_NAME, 0).edit().clear().commit();
        EnpassApplication.getInstance().getSharedPreferences("dropbox_token_pref", 0).edit().clear().commit();
        storeRevision(BuildConfig.FLAVOR);
        this.mLastRevision = BuildConfig.FLAVOR;
        this.mRevision = BuildConfig.FLAVOR;
        setDirty(false);
        this.mLatestUploaded = true;
        Utils.delete("dropbox.db", this.mContext);
    }

    String restoreMetadataInfo() {
        return this.mContext.getSharedPreferences(ACCESS_LAST_REMOTE_REV, 0).getString(ACCESS_LAST_REMOTE_REV, BuildConfig.FLAVOR);
    }

    void storeRevision(String revision) {
        Editor edit = this.mContext.getSharedPreferences(ACCESS_LAST_REMOTE_REV, 0).edit();
        edit.putString(ACCESS_LAST_REMOTE_REV, revision);
        edit.commit();
    }

    public void setPasswordChangePending(boolean value) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(PASSWORD_CHANGE_PENDING_PREFERENCE, 0).edit();
        edit.putBoolean("passwordChange", value);
        edit.commit();
        this.mPasswordChangePending = value;
    }

    public boolean getPasswordChangePending() {
        this.mPasswordChangePending = EnpassApplication.getInstance().getSharedPreferences(PASSWORD_CHANGE_PENDING_PREFERENCE, 0).getBoolean("passwordChange", false);
        return this.mPasswordChangePending;
    }

    public void clearLastRevision() {
        storeRevision(BuildConfig.FLAVOR);
        this.mLastRevision = BuildConfig.FLAVOR;
        this.mRevision = BuildConfig.FLAVOR;
    }

    protected boolean hasToken() {
        if (this.mContext.getSharedPreferences("dropbox_token_pref", 0).getString("access-token", null) != null) {
            return true;
        }
        return false;
    }

    protected void setDropboxToken(String token) {
        this.mContext.getSharedPreferences("dropbox_token_pref", 0).edit().putString("access-token", token).apply();
    }
}
