package com.dropbox.core.v2.files;

import com.box.androidsdk.content.BoxApiMetadata;
import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.util.LangUtil;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class GetCopyReferenceResult {
    protected final String copyReference;
    protected final Date expires;
    protected final Metadata metadata;

    static class Serializer extends StructSerializer<GetCopyReferenceResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GetCopyReferenceResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxApiMetadata.BOX_API_METADATA);
            Serializer.INSTANCE.serialize(value.metadata, g);
            g.writeFieldName("copy_reference");
            StoneSerializers.string().serialize(value.copyReference, g);
            g.writeFieldName("expires");
            StoneSerializers.timestamp().serialize(value.expires, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GetCopyReferenceResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Metadata f_metadata = null;
                String f_copyReference = null;
                Date f_expires = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxApiMetadata.BOX_API_METADATA.equals(field)) {
                        f_metadata = (Metadata) Serializer.INSTANCE.deserialize(p);
                    } else if ("copy_reference".equals(field)) {
                        f_copyReference = (String) StoneSerializers.string().deserialize(p);
                    } else if ("expires".equals(field)) {
                        f_expires = (Date) StoneSerializers.timestamp().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_metadata == null) {
                    throw new JsonParseException(p, "Required field \"metadata\" missing.");
                } else if (f_copyReference == null) {
                    throw new JsonParseException(p, "Required field \"copy_reference\" missing.");
                } else if (f_expires == null) {
                    throw new JsonParseException(p, "Required field \"expires\" missing.");
                } else {
                    GetCopyReferenceResult value = new GetCopyReferenceResult(f_metadata, f_copyReference, f_expires);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GetCopyReferenceResult(Metadata metadata, String copyReference, Date expires) {
        if (metadata == null) {
            throw new IllegalArgumentException("Required value for 'metadata' is null");
        }
        this.metadata = metadata;
        if (copyReference == null) {
            throw new IllegalArgumentException("Required value for 'copyReference' is null");
        }
        this.copyReference = copyReference;
        if (expires == null) {
            throw new IllegalArgumentException("Required value for 'expires' is null");
        }
        this.expires = LangUtil.truncateMillis(expires);
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public String getCopyReference() {
        return this.copyReference;
    }

    public Date getExpires() {
        return this.expires;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.metadata, this.copyReference, this.expires});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GetCopyReferenceResult other = (GetCopyReferenceResult) obj;
        if ((this.metadata == other.metadata || this.metadata.equals(other.metadata)) && ((this.copyReference == other.copyReference || this.copyReference.equals(other.copyReference)) && (this.expires == other.expires || this.expires.equals(other.expires)))) {
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
