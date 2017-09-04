package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class PermissionId extends GenericJson {
    @Key
    private String id;
    @Key
    private String kind;

    public String getId() {
        return this.id;
    }

    public PermissionId setId(String str) {
        this.id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public PermissionId setKind(String str) {
        this.kind = str;
        return this;
    }

    public PermissionId set(String str, Object obj) {
        return (PermissionId) super.set(str, obj);
    }

    public PermissionId clone() {
        return (PermissionId) super.clone();
    }
}
