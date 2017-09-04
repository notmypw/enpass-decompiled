package com.dropbox.core.v2.team;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StructSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;

public class GroupMemberInfo {
    protected final GroupAccessType accessType;
    protected final MemberProfile profile;

    static class Serializer extends StructSerializer<GroupMemberInfo> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GroupMemberInfo value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("profile");
            Serializer.INSTANCE.serialize(value.profile, g);
            g.writeFieldName("access_type");
            Serializer.INSTANCE.serialize(value.accessType, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GroupMemberInfo deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                MemberProfile f_profile = null;
                GroupAccessType f_accessType = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("profile".equals(field)) {
                        f_profile = (MemberProfile) Serializer.INSTANCE.deserialize(p);
                    } else if ("access_type".equals(field)) {
                        f_accessType = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_profile == null) {
                    throw new JsonParseException(p, "Required field \"profile\" missing.");
                } else if (f_accessType == null) {
                    throw new JsonParseException(p, "Required field \"access_type\" missing.");
                } else {
                    GroupMemberInfo value = new GroupMemberInfo(f_profile, f_accessType);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GroupMemberInfo(MemberProfile profile, GroupAccessType accessType) {
        if (profile == null) {
            throw new IllegalArgumentException("Required value for 'profile' is null");
        }
        this.profile = profile;
        if (accessType == null) {
            throw new IllegalArgumentException("Required value for 'accessType' is null");
        }
        this.accessType = accessType;
    }

    public MemberProfile getProfile() {
        return this.profile;
    }

    public GroupAccessType getAccessType() {
        return this.accessType;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.profile, this.accessType});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GroupMemberInfo other = (GroupMemberInfo) obj;
        if ((this.profile == other.profile || this.profile.equals(other.profile)) && (this.accessType == other.accessType || this.accessType.equals(other.accessType))) {
            return true;
        }
        return false;
    }

    public String toString() {
        return Serializer.INSTANCE.serialize((Object) this, false);
    }

    public String toStringMultiline() {
        return Serializer.INSTANCE.serialize((Object) this, true);
    }
}
