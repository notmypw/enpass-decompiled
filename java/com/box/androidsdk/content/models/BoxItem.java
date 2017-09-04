package com.box.androidsdk.content.models;

import com.box.androidsdk.content.models.BoxSharedLink.Access;
import com.box.androidsdk.content.utils.BoxDateFormat;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import in.sinew.enpassengine.Attachment;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class BoxItem extends BoxEntity {
    static final /* synthetic */ boolean $assertionsDisabled = (!BoxItem.class.desiredAssertionStatus() ? true : $assertionsDisabled);
    public static final String FIELD_ALLOWED_SHARED_LINK_ACCESS_LEVELS = "allowed_shared_link_access_levels";
    public static final String FIELD_CREATED_AT = "created_at";
    public static final String FIELD_CREATED_BY = "created_by";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ETAG = "etag";
    public static final String FIELD_ITEM_STATUS = "item_status";
    public static final String FIELD_MODIFIED_AT = "modified_at";
    public static final String FIELD_MODIFIED_BY = "modified_by";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_OWNED_BY = "owned_by";
    public static final String FIELD_PARENT = "parent";
    public static final String FIELD_PATH_COLLECTION = "path_collection";
    public static final String FIELD_PERMISSIONS = "permissions";
    public static final String FIELD_PURGED_AT = "purged_at";
    public static final String FIELD_SEQUENCE_ID = "sequence_id";
    public static final String FIELD_SHARED_LINK = "shared_link";
    public static final String FIELD_SYNCED = "synced";
    public static final String FIELD_TAGS = "tags";
    public static final String FIELD_TRASHED_AT = "trashed_at";
    private static final long serialVersionUID = 4876182952337609430L;

    public BoxItem(Map<String, Object> map) {
        super(map);
    }

    public String getEtag() {
        return (String) this.mProperties.get(FIELD_ETAG);
    }

    public String getName() {
        return (String) this.mProperties.get(FIELD_NAME);
    }

    public Date getCreatedAt() {
        return (Date) this.mProperties.get(FIELD_CREATED_AT);
    }

    public Date getModifiedAt() {
        return (Date) this.mProperties.get(FIELD_MODIFIED_AT);
    }

    public String getDescription() {
        return (String) this.mProperties.get(FIELD_DESCRIPTION);
    }

    public Long getSize() {
        return (Long) this.mProperties.get(Attachment.ATTACHMENT_SIZE);
    }

    public BoxList<BoxFolder> getPathCollection() {
        return (BoxList) this.mProperties.get(FIELD_PATH_COLLECTION);
    }

    public BoxUser getCreatedBy() {
        return (BoxUser) this.mProperties.get(FIELD_CREATED_BY);
    }

    public BoxUser getModifiedBy() {
        return (BoxUser) this.mProperties.get(FIELD_MODIFIED_BY);
    }

    public Date getTrashedAt() {
        return (Date) this.mProperties.get(FIELD_TRASHED_AT);
    }

    public Date getPurgedAt() {
        return (Date) this.mProperties.get(FIELD_PURGED_AT);
    }

    protected Date getContentCreatedAt() {
        return (Date) this.mProperties.get(BoxFolder.FIELD_CONTENT_CREATED_AT);
    }

    protected Date getContentModifiedAt() {
        return (Date) this.mProperties.get(BoxFolder.FIELD_CONTENT_MODIFIED_AT);
    }

    public BoxUser getOwnedBy() {
        return (BoxUser) this.mProperties.get(FIELD_OWNED_BY);
    }

    public BoxSharedLink getSharedLink() {
        return (BoxSharedLink) this.mProperties.get(FIELD_SHARED_LINK);
    }

    public String getSequenceID() {
        return (String) this.mProperties.get(FIELD_SEQUENCE_ID);
    }

    public ArrayList<Access> getAllowedSharedLinkAccessLevels() {
        return (ArrayList) this.mProperties.get(FIELD_ALLOWED_SHARED_LINK_ACCESS_LEVELS);
    }

    public BoxFolder getParent() {
        return (BoxFolder) this.mProperties.get(FIELD_PARENT);
    }

    public String getItemStatus() {
        return (String) this.mProperties.get(FIELD_ITEM_STATUS);
    }

    public Boolean getIsSynced() {
        return (Boolean) this.mProperties.get(FIELD_SYNCED);
    }

    protected Long getCommentCount() {
        return (Long) this.mProperties.get(BoxFile.FIELD_COMMENT_COUNT);
    }

    protected void parseJSONMember(Member member) {
        try {
            JsonValue value = member.getValue();
            if (member.getName().equals(FIELD_NAME)) {
                this.mProperties.put(FIELD_NAME, value.asString());
            } else if (member.getName().equals(FIELD_SEQUENCE_ID)) {
                this.mProperties.put(FIELD_SEQUENCE_ID, value.asString());
            } else if (member.getName().equals(FIELD_ETAG)) {
                this.mProperties.put(FIELD_ETAG, value.asString());
            } else if (member.getName().equals(FIELD_CREATED_AT)) {
                this.mProperties.put(FIELD_CREATED_AT, BoxDateFormat.parse(value.asString()));
            } else if (member.getName().equals(FIELD_MODIFIED_AT)) {
                this.mProperties.put(FIELD_MODIFIED_AT, BoxDateFormat.parse(value.asString()));
            } else if (member.getName().equals(FIELD_DESCRIPTION)) {
                this.mProperties.put(FIELD_DESCRIPTION, value.asString());
            } else if (member.getName().equals(Attachment.ATTACHMENT_SIZE)) {
                this.mProperties.put(Attachment.ATTACHMENT_SIZE, Long.valueOf(value.toString()));
            } else if (member.getName().equals(FIELD_TRASHED_AT)) {
                this.mProperties.put(FIELD_TRASHED_AT, BoxDateFormat.parse(value.asString()));
            } else if (member.getName().equals(FIELD_PURGED_AT)) {
                this.mProperties.put(FIELD_PURGED_AT, BoxDateFormat.parse(value.asString()));
            } else if (member.getName().equals(BoxFolder.FIELD_CONTENT_CREATED_AT)) {
                this.mProperties.put(BoxFolder.FIELD_CONTENT_CREATED_AT, BoxDateFormat.parse(value.asString()));
            } else if (member.getName().equals(BoxFolder.FIELD_CONTENT_MODIFIED_AT)) {
                this.mProperties.put(BoxFolder.FIELD_CONTENT_MODIFIED_AT, BoxDateFormat.parse(value.asString()));
            } else if (member.getName().equals(FIELD_PATH_COLLECTION)) {
                JsonObject jsonObject = value.asObject();
                BoxList<BoxFolder> collection = new BoxList();
                collection.createFromJson(jsonObject);
                this.mProperties.put(FIELD_PATH_COLLECTION, collection);
            } else if (member.getName().equals(FIELD_CREATED_BY)) {
                this.mProperties.put(FIELD_CREATED_BY, parseUserInfo(value.asObject()));
            } else if (member.getName().equals(FIELD_MODIFIED_BY)) {
                this.mProperties.put(FIELD_MODIFIED_BY, parseUserInfo(value.asObject()));
            } else if (member.getName().equals(FIELD_OWNED_BY)) {
                this.mProperties.put(FIELD_OWNED_BY, parseUserInfo(value.asObject()));
            } else if (member.getName().equals(FIELD_SHARED_LINK)) {
                BoxSharedLink sl = new BoxSharedLink();
                sl.createFromJson(value.asObject());
                this.mProperties.put(FIELD_SHARED_LINK, sl);
            } else if (member.getName().equals(FIELD_PARENT)) {
                BoxFolder folder = new BoxFolder();
                folder.createFromJson(value.asObject());
                this.mProperties.put(FIELD_PARENT, folder);
            } else if (member.getName().equals(FIELD_ITEM_STATUS)) {
                this.mProperties.put(FIELD_ITEM_STATUS, value.asString());
            } else if (member.getName().equals(FIELD_SYNCED)) {
                this.mProperties.put(FIELD_SYNCED, Boolean.valueOf(value.asBoolean()));
            } else if (member.getName().equals(BoxFile.FIELD_COMMENT_COUNT)) {
                this.mProperties.put(BoxFile.FIELD_COMMENT_COUNT, Long.valueOf(value.asLong()));
            } else if (member.getName().equals(FIELD_ALLOWED_SHARED_LINK_ACCESS_LEVELS)) {
                JsonArray accessArr = value.asArray();
                ArrayList<Access> accessLevels = new ArrayList();
                Iterator it = accessArr.iterator();
                while (it.hasNext()) {
                    accessLevels.add(Access.fromString(((JsonValue) it.next()).asString()));
                }
                this.mProperties.put(FIELD_ALLOWED_SHARED_LINK_ACCESS_LEVELS, accessLevels);
            } else {
                if (member.getName().equals(FIELD_TAGS)) {
                    this.mProperties.put(FIELD_TAGS, value.asArray());
                    return;
                }
                super.parseJSONMember(member);
            }
        } catch (Exception e) {
            if (!$assertionsDisabled) {
                throw new AssertionError("A ParseException indicates a bug in the SDK.");
            }
        }
    }

    private List<BoxFolder> parsePathCollection(JsonObject jsonObject) {
        List<BoxFolder> pathCollection = new ArrayList(jsonObject.get(BoxList.FIELD_TOTAL_COUNT).asInt());
        Iterator it = jsonObject.get(BoxList.FIELD_ENTRIES).asArray().iterator();
        while (it.hasNext()) {
            JsonObject entry = ((JsonValue) it.next()).asObject();
            BoxFolder folder = new BoxFolder();
            folder.createFromJson(entry);
            pathCollection.add(folder);
        }
        return pathCollection;
    }

    private BoxUser parseUserInfo(JsonObject jsonObject) {
        BoxUser user = new BoxUser();
        user.createFromJson(jsonObject);
        return user;
    }

    private List<String> parseTags(JsonArray jsonArray) {
        List<String> tags = new ArrayList(jsonArray.size());
        Iterator it = jsonArray.iterator();
        while (it.hasNext()) {
            tags.add(((JsonValue) it.next()).asString());
        }
        return tags;
    }

    protected JsonValue parseJsonObject(Entry<String, Object> entry) {
        return super.parseJsonObject(entry);
    }

    public static BoxItem createBoxItemFromJson(String json) {
        BoxEntity createdByEntity = new BoxEntity();
        createdByEntity.createFromJson(json);
        if (createdByEntity.getType().equals(BoxFile.TYPE)) {
            BoxFile file = new BoxFile();
            file.createFromJson(json);
            return file;
        } else if (createdByEntity.getType().equals(BoxBookmark.TYPE)) {
            BoxItem bookmark = new BoxBookmark();
            bookmark.createFromJson(json);
            return bookmark;
        } else if (!createdByEntity.getType().equals(BoxFolder.TYPE)) {
            return null;
        } else {
            BoxItem folder = new BoxFolder();
            folder.createFromJson(json);
            return folder;
        }
    }

    public static BoxItem createBoxItemFromJson(JsonObject json) {
        BoxEntity createdByEntity = new BoxEntity();
        createdByEntity.createFromJson(json);
        if (createdByEntity.getType().equals(BoxFile.TYPE)) {
            BoxFile file = new BoxFile();
            file.createFromJson(json);
            return file;
        } else if (createdByEntity.getType().equals(BoxBookmark.TYPE)) {
            BoxItem bookmark = new BoxBookmark();
            bookmark.createFromJson(json);
            return bookmark;
        } else if (!createdByEntity.getType().equals(BoxFolder.TYPE)) {
            return null;
        } else {
            BoxItem folder = new BoxFolder();
            folder.createFromJson(json);
            return folder;
        }
    }
}
