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

public enum MembersRecoverError {
    USER_NOT_FOUND,
    USER_UNRECOVERABLE,
    USER_NOT_IN_TEAM,
    TEAM_LICENSE_LIMIT,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$MembersRecoverError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$team$MembersRecoverError = new int[MembersRecoverError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersRecoverError[MembersRecoverError.USER_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersRecoverError[MembersRecoverError.USER_UNRECOVERABLE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersRecoverError[MembersRecoverError.USER_NOT_IN_TEAM.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersRecoverError[MembersRecoverError.TEAM_LICENSE_LIMIT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static class Serializer extends UnionSerializer<MembersRecoverError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(MembersRecoverError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$MembersRecoverError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("user_not_found");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("user_unrecoverable");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("user_not_in_team");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("team_license_limit");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public MembersRecoverError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            MembersRecoverError value;
            if ("user_not_found".equals(tag)) {
                value = MembersRecoverError.USER_NOT_FOUND;
            } else if ("user_unrecoverable".equals(tag)) {
                value = MembersRecoverError.USER_UNRECOVERABLE;
            } else if ("user_not_in_team".equals(tag)) {
                value = MembersRecoverError.USER_NOT_IN_TEAM;
            } else if ("team_license_limit".equals(tag)) {
                value = MembersRecoverError.TEAM_LICENSE_LIMIT;
            } else {
                value = MembersRecoverError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
