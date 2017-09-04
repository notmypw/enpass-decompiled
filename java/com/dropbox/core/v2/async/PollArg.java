package com.dropbox.core.v2.async;

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

public class PollArg {
    protected final String asyncJobId;

    public static class Serializer extends StructSerializer<PollArg> {
        public static final Serializer INSTANCE = new Serializer();

        public void serialize(PollArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("async_job_id");
            StoneSerializers.string().serialize(value.asyncJobId, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public PollArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_asyncJobId = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("async_job_id".equals(field)) {
                        f_asyncJobId = (String) StoneSerializers.string().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_asyncJobId == null) {
                    throw new JsonParseException(p, "Required field \"async_job_id\" missing.");
                }
                PollArg value = new PollArg(f_asyncJobId);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public PollArg(String asyncJobId) {
        if (asyncJobId == null) {
            throw new IllegalArgumentException("Required value for 'asyncJobId' is null");
        } else if (asyncJobId.length() < 1) {
            throw new IllegalArgumentException("String 'asyncJobId' is shorter than 1");
        } else {
            this.asyncJobId = asyncJobId;
        }
    }

    public String getAsyncJobId() {
        return this.asyncJobId;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.asyncJobId});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        PollArg other = (PollArg) obj;
        if (this.asyncJobId == other.asyncJobId || this.asyncJobId.equals(other.asyncJobId)) {
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
