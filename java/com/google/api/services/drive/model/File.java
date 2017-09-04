package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.Base64;
import com.google.api.client.util.Data;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import java.util.List;
import java.util.Map;

public final class File extends GenericJson {
    @Key
    private String alternateLink;
    @Key
    private Boolean appDataContents;
    @Key
    private Boolean copyable;
    @Key
    private DateTime createdDate;
    @Key
    private String defaultOpenWithLink;
    @Key
    private String description;
    @Key
    private String downloadUrl;
    @Key
    private Boolean editable;
    @Key
    private String embedLink;
    @Key
    private String etag;
    @Key
    private Boolean explicitlyTrashed;
    @Key
    private Map<String, String> exportLinks;
    @Key
    private String fileExtension;
    @Key
    @JsonString
    private Long fileSize;
    @Key
    private String folderColorRgb;
    @Key
    private String headRevisionId;
    @Key
    private String iconLink;
    @Key
    private String id;
    @Key
    private ImageMediaMetadata imageMediaMetadata;
    @Key
    private IndexableText indexableText;
    @Key
    private String kind;
    @Key
    private Labels labels;
    @Key
    private User lastModifyingUser;
    @Key
    private String lastModifyingUserName;
    @Key
    private DateTime lastViewedByMeDate;
    @Key
    private DateTime markedViewedByMeDate;
    @Key
    private String md5Checksum;
    @Key
    private String mimeType;
    @Key
    private DateTime modifiedByMeDate;
    @Key
    private DateTime modifiedDate;
    @Key
    private Map<String, String> openWithLinks;
    @Key
    private String originalFilename;
    @Key
    private List<String> ownerNames;
    @Key
    private List<User> owners;
    @Key
    private List<ParentReference> parents;
    @Key
    private List<Permission> permissions;
    @Key
    private List<Property> properties;
    @Key
    @JsonString
    private Long quotaBytesUsed;
    @Key
    private String selfLink;
    @Key
    private Boolean shared;
    @Key
    private DateTime sharedWithMeDate;
    @Key
    private User sharingUser;
    @Key
    private Thumbnail thumbnail;
    @Key
    private String thumbnailLink;
    @Key
    private String title;
    @Key
    private Permission userPermission;
    @Key
    @JsonString
    private Long version;
    @Key
    private VideoMediaMetadata videoMediaMetadata;
    @Key
    private String webContentLink;
    @Key
    private String webViewLink;
    @Key
    private Boolean writersCanShare;

    public static final class ImageMediaMetadata extends GenericJson {
        @Key
        private Float aperture;
        @Key
        private String cameraMake;
        @Key
        private String cameraModel;
        @Key
        private String colorSpace;
        @Key
        private String date;
        @Key
        private Float exposureBias;
        @Key
        private String exposureMode;
        @Key
        private Float exposureTime;
        @Key
        private Boolean flashUsed;
        @Key
        private Float focalLength;
        @Key
        private Integer height;
        @Key
        private Integer isoSpeed;
        @Key
        private String lens;
        @Key
        private Location location;
        @Key
        private Float maxApertureValue;
        @Key
        private String meteringMode;
        @Key
        private Integer rotation;
        @Key
        private String sensor;
        @Key
        private Integer subjectDistance;
        @Key
        private String whiteBalance;
        @Key
        private Integer width;

        public static final class Location extends GenericJson {
            @Key
            private Double altitude;
            @Key
            private Double latitude;
            @Key
            private Double longitude;

            public Double getAltitude() {
                return this.altitude;
            }

            public Location setAltitude(Double d) {
                this.altitude = d;
                return this;
            }

            public Double getLatitude() {
                return this.latitude;
            }

            public Location setLatitude(Double d) {
                this.latitude = d;
                return this;
            }

            public Double getLongitude() {
                return this.longitude;
            }

            public Location setLongitude(Double d) {
                this.longitude = d;
                return this;
            }

            public Location set(String str, Object obj) {
                return (Location) super.set(str, obj);
            }

            public Location clone() {
                return (Location) super.clone();
            }
        }

        public Float getAperture() {
            return this.aperture;
        }

        public ImageMediaMetadata setAperture(Float f) {
            this.aperture = f;
            return this;
        }

        public String getCameraMake() {
            return this.cameraMake;
        }

        public ImageMediaMetadata setCameraMake(String str) {
            this.cameraMake = str;
            return this;
        }

        public String getCameraModel() {
            return this.cameraModel;
        }

        public ImageMediaMetadata setCameraModel(String str) {
            this.cameraModel = str;
            return this;
        }

        public String getColorSpace() {
            return this.colorSpace;
        }

        public ImageMediaMetadata setColorSpace(String str) {
            this.colorSpace = str;
            return this;
        }

        public String getDate() {
            return this.date;
        }

        public ImageMediaMetadata setDate(String str) {
            this.date = str;
            return this;
        }

        public Float getExposureBias() {
            return this.exposureBias;
        }

        public ImageMediaMetadata setExposureBias(Float f) {
            this.exposureBias = f;
            return this;
        }

        public String getExposureMode() {
            return this.exposureMode;
        }

        public ImageMediaMetadata setExposureMode(String str) {
            this.exposureMode = str;
            return this;
        }

        public Float getExposureTime() {
            return this.exposureTime;
        }

        public ImageMediaMetadata setExposureTime(Float f) {
            this.exposureTime = f;
            return this;
        }

        public Boolean getFlashUsed() {
            return this.flashUsed;
        }

        public ImageMediaMetadata setFlashUsed(Boolean bool) {
            this.flashUsed = bool;
            return this;
        }

        public Float getFocalLength() {
            return this.focalLength;
        }

        public ImageMediaMetadata setFocalLength(Float f) {
            this.focalLength = f;
            return this;
        }

        public Integer getHeight() {
            return this.height;
        }

        public ImageMediaMetadata setHeight(Integer num) {
            this.height = num;
            return this;
        }

        public Integer getIsoSpeed() {
            return this.isoSpeed;
        }

        public ImageMediaMetadata setIsoSpeed(Integer num) {
            this.isoSpeed = num;
            return this;
        }

        public String getLens() {
            return this.lens;
        }

        public ImageMediaMetadata setLens(String str) {
            this.lens = str;
            return this;
        }

        public Location getLocation() {
            return this.location;
        }

        public ImageMediaMetadata setLocation(Location location) {
            this.location = location;
            return this;
        }

        public Float getMaxApertureValue() {
            return this.maxApertureValue;
        }

        public ImageMediaMetadata setMaxApertureValue(Float f) {
            this.maxApertureValue = f;
            return this;
        }

        public String getMeteringMode() {
            return this.meteringMode;
        }

        public ImageMediaMetadata setMeteringMode(String str) {
            this.meteringMode = str;
            return this;
        }

        public Integer getRotation() {
            return this.rotation;
        }

        public ImageMediaMetadata setRotation(Integer num) {
            this.rotation = num;
            return this;
        }

        public String getSensor() {
            return this.sensor;
        }

        public ImageMediaMetadata setSensor(String str) {
            this.sensor = str;
            return this;
        }

        public Integer getSubjectDistance() {
            return this.subjectDistance;
        }

        public ImageMediaMetadata setSubjectDistance(Integer num) {
            this.subjectDistance = num;
            return this;
        }

        public String getWhiteBalance() {
            return this.whiteBalance;
        }

        public ImageMediaMetadata setWhiteBalance(String str) {
            this.whiteBalance = str;
            return this;
        }

        public Integer getWidth() {
            return this.width;
        }

        public ImageMediaMetadata setWidth(Integer num) {
            this.width = num;
            return this;
        }

        public ImageMediaMetadata set(String str, Object obj) {
            return (ImageMediaMetadata) super.set(str, obj);
        }

        public ImageMediaMetadata clone() {
            return (ImageMediaMetadata) super.clone();
        }
    }

    public static final class IndexableText extends GenericJson {
        @Key
        private String text;

        public String getText() {
            return this.text;
        }

        public IndexableText setText(String str) {
            this.text = str;
            return this;
        }

        public IndexableText set(String str, Object obj) {
            return (IndexableText) super.set(str, obj);
        }

        public IndexableText clone() {
            return (IndexableText) super.clone();
        }
    }

    public static final class Labels extends GenericJson {
        @Key
        private Boolean hidden;
        @Key
        private Boolean restricted;
        @Key
        private Boolean starred;
        @Key
        private Boolean trashed;
        @Key
        private Boolean viewed;

        public Boolean getHidden() {
            return this.hidden;
        }

        public Labels setHidden(Boolean bool) {
            this.hidden = bool;
            return this;
        }

        public Boolean getRestricted() {
            return this.restricted;
        }

        public Labels setRestricted(Boolean bool) {
            this.restricted = bool;
            return this;
        }

        public Boolean getStarred() {
            return this.starred;
        }

        public Labels setStarred(Boolean bool) {
            this.starred = bool;
            return this;
        }

        public Boolean getTrashed() {
            return this.trashed;
        }

        public Labels setTrashed(Boolean bool) {
            this.trashed = bool;
            return this;
        }

        public Boolean getViewed() {
            return this.viewed;
        }

        public Labels setViewed(Boolean bool) {
            this.viewed = bool;
            return this;
        }

        public Labels set(String str, Object obj) {
            return (Labels) super.set(str, obj);
        }

        public Labels clone() {
            return (Labels) super.clone();
        }
    }

    public static final class Thumbnail extends GenericJson {
        @Key
        private String image;
        @Key
        private String mimeType;

        public String getImage() {
            return this.image;
        }

        public byte[] decodeImage() {
            return Base64.decodeBase64(this.image);
        }

        public Thumbnail setImage(String str) {
            this.image = str;
            return this;
        }

        public Thumbnail encodeImage(byte[] bArr) {
            this.image = Base64.encodeBase64URLSafeString(bArr);
            return this;
        }

        public String getMimeType() {
            return this.mimeType;
        }

        public Thumbnail setMimeType(String str) {
            this.mimeType = str;
            return this;
        }

        public Thumbnail set(String str, Object obj) {
            return (Thumbnail) super.set(str, obj);
        }

        public Thumbnail clone() {
            return (Thumbnail) super.clone();
        }
    }

    public static final class VideoMediaMetadata extends GenericJson {
        @Key
        @JsonString
        private Long durationMillis;
        @Key
        private Integer height;
        @Key
        private Integer width;

        public Long getDurationMillis() {
            return this.durationMillis;
        }

        public VideoMediaMetadata setDurationMillis(Long l) {
            this.durationMillis = l;
            return this;
        }

        public Integer getHeight() {
            return this.height;
        }

        public VideoMediaMetadata setHeight(Integer num) {
            this.height = num;
            return this;
        }

        public Integer getWidth() {
            return this.width;
        }

        public VideoMediaMetadata setWidth(Integer num) {
            this.width = num;
            return this;
        }

        public VideoMediaMetadata set(String str, Object obj) {
            return (VideoMediaMetadata) super.set(str, obj);
        }

        public VideoMediaMetadata clone() {
            return (VideoMediaMetadata) super.clone();
        }
    }

    static {
        Data.nullOf(User.class);
        Data.nullOf(ParentReference.class);
        Data.nullOf(Permission.class);
        Data.nullOf(Property.class);
    }

    public String getAlternateLink() {
        return this.alternateLink;
    }

    public File setAlternateLink(String str) {
        this.alternateLink = str;
        return this;
    }

    public Boolean getAppDataContents() {
        return this.appDataContents;
    }

    public File setAppDataContents(Boolean bool) {
        this.appDataContents = bool;
        return this;
    }

    public Boolean getCopyable() {
        return this.copyable;
    }

    public File setCopyable(Boolean bool) {
        this.copyable = bool;
        return this;
    }

    public DateTime getCreatedDate() {
        return this.createdDate;
    }

    public File setCreatedDate(DateTime dateTime) {
        this.createdDate = dateTime;
        return this;
    }

    public String getDefaultOpenWithLink() {
        return this.defaultOpenWithLink;
    }

    public File setDefaultOpenWithLink(String str) {
        this.defaultOpenWithLink = str;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public File setDescription(String str) {
        this.description = str;
        return this;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public File setDownloadUrl(String str) {
        this.downloadUrl = str;
        return this;
    }

    public Boolean getEditable() {
        return this.editable;
    }

    public File setEditable(Boolean bool) {
        this.editable = bool;
        return this;
    }

    public String getEmbedLink() {
        return this.embedLink;
    }

    public File setEmbedLink(String str) {
        this.embedLink = str;
        return this;
    }

    public String getEtag() {
        return this.etag;
    }

    public File setEtag(String str) {
        this.etag = str;
        return this;
    }

    public Boolean getExplicitlyTrashed() {
        return this.explicitlyTrashed;
    }

    public File setExplicitlyTrashed(Boolean bool) {
        this.explicitlyTrashed = bool;
        return this;
    }

    public Map<String, String> getExportLinks() {
        return this.exportLinks;
    }

    public File setExportLinks(Map<String, String> map) {
        this.exportLinks = map;
        return this;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public File setFileExtension(String str) {
        this.fileExtension = str;
        return this;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public File setFileSize(Long l) {
        this.fileSize = l;
        return this;
    }

    public String getFolderColorRgb() {
        return this.folderColorRgb;
    }

    public File setFolderColorRgb(String str) {
        this.folderColorRgb = str;
        return this;
    }

    public String getHeadRevisionId() {
        return this.headRevisionId;
    }

    public File setHeadRevisionId(String str) {
        this.headRevisionId = str;
        return this;
    }

    public String getIconLink() {
        return this.iconLink;
    }

    public File setIconLink(String str) {
        this.iconLink = str;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public File setId(String str) {
        this.id = str;
        return this;
    }

    public ImageMediaMetadata getImageMediaMetadata() {
        return this.imageMediaMetadata;
    }

    public File setImageMediaMetadata(ImageMediaMetadata imageMediaMetadata) {
        this.imageMediaMetadata = imageMediaMetadata;
        return this;
    }

    public IndexableText getIndexableText() {
        return this.indexableText;
    }

    public File setIndexableText(IndexableText indexableText) {
        this.indexableText = indexableText;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public File setKind(String str) {
        this.kind = str;
        return this;
    }

    public Labels getLabels() {
        return this.labels;
    }

    public File setLabels(Labels labels) {
        this.labels = labels;
        return this;
    }

    public User getLastModifyingUser() {
        return this.lastModifyingUser;
    }

    public File setLastModifyingUser(User user) {
        this.lastModifyingUser = user;
        return this;
    }

    public String getLastModifyingUserName() {
        return this.lastModifyingUserName;
    }

    public File setLastModifyingUserName(String str) {
        this.lastModifyingUserName = str;
        return this;
    }

    public DateTime getLastViewedByMeDate() {
        return this.lastViewedByMeDate;
    }

    public File setLastViewedByMeDate(DateTime dateTime) {
        this.lastViewedByMeDate = dateTime;
        return this;
    }

    public DateTime getMarkedViewedByMeDate() {
        return this.markedViewedByMeDate;
    }

    public File setMarkedViewedByMeDate(DateTime dateTime) {
        this.markedViewedByMeDate = dateTime;
        return this;
    }

    public String getMd5Checksum() {
        return this.md5Checksum;
    }

    public File setMd5Checksum(String str) {
        this.md5Checksum = str;
        return this;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public File setMimeType(String str) {
        this.mimeType = str;
        return this;
    }

    public DateTime getModifiedByMeDate() {
        return this.modifiedByMeDate;
    }

    public File setModifiedByMeDate(DateTime dateTime) {
        this.modifiedByMeDate = dateTime;
        return this;
    }

    public DateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public File setModifiedDate(DateTime dateTime) {
        this.modifiedDate = dateTime;
        return this;
    }

    public Map<String, String> getOpenWithLinks() {
        return this.openWithLinks;
    }

    public File setOpenWithLinks(Map<String, String> map) {
        this.openWithLinks = map;
        return this;
    }

    public String getOriginalFilename() {
        return this.originalFilename;
    }

    public File setOriginalFilename(String str) {
        this.originalFilename = str;
        return this;
    }

    public List<String> getOwnerNames() {
        return this.ownerNames;
    }

    public File setOwnerNames(List<String> list) {
        this.ownerNames = list;
        return this;
    }

    public List<User> getOwners() {
        return this.owners;
    }

    public File setOwners(List<User> list) {
        this.owners = list;
        return this;
    }

    public List<ParentReference> getParents() {
        return this.parents;
    }

    public File setParents(List<ParentReference> list) {
        this.parents = list;
        return this;
    }

    public List<Permission> getPermissions() {
        return this.permissions;
    }

    public File setPermissions(List<Permission> list) {
        this.permissions = list;
        return this;
    }

    public List<Property> getProperties() {
        return this.properties;
    }

    public File setProperties(List<Property> list) {
        this.properties = list;
        return this;
    }

    public Long getQuotaBytesUsed() {
        return this.quotaBytesUsed;
    }

    public File setQuotaBytesUsed(Long l) {
        this.quotaBytesUsed = l;
        return this;
    }

    public String getSelfLink() {
        return this.selfLink;
    }

    public File setSelfLink(String str) {
        this.selfLink = str;
        return this;
    }

    public Boolean getShared() {
        return this.shared;
    }

    public File setShared(Boolean bool) {
        this.shared = bool;
        return this;
    }

    public DateTime getSharedWithMeDate() {
        return this.sharedWithMeDate;
    }

    public File setSharedWithMeDate(DateTime dateTime) {
        this.sharedWithMeDate = dateTime;
        return this;
    }

    public User getSharingUser() {
        return this.sharingUser;
    }

    public File setSharingUser(User user) {
        this.sharingUser = user;
        return this;
    }

    public Thumbnail getThumbnail() {
        return this.thumbnail;
    }

    public File setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public String getThumbnailLink() {
        return this.thumbnailLink;
    }

    public File setThumbnailLink(String str) {
        this.thumbnailLink = str;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public File setTitle(String str) {
        this.title = str;
        return this;
    }

    public Permission getUserPermission() {
        return this.userPermission;
    }

    public File setUserPermission(Permission permission) {
        this.userPermission = permission;
        return this;
    }

    public Long getVersion() {
        return this.version;
    }

    public File setVersion(Long l) {
        this.version = l;
        return this;
    }

    public VideoMediaMetadata getVideoMediaMetadata() {
        return this.videoMediaMetadata;
    }

    public File setVideoMediaMetadata(VideoMediaMetadata videoMediaMetadata) {
        this.videoMediaMetadata = videoMediaMetadata;
        return this;
    }

    public String getWebContentLink() {
        return this.webContentLink;
    }

    public File setWebContentLink(String str) {
        this.webContentLink = str;
        return this;
    }

    public String getWebViewLink() {
        return this.webViewLink;
    }

    public File setWebViewLink(String str) {
        this.webViewLink = str;
        return this;
    }

    public Boolean getWritersCanShare() {
        return this.writersCanShare;
    }

    public File setWritersCanShare(Boolean bool) {
        this.writersCanShare = bool;
        return this;
    }

    public File set(String str, Object obj) {
        return (File) super.set(str, obj);
    }

    public File clone() {
        return (File) super.clone();
    }
}
