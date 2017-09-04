package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public final class CommentReplyList extends GenericJson {
    @Key
    private List<CommentReply> items;
    @Key
    private String kind;
    @Key
    private String nextLink;
    @Key
    private String nextPageToken;
    @Key
    private String selfLink;

    public List<CommentReply> getItems() {
        return this.items;
    }

    public CommentReplyList setItems(List<CommentReply> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public CommentReplyList setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getNextLink() {
        return this.nextLink;
    }

    public CommentReplyList setNextLink(String str) {
        this.nextLink = str;
        return this;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public CommentReplyList setNextPageToken(String str) {
        this.nextPageToken = str;
        return this;
    }

    public String getSelfLink() {
        return this.selfLink;
    }

    public CommentReplyList setSelfLink(String str) {
        this.selfLink = str;
        return this;
    }

    public CommentReplyList set(String str, Object obj) {
        return (CommentReplyList) super.set(str, obj);
    }

    public CommentReplyList clone() {
        return (CommentReplyList) super.clone();
    }
}
