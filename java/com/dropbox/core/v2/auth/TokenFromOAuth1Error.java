package com.dropbox.core.v2.auth;

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

public enum TokenFromOAuth1Error {
    INVALID_OAUTH1_TOKEN_INFO,
    APP_ID_MISMATCH,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$auth$TokenFromOAuth1Error = null;

        static {
            $SwitchMap$com$dropbox$core$v2$auth$TokenFromOAuth1Error = new int[TokenFromOAuth1Error.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$auth$TokenFromOAuth1Error[TokenFromOAuth1Error.INVALID_OAUTH1_TOKEN_INFO.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$auth$TokenFromOAuth1Error[TokenFromOAuth1Error.APP_ID_MISMATCH.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<TokenFromOAuth1Error> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(TokenFromOAuth1Error value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$auth$TokenFromOAuth1Error[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("invalid_oauth1_token_info");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("app_id_mismatch");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public TokenFromOAuth1Error deserialize(JsonParser p) throws IOException, JsonParseException {
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
            TokenFromOAuth1Error value;
            if ("invalid_oauth1_token_info".equals(tag)) {
                value = TokenFromOAuth1Error.INVALID_OAUTH1_TOKEN_INFO;
            } else if ("app_id_mismatch".equals(tag)) {
                value = TokenFromOAuth1Error.APP_ID_MISMATCH;
            } else {
                value = TokenFromOAuth1Error.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
