package com.dropbox.core.v2.files;

import com.box.androidsdk.content.BoxApiMetadata;
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

public class GetTemporaryLinkResult {
    protected final String link;
    protected final FileMetadata metadata;

    static class Serializer extends StructSerializer<GetTemporaryLinkResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GetTemporaryLinkResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxApiMetadata.BOX_API_METADATA);
            Serializer.INSTANCE.serialize(value.metadata, g);
            g.writeFieldName("link");
            StoneSerializers.string().serialize(value.link, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GetTemporaryLinkResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                FileMetadata f_metadata = null;
                String f_link = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxApiMetadata.BOX_API_METADATA.equals(field)) {
                        f_metadata = (FileMetadata) Serializer.INSTANCE.deserialize(p);
                    } else if ("link".equals(field)) {
                        f_link = (String) StoneSerializers.string().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_metadata == null) {
                    throw new JsonParseException(p, "Required field \"metadata\" missing.");
                } else if (f_link == null) {
                    throw new JsonParseException(p, "Required field \"link\" missing.");
                } else {
                    GetTemporaryLinkResult value = new GetTemporaryLinkResult(f_metadata, f_link);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GetTemporaryLinkResult(FileMetadata metadata, String link) {
        if (metadata == null) {
            throw new IllegalArgumentException("Required value for 'metadata' is null");
        }
        this.metadata = metadata;
        if (link == null) {
            throw new IllegalArgumentException("Required value for 'link' is null");
        }
        this.link = link;
    }

    public FileMetadata getMetadata() {
        return this.metadata;
    }

    public String getLink() {
        return this.link;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.metadata, this.link});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GetTemporaryLinkResult other = (GetTemporaryLinkResult) obj;
        if ((this.metadata == other.metadata || this.metadata.equals(other.metadata)) && (this.link == other.link || this.link.equals(other.link))) {
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
