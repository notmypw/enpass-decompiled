package com.box.androidsdk.content.models;

import com.box.androidsdk.content.utils.BoxDateFormat;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public class BoxFileVersion extends BoxEntity {
    static final /* synthetic */ boolean $assertionsDisabled = (!BoxFileVersion.class.desiredAssertionStatus() ? true : $assertionsDisabled);
    public static final String[] ALL_FIELDS = new String[]{FIELD_NAME, FIELD_SIZE, FIELD_SHA1, FIELD_MODIFIED_BY, FIELD_CREATED_AT, FIELD_MODIFIED_AT, FIELD_DELETED_AT};
    public static final String FIELD_CREATED_AT = "created_at";
    public static final String FIELD_DELETED_AT = "deleted_at";
    public static final String FIELD_MODIFIED_AT = "modified_at";
    public static final String FIELD_MODIFIED_BY = "modified_by";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_SHA1 = "sha1";
    public static final String FIELD_SIZE = "size";
    public static final String TYPE = "file_version";
    private static final long serialVersionUID = -1013756375421636876L;

    public BoxFileVersion(Map<String, Object> map) {
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

    public String getSha1() {
        return (String) this.mProperties.get(FIELD_SHA1);
    }

    public Date getDeletedAt() {
        return (Date) this.mProperties.get(FIELD_DELETED_AT);
    }

    public Long getSize() {
        return (Long) this.mProperties.get(FIELD_SIZE);
    }

    public BoxUser getModifiedBy() {
        return (BoxUser) this.mProperties.get(FIELD_MODIFIED_BY);
    }

    protected void parseJSONMember(Member member) {
        try {
            String memberName = member.getName();
            JsonValue value = member.getValue();
            if (memberName.equals(FIELD_NAME)) {
                this.mProperties.put(FIELD_NAME, value.asString());
            } else if (memberName.equals(FIELD_SHA1)) {
                this.mProperties.put(FIELD_SHA1, value.asString());
            } else if (memberName.equals(FIELD_DELETED_AT)) {
                this.mProperties.put(FIELD_DELETED_AT, BoxDateFormat.parse(value.asString()));
            } else if (memberName.equals(FIELD_SIZE)) {
                this.mProperties.put(FIELD_SIZE, Long.valueOf(value.toString()));
            } else if (memberName.equals(FIELD_MODIFIED_BY)) {
                this.mProperties.put(FIELD_MODIFIED_BY, parseUserInfo(value.asObject()));
            } else if (memberName.equals(FIELD_CREATED_AT)) {
                this.mProperties.put(FIELD_CREATED_AT, BoxDateFormat.parse(value.asString()));
            } else {
                if (memberName.equals(FIELD_MODIFIED_AT)) {
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

    private BoxUser parseUserInfo(JsonObject jsonObject) {
        BoxUser user = new BoxUser();
        user.createFromJson(jsonObject);
        return user;
    }
}
