package com.box.androidsdk.content.models;

import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import java.util.List;
import java.util.Map;

public class BoxMetadata extends BoxJsonObject {
    static final /* synthetic */ boolean $assertionsDisabled = (!BoxMetadata.class.desiredAssertionStatus() ? true : $assertionsDisabled);
    public static final String FIELD_PARENT = "parent";
    public static final String FIELD_SCOPE = "scope";
    public static final String FIELD_TEMPLATE = "template";
    private List<String> mMetadataKeys;

    public BoxMetadata(Map<String, Object> JSONData) {
        super(JSONData);
    }

    public String getParent() {
        return (String) this.mProperties.get(FIELD_PARENT);
    }

    public String getTemplate() {
        return (String) this.mProperties.get(FIELD_TEMPLATE);
    }

    public String getScope() {
        return (String) this.mProperties.get(FIELD_SCOPE);
    }

    protected void parseJSONMember(Member member) {
        try {
            String memberName = member.getName();
            JsonValue value = member.getValue();
            if (memberName.equals(FIELD_PARENT)) {
                this.mProperties.put(FIELD_PARENT, value.asString());
            } else if (memberName.equals(FIELD_TEMPLATE)) {
                this.mProperties.put(FIELD_TEMPLATE, value.asString());
            } else if (memberName.equals(FIELD_SCOPE)) {
                this.mProperties.put(FIELD_SCOPE, value.asString());
            } else {
                if (!this.mMetadataKeys.contains(memberName)) {
                    this.mProperties.put(memberName, value.asString());
                    this.mMetadataKeys.add(memberName);
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
}
