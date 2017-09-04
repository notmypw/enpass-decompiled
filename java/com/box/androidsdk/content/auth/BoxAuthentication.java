package com.box.androidsdk.content.auth;

import android.content.Context;
import android.content.Intent;
import com.box.androidsdk.content.BoxApiUser;
import com.box.androidsdk.content.BoxConfig;
import com.box.androidsdk.content.BoxConstants;
import com.box.androidsdk.content.BoxException;
import com.box.androidsdk.content.BoxException.RefreshFailure;
import com.box.androidsdk.content.models.BoxCollaborator;
import com.box.androidsdk.content.models.BoxJsonObject;
import com.box.androidsdk.content.models.BoxMapJsonObject;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.models.BoxUser;
import com.box.androidsdk.content.utils.BoxLogUtils;
import com.box.androidsdk.content.utils.SdkUtils;
import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import com.github.clans.fab.BuildConfig;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import net.sqlcipher.database.SQLiteDatabase;

public class BoxAuthentication {
    public static final ThreadPoolExecutor AUTH_EXECUTOR = SdkUtils.createDefaultThreadPoolExecutor(1, 1, 3600, TimeUnit.SECONDS);
    private static String TAG = BoxAuthentication.class.getName();
    private static BoxAuthentication mAuthentication = new BoxAuthentication();
    private int EXPIRATION_GRACE = 1000;
    private AuthStorage authStorage = new AuthStorage();
    private ConcurrentHashMap<String, BoxAuthenticationInfo> mCurrentAccessInfo;
    private ConcurrentLinkedQueue<WeakReference<AuthListener>> mListeners = new ConcurrentLinkedQueue();
    private AuthenticationRefreshProvider mRefreshProvider;
    private ConcurrentHashMap<String, FutureTask> mRefreshingTasks = new ConcurrentHashMap();

    public static class BoxAuthenticationInfo extends BoxJsonObject {
        public static final String FIELD_ACCESS_TOKEN = "access_token";
        public static final String FIELD_BASE_DOMAIN = "base_domain";
        public static final String FIELD_CLIENT_ID = "client_id";
        public static final String FIELD_EXPIRES_IN = "expires_in";
        private static final String FIELD_REFRESH_TIME = "refresh_time";
        public static final String FIELD_REFRESH_TOKEN = "refresh_token";
        public static final String FIELD_USER = "user";
        private static final long serialVersionUID = 2878150977399126399L;

        public BoxAuthenticationInfo clone() {
            BoxAuthenticationInfo cloned = new BoxAuthenticationInfo();
            cloneInfo(cloned, this);
            return cloned;
        }

        public static void cloneInfo(BoxAuthenticationInfo targetInfo, BoxAuthenticationInfo sourceInfo) {
            targetInfo.setAccessToken(sourceInfo.accessToken());
            targetInfo.setRefreshToken(sourceInfo.refreshToken());
            targetInfo.setRefreshTime(sourceInfo.getRefreshTime());
            targetInfo.setClientId(sourceInfo.getClientId());
            targetInfo.setBaseDomain(sourceInfo.getBaseDomain());
            if (targetInfo.getUser() == null) {
                targetInfo.setUser(sourceInfo.getUser());
            }
        }

        public String getClientId() {
            return (String) this.mProperties.get(FIELD_CLIENT_ID);
        }

        public String accessToken() {
            return (String) this.mProperties.get(FIELD_ACCESS_TOKEN);
        }

        public String refreshToken() {
            return (String) this.mProperties.get(FIELD_REFRESH_TOKEN);
        }

        public Long expiresIn() {
            return (Long) this.mProperties.get(FIELD_EXPIRES_IN);
        }

        public Long getRefreshTime() {
            return (Long) this.mProperties.get(FIELD_REFRESH_TIME);
        }

        public void setRefreshTime(Long refreshTime) {
            this.mProperties.put(FIELD_REFRESH_TIME, refreshTime);
        }

        public void setClientId(String clientId) {
            this.mProperties.put(FIELD_CLIENT_ID, clientId);
        }

        public void setAccessToken(String access) {
            this.mProperties.put(FIELD_ACCESS_TOKEN, access);
        }

        public void setRefreshToken(String refresh) {
            this.mProperties.put(FIELD_REFRESH_TOKEN, refresh);
        }

        public void setBaseDomain(String baseDomain) {
            this.mProperties.put(FIELD_BASE_DOMAIN, baseDomain);
        }

        public String getBaseDomain() {
            return (String) this.mProperties.get(FIELD_BASE_DOMAIN);
        }

        public void setUser(BoxUser user) {
            this.mProperties.put(FIELD_USER, user);
        }

        public BoxUser getUser() {
            return (BoxUser) this.mProperties.get(FIELD_USER);
        }

        public void wipeOutAuth() {
            setUser(null);
            setClientId(null);
            setAccessToken(null);
            setRefreshToken(null);
        }

        protected void parseJSONMember(Member member) {
            String memberName = member.getName();
            JsonValue value = member.getValue();
            if (memberName.equals(FIELD_ACCESS_TOKEN)) {
                this.mProperties.put(FIELD_ACCESS_TOKEN, value.asString());
            } else if (memberName.equals(FIELD_REFRESH_TOKEN)) {
                this.mProperties.put(FIELD_REFRESH_TOKEN, value.asString());
            } else if (memberName.equals(FIELD_USER)) {
                this.mProperties.put(FIELD_USER, BoxCollaborator.createCollaboratorFromJson(value.asObject()));
            } else if (memberName.equals(FIELD_EXPIRES_IN)) {
                this.mProperties.put(FIELD_EXPIRES_IN, Long.valueOf(value.asLong()));
            } else if (memberName.equals(FIELD_REFRESH_TIME)) {
                this.mProperties.put(FIELD_REFRESH_TIME, Long.valueOf(SdkUtils.parseJsonValueToLong(value)));
            } else if (memberName.equals(FIELD_CLIENT_ID)) {
                this.mProperties.put(FIELD_CLIENT_ID, value.asString());
            } else {
                super.parseJSONMember(member);
            }
        }
    }

    public interface AuthListener {
        void onAuthCreated(BoxAuthenticationInfo boxAuthenticationInfo);

        void onAuthFailure(BoxAuthenticationInfo boxAuthenticationInfo, Exception exception);

        void onLoggedOut(BoxAuthenticationInfo boxAuthenticationInfo, Exception exception);

        void onRefreshed(BoxAuthenticationInfo boxAuthenticationInfo);
    }

    public static class AuthStorage {
        private static final String AUTH_MAP_STORAGE_KEY = (AuthStorage.class.getCanonicalName() + "_authInfoMap");
        private static final String AUTH_STORAGE_LAST_AUTH_USER_ID_KEY = (AuthStorage.class.getCanonicalName() + "_lastAuthUserId");
        private static final String AUTH_STORAGE_NAME = (AuthStorage.class.getCanonicalName() + "_SharedPref");

        protected void storeAuthInfoMap(Map<String, BoxAuthenticationInfo> authInfo, Context context) {
            HashMap<String, Object> map = new HashMap();
            for (String key : authInfo.keySet()) {
                map.put(key, authInfo.get(key));
            }
            context.getSharedPreferences(AUTH_STORAGE_NAME, 0).edit().putString(AUTH_MAP_STORAGE_KEY, new BoxMapJsonObject(map).toJson()).apply();
        }

        protected void clearAuthInfoMap(Context context) {
            context.getSharedPreferences(AUTH_STORAGE_NAME, 0).edit().remove(AUTH_MAP_STORAGE_KEY).apply();
        }

        protected void storeLastAuthenticatedUserId(String userId, Context context) {
            if (SdkUtils.isEmptyString(userId)) {
                context.getSharedPreferences(AUTH_STORAGE_NAME, 0).edit().remove(AUTH_STORAGE_LAST_AUTH_USER_ID_KEY).apply();
            } else {
                context.getSharedPreferences(AUTH_STORAGE_NAME, 0).edit().putString(AUTH_STORAGE_LAST_AUTH_USER_ID_KEY, userId).apply();
            }
        }

        protected String getLastAuthentictedUserId(Context context) {
            return context.getSharedPreferences(AUTH_STORAGE_NAME, 0).getString(AUTH_STORAGE_LAST_AUTH_USER_ID_KEY, null);
        }

        protected ConcurrentHashMap<String, BoxAuthenticationInfo> loadAuthInfoMap(Context context) {
            ConcurrentHashMap<String, BoxAuthenticationInfo> map = new ConcurrentHashMap();
            String json = context.getSharedPreferences(AUTH_STORAGE_NAME, 0).getString(AUTH_MAP_STORAGE_KEY, BuildConfig.FLAVOR);
            if (json.length() > 0) {
                BoxMapJsonObject obj = new BoxMapJsonObject();
                obj.createFromJson(json);
                for (Entry<String, Object> entry : obj.getPropertiesAsHashMap().entrySet()) {
                    BoxAuthenticationInfo info = null;
                    if (entry.getValue() instanceof String) {
                        info = new BoxAuthenticationInfo();
                        info.createFromJson((String) entry.getValue());
                    } else if (entry.getValue() instanceof BoxAuthenticationInfo) {
                        info = (BoxAuthenticationInfo) entry.getValue();
                    }
                    map.put(entry.getKey(), info);
                }
            }
            return map;
        }
    }

    public interface AuthenticationRefreshProvider {
        boolean launchAuthUi(String str, BoxSession boxSession);

        BoxAuthenticationInfo refreshAuthenticationInfo(BoxAuthenticationInfo boxAuthenticationInfo) throws BoxException;
    }

    private BoxAuthentication() {
    }

    private BoxAuthentication(AuthenticationRefreshProvider refreshProvider) {
        this.mRefreshProvider = refreshProvider;
    }

    public BoxAuthenticationInfo getAuthInfo(String userId, Context context) {
        return userId == null ? null : (BoxAuthenticationInfo) getAuthInfoMap(context).get(userId);
    }

    public Map<String, BoxAuthenticationInfo> getStoredAuthInfo(Context context) {
        return getAuthInfoMap(context);
    }

    public String getLastAuthenticatedUserId(Context context) {
        return this.authStorage.getLastAuthentictedUserId(context);
    }

    public static BoxAuthentication getInstance() {
        return mAuthentication;
    }

    public void setAuthStorage(AuthStorage storage) {
        this.authStorage = storage;
    }

    public AuthStorage getAuthStorage() {
        return this.authStorage;
    }

    public synchronized void startAuthenticationUI(BoxSession session) {
        startAuthenticateUI(session);
    }

    public synchronized void onAuthenticated(BoxAuthenticationInfo info, Context context) {
        getAuthInfoMap(context).put(info.getUser().getId(), info.clone());
        this.authStorage.storeLastAuthenticatedUserId(info.getUser().getId(), context);
        this.authStorage.storeAuthInfoMap(this.mCurrentAccessInfo, context);
        for (AuthListener listener : getListeners()) {
            listener.onAuthCreated(info);
        }
    }

    public synchronized void onAuthenticationFailure(BoxAuthenticationInfo info, Exception ex) {
        for (AuthListener listener : getListeners()) {
            listener.onAuthFailure(info, ex);
        }
    }

    public synchronized void onLoggedOut(BoxAuthenticationInfo info, Exception ex) {
        for (AuthListener listener : getListeners()) {
            listener.onLoggedOut(info, ex);
        }
    }

    public Set<AuthListener> getListeners() {
        Set<AuthListener> listeners = new LinkedHashSet();
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            AuthListener rc = (AuthListener) ((WeakReference) it.next()).get();
            if (rc != null) {
                listeners.add(rc);
            }
        }
        if (this.mListeners.size() > listeners.size()) {
            this.mListeners = new ConcurrentLinkedQueue();
            for (AuthListener listener : listeners) {
                this.mListeners.add(new WeakReference(listener));
            }
        }
        return listeners;
    }

    private void clearCache(BoxSession session) {
        File cacheDir = session.getCacheDir();
        if (cacheDir.exists()) {
            File[] files = cacheDir.listFiles();
            if (files != null) {
                for (File child : files) {
                    deleteFilesRecursively(child);
                }
            }
        }
    }

    private void deleteFilesRecursively(File fileOrDirectory) {
        if (fileOrDirectory != null) {
            if (fileOrDirectory.isDirectory()) {
                File[] files = fileOrDirectory.listFiles();
                if (files != null) {
                    for (File child : files) {
                        deleteFilesRecursively(child);
                    }
                }
            }
            fileOrDirectory.delete();
        }
    }

    public synchronized void logout(BoxSession session) {
        BoxUser user = session.getUser();
        if (user != null) {
            clearCache(session);
            Context context = session.getApplicationContext();
            String userId = user.getId();
            getAuthInfoMap(session.getApplicationContext());
            BoxAuthenticationInfo info = (BoxAuthenticationInfo) this.mCurrentAccessInfo.get(userId);
            Exception ex = null;
            try {
                new BoxApiAuthentication(session).revokeOAuth(info.refreshToken(), session.getClientId(), session.getClientSecret()).send();
            } catch (Exception e) {
                ex = e;
                BoxLogUtils.e(TAG, "logout", e);
            }
            this.mCurrentAccessInfo.remove(userId);
            if (this.authStorage.getLastAuthentictedUserId(context) != null && userId.equals(userId)) {
                this.authStorage.storeLastAuthenticatedUserId(null, context);
            }
            this.authStorage.storeAuthInfoMap(this.mCurrentAccessInfo, context);
            onLoggedOut(info, ex);
        }
    }

    public synchronized void logoutAllUsers(Context context) {
        getAuthInfoMap(context);
        for (String userId : this.mCurrentAccessInfo.keySet()) {
            logout(new BoxSession(context, userId));
        }
    }

    public synchronized FutureTask<BoxAuthenticationInfo> create(BoxSession session, String code) throws BoxException {
        FutureTask<BoxAuthenticationInfo> task;
        task = doCreate(session, code);
        AUTH_EXECUTOR.submit(task);
        return task;
    }

    public synchronized FutureTask<BoxAuthenticationInfo> refresh(BoxSession session) throws BoxException {
        FutureTask<BoxAuthenticationInfo> doRefresh;
        BoxUser user = session.getUser();
        if (user == null) {
            doRefresh = doRefresh(session, session.getAuthInfo());
        } else {
            getAuthInfoMap(session.getApplicationContext());
            BoxAuthenticationInfo info = (BoxAuthenticationInfo) this.mCurrentAccessInfo.get(user.getId());
            if (info == null) {
                this.mCurrentAccessInfo.put(user.getId(), session.getAuthInfo());
                info = (BoxAuthenticationInfo) this.mCurrentAccessInfo.get(user.getId());
            }
            if (session.getAuthInfo().accessToken().equals(info.accessToken())) {
                doRefresh = (FutureTask) this.mRefreshingTasks.get(user.getId());
                if (doRefresh == null || doRefresh.isCancelled() || doRefresh.isDone()) {
                    doRefresh = doRefresh(session, info);
                }
            } else {
                final BoxAuthenticationInfo latestInfo = info;
                BoxAuthenticationInfo.cloneInfo(session.getAuthInfo(), info);
                doRefresh = new FutureTask(new Callable<BoxAuthenticationInfo>() {
                    public BoxAuthenticationInfo call() throws Exception {
                        return latestInfo;
                    }
                });
            }
        }
        return doRefresh;
    }

    private FutureTask<BoxAuthenticationInfo> doCreate(final BoxSession session, final String code) {
        return new FutureTask(new Callable<BoxAuthenticationInfo>() {
            public BoxAuthenticationInfo call() throws Exception {
                BoxCreateAuthRequest request = new BoxApiAuthentication(session).createOAuth(code, session.getClientId(), session.getClientSecret());
                BoxAuthenticationInfo info = new BoxAuthenticationInfo();
                BoxAuthenticationInfo.cloneInfo(info, session.getAuthInfo());
                BoxAuthenticationInfo authenticatedInfo = (BoxAuthenticationInfo) request.send();
                info.setAccessToken(authenticatedInfo.accessToken());
                info.setRefreshToken(authenticatedInfo.refreshToken());
                info.setRefreshTime(Long.valueOf(System.currentTimeMillis()));
                info.setUser((BoxUser) new BoxApiUser(new BoxSession(session.getApplicationContext(), info, null)).getCurrentUserInfoRequest().send());
                BoxAuthentication.getInstance().onAuthenticated(info, session.getApplicationContext());
                return info;
            }
        });
    }

    public synchronized void addListener(AuthListener listener) {
        this.mListeners.add(new WeakReference(listener));
    }

    protected synchronized void startAuthenticateUI(BoxSession session) {
        Context context = session.getApplicationContext();
        boolean z = isBoxAuthAppAvailable(context) && session.isEnabledBoxAppAuthentication();
        Intent intent = OAuthActivity.createOAuthActivityIntent(context, session, z);
        intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        context.startActivity(intent);
    }

    private RefreshFailure handleRefreshException(BoxException e, BoxAuthenticationInfo info) {
        RefreshFailure refreshFailure = new RefreshFailure(e);
        getInstance().onAuthenticationFailure(info, refreshFailure);
        return refreshFailure;
    }

    private FutureTask<BoxAuthenticationInfo> doRefresh(BoxSession session, BoxAuthenticationInfo info) throws BoxException {
        final boolean userUnknown = info.getUser() == null && session.getUser() == null;
        final String taskKey = (SdkUtils.isBlank(session.getUserId()) && userUnknown) ? info.accessToken() : session.getUserId();
        final BoxSession boxSession = session;
        final BoxAuthenticationInfo boxAuthenticationInfo = info;
        FutureTask<BoxAuthenticationInfo> task = new FutureTask(new Callable<BoxAuthenticationInfo>() {
            public BoxAuthenticationInfo call() throws Exception {
                BoxAuthenticationInfo refreshInfo;
                if (boxSession.getRefreshProvider() != null) {
                    try {
                        refreshInfo = boxSession.getRefreshProvider().refreshAuthenticationInfo(boxAuthenticationInfo);
                    } catch (BoxException e) {
                        BoxAuthentication.this.mRefreshingTasks.remove(taskKey);
                        throw BoxAuthentication.this.handleRefreshException(e, boxAuthenticationInfo);
                    }
                } else if (BoxAuthentication.this.mRefreshProvider != null) {
                    try {
                        refreshInfo = BoxAuthentication.this.mRefreshProvider.refreshAuthenticationInfo(boxAuthenticationInfo);
                    } catch (BoxException e2) {
                        BoxAuthentication.this.mRefreshingTasks.remove(taskKey);
                        throw BoxAuthentication.this.handleRefreshException(e2, boxAuthenticationInfo);
                    }
                } else {
                    String refreshToken = boxAuthenticationInfo.refreshToken() != null ? boxAuthenticationInfo.refreshToken() : BuildConfig.FLAVOR;
                    String clientId = boxSession.getClientId() != null ? boxSession.getClientId() : BoxConfig.CLIENT_ID;
                    String clientSecret = boxSession.getClientSecret() != null ? boxSession.getClientSecret() : BoxConfig.CLIENT_SECRET;
                    if (SdkUtils.isBlank(clientId) || SdkUtils.isBlank(clientSecret)) {
                        throw BoxAuthentication.this.handleRefreshException(new BoxException("client id or secret not specified", 400, "{\"error\": \"bad_request\",\n  \"error_description\": \"client id or secret not specified\"}", null), boxAuthenticationInfo);
                    }
                    try {
                        refreshInfo = new BoxApiAuthentication(boxSession).refreshOAuth(refreshToken, clientId, clientSecret).send();
                    } catch (BoxException e22) {
                        BoxAuthentication.this.mRefreshingTasks.remove(taskKey);
                        throw BoxAuthentication.this.handleRefreshException(e22, boxAuthenticationInfo);
                    }
                }
                if (refreshInfo != null) {
                    refreshInfo.setRefreshTime(Long.valueOf(System.currentTimeMillis()));
                }
                BoxAuthenticationInfo.cloneInfo(boxSession.getAuthInfo(), refreshInfo);
                if (!(!userUnknown && boxSession.getRefreshProvider() == null && BoxAuthentication.this.mRefreshProvider == null)) {
                    boxAuthenticationInfo.setUser((BoxUser) new BoxApiUser(boxSession).getCurrentUserInfoRequest().send());
                }
                BoxAuthentication.this.getAuthInfoMap(boxSession.getApplicationContext()).put(boxAuthenticationInfo.getUser().getId(), refreshInfo);
                BoxAuthentication.this.authStorage.storeAuthInfoMap(BoxAuthentication.this.mCurrentAccessInfo, boxSession.getApplicationContext());
                Iterator it = BoxAuthentication.this.mListeners.iterator();
                while (it.hasNext()) {
                    AuthListener rc = (AuthListener) ((WeakReference) it.next()).get();
                    if (rc != null) {
                        rc.onRefreshed(refreshInfo);
                    }
                }
                if (!boxSession.getUserId().equals(boxAuthenticationInfo.getUser().getId())) {
                    boxSession.onAuthFailure(boxAuthenticationInfo, new BoxException("Session User Id has changed!"));
                }
                BoxAuthentication.this.mRefreshingTasks.remove(taskKey);
                return boxAuthenticationInfo;
            }
        });
        this.mRefreshingTasks.put(taskKey, task);
        AUTH_EXECUTOR.execute(task);
        return task;
    }

    private ConcurrentHashMap<String, BoxAuthenticationInfo> getAuthInfoMap(Context context) {
        if (this.mCurrentAccessInfo == null) {
            this.mCurrentAccessInfo = this.authStorage.loadAuthInfoMap(context);
        }
        return this.mCurrentAccessInfo;
    }

    public static boolean isBoxAuthAppAvailable(Context context) {
        if (context.getPackageManager().queryIntentActivities(new Intent(BoxConstants.REQUEST_BOX_APP_FOR_AUTH_INTENT_ACTION), 65600).size() > 0) {
            return true;
        }
        return false;
    }
}
