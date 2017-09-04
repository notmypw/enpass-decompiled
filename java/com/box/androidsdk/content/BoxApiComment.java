package com.box.androidsdk.content;

import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.requests.BoxRequestsComment.AddReplyComment;
import com.box.androidsdk.content.requests.BoxRequestsComment.DeleteComment;
import com.box.androidsdk.content.requests.BoxRequestsComment.GetCommentInfo;
import com.box.androidsdk.content.requests.BoxRequestsComment.UpdateComment;

public class BoxApiComment extends BoxApi {
    public static final String COMMENTS_ENDPOINT = "/comments";

    public BoxApiComment(BoxSession session) {
        super(session);
    }

    protected String getCommentsUrl() {
        return getBaseUri() + COMMENTS_ENDPOINT;
    }

    protected String getCommentInfoUrl(String id) {
        return String.format("%s/%s", new Object[]{getCommentsUrl(), id});
    }

    public GetCommentInfo getInfoRequest(String id) {
        return new GetCommentInfo(id, getCommentInfoUrl(id), this.mSession);
    }

    public AddReplyComment getAddCommentReplyRequest(String commentId, String message) {
        return new AddReplyComment(commentId, message, getCommentsUrl(), this.mSession);
    }

    public UpdateComment getUpdateRequest(String id, String newMessage) {
        return new UpdateComment(id, newMessage, getCommentInfoUrl(id), this.mSession);
    }

    public DeleteComment getDeleteRequest(String id) {
        return new DeleteComment(id, getCommentInfoUrl(id), this.mSession);
    }
}
