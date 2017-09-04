package com.box.androidsdk.content.models;

import com.box.androidsdk.content.utils.BoxDateFormat;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public abstract class BoxCollaborator extends BoxEntity {
    static final /* synthetic */ boolean $assertionsDisabled = (!BoxCollaborator.class.desiredAssertionStatus() ? true : $assertionsDisabled);
    public static final String FIELD_CREATED_AT = "created_at";
    public static final String FIELD_MODIFIED_AT = "modified_at";
    public static final String FIELD_NAME = "name";
    private static final long serialVersionUID = 4995483369186543255L;

    public BoxCollaborator(Map<String, Object> map) {
        super(map);
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

    protected void parseJSONMember(Member member) {
        try {
            JsonValue value = member.getValue();
            if (member.getName().equals(FIELD_NAME)) {
                this.mProperties.put(FIELD_NAME, value.asString());
            } else if (member.getName().equals(FIELD_CREATED_AT)) {
                this.mProperties.put(FIELD_CREATED_AT, BoxDateFormat.parse(value.asString()));
            } else {
                if (member.getName().equals(FIELD_MODIFIED_AT)) {
                    this.mProperties.put(FIELD_MODIFIED_AT, BoxDateFormat.parse(value.asString()));
                    return;
                }
                super.parseJSONMember(member);
            }
        } catch (ParseException e) {
            if (!$assertionsDisabled) {
                throw new AssertionError("A ParseException indicates a bug in the SDK.");
            }
        }
    }

    public static BoxCollaborator createCollaboratorFromJson(String json) {
        BoxEntity createdByEntity = new BoxEntity();
        createdByEntity.createFromJson(json);
        if (createdByEntity.getType().equals(BoxUser.TYPE)) {
            BoxUser user = new BoxUser();
            user.createFromJson(json);
            return user;
        } else if (!createdByEntity.getType().equals(BoxGroup.TYPE)) {
            return null;
        } else {
            BoxCollaborator group = new BoxGroup();
            group.createFromJson(json);
            return group;
        }
    }

    public static BoxCollaborator createCollaboratorFromJson(JsonObject json) {
        BoxEntity createdByEntity = new BoxEntity();
        createdByEntity.createFromJson(json);
        if (createdByEntity.getType().equals(BoxUser.TYPE)) {
            BoxUser user = new BoxUser();
            user.createFromJson(json);
            return user;
        } else if (!createdByEntity.getType().equals(BoxGroup.TYPE)) {
            return null;
        } else {
            BoxCollaborator group = new BoxGroup();
            group.createFromJson(json);
            return group;
        }
    }
}
