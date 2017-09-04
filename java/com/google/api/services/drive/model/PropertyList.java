package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public final class PropertyList extends GenericJson {
    @Key
    private String etag;
    @Key
    private List<Property> items;
    @Key
    private String kind;
    @Key
    private String selfLink;

    public String getEtag() {
        return this.etag;
    }

    public PropertyList setEtag(String str) {
        this.etag = str;
        return this;
    }

    public List<Property> getItems() {
        return this.items;
    }

    public PropertyList setItems(List<Property> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public PropertyList setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getSelfLink() {
        return this.selfLink;
    }

    public PropertyList setSelfLink(String str) {
        this.selfLink = str;
        return this;
    }

    public PropertyList set(String str, Object obj) {
        return (PropertyList) super.set(str, obj);
    }

    public PropertyList clone() {
        return (PropertyList) super.clone();
    }
}
