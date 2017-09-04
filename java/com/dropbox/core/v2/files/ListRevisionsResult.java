package com.dropbox.core.v2.files;

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
import java.util.List;

public class ListRevisionsResult {
    protected final List<FileMetadata> entries;
    protected final boolean isDeleted;

    static class Serializer extends StructSerializer<ListRevisionsResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListRevisionsResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("is_deleted");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.isDeleted), g);
            g.writeFieldName(BoxList.FIELD_ENTRIES);
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.entries, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListRevisionsResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Boolean f_isDeleted = null;
                List<FileMetadata> f_entries = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("is_deleted".equals(field)) {
                        f_isDeleted = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if (BoxList.FIELD_ENTRIES.equals(field)) {
                        f_entries = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_isDeleted == null) {
                    throw new JsonParseException(p, "Required field \"is_deleted\" missing.");
                } else if (f_entries == null) {
                    throw new JsonParseException(p, "Required field \"entries\" missing.");
                } else {
                    ListRevisionsResult value = new ListRevisionsResult(f_isDeleted.booleanValue(), f_entries);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListRevisionsResult(boolean isDeleted, List<FileMetadata> entries) {
        this.isDeleted = isDeleted;
        if (entries == null) {
            throw new IllegalArgumentException("Required value for 'entries' is null");
        }
        for (FileMetadata x : entries) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'entries' is null");
            }
        }
        this.entries = entries;
    }

    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    public List<FileMetadata> getEntries() {
        return this.entries;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Boolean.valueOf(this.isDeleted), this.entries});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListRevisionsResult other = (ListRevisionsResult) obj;
        if (this.isDeleted == other.isDeleted && (this.entries == other.entries || this.entries.equals(other.entries))) {
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
