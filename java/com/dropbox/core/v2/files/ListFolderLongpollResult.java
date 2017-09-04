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

public class ListFolderLongpollResult {
    protected final Long backoff;
    protected final boolean changes;

    static class Serializer extends StructSerializer<ListFolderLongpollResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListFolderLongpollResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxRequestEvent.STREAM_TYPE_CHANGES);
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.changes), g);
            if (value.backoff != null) {
                g.writeFieldName("backoff");
                StoneSerializers.nullable(StoneSerializers.uInt64()).serialize(value.backoff, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListFolderLongpollResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Boolean f_changes = null;
                Long f_backoff = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxRequestEvent.STREAM_TYPE_CHANGES.equals(field)) {
                        f_changes = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("backoff".equals(field)) {
                        f_backoff = (Long) StoneSerializers.nullable(StoneSerializers.uInt64()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_changes == null) {
                    throw new JsonParseException(p, "Required field \"changes\" missing.");
                }
                ListFolderLongpollResult value = new ListFolderLongpollResult(f_changes.booleanValue(), f_backoff);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListFolderLongpollResult(boolean changes, Long backoff) {
        this.changes = changes;
        this.backoff = backoff;
    }

    public ListFolderLongpollResult(boolean changes) {
        this(changes, null);
    }

    public boolean getChanges() {
        return this.changes;
    }

    public Long getBackoff() {
        return this.backoff;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Boolean.valueOf(this.changes), this.backoff});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListFolderLongpollResult other = (ListFolderLongpollResult) obj;
        if (this.changes == other.changes) {
            if (this.backoff == other.backoff) {
                return true;
            }
            if (this.backoff != null && this.backoff.equals(other.backoff)) {
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
