package com.box.androidsdk.content.models;

import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;

public class BoxSimpleMessage extends BoxJsonObject {
    public static final String FIELD_MESSAGE = "message";
    public static final String MESSAGE_NEW_CHANGE = "new_change";
    public static final String MESSAGE_RECONNECT = "reconnect";
    private static final long serialVersionUID = 1626798809346520004L;

    protected void parseJSONMember(Member member) {
        JsonValue value = member.getValue();
        if (member.getName().equals(FIELD_MESSAGE)) {
            this.mProperties.put(FIELD_MESSAGE, value.asString());
        } else {
            super.parseJSONMember(member);
        }
    }

    public String getMessage() {
        return (String) this.mProperties.get(FIELD_MESSAGE);
    }
}
