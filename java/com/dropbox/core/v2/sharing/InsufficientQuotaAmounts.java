package com.dropbox.core.v2.sharing;

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

public class InsufficientQuotaAmounts {
    protected final long spaceLeft;
    protected final long spaceNeeded;
    protected final long spaceShortage;

    static class Serializer extends StructSerializer<InsufficientQuotaAmounts> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(InsufficientQuotaAmounts value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("space_needed");
            StoneSerializers.uInt64().serialize(Long.valueOf(value.spaceNeeded), g);
            g.writeFieldName("space_shortage");
            StoneSerializers.uInt64().serialize(Long.valueOf(value.spaceShortage), g);
            g.writeFieldName("space_left");
            StoneSerializers.uInt64().serialize(Long.valueOf(value.spaceLeft), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public InsufficientQuotaAmounts deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Long f_spaceNeeded = null;
                Long f_spaceShortage = null;
                Long f_spaceLeft = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("space_needed".equals(field)) {
                        f_spaceNeeded = (Long) StoneSerializers.uInt64().deserialize(p);
                    } else if ("space_shortage".equals(field)) {
                        f_spaceShortage = (Long) StoneSerializers.uInt64().deserialize(p);
                    } else if ("space_left".equals(field)) {
                        f_spaceLeft = (Long) StoneSerializers.uInt64().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_spaceNeeded == null) {
                    throw new JsonParseException(p, "Required field \"space_needed\" missing.");
                } else if (f_spaceShortage == null) {
                    throw new JsonParseException(p, "Required field \"space_shortage\" missing.");
                } else if (f_spaceLeft == null) {
                    throw new JsonParseException(p, "Required field \"space_left\" missing.");
                } else {
                    InsufficientQuotaAmounts value = new InsufficientQuotaAmounts(f_spaceNeeded.longValue(), f_spaceShortage.longValue(), f_spaceLeft.longValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public InsufficientQuotaAmounts(long spaceNeeded, long spaceShortage, long spaceLeft) {
        this.spaceNeeded = spaceNeeded;
        this.spaceShortage = spaceShortage;
        this.spaceLeft = spaceLeft;
    }

    public long getSpaceNeeded() {
        return this.spaceNeeded;
    }

    public long getSpaceShortage() {
        return this.spaceShortage;
    }

    public long getSpaceLeft() {
        return this.spaceLeft;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.spaceNeeded), Long.valueOf(this.spaceShortage), Long.valueOf(this.spaceLeft)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        InsufficientQuotaAmounts other = (InsufficientQuotaAmounts) obj;
        if (this.spaceNeeded == other.spaceNeeded && this.spaceShortage == other.spaceShortage && this.spaceLeft == other.spaceLeft) {
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
