package com.box.androidsdk.content.auth;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.net.http.SslCertificate.DName;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.box.androidsdk.content.BoxConfig;
import com.box.androidsdk.content.BoxConstants;
import com.box.androidsdk.content.auth.BoxAuthentication.BoxAuthenticationInfo;
import com.box.androidsdk.content.models.BoxError;
import com.box.androidsdk.content.requests.BoxRequest.BoxRequestHandler;
import com.box.androidsdk.content.utils.SdkUtils;
import com.box.sdk.android.R;
import com.github.clans.fab.BuildConfig;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Formatter;
import net.sqlcipher.database.SQLiteDatabase;

public class OAuthWebView extends WebView {
    private static final String STATE = "state";
    private static final String URL_QUERY_LOGIN = "box_login";
    private String mBoxAccountEmail;
    private String state;

    public interface OnPageFinishedListener {
        void onPageFinished(WebView webView, String str);
    }

    public static class AuthFailure {
        public static final int TYPE_URL_MISMATCH = 1;
        public static final int TYPE_USER_INTERACTION = 0;
        public static final int TYPE_WEB_ERROR = 2;
        public WebViewException mWebException;
        public String message;
        public int type;

        public AuthFailure(int failType, String failMessage) {
            this.type = failType;
            this.message = failMessage;
        }

        public AuthFailure(WebViewException exception) {
            this(TYPE_WEB_ERROR, null);
            this.mWebException = exception;
        }
    }

    private static class InvalidUrlException extends Exception {
        private static final long serialVersionUID = 1;

        private InvalidUrlException() {
        }
    }

    public static class OAuthWebViewClient extends WebViewClient {
        private static final int WEB_VIEW_TIMEOUT = 30000;
        private Handler mHandler = new Handler(Looper.getMainLooper());
        private OnPageFinishedListener mOnPageFinishedListener;
        private String mRedirectUrl;
        private WebViewTimeOutRunnable mTimeOutRunnable;
        private WebEventListener mWebEventListener;
        private boolean sslErrorDialogContinueButtonClicked;
        private String state;

        public interface WebEventListener {
            boolean onAuthFailure(AuthFailure authFailure);

            void onReceivedAuthCode(String str);

            void onReceivedAuthCode(String str, String str2);
        }

        class WebViewTimeOutRunnable implements Runnable {
            final String mFailingUrl;
            final WeakReference<WebView> mViewHolder;

            public WebViewTimeOutRunnable(WebView view, String failingUrl) {
                this.mFailingUrl = failingUrl;
                this.mViewHolder = new WeakReference(view);
            }

            public void run() {
                OAuthWebViewClient.this.onReceivedError((WebView) this.mViewHolder.get(), -8, "loading timed out", this.mFailingUrl);
            }
        }

        public OAuthWebViewClient(WebEventListener eventListener, String redirectUrl, String stateString) {
            this.mWebEventListener = eventListener;
            this.mRedirectUrl = redirectUrl;
            this.state = stateString;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            try {
                Uri uri = getURIfromURL(url);
                String code = getValueFromURI(uri, BoxError.FIELD_CODE);
                if (!SdkUtils.isEmptyString(getValueFromURI(uri, BoxRequestHandler.OAUTH_ERROR_HEADER))) {
                    this.mWebEventListener.onAuthFailure(new AuthFailure(0, null));
                } else if (!SdkUtils.isEmptyString(code)) {
                    String baseDomain = getValueFromURI(uri, BoxAuthenticationInfo.FIELD_BASE_DOMAIN);
                    if (baseDomain != null) {
                        this.mWebEventListener.onReceivedAuthCode(code, baseDomain);
                    } else {
                        this.mWebEventListener.onReceivedAuthCode(code);
                    }
                }
            } catch (InvalidUrlException e) {
                this.mWebEventListener.onAuthFailure(new AuthFailure(1, null));
            }
            if (this.mTimeOutRunnable != null) {
                this.mHandler.removeCallbacks(this.mTimeOutRunnable);
            }
            this.mTimeOutRunnable = new WebViewTimeOutRunnable(view, url);
            this.mHandler.postDelayed(this.mTimeOutRunnable, 30000);
        }

        public void onPageFinished(WebView view, String url) {
            if (this.mTimeOutRunnable != null) {
                this.mHandler.removeCallbacks(this.mTimeOutRunnable);
            }
            super.onPageFinished(view, url);
            if (this.mOnPageFinishedListener != null) {
                this.mOnPageFinishedListener.onPageFinished(view, url);
            }
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            if (this.mTimeOutRunnable != null) {
                this.mHandler.removeCallbacks(this.mTimeOutRunnable);
            }
            if (!this.mWebEventListener.onAuthFailure(new AuthFailure(new WebViewException(errorCode, description, failingUrl)))) {
                String html;
                Formatter formatter;
                switch (errorCode) {
                    case -6:
                    case -2:
                        if (!SdkUtils.isInternetAvailable(view.getContext())) {
                            html = SdkUtils.getAssetFile(view.getContext(), "offline.html");
                            formatter = new Formatter();
                            formatter.format(html, new Object[]{view.getContext().getString(R.string.boxsdk_no_offline_access), view.getContext().getString(R.string.boxsdk_no_offline_access_detail), view.getContext().getString(R.string.boxsdk_no_offline_access_todo)});
                            view.loadData(formatter.toString(), "text/html", "UTF-8");
                            formatter.close();
                            break;
                        }
                    case -8:
                        html = SdkUtils.getAssetFile(view.getContext(), "offline.html");
                        formatter = new Formatter();
                        formatter.format(html, new Object[]{view.getContext().getString(R.string.boxsdk_unable_to_connect), view.getContext().getString(R.string.boxsdk_unable_to_connect_detail), view.getContext().getString(R.string.boxsdk_unable_to_connect_todo)});
                        view.loadData(formatter.toString(), "text/html", "UTF-8");
                        formatter.close();
                        break;
                }
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        }

        public void onReceivedHttpAuthRequest(WebView view, final HttpAuthHandler handler, String host, String realm) {
            final View textEntryView = LayoutInflater.from(view.getContext()).inflate(R.layout.boxsdk_alert_dialog_text_entry, null);
            new Builder(view.getContext()).setTitle(R.string.boxsdk_alert_dialog_text_entry).setView(textEntryView).setPositiveButton(R.string.boxsdk_alert_dialog_ok, new OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    handler.proceed(((EditText) textEntryView.findViewById(R.id.username_edit)).getText().toString(), ((EditText) textEntryView.findViewById(R.id.password_edit)).getText().toString());
                }
            }).setNegativeButton(R.string.boxsdk_alert_dialog_cancel, new OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    handler.cancel();
                    OAuthWebViewClient.this.mWebEventListener.onAuthFailure(new AuthFailure(0, null));
                }
            }).create().show();
        }

        public void onReceivedSslError(final WebView view, final SslErrorHandler handler, final SslError error) {
            String sslErrorType;
            if (this.mTimeOutRunnable != null) {
                this.mHandler.removeCallbacks(this.mTimeOutRunnable);
            }
            Resources resources = view.getContext().getResources();
            StringBuilder sslErrorMessage = new StringBuilder(resources.getString(R.string.boxsdk_There_are_problems_with_the_security_certificate_for_this_site));
            sslErrorMessage.append(" ");
            switch (error.getPrimaryError()) {
                case SQLiteDatabase.OPEN_READWRITE /*0*/:
                    sslErrorType = resources.getString(R.string.boxsdk_ssl_error_warning_NOT_YET_VALID);
                    break;
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    sslErrorType = resources.getString(R.string.boxsdk_ssl_error_warning_EXPIRED);
                    break;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    sslErrorType = resources.getString(R.string.boxsdk_ssl_error_warning_ID_MISMATCH);
                    break;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    sslErrorType = resources.getString(R.string.boxsdk_ssl_error_warning_UNTRUSTED);
                    break;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    sslErrorType = view.getResources().getString(R.string.boxsdk_ssl_error_warning_DATE_INVALID);
                    break;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    sslErrorType = resources.getString(R.string.boxsdk_ssl_error_warning_INVALID);
                    break;
                default:
                    sslErrorType = resources.getString(R.string.boxsdk_ssl_error_warning_INVALID);
                    break;
            }
            sslErrorMessage.append(sslErrorType);
            sslErrorMessage.append(" ");
            sslErrorMessage.append(resources.getString(R.string.boxsdk_ssl_should_not_proceed));
            this.sslErrorDialogContinueButtonClicked = false;
            Builder alertBuilder = new Builder(view.getContext()).setTitle(R.string.boxsdk_Security_Warning).setMessage(sslErrorMessage.toString()).setIcon(R.drawable.boxsdk_dialog_warning).setNegativeButton(R.string.boxsdk_Go_back, new OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    OAuthWebViewClient.this.sslErrorDialogContinueButtonClicked = true;
                    handler.cancel();
                    OAuthWebViewClient.this.mWebEventListener.onAuthFailure(new AuthFailure(0, null));
                }
            });
            if (BoxConfig.ALLOW_SSL_ERROR) {
                alertBuilder.setNeutralButton(R.string.boxsdk_ssl_error_details, null);
                alertBuilder.setPositiveButton(R.string.boxsdk_Continue, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        OAuthWebViewClient.this.sslErrorDialogContinueButtonClicked = true;
                        handler.proceed();
                    }
                });
            }
            AlertDialog loginAlert = alertBuilder.create();
            loginAlert.setOnDismissListener(new OnDismissListener() {
                public void onDismiss(DialogInterface dialog) {
                    if (!OAuthWebViewClient.this.sslErrorDialogContinueButtonClicked) {
                        OAuthWebViewClient.this.mWebEventListener.onAuthFailure(new AuthFailure(0, null));
                    }
                }
            });
            loginAlert.show();
            if (BoxConfig.ALLOW_SSL_ERROR) {
                Button neutralButton = loginAlert.getButton(-3);
                if (neutralButton != null) {
                    neutralButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            OAuthWebViewClient.this.showCertDialog(view.getContext(), error);
                        }
                    });
                }
            }
        }

        protected void showCertDialog(Context context, SslError error) {
            new Builder(context).setTitle(R.string.boxsdk_Security_Warning).setView(getCertErrorView(context, error.getCertificate())).create().show();
        }

        private View getCertErrorView(Context context, SslCertificate certificate) {
            View certificateView = LayoutInflater.from(context).inflate(R.layout.ssl_certificate, null);
            DName issuedTo = certificate.getIssuedTo();
            if (issuedTo != null) {
                ((TextView) certificateView.findViewById(R.id.to_common)).setText(issuedTo.getCName());
                ((TextView) certificateView.findViewById(R.id.to_org)).setText(issuedTo.getOName());
                ((TextView) certificateView.findViewById(R.id.to_org_unit)).setText(issuedTo.getUName());
            }
            DName issuedBy = certificate.getIssuedBy();
            if (issuedBy != null) {
                ((TextView) certificateView.findViewById(R.id.by_common)).setText(issuedBy.getCName());
                ((TextView) certificateView.findViewById(R.id.by_org)).setText(issuedBy.getOName());
                ((TextView) certificateView.findViewById(R.id.by_org_unit)).setText(issuedBy.getUName());
            }
            ((TextView) certificateView.findViewById(R.id.issued_on)).setText(formatCertificateDate(context, certificate.getValidNotBeforeDate()));
            ((TextView) certificateView.findViewById(R.id.expires_on)).setText(formatCertificateDate(context, certificate.getValidNotAfterDate()));
            return certificateView;
        }

        private String formatCertificateDate(Context context, Date certificateDate) {
            if (certificateDate == null) {
                return BuildConfig.FLAVOR;
            }
            return DateFormat.getDateFormat(context).format(certificateDate);
        }

        public void destroy() {
            this.mWebEventListener = null;
        }

        private Uri getURIfromURL(String url) {
            Uri uri = Uri.parse(url);
            if (SdkUtils.isEmptyString(this.mRedirectUrl)) {
                return uri;
            }
            Uri redirectUri = Uri.parse(this.mRedirectUrl);
            if (redirectUri.getScheme() != null && redirectUri.getScheme().equals(uri.getScheme()) && redirectUri.getAuthority().equals(uri.getAuthority())) {
                return uri;
            }
            return null;
        }

        private String getValueFromURI(Uri uri, String key) throws InvalidUrlException {
            if (uri == null) {
                return null;
            }
            String value = null;
            try {
                value = uri.getQueryParameter(key);
            } catch (Exception e) {
            }
            if (SdkUtils.isEmptyString(value) || SdkUtils.isEmptyString(this.state)) {
                return value;
            }
            if (this.state.equals(uri.getQueryParameter(OAuthWebView.STATE))) {
                return value;
            }
            throw new InvalidUrlException();
        }

        public void setOnPageFinishedListener(OnPageFinishedListener listener) {
            this.mOnPageFinishedListener = listener;
        }
    }

    public static class WebViewException extends Exception {
        private final String mDescription;
        private final int mErrorCode;
        private final String mFailingUrl;

        public WebViewException(int errorCode, String description, String failingUrl) {
            this.mErrorCode = errorCode;
            this.mDescription = description;
            this.mFailingUrl = failingUrl;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        public String getDescription() {
            return this.mDescription;
        }

        public String getFailingUrl() {
            return this.mFailingUrl;
        }
    }

    public OAuthWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public String getStateString() {
        return this.state;
    }

    public void setBoxAccountEmail(String boxAccountEmail) {
        this.mBoxAccountEmail = boxAccountEmail;
    }

    public void authenticate(String clientId, String redirectUrl) {
        authenticate(buildUrl(clientId, redirectUrl));
    }

    public void authenticate(Uri.Builder authenticationUriBuilder) {
        this.state = SdkUtils.generateStateToken();
        authenticationUriBuilder.appendQueryParameter(STATE, this.state);
        loadUrl(authenticationUriBuilder.build().toString());
    }

    protected Uri.Builder buildUrl(String clientId, String redirectUrl) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https");
        builder.authority("app.box.com");
        builder.appendPath("api");
        builder.appendPath("oauth2");
        builder.appendPath("authorize");
        builder.appendQueryParameter("response_type", BoxError.FIELD_CODE);
        builder.appendQueryParameter(BoxAuthenticationInfo.FIELD_CLIENT_ID, clientId);
        builder.appendQueryParameter(BoxConstants.KEY_REDIRECT_URL, redirectUrl);
        if (this.mBoxAccountEmail != null) {
            builder.appendQueryParameter(URL_QUERY_LOGIN, this.mBoxAccountEmail);
        }
        return builder;
    }
}
