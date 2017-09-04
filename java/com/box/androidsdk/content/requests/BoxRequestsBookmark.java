package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.models.BoxBookmark;
import com.box.androidsdk.content.models.BoxComment;
import com.box.androidsdk.content.models.BoxEntity;
import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.models.BoxFolder;
import com.box.androidsdk.content.models.BoxItem;
import com.box.androidsdk.content.models.BoxListComments;
import com.box.androidsdk.content.models.BoxMetadata;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.models.BoxSharedLink;
import com.box.androidsdk.content.requests.BoxRequest.Methods;
import java.util.HashMap;

public class BoxRequestsBookmark {

    public static class AddBookmarkToCollection extends BoxRequestCollectionUpdate<BoxBookmark, AddBookmarkToCollection> {
        public AddBookmarkToCollection(String id, String collectionId, String requestUrl, BoxSession session) {
            super(BoxBookmark.class, id, requestUrl, session);
            setCollectionId(collectionId);
        }

        public AddBookmarkToCollection setCollectionId(String id) {
            return (AddBookmarkToCollection) super.setCollectionId(id);
        }
    }

    public static class AddCommentToBookmark extends BoxRequestCommentAdd<BoxComment, AddCommentToBookmark> {
        public /* bridge */ /* synthetic */ String getItemId() {
            return super.getItemId();
        }

        public /* bridge */ /* synthetic */ String getItemType() {
            return super.getItemType();
        }

        public /* bridge */ /* synthetic */ String getMessage() {
            return super.getMessage();
        }

        public AddCommentToBookmark(String bookmarkId, String message, String requestUrl, BoxSession session) {
            super(BoxComment.class, requestUrl, session);
            setItemId(bookmarkId);
            setItemType(BoxBookmark.TYPE);
            setMessage(message);
        }
    }

    public static class CopyBookmark extends BoxRequestItemCopy<BoxBookmark, CopyBookmark> {
        public /* bridge */ /* synthetic */ String getName() {
            return super.getName();
        }

        public /* bridge */ /* synthetic */ String getParentId() {
            return super.getParentId();
        }

        public CopyBookmark(String id, String parentId, String requestUrl, BoxSession session) {
            super(BoxBookmark.class, id, parentId, requestUrl, session);
        }
    }

    public static class CreateBookmark extends BoxRequestItem<BoxBookmark, CreateBookmark> {
        public CreateBookmark(String parentId, String url, String requestUrl, BoxSession session) {
            super(BoxBookmark.class, null, requestUrl, session);
            this.mRequestMethod = Methods.POST;
            setParentId(parentId);
            setUrl(url);
        }

        public String getParentId() {
            return this.mBodyMap.containsKey(BoxMetadata.FIELD_PARENT) ? (String) this.mBodyMap.get(BoxEntity.FIELD_ID) : null;
        }

        public CreateBookmark setParentId(String id) {
            HashMap<String, Object> map = new HashMap();
            map.put(BoxEntity.FIELD_ID, id);
            this.mBodyMap.put(BoxMetadata.FIELD_PARENT, new BoxFolder(map));
            return this;
        }

        public String getUrl() {
            return (String) this.mBodyMap.get(BoxSharedLink.FIELD_URL);
        }

        public CreateBookmark setUrl(String url) {
            this.mBodyMap.put(BoxSharedLink.FIELD_URL, url);
            return this;
        }

        public String getName() {
            return (String) this.mBodyMap.get(BoxFileVersion.FIELD_NAME);
        }

        public CreateBookmark setName(String name) {
            this.mBodyMap.put(BoxFileVersion.FIELD_NAME, name);
            return this;
        }

        public String getDescription() {
            return (String) this.mBodyMap.get(BoxItem.FIELD_DESCRIPTION);
        }

        public CreateBookmark setDescription(String description) {
            this.mBodyMap.put(BoxItem.FIELD_DESCRIPTION, description);
            return this;
        }
    }

    public static class DeleteBookmark extends BoxRequestItemDelete<DeleteBookmark> {
        public /* bridge */ /* synthetic */ String getId() {
            return super.getId();
        }

        public /* bridge */ /* synthetic */ String getIfMatchEtag() {
            return super.getIfMatchEtag();
        }

        public DeleteBookmark(String id, String requestUrl, BoxSession session) {
            super(id, requestUrl, session);
        }
    }

    public static class DeleteBookmarkFromCollection extends BoxRequestCollectionUpdate<BoxBookmark, DeleteBookmarkFromCollection> {
        public DeleteBookmarkFromCollection(String id, String requestUrl, BoxSession session) {
            super(BoxBookmark.class, id, requestUrl, session);
            setCollectionId(null);
        }
    }

    public static class DeleteTrashedBookmark extends BoxRequestItemDelete<DeleteTrashedBookmark> {
        public /* bridge */ /* synthetic */ String getId() {
            return super.getId();
        }

        public /* bridge */ /* synthetic */ String getIfMatchEtag() {
            return super.getIfMatchEtag();
        }

        public DeleteTrashedBookmark(String id, String requestUrl, BoxSession session) {
            super(id, requestUrl, session);
        }
    }

    public static class GetBookmarkComments extends BoxRequestItem<BoxListComments, GetBookmarkComments> {
        public GetBookmarkComments(String id, String requestUrl, BoxSession session) {
            super(BoxListComments.class, id, requestUrl, session);
            this.mRequestMethod = Methods.GET;
        }
    }

    public static class GetBookmarkInfo extends BoxRequestItem<BoxBookmark, GetBookmarkInfo> {
        public GetBookmarkInfo(String id, String requestUrl, BoxSession session) {
            super(BoxBookmark.class, id, requestUrl, session);
            this.mRequestMethod = Methods.GET;
        }

        public GetBookmarkInfo setIfNoneMatchEtag(String etag) {
            return (GetBookmarkInfo) super.setIfNoneMatchEtag(etag);
        }

        public String getIfNoneMatchEtag() {
            return super.getIfNoneMatchEtag();
        }
    }

    public static class GetTrashedBookmark extends BoxRequestItem<BoxBookmark, GetTrashedBookmark> {
        public GetTrashedBookmark(String id, String requestUrl, BoxSession session) {
            super(BoxBookmark.class, id, requestUrl, session);
            this.mRequestMethod = Methods.GET;
        }

        public GetTrashedBookmark setIfNoneMatchEtag(String etag) {
            return (GetTrashedBookmark) super.setIfNoneMatchEtag(etag);
        }

        public String getIfNoneMatchEtag() {
            return super.getIfNoneMatchEtag();
        }
    }

    public static class RestoreTrashedBookmark extends BoxRequestItemRestoreTrashed<BoxBookmark, RestoreTrashedBookmark> {
        public /* bridge */ /* synthetic */ String getName() {
            return super.getName();
        }

        public /* bridge */ /* synthetic */ String getParentId() {
            return super.getParentId();
        }

        public RestoreTrashedBookmark(String id, String requestUrl, BoxSession session) {
            super(BoxBookmark.class, id, requestUrl, session);
        }
    }

    public static class UpdateBookmark extends BoxRequestItemUpdate<BoxBookmark, UpdateBookmark> {
        public UpdateBookmark(String id, String requestUrl, BoxSession session) {
            super(BoxBookmark.class, id, requestUrl, session);
        }

        public String getUrl() {
            return (String) this.mBodyMap.get(BoxSharedLink.FIELD_URL);
        }

        public UpdateBookmark setUrl(String url) {
            this.mBodyMap.put(BoxSharedLink.FIELD_URL, url);
            return this;
        }

        public UpdateSharedBookmark updateSharedLink() {
            return new UpdateSharedBookmark(this);
        }
    }

    public static class UpdateSharedBookmark extends BoxRequestUpdateSharedItem<BoxBookmark, UpdateSharedBookmark> {
        public UpdateSharedBookmark(String id, String requestUrl, BoxSession session) {
            super(BoxBookmark.class, id, requestUrl, session);
        }

        public UpdateSharedBookmark(UpdateBookmark update) {
            super(update);
        }
    }
}
