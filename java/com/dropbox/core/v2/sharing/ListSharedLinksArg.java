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
import java.util.regex.Pattern;

class ListSharedLinksArg {
    protected final String cursor;
    protected final Boolean directOnly;
    protected final String path;

    public static class Builder {
        protected String cursor = null;
        protected Boolean directOnly = null;
        protected String path = null;

        protected Builder() {
        }

        public Builder withPath(String path) {
            if (path == null || Pattern.matches("(/(.|[\\r\\n])*|id:.*)|(rev:[0-9a-f]{9,})|(ns:[0-9]+(/.*)?)", path)) {
                this.path = path;
                return this;
            }
            throw new IllegalArgumentException("String 'path' does not match pattern");
        }

        public Builder withCursor(String cursor) {
            this.cursor = cursor;
            return this;
        }

        public Builder withDirectOnly(Boolean directOnly) {
            this.directOnly = directOnly;
            return this;
        }

        public ListSharedLinksArg build() {
            return new ListSharedLinksArg(this.path, this.cursor, this.directOnly);
        }
    }

    static class Serializer extends StructSerializer<ListSharedLinksArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListSharedLinksArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            if (value.path != null) {
                g.writeFieldName(BoxMetadataUpdateTask.PATH);
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.path, g);
            }
            if (value.cursor != null) {
                g.writeFieldName("cursor");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.cursor, g);
            }
            if (value.directOnly != null) {
                g.writeFieldName("direct_only");
                StoneSerializers.nullable(StoneSerializers.boolean_()).serialize(value.directOnly, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListSharedLinksArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_path = null;
                String f_cursor = null;
                Boolean f_directOnly = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxMetadataUpdateTask.PATH.equals(field)) {
                        f_path = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("cursor".equals(field)) {
                        f_cursor = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("direct_only".equals(field)) {
                        f_directOnly = (Boolean) StoneSerializers.nullable(StoneSerializers.boolean_()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                ListSharedLinksArg value = new ListSharedLinksArg(f_path, f_cursor, f_directOnly);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListSharedLinksArg(String path, String cursor, Boolean directOnly) {
        if (path == null || Pattern.matches("(/(.|[\\r\\n])*|id:.*)|(rev:[0-9a-f]{9,})|(ns:[0-9]+(/.*)?)", path)) {
            this.path = path;
            this.cursor = cursor;
            this.directOnly = directOnly;
            return;
        }
        throw new IllegalArgumentException("String 'path' does not match pattern");
    }

    public ListSharedLinksArg() {
        this(null, null, null);
    }

    public String getPath() {
        return this.path;
    }

    public String getCursor() {
        return this.cursor;
    }

    public Boolean getDirectOnly() {
        return this.directOnly;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.path, this.cursor, this.directOnly});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListSharedLinksArg other = (ListSharedLinksArg) obj;
        if ((this.path == other.path || (this.path != null && this.path.equals(other.path))) && (this.cursor == other.cursor || (this.cursor != null && this.cursor.equals(other.cursor)))) {
            if (this.directOnly == other.directOnly) {
                return true;
            }
            if (this.directOnly != null && this.directOnly.equals(other.directOnly)) {
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
