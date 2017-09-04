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

class UploadSessionStartArg {
    protected final boolean close;

    static class Serializer extends StructSerializer<UploadSessionStartArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UploadSessionStartArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("close");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.close), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public UploadSessionStartArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Boolean f_close = Boolean.valueOf(false);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("close".equals(field)) {
                        f_close = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                UploadSessionStartArg value = new UploadSessionStartArg(f_close.booleanValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public UploadSessionStartArg(boolean close) {
        this.close = close;
    }

    public UploadSessionStartArg() {
        this(false);
    }

    public boolean getClose() {
        return this.close;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Boolean.valueOf(this.close)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        if (this.close != ((UploadSessionStartArg) obj).close) {
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
