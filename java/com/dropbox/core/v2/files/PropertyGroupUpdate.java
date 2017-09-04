package com.dropbox.core.v2.files;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.v2.properties.PropertyField;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class PropertyGroupUpdate {
    protected final List<PropertyField> addOrUpdateFields;
    protected final List<String> removeFields;
    protected final String templateId;

    public static class Builder {
        protected List<PropertyField> addOrUpdateFields;
        protected List<String> removeFields;
        protected final String templateId;

        protected Builder(String templateId) {
            if (templateId == null) {
                throw new IllegalArgumentException("Required value for 'templateId' is null");
            } else if (templateId.length() < 1) {
                throw new IllegalArgumentException("String 'templateId' is shorter than 1");
            } else if (Pattern.matches("(/|ptid:).*", templateId)) {
                this.templateId = templateId;
                this.addOrUpdateFields = null;
                this.removeFields = null;
            } else {
                throw new IllegalArgumentException("String 'templateId' does not match pattern");
            }
        }

        public Builder withAddOrUpdateFields(List<PropertyField> addOrUpdateFields) {
            if (addOrUpdateFields != null) {
                for (PropertyField x : addOrUpdateFields) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'addOrUpdateFields' is null");
                    }
                }
            }
            this.addOrUpdateFields = addOrUpdateFields;
            return this;
        }

        public Builder withRemoveFields(List<String> removeFields) {
            if (removeFields != null) {
                for (String x : removeFields) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'removeFields' is null");
                    }
                }
            }
            this.removeFields = removeFields;
            return this;
        }

        public PropertyGroupUpdate build() {
            return new PropertyGroupUpdate(this.templateId, this.addOrUpdateFields, this.removeFields);
        }
    }

    static class Serializer extends StructSerializer<PropertyGroupUpdate> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(PropertyGroupUpdate value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("template_id");
            StoneSerializers.string().serialize(value.templateId, g);
            if (value.addOrUpdateFields != null) {
                g.writeFieldName("add_or_update_fields");
                StoneSerializers.nullable(StoneSerializers.list(com.dropbox.core.v2.properties.PropertyField.Serializer.INSTANCE)).serialize(value.addOrUpdateFields, g);
            }
            if (value.removeFields != null) {
                g.writeFieldName("remove_fields");
                StoneSerializers.nullable(StoneSerializers.list(StoneSerializers.string())).serialize(value.removeFields, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public PropertyGroupUpdate deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_templateId = null;
                List<PropertyField> f_addOrUpdateFields = null;
                List<String> f_removeFields = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("template_id".equals(field)) {
                        f_templateId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("add_or_update_fields".equals(field)) {
                        f_addOrUpdateFields = (List) StoneSerializers.nullable(StoneSerializers.list(com.dropbox.core.v2.properties.PropertyField.Serializer.INSTANCE)).deserialize(p);
                    } else if ("remove_fields".equals(field)) {
                        f_removeFields = (List) StoneSerializers.nullable(StoneSerializers.list(StoneSerializers.string())).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_templateId == null) {
                    throw new JsonParseException(p, "Required field \"template_id\" missing.");
                }
                PropertyGroupUpdate value = new PropertyGroupUpdate(f_templateId, f_addOrUpdateFields, f_removeFields);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public PropertyGroupUpdate(String templateId, List<PropertyField> addOrUpdateFields, List<String> removeFields) {
        if (templateId == null) {
            throw new IllegalArgumentException("Required value for 'templateId' is null");
        } else if (templateId.length() < 1) {
            throw new IllegalArgumentException("String 'templateId' is shorter than 1");
        } else if (Pattern.matches("(/|ptid:).*", templateId)) {
            this.templateId = templateId;
            if (addOrUpdateFields != null) {
                for (PropertyField x : addOrUpdateFields) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'addOrUpdateFields' is null");
                    }
                }
            }
            this.addOrUpdateFields = addOrUpdateFields;
            if (removeFields != null) {
                for (String x2 : removeFields) {
                    if (x2 == null) {
                        throw new IllegalArgumentException("An item in list 'removeFields' is null");
                    }
                }
            }
            this.removeFields = removeFields;
        } else {
            throw new IllegalArgumentException("String 'templateId' does not match pattern");
        }
    }

    public PropertyGroupUpdate(String templateId) {
        this(templateId, null, null);
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public List<PropertyField> getAddOrUpdateFields() {
        return this.addOrUpdateFields;
    }

    public List<String> getRemoveFields() {
        return this.removeFields;
    }

    public static Builder newBuilder(String templateId) {
        return new Builder(templateId);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.templateId, this.addOrUpdateFields, this.removeFields});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        PropertyGroupUpdate other = (PropertyGroupUpdate) obj;
        if ((this.templateId == other.templateId || this.templateId.equals(other.templateId)) && (this.addOrUpdateFields == other.addOrUpdateFields || (this.addOrUpdateFields != null && this.addOrUpdateFields.equals(other.addOrUpdateFields)))) {
            if (this.removeFields == other.removeFields) {
                return true;
            }
            if (this.removeFields != null && this.removeFields.equals(other.removeFields)) {
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
