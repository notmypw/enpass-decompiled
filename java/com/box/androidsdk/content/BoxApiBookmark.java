package com.box.androidsdk.content;

import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.requests.BoxRequestsBookmark.AddBookmarkToCollection;
import com.box.androidsdk.content.requests.BoxRequestsBookmark.AddCommentToBookmark;
import com.box.androidsdk.content.requests.BoxRequestsBookmark.CopyBookmark;
import com.box.androidsdk.content.requests.BoxRequestsBookmark.CreateBookmark;
import com.box.androidsdk.content.requests.BoxRequestsBookmark.DeleteBookmark;
import com.box.androidsdk.content.requests.BoxRequestsBookmark.DeleteBookmarkFromCollection;
import com.box.androidsdk.content.requests.BoxRequestsBookmark.DeleteTrashedBookmark;
import com.box.androidsdk.content.requests.BoxRequestsBookmark.GetBookmarkComments;
import com.box.androidsdk.content.requests.BoxRequestsBookmark.GetBookmarkInfo;
import com.box.androidsdk.content.requests.BoxRequestsBookmark.GetTrashedBookmark;
import com.box.androidsdk.content.requests.BoxRequestsBookmark.RestoreTrashedBookmark;
import com.box.androidsdk.content.requests.BoxRequestsBookmark.UpdateBookmark;
import com.box.androidsdk.content.requests.BoxRequestsBookmark.UpdateSharedBookmark;

public class BoxApiBookmark extends BoxApi {
    public BoxApiBookmark(BoxSession session) {
        super(session);
    }

    protected String getBookmarksUrl() {
        return String.format("%s/web_links", new Object[]{getBaseUri()});
    }

    protected String getBookmarkInfoUrl(String id) {
        return String.format("%s/%s", new Object[]{getBookmarksUrl(), id});
    }

    protected String getBookmarkCopyUrl(String id) {
        return String.format(getBookmarkInfoUrl(id) + "/copy", new Object[0]);
    }

    protected String getTrashedBookmarkUrl(String id) {
        return getBookmarkInfoUrl(id) + "/trash";
    }

    protected String getBookmarkCommentsUrl(String id) {
        return getBookmarkInfoUrl(id) + BoxApiComment.COMMENTS_ENDPOINT;
    }

    protected String getCommentUrl() {
        return getBaseUri() + BoxApiComment.COMMENTS_ENDPOINT;
    }

    public GetBookmarkInfo getInfoRequest(String id) {
        return new GetBookmarkInfo(id, getBookmarkInfoUrl(id), this.mSession);
    }

    public CreateBookmark getCreateRequest(String parentId, String url) {
        return new CreateBookmark(parentId, url, getBookmarksUrl(), this.mSession);
    }

    public UpdateBookmark getUpdateRequest(String id) {
        return new UpdateBookmark(id, getBookmarkInfoUrl(id), this.mSession);
    }

    public CopyBookmark getCopyRequest(String id, String parentId) {
        return new CopyBookmark(id, parentId, getBookmarkCopyUrl(id), this.mSession);
    }

    public UpdateBookmark getRenameRequest(String id, String newName) {
        UpdateBookmark request = new UpdateBookmark(id, getBookmarkInfoUrl(id), this.mSession);
        request.setName(newName);
        return request;
    }

    public UpdateBookmark getMoveRequest(String id, String parentId) {
        UpdateBookmark request = new UpdateBookmark(id, getBookmarkInfoUrl(id), this.mSession);
        request.setParentId(parentId);
        return request;
    }

    public DeleteBookmark getDeleteRequest(String id) {
        return new DeleteBookmark(id, getBookmarkInfoUrl(id), this.mSession);
    }

    public UpdateSharedBookmark getCreateSharedLinkRequest(String id) {
        return (UpdateSharedBookmark) new UpdateSharedBookmark(id, getBookmarkInfoUrl(id), this.mSession).setAccess(null);
    }

    public UpdateBookmark getDisableSharedLinkRequest(String id) {
        return (UpdateBookmark) new UpdateBookmark(id, getBookmarkInfoUrl(id), this.mSession).setSharedLink(null);
    }

    public AddCommentToBookmark getAddCommentRequest(String bookmarkId, String message) {
        return new AddCommentToBookmark(bookmarkId, message, getCommentUrl(), this.mSession);
    }

    public GetTrashedBookmark getTrashedBookmarkRequest(String id) {
        return new GetTrashedBookmark(id, getTrashedBookmarkUrl(id), this.mSession);
    }

    public DeleteTrashedBookmark getDeleteTrashedBookmarkRequest(String id) {
        return new DeleteTrashedBookmark(id, getTrashedBookmarkUrl(id), this.mSession);
    }

    public RestoreTrashedBookmark getRestoreTrashedBookmarkRequest(String id) {
        return new RestoreTrashedBookmark(id, getBookmarkInfoUrl(id), this.mSession);
    }

    public GetBookmarkComments getCommentsRequest(String id) {
        return new GetBookmarkComments(id, getBookmarkCommentsUrl(id), this.mSession);
    }

    public AddBookmarkToCollection getAddToCollectionRequest(String bookmarkId, String collectionId) {
        return new AddBookmarkToCollection(bookmarkId, collectionId, getBookmarkInfoUrl(bookmarkId), this.mSession);
    }

    public DeleteBookmarkFromCollection getDeleteFromCollectionRequest(String id) {
        return new DeleteBookmarkFromCollection(id, getBookmarkInfoUrl(id), this.mSession);
    }
}
