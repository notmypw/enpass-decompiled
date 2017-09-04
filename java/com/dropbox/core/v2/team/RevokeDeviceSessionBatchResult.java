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

public class RevokeDeviceSessionBatchResult {
    protected final List<RevokeDeviceSessionStatus> revokeDevicesStatus;

    static class Serializer extends StructSerializer<RevokeDeviceSessionBatchResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RevokeDeviceSessionBatchResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("revoke_devices_status");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.revokeDevicesStatus, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public RevokeDeviceSessionBatchResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<RevokeDeviceSessionStatus> f_revokeDevicesStatus = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("revoke_devices_status".equals(field)) {
                        f_revokeDevicesStatus = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_revokeDevicesStatus == null) {
                    throw new JsonParseException(p, "Required field \"revoke_devices_status\" missing.");
                }
                RevokeDeviceSessionBatchResult value = new RevokeDeviceSessionBatchResult(f_revokeDevicesStatus);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public RevokeDeviceSessionBatchResult(List<RevokeDeviceSessionStatus> revokeDevicesStatus) {
        if (revokeDevicesStatus == null) {
            throw new IllegalArgumentException("Required value for 'revokeDevicesStatus' is null");
        }
        for (RevokeDeviceSessionStatus x : revokeDevicesStatus) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'revokeDevicesStatus' is null");
            }
        }
        this.revokeDevicesStatus = revokeDevicesStatus;
    }

    public List<RevokeDeviceSessionStatus> getRevokeDevicesStatus() {
        return this.revokeDevicesStatus;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.revokeDevicesStatus});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        RevokeDeviceSessionBatchResult other = (RevokeDeviceSessionBatchResult) obj;
        if (this.revokeDevicesStatus == other.revokeDevicesStatus || this.revokeDevicesStatus.equals(other.revokeDevicesStatus)) {
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
