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

public enum GroupsGetInfoError {
    GROUP_NOT_ON_TEAM,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$GroupsGetInfoError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$team$GroupsGetInfoError = new int[GroupsGetInfoError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupsGetInfoError[GroupsGetInfoError.GROUP_NOT_ON_TEAM.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    static class Serializer extends UnionSerializer<GroupsGetInfoError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(GroupsGetInfoError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$GroupsGetInfoError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("group_not_on_team");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public GroupsGetInfoError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            GroupsGetInfoError value;
            if ("group_not_on_team".equals(tag)) {
                value = GroupsGetInfoError.GROUP_NOT_ON_TEAM;
            } else {
                value = GroupsGetInfoError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
