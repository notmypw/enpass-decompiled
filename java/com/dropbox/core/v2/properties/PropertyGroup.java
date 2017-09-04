package com.dropbox.core.v2.properties;

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
import java.util.regex.Pattern;

public class PropertyGroup {
    protected final List<PropertyField> fields;
    protected final String templateId;

    public static class Serializer extends StructSerializer<PropertyGroup> {
        public static final Serializer INSTANCE = new Serializer();

        public void serialize(PropertyGroup value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("template_id");
            StoneSerializers.string().serialize(value.templateId, g);
            g.writeFieldName("fields");
            StoneSerializers.list(com.dropbox.core.v2.properties.PropertyField.Serializer.INSTANCE).serialize(value.fields, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public PropertyGroup deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_templateId = null;
                List<PropertyField> f_fields = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("template_id".equals(field)) {
                        f_templateId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("fields".equals(field)) {
                        f_fields = (List) StoneSerializers.list(com.dropbox.core.v2.properties.PropertyField.Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_templateId == null) {
                    throw new JsonParseException(p, "Required field \"template_id\" missing.");
                } else if (f_fields == null) {
                    throw new JsonParseException(p, "Required field \"fields\" missing.");
                } else {
                    PropertyGroup value = new PropertyGroup(f_templateId, f_fields);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public PropertyGroup(String templateId, List<PropertyField> fields) {
        if (templateId == null) {
            throw new IllegalArgumentException("Required value for 'templateId' is null");
        } else if (templateId.length() < 1) {
            throw new IllegalArgumentException("String 'templateId' is shorter than 1");
        } else if (Pattern.matches("(/|ptid:).*", templateId)) {
            this.templateId = templateId;
            if (fields == null) {
                throw new IllegalArgumentException("Required value for 'fields' is null");
            }
            for (PropertyField x : fields) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'fields' is null");
                }
            }
            this.fields = fields;
        } else {
            throw new IllegalArgumentException("String 'templateId' does not match pattern");
        }
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public List<PropertyField> getFields() {
        return this.fields;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.templateId, this.fields});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        PropertyGroup other = (PropertyGroup) obj;
        if ((this.templateId == other.templateId || this.templateId.equals(other.templateId)) && (this.fields == other.fields || this.fields.equals(other.fields))) {
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
