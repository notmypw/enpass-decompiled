package in.sinew.enpassengine;

public interface IDisplayItem {
    int getDisplayIconId();

    String getDisplayIdentifier();

    String getDisplayName();

    DisplayItemType getDisplayType();

    String getSubTitle();
}
