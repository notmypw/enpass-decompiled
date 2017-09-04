package com.dropbox.core.v2.sharing;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.UnionSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.samsung.android.sdk.pass.SpassFingerprint;
import in.sinew.enpass.IRemoteStorage;
import java.io.IOException;
import net.sqlcipher.database.SQLiteDatabase;

public enum PermissionDeniedReason {
    USER_NOT_SAME_TEAM_AS_OWNER,
    USER_NOT_ALLOWED_BY_OWNER,
    TARGET_IS_INDIRECT_MEMBER,
    TARGET_IS_OWNER,
    TARGET_IS_SELF,
    TARGET_NOT_ACTIVE,
    FOLDER_IS_LIMITED_TEAM_FOLDER,
    OWNER_NOT_ON_TEAM,
    PERMISSION_DENIED,
    RESTRICTED_BY_TEAM,
    USER_ACCOUNT_TYPE,
    USER_NOT_ON_TEAM,
    FOLDER_IS_INSIDE_SHARED_FOLDER,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$PermissionDeniedReason = null;

        static {
            $SwitchMap$com$dropbox$core$v2$sharing$PermissionDeniedReason = new int[PermissionDeniedReason.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$PermissionDeniedReason[PermissionDeniedReason.USER_NOT_SAME_TEAM_AS_OWNER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$PermissionDeniedReason[PermissionDeniedReason.USER_NOT_ALLOWED_BY_OWNER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$PermissionDeniedReason[PermissionDeniedReason.TARGET_IS_INDIRECT_MEMBER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$PermissionDeniedReason[PermissionDeniedReason.TARGET_IS_OWNER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$PermissionDeniedReason[PermissionDeniedReason.TARGET_IS_SELF.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$PermissionDeniedReason[PermissionDeniedReason.TARGET_NOT_ACTIVE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$PermissionDeniedReason[PermissionDeniedReason.FOLDER_IS_LIMITED_TEAM_FOLDER.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$PermissionDeniedReason[PermissionDeniedReason.OWNER_NOT_ON_TEAM.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$PermissionDeniedReason[PermissionDeniedReason.PERMISSION_DENIED.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$PermissionDeniedReason[PermissionDeniedReason.RESTRICTED_BY_TEAM.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$PermissionDeniedReason[PermissionDeniedReason.USER_ACCOUNT_TYPE.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$PermissionDeniedReason[PermissionDeniedReason.USER_NOT_ON_TEAM.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$PermissionDeniedReason[PermissionDeniedReason.FOLDER_IS_INSIDE_SHARED_FOLDER.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
        }
    }

    static class Serializer extends UnionSerializer<PermissionDeniedReason> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(PermissionDeniedReason value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$PermissionDeniedReason[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("user_not_same_team_as_owner");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("user_not_allowed_by_owner");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("target_is_indirect_member");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("target_is_owner");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("target_is_self");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeString("target_not_active");
                    return;
                case IRemoteStorage.PIN /*7*/:
                    g.writeString("folder_is_limited_team_folder");
                    return;
                case IRemoteStorage.FOLDER_REMOTE /*8*/:
                    g.writeString("owner_not_on_team");
                    return;
                case IRemoteStorage.WEBDAV_REMOTE /*9*/:
                    g.writeString("permission_denied");
                    return;
                case IRemoteStorage.WEBDAV_REMOTE_USERNAME /*10*/:
                    g.writeString("restricted_by_team");
                    return;
                case IRemoteStorage.WEBDAV_REMOTE_PASSWORD /*11*/:
                    g.writeString("user_account_type");
                    return;
                case SpassFingerprint.STATUS_QUALITY_FAILED /*12*/:
                    g.writeString("user_not_on_team");
                    return;
                case SpassFingerprint.STATUS_USER_CANCELLED_BY_TOUCH_OUTSIDE /*13*/:
                    g.writeString("folder_is_inside_shared_folder");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public PermissionDeniedReason deserialize(JsonParser p) throws IOException, JsonParseException {
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
            PermissionDeniedReason value;
            if ("user_not_same_team_as_owner".equals(tag)) {
                value = PermissionDeniedReason.USER_NOT_SAME_TEAM_AS_OWNER;
            } else if ("user_not_allowed_by_owner".equals(tag)) {
                value = PermissionDeniedReason.USER_NOT_ALLOWED_BY_OWNER;
            } else if ("target_is_indirect_member".equals(tag)) {
                value = PermissionDeniedReason.TARGET_IS_INDIRECT_MEMBER;
            } else if ("target_is_owner".equals(tag)) {
                value = PermissionDeniedReason.TARGET_IS_OWNER;
            } else if ("target_is_self".equals(tag)) {
                value = PermissionDeniedReason.TARGET_IS_SELF;
            } else if ("target_not_active".equals(tag)) {
                value = PermissionDeniedReason.TARGET_NOT_ACTIVE;
            } else if ("folder_is_limited_team_folder".equals(tag)) {
                value = PermissionDeniedReason.FOLDER_IS_LIMITED_TEAM_FOLDER;
            } else if ("owner_not_on_team".equals(tag)) {
                value = PermissionDeniedReason.OWNER_NOT_ON_TEAM;
            } else if ("permission_denied".equals(tag)) {
                value = PermissionDeniedReason.PERMISSION_DENIED;
            } else if ("restricted_by_team".equals(tag)) {
                value = PermissionDeniedReason.RESTRICTED_BY_TEAM;
            } else if ("user_account_type".equals(tag)) {
                value = PermissionDeniedReason.USER_ACCOUNT_TYPE;
            } else if ("user_not_on_team".equals(tag)) {
                value = PermissionDeniedReason.USER_NOT_ON_TEAM;
            } else if ("folder_is_inside_shared_folder".equals(tag)) {
                value = PermissionDeniedReason.FOLDER_IS_INSIDE_SHARED_FOLDER;
            } else {
                value = PermissionDeniedReason.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
