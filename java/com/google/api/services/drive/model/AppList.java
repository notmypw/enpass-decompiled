package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public final class AppList extends GenericJson {
    @Key
    private List<String> defaultAppIds;
    @Key
    private String etag;
    @Key
    private List<App> items;
    @Key
    private String kind;
    @Key
    private String selfLink;

    public List<String> getDefaultAppIds() {
        return this.defaultAppIds;
    }

    public AppList setDefaultAppIds(List<String> list) {
        this.defaultAppIds = list;
        return this;
    }

    public String getEtag() {
        return this.etag;
    }

    public AppList setEtag(String str) {
        this.etag = str;
        return this;
    }

    public List<App> getItems() {
        return this.items;
    }

    public AppList setItems(List<App> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public AppList setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getSelfLink() {
        return this.selfLink;
    }

    public AppList setSelfLink(String str) {
        this.selfLink = str;
        return this;
    }

    public AppList set(String str, Object obj) {
        return (AppList) super.set(str, obj);
    }

    public AppList clone() {
        return (AppList) super.clone();
    }
}
