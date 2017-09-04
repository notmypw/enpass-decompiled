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

public class IndividualSpaceAllocation {
    protected final long allocated;

    static class Serializer extends StructSerializer<IndividualSpaceAllocation> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(IndividualSpaceAllocation value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("allocated");
            StoneSerializers.uInt64().serialize(Long.valueOf(value.allocated), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public IndividualSpaceAllocation deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Long f_allocated = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("allocated".equals(field)) {
                        f_allocated = (Long) StoneSerializers.uInt64().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_allocated == null) {
                    throw new JsonParseException(p, "Required field \"allocated\" missing.");
                }
                IndividualSpaceAllocation value = new IndividualSpaceAllocation(f_allocated.longValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public IndividualSpaceAllocation(long allocated) {
        this.allocated = allocated;
    }

    public long getAllocated() {
        return this.allocated;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.allocated)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        if (this.allocated != ((IndividualSpaceAllocation) obj).allocated) {
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
