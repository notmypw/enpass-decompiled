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

public enum MembersSetProfileError {
    USER_NOT_FOUND,
    USER_NOT_IN_TEAM,
    EXTERNAL_ID_AND_NEW_EXTERNAL_ID_UNSAFE,
    NO_NEW_DATA_SPECIFIED,
    EMAIL_RESERVED_FOR_OTHER_USER,
    EXTERNAL_ID_USED_BY_OTHER_USER,
    SET_PROFILE_DISALLOWED,
    PARAM_CANNOT_BE_EMPTY,
    PERSISTENT_ID_DISABLED,
    PERSISTENT_ID_USED_BY_OTHER_USER,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$MembersSetProfileError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$team$MembersSetProfileError = new int[MembersSetProfileError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersSetProfileError[MembersSetProfileError.USER_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersSetProfileError[MembersSetProfileError.USER_NOT_IN_TEAM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersSetProfileError[MembersSetProfileError.EXTERNAL_ID_AND_NEW_EXTERNAL_ID_UNSAFE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersSetProfileError[MembersSetProfileError.NO_NEW_DATA_SPECIFIED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersSetProfileError[MembersSetProfileError.EMAIL_RESERVED_FOR_OTHER_USER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersSetProfileError[MembersSetProfileError.EXTERNAL_ID_USED_BY_OTHER_USER.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersSetProfileError[MembersSetProfileError.SET_PROFILE_DISALLOWED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersSetProfileError[MembersSetProfileError.PARAM_CANNOT_BE_EMPTY.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersSetProfileError[MembersSetProfileError.PERSISTENT_ID_DISABLED.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersSetProfileError[MembersSetProfileError.PERSISTENT_ID_USED_BY_OTHER_USER.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    static class Serializer extends UnionSerializer<MembersSetProfileError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(MembersSetProfileError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$MembersSetProfileError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("user_not_found");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("user_not_in_team");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("external_id_and_new_external_id_unsafe");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("no_new_data_specified");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("email_reserved_for_other_user");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeString("external_id_used_by_other_user");
                    return;
                case IRemoteStorage.PIN /*7*/:
                    g.writeString("set_profile_disallowed");
                    return;
                case IRemoteStorage.FOLDER_REMOTE /*8*/:
                    g.writeString("param_cannot_be_empty");
                    return;
                case IRemoteStorage.WEBDAV_REMOTE /*9*/:
                    g.writeString("persistent_id_disabled");
                    return;
                case IRemoteStorage.WEBDAV_REMOTE_USERNAME /*10*/:
                    g.writeString("persistent_id_used_by_other_user");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public MembersSetProfileError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            MembersSetProfileError value;
            if ("user_not_found".equals(tag)) {
                value = MembersSetProfileError.USER_NOT_FOUND;
            } else if ("user_not_in_team".equals(tag)) {
                value = MembersSetProfileError.USER_NOT_IN_TEAM;
            } else if ("external_id_and_new_external_id_unsafe".equals(tag)) {
                value = MembersSetProfileError.EXTERNAL_ID_AND_NEW_EXTERNAL_ID_UNSAFE;
            } else if ("no_new_data_specified".equals(tag)) {
                value = MembersSetProfileError.NO_NEW_DATA_SPECIFIED;
            } else if ("email_reserved_for_other_user".equals(tag)) {
                value = MembersSetProfileError.EMAIL_RESERVED_FOR_OTHER_USER;
            } else if ("external_id_used_by_other_user".equals(tag)) {
                value = MembersSetProfileError.EXTERNAL_ID_USED_BY_OTHER_USER;
            } else if ("set_profile_disallowed".equals(tag)) {
                value = MembersSetProfileError.SET_PROFILE_DISALLOWED;
            } else if ("param_cannot_be_empty".equals(tag)) {
                value = MembersSetProfileError.PARAM_CANNOT_BE_EMPTY;
            } else if ("persistent_id_disabled".equals(tag)) {
                value = MembersSetProfileError.PERSISTENT_ID_DISABLED;
            } else if ("persistent_id_used_by_other_user".equals(tag)) {
                value = MembersSetProfileError.PERSISTENT_ID_USED_BY_OTHER_USER;
            } else {
                value = MembersSetProfileError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
