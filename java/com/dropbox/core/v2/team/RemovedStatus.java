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

public class RemovedStatus {
    protected final boolean isRecoverable;

    static class Serializer extends StructSerializer<RemovedStatus> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RemovedStatus value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("is_recoverable");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.isRecoverable), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public RemovedStatus deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Boolean f_isRecoverable = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("is_recoverable".equals(field)) {
                        f_isRecoverable = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_isRecoverable == null) {
                    throw new JsonParseException(p, "Required field \"is_recoverable\" missing.");
                }
                RemovedStatus value = new RemovedStatus(f_isRecoverable.booleanValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public RemovedStatus(boolean isRecoverable) {
        this.isRecoverable = isRecoverable;
    }

    public boolean getIsRecoverable() {
        return this.isRecoverable;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Boolean.valueOf(this.isRecoverable)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        if (this.isRecoverable != ((RemovedStatus) obj).isRecoverable) {
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
