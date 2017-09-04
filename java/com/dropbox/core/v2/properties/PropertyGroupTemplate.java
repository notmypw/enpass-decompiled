package com.dropbox.core.v2.properties;

import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.models.BoxItem;
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

public class PropertyGroupTemplate {
    protected final String description;
    protected final List<PropertyFieldTemplate> fields;
    protected final String name;

    private static class Serializer extends StructSerializer<PropertyGroupTemplate> {
        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {
        }

        public void serialize(PropertyGroupTemplate value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxFileVersion.FIELD_NAME);
            StoneSerializers.string().serialize(value.name, g);
            g.writeFieldName(BoxItem.FIELD_DESCRIPTION);
            StoneSerializers.string().serialize(value.description, g);
            g.writeFieldName("fields");
            StoneSerializers.list(com.dropbox.core.v2.properties.PropertyFieldTemplate.Serializer.INSTANCE).serialize(value.fields, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public PropertyGroupTemplate deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_name = null;
                String f_description = null;
                List<PropertyFieldTemplate> f_fields = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxFileVersion.FIELD_NAME.equals(field)) {
                        f_name = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxItem.FIELD_DESCRIPTION.equals(field)) {
                        f_description = (String) StoneSerializers.string().deserialize(p);
                    } else if ("fields".equals(field)) {
                        f_fields = (List) StoneSerializers.list(com.dropbox.core.v2.properties.PropertyFieldTemplate.Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_name == null) {
                    throw new JsonParseException(p, "Required field \"name\" missing.");
                } else if (f_description == null) {
                    throw new JsonParseException(p, "Required field \"description\" missing.");
                } else if (f_fields == null) {
                    throw new JsonParseException(p, "Required field \"fields\" missing.");
                } else {
                    PropertyGroupTemplate value = new PropertyGroupTemplate(f_name, f_description, f_fields);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public PropertyGroupTemplate(String name, String description, List<PropertyFieldTemplate> fields) {
        if (name == null) {
            throw new IllegalArgumentException("Required value for 'name' is null");
        }
        this.name = name;
        if (description == null) {
            throw new IllegalArgumentException("Required value for 'description' is null");
        }
        this.description = description;
        if (fields == null) {
            throw new IllegalArgumentException("Required value for 'fields' is null");
        }
        for (PropertyFieldTemplate x : fields) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'fields' is null");
            }
        }
        this.fields = fields;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public List<PropertyFieldTemplate> getFields() {
        return this.fields;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.name, this.description, this.fields});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        PropertyGroupTemplate other = (PropertyGroupTemplate) obj;
        if ((this.name == other.name || this.name.equals(other.name)) && ((this.description == other.description || this.description.equals(other.description)) && (this.fields == other.fields || this.fields.equals(other.fields)))) {
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
