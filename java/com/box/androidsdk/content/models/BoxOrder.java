package com.box.androidsdk.content.models;

import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;

public class BoxOrder extends BoxJsonObject {
    public static final String FIELD_BY = "by";
    public static final String FIELD_DIRECTION = "direction";

    protected void parseJSONMember(Member member) {
        String memberName = member.getName();
        JsonValue value = member.getValue();
        if (memberName.equals(FIELD_BY)) {
            this.mProperties.put(FIELD_BY, value.asString());
        } else if (memberName.equals(FIELD_DIRECTION)) {
            this.mProperties.put(FIELD_DIRECTION, value.asString());
        } else {
            super.parseJSONMember(member);
        }
    }
}
