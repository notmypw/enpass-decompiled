package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public final class CommentList extends GenericJson {
    @Key
    private List<Comment> items;
    @Key
    private String kind;
    @Key
    private String nextLink;
    @Key
    private String nextPageToken;
    @Key
    private String selfLink;

    public List<Comment> getItems() {
        return this.items;
    }

    public CommentList setItems(List<Comment> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public CommentList setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getNextLink() {
        return this.nextLink;
    }

    public CommentList setNextLink(String str) {
        this.nextLink = str;
        return this;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public CommentList setNextPageToken(String str) {
        this.nextPageToken = str;
        return this;
    }

    public String getSelfLink() {
        return this.selfLink;
    }

    public CommentList setSelfLink(String str) {
        this.selfLink = str;
        return this;
    }

    public CommentList set(String str, Object obj) {
        return (CommentList) super.set(str, obj);
    }

    public CommentList clone() {
        return (CommentList) super.clone();
    }
}
