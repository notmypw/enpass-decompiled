package com.dropbox.core.v2.paper;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.v2.sharing.MemberSelector;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;

public class AddMember {
    protected final MemberSelector member;
    protected final PaperDocPermissionLevel permissionLevel;

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
            g.writeFieldName("permission_level");
            Serializer.INSTANCE.serialize(value.permissionLevel, g);
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
                PaperDocPermissionLevel f_permissionLevel = PaperDocPermissionLevel.EDIT;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("member".equals(field)) {
                        f_member = com.dropbox.core.v2.sharing.MemberSelector.Serializer.INSTANCE.deserialize(p);
                    } else if ("permission_level".equals(field)) {
                        f_permissionLevel = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_member == null) {
                    throw new JsonParseException(p, "Required field \"member\" missing.");
                }
                AddMember value = new AddMember(f_member, f_permissionLevel);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public AddMember(MemberSelector member, PaperDocPermissionLevel permissionLevel) {
        if (permissionLevel == null) {
            throw new IllegalArgumentException("Required value for 'permissionLevel' is null");
        }
        this.permissionLevel = permissionLevel;
        if (member == null) {
            throw new IllegalArgumentException("Required value for 'member' is null");
        }
        this.member = member;
    }

    public AddMember(MemberSelector member) {
        this(member, PaperDocPermissionLevel.EDIT);
    }

    public MemberSelector getMember() {
        return this.member;
    }

    public PaperDocPermissionLevel getPermissionLevel() {
        return this.permissionLevel;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.permissionLevel, this.member});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        AddMember other = (AddMember) obj;
        if ((this.member == other.member || this.member.equals(other.member)) && (this.permissionLevel == other.permissionLevel || this.permissionLevel.equals(other.permissionLevel))) {
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
