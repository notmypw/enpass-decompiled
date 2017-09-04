package com.box.androidsdk.content.models;

import com.box.androidsdk.content.utils.BoxDateFormat;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class BoxComment extends BoxEntity {
    static final /* synthetic */ boolean $assertionsDisabled = (!BoxComment.class.desiredAssertionStatus() ? true : $assertionsDisabled);
    public static final String[] ALL_FIELDS = new String[]{BoxRealTimeServer.FIELD_TYPE, BoxEntity.FIELD_ID, FIELD_IS_REPLY_COMMENT, FIELD_MESSAGE, FIELD_TAGGED_MESSAGE, FIELD_CREATED_BY, FIELD_CREATED_AT, FIELD_ITEM, FIELD_MODIFIED_AT};
    public static final String FIELD_CREATED_AT = "created_at";
    public static final String FIELD_CREATED_BY = "created_by";
    public static final String FIELD_IS_REPLY_COMMENT = "is_reply_comment";
    public static final String FIELD_ITEM = "item";
    public static final String FIELD_MESSAGE = "message";
    public static final String FIELD_MODIFIED_AT = "modified_at";
    public static final String FIELD_TAGGED_MESSAGE = "tagged_message";
    public static final String TYPE = "comment";
    private static final long serialVersionUID = 8873984774699405343L;

    public BoxComment(Map<String, Object> map) {
        super(map);
    }

    public Boolean getIsReplyComment() {
        return (Boolean) this.mProperties.get(FIELD_IS_REPLY_COMMENT);
    }

    public String getMessage() {
        return (String) this.mProperties.get(FIELD_MESSAGE);
    }

    public BoxUser getCreatedBy() {
        return (BoxUser) this.mProperties.get(FIELD_CREATED_BY);
    }

    public Date getCreatedAt() {
        return (Date) this.mProperties.get(FIELD_CREATED_AT);
    }

    public BoxItem getItem() {
        return (BoxItem) this.mProperties.get(FIELD_ITEM);
    }

    public Date getModifiedAt() {
        return (Date) this.mProperties.get(FIELD_MODIFIED_AT);
    }

    protected void parseJSONMember(Member member) {
        try {
            String memberName = member.getName();
            JsonValue value = member.getValue();
            if (memberName.equals(FIELD_IS_REPLY_COMMENT)) {
                this.mProperties.put(FIELD_IS_REPLY_COMMENT, Boolean.valueOf(value.asBoolean()));
            } else if (memberName.equals(FIELD_MESSAGE)) {
                this.mProperties.put(FIELD_MESSAGE, value.asString());
            } else if (memberName.equals(FIELD_TAGGED_MESSAGE)) {
                this.mProperties.put(FIELD_TAGGED_MESSAGE, value.asString());
            } else if (memberName.equals(FIELD_CREATED_BY)) {
                BoxUser createdBy = new BoxUser();
                createdBy.createFromJson(value.asObject());
                this.mProperties.put(FIELD_CREATED_BY, createdBy);
            } else if (memberName.equals(FIELD_CREATED_AT)) {
                this.mProperties.put(FIELD_CREATED_AT, BoxDateFormat.parse(value.asString()));
            } else if (memberName.equals(FIELD_MODIFIED_AT)) {
                this.mProperties.put(FIELD_MODIFIED_AT, BoxDateFormat.parse(value.asString()));
            } else {
                if (memberName.equals(FIELD_ITEM)) {
                    BoxEntity entity;
                    JsonObject itemObj = value.asObject();
                    String itemType = itemObj.get(BoxRealTimeServer.FIELD_TYPE).asString();
                    if (itemType.equals(BoxFile.TYPE)) {
                        entity = new BoxFile();
                        entity.createFromJson(itemObj);
                    } else if (itemType.equals(TYPE)) {
                        entity = new BoxComment();
                        entity.createFromJson(itemObj);
                    } else if (itemType.equals(BoxBookmark.TYPE)) {
                        entity = new BoxBookmark();
                        entity.createFromJson(itemObj);
                    } else {
                        throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Unsupported type \"%s\" for comment found", new Object[]{itemType}));
                    }
                    this.mProperties.put(FIELD_ITEM, entity);
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
}
