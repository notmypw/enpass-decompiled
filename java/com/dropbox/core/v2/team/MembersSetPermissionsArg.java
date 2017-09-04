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

class MembersSetPermissionsArg {
    protected final AdminTier newRole;
    protected final UserSelectorArg user;

    static class Serializer extends StructSerializer<MembersSetPermissionsArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(MembersSetPermissionsArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxUser.TYPE);
            Serializer.INSTANCE.serialize(value.user, g);
            g.writeFieldName("new_role");
            Serializer.INSTANCE.serialize(value.newRole, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public MembersSetPermissionsArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                UserSelectorArg f_user = null;
                AdminTier f_newRole = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxUser.TYPE.equals(field)) {
                        f_user = Serializer.INSTANCE.deserialize(p);
                    } else if ("new_role".equals(field)) {
                        f_newRole = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_user == null) {
                    throw new JsonParseException(p, "Required field \"user\" missing.");
                } else if (f_newRole == null) {
                    throw new JsonParseException(p, "Required field \"new_role\" missing.");
                } else {
                    MembersSetPermissionsArg value = new MembersSetPermissionsArg(f_user, f_newRole);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public MembersSetPermissionsArg(UserSelectorArg user, AdminTier newRole) {
        if (user == null) {
            throw new IllegalArgumentException("Required value for 'user' is null");
        }
        this.user = user;
        if (newRole == null) {
            throw new IllegalArgumentException("Required value for 'newRole' is null");
        }
        this.newRole = newRole;
    }

    public UserSelectorArg getUser() {
        return this.user;
    }

    public AdminTier getNewRole() {
        return this.newRole;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.user, this.newRole});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        MembersSetPermissionsArg other = (MembersSetPermissionsArg) obj;
        if ((this.user == other.user || this.user.equals(other.user)) && (this.newRole == other.newRole || this.newRole.equals(other.newRole))) {
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
