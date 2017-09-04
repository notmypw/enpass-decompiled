package in.sinew.enpass;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import com.box.androidsdk.content.requests.BoxRequest.BoxRequestHandler;
import com.github.clans.fab.BuildConfig;
import in.sinew.enpassengine.Utils;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class SyncManager implements IRemoteStorageDelegate {
    public static final String ERROR_PREFERENCE = "errorPrefrence";
    public static final String LASTSYNCTIME_PREFERENCE = "lastSyncTimePrefrence";
    public static final String LAST_SYNC_ATTEMPTIME_PREFERENCE = "lastSyncAttempTimePreference";
    boolean codeExecute = false;
    private boolean mAbort;
    private long mAbortRequestTime;
    IRemoteStorage mActiveCloudRemote;
    Context mContext;
    private String mError;
    Handler mHandler;
    IRemoteStorage mLocalRemote;
    private int mResumeSeconds;
    Runnable mRunnable;
    private boolean mRunning = false;
    ArrayList<ISyncManagerDelegate> mSyncDelegate;
    byte[] password;

    SyncManager(Context aContext) {
        this.mContext = aContext;
        this.mSyncDelegate = new ArrayList();
        this.mHandler = new Handler();
    }

    void scheduleSync() {
        scheduleSyncIn(15);
    }

    void scheduleSyncIn(int aSeconds) {
        this.mHandler.removeCallbacks(this.mRunnable);
        this.mRunnable = new 1(this);
        boolean appInBackground = EnpassApplication.getInstance().mAppInBackgorund;
        if (!requestAbort(aSeconds) && !appInBackground) {
            this.mHandler.postDelayed(this.mRunnable, (long) (aSeconds * 1000));
        }
    }

    void scheduleSyncFromKeyboard(int aSeconds) {
        this.mHandler.removeCallbacks(this.mRunnable);
        this.mRunnable = new 2(this);
        boolean isKeyboardVisible = EnpassApplication.getInstance().isKeyboardExtendedViewVisible();
        if (!requestAbort(aSeconds) && !isKeyboardVisible) {
            this.mHandler.postDelayed(this.mRunnable, (long) (aSeconds * 1000));
        }
    }

    void handleTimeout() {
        new Handler(this.mContext.getMainLooper()).post(new 3(this));
    }

    void startSync() {
        if (EnpassApplication.getInstance().getKeychain() != null) {
            this.mLocalRemote = EnpassApplication.getInstance();
            if (EnpassApplication.getInstance().getAppSettings().isRemoteActive()) {
                this.mActiveCloudRemote = EnpassApplication.getInstance().getActiveRemote();
                this.mActiveCloudRemote.setDelegate(this);
                this.mLocalRemote.setDelegate(this);
                clearState();
                syncStarted();
                this.mRunning = true;
                this.mActiveCloudRemote.requestLatest();
                return;
            }
            return;
        }
        scheduleSync();
    }

    void clearState() {
        this.mActiveCloudRemote.clearState();
        this.mLocalRemote.clearState();
        setError(BuildConfig.FLAVOR);
    }

    public void latestRequestDone(IRemoteStorage aRemote) {
        if (!abortIfRequired()) {
            boolean noOneDirty = true;
            if (this.mLocalRemote.isDirty() || this.mActiveCloudRemote.isDirty()) {
                noOneDirty = false;
            }
            if (noOneDirty) {
                this.mRunning = false;
                scheduleSync();
                setLastSyncAttemptedTime(new Date().getTime());
                syncDone();
                setError(BuildConfig.FLAVOR);
                return;
            }
            realSyncStarted();
            new RealSync(this).execute(new Void[0]);
        }
    }

    public void latestRequestError(IRemoteStorage aRemote, String aErrorMsg) {
        if (!abortIfRequired()) {
            setError(aErrorMsg);
            this.mRunning = false;
            scheduleSync();
            syncError(this.mError);
        }
    }

    void setError(String error) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(ERROR_PREFERENCE, 0).edit();
        edit.putString(BoxRequestHandler.OAUTH_ERROR_HEADER, error);
        edit.commit();
        this.mError = error;
    }

    String getError() {
        this.mError = EnpassApplication.getInstance().getSharedPreferences(ERROR_PREFERENCE, 0).getString(BoxRequestHandler.OAUTH_ERROR_HEADER, BuildConfig.FLAVOR);
        return this.mError;
    }

    public void setLastSyncTime(long lastSyncTimeInMillis) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(LASTSYNCTIME_PREFERENCE, 0).edit();
        edit.putLong("lastSyncTime", lastSyncTimeInMillis);
        edit.commit();
    }

    long getLastSyncTime() {
        return EnpassApplication.getInstance().getSharedPreferences(LASTSYNCTIME_PREFERENCE, 0).getLong("lastSyncTime", new Date().getTime());
    }

    public void setLastSyncAttemptedTime(long lastSyncAttempTimeInMillis) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(LAST_SYNC_ATTEMPTIME_PREFERENCE, 0).edit();
        edit.putLong("lastSyncAttemptTime", lastSyncAttempTimeInMillis);
        edit.commit();
    }

    long getLastSyncAttemptedTime() {
        return EnpassApplication.getInstance().getSharedPreferences(LAST_SYNC_ATTEMPTIME_PREFERENCE, 0).getLong("lastSyncAttemptTime", 0);
    }

    public void uploadRequestDone(IRemoteStorage aRemote) {
        if (this.mLocalRemote.isLatestUpload() && this.mActiveCloudRemote.isLatestUpload() && !abortIfRequired()) {
            setLastSyncAttemptedTime(new Date().getTime());
            this.mRunning = false;
            syncDone();
            setError(BuildConfig.FLAVOR);
            scheduleSync();
        }
    }

    public void uploadRequestError(IRemoteStorage aRemote, String aErrorMsg) {
        if (!abortIfRequired()) {
            setError(aErrorMsg);
            this.mRunning = false;
            syncError(aErrorMsg);
            scheduleSync();
        }
    }

    public void latestRequestNotFound() {
        if (!abortIfRequired()) {
            try {
                EnpassApplication.getInstance().getKeychain().setPoolDataForRow(this.mActiveCloudRemote.getIdentifier(), new String((char[]) EnpassApplication.getInstance().getKeychain().getPoolDataForRow(1)).getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (EnpassApplication.getInstance().getKeychain() != null) {
                Utils.copyFile(EnpassApplication.getInstance().getLatestFile(), this.mContext.getDatabasePath(this.mActiveCloudRemote.getFileName() + ".sync").getAbsolutePath());
                EnpassApplication.getInstance().setLatestUploaded(true);
            }
            this.mActiveCloudRemote.uploadLatest();
        }
    }

    private boolean requestAbort(int aSeconds) {
        if (this.mRunning) {
            this.mAbort = true;
            this.mAbortRequestTime = Calendar.getInstance().getTimeInMillis();
            this.mResumeSeconds = aSeconds;
        }
        return this.mAbort;
    }

    boolean abortIfRequired() {
        boolean ret = false;
        if (this.mAbort) {
            ret = true;
            this.mAbort = false;
            this.mRunning = false;
            syncAborted();
            int foundTime = this.mResumeSeconds - (((int) (Calendar.getInstance().getTimeInMillis() - this.mAbortRequestTime)) / 1000);
            if (foundTime < 0) {
                foundTime = 0;
            }
            scheduleSyncIn(foundTime);
        }
        return ret;
    }

    public void addSyncDelegate(ISyncManagerDelegate aDelegate) {
        if (!this.mSyncDelegate.contains(aDelegate)) {
            this.mSyncDelegate.add(aDelegate);
        }
    }

    public void removeSyncDelegate(ISyncManagerDelegate aDelegate) {
        this.mSyncDelegate.remove(aDelegate);
    }

    public void syncStarted() {
        Iterator it = this.mSyncDelegate.iterator();
        while (it.hasNext()) {
            ((ISyncManagerDelegate) it.next()).syncStarted();
        }
    }

    public void realSyncStarted() {
        Iterator it = this.mSyncDelegate.iterator();
        while (it.hasNext()) {
            ((ISyncManagerDelegate) it.next()).realSyncStarted();
        }
    }

    public void syncDone() {
        Iterator it = this.mSyncDelegate.iterator();
        while (it.hasNext()) {
            ((ISyncManagerDelegate) it.next()).syncDone();
        }
        if (EnpassApplication.getInstance().getCloseDbAfterSync()) {
            EnpassApplication.getInstance().closeDbImmediately();
            EnpassApplication.getInstance().setCloseDbAfterSync(false);
        }
    }

    public void syncError(String Errormsg) {
        Iterator it = this.mSyncDelegate.iterator();
        while (it.hasNext()) {
            ((ISyncManagerDelegate) it.next()).syncError(Errormsg);
        }
        if (EnpassApplication.getInstance().getCloseDbAfterSync()) {
            EnpassApplication.getInstance().closeDbImmediately();
            EnpassApplication.getInstance().setCloseDbAfterSync(false);
        }
    }

    public void syncPasswordError(IRemoteStorage aRemote) {
        Iterator it = this.mSyncDelegate.iterator();
        while (it.hasNext()) {
            ((ISyncManagerDelegate) it.next()).syncPasswordError(aRemote);
        }
        if (EnpassApplication.getInstance().getCloseDbAfterSync()) {
            EnpassApplication.getInstance().closeDbImmediately();
            EnpassApplication.getInstance().setCloseDbAfterSync(false);
        }
    }

    public void syncAborted() {
        Iterator it = this.mSyncDelegate.iterator();
        while (it.hasNext()) {
            ((ISyncManagerDelegate) it.next()).syncAborted();
        }
        if (EnpassApplication.getInstance().getCloseDbAfterSync()) {
            EnpassApplication.getInstance().closeDbImmediately();
            EnpassApplication.getInstance().setCloseDbAfterSync(false);
        }
    }

    public boolean isRunning() {
        return this.mRunning;
    }

    public void clean() {
        EnpassApplication.getInstance().getSharedPreferences(ERROR_PREFERENCE, 0).edit().clear().commit();
        EnpassApplication.getInstance().getSharedPreferences(LASTSYNCTIME_PREFERENCE, 0).edit().clear().commit();
    }
}
