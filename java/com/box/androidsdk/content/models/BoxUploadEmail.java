package com.box.androidsdk.content.models;

import android.text.TextUtils;
import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import java.util.Locale;
import java.util.Map;

public class BoxUploadEmail extends BoxJsonObject {
    public static final String FIELD_ACCESS = "access";
    public static final String FIELD_EMAIL = "email";
    private static final long serialVersionUID = -1707312180661448119L;

    public enum Access {
        OPEN("open"),
        COLLABORATORS("collaborators");
        
        private final String mValue;

        public static Access fromString(String text) {
            if (!TextUtils.isEmpty(text)) {
                for (Access e : values()) {
                    if (text.equalsIgnoreCase(e.toString())) {
                        return e;
                    }
                }
            }
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "No enum with text %s found", new Object[]{text}));
        }

        private Access(String value) {
            this.mValue = value;
        }

        public String toString() {
            return this.mValue;
        }
    }

    public BoxUploadEmail(Map<String, Object> map) {
        super(map);
    }

    public Access getAccess() {
        return (Access) this.mProperties.get(FIELD_ACCESS);
    }

    public String getEmail() {
        return (String) this.mProperties.get(FIELD_EMAIL);
    }

    protected void parseJSONMember(Member member) {
        JsonValue value = member.getValue();
        if (member.getName().equals(FIELD_ACCESS)) {
            this.mProperties.put(FIELD_ACCESS, Access.fromString(value.asString()));
        } else if (member.getName().equals(FIELD_EMAIL)) {
            this.mProperties.put(FIELD_EMAIL, value.asString());
        } else {
            super.parseJSONMember(member);
        }
    }
}
