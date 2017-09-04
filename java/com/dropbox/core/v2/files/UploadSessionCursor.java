package com.dropbox.core.v2.files;

import com.box.androidsdk.content.models.BoxEvent;
import com.box.androidsdk.content.models.BoxList;
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

public class UploadSessionCursor {
    protected final long offset;
    protected final String sessionId;

    static class Serializer extends StructSerializer<UploadSessionCursor> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UploadSessionCursor value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxEvent.FIELD_SESSION_ID);
            StoneSerializers.string().serialize(value.sessionId, g);
            g.writeFieldName(BoxList.FIELD_OFFSET);
            StoneSerializers.uInt64().serialize(Long.valueOf(value.offset), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public UploadSessionCursor deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_sessionId = null;
                Long f_offset = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxEvent.FIELD_SESSION_ID.equals(field)) {
                        f_sessionId = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxList.FIELD_OFFSET.equals(field)) {
                        f_offset = (Long) StoneSerializers.uInt64().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_sessionId == null) {
                    throw new JsonParseException(p, "Required field \"session_id\" missing.");
                } else if (f_offset == null) {
                    throw new JsonParseException(p, "Required field \"offset\" missing.");
                } else {
                    UploadSessionCursor value = new UploadSessionCursor(f_sessionId, f_offset.longValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public UploadSessionCursor(String sessionId, long offset) {
        if (sessionId == null) {
            throw new IllegalArgumentException("Required value for 'sessionId' is null");
        }
        this.sessionId = sessionId;
        this.offset = offset;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public long getOffset() {
        return this.offset;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.sessionId, Long.valueOf(this.offset)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        UploadSessionCursor other = (UploadSessionCursor) obj;
        if ((this.sessionId == other.sessionId || this.sessionId.equals(other.sessionId)) && this.offset == other.offset) {
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
