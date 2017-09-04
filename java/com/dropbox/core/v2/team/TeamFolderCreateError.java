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

public enum TeamFolderCreateError {
    INVALID_FOLDER_NAME,
    FOLDER_NAME_ALREADY_USED,
    FOLDER_NAME_RESERVED,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$TeamFolderCreateError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$team$TeamFolderCreateError = new int[TeamFolderCreateError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamFolderCreateError[TeamFolderCreateError.INVALID_FOLDER_NAME.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamFolderCreateError[TeamFolderCreateError.FOLDER_NAME_ALREADY_USED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamFolderCreateError[TeamFolderCreateError.FOLDER_NAME_RESERVED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<TeamFolderCreateError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(TeamFolderCreateError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$TeamFolderCreateError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("invalid_folder_name");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("folder_name_already_used");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("folder_name_reserved");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public TeamFolderCreateError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            TeamFolderCreateError value;
            if ("invalid_folder_name".equals(tag)) {
                value = TeamFolderCreateError.INVALID_FOLDER_NAME;
            } else if ("folder_name_already_used".equals(tag)) {
                value = TeamFolderCreateError.FOLDER_NAME_ALREADY_USED;
            } else if ("folder_name_reserved".equals(tag)) {
                value = TeamFolderCreateError.FOLDER_NAME_RESERVED;
            } else {
                value = TeamFolderCreateError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
