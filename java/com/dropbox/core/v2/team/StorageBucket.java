package com.dropbox.core.v2.team;

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

public class StorageBucket {
    protected final String bucket;
    protected final long users;

    static class Serializer extends StructSerializer<StorageBucket> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(StorageBucket value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("bucket");
            StoneSerializers.string().serialize(value.bucket, g);
            g.writeFieldName("users");
            StoneSerializers.uInt64().serialize(Long.valueOf(value.users), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public StorageBucket deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_bucket = null;
                Long f_users = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("bucket".equals(field)) {
                        f_bucket = (String) StoneSerializers.string().deserialize(p);
                    } else if ("users".equals(field)) {
                        f_users = (Long) StoneSerializers.uInt64().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_bucket == null) {
                    throw new JsonParseException(p, "Required field \"bucket\" missing.");
                } else if (f_users == null) {
                    throw new JsonParseException(p, "Required field \"users\" missing.");
                } else {
                    StorageBucket value = new StorageBucket(f_bucket, f_users.longValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public StorageBucket(String bucket, long users) {
        if (bucket == null) {
            throw new IllegalArgumentException("Required value for 'bucket' is null");
        }
        this.bucket = bucket;
        this.users = users;
    }

    public String getBucket() {
        return this.bucket;
    }

    public long getUsers() {
        return this.users;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.bucket, Long.valueOf(this.users)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        StorageBucket other = (StorageBucket) obj;
        if ((this.bucket == other.bucket || this.bucket.equals(other.bucket)) && this.users == other.users) {
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
