package com.dropbox.core.v2.files;

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

public class UploadSessionOffsetError {
    protected final long correctOffset;

    static class Serializer extends StructSerializer<UploadSessionOffsetError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UploadSessionOffsetError value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("correct_offset");
            StoneSerializers.uInt64().serialize(Long.valueOf(value.correctOffset), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public UploadSessionOffsetError deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Long f_correctOffset = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("correct_offset".equals(field)) {
                        f_correctOffset = (Long) StoneSerializers.uInt64().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_correctOffset == null) {
                    throw new JsonParseException(p, "Required field \"correct_offset\" missing.");
                }
                UploadSessionOffsetError value = new UploadSessionOffsetError(f_correctOffset.longValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public UploadSessionOffsetError(long correctOffset) {
        this.correctOffset = correctOffset;
    }

    public long getCorrectOffset() {
        return this.correctOffset;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.correctOffset)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        if (this.correctOffset != ((UploadSessionOffsetError) obj).correctOffset) {
            return false;
        }
        return true;
    }

    public String toString() {
        return Serializer.INSTANCE.serialize((Object) this, false);
    }

    public String toStringMultiline() {
        return Serializer.INSTANCE.serialize((Object) this, true);
    }
}
