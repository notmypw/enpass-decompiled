package in.sinew.enpass;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;
import in.sinew.enpassengine.Utils;
import io.enpass.app.R;
import java.io.File;

public class FolderRemote implements IRemoteStorage {
    public static final String FOLDER_DIRTY_PREFERENCE = "folderDirty";
    public static final String FOLDER_PASSWORD_CHANGE_PENDING_PREFERENCE = "folderchangePending";
    private final String ACCESS_LAST_MODIFIED_DATE = "last_modified_date";
    private final String FILE_NAME = "sync_default.walletx";
    private final String FOLDER_NAME = "Enpass";
    Context mContext;
    FolderState mFolderState = FolderState.Idle;
    private long mLastModifiedDate = -1;
    boolean mLatestUploaded = true;
    IRemoteStorageDelegate mRemoteStorageDelegate = null;

    public FolderRemote(Context context) {
        this.mContext = context;
    }

    public boolean isDirty() {
        return EnpassApplication.getInstance().getSharedPreferences(FOLDER_DIRTY_PREFERENCE, 0).getBoolean(FOLDER_DIRTY_PREFERENCE, false);
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

    public void uploadLatest() {
        this.mFolderState = FolderState.Uploading;
        this.mLatestUploaded = false;
        new UploadFileOnFolder(this, null).execute(new String[]{getFolderPath()});
    }

    public void setLatestUploaded(boolean value) {
        this.mLatestUploaded = value;
    }

    public String getLatestFile() {
        Utils.delete("folder.db.sync", this.mContext);
        if (Utils.isFileExist("folder.db", this.mContext)) {
            return Utils.copySyncDbFile("folder.db", this.mContext);
        }
        storeLastModifiedDate(-1);
        return null;
    }

    public void abort() {
    }

    public void setDirty(boolean value) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(FOLDER_DIRTY_PREFERENCE, 0).edit();
        edit.putBoolean(FOLDER_DIRTY_PREFERENCE, value);
        edit.commit();
    }

    public int getIdentifier() {
        return 8;
    }

    public void clearState() {
        this.mLatestUploaded = false;
    }

    public void setPasswordChangePending(boolean value) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(FOLDER_PASSWORD_CHANGE_PENDING_PREFERENCE, 0).edit();
        edit.putBoolean("passwordChange", value);
        edit.commit();
    }

    public boolean getPasswordChangePending() {
        return EnpassApplication.getInstance().getSharedPreferences(FOLDER_PASSWORD_CHANGE_PENDING_PREFERENCE, 0).getBoolean("passwordChange", false);
    }

    public boolean isFileExistOnCloud() {
        return true;
    }

    public String getFileName() {
        return "folder.db";
    }

    void checkMetaData() {
        this.mFolderState = FolderState.Metadata;
        String path = getFolderPath();
        new GetMetaData(this, null).execute(new String[]{path});
    }

    long getmLastModifiedDate() {
        return EnpassApplication.getInstance().getSharedPreferences("last_modified_date", 0).getLong("last_modified", -1);
    }

    void storeLastModifiedDate(long lastModified) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences("last_modified_date", 0).edit();
        edit.putLong("last_modified", lastModified);
        edit.commit();
    }

    private File isEnpassFolderExist(String filePath) {
        try {
            for (File ff : new File(filePath).listFiles()) {
                if (ff.isDirectory() && ff.getName().equals("Enpass")) {
                    return ff;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private File isSyncDefaultFileExist(String folderPath) {
        File syncDefaultFile = null;
        try {
            for (File ff : new File(folderPath).listFiles()) {
                if (!ff.isDirectory() && ff.getName().equals("sync_default.walletx")) {
                    syncDefaultFile = ff;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return syncDefaultFile;
    }

    private File createEnpassFolder(String filepath) {
        File file = new File(filepath);
        File enpassFolder = null;
        if (file != null && file.canWrite()) {
            enpassFolder = new File(file, "Enpass");
            if (!enpassFolder.mkdir()) {
                Toast.makeText(this.mContext, this.mContext.getString(R.string.create_folder_error), 1).show();
            }
        } else if (file == null || file.canWrite()) {
            Toast.makeText(this.mContext, this.mContext.getString(R.string.create_folder_error), 1).show();
        } else {
            Toast.makeText(this.mContext, this.mContext.getString(R.string.create_folder_error_no_write_access), 1).show();
        }
        return enpassFolder;
    }

    private String getFolderPath() {
        return EnpassApplication.getInstance().getSharedPreferences("folder_remote_path", 0).getString("folder_path", null);
    }

    void clear() {
        EnpassApplication.getInstance().getSharedPreferences(FOLDER_PASSWORD_CHANGE_PENDING_PREFERENCE, 0).edit().clear().commit();
        EnpassApplication.getInstance().getSharedPreferences("folder_remote_path", 0).edit().clear().commit();
        storeLastModifiedDate(-1);
        this.mLastModifiedDate = -1;
        setDirty(false);
        this.mLatestUploaded = true;
        Utils.delete("folder.db", this.mContext);
    }
}
