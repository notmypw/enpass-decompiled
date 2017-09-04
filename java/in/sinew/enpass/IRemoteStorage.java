package in.sinew.enpass;

public interface IRemoteStorage {
    public static final int BOX_REMOTE = 6;
    public static final int DROPBOX_REMOTE = 2;
    public static final int FOLDER_REMOTE = 8;
    public static final int GOOGLE_DRIVE_REMOTE = 4;
    public static final int ICLOUD_REMOTE = 3;
    public static final int LOCAL_REMOTE = 1;
    public static final int ONE_DRIVE_REMOTE = 5;
    public static final int PIN = 7;
    public static final int WEBDAV_REMOTE = 9;
    public static final int WEBDAV_REMOTE_PASSWORD = 11;
    public static final int WEBDAV_REMOTE_USERNAME = 10;

    void abort();

    void clearState();

    String getFileName();

    int getIdentifier();

    String getLatestFile();

    boolean getPasswordChangePending();

    boolean isDirty();

    boolean isFileExistOnCloud();

    boolean isLatestUpload();

    void requestLatest();

    void setDelegate(IRemoteStorageDelegate iRemoteStorageDelegate);

    void setDirty(boolean z);

    void setLatestUploaded(boolean z);

    void setPasswordChangePending(boolean z);

    void uploadLatest();
}
