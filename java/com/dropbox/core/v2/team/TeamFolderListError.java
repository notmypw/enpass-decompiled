package com.dropbox.core.v2.team;

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

public class TeamFolderListError {
    protected final TeamFolderAccessError accessError;

    static class Serializer extends StructSerializer<TeamFolderListError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(TeamFolderListError value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("access_error");
            Serializer.INSTANCE.serialize(value.accessError, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public TeamFolderListError deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                TeamFolderAccessError f_accessError = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("access_error".equals(field)) {
                        f_accessError = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_accessError == null) {
                    throw new JsonParseException(p, "Required field \"access_error\" missing.");
                }
                TeamFolderListError value = new TeamFolderListError(f_accessError);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public TeamFolderListError(TeamFolderAccessError accessError) {
        if (accessError == null) {
            throw new IllegalArgumentException("Required value for 'accessError' is null");
        }
        this.accessError = accessError;
    }

    public TeamFolderAccessError getAccessError() {
        return this.accessError;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.accessError});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        TeamFolderListError other = (TeamFolderListError) obj;
        if (this.accessError == other.accessError || this.accessError.equals(other.accessError)) {
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
