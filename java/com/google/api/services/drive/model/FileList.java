package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public final class FileList extends GenericJson {
    @Key
    private String etag;
    @Key
    private List<File> items;
    @Key
    private String kind;
    @Key
    private String nextLink;
    @Key
    private String nextPageToken;
    @Key
    private String selfLink;

    public String getEtag() {
        return this.etag;
    }

    public FileList setEtag(String str) {
        this.etag = str;
        return this;
    }

    public List<File> getItems() {
        return this.items;
    }

    public FileList setItems(List<File> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public FileList setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getNextLink() {
        return this.nextLink;
    }

    public FileList setNextLink(String str) {
        this.nextLink = str;
        return this;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public FileList setNextPageToken(String str) {
        this.nextPageToken = str;
        return this;
    }

    public String getSelfLink() {
        return this.selfLink;
    }

    public FileList setSelfLink(String str) {
        this.selfLink = str;
        return this;
    }

    public FileList set(String str, Object obj) {
        return (FileList) super.set(str, obj);
    }

    public FileList clone() {
        return (FileList) super.clone();
    }
}
