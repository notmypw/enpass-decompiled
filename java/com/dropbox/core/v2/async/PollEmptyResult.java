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

public enum PollEmptyResult {
    IN_PROGRESS,
    COMPLETE;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$async$PollEmptyResult = null;

        static {
            $SwitchMap$com$dropbox$core$v2$async$PollEmptyResult = new int[PollEmptyResult.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$async$PollEmptyResult[PollEmptyResult.IN_PROGRESS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$async$PollEmptyResult[PollEmptyResult.COMPLETE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public static class Serializer extends UnionSerializer<PollEmptyResult> {
        public static final Serializer INSTANCE = null;

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(PollEmptyResult value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$async$PollEmptyResult[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("in_progress");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("complete");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value);
            }
        }

        public PollEmptyResult deserialize(JsonParser p) throws IOException, JsonParseException {
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
            PollEmptyResult value;
            if ("in_progress".equals(tag)) {
                value = PollEmptyResult.IN_PROGRESS;
            } else if ("complete".equals(tag)) {
                value = PollEmptyResult.COMPLETE;
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
