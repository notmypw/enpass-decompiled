package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class App extends GenericJson {
    @Key
    private Boolean authorized;
    @Key
    private String createInFolderTemplate;
    @Key
    private String createUrl;
    @Key
    private Boolean hasDriveWideScope;
    @Key
    private List<Icons> icons;
    @Key
    private String id;
    @Key
    private Boolean installed;
    @Key
    private String kind;
    @Key
    private String longDescription;
    @Key
    private String name;
    @Key
    private String objectType;
    @Key
    private String openUrlTemplate;
    @Key
    private List<String> primaryFileExtensions;
    @Key
    private List<String> primaryMimeTypes;
    @Key
    private String productId;
    @Key
    private String productUrl;
    @Key
    private List<String> secondaryFileExtensions;
    @Key
    private List<String> secondaryMimeTypes;
    @Key
    private String shortDescription;
    @Key
    private Boolean supportsCreate;
    @Key
    private Boolean supportsImport;
    @Key
    private Boolean supportsMultiOpen;
    @Key
    private Boolean supportsOfflineCreate;
    @Key
    private Boolean useByDefault;

    public static final class Icons extends GenericJson {
        @Key
        private String category;
        @Key
        private String iconUrl;
        @Key
        private Integer size;

        public String getCategory() {
            return this.category;
        }

        public Icons setCategory(String str) {
            this.category = str;
            return this;
        }

        public String getIconUrl() {
            return this.iconUrl;
        }

        public Icons setIconUrl(String str) {
            this.iconUrl = str;
            return this;
        }

        public Integer getSize() {
            return this.size;
        }

        public Icons setSize(Integer num) {
            this.size = num;
            return this;
        }

        public Icons set(String str, Object obj) {
            return (Icons) super.set(str, obj);
        }

        public Icons clone() {
            return (Icons) super.clone();
        }
    }

    static {
        Data.nullOf(Icons.class);
    }

    public Boolean getAuthorized() {
        return this.authorized;
    }

    public App setAuthorized(Boolean bool) {
        this.authorized = bool;
        return this;
    }

    public String getCreateInFolderTemplate() {
        return this.createInFolderTemplate;
    }

    public App setCreateInFolderTemplate(String str) {
        this.createInFolderTemplate = str;
        return this;
    }

    public String getCreateUrl() {
        return this.createUrl;
    }

    public App setCreateUrl(String str) {
        this.createUrl = str;
        return this;
    }

    public Boolean getHasDriveWideScope() {
        return this.hasDriveWideScope;
    }

    public App setHasDriveWideScope(Boolean bool) {
        this.hasDriveWideScope = bool;
        return this;
    }

    public List<Icons> getIcons() {
        return this.icons;
    }

    public App setIcons(List<Icons> list) {
        this.icons = list;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public App setId(String str) {
        this.id = str;
        return this;
    }

    public Boolean getInstalled() {
        return this.installed;
    }

    public App setInstalled(Boolean bool) {
        this.installed = bool;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public App setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getLongDescription() {
        return this.longDescription;
    }

    public App setLongDescription(String str) {
        this.longDescription = str;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public App setName(String str) {
        this.name = str;
        return this;
    }

    public String getObjectType() {
        return this.objectType;
    }

    public App setObjectType(String str) {
        this.objectType = str;
        return this;
    }

    public String getOpenUrlTemplate() {
        return this.openUrlTemplate;
    }

    public App setOpenUrlTemplate(String str) {
        this.openUrlTemplate = str;
        return this;
    }

    public List<String> getPrimaryFileExtensions() {
        return this.primaryFileExtensions;
    }

    public App setPrimaryFileExtensions(List<String> list) {
        this.primaryFileExtensions = list;
        return this;
    }

    public List<String> getPrimaryMimeTypes() {
        return this.primaryMimeTypes;
    }

    public App setPrimaryMimeTypes(List<String> list) {
        this.primaryMimeTypes = list;
        return this;
    }

    public String getProductId() {
        return this.productId;
    }

    public App setProductId(String str) {
        this.productId = str;
        return this;
    }

    public String getProductUrl() {
        return this.productUrl;
    }

    public App setProductUrl(String str) {
        this.productUrl = str;
        return this;
    }

    public List<String> getSecondaryFileExtensions() {
        return this.secondaryFileExtensions;
    }

    public App setSecondaryFileExtensions(List<String> list) {
        this.secondaryFileExtensions = list;
        return this;
    }

    public List<String> getSecondaryMimeTypes() {
        return this.secondaryMimeTypes;
    }

    public App setSecondaryMimeTypes(List<String> list) {
        this.secondaryMimeTypes = list;
        return this;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public App setShortDescription(String str) {
        this.shortDescription = str;
        return this;
    }

    public Boolean getSupportsCreate() {
        return this.supportsCreate;
    }

    public App setSupportsCreate(Boolean bool) {
        this.supportsCreate = bool;
        return this;
    }

    public Boolean getSupportsImport() {
        return this.supportsImport;
    }

    public App setSupportsImport(Boolean bool) {
        this.supportsImport = bool;
        return this;
    }

    public Boolean getSupportsMultiOpen() {
        return this.supportsMultiOpen;
    }

    public App setSupportsMultiOpen(Boolean bool) {
        this.supportsMultiOpen = bool;
        return this;
    }

    public Boolean getSupportsOfflineCreate() {
        return this.supportsOfflineCreate;
    }

    public App setSupportsOfflineCreate(Boolean bool) {
        this.supportsOfflineCreate = bool;
        return this;
    }

    public Boolean getUseByDefault() {
        return this.useByDefault;
    }

    public App setUseByDefault(Boolean bool) {
        this.useByDefault = bool;
        return this;
    }

    public App set(String str, Object obj) {
        return (App) super.set(str, obj);
    }

    public App clone() {
        return (App) super.clone();
    }
}
