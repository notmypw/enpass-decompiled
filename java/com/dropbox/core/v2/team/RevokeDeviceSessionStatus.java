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

public class RevokeDeviceSessionStatus {
    protected final RevokeDeviceSessionError errorType;
    protected final boolean success;

    static class Serializer extends StructSerializer<RevokeDeviceSessionStatus> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RevokeDeviceSessionStatus value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("success");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.success), g);
            if (value.errorType != null) {
                g.writeFieldName("error_type");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.errorType, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public RevokeDeviceSessionStatus deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Boolean f_success = null;
                RevokeDeviceSessionError f_errorType = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("success".equals(field)) {
                        f_success = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("error_type".equals(field)) {
                        f_errorType = (RevokeDeviceSessionError) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_success == null) {
                    throw new JsonParseException(p, "Required field \"success\" missing.");
                }
                RevokeDeviceSessionStatus value = new RevokeDeviceSessionStatus(f_success.booleanValue(), f_errorType);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public RevokeDeviceSessionStatus(boolean success, RevokeDeviceSessionError errorType) {
        this.success = success;
        this.errorType = errorType;
    }

    public RevokeDeviceSessionStatus(boolean success) {
        this(success, null);
    }

    public boolean getSuccess() {
        return this.success;
    }

    public RevokeDeviceSessionError getErrorType() {
        return this.errorType;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Boolean.valueOf(this.success), this.errorType});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        RevokeDeviceSessionStatus other = (RevokeDeviceSessionStatus) obj;
        if (this.success == other.success) {
            if (this.errorType == other.errorType) {
                return true;
            }
            if (this.errorType != null && this.errorType.equals(other.errorType)) {
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
