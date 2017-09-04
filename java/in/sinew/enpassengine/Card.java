package in.sinew.enpassengine;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import com.box.androidsdk.content.models.BoxRealTimeServer;
import com.box.androidsdk.content.models.BoxSharedLink;
import com.box.androidsdk.content.models.BoxUploadEmail;
import com.box.androidsdk.content.models.BoxUser;
import com.github.clans.fab.BuildConfig;
import in.sinew.enpassengine.CardField.CardFieldType;
import in.sinew.enpassengine.IDisplayItem.DisplayItemType;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Card implements IDisplayItem {
    public static final String AUTOSUBMITLOGIN = "autosubmit_login";
    private static final String CARD_CATEGORY = "category";
    private static final String CARD_CUSTOMICONID = "customiconid";
    private static final String CARD_ICONID = "iconid";
    private static final String CARD_NAME = "name";
    private static final String CARD_NOTE = "note";
    private static final String CARD_TEMPLATETYPE = "templatetype";
    private static final String CARD_UPDATETIME = "updatetime";
    private static final String CARD_UUID = "uuid";
    public static final String FORMDATATIMESTAMP = "update_timeStamp";
    public static int NEW_FIELD_UID_START_LIMIT = 5000;
    public static final int NOTE_ID_FOR_WATCH = 22021;
    public static final String WATCHFIELDUIDS = "WATCHFIELDUIDS";
    public static Map<CardFieldType, String> mCardFieldTypeMap = new HashMap();
    final String CARDFIELDTYPEPASSWORD;
    private boolean isDeleted;
    private String mCardCategory;
    private String mCustomIconId;
    public ArrayList<CardField> mFields;
    public Map<String, Object> mFormFieldsMap;
    boolean mHistoryChangeDuringSync;
    private int mIconId;
    private boolean mIsAutoSubmitLogin;
    private String mName;
    private StringBuilder mNote;
    private String mTemplateType;
    private Date mTimestamp;
    private boolean mTrashed;
    private String mUuid;
    String randomPassword;

    public enum DBValidationResult {
        DBIsValid,
        DBIsAdvanced,
        DBIsInvalid,
        DBIsOlder,
        DBIsTooOld,
        DBResultPasswordOk,
        DBResultPasswordMismatch,
        DBResultUpgradeFailed,
        CipherDBIsInValidOrPasswordMissmatch
    }

    static {
        mCardFieldTypeMap.put(CardFieldType.CardFieldTypeText, "text");
        mCardFieldTypeMap.put(CardFieldType.CardFieldTypePassword, BoxSharedLink.FIELD_PASSWORD);
        mCardFieldTypeMap.put(CardFieldType.CardFieldTypePin, "pin");
        mCardFieldTypeMap.put(CardFieldType.CardFieldTypeNumeric, "numeric");
        mCardFieldTypeMap.put(CardFieldType.CardFieldTypeDate, "date");
        mCardFieldTypeMap.put(CardFieldType.CardFieldTypeEmail, BoxUploadEmail.FIELD_EMAIL);
        mCardFieldTypeMap.put(CardFieldType.CardFieldTypeUrl, BoxSharedLink.FIELD_URL);
        mCardFieldTypeMap.put(CardFieldType.CardFieldTypePhone, BoxUser.FIELD_PHONE);
        mCardFieldTypeMap.put(CardFieldType.CardFieldTypeSeperator, "seperator");
        mCardFieldTypeMap.put(CardFieldType.CardFieldTypeNote, CARD_NOTE);
        mCardFieldTypeMap.put(CardFieldType.CardFieldTypeNone, "none");
        mCardFieldTypeMap.put(CardFieldType.CardFieldTypeUsername, "username");
        mCardFieldTypeMap.put(CardFieldType.CardFieldTypeTOTP, "totp");
    }

    public Card(String aUuid, Date aTimestamp, int aIconId, String aName, StringBuilder aNote, String aTemplateType, String aCardCategory, String customIconId, boolean trash, boolean delete) {
        this.mIsAutoSubmitLogin = false;
        this.mFields = new ArrayList();
        this.mFormFieldsMap = new HashMap();
        this.randomPassword = "I4^O$rA9;YNtF(85Dc2_>+zk3gj1B4#u";
        this.CARDFIELDTYPEPASSWORD = BoxSharedLink.FIELD_PASSWORD;
        this.mHistoryChangeDuringSync = false;
        this.mUuid = aUuid;
        this.mTimestamp = aTimestamp;
        this.mIconId = aIconId;
        this.mName = aName;
        this.mNote = aNote;
        this.mTemplateType = aTemplateType;
        this.mCardCategory = aCardCategory;
        this.mCustomIconId = customIconId;
        this.mTrashed = trash;
        this.isDeleted = delete;
    }

    public Card(String aTemplateType, String category, int aIconId, String aName) {
        this(BuildConfig.FLAVOR, new Date(), aIconId, aName, new StringBuilder(BuildConfig.FLAVOR), aTemplateType, category, BuildConfig.FLAVOR, false, false);
    }

    public Card() {
        this(BuildConfig.FLAVOR, new Date(), -1, BuildConfig.FLAVOR, new StringBuilder(BuildConfig.FLAVOR), BuildConfig.FLAVOR, BuildConfig.FLAVOR, BuildConfig.FLAVOR, false, false);
    }

    public String getUuid() {
        return this.mUuid;
    }

    public void setUuid(String aUuid) {
        this.mUuid = aUuid;
    }

    public Date getTimestamp() {
        return this.mTimestamp;
    }

    public void setTimestamp(Date aTimestamp) {
        this.mTimestamp = aTimestamp;
    }

    public int getIconId() {
        return this.mIconId;
    }

    public void setIconId(int aIconId) {
        this.mIconId = aIconId;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String aName) {
        this.mName = aName;
    }

    public StringBuilder getNote() {
        return this.mNote;
    }

    public void setNote(StringBuilder aNote) {
        Utils.wipeString(this.mNote);
        this.mNote = aNote;
    }

    public String getTemplateType() {
        return this.mTemplateType;
    }

    public void setTemplateType(String aTemplateType) {
        this.mTemplateType = aTemplateType;
    }

    public String getCustomIconId() {
        return this.mCustomIconId;
    }

    public void setCustomIconId(String customIconId) {
        this.mCustomIconId = customIconId;
    }

    public boolean isTrashed() {
        return this.mTrashed;
    }

    public void setTrashed(boolean trashed) {
        this.mTrashed = trashed;
    }

    public void setCardCategory(String cardCategory) {
        this.mCardCategory = cardCategory;
    }

    public String getCardCategory() {
        return this.mCardCategory;
    }

    public ArrayList<CardField> getFields() {
        return this.mFields;
    }

    public void setDeleted(boolean deleted) {
        this.isDeleted = deleted;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public Card copyTemplateWithUuid(String aUuid) {
        Card newCard = new Card(aUuid, new Date(), this.mIconId, this.mName, new StringBuilder(BuildConfig.FLAVOR), this.mTemplateType, this.mCardCategory, this.mCustomIconId, this.mTrashed, this.isDeleted);
        Iterator<CardField> iterator = this.mFields.iterator();
        while (iterator.hasNext()) {
            newCard.mFields.add(((CardField) iterator.next()).copyAsTemplateField());
        }
        return newCard;
    }

    public CardField getFieldForUid(int aUid) {
        Iterator<CardField> iterator = this.mFields.iterator();
        while (iterator.hasNext()) {
            CardField field = (CardField) iterator.next();
            if (Math.abs(field.getUid()) == Math.abs(aUid)) {
                return field;
            }
        }
        return null;
    }

    public boolean checkAndUpdateTimestamp(Card newCard) {
        Date timeNow = new Date();
        boolean hasChanged = false;
        ArrayList<CardField> newCardFields = newCard.mFields;
        if (this.mIconId != newCard.mIconId) {
            this.mIconId = newCard.mIconId;
            hasChanged = true;
        }
        if (!(this.mCustomIconId == null || this.mCustomIconId.equals(newCard.mCustomIconId))) {
            this.mCustomIconId = newCard.mCustomIconId;
            hasChanged = true;
        }
        if (!this.mName.toString().equals(newCard.mName.toString())) {
            this.mName = newCard.mName;
            hasChanged = true;
        }
        if (!this.mNote.toString().equals(newCard.mNote.toString())) {
            this.mNote = newCard.mNote;
            hasChanged = true;
        }
        if (!this.mCardCategory.equals(newCard.mCardCategory)) {
            this.mCardCategory = newCard.mCardCategory;
            hasChanged = true;
        }
        if (isFieldOrderChanged(newCard)) {
            hasChanged = true;
        }
        List<CardField> updatedFields = new ArrayList();
        Iterator it = newCard.mFields.iterator();
        while (it.hasNext()) {
            CardField newField = (CardField) it.next();
            CardField oldField = getFieldForUid(newField.getUid());
            if (oldField == null) {
                hasChanged = true;
                newField.setTimestamp(timeNow);
                updatedFields.add(newField);
            } else {
                if (newField.getMetaModified()) {
                    oldField.setLabel(newField.getLabel());
                    oldField.setValue(newField.getValue());
                    oldField.setType(newField.getType());
                    oldField.setSensitive(newField.isSensitive().booleanValue());
                    hasChanged = true;
                    oldField.setTimestamp(timeNow);
                } else if (!oldField.getValue().toString().equals(newField.getValue().toString())) {
                    if (oldField.getType().equals(mCardFieldTypeMap.get(CardFieldType.CardFieldTypePassword)) || oldField.getType().equals(EnpassEngineConstants.CardFieldTypeTxnPassword)) {
                        CardFieldHistory history = new CardFieldHistory();
                        if (oldField.getValue() != null) {
                            boolean isAlreadyExist = false;
                            if (!TextUtils.isEmpty(oldField.getValue())) {
                                history.setValue(oldField.getValue().toString());
                                history.setTimeStamp(oldField.getTimestamp());
                                isAlreadyExist = oldField.addHistoryToList(history);
                            }
                            oldField.setValue(newField.getValue());
                            hasChanged = !isAlreadyExist;
                            oldField.setTimestamp(timeNow);
                        }
                    } else {
                        oldField.setValue(newField.getValue());
                        hasChanged = true;
                        oldField.setTimestamp(timeNow);
                    }
                }
                updatedFields.add(oldField);
                this.mFields.remove(oldField);
            }
        }
        it = this.mFields.iterator();
        while (it.hasNext()) {
            CardField field = (CardField) it.next();
            field.setDeleted(true);
            field.setTimestamp(timeNow);
            updatedFields.add(field);
            hasChanged = true;
        }
        this.mFields.clear();
        this.mFields.addAll(updatedFields);
        if (hasChanged) {
            this.mTimestamp = timeNow;
        }
        return hasChanged;
    }

    private boolean isFieldOrderChanged(Card newCard) {
        ArrayList<CardField> newCardFields = newCard.getFields();
        if (this.mFields.size() != newCardFields.size()) {
            return true;
        }
        for (int i = 0; i < this.mFields.size(); i++) {
            if (((CardField) this.mFields.get(i)).getUid() != ((CardField) newCardFields.get(i)).getUid()) {
                return true;
            }
        }
        return false;
    }

    public void wipe() {
        Utils.wipeString(this.mNote);
        Iterator<CardField> iterator = this.mFields.iterator();
        while (iterator.hasNext()) {
            ((CardField) iterator.next()).wipe();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--*Card::" + this.mName + "  " + this.mUuid + "  " + this.mTimestamp + "  " + this.mIconId + "  " + this.mNote + "  " + this.mTemplateType + "\n");
        Iterator<CardField> cardFieldsItr = this.mFields.iterator();
        while (cardFieldsItr.hasNext()) {
            sb.append(((CardField) cardFieldsItr.next()).toString());
        }
        return sb.toString();
    }

    public JSONObject write() {
        double timeTicksInSeconds = Utils.timestampToTicks(this.mTimestamp);
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put(CARD_UUID, this.mUuid);
            jsonObj.put(CARD_UPDATETIME, timeTicksInSeconds);
            jsonObj.put(CARD_TEMPLATETYPE, this.mTemplateType);
            jsonObj.put(CARD_ICONID, this.mIconId);
            jsonObj.put(CARD_NAME, this.mName);
            jsonObj.put(CARD_NOTE, this.mNote);
            Iterator<CardField> cardFieldItr = this.mFields.iterator();
            JSONArray jArray = new JSONArray();
            while (cardFieldItr.hasNext()) {
                CardField field = (CardField) cardFieldItr.next();
                double timeTicksInSeconds2 = Utils.timestampToTicks(field.getTimestamp());
                JSONObject jobj = new JSONObject();
                jobj.put("uid", field.getUid());
                jobj.put(CARD_UPDATETIME, timeTicksInSeconds2);
                jobj.put("label", Utils.removeWhiteSpace(Utils.removeTabs(new StringBuilder(field.getLabel()))).toString());
                jobj.put(BoxMetadataUpdateTask.VALUE, Utils.removeWhiteSpace(Utils.removeTabs(field.getValue())));
                jobj.put(BoxRealTimeServer.FIELD_TYPE, field.getType());
                jobj.put("sensitive", field.isSensitive().booleanValue() ? 1 : 0);
                jobj.put("isdeleted", field.isDeleted() ? 1 : 0);
                List<CardFieldHistory> passwordHistoryList = field.getPasswordHistoryList();
                if (passwordHistoryList != null) {
                    JSONArray passArray = new JSONArray();
                    for (CardFieldHistory historyObject : passwordHistoryList) {
                        JSONObject passObject = new JSONObject();
                        passObject.put(BoxMetadataUpdateTask.VALUE, historyObject.getValue());
                        passObject.put(CARD_UPDATETIME, Utils.timestampToTicks(historyObject.getTimeStamp()));
                        passArray.put(passObject);
                    }
                    jobj.put("history", passArray);
                }
                jArray.put(jobj);
            }
            jsonObj.put("fields", jArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public void read4(byte[] bytes) {
        String[] parts = new String(bytes).split("\t");
        int i = 0 + 1;
        this.mUuid = parts[0];
        int i2 = i + 1;
        this.mTimestamp = new Date(1000 * ((long) new Double(parts[i]).doubleValue()));
        i = i2 + 1;
        this.mTemplateType = parts[i2];
        this.mCardCategory = this.mTemplateType.substring(0, this.mTemplateType.indexOf("."));
        i2 = i + 1;
        this.mIconId = new Integer(parts[i]).intValue();
        i = i2 + 1;
        this.mName = parts[i2];
        i2 = i + 1;
        this.mNote = new StringBuilder(parts[i]);
        i = i2 + 1;
        int count = new Integer(parts[i2]).intValue();
        while (count > 0) {
            count--;
            i2 = i + 1;
            int uid = new Integer(parts[i]).intValue();
            i = i2 + 1;
            Date dateTs = new Date(1000 * ((long) new Double(parts[i2]).doubleValue()));
            i2 = i + 1;
            String label = parts[i];
            i = i2 + 1;
            StringBuilder value = new StringBuilder(parts[i2]);
            i2 = i + 1;
            CardFieldType type = CardFieldType.values()[new Integer(parts[i]).intValue()];
            i = i2 + 1;
            boolean sensitiive = new Integer(parts[i2]).intValue() != 0;
            String cardFieldtype = (String) mCardFieldTypeMap.get(type);
            boolean isDeleted = false;
            if (uid < 0) {
                isDeleted = true;
                uid = Math.abs(uid);
            }
            this.mFields.add(new CardField(uid, dateTs, label, value, sensitiive, cardFieldtype, isDeleted));
        }
    }

    public JSONObject writeForShare() {
        double timeTicksInSeconds = Utils.timestampToTicks(this.mTimestamp);
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put(CARD_UUID, this.mUuid);
            jsonObj.put(CARD_UPDATETIME, timeTicksInSeconds);
            jsonObj.put(CARD_TEMPLATETYPE, this.mTemplateType);
            jsonObj.put(CARD_ICONID, this.mIconId);
            jsonObj.put(CARD_NAME, this.mName);
            jsonObj.put(CARD_NOTE, this.mNote);
            jsonObj.put(CARD_CATEGORY, this.mCardCategory);
            jsonObj.put(CARD_CUSTOMICONID, this.mCustomIconId);
            Iterator<CardField> cardFieldItr = this.mFields.iterator();
            JSONArray jArray = new JSONArray();
            while (cardFieldItr.hasNext()) {
                CardField field = (CardField) cardFieldItr.next();
                double timeTicksInSeconds2 = Utils.timestampToTicks(field.getTimestamp());
                JSONObject jobj = new JSONObject();
                jobj.put("uid", field.getUid());
                jobj.put(CARD_UPDATETIME, timeTicksInSeconds2);
                jobj.put("label", Utils.removeWhiteSpace(Utils.removeTabs(new StringBuilder(field.getLabel()))).toString());
                jobj.put(BoxMetadataUpdateTask.VALUE, Utils.removeWhiteSpace(Utils.removeTabs(field.getValue())));
                jobj.put(BoxRealTimeServer.FIELD_TYPE, field.getType());
                jobj.put("sensitive", field.isSensitive().booleanValue() ? 1 : 0);
                jobj.put("isdeleted", field.isDeleted() ? 1 : 0);
                jArray.put(jobj);
            }
            jsonObj.put("fields", jArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public JSONObject writeForWatch() {
        double timeTicksInSeconds = Utils.timestampToTicks(this.mTimestamp);
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put(CARD_UUID, this.mUuid);
            jsonObj.put(CARD_UPDATETIME, timeTicksInSeconds);
            jsonObj.put(CARD_TEMPLATETYPE, this.mTemplateType);
            jsonObj.put(CARD_ICONID, this.mIconId);
            jsonObj.put(CARD_NAME, this.mName);
            jsonObj.put(CARD_NOTE, this.mNote);
            jsonObj.put(CARD_CATEGORY, this.mCardCategory);
            jsonObj.put(CARD_CUSTOMICONID, this.mCustomIconId);
            jsonObj.put("subtitle", getSubTitle());
            Iterator<CardField> cardFieldItr = this.mFields.iterator();
            JSONArray jArray = new JSONArray();
            while (cardFieldItr.hasNext()) {
                CardField field = (CardField) cardFieldItr.next();
                double timeTicksInSeconds2 = Utils.timestampToTicks(field.getTimestamp());
                JSONObject jobj = new JSONObject();
                jobj.put("uid", field.getUid());
                jobj.put(CARD_UPDATETIME, timeTicksInSeconds2);
                jobj.put("label", Utils.removeWhiteSpace(Utils.removeTabs(new StringBuilder(field.getLabel()))).toString());
                jobj.put(BoxMetadataUpdateTask.VALUE, Utils.removeWhiteSpace(Utils.removeTabs(field.getValue())));
                jobj.put(BoxRealTimeServer.FIELD_TYPE, field.getType());
                jobj.put("sensitive", field.isSensitive().booleanValue() ? 1 : 0);
                jobj.put("isdeleted", field.isDeleted() ? 1 : 0);
                jArray.put(jobj);
            }
            jsonObj.put("fields", jArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public String getEncryptShareUrl() {
        JSONObject currentCardData = writeForShare();
        byte[] infoData = new byte[32];
        new SecureRandom().nextBytes(infoData);
        byte[] salt = new byte[16];
        byte[] iv = new byte[16];
        int count = 0;
        while (count <= 15) {
            iv[count] = infoData[count];
            count++;
        }
        int valueCount = 0;
        while (count <= 31) {
            salt[valueCount] = infoData[count];
            valueCount++;
            count++;
        }
        String encryptedUrl = null;
        try {
            byte[] cryptoEncCardData = new Crypto(this.randomPassword, salt, iv, 5).encrypt(currentCardData.toString().getBytes("UTF-8"));
            byte[] combineData = new byte[(cryptoEncCardData.length + infoData.length)];
            System.arraycopy(infoData, 0, combineData, 0, infoData.length);
            System.arraycopy(cryptoEncCardData, 0, combineData, infoData.length, cryptoEncCardData.length);
            encryptedUrl = "enpass://share?data=" + URLEncoder.encode(Base64.encodeToString(combineData, 2), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encryptedUrl;
    }

    public boolean decodeShareUrl(Uri shareUri) {
        try {
            Uri uri = Uri.parse(shareUri.toString().replaceAll("\\s+", BuildConfig.FLAVOR));
            String decodedCardData = BuildConfig.FLAVOR;
            byte[] salt = new byte[16];
            byte[] iv = new byte[16];
            byte[] encryptedCardData = Base64.decode(uri.getQueryParameter("data"), 2);
            if (encryptedCardData.length <= 32) {
                return false;
            }
            int count = 0;
            while (count <= 15) {
                iv[count] = encryptedCardData[count];
                count++;
            }
            int valueCount = 0;
            while (count <= 31) {
                salt[valueCount] = encryptedCardData[count];
                valueCount++;
                count++;
            }
            byte[] dataArray = new byte[(encryptedCardData.length - 32)];
            valueCount = 0;
            while (count <= encryptedCardData.length - 1) {
                dataArray[valueCount] = encryptedCardData[count];
                valueCount++;
                count++;
            }
            return readAfterShare(new JSONObject(new String(new Crypto(this.randomPassword, salt, iv, 5).decrypt(dataArray))));
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            return false;
        } catch (NullPointerException e3) {
            e3.printStackTrace();
            return false;
        }
    }

    private boolean readAfterShare(JSONObject jsonObj) {
        try {
            this.mUuid = jsonObj.getString(CARD_UUID);
            this.mTimestamp = new Date(1000 * ((long) jsonObj.getDouble(CARD_UPDATETIME)));
            this.mTemplateType = jsonObj.getString(CARD_TEMPLATETYPE);
            this.mIconId = jsonObj.getInt(CARD_ICONID);
            this.mName = jsonObj.getString(CARD_NAME);
            this.mNote = new StringBuilder(jsonObj.getString(CARD_NOTE));
            this.mCardCategory = jsonObj.getString(CARD_CATEGORY);
            this.mCustomIconId = jsonObj.getString(CARD_CUSTOMICONID);
            JSONArray jArray = jsonObj.getJSONArray("fields");
            int aryLength = jArray.length();
            for (int i = 0; i < aryLength; i++) {
                JSONObject jObj = jArray.getJSONObject(i);
                this.mFields.add(new CardField(jObj.getInt("uid"), new Date(1000 * jObj.getLong(CARD_UPDATETIME)), jObj.getString("label"), new StringBuilder(jObj.getString(BoxMetadataUpdateTask.VALUE)), jObj.getInt("sensitive") == 1, jObj.getString(BoxRealTimeServer.FIELD_TYPE), jObj.getInt("isdeleted") == 1));
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void upgradeCard() {
        if (this.mTemplateType.startsWith("cards.")) {
            this.mTemplateType = this.mTemplateType.replace("cards", "misc");
        }
        removeAllSeperators();
        String suffix = getSuffix();
        if (this.mTemplateType.startsWith("creditcard.") && !suffix.equals("imported")) {
            this.mTemplateType = "creditcard.default";
        }
        if (!this.mTemplateType.startsWith("login.") || suffix.equals("imported")) {
            upgradeOtherCard();
        } else {
            upgradeLoginCard();
        }
    }

    public void clearAllLabel() {
        if (TemplateFactory.getTemplateOfType(this.mTemplateType) != null) {
            Iterator it = this.mFields.iterator();
            while (it.hasNext()) {
                ((CardField) it.next()).setLabel(BuildConfig.FLAVOR);
            }
        }
    }

    public void removeAllSeperators() {
        ArrayList<CardField> seperatorField = new ArrayList();
        for (int i = this.mFields.size() - 1; i >= 0; i--) {
            if (((CardField) this.mFields.get(i)).getType().equals("seperator")) {
                seperatorField.add(this.mFields.get(i));
            }
        }
        this.mFields.removeAll(seperatorField);
    }

    public String getSuffix() {
        return this.mTemplateType.substring(this.mTemplateType.indexOf("."), this.mTemplateType.length());
    }

    private void upgradeLoginCard() {
        this.mTemplateType = "login.default";
        Card defaultLoginCard = TemplateFactory.getTemplateOfType("login.default").copyTemplateWithUuid(this.mUuid);
        defaultLoginCard.clearAllLabel();
        Iterator it = defaultLoginCard.mFields.iterator();
        while (it.hasNext()) {
            CardField defaultField = (CardField) it.next();
            for (int count = this.mFields.size() - 1; count >= 0; count--) {
                if (defaultField.getType().equals(((CardField) this.mFields.get(count)).getType()) && defaultField.getUid() < NEW_FIELD_UID_START_LIMIT) {
                    defaultField.setValue(((CardField) this.mFields.get(count)).getValue());
                    defaultField.setLabel(((CardField) this.mFields.get(count)).getLabel());
                    defaultField.setSensitive(((CardField) this.mFields.get(count)).isSensitive().booleanValue());
                    this.mFields.remove(count);
                    break;
                }
            }
        }
        defaultLoginCard.mFields.addAll(this.mFields);
        this.mFields.clear();
        this.mFields.addAll(defaultLoginCard.mFields);
    }

    private void upgradeOtherCard() {
        Card templateCardType = TemplateFactory.getTemplateOfType(this.mTemplateType);
        if (templateCardType != null) {
            Card templateCard = templateCardType.copyTemplateWithUuid(this.mUuid);
            templateCard.clearAllLabel();
            ArrayList<CardField> newFields = new ArrayList();
            Iterator it = templateCard.mFields.iterator();
            while (it.hasNext()) {
                CardField field = (CardField) it.next();
                CardField oldField = getFieldForUid(field.getUid());
                if (oldField == null) {
                    oldField = field.copyAsTemplateField();
                } else {
                    oldField.setType(field.getType());
                }
                newFields.add(oldField);
                this.mFields.remove(oldField);
            }
            it = this.mFields.iterator();
            while (it.hasNext()) {
                newFields.add((CardField) it.next());
            }
            this.mFields.clear();
            this.mFields.addAll(newFields);
        }
    }

    public void read(JSONObject jsonObj) {
        try {
            this.mUuid = jsonObj.getString(CARD_UUID);
            this.mTimestamp = new Date(1000 * ((long) jsonObj.getDouble(CARD_UPDATETIME)));
            this.mTemplateType = jsonObj.getString(CARD_TEMPLATETYPE);
            this.mIconId = jsonObj.getInt(CARD_ICONID);
            this.mName = jsonObj.getString(CARD_NAME);
            this.mNote = new StringBuilder(jsonObj.getString(CARD_NOTE));
            JSONArray jArray = jsonObj.getJSONArray("fields");
            int aryLength = jArray.length();
            for (int i = 0; i < aryLength; i++) {
                JSONObject jObj = jArray.getJSONObject(i);
                CardField field = new CardField(jObj.getInt("uid"), new Date(1000 * ((long) jObj.getDouble(CARD_UPDATETIME))), jObj.getString("label"), new StringBuilder(jObj.getString(BoxMetadataUpdateTask.VALUE)), jObj.getInt("sensitive") == 1, jObj.getString(BoxRealTimeServer.FIELD_TYPE), jObj.getInt("isdeleted") == 1);
                if (jObj.has("history")) {
                    List<CardFieldHistory> passwordHistoryList = new ArrayList();
                    JSONArray historyArray = jObj.getJSONArray("history");
                    int arrayLength = historyArray.length();
                    for (int j = 0; j < arrayLength; j++) {
                        JSONObject passwordHistoryObj = historyArray.getJSONObject(j);
                        passwordHistoryList.add(new CardFieldHistory(passwordHistoryObj.getString(BoxMetadataUpdateTask.VALUE), new Date(1000 * ((long) passwordHistoryObj.getDouble(CARD_UPDATETIME)))));
                    }
                    field.setPasswordHistoryList(passwordHistoryList);
                }
                this.mFields.add(field);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getSubTitle() {
        Iterator it;
        CardField cardField;
        if (this.mTemplateType.startsWith("login.default")) {
            it = this.mFields.iterator();
            while (it.hasNext()) {
                cardField = (CardField) it.next();
                if (!cardField.getType().equals("username") || cardField.isDeleted() || cardField.isSensitive().booleanValue()) {
                    if (!(!cardField.getType().equals(BoxUploadEmail.FIELD_EMAIL) || cardField.isDeleted() || cardField.isSensitive().booleanValue() || TextUtils.isEmpty(cardField.getValue().toString()))) {
                        return cardField.getValue().toString();
                    }
                } else if (!TextUtils.isEmpty(cardField.getValue().toString())) {
                    return cardField.getValue().toString();
                }
            }
            it = this.mFields.iterator();
            while (it.hasNext()) {
                cardField = (CardField) it.next();
                if (!cardField.getType().equals(BoxUser.FIELD_PHONE) || cardField.isDeleted() || cardField.isSensitive().booleanValue()) {
                    if (!(!cardField.getType().equals(BoxSharedLink.FIELD_URL) || cardField.isDeleted() || cardField.isSensitive().booleanValue() || TextUtils.isEmpty(cardField.getValue().toString()))) {
                        return cardField.getValue().toString();
                    }
                } else if (!TextUtils.isEmpty(cardField.getValue().toString())) {
                    return cardField.getValue().toString();
                }
            }
        } else if (this.mTemplateType.startsWith("creditcard.default")) {
            it = this.mFields.iterator();
            while (it.hasNext()) {
                CardField cardFiled = (CardField) it.next();
                if (cardFiled.getType().equals(EnpassEngineConstants.CardFieldTypeCardNumber) && !cardFiled.isDeleted() && !cardFiled.isSensitive().booleanValue() && !cardFiled.isOptionalField() && !TextUtils.isEmpty(cardFiled.getValue().toString())) {
                    String cardNumber = BuildConfig.FLAVOR;
                    String number = cardFiled.getValue().toString();
                    if (number.length() > 4) {
                        return "***** " + number.substring(number.length() - 4, number.length());
                    }
                    return number;
                }
            }
        } else if (this.mTemplateType.startsWith("note.default")) {
            String[] firstLine = this.mNote.toString().trim().split(System.getProperty("line.separator"));
            int subtitleLength = firstLine[0].length() < 40 ? firstLine[0].length() : 40;
            if (!(TextUtils.isEmpty(firstLine[0]) || subtitleLength == 0)) {
                return firstLine[0].substring(0, subtitleLength);
            }
        } else if (this.mTemplateType.startsWith("identity.default")) {
            String firstName = BuildConfig.FLAVOR;
            String initial = BuildConfig.FLAVOR;
            String lastName = BuildConfig.FLAVOR;
            it = this.mFields.iterator();
            while (it.hasNext()) {
                cardField = (CardField) it.next();
                if (!cardField.getType().equals(EnpassEngineConstants.CardFieldTypeFirstName) || cardField.isDeleted() || cardField.isSensitive().booleanValue() || cardField.isOptionalField()) {
                    if (!cardField.getType().equals(EnpassEngineConstants.CardFieldTypeNameInitial) || cardField.isDeleted() || cardField.isSensitive().booleanValue() || cardField.isOptionalField()) {
                        if (!(!cardField.getType().equals(EnpassEngineConstants.CardFieldTypeLastName) || cardField.isDeleted() || cardField.isSensitive().booleanValue() || cardField.isOptionalField() || TextUtils.isEmpty(cardField.getValue().toString()))) {
                            lastName = cardField.getValue().toString();
                        }
                    } else if (!TextUtils.isEmpty(cardField.getValue().toString())) {
                        initial = cardField.getValue().toString();
                    }
                } else if (!TextUtils.isEmpty(cardField.getValue().toString())) {
                    firstName = cardField.getValue().toString();
                }
            }
            String name = initial + " " + firstName + " " + lastName;
            if (!TextUtils.isEmpty(name.trim())) {
                return name.trim();
            }
        } else {
            it = this.mFields.iterator();
            while (it.hasNext()) {
                cardField = (CardField) it.next();
                if (!cardField.isSensitive().booleanValue() && !cardField.isDeleted() && !cardField.isOptionalField() && !cardField.getType().equals(mCardFieldTypeMap.get(CardFieldType.CardFieldTypeTOTP)) && !TextUtils.isEmpty(cardField.getValue().toString())) {
                    return cardField.getValue().toString();
                }
            }
        }
        return BuildConfig.FLAVOR;
    }

    public void addOrUpdateField(int aUid, Date aTimestamp, String aLabel, StringBuilder aValue, boolean aSensitive, String aType) {
        CardField field = getFieldForUid(aUid);
        if (field != null) {
            field.updateWithUid(aUid, aTimestamp, aLabel, aValue, aSensitive, aType);
        } else {
            addFieldWithData(aUid, aTimestamp, aLabel, aValue, aSensitive, aType);
        }
    }

    public void addFieldWithData(int aUid, Date aTimestamp, String aLabel, StringBuilder aValue, boolean aSensitive, String aType) {
        this.mFields.add(new CardField(aUid, aTimestamp, aLabel, aValue, aSensitive, aType, false));
    }

    public String getUrl() {
        String url = BuildConfig.FLAVOR;
        Iterator it = this.mFields.iterator();
        while (it.hasNext()) {
            CardField iterate = (CardField) it.next();
            if (!iterate.isDeleted() && iterate.getType().equals(mCardFieldTypeMap.get(CardFieldType.CardFieldTypeUrl))) {
                String urlValue = iterate.getValue().toString();
                if (urlValue != null) {
                    if (!urlValue.startsWith("http")) {
                        urlValue = "http://" + urlValue;
                    }
                    if (GetDomain.GetDomainFromUrl(Uri.parse(urlValue)) != null) {
                        url = String.format("%s,", new Object[]{url.concat(GetDomain.GetDomainFromUrl(Uri.parse(urlValue)).replaceAll("\\s+", BuildConfig.FLAVOR))});
                    }
                }
            }
        }
        return url;
    }

    public String getFirstUrl() {
        String url = BuildConfig.FLAVOR;
        Iterator it = this.mFields.iterator();
        while (it.hasNext()) {
            CardField iterate = (CardField) it.next();
            if (!iterate.isDeleted() && iterate.getType().equals(mCardFieldTypeMap.get(CardFieldType.CardFieldTypeUrl))) {
                String urlValue = iterate.getValue().toString();
                if (urlValue == null) {
                    continue;
                } else {
                    if (!urlValue.startsWith("http")) {
                        urlValue = "http://" + urlValue;
                    }
                    if (GetDomain.GetDomainFromUrl(Uri.parse(urlValue)) != null) {
                        return String.format("%s,", new Object[]{url.concat(GetDomain.GetDomainFromUrl(Uri.parse(urlValue)).replaceAll("\\s+", BuildConfig.FLAVOR))});
                    }
                }
            }
        }
        return url;
    }

    public String getLabelForField(CardField aFiled) {
        Card templateCard = TemplateFactory.getTemplateOfType(this.mTemplateType);
        if (templateCard != null) {
            CardField field = templateCard.getFieldForUid(aFiled.getUid());
            if (field != null) {
                return field.getLabel();
            }
        }
        return aFiled.getLabel();
    }

    public int getDisplayIconId() {
        return this.mIconId;
    }

    public String getDisplayName() {
        return this.mName;
    }

    public String getDisplayIdentifier() {
        if (this.mUuid.equals(BuildConfig.FLAVOR)) {
            return this.mTemplateType;
        }
        return this.mUuid;
    }

    public DisplayItemType getDisplayType() {
        return DisplayItemType.DisplayItemCard;
    }

    public boolean foundText(String searchingText, Locale current) {
        String text = searchingText.toLowerCase(current);
        if (this.mName.toLowerCase(current).contains(text) || this.mNote.toString().toLowerCase(current).contains(text)) {
            return true;
        }
        Iterator it = this.mFields.iterator();
        while (it.hasNext()) {
            if (((CardField) it.next()).foundText(searchingText, current)) {
                return true;
            }
        }
        return false;
    }

    public String discription() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mName + "\n");
        Iterator<CardField> cardFieldsItr = this.mFields.iterator();
        while (cardFieldsItr.hasNext()) {
            CardField field = (CardField) cardFieldsItr.next();
            if (field.getValue().toString().length() > 0) {
                if (field.getType().equals(EnpassEngineConstants.CardFieldTypeCreditCardType)) {
                    sb.append(field.getLabel() + " :\t " + field.getValue().toString().split("-")[1]);
                } else {
                    sb.append(field.getLabel() + " :\t " + field.getValue());
                }
                sb.append("\n");
            }
        }
        sb.append(this.mNote);
        return sb.toString();
    }

    public boolean isHistoryChangedDuringSync() {
        return this.mHistoryChangeDuringSync;
    }

    public Card syncWithCard(Card aClientCard) {
        if (this.mTimestamp.before(aClientCard.getTimestamp())) {
            this.mHistoryChangeDuringSync = realSyncWithCard(aClientCard);
            return this;
        }
        this.mHistoryChangeDuringSync = aClientCard.realSyncWithCard(this);
        return aClientCard;
    }

    public boolean realSyncWithCard(Card aNewCard) {
        this.mIconId = aNewCard.getIconId();
        this.mName = aNewCard.getName();
        this.mNote = aNewCard.getNote();
        this.mTimestamp = aNewCard.getTimestamp();
        this.mCardCategory = aNewCard.getCardCategory();
        this.mCustomIconId = aNewCard.getCustomIconId();
        this.mTrashed = aNewCard.isTrashed();
        this.isDeleted = aNewCard.isDeleted();
        this.mFormFieldsMap = aNewCard.mFormFieldsMap;
        long newCardFormFieldDate = 0;
        try {
            JSONObject obj = new JSONObject(aNewCard.getFormFields());
            if (obj.has(FORMDATATIMESTAMP)) {
                newCardFormFieldDate = (long) obj.getDouble(FORMDATATIMESTAMP);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Object formatDateObj = this.mFormFieldsMap.get(FORMDATATIMESTAMP);
        if (formatDateObj != null) {
            long formFieldUpdateTime = (long) ((Integer) formatDateObj).intValue();
            if (formFieldUpdateTime != 0 && formFieldUpdateTime < newCardFormFieldDate) {
                setFormFields(aNewCard.getFormFields());
            }
        }
        boolean isHistoryChanged = false;
        if (this.isDeleted) {
            clearRealData();
        } else {
            ArrayList<CardField> newFields = aNewCard.getFields();
            Iterator it = newFields.iterator();
            while (it.hasNext()) {
                CardField newField = (CardField) it.next();
                CardField oldField = getFieldForUid(newField.getUid());
                if (oldField != null) {
                    CardFieldHistory historyItem;
                    if (newField.getTimestamp().before(oldField.getTimestamp())) {
                        newField.setUid(oldField.getUid());
                        newField.setLabel(oldField.getLabel());
                        newField.setType(oldField.getType());
                        newField.setSensitive(oldField.isSensitive().booleanValue());
                        newField.setDeleted(oldField.isDeleted());
                        if (!(!newField.getType().endsWith(BoxSharedLink.FIELD_PASSWORD) || newField.getValue().toString().equals(BuildConfig.FLAVOR) || newField.getValue().toString().equals(oldField.getValue().toString()))) {
                            historyItem = new CardFieldHistory();
                            historyItem.setValue(newField.getValue().toString());
                            historyItem.setTimeStamp(newField.getTimestamp());
                            if (!newField.addHistoryToList(historyItem)) {
                                isHistoryChanged = true;
                            }
                        }
                        newField.setTimestamp(oldField.getTimestamp());
                        newField.setValue(oldField.getValue());
                    } else if (oldField.getTimestamp().before(newField.getTimestamp()) && newField.getType().endsWith(BoxSharedLink.FIELD_PASSWORD) && !oldField.getValue().toString().equals(BuildConfig.FLAVOR) && !newField.getValue().toString().equals(oldField.getValue().toString())) {
                        historyItem = new CardFieldHistory();
                        historyItem.setValue(oldField.getValue().toString());
                        historyItem.setTimeStamp(oldField.getTimestamp());
                        if (!oldField.addHistoryToList(historyItem)) {
                            isHistoryChanged = true;
                        }
                    }
                    if (newField.getType().endsWith(BoxSharedLink.FIELD_PASSWORD)) {
                        newField.syncHistoryWithField(oldField);
                    }
                    this.mFields.remove(oldField);
                }
            }
            it = this.mFields.iterator();
            while (it.hasNext()) {
                newFields.add((CardField) it.next());
            }
            this.mFields.clear();
            this.mFields.addAll(newFields);
        }
        if (isHistoryChanged) {
            this.mTimestamp = new Date();
        }
        return isHistoryChanged;
    }

    public void clearRealData() {
        wipe();
        this.mFields.clear();
        this.mNote = new StringBuilder(BuildConfig.FLAVOR);
    }

    public void setFormFields(String formFields) {
        JSONObject jsonObj = null;
        if (formFields != null && !formFields.isEmpty()) {
            try {
                jsonObj = new JSONObject(formFields);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                Iterator<String> iter = jsonObj.keys();
                while (iter.hasNext()) {
                    String key = (String) iter.next();
                    this.mFormFieldsMap.put(key, jsonObj.get(key));
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public String getFormFields() {
        JSONObject jsonObj = new JSONObject();
        if (this.mFormFieldsMap.size() > 0) {
            for (String key : this.mFormFieldsMap.keySet()) {
                try {
                    jsonObj.put(key, this.mFormFieldsMap.get(key));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                jsonObj.put(AUTOSUBMITLOGIN, true);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return jsonObj.toString();
    }

    public Card createCardForWatch() {
        Card tempCard;
        JSONException e;
        ArrayList<CardField> allFields = getFields();
        try {
            StringBuilder noteValue = getNote();
            ArrayList<Integer> uidList = new ArrayList();
            JSONObject jSONObject = new JSONObject(getFormFields());
            int i;
            CardField field;
            if (jSONObject.has(WATCHFIELDUIDS)) {
                JSONArray ary = (JSONArray) jSONObject.get(WATCHFIELDUIDS);
                for (i = 0; i < ary.length(); i++) {
                    uidList.add(Integer.valueOf(ary.getInt(i)));
                }
                for (i = allFields.size() - 1; i >= 0; i--) {
                    field = (CardField) allFields.get(i);
                    if (uidList.indexOf(Integer.valueOf(field.getUid())) == -1 || field.isDeleted()) {
                        allFields.remove(field);
                    }
                }
                if (uidList.indexOf(Integer.valueOf(NOTE_ID_FOR_WATCH)) == -1) {
                    noteValue = new StringBuilder();
                }
            } else {
                for (i = allFields.size() - 1; i >= 0; i--) {
                    field = (CardField) allFields.get(i);
                    if (field.isDeleted()) {
                        allFields.remove(field);
                    }
                }
            }
            tempCard = new Card(getUuid(), new Date(), getIconId(), getName(), noteValue, getTemplateType(), getCardCategory(), BuildConfig.FLAVOR, false, false);
            try {
                tempCard.mFields.addAll(allFields);
            } catch (JSONException e2) {
                e = e2;
                e.printStackTrace();
                return tempCard;
            }
        } catch (JSONException e3) {
            e = e3;
            tempCard = null;
            e.printStackTrace();
            return tempCard;
        }
        return tempCard;
    }

    public JSONObject writeForChromeExtension() {
        double timeTicksInSeconds = Utils.timestampToTicks(this.mTimestamp);
        JSONObject cardObj = new JSONObject();
        try {
            cardObj.put(CARD_UUID, this.mUuid);
            cardObj.put(CARD_UPDATETIME, timeTicksInSeconds);
            cardObj.put(CARD_TEMPLATETYPE, this.mTemplateType);
            cardObj.put(CARD_ICONID, this.mIconId);
            cardObj.put(CARD_NAME, this.mName);
            cardObj.put(CARD_NOTE, this.mNote);
            Iterator<CardField> cardFieldItr = this.mFields.iterator();
            JSONArray jArray = new JSONArray();
            while (cardFieldItr.hasNext()) {
                CardField field = (CardField) cardFieldItr.next();
                if (!(field.isDeleted() || TextUtils.isEmpty(field.getValue()))) {
                    double timeTicksInSeconds2 = Utils.timestampToTicks(field.getTimestamp());
                    JSONObject jobj = new JSONObject();
                    jobj.put("uid", field.getUid());
                    jobj.put(CARD_UPDATETIME, timeTicksInSeconds2);
                    StringBuilder label = Utils.removeWhiteSpace(Utils.removeTabs(new StringBuilder(field.getLabel())));
                    if (TextUtils.isEmpty(label)) {
                        label = new StringBuilder(TemplateFactory.getLabelForUid(getTemplateType(), field.getUid()));
                    }
                    jobj.put("label", label.toString());
                    jobj.put(BoxMetadataUpdateTask.VALUE, Utils.removeWhiteSpace(Utils.removeTabs(field.getValue())));
                    jobj.put(BoxRealTimeServer.FIELD_TYPE, field.getType());
                    jobj.put("sensitive", field.isSensitive().booleanValue() ? 1 : 0);
                    jobj.put("isdeleted", field.isDeleted() ? 1 : 0);
                    jArray.put(jobj);
                }
            }
            cardObj.put("fields", jArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cardObj;
    }
}
