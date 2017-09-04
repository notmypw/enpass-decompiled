package com.dropbox.core.v2.files;

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

public enum DeleteBatchError {
    TOO_MANY_WRITE_OPERATIONS,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$DeleteBatchError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$files$DeleteBatchError = new int[DeleteBatchError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$files$DeleteBatchError[DeleteBatchError.TOO_MANY_WRITE_OPERATIONS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    static class Serializer extends UnionSerializer<DeleteBatchError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(DeleteBatchError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$DeleteBatchError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("too_many_write_operations");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public DeleteBatchError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            DeleteBatchError value;
            if ("too_many_write_operations".equals(tag)) {
                value = DeleteBatchError.TOO_MANY_WRITE_OPERATIONS;
            } else {
                value = DeleteBatchError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
