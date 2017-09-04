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

class ListUsersOnFolderContinueArgs extends RefPaperDoc {
    protected final String cursor;

    static class Serializer extends StructSerializer<ListUsersOnFolderContinueArgs> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListUsersOnFolderContinueArgs value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("doc_id");
            StoneSerializers.string().serialize(value.docId, g);
            g.writeFieldName("cursor");
            StoneSerializers.string().serialize(value.cursor, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListUsersOnFolderContinueArgs deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_docId = null;
                String f_cursor = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("doc_id".equals(field)) {
                        f_docId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("cursor".equals(field)) {
                        f_cursor = (String) StoneSerializers.string().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_docId == null) {
                    throw new JsonParseException(p, "Required field \"doc_id\" missing.");
                } else if (f_cursor == null) {
                    throw new JsonParseException(p, "Required field \"cursor\" missing.");
                } else {
                    ListUsersOnFolderContinueArgs value = new ListUsersOnFolderContinueArgs(f_docId, f_cursor);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListUsersOnFolderContinueArgs(String docId, String cursor) {
        super(docId);
        if (cursor == null) {
            throw new IllegalArgumentException("Required value for 'cursor' is null");
        }
        this.cursor = cursor;
    }

    public String getDocId() {
        return this.docId;
    }

    public String getCursor() {
        return this.cursor;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.cursor}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListUsersOnFolderContinueArgs other = (ListUsersOnFolderContinueArgs) obj;
        if ((this.docId == other.docId || this.docId.equals(other.docId)) && (this.cursor == other.cursor || this.cursor.equals(other.cursor))) {
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
