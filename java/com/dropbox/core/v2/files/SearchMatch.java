package com.dropbox.core.v2.files;

import com.box.androidsdk.content.BoxApiMetadata;
import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StructSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;

public class SearchMatch {
    protected final SearchMatchType matchType;
    protected final Metadata metadata;

    static class Serializer extends StructSerializer<SearchMatch> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(SearchMatch value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("match_type");
            Serializer.INSTANCE.serialize(value.matchType, g);
            g.writeFieldName(BoxApiMetadata.BOX_API_METADATA);
            Serializer.INSTANCE.serialize(value.metadata, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public SearchMatch deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                SearchMatchType f_matchType = null;
                Metadata f_metadata = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("match_type".equals(field)) {
                        f_matchType = Serializer.INSTANCE.deserialize(p);
                    } else if (BoxApiMetadata.BOX_API_METADATA.equals(field)) {
                        f_metadata = (Metadata) Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_matchType == null) {
                    throw new JsonParseException(p, "Required field \"match_type\" missing.");
                } else if (f_metadata == null) {
                    throw new JsonParseException(p, "Required field \"metadata\" missing.");
                } else {
                    SearchMatch value = new SearchMatch(f_matchType, f_metadata);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public SearchMatch(SearchMatchType matchType, Metadata metadata) {
        if (matchType == null) {
            throw new IllegalArgumentException("Required value for 'matchType' is null");
        }
        this.matchType = matchType;
        if (metadata == null) {
            throw new IllegalArgumentException("Required value for 'metadata' is null");
        }
        this.metadata = metadata;
    }

    public SearchMatchType getMatchType() {
        return this.matchType;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.matchType, this.metadata});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        SearchMatch other = (SearchMatch) obj;
        if ((this.matchType == other.matchType || this.matchType.equals(other.matchType)) && (this.metadata == other.metadata || this.metadata.equals(other.metadata))) {
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
