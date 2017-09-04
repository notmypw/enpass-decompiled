package in.sinew.enpass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.app.KeyguardManager;
import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.support.v4.content.LocalBroadcastManager;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import com.box.androidsdk.content.models.BoxSharedLink;
import com.github.clans.fab.BuildConfig;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.api.client.http.HttpStatusCodes;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.pass.Spass;
import com.samsung.android.sdk.pass.SpassFingerprint;
import in.sinew.enpass.AndroidWatch.WearConnectionRemote;
import in.sinew.enpass.autofill.NotificationFillActivity;
import in.sinew.enpass.chromeconnector.ChromeConnectorForegroundService;
import in.sinew.enpass.locker.EnpassAutoLocker;
import in.sinew.enpass.locker.EnpassAutoLocker.LoginScreenEnum;
import in.sinew.enpass.utill.EnpassDbValidator;
import in.sinew.enpass.utill.TimeTrackerForKeyboard;
import in.sinew.enpass.utill.UIUtils;
import in.sinew.enpass.utill.Waiter;
import in.sinew.enpassengine.Card;
import in.sinew.enpassengine.Card.DBValidationResult;
import in.sinew.enpassengine.CardField;
import in.sinew.enpassengine.CardMeta;
import in.sinew.enpassengine.IAppEventSubscriber;
import in.sinew.enpassengine.IDisplayItem;
import in.sinew.enpassengine.IKeychainDelegate;
import in.sinew.enpassengine.IKeychainDelegate.KeychainChangeType;
import in.sinew.enpassengine.Keychain;
import in.sinew.enpassengine.TemplateLoader;
import in.sinew.enpassengine.Utils;
import io.enpass.app.R;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import net.sqlcipher.database.SQLiteDatabase;
import org.json.JSONException;
import org.json.JSONObject;

public class EnpassApplication extends Application implements IRemoteStorage, IKeychainDelegate {
    public static final String APP_DIRTY_PREFERENCE = "appDirty";
    public static final String BOX_CLIENT_ID = "jqvtl0k85tgnt9050z1el6gvzembioyg";
    public static final String BOX_CLIENT_SECRET = "XTsP1XaNmdeDHYvG1Y2RwyDq3cIVih4T";
    public static final String BOX_REDIRECT_URL = "https://www.sinew.in/boxenpass/";
    public static int BOX_REMOTE = 6;
    public static int DROPBOX_REMOTE = 2;
    public static final String FINGERPRINT_TYPE_ANDROID = "ANDROID_FINGERPRINT_SCANNER";
    public static final String FINGERPRINT_TYPE_SAMSUNG = "SAMSUNG_FINGERPRINT_SCANNER";
    private static final String GOOGLE_ANALYTICS_PROPERTY_ID = "UA-74241294-1";
    public static int GOOGLE_DRIVE_REMOTE = 4;
    public static int ICLOUD_REMOTE = 3;
    public static int INSTALL_FROM_AMAZON_STORE = 2;
    public static int INSTALL_FROM_GOOGLE_PLAY = 1;
    public static int INSTALL_FROM_NOKIA_STORE = 3;
    public static final String LANGUAGE_PREFERENCE = "language";
    public static int LOCAL_REMOTE = 1;
    public static int MARKET_ID = INSTALL_FROM_GOOGLE_PLAY;
    public static int MIN_MASTER_PASSWORD_LENGTH = 10;
    public static int PIN = 7;
    public static int SKY_DRIVE_REMOTE = 5;
    public static int WIFIEXTENSION_PIN = 12;
    public static BoxRemote boxRemoteInstance = null;
    public static DropboxRemote dropboxInstance = null;
    public static FolderRemote folderRemoteInstance = null;
    public static GoogleDriveRemote googleDriveInstance = null;
    private static final String mDeviceDefaultLanguage = "default";
    public static EnpassApplication mInstance;
    public static OneDriveRemote oneDriveInstance;
    public static SyncManager sSyncManager;
    public static WearConnectionRemote wearRemoteInstance;
    public static WebDavRemote webDavRemoteInstance;
    public String AMAZON_STORE_URL = "http://www.amazon.com/gp/mas/dl/android?p=";
    private final long APP_IN_BACKGROUND_TIMER_TIME_MS = 1500;
    public String CHECK_UPDATE_AMAZON_URL = "https://rest.sinew.in/api/v1/checkupdate2/?format=json&platform=android-amazon&product=enpass&lite=%s&version=%s";
    public String CHECK_UPDATE_GOOGLE_URL = "https://rest.sinew.in/api/v1/checkupdate2/?format=json&platform=android&product=enpass&lite=%s&version=%s";
    public String CHECK_UPDATE_NOKIA_URL = "https://rest.sinew.in/api/v1/checkupdate2/?format=json&platform=android-nokiax&product=enpass&lite=%sversion=%s";
    public String CHECK_UPDATE_URL = BuildConfig.FLAVOR;
    private String FREE_PROMO_REGISTRATION_URL = "https://rest.sinew.in/freepromo/registerLicence/?product=Enpass&platform=Android";
    private String FREE_PROMO_VALIDATION_URL = "https://rest.sinew.in/freepromo/validateLicence/?product=Enpass&platform=Android";
    public String GOOGLE_STORE_URL = "https://play.google.com/store/apps/details?id=";
    public final int NEVER = 10;
    public String NOKIA_STORE_URL = "market://details?id=";
    public final String PARTNER_SERVER = "https://partner.enpass.io/";
    public String PLAY_STORE_ID = "io.enpass.app";
    public String PLAY_STORE_URL = BuildConfig.FLAVOR;
    private final int[] RealAutolockIntervals = new int[]{30, 60, HttpStatusCodes.STATUS_CODE_MULTIPLE_CHOICES, LoginActivity.TAB_WIDTH, 1800, 3600, 18000, 43200};
    String alreadyPurchaseHelpUrl = "http://enpass.io/kb/how-can-i-restore-my-previous-purchase-after-the-latest-release-of-enpass-as-a-new-app/";
    String amazon_recommend = "http://www.amazon.com/gp/mas/dl/android?p=io.enpass.app";
    String appName = "Enpass";
    String blogUrl = "http://www.enpass.io/blog/";
    String chromebookAutofillLearnMoreUrl = "https://www.enpass.io/chromebook-autofill-learn-more/";
    String chromebookUserManualUrl = "https://www.enpass.io/docs/android/chromebook.html";
    private boolean closeDbAfterSync = false;
    int daysUntilPrompt = 5;
    String deviceName = Build.MODEL;
    ArrayList<String> enableKeyboardsList;
    String facebookUrl = "https://www.facebook.com/Enpassapp";
    String faqUrl = "http://www.enpass.io/kb/android/";
    Map<Integer, String> fieldMap = new HashMap();
    String forumUrl = "http://discussion.enpass.io/";
    String google_recommend = "https://play.google.com/store/apps/details?id=io.enpass.app";
    Handler handler = new Handler();
    public boolean isAppUnlocked = false;
    ArrayList<String> launcherList;
    String locale = Locale.getDefault().toString();
    public Activity mAccountActivity;
    boolean mAppInBackgorund = false;
    private Timer mAppInBackgroundTimer;
    private TimerTask mAppInBackgroundTimerTask;
    private AppSettings mAppSettings;
    int mAutoLockInterval;
    EnpassAutoLocker mAutolocker;
    BrowserActivity mBrowserActivity = null;
    WebView mBrowserView = null;
    private Timer mCloseDbInBackgroundTimer;
    private TimerTask mCloseDbInBackgroundTimerTask;
    Configuration mConfig;
    int mCopyHash;
    ArrayList<CardField> mCurrentEditCardFields;
    private String mCurrentVisiblePackageName;
    boolean mDirty = false;
    private final String mEnpassScheme = "enpass://share";
    public List<IDisplayItem> mFilterList;
    protected boolean mForcelock;
    final Handler mHandler = new Handler();
    boolean mHaveLatest = false;
    private Date mInactiveSince = null;
    public Date mIsBackFromLogin = null;
    boolean mIsCreditCardLogin = false;
    private boolean mKeyboardExtendedViewVisible = false;
    Handler mKeyboardHandler = new Handler();
    Runnable mKeyboardRunnable;
    public TimeTrackerForKeyboard mKeyboardTimeTracker;
    Keychain mKeychain = null;
    public String mKeychainFilename = null;
    boolean mLatestUploaded = true;
    private Locale mLocale;
    public boolean mLoginActive = false;
    private LoginScreenEnum mLoginScreen = null;
    public Activity mMainActivity;
    MutableContextWrapper mMutableContext;
    private String mPackageNameForNotificationFill;
    String mProduct = "Enpass";
    IRemoteStorageDelegate mRemoteStorageDelegate = null;
    List<WebView> mRemovedViews;
    private boolean mSamsungFingerprintScannerAvailable;
    boolean mShowWelcome = true;
    private Spass mSpass;
    private SpassFingerprint mSpassFingerprint;
    Activity mSyncActivity = null;
    public int mTabId = 0;
    public boolean mTabSelected = false;
    List<Tab> mTabs;
    Timer mTimer;
    private Tracker mTracker;
    String manualUrl = "http://www.enpass.io/docs/android/";
    public int maxCardAllowed = 20;
    boolean multiWindowMode = false;
    String nokia_recommend = "http://store.ovi.com/content/502677";
    String platform = "Android";
    String recommend = BuildConfig.FLAVOR;
    int reminderAfterCards = 10;
    Runnable runnable;
    List<IAppEventSubscriber> subscribers;
    String system = ("Android " + VERSION.RELEASE);
    String twitterUrl = "https://twitter.com/EnpassApp";
    String updateAndAnalyticsLearnkMoreUrl = "https://www.enpass.io/mobile-updatesanalytics-learnmore/";
    int usageUntilPrompt = 10;
    public String version = "5.5.6";
    public Waiter waiter;
    String wearLearnMoreUrl = "https://www.enpass.io/android-wear-learn-more/";
    String writeUsEmail = "support@enpass.io";

    public Activity getBrowerActivity() {
        return this.mBrowserActivity;
    }

    public void setBrowserActivity(Activity activity) {
        this.mBrowserActivity = (BrowserActivity) activity;
    }

    public static EnpassApplication getInstance() {
        return mInstance;
    }

    public void setMainActivity(Activity activity) {
        this.mMainActivity = activity;
    }

    public Activity getMainActivity() {
        return this.mMainActivity;
    }

    public Keychain getKeychain() {
        return this.mKeychain;
    }

    public void setAccountActivity(AccountActivity activity) {
        this.mAccountActivity = activity;
    }

    public Activity getAccountActivity() {
        return this.mAccountActivity;
    }

    public void setCopyHash(int ahash) {
        this.mCopyHash = ahash;
    }

    public EnpassAutoLocker getEnpassAutoLocker() {
        return this.mAutolocker;
    }

    public boolean getIsCreditCardLogin() {
        return this.mIsCreditCardLogin;
    }

    public void setIsCreditCardLogin(boolean isCreditCard) {
        this.mIsCreditCardLogin = isCreditCard;
    }

    public void setReorderCardFields(ArrayList<CardField> fields) {
        this.mCurrentEditCardFields = fields;
    }

    public ArrayList<CardField> getReorderCardFields() {
        return this.mCurrentEditCardFields;
    }

    public MutableContextWrapper getMutableContext() {
        return this.mMutableContext;
    }

    public boolean isKeyboardExtendedViewVisible() {
        return this.mKeyboardExtendedViewVisible;
    }

    public void setKeyboardExtendedViewVisible(boolean keyboardExtendedViewVisible) {
        this.mKeyboardExtendedViewVisible = keyboardExtendedViewVisible;
    }

    public String getFreePromoRegistrationUrl() {
        return this.FREE_PROMO_REGISTRATION_URL;
    }

    public String getFreePromoValidationUrl() {
        return this.FREE_PROMO_VALIDATION_URL;
    }

    public String getWearLearnMoreLink() {
        return this.wearLearnMoreUrl;
    }

    public String getUpdateAndAnalyticsLearnkMoreUrl() {
        return this.updateAndAnalyticsLearnkMoreUrl;
    }

    public String getChromebookAutofillLearnMoreUrl() {
        return this.chromebookAutofillLearnMoreUrl;
    }

    public void onCreate() {
        super.onCreate();
        mInstance = this;
        this.mForcelock = true;
        this.mLoginActive = false;
        this.mInactiveSince = null;
        this.waiter = new Waiter(60000);
        this.mKeyboardTimeTracker = new TimeTrackerForKeyboard(60000);
        this.mAutolocker = new EnpassAutoLocker();
        this.mTabs = new ArrayList();
        this.mRemovedViews = new ArrayList();
        File file = new File(Environment.getDataDirectory() + "/data/io.enpass.app/databases/");
        if (!file.exists() && file.mkdir()) {
        }
        if (MARKET_ID == INSTALL_FROM_GOOGLE_PLAY) {
            this.PLAY_STORE_URL = this.GOOGLE_STORE_URL;
            this.CHECK_UPDATE_URL = this.CHECK_UPDATE_GOOGLE_URL;
            this.recommend = this.google_recommend;
        } else if (MARKET_ID == INSTALL_FROM_AMAZON_STORE) {
            this.PLAY_STORE_URL = this.AMAZON_STORE_URL;
            this.CHECK_UPDATE_URL = this.CHECK_UPDATE_AMAZON_URL;
            this.recommend = this.amazon_recommend;
        } else if (MARKET_ID == INSTALL_FROM_NOKIA_STORE) {
            this.PLAY_STORE_URL = this.NOKIA_STORE_URL;
            this.CHECK_UPDATE_URL = this.CHECK_UPDATE_NOKIA_URL;
            this.recommend = this.nokia_recommend;
        }
        this.mKeychainFilename = getDatabasePath("walletx.db").getAbsolutePath();
        this.mAppSettings = new AppSettings();
        getDefaultGoogleAnalyticsTracker();
        this.mSpass = new Spass();
        try {
            this.mSpass.initialize(this);
            this.mSamsungFingerprintScannerAvailable = this.mSpass.isFeatureEnabled(0);
            if (this.mSamsungFingerprintScannerAvailable) {
                this.mSpassFingerprint = new SpassFingerprint(this);
            }
        } catch (SsdkUnsupportedException e) {
            e.printStackTrace();
        } catch (UnsupportedOperationException e2) {
            e2.printStackTrace();
        }
        initializeRemote(this.mAppSettings.getRemote());
        changeLocale(this);
        new TemplateLoader((Context) this).parse();
        Keychain.loadCipherLibs(this);
        this.subscribers = new ArrayList();
        this.mMutableContext = new MutableContextWrapper(this);
        setLauncherList();
        initializeEnabledInputMethodsList();
        getAppSettings().setAttachmentSyncSlowMsgFisrtTime(true);
        if (getAppSettings().isPremiumVersion() && getAppSettings().getSignInAsTeamTokenValidPref()) {
            new SignInAsTeamTokenVerifier().verifyToken();
        }
        File f = new File("/data/data/io.enpass.app/shared_prefs/data.xml");
        if (f.exists()) {
            f.delete();
        }
    }

    public synchronized Tracker getDefaultGoogleAnalyticsTracker() {
        boolean canSendAnalytics = getInstance().getAppSettings().sendAnalyticsPref();
        if (this.mTracker == null && canSendAnalytics) {
            this.mTracker = GoogleAnalytics.getInstance(this).newTracker(GOOGLE_ANALYTICS_PROPERTY_ID);
            this.mTracker.enableAdvertisingIdCollection(true);
        }
        return this.mTracker;
    }

    public void disposeGoogleAnalyticsTracker() {
        this.mTracker = null;
    }

    public boolean isFingerprintScannerAvailable() {
        return this.mSamsungFingerprintScannerAvailable;
    }

    public String getFingerPrintScannerType() {
        String fingerprintScannerType = BuildConfig.FLAVOR;
        if (isGoogleFingerprintHardwareAvailable()) {
            return FINGERPRINT_TYPE_ANDROID;
        }
        if (isFingerprintScannerAvailable()) {
            return FINGERPRINT_TYPE_SAMSUNG;
        }
        return fingerprintScannerType;
    }

    public SpassFingerprint getSpassFingerprint() {
        return this.mSpassFingerprint;
    }

    public void onForeignActivityLaunched(Activity activity) {
        this.mInactiveSince = new Date();
    }

    public AppSettings getAppSettings() {
        return this.mAppSettings;
    }

    public static DropboxRemote getDropboxRemote() {
        if (dropboxInstance == null) {
            dropboxInstance = new DropboxRemote(mInstance);
        }
        return dropboxInstance;
    }

    public static GoogleDriveRemote getGoogleDriveRemote() {
        if (googleDriveInstance == null) {
            googleDriveInstance = new GoogleDriveRemote(mInstance);
        }
        return googleDriveInstance;
    }

    public static OneDriveRemote getOneDriveRemote() {
        if (oneDriveInstance == null) {
            oneDriveInstance = new OneDriveRemote(mInstance);
        }
        return oneDriveInstance;
    }

    public static BoxRemote getBoxRemote() {
        if (boxRemoteInstance == null) {
            boxRemoteInstance = new BoxRemote(mInstance);
        }
        return boxRemoteInstance;
    }

    public static void setBoxRemote(BoxRemote remoteState) {
        boxRemoteInstance = remoteState;
    }

    public static WebDavRemote getWebDavRemote() {
        if (!Utils.dbExists(getInstance(), getInstance().mKeychainFilename) && webDavRemoteInstance == null) {
            webDavRemoteInstance = new WebDavRemote(mInstance);
        } else if (webDavRemoteInstance == null && getInstance().getKeychain() != null) {
            webDavRemoteInstance = new WebDavRemote(mInstance);
        }
        return webDavRemoteInstance;
    }

    public static FolderRemote getFolderRemote() {
        if (folderRemoteInstance == null) {
            folderRemoteInstance = new FolderRemote(mInstance);
        }
        return folderRemoteInstance;
    }

    public static WearConnectionRemote getWearConnectionRemote() {
        if (wearRemoteInstance == null) {
            wearRemoteInstance = new WearConnectionRemote(mInstance);
        }
        return wearRemoteInstance;
    }

    public void connectToWatch(boolean isConnected) {
        WearConnectionRemote remote = getWearConnectionRemote();
        if (isConnected) {
            remote.initializeConnection();
        } else {
            remote.disconnect();
        }
    }

    public IRemoteStorage getActiveRemote() {
        int activeRemoteIdentifier = getAppSettings().getRemote();
        if (activeRemoteIdentifier == 2) {
            return getDropboxRemote();
        }
        if (activeRemoteIdentifier == 4) {
            return getGoogleDriveRemote();
        }
        if (activeRemoteIdentifier == 5) {
            return getOneDriveRemote();
        }
        if (activeRemoteIdentifier == 6) {
            return getBoxRemote();
        }
        if (activeRemoteIdentifier == 9) {
            return getWebDavRemote();
        }
        if (activeRemoteIdentifier == 8) {
            return getFolderRemote();
        }
        return null;
    }

    public LoginScreenEnum getLoginScreen() {
        boolean fingerprintScanner = getAppSettings().getFingerprintScannerStatus();
        String fingerprintScannerType = getFingerPrintScannerType();
        if (fingerprintScanner && VERSION.SDK_INT >= 23 && fingerprintScannerType.equals(FINGERPRINT_TYPE_ANDROID)) {
            this.mLoginScreen = LoginScreenEnum.showFingerScanner;
        }
        return this.mLoginScreen;
    }

    public void setLoginScreen(LoginScreenEnum loginScreenEnum) {
        this.mLoginScreen = loginScreenEnum;
    }

    public void onActivityResume(Activity aActivity) {
        stopAppInBackgroundTimer();
        boolean isDbExist = isDbExist();
        String aSharedText = BuildConfig.FLAVOR;
        Uri aUri = getAppSettings().getSharedData();
        ClipboardManager clipBoard = (ClipboardManager) getSystemService("clipboard");
        if (clipBoard != null) {
            ClipData clipData = clipBoard.getPrimaryClip();
            if (clipData != null && aUri == null) {
                try {
                    aSharedText = clipData.getItemAt(clipData.getItemCount() - 1).getText().toString();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
        if (isDbExist) {
            if (isDbExist && getAppSettings().getRestorePwdNotMatchPref()) {
                startSecondLaunch(aActivity);
            } else if (this.mLoginActive || this.mForcelock) {
                showLoginWhenAppComeFromBackground(aActivity);
            } else if (this.mIsBackFromLogin != null) {
                lockEnpass(aActivity, this.mIsBackFromLogin);
                this.mIsBackFromLogin = null;
            } else if (this.mInactiveSince != null) {
                lockEnpass(aActivity, this.mInactiveSince);
            } else if (isDbExist && aSharedText.startsWith("enpass://share")) {
                Activity activity = aActivity;
                Uri data = Uri.parse(aSharedText);
                if (data != null) {
                    Card card = new Card();
                    if (card.decodeShareUrl(data)) {
                        Card sharedCardMeta = getKeychain().getCardWithUuid(card.getUuid());
                        if (sharedCardMeta != null && !sharedCardMeta.isTrashed()) {
                            addOrUpdateSharedCard(2, card, activity);
                        } else if (sharedCardMeta == null || !sharedCardMeta.isTrashed()) {
                            addOrUpdateSharedCard(1, card, activity);
                        } else {
                            addOrUpdateSharedCard(3, card, activity);
                        }
                        clipBoard.setPrimaryClip(ClipData.newPlainText(BuildConfig.FLAVOR, BuildConfig.FLAVOR));
                    } else {
                        showCardImportMessage(R.string.share_card_import_unsuccess, activity);
                        clipBoard.setPrimaryClip(ClipData.newPlainText(BuildConfig.FLAVOR, BuildConfig.FLAVOR));
                    }
                }
            }
            appAutoLocker(aActivity);
            this.mForcelock = false;
            this.mInactiveSince = null;
            this.mAppInBackgorund = false;
            this.mIsBackFromLogin = null;
            return;
        }
        this.isAppUnlocked = false;
        if (getAppSettings().getProductTourPref()) {
            startSecondLaunch(aActivity);
        } else {
            startFirstLaunch(aActivity);
        }
        this.mForcelock = false;
        this.mInactiveSince = null;
    }

    public boolean isDbExist() {
        return Utils.dbExists(this, this.mKeychainFilename);
    }

    private void lockEnpass(Activity aActivity, Date aInactiveSince) {
        long interval = (new Date().getTime() - aInactiveSince.getTime()) / 1000;
        this.mLoginScreen = this.mAutolocker.getLoginScreen(interval);
        if (interval > 2 && (aActivity instanceof NotificationFillActivity)) {
            aActivity.finish();
        }
        if (this.mLoginScreen != null) {
            showLoginWhenAppComeFromBackground(aActivity);
        } else {
            appInForegroundEvent();
        }
    }

    private void addOrUpdateSharedCard(final int value, final Card card, Activity activity) {
        int msgId = -1;
        int positiveBtnId = -1;
        boolean showAddAsNewBtn = false;
        if (value == 2) {
            msgId = R.string.share_existing_card_onclipboard;
            positiveBtnId = R.string.update;
            showAddAsNewBtn = true;
        } else if (value == 1) {
            msgId = R.string.share_new_card_onclipboard;
            positiveBtnId = R.string.add;
        } else if (value == 3) {
            msgId = R.string.share_new_card_onclipboard;
            positiveBtnId = R.string.add;
        }
        Builder builder = new Builder(activity);
        builder.setTitle(R.string.app_name);
        builder.setCancelable(false);
        builder.setMessage(String.format(getString(R.string.share_onClipboard) + " " + getString(msgId), new Object[]{card.getDisplayName()}));
        builder.setPositiveButton(positiveBtnId, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                EnpassApplication.this.addOrUpdateCard(value, card);
            }
        });
        if (showAddAsNewBtn) {
            builder.setNeutralButton(R.string.add_as_new, new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    boolean isPremiumversion = EnpassApplication.getInstance().getAppSettings().isPremiumVersion();
                    if (EnpassApplication.getInstance().getKeychain() != null) {
                        int count = EnpassApplication.getInstance().getKeychain().getCardsCount();
                        int maxLimit = EnpassApplication.getInstance().maxCardAllowed;
                        if (isPremiumversion || count < maxLimit) {
                            card.setUuid(Utils.generateUUID());
                            EnpassApplication.this.getKeychain().addCardNotified(card);
                        } else {
                            EnpassApplication.getInstance().maxCardAlert(EnpassApplication.this.getMainActivity(), String.format(EnpassApplication.this.getResources().getString(R.string.buyMsg), new Object[]{Integer.valueOf(EnpassApplication.getInstance().maxCardAllowed)}));
                        }
                        dialog.dismiss();
                    }
                }
            });
        }
        builder.setNegativeButton(R.string.cancel, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void addOrUpdateCard(int value, Card card) {
        boolean isPremiumversion;
        int count;
        int maxLimit;
        if (value == 1) {
            isPremiumversion = getInstance().getAppSettings().isPremiumVersion();
            if (getInstance().getKeychain() != null) {
                count = getInstance().getKeychain().getCardsCount();
                maxLimit = getInstance().maxCardAllowed;
                if (isPremiumversion || count < maxLimit) {
                    getKeychain().addCardNotified(card);
                    return;
                }
                getInstance().maxCardAlert(getMainActivity(), String.format(getResources().getString(R.string.buyMsg), new Object[]{Integer.valueOf(getInstance().maxCardAllowed)}));
            }
        } else if (value == 2) {
            getKeychain().updateCardNotified(card.syncWithCard(getKeychain().getCardWithUuid(card.getUuid())));
        } else if (value == 3) {
            isPremiumversion = getInstance().getAppSettings().isPremiumVersion();
            if (getInstance().getKeychain() != null) {
                count = getInstance().getKeychain().getCardsCount();
                maxLimit = getInstance().maxCardAllowed;
                if (isPremiumversion || count < maxLimit) {
                    getKeychain().updateCardNotifiedAfterShare(card);
                    return;
                }
                getInstance().maxCardAlert(getMainActivity(), String.format(getResources().getString(R.string.buyMsg), new Object[]{Integer.valueOf(getInstance().maxCardAllowed)}));
            }
        }
    }

    private void showCardImportMessage(int msgId, Activity activity) {
        Builder builder = new Builder(activity);
        builder.setMessage(msgId);
        builder.setNeutralButton(R.string.ok, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void onActivityPause(Activity aActivity) {
        if (Utils.dbExists(this, this.mKeychainFilename) && !this.mLoginActive) {
            this.mInactiveSince = new Date();
        }
        isApplicationBroughtToBackground();
    }

    public void goingTomultiWindowMode(boolean goingTOMultiWindowMode) {
        this.multiWindowMode = goingTOMultiWindowMode;
    }

    public boolean isAppGoingInMultiWindow() {
        return this.multiWindowMode;
    }

    public void appAutoLocker(final Activity activityContext) {
        boolean isDbExist = Utils.dbExists(this, this.mKeychainFilename);
        if (this.runnable != null) {
            this.mHandler.removeCallbacks(this.runnable);
        }
        if (!this.mLoginActive && isDbExist) {
            long timerTime = (long) this.RealAutolockIntervals[this.mAutolocker.getMinLockingTime()];
            this.runnable = new Runnable() {
                public void run() {
                    EnpassApplication.this.mAutolocker.appUnlocked(activityContext);
                    EnpassApplication.this.mHandler.removeCallbacks(EnpassApplication.this.runnable);
                }
            };
            this.mHandler.postDelayed(this.runnable, 1000 * timerTime);
        }
    }

    public void keyboardAutolocker() {
        boolean isDbExist = Utils.dbExists(this, this.mKeychainFilename);
        if (this.mKeyboardRunnable != null) {
            this.mKeyboardHandler.removeCallbacks(this.mKeyboardRunnable);
        }
        if (isDbExist) {
            long timerTime = (long) this.RealAutolockIntervals[this.mAutolocker.getMinLockingTime()];
            this.mKeyboardRunnable = new Runnable() {
                public void run() {
                    EnpassApplication.this.mAutolocker.keyboardUnlocked();
                    EnpassApplication.this.mKeyboardHandler.removeCallbacks(EnpassApplication.this.mKeyboardRunnable);
                }
            };
            this.mKeyboardHandler.postDelayed(this.mKeyboardRunnable, 1000 * timerTime);
        }
    }

    public void removeAutolockHandler() {
        if (this.runnable != null) {
            this.mHandler.removeCallbacks(this.runnable);
        }
        if (this.mAutolocker != null) {
            this.mAutolocker.clear();
        }
    }

    public void removeKeyboardAutolockHandler() {
        if (this.mKeyboardRunnable != null) {
            this.mKeyboardHandler.removeCallbacks(this.mKeyboardRunnable);
        }
        if (this.mAutolocker != null) {
            this.mAutolocker.clearKeyboardHandler();
        }
    }

    public void isApplicationBroughtToBackground() {
        final AppSettings appSettings = getAppSettings();
        this.mAppInBackgroundTimer = new Timer();
        this.mAppInBackgroundTimerTask = new TimerTask() {
            public void run() {
                EnpassApplication.this.mAppInBackgorund = true;
                if (!EnpassApplication.this.isConnectorServiceRunning()) {
                    EnpassApplication.this.removeAutolockHandler();
                    if (!appSettings.getQuickUnlock()) {
                        if ((!appSettings.getFingerprintScannerStatus() || !EnpassApplication.this.getFingerPrintScannerType().equals(EnpassApplication.FINGERPRINT_TYPE_SAMSUNG)) && appSettings != null && appSettings.getLockOnLeaving()) {
                            EnpassApplication.this.closeDbAfterSync();
                        }
                    }
                }
            }
        };
        this.mAppInBackgroundTimer.schedule(this.mAppInBackgroundTimerTask, 1500);
        if (!isConnectorServiceRunning() && !appSettings.getQuickUnlock()) {
            if (!appSettings.getFingerprintScannerStatus() || !getFingerPrintScannerType().equals(FINGERPRINT_TYPE_SAMSUNG)) {
                this.mCloseDbInBackgroundTimer = new Timer();
                this.mCloseDbInBackgroundTimerTask = new TimerTask() {
                    public void run() {
                        EnpassApplication.this.closeDbAfterSync();
                    }
                };
                this.mCloseDbInBackgroundTimer.schedule(this.mCloseDbInBackgroundTimerTask, 1000 * ((long) this.RealAutolockIntervals[getAppSettings().getAutoLockInterval()]));
            }
        }
    }

    public void stopAppInBackgroundTimer() {
        if (this.mAppInBackgroundTimerTask != null) {
            this.mAppInBackgroundTimerTask.cancel();
        }
        if (this.mAppInBackgroundTimer != null) {
            this.mAppInBackgroundTimer.cancel();
        }
        if (this.mCloseDbInBackgroundTimerTask != null) {
            this.mCloseDbInBackgroundTimerTask.cancel();
        }
        if (this.mCloseDbInBackgroundTimer != null) {
            this.mCloseDbInBackgroundTimer.cancel();
        }
        this.mAppInBackgorund = false;
    }

    public void touch() {
        this.waiter.touch();
        this.mKeyboardTimeTracker.touch();
    }

    private void startFirstLaunch(Context context) {
        context.startActivity(new Intent(context, ProductTour.class));
    }

    private void startSecondLaunch(Context context) {
        context.startActivity(new Intent(context, WelcomeActivity.class));
    }

    public void showLogin(Context aContext) {
        this.isAppUnlocked = false;
        this.mLoginActive = true;
        if (!(getAppSettings().getQuickUnlock() || ((getAppSettings().getFingerprintScannerStatus() && getFingerPrintScannerType().equals(FINGERPRINT_TYPE_SAMSUNG)) || isConnectorServiceRunning()))) {
            closeDbAfterSync();
        }
        Intent intent = new Intent(aContext, LoginActivity.class);
        intent.setFlags(67108864);
        aContext.startActivity(intent);
        if (isConnectorServiceRunning()) {
            try {
                JSONObject headerObj = new JSONObject();
                headerObj.put("header", "lock_status");
                JSONObject dataObj = new JSONObject();
                String screenStatus = BoxSharedLink.FIELD_PASSWORD;
                if (getAppSettings().getQuickUnlock()) {
                    screenStatus = "pin";
                }
                dataObj.put("screen_status", screenStatus);
                headerObj.put("data", dataObj);
                ChromeConnectorForegroundService.getSocketServer().sendEncryptMessageToConnectionHandler(headerObj.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void showLoginWhenAppComeFromBackground(Context aContext) {
        this.isAppUnlocked = false;
        this.mLoginActive = true;
        Intent intent = new Intent(aContext, LoginActivity.class);
        intent.setFlags(67108864);
        aContext.startActivity(intent);
        if (isConnectorServiceRunning()) {
            try {
                JSONObject headerObj = new JSONObject();
                headerObj.put("header", "lock_status");
                JSONObject dataObj = new JSONObject();
                String screenStatus = BoxSharedLink.FIELD_PASSWORD;
                if (getAppSettings().getQuickUnlock()) {
                    screenStatus = "pin";
                }
                dataObj.put("screen_status", screenStatus);
                headerObj.put("data", dataObj);
                ChromeConnectorForegroundService.getSocketServer().sendEncryptMessageToConnectionHandler(headerObj.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void loginCallFromExtension() {
        this.mForcelock = true;
        if (this.mAppInBackgorund) {
            this.mForcelock = true;
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    Intent intent = new Intent(EnpassApplication.this, MainActivity.class);
                    intent.setFlags(805306368);
                    EnpassApplication.this.startActivity(intent);
                }
            });
        }
    }

    public DBValidationResult unlock(char[] aPassword) {
        DBValidationResult[] result = new DBValidationResult[]{DBValidationResult.DBIsValid};
        boolean checkPassword = false;
        try {
            if (this.mKeychain == null) {
                this.mKeychain = EnpassDbValidator.openKeychain(this, this.mKeychainFilename, aPassword, result);
                if (this.mKeychain != null) {
                    this.mKeychain.setDelegate(this);
                    checkPassword = true;
                }
            } else if (this.mLoginScreen == LoginScreenEnum.showPin && getAppSettings().getQuickUnlock()) {
                if (Arrays.equals(aPassword, getPin())) {
                    result[0] = DBValidationResult.DBResultPasswordOk;
                    checkPassword = true;
                } else {
                    result[0] = DBValidationResult.DBResultPasswordMismatch;
                }
            } else if (this.mLoginScreen == LoginScreenEnum.showFingerScanner && getFingerPrintScannerType() == FINGERPRINT_TYPE_SAMSUNG) {
                result[0] = DBValidationResult.DBResultPasswordOk;
                checkPassword = true;
            } else if (new String((char[]) this.mKeychain.getPoolDataForRow(LOCAL_REMOTE)).equals(new String(aPassword))) {
                result[0] = DBValidationResult.DBResultPasswordOk;
                checkPassword = true;
            } else {
                result[0] = DBValidationResult.DBResultPasswordMismatch;
            }
            if (checkPassword) {
                this.mLoginActive = false;
                this.mForcelock = false;
                this.mInactiveSince = null;
                this.mAppInBackgorund = false;
                this.mIsBackFromLogin = null;
                this.closeDbAfterSync = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result[0];
    }

    public DBValidationResult unlockFromKeyboard(char[] aPassword, LoginScreenEnum mLoginScreen) {
        DBValidationResult[] result = new DBValidationResult[]{DBValidationResult.DBIsValid};
        try {
            if (this.mKeychain == null) {
                this.mKeychain = EnpassDbValidator.openKeychain(this, this.mKeychainFilename, aPassword, result);
                if (this.mKeychain != null) {
                    this.mKeychain.setDelegate(this);
                }
            } else if (mLoginScreen == LoginScreenEnum.showPin && getAppSettings().getQuickUnlock()) {
                if (Arrays.equals(aPassword, getPin())) {
                    result[0] = DBValidationResult.DBResultPasswordOk;
                } else {
                    result[0] = DBValidationResult.DBResultPasswordMismatch;
                }
            } else if (mLoginScreen == LoginScreenEnum.showFingerScanner && getFingerPrintScannerType() == FINGERPRINT_TYPE_SAMSUNG) {
                result[0] = DBValidationResult.DBResultPasswordOk;
            } else if (new String((char[]) this.mKeychain.getPoolDataForRow(LOCAL_REMOTE)).equals(new String(aPassword))) {
                result[0] = DBValidationResult.DBResultPasswordOk;
            } else {
                result[0] = DBValidationResult.DBResultPasswordMismatch;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result[0];
    }

    public boolean unlockFromChromeExtension(char[] aPassword) {
        boolean z = false;
        boolean checkPassword = false;
        try {
            this.mKeychain = EnpassDbValidator.openKeychain(this, this.mKeychainFilename, aPassword, new DBValidationResult[]{DBValidationResult.DBIsValid});
            if (this.mKeychain != null) {
                this.mKeychain.setDelegate(this);
                checkPassword = true;
                z = true;
            }
            if (checkPassword) {
                this.mLoginActive = false;
                this.mForcelock = false;
                this.mInactiveSince = null;
                this.mAppInBackgorund = false;
                this.mIsBackFromLogin = null;
                this.closeDbAfterSync = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return z;
    }

    public void startSyncFirstTime() {
        try {
            getMainActivity().runOnUiThread(new Runnable() {
                public void run() {
                    EnpassApplication.getSyncManagerInstance().scheduleSyncIn(0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startSyncKeyboardRequest() {
        try {
            getSyncManagerInstance().scheduleSyncFromKeyboard(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void keychainChanged(KeychainChangeType type, IDisplayItem item, String extra) {
        if (type == KeychainChangeType.KeychainCardAdded) {
            this.mFilterList.add(item);
            if (this.mKeychain != null) {
                boolean isPremiumversion = getInstance().getAppSettings().isPremiumVersion();
                int count = this.mKeychain.getCardsCount();
                if (!isPremiumversion && count % this.reminderAfterCards == 0 && count < this.maxCardAllowed && count > 0) {
                    showRemainingCardDialog(this.maxCardAllowed - count);
                }
            }
        } else if (type == KeychainChangeType.KeychainCardRemoved) {
            this.mFilterList.remove(item);
        } else if (type == KeychainChangeType.KeychainCardChanged) {
            for (IDisplayItem displayItem : this.mFilterList) {
                if (displayItem.getDisplayIdentifier().equals(item.getDisplayIdentifier())) {
                    this.mFilterList.remove(displayItem);
                    break;
                }
            }
            this.mFilterList.add(item);
        }
        notifyItemChanged(type, item, extra);
        getInstance().setDirty(true);
        getSyncManagerInstance().scheduleSyncIn(5);
        if (type == KeychainChangeType.KeychainCardAdded || type == KeychainChangeType.KeychainCardRemoved || type == KeychainChangeType.KeychainCardChanged) {
            watchItemChanged();
        } else if ((type == KeychainChangeType.KeychainFolderCardAdded || type == KeychainChangeType.KeychainFolderCardRemoved) && extra.equals(Keychain.WATCH_FOLDER_UUID)) {
            watchItemChanged();
        }
    }

    public void setDirty(boolean value) {
        Editor edit = getInstance().getSharedPreferences(APP_DIRTY_PREFERENCE, 0).edit();
        edit.putBoolean(APP_DIRTY_PREFERENCE, value);
        edit.commit();
        this.mDirty = value;
    }

    public void notifyItemChanged(KeychainChangeType type, IDisplayItem item, String extra) {
        for (IAppEventSubscriber subscriber : this.subscribers) {
            subscriber.ItemChanged(type, item, extra);
        }
    }

    public void notifyReload() {
        createFilterList();
        for (IAppEventSubscriber subscriber : this.subscribers) {
            subscriber.reload();
        }
    }

    public List<IDisplayItem> createFilterList() {
        if (getInstance().getKeychain() == null) {
            this.mFilterList = new ArrayList();
        } else {
            List<IDisplayItem> allcards = getInstance().getKeychain().getAllCards();
            if (allcards.size() > this.maxCardAllowed) {
                this.mFilterList = new ArrayList(allcards.subList(0, this.maxCardAllowed));
            } else {
                this.mFilterList = new ArrayList(allcards);
            }
        }
        return this.mFilterList;
    }

    public void createNewKeychain(char[] aPassword) {
        this.mKeychain = Keychain.openOrCreate(mInstance, this.mKeychainFilename, aPassword);
        if (this.mKeychain != null) {
            this.mKeychain.setDelegate(this);
            this.mLoginActive = false;
        }
    }

    public String getDbFileName() {
        return this.mKeychainFilename;
    }

    public void restoreDatabase(String aBackupFile) {
        try {
            if (this.mKeychain != null) {
                this.mKeychain.close();
                this.mKeychain = null;
            }
            Utils.copyFile(getDatabasePath(aBackupFile).getAbsolutePath(), this.mKeychainFilename);
            this.mForcelock = true;
            Intent intent = new Intent(this.mMainActivity, MainActivity.class);
            intent.setFlags(67108864);
            this.mMainActivity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPin(String pin) {
        try {
            this.mKeychain.setPoolDataForRow(PIN, pin.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public char[] getPin() {
        return (char[]) this.mKeychain.getPoolDataForRow(PIN);
    }

    public void setChromePin(String pin) {
        try {
            this.mKeychain.setPoolDataForRow(WIFIEXTENSION_PIN, pin.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void setWatchPin(String pin) {
        try {
            this.mKeychain.setPoolDataForRow(Keychain.ANDROID_WATCH_PIN_CODE, pin.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public byte[] getChromePin() {
        return (byte[]) this.mKeychain.getPoolDataForRow(WIFIEXTENSION_PIN);
    }

    public char[] getWatchPin() {
        return (char[]) this.mKeychain.getPoolDataForRow(Keychain.ANDROID_WATCH_PIN_CODE);
    }

    public void setKeychainPassword(char[] oldPassword, char[] newPassword) {
        getInstance().getKeychain().changePassword(this, newPassword);
    }

    public void addSubscriber(IAppEventSubscriber subscriber) {
        if (this.subscribers.indexOf(subscriber) == -1) {
            this.subscribers.add(subscriber);
        }
    }

    public void removeSubscriber(IAppEventSubscriber subscriber) {
        this.subscribers.remove(subscriber);
    }

    public List<IDisplayItem> filterCards(List<IDisplayItem> list) {
        if (getInstance().getAppSettings().isPremiumVersion()) {
            return list;
        }
        List<IDisplayItem> integratedList = new ArrayList();
        for (IDisplayItem itemInList : list) {
            for (IDisplayItem itemInFilterList : this.mFilterList) {
                if (itemInList.getDisplayIdentifier().equals(itemInFilterList.getDisplayIdentifier())) {
                    integratedList.add(itemInList);
                    break;
                }
            }
        }
        return integratedList;
    }

    public List<CardMeta> filterCardMeta(List<CardMeta> list) {
        if (getInstance().getAppSettings().isPremiumVersion()) {
            return list;
        }
        List<CardMeta> integratedList = new ArrayList();
        for (IDisplayItem itemInList : list) {
            for (IDisplayItem itemInFilterList : this.mFilterList) {
                if (itemInList.getDisplayIdentifier().equals(itemInFilterList.getDisplayIdentifier())) {
                    integratedList.add((CardMeta) itemInList);
                    break;
                }
            }
        }
        return integratedList;
    }

    public void appInForegroundEvent() {
        MainActivity activity = (MainActivity) getMainActivity();
        if (activity != null) {
            activity.appComeInForeground();
        }
    }

    public void maxCardAlert(final Context aContext, String msg) {
        Builder builder = new Builder(aContext);
        builder.setTitle(R.string.app_name);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.buy, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                aContext.startActivity(new Intent(aContext, AccountActivity.class));
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.later, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void clearClipboardTimer(long aSeconds) {
        if (this.mTimer != null) {
            this.mTimer.cancel();
            this.mTimer = null;
        }
        this.mTimer = new Timer();
        this.mTimer.schedule(new TimerTask() {
            public void run() {
                EnpassApplication.this.handleClearClipboardTimeout();
            }
        }, 1000 * aSeconds);
    }

    void handleClearClipboardTimeout() {
        new Handler(getMainLooper()).post(new Runnable() {
            public void run() {
                EnpassApplication.this.clearClipboard();
            }
        });
    }

    void clearClipboard() {
        ClipboardManager clipBoard = (ClipboardManager) getSystemService("clipboard");
        if (clipBoard.hasPrimaryClip()) {
            ClipData clipData = clipBoard.getPrimaryClip();
            if (clipData != null) {
                Item clipDataItem = clipData.getItemAt(clipData.getItemCount() - 1);
                if (clipDataItem.getText() != null && UIUtils.getHashCode(clipDataItem.getText().toString()) == this.mCopyHash) {
                    clipBoard.setPrimaryClip(ClipData.newPlainText(BuildConfig.FLAVOR, " "));
                }
            }
        }
    }

    public DBValidationResult isValidPassword(char[] aPassword) {
        return Keychain.isValidDatabase(this, this.mKeychainFilename, aPassword);
    }

    public void addTab(WebView browerView, Bitmap screenshot) {
        Tab tab = new Tab(this, browerView, screenshot);
        if (this.mTabs.size() <= 0) {
            this.mTabs.add(tab);
            return;
        }
        for (int i = 0; i < this.mTabs.size(); i++) {
            if (((Tab) this.mTabs.get(i)).getWebView() == browerView) {
                this.mTabSelected = true;
            }
        }
        if (this.mTabSelected) {
            this.mTabs.set(this.mTabId, tab);
            this.mTabSelected = false;
            return;
        }
        this.mTabs.add(tab);
        this.mTabSelected = false;
    }

    public void removeTab(int tabId) {
        if (this.mTabs.size() == 1) {
            this.mRemovedViews.add(((Tab) this.mTabs.get(tabId)).getWebView());
            this.mTabs.remove(tabId);
            this.mTabId = 0;
        } else if (this.mTabs.size() <= 1) {
        } else {
            if (this.mTabId == tabId) {
                this.mRemovedViews.add(((Tab) this.mTabs.get(tabId)).getWebView());
                this.mTabs.remove(tabId);
                this.mBrowserView = ((Tab) this.mTabs.get(this.mTabs.size() - 1)).getWebView();
                this.mTabId = this.mTabs.size() - 1;
            } else if (tabId < this.mTabId) {
                this.mRemovedViews.add(((Tab) this.mTabs.get(tabId)).getWebView());
                this.mTabs.remove(tabId);
                this.mBrowserView = ((Tab) this.mTabs.get(this.mTabId - 1)).getWebView();
                this.mTabId--;
            } else if (this.mTabId < tabId) {
                this.mRemovedViews.add(((Tab) this.mTabs.get(tabId)).getWebView());
                this.mTabs.remove(tabId);
            }
        }
    }

    public void deleteKeychain() {
        getInstance().getKeychain().close();
        this.mKeychain = null;
        Utils.delete(this.mKeychainFilename, this);
        notifyReload();
    }

    public Context changeLocale(Context context) {
        Configuration newConfig = context.getResources().getConfiguration();
        if (VERSION.SDK_INT >= 17) {
            newConfig.locale = getLocaleFromBeforeInitializationPref(context);
            return context.createConfigurationContext(newConfig);
        }
        overwriteConfigurationLocale(context, newConfig, getLocaleFromBeforeInitializationPref(context));
        return context;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public Locale getLocaleFromPref() {
        Locale locale = Locale.getDefault();
        String language = getInstance().getAppSettings().getLanguage();
        if (language.equals(mDeviceDefaultLanguage)) {
            return locale;
        }
        if (language.equals("zh_CN")) {
            return Locale.SIMPLIFIED_CHINESE;
        }
        if (language.equals("zh_TW")) {
            return Locale.TRADITIONAL_CHINESE;
        }
        locale = new Locale(language);
        Locale.setDefault(locale);
        return locale;
    }

    public void overwriteConfigurationLocale(Context context, Configuration config, Locale locale) {
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public static SyncManager getSyncManagerInstance() {
        if (sSyncManager == null) {
            sSyncManager = new SyncManager(getInstance());
        }
        return sSyncManager;
    }

    public boolean isDirty() {
        this.mDirty = getInstance().getSharedPreferences(APP_DIRTY_PREFERENCE, 0).getBoolean(APP_DIRTY_PREFERENCE, false);
        return this.mDirty;
    }

    public boolean isLatestUpload() {
        return this.mLatestUploaded;
    }

    public void setDelegate(IRemoteStorageDelegate aDelegate) {
        this.mRemoteStorageDelegate = aDelegate;
    }

    public void requestLatest() {
        this.mHaveLatest = true;
        if (this.mRemoteStorageDelegate != null) {
            this.mRemoteStorageDelegate.latestRequestDone(this);
        }
    }

    public void uploadLatest() {
        this.mLatestUploaded = false;
        char[] password = (char[]) getInstance().getKeychain().getPoolDataForRow(1);
        this.mKeychain.close();
        this.mKeychain = null;
        Utils.copyFile(getDatabasePath("walletx.db.sync").getAbsolutePath(), this.mKeychainFilename);
        this.mKeychain = Keychain.openOrCreate(this, this.mKeychainFilename, password);
        this.mKeychain.setDelegate(this);
        notifyReload();
        this.mLatestUploaded = true;
        setDirty(false);
        if (this.mRemoteStorageDelegate != null) {
            this.mRemoteStorageDelegate.uploadRequestDone(this);
        }
        if (getAppSettings().isWatchEnabled()) {
            watchItemChanged();
        }
    }

    public void setLatestUploaded(boolean value) {
        this.mLatestUploaded = value;
    }

    public String getLatestFile() {
        Utils.delete(this.mKeychainFilename.concat(".sync"), this);
        return Utils.copySyncDbFile(this.mKeychainFilename, this);
    }

    public void abort() {
    }

    public int getIdentifier() {
        return LOCAL_REMOTE;
    }

    public void clearState() {
        this.mLatestUploaded = false;
    }

    public String getFileName() {
        return "walletx.db";
    }

    public void setPasswordChangePending(boolean value) {
    }

    public boolean getPasswordChangePending() {
        return false;
    }

    public boolean isFileExistOnCloud() {
        return true;
    }

    void initializeRemote(int remoteId) {
        switch (remoteId) {
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                getDropboxRemote();
                return;
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                getGoogleDriveRemote();
                return;
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                getOneDriveRemote();
                return;
            case IRemoteStorage.BOX_REMOTE /*6*/:
                getBoxRemote();
                return;
            case IRemoteStorage.WEBDAV_REMOTE /*9*/:
                getWebDavRemote();
                return;
            default:
                return;
        }
    }

    public void showSyncTurnOnDialog() {
        if (!getMainActivity().isFinishing()) {
            boolean isRemoteActive = getAppSettings().isRemoteActive();
            boolean isShowDialog = getAppSettings().getIsShowSyncOnDialog();
            final Context aContext = getMainActivity();
            if (!isRemoteActive && isShowDialog) {
                Builder dialog = new Builder(getMainActivity());
                dialog.setTitle(R.string.app_name);
                dialog.setMessage(R.string.turn_sync_on_message);
                dialog.setPositiveButton(R.string.settings, new OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        aContext.startActivity(new Intent(aContext, SyncActivity.class));
                    }
                }).setNegativeButton(R.string.cancel, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.create();
                dialog.show();
                getAppSettings().setIsShowSyncOnDialog(false);
            }
        }
    }

    public void clearSyncRemoteInfo() {
        getSyncManagerInstance().scheduleSyncIn(9999);
        getSyncManagerInstance().clean();
        switch (getAppSettings().getRemote()) {
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                getDropboxRemote().clear();
                break;
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                getGoogleDriveRemote().clear();
                break;
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                getOneDriveRemote().clear();
                break;
            case IRemoteStorage.BOX_REMOTE /*6*/:
                getBoxRemote().clear();
                break;
            case IRemoteStorage.FOLDER_REMOTE /*8*/:
                getFolderRemote().clear();
                break;
            case IRemoteStorage.WEBDAV_REMOTE /*9*/:
                getWebDavRemote().clear();
                break;
        }
        getAppSettings().setRemoteActive(false);
        getAppSettings().setSigninId(BuildConfig.FLAVOR);
        getAppSettings().setRemote(-1);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(changeLocale(base));
        MultiDex.install(this);
    }

    public Locale getLocaleFromBeforeInitializationPref(Context context) {
        Locale locale = Locale.getDefault();
        String language = context.getSharedPreferences(LANGUAGE_PREFERENCE, 0).getString(LANGUAGE_PREFERENCE, mDeviceDefaultLanguage);
        if (language.equals(mDeviceDefaultLanguage)) {
            return locale;
        }
        if (language.equals("zh_CN")) {
            return Locale.SIMPLIFIED_CHINESE;
        }
        if (language.equals("zh_TW")) {
            return Locale.TRADITIONAL_CHINESE;
        }
        locale = new Locale(language);
        Locale.setDefault(locale);
        return locale;
    }

    private void showRemainingCardDialog(int remainingCards) {
        final Context aContext = getMainActivity();
        if (aContext != null && !getMainActivity().isFinishing()) {
            String remCardsMsg = String.format(aContext.getString(R.string.remaining_cards_alert_msg), new Object[]{Integer.valueOf(remainingCards), Integer.valueOf(this.maxCardAllowed)});
            Builder dialog = new Builder(aContext);
            dialog.setTitle(R.string.enpass_trial);
            dialog.setMessage(remCardsMsg);
            dialog.setPositiveButton(R.string.buy, new OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    aContext.startActivity(new Intent(aContext, AccountActivity.class));
                    dialog.dismiss();
                }
            }).setNegativeButton(R.string.later, new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.create();
            dialog.show();
        }
    }

    public boolean isCommonPassword(Context aContext, String password) {
        String[] aPasswordsList = getMostCommonPasswordList(aContext);
        for (Object equals : aPasswordsList) {
            if (password.equals(equals)) {
                return true;
            }
        }
        return false;
    }

    private String[] getMostCommonPasswordList(Context aContext) {
        String aPasswords = null;
        try {
            InputStream is = aContext.getAssets().open("MostCommonPasswords.txt");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            aPasswords = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return aPasswords.replace("\r", BuildConfig.FLAVOR).split("\n");
    }

    public KeyguardManager getKeyguardManager() {
        return (KeyguardManager) getSystemService("keyguard");
    }

    @SuppressLint({"NewApi"})
    public FingerprintManager getFingerprintManager() {
        if (VERSION.SDK_INT >= 23) {
            return (FingerprintManager) getSystemService("fingerprint");
        }
        return null;
    }

    @SuppressLint({"NewApi"})
    public boolean isGoogleFingerprintAuthAvailable() {
        if (VERSION.SDK_INT >= 23 && checkSelfPermission("android.permission.USE_FINGERPRINT") == 0 && getFingerprintManager() != null && getFingerprintManager().isHardwareDetected() && getFingerprintManager().hasEnrolledFingerprints()) {
            return true;
        }
        return false;
    }

    @SuppressLint({"NewApi"})
    public boolean isGoogleFingerprintHardwareAvailable() {
        if (VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.USE_FINGERPRINT") != 0 || getFingerprintManager() == null) {
            return false;
        }
        return getFingerprintManager().isHardwareDetected();
    }

    public void setFillingPackageName(String packageName) {
        this.mPackageNameForNotificationFill = packageName;
    }

    public String getFillingPackageName() {
        return this.mPackageNameForNotificationFill;
    }

    public void setCurrentVisiblePackageName(String packageName) {
        this.mCurrentVisiblePackageName = packageName;
    }

    public String getCurrentVisiblePackageName() {
        return this.mCurrentVisiblePackageName;
    }

    public ArrayList<String> getLauncherList() {
        return this.launcherList;
    }

    public void setLauncherList() {
        this.launcherList = null;
        this.launcherList = new ArrayList();
        PackageManager pm = getPackageManager();
        Intent i = new Intent("android.intent.action.MAIN");
        i.addCategory("android.intent.category.HOME");
        for (ResolveInfo resolveInfo : pm.queryIntentActivities(i, 0)) {
            this.launcherList.add(resolveInfo.activityInfo.packageName);
        }
    }

    public void initializeEnabledInputMethodsList() {
        this.enableKeyboardsList = null;
        this.enableKeyboardsList = new ArrayList();
        for (InputMethodInfo info : ((InputMethodManager) getApplicationContext().getSystemService("input_method")).getEnabledInputMethodList()) {
            this.enableKeyboardsList.add(info.getPackageName());
        }
    }

    public ArrayList<String> getEnabledInputMethodList() {
        this.enableKeyboardsList.remove("com.google.android.googlequicksearchbox");
        return this.enableKeyboardsList;
    }

    public void watchItemChanged() {
        if (!getAppSettings().isWatchEnabled()) {
            return;
        }
        if (getWearConnectionRemote().isWatchGoogleApiClientNull()) {
            getWearConnectionRemote().sendDataToWatch();
        } else {
            getWearConnectionRemote().initializeConnection();
        }
    }

    public void clearWatchData() {
        if (getAppSettings().isWatchEnabled()) {
            getWearConnectionRemote().sendClearDataSignalToWatch();
        }
        getAppSettings().setWatchEnabled(false);
        getAppSettings().setShowOnlyTotpInWatch(false);
        getAppSettings().setWatchPinEnabled(false);
        getInstance().getSharedPreferences(AppSettings.ANDROID_WATCH_PREFERENCE, 0).edit().clear().commit();
        getInstance().getSharedPreferences(AppSettings.SHOW_ONLY_TOTP_IN_WATCH, 0).edit().clear().commit();
        getInstance().getSharedPreferences(AppSettings.WATCH_PIN_ENABLED, 0).edit().clear().commit();
        getWearConnectionRemote().sendClearDataSignalToWatch();
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        for (RunningServiceInfo service : ((ActivityManager) getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public boolean isConnectorServiceRunning() {
        return isServiceRunning(ChromeConnectorForegroundService.class);
    }

    public boolean isRunningOnChromebook() {
        if (getPackageManager().hasSystemFeature("org.chromium.arc.device_management")) {
            return true;
        }
        return false;
    }

    public String getAddress() {
        String address = BuildConfig.FLAVOR;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                Enumeration<InetAddress> addresses = ((NetworkInterface) interfaces.nextElement()).getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress currentAddress = (InetAddress) addresses.nextElement();
                    if ((currentAddress instanceof Inet4Address) && !currentAddress.isLoopbackAddress()) {
                        address = currentAddress.getHostAddress();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }

    public void setValuesForExtensionUnlock() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("unlock_from_extension"));
    }

    public void checkSignInAsTeamTime() {
        if (getAppSettings().isPremiumVersion() && getAppSettings().getSignInAsTeamTokenValidPref()) {
            long savedTime = getAppSettings().getSignInAsTeamTimePref();
            if (savedTime > 0 && (((new Date().getTime() - savedTime) / 1000) / 60) / 60 > 11) {
                new SignInAsTeamTokenVerifier().verifyToken();
            }
        }
    }

    public boolean isTimeToShowLoginScreen() {
        boolean showLoginScreen = false;
        if (this.mLoginActive || this.mForcelock) {
            showLoginScreen = true;
        } else if (!(this.mInactiveSince == null || this.mAutolocker == null)) {
            this.mLoginScreen = this.mAutolocker.getLoginScreen((new Date().getTime() - this.mInactiveSince.getTime()) / 1000);
            showLoginScreen = this.mLoginScreen != null;
        }
        if (getInstance().getAppSettings().getLockOnLeaving()) {
            return true;
        }
        return showLoginScreen;
    }

    public void setEditActivityCardFields(Map<Integer, String> map) {
        this.fieldMap = map;
    }

    public Map<Integer, String> getEditActivityCardFields() {
        return this.fieldMap;
    }

    public boolean getCloseDbAfterSync() {
        return this.closeDbAfterSync;
    }

    public void setCloseDbAfterSync(boolean closeDb) {
        this.closeDbAfterSync = closeDb;
    }

    void closeDbAfterSync() {
        if (sSyncManager != null && !sSyncManager.isRunning() && getKeychain() != null) {
            getKeychain().close();
            this.mKeychain = null;
        } else if (getKeychain() != null) {
            setCloseDbAfterSync(true);
        }
    }

    void closeDbImmediately() {
        if (this.mKeychain != null) {
            getKeychain().close();
            this.mKeychain = null;
        }
    }
}
