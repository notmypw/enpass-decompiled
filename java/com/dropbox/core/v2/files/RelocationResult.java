package com.dropbox.core.v2.files;

import com.box.androidsdk.content.BoxApiMetadata;
import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StructSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;

public class RelocationResult {
    protected final Metadata metadata;

    static class Serializer extends StructSerializer<RelocationResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RelocationResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxApiMetadata.BOX_API_METADATA);
            Serializer.INSTANCE.serialize(value.metadata, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public RelocationResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Metadata f_metadata = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxApiMetadata.BOX_API_METADATA.equals(field)) {
                        f_metadata = (Metadata) Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_metadata == null) {
                    throw new JsonParseException(p, "Required field \"metadata\" missing.");
                }
                RelocationResult value = new RelocationResult(f_metadata);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public RelocationResult(Metadata metadata) {
        if (metadata == null) {
            throw new IllegalArgumentException("Required value for 'metadata' is null");
        }
        this.metadata = metadata;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.metadata});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        RelocationResult other = (RelocationResult) obj;
        if (this.metadata == other.metadata || this.metadata.equals(other.metadata)) {
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
