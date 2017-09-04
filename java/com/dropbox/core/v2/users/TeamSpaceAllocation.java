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

public class TeamSpaceAllocation {
    protected final long allocated;
    protected final long used;

    static class Serializer extends StructSerializer<TeamSpaceAllocation> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(TeamSpaceAllocation value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("used");
            StoneSerializers.uInt64().serialize(Long.valueOf(value.used), g);
            g.writeFieldName("allocated");
            StoneSerializers.uInt64().serialize(Long.valueOf(value.allocated), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public TeamSpaceAllocation deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Long f_used = null;
                Long f_allocated = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("used".equals(field)) {
                        f_used = (Long) StoneSerializers.uInt64().deserialize(p);
                    } else if ("allocated".equals(field)) {
                        f_allocated = (Long) StoneSerializers.uInt64().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_used == null) {
                    throw new JsonParseException(p, "Required field \"used\" missing.");
                } else if (f_allocated == null) {
                    throw new JsonParseException(p, "Required field \"allocated\" missing.");
                } else {
                    TeamSpaceAllocation value = new TeamSpaceAllocation(f_used.longValue(), f_allocated.longValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public TeamSpaceAllocation(long used, long allocated) {
        this.used = used;
        this.allocated = allocated;
    }

    public long getUsed() {
        return this.used;
    }

    public long getAllocated() {
        return this.allocated;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.used), Long.valueOf(this.allocated)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        TeamSpaceAllocation other = (TeamSpaceAllocation) obj;
        if (this.used == other.used && this.allocated == other.allocated) {
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
