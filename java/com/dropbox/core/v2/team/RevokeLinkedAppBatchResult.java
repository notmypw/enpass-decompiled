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

public class RevokeLinkedAppBatchResult {
    protected final List<RevokeLinkedAppStatus> revokeLinkedAppStatus;

    static class Serializer extends StructSerializer<RevokeLinkedAppBatchResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RevokeLinkedAppBatchResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("revoke_linked_app_status");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.revokeLinkedAppStatus, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public RevokeLinkedAppBatchResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<RevokeLinkedAppStatus> f_revokeLinkedAppStatus = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("revoke_linked_app_status".equals(field)) {
                        f_revokeLinkedAppStatus = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_revokeLinkedAppStatus == null) {
                    throw new JsonParseException(p, "Required field \"revoke_linked_app_status\" missing.");
                }
                RevokeLinkedAppBatchResult value = new RevokeLinkedAppBatchResult(f_revokeLinkedAppStatus);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public RevokeLinkedAppBatchResult(List<RevokeLinkedAppStatus> revokeLinkedAppStatus) {
        if (revokeLinkedAppStatus == null) {
            throw new IllegalArgumentException("Required value for 'revokeLinkedAppStatus' is null");
        }
        for (RevokeLinkedAppStatus x : revokeLinkedAppStatus) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'revokeLinkedAppStatus' is null");
            }
        }
        this.revokeLinkedAppStatus = revokeLinkedAppStatus;
    }

    public List<RevokeLinkedAppStatus> getRevokeLinkedAppStatus() {
        return this.revokeLinkedAppStatus;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.revokeLinkedAppStatus});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        RevokeLinkedAppBatchResult other = (RevokeLinkedAppBatchResult) obj;
        if (this.revokeLinkedAppStatus == other.revokeLinkedAppStatus || this.revokeLinkedAppStatus.equals(other.revokeLinkedAppStatus)) {
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
