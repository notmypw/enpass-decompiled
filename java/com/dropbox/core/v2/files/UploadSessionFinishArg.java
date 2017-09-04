package com.dropbox.core.v2.files;

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

public class UploadSessionFinishArg {
    protected final CommitInfo commit;
    protected final UploadSessionCursor cursor;

    static class Serializer extends StructSerializer<UploadSessionFinishArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UploadSessionFinishArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("cursor");
            Serializer.INSTANCE.serialize(value.cursor, g);
            g.writeFieldName("commit");
            Serializer.INSTANCE.serialize(value.commit, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public UploadSessionFinishArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                UploadSessionCursor f_cursor = null;
                CommitInfo f_commit = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("cursor".equals(field)) {
                        f_cursor = (UploadSessionCursor) Serializer.INSTANCE.deserialize(p);
                    } else if ("commit".equals(field)) {
                        f_commit = (CommitInfo) Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_cursor == null) {
                    throw new JsonParseException(p, "Required field \"cursor\" missing.");
                } else if (f_commit == null) {
                    throw new JsonParseException(p, "Required field \"commit\" missing.");
                } else {
                    UploadSessionFinishArg value = new UploadSessionFinishArg(f_cursor, f_commit);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public UploadSessionFinishArg(UploadSessionCursor cursor, CommitInfo commit) {
        if (cursor == null) {
            throw new IllegalArgumentException("Required value for 'cursor' is null");
        }
        this.cursor = cursor;
        if (commit == null) {
            throw new IllegalArgumentException("Required value for 'commit' is null");
        }
        this.commit = commit;
    }

    public UploadSessionCursor getCursor() {
        return this.cursor;
    }

    public CommitInfo getCommit() {
        return this.commit;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.cursor, this.commit});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        UploadSessionFinishArg other = (UploadSessionFinishArg) obj;
        if ((this.cursor == other.cursor || this.cursor.equals(other.cursor)) && (this.commit == other.commit || this.commit.equals(other.commit))) {
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
