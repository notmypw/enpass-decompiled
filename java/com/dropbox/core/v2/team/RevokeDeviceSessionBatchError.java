package com.dropbox.core.v2.team;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.UnionSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;

public enum RevokeDeviceSessionBatchError {
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$RevokeDeviceSessionBatchError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$team$RevokeDeviceSessionBatchError = new int[RevokeDeviceSessionBatchError.values().length];
        }
    }

    static class Serializer extends UnionSerializer<RevokeDeviceSessionBatchError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(RevokeDeviceSessionBatchError value, JsonGenerator g) throws IOException, JsonGenerationException {
            int i = AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$RevokeDeviceSessionBatchError[value.ordinal()];
            g.writeString("other");
        }

        public RevokeDeviceSessionBatchError deserialize(JsonParser p) throws IOException, JsonParseException {
            boolean collapsed;
            String tag;
            if (p.getCurrentToken() == JsonToken.VALUE_STRING) {
                collapsed = true;
                tag = StoneSerializer.getStringValue(p);
                p.nextToken();
            } else {
                collapsed = false;
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                throw new JsonParseException(p, "Required field missing: .tag");
            }
            RevokeDeviceSessionBatchError value = RevokeDeviceSessionBatchError.OTHER;
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
