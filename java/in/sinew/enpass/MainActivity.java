package in.sinew.enpass;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.amazon.device.iap.PurchasingService;
import com.amazon.device.iap.model.RequestId;
import com.box.androidsdk.content.models.BoxFileVersion;
import com.github.clans.fab.BuildConfig;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.Tracker;
import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;
import in.sinew.enpass.amazonutil.AmazonIapManager;
import in.sinew.enpass.amazonutil.AmazonPurchasingListener;
import in.sinew.enpass.amazonutil.AmazonSku;
import in.sinew.enpass.autofill.NotificationFillActivity;
import in.sinew.enpass.googleutil.IabHelper;
import in.sinew.enpass.googleutil.IabHelper.QueryInventoryFinishedListener;
import in.sinew.enpass.googleutil.Purchase;
import in.sinew.enpass.nokiautil.NokiaIabHelper;
import in.sinew.enpass.nokiautil.NokiaIabHelper.OnIabPurchaseFinishedListener;
import in.sinew.enpass.sinewkit.SinewKit;
import in.sinew.enpassengine.Card;
import in.sinew.enpassengine.Category;
import in.sinew.enpassengine.IAppEventSubscriber;
import in.sinew.enpassengine.IDisplayItem;
import in.sinew.enpassengine.IDisplayItem.DisplayItemType;
import in.sinew.enpassengine.IKeychainDelegate.KeychainChangeType;
import in.sinew.enpassengine.TemplateFactory;
import in.sinew.enpassengine.Utils;
import in.sinew.enpassui.adapter.CardListChooserAdapter;
import in.sinew.enpassui.adapter.CategoryChooserListAdapter;
import in.sinew.enpassui.adapter.DrawerAdapter;
import in.sinew.enpassui.adapter.MultiSelectionListAdapter;
import in.sinew.widget.ScrimInsetsFrameLayout;
import io.enpass.app.R;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.hockeyapp.android.CrashManager;
import net.sqlcipher.database.SQLiteDatabase;

public class MainActivity extends EnpassActivity implements OnTouchListener, ISyncManagerDelegate, IAppEventSubscriber {
    public static final int ADD_SHARED_ITEM = 1;
    public static final int ADD_TRASHED_ITEM = 3;
    public static String ALLITEMS_DISPLAY_IDENTIFIER = "ALL_ITEMS_IDENTIFIER";
    public static String BLANK_CATEGORY_DISPLAY_IDENTIFIER = "CATEGORY_IDENTIFIER";
    public static String DRAWER_LIST_FOLDER_CHANGE = "drawerListChange";
    public static String FAVORITE_DISPLAY_IDENTIFIER = "FAVORITE_IDENTIFIER";
    public static String FAVORITE_EXISTING_CARDS_IDENTIFIER = "favexistingcards";
    public static String FOLDER_DISPLAY_IDENTIFIER = "FOLDER_IDENTIFIER";
    public static String FOLDER_EXISTING_CARDS_IDENTIFIER = "folderexistingcards";
    public static final String HAS_PREMIUM = "hasPremium";
    public static String IS_DRAWER_LIST_REORDERED = "isReordered";
    public static String NEW_FOLDER_IDENTIFIER = "newfolder";
    public static final String PREMIUM_VERSION_PREFERENCE = "premiumVersion";
    static final int RC_REQUEST = 100;
    public static String REORDERED_LIST = "reorderedList";
    public static String SELECTED_DRAWER_ITEM = "selected_drawer_item";
    static final String SKU_PREMIUM = "pro_upgrade";
    public static final String SKU_PREMIUM_NOKIA = "1212889";
    public static final int UPDATE_SHARED_ITEM = 2;
    public static boolean mIsDetailFrameVisible = false;
    public static boolean mIsDetailPresent = false;
    public static MainActivity mMainActivityInstance;
    public static int mSelectedDrawerPosition = 0;
    public static boolean mTwoPane = false;
    String APP_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj+sNWTUpAw9nXF+1ILyJo8ABV7CSYljucjV4ZgbMiPDPD/VM/CtzDQ4IsHkqDqbbubEBhUggDlaPDKMzjMcNKN3FZMUZiwU72jkDBYilsHQlbPM1M4rEfxbbQtXn229YED9yLDlfQhPyi7CG2/GzmlIPCE4HaFW4doOuofhY+FXsABzYfudDa0fctI+sikaB6K7ujkLvdp3uQ5uaLy692AUqqO9AaFggS/EG+tCpFZLtwS6uh1buxjqV0KDmtkO50qcLbuQHuZy/mk6hC6sn43ah6ZogeOqOAOAPGPfgPwZQSLEn5ir/PAVIHukc9gRyEIl5cR+K3zpHAGWH3QDvjwIDAQAB";
    private final int DEEP_SEARCH = ADD_SHARED_ITEM;
    private final String HOCKEY_APP_ID = "fe7df91876ca31a7e634da28a3aef6ed";
    final int TAB_WIDTH = LoginActivity.TAB_WIDTH;
    private final int TITLE_SEARCH = 0;
    int count = 0;
    private String currentUser;
    public FragmentManager fragmentManager;
    boolean isUpdateNotChecked = true;
    public AmazonIapManager mAmazonIapManager;
    boolean mAppStarted = true;
    RelativeLayout mBottomOverlay;
    MenuItem mBrowserMenuItem;
    RelativeLayout mContentFrame;
    Context mContext;
    String mCurrentFolder = BuildConfig.FLAVOR;
    public Fragment mCurrentFragment = null;
    RelativeLayout mDetailContentFrame;
    Toolbar mDetailToolbar;
    List<IDisplayItem> mDrawerItems;
    public DrawerLayout mDrawerLayout;
    public ListView mDrawerList;
    DrawerAdapter mDrawerListAdapter;
    public ActionBarDrawerToggle mDrawerToggle;
    private final String mEnpassScheme = "enpass://share";
    View mFragmentsDivider;
    private GestureDetector mGestureDetector;
    QueryInventoryFinishedListener mGotInventoryListener = new 7(this);
    boolean mInsideFolder = false;
    private boolean mInstanceState = false;
    boolean mIsBothPane = true;
    MultiSelectionListAdapter mMultiSelectionCustomAdapter = null;
    IabHelper mNewPurchaseHelper;
    NokiaIabHelper.QueryInventoryFinishedListener mNokiaGotInventoryListener = new 9(this);
    NokiaIabHelper mNokiaHelper;
    OnIabPurchaseFinishedListener mNokiaPurchaseFinishedListener = new 12(this);
    String mPayload = BuildConfig.FLAVOR;
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new 16(this);
    IabHelper mPurchaseTestHelper;
    ScrimInsetsFrameLayout mScrimInsetsFrameLayout;
    SearchFragment mSearchFragment;
    MenuItem mSearchItem;
    public CharSequence mTitle;
    private MaterialSearchView searchView;

    public static MainActivity getmMainActivityInstance() {
        return mMainActivityInstance;
    }

    @TargetApi(21)
    @SuppressLint({"NewApi"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivityInstance = this;
        Intent intent = getIntent();
        if (intent != null && (1048576 & intent.getFlags()) == 0) {
            String extra = intent.getStringExtra("caller");
            intent.removeExtra("caller");
            if (extra != null) {
                if (extra.equals("autofillnotification")) {
                    startActivity(new Intent(this, NotificationFillActivity.class));
                }
            }
        }
        if (EnpassApplication.getInstance() != null) {
            EnpassApplication.getInstance().changeLocale(this);
        } else {
            System.runFinalization();
            Process.killProcess(Process.myPid());
            System.exit(0);
        }
        setContentView(R.layout.main_activity);
        this.mContext = this;
        if (findViewById(R.id.content_detail_frame) != null) {
            mTwoPane = true;
            this.mContentFrame = (RelativeLayout) findViewById(R.id.content_frame_layout);
            this.mDetailContentFrame = (RelativeLayout) findViewById(R.id.detail_content_frame_layout);
            if (this.mIsBothPane && getResources().getConfiguration().orientation == ADD_SHARED_ITEM) {
                this.mIsBothPane = false;
                mIsDetailFrameVisible = false;
                this.mContentFrame.setVisibility(0);
                this.mContentFrame.getLayoutParams().width = -1;
                this.mDetailContentFrame.setVisibility(4);
                mIsDetailPresent = false;
            }
        } else {
            mTwoPane = false;
        }
        Toolbar aDrawerToolbar = (Toolbar) findViewById(R.id.drawer_toolbar);
        if (aDrawerToolbar != null) {
            setSupportActionBar(aDrawerToolbar);
        }
        if (mTwoPane) {
            this.mDetailToolbar = (Toolbar) findViewById(R.id.detail_drawer_toolbar);
            this.mDetailToolbar.inflateMenu(R.menu.main_activity_menu);
            this.mDetailToolbar.getMenu().findItem(R.id.action_search).setVisible(false);
            this.mFragmentsDivider = findViewById(R.id.frag_divider);
            this.mDetailToolbar.setOnMenuItemClickListener(new 1(this));
        }
        this.mBottomOverlay = (RelativeLayout) findViewById(R.id.bottom_overlay_layout);
        this.searchView = (MaterialSearchView) findViewById(R.id.search_view);
        this.searchView.setCursorDrawable(R.drawable.custom_cursor);
        this.searchView.setEllipsize(true);
        this.searchView.setOnQueryTextListener(new 2(this));
        this.searchView.setOnSearchViewListener(new 3(this, aDrawerToolbar));
        getOverflowMenu();
        EnpassApplication.getInstance().setMainActivity(this);
        if (EnpassApplication.MARKET_ID == EnpassApplication.INSTALL_FROM_GOOGLE_PLAY) {
            if (TextUtils.isEmpty(EnpassApplication.getInstance().getAppSettings().getSignInAsTeamOrgIdPref())) {
                if (!EnpassApplication.getInstance().getAppSettings().getRestorePurchased()) {
                    setupForGooglePlay();
                } else if (ContextCompat.checkSelfPermission(this, "android.permission.GET_ACCOUNTS") == 0) {
                    Account[] accounts = AccountManager.get(this).getAccountsByType(GoogleAccountManager.ACCOUNT_TYPE);
                    String regAccount = EnpassApplication.getInstance().getAppSettings().getRegisteredAccount();
                    boolean idFound = false;
                    for (int count = 0; count < accounts.length; count += ADD_SHARED_ITEM) {
                        if (regAccount.equals(accounts[count].name)) {
                            idFound = true;
                            break;
                        }
                    }
                    if (!idFound) {
                        EnpassApplication.getInstance().getAppSettings().saveRestorePurchase(false);
                        savePurchase(false);
                        setupForGooglePlay();
                    }
                }
            }
        } else if (EnpassApplication.MARKET_ID == EnpassApplication.INSTALL_FROM_AMAZON_STORE) {
            setupForAmazonStore();
        } else if (EnpassApplication.MARKET_ID == EnpassApplication.INSTALL_FROM_NOKIA_STORE) {
            setupForNokiaStore();
        }
        SinewKit.getSinewKitInstance().setPlatform(EnpassApplication.getInstance().platform);
        SinewKit.getSinewKitInstance().setAppName(EnpassApplication.getInstance().appName);
        if (SinewKit.getSinewKitInstance().getAppVersion().equals(BuildConfig.FLAVOR)) {
            SinewKit.getSinewKitInstance().storeAppVersion(EnpassApplication.getInstance().version);
        }
        SinewKit.getSinewKitInstance().setDaysUntilPrompt((long) EnpassApplication.getInstance().daysUntilPrompt);
        SinewKit.getSinewKitInstance().setUsageUntilPrompt(EnpassApplication.getInstance().usageUntilPrompt);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.mScrimInsetsFrameLayout = (ScrimInsetsFrameLayout) findViewById(R.id.drawer_linearLayout);
        if (getResources().getConfiguration().smallestScreenWidthDp < LoginActivity.TAB_WIDTH) {
            int displayWidth;
            if (getResources().getConfiguration().orientation == ADD_SHARED_ITEM) {
                displayWidth = getResources().getDisplayMetrics().widthPixels;
            } else {
                displayWidth = getResources().getDisplayMetrics().heightPixels;
            }
            int size = (int) Utils.pixelToDp(56.0f, this);
            this.mScrimInsetsFrameLayout.getLayoutParams().width = displayWidth - size;
        }
        this.mDrawerList = (ListView) findViewById(R.id.left_drawer);
        this.mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, 8388611);
        this.mTitle = getTitle();
        this.mDrawerItems = new ArrayList();
        String[] dropdownAry = getResources().getStringArray(2130903050);
        this.mDrawerItems.add(new ChangeSimpleItemToIDisplayItem(dropdownAry[0], ALLITEMS_DISPLAY_IDENTIFIER, 1030));
        this.mDrawerItems.add(new ChangeSimpleItemToIDisplayItem(dropdownAry[ADD_SHARED_ITEM], FAVORITE_DISPLAY_IDENTIFIER, 1012));
        this.mDrawerItems.add(new ChangeSimpleItemToIDisplayItem(dropdownAry[UPDATE_SHARED_ITEM], FOLDER_DISPLAY_IDENTIFIER, 1008));
        this.mDrawerItems.add(new ChangeSimpleItemToIDisplayItem(dropdownAry[ADD_TRASHED_ITEM], BLANK_CATEGORY_DISPLAY_IDENTIFIER, -1));
        this.mDrawerLayout.setScrimColor(Color.argb(150, 0, 0, 0));
        this.mDrawerLayout.setFocusableInTouchMode(false);
        this.mDrawerListAdapter = new DrawerAdapter(this, this.mDrawerItems, this.mDrawerLayout, this.mScrimInsetsFrameLayout);
        this.mDrawerList.setAdapter(this.mDrawerListAdapter);
        this.mDrawerList.setOnItemClickListener(new DrawerItemClickListener(this, null));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        supportInvalidateOptionsMenu();
        this.mDrawerToggle = new 4(this, this, this.mDrawerLayout, aDrawerToolbar, R.string.drawer_open, R.string.drawer_close);
        this.mDrawerLayout.setDrawerListener(this.mDrawerToggle);
        if (savedInstanceState == null) {
            this.mInstanceState = true;
        }
        if (!EnpassApplication.getInstance().getAppSettings().isBrowserButtonOverlayVisible() || EnpassApplication.getInstance().isRunningOnChromebook()) {
            this.mBottomOverlay.setVisibility(4);
        } else {
            this.mBottomOverlay.setVisibility(0);
        }
        this.mBottomOverlay.setOnTouchListener(new 5(this));
        Uri data = getIntent().getData();
        if (data != null) {
            EnpassApplication.getInstance().getAppSettings().saveSharedData(data);
        }
        if (EnpassApplication.getInstance().getAppSettings().sendAnalyticsPref()) {
            Tracker tracker = EnpassApplication.getInstance().getDefaultGoogleAnalyticsTracker();
            if (tracker != null) {
                tracker.send(new EventBuilder().setCategory("Action").build());
            }
        }
        if (EnpassApplication.getInstance().getAppSettings().getBoxDisableAlert()) {
            showBoxAlert();
            EnpassApplication.setBoxRemote(null);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putBoolean("mInstanceState", true);
        }
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            this.mInstanceState = savedInstanceState.getBoolean("mInstanceState");
        }
    }

    void setupForGooglePlay() {
        this.mPurchaseTestHelper = new IabHelper(this, this.APP_KEY);
        this.mPurchaseTestHelper.startSetup(new 6(this));
    }

    void setupForAmazonStore() {
        this.mAmazonIapManager = new AmazonIapManager(this);
        this.mAmazonIapManager.activate();
        PurchasingService.registerListener(getApplicationContext(), new AmazonPurchasingListener(this.mAmazonIapManager));
    }

    void setupForNokiaStore() {
        this.mNokiaHelper = new NokiaIabHelper(this, this.APP_KEY);
        this.mNokiaHelper.startSetup(new 8(this));
    }

    private void selectItem(int position, boolean closeDrawer) {
        mSelectedDrawerPosition = position;
        this.fragmentManager = getFragmentManager();
        DetailFragment frag = (DetailFragment) this.fragmentManager.findFragmentById(R.id.content_detail_frame);
        if (frag != null) {
            this.fragmentManager.beginTransaction().remove(frag).commitAllowingStateLoss();
        }
        Object[] objArr;
        String format;
        switch (position) {
            case SQLiteDatabase.OPEN_READWRITE /*0*/:
                this.mCurrentFragment = new AllCardsFragment();
                this.mDrawerList.setItemChecked(position + ADD_SHARED_ITEM, true);
                setTitle(((IDisplayItem) this.mDrawerItems.get(position)).getDisplayName());
                if (closeDrawer) {
                    this.mDrawerLayout.closeDrawer(this.mScrimInsetsFrameLayout);
                    break;
                }
                break;
            case ADD_SHARED_ITEM /*1*/:
                this.mCurrentFragment = new FavoriteFragment();
                this.mDrawerList.setItemChecked(position + ADD_SHARED_ITEM, true);
                setTitle(((IDisplayItem) this.mDrawerItems.get(position)).getDisplayName());
                if (closeDrawer) {
                    this.mDrawerLayout.closeDrawer(this.mScrimInsetsFrameLayout);
                    break;
                }
                break;
            case UPDATE_SHARED_ITEM /*2*/:
                this.mCurrentFragment = new FolderFragment();
                FOLDER_DISPLAY_IDENTIFIER = ((IDisplayItem) this.mDrawerItems.get(position)).getDisplayIdentifier();
                this.mDrawerList.setItemChecked(position + ADD_SHARED_ITEM, true);
                setTitle(((IDisplayItem) this.mDrawerItems.get(position)).getDisplayName());
                objArr = new Object[ADD_SHARED_ITEM];
                objArr[0] = Integer.valueOf(((IDisplayItem) this.mDrawerItems.get(position)).getDisplayIconId());
                format = String.format(Locale.US, "sm%d_sel", objArr);
                if (closeDrawer) {
                    this.mDrawerLayout.closeDrawer(this.mScrimInsetsFrameLayout);
                    break;
                }
                break;
            default:
                IDisplayItem selectedItem;
                Bundle bundle;
                if (((IDisplayItem) this.mDrawerItems.get(position)).getDisplayType() != DisplayItemType.DisplayItemCategory) {
                    if (((IDisplayItem) this.mDrawerItems.get(position)).getDisplayType() == DisplayItemType.DisplayItemFolder) {
                        selectedItem = (IDisplayItem) this.mDrawerItems.get(position);
                        this.mCurrentFragment = new FolderFragment();
                        FOLDER_DISPLAY_IDENTIFIER = selectedItem.getDisplayIdentifier();
                        bundle = new Bundle();
                        bundle.putString("identifier", selectedItem.getDisplayIdentifier());
                        bundle.putString(BoxFileVersion.FIELD_NAME, selectedItem.getDisplayName());
                        bundle.putBoolean("openFromDrawer", true);
                        this.mCurrentFragment.setArguments(bundle);
                        this.mDrawerList.setItemChecked(position + ADD_SHARED_ITEM, true);
                        setTitle(((IDisplayItem) this.mDrawerItems.get(position)).getDisplayName());
                        objArr = new Object[ADD_SHARED_ITEM];
                        objArr[0] = Integer.valueOf(((IDisplayItem) this.mDrawerItems.get(position)).getDisplayIconId());
                        format = String.format(Locale.US, "sm%d_sel", objArr);
                        if (closeDrawer) {
                            this.mDrawerLayout.closeDrawer(this.mScrimInsetsFrameLayout);
                            break;
                        }
                    }
                }
                selectedItem = (IDisplayItem) this.mDrawerItems.get(position);
                this.mCurrentFragment = new CategoryCardsFragment();
                bundle = new Bundle();
                bundle.putString("identifier", selectedItem.getDisplayIdentifier());
                bundle.putString(BoxFileVersion.FIELD_NAME, selectedItem.getDisplayName());
                this.mCurrentFragment.setArguments(bundle);
                this.mDrawerList.setItemChecked(position + ADD_SHARED_ITEM, true);
                setTitle(((IDisplayItem) this.mDrawerItems.get(position)).getDisplayName());
                objArr = new Object[ADD_SHARED_ITEM];
                objArr[0] = Integer.valueOf(((IDisplayItem) this.mDrawerItems.get(position)).getDisplayIconId());
                format = String.format(Locale.US, "sm%d_sel", objArr);
                if (closeDrawer) {
                    this.mDrawerLayout.closeDrawer(this.mScrimInsetsFrameLayout);
                    break;
                }
                break;
        }
        if (this.mCurrentFragment != null) {
            this.fragmentManager.beginTransaction().replace(R.id.content_frame, this.mCurrentFragment).commitAllowingStateLoss();
            String selectedItemIdentifier = ((IDisplayItem) this.mDrawerItems.get(position)).getDisplayIdentifier();
            Editor selectedEdit = EnpassApplication.getInstance().getSharedPreferences(SELECTED_DRAWER_ITEM, 0).edit();
            selectedEdit.putString("DrawerLastSelectedPos", selectedItemIdentifier);
            selectedEdit.commit();
            clearDetailMenus();
        }
    }

    public void setTitle(CharSequence title) {
        this.mTitle = title;
        getSupportActionBar().setTitle(this.mTitle);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        if (mTwoPane) {
            this.mBrowserMenuItem = menu.findItem(R.id.menu_browser);
            if (getResources().getConfiguration().orientation == ADD_SHARED_ITEM) {
                this.mBrowserMenuItem.setVisible(true);
            } else {
                this.mBrowserMenuItem.setVisible(false);
            }
        }
        MenuItem searchItem = menu.findItem(R.id.action_search);
        this.searchView.setMenuItem(searchItem);
        MenuItemCompat.setOnActionExpandListener(searchItem, new 10(this));
        if (EnpassApplication.getInstance().isRunningOnChromebook() && this.mBrowserMenuItem != null) {
            this.mBrowserMenuItem.setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean z = true;
        this.mSearchItem = menu.findItem(R.id.action_search);
        if (!this.mDrawerLayout.isDrawerOpen(this.mScrimInsetsFrameLayout)) {
            MenuItem findItem;
            if (!mTwoPane && !EnpassApplication.getInstance().isRunningOnChromebook()) {
                findItem = menu.findItem(R.id.menu_browser);
                if (this.searchView.isSearchOpen()) {
                    z = false;
                }
                findItem.setVisible(z);
            } else if (!(this.mIsBothPane || getResources().getConfiguration().orientation != ADD_SHARED_ITEM || EnpassApplication.getInstance().isRunningOnChromebook())) {
                findItem = menu.findItem(R.id.menu_browser);
                if (this.searchView.isSearchOpen()) {
                    z = false;
                }
                findItem.setVisible(z);
            }
        }
        if (EnpassApplication.getInstance().getAppSettings().isBrowserButtonOverlayVisible()) {
            if (this.searchView.isSearchOpen() || EnpassApplication.getInstance().isRunningOnChromebook()) {
                this.mBottomOverlay.setVisibility(4);
                if (this.searchView.isSearchOpen()) {
                    findViewById(R.id.search_view_cardview).setVisibility(0);
                    if (mTwoPane) {
                        ((CardView) findViewById(R.id.detail_toolbar_cardview)).setMaxCardElevation(4.0f);
                    }
                } else {
                    findViewById(R.id.search_view_cardview).setVisibility(8);
                    if (mTwoPane) {
                        ((CardView) findViewById(R.id.detail_toolbar_cardview)).setMaxCardElevation(0.0f);
                    }
                }
            } else {
                this.mBottomOverlay.setVisibility(0);
                findViewById(R.id.search_view_cardview).setVisibility(8);
                if (mTwoPane) {
                    ((CardView) findViewById(R.id.detail_toolbar_cardview)).setMaxCardElevation(0.0f);
                }
            }
        }
        if (EnpassApplication.getInstance().isRunningOnChromebook() && this.mBrowserMenuItem != null) {
            this.mBrowserMenuItem.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_search /*2131296294*/:
                if (mTwoPane) {
                    this.mDetailToolbar.setBackgroundColor(getResources().getColor(17170443));
                    ((CardView) findViewById(R.id.detail_toolbar_cardview)).setMaxCardElevation(4.0f);
                }
                findViewById(R.id.search_view_cardview).setVisibility(0);
                break;
            case R.id.menu_browser /*2131296727*/:
                onSwipeRight();
                this.mBottomOverlay.setVisibility(4);
                EnpassApplication.getInstance().getAppSettings().setMainPageBrowserButtonOverlay(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void showSearchFrag() {
        FragmentManager fragmentManager = getFragmentManager();
        DetailFragment frag = (DetailFragment) fragmentManager.findFragmentById(R.id.content_detail_frame);
        if (frag != null) {
            fragmentManager.beginTransaction().remove(frag).commit();
        }
        this.mSearchFragment = new SearchFragment();
        FragmentTransaction trans = fragmentManager.beginTransaction().replace(R.id.content_frame, this.mSearchFragment, "SearchFragment");
        trans.addToBackStack(null);
        trans.commitAllowingStateLoss();
        this.mInsideFolder = true;
        if (mTwoPane) {
            clearDetailMenus();
        }
    }

    public void launchPurchaseFlow(Activity act) {
        this.mNewPurchaseHelper = new IabHelper(act, this.APP_KEY);
        this.mNewPurchaseHelper.startSetup(new 11(this, act));
    }

    public void disposeIabHelper() {
        if (this.mNewPurchaseHelper != null) {
            this.mNewPurchaseHelper.dispose();
        }
    }

    void launchAmazonPurchaseFlow() {
        RequestId requestId = PurchasingService.purchase(AmazonSku.PRO.getSku());
    }

    void launchNokiaPurchaseFlow(Activity act) {
        if (this.mNokiaHelper != null) {
            try {
                this.mNokiaHelper.launchPurchaseFlow(act, SKU_PREMIUM_NOKIA, RC_REQUEST, this.mNokiaPurchaseFinishedListener, this.mPayload);
            } catch (IllegalStateException e) {
                alert("Problem setting up in-app billing.");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void savePurchase(boolean hasPremiumVersion) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(PREMIUM_VERSION_PREFERENCE, 0).edit();
        edit.putBoolean(PREMIUM_VERSION_PREFERENCE, hasPremiumVersion);
        edit.commit();
        runOnUiThread(new 13(this));
    }

    public void alert(String message) {
        if (EnpassApplication.getInstance().getAccountActivity() != null) {
            Builder bld = new Builder(EnpassApplication.getInstance().getAccountActivity());
            bld.setMessage(message);
            bld.setTitle(getString(R.string.app_name));
            bld.setCancelable(false);
            bld.setNeutralButton(getResources().getString(R.string.ok), new 14(this));
            bld.create().show();
        }
    }

    public void showAmazonPurchaseMessage(String message) {
        if (EnpassApplication.getInstance().getAccountActivity() != null) {
            Builder bld = new Builder(EnpassApplication.getInstance().getAccountActivity());
            bld.setMessage(message);
            bld.setTitle(getString(R.string.app_name));
            bld.setCancelable(false);
            bld.setNeutralButton(getResources().getString(R.string.ok), new 15(this));
            bld.create().show();
        }
    }

    public void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        if (EnpassApplication.MARKET_ID == EnpassApplication.INSTALL_FROM_GOOGLE_PLAY) {
            this.mNewPurchaseHelper.handleActivityResult(requestCode, resultCode, data);
        } else if (EnpassApplication.MARKET_ID == EnpassApplication.INSTALL_FROM_NOKIA_STORE) {
            this.mNokiaHelper.handleActivityResult(requestCode, resultCode, data);
        }
    }

    boolean verifyDeveloperPayload(Purchase p) {
        if (this.mPayload.equals(p.getDeveloperPayload())) {
            return true;
        }
        return false;
    }

    public void showCategoryChooser() {
        if (!isFinishing()) {
            Builder builder = new Builder(this);
            builder.setTitle(R.string.choose_category);
            ArrayList<Category> listcat = new ArrayList();
            listcat.addAll((ArrayList) TemplateFactory.getCategoryWithoutIdentity());
            builder.setSingleChoiceItems(new CategoryChooserListAdapter(this, listcat), -1, new 17(this, listcat));
            builder.show();
        }
    }

    public void showTemplateChooser(String aCategory) {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.choose_template);
        ArrayList<Card> templateList = (ArrayList) TemplateFactory.getTemplatesOfCategory(aCategory);
        Card aCard = (Card) templateList.get(templateList.size() - 1);
        templateList.remove(templateList.size() - 1);
        Collections.sort(templateList, new DisplayItemComparator());
        templateList.add(aCard);
        builder.setSingleChoiceItems(new CardListChooserAdapter(this, templateList, true, true, false), -1, new 18(this, templateList));
        builder.show();
    }

    public void favoriteCardChooser() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.add);
        ArrayList<IDisplayItem> unfavoritesList = (ArrayList) EnpassApplication.getInstance().filterCards(EnpassApplication.getInstance().getKeychain().getUnfavoriesCards());
        if (unfavoritesList.size() >= ADD_SHARED_ITEM) {
            this.mMultiSelectionCustomAdapter = new MultiSelectionListAdapter(this, unfavoritesList, false);
            builder.setSingleChoiceItems(this.mMultiSelectionCustomAdapter, -1, null);
        } else {
            builder.setTitle(BuildConfig.FLAVOR).setMessage(R.string.empty_unfavorites).setNeutralButton(R.string.ok, null);
        }
        builder.setNeutralButton(getResources().getString(R.string.ok), new 19(this));
        builder.show();
    }

    public void disableDrawerToggle(String name) {
        this.mInsideFolder = true;
        this.mDrawerLayout.setDrawerLockMode(ADD_SHARED_ITEM);
        this.mDrawerToggle.setDrawerIndicatorEnabled(false);
        this.mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setTitle(name);
        this.mDrawerToggle.setToolbarNavigationClickListener(new 20(this));
    }

    public void enableDrawerToggle() {
        this.mInsideFolder = false;
        this.mDrawerLayout.setDrawerLockMode(0);
        this.mDrawerToggle.setDrawerIndicatorEnabled(true);
        getSupportActionBar().setTitle(((IDisplayItem) this.mDrawerItems.get(UPDATE_SHARED_ITEM)).getDisplayName());
    }

    public void onFolderRemoveAlert(String aCurrentFolderUuid, IDisplayItem aSelectedItem) {
        Builder builder = new Builder(this);
        builder.setTitle(String.format(getResources().getString(R.string.warning), new Object[0]));
        String string = getResources().getString(R.string.folder_remove_alert);
        Object[] objArr = new Object[ADD_SHARED_ITEM];
        objArr[0] = aSelectedItem.getDisplayName();
        builder.setMessage(String.format(string, objArr));
        builder.setPositiveButton(R.string.ok, new 21(this, aCurrentFolderUuid));
        builder.setNegativeButton(R.string.cancel, new 22(this));
        builder.show();
    }

    public void clickOnAddCard(String aCurrentFolder) {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.add);
        ArrayList<IDisplayItem> cardsList = (ArrayList) EnpassApplication.getInstance().filterCards(EnpassApplication.getInstance().getKeychain().getCardNotInFolder(aCurrentFolder));
        if (cardsList.size() >= ADD_SHARED_ITEM) {
            this.mMultiSelectionCustomAdapter = new MultiSelectionListAdapter(this, cardsList, false);
            builder.setSingleChoiceItems(this.mMultiSelectionCustomAdapter, -1, null);
        } else {
            builder.setTitle(BuildConfig.FLAVOR).setMessage(R.string.no_card_to_add).setNeutralButton(R.string.ok, null);
        }
        builder.setNeutralButton(getResources().getString(R.string.ok), new 23(this, aCurrentFolder));
        builder.show();
    }

    public List<IDisplayItem> getDrawerItems() {
        return this.mDrawerItems;
    }

    protected void onStart() {
        super.onStart();
        if (EnpassApplication.MARKET_ID == EnpassApplication.INSTALL_FROM_AMAZON_STORE) {
            Set<String> productSkus = new HashSet();
            AmazonSku[] values = AmazonSku.values();
            int length = values.length;
            for (int i = 0; i < length; i += ADD_SHARED_ITEM) {
                productSkus.add(values[i].getSku());
            }
            PurchasingService.getProductData(productSkus);
        }
        EnpassApplication.getInstance().addSubscriber((IAppEventSubscriber) EnpassApplication.getInstance().getMainActivity());
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onResume() {
        super.onResume();
        boolean appUnlocked = EnpassApplication.getInstance().isAppUnlocked;
        if (this.isUpdateNotChecked && appUnlocked) {
            SinewKit.getSinewKitInstance().appStarted();
            this.isUpdateNotChecked = false;
        }
        boolean aLockOnLeaving = EnpassApplication.getInstance().getAppSettings().getLockOnLeaving();
        if (this.mAppStarted) {
            this.mAppStarted = false;
            EnpassApplication.getInstance().notifyReload();
            if (!aLockOnLeaving && appUnlocked) {
                EnpassApplication.getInstance().appInForegroundEvent();
            }
        }
        EnpassApplication.getSyncManagerInstance().addSyncDelegate(this);
        if (EnpassApplication.MARKET_ID == EnpassApplication.INSTALL_FROM_AMAZON_STORE) {
            PurchasingService.getUserData();
            PurchasingService.getPurchaseUpdates(false);
        }
        if (this.searchView.isSearchOpen()) {
            findViewById(R.id.search_view_cardview).setVisibility(0);
            if (this.mSearchFragment != null) {
                int aSearchInCardsVal = EnpassApplication.getInstance().getAppSettings().getSearchInCards();
                if (aSearchInCardsVal == 0) {
                    this.mSearchFragment.searchInCards(this.searchView.getmOldQueryText().toString(), false);
                } else if (aSearchInCardsVal == ADD_SHARED_ITEM) {
                    this.mSearchFragment.searchInCards(this.searchView.getmOldQueryText().toString(), true);
                }
            }
        } else {
            if (mTwoPane) {
                this.mDetailToolbar.setBackgroundColor(getResources().getColor(2131099740));
            }
            findViewById(R.id.search_view_cardview).setVisibility(8);
        }
        if (EnpassApplication.getInstance().getAppSettings().checkSendCrashReportPref()) {
            checkForCrashes();
        }
    }

    private void checkForCrashes() {
        CrashManager.register(this, "fe7df91876ca31a7e634da28a3aef6ed", new 24(this));
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
        EnpassApplication.getInstance().mTabs.clear();
        EnpassApplication.getInstance().mTabId = 0;
        EnpassApplication.getInstance().removeSubscriber((IAppEventSubscriber) EnpassApplication.getInstance().getMainActivity());
        EnpassApplication.getSyncManagerInstance().removeSyncDelegate(this);
        if (this.mPurchaseTestHelper != null) {
            this.mPurchaseTestHelper.dispose();
        }
    }

    public void ItemChanged(KeychainChangeType type, IDisplayItem item, String extra) {
    }

    public void reload() {
        if (EnpassApplication.getInstance().getKeychain() != null && this.mInstanceState) {
            int selectedItemPosition = -1;
            String savedItemIdentifier = EnpassApplication.getInstance().getSharedPreferences(SELECTED_DRAWER_ITEM, 0).getString("DrawerLastSelectedPos", null);
            boolean lastCategorySelected = EnpassApplication.getInstance().getAppSettings().getLastSelectedCategoryEnable();
            ArrayList<IDisplayItem> reorderList = (ArrayList) EnpassApplication.getInstance().getAppSettings().getReorderList();
            List<String> categoriesList = EnpassApplication.getInstance().getAppSettings().getDrawerVisibleCategories();
            ArrayList<IDisplayItem> sidebarList = new ArrayList();
            Iterator it = reorderList.iterator();
            while (it.hasNext()) {
                IDisplayItem item = (IDisplayItem) it.next();
                if (item.getDisplayType() == DisplayItemType.DisplayItemFolder || categoriesList.indexOf(item.getDisplayIdentifier()) != -1) {
                    sidebarList.add(item);
                }
            }
            this.mDrawerItems.addAll(sidebarList);
            for (int i = 0; i < this.mDrawerItems.size(); i += ADD_SHARED_ITEM) {
                if (((IDisplayItem) this.mDrawerItems.get(i)).getDisplayIdentifier().equals(savedItemIdentifier)) {
                    selectedItemPosition = i;
                    break;
                }
            }
            if (selectedItemPosition == -1) {
                mSelectedDrawerPosition = 0;
            } else {
                mSelectedDrawerPosition = selectedItemPosition;
            }
            if (!lastCategorySelected) {
                mSelectedDrawerPosition = 0;
            }
            selectItem(mSelectedDrawerPosition, true);
            this.mInstanceState = false;
        }
    }

    public void onBackPressed() {
        if (this.mInsideFolder) {
            if (mTwoPane && mIsDetailFrameVisible && getResources().getConfiguration().orientation == ADD_SHARED_ITEM) {
                backFromDetailPage();
                this.mDrawerLayout.setDrawerLockMode(0);
                return;
            }
            getFragmentManager().popBackStack();
            if (mTwoPane) {
                DetailFragment frag = (DetailFragment) getFragmentManager().findFragmentById(R.id.content_detail_frame);
                if (frag != null) {
                    this.fragmentManager.beginTransaction().remove(frag).commit();
                }
                clearDetailMenus();
            }
        } else if (mTwoPane && mIsDetailFrameVisible && getResources().getConfiguration().orientation == ADD_SHARED_ITEM) {
            backFromDetailPage();
            this.mDrawerLayout.setDrawerLockMode(0);
            return;
        } else if (this.mDrawerLayout.isDrawerOpen(8388611)) {
            super.onBackPressed();
            return;
        } else {
            this.mDrawerLayout.openDrawer(8388611);
        }
        if (this.searchView.isSearchOpen()) {
            this.searchView.closeSearch();
        }
    }

    public void backFromDetailPage() {
        this.mDetailContentFrame.setVisibility(8);
        this.mContentFrame.setVisibility(0);
        mIsDetailFrameVisible = false;
        if (this.mBrowserMenuItem != null && !EnpassApplication.getInstance().isRunningOnChromebook()) {
            this.mBrowserMenuItem.setVisible(true);
        }
    }

    public void syncStarted() {
        this.mDrawerListAdapter.startSyncing();
    }

    public void realSyncStarted() {
    }

    public void syncDone() {
        this.mDrawerListAdapter.stopSyncing();
        this.mDrawerListAdapter.setImageSyncBackground();
        EnpassApplication.getInstance().getAppSettings().setSyncErrorPref(false);
        Fragment fragment = this.fragmentManager.findFragmentById(R.id.content_frame);
        if (fragment instanceof AllCardsFragment) {
            ((AllCardsFragment) fragment).removeSyncErrorNotification();
        } else if (fragment instanceof FolderFragment) {
            ((FolderFragment) fragment).removeSyncErrorNotification();
        }
        ArrayList<IDisplayItem> reorderList = (ArrayList) EnpassApplication.getInstance().getAppSettings().getReorderList();
        boolean sidebarChangedInSync = false;
        int i = reorderList.size() - 1;
        while (i >= 0) {
            if (((IDisplayItem) reorderList.get(i)).getDisplayType() == DisplayItemType.DisplayItemFolder && EnpassApplication.getInstance().getKeychain().getFolderDataForUuid(((IDisplayItem) reorderList.get(i)).getDisplayIdentifier()).getTrashed()) {
                reorderList.remove(i);
                sidebarChangedInSync = true;
            }
            i--;
        }
        if (sidebarChangedInSync) {
            Set<String> listWithOrder = new HashSet();
            for (int count = 0; count < reorderList.size(); count += ADD_SHARED_ITEM) {
                listWithOrder.add(count + "=" + ((IDisplayItem) reorderList.get(count)).getDisplayIdentifier());
            }
            Editor sidebarPrefEditor = EnpassApplication.getInstance().getSharedPreferences(REORDERED_LIST, 0).edit();
            sidebarPrefEditor.putStringSet("list", listWithOrder);
            sidebarPrefEditor.commit();
            EnpassApplication.getInstance().getAppSettings().setDrawerChange(true);
        }
    }

    public void syncError(String Errormsg) {
        this.mDrawerListAdapter.stopSyncing();
        this.mDrawerListAdapter.setImageNonSyncBackground();
        EnpassApplication.getInstance().getAppSettings().setSyncErrorPref(true);
        try {
            ((AllCardsFragment) this.fragmentManager.findFragmentById(R.id.content_frame)).addSyncErrorNotification();
        } catch (Exception e) {
        }
        try {
            ((FolderFragment) this.fragmentManager.findFragmentById(R.id.content_frame)).addSyncErrorNotification();
        } catch (Exception e2) {
        }
    }

    public void setNonPremiumVersionNotification() {
        if (this.fragmentManager != null) {
            Fragment fragment = this.fragmentManager.findFragmentById(R.id.content_frame);
            if (fragment instanceof AllCardsFragment) {
                ((AllCardsFragment) fragment).addItemRemainingNotification();
                if (EnpassApplication.getInstance().getAppSettings().getSyncErrorPref()) {
                    ((AllCardsFragment) fragment).removeSyncErrorNotification();
                    ((AllCardsFragment) fragment).addSyncErrorNotification();
                }
            } else if (fragment instanceof FavoriteFragment) {
                ((FavoriteFragment) fragment).addItemRemainingNotification();
                if (EnpassApplication.getInstance().getAppSettings().getSyncErrorPref()) {
                    ((FavoriteFragment) fragment).removeSyncErrorNotification();
                    ((FavoriteFragment) fragment).addSyncErrorNotification();
                }
            } else if (fragment instanceof FolderFragment) {
                ((FolderFragment) fragment).addItemRemainingNotification();
                if (EnpassApplication.getInstance().getAppSettings().getSyncErrorPref()) {
                    ((FolderFragment) fragment).removeSyncErrorNotification();
                    ((FolderFragment) fragment).addSyncErrorNotification();
                }
            }
        }
    }

    public void syncPasswordError(IRemoteStorage aRemote) {
        this.mDrawerListAdapter.syncPasswordMissMatch();
    }

    public void syncAborted() {
        this.mDrawerListAdapter.stopSyncing();
    }

    public boolean onTouch(View view, MotionEvent event) {
        return this.mGestureDetector.onTouchEvent(event);
    }

    public void onSwipeRight() {
        Intent intent = new Intent(this.mContext, BrowserActivity.class);
        intent.putExtra("cardUrl", BuildConfig.FLAVOR);
        intent.putExtra("cardUuid", BuildConfig.FLAVOR);
        intent.putExtra("new_tab", false);
        this.mContext.startActivity(intent);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.smallestScreenWidthDp >= LoginActivity.TAB_WIDTH) {
            if (newConfig.orientation != ADD_SHARED_ITEM || newConfig.smallestScreenWidthDp < LoginActivity.TAB_WIDTH) {
                this.mIsBothPane = true;
                mIsDetailFrameVisible = false;
                if (this.mBrowserMenuItem != null) {
                    this.mBrowserMenuItem.setVisible(false);
                }
                this.mDrawerLayout.setDrawerLockMode(0);
            } else if (mIsDetailPresent) {
                this.mIsBothPane = false;
                mIsDetailFrameVisible = true;
                this.mContentFrame.setVisibility(8);
                this.mDrawerLayout.setDrawerLockMode(ADD_SHARED_ITEM);
            } else {
                this.mIsBothPane = false;
                mIsDetailFrameVisible = false;
                if (this.mContentFrame == null) {
                    this.mContentFrame = (RelativeLayout) findViewById(R.id.content_frame_layout);
                }
                this.mContentFrame.setVisibility(0);
                this.mContentFrame.getLayoutParams().width = -1;
                this.mDetailContentFrame.setVisibility(8);
                if (this.mBrowserMenuItem != null) {
                    this.mBrowserMenuItem.setVisible(true);
                }
                this.mDrawerLayout.setDrawerLockMode(0);
            }
        }
        if (EnpassApplication.getInstance().isRunningOnChromebook() && this.mBrowserMenuItem != null) {
            this.mBrowserMenuItem.setVisible(false);
        }
        super.onConfigurationChanged(newConfig);
        this.mDrawerToggle.onConfigurationChanged(newConfig);
        EnpassApplication.getInstance().changeLocale(this);
    }

    public void clearDetailMenus() {
        if (mTwoPane) {
            findViewById(R.id.content_detail_frame).setVisibility(4);
            this.mFragmentsDivider.setVisibility(0);
            this.mDetailToolbar.getMenu().clear();
            mIsDetailPresent = false;
            this.mDetailToolbar.inflateMenu(R.menu.main_activity_menu);
            this.mDetailToolbar.getMenu().findItem(R.id.action_search).setVisible(false);
            if (EnpassApplication.getInstance().isRunningOnChromebook()) {
                this.mDetailToolbar.getMenu().findItem(R.id.menu_browser).setVisible(false);
            } else {
                this.mDetailToolbar.getMenu().findItem(R.id.menu_browser).setVisible(true);
            }
            this.mDetailToolbar.setOnMenuItemClickListener(new 25(this));
        }
    }

    public void hideOrShowDetail() {
        findViewById(R.id.content_detail_frame).setVisibility(0);
        this.mFragmentsDivider.setVisibility(4);
        if (!this.mIsBothPane && getResources().getConfiguration().orientation == ADD_SHARED_ITEM) {
            this.mIsBothPane = false;
            this.mContentFrame.setVisibility(8);
            this.mDetailContentFrame.setVisibility(0);
            mIsDetailFrameVisible = true;
            this.mDrawerLayout.setDrawerLockMode(ADD_SHARED_ITEM);
        }
    }

    public void backFromDetailInPortraitMode() {
        if (!mTwoPane) {
            return;
        }
        if (mIsDetailFrameVisible && getResources().getConfiguration().orientation == ADD_SHARED_ITEM) {
            backFromDetailPage();
            if (!this.mInsideFolder) {
                this.mDrawerLayout.setDrawerLockMode(0);
                return;
            }
            return;
        }
        clearDetailMenus();
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.mDrawerToggle.syncState();
    }

    public void setCurrentSelectedFolder(String aFolder) {
        this.mCurrentFolder = aFolder;
    }

    public void appComeInForeground() {
        Uri sharedData = EnpassApplication.getInstance().getAppSettings().getSharedData();
        if (sharedData != null) {
            String scheme = sharedData.getScheme();
            String host = sharedData.getHost();
            Card card = new Card();
            if (!card.decodeShareUrl(sharedData)) {
                showCardImportMessage(R.string.share_card_import_unsuccess);
            } else if (EnpassApplication.getInstance().getKeychain() != null) {
                Card sharedCardMeta = EnpassApplication.getInstance().getKeychain().getCardWithUuid(card.getUuid());
                if (sharedCardMeta != null && !sharedCardMeta.isTrashed()) {
                    addOrUpdateSharedCard(UPDATE_SHARED_ITEM, card);
                } else if (sharedCardMeta == null || !sharedCardMeta.isTrashed()) {
                    addOrUpdateSharedCard(ADD_SHARED_ITEM, card);
                } else {
                    addOrUpdateSharedCard(ADD_TRASHED_ITEM, card);
                }
            }
            String aSharedText = null;
            ClipboardManager clipBoard = (ClipboardManager) getSystemService("clipboard");
            ClipData clipData = clipBoard.getPrimaryClip();
            if (clipData != null) {
                aSharedText = clipData.getItemAt(clipData.getItemCount() - 1).getText().toString();
            }
            if (aSharedText.startsWith("enpass://share")) {
                clipBoard.setPrimaryClip(ClipData.newPlainText(BuildConfig.FLAVOR, BuildConfig.FLAVOR));
            }
        }
    }

    private void addOrUpdateSharedCard(int value, Card card) {
        int msgId = -1;
        int positiveBtnId = -1;
        boolean showAddAsNewBtn = false;
        if (value == UPDATE_SHARED_ITEM) {
            msgId = R.string.share_card_update;
            positiveBtnId = R.string.update;
            showAddAsNewBtn = true;
        } else if (value == ADD_SHARED_ITEM) {
            msgId = R.string.share_card_add;
            positiveBtnId = R.string.add;
        } else if (value == ADD_TRASHED_ITEM) {
            msgId = R.string.share_card_add;
            positiveBtnId = R.string.add;
        }
        Builder builder = new Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setCancelable(false);
        String string = getString(msgId);
        Object[] objArr = new Object[ADD_SHARED_ITEM];
        objArr[0] = card.getDisplayName();
        builder.setMessage(String.format(string, objArr));
        builder.setPositiveButton(positiveBtnId, new 26(this, value, card));
        if (showAddAsNewBtn) {
            builder.setNeutralButton(R.string.add_as_new, new 27(this, card));
        }
        builder.setNegativeButton(R.string.cancel, new 28(this));
        builder.create().show();
    }

    private void showCardImportMessage(int msgId) {
        Builder builder = new Builder(this);
        builder.setMessage(msgId);
        builder.setNeutralButton(R.string.ok, new 29(this));
        builder.create().show();
    }

    private void getOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 82) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String extra = intent.getStringExtra("caller");
        intent.removeExtra("caller");
        setIntent(intent);
        if (extra != null && extra.equals("autofillnotification")) {
            startActivity(new Intent(this, NotificationFillActivity.class));
        }
    }

    void showBoxAlert() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setCancelable(false);
        builder.setMessage(this.mContext.getString(R.string.box_not_active));
        builder.setPositiveButton(this.mContext.getString(R.string.ok), new 30(this));
        builder.show();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(EnpassApplication.getInstance().changeLocale(base));
    }
}
