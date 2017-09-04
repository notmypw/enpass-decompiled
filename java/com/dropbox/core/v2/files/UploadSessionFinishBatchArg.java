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
import java.util.List;

class UploadSessionFinishBatchArg {
    protected final List<UploadSessionFinishArg> entries;

    static class Serializer extends StructSerializer<UploadSessionFinishBatchArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UploadSessionFinishBatchArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxList.FIELD_ENTRIES);
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.entries, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public UploadSessionFinishBatchArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<UploadSessionFinishArg> f_entries = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxList.FIELD_ENTRIES.equals(field)) {
                        f_entries = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_entries == null) {
                    throw new JsonParseException(p, "Required field \"entries\" missing.");
                }
                UploadSessionFinishBatchArg value = new UploadSessionFinishBatchArg(f_entries);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public UploadSessionFinishBatchArg(List<UploadSessionFinishArg> entries) {
        if (entries == null) {
            throw new IllegalArgumentException("Required value for 'entries' is null");
        } else if (entries.size() > 1000) {
            throw new IllegalArgumentException("List 'entries' has more than 1000 items");
        } else {
            for (UploadSessionFinishArg x : entries) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'entries' is null");
                }
            }
            this.entries = entries;
        }
    }

    public List<UploadSessionFinishArg> getEntries() {
        return this.entries;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.entries});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        UploadSessionFinishBatchArg other = (UploadSessionFinishBatchArg) obj;
        if (this.entries == other.entries || this.entries.equals(other.entries)) {
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
