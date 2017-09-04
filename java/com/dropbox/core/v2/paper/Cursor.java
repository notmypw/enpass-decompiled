package com.dropbox.core.v2.paper;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.util.LangUtil;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class Cursor {
    protected final Date expiration;
    protected final String value;

    static class Serializer extends StructSerializer<Cursor> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(Cursor value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxMetadataUpdateTask.VALUE);
            StoneSerializers.string().serialize(value.value, g);
            if (value.expiration != null) {
                g.writeFieldName("expiration");
                StoneSerializers.nullable(StoneSerializers.timestamp()).serialize(value.expiration, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public Cursor deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_value = null;
                Date f_expiration = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxMetadataUpdateTask.VALUE.equals(field)) {
                        f_value = (String) StoneSerializers.string().deserialize(p);
                    } else if ("expiration".equals(field)) {
                        f_expiration = (Date) StoneSerializers.nullable(StoneSerializers.timestamp()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_value == null) {
                    throw new JsonParseException(p, "Required field \"value\" missing.");
                }
                Cursor value = new Cursor(f_value, f_expiration);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public Cursor(String value, Date expiration) {
        if (value == null) {
            throw new IllegalArgumentException("Required value for 'value' is null");
        }
        this.value = value;
        this.expiration = LangUtil.truncateMillis(expiration);
    }

    public Cursor(String value) {
        this(value, null);
    }

    public String getValue() {
        return this.value;
    }

    public Date getExpiration() {
        return this.expiration;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.value, this.expiration});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        Cursor other = (Cursor) obj;
        if (this.value == other.value || this.value.equals(other.value)) {
            if (this.expiration == other.expiration) {
                return true;
            }
            if (this.expiration != null && this.expiration.equals(other.expiration)) {
                return true;
            }
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
