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

class ListFolderLongpollArg {
    protected final String cursor;
    protected final long timeout;

    static class Serializer extends StructSerializer<ListFolderLongpollArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListFolderLongpollArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("cursor");
            StoneSerializers.string().serialize(value.cursor, g);
            g.writeFieldName("timeout");
            StoneSerializers.uInt64().serialize(Long.valueOf(value.timeout), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListFolderLongpollArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_cursor = null;
                Long f_timeout = Long.valueOf(30);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("cursor".equals(field)) {
                        f_cursor = (String) StoneSerializers.string().deserialize(p);
                    } else if ("timeout".equals(field)) {
                        f_timeout = (Long) StoneSerializers.uInt64().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_cursor == null) {
                    throw new JsonParseException(p, "Required field \"cursor\" missing.");
                }
                ListFolderLongpollArg value = new ListFolderLongpollArg(f_cursor, f_timeout.longValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListFolderLongpollArg(String cursor, long timeout) {
        if (cursor == null) {
            throw new IllegalArgumentException("Required value for 'cursor' is null");
        } else if (cursor.length() < 1) {
            throw new IllegalArgumentException("String 'cursor' is shorter than 1");
        } else {
            this.cursor = cursor;
            if (timeout < 30) {
                throw new IllegalArgumentException("Number 'timeout' is smaller than 30L");
            } else if (timeout > 480) {
                throw new IllegalArgumentException("Number 'timeout' is larger than 480L");
            } else {
                this.timeout = timeout;
            }
        }
    }

    public ListFolderLongpollArg(String cursor) {
        this(cursor, 30);
    }

    public String getCursor() {
        return this.cursor;
    }

    public long getTimeout() {
        return this.timeout;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.cursor, Long.valueOf(this.timeout)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListFolderLongpollArg other = (ListFolderLongpollArg) obj;
        if ((this.cursor == other.cursor || this.cursor.equals(other.cursor)) && this.timeout == other.timeout) {
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
