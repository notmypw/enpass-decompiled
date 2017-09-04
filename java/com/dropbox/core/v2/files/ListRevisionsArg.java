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
import java.util.regex.Pattern;

class ListRevisionsArg {
    protected final long limit;
    protected final String path;

    static class Serializer extends StructSerializer<ListRevisionsArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListRevisionsArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxMetadataUpdateTask.PATH);
            StoneSerializers.string().serialize(value.path, g);
            g.writeFieldName(BoxList.FIELD_LIMIT);
            StoneSerializers.uInt64().serialize(Long.valueOf(value.limit), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListRevisionsArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_path = null;
                Long f_limit = Long.valueOf(10);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxMetadataUpdateTask.PATH.equals(field)) {
                        f_path = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxList.FIELD_LIMIT.equals(field)) {
                        f_limit = (Long) StoneSerializers.uInt64().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_path == null) {
                    throw new JsonParseException(p, "Required field \"path\" missing.");
                }
                ListRevisionsArg value = new ListRevisionsArg(f_path, f_limit.longValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListRevisionsArg(String path, long limit) {
        if (path == null) {
            throw new IllegalArgumentException("Required value for 'path' is null");
        } else if (Pattern.matches("/(.|[\\r\\n])*|id:.*|(ns:[0-9]+(/.*)?)", path)) {
            this.path = path;
            if (limit < 1) {
                throw new IllegalArgumentException("Number 'limit' is smaller than 1L");
            } else if (limit > 100) {
                throw new IllegalArgumentException("Number 'limit' is larger than 100L");
            } else {
                this.limit = limit;
            }
        } else {
            throw new IllegalArgumentException("String 'path' does not match pattern");
        }
    }

    public ListRevisionsArg(String path) {
        this(path, 10);
    }

    public String getPath() {
        return this.path;
    }

    public long getLimit() {
        return this.limit;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.path, Long.valueOf(this.limit)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListRevisionsArg other = (ListRevisionsArg) obj;
        if ((this.path == other.path || this.path.equals(other.path)) && this.limit == other.limit) {
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
