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

public class ListFileMembersBatchResult {
    protected final String file;
    protected final ListFileMembersIndividualResult result;

    static class Serializer extends StructSerializer<ListFileMembersBatchResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListFileMembersBatchResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxFile.TYPE);
            StoneSerializers.string().serialize(value.file, g);
            g.writeFieldName("result");
            Serializer.INSTANCE.serialize(value.result, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListFileMembersBatchResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_file = null;
                ListFileMembersIndividualResult f_result = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxFile.TYPE.equals(field)) {
                        f_file = (String) StoneSerializers.string().deserialize(p);
                    } else if ("result".equals(field)) {
                        f_result = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_file == null) {
                    throw new JsonParseException(p, "Required field \"file\" missing.");
                } else if (f_result == null) {
                    throw new JsonParseException(p, "Required field \"result\" missing.");
                } else {
                    ListFileMembersBatchResult value = new ListFileMembersBatchResult(f_file, f_result);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListFileMembersBatchResult(String file, ListFileMembersIndividualResult result) {
        if (file == null) {
            throw new IllegalArgumentException("Required value for 'file' is null");
        } else if (file.length() < 1) {
            throw new IllegalArgumentException("String 'file' is shorter than 1");
        } else if (Pattern.matches("((/|id:).*|nspath:[0-9]+:.*)|ns:[0-9]+(/.*)?", file)) {
            this.file = file;
            if (result == null) {
                throw new IllegalArgumentException("Required value for 'result' is null");
            }
            this.result = result;
        } else {
            throw new IllegalArgumentException("String 'file' does not match pattern");
        }
    }

    public String getFile() {
        return this.file;
    }

    public ListFileMembersIndividualResult getResult() {
        return this.result;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.file, this.result});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListFileMembersBatchResult other = (ListFileMembersBatchResult) obj;
        if ((this.file == other.file || this.file.equals(other.file)) && (this.result == other.result || this.result.equals(other.result))) {
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
