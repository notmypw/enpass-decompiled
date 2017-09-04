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

class RemoveFileMemberArg {
    protected final String file;
    protected final MemberSelector member;

    static class Serializer extends StructSerializer<RemoveFileMemberArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RemoveFileMemberArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxFile.TYPE);
            StoneSerializers.string().serialize(value.file, g);
            g.writeFieldName("member");
            com.dropbox.core.v2.sharing.MemberSelector.Serializer.INSTANCE.serialize(value.member, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public RemoveFileMemberArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_file = null;
                MemberSelector f_member = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxFile.TYPE.equals(field)) {
                        f_file = (String) StoneSerializers.string().deserialize(p);
                    } else if ("member".equals(field)) {
                        f_member = com.dropbox.core.v2.sharing.MemberSelector.Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_file == null) {
                    throw new JsonParseException(p, "Required field \"file\" missing.");
                } else if (f_member == null) {
                    throw new JsonParseException(p, "Required field \"member\" missing.");
                } else {
                    RemoveFileMemberArg value = new RemoveFileMemberArg(f_file, f_member);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public RemoveFileMemberArg(String file, MemberSelector member) {
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

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.file, this.member});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        RemoveFileMemberArg other = (RemoveFileMemberArg) obj;
        if ((this.file == other.file || this.file.equals(other.file)) && (this.member == other.member || this.member.equals(other.member))) {
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
