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

public enum GroupDeleteError {
    GROUP_NOT_FOUND,
    OTHER,
    SYSTEM_MANAGED_GROUP_DISALLOWED,
    GROUP_ALREADY_DELETED;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$GroupDeleteError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$team$GroupDeleteError = new int[GroupDeleteError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupDeleteError[GroupDeleteError.GROUP_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupDeleteError[GroupDeleteError.OTHER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupDeleteError[GroupDeleteError.SYSTEM_MANAGED_GROUP_DISALLOWED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupDeleteError[GroupDeleteError.GROUP_ALREADY_DELETED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static class Serializer extends UnionSerializer<GroupDeleteError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(GroupDeleteError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$GroupDeleteError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("group_not_found");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("other");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("system_managed_group_disallowed");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("group_already_deleted");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value);
            }
        }

        public GroupDeleteError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            GroupDeleteError value;
            if ("group_not_found".equals(tag)) {
                value = GroupDeleteError.GROUP_NOT_FOUND;
            } else if ("other".equals(tag)) {
                value = GroupDeleteError.OTHER;
            } else if ("system_managed_group_disallowed".equals(tag)) {
                value = GroupDeleteError.SYSTEM_MANAGED_GROUP_DISALLOWED;
            } else if ("group_already_deleted".equals(tag)) {
                value = GroupDeleteError.GROUP_ALREADY_DELETED;
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
