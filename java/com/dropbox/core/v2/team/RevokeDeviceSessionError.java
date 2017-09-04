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
import net.sqlcipher.database.SQLiteDatabase;

public enum RevokeDeviceSessionError {
    DEVICE_SESSION_NOT_FOUND,
    MEMBER_NOT_FOUND,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$RevokeDeviceSessionError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$team$RevokeDeviceSessionError = new int[RevokeDeviceSessionError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$team$RevokeDeviceSessionError[RevokeDeviceSessionError.DEVICE_SESSION_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$RevokeDeviceSessionError[RevokeDeviceSessionError.MEMBER_NOT_FOUND.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<RevokeDeviceSessionError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(RevokeDeviceSessionError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$RevokeDeviceSessionError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("device_session_not_found");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("member_not_found");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public RevokeDeviceSessionError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            RevokeDeviceSessionError value;
            if ("device_session_not_found".equals(tag)) {
                value = RevokeDeviceSessionError.DEVICE_SESSION_NOT_FOUND;
            } else if ("member_not_found".equals(tag)) {
                value = RevokeDeviceSessionError.MEMBER_NOT_FOUND;
            } else {
                value = RevokeDeviceSessionError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
