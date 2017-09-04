package com.dropbox.core.v2.sharing;

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

public class ListFilesResult {
    protected final String cursor;
    protected final List<SharedFileMetadata> entries;

    static class Serializer extends StructSerializer<ListFilesResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListFilesResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxList.FIELD_ENTRIES);
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.entries, g);
            if (value.cursor != null) {
                g.writeFieldName("cursor");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.cursor, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListFilesResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<SharedFileMetadata> f_entries = null;
                String f_cursor = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxList.FIELD_ENTRIES.equals(field)) {
                        f_entries = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else if ("cursor".equals(field)) {
                        f_cursor = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_entries == null) {
                    throw new JsonParseException(p, "Required field \"entries\" missing.");
                }
                ListFilesResult value = new ListFilesResult(f_entries, f_cursor);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListFilesResult(List<SharedFileMetadata> entries, String cursor) {
        if (entries == null) {
            throw new IllegalArgumentException("Required value for 'entries' is null");
        }
        for (SharedFileMetadata x : entries) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'entries' is null");
            }
        }
        this.entries = entries;
        this.cursor = cursor;
    }

    public ListFilesResult(List<SharedFileMetadata> entries) {
        this(entries, null);
    }

    public List<SharedFileMetadata> getEntries() {
        return this.entries;
    }

    public String getCursor() {
        return this.cursor;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.entries, this.cursor});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListFilesResult other = (ListFilesResult) obj;
        if (this.entries == other.entries || this.entries.equals(other.entries)) {
            if (this.cursor == other.cursor) {
                return true;
            }
            if (this.cursor != null && this.cursor.equals(other.cursor)) {
                return true;
            }
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
