package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class About extends GenericJson {
    @Key
    private List<AdditionalRoleInfo> additionalRoleInfo;
    @Key
    private String domainSharingPolicy;
    @Key
    private String etag;
    @Key
    private List<ExportFormats> exportFormats;
    @Key
    private List<Features> features;
    @Key
    private List<String> folderColorPalette;
    @Key
    private List<ImportFormats> importFormats;
    @Key
    private Boolean isCurrentAppInstalled;
    @Key
    private String kind;
    @Key
    private String languageCode;
    @Key
    @JsonString
    private Long largestChangeId;
    @Key
    private List<MaxUploadSizes> maxUploadSizes;
    @Key
    private String name;
    @Key
    private String permissionId;
    @Key
    private List<QuotaBytesByService> quotaBytesByService;
    @Key
    @JsonString
    private Long quotaBytesTotal;
    @Key
    @JsonString
    private Long quotaBytesUsed;
    @Key
    @JsonString
    private Long quotaBytesUsedAggregate;
    @Key
    @JsonString
    private Long quotaBytesUsedInTrash;
    @Key
    private String quotaType;
    @Key
    @JsonString
    private Long remainingChangeIds;
    @Key
    private String rootFolderId;
    @Key
    private String selfLink;
    @Key
    private User user;

    public static final class AdditionalRoleInfo extends GenericJson {
        @Key
        private List<RoleSets> roleSets;
        @Key
        private String type;

        public static final class RoleSets extends GenericJson {
            @Key
            private List<String> additionalRoles;
            @Key
            private String primaryRole;

            public List<String> getAdditionalRoles() {
                return this.additionalRoles;
            }

            public RoleSets setAdditionalRoles(List<String> list) {
                this.additionalRoles = list;
                return this;
            }

            public String getPrimaryRole() {
                return this.primaryRole;
            }

            public RoleSets setPrimaryRole(String str) {
                this.primaryRole = str;
                return this;
            }

            public RoleSets set(String str, Object obj) {
                return (RoleSets) super.set(str, obj);
            }

            public RoleSets clone() {
                return (RoleSets) super.clone();
            }
        }

        static {
            Data.nullOf(RoleSets.class);
        }

        public List<RoleSets> getRoleSets() {
            return this.roleSets;
        }

        public AdditionalRoleInfo setRoleSets(List<RoleSets> list) {
            this.roleSets = list;
            return this;
        }

        public String getType() {
            return this.type;
        }

        public AdditionalRoleInfo setType(String str) {
            this.type = str;
            return this;
        }

        public AdditionalRoleInfo set(String str, Object obj) {
            return (AdditionalRoleInfo) super.set(str, obj);
        }

        public AdditionalRoleInfo clone() {
            return (AdditionalRoleInfo) super.clone();
        }
    }

    public static final class ExportFormats extends GenericJson {
        @Key
        private String source;
        @Key
        private List<String> targets;

        public String getSource() {
            return this.source;
        }

        public ExportFormats setSource(String str) {
            this.source = str;
            return this;
        }

        public List<String> getTargets() {
            return this.targets;
        }

        public ExportFormats setTargets(List<String> list) {
            this.targets = list;
            return this;
        }

        public ExportFormats set(String str, Object obj) {
            return (ExportFormats) super.set(str, obj);
        }

        public ExportFormats clone() {
            return (ExportFormats) super.clone();
        }
    }

    public static final class Features extends GenericJson {
        @Key
        private String featureName;
        @Key
        private Double featureRate;

        public String getFeatureName() {
            return this.featureName;
        }

        public Features setFeatureName(String str) {
            this.featureName = str;
            return this;
        }

        public Double getFeatureRate() {
            return this.featureRate;
        }

        public Features setFeatureRate(Double d) {
            this.featureRate = d;
            return this;
        }

        public Features set(String str, Object obj) {
            return (Features) super.set(str, obj);
        }

        public Features clone() {
            return (Features) super.clone();
        }
    }

    public static final class ImportFormats extends GenericJson {
        @Key
        private String source;
        @Key
        private List<String> targets;

        public String getSource() {
            return this.source;
        }

        public ImportFormats setSource(String str) {
            this.source = str;
            return this;
        }

        public List<String> getTargets() {
            return this.targets;
        }

        public ImportFormats setTargets(List<String> list) {
            this.targets = list;
            return this;
        }

        public ImportFormats set(String str, Object obj) {
            return (ImportFormats) super.set(str, obj);
        }

        public ImportFormats clone() {
            return (ImportFormats) super.clone();
        }
    }

    public static final class MaxUploadSizes extends GenericJson {
        @Key
        @JsonString
        private Long size;
        @Key
        private String type;

        public Long getSize() {
            return this.size;
        }

        public MaxUploadSizes setSize(Long l) {
            this.size = l;
            return this;
        }

        public String getType() {
            return this.type;
        }

        public MaxUploadSizes setType(String str) {
            this.type = str;
            return this;
        }

        public MaxUploadSizes set(String str, Object obj) {
            return (MaxUploadSizes) super.set(str, obj);
        }

        public MaxUploadSizes clone() {
            return (MaxUploadSizes) super.clone();
        }
    }

    public static final class QuotaBytesByService extends GenericJson {
        @Key
        @JsonString
        private Long bytesUsed;
        @Key
        private String serviceName;

        public Long getBytesUsed() {
            return this.bytesUsed;
        }

        public QuotaBytesByService setBytesUsed(Long l) {
            this.bytesUsed = l;
            return this;
        }

        public String getServiceName() {
            return this.serviceName;
        }

        public QuotaBytesByService setServiceName(String str) {
            this.serviceName = str;
            return this;
        }

        public QuotaBytesByService set(String str, Object obj) {
            return (QuotaBytesByService) super.set(str, obj);
        }

        public QuotaBytesByService clone() {
            return (QuotaBytesByService) super.clone();
        }
    }

    static {
        Data.nullOf(AdditionalRoleInfo.class);
        Data.nullOf(ExportFormats.class);
        Data.nullOf(Features.class);
        Data.nullOf(ImportFormats.class);
        Data.nullOf(MaxUploadSizes.class);
        Data.nullOf(QuotaBytesByService.class);
    }

    public List<AdditionalRoleInfo> getAdditionalRoleInfo() {
        return this.additionalRoleInfo;
    }

    public About setAdditionalRoleInfo(List<AdditionalRoleInfo> list) {
        this.additionalRoleInfo = list;
        return this;
    }

    public String getDomainSharingPolicy() {
        return this.domainSharingPolicy;
    }

    public About setDomainSharingPolicy(String str) {
        this.domainSharingPolicy = str;
        return this;
    }

    public String getEtag() {
        return this.etag;
    }

    public About setEtag(String str) {
        this.etag = str;
        return this;
    }

    public List<ExportFormats> getExportFormats() {
        return this.exportFormats;
    }

    public About setExportFormats(List<ExportFormats> list) {
        this.exportFormats = list;
        return this;
    }

    public List<Features> getFeatures() {
        return this.features;
    }

    public About setFeatures(List<Features> list) {
        this.features = list;
        return this;
    }

    public List<String> getFolderColorPalette() {
        return this.folderColorPalette;
    }

    public About setFolderColorPalette(List<String> list) {
        this.folderColorPalette = list;
        return this;
    }

    public List<ImportFormats> getImportFormats() {
        return this.importFormats;
    }

    public About setImportFormats(List<ImportFormats> list) {
        this.importFormats = list;
        return this;
    }

    public Boolean getIsCurrentAppInstalled() {
        return this.isCurrentAppInstalled;
    }

    public About setIsCurrentAppInstalled(Boolean bool) {
        this.isCurrentAppInstalled = bool;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public About setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getLanguageCode() {
        return this.languageCode;
    }

    public About setLanguageCode(String str) {
        this.languageCode = str;
        return this;
    }

    public Long getLargestChangeId() {
        return this.largestChangeId;
    }

    public About setLargestChangeId(Long l) {
        this.largestChangeId = l;
        return this;
    }

    public List<MaxUploadSizes> getMaxUploadSizes() {
        return this.maxUploadSizes;
    }

    public About setMaxUploadSizes(List<MaxUploadSizes> list) {
        this.maxUploadSizes = list;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public About setName(String str) {
        this.name = str;
        return this;
    }

    public String getPermissionId() {
        return this.permissionId;
    }

    public About setPermissionId(String str) {
        this.permissionId = str;
        return this;
    }

    public List<QuotaBytesByService> getQuotaBytesByService() {
        return this.quotaBytesByService;
    }

    public About setQuotaBytesByService(List<QuotaBytesByService> list) {
        this.quotaBytesByService = list;
        return this;
    }

    public Long getQuotaBytesTotal() {
        return this.quotaBytesTotal;
    }

    public About setQuotaBytesTotal(Long l) {
        this.quotaBytesTotal = l;
        return this;
    }

    public Long getQuotaBytesUsed() {
        return this.quotaBytesUsed;
    }

    public About setQuotaBytesUsed(Long l) {
        this.quotaBytesUsed = l;
        return this;
    }

    public Long getQuotaBytesUsedAggregate() {
        return this.quotaBytesUsedAggregate;
    }

    public About setQuotaBytesUsedAggregate(Long l) {
        this.quotaBytesUsedAggregate = l;
        return this;
    }

    public Long getQuotaBytesUsedInTrash() {
        return this.quotaBytesUsedInTrash;
    }

    public About setQuotaBytesUsedInTrash(Long l) {
        this.quotaBytesUsedInTrash = l;
        return this;
    }

    public String getQuotaType() {
        return this.quotaType;
    }

    public About setQuotaType(String str) {
        this.quotaType = str;
        return this;
    }

    public Long getRemainingChangeIds() {
        return this.remainingChangeIds;
    }

    public About setRemainingChangeIds(Long l) {
        this.remainingChangeIds = l;
        return this;
    }

    public String getRootFolderId() {
        return this.rootFolderId;
    }

    public About setRootFolderId(String str) {
        this.rootFolderId = str;
        return this;
    }

    public String getSelfLink() {
        return this.selfLink;
    }

    public About setSelfLink(String str) {
        this.selfLink = str;
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public About setUser(User user) {
        this.user = user;
        return this;
    }

    public About set(String str, Object obj) {
        return (About) super.set(str, obj);
    }

    public About clone() {
        return (About) super.clone();
    }
}
