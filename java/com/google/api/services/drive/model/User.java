package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class User extends GenericJson {
    @Key
    private String displayName;
    @Key
    private String emailAddress;
    @Key
    private Boolean isAuthenticatedUser;
    @Key
    private String kind;
    @Key
    private String permissionId;
    @Key
    private Picture picture;

    public static final class Picture extends GenericJson {
        @Key
        private String url;

        public String getUrl() {
            return this.url;
        }

        public Picture setUrl(String str) {
            this.url = str;
            return this;
        }

        public Picture set(String str, Object obj) {
            return (Picture) super.set(str, obj);
        }

        public Picture clone() {
            return (Picture) super.clone();
        }
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public User setDisplayName(String str) {
        this.displayName = str;
        return this;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public User setEmailAddress(String str) {
        this.emailAddress = str;
        return this;
    }

    public Boolean getIsAuthenticatedUser() {
        return this.isAuthenticatedUser;
    }

    public User setIsAuthenticatedUser(Boolean bool) {
        this.isAuthenticatedUser = bool;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public User setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getPermissionId() {
        return this.permissionId;
    }

    public User setPermissionId(String str) {
        this.permissionId = str;
        return this;
    }

    public Picture getPicture() {
        return this.picture;
    }

    public User setPicture(Picture picture) {
        this.picture = picture;
        return this;
    }

    public User set(String str, Object obj) {
        return (User) super.set(str, obj);
    }

    public User clone() {
        return (User) super.clone();
    }
}
