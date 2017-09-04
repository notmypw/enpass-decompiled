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

public class GetSharedLinksResult {
    protected final List<LinkMetadata> links;

    static class Serializer extends StructSerializer<GetSharedLinksResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GetSharedLinksResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("links");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.links, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GetSharedLinksResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<LinkMetadata> f_links = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("links".equals(field)) {
                        f_links = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_links == null) {
                    throw new JsonParseException(p, "Required field \"links\" missing.");
                }
                GetSharedLinksResult value = new GetSharedLinksResult(f_links);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GetSharedLinksResult(List<LinkMetadata> links) {
        if (links == null) {
            throw new IllegalArgumentException("Required value for 'links' is null");
        }
        for (LinkMetadata x : links) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'links' is null");
            }
        }
        this.links = links;
    }

    public List<LinkMetadata> getLinks() {
        return this.links;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.links});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GetSharedLinksResult other = (GetSharedLinksResult) obj;
        if (this.links == other.links || this.links.equals(other.links)) {
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
