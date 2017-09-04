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

public enum TeamFolderAccessError {
    INVALID_TEAM_FOLDER_ID,
    NO_ACCESS,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$TeamFolderAccessError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$team$TeamFolderAccessError = new int[TeamFolderAccessError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamFolderAccessError[TeamFolderAccessError.INVALID_TEAM_FOLDER_ID.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamFolderAccessError[TeamFolderAccessError.NO_ACCESS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<TeamFolderAccessError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(TeamFolderAccessError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$TeamFolderAccessError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("invalid_team_folder_id");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("no_access");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public TeamFolderAccessError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            TeamFolderAccessError value;
            if ("invalid_team_folder_id".equals(tag)) {
                value = TeamFolderAccessError.INVALID_TEAM_FOLDER_ID;
            } else if ("no_access".equals(tag)) {
                value = TeamFolderAccessError.NO_ACCESS;
            } else {
                value = TeamFolderAccessError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
