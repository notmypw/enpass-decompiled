package com.dropbox.core.v2.users;

import com.box.androidsdk.content.models.BoxEntity;
import com.box.androidsdk.content.models.BoxFileVersion;
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

public class Team {
    protected final String id;
    protected final String name;

    public static class Serializer extends StructSerializer<Team> {
        public static final Serializer INSTANCE = new Serializer();

        public void serialize(Team value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxEntity.FIELD_ID);
            StoneSerializers.string().serialize(value.id, g);
            g.writeFieldName(BoxFileVersion.FIELD_NAME);
            StoneSerializers.string().serialize(value.name, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public Team deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_id = null;
                String f_name = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxEntity.FIELD_ID.equals(field)) {
                        f_id = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxFileVersion.FIELD_NAME.equals(field)) {
                        f_name = (String) StoneSerializers.string().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_id == null) {
                    throw new JsonParseException(p, "Required field \"id\" missing.");
                } else if (f_name == null) {
                    throw new JsonParseException(p, "Required field \"name\" missing.");
                } else {
                    Team value = new Team(f_id, f_name);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public Team(String id, String name) {
        if (id == null) {
            throw new IllegalArgumentException("Required value for 'id' is null");
        }
        this.id = id;
        if (name == null) {
            throw new IllegalArgumentException("Required value for 'name' is null");
        }
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.id, this.name});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        Team other = (Team) obj;
        if ((this.id == other.id || this.id.equals(other.id)) && (this.name == other.name || this.name.equals(other.name))) {
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
