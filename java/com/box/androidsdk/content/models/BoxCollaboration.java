package com.box.androidsdk.content.models;

import android.text.TextUtils;
import com.box.androidsdk.content.utils.BoxDateFormat;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class BoxCollaboration extends BoxEntity {
    static final /* synthetic */ boolean $assertionsDisabled = (!BoxCollaboration.class.desiredAssertionStatus() ? true : $assertionsDisabled);
    public static final String[] ALL_FIELDS = new String[]{BoxRealTimeServer.FIELD_TYPE, BoxEntity.FIELD_ID, FIELD_CREATED_BY, FIELD_CREATED_AT, FIELD_MODIFIED_AT, FIELD_EXPIRES_AT, FIELD_STATUS, FIELD_ACCESSIBLE_BY, FIELD_ROLE, FIELD_ACKNOWLEDGED_AT, FIELD_ITEM};
    public static final String FIELD_ACCESSIBLE_BY = "accessible_by";
    public static final String FIELD_ACKNOWLEDGED_AT = "acknowledged_at";
    public static final String FIELD_CREATED_AT = "created_at";
    public static final String FIELD_CREATED_BY = "created_by";
    public static final String FIELD_EXPIRES_AT = "expires_at";
    public static final String FIELD_ITEM = "item";
    public static final String FIELD_MODIFIED_AT = "modified_at";
    public static final String FIELD_ROLE = "role";
    public static final String FIELD_STATUS = "status";
    public static final String TYPE = "collaboration";
    private static final long serialVersionUID = 8125965031679671555L;

    public enum Role {
        EDITOR("editor"),
        VIEWER("viewer"),
        PREVIEWER("previewer"),
        UPLOADER("uploader"),
        PREVIEWER_UPLOADER("previewer uploader"),
        VIEWER_UPLOADER("viewer uploader"),
        CO_OWNER("co-owner"),
        OWNER("owner");
        
        private final String mValue;

        private Role(String value) {
            this.mValue = value;
        }

        public static Role fromString(String text) {
            if (!TextUtils.isEmpty(text)) {
                for (Role e : values()) {
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

    public enum Status {
        ACCEPTED("accepted"),
        PENDING("pending"),
        REJECTED("rejected");
        
        private final String mValue;

        private Status(String value) {
            this.mValue = value;
        }

        public static Status fromString(String text) {
            if (!TextUtils.isEmpty(text)) {
                for (Status e : values()) {
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

    public BoxCollaboration(Map<String, Object> map) {
        super(map);
    }

    public BoxCollaborator getCreatedBy() {
        return (BoxCollaborator) this.mProperties.get(FIELD_CREATED_BY);
    }

    public Date getCreatedAt() {
        return (Date) this.mProperties.get(FIELD_CREATED_AT);
    }

    public Date getModifiedAt() {
        return (Date) this.mProperties.get(FIELD_MODIFIED_AT);
    }

    public Date getExpiresAt() {
        return (Date) this.mProperties.get(FIELD_EXPIRES_AT);
    }

    public Status getStatus() {
        return (Status) this.mProperties.get(FIELD_STATUS);
    }

    public BoxCollaborator getAccessibleBy() {
        return (BoxCollaborator) this.mProperties.get(FIELD_ACCESSIBLE_BY);
    }

    public Role getRole() {
        return (Role) this.mProperties.get(FIELD_ROLE);
    }

    public Date getAcknowledgedAt() {
        return (Date) this.mProperties.get(FIELD_ACKNOWLEDGED_AT);
    }

    public BoxFolder getItem() {
        return (BoxFolder) this.mProperties.get(FIELD_ITEM);
    }

    protected void parseJSONMember(Member member) {
        String memberName = member.getName();
        JsonValue value = member.getValue();
        try {
            if (memberName.equals(FIELD_CREATED_BY)) {
                this.mProperties.put(FIELD_CREATED_BY, BoxCollaborator.createCollaboratorFromJson(value.asObject()));
            } else if (memberName.equals(FIELD_CREATED_AT)) {
                this.mProperties.put(FIELD_CREATED_AT, BoxDateFormat.parse(value.asString()));
            } else if (memberName.equals(FIELD_MODIFIED_AT)) {
                this.mProperties.put(FIELD_MODIFIED_AT, BoxDateFormat.parse(value.asString()));
            } else if (memberName.equals(FIELD_EXPIRES_AT)) {
                this.mProperties.put(FIELD_EXPIRES_AT, BoxDateFormat.parse(value.asString()));
            } else if (memberName.equals(FIELD_STATUS)) {
                this.mProperties.put(FIELD_STATUS, Status.fromString(value.asString()));
            } else if (memberName.equals(FIELD_ACCESSIBLE_BY)) {
                BoxUser accessibleBy = new BoxUser();
                accessibleBy.createFromJson(value.asObject());
                this.mProperties.put(FIELD_ACCESSIBLE_BY, accessibleBy);
            } else if (memberName.equals(FIELD_ROLE)) {
                this.mProperties.put(FIELD_ROLE, Role.fromString(value.asString()));
            } else if (memberName.equals(FIELD_ACKNOWLEDGED_AT)) {
                this.mProperties.put(FIELD_ACKNOWLEDGED_AT, BoxDateFormat.parse(value.asString()));
            } else {
                if (memberName.equals(FIELD_ITEM)) {
                    JsonObject itemObj = value.asObject();
                    if (itemObj.get(BoxRealTimeServer.FIELD_TYPE).asString().equals(BoxFolder.TYPE)) {
                        BoxEntity entity = new BoxFolder();
                        entity.createFromJson(itemObj);
                        this.mProperties.put(FIELD_ITEM, entity);
                        return;
                    }
                    throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Unsupported type \"%s\" for collaboration found", new Object[]{itemObj.get(BoxRealTimeServer.FIELD_TYPE).asString()}));
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
