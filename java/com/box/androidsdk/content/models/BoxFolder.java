package com.box.androidsdk.content.models;

import android.text.TextUtils;
import com.box.androidsdk.content.models.BoxCollaboration.Role;
import com.box.androidsdk.content.models.BoxSharedLink.Access;
import com.box.androidsdk.content.models.BoxSharedLink.Permissions;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class BoxFolder extends BoxItem {
    public static final String[] ALL_FIELDS = new String[]{BoxRealTimeServer.FIELD_TYPE, BoxEntity.FIELD_ID, BoxItem.FIELD_SEQUENCE_ID, BoxItem.FIELD_ETAG, BoxFileVersion.FIELD_NAME, BoxFileVersion.FIELD_CREATED_AT, BoxFileVersion.FIELD_MODIFIED_AT, BoxItem.FIELD_DESCRIPTION, FIELD_SIZE, BoxItem.FIELD_PATH_COLLECTION, BoxEvent.FIELD_CREATED_BY, BoxFileVersion.FIELD_MODIFIED_BY, BoxItem.FIELD_TRASHED_AT, BoxItem.FIELD_PURGED_AT, FIELD_CONTENT_CREATED_AT, FIELD_CONTENT_MODIFIED_AT, BoxItem.FIELD_OWNED_BY, BoxItem.FIELD_SHARED_LINK, FIELD_FOLDER_UPLOAD_EMAIL, BoxMetadata.FIELD_PARENT, BoxItem.FIELD_ITEM_STATUS, FIELD_ITEM_COLLECTION, FIELD_SYNC_STATE, FIELD_HAS_COLLABORATIONS, BoxSharedLink.FIELD_PERMISSIONS, FIELD_CAN_NON_OWNERS_INVITE, FIELD_IS_EXTERNALLY_OWNED, BoxItem.FIELD_ALLOWED_SHARED_LINK_ACCESS_LEVELS, FIELD_ALLOWED_INVITEE_ROLES};
    public static final String FIELD_ALLOWED_INVITEE_ROLES = "allowed_invitee_roles";
    public static final String FIELD_CAN_NON_OWNERS_INVITE = "can_non_owners_invite";
    public static final String FIELD_CONTENT_CREATED_AT = "content_created_at";
    public static final String FIELD_CONTENT_MODIFIED_AT = "content_modified_at";
    public static final String FIELD_FOLDER_UPLOAD_EMAIL = "folder_upload_email";
    public static final String FIELD_HAS_COLLABORATIONS = "has_collaborations";
    public static final String FIELD_IS_EXTERNALLY_OWNED = "is_externally_owned";
    public static final String FIELD_ITEM_COLLECTION = "item_collection";
    public static final String FIELD_SIZE = "size";
    public static final String FIELD_SYNC_STATE = "sync_state";
    public static final String TYPE = "folder";
    private static final long serialVersionUID = 8020073615785970254L;

    public enum Permission {
        CAN_DOWNLOAD(Permissions.FIELD_CAN_DOWNLOAD),
        CAN_UPLOAD("can_upload"),
        CAN_RENAME("can_rename"),
        CAN_DELETE("can_delete"),
        CAN_SHARE("can_share"),
        CAN_INVITE_COLLABORATOR("can_invite_collaborator"),
        CAN_SET_SHARE_ACCESS("can_set_share_access");
        
        private final String mValue;

        private Permission(String value) {
            this.mValue = value;
        }

        public static Permission fromString(String text) {
            if (!TextUtils.isEmpty(text)) {
                for (Permission e : values()) {
                    if (text.equalsIgnoreCase(e.toString())) {
                        return e;
                    }
                }
            }
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "No enum with text %s found", new Object[]{text}));
        }

        public String toString() {
            return this.mValue;
        }
    }

    public enum SyncState {
        SYNCED(BoxItem.FIELD_SYNCED),
        NOT_SYNCED("not_synced"),
        PARTIALLY_SYNCED("partially_synced");
        
        private final String mValue;

        private SyncState(String value) {
            this.mValue = value;
        }

        public static SyncState fromString(String text) {
            if (!TextUtils.isEmpty(text)) {
                for (SyncState e : values()) {
                    if (text.equalsIgnoreCase(e.toString())) {
                        return e;
                    }
                }
            }
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "No enum with text %s found", new Object[]{text}));
        }

        public String toString() {
            return this.mValue;
        }
    }

    public BoxFolder(Map<String, Object> map) {
        super(map);
    }

    public static BoxFolder createFromId(String folderId) {
        LinkedHashMap<String, Object> folderMap = new LinkedHashMap();
        folderMap.put(BoxEntity.FIELD_ID, folderId);
        folderMap.put(BoxRealTimeServer.FIELD_TYPE, TYPE);
        return new BoxFolder(folderMap);
    }

    public BoxUploadEmail getUploadEmail() {
        return (BoxUploadEmail) this.mProperties.get(FIELD_FOLDER_UPLOAD_EMAIL);
    }

    public Boolean getHasCollaborations() {
        return (Boolean) this.mProperties.get(FIELD_HAS_COLLABORATIONS);
    }

    public SyncState getSyncState() {
        return (SyncState) this.mProperties.get(FIELD_SYNC_STATE);
    }

    public EnumSet<Permission> getPermissions() {
        return (EnumSet) this.mProperties.get(BoxSharedLink.FIELD_PERMISSIONS);
    }

    public Boolean getCanNonOwnersInvite() {
        return (Boolean) this.mProperties.get(FIELD_CAN_NON_OWNERS_INVITE);
    }

    public BoxListItems getItemCollection() {
        return this.mProperties.containsKey(FIELD_ITEM_COLLECTION) ? (BoxListItems) this.mProperties.get(FIELD_ITEM_COLLECTION) : null;
    }

    public Boolean getIsExternallyOwned() {
        return (Boolean) this.mProperties.get(FIELD_IS_EXTERNALLY_OWNED);
    }

    public ArrayList<Access> getAllowedSharedLinkAccessLevels() {
        return (ArrayList) this.mProperties.get(BoxItem.FIELD_ALLOWED_SHARED_LINK_ACCESS_LEVELS);
    }

    public ArrayList<Role> getAllowedInviteeRoles() {
        return (ArrayList) this.mProperties.get(FIELD_ALLOWED_INVITEE_ROLES);
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

    protected void parseJSONMember(Member member) {
        String memberName = member.getName();
        JsonValue value = member.getValue();
        if (memberName.equals(FIELD_FOLDER_UPLOAD_EMAIL)) {
            BoxUploadEmail uploadEmail = new BoxUploadEmail();
            uploadEmail.createFromJson(value.asObject());
            this.mProperties.put(FIELD_FOLDER_UPLOAD_EMAIL, uploadEmail);
        } else if (memberName.equals(FIELD_HAS_COLLABORATIONS)) {
            this.mProperties.put(FIELD_HAS_COLLABORATIONS, Boolean.valueOf(value.asBoolean()));
        } else if (memberName.equals(FIELD_SYNC_STATE)) {
            this.mProperties.put(FIELD_SYNC_STATE, SyncState.fromString(value.asString()));
        } else if (memberName.equals(BoxSharedLink.FIELD_PERMISSIONS)) {
            this.mProperties.put(BoxSharedLink.FIELD_PERMISSIONS, parsePermissions(value.asObject()));
        } else if (memberName.equals(FIELD_CAN_NON_OWNERS_INVITE)) {
            this.mProperties.put(FIELD_CAN_NON_OWNERS_INVITE, Boolean.valueOf(value.asBoolean()));
        } else if (memberName.equals(FIELD_ITEM_COLLECTION)) {
            JsonObject jsonObject = value.asObject();
            BoxListItems collection = new BoxListItems();
            collection.createFromJson(jsonObject);
            this.mProperties.put(FIELD_ITEM_COLLECTION, collection);
        } else if (memberName.equals(FIELD_IS_EXTERNALLY_OWNED)) {
            this.mProperties.put(FIELD_IS_EXTERNALLY_OWNED, Boolean.valueOf(value.asBoolean()));
        } else if (memberName.equals(FIELD_ALLOWED_INVITEE_ROLES)) {
            JsonArray rolesArr = value.asArray();
            ArrayList<Role> allowedRoles = new ArrayList();
            Iterator it = rolesArr.iterator();
            while (it.hasNext()) {
                allowedRoles.add(Role.fromString(((JsonValue) it.next()).asString()));
            }
            this.mProperties.put(FIELD_ALLOWED_INVITEE_ROLES, allowedRoles);
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
                } else if (memberName.equals("can_invite_collaborator")) {
                    permissions.add(Permission.CAN_INVITE_COLLABORATOR);
                } else if (memberName.equals("can_set_share_access")) {
                    permissions.add(Permission.CAN_SET_SHARE_ACCESS);
                }
            }
        }
        return permissions;
    }
}
