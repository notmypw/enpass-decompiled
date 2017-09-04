package com.box.androidsdk.content.models;

import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import java.util.Map;

public class BoxCollection extends BoxEntity {
    public static final String FIELD_COLLECTION_TYPE = "collection_type";
    public static final String FIELD_NAME = "name";
    public static final String TYPE = "collection";

    public BoxCollection(Map<String, Object> map) {
        super(map);
    }

    public String getName() {
        return (String) this.mProperties.get(FIELD_NAME);
    }

    public String getCollectionType() {
        return (String) this.mProperties.get(FIELD_COLLECTION_TYPE);
    }

    protected void parseJSONMember(Member member) {
        String memberName = member.getName();
        JsonValue value = member.getValue();
        if (memberName.equals(FIELD_NAME)) {
            this.mProperties.put(FIELD_NAME, value.asString());
        } else if (memberName.equals(FIELD_COLLECTION_TYPE)) {
            this.mProperties.put(FIELD_COLLECTION_TYPE, value.asString());
        } else {
            super.parseJSONMember(member);
        }
    }
}
