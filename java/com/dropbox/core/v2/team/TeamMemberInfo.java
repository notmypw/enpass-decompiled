package com.dropbox.core.v2.team;

import com.box.androidsdk.content.models.BoxUser;
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

public class TeamMemberInfo {
    protected final TeamMemberProfile profile;
    protected final AdminTier role;

    static class Serializer extends StructSerializer<TeamMemberInfo> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(TeamMemberInfo value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("profile");
            Serializer.INSTANCE.serialize(value.profile, g);
            g.writeFieldName(BoxUser.FIELD_ROLE);
            Serializer.INSTANCE.serialize(value.role, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public TeamMemberInfo deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                TeamMemberProfile f_profile = null;
                AdminTier f_role = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("profile".equals(field)) {
                        f_profile = (TeamMemberProfile) Serializer.INSTANCE.deserialize(p);
                    } else if (BoxUser.FIELD_ROLE.equals(field)) {
                        f_role = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_profile == null) {
                    throw new JsonParseException(p, "Required field \"profile\" missing.");
                } else if (f_role == null) {
                    throw new JsonParseException(p, "Required field \"role\" missing.");
                } else {
                    TeamMemberInfo value = new TeamMemberInfo(f_profile, f_role);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public TeamMemberInfo(TeamMemberProfile profile, AdminTier role) {
        if (profile == null) {
            throw new IllegalArgumentException("Required value for 'profile' is null");
        }
        this.profile = profile;
        if (role == null) {
            throw new IllegalArgumentException("Required value for 'role' is null");
        }
        this.role = role;
    }

    public TeamMemberProfile getProfile() {
        return this.profile;
    }

    public AdminTier getRole() {
        return this.role;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.profile, this.role});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        TeamMemberInfo other = (TeamMemberInfo) obj;
        if ((this.profile == other.profile || this.profile.equals(other.profile)) && (this.role == other.role || this.role.equals(other.role))) {
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
