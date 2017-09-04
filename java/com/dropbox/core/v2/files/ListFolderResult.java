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

public class ListFolderResult {
    protected final String cursor;
    protected final List<Metadata> entries;
    protected final boolean hasMore;

    static class Serializer extends StructSerializer<ListFolderResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListFolderResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxList.FIELD_ENTRIES);
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.entries, g);
            g.writeFieldName("cursor");
            StoneSerializers.string().serialize(value.cursor, g);
            g.writeFieldName("has_more");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.hasMore), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListFolderResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<Metadata> f_entries = null;
                String f_cursor = null;
                Boolean f_hasMore = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxList.FIELD_ENTRIES.equals(field)) {
                        f_entries = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else if ("cursor".equals(field)) {
                        f_cursor = (String) StoneSerializers.string().deserialize(p);
                    } else if ("has_more".equals(field)) {
                        f_hasMore = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_entries == null) {
                    throw new JsonParseException(p, "Required field \"entries\" missing.");
                } else if (f_cursor == null) {
                    throw new JsonParseException(p, "Required field \"cursor\" missing.");
                } else if (f_hasMore == null) {
                    throw new JsonParseException(p, "Required field \"has_more\" missing.");
                } else {
                    ListFolderResult value = new ListFolderResult(f_entries, f_cursor, f_hasMore.booleanValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListFolderResult(List<Metadata> entries, String cursor, boolean hasMore) {
        if (entries == null) {
            throw new IllegalArgumentException("Required value for 'entries' is null");
        }
        for (Metadata x : entries) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'entries' is null");
            }
        }
        this.entries = entries;
        if (cursor == null) {
            throw new IllegalArgumentException("Required value for 'cursor' is null");
        } else if (cursor.length() < 1) {
            throw new IllegalArgumentException("String 'cursor' is shorter than 1");
        } else {
            this.cursor = cursor;
            this.hasMore = hasMore;
        }
    }

    public List<Metadata> getEntries() {
        return this.entries;
    }

    public String getCursor() {
        return this.cursor;
    }

    public boolean getHasMore() {
        return this.hasMore;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.entries, this.cursor, Boolean.valueOf(this.hasMore)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListFolderResult other = (ListFolderResult) obj;
        if ((this.entries == other.entries || this.entries.equals(other.entries)) && ((this.cursor == other.cursor || this.cursor.equals(other.cursor)) && this.hasMore == other.hasMore)) {
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
