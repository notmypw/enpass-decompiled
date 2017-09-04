package com.dropbox.core.v2.sharing;

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
import java.util.regex.Pattern;

class CreateSharedLinkWithSettingsArg {
    protected final String path;
    protected final SharedLinkSettings settings;

    static class Serializer extends StructSerializer<CreateSharedLinkWithSettingsArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(CreateSharedLinkWithSettingsArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxMetadataUpdateTask.PATH);
            StoneSerializers.string().serialize(value.path, g);
            if (value.settings != null) {
                g.writeFieldName("settings");
                StoneSerializers.nullableStruct(Serializer.INSTANCE).serialize(value.settings, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public CreateSharedLinkWithSettingsArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_path = null;
                SharedLinkSettings f_settings = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxMetadataUpdateTask.PATH.equals(field)) {
                        f_path = (String) StoneSerializers.string().deserialize(p);
                    } else if ("settings".equals(field)) {
                        f_settings = (SharedLinkSettings) StoneSerializers.nullableStruct(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_path == null) {
                    throw new JsonParseException(p, "Required field \"path\" missing.");
                }
                CreateSharedLinkWithSettingsArg value = new CreateSharedLinkWithSettingsArg(f_path, f_settings);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public CreateSharedLinkWithSettingsArg(String path, SharedLinkSettings settings) {
        if (path == null) {
            throw new IllegalArgumentException("Required value for 'path' is null");
        } else if (Pattern.matches("(/(.|[\\r\\n])*|id:.*)|(rev:[0-9a-f]{9,})|(ns:[0-9]+(/.*)?)", path)) {
            this.path = path;
            this.settings = settings;
        } else {
            throw new IllegalArgumentException("String 'path' does not match pattern");
        }
    }

    public CreateSharedLinkWithSettingsArg(String path) {
        this(path, null);
    }

    public String getPath() {
        return this.path;
    }

    public SharedLinkSettings getSettings() {
        return this.settings;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.path, this.settings});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        CreateSharedLinkWithSettingsArg other = (CreateSharedLinkWithSettingsArg) obj;
        if (this.path == other.path || this.path.equals(other.path)) {
            if (this.settings == other.settings) {
                return true;
            }
            if (this.settings != null && this.settings.equals(other.settings)) {
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
