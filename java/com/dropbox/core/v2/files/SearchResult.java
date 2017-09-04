package com.dropbox.core.v2.files;

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

public class SearchResult {
    protected final List<SearchMatch> matches;
    protected final boolean more;
    protected final long start;

    static class Serializer extends StructSerializer<SearchResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(SearchResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("matches");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.matches, g);
            g.writeFieldName("more");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.more), g);
            g.writeFieldName("start");
            StoneSerializers.uInt64().serialize(Long.valueOf(value.start), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public SearchResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<SearchMatch> f_matches = null;
                Boolean f_more = null;
                Long f_start = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("matches".equals(field)) {
                        f_matches = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else if ("more".equals(field)) {
                        f_more = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("start".equals(field)) {
                        f_start = (Long) StoneSerializers.uInt64().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_matches == null) {
                    throw new JsonParseException(p, "Required field \"matches\" missing.");
                } else if (f_more == null) {
                    throw new JsonParseException(p, "Required field \"more\" missing.");
                } else if (f_start == null) {
                    throw new JsonParseException(p, "Required field \"start\" missing.");
                } else {
                    SearchResult value = new SearchResult(f_matches, f_more.booleanValue(), f_start.longValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public SearchResult(List<SearchMatch> matches, boolean more, long start) {
        if (matches == null) {
            throw new IllegalArgumentException("Required value for 'matches' is null");
        }
        for (SearchMatch x : matches) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'matches' is null");
            }
        }
        this.matches = matches;
        this.more = more;
        this.start = start;
    }

    public List<SearchMatch> getMatches() {
        return this.matches;
    }

    public boolean getMore() {
        return this.more;
    }

    public long getStart() {
        return this.start;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.matches, Boolean.valueOf(this.more), Long.valueOf(this.start)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        SearchResult other = (SearchResult) obj;
        if ((this.matches == other.matches || this.matches.equals(other.matches)) && this.more == other.more && this.start == other.start) {
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
