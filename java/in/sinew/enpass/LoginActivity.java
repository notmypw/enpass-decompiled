package in.sinew.enpass;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.hardware.fingerprint.FingerprintManager.CryptoObject;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.github.clans.fab.BuildConfig;
import com.samsung.android.sdk.pass.SpassFingerprint.IdentifyListener;
import in.sinew.enpass.fingerprint.FingerprintAuthenticationDialogFragment;
import in.sinew.enpass.fingerprint.FingerprintAuthenticationDialogFragment.MODE;
import in.sinew.enpass.fingerprint.FingerprintAuthenticationDialogFragment.Stage;
import in.sinew.enpass.fingerprint.FingerprintKeyStoreHelper;
import in.sinew.enpass.locker.EnpassAutoLocker.LoginScreenEnum;
import in.sinew.enpass.utill.EnpassDbValidator.IDbValidationResult;
import in.sinew.enpass.utill.RootChecker;
import in.sinew.enpassengine.Card.DBValidationResult;
import in.sinew.enpassengine.Category;
import in.sinew.enpassengine.TemplateFactory;
import in.sinew.enpassengine.Utils;
import io.enpass.app.R;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity implements IDbValidationResult {
    public static String ADD_FOLDER_TO_DRAWERLIST_PREF = "FoldersInDrawer";
    public static final String DIALOG_FRAGMENT_TAG = "myFragment";
    public static int PIN_LIMIT = 4;
    public static final String SIDEBARLIST_PREF = "sidebarList";
    public static final int TAB_WIDTH = 600;
    public static String VISIBLE_CATEGORIES_IN_SIDEBAR = "visibleCategories";
    static boolean isAudioEnable = true;
    static MediaPlayer mPlayer;
    private final long APP_IN_BACKGROUND_TIMER_TIME_MS = 500;
    final EnpassApplication app = EnpassApplication.getInstance();
    private IdentifyListener listener = new 1(this);
    boolean mAppCanGoBack = false;
    private Timer mAppInBackgroundTimer;
    private TimerTask mAppInBackgroundTimerTask;
    FingerprintAuthenticationDialogFragment mFragment;
    boolean mIsOlderVersion = false;
    RelativeLayout mLockLayout;
    private View mLoginColorView;
    LoginScreenEnum mLoginScreen = LoginScreenEnum.showPassword;
    RelativeLayout mMainLayout;
    EditText mMasterPassword;
    TextView mMasterPasswordLabel;
    boolean mShowPinKeyboard = true;
    Button mUnlockButton;
    private BroadcastReceiver mUnlockFromExtenstionReceiver = new 7(this);
    ProgressBar mUnlockProgressBar;
    String mValidateOrUpgrade;
    Vibrator mVibrate;
    String mWrongPinOrPass;
    boolean upgradeRequired = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EnpassApplication.getInstance().getAppSettings().isScreenshotEnabledPref()) {
            getWindow().setFlags(8192, 8192);
        }
        setContentView(R.layout.new_login_activity);
        boolean rootedDeviceWarningAlreadyShown = EnpassApplication.getInstance().getAppSettings().isrootedDeviceWarningAlreadyShown();
        if (RootChecker.isDeviceRooted() && !rootedDeviceWarningAlreadyShown) {
            showRootedDeviceWarning();
        }
        this.mUnlockButton = (Button) findViewById(R.id.login_unlock_button);
        this.mUnlockProgressBar = (ProgressBar) findViewById(R.id.unlock_progress);
        this.mMasterPassword = (EditText) findViewById(R.id.new_login_master_password);
        this.mLoginColorView = findViewById(R.id.new_login_color_view);
        this.mMasterPasswordLabel = (TextView) findViewById(R.id.new_login_master_password_label);
        this.mLockLayout = (RelativeLayout) findViewById(R.id.lock_layout);
        this.mMainLayout = (RelativeLayout) findViewById(R.id.new_login_main_layout);
        this.mLoginColorView.setBackgroundResource(R.drawable.login_page_lock_bg);
        this.mMasterPassword.setImeOptions(268435462);
        this.mWrongPinOrPass = getString(R.string.login_fail);
        Configuration aConfiguration = getResources().getConfiguration();
        PIN_LIMIT = EnpassApplication.getInstance().getAppSettings().getPinCodeLength();
        if (aConfiguration.orientation != 2 || aConfiguration.smallestScreenWidthDp >= TAB_WIDTH) {
            this.mLockLayout.setVisibility(0);
        } else {
            this.mLockLayout.setVisibility(8);
            ((InputMethodManager) getSystemService("input_method")).showSoftInput(this.mMasterPassword, 2);
        }
        LoginScreenEnum aLoginScreen = this.app.getLoginScreen();
        if (aLoginScreen != null) {
            if (aLoginScreen == LoginScreenEnum.showFingerScanner && EnpassApplication.getInstance().getFingerPrintScannerType() == EnpassApplication.FINGERPRINT_TYPE_ANDROID && !FingerprintKeyStoreHelper.hasEncryptedFingerprintPref()) {
                this.mLoginScreen = LoginScreenEnum.showPassword;
                EnpassApplication.getInstance().getAppSettings().setFingerprintScannerStatus(false);
                showAlert(getString(R.string.fingerprint_disabled_for_old));
            } else {
                this.mLoginScreen = aLoginScreen;
            }
        }
        if (Utils.isDbTypeIsSqlCipher(EnpassApplication.getInstance().getDbFileName())) {
            this.mValidateOrUpgrade = getString(R.string.password_authenticating);
        } else {
            this.mValidateOrUpgrade = getString(R.string.dbOlder_text);
            this.mIsOlderVersion = true;
        }
        if (this.mLoginScreen == LoginScreenEnum.showPassword) {
            this.mMasterPassword.setInputType(129);
        } else if (this.mLoginScreen == LoginScreenEnum.showFingerScanner) {
            try {
                String fingerprintScannerType = EnpassApplication.getInstance().getFingerPrintScannerType();
                this.mMasterPassword.setHint(getString(R.string.scan_finger_print));
                this.mMasterPassword.setCursorVisible(false);
                this.mUnlockButton.setVisibility(4);
                if (fingerprintScannerType.equals(EnpassApplication.FINGERPRINT_TYPE_SAMSUNG) && EnpassApplication.getInstance().getSpassFingerprint().hasRegisteredFinger()) {
                    EnpassApplication.getInstance().getSpassFingerprint().startIdentifyWithDialog(this, this.listener, false);
                } else if (!fingerprintScannerType.equals(EnpassApplication.FINGERPRINT_TYPE_ANDROID)) {
                    EnpassApplication.getInstance().getAppSettings().setFingerprintScannerStatus(false);
                    this.mLoginScreen = LoginScreenEnum.showPassword;
                    this.mMasterPassword.setInputType(129);
                    this.mMasterPassword.setHint(getString(R.string.master_password));
                    this.mMasterPassword.setCursorVisible(true);
                    this.mUnlockButton.setVisibility(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, BuildConfig.FLAVOR + e.getMessage(), 1).show();
            }
        } else if (this.mLoginScreen == LoginScreenEnum.showPin) {
            this.mMasterPassword.setInputType(18);
            this.mWrongPinOrPass = getString(R.string.wrong_pin);
            this.mMasterPassword.setHint(getString(R.string.unlock_using_pin));
            this.mUnlockButton.setVisibility(8);
            this.mMasterPassword.setImeOptions(268435457);
        }
        this.mMainLayout.setOnClickListener(new 2(this));
        this.mMasterPassword.addTextChangedListener(new 3(this));
        this.mUnlockButton.setOnClickListener(new 4(this));
        this.mMasterPassword.setOnEditorActionListener(new 5(this));
        this.mVibrate = (Vibrator) getSystemService("vibrator");
        isAudioEnable = EnpassApplication.getInstance().getAppSettings().getAudioEnable();
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mUnlockFromExtenstionReceiver, new IntentFilter("unlock_from_extension"));
    }

    @SuppressLint({"NewApi"})
    void showAndroidFingerprint() {
        this.mFragment = new FingerprintAuthenticationDialogFragment();
        this.mFragment.setCancelable(false);
        FingerprintKeyStoreHelper.initialize(this);
        if (FingerprintKeyStoreHelper.initDecryptCipher()) {
            this.mFragment.setCryptoObject(new CryptoObject(FingerprintKeyStoreHelper.getmDecryptCipher()));
            this.mFragment.setStage(Stage.FINGERPRINT);
            this.mFragment.setMode(MODE.DECRYPT);
            this.mFragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
            return;
        }
        this.mFragment.setCryptoObject(new CryptoObject(FingerprintKeyStoreHelper.getmDecryptCipher()));
        this.mFragment.setStage(Stage.NEW_FINGERPRINT_ENROLLED);
        this.mFragment.setMode(MODE.DECRYPT);
        this.mFragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
    }

    void showAlert(String msg) {
        Builder alert = new Builder(this);
        alert.setMessage(msg).setNeutralButton(getString(R.string.ok), new 6(this));
        alert.show();
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void unlockEnpass(String passphrase) {
        getWindow().setSoftInputMode(3);
        this.mAppCanGoBack = true;
        this.mMasterPasswordLabel.setVisibility(0);
        this.mMasterPasswordLabel.setText(this.mValidateOrUpgrade);
        this.mMasterPassword.setVisibility(4);
        new UnlockAsyncTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{passphrase});
    }

    public void onUserInteraction() {
        super.onUserInteraction();
        EnpassApplication.getInstance().touch();
    }

    public void DBResult(DBValidationResult result) {
        if (result == DBValidationResult.DBIsOlder) {
            this.upgradeRequired = true;
        }
    }

    public void updateSidebarOfOlderVersion() {
        SharedPreferences addFolderToDrawerPref = EnpassApplication.getInstance().getSharedPreferences(ADD_FOLDER_TO_DRAWERLIST_PREF, 0);
        Set<String> foldersInList = new HashSet(addFolderToDrawerPref.getStringSet("foldersInDrawerList", new HashSet()));
        addFolderToDrawerPref.edit().clear().commit();
        SharedPreferences sidebarSharedPref = EnpassApplication.getInstance().getSharedPreferences(SIDEBARLIST_PREF, 0);
        Set<String> aOldVersionCat = new HashSet(sidebarSharedPref.getStringSet("sidebarlist", new HashSet()));
        sidebarSharedPref.edit().clear().commit();
        EnpassApplication.getInstance().getAppSettings().initializeDrawerList();
        EnpassApplication.getInstance().getAppSettings().setDrawerChange(true);
        Set<String> oldVisibleCategoriesList = new HashSet();
        List<Category> categoryList = TemplateFactory.getCategoryWithoutIdentity();
        for (int i = 0; i < categoryList.size(); i++) {
            if (!aOldVersionCat.contains(((Category) categoryList.get(i)).getDisplayIdentifier())) {
                oldVisibleCategoriesList.add(((Category) categoryList.get(i)).getDisplayIdentifier());
            }
        }
        Editor sidebarCategoriesPrefEditor = EnpassApplication.getInstance().getSharedPreferences(VISIBLE_CATEGORIES_IN_SIDEBAR, 0).edit();
        sidebarCategoriesPrefEditor.putStringSet("categoriesList", oldVisibleCategoriesList);
        sidebarCategoriesPrefEditor.commit();
        for (String folder : foldersInList) {
            EnpassApplication.getInstance().getAppSettings().addFolderInDrawer(folder);
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation != 2 || newConfig.smallestScreenWidthDp >= TAB_WIDTH) {
            this.mLockLayout.setVisibility(0);
        } else {
            this.mLockLayout.setVisibility(8);
        }
        EnpassApplication.getInstance().changeLocale(this);
    }

    protected void onResume() {
        super.onResume();
        String fingerprintScannerType = EnpassApplication.getInstance().getFingerPrintScannerType();
        if (this.mLoginScreen == LoginScreenEnum.showFingerScanner && fingerprintScannerType.equals(EnpassApplication.FINGERPRINT_TYPE_ANDROID) && EnpassApplication.getInstance().isGoogleFingerprintAuthAvailable() && getFragmentManager().findFragmentByTag(DIALOG_FRAGMENT_TAG) == null) {
            showAndroidFingerprint();
        }
    }

    protected void onPause() {
        super.onPause();
        Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_FRAGMENT_TAG);
        if (!(prev == null || !prev.isVisible() || this.mFragment == null)) {
            this.mFragment.dismiss();
        }
        if (this.mAppCanGoBack) {
            isApplicationBroughtToBackground();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.mAppInBackgroundTimerTask != null) {
            this.mAppInBackgroundTimerTask.cancel();
        }
        if (this.mAppInBackgroundTimer != null) {
            this.mAppInBackgroundTimer.cancel();
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mUnlockFromExtenstionReceiver);
    }

    public void isApplicationBroughtToBackground() {
        this.mAppInBackgroundTimer = new Timer();
        this.mAppInBackgroundTimerTask = new 8(this);
        this.mAppInBackgroundTimer.schedule(this.mAppInBackgroundTimerTask, 500);
    }

    public void wrongFingerPrint() {
        int aSystemVolume = ((AudioManager) getSystemService("audio")).getStreamVolume(1);
        this.mLoginScreen = LoginScreenEnum.showPassword;
        this.mLoginColorView.setBackgroundResource(R.drawable.login_page_lock_bg_unsucess);
        this.mMasterPassword.setImeOptions(268435462);
        if (isAudioEnable) {
            this.mVibrate.vibrate(500);
            mPlayer = MediaPlayer.create(this, 2131623940);
            if (mPlayer != null) {
                mPlayer.setVolume((float) aSystemVolume, (float) aSystemVolume);
                mPlayer.setOnCompletionListener(new 9(this));
                mPlayer.start();
                return;
            }
            return;
        }
        wrongPasswordOrPinResetLoginScreen();
    }

    private void wrongPasswordOrPinResetLoginScreen() {
        new Handler().postDelayed(new 10(this), 500);
    }

    protected void appUnlocked() {
    }

    void showRootedDeviceWarning() {
        Builder alert = new Builder(this);
        alert.setCancelable(true);
        alert.setTitle(getString(R.string.warning));
        alert.setMessage(getString(R.string.device_root_msg));
        alert.setNeutralButton(getString(R.string.ok), new 11(this)).show();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(EnpassApplication.getInstance().changeLocale(base));
    }
}
