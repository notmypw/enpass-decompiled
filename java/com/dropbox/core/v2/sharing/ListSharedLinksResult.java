package com.dropbox.core.v2.sharing;

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

public class ListSharedLinksResult {
    protected final String cursor;
    protected final boolean hasMore;
    protected final List<SharedLinkMetadata> links;

    static class Serializer extends StructSerializer<ListSharedLinksResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListSharedLinksResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("links");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.links, g);
            g.writeFieldName("has_more");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.hasMore), g);
            if (value.cursor != null) {
                g.writeFieldName("cursor");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.cursor, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListSharedLinksResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<SharedLinkMetadata> f_links = null;
                Boolean f_hasMore = null;
                String f_cursor = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("links".equals(field)) {
                        f_links = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else if ("has_more".equals(field)) {
                        f_hasMore = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("cursor".equals(field)) {
                        f_cursor = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_links == null) {
                    throw new JsonParseException(p, "Required field \"links\" missing.");
                } else if (f_hasMore == null) {
                    throw new JsonParseException(p, "Required field \"has_more\" missing.");
                } else {
                    ListSharedLinksResult value = new ListSharedLinksResult(f_links, f_hasMore.booleanValue(), f_cursor);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListSharedLinksResult(List<SharedLinkMetadata> links, boolean hasMore, String cursor) {
        if (links == null) {
            throw new IllegalArgumentException("Required value for 'links' is null");
        }
        for (SharedLinkMetadata x : links) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'links' is null");
            }
        }
        this.links = links;
        this.hasMore = hasMore;
        this.cursor = cursor;
    }

    public ListSharedLinksResult(List<SharedLinkMetadata> links, boolean hasMore) {
        this(links, hasMore, null);
    }

    public List<SharedLinkMetadata> getLinks() {
        return this.links;
    }

    public boolean getHasMore() {
        return this.hasMore;
    }

    public String getCursor() {
        return this.cursor;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.links, Boolean.valueOf(this.hasMore), this.cursor});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListSharedLinksResult other = (ListSharedLinksResult) obj;
        if ((this.links == other.links || this.links.equals(other.links)) && this.hasMore == other.hasMore) {
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
