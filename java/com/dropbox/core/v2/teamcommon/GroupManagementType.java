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

public enum GroupManagementType {
    USER_MANAGED,
    COMPANY_MANAGED,
    SYSTEM_MANAGED,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$teamcommon$GroupManagementType = null;

        static {
            $SwitchMap$com$dropbox$core$v2$teamcommon$GroupManagementType = new int[GroupManagementType.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$teamcommon$GroupManagementType[GroupManagementType.USER_MANAGED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$teamcommon$GroupManagementType[GroupManagementType.COMPANY_MANAGED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$teamcommon$GroupManagementType[GroupManagementType.SYSTEM_MANAGED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public static class Serializer extends UnionSerializer<GroupManagementType> {
        public static final Serializer INSTANCE = null;

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(GroupManagementType value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$teamcommon$GroupManagementType[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("user_managed");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("company_managed");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("system_managed");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public GroupManagementType deserialize(JsonParser p) throws IOException, JsonParseException {
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
            GroupManagementType value;
            if ("user_managed".equals(tag)) {
                value = GroupManagementType.USER_MANAGED;
            } else if ("company_managed".equals(tag)) {
                value = GroupManagementType.COMPANY_MANAGED;
            } else if ("system_managed".equals(tag)) {
                value = GroupManagementType.SYSTEM_MANAGED;
            } else {
                value = GroupManagementType.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
