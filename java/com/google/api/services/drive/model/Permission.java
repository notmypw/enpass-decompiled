package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public final class Permission extends GenericJson {
    @Key
    private List<String> additionalRoles;
    @Key
    private String authKey;
    @Key
    private String domain;
    @Key
    private String emailAddress;
    @Key
    private String etag;
    @Key
    private String id;
    @Key
    private String kind;
    @Key
    private String name;
    @Key
    private String photoLink;
    @Key
    private String role;
    @Key
    private String selfLink;
    @Key
    private String type;
    @Key
    private String value;
    @Key
    private Boolean withLink;

    public List<String> getAdditionalRoles() {
        return this.additionalRoles;
    }

    public Permission setAdditionalRoles(List<String> list) {
        this.additionalRoles = list;
        return this;
    }

    public String getAuthKey() {
        return this.authKey;
    }

    public Permission setAuthKey(String str) {
        this.authKey = str;
        return this;
    }

    public String getDomain() {
        return this.domain;
    }

    public Permission setDomain(String str) {
        this.domain = str;
        return this;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public Permission setEmailAddress(String str) {
        this.emailAddress = str;
        return this;
    }

    public String getEtag() {
        return this.etag;
    }

    public Permission setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public Permission setId(String str) {
        this.id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public Permission setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Permission setName(String str) {
        this.name = str;
        return this;
    }

    public String getPhotoLink() {
        return this.photoLink;
    }

    public Permission setPhotoLink(String str) {
        this.photoLink = str;
        return this;
    }

    public String getRole() {
        return this.role;
    }

    public Permission setRole(String str) {
        this.role = str;
        return this;
    }

    public String getSelfLink() {
        return this.selfLink;
    }

    public Permission setSelfLink(String str) {
        this.selfLink = str;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public Permission setType(String str) {
        this.type = str;
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public Permission setValue(String str) {
        this.value = str;
        return this;
    }

    public Boolean getWithLink() {
        return this.withLink;
    }

    public Permission setWithLink(Boolean bool) {
        this.withLink = bool;
        return this;
    }

    public Permission set(String str, Object obj) {
        return (Permission) super.set(str, obj);
    }

    public Permission clone() {
        return (Permission) super.clone();
    }
}
