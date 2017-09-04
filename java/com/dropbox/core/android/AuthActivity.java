package com.dropbox.core.android;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.box.androidsdk.content.BoxConstants;
import com.dropbox.core.DbxRequestUtil;
import com.github.clans.fab.BuildConfig;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;

public class AuthActivity extends Activity {
    public static final String ACTION_AUTHENTICATE_V1 = "com.dropbox.android.AUTHENTICATE_V1";
    public static final String ACTION_AUTHENTICATE_V2 = "com.dropbox.android.AUTHENTICATE_V2";
    public static final String AUTH_PATH_CONNECT = "/connect";
    public static final int AUTH_VERSION = 1;
    private static final String DEFAULT_WEB_HOST = "www.dropbox.com";
    public static final String EXTRA_ACCESS_SECRET = "ACCESS_SECRET";
    public static final String EXTRA_ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String EXTRA_ALREADY_AUTHED_UIDS = "ALREADY_AUTHED_UIDS";
    public static final String EXTRA_AUTH_STATE = "AUTH_STATE";
    public static final String EXTRA_CALLING_CLASS = "CALLING_CLASS";
    public static final String EXTRA_CALLING_PACKAGE = "CALLING_PACKAGE";
    public static final String EXTRA_CONSUMER_KEY = "CONSUMER_KEY";
    public static final String EXTRA_CONSUMER_SIG = "CONSUMER_SIG";
    public static final String EXTRA_DESIRED_UID = "DESIRED_UID";
    public static final String EXTRA_SESSION_ID = "SESSION_ID";
    public static final String EXTRA_UID = "UID";
    private static final String SIS_KEY_AUTH_STATE_NONCE = "SIS_KEY_AUTH_STATE_NONCE";
    private static final String TAG = AuthActivity.class.getName();
    public static Intent result = null;
    private static String[] sAlreadyAuthedUids;
    private static String sApiType;
    private static String sAppKey;
    private static String sDesiredUid;
    private static SecurityProvider sSecurityProvider = new SecurityProvider() {
        public SecureRandom getSecureRandom() {
            return FixedSecureRandom.get();
        }
    };
    private static final Object sSecurityProviderLock = new Object();
    private static String sSessionId;
    private static String sWebHost = DEFAULT_WEB_HOST;
    private boolean mActivityDispatchHandlerPosted = false;
    private String[] mAlreadyAuthedUids;
    private String mApiType;
    private String mAppKey;
    private String mAuthStateNonce = null;
    private String mDesiredUid;
    private String mSessionId;
    private String mWebHost;

    public interface SecurityProvider {
        SecureRandom getSecureRandom();
    }

    static void setAuthParams(String appKey, String desiredUid, String[] alreadyAuthedUids) {
        setAuthParams(appKey, desiredUid, alreadyAuthedUids, null);
    }

    static void setAuthParams(String appKey, String desiredUid, String[] alreadyAuthedUids, String webHost, String apiType) {
        setAuthParams(appKey, desiredUid, alreadyAuthedUids, null, null, null);
    }

    static void setAuthParams(String appKey, String desiredUid, String[] alreadyAuthedUids, String sessionId) {
        setAuthParams(appKey, desiredUid, alreadyAuthedUids, sessionId, null, null);
    }

    static void setAuthParams(String appKey, String desiredUid, String[] alreadyAuthedUids, String sessionId, String webHost, String apiType) {
        sAppKey = appKey;
        sDesiredUid = desiredUid;
        if (alreadyAuthedUids == null) {
            alreadyAuthedUids = new String[0];
        }
        sAlreadyAuthedUids = alreadyAuthedUids;
        sSessionId = sessionId;
        if (webHost == null) {
            webHost = DEFAULT_WEB_HOST;
        }
        sWebHost = webHost;
        sApiType = apiType;
    }

    public static Intent makeIntent(Context context, String appKey, String webHost, String apiType) {
        return makeIntent(context, appKey, null, null, null, webHost, apiType);
    }

    public static Intent makeIntent(Context context, String appKey, String desiredUid, String[] alreadyAuthedUids, String sessionId, String webHost, String apiType) {
        if (appKey == null) {
            throw new IllegalArgumentException("'appKey' can't be null");
        }
        setAuthParams(appKey, desiredUid, alreadyAuthedUids, sessionId, webHost, apiType);
        return new Intent(context, AuthActivity.class);
    }

    public static boolean checkAppBeforeAuth(Context context, String appKey, boolean alertUser) {
        Intent testIntent = new Intent("android.intent.action.VIEW");
        String scheme = "db-" + appKey;
        testIntent.setData(Uri.parse(scheme + "://" + AUTH_VERSION + AUTH_PATH_CONNECT));
        List<ResolveInfo> activities = context.getPackageManager().queryIntentActivities(testIntent, 0);
        if (activities == null || activities.size() == 0) {
            throw new IllegalStateException("URI scheme in your app's manifest is not set up correctly. You should have a " + AuthActivity.class.getName() + " with the " + "scheme: " + scheme);
        } else if (activities.size() <= AUTH_VERSION) {
            ResolveInfo resolveInfo = (ResolveInfo) activities.get(0);
            if (resolveInfo != null && resolveInfo.activityInfo != null && context.getPackageName().equals(resolveInfo.activityInfo.packageName)) {
                return true;
            }
            throw new IllegalStateException("There must be a " + AuthActivity.class.getName() + " within your app's package " + "registered for your URI scheme (" + scheme + "). However, " + "it appears that an activity in a different package is " + "registered for that scheme instead. If you have " + "multiple apps that all want to use the same access" + "token pair, designate one of them to do " + "authentication and have the other apps launch it " + "and then retrieve the token pair from it.");
        } else if (alertUser) {
            Builder builder = new Builder(context);
            builder.setTitle("Security alert");
            builder.setMessage("Another app on your phone may be trying to pose as the app you are currently using. The malicious app can't access your account, but linking to Dropbox has been disabled as a precaution. Please contact support@dropbox.com.");
            builder.setPositiveButton("OK", new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
            return false;
        } else {
            Log.w(TAG, "There are multiple apps registered for the AuthActivity URI scheme (" + scheme + ").  Another app may be trying to " + " impersonate this app, so authentication will be disabled.");
            return false;
        }
    }

    public static void setSecurityProvider(SecurityProvider prov) {
        synchronized (sSecurityProviderLock) {
            sSecurityProvider = prov;
        }
    }

    private static SecurityProvider getSecurityProvider() {
        SecurityProvider securityProvider;
        synchronized (sSecurityProviderLock) {
            securityProvider = sSecurityProvider;
        }
        return securityProvider;
    }

    private static SecureRandom getSecureRandom() {
        SecurityProvider prov = getSecurityProvider();
        if (prov != null) {
            return prov.getSecureRandom();
        }
        return new SecureRandom();
    }

    protected void onCreate(Bundle savedInstanceState) {
        this.mAppKey = sAppKey;
        this.mWebHost = sWebHost;
        this.mApiType = sApiType;
        this.mDesiredUid = sDesiredUid;
        this.mAlreadyAuthedUids = sAlreadyAuthedUids;
        this.mSessionId = sSessionId;
        if (savedInstanceState == null) {
            result = null;
            this.mAuthStateNonce = null;
        } else {
            this.mAuthStateNonce = savedInstanceState.getString(SIS_KEY_AUTH_STATE_NONCE);
        }
        setTheme(16973840);
        super.onCreate(savedInstanceState);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SIS_KEY_AUTH_STATE_NONCE, this.mAuthStateNonce);
    }

    static Intent getOfficialAuthIntent() {
        Intent authIntent = new Intent(ACTION_AUTHENTICATE_V2);
        authIntent.setPackage("com.dropbox.android");
        return authIntent;
    }

    protected void onResume() {
        super.onResume();
        if (!isFinishing()) {
            if (this.mAuthStateNonce != null || this.mAppKey == null) {
                authFinished(null);
                return;
            }
            result = null;
            if (this.mActivityDispatchHandlerPosted) {
                Log.w(TAG, "onResume called again before Handler run");
                return;
            }
            final String state = createStateNonce();
            final Intent officialAuthIntent = getOfficialAuthIntent();
            officialAuthIntent.putExtra(EXTRA_CONSUMER_KEY, this.mAppKey);
            officialAuthIntent.putExtra(EXTRA_CONSUMER_SIG, BuildConfig.FLAVOR);
            officialAuthIntent.putExtra(EXTRA_DESIRED_UID, this.mDesiredUid);
            officialAuthIntent.putExtra(EXTRA_ALREADY_AUTHED_UIDS, this.mAlreadyAuthedUids);
            officialAuthIntent.putExtra(EXTRA_SESSION_ID, this.mSessionId);
            officialAuthIntent.putExtra(EXTRA_CALLING_PACKAGE, getPackageName());
            officialAuthIntent.putExtra(EXTRA_CALLING_CLASS, getClass().getName());
            officialAuthIntent.putExtra(EXTRA_AUTH_STATE, state);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    Log.d(AuthActivity.TAG, "running startActivity in handler");
                    try {
                        if (DbxOfficialAppConnector.getDropboxAppPackage(AuthActivity.this, officialAuthIntent) != null) {
                            AuthActivity.this.startActivity(officialAuthIntent);
                        } else {
                            AuthActivity.this.startWebAuth(state);
                        }
                        AuthActivity.this.mAuthStateNonce = state;
                        AuthActivity.setAuthParams(null, null, null);
                    } catch (ActivityNotFoundException e) {
                        Log.e(AuthActivity.TAG, "Could not launch intent. User may have restricted profile", e);
                        AuthActivity.this.finish();
                    }
                }
            });
            this.mActivityDispatchHandlerPosted = true;
        }
    }

    protected void onNewIntent(Intent intent) {
        if (this.mAuthStateNonce == null) {
            authFinished(null);
            return;
        }
        Intent newResult;
        String token = null;
        String secret = null;
        String uid = null;
        String state = null;
        if (intent.hasExtra(EXTRA_ACCESS_TOKEN)) {
            token = intent.getStringExtra(EXTRA_ACCESS_TOKEN);
            secret = intent.getStringExtra(EXTRA_ACCESS_SECRET);
            uid = intent.getStringExtra(EXTRA_UID);
            state = intent.getStringExtra(EXTRA_AUTH_STATE);
        } else {
            Uri uri = intent.getData();
            if (uri != null) {
                if (AUTH_PATH_CONNECT.equals(uri.getPath())) {
                    try {
                        token = uri.getQueryParameter("oauth_token");
                        secret = uri.getQueryParameter("oauth_token_secret");
                        uid = uri.getQueryParameter("uid");
                        state = uri.getQueryParameter("state");
                    } catch (UnsupportedOperationException e) {
                    }
                }
            }
        }
        if (token == null || token.equals(BuildConfig.FLAVOR) || secret == null || secret.equals(BuildConfig.FLAVOR) || uid == null || uid.equals(BuildConfig.FLAVOR) || state == null || state.equals(BuildConfig.FLAVOR)) {
            newResult = null;
        } else if (this.mAuthStateNonce.equals(state)) {
            newResult = new Intent();
            newResult.putExtra(EXTRA_ACCESS_TOKEN, token);
            newResult.putExtra(EXTRA_ACCESS_SECRET, secret);
            newResult.putExtra(EXTRA_UID, uid);
        } else {
            authFinished(null);
            return;
        }
        authFinished(newResult);
    }

    private void authFinished(Intent authResult) {
        result = authResult;
        this.mAuthStateNonce = null;
        setAuthParams(null, null, null);
        finish();
    }

    private void startWebAuth(String state) {
        String path = "1/connect";
        Locale locale = Locale.getDefault();
        String alreadyAuthedUid = this.mAlreadyAuthedUids.length > 0 ? this.mAlreadyAuthedUids[0] : BoxConstants.ROOT_FOLDER_ID;
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(DbxRequestUtil.buildUrlWithParams(locale.toString(), this.mWebHost, path, new String[]{"k", this.mAppKey, "n", alreadyAuthedUid, "api", this.mApiType, "state", state}))));
    }

    private String createStateNonce() {
        byte[] randomBytes = new byte[16];
        getSecureRandom().nextBytes(randomBytes);
        StringBuilder sb = new StringBuilder();
        sb.append("oauth2:");
        for (int i = 0; i < 16; i += AUTH_VERSION) {
            Object[] objArr = new Object[AUTH_VERSION];
            objArr[0] = Integer.valueOf(randomBytes[i] & 255);
            sb.append(String.format("%02x", objArr));
        }
        return sb.toString();
    }
}
