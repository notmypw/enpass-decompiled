package com.dropbox.core.v2.team;

import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.models.BoxItem;
import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.v2.properties.PropertyFieldTemplate;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

class UpdatePropertyTemplateArg {
    protected final List<PropertyFieldTemplate> addFields;
    protected final String description;
    protected final String name;
    protected final String templateId;

    public static class Builder {
        protected List<PropertyFieldTemplate> addFields;
        protected String description;
        protected String name;
        protected final String templateId;

        protected Builder(String templateId) {
            if (templateId == null) {
                throw new IllegalArgumentException("Required value for 'templateId' is null");
            } else if (templateId.length() < 1) {
                throw new IllegalArgumentException("String 'templateId' is shorter than 1");
            } else if (Pattern.matches("(/|ptid:).*", templateId)) {
                this.templateId = templateId;
                this.name = null;
                this.description = null;
                this.addFields = null;
            } else {
                throw new IllegalArgumentException("String 'templateId' does not match pattern");
            }
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withAddFields(List<PropertyFieldTemplate> addFields) {
            if (addFields != null) {
                for (PropertyFieldTemplate x : addFields) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'addFields' is null");
                    }
                }
            }
            this.addFields = addFields;
            return this;
        }

        public UpdatePropertyTemplateArg build() {
            return new UpdatePropertyTemplateArg(this.templateId, this.name, this.description, this.addFields);
        }
    }

    static class Serializer extends StructSerializer<UpdatePropertyTemplateArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UpdatePropertyTemplateArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("template_id");
            StoneSerializers.string().serialize(value.templateId, g);
            if (value.name != null) {
                g.writeFieldName(BoxFileVersion.FIELD_NAME);
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.name, g);
            }
            if (value.description != null) {
                g.writeFieldName(BoxItem.FIELD_DESCRIPTION);
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.description, g);
            }
            if (value.addFields != null) {
                g.writeFieldName("add_fields");
                StoneSerializers.nullable(StoneSerializers.list(com.dropbox.core.v2.properties.PropertyFieldTemplate.Serializer.INSTANCE)).serialize(value.addFields, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public UpdatePropertyTemplateArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_templateId = null;
                String f_name = null;
                String f_description = null;
                List<PropertyFieldTemplate> f_addFields = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("template_id".equals(field)) {
                        f_templateId = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxFileVersion.FIELD_NAME.equals(field)) {
                        f_name = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if (BoxItem.FIELD_DESCRIPTION.equals(field)) {
                        f_description = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("add_fields".equals(field)) {
                        f_addFields = (List) StoneSerializers.nullable(StoneSerializers.list(com.dropbox.core.v2.properties.PropertyFieldTemplate.Serializer.INSTANCE)).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_templateId == null) {
                    throw new JsonParseException(p, "Required field \"template_id\" missing.");
                }
                UpdatePropertyTemplateArg value = new UpdatePropertyTemplateArg(f_templateId, f_name, f_description, f_addFields);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public UpdatePropertyTemplateArg(String templateId, String name, String description, List<PropertyFieldTemplate> addFields) {
        if (templateId == null) {
            throw new IllegalArgumentException("Required value for 'templateId' is null");
        } else if (templateId.length() < 1) {
            throw new IllegalArgumentException("String 'templateId' is shorter than 1");
        } else if (Pattern.matches("(/|ptid:).*", templateId)) {
            this.templateId = templateId;
            this.name = name;
            this.description = description;
            if (addFields != null) {
                for (PropertyFieldTemplate x : addFields) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'addFields' is null");
                    }
                }
            }
            this.addFields = addFields;
        } else {
            throw new IllegalArgumentException("String 'templateId' does not match pattern");
        }
    }

    public UpdatePropertyTemplateArg(String templateId) {
        this(templateId, null, null, null);
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public List<PropertyFieldTemplate> getAddFields() {
        return this.addFields;
    }

    public static Builder newBuilder(String templateId) {
        return new Builder(templateId);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.templateId, this.name, this.description, this.addFields});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        UpdatePropertyTemplateArg other = (UpdatePropertyTemplateArg) obj;
        if ((this.templateId == other.templateId || this.templateId.equals(other.templateId)) && ((this.name == other.name || (this.name != null && this.name.equals(other.name))) && (this.description == other.description || (this.description != null && this.description.equals(other.description))))) {
            if (this.addFields == other.addFields) {
                return true;
            }
            if (this.addFields != null && this.addFields.equals(other.addFields)) {
                return true;
            }
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
