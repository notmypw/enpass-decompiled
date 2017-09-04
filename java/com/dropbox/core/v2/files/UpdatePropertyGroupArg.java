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

class UpdatePropertyGroupArg {
    protected final String path;
    protected final List<PropertyGroupUpdate> updatePropertyGroups;

    static class Serializer extends StructSerializer<UpdatePropertyGroupArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UpdatePropertyGroupArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxMetadataUpdateTask.PATH);
            StoneSerializers.string().serialize(value.path, g);
            g.writeFieldName("update_property_groups");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.updatePropertyGroups, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public UpdatePropertyGroupArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_path = null;
                List<PropertyGroupUpdate> f_updatePropertyGroups = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxMetadataUpdateTask.PATH.equals(field)) {
                        f_path = (String) StoneSerializers.string().deserialize(p);
                    } else if ("update_property_groups".equals(field)) {
                        f_updatePropertyGroups = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_path == null) {
                    throw new JsonParseException(p, "Required field \"path\" missing.");
                } else if (f_updatePropertyGroups == null) {
                    throw new JsonParseException(p, "Required field \"update_property_groups\" missing.");
                } else {
                    UpdatePropertyGroupArg value = new UpdatePropertyGroupArg(f_path, f_updatePropertyGroups);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public UpdatePropertyGroupArg(String path, List<PropertyGroupUpdate> updatePropertyGroups) {
        if (path == null) {
            throw new IllegalArgumentException("Required value for 'path' is null");
        } else if (Pattern.matches("/(.|[\\r\\n])*|id:.*|(ns:[0-9]+(/.*)?)", path)) {
            this.path = path;
            if (updatePropertyGroups == null) {
                throw new IllegalArgumentException("Required value for 'updatePropertyGroups' is null");
            }
            for (PropertyGroupUpdate x : updatePropertyGroups) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'updatePropertyGroups' is null");
                }
            }
            this.updatePropertyGroups = updatePropertyGroups;
        } else {
            throw new IllegalArgumentException("String 'path' does not match pattern");
        }
    }

    public String getPath() {
        return this.path;
    }

    public List<PropertyGroupUpdate> getUpdatePropertyGroups() {
        return this.updatePropertyGroups;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.path, this.updatePropertyGroups});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        UpdatePropertyGroupArg other = (UpdatePropertyGroupArg) obj;
        if ((this.path == other.path || this.path.equals(other.path)) && (this.updatePropertyGroups == other.updatePropertyGroups || this.updatePropertyGroups.equals(other.updatePropertyGroups))) {
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
