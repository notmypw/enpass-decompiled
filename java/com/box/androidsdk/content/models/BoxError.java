package com.box.androidsdk.content.models;

import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class BoxError extends BoxJsonObject {
    public static final String FIELD_CODE = "code";
    public static final String FIELD_CONTEXT_INFO = "context_info";
    public static final String FIELD_ERROR = "error";
    public static final String FIELD_ERROR_DESCRIPTION = "error_description";
    public static final String FIELD_HELP_URL = "help_url";
    public static final String FIELD_MESSAGE = "message";
    public static final String FIELD_REQUEST_ID = "request_id";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_TYPE = "type";

    public static class ErrorContext extends BoxMapJsonObject {
        public static final String FIELD_CONFLICTS = "conflicts";

        protected void parseJSONMember(Member member) {
            String memberName = member.getName();
            JsonValue value = member.getValue();
            if (memberName.equals(FIELD_CONFLICTS)) {
                ArrayList<BoxEntity> boxItems = new ArrayList();
                if (value.isArray()) {
                    Iterator it = value.asArray().iterator();
                    while (it.hasNext()) {
                        boxItems.add(BoxEntity.createEntityFromJson(((JsonValue) it.next()).asObject()));
                    }
                } else {
                    boxItems.add(BoxEntity.createEntityFromJson(value.asObject()));
                }
                this.mProperties.put(FIELD_CONFLICTS, boxItems);
                return;
            }
            super.parseJSONMember(member);
        }

        public ArrayList<BoxEntity> getConflicts() {
            return (ArrayList) this.mProperties.get(FIELD_CONFLICTS);
        }
    }

    public BoxError(Map<String, Object> map) {
        super(map);
    }

    public String getType() {
        return (String) this.mProperties.get(FIELD_TYPE);
    }

    public Integer getStatus() {
        return (Integer) this.mProperties.get(FIELD_STATUS);
    }

    public String getCode() {
        return (String) this.mProperties.get(FIELD_CODE);
    }

    public ErrorContext getContextInfo() {
        return (ErrorContext) this.mProperties.get(FIELD_CONTEXT_INFO);
    }

    public String getFieldHelpUrl() {
        return (String) this.mProperties.get(FIELD_HELP_URL);
    }

    public String getMessage() {
        return (String) this.mProperties.get(FIELD_MESSAGE);
    }

    public String getRequestId() {
        return (String) this.mProperties.get(FIELD_REQUEST_ID);
    }

    public String getError() {
        return (String) this.mProperties.get(FIELD_ERROR);
    }

    public String getErrorDescription() {
        return (String) this.mProperties.get(FIELD_ERROR_DESCRIPTION);
    }

    protected void parseJSONMember(Member member) {
        String memberName = member.getName();
        JsonValue value = member.getValue();
        if (memberName.equals(FIELD_TYPE)) {
            this.mProperties.put(FIELD_TYPE, value.asString());
        } else if (memberName.equals(FIELD_STATUS)) {
            this.mProperties.put(FIELD_STATUS, Integer.valueOf(value.asInt()));
        } else if (memberName.equals(FIELD_CODE)) {
            this.mProperties.put(FIELD_CODE, value.asString());
        } else if (memberName.equals(FIELD_CONTEXT_INFO)) {
            ErrorContext mapObject = new ErrorContext();
            mapObject.createFromJson(value.asObject());
            this.mProperties.put(FIELD_CONTEXT_INFO, mapObject);
        } else if (memberName.equals(FIELD_HELP_URL)) {
            this.mProperties.put(FIELD_HELP_URL, value.asString());
        } else if (memberName.equals(FIELD_MESSAGE)) {
            this.mProperties.put(FIELD_MESSAGE, value.asString());
        } else if (memberName.equals(FIELD_REQUEST_ID)) {
            this.mProperties.put(FIELD_REQUEST_ID, value.asString());
        } else if (memberName.equals(FIELD_ERROR)) {
            this.mProperties.put(FIELD_ERROR, value.asString());
        } else if (memberName.equals(FIELD_ERROR_DESCRIPTION)) {
            this.mProperties.put(FIELD_ERROR_DESCRIPTION, value.asString());
        } else {
            super.parseJSONMember(member);
        }
    }
}
