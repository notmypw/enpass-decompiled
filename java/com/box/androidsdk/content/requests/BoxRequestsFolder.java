package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.models.BoxEntity;
import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.models.BoxFolder;
import com.box.androidsdk.content.models.BoxFolder.SyncState;
import com.box.androidsdk.content.models.BoxItem;
import com.box.androidsdk.content.models.BoxList;
import com.box.androidsdk.content.models.BoxListCollaborations;
import com.box.androidsdk.content.models.BoxListItems;
import com.box.androidsdk.content.models.BoxMetadata;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.models.BoxUploadEmail;
import com.box.androidsdk.content.models.BoxUploadEmail.Access;
import com.box.androidsdk.content.models.BoxUser;
import com.box.androidsdk.content.requests.BoxRequest.Methods;
import com.eclipsesource.json.JsonObject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class BoxRequestsFolder {

    public static class AddFolderToCollection extends BoxRequestCollectionUpdate<BoxFolder, AddFolderToCollection> {
        public AddFolderToCollection(String id, String collectionId, String requestUrl, BoxSession session) {
            super(BoxFolder.class, id, requestUrl, session);
            setCollectionId(collectionId);
            this.mRequestMethod = Methods.PUT;
        }

        public AddFolderToCollection setCollectionId(String id) {
            return (AddFolderToCollection) super.setCollectionId(id);
        }
    }

    public static class CopyFolder extends BoxRequestItemCopy<BoxFolder, CopyFolder> {
        public /* bridge */ /* synthetic */ String getName() {
            return super.getName();
        }

        public /* bridge */ /* synthetic */ String getParentId() {
            return super.getParentId();
        }

        public CopyFolder(String id, String parentId, String requestUrl, BoxSession session) {
            super(BoxFolder.class, id, parentId, requestUrl, session);
        }
    }

    public static class CreateFolder extends BoxRequestItem<BoxFolder, CreateFolder> {
        public CreateFolder(String parentId, String name, String requestUrl, BoxSession session) {
            super(BoxFolder.class, null, requestUrl, session);
            this.mRequestMethod = Methods.POST;
            setParentId(parentId);
            setName(name);
        }

        public String getParentId() {
            return this.mBodyMap.containsKey(BoxMetadata.FIELD_PARENT) ? (String) this.mBodyMap.get(BoxEntity.FIELD_ID) : null;
        }

        public CreateFolder setParentId(String id) {
            HashMap<String, Object> map = new HashMap();
            map.put(BoxEntity.FIELD_ID, id);
            this.mBodyMap.put(BoxMetadata.FIELD_PARENT, new BoxFolder(map));
            return this;
        }

        public String getName() {
            return (String) this.mBodyMap.get(BoxFileVersion.FIELD_NAME);
        }

        public CreateFolder setName(String name) {
            this.mBodyMap.put(BoxFileVersion.FIELD_NAME, name);
            return this;
        }
    }

    public static class DeleteFolder extends BoxRequestItemDelete<DeleteFolder> {
        private static final String FALSE = "false";
        private static final String FIELD_RECURSIVE = "recursive";
        private static final String TRUE = "true";

        public /* bridge */ /* synthetic */ String getId() {
            return super.getId();
        }

        public /* bridge */ /* synthetic */ String getIfMatchEtag() {
            return super.getIfMatchEtag();
        }

        public DeleteFolder(String id, String requestUrl, BoxSession session) {
            super(id, requestUrl, session);
            setRecursive(true);
        }

        public DeleteFolder setRecursive(boolean recursive) {
            this.mQueryMap.put(FIELD_RECURSIVE, recursive ? TRUE : FALSE);
            return this;
        }

        public Boolean getRecursive() {
            return Boolean.valueOf(TRUE.equals(this.mQueryMap.get(FIELD_RECURSIVE)));
        }
    }

    public static class DeleteFolderFromCollection extends BoxRequestCollectionUpdate<BoxFolder, AddFolderToCollection> {
        public DeleteFolderFromCollection(String id, String requestUrl, BoxSession session) {
            super(BoxFolder.class, id, requestUrl, session);
            setCollectionId(null);
        }
    }

    public static class DeleteTrashedFolder extends BoxRequestItemDelete<DeleteTrashedFolder> {
        public /* bridge */ /* synthetic */ String getId() {
            return super.getId();
        }

        public /* bridge */ /* synthetic */ String getIfMatchEtag() {
            return super.getIfMatchEtag();
        }

        public DeleteTrashedFolder(String id, String requestUrl, BoxSession session) {
            super(id, requestUrl, session);
        }
    }

    public static class GetCollaborations extends BoxRequestItem<BoxListCollaborations, GetCollaborations> {
        public GetCollaborations(String id, String requestUrl, BoxSession session) {
            super(BoxListCollaborations.class, id, requestUrl, session);
            this.mRequestMethod = Methods.GET;
        }
    }

    public static class GetFolderInfo extends BoxRequestItem<BoxFolder, GetFolderInfo> {
        public GetFolderInfo(String id, String requestUrl, BoxSession session) {
            super(BoxFolder.class, id, requestUrl, session);
            this.mRequestMethod = Methods.GET;
        }

        public GetFolderInfo setIfNoneMatchEtag(String etag) {
            return (GetFolderInfo) super.setIfNoneMatchEtag(etag);
        }

        public String getIfNoneMatchEtag() {
            return super.getIfNoneMatchEtag();
        }

        public GetFolderInfo setLimit(int limit) {
            this.mQueryMap.put(BoxList.FIELD_LIMIT, String.valueOf(limit));
            return this;
        }

        public GetFolderInfo setOffset(int offset) {
            this.mQueryMap.put(BoxList.FIELD_OFFSET, String.valueOf(offset));
            return this;
        }
    }

    public static class GetFolderItems extends BoxRequestItem<BoxListItems, GetFolderItems> {
        private static final String DEFAULT_LIMIT = "1000";
        private static final String DEFAULT_OFFSET = "0";
        private static final String LIMIT = "limit";
        private static final String OFFSET = "offset";

        public GetFolderItems(String id, String requestUrl, BoxSession session) {
            super(BoxListItems.class, id, requestUrl, session);
            this.mRequestMethod = Methods.GET;
            this.mQueryMap.put(LIMIT, DEFAULT_LIMIT);
            this.mQueryMap.put(OFFSET, DEFAULT_OFFSET);
        }

        public GetFolderItems setLimit(int limit) {
            this.mQueryMap.put(LIMIT, String.valueOf(limit));
            return this;
        }

        public GetFolderItems setOffset(int offset) {
            this.mQueryMap.put(OFFSET, String.valueOf(offset));
            return this;
        }
    }

    public static class GetTrashedFolder extends BoxRequestItem<BoxFolder, GetTrashedFolder> {
        public GetTrashedFolder(String id, String requestUrl, BoxSession session) {
            super(BoxFolder.class, id, requestUrl, session);
            this.mRequestMethod = Methods.GET;
        }

        public GetTrashedFolder setIfNoneMatchEtag(String etag) {
            return (GetTrashedFolder) super.setIfNoneMatchEtag(etag);
        }

        public String getIfNoneMatchEtag() {
            return super.getIfNoneMatchEtag();
        }
    }

    public static class GetTrashedItems extends BoxRequest<BoxListItems, GetTrashedItems> {
        public GetTrashedItems(String requestUrl, BoxSession session) {
            super(BoxListItems.class, requestUrl, session);
            this.mRequestMethod = Methods.GET;
        }
    }

    public static class RestoreTrashedFolder extends BoxRequestItemRestoreTrashed<BoxFolder, RestoreTrashedFolder> {
        public /* bridge */ /* synthetic */ String getName() {
            return super.getName();
        }

        public /* bridge */ /* synthetic */ String getParentId() {
            return super.getParentId();
        }

        public RestoreTrashedFolder(String id, String requestUrl, BoxSession session) {
            super(BoxFolder.class, id, requestUrl, session);
        }
    }

    public static class UpdateFolder extends BoxRequestItemUpdate<BoxFolder, UpdateFolder> {
        public UpdateFolder(String id, String requestUrl, BoxSession session) {
            super(BoxFolder.class, id, requestUrl, session);
        }

        public UpdateSharedFolder updateSharedLink() {
            return new UpdateSharedFolder(this);
        }

        public SyncState getSyncState() {
            return this.mBodyMap.containsKey(BoxFolder.FIELD_SYNC_STATE) ? (SyncState) this.mBodyMap.get(BoxFolder.FIELD_SYNC_STATE) : null;
        }

        public UpdateFolder setSyncState(SyncState syncState) {
            this.mBodyMap.put(BoxFolder.FIELD_SYNC_STATE, syncState);
            return this;
        }

        public Access getUploadEmailAccess() {
            return this.mBodyMap.containsKey(BoxFolder.FIELD_FOLDER_UPLOAD_EMAIL) ? ((BoxUploadEmail) this.mBodyMap.get(BoxFolder.FIELD_FOLDER_UPLOAD_EMAIL)).getAccess() : null;
        }

        public UpdateFolder setFolderUploadEmailAccess(Access access) {
            LinkedHashMap<String, Object> map = new LinkedHashMap();
            map.put(BoxUploadEmail.FIELD_ACCESS, access == null ? "null" : access.toString());
            this.mBodyMap.put(BoxFolder.FIELD_FOLDER_UPLOAD_EMAIL, new BoxUploadEmail(map));
            return this;
        }

        public String getOwnedById() {
            return this.mBodyMap.containsKey(BoxItem.FIELD_OWNED_BY) ? ((BoxUser) this.mBodyMap.get(BoxItem.FIELD_OWNED_BY)).getId() : null;
        }

        public UpdateFolder setOwnedById(String userId) {
            LinkedHashMap<String, Object> map = new LinkedHashMap();
            map.put(BoxEntity.FIELD_ID, userId);
            this.mBodyMap.put(BoxItem.FIELD_OWNED_BY, new BoxUser(map));
            return this;
        }

        protected void parseHashMapEntry(JsonObject jsonBody, Entry<String, Object> entry) {
            if (((String) entry.getKey()).equals(BoxFolder.FIELD_FOLDER_UPLOAD_EMAIL)) {
                jsonBody.add((String) entry.getKey(), parseJsonObject(entry.getValue()));
            } else if (((String) entry.getKey()).equals(BoxItem.FIELD_OWNED_BY)) {
                jsonBody.add((String) entry.getKey(), parseJsonObject(entry.getValue()));
            } else if (((String) entry.getKey()).equals(BoxFolder.FIELD_SYNC_STATE)) {
                jsonBody.add((String) entry.getKey(), ((SyncState) entry.getValue()).toString());
            } else {
                super.parseHashMapEntry(jsonBody, entry);
            }
        }
    }

    public static class UpdateSharedFolder extends BoxRequestUpdateSharedItem<BoxFolder, UpdateSharedFolder> {
        public UpdateSharedFolder(String id, String requestUrl, BoxSession session) {
            super(BoxFolder.class, id, requestUrl, session);
        }

        protected UpdateSharedFolder(UpdateFolder r) {
            super(r);
        }

        public UpdateSharedFolder setCanDownload(boolean canDownload) {
            return (UpdateSharedFolder) super.setCanDownload(canDownload);
        }

        public Boolean getCanDownload() {
            return super.getCanDownload();
        }
    }
}
