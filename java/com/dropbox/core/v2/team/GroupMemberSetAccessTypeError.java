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

public enum GroupMemberSetAccessTypeError {
    GROUP_NOT_FOUND,
    OTHER,
    SYSTEM_MANAGED_GROUP_DISALLOWED,
    MEMBER_NOT_IN_GROUP,
    USER_CANNOT_BE_MANAGER_OF_COMPANY_MANAGED_GROUP;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$GroupMemberSetAccessTypeError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$team$GroupMemberSetAccessTypeError = new int[GroupMemberSetAccessTypeError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMemberSetAccessTypeError[GroupMemberSetAccessTypeError.GROUP_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMemberSetAccessTypeError[GroupMemberSetAccessTypeError.OTHER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMemberSetAccessTypeError[GroupMemberSetAccessTypeError.SYSTEM_MANAGED_GROUP_DISALLOWED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMemberSetAccessTypeError[GroupMemberSetAccessTypeError.MEMBER_NOT_IN_GROUP.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMemberSetAccessTypeError[GroupMemberSetAccessTypeError.USER_CANNOT_BE_MANAGER_OF_COMPANY_MANAGED_GROUP.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    static class Serializer extends UnionSerializer<GroupMemberSetAccessTypeError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(GroupMemberSetAccessTypeError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$GroupMemberSetAccessTypeError[value.ordinal()]) {
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
                    g.writeString("member_not_in_group");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("user_cannot_be_manager_of_company_managed_group");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value);
            }
        }

        public GroupMemberSetAccessTypeError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            GroupMemberSetAccessTypeError value;
            if ("group_not_found".equals(tag)) {
                value = GroupMemberSetAccessTypeError.GROUP_NOT_FOUND;
            } else if ("other".equals(tag)) {
                value = GroupMemberSetAccessTypeError.OTHER;
            } else if ("system_managed_group_disallowed".equals(tag)) {
                value = GroupMemberSetAccessTypeError.SYSTEM_MANAGED_GROUP_DISALLOWED;
            } else if ("member_not_in_group".equals(tag)) {
                value = GroupMemberSetAccessTypeError.MEMBER_NOT_IN_GROUP;
            } else if ("user_cannot_be_manager_of_company_managed_group".equals(tag)) {
                value = GroupMemberSetAccessTypeError.USER_CANNOT_BE_MANAGER_OF_COMPANY_MANAGED_GROUP;
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
