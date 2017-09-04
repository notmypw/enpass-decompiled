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

public enum RevokeLinkedAppError {
    APP_NOT_FOUND,
    MEMBER_NOT_FOUND,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$RevokeLinkedAppError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$team$RevokeLinkedAppError = new int[RevokeLinkedAppError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$team$RevokeLinkedAppError[RevokeLinkedAppError.APP_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$RevokeLinkedAppError[RevokeLinkedAppError.MEMBER_NOT_FOUND.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<RevokeLinkedAppError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(RevokeLinkedAppError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$RevokeLinkedAppError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("app_not_found");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("member_not_found");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public RevokeLinkedAppError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            RevokeLinkedAppError value;
            if ("app_not_found".equals(tag)) {
                value = RevokeLinkedAppError.APP_NOT_FOUND;
            } else if ("member_not_found".equals(tag)) {
                value = RevokeLinkedAppError.MEMBER_NOT_FOUND;
            } else {
                value = RevokeLinkedAppError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
