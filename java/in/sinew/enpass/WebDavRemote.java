package in.sinew.enpass;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import in.sinew.enpass.webdav.AdvancedSslSocketFactory;
import in.sinew.enpass.webdav.AdvancedX509TrustManager;
import in.sinew.enpass.webdav.WebDavUtils;
import in.sinew.enpassengine.Utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;

public class WebDavRemote implements IRemoteStorage {
    public static final String PASSWORD_CHANGE_PENDING_PREFERENCE = "changePending";
    public static final String WEBDAV_DIRTY_PREFERENCE = "webDavDirty";
    private static AdvancedSslSocketFactory mAdvancedSslSocketFactory = null;
    private static X509HostnameVerifier mHostnameVerifier = null;
    final String FILE_MODIFIED_TIME_ON_WEBDAV = "lastModifiedTimeOnWebDav";
    private String FILE_ON_WEBDAV_SERVER = "sync_default.walletx";
    private String FOLDER_ON_WEBDAV_SERVER = "Enpass";
    private final String WEBDAV_SERVER_URL = "webDavServerUrl";
    Credentials credentials;
    boolean isAuthSuccess = true;
    private boolean isFileExistOncloud = true;
    public HttpClient mClient;
    Context mContext;
    boolean mDirty;
    long mFileUpdateTimeOnDrive;
    boolean mIsFolderCreated = false;
    boolean mLatestUploaded = true;
    FileOutputStream mOutputStream = null;
    private boolean mPasswordChangePending = false;
    IRemoteStorageDelegate mRemoteStorageDelegate = null;
    boolean mWebDavFolderExist = false;
    String mWebDavServerUrl;
    private List<ThreadLocal<SimpleDateFormat>> threadLocals = new ArrayList();

    public WebDavRemote(Context context) {
        if (EnpassApplication.getInstance().getAppSettings().isRemoteActive() && EnpassApplication.getInstance().getAppSettings().getRemote() == 9) {
            authenticate(context, String.valueOf((char[]) EnpassApplication.getInstance().getKeychain().getPoolDataForRow(10)), String.valueOf((char[]) EnpassApplication.getInstance().getKeychain().getPoolDataForRow(11)), EnpassApplication.getInstance().getAppSettings().getWebDavServerUrl());
        }
        initialzieDateParser();
    }

    public void authenticate(Context aContext, String aUsername, String aPassword, String aUrl) {
        try {
            this.mContext = aContext;
            this.mWebDavServerUrl = aUrl;
            HostConfiguration hostConfig = new HostConfiguration();
            Protocol lEasyHttps = new Protocol("https", getAdvancedSslSocketFactory(this.mContext), 443);
            Protocol.registerProtocol("https", lEasyHttps);
            hostConfig.setHost(this.mWebDavServerUrl, 443, lEasyHttps);
            HttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
            HttpConnectionManagerParams params1 = new HttpConnectionManagerParams();
            params1.setMaxConnectionsPerHost(hostConfig, 20);
            connectionManager.setParams(params1);
            this.mClient = new HttpClient(connectionManager);
            this.mClient.setHostConfiguration(hostConfig);
            this.mClient.getParams().setParameter("http.protocol.single-cookie-header", Boolean.valueOf(true));
            this.mClient.getState().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(aUsername, aPassword));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public boolean isDirty() {
        this.mDirty = EnpassApplication.getInstance().getSharedPreferences(WEBDAV_DIRTY_PREFERENCE, 0).getBoolean(WEBDAV_DIRTY_PREFERENCE, false);
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

    public void uploadLatest() {
        this.mLatestUploaded = false;
        uploadFileOnWebDav();
    }

    public void setLatestUploaded(boolean value) {
        this.mLatestUploaded = value;
    }

    public String getLatestFile() {
        Utils.delete("webdav.db.sync", this.mContext);
        if (Utils.isFileExist("webdav.db", this.mContext)) {
            return Utils.copySyncDbFile("webdav.db", this.mContext);
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

    public void setDirty(boolean value) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(WEBDAV_DIRTY_PREFERENCE, 0).edit();
        edit.putBoolean(WEBDAV_DIRTY_PREFERENCE, value);
        edit.commit();
        this.mDirty = value;
    }

    public int getIdentifier() {
        return 9;
    }

    public void clearState() {
        this.mLatestUploaded = false;
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

    public boolean isFileExistOnCloud() {
        return this.isFileExistOncloud;
    }

    void clearLastSaveTime() {
        storeModifiedTime(0);
        this.mFileUpdateTimeOnDrive = 0;
    }

    void clear() {
        EnpassApplication.getInstance().getSharedPreferences(PASSWORD_CHANGE_PENDING_PREFERENCE, 0).edit().clear().commit();
        storeModifiedTime(0);
        this.mFileUpdateTimeOnDrive = 0;
        setDirty(false);
        this.mLatestUploaded = true;
        Utils.delete("webdav.db", this.mContext);
        if (this.mClient != null) {
            this.mClient = null;
        }
    }

    public String getFileName() {
        return "webdav.db";
    }

    void uploadFileOnWebDav() {
        if (this.mWebDavFolderExist) {
            new UploadFileOnWebDav(this, null).execute(new Void[0]);
        } else {
            new CreateWebDavFolder(this).execute(new Void[0]);
        }
    }

    void checkMetaData() {
        this.isFileExistOncloud = true;
        new GetMetaData(this).execute(new Void[0]);
    }

    long restoreLastModofiedTimeStamp() {
        return EnpassApplication.getInstance().getSharedPreferences("lastModifiedTimeOnWebDav", 0).getLong("timeInSec", 0);
    }

    void storeModifiedTime(long modifiedTime) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences("lastModifiedTimeOnWebDav", 0).edit();
        edit.putLong("timeInSec", modifiedTime);
        edit.commit();
    }

    private void createFile(File aFile, InputStream input) {
        OutputStream fOut = null;
        try {
            fOut = new FileOutputStream(aFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] buffer = new byte[1024];
        while (true) {
            try {
                int len = input.read(buffer);
                if (len > 0) {
                    fOut.write(buffer, 0, len);
                } else {
                    fOut.flush();
                    fOut.close();
                    return;
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                return;
            }
        }
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public HttpClient getWebDavClient() {
        return this.mClient;
    }

    public String getWebDavUrl() {
        return this.mWebDavServerUrl;
    }

    public void initialzieDateParser() {
        this.threadLocals.clear();
        TimeZone tz = TimeZone.getTimeZone("Etc/UTC");
        List<String> formats = new ArrayList();
        formats.add("EEE, dd MMM yyyy HH:mm:ss 'GMT'");
        formats.add("EEE, dd MMM y HH:mm:ss zzz");
        formats.add("yyyy-MM-dd'T'HH:mm:ss'Z'");
        formats.add("yyyy-MM-dd'T'HH:mm:ss");
        formats.add("d MMM yyyy hh:mm:ss");
        for (String format : formats) {
            this.threadLocals.add(new 1(this, format, tz));
        }
    }

    public Date parseDate(String dateStr) throws ParseException {
        Iterator it = this.threadLocals.iterator();
        while (it.hasNext()) {
            try {
                return ((SimpleDateFormat) ((ThreadLocal) it.next()).get()).parse(dateStr);
            } catch (ParseException e) {
            }
        }
        return null;
    }

    public static AdvancedSslSocketFactory getAdvancedSslSocketFactory(Context context) throws GeneralSecurityException, IOException {
        if (mAdvancedSslSocketFactory == null) {
            TrustManager[] tms = new TrustManager[]{new AdvancedX509TrustManager(WebDavUtils.getKnownServersStore(context))};
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tms, null);
            mHostnameVerifier = new BrowserCompatHostnameVerifier();
            mAdvancedSslSocketFactory = new AdvancedSslSocketFactory(sslContext, trustMgr, mHostnameVerifier);
        }
        return mAdvancedSslSocketFactory;
    }
}
