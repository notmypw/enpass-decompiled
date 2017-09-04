package com.dropbox.core.v2.sharing;

import com.box.androidsdk.content.models.BoxSharedLink;
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

class ModifySharedLinkSettingsArgs {
    protected final boolean removeExpiration;
    protected final SharedLinkSettings settings;
    protected final String url;

    static class Serializer extends StructSerializer<ModifySharedLinkSettingsArgs> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ModifySharedLinkSettingsArgs value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxSharedLink.FIELD_URL);
            StoneSerializers.string().serialize(value.url, g);
            g.writeFieldName("settings");
            Serializer.INSTANCE.serialize(value.settings, g);
            g.writeFieldName("remove_expiration");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.removeExpiration), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ModifySharedLinkSettingsArgs deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_url = null;
                SharedLinkSettings f_settings = null;
                Boolean f_removeExpiration = Boolean.valueOf(false);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxSharedLink.FIELD_URL.equals(field)) {
                        f_url = (String) StoneSerializers.string().deserialize(p);
                    } else if ("settings".equals(field)) {
                        f_settings = (SharedLinkSettings) Serializer.INSTANCE.deserialize(p);
                    } else if ("remove_expiration".equals(field)) {
                        f_removeExpiration = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_url == null) {
                    throw new JsonParseException(p, "Required field \"url\" missing.");
                } else if (f_settings == null) {
                    throw new JsonParseException(p, "Required field \"settings\" missing.");
                } else {
                    ModifySharedLinkSettingsArgs value = new ModifySharedLinkSettingsArgs(f_url, f_settings, f_removeExpiration.booleanValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ModifySharedLinkSettingsArgs(String url, SharedLinkSettings settings, boolean removeExpiration) {
        if (url == null) {
            throw new IllegalArgumentException("Required value for 'url' is null");
        }
        this.url = url;
        if (settings == null) {
            throw new IllegalArgumentException("Required value for 'settings' is null");
        }
        this.settings = settings;
        this.removeExpiration = removeExpiration;
    }

    public ModifySharedLinkSettingsArgs(String url, SharedLinkSettings settings) {
        this(url, settings, false);
    }

    public String getUrl() {
        return this.url;
    }

    public SharedLinkSettings getSettings() {
        return this.settings;
    }

    public boolean getRemoveExpiration() {
        return this.removeExpiration;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.url, this.settings, Boolean.valueOf(this.removeExpiration)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ModifySharedLinkSettingsArgs other = (ModifySharedLinkSettingsArgs) obj;
        if ((this.url == other.url || this.url.equals(other.url)) && ((this.settings == other.settings || this.settings.equals(other.settings)) && this.removeExpiration == other.removeExpiration)) {
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
