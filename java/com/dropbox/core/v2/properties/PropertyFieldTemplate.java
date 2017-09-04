package com.dropbox.core.v2.properties;

import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.models.BoxItem;
import com.box.androidsdk.content.models.BoxRealTimeServer;
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

public class PropertyFieldTemplate {
    protected final String description;
    protected final String name;
    protected final PropertyType type;

    public static class Serializer extends StructSerializer<PropertyFieldTemplate> {
        public static final Serializer INSTANCE = new Serializer();

        public void serialize(PropertyFieldTemplate value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxFileVersion.FIELD_NAME);
            StoneSerializers.string().serialize(value.name, g);
            g.writeFieldName(BoxItem.FIELD_DESCRIPTION);
            StoneSerializers.string().serialize(value.description, g);
            g.writeFieldName(BoxRealTimeServer.FIELD_TYPE);
            Serializer.INSTANCE.serialize(value.type, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public PropertyFieldTemplate deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_name = null;
                String f_description = null;
                PropertyType f_type = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxFileVersion.FIELD_NAME.equals(field)) {
                        f_name = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxItem.FIELD_DESCRIPTION.equals(field)) {
                        f_description = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxRealTimeServer.FIELD_TYPE.equals(field)) {
                        f_type = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_name == null) {
                    throw new JsonParseException(p, "Required field \"name\" missing.");
                } else if (f_description == null) {
                    throw new JsonParseException(p, "Required field \"description\" missing.");
                } else if (f_type == null) {
                    throw new JsonParseException(p, "Required field \"type\" missing.");
                } else {
                    PropertyFieldTemplate value = new PropertyFieldTemplate(f_name, f_description, f_type);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public PropertyFieldTemplate(String name, String description, PropertyType type) {
        if (name == null) {
            throw new IllegalArgumentException("Required value for 'name' is null");
        }
        this.name = name;
        if (description == null) {
            throw new IllegalArgumentException("Required value for 'description' is null");
        }
        this.description = description;
        if (type == null) {
            throw new IllegalArgumentException("Required value for 'type' is null");
        }
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public PropertyType getType() {
        return this.type;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.name, this.description, this.type});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        PropertyFieldTemplate other = (PropertyFieldTemplate) obj;
        if ((this.name == other.name || this.name.equals(other.name)) && ((this.description == other.description || this.description.equals(other.description)) && (this.type == other.type || this.type.equals(other.type)))) {
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
