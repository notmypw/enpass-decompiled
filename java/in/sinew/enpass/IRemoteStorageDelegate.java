package in.sinew.enpass;

public interface IRemoteStorageDelegate {
    void latestRequestDone(IRemoteStorage iRemoteStorage);

    void latestRequestError(IRemoteStorage iRemoteStorage, String str);

    void latestRequestNotFound();

    void uploadRequestDone(IRemoteStorage iRemoteStorage);

    void uploadRequestError(IRemoteStorage iRemoteStorage, String str);
}
