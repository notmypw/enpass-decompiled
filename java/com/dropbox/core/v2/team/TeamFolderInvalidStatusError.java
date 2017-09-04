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

public enum TeamFolderInvalidStatusError {
    ACTIVE,
    ARCHIVED,
    ARCHIVE_IN_PROGRESS,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$TeamFolderInvalidStatusError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$team$TeamFolderInvalidStatusError = new int[TeamFolderInvalidStatusError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamFolderInvalidStatusError[TeamFolderInvalidStatusError.ACTIVE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamFolderInvalidStatusError[TeamFolderInvalidStatusError.ARCHIVED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamFolderInvalidStatusError[TeamFolderInvalidStatusError.ARCHIVE_IN_PROGRESS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<TeamFolderInvalidStatusError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(TeamFolderInvalidStatusError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$TeamFolderInvalidStatusError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("active");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("archived");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("archive_in_progress");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public TeamFolderInvalidStatusError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            TeamFolderInvalidStatusError value;
            if ("active".equals(tag)) {
                value = TeamFolderInvalidStatusError.ACTIVE;
            } else if ("archived".equals(tag)) {
                value = TeamFolderInvalidStatusError.ARCHIVED;
            } else if ("archive_in_progress".equals(tag)) {
                value = TeamFolderInvalidStatusError.ARCHIVE_IN_PROGRESS;
            } else {
                value = TeamFolderInvalidStatusError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
