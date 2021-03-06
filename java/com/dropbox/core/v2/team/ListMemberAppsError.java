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

public enum ListMemberAppsError {
    MEMBER_NOT_FOUND,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$ListMemberAppsError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$team$ListMemberAppsError = new int[ListMemberAppsError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$team$ListMemberAppsError[ListMemberAppsError.MEMBER_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    static class Serializer extends UnionSerializer<ListMemberAppsError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(ListMemberAppsError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$ListMemberAppsError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("member_not_found");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public ListMemberAppsError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            ListMemberAppsError value;
            if ("member_not_found".equals(tag)) {
                value = ListMemberAppsError.MEMBER_NOT_FOUND;
            } else {
                value = ListMemberAppsError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
