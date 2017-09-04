package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

public final class Change extends GenericJson {
    @Key
    private Boolean deleted;
    @Key
    private File file;
    @Key
    private String fileId;
    @Key
    @JsonString
    private Long id;
    @Key
    private String kind;
    @Key
    private DateTime modificationDate;
    @Key
    private String selfLink;

    public Boolean getDeleted() {
        return this.deleted;
    }

    public Change setDeleted(Boolean bool) {
        this.deleted = bool;
        return this;
    }

    public File getFile() {
        return this.file;
    }

    public Change setFile(File file) {
        this.file = file;
        return this;
    }

    public String getFileId() {
        return this.fileId;
    }

    public Change setFileId(String str) {
        this.fileId = str;
        return this;
    }

    public Long getId() {
        return this.id;
    }

    public Change setId(Long l) {
        this.id = l;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public Change setKind(String str) {
        this.kind = str;
        return this;
    }

    public DateTime getModificationDate() {
        return this.modificationDate;
    }

    public Change setModificationDate(DateTime dateTime) {
        this.modificationDate = dateTime;
        return this;
    }

    public String getSelfLink() {
        return this.selfLink;
    }

    public Change setSelfLink(String str) {
        this.selfLink = str;
        return this;
    }

    public Change set(String str, Object obj) {
        return (Change) super.set(str, obj);
    }

    public Change clone() {
        return (Change) super.clone();
    }
}
