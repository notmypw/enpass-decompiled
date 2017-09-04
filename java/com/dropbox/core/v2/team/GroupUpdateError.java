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

public enum GroupUpdateError {
    GROUP_NOT_FOUND,
    OTHER,
    SYSTEM_MANAGED_GROUP_DISALLOWED,
    GROUP_NAME_ALREADY_USED,
    GROUP_NAME_INVALID,
    EXTERNAL_ID_ALREADY_IN_USE;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$GroupUpdateError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$team$GroupUpdateError = new int[GroupUpdateError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupUpdateError[GroupUpdateError.GROUP_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupUpdateError[GroupUpdateError.OTHER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupUpdateError[GroupUpdateError.SYSTEM_MANAGED_GROUP_DISALLOWED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupUpdateError[GroupUpdateError.GROUP_NAME_ALREADY_USED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupUpdateError[GroupUpdateError.GROUP_NAME_INVALID.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupUpdateError[GroupUpdateError.EXTERNAL_ID_ALREADY_IN_USE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    static class Serializer extends UnionSerializer<GroupUpdateError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(GroupUpdateError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$GroupUpdateError[value.ordinal()]) {
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
                    g.writeString("group_name_already_used");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("group_name_invalid");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeString("external_id_already_in_use");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value);
            }
        }

        public GroupUpdateError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            GroupUpdateError value;
            if ("group_not_found".equals(tag)) {
                value = GroupUpdateError.GROUP_NOT_FOUND;
            } else if ("other".equals(tag)) {
                value = GroupUpdateError.OTHER;
            } else if ("system_managed_group_disallowed".equals(tag)) {
                value = GroupUpdateError.SYSTEM_MANAGED_GROUP_DISALLOWED;
            } else if ("group_name_already_used".equals(tag)) {
                value = GroupUpdateError.GROUP_NAME_ALREADY_USED;
            } else if ("group_name_invalid".equals(tag)) {
                value = GroupUpdateError.GROUP_NAME_INVALID;
            } else if ("external_id_already_in_use".equals(tag)) {
                value = GroupUpdateError.EXTERNAL_ID_ALREADY_IN_USE;
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
