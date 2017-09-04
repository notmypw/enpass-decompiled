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

public enum SharedLinkCreatePolicy {
    DEFAULT_PUBLIC,
    DEFAULT_TEAM_ONLY,
    TEAM_ONLY,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$teampolicies$SharedLinkCreatePolicy = null;

        static {
            $SwitchMap$com$dropbox$core$v2$teampolicies$SharedLinkCreatePolicy = new int[SharedLinkCreatePolicy.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$teampolicies$SharedLinkCreatePolicy[SharedLinkCreatePolicy.DEFAULT_PUBLIC.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$teampolicies$SharedLinkCreatePolicy[SharedLinkCreatePolicy.DEFAULT_TEAM_ONLY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$teampolicies$SharedLinkCreatePolicy[SharedLinkCreatePolicy.TEAM_ONLY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<SharedLinkCreatePolicy> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(SharedLinkCreatePolicy value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$teampolicies$SharedLinkCreatePolicy[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("default_public");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("default_team_only");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("team_only");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public SharedLinkCreatePolicy deserialize(JsonParser p) throws IOException, JsonParseException {
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
            SharedLinkCreatePolicy value;
            if ("default_public".equals(tag)) {
                value = SharedLinkCreatePolicy.DEFAULT_PUBLIC;
            } else if ("default_team_only".equals(tag)) {
                value = SharedLinkCreatePolicy.DEFAULT_TEAM_ONLY;
            } else if ("team_only".equals(tag)) {
                value = SharedLinkCreatePolicy.TEAM_ONLY;
            } else {
                value = SharedLinkCreatePolicy.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
