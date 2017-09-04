package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class Property extends GenericJson {
    @Key
    private String etag;
    @Key
    private String key;
    @Key
    private String kind;
    @Key
    private String selfLink;
    @Key
    private String value;
    @Key
    private String visibility;

    public String getEtag() {
        return this.etag;
    }

    public Property setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public Property setKey(String str) {
        this.key = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public Property setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getSelfLink() {
        return this.selfLink;
    }

    public Property setSelfLink(String str) {
        this.selfLink = str;
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public Property setValue(String str) {
        this.value = str;
        return this;
    }

    public String getVisibility() {
        return this.visibility;
    }

    public Property setVisibility(String str) {
        this.visibility = str;
        return this;
    }

    public Property set(String str, Object obj) {
        return (Property) super.set(str, obj);
    }

    public Property clone() {
        return (Property) super.clone();
    }
}
