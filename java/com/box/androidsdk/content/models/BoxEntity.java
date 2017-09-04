package com.box.androidsdk.content.models;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import java.util.Map;

public class BoxEntity extends BoxJsonObject {
    public static final String FIELD_ID = "id";
    public static final String FIELD_ITEM_ID = "item_id";
    public static final String FIELD_ITEM_TYPE = "item_type";
    public static final String FIELD_TYPE = "type";
    private static final long serialVersionUID = 1626798809346520004L;

    public BoxEntity(Map<String, Object> map) {
        super(map);
    }

    public String getId() {
        String id = (String) this.mProperties.get(FIELD_ID);
        if (id == null) {
            return (String) this.mProperties.get(FIELD_ITEM_ID);
        }
        return id;
    }

    public String getType() {
        String type = (String) this.mProperties.get(FIELD_TYPE);
        if (type == null) {
            return (String) this.mProperties.get(FIELD_ITEM_TYPE);
        }
        return type;
    }

    protected void parseJSONMember(Member member) {
        String memberName = member.getName();
        JsonValue value = member.getValue();
        if (memberName.equals(FIELD_ID)) {
            this.mProperties.put(FIELD_ID, value.asString());
        } else if (memberName.equals(FIELD_TYPE)) {
            this.mProperties.put(FIELD_TYPE, value.asString());
        } else if (memberName.equals(FIELD_ITEM_TYPE)) {
            this.mProperties.put(FIELD_ITEM_TYPE, value.asString());
        } else if (memberName.equals(FIELD_ITEM_ID)) {
            this.mProperties.put(FIELD_ITEM_ID, value.asString());
        } else {
            super.parseJSONMember(member);
        }
    }

    public static BoxEntity createEntityFromJson(String json) {
        BoxEntity createdByEntity = new BoxEntity();
        createdByEntity.createFromJson(json);
        if (createdByEntity.getType() == null) {
            return createdByEntity;
        }
        if (createdByEntity.getType().equals(BoxCollection.TYPE)) {
            BoxEntity collection = new BoxCollection();
            collection.createFromJson(json);
            return collection;
        } else if (createdByEntity.getType().equals(BoxComment.TYPE)) {
            BoxEntity comment = new BoxComment();
            comment.createFromJson(json);
            return comment;
        } else if (createdByEntity.getType().equals(BoxCollaboration.TYPE)) {
            BoxEntity collaboration = new BoxCollaboration();
            collaboration.createFromJson(json);
            return collaboration;
        } else if (createdByEntity.getType().equals(BoxUser.FIELD_ENTERPRISE)) {
            BoxEntity enterprise = new BoxEnterprise();
            enterprise.createFromJson(json);
            return enterprise;
        } else if (createdByEntity.getType().equals(BoxFileVersion.TYPE)) {
            version = new BoxFileVersion();
            version.createFromJson(json);
            return version;
        } else if (createdByEntity.getType().equals(BoxEvent.TYPE)) {
            version = new BoxEnterpriseEvent();
            version.createFromJson(json);
            return version;
        } else {
            BoxEntity item = BoxItem.createBoxItemFromJson(json);
            if (item != null) {
                return item;
            }
            item = BoxCollaborator.createCollaboratorFromJson(json);
            if (item != null) {
                return item;
            }
            return null;
        }
    }

    public static BoxEntity createEntityFromJson(JsonObject json) {
        BoxEntity createdByEntity = new BoxEntity();
        createdByEntity.createFromJson(json);
        if (createdByEntity.getType() == null) {
            return createdByEntity;
        }
        if (createdByEntity.getType().equals(BoxCollection.TYPE)) {
            BoxEntity collection = new BoxCollection();
            collection.createFromJson(json);
            return collection;
        } else if (createdByEntity.getType().equals(BoxComment.TYPE)) {
            BoxEntity comment = new BoxComment();
            comment.createFromJson(json);
            return comment;
        } else if (createdByEntity.getType().equals(BoxCollaboration.TYPE)) {
            BoxEntity collaboration = new BoxCollaboration();
            collaboration.createFromJson(json);
            return collaboration;
        } else if (createdByEntity.getType().equals(BoxUser.FIELD_ENTERPRISE)) {
            BoxEntity enterprise = new BoxEnterprise();
            enterprise.createFromJson(json);
            return enterprise;
        } else if (createdByEntity.getType().equals(BoxFileVersion.TYPE)) {
            version = new BoxFileVersion();
            version.createFromJson(json);
            return version;
        } else if (createdByEntity.getType().equals(BoxEvent.TYPE)) {
            version = new BoxEnterpriseEvent();
            version.createFromJson(json);
            return version;
        } else {
            BoxEntity item = BoxItem.createBoxItemFromJson(json);
            if (item != null) {
                return item;
            }
            item = BoxCollaborator.createCollaboratorFromJson(json);
            if (item != null) {
                return item;
            }
            return null;
        }
    }
}
