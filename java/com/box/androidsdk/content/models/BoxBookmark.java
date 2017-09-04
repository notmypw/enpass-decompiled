package com.box.androidsdk.content.models;

import android.text.TextUtils;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class BoxBookmark extends BoxItem {
    public static final String[] ALL_FIELDS = new String[]{BoxRealTimeServer.FIELD_TYPE, BoxEntity.FIELD_ID, BoxItem.FIELD_SEQUENCE_ID, BoxItem.FIELD_ETAG, BoxFileVersion.FIELD_NAME, FIELD_URL, BoxFileVersion.FIELD_CREATED_AT, BoxFileVersion.FIELD_MODIFIED_AT, BoxItem.FIELD_DESCRIPTION, BoxItem.FIELD_PATH_COLLECTION, BoxEvent.FIELD_CREATED_BY, BoxFileVersion.FIELD_MODIFIED_BY, BoxItem.FIELD_TRASHED_AT, BoxItem.FIELD_PURGED_AT, BoxItem.FIELD_OWNED_BY, BoxItem.FIELD_SHARED_LINK, BoxMetadata.FIELD_PARENT, BoxItem.FIELD_ITEM_STATUS, BoxSharedLink.FIELD_PERMISSIONS, FIELD_COMMENT_COUNT};
    public static final String FIELD_COMMENT_COUNT = "comment_count";
    public static final String FIELD_URL = "url";
    public static final String TYPE = "web_link";

    public enum Permission {
        CAN_RENAME("can_rename"),
        CAN_DELETE("can_delete"),
        CAN_SHARE("can_share"),
        CAN_SET_SHARE_ACCESS("can_set_share_access"),
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

    public BoxBookmark(Map<String, Object> map) {
        super(map);
    }

    public static BoxBookmark createFromId(String bookmarkId) {
        LinkedHashMap<String, Object> bookmarkMap = new LinkedHashMap();
        bookmarkMap.put(BoxEntity.FIELD_ID, bookmarkId);
        bookmarkMap.put(BoxRealTimeServer.FIELD_TYPE, TYPE);
        return new BoxBookmark(bookmarkMap);
    }

    public String getUrl() {
        return (String) this.mProperties.get(FIELD_URL);
    }

    public Long getCommentCount() {
        return super.getCommentCount();
    }

    public Long getSize() {
        return null;
    }

    public EnumSet<Permission> getPermissions() {
        return (EnumSet) this.mProperties.get(BoxSharedLink.FIELD_PERMISSIONS);
    }

    protected void parseJSONMember(Member member) {
        String memberName = member.getName();
        JsonValue value = member.getValue();
        if (memberName.equals(FIELD_URL)) {
            this.mProperties.put(FIELD_URL, value.asString());
        } else if (memberName.equals(BoxSharedLink.FIELD_PERMISSIONS)) {
            this.mProperties.put(BoxSharedLink.FIELD_PERMISSIONS, parsePermissions(value.asObject()));
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
                if (memberName.equals("can_rename")) {
                    permissions.add(Permission.CAN_RENAME);
                } else if (memberName.equals("can_delete")) {
                    permissions.add(Permission.CAN_DELETE);
                } else if (memberName.equals("can_share")) {
                    permissions.add(Permission.CAN_SHARE);
                } else if (memberName.equals("can_set_share_access")) {
                    permissions.add(Permission.CAN_SET_SHARE_ACCESS);
                } else if (memberName.equals("can_comment")) {
                    permissions.add(Permission.CAN_COMMENT);
                }
            }
        }
        return permissions;
    }
}
