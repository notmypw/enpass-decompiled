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
import java.util.Arrays;
import java.util.regex.Pattern;

class ChangeFileMemberAccessArgs {
    protected final AccessLevel accessLevel;
    protected final String file;
    protected final MemberSelector member;

    static class Serializer extends StructSerializer<ChangeFileMemberAccessArgs> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ChangeFileMemberAccessArgs value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
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

        public ChangeFileMemberAccessArgs deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
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
                    ChangeFileMemberAccessArgs value = new ChangeFileMemberAccessArgs(f_file, f_member, f_accessLevel);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ChangeFileMemberAccessArgs(String file, MemberSelector member, AccessLevel accessLevel) {
        if (file == null) {
            throw new IllegalArgumentException("Required value for 'file' is null");
        } else if (file.length() < 1) {
            throw new IllegalArgumentException("String 'file' is shorter than 1");
        } else if (Pattern.matches("((/|id:).*|nspath:[0-9]+:.*)|ns:[0-9]+(/.*)?", file)) {
            this.file = file;
            if (member == null) {
                throw new IllegalArgumentException("Required value for 'member' is null");
            }
            this.member = member;
            if (accessLevel == null) {
                throw new IllegalArgumentException("Required value for 'accessLevel' is null");
            }
            this.accessLevel = accessLevel;
        } else {
            throw new IllegalArgumentException("String 'file' does not match pattern");
        }
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
        return Arrays.hashCode(new Object[]{this.file, this.member, this.accessLevel});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ChangeFileMemberAccessArgs other = (ChangeFileMemberAccessArgs) obj;
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
