package com.dropbox.core.v2.sharing;

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

public class AddMember {
    protected final AccessLevel accessLevel;
    protected final MemberSelector member;

    static class Serializer extends StructSerializer<AddMember> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(AddMember value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("member");
            com.dropbox.core.v2.sharing.MemberSelector.Serializer.INSTANCE.serialize(value.member, g);
            g.writeFieldName("access_level");
            Serializer.INSTANCE.serialize(value.accessLevel, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public AddMember deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                MemberSelector f_member = null;
                AccessLevel f_accessLevel = AccessLevel.VIEWER;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("member".equals(field)) {
                        f_member = com.dropbox.core.v2.sharing.MemberSelector.Serializer.INSTANCE.deserialize(p);
                    } else if ("access_level".equals(field)) {
                        f_accessLevel = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_member == null) {
                    throw new JsonParseException(p, "Required field \"member\" missing.");
                }
                AddMember value = new AddMember(f_member, f_accessLevel);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public AddMember(MemberSelector member, AccessLevel accessLevel) {
        if (member == null) {
            throw new IllegalArgumentException("Required value for 'member' is null");
        }
        this.member = member;
        if (accessLevel == null) {
            throw new IllegalArgumentException("Required value for 'accessLevel' is null");
        }
        this.accessLevel = accessLevel;
    }

    public AddMember(MemberSelector member) {
        this(member, AccessLevel.VIEWER);
    }

    public MemberSelector getMember() {
        return this.member;
    }

    public AccessLevel getAccessLevel() {
        return this.accessLevel;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.member, this.accessLevel});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        AddMember other = (AddMember) obj;
        if ((this.member == other.member || this.member.equals(other.member)) && (this.accessLevel == other.accessLevel || this.accessLevel.equals(other.accessLevel))) {
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
