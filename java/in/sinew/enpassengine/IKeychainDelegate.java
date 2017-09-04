package in.sinew.enpassengine;

public interface IKeychainDelegate {

    public enum KeychainChangeType {
        KeychainCardAdded,
        KeychainCardRemoved,
        KeychainCardChanged,
        KeychainFavoriteAdded,
        KeychainFavoriteRemoved,
        KeychainFolderAdded,
        KeychainFolderRemoved,
        KeychainFolderChanged,
        KeychainFolderCardAdded,
        KeychainFolderCardRemoved,
        KeychainCardCategoryChanged
    }

    void keychainChanged(KeychainChangeType keychainChangeType, IDisplayItem iDisplayItem, String str);
}
