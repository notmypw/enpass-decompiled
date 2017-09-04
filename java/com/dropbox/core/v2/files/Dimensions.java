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

public class Dimensions {
    protected final long height;
    protected final long width;

    static class Serializer extends StructSerializer<Dimensions> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(Dimensions value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("height");
            StoneSerializers.uInt64().serialize(Long.valueOf(value.height), g);
            g.writeFieldName("width");
            StoneSerializers.uInt64().serialize(Long.valueOf(value.width), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public Dimensions deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Long f_height = null;
                Long f_width = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("height".equals(field)) {
                        f_height = (Long) StoneSerializers.uInt64().deserialize(p);
                    } else if ("width".equals(field)) {
                        f_width = (Long) StoneSerializers.uInt64().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_height == null) {
                    throw new JsonParseException(p, "Required field \"height\" missing.");
                } else if (f_width == null) {
                    throw new JsonParseException(p, "Required field \"width\" missing.");
                } else {
                    Dimensions value = new Dimensions(f_height.longValue(), f_width.longValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public Dimensions(long height, long width) {
        this.height = height;
        this.width = width;
    }

    public long getHeight() {
        return this.height;
    }

    public long getWidth() {
        return this.width;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.height), Long.valueOf(this.width)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        Dimensions other = (Dimensions) obj;
        if (this.height == other.height && this.width == other.width) {
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
