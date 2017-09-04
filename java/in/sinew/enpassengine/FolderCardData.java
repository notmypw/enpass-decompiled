package in.sinew.enpassengine;

public class FolderCardData {
    String mCardUuid;
    String mFolderUuid;
    boolean mTrashed;
    double mUpdateTime;

    public FolderCardData(String aFolderUuid, String aCardUuid, double aUpdateTime, boolean aTrashed) {
        this.mFolderUuid = aFolderUuid;
        this.mCardUuid = aCardUuid;
        this.mUpdateTime = aUpdateTime;
        this.mTrashed = aTrashed;
    }

    public String getFolderUuid() {
        return this.mFolderUuid;
    }

    public String getCardUuid() {
        return this.mCardUuid;
    }

    public boolean getTrashed() {
        return this.mTrashed;
    }
}
