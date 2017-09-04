package in.sinew.enpassengine;

import in.sinew.enpassengine.IKeychainDelegate.KeychainChangeType;

public interface IAppEventSubscriber {
    void ItemChanged(KeychainChangeType keychainChangeType, IDisplayItem iDisplayItem, String str);

    void reload();
}
