package com.dropbox.core.v2.files;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.v2.properties.PropertyGroup;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

class PropertyGroupWithPath {
    protected final String path;
    protected final List<PropertyGroup> propertyGroups;

    static class Serializer extends StructSerializer<PropertyGroupWithPath> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(PropertyGroupWithPath value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxMetadataUpdateTask.PATH);
            StoneSerializers.string().serialize(value.path, g);
            g.writeFieldName("property_groups");
            StoneSerializers.list(com.dropbox.core.v2.properties.PropertyGroup.Serializer.INSTANCE).serialize(value.propertyGroups, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public PropertyGroupWithPath deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_path = null;
                List<PropertyGroup> f_propertyGroups = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxMetadataUpdateTask.PATH.equals(field)) {
                        f_path = (String) StoneSerializers.string().deserialize(p);
                    } else if ("property_groups".equals(field)) {
                        f_propertyGroups = (List) StoneSerializers.list(com.dropbox.core.v2.properties.PropertyGroup.Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_path == null) {
                    throw new JsonParseException(p, "Required field \"path\" missing.");
                } else if (f_propertyGroups == null) {
                    throw new JsonParseException(p, "Required field \"property_groups\" missing.");
                } else {
                    PropertyGroupWithPath value = new PropertyGroupWithPath(f_path, f_propertyGroups);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public PropertyGroupWithPath(String path, List<PropertyGroup> propertyGroups) {
        if (path == null) {
            throw new IllegalArgumentException("Required value for 'path' is null");
        } else if (Pattern.matches("/(.|[\\r\\n])*|id:.*|(ns:[0-9]+(/.*)?)", path)) {
            this.path = path;
            if (propertyGroups == null) {
                throw new IllegalArgumentException("Required value for 'propertyGroups' is null");
            }
            for (PropertyGroup x : propertyGroups) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'propertyGroups' is null");
                }
            }
            this.propertyGroups = propertyGroups;
        } else {
            throw new IllegalArgumentException("String 'path' does not match pattern");
        }
    }

    public String getPath() {
        return this.path;
    }

    public List<PropertyGroup> getPropertyGroups() {
        return this.propertyGroups;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.path, this.propertyGroups});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        PropertyGroupWithPath other = (PropertyGroupWithPath) obj;
        if ((this.path == other.path || this.path.equals(other.path)) && (this.propertyGroups == other.propertyGroups || this.propertyGroups.equals(other.propertyGroups))) {
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
