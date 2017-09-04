package com.dropbox.core.v2.paper;

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

public enum AddPaperDocUserResult {
    SUCCESS,
    UNKNOWN_ERROR,
    SHARING_OUTSIDE_TEAM_DISABLED,
    DAILY_LIMIT_REACHED,
    USER_IS_OWNER,
    FAILED_USER_DATA_RETRIEVAL,
    PERMISSION_ALREADY_GRANTED,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$paper$AddPaperDocUserResult = null;

        static {
            $SwitchMap$com$dropbox$core$v2$paper$AddPaperDocUserResult = new int[AddPaperDocUserResult.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$paper$AddPaperDocUserResult[AddPaperDocUserResult.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$AddPaperDocUserResult[AddPaperDocUserResult.UNKNOWN_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$AddPaperDocUserResult[AddPaperDocUserResult.SHARING_OUTSIDE_TEAM_DISABLED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$AddPaperDocUserResult[AddPaperDocUserResult.DAILY_LIMIT_REACHED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$AddPaperDocUserResult[AddPaperDocUserResult.USER_IS_OWNER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$AddPaperDocUserResult[AddPaperDocUserResult.FAILED_USER_DATA_RETRIEVAL.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$AddPaperDocUserResult[AddPaperDocUserResult.PERMISSION_ALREADY_GRANTED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    static class Serializer extends UnionSerializer<AddPaperDocUserResult> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(AddPaperDocUserResult value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$paper$AddPaperDocUserResult[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("success");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("unknown_error");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("sharing_outside_team_disabled");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("daily_limit_reached");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("user_is_owner");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeString("failed_user_data_retrieval");
                    return;
                case IRemoteStorage.PIN /*7*/:
                    g.writeString("permission_already_granted");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public AddPaperDocUserResult deserialize(JsonParser p) throws IOException, JsonParseException {
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
            AddPaperDocUserResult value;
            if ("success".equals(tag)) {
                value = AddPaperDocUserResult.SUCCESS;
            } else if ("unknown_error".equals(tag)) {
                value = AddPaperDocUserResult.UNKNOWN_ERROR;
            } else if ("sharing_outside_team_disabled".equals(tag)) {
                value = AddPaperDocUserResult.SHARING_OUTSIDE_TEAM_DISABLED;
            } else if ("daily_limit_reached".equals(tag)) {
                value = AddPaperDocUserResult.DAILY_LIMIT_REACHED;
            } else if ("user_is_owner".equals(tag)) {
                value = AddPaperDocUserResult.USER_IS_OWNER;
            } else if ("failed_user_data_retrieval".equals(tag)) {
                value = AddPaperDocUserResult.FAILED_USER_DATA_RETRIEVAL;
            } else if ("permission_already_granted".equals(tag)) {
                value = AddPaperDocUserResult.PERMISSION_ALREADY_GRANTED;
            } else {
                value = AddPaperDocUserResult.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
