package com.dropbox.core.v2.team;

import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.models.BoxItem;
import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.v2.properties.PropertyFieldTemplate;
import com.dropbox.core.v2.properties.PropertyGroupTemplate;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.List;

class AddPropertyTemplateArg extends PropertyGroupTemplate {

    static class Serializer extends StructSerializer<AddPropertyTemplateArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(AddPropertyTemplateArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
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

        public AddPropertyTemplateArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
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
                    AddPropertyTemplateArg value = new AddPropertyTemplateArg(f_name, f_description, f_fields);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public AddPropertyTemplateArg(String name, String description, List<PropertyFieldTemplate> fields) {
        super(name, description, fields);
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
        return getClass().toString().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        AddPropertyTemplateArg other = (AddPropertyTemplateArg) obj;
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
