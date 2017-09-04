package com.box.androidsdk.content.models;

import android.text.TextUtils;
import com.box.androidsdk.content.models.BoxSharedLink.Permissions;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import java.util.Date;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class BoxFile extends BoxItem {
    public static final String[] ALL_FIELDS = new String[]{BoxRealTimeServer.FIELD_TYPE, BoxEntity.FIELD_ID, FIELD_FILE_VERSION, BoxItem.FIELD_SEQUENCE_ID, BoxItem.FIELD_ETAG, FIELD_SHA1, BoxFileVersion.FIELD_NAME, BoxFileVersion.FIELD_CREATED_AT, BoxFileVersion.FIELD_MODIFIED_AT, BoxItem.FIELD_DESCRIPTION, FIELD_SIZE, BoxItem.FIELD_PATH_COLLECTION, BoxEvent.FIELD_CREATED_BY, BoxFileVersion.FIELD_MODIFIED_BY, BoxItem.FIELD_TRASHED_AT, BoxItem.FIELD_PURGED_AT, FIELD_CONTENT_CREATED_AT, FIELD_CONTENT_MODIFIED_AT, BoxItem.FIELD_OWNED_BY, BoxItem.FIELD_SHARED_LINK, BoxMetadata.FIELD_PARENT, BoxItem.FIELD_ITEM_STATUS, FIELD_VERSION_NUMBER, FIELD_COMMENT_COUNT, BoxSharedLink.FIELD_PERMISSIONS, FIELD_EXTENSION, FIELD_IS_PACKAGE};
    public static final String FIELD_COMMENT_COUNT = "comment_count";
    public static final String FIELD_CONTENT_CREATED_AT = "content_created_at";
    public static final String FIELD_CONTENT_MODIFIED_AT = "content_modified_at";
    public static final String FIELD_EXTENSION = "extension";
    public static final String FIELD_FILE_VERSION = "file_version";
    public static final String FIELD_IS_PACKAGE = "is_package";
    public static final String FIELD_SHA1 = "sha1";
    public static final String FIELD_SIZE = "size";
    public static final String FIELD_VERSION_NUMBER = "version_number";
    public static final String TYPE = "file";
    private static final long serialVersionUID = -4732748896882484735L;

    public enum Permission {
        CAN_DOWNLOAD(Permissions.FIELD_CAN_DOWNLOAD),
        CAN_UPLOAD("can_upload"),
        CAN_RENAME("can_rename"),
        CAN_DELETE("can_delete"),
        CAN_SHARE("can_share"),
        CAN_SET_SHARE_ACCESS("can_set_share_access"),
        CAN_PREVIEW("can_preview"),
        CAN_COMMENT("can_comment");
        
        private final String value;

        private Permission(String value) {
            this.value = value;
        }

        public static Permission fromString(String text) {
            if (!TextUtils.isEmpty(text)) {
                for (Permission a : values()) {
                    if (text.equalsIgnoreCase(a.name())) {
                        return a;
                    }
                }
            }
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "No enum with text %s found", new Object[]{text}));
        }

        public String toString() {
            return this.value;
        }
    }

    public BoxFile(Map<String, Object> map) {
        super(map);
    }

    public static BoxFile createFromId(String fileId) {
        LinkedHashMap<String, Object> fileMap = new LinkedHashMap();
        fileMap.put(BoxEntity.FIELD_ID, fileId);
        fileMap.put(BoxRealTimeServer.FIELD_TYPE, TYPE);
        return new BoxFile(fileMap);
    }

    public BoxFileVersion getFileVersion() {
        return (BoxFileVersion) this.mProperties.get(FIELD_FILE_VERSION);
    }

    public String getSha1() {
        return (String) this.mProperties.get(FIELD_SHA1);
    }

    public String getVersionNumber() {
        return (String) this.mProperties.get(FIELD_VERSION_NUMBER);
    }

    public EnumSet<Permission> getPermissions() {
        return (EnumSet) this.mProperties.get(BoxSharedLink.FIELD_PERMISSIONS);
    }

    public String getExtension() {
        return (String) this.mProperties.get(FIELD_EXTENSION);
    }

    public Boolean getIsPackage() {
        return (Boolean) this.mProperties.get(FIELD_IS_PACKAGE);
    }

    public Date getContentCreatedAt() {
        return super.getContentCreatedAt();
    }

    public Long getSize() {
        return super.getSize();
    }

    public Date getContentModifiedAt() {
        return super.getContentModifiedAt();
    }

    public Long getCommentCount() {
        return super.getCommentCount();
    }

    protected void parseJSONMember(Member member) {
        String memberName = member.getName();
        JsonValue value = member.getValue();
        if (memberName.equals(FIELD_SHA1)) {
            this.mProperties.put(FIELD_SHA1, value.asString());
        } else if (memberName.equals(FIELD_VERSION_NUMBER)) {
            this.mProperties.put(FIELD_VERSION_NUMBER, value.asString());
        } else if (memberName.equals(BoxSharedLink.FIELD_PERMISSIONS)) {
            this.mProperties.put(BoxSharedLink.FIELD_PERMISSIONS, parsePermissions(value.asObject()));
        } else if (memberName.equals(FIELD_EXTENSION)) {
            this.mProperties.put(FIELD_EXTENSION, value.asString());
        } else if (memberName.equals(FIELD_IS_PACKAGE)) {
            this.mProperties.put(FIELD_IS_PACKAGE, Boolean.valueOf(value.asBoolean()));
        } else if (memberName.equals(FIELD_FILE_VERSION)) {
            JsonObject jsonObject = value.asObject();
            BoxFileVersion version = new BoxFileVersion();
            version.createFromJson(jsonObject);
            this.mProperties.put(FIELD_FILE_VERSION, version);
        } else {
            super.parseJSONMember(member);
        }
    }

    private EnumSet<Permission> parsePermissions(JsonObject jsonObject) {
        EnumSet<Permission> permissions = EnumSet.noneOf(Permission.class);
        Iterator it = jsonObject.iterator();
        while (it.hasNext()) {
            Member member = (Member) it.next();
            JsonValue value = member.getValue();
            if (!value.isNull() && value.asBoolean()) {
                String memberName = member.getName();
                if (memberName.equals(Permissions.FIELD_CAN_DOWNLOAD)) {
                    permissions.add(Permission.CAN_DOWNLOAD);
                } else if (memberName.equals("can_upload")) {
                    permissions.add(Permission.CAN_UPLOAD);
                } else if (memberName.equals("can_rename")) {
                    permissions.add(Permission.CAN_RENAME);
                } else if (memberName.equals("can_delete")) {
                    permissions.add(Permission.CAN_DELETE);
                } else if (memberName.equals("can_share")) {
                    permissions.add(Permission.CAN_SHARE);
                } else if (memberName.equals("can_set_share_access")) {
                    permissions.add(Permission.CAN_SET_SHARE_ACCESS);
                } else if (memberName.equals("can_preview")) {
                    permissions.add(Permission.CAN_PREVIEW);
                } else if (memberName.equals("can_comment")) {
                    permissions.add(Permission.CAN_COMMENT);
                }
            }
        }
        return permissions;
    }
}
