package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

public final class CommentReply extends GenericJson {
    @Key
    private User author;
    @Key
    private String content;
    @Key
    private DateTime createdDate;
    @Key
    private Boolean deleted;
    @Key
    private String htmlContent;
    @Key
    private String kind;
    @Key
    private DateTime modifiedDate;
    @Key
    private String replyId;
    @Key
    private String verb;

    public User getAuthor() {
        return this.author;
    }

    public CommentReply setAuthor(User user) {
        this.author = user;
        return this;
    }

    public String getContent() {
        return this.content;
    }

    public CommentReply setContent(String str) {
        this.content = str;
        return this;
    }

    public DateTime getCreatedDate() {
        return this.createdDate;
    }

    public CommentReply setCreatedDate(DateTime dateTime) {
        this.createdDate = dateTime;
        return this;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public CommentReply setDeleted(Boolean bool) {
        this.deleted = bool;
        return this;
    }

    public String getHtmlContent() {
        return this.htmlContent;
    }

    public CommentReply setHtmlContent(String str) {
        this.htmlContent = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public CommentReply setKind(String str) {
        this.kind = str;
        return this;
    }

    public DateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public CommentReply setModifiedDate(DateTime dateTime) {
        this.modifiedDate = dateTime;
        return this;
    }

    public String getReplyId() {
        return this.replyId;
    }

    public CommentReply setReplyId(String str) {
        this.replyId = str;
        return this;
    }

    public String getVerb() {
        return this.verb;
    }

    public CommentReply setVerb(String str) {
        this.verb = str;
        return this;
    }

    public CommentReply set(String str, Object obj) {
        return (CommentReply) super.set(str, obj);
    }

    public CommentReply clone() {
        return (CommentReply) super.clone();
    }
}
