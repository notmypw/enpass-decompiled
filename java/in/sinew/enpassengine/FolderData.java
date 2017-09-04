package in.sinew.enpassengine;

public class FolderData {
    int mIconId;
    String mParent;
    String mTitle;
    boolean mTrashed;
    double mUpdateTime;
    String mUuid;

    FolderData(String aTitle, double aUpdateTime, String aUuid, String aParent, boolean aTrashed, int iconId) {
        this.mTitle = aTitle;
        this.mUpdateTime = aUpdateTime;
        this.mUuid = aUuid;
        this.mParent = aParent;
        this.mTrashed = aTrashed;
        this.mIconId = iconId;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getUuid() {
        return this.mUuid;
    }

    public String getParent() {
        return this.mParent;
    }

    public boolean getTrashed() {
        return this.mTrashed;
    }

    public int getIconId() {
        return this.mIconId;
    }
}
