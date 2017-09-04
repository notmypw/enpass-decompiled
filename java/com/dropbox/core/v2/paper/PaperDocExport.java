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

class PaperDocExport extends RefPaperDoc {
    protected final ExportFormat exportFormat;

    static class Serializer extends StructSerializer<PaperDocExport> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(PaperDocExport value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("doc_id");
            StoneSerializers.string().serialize(value.docId, g);
            g.writeFieldName("export_format");
            Serializer.INSTANCE.serialize(value.exportFormat, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public PaperDocExport deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_docId = null;
                ExportFormat f_exportFormat = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("doc_id".equals(field)) {
                        f_docId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("export_format".equals(field)) {
                        f_exportFormat = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_docId == null) {
                    throw new JsonParseException(p, "Required field \"doc_id\" missing.");
                } else if (f_exportFormat == null) {
                    throw new JsonParseException(p, "Required field \"export_format\" missing.");
                } else {
                    PaperDocExport value = new PaperDocExport(f_docId, f_exportFormat);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public PaperDocExport(String docId, ExportFormat exportFormat) {
        super(docId);
        if (exportFormat == null) {
            throw new IllegalArgumentException("Required value for 'exportFormat' is null");
        }
        this.exportFormat = exportFormat;
    }

    public String getDocId() {
        return this.docId;
    }

    public ExportFormat getExportFormat() {
        return this.exportFormat;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.exportFormat}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        PaperDocExport other = (PaperDocExport) obj;
        if ((this.docId == other.docId || this.docId.equals(other.docId)) && (this.exportFormat == other.exportFormat || this.exportFormat.equals(other.exportFormat))) {
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
