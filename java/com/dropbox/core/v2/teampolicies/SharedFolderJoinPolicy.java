package com.dropbox.core.v2.teampolicies;

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

public enum SharedFolderJoinPolicy {
    FROM_TEAM_ONLY,
    FROM_ANYONE,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$teampolicies$SharedFolderJoinPolicy = null;

        static {
            $SwitchMap$com$dropbox$core$v2$teampolicies$SharedFolderJoinPolicy = new int[SharedFolderJoinPolicy.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$teampolicies$SharedFolderJoinPolicy[SharedFolderJoinPolicy.FROM_TEAM_ONLY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$teampolicies$SharedFolderJoinPolicy[SharedFolderJoinPolicy.FROM_ANYONE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<SharedFolderJoinPolicy> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(SharedFolderJoinPolicy value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$teampolicies$SharedFolderJoinPolicy[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("from_team_only");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("from_anyone");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public SharedFolderJoinPolicy deserialize(JsonParser p) throws IOException, JsonParseException {
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
            SharedFolderJoinPolicy value;
            if ("from_team_only".equals(tag)) {
                value = SharedFolderJoinPolicy.FROM_TEAM_ONLY;
            } else if ("from_anyone".equals(tag)) {
                value = SharedFolderJoinPolicy.FROM_ANYONE;
            } else {
                value = SharedFolderJoinPolicy.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
