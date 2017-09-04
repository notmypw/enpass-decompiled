package com.dropbox.core.v2.team;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.UnionSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.github.clans.fab.R;
import com.samsung.android.sdk.pass.SpassFingerprint;
import in.sinew.enpass.IRemoteStorage;
import java.io.IOException;
import net.sqlcipher.database.SQLiteDatabase;

public enum MembersRemoveError {
    USER_NOT_FOUND,
    USER_NOT_IN_TEAM,
    OTHER,
    REMOVE_LAST_ADMIN,
    REMOVED_AND_TRANSFER_DEST_SHOULD_DIFFER,
    REMOVED_AND_TRANSFER_ADMIN_SHOULD_DIFFER,
    TRANSFER_DEST_USER_NOT_FOUND,
    TRANSFER_DEST_USER_NOT_IN_TEAM,
    TRANSFER_ADMIN_USER_NOT_FOUND,
    TRANSFER_ADMIN_USER_NOT_IN_TEAM,
    UNSPECIFIED_TRANSFER_ADMIN_ID,
    TRANSFER_ADMIN_IS_NOT_ADMIN,
    CANNOT_KEEP_ACCOUNT_AND_TRANSFER,
    CANNOT_KEEP_ACCOUNT_AND_DELETE_DATA,
    EMAIL_ADDRESS_TOO_LONG_TO_BE_DISABLED;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$MembersRemoveError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$team$MembersRemoveError = new int[MembersRemoveError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersRemoveError[MembersRemoveError.USER_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersRemoveError[MembersRemoveError.USER_NOT_IN_TEAM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersRemoveError[MembersRemoveError.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersRemoveError[MembersRemoveError.REMOVE_LAST_ADMIN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersRemoveError[MembersRemoveError.REMOVED_AND_TRANSFER_DEST_SHOULD_DIFFER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersRemoveError[MembersRemoveError.REMOVED_AND_TRANSFER_ADMIN_SHOULD_DIFFER.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersRemoveError[MembersRemoveError.TRANSFER_DEST_USER_NOT_FOUND.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersRemoveError[MembersRemoveError.TRANSFER_DEST_USER_NOT_IN_TEAM.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersRemoveError[MembersRemoveError.TRANSFER_ADMIN_USER_NOT_FOUND.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersRemoveError[MembersRemoveError.TRANSFER_ADMIN_USER_NOT_IN_TEAM.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersRemoveError[MembersRemoveError.UNSPECIFIED_TRANSFER_ADMIN_ID.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersRemoveError[MembersRemoveError.TRANSFER_ADMIN_IS_NOT_ADMIN.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersRemoveError[MembersRemoveError.CANNOT_KEEP_ACCOUNT_AND_TRANSFER.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersRemoveError[MembersRemoveError.CANNOT_KEEP_ACCOUNT_AND_DELETE_DATA.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MembersRemoveError[MembersRemoveError.EMAIL_ADDRESS_TOO_LONG_TO_BE_DISABLED.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
        }
    }

    static class Serializer extends UnionSerializer<MembersRemoveError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(MembersRemoveError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$MembersRemoveError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("user_not_found");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("user_not_in_team");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("other");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("remove_last_admin");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("removed_and_transfer_dest_should_differ");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeString("removed_and_transfer_admin_should_differ");
                    return;
                case IRemoteStorage.PIN /*7*/:
                    g.writeString("transfer_dest_user_not_found");
                    return;
                case IRemoteStorage.FOLDER_REMOTE /*8*/:
                    g.writeString("transfer_dest_user_not_in_team");
                    return;
                case IRemoteStorage.WEBDAV_REMOTE /*9*/:
                    g.writeString("transfer_admin_user_not_found");
                    return;
                case IRemoteStorage.WEBDAV_REMOTE_USERNAME /*10*/:
                    g.writeString("transfer_admin_user_not_in_team");
                    return;
                case IRemoteStorage.WEBDAV_REMOTE_PASSWORD /*11*/:
                    g.writeString("unspecified_transfer_admin_id");
                    return;
                case SpassFingerprint.STATUS_QUALITY_FAILED /*12*/:
                    g.writeString("transfer_admin_is_not_admin");
                    return;
                case SpassFingerprint.STATUS_USER_CANCELLED_BY_TOUCH_OUTSIDE /*13*/:
                    g.writeString("cannot_keep_account_and_transfer");
                    return;
                case R.styleable.FloatingActionMenu_menu_labels_colorRipple /*14*/:
                    g.writeString("cannot_keep_account_and_delete_data");
                    return;
                case R.styleable.FloatingActionMenu_menu_labels_cornerRadius /*15*/:
                    g.writeString("email_address_too_long_to_be_disabled");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value);
            }
        }

        public MembersRemoveError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            MembersRemoveError value;
            if ("user_not_found".equals(tag)) {
                value = MembersRemoveError.USER_NOT_FOUND;
            } else if ("user_not_in_team".equals(tag)) {
                value = MembersRemoveError.USER_NOT_IN_TEAM;
            } else if ("other".equals(tag)) {
                value = MembersRemoveError.OTHER;
            } else if ("remove_last_admin".equals(tag)) {
                value = MembersRemoveError.REMOVE_LAST_ADMIN;
            } else if ("removed_and_transfer_dest_should_differ".equals(tag)) {
                value = MembersRemoveError.REMOVED_AND_TRANSFER_DEST_SHOULD_DIFFER;
            } else if ("removed_and_transfer_admin_should_differ".equals(tag)) {
                value = MembersRemoveError.REMOVED_AND_TRANSFER_ADMIN_SHOULD_DIFFER;
            } else if ("transfer_dest_user_not_found".equals(tag)) {
                value = MembersRemoveError.TRANSFER_DEST_USER_NOT_FOUND;
            } else if ("transfer_dest_user_not_in_team".equals(tag)) {
                value = MembersRemoveError.TRANSFER_DEST_USER_NOT_IN_TEAM;
            } else if ("transfer_admin_user_not_found".equals(tag)) {
                value = MembersRemoveError.TRANSFER_ADMIN_USER_NOT_FOUND;
            } else if ("transfer_admin_user_not_in_team".equals(tag)) {
                value = MembersRemoveError.TRANSFER_ADMIN_USER_NOT_IN_TEAM;
            } else if ("unspecified_transfer_admin_id".equals(tag)) {
                value = MembersRemoveError.UNSPECIFIED_TRANSFER_ADMIN_ID;
            } else if ("transfer_admin_is_not_admin".equals(tag)) {
                value = MembersRemoveError.TRANSFER_ADMIN_IS_NOT_ADMIN;
            } else if ("cannot_keep_account_and_transfer".equals(tag)) {
                value = MembersRemoveError.CANNOT_KEEP_ACCOUNT_AND_TRANSFER;
            } else if ("cannot_keep_account_and_delete_data".equals(tag)) {
                value = MembersRemoveError.CANNOT_KEEP_ACCOUNT_AND_DELETE_DATA;
            } else if ("email_address_too_long_to_be_disabled".equals(tag)) {
                value = MembersRemoveError.EMAIL_ADDRESS_TOO_LONG_TO_BE_DISABLED;
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
