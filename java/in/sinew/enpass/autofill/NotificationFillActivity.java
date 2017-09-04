package in.sinew.enpass.autofill;

import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog.Builder;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.github.clans.fab.BuildConfig;
import in.sinew.enpass.EnpassActivity;
import in.sinew.enpass.EnpassApplication;
import in.sinew.enpass.IRemoteStorage;
import in.sinew.enpass.ISyncManagerDelegate;
import in.sinew.enpass.autofill.adapter.MatchingCardsForNotificationFilling;
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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class NotificationFillActivity extends EnpassActivity implements ISyncManagerDelegate, IAppEventSubscriber {
    public static int NEW_FIELD_UID_LAST_LIMIT = 9999;
    public static int NEW_FIELD_UID_START_LIMIT = 5000;
    private final int DEEP_SEARCH = 1;
    String TAG = "NotificationFillActivity";
    private final int TITLE_SEARCH = 0;
    boolean isItemSearched;
    ListView mCardsListView;
    ImageButton mClearSearchBtn;
    Locale mCurrentLocale;
    String mDomain;
    TextView mEmptyView;
    ArrayList<IDisplayItem> mFilterList;
    Handler mHandler = new Handler();
    String mPackageName;
    Runnable mRunnable;
    EditText mSearchBox;
    ImageView mSyncError;
    ProgressBar mSyncProgressbar;
    MenuItem syncProgress;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_fill);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.accessibility_service_label);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setElevation(0.0f);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(2131099794)));
        if (VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(2131099741));
        }
        this.mCurrentLocale = getResources().getConfiguration().locale;
        this.mCardsListView = (ListView) findViewById(R.id.notification_fill_list_view);
        this.mEmptyView = (TextView) findViewById(R.id.notification_fill_empty_view);
        this.mClearSearchBtn = (ImageButton) findViewById(R.id.notification_fill_clear_button);
        this.mSearchBox = (EditText) findViewById(R.id.notification_fill_search_field);
        View v = getLayoutInflater().inflate(R.layout.notification_fill_progress_bar, null);
        this.mSyncProgressbar = (ProgressBar) v.findViewById(R.id.notification_fill_progress);
        this.mSyncError = (ImageView) v.findViewById(R.id.notification_fill_sync_error);
        this.mCardsListView.setOnItemClickListener(new 1(this));
        this.mClearSearchBtn.setOnClickListener(new 2(this));
        this.mSearchBox.addTextChangedListener(new 3(this));
    }

    protected void onResume() {
        super.onResume();
        EnpassApplication.getInstance().addSubscriber(this);
        EnpassApplication.getSyncManagerInstance().addSyncDelegate(this);
        this.mPackageName = EnpassApplication.getInstance().getFillingPackageName();
        if (isBrowserDetected()) {
            String domain = getDomainFromBrowserUrl();
            if (!TextUtils.isEmpty(domain)) {
                this.mDomain = domain;
            }
        } else if (this.mPackageName != null) {
            int i = nthOccurrence(this.mPackageName, '.', 2);
            if (i != -1) {
                this.mPackageName = this.mPackageName.substring(0, i);
            }
            String[] domainAry = this.mPackageName.split("\\.");
            if (domainAry.length >= 2) {
                this.mDomain = domainAry[1] + "." + domainAry[0];
            }
        }
        getMatchingCards();
    }

    protected void onPause() {
        super.onPause();
        EnpassApplication.getInstance().removeSubscriber(this);
        EnpassApplication.getSyncManagerInstance().removeSyncDelegate(this);
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notificationfill_activity_option_menu, menu);
        this.syncProgress = menu.findItem(R.id.notification_fill_menu_sync);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                if (VERSION.SDK_INT < 21) {
                    finish();
                    break;
                }
                finishAffinity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void tryAutoFilling(String cardUUID) {
        EnpassAccessibilityService fillingAccessibilityService = EnpassAccessibilityService.getInstance();
        if (fillingAccessibilityService != null) {
            Handler handler = new Handler();
            if (fillingAccessibilityService.hasNonEmptyUsernameNode()) {
                fillingAccessibilityService.focusNonEmptyUersnameNode();
                handler.postDelayed(new 4(this, fillingAccessibilityService, cardUUID), 250);
                handler.postDelayed(new 5(this, fillingAccessibilityService), 550);
                handler.postDelayed(new 6(this, fillingAccessibilityService, cardUUID), 750);
            } else if (fillingAccessibilityService.hasSinglePasswordNode()) {
                handler.postDelayed(new 7(this, fillingAccessibilityService, cardUUID), 0);
            }
        }
    }

    void fillUsername(String cardUUID, AccessibilityNodeInfo node) {
        if (EnpassAccessibilityService.getInstance() != null) {
            Card card = EnpassApplication.getInstance().getKeychain().getCardWithUuid(cardUUID);
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
            Bundle arguments = new Bundle();
            arguments.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", userName);
            node.performAction(2097152, arguments);
            card.wipe();
        }
    }

    void fillPassword(String cardUUID, AccessibilityNodeInfo node) {
        if (EnpassAccessibilityService.getInstance() != null) {
            List<CardField> fields = EnpassApplication.getInstance().getKeychain().getCardWithUuid(cardUUID).getFields();
            StringBuilder password = new StringBuilder(BuildConfig.FLAVOR);
            for (CardField f : fields) {
                if (f.getType().equals(Card.mCardFieldTypeMap.get(CardFieldType.CardFieldTypePassword)) && !f.isDeleted() && TextUtils.isEmpty(password)) {
                    password = f.getValue();
                    break;
                }
            }
            Bundle arguments = new Bundle();
            arguments.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", password);
            node.performAction(2097152, arguments);
        }
    }

    void getMatchingCards() {
        Keychain keychainObj = EnpassApplication.getInstance().getKeychain();
        List<IDisplayItem> cardList = new ArrayList();
        if (keychainObj != null && this.mDomain != null) {
            List<String> matchingCardsUuidList = null;
            if (EnpassApplication.getInstance().getAppSettings().isMatchHostnameEnabled() && isBrowserDetected()) {
                Uri uri = EnpassAccessibilityService.getInstance().getUri();
                if (uri != null) {
                    matchingCardsUuidList = keychainObj.getAllCardsWithSameHostname(uri);
                }
            } else {
                matchingCardsUuidList = keychainObj.getAllCardsWithSameDomainName(this.mDomain);
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
            this.mFilterList = (ArrayList) cardList;
            this.mCardsListView.setAdapter(new MatchingCardsForNotificationFilling(this, this.mFilterList));
            if (this.mFilterList.size() <= 0) {
                String info = getString(R.string.no_card);
                if (!TextUtils.isEmpty(this.mDomain)) {
                    info = String.format(getString(R.string.no_login_found), new Object[]{this.mDomain});
                }
                this.mEmptyView.setText(info);
                this.mEmptyView.setVisibility(0);
                return;
            }
            this.mCardsListView.setVisibility(0);
            this.mEmptyView.setVisibility(4);
        }
    }

    boolean isBrowserDetected() {
        for (String pckg : getResources().getStringArray(2130903042)) {
            if (pckg.equalsIgnoreCase(this.mPackageName)) {
                return true;
            }
        }
        return false;
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

    public void searchInCards(String searchText) {
        String aSearchText = searchText;
        EnpassApplication.getInstance().touch();
        int searchInCardsVal = EnpassApplication.getInstance().getAppSettings().getSearchInCards();
        if (searchInCardsVal == 0) {
            searchInCards(searchText, false);
        } else if (searchInCardsVal == 1) {
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
                boolean matchFound = false;
                Card c = keychain.getCardWithUuid(item.getDisplayIdentifier());
                if (c.foundText(searchText, this.mCurrentLocale)) {
                    matchFound = true;
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
            this.isItemSearched = true;
            return;
        }
        this.mCardsListView.setVisibility(4);
        this.mEmptyView.setText(getString(R.string.no_card));
        this.mEmptyView.setVisibility(0);
    }

    public void syncStarted() {
        this.mSyncProgressbar.setVisibility(0);
        this.mSyncError.setVisibility(4);
        if (this.syncProgress != null) {
            this.syncProgress.setActionView(this.mSyncProgressbar);
            this.syncProgress.getActionView().postDelayed(new 9(this), 10000);
        }
    }

    public void syncDone() {
    }

    public void syncError(String Errormsg) {
        this.mSyncProgressbar.setVisibility(4);
        this.mSyncError.setVisibility(0);
        if (this.syncProgress != null) {
            this.syncProgress.setActionView(this.mSyncError);
        }
    }

    public void syncPasswordError(IRemoteStorage aRemote) {
        this.mSyncProgressbar.setVisibility(4);
        this.mSyncError.setVisibility(0);
        if (this.syncProgress != null) {
            this.syncProgress.setActionView(this.mSyncError);
        }
    }

    public void syncAborted() {
    }

    public void realSyncStarted() {
    }

    public void ItemChanged(KeychainChangeType type, IDisplayItem item, String extra) {
        getMatchingCards();
    }

    public void reload() {
        getMatchingCards();
    }

    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    void saveCardUrl(IDisplayItem item) {
        Builder dialog = new Builder(this);
        dialog.setMessage(String.format(getString(R.string.msg_save_url), new Object[]{this.mDomain, item.getDisplayName(), item.getDisplayName(), this.mDomain}));
        dialog.setNegativeButton(getString(R.string.no), new 10(this, item));
        dialog.setPositiveButton(getString(R.string.yes), new 11(this, item));
        dialog.show();
    }

    void addField(Card card) {
        ArrayList<CardField> cardFields = card.getFields();
        CardField newFiled = new CardField(generateUid(cardFields), new Date(), TemplateFactory.getLabelForUid("login.default", 13), new StringBuilder(this.mDomain), false, (String) Card.mCardFieldTypeMap.get(CardFieldType.CardFieldTypeUrl), false);
        int addPosition = cardFields.size() - 2;
        if (addPosition >= 0) {
            cardFields.add(addPosition, newFiled);
        } else {
            cardFields.add(0, newFiled);
        }
        card.setTimestamp(new Date());
        EnpassApplication.getInstance().getKeychain().updateCardNotified(card);
    }

    int generateUid(ArrayList<CardField> cardFields) {
        int newCardUid;
        do {
            newCardUid = new Random().nextInt(NEW_FIELD_UID_LAST_LIMIT - NEW_FIELD_UID_START_LIMIT) + NEW_FIELD_UID_START_LIMIT;
        } while (checkForSameUid(newCardUid, cardFields));
        return newCardUid;
    }

    boolean checkForSameUid(int uid, ArrayList<CardField> cardFields) {
        boolean isSame = false;
        Iterator it = cardFields.iterator();
        while (it.hasNext()) {
            if (Math.abs(uid) == Math.abs(((CardField) it.next()).getUid())) {
                isSame = true;
            }
        }
        return isSame;
    }
}
