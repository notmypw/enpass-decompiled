package in.sinew.enpass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;
import com.github.clans.fab.BuildConfig;
import in.sinew.enpassengine.Category;
import in.sinew.enpassengine.IDisplayItem;
import in.sinew.enpassengine.TemplateFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;

public class AppSettings {
    private static final String ACCOUNT_PREF = "accountId_pref";
    public static final String ANDROID_WATCH_PREFERENCE = "enable_watch";
    public static final String ATTACHMENT_OPEN_OUTSIDE_WARNING_PREF = "attachment_open_outside_warning";
    public static final String ATTACHMENT_SHARE_OUTSIDE_WARNING_PREF = "attachment_share_outside_warning";
    public static final String ATTACHMENT_SYNC_SLOW_MSG_FISRT_TIME = "attachment_sync_slow_msg_first_time";
    public static final String ATTACHMENT_SYNC_SLOW_MSG_SHOW_TIME = "attachment_sync_slow_msg_show_time";
    public static final String AUTOFILL_ENABLED_IN_CHROMEBOOK = "autofill_enabled";
    public static final String AUTOLOCK_PREFERENCE = "auto_lock_interval";
    public static final String AUTO_SUBMIT_PREFERENCE = "auto_submit";
    private static String BOTTOM_OVERLAY_LAYOUT = "bottom_overlay_layout";
    private static final String BOX_DISABLE = "box_disabled";
    public static final String BROWSER_USER_AGENT = "user_agent";
    public static final String CHECK_UPDATE_PREF = "check_update";
    public static final String CLEAR_CLIPBOARD_PREFERENCE = "clearclipboardInterval";
    public static final String CLOSE_WARNING_PREFERENCE = "closeWarning";
    public static final String DEFAULT_SEARCH_PROVIDER_PREFERENCE = "default_search_engine";
    public static final String DONT_SHOW_SYNC_TURN_ON_PREF = "dont_show_sync_turn_on_dialog";
    public static String DRAWER_LIST_CATEGORY_CHANGE = "drawerListCategoryChange";
    private static final String ENABLE_ACCESSIBILTY_AUTOFILL_PREFERENCE = "accessibility_autofill";
    public static final String ENABLE_AUDIO_PREFERENCE = "enableAudio";
    private static final String ENABLE_SCREENSHOT_PREFERENCE = "enable_screenshot";
    public static final String FINGERPRINT_SCANNER_SHAREDPREFERENCE = "fingerprint_scanner_sharedpreference";
    public static final String HIDE_SENSITIVE_PREFERENCE = "hideSensitive";
    public static final String IMAGE_ATTACHMENT_WARNING_SHOW_PREF = "image_attachment_warning_show";
    public static final String INTEGRITY_REPORT_STATUS = "integrity_status";
    public static String IS_DRAWER_LIST_REORDERED = "isReordered";
    public static String IS_REMOTE_ACTIVE = "isRemoteActive";
    public static final String LANGUAGE_PREFERENCE = "language";
    public static String LAST_SELECTED_CATEGORY_PREFERENCE = "lastSelectedCategory";
    public static final String LOCK_ON_LEAVING_PREFERENCE = "lock_on_leaving";
    public static final String MATCH_URL_HOSTNAME_PREF = "match_url_hostname_pref";
    public static final String NOTIFICATION_AUTOFILL_PREF = "notification_pref";
    public static final String OPEN_LINK_IN_PREFERENCE = "open_links_in";
    private static final String ORDER_PREF = "orderId_pref";
    public static final String PINCODE_LENGTH = "pin_length";
    public static final String PORT_IN_CHROMEBOOK = "autofill_port";
    public static final String PREMIUM_VERSION_PREFERENCE = "premiumVersion";
    public static final String PRODUCT_TOUR_PREFERENCE = "product_tour";
    public static String PRONOUNCEABLE_DIGITS_PREF = "pronounceable_digit";
    public static String PRONOUNCEABLE_PREF = "pronounceable";
    public static String PRONOUNCEABLE_SYMBOLS_PREF = "pronounceable_symbol";
    public static String PRONOUNCEABLE_UPPERCASE_PREF = "pronounceable_uppercase";
    public static final String QUICK_UNLOCK_CODE_PREFERENCE = "quick_unlock_pin";
    public static String REORDERED_LIST = "reorderedList";
    private static final String RESTORE_PRO = "restore_pro";
    public static final String RESTORE_PWD_NOT_MATCH_PREF = "restore_pwd_not_match";
    private static final String ROOTED_DEVICE_WARNING_SHOWN = "rooted_device_warning_shown";
    private static final String SEARCH_IN_CARDS_PREFERENCE = "searchInCards";
    public static final String SEND_ANALYTICS_PREF = "send_analytics";
    public static final String SEND_CRASH_REPORT_PREF = "send_crash_report";
    public static String SHARED_DATA = "shared_data";
    public static final String SHOW_ONLY_TOTP_IN_WATCH = "show_only_totp_in_watch";
    private static final String SHOW_SUBTITLE_PREFERENCE = "showSubtitle";
    public static final String SHOW_SYNC_TURN_ON_DIALOG = "notify_sync_turn_on";
    public static final String SIGN_IN_AS_TEAM_EMAIL_ID_PREF = "sign_in_team_email_id";
    public static final String SIGN_IN_AS_TEAM_ORG_ID_PREF = "sign_in_team_org_id";
    public static final String SIGN_IN_AS_TEAM_TIME_PREF = "sign_in_team_time";
    public static final String SIGN_IN_AS_TEAM_TOKEN_PREF = "sign_in_team_token";
    public static final String SIGN_IN_AS_TEAM_TOKEN_VALID_PREF = "sign_in_team_token_valid";
    public static final String SOUND_PREF = "sound_on";
    public static final String SYNC_ERROR_PREF = "sync_error";
    public static String SYNC_REMOTE_IDENTIFIER = "remoteIdentifier";
    public static String SYNC_USER_SIGNINID = "sync_signInId";
    public static final String TEAM_ACCOUNT_CREDENTIAL_PREF = "team_account_credential";
    private static String TOP_OVERLAY_LAYOUT = "top_overlay_layout";
    public static final String USER_REMOVE_NOTIFICATION_AFTER_10_CARD = "remove_notification_after_10cards";
    public static final String USER_REMOVE_NOTIFICATION_BEFORE_10_CARD = "remove_notification_before_10cards";
    public static final String VIBRATE_PREF = "vibration_on";
    public static String VISIBLE_CATEGORIES_IN_SIDEBAR = "visibleCategories";
    public static final String WATCH_PIN_ENABLED = "watch_pin_enabled";
    private static final String WEBDAV_SERVER_URL = "webdav_server_url";
    public static final String mFacebook = EnpassApplication.getInstance().facebookUrl;
    public static final String mFaqUrl = EnpassApplication.getInstance().faqUrl;
    public static final String mTwitterUrl = EnpassApplication.getInstance().twitterUrl;
    private final String ALWAYS_ASK;
    private final int BAIDU_SEARCH;
    private final int GOOGLE_SEARCH;
    String blogUrl;
    String deviceName;
    String forumUrl;
    String locale;
    private boolean mAudioEnable;
    public int mAutoLockInterval;
    private int mBrowserUserAgent;
    private int mClearClipboardInterval;
    private int mDefaultSearchProvider;
    private boolean mFingerprintScannerStatus;
    public boolean mHideSensitive;
    private boolean mIsAutoSubmitEnable;
    public boolean mIsCloseWarningEnable;
    private String mLanguage;
    private boolean mLastSelectedCategoryEnable;
    private boolean mLockOnLeaving;
    private String mOpenBrowserIn;
    private String mProduct;
    private boolean mQuickUnlock;
    private int mSearchInCards;
    private boolean mShowSubtitle;
    String recommend;
    String system;
    String version;
    String writeUsEmail;

    public AppSettings() {
        this.mProduct = EnpassApplication.getInstance().mProduct;
        this.version = EnpassApplication.getInstance().version;
        this.system = EnpassApplication.getInstance().system;
        this.deviceName = EnpassApplication.getInstance().deviceName;
        this.locale = EnpassApplication.getInstance().locale;
        this.writeUsEmail = EnpassApplication.getInstance().writeUsEmail;
        this.blogUrl = EnpassApplication.getInstance().blogUrl;
        this.recommend = EnpassApplication.getInstance().recommend;
        this.forumUrl = EnpassApplication.getInstance().forumUrl;
        this.ALWAYS_ASK = "ALWAYS_ASK";
        this.mShowSubtitle = true;
        this.BAIDU_SEARCH = 4;
        this.GOOGLE_SEARCH = 0;
        this.mOpenBrowserIn = EnpassApplication.getInstance().getSharedPreferences(OPEN_LINK_IN_PREFERENCE, 0).getString("openLinkIn", "ALWAYS_ASK");
        this.mHideSensitive = EnpassApplication.getInstance().getSharedPreferences(HIDE_SENSITIVE_PREFERENCE, 0).getBoolean("sensitive", true);
        this.mAudioEnable = EnpassApplication.getInstance().getSharedPreferences(ENABLE_AUDIO_PREFERENCE, 0).getBoolean("isAudioEnable", true);
        this.mIsCloseWarningEnable = EnpassApplication.getInstance().getSharedPreferences(CLOSE_WARNING_PREFERENCE, 0).getBoolean("isCloseWarningEnable", true);
        this.mAutoLockInterval = EnpassApplication.getInstance().getSharedPreferences(AUTOLOCK_PREFERENCE, 0).getInt("autolock", 1);
        this.mClearClipboardInterval = EnpassApplication.getInstance().getSharedPreferences(CLEAR_CLIPBOARD_PREFERENCE, 0).getInt("clipboardInterval", 0);
        this.mQuickUnlock = EnpassApplication.getInstance().getSharedPreferences(QUICK_UNLOCK_CODE_PREFERENCE, 0).getBoolean("quick_unlock_code", false);
        this.mFingerprintScannerStatus = EnpassApplication.getInstance().getSharedPreferences(FINGERPRINT_SCANNER_SHAREDPREFERENCE, 0).getBoolean("fingerprint", false);
        this.mLockOnLeaving = EnpassApplication.getInstance().getSharedPreferences(LOCK_ON_LEAVING_PREFERENCE, 0).getBoolean(LOCK_ON_LEAVING_PREFERENCE, true);
        this.mLanguage = EnpassApplication.getInstance().getSharedPreferences(LANGUAGE_PREFERENCE, 0).getString(LANGUAGE_PREFERENCE, "default");
        this.mLastSelectedCategoryEnable = EnpassApplication.getInstance().getSharedPreferences(LAST_SELECTED_CATEGORY_PREFERENCE, 0).getBoolean(LAST_SELECTED_CATEGORY_PREFERENCE, true);
        this.mShowSubtitle = EnpassApplication.getInstance().getSharedPreferences(SHOW_SUBTITLE_PREFERENCE, 0).getBoolean(SHOW_SUBTITLE_PREFERENCE, true);
        this.mSearchInCards = EnpassApplication.getInstance().getSharedPreferences(SEARCH_IN_CARDS_PREFERENCE, 0).getInt(SEARCH_IN_CARDS_PREFERENCE, 1);
        this.mIsAutoSubmitEnable = EnpassApplication.getInstance().getSharedPreferences(AUTO_SUBMIT_PREFERENCE, 0).getBoolean(AUTO_SUBMIT_PREFERENCE, true);
        SharedPreferences defaultSearchPref = EnpassApplication.getInstance().getSharedPreferences(DEFAULT_SEARCH_PROVIDER_PREFERENCE, 0);
        if (Locale.getDefault().toString().equals("zh_CN")) {
            this.mDefaultSearchProvider = defaultSearchPref.getInt(DEFAULT_SEARCH_PROVIDER_PREFERENCE, 4);
        } else {
            this.mDefaultSearchProvider = defaultSearchPref.getInt(DEFAULT_SEARCH_PROVIDER_PREFERENCE, 0);
        }
        this.mBrowserUserAgent = EnpassApplication.getInstance().getSharedPreferences(BROWSER_USER_AGENT, 0).getInt(BROWSER_USER_AGENT, 0);
    }

    public void setAutoSubmitEnable(boolean enable) {
        this.mIsAutoSubmitEnable = enable;
    }

    public boolean getAutoSubmitEnable() {
        return this.mIsAutoSubmitEnable;
    }

    public void setDefaultSearchProvider(int search) {
        this.mDefaultSearchProvider = search;
    }

    public int getDefaultSearchProvider() {
        return this.mDefaultSearchProvider;
    }

    public void setBrowserUserAgent(int UA) {
        this.mBrowserUserAgent = UA;
    }

    public int getBrowserUserAgent() {
        return this.mBrowserUserAgent;
    }

    public boolean getLastSelectedCategoryEnable() {
        return this.mLastSelectedCategoryEnable;
    }

    public void setLastSelectedCategoryEnable(boolean aLastSelectedCategoryEnable) {
        this.mLastSelectedCategoryEnable = aLastSelectedCategoryEnable;
    }

    public void setShowSubtitle(boolean show) {
        this.mShowSubtitle = show;
    }

    public boolean getShowSubtitle() {
        return this.mShowSubtitle;
    }

    public void setSearchInCards(int value) {
        this.mSearchInCards = value;
    }

    public int getSearchInCards() {
        return this.mSearchInCards;
    }

    public int getAutoLockInterval() {
        return this.mAutoLockInterval;
    }

    public void setAutoLockInterval(int aInterval) {
        this.mAutoLockInterval = aInterval;
    }

    public void setCloseWarning(boolean isEnable) {
        this.mIsCloseWarningEnable = isEnable;
    }

    public void setAudioEnable(boolean audioEnable) {
        this.mAudioEnable = audioEnable;
    }

    public boolean getAudioEnable() {
        return this.mAudioEnable;
    }

    public boolean getHideSensitive() {
        return this.mHideSensitive;
    }

    public void setHideSensitive(boolean aSensitive) {
        this.mHideSensitive = aSensitive;
    }

    public void setClearClipboardInterval(int aInterval) {
        this.mClearClipboardInterval = aInterval;
    }

    public int getClearClipboardInterval() {
        return this.mClearClipboardInterval;
    }

    public void setQuickUnlock(boolean aQuickUnlock) {
        this.mQuickUnlock = aQuickUnlock;
    }

    public boolean getQuickUnlock() {
        return this.mQuickUnlock;
    }

    public void setFingerprintScannerStatus(boolean isEnable) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(FINGERPRINT_SCANNER_SHAREDPREFERENCE, 0).edit();
        edit.putBoolean("fingerprint", isEnable);
        edit.commit();
        this.mFingerprintScannerStatus = isEnable;
    }

    public boolean getFingerprintScannerStatus() {
        return this.mFingerprintScannerStatus;
    }

    public String getFacebookUrl() {
        return mFacebook;
    }

    public String getTwitterUrl() {
        return mTwitterUrl;
    }

    public void setOpenBrowserIn(String openIn) {
        this.mOpenBrowserIn = openIn;
    }

    public String getOpenBrowserIn() {
        return this.mOpenBrowserIn;
    }

    public String getFaqUrl() {
        return mFaqUrl;
    }

    public String getProduct() {
        return this.mProduct;
    }

    public String getVersion() {
        return this.version;
    }

    public String getSystem() {
        return this.system;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public String getLocale() {
        return this.locale;
    }

    public String getWriteUs() {
        return this.writeUsEmail;
    }

    public String getBlogUrl() {
        return this.blogUrl;
    }

    public String getForumUrl() {
        return this.forumUrl;
    }

    public String getManualUrl() {
        return EnpassApplication.getInstance().manualUrl;
    }

    public String getChromebookUserManualUrl() {
        return EnpassApplication.getInstance().chromebookUserManualUrl;
    }

    public String getRecommend() {
        return this.recommend;
    }

    public void setLanguage(String aLanguage) {
        this.mLanguage = aLanguage;
    }

    public String getLanguage() {
        return this.mLanguage;
    }

    public void setLockOnLeaving(boolean aLockOnLeaving) {
        this.mLockOnLeaving = aLockOnLeaving;
    }

    public boolean getLockOnLeaving() {
        return this.mLockOnLeaving;
    }

    public boolean getIsShowSyncOnDialog() {
        return EnpassApplication.getInstance().getSharedPreferences(SHOW_SYNC_TURN_ON_DIALOG, 0).getBoolean(SHOW_SYNC_TURN_ON_DIALOG, true);
    }

    public void setIsShowSyncOnDialog(boolean show) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(SHOW_SYNC_TURN_ON_DIALOG, 0).edit();
        edit.putBoolean(SHOW_SYNC_TURN_ON_DIALOG, show);
        edit.commit();
    }

    public boolean isBrowserButtonOverlayVisible() {
        return EnpassApplication.getInstance().getSharedPreferences(BOTTOM_OVERLAY_LAYOUT, 0).getBoolean(BOTTOM_OVERLAY_LAYOUT, true);
    }

    public void setMainPageBrowserButtonOverlay(boolean isVisible) {
        Editor editor = EnpassApplication.getInstance().getSharedPreferences(BOTTOM_OVERLAY_LAYOUT, 0).edit();
        editor.putBoolean(BOTTOM_OVERLAY_LAYOUT, isVisible);
        editor.commit();
    }

    public void setRemote(int remoteId) {
        Editor remotePrefEditor = EnpassApplication.getInstance().getSharedPreferences(SYNC_REMOTE_IDENTIFIER, 0).edit();
        remotePrefEditor.putInt("identifier", remoteId);
        remotePrefEditor.commit();
    }

    public int getRemote() {
        return EnpassApplication.getInstance().getSharedPreferences(SYNC_REMOTE_IDENTIFIER, 0).getInt("identifier", -1);
    }

    public void setRemoteActive(boolean isActive) {
        Editor remotePrefEditor = EnpassApplication.getInstance().getSharedPreferences(IS_REMOTE_ACTIVE, 0).edit();
        remotePrefEditor.putBoolean("remoteActive", isActive);
        remotePrefEditor.commit();
    }

    public void setWebDavServerUrl(String url) {
        Editor remotePrefEditor = EnpassApplication.getInstance().getSharedPreferences(WEBDAV_SERVER_URL, 0).edit();
        remotePrefEditor.putString(WEBDAV_SERVER_URL, url);
        remotePrefEditor.commit();
    }

    public String getWebDavServerUrl() {
        return EnpassApplication.getInstance().getSharedPreferences(WEBDAV_SERVER_URL, 0).getString(WEBDAV_SERVER_URL, BuildConfig.FLAVOR);
    }

    public boolean isRemoteActive() {
        return EnpassApplication.getInstance().getSharedPreferences(IS_REMOTE_ACTIVE, 0).getBoolean("remoteActive", false);
    }

    public void setSigninId(String signinId) {
        Editor remotePrefEditor = EnpassApplication.getInstance().getSharedPreferences(SYNC_USER_SIGNINID, 0).edit();
        remotePrefEditor.putString("signinId", signinId);
        remotePrefEditor.commit();
    }

    public String getSigninId() {
        return EnpassApplication.getInstance().getSharedPreferences(SYNC_USER_SIGNINID, 0).getString("signinId", BuildConfig.FLAVOR);
    }

    public void setPronounceableChecked(boolean isChecked) {
        Editor pronouncablePrefEditor = EnpassApplication.getInstance().getSharedPreferences(PRONOUNCEABLE_PREF, 0).edit();
        pronouncablePrefEditor.putBoolean("isPronounceable", isChecked);
        pronouncablePrefEditor.commit();
    }

    public boolean isPronounceable() {
        return EnpassApplication.getInstance().getSharedPreferences(PRONOUNCEABLE_PREF, 0).getBoolean("isPronounceable", false);
    }

    public void setPronounceableDigitchecked(boolean isChecked) {
        Editor pronouncablePrefEditor = EnpassApplication.getInstance().getSharedPreferences(PRONOUNCEABLE_DIGITS_PREF, 0).edit();
        pronouncablePrefEditor.putBoolean("isDigitChecked", isChecked);
        pronouncablePrefEditor.commit();
    }

    public boolean isPronounceableDigitchecked() {
        return EnpassApplication.getInstance().getSharedPreferences(PRONOUNCEABLE_DIGITS_PREF, 0).getBoolean("isDigitChecked", false);
    }

    public void setPronounceableSymbolchecked(boolean isChecked) {
        Editor pronouncablePrefEditor = EnpassApplication.getInstance().getSharedPreferences(PRONOUNCEABLE_SYMBOLS_PREF, 0).edit();
        pronouncablePrefEditor.putBoolean("isSymbolChecked", isChecked);
        pronouncablePrefEditor.commit();
    }

    public boolean isPronounceableSymbolchecked() {
        return EnpassApplication.getInstance().getSharedPreferences(PRONOUNCEABLE_SYMBOLS_PREF, 0).getBoolean("isSymbolChecked", false);
    }

    public void setPronounceableUppercasechecked(boolean isChecked) {
        Editor pronouncablePrefEditor = EnpassApplication.getInstance().getSharedPreferences(PRONOUNCEABLE_UPPERCASE_PREF, 0).edit();
        pronouncablePrefEditor.putBoolean("isUppercaseChecked", isChecked);
        pronouncablePrefEditor.commit();
    }

    public boolean isPronounceableUppercasechecked() {
        return EnpassApplication.getInstance().getSharedPreferences(PRONOUNCEABLE_UPPERCASE_PREF, 0).getBoolean("isUppercaseChecked", false);
    }

    public void addFolderInDrawer(String folderUuid) {
        Set<String> reorderedSidebarSet = new HashSet(EnpassApplication.getInstance().getSharedPreferences(REORDERED_LIST, 0).getStringSet("list", new HashSet()));
        for (String value : reorderedSidebarSet) {
            if (folderUuid.equals(value.split("\\=")[1])) {
                return;
            }
        }
        reorderedSidebarSet.add((reorderedSidebarSet.size() + 10) + "=" + folderUuid);
        Editor sidebarPrefEditor = EnpassApplication.getInstance().getSharedPreferences(REORDERED_LIST, 0).edit();
        sidebarPrefEditor.putStringSet("list", reorderedSidebarSet);
        sidebarPrefEditor.commit();
    }

    public void removeFolderFromDrawer(String folderUuid) {
        try {
            Set<String> reorderedSidebarSet = new HashSet(EnpassApplication.getInstance().getSharedPreferences(REORDERED_LIST, 0).getStringSet("list", new HashSet()));
            String removedItem = null;
            for (String item : reorderedSidebarSet) {
                if (item.contains(folderUuid)) {
                    removedItem = item;
                }
            }
            reorderedSidebarSet.remove(removedItem);
            int removedPos = Integer.parseInt(removedItem.split("\\=")[0]);
            TreeMap<Integer, String> valueOrderPair = new TreeMap();
            for (String value : reorderedSidebarSet) {
                String[] split = value.split("\\=");
                int order = Integer.parseInt(split[0]);
                if (removedPos < order) {
                    order--;
                }
                valueOrderPair.put(Integer.valueOf(order), split[1]);
            }
            Set<String> updatedSet = new HashSet();
            for (Integer key : valueOrderPair.keySet()) {
                updatedSet.add(key + "=" + ((String) valueOrderPair.get(key)));
            }
            Editor sidebarPrefEditor = EnpassApplication.getInstance().getSharedPreferences(REORDERED_LIST, 0).edit();
            sidebarPrefEditor.putStringSet("list", updatedSet);
            sidebarPrefEditor.commit();
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(EnpassApplication.getInstance(), e.getMessage(), 1).show();
        }
    }

    public List<String> getOrderedListFromPref() {
        Set<String> reorderedSidebarSet = new HashSet(EnpassApplication.getInstance().getSharedPreferences(REORDERED_LIST, 0).getStringSet("list", new HashSet()));
        TreeMap<Integer, String> valueOrderPair = new TreeMap();
        for (String value : reorderedSidebarSet) {
            String[] split = value.split("\\=");
            valueOrderPair.put(Integer.valueOf(Integer.parseInt(split[0])), split[1]);
        }
        return new ArrayList(valueOrderPair.values());
    }

    public List<IDisplayItem> getReorderList() {
        return getIDisplayItemFromIdentifiers((ArrayList) getOrderedListFromPref());
    }

    public List<String> getDrawerVisibleCategories() {
        return new ArrayList(new HashSet(EnpassApplication.getInstance().getSharedPreferences(VISIBLE_CATEGORIES_IN_SIDEBAR, 0).getStringSet("categoriesList", new HashSet())));
    }

    public ArrayList<IDisplayItem> getIDisplayItemFromIdentifiers(List<String> aList) {
        ArrayList<IDisplayItem> sidebarList = new ArrayList();
        IDisplayItem item = null;
        ArrayList<Category> categoryList = (ArrayList) TemplateFactory.getCategoryWithoutIdentity();
        for (String identifier : aList) {
            if (EnpassApplication.getInstance().getKeychain() != null) {
                item = EnpassApplication.getInstance().getKeychain().getFolderForIdentifier(identifier);
            }
            if (item == null) {
                Iterator it = categoryList.iterator();
                while (it.hasNext()) {
                    Category category = (Category) it.next();
                    if (category.getType().equals(identifier)) {
                        item = category;
                        break;
                    }
                }
            }
            if (item != null) {
                sidebarList.add(item);
            }
        }
        return sidebarList;
    }

    public void setDrawerChange(boolean isChange) {
        Editor drawerListPref = EnpassApplication.getInstance().getSharedPreferences(IS_DRAWER_LIST_REORDERED, 0).edit();
        drawerListPref.putBoolean("reordered", isChange);
        drawerListPref.commit();
    }

    public boolean isDrawerChange() {
        return EnpassApplication.getInstance().getSharedPreferences(IS_DRAWER_LIST_REORDERED, 0).getBoolean("reordered", false);
    }

    private List<String> createDrawableListWithOrder() {
        List<Category> categoryList = TemplateFactory.getCategoryWithoutIdentity();
        Category identityCategory = null;
        for (Category category : categoryList) {
            if (category.getDisplayIdentifier().equals("identity")) {
                identityCategory = category;
            }
        }
        if (identityCategory != null) {
            categoryList.remove(identityCategory);
        }
        Set<String> listWithOrder = new HashSet();
        for (int count = 0; count < categoryList.size(); count++) {
            listWithOrder.add(count + "=" + ((Category) categoryList.get(count)).getDisplayIdentifier());
        }
        Editor sidebarPrefEditor = EnpassApplication.getInstance().getSharedPreferences(REORDERED_LIST, 0).edit();
        sidebarPrefEditor.putStringSet("list", listWithOrder);
        sidebarPrefEditor.commit();
        Set<String> visibleCategoriesSet = new HashSet();
        for (Category item : categoryList) {
            visibleCategoriesSet.add(item.getDisplayIdentifier());
        }
        Editor sidebarCategoriesPrefEditor = EnpassApplication.getInstance().getSharedPreferences(VISIBLE_CATEGORIES_IN_SIDEBAR, 0).edit();
        sidebarCategoriesPrefEditor.putStringSet("categoriesList", visibleCategoriesSet);
        sidebarCategoriesPrefEditor.commit();
        return new ArrayList(listWithOrder);
    }

    public void initializeDrawerList() {
        if (getReorderList().size() == 0) {
            createDrawableListWithOrder();
        }
    }

    public void saveSharedData(Uri data) {
        Editor shareDataEditor = EnpassApplication.getInstance().getSharedPreferences(SHARED_DATA, 0).edit();
        if (data != null) {
            shareDataEditor.putString("uri", data.toString());
        } else {
            shareDataEditor.putString("uri", null);
        }
        shareDataEditor.commit();
    }

    public Uri getSharedData() {
        String data = EnpassApplication.getInstance().getSharedPreferences(SHARED_DATA, 0).getString("uri", null);
        if (data != null) {
            return Uri.parse(data);
        }
        return null;
    }

    public void saveRestorePurchase(boolean isPro) {
        Editor purchasePrefEditor = EnpassApplication.getInstance().getSharedPreferences(RESTORE_PRO, 0).edit();
        purchasePrefEditor.putBoolean("pro_version", isPro);
        purchasePrefEditor.commit();
    }

    public boolean isPremiumVersion() {
        return EnpassApplication.getInstance().getSharedPreferences(PREMIUM_VERSION_PREFERENCE, 0).getBoolean(PREMIUM_VERSION_PREFERENCE, false);
    }

    public boolean getRestorePurchased() {
        return EnpassApplication.getInstance().getSharedPreferences(RESTORE_PRO, 0).getBoolean("pro_version", false);
    }

    public void saveCredentialsForFreePurchase(String accountId) {
        Editor accountPrefEditor = EnpassApplication.getInstance().getSharedPreferences(ACCOUNT_PREF, 0).edit();
        accountPrefEditor.putString("mail", accountId);
        accountPrefEditor.commit();
    }

    public void saveCredentials(String orderId, String accountId) {
        Editor accountPrefEditor = EnpassApplication.getInstance().getSharedPreferences(ACCOUNT_PREF, 0).edit();
        accountPrefEditor.putString("mail", accountId);
        accountPrefEditor.commit();
        Editor orderPrefEditor = EnpassApplication.getInstance().getSharedPreferences(ORDER_PREF, 0).edit();
        orderPrefEditor.putString("orderId", orderId);
        orderPrefEditor.commit();
    }

    public String getRegisteredAccount() {
        return EnpassApplication.getInstance().getSharedPreferences(ACCOUNT_PREF, 0).getString("mail", BuildConfig.FLAVOR);
    }

    public void setVibrationOnKeypress(boolean vibrate) {
        Editor vibratePrefEditor = EnpassApplication.getInstance().getSharedPreferences(VIBRATE_PREF, 0).edit();
        vibratePrefEditor.putBoolean("is_vibrate", vibrate);
        vibratePrefEditor.commit();
    }

    public boolean isVibrationON() {
        return EnpassApplication.getInstance().getSharedPreferences(VIBRATE_PREF, 0).getBoolean("is_vibrate", true);
    }

    public void setSoundOnKeypress(boolean soundOn) {
        Editor soundPrefEditor = EnpassApplication.getInstance().getSharedPreferences(SOUND_PREF, 0).edit();
        soundPrefEditor.putBoolean("is_sound_on", soundOn);
        soundPrefEditor.commit();
    }

    public boolean isSoundON() {
        return EnpassApplication.getInstance().getSharedPreferences(SOUND_PREF, 0).getBoolean("is_sound_on", false);
    }

    public void savePinCodeLength(int pinLength) {
        Editor prefEditor = EnpassApplication.getInstance().getSharedPreferences(PINCODE_LENGTH, 0).edit();
        prefEditor.putInt("pinLength", pinLength);
        prefEditor.commit();
    }

    public int getPinCodeLength() {
        return EnpassApplication.getInstance().getSharedPreferences(PINCODE_LENGTH, 0).getInt("pinLength", 4);
    }

    public void setNotificationAutofillOn(boolean notificationOn) {
        Intent intent = new Intent("filling_notification");
        intent.putExtra("notification_value", notificationOn);
        LocalBroadcastManager.getInstance(EnpassApplication.getInstance()).sendBroadcast(intent);
        Editor notificationPrefEditor = EnpassApplication.getInstance().getSharedPreferences(NOTIFICATION_AUTOFILL_PREF, 0).edit();
        notificationPrefEditor.putBoolean("notification_on", notificationOn);
        notificationPrefEditor.commit();
    }

    public boolean isNotificationAutofillON() {
        return EnpassApplication.getInstance().getSharedPreferences(NOTIFICATION_AUTOFILL_PREF, 0).getBoolean("notification_on", false);
    }

    public void setBoxDisableAlert(boolean disable) {
        Editor prefEditor = EnpassApplication.getInstance().getSharedPreferences(BOX_DISABLE, 0).edit();
        prefEditor.putBoolean("disabled", disable);
        prefEditor.commit();
    }

    public boolean getBoxDisableAlert() {
        return EnpassApplication.getInstance().getSharedPreferences(BOX_DISABLE, 0).getBoolean("disabled", false);
    }

    public boolean isWatchEnabled() {
        return EnpassApplication.getInstance().getSharedPreferences(ANDROID_WATCH_PREFERENCE, 0).getBoolean("watch_enable_value", false);
    }

    public void setWatchEnabled(boolean enable) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(ANDROID_WATCH_PREFERENCE, 0).edit();
        edit.putBoolean("watch_enable_value", enable);
        edit.commit();
    }

    public boolean showOnlyTotpInWatch() {
        return EnpassApplication.getInstance().getSharedPreferences(SHOW_ONLY_TOTP_IN_WATCH, 0).getBoolean("totp_only", true);
    }

    public void setShowOnlyTotpInWatch(boolean enable) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(SHOW_ONLY_TOTP_IN_WATCH, 0).edit();
        edit.putBoolean("totp_only", enable);
        edit.commit();
    }

    public boolean isWatchPinEnabled() {
        return EnpassApplication.getInstance().getSharedPreferences(WATCH_PIN_ENABLED, 0).getBoolean("pin_enable", false);
    }

    public void setWatchPinEnabled(boolean enable) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(WATCH_PIN_ENABLED, 0).edit();
        edit.putBoolean("pin_enable", enable);
        edit.commit();
    }

    public boolean isIntegrityReportSent() {
        return EnpassApplication.getInstance().getSharedPreferences(INTEGRITY_REPORT_STATUS, 0).getBoolean("integrity_report", false);
    }

    public void setIntegrityReportSent(boolean sent) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(INTEGRITY_REPORT_STATUS, 0).edit();
        edit.putBoolean("integrity_report", sent);
        edit.commit();
    }

    public void saveAutofillEnabledInChromebook(boolean isEnabled) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(AUTOFILL_ENABLED_IN_CHROMEBOOK, 0).edit();
        edit.putBoolean(AUTOFILL_ENABLED_IN_CHROMEBOOK, isEnabled);
        edit.commit();
    }

    public boolean isAutofillEnabledInChromebook() {
        return EnpassApplication.getInstance().getSharedPreferences(AUTOFILL_ENABLED_IN_CHROMEBOOK, 0).getBoolean(AUTOFILL_ENABLED_IN_CHROMEBOOK, false);
    }

    public void savePort(int port) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(PORT_IN_CHROMEBOOK, 0).edit();
        edit.putInt("port", port);
        edit.commit();
    }

    public int getPort() {
        return EnpassApplication.getInstance().getSharedPreferences(PORT_IN_CHROMEBOOK, 0).getInt("port", 10391);
    }

    public void setMatchHostname(boolean isEnabled) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(MATCH_URL_HOSTNAME_PREF, 0).edit();
        edit.putBoolean(MATCH_URL_HOSTNAME_PREF, isEnabled);
        edit.commit();
    }

    public boolean isMatchHostnameEnabled() {
        return EnpassApplication.getInstance().getSharedPreferences(MATCH_URL_HOSTNAME_PREF, 0).getBoolean(MATCH_URL_HOSTNAME_PREF, true);
    }

    public void saveCredentialsForTeamPurchase(String accountId) {
        Editor accountPrefEditor = EnpassApplication.getInstance().getSharedPreferences(TEAM_ACCOUNT_CREDENTIAL_PREF, 0).edit();
        accountPrefEditor.putString(TEAM_ACCOUNT_CREDENTIAL_PREF, accountId);
        accountPrefEditor.commit();
    }

    public String getSaveCredentialForTeamPurchase() {
        return EnpassApplication.getInstance().getSharedPreferences(TEAM_ACCOUNT_CREDENTIAL_PREF, 0).getString(TEAM_ACCOUNT_CREDENTIAL_PREF, BuildConfig.FLAVOR);
    }

    public void setProductTourPref(boolean value) {
        Editor ProTourEditor = EnpassApplication.getInstance().getSharedPreferences(PRODUCT_TOUR_PREFERENCE, 0).edit();
        ProTourEditor.putBoolean(PRODUCT_TOUR_PREFERENCE, value);
        ProTourEditor.commit();
    }

    public boolean getProductTourPref() {
        return EnpassApplication.getInstance().getSharedPreferences(PRODUCT_TOUR_PREFERENCE, 0).getBoolean(PRODUCT_TOUR_PREFERENCE, false);
    }

    public void setRestorePwdNotMatchPref(boolean value) {
        Editor ProTourEditor = EnpassApplication.getInstance().getSharedPreferences(RESTORE_PWD_NOT_MATCH_PREF, 0).edit();
        ProTourEditor.putBoolean(RESTORE_PWD_NOT_MATCH_PREF, value);
        ProTourEditor.commit();
    }

    public boolean getRestorePwdNotMatchPref() {
        return EnpassApplication.getInstance().getSharedPreferences(RESTORE_PWD_NOT_MATCH_PREF, 0).getBoolean(RESTORE_PWD_NOT_MATCH_PREF, false);
    }

    public void setRemoveNotificationBefore10CardPref(boolean value) {
        Editor RemoveNotificationBefore10CardPrefEditor = EnpassApplication.getInstance().getSharedPreferences(USER_REMOVE_NOTIFICATION_BEFORE_10_CARD, 0).edit();
        RemoveNotificationBefore10CardPrefEditor.putBoolean(USER_REMOVE_NOTIFICATION_BEFORE_10_CARD, value);
        RemoveNotificationBefore10CardPrefEditor.commit();
    }

    public boolean getRemoveNotificationBefore10CardPref() {
        return EnpassApplication.getInstance().getSharedPreferences(USER_REMOVE_NOTIFICATION_BEFORE_10_CARD, 0).getBoolean(USER_REMOVE_NOTIFICATION_BEFORE_10_CARD, false);
    }

    public void setRemoveNotificationAfter10CardPref(boolean value) {
        Editor RemoveNotificationAfter10CardPrefEditor = EnpassApplication.getInstance().getSharedPreferences(USER_REMOVE_NOTIFICATION_AFTER_10_CARD, 0).edit();
        RemoveNotificationAfter10CardPrefEditor.putBoolean(USER_REMOVE_NOTIFICATION_AFTER_10_CARD, value);
        RemoveNotificationAfter10CardPrefEditor.commit();
    }

    public boolean getRemoveNotificationAfter10CardPref() {
        return EnpassApplication.getInstance().getSharedPreferences(USER_REMOVE_NOTIFICATION_AFTER_10_CARD, 0).getBoolean(USER_REMOVE_NOTIFICATION_AFTER_10_CARD, false);
    }

    public void setSyncErrorPref(boolean value) {
        Editor SyncErrorPrefEditor = EnpassApplication.getInstance().getSharedPreferences(SYNC_ERROR_PREF, 0).edit();
        SyncErrorPrefEditor.putBoolean(SYNC_ERROR_PREF, value);
        SyncErrorPrefEditor.commit();
    }

    public boolean getSyncErrorPref() {
        return EnpassApplication.getInstance().getSharedPreferences(SYNC_ERROR_PREF, 0).getBoolean(SYNC_ERROR_PREF, false);
    }

    public void setImageAttachmentWaringShowPref(boolean value) {
        Editor ImageAttachmentWaringShowPrefEditor = EnpassApplication.getInstance().getSharedPreferences(IMAGE_ATTACHMENT_WARNING_SHOW_PREF, 0).edit();
        ImageAttachmentWaringShowPrefEditor.putBoolean(IMAGE_ATTACHMENT_WARNING_SHOW_PREF, value);
        ImageAttachmentWaringShowPrefEditor.commit();
    }

    public boolean getImageAttachmentWaringShowPref() {
        return EnpassApplication.getInstance().getSharedPreferences(IMAGE_ATTACHMENT_WARNING_SHOW_PREF, 0).getBoolean(IMAGE_ATTACHMENT_WARNING_SHOW_PREF, true);
    }

    public void setAttachmentOpenOutsideWarningPref(boolean value) {
        Editor attachmentOpenOutsideWarningEditor = EnpassApplication.getInstance().getSharedPreferences(ATTACHMENT_OPEN_OUTSIDE_WARNING_PREF, 0).edit();
        attachmentOpenOutsideWarningEditor.putBoolean(ATTACHMENT_OPEN_OUTSIDE_WARNING_PREF, value);
        attachmentOpenOutsideWarningEditor.commit();
    }

    public boolean getAttachmentOpenOutsideWarningPref() {
        return EnpassApplication.getInstance().getSharedPreferences(ATTACHMENT_OPEN_OUTSIDE_WARNING_PREF, 0).getBoolean(ATTACHMENT_OPEN_OUTSIDE_WARNING_PREF, true);
    }

    public void setAttachmentShareOutsideWarningPref(boolean value) {
        Editor attachmentShareOutsideWarningPrefEditor = EnpassApplication.getInstance().getSharedPreferences(ATTACHMENT_SHARE_OUTSIDE_WARNING_PREF, 0).edit();
        attachmentShareOutsideWarningPrefEditor.putBoolean(ATTACHMENT_SHARE_OUTSIDE_WARNING_PREF, value);
        attachmentShareOutsideWarningPrefEditor.commit();
    }

    public boolean getAttachmentShareOutsideWarningPref() {
        return EnpassApplication.getInstance().getSharedPreferences(ATTACHMENT_SHARE_OUTSIDE_WARNING_PREF, 0).getBoolean(ATTACHMENT_SHARE_OUTSIDE_WARNING_PREF, true);
    }

    public void setSignInAsTeamTimePref(long time) {
        Editor signInAsTeamTimePrefEditor = EnpassApplication.getInstance().getSharedPreferences(SIGN_IN_AS_TEAM_TIME_PREF, 0).edit();
        signInAsTeamTimePrefEditor.putLong(SIGN_IN_AS_TEAM_TIME_PREF, time);
        signInAsTeamTimePrefEditor.commit();
    }

    public long getSignInAsTeamTimePref() {
        return EnpassApplication.getInstance().getSharedPreferences(SIGN_IN_AS_TEAM_TIME_PREF, 0).getLong(SIGN_IN_AS_TEAM_TIME_PREF, 0);
    }

    public void setSignInAsTeamTokenPref(String token) {
        Editor signInAsTeamTokenPrefEditor = EnpassApplication.getInstance().getSharedPreferences(SIGN_IN_AS_TEAM_TOKEN_PREF, 0).edit();
        signInAsTeamTokenPrefEditor.putString(SIGN_IN_AS_TEAM_TOKEN_PREF, token);
        signInAsTeamTokenPrefEditor.commit();
    }

    public String getSignInAsTeamTokenPref() {
        return EnpassApplication.getInstance().getSharedPreferences(SIGN_IN_AS_TEAM_TOKEN_PREF, 0).getString(SIGN_IN_AS_TEAM_TOKEN_PREF, BuildConfig.FLAVOR);
    }

    public void setSignInAsTeamTokenValidPref(boolean value) {
        Editor signInAsTeamTokenPrefEditor = EnpassApplication.getInstance().getSharedPreferences(SIGN_IN_AS_TEAM_TOKEN_VALID_PREF, 0).edit();
        signInAsTeamTokenPrefEditor.putBoolean(SIGN_IN_AS_TEAM_TOKEN_VALID_PREF, value);
        signInAsTeamTokenPrefEditor.commit();
    }

    public boolean getSignInAsTeamTokenValidPref() {
        return EnpassApplication.getInstance().getSharedPreferences(SIGN_IN_AS_TEAM_TOKEN_VALID_PREF, 0).getBoolean(SIGN_IN_AS_TEAM_TOKEN_VALID_PREF, false);
    }

    public void setSignInAsTeamOrgIdPref(String orgId) {
        Editor signInAsTeamOrgIdPrefEditor = EnpassApplication.getInstance().getSharedPreferences(SIGN_IN_AS_TEAM_ORG_ID_PREF, 0).edit();
        signInAsTeamOrgIdPrefEditor.putString(SIGN_IN_AS_TEAM_ORG_ID_PREF, orgId);
        signInAsTeamOrgIdPrefEditor.commit();
    }

    public String getSignInAsTeamOrgIdPref() {
        return EnpassApplication.getInstance().getSharedPreferences(SIGN_IN_AS_TEAM_ORG_ID_PREF, 0).getString(SIGN_IN_AS_TEAM_ORG_ID_PREF, BuildConfig.FLAVOR);
    }

    public void setSignInAsTeamEmailIdPref(String emailId) {
        Editor signInAsTeamEmailIdPrefEditor = EnpassApplication.getInstance().getSharedPreferences(SIGN_IN_AS_TEAM_ORG_ID_PREF, 0).edit();
        signInAsTeamEmailIdPrefEditor.putString(SIGN_IN_AS_TEAM_EMAIL_ID_PREF, emailId);
        signInAsTeamEmailIdPrefEditor.commit();
    }

    public String getSignInAsTeamEmailIdPref() {
        return EnpassApplication.getInstance().getSharedPreferences(SIGN_IN_AS_TEAM_ORG_ID_PREF, 0).getString(SIGN_IN_AS_TEAM_EMAIL_ID_PREF, BuildConfig.FLAVOR);
    }

    public void setDontShowSyncTurnOnPref(boolean value) {
        Editor SyncErrorPrefEditor = EnpassApplication.getInstance().getSharedPreferences(DONT_SHOW_SYNC_TURN_ON_PREF, 0).edit();
        SyncErrorPrefEditor.putBoolean(DONT_SHOW_SYNC_TURN_ON_PREF, value);
        SyncErrorPrefEditor.commit();
    }

    public boolean getDontShowSyncTurnOnPref() {
        return EnpassApplication.getInstance().getSharedPreferences(DONT_SHOW_SYNC_TURN_ON_PREF, 0).getBoolean(DONT_SHOW_SYNC_TURN_ON_PREF, false);
    }

    public void setAttachmentSyncSlowMsgShowTime(long time) {
        Editor mAttachmentSyncSlowMsgShowTimEditor = EnpassApplication.getInstance().getSharedPreferences(ATTACHMENT_SYNC_SLOW_MSG_SHOW_TIME, 0).edit();
        mAttachmentSyncSlowMsgShowTimEditor.putLong(ATTACHMENT_SYNC_SLOW_MSG_SHOW_TIME, time);
        mAttachmentSyncSlowMsgShowTimEditor.commit();
    }

    public long getAttachmentSyncSlowMsgShowTime() {
        return EnpassApplication.getInstance().getSharedPreferences(ATTACHMENT_SYNC_SLOW_MSG_SHOW_TIME, 0).getLong(ATTACHMENT_SYNC_SLOW_MSG_SHOW_TIME, 0);
    }

    public void setAttachmentSyncSlowMsgFisrtTime(boolean value) {
        Editor AttachmentSyncSlowMsgFisrtTimePrefEditor = EnpassApplication.getInstance().getSharedPreferences(ATTACHMENT_SYNC_SLOW_MSG_FISRT_TIME, 0).edit();
        AttachmentSyncSlowMsgFisrtTimePrefEditor.putBoolean(ATTACHMENT_SYNC_SLOW_MSG_FISRT_TIME, value);
        AttachmentSyncSlowMsgFisrtTimePrefEditor.commit();
    }

    public boolean getAttachmentSyncSlowMsgFisrtTime() {
        return EnpassApplication.getInstance().getSharedPreferences(ATTACHMENT_SYNC_SLOW_MSG_FISRT_TIME, 0).getBoolean(ATTACHMENT_SYNC_SLOW_MSG_FISRT_TIME, false);
    }

    public boolean checkUpdatesPref() {
        return EnpassApplication.getInstance().getSharedPreferences(CHECK_UPDATE_PREF, 0).getBoolean(CHECK_UPDATE_PREF, true);
    }

    public void setCheckUpdatesPref(boolean value) {
        Editor checkUpdatePrefEditor = EnpassApplication.getInstance().getSharedPreferences(CHECK_UPDATE_PREF, 0).edit();
        checkUpdatePrefEditor.putBoolean(CHECK_UPDATE_PREF, value);
        checkUpdatePrefEditor.commit();
    }

    public boolean checkSendCrashReportPref() {
        return EnpassApplication.getInstance().getSharedPreferences(SEND_CRASH_REPORT_PREF, 0).getBoolean(SEND_CRASH_REPORT_PREF, true);
    }

    public void setSendCrashReportPref(boolean value) {
        Editor checkUpdatePrefEditor = EnpassApplication.getInstance().getSharedPreferences(SEND_CRASH_REPORT_PREF, 0).edit();
        checkUpdatePrefEditor.putBoolean(SEND_CRASH_REPORT_PREF, value);
        checkUpdatePrefEditor.commit();
    }

    public boolean sendAnalyticsPref() {
        return EnpassApplication.getInstance().getSharedPreferences(SEND_ANALYTICS_PREF, 0).getBoolean(SEND_ANALYTICS_PREF, true);
    }

    public void setSendAnalyticsPref(boolean value) {
        Editor sendAnalyticsPrefEditor = EnpassApplication.getInstance().getSharedPreferences(SEND_ANALYTICS_PREF, 0).edit();
        sendAnalyticsPrefEditor.putBoolean(SEND_ANALYTICS_PREF, value);
        sendAnalyticsPrefEditor.commit();
    }

    public boolean isScreenshotEnabledPref() {
        return EnpassApplication.getInstance().getSharedPreferences(ENABLE_SCREENSHOT_PREFERENCE, 0).getBoolean(ENABLE_SCREENSHOT_PREFERENCE, false);
    }

    public void setEnableScreenshotPreference(boolean value) {
        Editor checkUpdatePrefEditor = EnpassApplication.getInstance().getSharedPreferences(ENABLE_SCREENSHOT_PREFERENCE, 0).edit();
        checkUpdatePrefEditor.putBoolean(ENABLE_SCREENSHOT_PREFERENCE, value);
        checkUpdatePrefEditor.commit();
    }

    public boolean isAccessibilityAutofillEnabled() {
        return EnpassApplication.getInstance().getSharedPreferences(ENABLE_ACCESSIBILTY_AUTOFILL_PREFERENCE, 0).getBoolean(ENABLE_ACCESSIBILTY_AUTOFILL_PREFERENCE, false);
    }

    public void setAccessibilityAutofillEnabled(boolean value) {
        Editor checkUpdatePrefEditor = EnpassApplication.getInstance().getSharedPreferences(ENABLE_ACCESSIBILTY_AUTOFILL_PREFERENCE, 0).edit();
        checkUpdatePrefEditor.putBoolean(ENABLE_ACCESSIBILTY_AUTOFILL_PREFERENCE, value);
        checkUpdatePrefEditor.commit();
    }

    public boolean isrootedDeviceWarningAlreadyShown() {
        return EnpassApplication.getInstance().getSharedPreferences(ROOTED_DEVICE_WARNING_SHOWN, 0).getBoolean(ROOTED_DEVICE_WARNING_SHOWN, false);
    }

    public void setrootedDeviceWarningAlreadyShown(boolean value) {
        Editor warningPrefEditor = EnpassApplication.getInstance().getSharedPreferences(ROOTED_DEVICE_WARNING_SHOWN, 0).edit();
        warningPrefEditor.putBoolean(ROOTED_DEVICE_WARNING_SHOWN, value);
        warningPrefEditor.commit();
    }
}
