package in.sinew.enpassengine;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class Attachment {
    public static final String ATTACHMENT_FILENAME = "filename";
    public static final String ATTACHMENT_KIND = "kind";
    public static final String ATTACHMENT_ORDER = "order";
    public static final String ATTACHMENT_SIZE = "size";
    private String mCardUuid;
    private byte[] mData;
    private String mMetadata;
    private Map<String, Object> mMetadataMap;
    private Date mTimestamp;
    private boolean mTrashed;
    private String mUuid;

    public Attachment(String uuid, String carduuid, String metadata, byte[] data, boolean trash, Date timestamp) {
        this.mUuid = uuid;
        this.mCardUuid = carduuid;
        this.mMetadata = metadata;
        this.mData = data;
        this.mTrashed = trash;
        this.mTimestamp = timestamp;
    }

    public String getUuid() {
        return this.mUuid;
    }

    public void setUuid(String mUuid) {
        this.mUuid = mUuid;
    }

    public String getCardUuid() {
        return this.mCardUuid;
    }

    public void setCardUuid(String mCardUuid) {
        this.mCardUuid = mCardUuid;
    }

    public String getMetadata() {
        return this.mMetadata;
    }

    public void setMetadata(String mMetadata) {
        this.mMetadata = mMetadata;
    }

    public byte[] getData() {
        return this.mData;
    }

    public void setData(byte[] mData) {
        this.mData = mData;
    }

    public boolean isTrashed() {
        return this.mTrashed;
    }

    public void setTrashed(boolean mTrashed) {
        this.mTrashed = mTrashed;
    }

    public Date getTimestamp() {
        return this.mTimestamp;
    }

    public void setTimestamp(Date mTimestamp) {
        this.mTimestamp = mTimestamp;
    }

    public void readMetaData() {
        try {
            this.mMetadataMap = new HashMap();
            JSONObject jsonObject = new JSONObject(this.mMetadata);
            if (jsonObject.has(ATTACHMENT_FILENAME)) {
                this.mMetadataMap.put(ATTACHMENT_FILENAME, jsonObject.get(ATTACHMENT_FILENAME));
            }
            if (jsonObject.has(ATTACHMENT_KIND)) {
                this.mMetadataMap.put(ATTACHMENT_KIND, jsonObject.get(ATTACHMENT_KIND));
            }
            if (jsonObject.has(ATTACHMENT_SIZE)) {
                this.mMetadataMap.put(ATTACHMENT_SIZE, jsonObject.get(ATTACHMENT_SIZE));
            }
            if (jsonObject.has(ATTACHMENT_ORDER)) {
                this.mMetadataMap.put(ATTACHMENT_ORDER, jsonObject.get(ATTACHMENT_ORDER));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> getMetadataMap() {
        if (this.mMetadataMap == null) {
            readMetaData();
        }
        return this.mMetadataMap;
    }
}
