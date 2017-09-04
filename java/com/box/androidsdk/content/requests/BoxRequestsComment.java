package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.models.BoxComment;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.models.BoxSimpleMessage;
import com.box.androidsdk.content.models.BoxVoid;
import com.box.androidsdk.content.requests.BoxRequest.Methods;

public class BoxRequestsComment {

    public static class AddReplyComment extends BoxRequestCommentAdd<BoxComment, AddReplyComment> {
        public /* bridge */ /* synthetic */ String getItemId() {
            return super.getItemId();
        }

        public /* bridge */ /* synthetic */ String getItemType() {
            return super.getItemType();
        }

        public /* bridge */ /* synthetic */ String getMessage() {
            return super.getMessage();
        }

        public AddReplyComment(String itemId, String message, String requestUrl, BoxSession session) {
            super(BoxComment.class, requestUrl, session);
            setItemId(itemId);
            setItemType(BoxComment.TYPE);
            setMessage(message);
        }
    }

    public static class DeleteComment extends BoxRequest<BoxVoid, DeleteComment> {
        private final String mId;

        public DeleteComment(String id, String requestUrl, BoxSession session) {
            super(BoxVoid.class, requestUrl, session);
            this.mRequestMethod = Methods.DELETE;
            this.mId = id;
        }

        public String getId() {
            return this.mId;
        }
    }

    public static class GetCommentInfo extends BoxRequestItem<BoxComment, GetCommentInfo> {
        public GetCommentInfo(String id, String requestUrl, BoxSession session) {
            super(BoxComment.class, id, requestUrl, session);
            this.mRequestMethod = Methods.GET;
        }
    }

    public static class UpdateComment extends BoxRequest<BoxComment, UpdateComment> {
        String mId;

        public UpdateComment(String id, String message, String requestUrl, BoxSession session) {
            super(BoxComment.class, requestUrl, session);
            this.mId = id;
            this.mRequestMethod = Methods.PUT;
            setMessage(message);
        }

        public String getId() {
            return this.mId;
        }

        public String getMessage() {
            return (String) this.mBodyMap.get(BoxSimpleMessage.FIELD_MESSAGE);
        }

        public UpdateComment setMessage(String message) {
            this.mBodyMap.put(BoxSimpleMessage.FIELD_MESSAGE, message);
            return this;
        }
    }
}
