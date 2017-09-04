package com.dropbox.core.v2.files;

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

class RemovePropertiesArg {
    protected final String path;
    protected final List<String> propertyTemplateIds;

    static class Serializer extends StructSerializer<RemovePropertiesArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RemovePropertiesArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxMetadataUpdateTask.PATH);
            StoneSerializers.string().serialize(value.path, g);
            g.writeFieldName("property_template_ids");
            StoneSerializers.list(StoneSerializers.string()).serialize(value.propertyTemplateIds, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public RemovePropertiesArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_path = null;
                List<String> f_propertyTemplateIds = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxMetadataUpdateTask.PATH.equals(field)) {
                        f_path = (String) StoneSerializers.string().deserialize(p);
                    } else if ("property_template_ids".equals(field)) {
                        f_propertyTemplateIds = (List) StoneSerializers.list(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_path == null) {
                    throw new JsonParseException(p, "Required field \"path\" missing.");
                } else if (f_propertyTemplateIds == null) {
                    throw new JsonParseException(p, "Required field \"property_template_ids\" missing.");
                } else {
                    RemovePropertiesArg value = new RemovePropertiesArg(f_path, f_propertyTemplateIds);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public RemovePropertiesArg(String path, List<String> propertyTemplateIds) {
        if (path == null) {
            throw new IllegalArgumentException("Required value for 'path' is null");
        } else if (Pattern.matches("/(.|[\\r\\n])*|id:.*|(ns:[0-9]+(/.*)?)", path)) {
            this.path = path;
            if (propertyTemplateIds == null) {
                throw new IllegalArgumentException("Required value for 'propertyTemplateIds' is null");
            }
            for (String x : propertyTemplateIds) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'propertyTemplateIds' is null");
                } else if (x.length() < 1) {
                    throw new IllegalArgumentException("Stringan item in list 'propertyTemplateIds' is shorter than 1");
                } else if (!Pattern.matches("(/|ptid:).*", x)) {
                    throw new IllegalArgumentException("Stringan item in list 'propertyTemplateIds' does not match pattern");
                }
            }
            this.propertyTemplateIds = propertyTemplateIds;
        } else {
            throw new IllegalArgumentException("String 'path' does not match pattern");
        }
    }

    public String getPath() {
        return this.path;
    }

    public List<String> getPropertyTemplateIds() {
        return this.propertyTemplateIds;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.path, this.propertyTemplateIds});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        RemovePropertiesArg other = (RemovePropertiesArg) obj;
        if ((this.path == other.path || this.path.equals(other.path)) && (this.propertyTemplateIds == other.propertyTemplateIds || this.propertyTemplateIds.equals(other.propertyTemplateIds))) {
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
