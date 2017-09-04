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

public enum MembersSendWelcomeError {
    USER_NOT_FOUND,
    USER_NOT_IN_TEAM,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$MembersSendWelcomeError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$team$MembersSendWelcomeError = new int[MembersSendWelcomeError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersSendWelcomeError[MembersSendWelcomeError.USER_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersSendWelcomeError[MembersSendWelcomeError.USER_NOT_IN_TEAM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<MembersSendWelcomeError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(MembersSendWelcomeError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$MembersSendWelcomeError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("user_not_found");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("user_not_in_team");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public MembersSendWelcomeError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            MembersSendWelcomeError value;
            if ("user_not_found".equals(tag)) {
                value = MembersSendWelcomeError.USER_NOT_FOUND;
            } else if ("user_not_in_team".equals(tag)) {
                value = MembersSendWelcomeError.USER_NOT_IN_TEAM;
            } else {
                value = MembersSendWelcomeError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
