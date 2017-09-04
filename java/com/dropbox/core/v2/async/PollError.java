package com.dropbox.core.v2.async;

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

public enum PollError {
    INVALID_ASYNC_JOB_ID,
    INTERNAL_ERROR,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$async$PollError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$async$PollError = new int[PollError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$async$PollError[PollError.INVALID_ASYNC_JOB_ID.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$async$PollError[PollError.INTERNAL_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public static class Serializer extends UnionSerializer<PollError> {
        public static final Serializer INSTANCE = null;

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(PollError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$async$PollError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("invalid_async_job_id");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("internal_error");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public PollError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            PollError value;
            if ("invalid_async_job_id".equals(tag)) {
                value = PollError.INVALID_ASYNC_JOB_ID;
            } else if ("internal_error".equals(tag)) {
                value = PollError.INTERNAL_ERROR;
            } else {
                value = PollError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
