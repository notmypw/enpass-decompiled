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
import in.sinew.enpass.DropboxRemote;
import java.io.IOException;
import java.util.Arrays;

public class PaperDocExportResult {
    protected final String mimeType;
    protected final String owner;
    protected final long revision;
    protected final String title;

    static class Serializer extends StructSerializer<PaperDocExportResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(PaperDocExportResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("owner");
            StoneSerializers.string().serialize(value.owner, g);
            g.writeFieldName("title");
            StoneSerializers.string().serialize(value.title, g);
            g.writeFieldName(DropboxRemote.ACCESS_LAST_REMOTE_REV);
            StoneSerializers.int64().serialize(Long.valueOf(value.revision), g);
            g.writeFieldName("mime_type");
            StoneSerializers.string().serialize(value.mimeType, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public PaperDocExportResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_owner = null;
                String f_title = null;
                Long f_revision = null;
                String f_mimeType = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("owner".equals(field)) {
                        f_owner = (String) StoneSerializers.string().deserialize(p);
                    } else if ("title".equals(field)) {
                        f_title = (String) StoneSerializers.string().deserialize(p);
                    } else if (DropboxRemote.ACCESS_LAST_REMOTE_REV.equals(field)) {
                        f_revision = (Long) StoneSerializers.int64().deserialize(p);
                    } else if ("mime_type".equals(field)) {
                        f_mimeType = (String) StoneSerializers.string().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_owner == null) {
                    throw new JsonParseException(p, "Required field \"owner\" missing.");
                } else if (f_title == null) {
                    throw new JsonParseException(p, "Required field \"title\" missing.");
                } else if (f_revision == null) {
                    throw new JsonParseException(p, "Required field \"revision\" missing.");
                } else if (f_mimeType == null) {
                    throw new JsonParseException(p, "Required field \"mime_type\" missing.");
                } else {
                    PaperDocExportResult value = new PaperDocExportResult(f_owner, f_title, f_revision.longValue(), f_mimeType);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public PaperDocExportResult(String owner, String title, long revision, String mimeType) {
        if (owner == null) {
            throw new IllegalArgumentException("Required value for 'owner' is null");
        }
        this.owner = owner;
        if (title == null) {
            throw new IllegalArgumentException("Required value for 'title' is null");
        }
        this.title = title;
        this.revision = revision;
        if (mimeType == null) {
            throw new IllegalArgumentException("Required value for 'mimeType' is null");
        }
        this.mimeType = mimeType;
    }

    public String getOwner() {
        return this.owner;
    }

    public String getTitle() {
        return this.title;
    }

    public long getRevision() {
        return this.revision;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.owner, this.title, Long.valueOf(this.revision), this.mimeType});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        PaperDocExportResult other = (PaperDocExportResult) obj;
        if ((this.owner == other.owner || this.owner.equals(other.owner)) && ((this.title == other.title || this.title.equals(other.title)) && this.revision == other.revision && (this.mimeType == other.mimeType || this.mimeType.equals(other.mimeType)))) {
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
