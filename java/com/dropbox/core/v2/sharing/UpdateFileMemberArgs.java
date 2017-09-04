package com.dropbox.core.v2.sharing;

import com.box.androidsdk.content.models.BoxFile;
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

class UpdateFileMemberArgs extends ChangeFileMemberAccessArgs {

    static class Serializer extends StructSerializer<UpdateFileMemberArgs> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UpdateFileMemberArgs value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxFile.TYPE);
            StoneSerializers.string().serialize(value.file, g);
            g.writeFieldName("member");
            com.dropbox.core.v2.sharing.MemberSelector.Serializer.INSTANCE.serialize(value.member, g);
            g.writeFieldName("access_level");
            Serializer.INSTANCE.serialize(value.accessLevel, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public UpdateFileMemberArgs deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_file = null;
                MemberSelector f_member = null;
                AccessLevel f_accessLevel = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxFile.TYPE.equals(field)) {
                        f_file = (String) StoneSerializers.string().deserialize(p);
                    } else if ("member".equals(field)) {
                        f_member = com.dropbox.core.v2.sharing.MemberSelector.Serializer.INSTANCE.deserialize(p);
                    } else if ("access_level".equals(field)) {
                        f_accessLevel = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_file == null) {
                    throw new JsonParseException(p, "Required field \"file\" missing.");
                } else if (f_member == null) {
                    throw new JsonParseException(p, "Required field \"member\" missing.");
                } else if (f_accessLevel == null) {
                    throw new JsonParseException(p, "Required field \"access_level\" missing.");
                } else {
                    UpdateFileMemberArgs value = new UpdateFileMemberArgs(f_file, f_member, f_accessLevel);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public UpdateFileMemberArgs(String file, MemberSelector member, AccessLevel accessLevel) {
        super(file, member, accessLevel);
    }

    public String getFile() {
        return this.file;
    }

    public MemberSelector getMember() {
        return this.member;
    }

    public AccessLevel getAccessLevel() {
        return this.accessLevel;
    }

    public int hashCode() {
        return getClass().toString().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        UpdateFileMemberArgs other = (UpdateFileMemberArgs) obj;
        if ((this.file == other.file || this.file.equals(other.file)) && ((this.member == other.member || this.member.equals(other.member)) && (this.accessLevel == other.accessLevel || this.accessLevel.equals(other.accessLevel)))) {
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
