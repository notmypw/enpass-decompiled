package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class ParentReference extends GenericJson {
    @Key
    private String id;
    @Key
    private Boolean isRoot;
    @Key
    private String kind;
    @Key
    private String parentLink;
    @Key
    private String selfLink;

    public String getId() {
        return this.id;
    }

    public ParentReference setId(String str) {
        this.id = str;
        return this;
    }

    public Boolean getIsRoot() {
        return this.isRoot;
    }

    public ParentReference setIsRoot(Boolean bool) {
        this.isRoot = bool;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public ParentReference setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getParentLink() {
        return this.parentLink;
    }

    public ParentReference setParentLink(String str) {
        this.parentLink = str;
        return this;
    }

    public String getSelfLink() {
        return this.selfLink;
    }

    public ParentReference setSelfLink(String str) {
        this.selfLink = str;
        return this;
    }

    public ParentReference set(String str, Object obj) {
        return (ParentReference) super.set(str, obj);
    }

    public ParentReference clone() {
        return (ParentReference) super.clone();
    }
}
