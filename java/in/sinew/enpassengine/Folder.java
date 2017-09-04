package in.sinew.enpassengine;

import in.sinew.enpassengine.IDisplayItem.DisplayItemType;

public class Folder implements IDisplayItem {
    long mChildcardsCount;
    String mFolderUuid;
    int mIconId;
    String mName;
    String mParentUuid;
    long mSubFoldersCount;

    public Folder(String aName, String aParentUuid, int aIconId, String aFolderUuid) {
        this.mName = aName;
        this.mParentUuid = aParentUuid;
        this.mIconId = aIconId;
        this.mFolderUuid = aFolderUuid;
    }

    public int getDisplayIconId() {
        return this.mIconId;
    }

    public String getDisplayName() {
        return this.mName;
    }

    public String getDisplayIdentifier() {
        return this.mFolderUuid;
    }

    public DisplayItemType getDisplayType() {
        return DisplayItemType.DisplayItemFolder;
    }

    public void setChildcardsCount(long childcardsCount) {
        this.mChildcardsCount = childcardsCount;
    }

    public void setSubFoldersCount(long subFoldersCount) {
        this.mSubFoldersCount = subFoldersCount;
    }

    public String getSubTitle() {
        return Long.toString(this.mChildcardsCount + this.mSubFoldersCount);
    }

    public String getParent() {
        return this.mParentUuid;
    }
}
