package in.sinew.enpass;

public interface ISyncManagerDelegate {
    void realSyncStarted();

    void syncAborted();

    void syncDone();

    void syncError(String str);

    void syncPasswordError(IRemoteStorage iRemoteStorage);

    void syncStarted();
}
