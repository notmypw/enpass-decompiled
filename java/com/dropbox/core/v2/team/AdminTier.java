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

public enum AdminTier {
    TEAM_ADMIN,
    USER_MANAGEMENT_ADMIN,
    SUPPORT_ADMIN,
    MEMBER_ONLY;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$AdminTier = null;

        static {
            $SwitchMap$com$dropbox$core$v2$team$AdminTier = new int[AdminTier.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$team$AdminTier[AdminTier.TEAM_ADMIN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$AdminTier[AdminTier.USER_MANAGEMENT_ADMIN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$AdminTier[AdminTier.SUPPORT_ADMIN.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$AdminTier[AdminTier.MEMBER_ONLY.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static class Serializer extends UnionSerializer<AdminTier> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(AdminTier value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$AdminTier[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("team_admin");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("user_management_admin");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("support_admin");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("member_only");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value);
            }
        }

        public AdminTier deserialize(JsonParser p) throws IOException, JsonParseException {
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
            AdminTier value;
            if ("team_admin".equals(tag)) {
                value = AdminTier.TEAM_ADMIN;
            } else if ("user_management_admin".equals(tag)) {
                value = AdminTier.USER_MANAGEMENT_ADMIN;
            } else if ("support_admin".equals(tag)) {
                value = AdminTier.SUPPORT_ADMIN;
            } else if ("member_only".equals(tag)) {
                value = AdminTier.MEMBER_ONLY;
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
