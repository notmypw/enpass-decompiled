package com.dropbox.core.v2.paper;

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

public enum PaperApiCursorError {
    EXPIRED_CURSOR,
    INVALID_CURSOR,
    WRONG_USER_IN_CURSOR,
    RESET,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$paper$PaperApiCursorError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$paper$PaperApiCursorError = new int[PaperApiCursorError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$paper$PaperApiCursorError[PaperApiCursorError.EXPIRED_CURSOR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$PaperApiCursorError[PaperApiCursorError.INVALID_CURSOR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$PaperApiCursorError[PaperApiCursorError.WRONG_USER_IN_CURSOR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$PaperApiCursorError[PaperApiCursorError.RESET.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static class Serializer extends UnionSerializer<PaperApiCursorError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(PaperApiCursorError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$paper$PaperApiCursorError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("expired_cursor");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("invalid_cursor");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("wrong_user_in_cursor");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("reset");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public PaperApiCursorError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            PaperApiCursorError value;
            if ("expired_cursor".equals(tag)) {
                value = PaperApiCursorError.EXPIRED_CURSOR;
            } else if ("invalid_cursor".equals(tag)) {
                value = PaperApiCursorError.INVALID_CURSOR;
            } else if ("wrong_user_in_cursor".equals(tag)) {
                value = PaperApiCursorError.WRONG_USER_IN_CURSOR;
            } else if ("reset".equals(tag)) {
                value = PaperApiCursorError.RESET;
            } else {
                value = PaperApiCursorError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
