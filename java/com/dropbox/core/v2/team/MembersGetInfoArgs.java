package com.dropbox.core.v2.team;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class MembersGetInfoArgs {
    protected final List<UserSelectorArg> members;

    static class Serializer extends StructSerializer<MembersGetInfoArgs> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(MembersGetInfoArgs value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("members");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.members, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public MembersGetInfoArgs deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<UserSelectorArg> f_members = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("members".equals(field)) {
                        f_members = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_members == null) {
                    throw new JsonParseException(p, "Required field \"members\" missing.");
                }
                MembersGetInfoArgs value = new MembersGetInfoArgs(f_members);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public MembersGetInfoArgs(List<UserSelectorArg> members) {
        if (members == null) {
            throw new IllegalArgumentException("Required value for 'members' is null");
        }
        for (UserSelectorArg x : members) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'members' is null");
            }
        }
        this.members = members;
    }

    public List<UserSelectorArg> getMembers() {
        return this.members;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.members});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        MembersGetInfoArgs other = (MembersGetInfoArgs) obj;
        if (this.members == other.members || this.members.equals(other.members)) {
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
