package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class ChildList extends GenericJson {
    @Key
    private String etag;
    @Key
    private List<ChildReference> items;
    @Key
    private String kind;
    @Key
    private String nextLink;
    @Key
    private String nextPageToken;
    @Key
    private String selfLink;

    static {
        Data.nullOf(ChildReference.class);
    }

    public String getEtag() {
        return this.etag;
    }

    public ChildList setEtag(String str) {
        this.etag = str;
        return this;
    }

    public List<ChildReference> getItems() {
        return this.items;
    }

    public ChildList setItems(List<ChildReference> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public ChildList setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getNextLink() {
        return this.nextLink;
    }

    public ChildList setNextLink(String str) {
        this.nextLink = str;
        return this;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public ChildList setNextPageToken(String str) {
        this.nextPageToken = str;
        return this;
    }

    public String getSelfLink() {
        return this.selfLink;
    }

    public ChildList setSelfLink(String str) {
        this.selfLink = str;
        return this;
    }

    public ChildList set(String str, Object obj) {
        return (ChildList) super.set(str, obj);
    }

    public ChildList clone() {
        return (ChildList) super.clone();
    }
}
