package in.sinew.enpass.keyboard.enpasskeyboard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintManager.AuthenticationResult;
import android.hardware.fingerprint.FingerprintManager.CryptoObject;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.text.TextUtils.SimpleStringSplitter;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.github.clans.fab.BuildConfig;
import com.samsung.android.sdk.pass.SpassFingerprint;
import com.samsung.android.sdk.pass.SpassFingerprint.IdentifyListener;
import in.sinew.enpass.EnpassApplication;
import in.sinew.enpass.IRemoteStorage;
import in.sinew.enpass.ISyncManagerDelegate;
import in.sinew.enpass.LoginActivity;
import in.sinew.enpass.autofill.EnpassAccessibilityService;
import in.sinew.enpass.fingerprint.FingerprintKeyStoreHelper;
import in.sinew.enpass.fingerprint.FingerprintUiHelper;
import in.sinew.enpass.fingerprint.FingerprintUiHelper$Callback;
import in.sinew.enpass.fingerprint.FingerprintUiHelper.FingerprintUiHelperBuilder;
import in.sinew.enpass.keyboard.enpasskeyboard.adapter.MatchingCardsAdapter;
import in.sinew.enpass.locker.EnpassAutoLocker.LoginScreenEnum;
import in.sinew.enpass.totp.OtpProvider;
import in.sinew.enpass.totp.OtpSource;
import in.sinew.enpass.totp.OtpSourceException;
import in.sinew.enpass.totp.TotpClock;
import in.sinew.enpass.totp.TotpCountdownTask;
import in.sinew.enpass.totp.TotpCounter;
import in.sinew.enpass.totp.Utilities;
import in.sinew.enpassengine.Card;
import in.sinew.enpassengine.CardField;
import in.sinew.enpassengine.CardField.CardFieldType;
import in.sinew.enpassengine.IAppEventSubscriber;
import in.sinew.enpassengine.IDisplayItem;
import in.sinew.enpassengine.IKeychainDelegate.KeychainChangeType;
import in.sinew.enpassengine.Keychain;
import in.sinew.enpassengine.TemplateFactory;
import io.enpass.app.R;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.sqlcipher.database.SQLiteDatabase;

public class EnpassInputMethodService extends InputMethodService implements OnKeyboardActionListener, ISyncManagerDelegate, FingerprintUiHelper$Callback, LatinKeyboardView$OnKeyboardLongPressListener, IAppEventSubscriber {
    static final boolean DEBUG = false;
    static final int IME_OPTION_LOCK = -99;
    static final boolean PROCESS_HARD_KEYS = true;
    static final String TAG = "EnpassInputMethodSer";
    private static final long TOTP_COUNTDOWN_REFRESH_PERIOD = 1000;
    static boolean isLoginAudioEnable = PROCESS_HARD_KEYS;
    private static EnpassInputMethodService mInstance;
    static MediaPlayer mLoginPlayer;
    private final int DEEP_SEARCH = 1;
    private final int TITLE_SEARCH = 0;
    List<String> allFieldsLabels;
    List<String> allFieldsValues;
    private Button btnCancelFingerprint;
    ImageView closeSuggestions;
    private IdentifyListener listener = new 14(this);
    LinearLayout ll;
    Key longPressedKey;
    private boolean mAppUnlocked = DEBUG;
    private boolean mAutoComplete;
    private boolean mAutoCorrectOn;
    TextView mAutofillHint;
    private CandidateView mCandidateView;
    private boolean mCapsLock;
    ListView mCardsListView;
    ImageButton mClearSearchButton;
    ImageButton mCloseButton;
    private LatinKeyboard mCurKeyboard;
    Locale mCurrentLocale;
    SUGGESTIONS_TYPE mCurrentSuggestionType;
    private LatinKeyboard mDateTimeKeyboard;
    private LatinKeyboard mDefaultKeyboard;
    String mDomain;
    EditorInfo mEditorInfo;
    TextView mEmptyView;
    private ImageView mEnpassLoginKey;
    View mExtractView;
    RelativeLayout mFillingLockLayout;
    ImageView mFillingLockView;
    private FingerprintUiHelper mFingerprintUiHelper;
    FingerprintUiHelperBuilder mFingerprintUiHelperBuilder;
    Handler mHandler = new Handler();
    private List<View> mHiddenViews;
    private InputMethodManager mInputMethodManager;
    private LatinKeyboardView mInputView;
    private AudioManager mKeyAudioManager;
    private Date mKeyboardInactiveSince = null;
    private int mLastDisplayWidth;
    private long mLastShiftTime;
    View mLockPanel;
    private View mLoginColorView;
    LoginScreenEnum mLoginScreen = LoginScreenEnum.showPassword;
    private BroadcastReceiver mMessageReceiver = new 13(this);
    private long mMetaState;
    private LatinKeyboard mNumericKeyboard;
    private OtpSource mOtpProvider;
    String mPackageName;
    private EditText mPasswordEditText;
    private LatinKeyboard mQwertyKeyboard;
    Runnable mRunnable;
    CardView mSearchBar;
    EditText mSearchField;
    int mSearchInCardsVal;
    IDisplayItem mSelectedItem;
    private boolean mSilentMode;
    private boolean mSoundOn;
    State mState = State.START;
    private LatinKeyboard mSymbolsKeyboard;
    private LatinKeyboard mSymbolsShiftedKeyboard;
    ImageView mSyncError;
    ProgressBar mSyncProgressBar;
    String mTOTPGeneratedValue;
    int mTOTPPos = -1;
    View mToobarView;
    private TotpClock mTotpClock;
    private TotpCountdownTask mTotpCountdownTask;
    private TotpCounter mTotpCounter;
    private boolean mVibrationOn;
    private Vibrator mVibrator;
    ViewFlipper mViewFlipper;
    private String mWordSeparators;
    Map<String, List<String>> popupCharMap;
    final String service = "io.enpass.app/in.sinew.enpass.autofill.EnpassAccessibilityService";

    public View onCreateInputView() {
        this.ll = (LinearLayout) getLayoutInflater().inflate(R.layout.filling_keyboard, null);
        this.mInputView = (LatinKeyboardView) this.ll.findViewById(R.id.view_keyboard);
        this.mInputView.setOnKeyboardActionListener(this);
        this.mInputView.setOnKeyboardLongPressListener(this);
        this.allFieldsLabels = new ArrayList();
        this.allFieldsValues = new ArrayList();
        this.popupCharMap = new HashMap();
        initializePopupMap();
        return this.ll;
    }

    private void loadExtractedView() {
        if (this.mExtractView == null) {
            this.mExtractView = getLayoutInflater().inflate(R.layout.filling_extract_view, null);
            this.mToobarView = this.mExtractView.findViewById(R.id.filling_toolbar);
            this.mSearchField = (EditText) this.mToobarView.findViewById(R.id.search_field);
            this.mSearchBar = (CardView) this.mToobarView.findViewById(R.id.filling_search_bar);
            this.mSearchBar.setVisibility(8);
            this.mClearSearchButton = (ImageButton) this.mToobarView.findViewById(R.id.clear_button);
            this.mClearSearchButton.setVisibility(8);
            this.mViewFlipper = (ViewFlipper) this.mExtractView.findViewById(R.id.view_flipper);
            this.mViewFlipper.setVisibility(8);
            this.mEmptyView = (TextView) this.mViewFlipper.findViewById(R.id.filling_empty_view);
            this.mCardsListView = (ListView) this.mViewFlipper.findViewById(R.id.list_view);
            this.mLockPanel = this.mExtractView.findViewById(R.id.lock_panel);
            this.mFillingLockLayout = (RelativeLayout) this.mLockPanel.findViewById(R.id.filling_lock_layout);
            this.mFillingLockView = (ImageView) this.mFillingLockLayout.findViewById(R.id.filling_finger_print);
            Configuration aConfiguration = getResources().getConfiguration();
            if (aConfiguration.orientation != 2 || aConfiguration.smallestScreenWidthDp >= LoginActivity.TAB_WIDTH) {
                if (this.mFillingLockLayout != null) {
                    this.mFillingLockLayout.setVisibility(0);
                }
            } else if (this.mFillingLockLayout != null) {
                this.mFillingLockLayout.setVisibility(8);
            }
            this.mLoginColorView = this.mLockPanel.findViewById(R.id.filling_color_view);
            this.mPasswordEditText = (EditText) this.mLockPanel.findViewById(R.id.filling_master_password);
            this.btnCancelFingerprint = (Button) this.mLockPanel.findViewById(R.id.btn_cancel_fingerprint);
            this.btnCancelFingerprint.setOnClickListener(new 1(this));
            this.mPasswordEditText.addTextChangedListener(new 2(this));
            this.mCloseButton = (ImageButton) this.mToobarView.findViewById(R.id.close_button);
            this.mCloseButton.setOnClickListener(new 3(this));
            this.mSyncProgressBar = (ProgressBar) this.mToobarView.findViewById(R.id.filling_sync_progress);
            this.mSyncError = (ImageView) this.mToobarView.findViewById(R.id.filling_sync_passwordmismatch);
            this.mClearSearchButton.setOnClickListener(new 4(this));
            ((LinearLayout) this.mToobarView.findViewById(R.id.filling_search_bar_layout)).setOnClickListener(new 5(this));
            this.mSearchField.setOnTouchListener(new 6(this));
            this.mSearchField.addTextChangedListener(new 7(this));
        }
    }

    public void showSearchView() {
        this.mState = State.SEARCH;
        showQwertyKeyboard();
        setCandidatesViewShown(PROCESS_HARD_KEYS);
        this.mInputView.setVisibility(0);
    }

    @SuppressLint({"NewApi"})
    public void showLoginScreen(LoginScreenEnum loginScreen) {
        this.mLoginScreen = loginScreen;
        this.mState = State.LOCK;
        this.mAppUnlocked = DEBUG;
        this.mToobarView.setVisibility(0);
        this.mToobarView.setBackgroundResource(2131099794);
        this.mLockPanel.setVisibility(0);
        setExtractViewShown(PROCESS_HARD_KEYS);
        this.mExtractView.setVisibility(0);
        setCandidatesViewShown(PROCESS_HARD_KEYS);
        this.mInputView.setVisibility(0);
        this.mLockPanel.setVisibility(0);
        this.mSearchBar.setVisibility(8);
        this.mSyncProgressBar.setVisibility(8);
        this.mSyncError.setVisibility(8);
        String fingerprintScannerType = EnpassApplication.getInstance().getFingerPrintScannerType();
        if (this.mLoginScreen == LoginScreenEnum.showPassword) {
            this.mPasswordEditText.setHintTextColor(getResources().getColor(2131099732));
            this.mPasswordEditText.setHint(getString(R.string.filling_enter_master_password));
            this.mPasswordEditText.setInputType(129);
            this.mFillingLockView.setVisibility(8);
            showQwertyKeyboard();
        } else if (this.mLoginScreen == LoginScreenEnum.showFingerScanner) {
            try {
                this.mPasswordEditText.setInputType(1);
                this.mPasswordEditText.setHintTextColor(getResources().getColor(2131099794));
                this.mPasswordEditText.setHint(getString(R.string.use_fingerprint_for_unlock_autofill));
                this.mPasswordEditText.setCursorVisible(DEBUG);
                this.btnCancelFingerprint.setVisibility(0);
                if (fingerprintScannerType.equals(EnpassApplication.FINGERPRINT_TYPE_ANDROID) && EnpassApplication.getInstance().isGoogleFingerprintAuthAvailable()) {
                    FingerprintKeyStoreHelper.initialize(this);
                    if (FingerprintKeyStoreHelper.initDecryptCipher()) {
                        this.mFillingLockView.setVisibility(0);
                        FingerprintManager fm = EnpassApplication.getInstance().getFingerprintManager();
                        if (fm != null) {
                            this.mFingerprintUiHelperBuilder = new FingerprintUiHelperBuilder(fm);
                            this.mFingerprintUiHelper = this.mFingerprintUiHelperBuilder.build(this.mFillingLockView, this.mPasswordEditText, getResources().getString(R.string.use_fingerprint_for_unlock_autofill), this);
                            this.mFingerprintUiHelper.startListening(new CryptoObject(FingerprintKeyStoreHelper.getmDecryptCipher()));
                            return;
                        }
                        return;
                    }
                    EnpassApplication.getInstance().getAppSettings().setFingerprintScannerStatus(DEBUG);
                    this.mLoginScreen = LoginScreenEnum.showPassword;
                    this.mPasswordEditText.setInputType(129);
                    this.mPasswordEditText.setHint(getString(R.string.master_password));
                    this.mPasswordEditText.setCursorVisible(PROCESS_HARD_KEYS);
                    this.mFillingLockView.setVisibility(8);
                } else if (fingerprintScannerType.equals(EnpassApplication.FINGERPRINT_TYPE_SAMSUNG) && EnpassApplication.getInstance().getSpassFingerprint().hasRegisteredFinger()) {
                    this.mFillingLockView.setVisibility(0);
                    EnpassApplication.getInstance().getSpassFingerprint().startIdentify(this.listener);
                } else {
                    EnpassApplication.getInstance().getAppSettings().setFingerprintScannerStatus(DEBUG);
                    this.mLoginScreen = LoginScreenEnum.showPassword;
                    this.mPasswordEditText.setInputType(129);
                    this.mPasswordEditText.setHint(getString(R.string.master_password));
                    this.mPasswordEditText.setCursorVisible(PROCESS_HARD_KEYS);
                    this.mFillingLockView.setVisibility(8);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, BuildConfig.FLAVOR + e.getMessage(), 1).show();
            }
        } else if (this.mLoginScreen == LoginScreenEnum.showPin) {
            this.mPasswordEditText.setHintTextColor(getResources().getColor(2131099732));
            this.mPasswordEditText.setHint(getString(R.string.filling_enter_pin));
            this.mPasswordEditText.setInputType(18);
            this.mFillingLockView.setVisibility(8);
            showNumericKeyboard();
        }
    }

    void resetKeyboard() {
        if (this.mAppUnlocked) {
            this.mKeyboardInactiveSince = new Date();
            this.mAppUnlocked = DEBUG;
        }
        if (this.mLoginScreen == LoginScreenEnum.showFingerScanner && EnpassApplication.getInstance().getFingerPrintScannerType().equals(EnpassApplication.FINGERPRINT_TYPE_ANDROID) && this.mFingerprintUiHelper != null) {
            this.mFingerprintUiHelper.stopListening();
        }
        this.mState = State.START;
        if (this.mExtractView != null) {
            this.mToobarView.setVisibility(8);
            this.mViewFlipper.setVisibility(8);
            this.mExtractView.setVisibility(8);
            this.mCloseButton.setVisibility(0);
            this.mSearchField.setText(BuildConfig.FLAVOR);
            this.mSearchBar.setVisibility(8);
            this.mLoginColorView.setBackgroundResource(R.drawable.login_page_lock_bg);
            this.mPasswordEditText.setText(BuildConfig.FLAVOR);
            if (this.mEnpassLoginKey != null) {
                this.mEnpassLoginKey.setVisibility(0);
            }
            if (this.mAutofillHint != null) {
                this.mAutofillHint.setVisibility(0);
            }
        }
        if (this.mInputView != null) {
            this.mInputView.setVisibility(0);
        }
        setExtractViewShown(DEBUG);
        if (this.mDefaultKeyboard != null) {
            this.mCurKeyboard = this.mDefaultKeyboard;
            this.mDefaultKeyboard = null;
        }
        updateInputViewShown();
        updateFullscreenMode();
        updateImeOption(this.mCurKeyboard);
        setLatinKeyboard(this.mCurKeyboard);
        EnpassApplication.getSyncManagerInstance().removeSyncDelegate(getInstance());
        EnpassApplication.getInstance().removeSubscriber(getInstance());
        setSuggestions(null, DEBUG, DEBUG, SUGGESTIONS_TYPE.EMPTY);
    }

    private void handleLockExtandedViewButton() {
        try {
            if (EnpassApplication.getInstance().isDbExist()) {
                updateFullscreenMode();
                if (this.mKeyboardInactiveSince == null) {
                    this.mLoginScreen = EnpassApplication.getInstance().getLoginScreen();
                    showLoginScreen(this.mLoginScreen);
                    return;
                }
                long interval = (new Date().getTime() - this.mKeyboardInactiveSince.getTime()) / TOTP_COUNTDOWN_REFRESH_PERIOD;
                if (interval < 0) {
                    this.mLoginScreen = EnpassApplication.getInstance().getLoginScreen();
                    showLoginScreen(this.mLoginScreen);
                    return;
                }
                this.mLoginScreen = EnpassApplication.getInstance().getEnpassAutoLocker().getLoginScreen(interval);
                if (this.mLoginScreen != null) {
                    showLoginScreen(this.mLoginScreen);
                    return;
                }
                this.mState = State.LOGINS;
                this.mSearchBar.setVisibility(0);
                this.mAppUnlocked = PROCESS_HARD_KEYS;
                if (isBrowserDetected()) {
                    this.mDomain = getDomainFromBrowserUrl();
                } else {
                    int i = nthOccurrence(this.mPackageName, '.', 2);
                    if (i != -1) {
                        this.mPackageName = this.mPackageName.substring(0, i);
                    }
                    String[] domainAry = this.mPackageName.split("\\.");
                    this.mDomain = domainAry[1] + "." + domainAry[0];
                }
                showLogins();
                return;
            }
            Toast.makeText(this, getString(R.string.filling_db_not_exist), 1).show();
            this.mState = State.START;
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.autofill_not_available_NPE) + -201, 1).show();
            Toast.makeText(this, BuildConfig.FLAVOR + e.getMessage(), 1).show();
        }
    }

    public void unlockEnpass(String passphrase) {
        String validating = getString(R.string.password_authenticating);
        this.mPasswordEditText.setText(BuildConfig.FLAVOR);
        this.mPasswordEditText.setHint(validating);
        this.mState = State.PASSWORD_VALIDATING;
        new UnlockAsyncTask(this, this, passphrase).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{passphrase});
    }

    private void showLogins() {
        EnpassApplication.getSyncManagerInstance().addSyncDelegate(getInstance());
        EnpassApplication.getInstance().addSubscriber(getInstance());
        EnpassApplication.getInstance().startSyncKeyboardRequest();
        this.mToobarView.setVisibility(0);
        this.mToobarView.setBackgroundResource(2131099794);
        this.mCardsListView.setVisibility(0);
        setExtractViewShown(PROCESS_HARD_KEYS);
        this.mExtractView.setVisibility(0);
        setCandidatesViewShown(DEBUG);
        this.mInputView.setVisibility(8);
        this.mLockPanel.setVisibility(8);
        this.mViewFlipper.setVisibility(0);
        getMatchingCards();
        updateFullscreenMode();
    }

    public void searchInCards(String searchText) {
        String aSearchText = searchText;
        EnpassApplication.getInstance().touch();
        this.mSearchInCardsVal = EnpassApplication.getInstance().getAppSettings().getSearchInCards();
        if (this.mSearchInCardsVal == 0) {
            searchInCards(searchText, DEBUG);
        } else if (this.mSearchInCardsVal == 1) {
            if (this.mRunnable != null) {
                this.mHandler.removeCallbacks(this.mRunnable);
            }
            this.mRunnable = new 8(this, aSearchText);
            this.mHandler.postDelayed(this.mRunnable, 500);
        }
    }

    private void searchInCards(String searchText, boolean enableDeepSearch) {
        boolean deepSearch = enableDeepSearch;
        if (searchText.equals(BuildConfig.FLAVOR)) {
            this.mEmptyView.setVisibility(8);
            this.mCardsListView.setVisibility(0);
            getMatchingCards();
            return;
        }
        ArrayList<IDisplayItem> matchingCardList = new ArrayList();
        List<IDisplayItem> filterList = EnpassApplication.getInstance().filterCards(EnpassApplication.getInstance().getKeychain().getAllCards());
        for (int i = 0; i < filterList.size(); i++) {
            if (((IDisplayItem) filterList.get(i)).getDisplayName().toLowerCase(this.mCurrentLocale).contains(searchText.toLowerCase(this.mCurrentLocale))) {
                matchingCardList.add(filterList.get(i));
            }
        }
        if (deepSearch) {
            matchingCardList.clear();
            Keychain keychain = EnpassApplication.getInstance().getKeychain();
            for (IDisplayItem item : filterList) {
                boolean matchFound = DEBUG;
                Card c = keychain.getCardWithUuid(item.getDisplayIdentifier());
                if (c.foundText(searchText, this.mCurrentLocale)) {
                    matchFound = PROCESS_HARD_KEYS;
                }
                if (matchFound) {
                    matchingCardList.add(keychain.getCardMetaForIdentifier(c.getDisplayIdentifier()));
                }
            }
        }
        if (matchingCardList.size() > 0) {
            this.mEmptyView.setVisibility(8);
            this.mCardsListView.setVisibility(0);
            updateLoginList(matchingCardList);
            return;
        }
        this.mCardsListView.setVisibility(4);
        this.mEmptyView.setText(getString(R.string.no_card));
        this.mEmptyView.setVisibility(0);
    }

    private void fillUsername() {
        Card card = EnpassApplication.getInstance().getKeychain().getCardWithUuid(this.mSelectedItem.getDisplayIdentifier());
        List<CardField> fields = card.getFields();
        StringBuilder userName = new StringBuilder(BuildConfig.FLAVOR);
        for (CardField f : fields) {
            if (!f.getType().equals(Card.mCardFieldTypeMap.get(CardFieldType.CardFieldTypeUsername)) || f.isDeleted() || TextUtils.isEmpty(f.getValue())) {
                if (userName.length() == 0 && f.getType().equals(Card.mCardFieldTypeMap.get(CardFieldType.CardFieldTypeEmail)) && !f.isDeleted() && !TextUtils.isEmpty(f.getValue())) {
                    userName = f.getValue();
                    break;
                }
            } else {
                userName = f.getValue();
                break;
            }
        }
        getCurrentInputConnection().deleteSurroundingText(100, 100);
        boolean isSuccess = getCurrentInputConnection().commitText(userName, userName.length());
        boolean endEdit = getCurrentInputConnection().endBatchEdit();
        card.wipe();
    }

    private void fillPassword() {
        Card card = EnpassApplication.getInstance().getKeychain().getCardWithUuid(this.mSelectedItem.getDisplayIdentifier());
        List<CardField> fields = card.getFields();
        StringBuilder password = new StringBuilder(BuildConfig.FLAVOR);
        for (CardField f : fields) {
            if (f.getType().equals(Card.mCardFieldTypeMap.get(CardFieldType.CardFieldTypePassword)) && !f.isDeleted() && TextUtils.isEmpty(password)) {
                password = f.getValue();
                break;
            }
        }
        getCurrentInputConnection().deleteSurroundingText(100, 100);
        boolean isSuccess = getCurrentInputConnection().commitText(password, password.length());
        boolean endEdit = getCurrentInputConnection().endBatchEdit();
        card.wipe();
    }

    void tryAutoFilling() {
        EnpassAccessibilityService fillingAccessibilityService = EnpassAccessibilityService.getInstance();
        if (fillingAccessibilityService != null && fillingAccessibilityService.hasPasswordNode()) {
            Handler handler = new Handler();
            if (fillingAccessibilityService.hasUsernameNode()) {
                fillingAccessibilityService.focusUersnameNode();
                handler.postDelayed(new 9(this), 250);
            }
            handler.postDelayed(new 10(this, fillingAccessibilityService), 550);
            handler.postDelayed(new 11(this), 750);
        }
    }

    public void showFilling(List<IDisplayItem> cardList, int position) {
        this.mSelectedItem = (IDisplayItem) cardList.get(position);
        this.allFieldsValues.clear();
        this.allFieldsLabels.clear();
        Card currentCard = EnpassApplication.getInstance().getKeychain().getCardWithUuid(this.mSelectedItem.getDisplayIdentifier());
        Iterator it = currentCard.getFields().iterator();
        while (it.hasNext()) {
            CardField field = (CardField) it.next();
            if (!(field.isDeleted() || field.getType().equals(Card.mCardFieldTypeMap.get(CardFieldType.CardFieldTypeSeperator)))) {
                String label = field.getLabel();
                if (label.equals(BuildConfig.FLAVOR)) {
                    label = TemplateFactory.getLabelForUid(currentCard.getTemplateType(), field.getUid());
                }
                String itemValue = field.getValue().toString();
                if (!itemValue.equals(BuildConfig.FLAVOR)) {
                    this.allFieldsLabels.add(label);
                    this.allFieldsValues.add(itemValue);
                    if (field.getType().equals(Card.mCardFieldTypeMap.get(CardFieldType.CardFieldTypeTOTP))) {
                        this.mTOTPPos = this.allFieldsValues.size() - 1;
                        if (!(field.isDeleted() || TextUtils.isEmpty(field.getValue().toString()))) {
                            updateCodesAndStartTotpCountdownTask();
                        }
                    }
                }
            }
        }
        this.closeSuggestions.setVisibility(0);
        if (this.mEnpassLoginKey != null) {
            this.mEnpassLoginKey.setVisibility(4);
        }
        if (this.mAutofillHint != null) {
            this.mAutofillHint.setVisibility(8);
        }
        setSuggestions(this.allFieldsLabels, PROCESS_HARD_KEYS, PROCESS_HARD_KEYS, SUGGESTIONS_TYPE.ALLFIELDS);
        showFillingView();
        updateFullscreenMode();
        tryAutoFilling();
    }

    private void showFillingView() {
        this.mState = State.FILL;
        updateImeOption(this.mCurKeyboard);
        setCandidatesViewShown(PROCESS_HARD_KEYS);
        this.mInputView.setVisibility(0);
        this.mCardsListView.setVisibility(8);
        this.mViewFlipper.setVisibility(8);
        this.mExtractView.setVisibility(8);
        this.mSearchField.setText(BuildConfig.FLAVOR);
        setExtractViewShown(DEBUG);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                if (event.getRepeatCount() == 0 && this.mInputView != null && this.mInputView.handleBack()) {
                    return PROCESS_HARD_KEYS;
                }
            case 66:
                return DEBUG;
            default:
                if (keyCode == 62 && (event.getMetaState() & 2) != 0) {
                    InputConnection ic = getCurrentInputConnection();
                    if (ic != null) {
                        ic.clearMetaKeyStates(2);
                        keyDownUp(29);
                        keyDownUp(42);
                        keyDownUp(32);
                        keyDownUp(46);
                        keyDownUp(43);
                        keyDownUp(37);
                        keyDownUp(32);
                        return PROCESS_HARD_KEYS;
                    }
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    void updateImeOption(LatinKeyboard keyboard) {
        if (this.mState == State.LOCK) {
            keyboard.setImeOptions(getResources(), IME_OPTION_LOCK);
        } else if (this.mState == State.SEARCH) {
            keyboard.setImeOptions(getResources(), 3);
        } else if (getCurrentInputEditorInfo() != null) {
            keyboard.setImeOptions(getResources(), getCurrentInputEditorInfo().imeOptions);
        }
    }

    public void onAuthenticated(AuthenticationResult result) {
        unlockEnpass(FingerprintKeyStoreHelper.tryDecryptData());
        if (this.mFingerprintUiHelper != null) {
            this.mFingerprintUiHelper.stopListening();
        }
    }

    public void onAuthenticationFailed() {
    }

    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
    }

    public void onError() {
        this.mPasswordEditText.setTextColor(getResources().getColor(2131099794));
        this.mPasswordEditText.setText(getString(R.string.fingerprint_not_recognized));
    }

    public void onManyAttempts() {
        this.mFillingLockView.setVisibility(4);
        if (this.btnCancelFingerprint != null) {
            this.btnCancelFingerprint.setVisibility(4);
        }
        if (isLoginAudioEnable) {
            int aSystemVolume = ((AudioManager) getSystemService("audio")).getStreamVolume(1);
            this.mVibrator.vibrate(500);
            mLoginPlayer = MediaPlayer.create(this, 2131623940);
            if (mLoginPlayer != null) {
                mLoginPlayer.setVolume((float) aSystemVolume, (float) aSystemVolume);
                mLoginPlayer.setOnCompletionListener(new 12(this));
                mLoginPlayer.start();
                return;
            }
            return;
        }
        wrongPasswordOrPinResetLoginScreen();
    }

    boolean isBrowserDetected() {
        for (String pckg : getResources().getStringArray(2130903042)) {
            if (pckg.equalsIgnoreCase(this.mPackageName)) {
                return PROCESS_HARD_KEYS;
            }
        }
        return DEBUG;
    }

    String getDomainFromBrowserUrl() {
        EnpassAccessibilityService fillingAccessibilityService = EnpassAccessibilityService.getInstance();
        String domain = BuildConfig.FLAVOR;
        if (fillingAccessibilityService == null) {
            return domain;
        }
        if (fillingAccessibilityService.hasUrlNode()) {
            String url = fillingAccessibilityService.getUrlDomainOrHostname();
            if (url != null) {
                domain = url;
            }
        }
        return domain;
    }

    void getMatchingCards() {
        Keychain keychainObj = EnpassApplication.getInstance().getKeychain();
        List<IDisplayItem> cardList = new ArrayList();
        if (keychainObj != null) {
            List<String> matchingCardsUuidList = null;
            if (!EnpassApplication.getInstance().getAppSettings().isMatchHostnameEnabled() || !isBrowserDetected()) {
                matchingCardsUuidList = keychainObj.getAllCardsWithSameDomainName(this.mDomain);
            } else if (EnpassAccessibilityService.getInstance() != null) {
                Uri uri = EnpassAccessibilityService.getInstance().getUri();
                if (uri != null) {
                    matchingCardsUuidList = keychainObj.getAllCardsWithSameHostname(uri);
                }
            }
            if (matchingCardsUuidList != null) {
                for (String s : matchingCardsUuidList) {
                    cardList.add(keychainObj.getCardWithUuid(s));
                }
            }
            updateLoginList((ArrayList) EnpassApplication.getInstance().filterCards(cardList));
        }
    }

    void updateLoginList(List<IDisplayItem> cardList) {
        if (cardList != null) {
            this.mCardsListView.setAdapter(new MatchingCardsAdapter(this, cardList));
            if (cardList.size() <= 0) {
                String info = getString(R.string.no_card);
                if (!TextUtils.isEmpty(this.mDomain)) {
                    info = String.format(getString(R.string.no_login_found), new Object[]{this.mDomain});
                }
                this.mEmptyView.setText(info);
                this.mEmptyView.setVisibility(0);
                return;
            }
            this.mEmptyView.setVisibility(4);
        }
    }

    public void onCreate() {
        super.onCreate();
        this.mInputMethodManager = (InputMethodManager) getSystemService("input_method");
        this.mWordSeparators = getResources().getString(R.string.word_separators);
        this.mCurrentLocale = getResources().getConfiguration().locale;
        mInstance = this;
        this.mVibrator = (Vibrator) getSystemService("vibrator");
        isLoginAudioEnable = EnpassApplication.getInstance().getAppSettings().getAudioEnable();
        initializeAudioManager();
        updateRingerMode();
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mMessageReceiver, new IntentFilter("keyboard_pref_change"));
        loadSettings();
        this.mOtpProvider = getOtpProvider();
        this.mTotpCounter = this.mOtpProvider.getTotpCounter();
        this.mTotpClock = this.mOtpProvider.getTotpClock();
    }

    void initializeAudioManager() {
        this.mKeyAudioManager = (AudioManager) getSystemService("audio");
    }

    public void updateRingerMode() {
        this.mSilentMode = this.mKeyAudioManager.getRingerMode() != 2 ? PROCESS_HARD_KEYS : DEBUG;
    }

    private void loadSettings() {
        this.mSoundOn = EnpassApplication.getInstance().getAppSettings().isSoundON();
        this.mVibrationOn = EnpassApplication.getInstance().getAppSettings().isVibrationON();
        if (this.mSoundOn) {
            try {
                this.mKeyAudioManager.loadSoundEffects();
            } catch (SecurityException e) {
                e.printStackTrace();
                this.mSoundOn = DEBUG;
            }
        }
    }

    public static EnpassInputMethodService getInstance() {
        return mInstance;
    }

    public void onInitializeInterface() {
        if (this.mQwertyKeyboard != null) {
            int displayWidth = getMaxWidth();
            if (displayWidth != this.mLastDisplayWidth) {
                this.mLastDisplayWidth = displayWidth;
            } else {
                return;
            }
        }
        this.mQwertyKeyboard = new LatinKeyboard(this, 2131886100);
        this.mSymbolsKeyboard = new LatinKeyboard(this, 2131886101);
        this.mSymbolsShiftedKeyboard = new LatinKeyboard(this, 2131886102);
        this.mNumericKeyboard = new LatinKeyboard(this, 2131886098);
        this.mDateTimeKeyboard = new LatinKeyboard(this, 2131886096);
    }

    private void commitSoftString(String username, String pass, EditorInfo editorInfo) {
        int inputType = editorInfo.inputType;
        if (inputType == 144 || inputType == 128 || inputType == 16) {
            getCurrentInputConnection().commitText(pass, 0);
        }
        if ((editorInfo.imeOptions & 1073742079) == 5) {
            getCurrentInputConnection().performEditorAction(editorInfo.actionId);
        }
        if ((editorInfo.imeOptions & 1073742079) == 6) {
            getCurrentInputConnection().commitText(username, 0);
            getCurrentInputConnection().performEditorAction(editorInfo.actionId);
        }
    }

    public void setExtractView(View view) {
        if (this.mExtractView != null) {
            ViewParent parent = this.mExtractView.getParent();
            if (parent != null && (parent instanceof ViewGroup)) {
                ((ViewGroup) parent).removeView(this.mExtractView);
                this.mExtractView = null;
            }
        }
        loadExtractedView();
        LinearLayout layout = (LinearLayout) view;
        if (this.mHiddenViews == null) {
            this.mHiddenViews = new ArrayList();
        }
        this.mHiddenViews.clear();
        int i = 0;
        int j = layout.getChildCount();
        if (0 < j) {
            while (i < j) {
                View defaultView = layout.getChildAt(i);
                defaultView.setVisibility(8);
                this.mHiddenViews.add(defaultView);
                i++;
            }
        }
        layout.addView(this.mExtractView, new LayoutParams(-1, -1));
        super.setExtractView(layout);
    }

    private void setLatinKeyboard(LatinKeyboard nextKeyboard) {
        if (this.mInputView != null) {
            this.mInputView.setKeyboard(nextKeyboard);
        }
    }

    void hideUnwantedExtractedViews() {
        if (this.mHiddenViews != null) {
            for (View visibility : this.mHiddenViews) {
                visibility.setVisibility(8);
            }
        }
    }

    public View onCreateCandidatesView() {
        return getLayoutInflater().inflate(R.layout.candidates, null);
    }

    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
        this.mPackageName = attribute.packageName;
        this.mEditorInfo = attribute;
        if (!restarting) {
            this.mMetaState = 0;
        }
        switch (attribute.inputType & 15) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                this.mCurKeyboard = this.mQwertyKeyboard;
                int variation = attribute.inputType & 4080;
                if (variation == 128 || variation == 144) {
                }
                if (variation == 32 || variation == 16 || variation == 176) {
                }
                if ((attribute.inputType & 65536) != 0) {
                    updateShiftKeyState(attribute);
                    break;
                }
                updateShiftKeyState(attribute);
                break;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                this.mCurKeyboard = this.mNumericKeyboard;
                break;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                this.mCurKeyboard = this.mSymbolsKeyboard;
                break;
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                this.mCurKeyboard = this.mDateTimeKeyboard;
                break;
            default:
                this.mCurKeyboard = this.mQwertyKeyboard;
                updateShiftKeyState(attribute);
                break;
        }
        this.mCurKeyboard.setImeOptions(getResources(), attribute.imeOptions);
        if (this.mEnpassLoginKey != null) {
            if (isAccessibilitySettingsOn()) {
                this.mEnpassLoginKey.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.filling_extendkeyboard, null));
            } else {
                this.mEnpassLoginKey.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.filling_extendkeyboard_disabled, null));
            }
        }
        if (this.mPackageName != null && this.mPackageName.equals(getPackageName())) {
            if (this.mEnpassLoginKey != null) {
                this.mEnpassLoginKey.setVisibility(4);
            }
            if (this.mAutofillHint != null) {
                this.mAutofillHint.setVisibility(4);
            }
        } else if (this.mCurrentSuggestionType == SUGGESTIONS_TYPE.EMPTY) {
            if (this.mEnpassLoginKey != null) {
                this.mEnpassLoginKey.setVisibility(0);
            }
            if (this.mAutofillHint != null) {
                this.mAutofillHint.setVisibility(0);
            }
        }
    }

    public void onFinishInput() {
        super.onFinishInput();
        this.mCurKeyboard = this.mQwertyKeyboard;
        if (this.mInputView != null) {
            this.mInputView.closing();
        }
    }

    public void onStartInputView(EditorInfo attribute, boolean restarting) {
        super.onStartInputView(attribute, restarting);
        setLatinKeyboard(this.mCurKeyboard);
        this.mInputView.closing();
        this.mInputView.setSubtypeOnSpaceKey(this.mInputMethodManager.getCurrentInputMethodSubtype());
    }

    public void onCurrentInputMethodSubtypeChanged(InputMethodSubtype subtype) {
        this.mInputView.setSubtypeOnSpaceKey(subtype);
    }

    public void onUpdateSelection(int oldSelStart, int oldSelEnd, int newSelStart, int newSelEnd, int candidatesStart, int candidatesEnd) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd);
        updateCandidates();
    }

    public void onDisplayCompletions(CompletionInfo[] completions) {
    }

    private void updateShiftKeyState(EditorInfo attr) {
        if (attr != null && this.mInputView != null && this.mQwertyKeyboard == this.mInputView.getKeyboard()) {
            int caps = 0;
            EditorInfo ei = getCurrentInputEditorInfo();
            InputConnection ic = getCurrentInputConnection();
            if (!(ei == null || ei.inputType == 0 || ic == null)) {
                caps = ic.getCursorCapsMode(attr.inputType);
            }
            LatinKeyboardView latinKeyboardView = this.mInputView;
            boolean z = (this.mCapsLock || caps != 0) ? PROCESS_HARD_KEYS : DEBUG;
            latinKeyboardView.setShifted(z);
            if (this.mInputView.isShifted()) {
                this.mCurKeyboard.setShiftKeyIcon(getResources(), ShiftState.ACTIVE);
            } else {
                this.mCurKeyboard.setShiftKeyIcon(getResources(), ShiftState.NOT_ACTIVE);
            }
        }
    }

    private boolean isAlphabet(int code) {
        if (Character.isLetter(code)) {
            return PROCESS_HARD_KEYS;
        }
        return DEBUG;
    }

    private void keyDownUp(int keyEventCode) {
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(0, keyEventCode));
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(1, keyEventCode));
    }

    public void onKey(int primaryCode, int[] keyCodes) {
        onTouch();
        if (isWordSeparator(primaryCode) && this.mState != State.LOCK && this.mState != State.SEARCH) {
            sendKeyChar((char) primaryCode);
            if (primaryCode == 32) {
                doubleSpace();
            }
            updateShiftKeyState(getCurrentInputEditorInfo());
        } else if (primaryCode == -5) {
            handleBackspace();
        } else if (primaryCode == -1) {
            handleShift();
        } else if (this.mState == State.LOCK && primaryCode == 10) {
            if (!TextUtils.isEmpty(this.mPasswordEditText.getText().toString())) {
                unlockEnpass(this.mPasswordEditText.getText().toString());
            }
        } else if (primaryCode == -101) {
            handleLanguageSwitch();
        } else if (primaryCode == -100) {
        } else {
            if (primaryCode == IME_OPTION_LOCK) {
                if (this.mLoginScreen != LoginScreenEnum.showFingerScanner) {
                    InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService("input_method");
                    if (imeManager != null) {
                        imeManager.showInputMethodPicker();
                    }
                }
            } else if (primaryCode != -2 || this.mInputView == null) {
                handleCharacter(primaryCode, keyCodes);
            } else {
                Keyboard current = this.mInputView.getKeyboard();
                if (current == this.mSymbolsKeyboard || current == this.mSymbolsShiftedKeyboard) {
                    setLatinKeyboard(this.mQwertyKeyboard);
                    updateImeOption(this.mQwertyKeyboard);
                    return;
                }
                setLatinKeyboard(this.mSymbolsKeyboard);
                this.mSymbolsKeyboard.setShifted(DEBUG);
                updateImeOption(this.mSymbolsKeyboard);
            }
        }
    }

    @SuppressLint({"NewApi"})
    private void handleLanguageSwitch() {
        if (VERSION.SDK_INT >= 16) {
            this.mInputMethodManager.switchToNextInputMethod(getToken(), DEBUG);
        }
    }

    private void handleCharacter(int primaryCode, int[] keyCodes) {
        if (isInputViewShown() && this.mInputView.isShifted()) {
            primaryCode = Character.toUpperCase(primaryCode);
            if (!this.mCapsLock) {
                this.mInputView.setShifted(DEBUG);
                this.mCurKeyboard.setShiftKeyIcon(getResources(), ShiftState.NOT_ACTIVE);
            }
        }
        StringBuilder mPass;
        if (this.mState != State.LOCK || this.mState == State.PASSWORD_VALIDATING) {
            if (this.mState == State.SEARCH) {
                if (primaryCode == 10) {
                    setCandidatesViewShown(DEBUG);
                    this.mInputView.setVisibility(8);
                    return;
                }
                mPass = new StringBuilder(this.mSearchField.getText().toString());
                mPass.append(String.valueOf((char) primaryCode));
                this.mSearchField.setText(mPass);
                this.mSearchField.setSelection(mPass.length());
            } else if (this.mState != State.PASSWORD_VALIDATING) {
                getCurrentInputConnection().commitText(String.valueOf((char) primaryCode), 1);
            }
        } else if (this.mLoginScreen != LoginScreenEnum.showFingerScanner && this.mPasswordEditText != null && this.mPasswordEditText.getVisibility() == 0) {
            mPass = new StringBuilder(this.mPasswordEditText.getText().toString());
            mPass.append(String.valueOf((char) primaryCode));
            this.mPasswordEditText.setText(mPass);
        }
    }

    public static int nthOccurrence(String str, char c, int n) {
        int pos = str.indexOf(c, 0);
        while (true) {
            n--;
            if (n <= 0 || pos == -1) {
                return pos;
            }
            pos = str.indexOf(c, pos + 1);
        }
        return pos;
    }

    public void onText(CharSequence text) {
        InputConnection ic = getCurrentInputConnection();
        if (ic != null) {
            ic.beginBatchEdit();
            ic.commitText(text, 0);
            ic.endBatchEdit();
            updateShiftKeyState(getCurrentInputEditorInfo());
        }
    }

    public void wrongPasswordOrPinResetLoginScreen() {
        this.mLoginColorView.setBackgroundResource(R.drawable.login_page_lock_bg_unsucess);
        this.mLoginScreen = LoginScreenEnum.showPassword;
        this.mPasswordEditText.setInputType(129);
        this.mPasswordEditText.setText(BuildConfig.FLAVOR);
        this.mPasswordEditText.setHint(getString(R.string.filling_wrong_password));
        showQwertyKeyboard();
        new Handler().postDelayed(new 15(this), 500);
    }

    private void handleBackspace() {
        String textString;
        if (this.mState == State.LOCK) {
            textString = this.mPasswordEditText.getText().toString();
            if (textString.length() > 0) {
                this.mPasswordEditText.setText(textString.substring(0, textString.length() - 1));
                this.mPasswordEditText.setSelection(this.mPasswordEditText.getText().length());
            }
        } else if (this.mState == State.SEARCH) {
            textString = this.mSearchField.getText().toString();
            if (textString.length() > 0) {
                this.mSearchField.setText(textString.substring(0, textString.length() - 1));
                this.mSearchField.setSelection(this.mSearchField.getText().length());
            }
        } else {
            keyDownUp(67);
            updateCandidates();
            updateShiftKeyState(getCurrentInputEditorInfo());
        }
    }

    private void handleShift() {
        boolean z = DEBUG;
        if (this.mInputView != null) {
            Keyboard currentKeyboard = this.mInputView.getKeyboard();
            if (this.mQwertyKeyboard == currentKeyboard) {
                checkToggleCapsLock();
                LatinKeyboardView latinKeyboardView = this.mInputView;
                if (this.mCapsLock || !this.mInputView.isShifted()) {
                    z = PROCESS_HARD_KEYS;
                }
                latinKeyboardView.setShifted(z);
                if (this.mCapsLock) {
                    this.mCurKeyboard.setShiftKeyIcon(getResources(), ShiftState.CAPS);
                } else if (this.mInputView.isShifted()) {
                    this.mCurKeyboard.setShiftKeyIcon(getResources(), ShiftState.ACTIVE);
                } else {
                    this.mCurKeyboard.setShiftKeyIcon(getResources(), ShiftState.NOT_ACTIVE);
                }
            } else if (currentKeyboard == this.mSymbolsKeyboard) {
                this.mSymbolsKeyboard.setShifted(PROCESS_HARD_KEYS);
                updateImeOption(this.mSymbolsShiftedKeyboard);
                setLatinKeyboard(this.mSymbolsShiftedKeyboard);
                this.mSymbolsShiftedKeyboard.setShifted(PROCESS_HARD_KEYS);
            } else if (currentKeyboard == this.mSymbolsShiftedKeyboard) {
                this.mSymbolsShiftedKeyboard.setShifted(DEBUG);
                updateImeOption(this.mSymbolsKeyboard);
                setLatinKeyboard(this.mSymbolsKeyboard);
                this.mSymbolsKeyboard.setShifted(DEBUG);
            }
        }
    }

    private void handleClose() {
        requestHideSelf(0);
        resetKeyboard();
    }

    private IBinder getToken() {
        Dialog dialog = getWindow();
        if (dialog == null) {
            return null;
        }
        Window window = dialog.getWindow();
        if (window != null) {
            return window.getAttributes().token;
        }
        return null;
    }

    private void checkToggleCapsLock() {
        long now = System.currentTimeMillis();
        if (this.mLastShiftTime + 800 > now) {
            this.mCapsLock = PROCESS_HARD_KEYS;
            this.mLastShiftTime = 0;
            return;
        }
        this.mCapsLock = DEBUG;
        this.mLastShiftTime = now;
    }

    private String getWordSeparators() {
        return this.mWordSeparators;
    }

    public boolean isWordSeparator(int code) {
        return getWordSeparators().contains(String.valueOf((char) code));
    }

    public void pickSuggestionManually(int index, CharSequence suggestionCharSeq, SUGGESTIONS_TYPE suggestionsType) {
        if (suggestionCharSeq != null) {
            String suggestion = suggestionCharSeq.toString();
            if (suggestionsType == SUGGESTIONS_TYPE.ALLFIELDS) {
                suggestion = (String) this.allFieldsValues.get(index);
                if (index == this.mTOTPPos) {
                    suggestion = this.mTOTPGeneratedValue;
                }
            }
            StringBuilder mPass;
            if (this.mState != State.LOCK || this.mState == State.PASSWORD_VALIDATING) {
                if (this.mState == State.SEARCH) {
                    mPass = new StringBuilder(this.mSearchField.getText().toString());
                    mPass.append(suggestion);
                    this.mSearchField.setText(mPass);
                    this.mSearchField.setSelection(mPass.length());
                } else if (this.mState != State.PASSWORD_VALIDATING) {
                    getCurrentInputConnection().commitText(suggestion, 1);
                }
            } else if (this.mLoginScreen != LoginScreenEnum.showFingerScanner && this.mPasswordEditText != null && this.mPasswordEditText.getVisibility() == 0) {
                mPass = new StringBuilder(this.mPasswordEditText.getText().toString());
                mPass.append(suggestion);
                this.mPasswordEditText.setText(mPass);
            }
        }
    }

    public void swipeRight() {
    }

    public void swipeLeft() {
        handleBackspace();
    }

    public void swipeDown() {
        handleClose();
    }

    public void swipeUp() {
    }

    public void onPress(int primaryCode) {
        if (!(!this.mVibrationOn || primaryCode == 0 || this.mVibrator == null)) {
            try {
                this.mVibrator.vibrate(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (primaryCode == 10 || primaryCode == 32 || primaryCode == -1 || primaryCode == -5 || primaryCode == -101 || primaryCode == -2) {
            this.mInputView.setPreviewEnabled(DEBUG);
        }
        if (this.mSoundOn && !this.mSilentMode && primaryCode != 0) {
            int keyFX;
            switch (primaryCode) {
                case -5:
                    keyFX = 7;
                    break;
                case IRemoteStorage.WEBDAV_REMOTE_USERNAME /*10*/:
                case SpassFingerprint.STATUS_USER_CANCELLED_BY_TOUCH_OUTSIDE /*13*/:
                    keyFX = 8;
                    break;
                case com.github.clans.fab.R.styleable.FloatingActionMenu_menu_openDirection /*32*/:
                    keyFX = 6;
                    break;
                default:
                    keyFX = 0;
                    break;
            }
            this.mKeyAudioManager.playSoundEffect(keyFX);
        }
    }

    public void onRelease(int primaryCode) {
        this.mInputView.setPreviewEnabled(PROCESS_HARD_KEYS);
    }

    public boolean onEvaluateFullscreenMode() {
        return (this.mState == State.LOCK || this.mState == State.LOGINS || this.mState == State.SEARCH) ? PROCESS_HARD_KEYS : DEBUG;
    }

    public void onUpdateExtractingViews(EditorInfo ei) {
        super.onUpdateExtractingViews(ei);
        hideUnwantedExtractedViews();
    }

    public void onUpdateExtractingVisibility(EditorInfo ei) {
        super.onUpdateExtractingVisibility(ei);
    }

    public void onWindowHidden() {
        super.onWindowHidden();
        clearSuggestions();
        resetKeyboard();
        setCandidatesViewShown(DEBUG);
        EnpassApplication.getInstance().removeAutolockHandler();
    }

    public void onWindowShown() {
        super.onWindowShown();
        resetKeyboard();
        if (getCurrentInputEditorInfo() != null) {
            setCandidatesViewShown(PROCESS_HARD_KEYS);
        }
        if (this.mPackageName != null && this.mPackageName.equals(getPackageName())) {
            if (this.mEnpassLoginKey != null) {
                this.mEnpassLoginKey.setVisibility(4);
            }
            if (this.mAutofillHint != null) {
                this.mAutofillHint.setVisibility(4);
            }
        } else if (this.mCurrentSuggestionType == SUGGESTIONS_TYPE.EMPTY) {
            if (this.mEnpassLoginKey != null) {
                this.mEnpassLoginKey.setVisibility(0);
            }
            if (this.mAutofillHint != null) {
                this.mAutofillHint.setVisibility(0);
            }
        }
    }

    public void onDestroy() {
        stopTotpCountdownTask();
        disposeCardFieldCellAndView();
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mMessageReceiver);
    }

    void onTouch() {
        EnpassApplication.getInstance().touch();
    }

    public void onConfigureWindow(Window win, boolean isFullscreen, boolean isCandidatesOnly) {
        super.onConfigureWindow(win, isFullscreen, isCandidatesOnly);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation != 2 || newConfig.smallestScreenWidthDp >= LoginActivity.TAB_WIDTH) {
            if (this.mFillingLockLayout != null) {
                this.mFillingLockLayout.setVisibility(0);
            }
        } else if (this.mFillingLockLayout != null) {
            this.mFillingLockLayout.setVisibility(8);
        }
        if (this.mState == State.LOCK || this.mState == State.SEARCH || this.mState == State.FILL || this.mState == State.LOGINS) {
            handleLockExtandedViewButton();
        } else {
            resetKeyboard();
        }
        EnpassApplication.getInstance().changeLocale(this);
    }

    void showQwertyKeyboard() {
        updateImeOption(this.mQwertyKeyboard);
        setLatinKeyboard(this.mQwertyKeyboard);
    }

    void showNumericKeyboard() {
        updateImeOption(this.mNumericKeyboard);
        setLatinKeyboard(this.mNumericKeyboard);
    }

    public void ItemChanged(KeychainChangeType type, IDisplayItem item, String extra) {
        getMatchingCards();
    }

    public void reload() {
        getMatchingCards();
    }

    public void syncStarted() {
        startSyncing();
    }

    public void realSyncStarted() {
    }

    public void syncDone() {
        stopSyncing();
    }

    public void syncError(String Errormsg) {
        stopSyncing();
    }

    public void syncPasswordError(IRemoteStorage aRemote) {
        syncPasswordMissMatch();
    }

    public void syncAborted() {
        stopSyncing();
    }

    void startSyncing() {
        this.mSyncProgressBar.setVisibility(0);
        this.mSyncError.setVisibility(8);
    }

    void stopSyncing() {
        this.mSyncProgressBar.setVisibility(8);
        this.mSyncError.setVisibility(8);
    }

    void syncPasswordMissMatch() {
        this.mSyncProgressBar.setVisibility(8);
        this.mSyncError.setVisibility(0);
    }

    private boolean doubleSpace() {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) {
            return DEBUG;
        }
        CharSequence lastThree = ic.getTextBeforeCursor(3, 0);
        if (lastThree == null || lastThree.length() != 3 || !Character.isLetterOrDigit(lastThree.charAt(0)) || lastThree.charAt(1) != ' ' || lastThree.charAt(2) != ' ') {
            return DEBUG;
        }
        ic.beginBatchEdit();
        ic.deleteSurroundingText(2, 0);
        ic.commitText(". ", 1);
        ic.endBatchEdit();
        return PROCESS_HARD_KEYS;
    }

    public void setCandidatesView(@NonNull View view) {
        super.setCandidatesView(view);
        this.mCandidateView = (CandidateView) view.findViewById(R.id.view_candidates);
        this.mCandidateView.setService(this);
        this.mEnpassLoginKey = (ImageView) view.findViewById(R.id.keyboard_unlock);
        this.mAutofillHint = (TextView) view.findViewById(R.id.autofill_strip_text);
        if (this.mPackageName == null || !this.mPackageName.equals(getPackageName())) {
            if (this.mEnpassLoginKey != null) {
                this.mEnpassLoginKey.setVisibility(0);
            }
            if (this.mAutofillHint != null) {
                this.mAutofillHint.setVisibility(0);
            }
        } else {
            if (this.mEnpassLoginKey != null) {
                this.mEnpassLoginKey.setVisibility(4);
            }
            if (this.mAutofillHint != null) {
                this.mAutofillHint.setVisibility(4);
            }
        }
        if (!isAccessibilitySettingsOn()) {
            this.mEnpassLoginKey.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.filling_extendkeyboard_disabled, null));
        }
        this.mEnpassLoginKey.setOnClickListener(new 16(this));
        this.closeSuggestions = (ImageView) view.findViewById(R.id.close_suggestions_strip_icon);
        this.closeSuggestions.setOnClickListener(new 17(this));
        this.mAutofillHint.setOnClickListener(new 18(this));
        setCandidatesViewShown(PROCESS_HARD_KEYS);
    }

    void showExtendedLoginView() {
        if (this.mPackageName.equals(getPackageName()) && this.mState != State.SEARCH) {
            Toast.makeText(this, String.format(getString(R.string.autofill_not_available), new Object[]{getPackageName()}), 1).show();
        } else if (!(this.mState == State.SEARCH || this.mState == State.LOCK)) {
            this.mState = State.LOCK;
            this.mDefaultKeyboard = this.mCurKeyboard;
            handleLockExtandedViewButton();
        }
        this.mAutofillHint.setVisibility(8);
    }

    @SuppressLint({"MissingSuperCall"})
    public boolean onEvaluateInputViewShown() {
        Configuration config = getResources().getConfiguration();
        if (config.keyboard == 1 || config.hardKeyboardHidden == 2) {
            return PROCESS_HARD_KEYS;
        }
        return DEBUG;
    }

    private void clearSuggestions() {
        if (this.closeSuggestions != null) {
            this.closeSuggestions.setVisibility(8);
        }
        if (this.mPackageName == null || !this.mPackageName.equals(getPackageName())) {
            if (this.mEnpassLoginKey != null) {
                this.mEnpassLoginKey.setVisibility(0);
            }
            if (this.mAutofillHint != null) {
                this.mAutofillHint.setVisibility(0);
            }
        } else {
            if (this.mEnpassLoginKey != null) {
                this.mEnpassLoginKey.setVisibility(4);
            }
            if (this.mAutofillHint != null) {
                this.mAutofillHint.setVisibility(4);
            }
        }
        setSuggestions(null, DEBUG, DEBUG, SUGGESTIONS_TYPE.EMPTY);
    }

    private void setSuggestions(List<String> suggestions, boolean completions, boolean typedWordValid, SUGGESTIONS_TYPE type) {
        this.mCurrentSuggestionType = type;
        if (this.mCandidateView != null) {
            this.mCandidateView.setSuggestions(suggestions, completions, typedWordValid, type);
        }
    }

    private void updateCandidates() {
        ArrayList<String> list = new ArrayList();
        if (this.longPressedKey != null) {
            int[] codes = this.longPressedKey.codes;
            if (codes.length > 0) {
                List<String> popupList = (List) this.popupCharMap.get(Character.toString((char) codes[0]));
                if (popupList != null && popupList.size() > 0) {
                    if (this.mInputView.isShifted()) {
                        ArrayList<String> uppercaseList = new ArrayList();
                        for (String popupChar : popupList) {
                            uppercaseList.add(popupChar.toUpperCase());
                        }
                        list.addAll(uppercaseList);
                    } else {
                        list.addAll(popupList);
                    }
                    setSuggestions(list, PROCESS_HARD_KEYS, PROCESS_HARD_KEYS, SUGGESTIONS_TYPE.POPUPCHAR);
                    if (this.closeSuggestions != null) {
                        this.closeSuggestions.setVisibility(8);
                    }
                    if (this.mAutofillHint != null) {
                        this.mAutofillHint.setVisibility(8);
                    }
                    if (!(this.mPackageName == null || !this.mPackageName.equals(getPackageName()) || this.mEnpassLoginKey == null)) {
                        this.mEnpassLoginKey.setVisibility(0);
                    }
                    this.longPressedKey = null;
                }
            }
        } else if (this.mCurrentSuggestionType != SUGGESTIONS_TYPE.ALLFIELDS) {
            list.clear();
            clearSuggestions();
        }
    }

    public void onLongPress(Key key) {
        this.longPressedKey = key;
        updateCandidates();
    }

    void initializePopupMap() {
        List<String> qpopupList = new ArrayList();
        qpopupList.add(Character.toString('1'));
        this.popupCharMap.put("q", qpopupList);
        List<String> wpopupList = new ArrayList();
        wpopupList.add(Character.toString('2'));
        this.popupCharMap.put("w", wpopupList);
        List<String> epopupList = new ArrayList();
        epopupList.add(Character.toString('3'));
        epopupList.add(Character.toString('\u00eb'));
        epopupList.add(Character.toString('\u0119'));
        epopupList.add(Character.toString('\u0113'));
        epopupList.add(Character.toString('\u20ac'));
        epopupList.add(Character.toString('\u00e8'));
        epopupList.add(Character.toString('\u00e9'));
        epopupList.add(Character.toString('\u00ea'));
        this.popupCharMap.put("e", epopupList);
        List<String> rpopupList = new ArrayList();
        rpopupList.add(Character.toString('4'));
        this.popupCharMap.put("r", rpopupList);
        List<String> tpopupList = new ArrayList();
        tpopupList.add(Character.toString('5'));
        this.popupCharMap.put("t", tpopupList);
        List<String> ypopupList = new ArrayList();
        ypopupList.add(Character.toString('6'));
        this.popupCharMap.put("y", ypopupList);
        List<String> upopupList = new ArrayList();
        upopupList.add(Character.toString('7'));
        upopupList.add(Character.toString('\u00fc'));
        upopupList.add(Character.toString('\u016d'));
        upopupList.add(Character.toString('\u0171'));
        upopupList.add(Character.toString('\u016b'));
        upopupList.add(Character.toString('\u00f9'));
        upopupList.add(Character.toString('\u00fa'));
        upopupList.add(Character.toString('\u00fb'));
        this.popupCharMap.put("u", upopupList);
        List<String> ipopupList = new ArrayList();
        ipopupList.add(Character.toString('8'));
        ipopupList.add(Character.toString('\u00ec'));
        ipopupList.add(Character.toString('\u00ed'));
        ipopupList.add(Character.toString('\u00ee'));
        ipopupList.add(Character.toString('\u00ef'));
        ipopupList.add(Character.toString('\u0142'));
        ipopupList.add(Character.toString('\u012b'));
        this.popupCharMap.put("i", ipopupList);
        List<String> opopupList = new ArrayList();
        opopupList.add(Character.toString('9'));
        opopupList.add(Character.toString('\u00f2'));
        opopupList.add(Character.toString('\u00f3'));
        opopupList.add(Character.toString('\u00f4'));
        opopupList.add(Character.toString('\u00f5'));
        opopupList.add(Character.toString('\u00f6'));
        opopupList.add(Character.toString('\u00f8'));
        opopupList.add(Character.toString('\u0151'));
        opopupList.add(Character.toString('\u0153'));
        opopupList.add(Character.toString('\u014d'));
        this.popupCharMap.put("o", opopupList);
        List<String> ppopupList = new ArrayList();
        ppopupList.add(Character.toString('0'));
        this.popupCharMap.put("p", ppopupList);
        List<String> apopupList = new ArrayList();
        apopupList.add(Character.toString('\u00e0'));
        apopupList.add(Character.toString('\u00e1'));
        apopupList.add(Character.toString('\u00e2'));
        apopupList.add(Character.toString('\u00e3'));
        apopupList.add(Character.toString('\u00e4'));
        apopupList.add(Character.toString('\u00e5'));
        apopupList.add(Character.toString('\u00e6'));
        apopupList.add(Character.toString('\u0105'));
        this.popupCharMap.put("a", apopupList);
        List<String> spopupList = new ArrayList();
        spopupList.add(Character.toString('\u00a7'));
        spopupList.add(Character.toString('\u00df'));
        spopupList.add(Character.toString('\u015b'));
        spopupList.add(Character.toString('\u015d'));
        spopupList.add(Character.toString('\u0161'));
        spopupList.add(Character.toString('\u015f'));
        this.popupCharMap.put("s", spopupList);
        List<String> cpopupList = new ArrayList();
        cpopupList.add(Character.toString('\u00e7'));
        cpopupList.add(Character.toString('\u0107'));
        cpopupList.add(Character.toString('\u0109'));
        cpopupList.add(Character.toString('\u010d'));
        this.popupCharMap.put("c", cpopupList);
        List<String> npopupList = new ArrayList();
        npopupList.add(Character.toString('\u00f1'));
        npopupList.add(Character.toString('\u0144'));
        this.popupCharMap.put("n", npopupList);
    }

    public void requestHideSelf(int flags) {
        super.requestHideSelf(flags);
    }

    public void onFinishCandidatesView(boolean finishingInput) {
        super.onFinishCandidatesView(finishingInput);
    }

    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void onUnbindInput() {
        super.onUnbindInput();
    }

    public void onFinishInputView(boolean finishingInput) {
        super.onFinishInputView(finishingInput);
    }

    public void switchInputMethod(String id) {
        super.switchInputMethod(id);
    }

    private boolean isAccessibilitySettingsOn() {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Secure.getInt(getContentResolver(), "accessibility_enabled");
        } catch (SettingNotFoundException e) {
            e.printStackTrace();
        }
        SimpleStringSplitter mStringColonSplitter = new SimpleStringSplitter(':');
        if (accessibilityEnabled != 1) {
            return DEBUG;
        }
        String settingValue = Secure.getString(getContentResolver(), "enabled_accessibility_services");
        if (settingValue == null) {
            return DEBUG;
        }
        SimpleStringSplitter splitter = mStringColonSplitter;
        splitter.setString(settingValue);
        while (splitter.hasNext()) {
            if (splitter.next().equalsIgnoreCase("io.enpass.app/in.sinew.enpass.autofill.EnpassAccessibilityService")) {
                return PROCESS_HARD_KEYS;
            }
        }
        return DEBUG;
    }

    public void disableEnpassKeyIcon() {
        if (this.mEnpassLoginKey != null) {
            this.mEnpassLoginKey.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.filling_extendkeyboard_disabled, null));
        }
    }

    public void enableEnpassKeyIcon() {
        if (this.mEnpassLoginKey != null) {
            this.mEnpassLoginKey.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.filling_extendkeyboard, null));
        }
    }

    public static synchronized OtpSource getOtpProvider() {
        OtpProvider otpProvider;
        synchronized (EnpassInputMethodService.class) {
            otpProvider = new OtpProvider(getTotpClock());
        }
        return otpProvider;
    }

    public static synchronized TotpClock getTotpClock() {
        TotpClock totpClock;
        synchronized (EnpassInputMethodService.class) {
            totpClock = new TotpClock(EnpassApplication.getInstance());
        }
        return totpClock;
    }

    private void stopTotpCountdownTask() {
        if (this.mTotpCountdownTask != null) {
            this.mTotpCountdownTask.stop();
            this.mTotpCountdownTask = null;
        }
    }

    private void disposeCardFieldCellAndView() {
    }

    private void updateCodesAndStartTotpCountdownTask() {
        stopTotpCountdownTask();
        this.mTotpCountdownTask = new TotpCountdownTask(this.mTotpCounter, this.mTotpClock, TOTP_COUNTDOWN_REFRESH_PERIOD);
        this.mTotpCountdownTask.setListener(new 19(this));
        this.mTotpCountdownTask.startAndNotifyListener();
    }

    private void refreshVerificationCodes() {
        refreshUserList();
    }

    public void refreshUserList() {
        try {
            String rawSecret = (String) this.allFieldsValues.get(this.mTOTPPos);
            String secret = BuildConfig.FLAVOR;
            if (rawSecret.contains("://")) {
                secret = Utilities.interpretScanResult(Uri.parse(rawSecret));
            } else {
                secret = rawSecret;
            }
            computeAndDisplayPin(secret);
        } catch (OtpSourceException e) {
        }
    }

    public void computeAndDisplayPin(String secret) throws OtpSourceException {
        try {
            String nextCode = this.mOtpProvider.getNextCode(secret);
            if (TextUtils.isEmpty(nextCode)) {
                this.mTOTPGeneratedValue = BuildConfig.FLAVOR;
                stopTotpCountdownTask();
                Toast.makeText(this, getString(R.string.wrong_secret), 1).show();
                return;
            }
            this.mTOTPGeneratedValue = nextCode;
        } catch (OtpSourceException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), 1).show();
        }
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(EnpassApplication.getInstance().changeLocale(base));
    }
}
