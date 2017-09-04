package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public final class ParentList extends GenericJson {
    @Key
    private String etag;
    @Key
    private List<ParentReference> items;
    @Key
    private String kind;
    @Key
    private String selfLink;

    public String getEtag() {
        return this.etag;
    }

    public ParentList setEtag(String str) {
        this.etag = str;
        return this;
    }

    public List<ParentReference> getItems() {
        return this.items;
    }

    public ParentList setItems(List<ParentReference> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public ParentList setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getSelfLink() {
        return this.selfLink;
    }

    public ParentList setSelfLink(String str) {
        this.selfLink = str;
        return this;
    }

    public ParentList set(String str, Object obj) {
        return (ParentList) super.set(str, obj);
    }

    public ParentList clone() {
        return (ParentList) super.clone();
    }
}
