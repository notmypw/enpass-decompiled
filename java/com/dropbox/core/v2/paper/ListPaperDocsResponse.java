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
import java.util.List;

public class ListPaperDocsResponse {
    protected final Cursor cursor;
    protected final List<String> docIds;
    protected final boolean hasMore;

    static class Serializer extends StructSerializer<ListPaperDocsResponse> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListPaperDocsResponse value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("doc_ids");
            StoneSerializers.list(StoneSerializers.string()).serialize(value.docIds, g);
            g.writeFieldName("cursor");
            Serializer.INSTANCE.serialize(value.cursor, g);
            g.writeFieldName("has_more");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.hasMore), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListPaperDocsResponse deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<String> f_docIds = null;
                Cursor f_cursor = null;
                Boolean f_hasMore = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("doc_ids".equals(field)) {
                        f_docIds = (List) StoneSerializers.list(StoneSerializers.string()).deserialize(p);
                    } else if ("cursor".equals(field)) {
                        f_cursor = (Cursor) Serializer.INSTANCE.deserialize(p);
                    } else if ("has_more".equals(field)) {
                        f_hasMore = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_docIds == null) {
                    throw new JsonParseException(p, "Required field \"doc_ids\" missing.");
                } else if (f_cursor == null) {
                    throw new JsonParseException(p, "Required field \"cursor\" missing.");
                } else if (f_hasMore == null) {
                    throw new JsonParseException(p, "Required field \"has_more\" missing.");
                } else {
                    ListPaperDocsResponse value = new ListPaperDocsResponse(f_docIds, f_cursor, f_hasMore.booleanValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListPaperDocsResponse(List<String> docIds, Cursor cursor, boolean hasMore) {
        if (docIds == null) {
            throw new IllegalArgumentException("Required value for 'docIds' is null");
        }
        for (String x : docIds) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'docIds' is null");
            }
        }
        this.docIds = docIds;
        if (cursor == null) {
            throw new IllegalArgumentException("Required value for 'cursor' is null");
        }
        this.cursor = cursor;
        this.hasMore = hasMore;
    }

    public List<String> getDocIds() {
        return this.docIds;
    }

    public Cursor getCursor() {
        return this.cursor;
    }

    public boolean getHasMore() {
        return this.hasMore;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.docIds, this.cursor, Boolean.valueOf(this.hasMore)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListPaperDocsResponse other = (ListPaperDocsResponse) obj;
        if ((this.docIds == other.docIds || this.docIds.equals(other.docIds)) && ((this.cursor == other.cursor || this.cursor.equals(other.cursor)) && this.hasMore == other.hasMore)) {
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
