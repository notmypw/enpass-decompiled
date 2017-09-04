package in.sinew.enpass.AndroidWatch;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import com.github.clans.fab.BuildConfig;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import in.sinew.enpass.EnpassApplication;
import in.sinew.enpass.utill.UIUtils;
import in.sinew.enpassengine.Card;
import in.sinew.enpassengine.CardField;
import in.sinew.enpassengine.EnpassEngineConstants;
import in.sinew.enpassengine.Folder;
import in.sinew.enpassengine.IDisplayItem;
import in.sinew.enpassengine.Keychain;
import in.sinew.enpassengine.TemplateFactory;
import io.enpass.app.R;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WearConnectionRemote implements ConnectionCallbacks, OnConnectionFailedListener {
    static final String TAG = "WearConnectionRemote";
    Context mContext;
    GoogleApiClient mGoogleApiClient;
    final String stClearWatchData = "clear_data";
    final String stDisconnect = "disconnect";

    public WearConnectionRemote(Context context) {
        this.mContext = context;
    }

    public void initializeConnection() {
        this.mGoogleApiClient = new Builder(this.mContext).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Wearable.API).build();
        this.mGoogleApiClient.connect();
    }

    public void disconnect() {
        if (this.mGoogleApiClient != null) {
            sendDisconnectSignalToWatch();
        }
    }

    public boolean isWatchGoogleApiClientNull() {
        return this.mGoogleApiClient != null;
    }

    public void onConnected(Bundle bundle) {
        editOrCreateWatchFolder(this.mContext.getString(R.string.watch), BuildConfig.FLAVOR, 1008);
        sendDataToWatch();
    }

    public void onConnectionSuspended(int i) {
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this.mContext, this.mContext.getString(R.string.unable_to_connect) + ": " + connectionResult.toString(), 1).show();
        EnpassApplication.getInstance().getAppSettings().setWatchEnabled(false);
    }

    private void editOrCreateWatchFolder(String folderName, String parentFolderUuid, int iconId) {
        Keychain keychain = EnpassApplication.getInstance().getKeychain();
        if (keychain != null) {
            Folder folder = new Folder(folderName, parentFolderUuid, iconId, Keychain.WATCH_FOLDER_UUID);
            if (keychain.isWatchFolderExist()) {
                keychain.updateWatchFolderNotified(folder);
                return;
            }
            keychain.addWatchFolderNotified(folder);
            EnpassApplication.getInstance().getAppSettings().addFolderInDrawer(Keychain.WATCH_FOLDER_UUID);
            EnpassApplication.getInstance().getAppSettings().setDrawerChange(true);
        }
    }

    private JSONArray createCardsJSON() {
        EnpassApplication app = EnpassApplication.getInstance();
        Keychain keychain = app.getKeychain();
        if (keychain == null) {
            return null;
        }
        ArrayList<IDisplayItem> childCardList = (ArrayList) app.filterCards(keychain.getChildCards(Keychain.WATCH_FOLDER_UUID));
        ArrayList<Card> completeCardsList = new ArrayList();
        Iterator it = childCardList.iterator();
        while (it.hasNext()) {
            Card card = keychain.getCardWithUuid(((IDisplayItem) it.next()).getDisplayIdentifier());
            Iterator it2 = card.getFields().iterator();
            while (it2.hasNext()) {
                CardField field = (CardField) it2.next();
                String labelValue = field.getLabel();
                if (labelValue.equals(BuildConfig.FLAVOR)) {
                    labelValue = TemplateFactory.getLabelForUid(card.getTemplateType(), field.getUid());
                }
                field.setLabel(labelValue);
                String selectedFieldkey;
                if (field.getType().equals("country_options")) {
                    selectedFieldkey = UIUtils.getOptionKeyPart(field.getValue().toString());
                    HashMap<String, String> map = (HashMap) UIUtils.getCountryList(loadJSONFromAsset());
                    for (String key : map.keySet()) {
                        if (key.equalsIgnoreCase(selectedFieldkey)) {
                            field.setValue(new StringBuilder((String) map.get(key)));
                        }
                    }
                } else if (field.getType().equals(EnpassEngineConstants.CardFieldTypeCreditCardType)) {
                    selectedFieldkey = UIUtils.getOptionKeyPart(field.getValue().toString());
                    Map<String, String> creditCardTypeMap = UIUtils.getCreditCardType(this.mContext);
                    for (String key2 : creditCardTypeMap.keySet()) {
                        if (key2.equalsIgnoreCase(selectedFieldkey)) {
                            field.setValue(new StringBuilder((String) creditCardTypeMap.get(key2)));
                        }
                    }
                }
            }
            completeCardsList.add(card.createCardForWatch());
        }
        return convertCardToJson(completeCardsList);
    }

    JSONObject createJSONForWatch() {
        JSONObject obj = new JSONObject();
        try {
            boolean pinEnabled = EnpassApplication.getInstance().getAppSettings().isWatchPinEnabled();
            if (pinEnabled) {
                obj.put("watch_pin", new String(EnpassApplication.getInstance().getWatchPin()));
            }
            obj.put("pin_code_status", pinEnabled);
            obj.put("totp", EnpassApplication.getInstance().getAppSettings().showOnlyTotpInWatch());
            obj.put("cards_array", createCardsJSON());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private JSONArray convertCardToJson(List<Card> cards) {
        JSONArray cardsMetaJsonArray = new JSONArray();
        for (Card card : cards) {
            cardsMetaJsonArray.put(card.writeForWatch());
        }
        return cardsMetaJsonArray;
    }

    public void sendDataToWatch() {
        new DataTask(this, this.mContext, createJSONForWatch().toString()).execute(new Node[0]);
    }

    private void sendDisconnectSignalToWatch() {
        new DataTask(this, this.mContext, "disconnect").execute(new Node[0]);
    }

    public void sendClearDataSignalToWatch() {
        new DataTask(this, this.mContext, "clear_data").execute(new Node[0]);
    }

    private String loadJSONFromAsset() {
        try {
            InputStream is = this.mContext.getAssets().open("CountryList.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            return json;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
