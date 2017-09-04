package com.dropbox.core.v2.users;

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

public class SpaceUsage {
    protected final SpaceAllocation allocation;
    protected final long used;

    static class Serializer extends StructSerializer<SpaceUsage> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(SpaceUsage value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("used");
            StoneSerializers.uInt64().serialize(Long.valueOf(value.used), g);
            g.writeFieldName("allocation");
            Serializer.INSTANCE.serialize(value.allocation, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public SpaceUsage deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Long f_used = null;
                SpaceAllocation f_allocation = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("used".equals(field)) {
                        f_used = (Long) StoneSerializers.uInt64().deserialize(p);
                    } else if ("allocation".equals(field)) {
                        f_allocation = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_used == null) {
                    throw new JsonParseException(p, "Required field \"used\" missing.");
                } else if (f_allocation == null) {
                    throw new JsonParseException(p, "Required field \"allocation\" missing.");
                } else {
                    SpaceUsage value = new SpaceUsage(f_used.longValue(), f_allocation);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public SpaceUsage(long used, SpaceAllocation allocation) {
        this.used = used;
        if (allocation == null) {
            throw new IllegalArgumentException("Required value for 'allocation' is null");
        }
        this.allocation = allocation;
    }

    public long getUsed() {
        return this.used;
    }

    public SpaceAllocation getAllocation() {
        return this.allocation;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.used), this.allocation});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        SpaceUsage other = (SpaceUsage) obj;
        if (this.used == other.used && (this.allocation == other.allocation || this.allocation.equals(other.allocation))) {
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
