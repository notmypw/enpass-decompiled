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

public enum GroupCreateError {
    GROUP_NAME_ALREADY_USED,
    GROUP_NAME_INVALID,
    EXTERNAL_ID_ALREADY_IN_USE,
    SYSTEM_MANAGED_GROUP_DISALLOWED,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$GroupCreateError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$team$GroupCreateError = new int[GroupCreateError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupCreateError[GroupCreateError.GROUP_NAME_ALREADY_USED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupCreateError[GroupCreateError.GROUP_NAME_INVALID.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupCreateError[GroupCreateError.EXTERNAL_ID_ALREADY_IN_USE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupCreateError[GroupCreateError.SYSTEM_MANAGED_GROUP_DISALLOWED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static class Serializer extends UnionSerializer<GroupCreateError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(GroupCreateError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$GroupCreateError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("group_name_already_used");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("group_name_invalid");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("external_id_already_in_use");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("system_managed_group_disallowed");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public GroupCreateError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            GroupCreateError value;
            if ("group_name_already_used".equals(tag)) {
                value = GroupCreateError.GROUP_NAME_ALREADY_USED;
            } else if ("group_name_invalid".equals(tag)) {
                value = GroupCreateError.GROUP_NAME_INVALID;
            } else if ("external_id_already_in_use".equals(tag)) {
                value = GroupCreateError.EXTERNAL_ID_ALREADY_IN_USE;
            } else if ("system_managed_group_disallowed".equals(tag)) {
                value = GroupCreateError.SYSTEM_MANAGED_GROUP_DISALLOWED;
            } else {
                value = GroupCreateError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
