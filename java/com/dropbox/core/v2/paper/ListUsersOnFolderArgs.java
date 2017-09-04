package com.dropbox.core.v2.paper;

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

class ListUsersOnFolderArgs extends RefPaperDoc {
    protected final int limit;

    static class Serializer extends StructSerializer<ListUsersOnFolderArgs> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListUsersOnFolderArgs value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("doc_id");
            StoneSerializers.string().serialize(value.docId, g);
            g.writeFieldName(BoxList.FIELD_LIMIT);
            StoneSerializers.int32().serialize(Integer.valueOf(value.limit), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListUsersOnFolderArgs deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_docId = null;
                Integer f_limit = Integer.valueOf(1000);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("doc_id".equals(field)) {
                        f_docId = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxList.FIELD_LIMIT.equals(field)) {
                        f_limit = (Integer) StoneSerializers.int32().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_docId == null) {
                    throw new JsonParseException(p, "Required field \"doc_id\" missing.");
                }
                ListUsersOnFolderArgs value = new ListUsersOnFolderArgs(f_docId, f_limit.intValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListUsersOnFolderArgs(String docId, int limit) {
        super(docId);
        if (limit < 1) {
            throw new IllegalArgumentException("Number 'limit' is smaller than 1");
        } else if (limit > 1000) {
            throw new IllegalArgumentException("Number 'limit' is larger than 1000");
        } else {
            this.limit = limit;
        }
    }

    public ListUsersOnFolderArgs(String docId) {
        this(docId, 1000);
    }

    public String getDocId() {
        return this.docId;
    }

    public int getLimit() {
        return this.limit;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.limit)}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListUsersOnFolderArgs other = (ListUsersOnFolderArgs) obj;
        if ((this.docId == other.docId || this.docId.equals(other.docId)) && this.limit == other.limit) {
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
