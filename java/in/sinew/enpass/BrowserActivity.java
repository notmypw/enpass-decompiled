package in.sinew.enpass;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewManager;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebIconDatabase;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.box.androidsdk.content.models.BoxRealTimeServer;
import com.box.androidsdk.content.models.BoxSharedLink;
import com.box.androidsdk.content.models.BoxUser;
import com.github.clans.fab.BuildConfig;
import in.sinew.enpass.utill.UIUtils;
import in.sinew.enpassengine.Card;
import in.sinew.enpassengine.CardField;
import in.sinew.enpassengine.CardField.CardFieldType;
import in.sinew.enpassengine.EnpassEngineConstants;
import in.sinew.enpassengine.GetDomain;
import in.sinew.enpassengine.IDisplayItem;
import in.sinew.enpassengine.Keychain;
import in.sinew.enpassui.adapter.BrowserAutoFillAdapter;
import io.enpass.app.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import net.sqlcipher.database.SQLiteDatabase;

public class BrowserActivity extends EnpassActivity {
    private final int ASK_SEARCH = 3;
    private final int BAIDU_SEARCH = 4;
    private final int BING_SEARCH = 1;
    private final String BROWSER_OVERLAY_LAYOUT = "browser_overlay";
    private final int CHROME_USER_AGENT = 1;
    private final int DUCKDUCKGO_SEARCH = 5;
    private final int ENPASS_USER_AGENT = 0;
    private final int FIREFOX_USER_AGENT = 2;
    private final int GOOGLE_SEARCH = 0;
    private final int OPERA_USER_AGENT = 3;
    private final int QWANT_SEARCH = 6;
    final int TAB_WIDTH = LoginActivity.TAB_WIDTH;
    private final int YAHOO_SEARCH = 2;
    String aurl;
    String cardDomainName = BuildConfig.FLAVOR;
    int count = 1;
    int index;
    private final String mAskSearchLink = "http://www.ask.com/web?q=%s";
    private final String mBaiduSearchLink = "https://www.baidu.com/s?wd=%s&rsv_spt=1&rsv_bp=0&ie";
    private final String mBingSearchLisk = "https://www.bing.com/search?q=%s";
    WebView mBrowserView;
    ImageButton mBtnReload;
    String mCardUuid;
    Context mContext;
    MutableContextWrapper mContextWrapper;
    private final String mDuckDuckGoLink = "https://duckduckgo.com/?q=%s";
    ImageView mFavicon;
    private final String mGoogleSearchLink = "https://www.google.com/search?q=%s";
    boolean mIsCreditCardLogin = false;
    boolean mIsNewTab = false;
    private final String mNewTab = "new tab";
    private final String mOldTab = "old tab";
    RelativeLayout mOverlayLayout;
    ProgressBar mProgressBar;
    private final String mQwant = "https://www.qwant.com/?q=%s";
    private final int mReguestCode = 1;
    List<Tab> mTabs;
    private final String mType = BoxRealTimeServer.FIELD_TYPE;
    String mUrl = BuildConfig.FLAVOR;
    AutoCompleteTextView mUrlEditor;
    private final String mYahooSearchLisk = "https://search.yahoo.com/search?p=%s";
    LinearLayout mainLayout;
    boolean selected = false;

    public class JsInterface {
        @JavascriptInterface
        public void setIsCreditCard(boolean val) {
            BrowserActivity.this.mIsCreditCardLogin = val;
            EnpassApplication.getInstance().setIsCreditCardLogin(val);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.animator.slide_left_in, R.animator.slide_left_out);
        setContentView(R.layout.activity_browser);
        EnpassApplication.getInstance().setBrowserActivity(this);
        Intent in = getIntent();
        this.mUrl = in.getStringExtra("cardUrl");
        this.mCardUuid = in.getStringExtra("cardUuid");
        this.mContext = this;
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.browser_action_bar);
        actionBar.setDisplayOptions(23);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.mainLayout = (LinearLayout) findViewById(R.id.browser_main_layout);
        this.mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.mFavicon = (ImageView) actionBar.getCustomView().findViewById(R.id.favicon);
        this.mUrlEditor = (AutoCompleteTextView) actionBar.getCustomView().findViewById(R.id.editText_browser_browserUrl);
        this.mBtnReload = (ImageButton) actionBar.getCustomView().findViewById(R.id.btn_refreshBrowser);
        this.mOverlayLayout = (RelativeLayout) findViewById(R.id.browser_overlay_layout);
        this.mFavicon.setVisibility(8);
        this.mProgressBar.setVisibility(8);
        this.mIsNewTab = getIntent().getBooleanExtra("new_tab", false);
        this.mainLayout.removeAllViews();
        this.mainLayout.removeAllViewsInLayout();
        int size = EnpassApplication.getInstance().mTabs.size();
        this.mContextWrapper = EnpassApplication.getInstance().getMutableContext();
        this.mContextWrapper.setBaseContext(this);
        if (size >= 1) {
            ArrayList<Tab> tabs = EnpassApplication.getInstance().mTabs;
            for (int i = 0; i < size; i++) {
                this.mBrowserView = ((Tab) tabs.get(i)).getWebView();
                ViewParent parent = this.mBrowserView.getParent();
                if (parent != null) {
                    ((ViewManager) parent).removeView(this.mBrowserView);
                }
                this.mainLayout.addView(this.mBrowserView, new LayoutParams(-1, -1));
                this.mFavicon.setVisibility(0);
            }
        } else if (size == 0) {
            this.mBrowserView = new WebView(this.mContextWrapper);
            this.mainLayout.addView(this.mBrowserView, new LayoutParams(-1, -1));
            EnpassApplication.getInstance().mTabId = 0;
        }
        this.mUrlEditor.setText(this.mUrl);
        if (TextUtils.isEmpty(this.mUrlEditor.getEditableText().toString())) {
            this.mBtnReload.setVisibility(4);
        } else {
            this.mBtnReload.setVisibility(0);
        }
        this.mBtnReload.setBackgroundResource(R.drawable.refresh_url);
        this.mTabs = EnpassApplication.getInstance().mTabs;
        if (size >= 1) {
            int a = EnpassApplication.getInstance().mTabId;
            setLastSelectedTab(EnpassApplication.getInstance().mTabId);
        }
        if (VERSION.SDK_INT <= 18) {
            WebIconDatabase.getInstance().open(getDir("icons", 0).getPath());
        }
        this.mBrowserView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("mailto://")) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction("android.intent.action.VIEW");
                    sendIntent.setData(Uri.parse(url));
                    BrowserActivity.this.mContext.startActivity(sendIntent);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Builder builder = new Builder(BrowserActivity.this);
                builder.setMessage(R.string.invalid_certificate);
                builder.setPositiveButton(BrowserActivity.this.getString(R.string.certificate_accept), new 1(this, handler));
                builder.setNegativeButton(BrowserActivity.this.getString(R.string.certificate_abort), new 2(this, handler));
                builder.create().show();
            }
        });
        getWindow().setSoftInputMode(3);
        WebSettings webSettings = this.mBrowserView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUserAgentString(getCustomUserAgentString(EnpassApplication.getInstance().getAppSettings().getBrowserUserAgent()));
        if (VERSION.SDK_INT <= 18) {
            webSettings.setSavePassword(false);
        }
        webSettings.setLoadWithOverviewMode(true);
        if (size == 0 && this.mIsNewTab) {
            loadWebUrl();
            this.mIsNewTab = false;
        }
        this.mUrlEditor.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                BrowserActivity.this.mUrlEditor.setCursorVisible(true);
                BrowserActivity.this.mUrlEditor.selectAll();
            }
        });
        this.mUrlEditor.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId != 2 || TextUtils.isEmpty(BrowserActivity.this.mUrlEditor.getText())) {
                    return false;
                }
                BrowserActivity.this.loadWebUrl();
                return true;
            }
        });
        this.mUrlEditor.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                BrowserActivity.this.loadWebUrl();
                ((InputMethodManager) BrowserActivity.this.getSystemService("input_method")).toggleSoftInput(1, 0);
            }
        });
        this.mBtnReload.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (BrowserActivity.this.mBrowserView.getProgress() == 100) {
                    BrowserActivity.this.mBrowserView.reload();
                } else {
                    BrowserActivity.this.mBrowserView.stopLoading();
                }
            }
        });
        if (EnpassApplication.getInstance().getSharedPreferences("browser_overlay", 0).getBoolean("browser_overlay", true)) {
            this.mOverlayLayout.setVisibility(0);
        } else {
            this.mOverlayLayout.setVisibility(4);
        }
        this.mOverlayLayout.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                BrowserActivity.this.mOverlayLayout.setVisibility(4);
                Editor editor = EnpassApplication.getInstance().getSharedPreferences("browser_overlay", 0).edit();
                editor.putBoolean("browser_overlay", false);
                editor.commit();
                return false;
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            if (data.getStringExtra(BoxRealTimeServer.FIELD_TYPE).equals("new tab")) {
                if (EnpassApplication.getInstance().mRemovedViews.size() > 0) {
                    removeWebviewFronMainPage();
                }
                EnpassApplication.getInstance().addTab(this.mBrowserView, snapShot());
                this.mUrl = BuildConfig.FLAVOR;
                addNewTab();
            }
            if (data.getStringExtra(BoxRealTimeServer.FIELD_TYPE).equals("old tab")) {
                this.mProgressBar.setVisibility(8);
                int id = data.getIntExtra("pos", 0);
                EnpassApplication.getInstance().mTabId = id;
                EnpassApplication.getInstance().mTabSelected = true;
                Tab atab = (Tab) this.mTabs.get(id);
                for (int i = 0; i < this.mTabs.size(); i++) {
                    if (((Tab) this.mTabs.get(i)).getWebView().equals(atab.getWebView())) {
                        atab.getWebView().setVisibility(0);
                        this.mBrowserView = atab.getWebView();
                    } else {
                        ((Tab) this.mTabs.get(i)).getWebView().setVisibility(8);
                    }
                }
                this.mUrlEditor.setText(this.mBrowserView.getUrl());
                if (this.mBrowserView.getFavicon() == null) {
                    this.mFavicon.setVisibility(8);
                } else {
                    this.mFavicon.setImageBitmap(this.mBrowserView.getFavicon());
                }
                this.mUrlEditor.setCursorVisible(false);
                guessCreditCardLogin();
            }
        }
    }

    public boolean isValidUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        return Patterns.WEB_URL.matcher(url.trim()).matches();
    }

    public void loadWebUrl() {
        String searchUrl = BuildConfig.FLAVOR;
        this.aurl = this.mUrlEditor.getText().toString();
        String country = ((TelephonyManager) this.mContext.getSystemService(BoxUser.FIELD_PHONE)).getSimCountryIso();
        this.mBrowserView.setVisibility(0);
        this.mBtnReload.setVisibility(0);
        if (isValidUrl(this.aurl)) {
            if (!this.aurl.startsWith("http")) {
                this.aurl = "https://" + this.aurl;
            }
            this.mBrowserView.loadUrl(this.aurl);
            this.mUrlEditor.setEnabled(false);
        } else {
            switch (EnpassApplication.getInstance().getAppSettings().getDefaultSearchProvider()) {
                case SQLiteDatabase.OPEN_READWRITE /*0*/:
                    searchUrl = String.format("https://www.google.com/search?q=%s", new Object[]{this.aurl});
                    break;
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    searchUrl = String.format("https://www.bing.com/search?q=%s", new Object[]{this.aurl});
                    break;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    searchUrl = String.format("https://search.yahoo.com/search?p=%s", new Object[]{this.aurl});
                    break;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    searchUrl = String.format("http://www.ask.com/web?q=%s", new Object[]{this.aurl});
                    break;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    searchUrl = String.format("https://www.baidu.com/s?wd=%s&rsv_spt=1&rsv_bp=0&ie", new Object[]{this.aurl});
                    break;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    searchUrl = String.format("https://duckduckgo.com/?q=%s", new Object[]{this.aurl});
                    break;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    searchUrl = String.format("https://www.qwant.com/?q=%s", new Object[]{this.aurl});
                    break;
            }
            this.mBrowserView.loadUrl(searchUrl);
            this.mUrlEditor.setEnabled(false);
        }
        if (this.mBrowserView != null) {
            this.mBrowserView.setWebViewClient(new WebViewClient() {
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    if (view.getVisibility() != 8) {
                        BrowserActivity.this.mBtnReload.setBackgroundResource(R.drawable.stop);
                        BrowserActivity.this.mProgressBar.setVisibility(0);
                        BrowserActivity.this.mUrlEditor.setText(url);
                    }
                }

                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if (view.getVisibility() != 8) {
                        BrowserActivity.this.mUrlEditor.setText(url);
                    }
                    BrowserActivity.this.mProgressBar.setVisibility(8);
                    BrowserActivity.this.mBtnReload.setBackgroundResource(R.drawable.refresh_url);
                    BrowserActivity.this.mUrlEditor.setEnabled(true);
                    BrowserActivity.this.mUrlEditor.setCursorVisible(false);
                    BrowserActivity.this.mUrlEditor.clearFocus();
                    BrowserActivity.this.guessCreditCardLogin();
                    Uri cardUri = Uri.parse(url);
                    BrowserActivity.this.cardDomainName = GetDomain.GetDomainFromUrl(cardUri);
                    if (EnpassApplication.getInstance().getAppSettings().isMatchHostnameEnabled()) {
                        BrowserActivity.this.cardDomainName = cardUri.getHost();
                        if (BrowserActivity.this.cardDomainName.contains("www.")) {
                            BrowserActivity.this.cardDomainName = BrowserActivity.this.cardDomainName.replace("www.", BuildConfig.FLAVOR);
                        }
                    }
                    if (!BrowserActivity.this.mUrl.isEmpty() && EnpassApplication.getInstance().getKeychain() != null) {
                        Card card = EnpassApplication.getInstance().getKeychain().getCardWithUuid(BrowserActivity.this.mCardUuid);
                        boolean isUrlMatch = false;
                        for (CardField field : card.getFields()) {
                            if (field.getType().equals(Card.mCardFieldTypeMap.get(CardFieldType.CardFieldTypeUrl))) {
                                String aFieldUrl = field.getValue().toString();
                                if (BrowserActivity.this.cardDomainName != null && aFieldUrl.contains(BrowserActivity.this.cardDomainName)) {
                                    isUrlMatch = true;
                                    break;
                                }
                            }
                        }
                        if (!isUrlMatch) {
                            return;
                        }
                        if (BrowserActivity.this.mIsCreditCardLogin) {
                            BrowserActivity.this.getCreditCardDetails(card);
                        } else {
                            BrowserActivity.this.getLoginDetailsFromCard(card);
                        }
                    }
                }

                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    Builder builder = new Builder(BrowserActivity.this);
                    builder.setMessage(R.string.invalid_certificate);
                    builder.setPositiveButton(BrowserActivity.this.getString(R.string.certificate_accept), new 1(this, handler));
                    builder.setNegativeButton(BrowserActivity.this.getString(R.string.certificate_abort), new 2(this, handler));
                    builder.create().show();
                }
            });
            this.mBrowserView.addJavascriptInterface(new JsInterface(), "guessCreditCardObject");
            this.mBrowserView.setWebChromeClient(new WebChromeClient() {
                public boolean onConsoleMessage(ConsoleMessage cm) {
                    return true;
                }

                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    if (view.getVisibility() != 8) {
                        BrowserActivity.this.mProgressBar.setProgress(newProgress);
                    }
                }

                public void onReceivedIcon(WebView view, Bitmap icon) {
                    super.onReceivedIcon(view, icon);
                    if (view.getVisibility() != 8) {
                        BrowserActivity.this.mFavicon.setVisibility(0);
                        BrowserActivity.this.mFavicon.setImageBitmap(icon);
                    }
                }
            });
        }
    }

    protected void onResume() {
        super.onResume();
        removeWebviewFronMainPage();
        if (this.mIsNewTab && this.mTabs.size() > 0) {
            addNewTab();
            this.mUrlEditor.setText(this.mUrl);
            loadWebUrl();
            this.mIsNewTab = false;
        }
        if (TextUtils.isEmpty(this.mUrlEditor.getEditableText().toString())) {
            this.mBtnReload.setVisibility(4);
        } else {
            this.mBtnReload.setVisibility(0);
        }
    }

    private void removeWebviewFronMainPage() {
        int size = EnpassApplication.getInstance().mRemovedViews.size();
        boolean aTabRemoved = false;
        if (size > 0) {
            aTabRemoved = true;
            while (size > 0) {
                this.mainLayout.removeView((View) EnpassApplication.getInstance().mRemovedViews.get(0));
                EnpassApplication.getInstance().mRemovedViews.remove(0);
                size--;
            }
            if (this.mTabs.size() == 0) {
                addNewTab();
            }
            this.mProgressBar.setVisibility(8);
        }
        if (this.mTabs.size() > 0) {
            boolean selectedTabRemoved = false;
            for (int i = 0; i < this.mTabs.size(); i++) {
                if (((Tab) this.mTabs.get(i)).getWebView() != this.mBrowserView) {
                    ((Tab) this.mTabs.get(i)).getWebView().setVisibility(8);
                } else {
                    selectedTabRemoved = true;
                }
            }
            if (!selectedTabRemoved && this.mTabs.size() > 0 && aTabRemoved) {
                this.mBrowserView = ((Tab) this.mTabs.get(this.mTabs.size() - 1)).getWebView();
                EnpassApplication.getInstance().mTabId = this.mTabs.size() - 1;
            }
        }
        this.mBrowserView.setVisibility(0);
        this.mUrlEditor.setText(this.mBrowserView.getUrl());
        if (this.mBrowserView.getFavicon() == null) {
            this.mFavicon.setVisibility(8);
        } else {
            this.mFavicon.setImageBitmap(this.mBrowserView.getFavicon());
        }
        guessCreditCardLogin();
    }

    public void onBackPressed() {
        if (this.mBrowserView.canGoBack()) {
            this.mBrowserView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public Bitmap snapShot() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int aDisplayHeight = metrics.heightPixels;
        int aDisplayWidth = metrics.widthPixels;
        if (getResources().getConfiguration().orientation == 1) {
            aDisplayHeight /= 2;
        }
        Bitmap bitmap = Bitmap.createBitmap(aDisplayWidth, aDisplayHeight, Config.ARGB_8888);
        this.mBrowserView.draw(new Canvas(bitmap));
        return Bitmap.createScaledBitmap(bitmap, (int) this.mContext.getResources().getDimension(R.dimen.tab_width1), (int) this.mContext.getResources().getDimension(R.dimen.tab_height1), true);
    }

    public void addNewTab() {
        this.mBrowserView = new WebView(this.mContextWrapper);
        this.mBrowserView.setWebViewClient(new WebViewClient() {
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Builder builder = new Builder(BrowserActivity.this);
                builder.setMessage(R.string.invalid_certificate);
                builder.setPositiveButton(BrowserActivity.this.getString(R.string.certificate_accept), new 1(this, handler));
                builder.setNegativeButton(BrowserActivity.this.getString(R.string.certificate_abort), new 2(this, handler));
                builder.create().show();
            }
        });
        this.mainLayout.addView(this.mBrowserView, new LayoutParams(-1, -1));
        WebSettings webSettings = this.mBrowserView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        if (VERSION.SDK_INT <= 18) {
            webSettings.setSavePassword(false);
        }
        this.mUrlEditor.setText(BuildConfig.FLAVOR);
        this.mUrlEditor.setEnabled(true);
        this.mUrlEditor.setCursorVisible(true);
        EnpassApplication.getInstance().mTabSelected = false;
        EnpassApplication.getInstance().mTabId = this.mTabs.size();
        for (int i = 0; i < this.mTabs.size(); i++) {
            ((Tab) this.mTabs.get(i)).getWebView().setVisibility(8);
        }
        this.mBrowserView.setVisibility(0);
        this.mFavicon.setVisibility(8);
        this.mProgressBar.setVisibility(8);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browser_main_menu, menu);
        MenuItem autoFillItem = menu.findItem(R.id.auto_fill);
        if (MainActivity.mTwoPane) {
            autoFillItem.setShowAsAction(2);
        } else {
            autoFillItem.setShowAsAction(0);
        }
        MenuItem showTabsItem = menu.findItem(R.id.show_tabs);
        if (MainActivity.mTwoPane) {
            showTabsItem.setShowAsAction(2);
        } else {
            showTabsItem.setShowAsAction(0);
        }
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                this.mOverlayLayout.setVisibility(4);
                Editor editor = EnpassApplication.getInstance().getSharedPreferences("browser_overlay", 0).edit();
                editor.putBoolean("browser_overlay", false);
                editor.commit();
                finish();
                break;
            case R.id.auto_fill /*2131296339*/:
                guessCreditCardLogin();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        BrowserActivity.this.fillDetails();
                    }
                }, 120);
                break;
            case R.id.browser_generate_password_menu /*2131296367*/:
                String aDomain = BuildConfig.FLAVOR;
                String aUrl = this.mUrlEditor.getText().toString();
                if (!aUrl.isEmpty()) {
                    if (!aUrl.startsWith("http")) {
                        aUrl = "https://" + aUrl;
                    }
                    aDomain = GetDomain.GetDomainFromUrl(Uri.parse(aUrl));
                    if (aDomain == null) {
                        aDomain = BuildConfig.FLAVOR;
                    }
                }
                if (getResources().getConfiguration().smallestScreenWidthDp >= LoginActivity.TAB_WIDTH) {
                    PasswordGeneratorDialogFragment dialog = new PasswordGeneratorDialogFragment();
                    dialog.setStyle(1, 0);
                    Bundle bundle = new Bundle();
                    bundle.putString("url_domain", BuildConfig.FLAVOR);
                    bundle.putBoolean("from_edit", false);
                    bundle.putBoolean("IsTab", true);
                    dialog.setArguments(bundle);
                    dialog.show(((Activity) this.mContext).getFragmentManager(), BoxSharedLink.FIELD_PASSWORD);
                    break;
                }
                Intent intent = new Intent(this.mContext, PasswordGeneratorActivity.class);
                intent.putExtra("url_domain", aDomain);
                intent.putExtra("from_edit", false);
                startActivity(intent);
                break;
            case R.id.show_tabs /*2131296964*/:
                EnpassApplication.getInstance().addTab(this.mBrowserView, snapShot());
                startActivityForResult(new Intent(this, TabViewActivity.class), 1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void finish() {
        EnpassApplication.getInstance().addTab(this.mBrowserView, snapShot());
        EnpassApplication.getInstance().mTabSelected = false;
        this.mainLayout.removeAllViews();
        this.mainLayout.removeAllViewsInLayout();
        super.finish();
        overridePendingTransition(R.animator.slide_right_in, R.animator.slide_right_out);
    }

    public void fillDetails() {
        String url = this.mUrlEditor.getText().toString();
        if (!url.isEmpty()) {
            Uri uri = Uri.parse(url);
            String urlTitle = GetDomain.GetDomainFromUrl(uri);
            boolean matchHostnameEnabled = EnpassApplication.getInstance().getAppSettings().isMatchHostnameEnabled();
            if (matchHostnameEnabled) {
                urlTitle = uri.getHost();
                if (urlTitle.contains("www.")) {
                    urlTitle = urlTitle.replace("www.", BuildConfig.FLAVOR);
                }
            }
            if (urlTitle != null) {
                Keychain keychainObj = EnpassApplication.getInstance().getKeychain();
                if (keychainObj != null) {
                    List<IDisplayItem> cardList = new ArrayList();
                    this.mIsCreditCardLogin = EnpassApplication.getInstance().getIsCreditCardLogin();
                    if (this.mIsCreditCardLogin) {
                        for (String aUuid : (ArrayList) keychainObj.getAllCreditCardsAndBankAccounts()) {
                            cardList.add(keychainObj.getCardWithUuid(aUuid));
                        }
                    } else {
                        List<String> cardsUuids;
                        if (matchHostnameEnabled) {
                            cardsUuids = keychainObj.getAllCardsWithSameHostname(uri);
                        } else {
                            cardsUuids = keychainObj.getAllCardsWithSameDomainName(urlTitle);
                        }
                        if (cardsUuids != null) {
                            for (String s : cardsUuids) {
                                cardList.add(keychainObj.getCardWithUuid(s));
                            }
                        }
                    }
                    List<IDisplayItem> filterList = EnpassApplication.getInstance().filterCards(cardList);
                    if (filterList.size() > 0) {
                        showCardsWithSameDomainName(filterList);
                        return;
                    } else {
                        showNoLoginItemsAlert(urlTitle);
                        return;
                    }
                }
                return;
            }
            showNoLoginItemsAlert(urlTitle);
        }
    }

    public void showCardsWithSameDomainName(List<IDisplayItem> aCardsList) {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.autofill);
        final ArrayList<IDisplayItem> cardList = (ArrayList) aCardsList;
        builder.setSingleChoiceItems(new BrowserAutoFillAdapter(this, cardList, true, true, false), -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                dialog.dismiss();
                Card card = EnpassApplication.getInstance().getKeychain().getCardWithUuid(((Card) cardList.get(item)).getDisplayIdentifier());
                if (BrowserActivity.this.mIsCreditCardLogin) {
                    BrowserActivity.this.getCreditCardDetails(card);
                } else {
                    BrowserActivity.this.getLoginDetailsFromCard(card);
                }
            }
        });
        builder.show();
    }

    void getLoginDetailsFromCard(Card selectedCard) {
        List<CardField> fields = selectedCard.getFields();
        StringBuilder userName = new StringBuilder(BuildConfig.FLAVOR);
        StringBuilder password = new StringBuilder(BuildConfig.FLAVOR);
        for (CardField f : fields) {
            if (!f.getType().equals(Card.mCardFieldTypeMap.get(CardFieldType.CardFieldTypeUsername)) || f.isDeleted()) {
                if (f.getType().equals(Card.mCardFieldTypeMap.get(CardFieldType.CardFieldTypePassword)) && !f.isDeleted() && TextUtils.isEmpty(password)) {
                    password = f.getValue();
                }
            } else if (TextUtils.isEmpty(userName)) {
                userName = f.getValue();
            }
            if (userName.length() == 0 && f.getType().equals(Card.mCardFieldTypeMap.get(CardFieldType.CardFieldTypeEmail)) && !f.isDeleted()) {
                userName = f.getValue();
            }
        }
        fillLoginDetails(userName.toString(), password.toString());
        selectedCard.wipe();
    }

    void fillLoginDetails(String userName, String password) {
        boolean aAutoSubmitEnable = EnpassApplication.getInstance().getAppSettings().getAutoSubmitEnable();
        try {
            String htmlData = readStream(getAssets().open("getLogin.js"));
            String aPassword = password.replace("\\", "\\\\").replace("'", "\\'").replace("\"", "\\\"");
            String aUserName = userName.replace("\\", "\\\\").replace("'", "\\'").replace("\"", "\\\"");
            String escapePassword = URLEncoder.encode(aPassword, "UTF-8");
            htmlData = String.format(htmlData, new Object[]{aUserName, escapePassword, Boolean.valueOf(aAutoSubmitEnable)});
            this.mUrl = BuildConfig.FLAVOR;
            this.mBrowserView.loadUrl("javascript:" + htmlData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void getCreditCardDetails(Card aCard) {
        List<CardField> fields = aCard.getFields();
        StringBuilder aCreditCardNum = new StringBuilder(BuildConfig.FLAVOR);
        StringBuilder aCreditCardHolder = new StringBuilder(BuildConfig.FLAVOR);
        StringBuilder aCreditCardCVV = new StringBuilder(BuildConfig.FLAVOR);
        StringBuilder aCreditCardType = new StringBuilder(BuildConfig.FLAVOR);
        StringBuilder aCreditCardExpMonth = new StringBuilder(BuildConfig.FLAVOR);
        StringBuilder aCreditCardExpYear = new StringBuilder(BuildConfig.FLAVOR);
        for (CardField f : fields) {
            if (!f.getType().equals(EnpassEngineConstants.CardFieldTypeCardNumber) || f.isDeleted()) {
                if (!f.getType().equals(EnpassEngineConstants.CardFieldTypeCardHolderName) || f.isDeleted()) {
                    if (!f.getType().equals(EnpassEngineConstants.CardFieldTypeCardCVC) || f.isDeleted()) {
                        if (!f.isOptionalField() || f.isDeleted()) {
                            if (f.getType().equals(EnpassEngineConstants.CardFieldTypeExpiryDate) && !f.isDeleted()) {
                                String expDate = f.getValue().toString();
                                String[] splitArr = new String[0];
                                if (expDate.contains("/")) {
                                    splitArr = expDate.split("/");
                                } else {
                                    if (expDate.contains("-")) {
                                        splitArr = expDate.split("-");
                                    } else {
                                        if (expDate.contains(".")) {
                                            splitArr = expDate.split(".");
                                        }
                                    }
                                }
                                if (splitArr.length == 2) {
                                    aCreditCardExpMonth = new StringBuilder(splitArr[0]);
                                    aCreditCardExpYear = new StringBuilder(splitArr[1]);
                                    if (aCreditCardExpYear.length() <= 2) {
                                        aCreditCardExpYear = new StringBuilder(String.valueOf(Integer.parseInt(aCreditCardExpYear.toString()) + 2000));
                                    }
                                } else if (splitArr.length == 3) {
                                    aCreditCardExpMonth = new StringBuilder(splitArr[1]);
                                    aCreditCardExpYear = new StringBuilder(splitArr[2]);
                                    if (aCreditCardExpYear.length() <= 2) {
                                        aCreditCardExpYear = new StringBuilder(String.valueOf(Integer.parseInt(aCreditCardExpYear.toString()) + 2000));
                                    }
                                } else {
                                    Calendar currentDate = Calendar.getInstance();
                                    int as = currentDate.get(2);
                                    aCreditCardExpMonth = new StringBuilder(String.valueOf(currentDate.get(2)));
                                    aCreditCardExpYear = new StringBuilder(String.valueOf(currentDate.get(1)));
                                }
                            }
                        } else if (TextUtils.isEmpty(aCreditCardType)) {
                            aCreditCardType = f.getValue();
                        }
                    } else if (TextUtils.isEmpty(aCreditCardCVV)) {
                        aCreditCardCVV = f.getValue();
                    }
                } else if (TextUtils.isEmpty(aCreditCardHolder)) {
                    aCreditCardHolder = f.getValue();
                }
            } else if (TextUtils.isEmpty(aCreditCardNum)) {
                aCreditCardNum = f.getValue();
            }
        }
        fillCreditCardDetails(aCreditCardNum.toString(), aCreditCardHolder.toString(), aCreditCardCVV.toString(), aCreditCardExpMonth.toString(), aCreditCardExpYear.toString(), UIUtils.getOptionRealPart(aCreditCardType.toString()));
        aCard.wipe();
    }

    void fillCreditCardDetails(String cardNum, String cardHolder, String aCVV, String month, String year, String type) {
        try {
            this.mBrowserView.loadUrl("javascript:" + String.format(readStream(getAssets().open("fillCreditCardForm.js")), new Object[]{cardNum, cardHolder, aCVV, month, year, type}));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readStream(InputStream inputStream) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder total = new StringBuilder();
        while (true) {
            String line = r.readLine();
            if (line == null) {
                return total.toString();
            }
            total.append(line);
        }
    }

    private boolean guessCreditCardLogin() {
        try {
            this.mBrowserView.loadUrl("javascript:" + readStream(getAssets().open("guessCreditCardForm.js")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    void showNoLoginItemsAlert(String domain) {
        String aNoLoginText = String.format(getResources().getString(R.string.no_login), new Object[]{domain});
        Builder builder = new Builder(this);
        builder.setMessage(aNoLoginText);
        builder.setNeutralButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void finishBrowserActivity() {
        finish();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        EnpassApplication.getInstance().changeLocale(this);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 82) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setLastSelectedTab(int id) {
        if (id >= this.mTabs.size()) {
            id = this.mTabs.size() - 1;
        }
        EnpassApplication.getInstance().mTabId = id;
        EnpassApplication.getInstance().mTabSelected = true;
        Tab atab = (Tab) this.mTabs.get(id);
        for (int i = 0; i < this.mTabs.size(); i++) {
            if (((Tab) this.mTabs.get(i)).getWebView().equals(atab.getWebView())) {
                atab.getWebView().setVisibility(0);
                this.mBrowserView = atab.getWebView();
            } else {
                ((Tab) this.mTabs.get(i)).getWebView().setVisibility(8);
            }
        }
        this.mUrlEditor.setText(this.mBrowserView.getUrl());
        if (this.mBrowserView.getFavicon() == null) {
            this.mFavicon.setVisibility(8);
        } else {
            this.mFavicon.setImageBitmap(this.mBrowserView.getFavicon());
        }
        this.mUrlEditor.setCursorVisible(false);
    }

    public String getCustomUserAgentString(int ua) {
        String userAgentString = BuildConfig.FLAVOR;
        AppSettings settings = EnpassApplication.getInstance().getAppSettings();
        switch (ua) {
            case SQLiteDatabase.OPEN_READWRITE /*0*/:
                return "Mozilla/5.0 (Linux; " + settings.getSystem() + "; " + settings.getDeviceName() + " Build/" + Build.ID + ") AppleWebKit/537.36 (KHTML, like Gecko) " + getString(R.string.app_name) + "/" + settings.getVersion() + " Mobile Safari/537.36";
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                return "Mozilla/5.0 (Linux; " + settings.getSystem() + "; " + settings.getDeviceName() + " Build/" + Build.ID + ") AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.83 Mobile Safari/537.36";
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                return "Mozilla/5.0 (" + settings.getSystem() + "; Mobile; rv:43.0) Gecko/43.0 Firefox/43.0";
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                return " Mozilla/5.0 (Linux; " + settings.getSystem() + ";" + settings.getDeviceName() + " Build/" + Build.ID + ") AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.99 Mobile Safari/537.36 OPR/35.0.2070.100283";
            default:
                return userAgentString;
        }
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(EnpassApplication.getInstance().changeLocale(base));
    }
}
