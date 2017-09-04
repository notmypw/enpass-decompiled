package com.dropbox.core.v2.users;

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

public enum GetAccountError {
    NO_ACCOUNT,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$users$GetAccountError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$users$GetAccountError = new int[GetAccountError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$users$GetAccountError[GetAccountError.NO_ACCOUNT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    static class Serializer extends UnionSerializer<GetAccountError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(GetAccountError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$users$GetAccountError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("no_account");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public GetAccountError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            GetAccountError value;
            if ("no_account".equals(tag)) {
                value = GetAccountError.NO_ACCOUNT;
            } else {
                value = GetAccountError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
