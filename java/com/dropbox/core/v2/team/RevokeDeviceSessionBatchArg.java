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

class RevokeDeviceSessionBatchArg {
    protected final List<RevokeDeviceSessionArg> revokeDevices;

    static class Serializer extends StructSerializer<RevokeDeviceSessionBatchArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RevokeDeviceSessionBatchArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("revoke_devices");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.revokeDevices, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public RevokeDeviceSessionBatchArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<RevokeDeviceSessionArg> f_revokeDevices = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("revoke_devices".equals(field)) {
                        f_revokeDevices = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_revokeDevices == null) {
                    throw new JsonParseException(p, "Required field \"revoke_devices\" missing.");
                }
                RevokeDeviceSessionBatchArg value = new RevokeDeviceSessionBatchArg(f_revokeDevices);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public RevokeDeviceSessionBatchArg(List<RevokeDeviceSessionArg> revokeDevices) {
        if (revokeDevices == null) {
            throw new IllegalArgumentException("Required value for 'revokeDevices' is null");
        }
        for (RevokeDeviceSessionArg x : revokeDevices) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'revokeDevices' is null");
            }
        }
        this.revokeDevices = revokeDevices;
    }

    public List<RevokeDeviceSessionArg> getRevokeDevices() {
        return this.revokeDevices;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.revokeDevices});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        RevokeDeviceSessionBatchArg other = (RevokeDeviceSessionBatchArg) obj;
        if (this.revokeDevices == other.revokeDevices || this.revokeDevices.equals(other.revokeDevices)) {
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
