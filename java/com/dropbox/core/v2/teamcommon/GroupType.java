package com.dropbox.core.v2.teamcommon;

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

public enum GroupType {
    TEAM,
    USER_MANAGED,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$teamcommon$GroupType = null;

        static {
            $SwitchMap$com$dropbox$core$v2$teamcommon$GroupType = new int[GroupType.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$teamcommon$GroupType[GroupType.TEAM.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$teamcommon$GroupType[GroupType.USER_MANAGED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public static class Serializer extends UnionSerializer<GroupType> {
        public static final Serializer INSTANCE = null;

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(GroupType value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$teamcommon$GroupType[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("team");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("user_managed");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public GroupType deserialize(JsonParser p) throws IOException, JsonParseException {
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
            GroupType value;
            if ("team".equals(tag)) {
                value = GroupType.TEAM;
            } else if ("user_managed".equals(tag)) {
                value = GroupType.USER_MANAGED;
            } else {
                value = GroupType.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
