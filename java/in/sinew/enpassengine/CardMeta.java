package in.sinew.enpassengine;

import in.sinew.enpassengine.IDisplayItem.DisplayItemType;
import org.json.JSONException;
import org.json.JSONObject;

public class CardMeta implements IDisplayItem {
    private static final String CARD_ICONID = "iconid";
    private static final String CARD_NAME = "name";
    private static final String CARD_SUBTITLE = "subtitle";
    private static final String CARD_UUID = "uuid";
    private int mIconId;
    private String mName;
    private String mSubTitle;
    private String mUuid;

    public CardMeta(String aUuid, int aIconId, String aName, String subtitle) {
        this.mUuid = aUuid;
        this.mIconId = aIconId;
        this.mName = aName;
        this.mSubTitle = subtitle;
    }

    public void setUuid(String mUuid) {
        this.mUuid = mUuid;
    }

    public void setIconId(int mIconId) {
        this.mIconId = mIconId;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setSubTitle(String subtitle) {
        this.mSubTitle = subtitle;
    }

    public int getDisplayIconId() {
        return this.mIconId;
    }

    public String getDisplayName() {
        return this.mName;
    }

    public String getDisplayIdentifier() {
        return this.mUuid;
    }

    public DisplayItemType getDisplayType() {
        return DisplayItemType.DisplayItemCard;
    }

    public String getSubTitle() {
        return this.mSubTitle;
    }

    public JSONObject writeForChromeExtension() {
        JSONObject cardMetaObj = new JSONObject();
        try {
            cardMetaObj.put(CARD_UUID, this.mUuid);
            cardMetaObj.put(CARD_ICONID, this.mIconId);
            cardMetaObj.put(CARD_NAME, this.mName);
            cardMetaObj.put(CARD_SUBTITLE, this.mSubTitle);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cardMetaObj;
    }
}
