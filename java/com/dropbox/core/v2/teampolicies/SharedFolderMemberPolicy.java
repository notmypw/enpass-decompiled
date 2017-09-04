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

public enum SharedFolderMemberPolicy {
    TEAM,
    ANYONE,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$teampolicies$SharedFolderMemberPolicy = null;

        static {
            $SwitchMap$com$dropbox$core$v2$teampolicies$SharedFolderMemberPolicy = new int[SharedFolderMemberPolicy.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$teampolicies$SharedFolderMemberPolicy[SharedFolderMemberPolicy.TEAM.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$teampolicies$SharedFolderMemberPolicy[SharedFolderMemberPolicy.ANYONE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<SharedFolderMemberPolicy> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(SharedFolderMemberPolicy value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$teampolicies$SharedFolderMemberPolicy[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("team");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("anyone");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public SharedFolderMemberPolicy deserialize(JsonParser p) throws IOException, JsonParseException {
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
            SharedFolderMemberPolicy value;
            if ("team".equals(tag)) {
                value = SharedFolderMemberPolicy.TEAM;
            } else if ("anyone".equals(tag)) {
                value = SharedFolderMemberPolicy.ANYONE;
            } else {
                value = SharedFolderMemberPolicy.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
