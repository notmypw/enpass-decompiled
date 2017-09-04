package com.dropbox.core.v2.team;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.UnionSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import in.sinew.enpass.IRemoteStorage;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;
import net.sqlcipher.database.SQLiteDatabase;

public final class MemberAddResult {
    private final Tag _tag;
    private final String duplicateExternalMemberIdValue;
    private final String duplicateMemberPersistentIdValue;
    private final String freeTeamMemberLimitReachedValue;
    private final String persistentIdDisabledValue;
    private final TeamMemberInfo successValue;
    private final String teamLicenseLimitValue;
    private final String userAlreadyOnTeamValue;
    private final String userAlreadyPairedValue;
    private final String userCreationFailedValue;
    private final String userMigrationFailedValue;
    private final String userOnAnotherTeamValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$MemberAddResult$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$team$MemberAddResult$Tag[Tag.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MemberAddResult$Tag[Tag.TEAM_LICENSE_LIMIT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MemberAddResult$Tag[Tag.FREE_TEAM_MEMBER_LIMIT_REACHED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MemberAddResult$Tag[Tag.USER_ALREADY_ON_TEAM.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MemberAddResult$Tag[Tag.USER_ON_ANOTHER_TEAM.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MemberAddResult$Tag[Tag.USER_ALREADY_PAIRED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MemberAddResult$Tag[Tag.USER_MIGRATION_FAILED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MemberAddResult$Tag[Tag.DUPLICATE_EXTERNAL_MEMBER_ID.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MemberAddResult$Tag[Tag.DUPLICATE_MEMBER_PERSISTENT_ID.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MemberAddResult$Tag[Tag.PERSISTENT_ID_DISABLED.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MemberAddResult$Tag[Tag.USER_CREATION_FAILED.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
        }
    }

    static class Serializer extends UnionSerializer<MemberAddResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(MemberAddResult value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$MemberAddResult$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("success", g);
                    Serializer.INSTANCE.serialize(value.successValue, g, true);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("team_license_limit", g);
                    g.writeFieldName("team_license_limit");
                    StoneSerializers.string().serialize(value.teamLicenseLimitValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeStartObject();
                    writeTag("free_team_member_limit_reached", g);
                    g.writeFieldName("free_team_member_limit_reached");
                    StoneSerializers.string().serialize(value.freeTeamMemberLimitReachedValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeStartObject();
                    writeTag("user_already_on_team", g);
                    g.writeFieldName("user_already_on_team");
                    StoneSerializers.string().serialize(value.userAlreadyOnTeamValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeStartObject();
                    writeTag("user_on_another_team", g);
                    g.writeFieldName("user_on_another_team");
                    StoneSerializers.string().serialize(value.userOnAnotherTeamValue, g);
                    g.writeEndObject();
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeStartObject();
                    writeTag("user_already_paired", g);
                    g.writeFieldName("user_already_paired");
                    StoneSerializers.string().serialize(value.userAlreadyPairedValue, g);
                    g.writeEndObject();
                    return;
                case IRemoteStorage.PIN /*7*/:
                    g.writeStartObject();
                    writeTag("user_migration_failed", g);
                    g.writeFieldName("user_migration_failed");
                    StoneSerializers.string().serialize(value.userMigrationFailedValue, g);
                    g.writeEndObject();
                    return;
                case IRemoteStorage.FOLDER_REMOTE /*8*/:
                    g.writeStartObject();
                    writeTag("duplicate_external_member_id", g);
                    g.writeFieldName("duplicate_external_member_id");
                    StoneSerializers.string().serialize(value.duplicateExternalMemberIdValue, g);
                    g.writeEndObject();
                    return;
                case IRemoteStorage.WEBDAV_REMOTE /*9*/:
                    g.writeStartObject();
                    writeTag("duplicate_member_persistent_id", g);
                    g.writeFieldName("duplicate_member_persistent_id");
                    StoneSerializers.string().serialize(value.duplicateMemberPersistentIdValue, g);
                    g.writeEndObject();
                    return;
                case IRemoteStorage.WEBDAV_REMOTE_USERNAME /*10*/:
                    g.writeStartObject();
                    writeTag("persistent_id_disabled", g);
                    g.writeFieldName("persistent_id_disabled");
                    StoneSerializers.string().serialize(value.persistentIdDisabledValue, g);
                    g.writeEndObject();
                    return;
                case IRemoteStorage.WEBDAV_REMOTE_PASSWORD /*11*/:
                    g.writeStartObject();
                    writeTag("user_creation_failed", g);
                    g.writeFieldName("user_creation_failed");
                    StoneSerializers.string().serialize(value.userCreationFailedValue, g);
                    g.writeEndObject();
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public MemberAddResult deserialize(JsonParser p) throws IOException, JsonParseException {
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
            MemberAddResult value;
            if ("success".equals(tag)) {
                value = MemberAddResult.success(Serializer.INSTANCE.deserialize(p, true));
            } else if ("team_license_limit".equals(tag)) {
                StoneSerializer.expectField("team_license_limit", p);
                value = MemberAddResult.teamLicenseLimit((String) StoneSerializers.string().deserialize(p));
            } else if ("free_team_member_limit_reached".equals(tag)) {
                StoneSerializer.expectField("free_team_member_limit_reached", p);
                value = MemberAddResult.freeTeamMemberLimitReached((String) StoneSerializers.string().deserialize(p));
            } else if ("user_already_on_team".equals(tag)) {
                StoneSerializer.expectField("user_already_on_team", p);
                value = MemberAddResult.userAlreadyOnTeam((String) StoneSerializers.string().deserialize(p));
            } else if ("user_on_another_team".equals(tag)) {
                StoneSerializer.expectField("user_on_another_team", p);
                value = MemberAddResult.userOnAnotherTeam((String) StoneSerializers.string().deserialize(p));
            } else if ("user_already_paired".equals(tag)) {
                StoneSerializer.expectField("user_already_paired", p);
                value = MemberAddResult.userAlreadyPaired((String) StoneSerializers.string().deserialize(p));
            } else if ("user_migration_failed".equals(tag)) {
                StoneSerializer.expectField("user_migration_failed", p);
                value = MemberAddResult.userMigrationFailed((String) StoneSerializers.string().deserialize(p));
            } else if ("duplicate_external_member_id".equals(tag)) {
                StoneSerializer.expectField("duplicate_external_member_id", p);
                value = MemberAddResult.duplicateExternalMemberId((String) StoneSerializers.string().deserialize(p));
            } else if ("duplicate_member_persistent_id".equals(tag)) {
                StoneSerializer.expectField("duplicate_member_persistent_id", p);
                value = MemberAddResult.duplicateMemberPersistentId((String) StoneSerializers.string().deserialize(p));
            } else if ("persistent_id_disabled".equals(tag)) {
                StoneSerializer.expectField("persistent_id_disabled", p);
                value = MemberAddResult.persistentIdDisabled((String) StoneSerializers.string().deserialize(p));
            } else if ("user_creation_failed".equals(tag)) {
                StoneSerializer.expectField("user_creation_failed", p);
                value = MemberAddResult.userCreationFailed((String) StoneSerializers.string().deserialize(p));
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

    public enum Tag {
        SUCCESS,
        TEAM_LICENSE_LIMIT,
        FREE_TEAM_MEMBER_LIMIT_REACHED,
        USER_ALREADY_ON_TEAM,
        USER_ON_ANOTHER_TEAM,
        USER_ALREADY_PAIRED,
        USER_MIGRATION_FAILED,
        DUPLICATE_EXTERNAL_MEMBER_ID,
        DUPLICATE_MEMBER_PERSISTENT_ID,
        PERSISTENT_ID_DISABLED,
        USER_CREATION_FAILED
    }

    private MemberAddResult(Tag _tag, TeamMemberInfo successValue, String teamLicenseLimitValue, String freeTeamMemberLimitReachedValue, String userAlreadyOnTeamValue, String userOnAnotherTeamValue, String userAlreadyPairedValue, String userMigrationFailedValue, String duplicateExternalMemberIdValue, String duplicateMemberPersistentIdValue, String persistentIdDisabledValue, String userCreationFailedValue) {
        this._tag = _tag;
        this.successValue = successValue;
        this.teamLicenseLimitValue = teamLicenseLimitValue;
        this.freeTeamMemberLimitReachedValue = freeTeamMemberLimitReachedValue;
        this.userAlreadyOnTeamValue = userAlreadyOnTeamValue;
        this.userOnAnotherTeamValue = userOnAnotherTeamValue;
        this.userAlreadyPairedValue = userAlreadyPairedValue;
        this.userMigrationFailedValue = userMigrationFailedValue;
        this.duplicateExternalMemberIdValue = duplicateExternalMemberIdValue;
        this.duplicateMemberPersistentIdValue = duplicateMemberPersistentIdValue;
        this.persistentIdDisabledValue = persistentIdDisabledValue;
        this.userCreationFailedValue = userCreationFailedValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isSuccess() {
        return this._tag == Tag.SUCCESS;
    }

    public static MemberAddResult success(TeamMemberInfo value) {
        if (value != null) {
            return new MemberAddResult(Tag.SUCCESS, value, null, null, null, null, null, null, null, null, null, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public TeamMemberInfo getSuccessValue() {
        if (this._tag == Tag.SUCCESS) {
            return this.successValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.SUCCESS, but was Tag." + this._tag.name());
    }

    public boolean isTeamLicenseLimit() {
        return this._tag == Tag.TEAM_LICENSE_LIMIT;
    }

    public static MemberAddResult teamLicenseLimit(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() > 255) {
            throw new IllegalArgumentException("String is longer than 255");
        } else if (Pattern.matches("^['&A-Za-z0-9._%+-]+@[A-Za-z0-9-][A-Za-z0-9.-]*.[A-Za-z]{2,15}$", value)) {
            return new MemberAddResult(Tag.TEAM_LICENSE_LIMIT, null, value, null, null, null, null, null, null, null, null, null);
        } else {
            throw new IllegalArgumentException("String does not match pattern");
        }
    }

    public String getTeamLicenseLimitValue() {
        if (this._tag == Tag.TEAM_LICENSE_LIMIT) {
            return this.teamLicenseLimitValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.TEAM_LICENSE_LIMIT, but was Tag." + this._tag.name());
    }

    public boolean isFreeTeamMemberLimitReached() {
        return this._tag == Tag.FREE_TEAM_MEMBER_LIMIT_REACHED;
    }

    public static MemberAddResult freeTeamMemberLimitReached(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() > 255) {
            throw new IllegalArgumentException("String is longer than 255");
        } else if (Pattern.matches("^['&A-Za-z0-9._%+-]+@[A-Za-z0-9-][A-Za-z0-9.-]*.[A-Za-z]{2,15}$", value)) {
            return new MemberAddResult(Tag.FREE_TEAM_MEMBER_LIMIT_REACHED, null, null, value, null, null, null, null, null, null, null, null);
        } else {
            throw new IllegalArgumentException("String does not match pattern");
        }
    }

    public String getFreeTeamMemberLimitReachedValue() {
        if (this._tag == Tag.FREE_TEAM_MEMBER_LIMIT_REACHED) {
            return this.freeTeamMemberLimitReachedValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.FREE_TEAM_MEMBER_LIMIT_REACHED, but was Tag." + this._tag.name());
    }

    public boolean isUserAlreadyOnTeam() {
        return this._tag == Tag.USER_ALREADY_ON_TEAM;
    }

    public static MemberAddResult userAlreadyOnTeam(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() > 255) {
            throw new IllegalArgumentException("String is longer than 255");
        } else if (Pattern.matches("^['&A-Za-z0-9._%+-]+@[A-Za-z0-9-][A-Za-z0-9.-]*.[A-Za-z]{2,15}$", value)) {
            return new MemberAddResult(Tag.USER_ALREADY_ON_TEAM, null, null, null, value, null, null, null, null, null, null, null);
        } else {
            throw new IllegalArgumentException("String does not match pattern");
        }
    }

    public String getUserAlreadyOnTeamValue() {
        if (this._tag == Tag.USER_ALREADY_ON_TEAM) {
            return this.userAlreadyOnTeamValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.USER_ALREADY_ON_TEAM, but was Tag." + this._tag.name());
    }

    public boolean isUserOnAnotherTeam() {
        return this._tag == Tag.USER_ON_ANOTHER_TEAM;
    }

    public static MemberAddResult userOnAnotherTeam(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() > 255) {
            throw new IllegalArgumentException("String is longer than 255");
        } else if (Pattern.matches("^['&A-Za-z0-9._%+-]+@[A-Za-z0-9-][A-Za-z0-9.-]*.[A-Za-z]{2,15}$", value)) {
            return new MemberAddResult(Tag.USER_ON_ANOTHER_TEAM, null, null, null, null, value, null, null, null, null, null, null);
        } else {
            throw new IllegalArgumentException("String does not match pattern");
        }
    }

    public String getUserOnAnotherTeamValue() {
        if (this._tag == Tag.USER_ON_ANOTHER_TEAM) {
            return this.userOnAnotherTeamValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.USER_ON_ANOTHER_TEAM, but was Tag." + this._tag.name());
    }

    public boolean isUserAlreadyPaired() {
        return this._tag == Tag.USER_ALREADY_PAIRED;
    }

    public static MemberAddResult userAlreadyPaired(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() > 255) {
            throw new IllegalArgumentException("String is longer than 255");
        } else if (Pattern.matches("^['&A-Za-z0-9._%+-]+@[A-Za-z0-9-][A-Za-z0-9.-]*.[A-Za-z]{2,15}$", value)) {
            return new MemberAddResult(Tag.USER_ALREADY_PAIRED, null, null, null, null, null, value, null, null, null, null, null);
        } else {
            throw new IllegalArgumentException("String does not match pattern");
        }
    }

    public String getUserAlreadyPairedValue() {
        if (this._tag == Tag.USER_ALREADY_PAIRED) {
            return this.userAlreadyPairedValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.USER_ALREADY_PAIRED, but was Tag." + this._tag.name());
    }

    public boolean isUserMigrationFailed() {
        return this._tag == Tag.USER_MIGRATION_FAILED;
    }

    public static MemberAddResult userMigrationFailed(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() > 255) {
            throw new IllegalArgumentException("String is longer than 255");
        } else if (Pattern.matches("^['&A-Za-z0-9._%+-]+@[A-Za-z0-9-][A-Za-z0-9.-]*.[A-Za-z]{2,15}$", value)) {
            return new MemberAddResult(Tag.USER_MIGRATION_FAILED, null, null, null, null, null, null, value, null, null, null, null);
        } else {
            throw new IllegalArgumentException("String does not match pattern");
        }
    }

    public String getUserMigrationFailedValue() {
        if (this._tag == Tag.USER_MIGRATION_FAILED) {
            return this.userMigrationFailedValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.USER_MIGRATION_FAILED, but was Tag." + this._tag.name());
    }

    public boolean isDuplicateExternalMemberId() {
        return this._tag == Tag.DUPLICATE_EXTERNAL_MEMBER_ID;
    }

    public static MemberAddResult duplicateExternalMemberId(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() > 255) {
            throw new IllegalArgumentException("String is longer than 255");
        } else if (Pattern.matches("^['&A-Za-z0-9._%+-]+@[A-Za-z0-9-][A-Za-z0-9.-]*.[A-Za-z]{2,15}$", value)) {
            return new MemberAddResult(Tag.DUPLICATE_EXTERNAL_MEMBER_ID, null, null, null, null, null, null, null, value, null, null, null);
        } else {
            throw new IllegalArgumentException("String does not match pattern");
        }
    }

    public String getDuplicateExternalMemberIdValue() {
        if (this._tag == Tag.DUPLICATE_EXTERNAL_MEMBER_ID) {
            return this.duplicateExternalMemberIdValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.DUPLICATE_EXTERNAL_MEMBER_ID, but was Tag." + this._tag.name());
    }

    public boolean isDuplicateMemberPersistentId() {
        return this._tag == Tag.DUPLICATE_MEMBER_PERSISTENT_ID;
    }

    public static MemberAddResult duplicateMemberPersistentId(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() > 255) {
            throw new IllegalArgumentException("String is longer than 255");
        } else if (Pattern.matches("^['&A-Za-z0-9._%+-]+@[A-Za-z0-9-][A-Za-z0-9.-]*.[A-Za-z]{2,15}$", value)) {
            return new MemberAddResult(Tag.DUPLICATE_MEMBER_PERSISTENT_ID, null, null, null, null, null, null, null, null, value, null, null);
        } else {
            throw new IllegalArgumentException("String does not match pattern");
        }
    }

    public String getDuplicateMemberPersistentIdValue() {
        if (this._tag == Tag.DUPLICATE_MEMBER_PERSISTENT_ID) {
            return this.duplicateMemberPersistentIdValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.DUPLICATE_MEMBER_PERSISTENT_ID, but was Tag." + this._tag.name());
    }

    public boolean isPersistentIdDisabled() {
        return this._tag == Tag.PERSISTENT_ID_DISABLED;
    }

    public static MemberAddResult persistentIdDisabled(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() > 255) {
            throw new IllegalArgumentException("String is longer than 255");
        } else if (Pattern.matches("^['&A-Za-z0-9._%+-]+@[A-Za-z0-9-][A-Za-z0-9.-]*.[A-Za-z]{2,15}$", value)) {
            return new MemberAddResult(Tag.PERSISTENT_ID_DISABLED, null, null, null, null, null, null, null, null, null, value, null);
        } else {
            throw new IllegalArgumentException("String does not match pattern");
        }
    }

    public String getPersistentIdDisabledValue() {
        if (this._tag == Tag.PERSISTENT_ID_DISABLED) {
            return this.persistentIdDisabledValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.PERSISTENT_ID_DISABLED, but was Tag." + this._tag.name());
    }

    public boolean isUserCreationFailed() {
        return this._tag == Tag.USER_CREATION_FAILED;
    }

    public static MemberAddResult userCreationFailed(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() > 255) {
            throw new IllegalArgumentException("String is longer than 255");
        } else if (Pattern.matches("^['&A-Za-z0-9._%+-]+@[A-Za-z0-9-][A-Za-z0-9.-]*.[A-Za-z]{2,15}$", value)) {
            return new MemberAddResult(Tag.USER_CREATION_FAILED, null, null, null, null, null, null, null, null, null, null, value);
        } else {
            throw new IllegalArgumentException("String does not match pattern");
        }
    }

    public String getUserCreationFailedValue() {
        if (this._tag == Tag.USER_CREATION_FAILED) {
            return this.userCreationFailedValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.USER_CREATION_FAILED, but was Tag." + this._tag.name());
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.successValue, this.teamLicenseLimitValue, this.freeTeamMemberLimitReachedValue, this.userAlreadyOnTeamValue, this.userOnAnotherTeamValue, this.userAlreadyPairedValue, this.userMigrationFailedValue, this.duplicateExternalMemberIdValue, this.duplicateMemberPersistentIdValue, this.persistentIdDisabledValue, this.userCreationFailedValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MemberAddResult)) {
            return false;
        }
        MemberAddResult other = (MemberAddResult) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$MemberAddResult$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.successValue == other.successValue || this.successValue.equals(other.successValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.teamLicenseLimitValue == other.teamLicenseLimitValue || this.teamLicenseLimitValue.equals(other.teamLicenseLimitValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                if (this.freeTeamMemberLimitReachedValue == other.freeTeamMemberLimitReachedValue || this.freeTeamMemberLimitReachedValue.equals(other.freeTeamMemberLimitReachedValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                if (this.userAlreadyOnTeamValue == other.userAlreadyOnTeamValue || this.userAlreadyOnTeamValue.equals(other.userAlreadyOnTeamValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                if (this.userOnAnotherTeamValue == other.userOnAnotherTeamValue || this.userOnAnotherTeamValue.equals(other.userOnAnotherTeamValue)) {
                    return true;
                }
                return false;
            case IRemoteStorage.BOX_REMOTE /*6*/:
                if (this.userAlreadyPairedValue == other.userAlreadyPairedValue || this.userAlreadyPairedValue.equals(other.userAlreadyPairedValue)) {
                    return true;
                }
                return false;
            case IRemoteStorage.PIN /*7*/:
                if (this.userMigrationFailedValue == other.userMigrationFailedValue || this.userMigrationFailedValue.equals(other.userMigrationFailedValue)) {
                    return true;
                }
                return false;
            case IRemoteStorage.FOLDER_REMOTE /*8*/:
                if (this.duplicateExternalMemberIdValue == other.duplicateExternalMemberIdValue || this.duplicateExternalMemberIdValue.equals(other.duplicateExternalMemberIdValue)) {
                    return true;
                }
                return false;
            case IRemoteStorage.WEBDAV_REMOTE /*9*/:
                if (this.duplicateMemberPersistentIdValue == other.duplicateMemberPersistentIdValue || this.duplicateMemberPersistentIdValue.equals(other.duplicateMemberPersistentIdValue)) {
                    return true;
                }
                return false;
            case IRemoteStorage.WEBDAV_REMOTE_USERNAME /*10*/:
                if (this.persistentIdDisabledValue == other.persistentIdDisabledValue || this.persistentIdDisabledValue.equals(other.persistentIdDisabledValue)) {
                    return true;
                }
                return false;
            case IRemoteStorage.WEBDAV_REMOTE_PASSWORD /*11*/:
                if (this.userCreationFailedValue == other.userCreationFailedValue || this.userCreationFailedValue.equals(other.userCreationFailedValue)) {
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    public String toString() {
        return Serializer.INSTANCE.serialize((Object) this, false);
    }

    public String toStringMultiline() {
        return Serializer.INSTANCE.serialize((Object) this, true);
    }
}
