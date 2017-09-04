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

public class FileMemberActionResult {
    protected final MemberSelector member;
    protected final FileMemberActionIndividualResult result;

    static class Serializer extends StructSerializer<FileMemberActionResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(FileMemberActionResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("member");
            com.dropbox.core.v2.sharing.MemberSelector.Serializer.INSTANCE.serialize(value.member, g);
            g.writeFieldName("result");
            Serializer.INSTANCE.serialize(value.result, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public FileMemberActionResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                MemberSelector f_member = null;
                FileMemberActionIndividualResult f_result = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("member".equals(field)) {
                        f_member = com.dropbox.core.v2.sharing.MemberSelector.Serializer.INSTANCE.deserialize(p);
                    } else if ("result".equals(field)) {
                        f_result = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_member == null) {
                    throw new JsonParseException(p, "Required field \"member\" missing.");
                } else if (f_result == null) {
                    throw new JsonParseException(p, "Required field \"result\" missing.");
                } else {
                    FileMemberActionResult value = new FileMemberActionResult(f_member, f_result);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public FileMemberActionResult(MemberSelector member, FileMemberActionIndividualResult result) {
        if (member == null) {
            throw new IllegalArgumentException("Required value for 'member' is null");
        }
        this.member = member;
        if (result == null) {
            throw new IllegalArgumentException("Required value for 'result' is null");
        }
        this.result = result;
    }

    public MemberSelector getMember() {
        return this.member;
    }

    public FileMemberActionIndividualResult getResult() {
        return this.result;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.member, this.result});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        FileMemberActionResult other = (FileMemberActionResult) obj;
        if ((this.member == other.member || this.member.equals(other.member)) && (this.result == other.result || this.result.equals(other.result))) {
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
