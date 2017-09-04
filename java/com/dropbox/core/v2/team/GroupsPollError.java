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

public enum GroupsPollError {
    INVALID_ASYNC_JOB_ID,
    INTERNAL_ERROR,
    OTHER,
    ACCESS_DENIED;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$GroupsPollError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$team$GroupsPollError = new int[GroupsPollError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupsPollError[GroupsPollError.INVALID_ASYNC_JOB_ID.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupsPollError[GroupsPollError.INTERNAL_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupsPollError[GroupsPollError.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupsPollError[GroupsPollError.ACCESS_DENIED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static class Serializer extends UnionSerializer<GroupsPollError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(GroupsPollError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$GroupsPollError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("invalid_async_job_id");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("internal_error");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("other");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("access_denied");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value);
            }
        }

        public GroupsPollError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            GroupsPollError value;
            if ("invalid_async_job_id".equals(tag)) {
                value = GroupsPollError.INVALID_ASYNC_JOB_ID;
            } else if ("internal_error".equals(tag)) {
                value = GroupsPollError.INTERNAL_ERROR;
            } else if ("other".equals(tag)) {
                value = GroupsPollError.OTHER;
            } else if ("access_denied".equals(tag)) {
                value = GroupsPollError.ACCESS_DENIED;
            } else {
                throw new JsonParseException(p, "Unknown tag: " + tag);
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
