package com.dropbox.core.v2.paper;

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

class PaperDocSharingPolicy extends RefPaperDoc {
    protected final SharingPolicy sharingPolicy;

    static class Serializer extends StructSerializer<PaperDocSharingPolicy> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(PaperDocSharingPolicy value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("doc_id");
            StoneSerializers.string().serialize(value.docId, g);
            g.writeFieldName("sharing_policy");
            Serializer.INSTANCE.serialize(value.sharingPolicy, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public PaperDocSharingPolicy deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_docId = null;
                SharingPolicy f_sharingPolicy = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("doc_id".equals(field)) {
                        f_docId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("sharing_policy".equals(field)) {
                        f_sharingPolicy = (SharingPolicy) Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_docId == null) {
                    throw new JsonParseException(p, "Required field \"doc_id\" missing.");
                } else if (f_sharingPolicy == null) {
                    throw new JsonParseException(p, "Required field \"sharing_policy\" missing.");
                } else {
                    PaperDocSharingPolicy value = new PaperDocSharingPolicy(f_docId, f_sharingPolicy);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public PaperDocSharingPolicy(String docId, SharingPolicy sharingPolicy) {
        super(docId);
        if (sharingPolicy == null) {
            throw new IllegalArgumentException("Required value for 'sharingPolicy' is null");
        }
        this.sharingPolicy = sharingPolicy;
    }

    public String getDocId() {
        return this.docId;
    }

    public SharingPolicy getSharingPolicy() {
        return this.sharingPolicy;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.sharingPolicy}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        PaperDocSharingPolicy other = (PaperDocSharingPolicy) obj;
        if ((this.docId == other.docId || this.docId.equals(other.docId)) && (this.sharingPolicy == other.sharingPolicy || this.sharingPolicy.equals(other.sharingPolicy))) {
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
