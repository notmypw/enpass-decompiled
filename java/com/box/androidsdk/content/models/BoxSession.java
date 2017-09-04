package com.box.androidsdk.content.models;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.box.androidsdk.content.BoxApiUser;
import com.box.androidsdk.content.BoxConfig;
import com.box.androidsdk.content.BoxException;
import com.box.androidsdk.content.BoxException.ErrorType;
import com.box.androidsdk.content.BoxException.RefreshFailure;
import com.box.androidsdk.content.BoxFutureTask;
import com.box.androidsdk.content.auth.BoxAuthentication;
import com.box.androidsdk.content.auth.BoxAuthentication.AuthListener;
import com.box.androidsdk.content.auth.BoxAuthentication.AuthenticationRefreshProvider;
import com.box.androidsdk.content.auth.BoxAuthentication.BoxAuthenticationInfo;
import com.box.androidsdk.content.requests.BoxRequest;
import com.box.androidsdk.content.utils.BoxLogUtils;
import com.box.androidsdk.content.utils.SdkUtils;
import com.box.sdk.android.BuildConfig;
import com.box.sdk.android.R;
import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import net.sqlcipher.database.SQLiteDatabase;

public class BoxSession extends BoxObject implements AuthListener, Serializable {
    private static final transient ThreadPoolExecutor AUTH_CREATION_EXECUTOR = SdkUtils.createDefaultThreadPoolExecutor(1, 20, 3600, TimeUnit.SECONDS);
    private static AtomicLong mLastToastTime = new AtomicLong();
    private static final long serialVersionUID = 8122900496609434013L;
    protected String mAccountEmail;
    private transient Context mApplicationContext;
    protected BoxAuthenticationInfo mAuthInfo;
    protected String mClientId;
    protected String mClientRedirectUrl;
    protected String mClientSecret;
    protected String mDeviceId;
    protected String mDeviceName;
    protected boolean mEnableBoxAppAuthentication;
    protected Long mExpiresAt;
    protected BoxMDMData mMDMData;
    protected AuthenticationRefreshProvider mRefreshProvider;
    private String mUserAgent;
    private String mUserId;
    private transient AuthListener sessionAuthListener;

    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$box$androidsdk$content$BoxException$ErrorType = new int[ErrorType.values().length];

        static {
            try {
                $SwitchMap$com$box$androidsdk$content$BoxException$ErrorType[ErrorType.NETWORK_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    private static class BoxSessionAuthCreationRequest extends BoxRequest<BoxSession, BoxSessionAuthCreationRequest> implements AuthListener {
        private CountDownLatch authLatch;
        private final BoxSession mSession;

        public BoxSessionAuthCreationRequest(BoxSession session, boolean viaBoxApp) {
            super(null, " ", null);
            this.mSession = session;
        }

        public BoxSession send() throws BoxException {
            BoxSession boxSession;
            synchronized (this.mSession) {
                if (this.mSession.getUser() == null) {
                    if (!(this.mSession.getAuthInfo() == null || SdkUtils.isBlank(this.mSession.getAuthInfo().accessToken()))) {
                        try {
                            BoxUser user = (BoxUser) new BoxApiUser(this.mSession).getCurrentUserInfoRequest().send();
                            this.mSession.setUserId(user.getId());
                            this.mSession.getAuthInfo().setUser(user);
                            this.mSession.onAuthCreated(this.mSession.getAuthInfo());
                            boxSession = this.mSession;
                        } catch (BoxException e) {
                            BoxLogUtils.e("BoxSession", "Unable to repair user", e);
                            if ((e instanceof RefreshFailure) && ((RefreshFailure) e).isErrorFatal()) {
                                BoxSession.toastString(this.mSession.getApplicationContext(), R.string.boxsdk_error_fatal_refresh);
                            } else if (e.getErrorType() == ErrorType.TERMS_OF_SERVICE_REQUIRED) {
                                BoxSession.toastString(this.mSession.getApplicationContext(), R.string.boxsdk_error_terms_of_service);
                            } else {
                                this.mSession.onAuthFailure(null, e);
                                throw e;
                            }
                        }
                    }
                    BoxAuthentication.getInstance().addListener(this);
                    launchAuthUI();
                    boxSession = this.mSession;
                } else {
                    BoxAuthenticationInfo info = BoxAuthentication.getInstance().getAuthInfo(this.mSession.getUserId(), this.mSession.getApplicationContext());
                    if (info != null) {
                        BoxAuthenticationInfo.cloneInfo(this.mSession.mAuthInfo, info);
                        this.mSession.onAuthCreated(this.mSession.getAuthInfo());
                    } else {
                        this.mSession.mAuthInfo.setUser(null);
                        launchAuthUI();
                    }
                    boxSession = this.mSession;
                }
            }
            return boxSession;
        }

        private void launchAuthUI() {
            this.authLatch = new CountDownLatch(1);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    if (BoxSessionAuthCreationRequest.this.mSession.getRefreshProvider() == null || !BoxSessionAuthCreationRequest.this.mSession.getRefreshProvider().launchAuthUi(BoxSessionAuthCreationRequest.this.mSession.getUserId(), BoxSessionAuthCreationRequest.this.mSession)) {
                        BoxSessionAuthCreationRequest.this.mSession.startAuthenticationUI();
                    }
                }
            });
            try {
                this.authLatch.await();
            } catch (InterruptedException e) {
                this.authLatch.countDown();
            }
        }

        public void onRefreshed(BoxAuthenticationInfo info) {
        }

        public void onAuthCreated(BoxAuthenticationInfo info) {
            BoxAuthenticationInfo.cloneInfo(this.mSession.mAuthInfo, info);
            this.mSession.setUserId(info.getUser().getId());
            this.mSession.onAuthCreated(info);
            this.authLatch.countDown();
        }

        public void onAuthFailure(BoxAuthenticationInfo info, Exception ex) {
            this.authLatch.countDown();
        }

        public void onLoggedOut(BoxAuthenticationInfo info, Exception ex) {
        }
    }

    private static class BoxSessionLogoutRequest extends BoxRequest<BoxSession, BoxSessionLogoutRequest> {
        private BoxSession mSession;

        public BoxSessionLogoutRequest(BoxSession session) {
            super(null, " ", null);
            this.mSession = session;
        }

        public BoxSession send() throws BoxException {
            synchronized (this.mSession) {
                if (this.mSession.getUser() != null) {
                    BoxAuthentication.getInstance().logout(this.mSession);
                    this.mSession.getAuthInfo().wipeOutAuth();
                    this.mSession.setUserId(null);
                }
            }
            return this.mSession;
        }
    }

    private static class BoxSessionRefreshRequest extends BoxRequest<BoxSession, BoxSessionRefreshRequest> {
        private BoxSession mSession;

        public BoxSessionRefreshRequest(BoxSession session) {
            super(null, " ", null);
            this.mSession = session;
        }

        public BoxSession send() throws BoxException {
            try {
                BoxAuthenticationInfo boxAuthenticationInfo = (BoxAuthenticationInfo) BoxAuthentication.getInstance().refresh(this.mSession).get();
                BoxAuthenticationInfo.cloneInfo(this.mSession.mAuthInfo, BoxAuthentication.getInstance().getAuthInfo(this.mSession.getUserId(), this.mSession.getApplicationContext()));
                return this.mSession;
            } catch (Throwable e) {
                BoxException r = (BoxException) e.getCause();
                if (e.getCause() instanceof BoxException) {
                    throw ((BoxException) e.getCause());
                }
                throw new BoxException("BoxSessionRefreshRequest failed", e);
            }
        }
    }

    public BoxSession(Context context) {
        this(context, getBestStoredUserId(context));
    }

    private static String getBestStoredUserId(Context context) {
        String lastAuthenticatedUserId = BoxAuthentication.getInstance().getLastAuthenticatedUserId(context);
        Map<String, BoxAuthenticationInfo> authInfoMap = BoxAuthentication.getInstance().getStoredAuthInfo(context);
        if (authInfoMap != null) {
            if (!SdkUtils.isEmptyString(lastAuthenticatedUserId) && authInfoMap.get(lastAuthenticatedUserId) != null) {
                return lastAuthenticatedUserId;
            }
            if (authInfoMap.size() == 1) {
                Iterator it = authInfoMap.keySet().iterator();
                if (it.hasNext()) {
                    return (String) it.next();
                }
            }
        }
        return null;
    }

    public BoxSession(Context context, String userId) {
        this(context, userId, BoxConfig.CLIENT_ID, BoxConfig.CLIENT_SECRET, BoxConfig.REDIRECT_URL);
        if (!SdkUtils.isEmptyString(BoxConfig.DEVICE_NAME)) {
            setDeviceName(BoxConfig.DEVICE_NAME);
        }
        if (!SdkUtils.isEmptyString(BoxConfig.DEVICE_ID)) {
            setDeviceName(BoxConfig.DEVICE_ID);
        }
    }

    public BoxSession(Context context, String userId, String clientId, String clientSecret, String redirectUrl) {
        this.mUserAgent = BuildConfig.APPLICATION_ID;
        this.mEnableBoxAppAuthentication = BoxConfig.ENABLE_BOX_APP_AUTHENTICATION;
        this.mClientId = clientId;
        this.mClientSecret = clientSecret;
        this.mClientRedirectUrl = redirectUrl;
        if (SdkUtils.isEmptyString(this.mClientId) || SdkUtils.isEmptyString(this.mClientSecret)) {
            throw new RuntimeException("Session must have a valid client id and client secret specified.");
        }
        this.mApplicationContext = context.getApplicationContext();
        if (!SdkUtils.isEmptyString(userId)) {
            this.mAuthInfo = BoxAuthentication.getInstance().getAuthInfo(userId, context);
            this.mUserId = userId;
        }
        if (this.mAuthInfo == null) {
            this.mUserId = userId;
            this.mAuthInfo = new BoxAuthenticationInfo();
        }
        this.mAuthInfo.setClientId(this.mClientId);
        setupSession();
    }

    protected BoxSession(BoxSession session) {
        this.mUserAgent = BuildConfig.APPLICATION_ID;
        this.mEnableBoxAppAuthentication = BoxConfig.ENABLE_BOX_APP_AUTHENTICATION;
        this.mApplicationContext = session.mApplicationContext;
        setAuthInfo(session.getAuthInfo());
        setupSession();
    }

    public BoxSession(Context context, BoxAuthenticationInfo authInfo, AuthenticationRefreshProvider refreshProvider) {
        this.mUserAgent = BuildConfig.APPLICATION_ID;
        this.mEnableBoxAppAuthentication = BoxConfig.ENABLE_BOX_APP_AUTHENTICATION;
        this.mApplicationContext = context.getApplicationContext();
        setAuthInfo(authInfo);
        this.mRefreshProvider = refreshProvider;
        setupSession();
    }

    protected void setAuthInfo(BoxAuthenticationInfo authInfo) {
        if (authInfo != null) {
            this.mAuthInfo = authInfo;
            if (authInfo.getUser() != null && !SdkUtils.isBlank(authInfo.getUser().getId())) {
                setUserId(authInfo.getUser().getId());
            }
        }
    }

    public BoxSession(Context context, String accessToken, AuthenticationRefreshProvider refreshProvider) {
        this(context, createSimpleBoxAuthenticationInfo(accessToken), refreshProvider);
    }

    private static BoxAuthenticationInfo createSimpleBoxAuthenticationInfo(String accessToken) {
        BoxAuthenticationInfo info = new BoxAuthenticationInfo();
        info.setAccessToken(accessToken);
        return info;
    }

    public void setEnableBoxAppAuthentication(boolean enabled) {
        this.mEnableBoxAppAuthentication = enabled;
    }

    public boolean isEnabledBoxAppAuthentication() {
        return this.mEnableBoxAppAuthentication;
    }

    public void setApplicationContext(Context context) {
        this.mApplicationContext = context.getApplicationContext();
    }

    public Context getApplicationContext() {
        return this.mApplicationContext;
    }

    public void setSessionAuthListener(AuthListener listener) {
        this.sessionAuthListener = listener;
    }

    protected void setupSession() {
        boolean isDebug = false;
        try {
            if (!(this.mApplicationContext == null || this.mApplicationContext.getPackageManager() == null)) {
                isDebug = (this.mApplicationContext.getPackageManager().getPackageInfo(this.mApplicationContext.getPackageName(), 0).applicationInfo.flags & 2) != 0;
            }
        } catch (NameNotFoundException e) {
        }
        BoxConfig.IS_DEBUG = isDebug;
        BoxAuthentication.getInstance().addListener(this);
    }

    public BoxUser getUser() {
        return this.mAuthInfo.getUser();
    }

    public String getUserId() {
        return this.mUserId;
    }

    protected void setUserId(String userId) {
        this.mUserId = userId;
    }

    public BoxAuthenticationInfo getAuthInfo() {
        return this.mAuthInfo;
    }

    public AuthenticationRefreshProvider getRefreshProvider() {
        return this.mRefreshProvider;
    }

    public void setDeviceId(String deviceId) {
        this.mDeviceId = deviceId;
    }

    public String getDeviceId() {
        return this.mDeviceId;
    }

    public void setDeviceName(String deviceName) {
        this.mDeviceName = deviceName;
    }

    public String getDeviceName() {
        return this.mDeviceName;
    }

    public String getUserAgent() {
        return this.mUserAgent;
    }

    public void setManagementData(BoxMDMData mdmData) {
        this.mMDMData = mdmData;
    }

    public BoxMDMData getManagementData() {
        return this.mMDMData;
    }

    public void setRefreshTokenExpiresAt(long expiresAt) {
        this.mExpiresAt = Long.valueOf(expiresAt);
    }

    public Long getRefreshTokenExpiresAt() {
        return this.mExpiresAt;
    }

    public void setBoxAccountEmail(String accountName) {
        this.mAccountEmail = accountName;
    }

    public String getBoxAccountEmail() {
        return this.mAccountEmail;
    }

    public BoxFutureTask<BoxSession> authenticate() {
        BoxFutureTask<BoxSession> task = new BoxSessionAuthCreationRequest(this, this.mEnableBoxAppAuthentication).toTask();
        AUTH_CREATION_EXECUTOR.submit(task);
        return task;
    }

    public BoxFutureTask<BoxSession> logout() {
        final BoxFutureTask<BoxSession> task = new BoxSessionLogoutRequest(this).toTask();
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... params) {
                task.run();
                return null;
            }
        }.execute(new Void[0]);
        return task;
    }

    public BoxFutureTask<BoxSession> refresh() {
        BoxFutureTask<BoxSession> task = new BoxSessionRefreshRequest(this).toTask();
        AUTH_CREATION_EXECUTOR.submit(task);
        return task;
    }

    public BoxSharedLinkSession getSharedLinkSession(String sharedLinkUri) {
        return new BoxSharedLinkSession(sharedLinkUri, this);
    }

    public File getCacheDir() {
        return new File(getApplicationContext().getFilesDir(), getUserId());
    }

    public void onRefreshed(BoxAuthenticationInfo info) {
        if (sameUser(info)) {
            BoxAuthenticationInfo.cloneInfo(this.mAuthInfo, info);
            if (this.sessionAuthListener != null) {
                this.sessionAuthListener.onRefreshed(info);
            }
        }
    }

    public void onAuthCreated(BoxAuthenticationInfo info) {
        if (sameUser(info)) {
            BoxAuthenticationInfo.cloneInfo(this.mAuthInfo, info);
            if (this.sessionAuthListener != null) {
                this.sessionAuthListener.onAuthCreated(info);
            }
        }
    }

    public void onAuthFailure(BoxAuthenticationInfo info, Exception ex) {
        if (sameUser(info) || (info == null && getUserId() == null)) {
            if (this.sessionAuthListener != null) {
                this.sessionAuthListener.onAuthFailure(info, ex);
            }
            if (ex instanceof BoxException) {
                switch (AnonymousClass3.$SwitchMap$com$box$androidsdk$content$BoxException$ErrorType[((BoxException) ex).getErrorType().ordinal()]) {
                    case SQLiteDatabase.OPEN_READONLY /*1*/:
                        toastString(this.mApplicationContext, R.string.boxsdk_error_network_connection);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    protected void startAuthenticationUI() {
        BoxAuthentication.getInstance().startAuthenticationUI(this);
    }

    private static void toastString(final Context context, final int id) {
        Handler handler = new Handler(Looper.getMainLooper());
        long currentTime = System.currentTimeMillis();
        if (currentTime - 3000 >= mLastToastTime.get()) {
            mLastToastTime.set(currentTime);
            handler.post(new Runnable() {
                public void run() {
                    Toast.makeText(context, id, 0).show();
                }
            });
        }
    }

    public void onLoggedOut(BoxAuthenticationInfo info, Exception ex) {
        if (sameUser(info)) {
            info.wipeOutAuth();
            getAuthInfo().wipeOutAuth();
            setUserId(null);
            if (this.sessionAuthListener != null) {
                this.sessionAuthListener.onLoggedOut(info, ex);
            }
        }
    }

    public String getClientId() {
        return this.mClientId;
    }

    public String getClientSecret() {
        return this.mClientSecret;
    }

    public String getRedirectUrl() {
        return this.mClientRedirectUrl;
    }

    private boolean sameUser(BoxAuthenticationInfo info) {
        return (info == null || info.getUser() == null || getUserId() == null || !getUserId().equals(info.getUser().getId())) ? false : true;
    }
}
