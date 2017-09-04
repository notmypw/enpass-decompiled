package com.box.androidsdk.content;

import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.requests.BoxRequestsFolder.AddFolderToCollection;
import com.box.androidsdk.content.requests.BoxRequestsFolder.CopyFolder;
import com.box.androidsdk.content.requests.BoxRequestsFolder.CreateFolder;
import com.box.androidsdk.content.requests.BoxRequestsFolder.DeleteFolder;
import com.box.androidsdk.content.requests.BoxRequestsFolder.DeleteFolderFromCollection;
import com.box.androidsdk.content.requests.BoxRequestsFolder.DeleteTrashedFolder;
import com.box.androidsdk.content.requests.BoxRequestsFolder.GetCollaborations;
import com.box.androidsdk.content.requests.BoxRequestsFolder.GetFolderInfo;
import com.box.androidsdk.content.requests.BoxRequestsFolder.GetFolderItems;
import com.box.androidsdk.content.requests.BoxRequestsFolder.GetTrashedFolder;
import com.box.androidsdk.content.requests.BoxRequestsFolder.GetTrashedItems;
import com.box.androidsdk.content.requests.BoxRequestsFolder.RestoreTrashedFolder;
import com.box.androidsdk.content.requests.BoxRequestsFolder.UpdateFolder;
import com.box.androidsdk.content.requests.BoxRequestsFolder.UpdateSharedFolder;

public class BoxApiFolder extends BoxApi {
    public BoxApiFolder(BoxSession session) {
        super(session);
    }

    protected String getFoldersUrl() {
        return String.format("%s/folders", new Object[]{getBaseUri()});
    }

    protected String getFolderInfoUrl(String id) {
        return String.format("%s/%s", new Object[]{getFoldersUrl(), id});
    }

    protected String getFolderItemsUrl(String id) {
        return getFolderInfoUrl(id) + "/items";
    }

    protected String getFolderCollaborationsUrl(String id) {
        return getFolderInfoUrl(id) + "/collaborations";
    }

    protected String getFolderCopyUrl(String id) {
        return getFolderInfoUrl(id) + "/copy";
    }

    protected String getTrashedFolderUrl(String id) {
        return getFolderInfoUrl(id) + "/trash";
    }

    protected String getTrashedItemsUrl() {
        return getFoldersUrl() + "/trash/items";
    }

    public GetFolderInfo getInfoRequest(String id) {
        return new GetFolderInfo(id, getFolderInfoUrl(id), this.mSession);
    }

    public GetFolderItems getItemsRequest(String id) {
        return new GetFolderItems(id, getFolderItemsUrl(id), this.mSession);
    }

    public CreateFolder getCreateRequest(String parentId, String name) {
        return new CreateFolder(parentId, name, getFoldersUrl(), this.mSession);
    }

    public UpdateFolder getUpdateRequest(String id) {
        return new UpdateFolder(id, getFolderInfoUrl(id), this.mSession);
    }

    public UpdateFolder getRenameRequest(String id, String newName) {
        return (UpdateFolder) new UpdateFolder(id, getFolderInfoUrl(id), this.mSession).setName(newName);
    }

    public UpdateFolder getMoveRequest(String id, String parentId) {
        return (UpdateFolder) new UpdateFolder(id, getFolderInfoUrl(id), this.mSession).setParentId(parentId);
    }

    public CopyFolder getCopyRequest(String id, String parentId) {
        return new CopyFolder(id, parentId, getFolderCopyUrl(id), this.mSession);
    }

    public DeleteFolder getDeleteRequest(String id) {
        return new DeleteFolder(id, getFolderInfoUrl(id), this.mSession);
    }

    public GetCollaborations getCollaborationsRequest(String id) {
        return new GetCollaborations(id, getFolderCollaborationsUrl(id), this.mSession);
    }

    public UpdateSharedFolder getCreateSharedLinkRequest(String id) {
        return (UpdateSharedFolder) new UpdateSharedFolder(id, getFolderInfoUrl(id), this.mSession).setAccess(null);
    }

    public UpdateFolder getDisableSharedLinkRequest(String id) {
        return (UpdateFolder) new UpdateFolder(id, getFolderInfoUrl(id), this.mSession).setSharedLink(null);
    }

    public AddFolderToCollection getAddToCollectionRequest(String folderId, String collectionId) {
        return new AddFolderToCollection(folderId, collectionId, getFolderInfoUrl(folderId), this.mSession);
    }

    public DeleteFolderFromCollection getDeleteFromCollectionRequest(String id) {
        return new DeleteFolderFromCollection(id, getFolderInfoUrl(id), this.mSession);
    }

    public GetTrashedItems getTrashedItemsRequest() {
        return new GetTrashedItems(getTrashedItemsUrl(), this.mSession);
    }

    public GetTrashedFolder getTrashedFolderRequest(String id) {
        return new GetTrashedFolder(id, getTrashedFolderUrl(id), this.mSession);
    }

    public DeleteTrashedFolder getDeleteTrashedFolderRequest(String id) {
        return new DeleteTrashedFolder(id, getTrashedFolderUrl(id), this.mSession);
    }

    public RestoreTrashedFolder getRestoreTrashedFolderRequest(String id) {
        return new RestoreTrashedFolder(id, getFolderInfoUrl(id), this.mSession);
    }
}
