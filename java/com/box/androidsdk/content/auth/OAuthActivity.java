package com.box.androidsdk.content.auth;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.widget.Toast;
import com.box.androidsdk.content.BoxConstants;
import com.box.androidsdk.content.BoxException;
import com.box.androidsdk.content.auth.BoxAuthentication.BoxAuthenticationInfo;
import com.box.androidsdk.content.auth.ChooseAuthenticationFragment.OnAuthenticationChosen;
import com.box.androidsdk.content.auth.OAuthWebView.AuthFailure;
import com.box.androidsdk.content.auth.OAuthWebView.OAuthWebViewClient;
import com.box.androidsdk.content.auth.OAuthWebView.OAuthWebViewClient.WebEventListener;
import com.box.androidsdk.content.auth.OAuthWebView.OnPageFinishedListener;
import com.box.androidsdk.content.models.BoxError;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.utils.SdkUtils;
import com.box.sdk.android.R;
import com.github.clans.fab.BuildConfig;
import com.google.api.client.http.HttpStatusCodes;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;

public class OAuthActivity extends Activity implements OnAuthenticationChosen, WebEventListener, OnPageFinishedListener {
    public static final String AUTH_CODE = "authcode";
    public static final String AUTH_INFO = "authinfo";
    public static final int AUTH_TYPE_APP = 1;
    public static final int AUTH_TYPE_WEBVIEW = 0;
    private static final String CHOOSE_AUTH_TAG = "choose_auth";
    public static final String EXTRA_DISABLE_ACCOUNT_CHOOSING = "disableAccountChoosing";
    public static final String EXTRA_SESSION = "session";
    public static final String EXTRA_USER_ID_RESTRICTION = "restrictToUserId";
    protected static final String LOGIN_VIA_BOX_APP = "loginviaboxapp";
    public static final int REQUEST_BOX_APP_FOR_AUTH_CODE = 1;
    public static final String USER_ID = "userId";
    private static Dialog dialog;
    private AtomicBoolean apiCallStarted = new AtomicBoolean(false);
    private int authType = AUTH_TYPE_WEBVIEW;
    private boolean mAuthWasSuccessful = false;
    private String mClientId;
    private String mClientSecret;
    private BroadcastReceiver mConnectedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE") && SdkUtils.isInternetAvailable(context) && !OAuthActivity.this.oauthView.getUrl().startsWith("http")) {
                OAuthActivity.this.startOAuth();
            }
        }
    };
    private String mDeviceId;
    private String mDeviceName;
    private String mRedirectUrl;
    private BoxSession mSession;
    protected OAuthWebViewClient oauthClient;
    protected OAuthWebView oauthView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        registerReceiver(this.mConnectedReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        Intent intent = getIntent();
        this.mClientId = intent.getStringExtra(BoxAuthenticationInfo.FIELD_CLIENT_ID);
        this.mClientSecret = intent.getStringExtra(BoxConstants.KEY_CLIENT_SECRET);
        this.mDeviceId = intent.getStringExtra(BoxConstants.KEY_BOX_DEVICE_ID);
        this.mDeviceName = intent.getStringExtra(BoxConstants.KEY_BOX_DEVICE_NAME);
        this.mRedirectUrl = intent.getStringExtra(BoxConstants.KEY_REDIRECT_URL);
        this.authType = intent.getBooleanExtra(LOGIN_VIA_BOX_APP, false) ? REQUEST_BOX_APP_FOR_AUTH_CODE : AUTH_TYPE_WEBVIEW;
        this.apiCallStarted.getAndSet(false);
        this.mSession = (BoxSession) intent.getSerializableExtra(EXTRA_SESSION);
        if (this.mSession != null) {
            this.mSession.setApplicationContext(getApplicationContext());
            return;
        }
        this.mSession = new BoxSession(this, null, this.mClientId, this.mClientSecret, this.mRedirectUrl);
        this.mSession.setDeviceId(this.mDeviceId);
        this.mSession.setDeviceName(this.mDeviceName);
    }

    protected void onResume() {
        super.onResume();
        if (this.oauthView == null || !this.oauthView.getUrl().startsWith("http")) {
            startOAuth();
        }
    }

    public void onReceivedAuthCode(String code) {
        onReceivedAuthCode(code, null);
    }

    public void onReceivedAuthCode(String code, String baseDomain) {
        if (this.authType == 0) {
            this.oauthView.setVisibility(4);
        }
        startMakingOAuthAPICall(code, baseDomain);
    }

    public void finish() {
        clearCachedAuthenticationData();
        if (!this.mAuthWasSuccessful) {
            BoxAuthentication.getInstance().onAuthenticationFailure(null, null);
        }
        super.finish();
    }

    public void onPageFinished(WebView view, String url) {
        dismissSpinner();
    }

    public boolean onAuthFailure(AuthFailure failure) {
        Resources resources;
        if (failure.type != 2) {
            if (!SdkUtils.isEmptyString(failure.message)) {
                switch (failure.type) {
                    case REQUEST_BOX_APP_FOR_AUTH_CODE /*1*/:
                        resources = getResources();
                        Toast.makeText(this, String.format("%s\n%s: %s", new Object[]{resources.getString(R.string.boxsdk_Authentication_fail), resources.getString(R.string.boxsdk_details), resources.getString(R.string.boxsdk_Authentication_fail_url_mismatch)}), REQUEST_BOX_APP_FOR_AUTH_CODE).show();
                        break;
                    default:
                        Toast.makeText(this, R.string.boxsdk_Authentication_fail, REQUEST_BOX_APP_FOR_AUTH_CODE).show();
                        break;
                }
            }
            Toast.makeText(this, R.string.boxsdk_Authentication_fail, REQUEST_BOX_APP_FOR_AUTH_CODE).show();
        } else if (failure.mWebException.getErrorCode() == -6 || failure.mWebException.getErrorCode() == -2 || failure.mWebException.getErrorCode() == -8) {
            return false;
        } else {
            resources = getResources();
            Toast.makeText(this, String.format("%s\n%s: %s", new Object[]{resources.getString(R.string.boxsdk_Authentication_fail), resources.getString(R.string.boxsdk_details), failure.mWebException.getErrorCode() + " " + failure.mWebException.getDescription()}), REQUEST_BOX_APP_FOR_AUTH_CODE).show();
        }
        finish();
        return true;
    }

    protected int getContentView() {
        return R.layout.boxsdk_activity_oauth;
    }

    protected void startOAuth() {
        if (!(this.authType == REQUEST_BOX_APP_FOR_AUTH_CODE || getIntent().getBooleanExtra(EXTRA_DISABLE_ACCOUNT_CHOOSING, false) || getFragmentManager().findFragmentByTag(CHOOSE_AUTH_TAG) != null)) {
            Map<String, BoxAuthenticationInfo> map = BoxAuthentication.getInstance().getStoredAuthInfo(this);
            if (SdkUtils.isEmptyString(getIntent().getStringExtra(EXTRA_USER_ID_RESTRICTION)) && map != null && map.size() > 0) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.oauth_container, ChooseAuthenticationFragment.createAuthenticationActivity(this), CHOOSE_AUTH_TAG);
                transaction.addToBackStack(CHOOSE_AUTH_TAG);
                transaction.commit();
            }
        }
        switch (this.authType) {
            case AUTH_TYPE_WEBVIEW /*0*/:
                break;
            case REQUEST_BOX_APP_FOR_AUTH_CODE /*1*/:
                Intent intent = getBoxAuthApp();
                if (intent != null) {
                    intent.putExtra(BoxAuthenticationInfo.FIELD_CLIENT_ID, this.mClientId);
                    intent.putExtra(BoxConstants.KEY_REDIRECT_URL, this.mRedirectUrl);
                    if (!SdkUtils.isEmptyString(getIntent().getStringExtra(EXTRA_USER_ID_RESTRICTION))) {
                        intent.putExtra(EXTRA_USER_ID_RESTRICTION, getIntent().getStringExtra(EXTRA_USER_ID_RESTRICTION));
                    }
                    startActivityForResult(intent, REQUEST_BOX_APP_FOR_AUTH_CODE);
                    return;
                }
                break;
            default:
                return;
        }
        showSpinner();
        this.oauthView = createOAuthView();
        this.oauthClient = createOAuthWebViewClient(this.oauthView.getStateString());
        this.oauthClient.setOnPageFinishedListener(this);
        this.oauthView.setWebViewClient(this.oauthClient);
        if (this.mSession.getBoxAccountEmail() != null) {
            this.oauthView.setBoxAccountEmail(this.mSession.getBoxAccountEmail());
        }
        this.oauthView.authenticate(this.mClientId, this.mRedirectUrl);
    }

    protected Intent getBoxAuthApp() {
        Intent intent = new Intent(BoxConstants.REQUEST_BOX_APP_FOR_AUTH_INTENT_ACTION);
        List<ResolveInfo> infos = getPackageManager().queryIntentActivities(intent, 65600);
        if (infos == null || infos.size() < REQUEST_BOX_APP_FOR_AUTH_CODE) {
            return null;
        }
        String officialBoxAppString = getResources().getString(R.string.boxsdk_box_app_signature);
        for (ResolveInfo info : infos) {
            try {
                if (officialBoxAppString.equals(getPackageManager().getPackageInfo(info.activityInfo.packageName, 64).signatures[AUTH_TYPE_WEBVIEW].toCharsString())) {
                    intent.setPackage(info.activityInfo.packageName);
                    Map<String, BoxAuthenticationInfo> authenticatedMap = BoxAuthentication.getInstance().getStoredAuthInfo(this);
                    if (authenticatedMap == null || authenticatedMap.size() <= 0) {
                        return intent;
                    }
                    ArrayList<String> authenticatedUsers = new ArrayList(authenticatedMap.size());
                    for (Entry<String, BoxAuthenticationInfo> set : authenticatedMap.entrySet()) {
                        if (((BoxAuthenticationInfo) set.getValue()).getUser() != null) {
                            authenticatedUsers.add(((BoxAuthenticationInfo) set.getValue()).getUser().toJson());
                        }
                    }
                    if (authenticatedUsers.size() <= 0) {
                        return intent;
                    }
                    intent.putStringArrayListExtra(BoxConstants.KEY_BOX_USERS, authenticatedUsers);
                    return intent;
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    public void onBackPressed() {
        if (getFragmentManager().findFragmentByTag(CHOOSE_AUTH_TAG) != null) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    public void onAuthenticationChosen(BoxAuthenticationInfo authInfo) {
        if (authInfo != null) {
            BoxAuthentication.getInstance().onAuthenticated(authInfo, this);
            dismissSpinnerAndFinishAuthenticate(authInfo);
        }
    }

    public void onDifferentAuthenticationChosen() {
        if (getFragmentManager().findFragmentByTag(CHOOSE_AUTH_TAG) != null) {
            getFragmentManager().popBackStack();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (-1 == resultCode && REQUEST_BOX_APP_FOR_AUTH_CODE == requestCode) {
            String userId = data.getStringExtra(USER_ID);
            String authCode = data.getStringExtra(AUTH_CODE);
            if (SdkUtils.isBlank(authCode) && !SdkUtils.isBlank(userId)) {
                BoxAuthenticationInfo info = (BoxAuthenticationInfo) BoxAuthentication.getInstance().getStoredAuthInfo(this).get(userId);
                if (info != null) {
                    onAuthenticationChosen(info);
                } else {
                    onAuthFailure(new AuthFailure(AUTH_TYPE_WEBVIEW, BuildConfig.FLAVOR));
                }
            } else if (!SdkUtils.isBlank(authCode)) {
                startMakingOAuthAPICall(authCode, null);
            }
        } else if (resultCode == 0) {
            finish();
        }
    }

    protected void startMakingOAuthAPICall(final String code, String baseDomain) {
        if (!this.apiCallStarted.getAndSet(true)) {
            showSpinner();
            this.mSession.getAuthInfo().setBaseDomain(baseDomain);
            new Thread() {
                public void run() {
                    try {
                        BoxAuthenticationInfo sessionAuth = (BoxAuthenticationInfo) BoxAuthentication.getInstance().create(OAuthActivity.this.mSession, code).get();
                        String restrictedUserId = OAuthActivity.this.getIntent().getStringExtra(OAuthActivity.EXTRA_USER_ID_RESTRICTION);
                        if (SdkUtils.isEmptyString(restrictedUserId) || sessionAuth.getUser().getId().equals(restrictedUserId)) {
                            OAuthActivity.this.dismissSpinnerAndFinishAuthenticate(sessionAuth);
                            return;
                        }
                        throw new RuntimeException("Unexpected user logged in. Expected " + restrictedUserId + " received " + sessionAuth.getUser().getId());
                    } catch (Exception e) {
                        OAuthActivity.this.dismissSpinnerAndFailAuthenticate(OAuthActivity.this.getAuthCreationErrorString(e));
                    }
                }
            }.start();
        }
    }

    protected void dismissSpinnerAndFinishAuthenticate(final BoxAuthenticationInfo auth) {
        runOnUiThread(new Runnable() {
            public void run() {
                OAuthActivity.this.dismissSpinner();
                Intent intent = new Intent();
                intent.putExtra(OAuthActivity.AUTH_INFO, auth);
                OAuthActivity.this.setResult(-1, intent);
                OAuthActivity.this.mAuthWasSuccessful = true;
                OAuthActivity.this.finish();
            }
        });
    }

    protected void dismissSpinnerAndFailAuthenticate(final String error) {
        runOnUiThread(new Runnable() {
            public void run() {
                OAuthActivity.this.dismissSpinner();
                Toast.makeText(OAuthActivity.this, error, OAuthActivity.REQUEST_BOX_APP_FOR_AUTH_CODE).show();
                OAuthActivity.this.setResult(OAuthActivity.AUTH_TYPE_WEBVIEW);
                OAuthActivity.this.finish();
            }
        });
    }

    protected OAuthWebView createOAuthView() {
        OAuthWebView webview = (OAuthWebView) findViewById(getOAuthWebViewRId());
        webview.setVisibility(AUTH_TYPE_WEBVIEW);
        webview.getSettings().setJavaScriptEnabled(true);
        return webview;
    }

    protected OAuthWebViewClient createOAuthWebViewClient(String optionalState) {
        return new OAuthWebViewClient(this, this.mRedirectUrl, optionalState);
    }

    protected int getOAuthWebViewRId() {
        return R.id.oauthview;
    }

    protected Dialog showDialogWhileWaitingForAuthenticationAPICall() {
        return ProgressDialog.show(this, getText(R.string.boxsdk_Authenticating), getText(R.string.boxsdk_Please_wait));
    }

    protected void showSpinner() {
        try {
            dialog = showDialogWhileWaitingForAuthenticationAPICall();
        } catch (Exception e) {
            dialog = null;
        }
    }

    protected void dismissSpinner() {
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
            } catch (IllegalArgumentException e) {
            }
            dialog = null;
        }
    }

    public void onDestroy() {
        unregisterReceiver(this.mConnectedReceiver);
        this.apiCallStarted.set(false);
        dismissSpinner();
        super.onDestroy();
    }

    public static Intent createOAuthActivityIntent(Context context, String clientId, String clientSecret, String redirectUrl, boolean loginViaBoxApp) {
        Intent intent = new Intent(context, OAuthActivity.class);
        intent.putExtra(BoxAuthenticationInfo.FIELD_CLIENT_ID, clientId);
        intent.putExtra(BoxConstants.KEY_CLIENT_SECRET, clientSecret);
        if (!SdkUtils.isEmptyString(redirectUrl)) {
            intent.putExtra(BoxConstants.KEY_REDIRECT_URL, redirectUrl);
        }
        intent.putExtra(LOGIN_VIA_BOX_APP, loginViaBoxApp);
        return intent;
    }

    public static Intent createOAuthActivityIntent(Context context, BoxSession session, boolean loginViaBoxApp) {
        Intent intent = createOAuthActivityIntent(context, session.getClientId(), session.getClientSecret(), session.getRedirectUrl(), loginViaBoxApp);
        intent.putExtra(EXTRA_SESSION, session);
        if (!SdkUtils.isEmptyString(session.getUserId())) {
            intent.putExtra(EXTRA_USER_ID_RESTRICTION, session.getUserId());
        }
        return intent;
    }

    private String getAuthCreationErrorString(Exception e) {
        String error = getString(R.string.boxsdk_Authentication_fail);
        if (e != null) {
            if (e instanceof BoxException) {
                BoxError boxError = ((BoxException) e).getAsBoxError();
                if (boxError != null) {
                    if (((BoxException) e).getResponseCode() == HttpStatusCodes.STATUS_CODE_FORBIDDEN || ((BoxException) e).getResponseCode() == HttpStatusCodes.STATUS_CODE_UNAUTHORIZED || boxError.getError().equals("unauthorized_device")) {
                        error = error + ":" + getResources().getText(R.string.boxsdk_Authentication_fail_forbidden) + "\n";
                    } else {
                        error = error + ":";
                    }
                    return error + boxError.getErrorDescription();
                }
            }
            error = error + ":" + e;
        }
        return error;
    }

    private void clearCachedAuthenticationData() {
        if (this.oauthView != null) {
            this.oauthView.clearCache(true);
            this.oauthView.clearFormData();
            this.oauthView.clearHistory();
        }
        CookieSyncManager.createInstance(this);
        CookieManager.getInstance().removeAllCookie();
        deleteDatabase("webview.db");
        deleteDatabase("webviewCache.db");
        File cacheDirectory = getCacheDir();
        SdkUtils.deleteFolderRecursive(cacheDirectory);
        cacheDirectory.mkdir();
    }
}
