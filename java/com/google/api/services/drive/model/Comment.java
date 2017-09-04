package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import java.util.List;

public final class Comment extends GenericJson {
    @Key
    private String anchor;
    @Key
    private User author;
    @Key
    private String commentId;
    @Key
    private String content;
    @Key
    private Context context;
    @Key
    private DateTime createdDate;
    @Key
    private Boolean deleted;
    @Key
    private String fileId;
    @Key
    private String fileTitle;
    @Key
    private String htmlContent;
    @Key
    private String kind;
    @Key
    private DateTime modifiedDate;
    @Key
    private List<CommentReply> replies;
    @Key
    private String selfLink;
    @Key
    private String status;

    public static final class Context extends GenericJson {
        @Key
        private String type;
        @Key
        private String value;

        public String getType() {
            return this.type;
        }

        public Context setType(String str) {
            this.type = str;
            return this;
        }

        public String getValue() {
            return this.value;
        }

        public Context setValue(String str) {
            this.value = str;
            return this;
        }

        public Context set(String str, Object obj) {
            return (Context) super.set(str, obj);
        }

        public Context clone() {
            return (Context) super.clone();
        }
    }

    static {
        Data.nullOf(CommentReply.class);
    }

    public String getAnchor() {
        return this.anchor;
    }

    public Comment setAnchor(String str) {
        this.anchor = str;
        return this;
    }

    public User getAuthor() {
        return this.author;
    }

    public Comment setAuthor(User user) {
        this.author = user;
        return this;
    }

    public String getCommentId() {
        return this.commentId;
    }

    public Comment setCommentId(String str) {
        this.commentId = str;
        return this;
    }

    public String getContent() {
        return this.content;
    }

    public Comment setContent(String str) {
        this.content = str;
        return this;
    }

    public Context getContext() {
        return this.context;
    }

    public Comment setContext(Context context) {
        this.context = context;
        return this;
    }

    public DateTime getCreatedDate() {
        return this.createdDate;
    }

    public Comment setCreatedDate(DateTime dateTime) {
        this.createdDate = dateTime;
        return this;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public Comment setDeleted(Boolean bool) {
        this.deleted = bool;
        return this;
    }

    public String getFileId() {
        return this.fileId;
    }

    public Comment setFileId(String str) {
        this.fileId = str;
        return this;
    }

    public String getFileTitle() {
        return this.fileTitle;
    }

    public Comment setFileTitle(String str) {
        this.fileTitle = str;
        return this;
    }

    public String getHtmlContent() {
        return this.htmlContent;
    }

    public Comment setHtmlContent(String str) {
        this.htmlContent = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public Comment setKind(String str) {
        this.kind = str;
        return this;
    }

    public DateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public Comment setModifiedDate(DateTime dateTime) {
        this.modifiedDate = dateTime;
        return this;
    }

    public List<CommentReply> getReplies() {
        return this.replies;
    }

    public Comment setReplies(List<CommentReply> list) {
        this.replies = list;
        return this;
    }

    public String getSelfLink() {
        return this.selfLink;
    }

    public Comment setSelfLink(String str) {
        this.selfLink = str;
        return this;
    }

    public String getStatus() {
        return this.status;
    }

    public Comment setStatus(String str) {
        this.status = str;
        return this;
    }

    public Comment set(String str, Object obj) {
        return (Comment) super.set(str, obj);
    }

    public Comment clone() {
        return (Comment) super.clone();
    }
}
