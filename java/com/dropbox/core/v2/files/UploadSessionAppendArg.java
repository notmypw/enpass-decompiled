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

class UploadSessionAppendArg {
    protected final boolean close;
    protected final UploadSessionCursor cursor;

    static class Serializer extends StructSerializer<UploadSessionAppendArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UploadSessionAppendArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("cursor");
            Serializer.INSTANCE.serialize(value.cursor, g);
            g.writeFieldName("close");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.close), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public UploadSessionAppendArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                UploadSessionCursor f_cursor = null;
                Boolean f_close = Boolean.valueOf(false);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("cursor".equals(field)) {
                        f_cursor = (UploadSessionCursor) Serializer.INSTANCE.deserialize(p);
                    } else if ("close".equals(field)) {
                        f_close = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_cursor == null) {
                    throw new JsonParseException(p, "Required field \"cursor\" missing.");
                }
                UploadSessionAppendArg value = new UploadSessionAppendArg(f_cursor, f_close.booleanValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public UploadSessionAppendArg(UploadSessionCursor cursor, boolean close) {
        if (cursor == null) {
            throw new IllegalArgumentException("Required value for 'cursor' is null");
        }
        this.cursor = cursor;
        this.close = close;
    }

    public UploadSessionAppendArg(UploadSessionCursor cursor) {
        this(cursor, false);
    }

    public UploadSessionCursor getCursor() {
        return this.cursor;
    }

    public boolean getClose() {
        return this.close;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.cursor, Boolean.valueOf(this.close)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        UploadSessionAppendArg other = (UploadSessionAppendArg) obj;
        if ((this.cursor == other.cursor || this.cursor.equals(other.cursor)) && this.close == other.close) {
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
