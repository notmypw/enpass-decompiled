package com.dropbox.core.v2.properties;

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

public class PropertyField {
    protected final String name;
    protected final String value;

    public static class Serializer extends StructSerializer<PropertyField> {
        public static final Serializer INSTANCE = new Serializer();

        public void serialize(PropertyField value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxFileVersion.FIELD_NAME);
            StoneSerializers.string().serialize(value.name, g);
            g.writeFieldName(BoxMetadataUpdateTask.VALUE);
            StoneSerializers.string().serialize(value.value, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public PropertyField deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_name = null;
                String f_value = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxFileVersion.FIELD_NAME.equals(field)) {
                        f_name = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxMetadataUpdateTask.VALUE.equals(field)) {
                        f_value = (String) StoneSerializers.string().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_name == null) {
                    throw new JsonParseException(p, "Required field \"name\" missing.");
                } else if (f_value == null) {
                    throw new JsonParseException(p, "Required field \"value\" missing.");
                } else {
                    PropertyField value = new PropertyField(f_name, f_value);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public PropertyField(String name, String value) {
        if (name == null) {
            throw new IllegalArgumentException("Required value for 'name' is null");
        }
        this.name = name;
        if (value == null) {
            throw new IllegalArgumentException("Required value for 'value' is null");
        }
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.name, this.value});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        PropertyField other = (PropertyField) obj;
        if ((this.name == other.name || this.name.equals(other.name)) && (this.value == other.value || this.value.equals(other.value))) {
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
