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

public class UploadWriteFailed {
    protected final WriteError reason;
    protected final String uploadSessionId;

    static class Serializer extends StructSerializer<UploadWriteFailed> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UploadWriteFailed value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("reason");
            Serializer.INSTANCE.serialize(value.reason, g);
            g.writeFieldName("upload_session_id");
            StoneSerializers.string().serialize(value.uploadSessionId, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public UploadWriteFailed deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                WriteError f_reason = null;
                String f_uploadSessionId = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("reason".equals(field)) {
                        f_reason = Serializer.INSTANCE.deserialize(p);
                    } else if ("upload_session_id".equals(field)) {
                        f_uploadSessionId = (String) StoneSerializers.string().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_reason == null) {
                    throw new JsonParseException(p, "Required field \"reason\" missing.");
                } else if (f_uploadSessionId == null) {
                    throw new JsonParseException(p, "Required field \"upload_session_id\" missing.");
                } else {
                    UploadWriteFailed value = new UploadWriteFailed(f_reason, f_uploadSessionId);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public UploadWriteFailed(WriteError reason, String uploadSessionId) {
        if (reason == null) {
            throw new IllegalArgumentException("Required value for 'reason' is null");
        }
        this.reason = reason;
        if (uploadSessionId == null) {
            throw new IllegalArgumentException("Required value for 'uploadSessionId' is null");
        }
        this.uploadSessionId = uploadSessionId;
    }

    public WriteError getReason() {
        return this.reason;
    }

    public String getUploadSessionId() {
        return this.uploadSessionId;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.reason, this.uploadSessionId});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        UploadWriteFailed other = (UploadWriteFailed) obj;
        if ((this.reason == other.reason || this.reason.equals(other.reason)) && (this.uploadSessionId == other.uploadSessionId || this.uploadSessionId.equals(other.uploadSessionId))) {
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
