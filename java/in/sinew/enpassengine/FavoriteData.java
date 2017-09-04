package in.sinew.enpassengine;

public class FavoriteData {
    long mTimestamp;
    boolean mTrashed;
    String mUuid;

    public FavoriteData(String aUuid, long aTimestamp, boolean aTrashed) {
        this.mUuid = aUuid;
        this.mTimestamp = aTimestamp;
        this.mTrashed = aTrashed;
    }

    public String getUuid() {
        return this.mUuid;
    }

    public boolean getTrashed() {
        return this.mTrashed;
    }
}
