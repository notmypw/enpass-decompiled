package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import java.util.Map;

public final class Revision extends GenericJson {
    @Key
    private String downloadUrl;
    @Key
    private String etag;
    @Key
    private Map<String, String> exportLinks;
    @Key
    @JsonString
    private Long fileSize;
    @Key
    private String id;
    @Key
    private String kind;
    @Key
    private User lastModifyingUser;
    @Key
    private String lastModifyingUserName;
    @Key
    private String md5Checksum;
    @Key
    private String mimeType;
    @Key
    private DateTime modifiedDate;
    @Key
    private String originalFilename;
    @Key
    private Boolean pinned;
    @Key
    private Boolean publishAuto;
    @Key
    private Boolean published;
    @Key
    private String publishedLink;
    @Key
    private Boolean publishedOutsideDomain;
    @Key
    private String selfLink;

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public Revision setDownloadUrl(String str) {
        this.downloadUrl = str;
        return this;
    }

    public String getEtag() {
        return this.etag;
    }

    public Revision setEtag(String str) {
        this.etag = str;
        return this;
    }

    public Map<String, String> getExportLinks() {
        return this.exportLinks;
    }

    public Revision setExportLinks(Map<String, String> map) {
        this.exportLinks = map;
        return this;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public Revision setFileSize(Long l) {
        this.fileSize = l;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public Revision setId(String str) {
        this.id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public Revision setKind(String str) {
        this.kind = str;
        return this;
    }

    public User getLastModifyingUser() {
        return this.lastModifyingUser;
    }

    public Revision setLastModifyingUser(User user) {
        this.lastModifyingUser = user;
        return this;
    }

    public String getLastModifyingUserName() {
        return this.lastModifyingUserName;
    }

    public Revision setLastModifyingUserName(String str) {
        this.lastModifyingUserName = str;
        return this;
    }

    public String getMd5Checksum() {
        return this.md5Checksum;
    }

    public Revision setMd5Checksum(String str) {
        this.md5Checksum = str;
        return this;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public Revision setMimeType(String str) {
        this.mimeType = str;
        return this;
    }

    public DateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public Revision setModifiedDate(DateTime dateTime) {
        this.modifiedDate = dateTime;
        return this;
    }

    public String getOriginalFilename() {
        return this.originalFilename;
    }

    public Revision setOriginalFilename(String str) {
        this.originalFilename = str;
        return this;
    }

    public Boolean getPinned() {
        return this.pinned;
    }

    public Revision setPinned(Boolean bool) {
        this.pinned = bool;
        return this;
    }

    public Boolean getPublishAuto() {
        return this.publishAuto;
    }

    public Revision setPublishAuto(Boolean bool) {
        this.publishAuto = bool;
        return this;
    }

    public Boolean getPublished() {
        return this.published;
    }

    public Revision setPublished(Boolean bool) {
        this.published = bool;
        return this;
    }

    public String getPublishedLink() {
        return this.publishedLink;
    }

    public Revision setPublishedLink(String str) {
        this.publishedLink = str;
        return this;
    }

    public Boolean getPublishedOutsideDomain() {
        return this.publishedOutsideDomain;
    }

    public Revision setPublishedOutsideDomain(Boolean bool) {
        this.publishedOutsideDomain = bool;
        return this;
    }

    public String getSelfLink() {
        return this.selfLink;
    }

    public Revision setSelfLink(String str) {
        this.selfLink = str;
        return this;
    }

    public Revision set(String str, Object obj) {
        return (Revision) super.set(str, obj);
    }

    public Revision clone() {
        return (Revision) super.clone();
    }
}
