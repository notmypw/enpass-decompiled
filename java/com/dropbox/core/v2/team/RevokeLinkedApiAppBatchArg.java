package com.dropbox.core.v2.team;

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

class RevokeLinkedApiAppBatchArg {
    protected final List<RevokeLinkedApiAppArg> revokeLinkedApp;

    static class Serializer extends StructSerializer<RevokeLinkedApiAppBatchArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RevokeLinkedApiAppBatchArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("revoke_linked_app");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.revokeLinkedApp, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public RevokeLinkedApiAppBatchArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<RevokeLinkedApiAppArg> f_revokeLinkedApp = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("revoke_linked_app".equals(field)) {
                        f_revokeLinkedApp = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_revokeLinkedApp == null) {
                    throw new JsonParseException(p, "Required field \"revoke_linked_app\" missing.");
                }
                RevokeLinkedApiAppBatchArg value = new RevokeLinkedApiAppBatchArg(f_revokeLinkedApp);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public RevokeLinkedApiAppBatchArg(List<RevokeLinkedApiAppArg> revokeLinkedApp) {
        if (revokeLinkedApp == null) {
            throw new IllegalArgumentException("Required value for 'revokeLinkedApp' is null");
        }
        for (RevokeLinkedApiAppArg x : revokeLinkedApp) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'revokeLinkedApp' is null");
            }
        }
        this.revokeLinkedApp = revokeLinkedApp;
    }

    public List<RevokeLinkedApiAppArg> getRevokeLinkedApp() {
        return this.revokeLinkedApp;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.revokeLinkedApp});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        RevokeLinkedApiAppBatchArg other = (RevokeLinkedApiAppBatchArg) obj;
        if (this.revokeLinkedApp == other.revokeLinkedApp || this.revokeLinkedApp.equals(other.revokeLinkedApp)) {
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
