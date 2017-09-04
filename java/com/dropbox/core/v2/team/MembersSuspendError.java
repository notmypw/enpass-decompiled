package com.dropbox.core.v2.team;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.UnionSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import in.sinew.enpass.IRemoteStorage;
import java.io.IOException;
import net.sqlcipher.database.SQLiteDatabase;

public enum MembersSuspendError {
    USER_NOT_FOUND,
    USER_NOT_IN_TEAM,
    OTHER,
    SUSPEND_INACTIVE_USER,
    SUSPEND_LAST_ADMIN,
    TEAM_LICENSE_LIMIT;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$MembersSuspendError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$team$MembersSuspendError = new int[MembersSuspendError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersSuspendError[MembersSuspendError.USER_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersSuspendError[MembersSuspendError.USER_NOT_IN_TEAM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersSuspendError[MembersSuspendError.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersSuspendError[MembersSuspendError.SUSPEND_INACTIVE_USER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersSuspendError[MembersSuspendError.SUSPEND_LAST_ADMIN.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersSuspendError[MembersSuspendError.TEAM_LICENSE_LIMIT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    static class Serializer extends UnionSerializer<MembersSuspendError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(MembersSuspendError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$MembersSuspendError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("user_not_found");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("user_not_in_team");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("other");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("suspend_inactive_user");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("suspend_last_admin");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeString("team_license_limit");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value);
            }
        }

        public MembersSuspendError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            MembersSuspendError value;
            if ("user_not_found".equals(tag)) {
                value = MembersSuspendError.USER_NOT_FOUND;
            } else if ("user_not_in_team".equals(tag)) {
                value = MembersSuspendError.USER_NOT_IN_TEAM;
            } else if ("other".equals(tag)) {
                value = MembersSuspendError.OTHER;
            } else if ("suspend_inactive_user".equals(tag)) {
                value = MembersSuspendError.SUSPEND_INACTIVE_USER;
            } else if ("suspend_last_admin".equals(tag)) {
                value = MembersSuspendError.SUSPEND_LAST_ADMIN;
            } else if ("team_license_limit".equals(tag)) {
                value = MembersSuspendError.TEAM_LICENSE_LIMIT;
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
