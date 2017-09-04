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
import java.util.List;
import net.sqlcipher.database.SQLiteDatabase;

public final class GroupMembersAddError {
    public static final GroupMembersAddError DUPLICATE_USER = new GroupMembersAddError(Tag.DUPLICATE_USER, null, null, null);
    public static final GroupMembersAddError GROUP_NOT_FOUND = new GroupMembersAddError(Tag.GROUP_NOT_FOUND, null, null, null);
    public static final GroupMembersAddError GROUP_NOT_IN_TEAM = new GroupMembersAddError(Tag.GROUP_NOT_IN_TEAM, null, null, null);
    public static final GroupMembersAddError OTHER = new GroupMembersAddError(Tag.OTHER, null, null, null);
    public static final GroupMembersAddError SYSTEM_MANAGED_GROUP_DISALLOWED = new GroupMembersAddError(Tag.SYSTEM_MANAGED_GROUP_DISALLOWED, null, null, null);
    public static final GroupMembersAddError USER_MUST_BE_ACTIVE_TO_BE_OWNER = new GroupMembersAddError(Tag.USER_MUST_BE_ACTIVE_TO_BE_OWNER, null, null, null);
    private final Tag _tag;
    private final List<String> membersNotInTeamValue;
    private final List<String> userCannotBeManagerOfCompanyManagedGroupValue;
    private final List<String> usersNotFoundValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$GroupMembersAddError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMembersAddError$Tag[Tag.GROUP_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMembersAddError$Tag[Tag.OTHER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMembersAddError$Tag[Tag.SYSTEM_MANAGED_GROUP_DISALLOWED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMembersAddError$Tag[Tag.DUPLICATE_USER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMembersAddError$Tag[Tag.GROUP_NOT_IN_TEAM.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMembersAddError$Tag[Tag.MEMBERS_NOT_IN_TEAM.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMembersAddError$Tag[Tag.USERS_NOT_FOUND.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMembersAddError$Tag[Tag.USER_MUST_BE_ACTIVE_TO_BE_OWNER.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMembersAddError$Tag[Tag.USER_CANNOT_BE_MANAGER_OF_COMPANY_MANAGED_GROUP.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
        }
    }

    static class Serializer extends UnionSerializer<GroupMembersAddError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GroupMembersAddError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$GroupMembersAddError$Tag[value.tag().ordinal()]) {
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
                    g.writeString("duplicate_user");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("group_not_in_team");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeStartObject();
                    writeTag("members_not_in_team", g);
                    g.writeFieldName("members_not_in_team");
                    StoneSerializers.list(StoneSerializers.string()).serialize(value.membersNotInTeamValue, g);
                    g.writeEndObject();
                    return;
                case IRemoteStorage.PIN /*7*/:
                    g.writeStartObject();
                    writeTag("users_not_found", g);
                    g.writeFieldName("users_not_found");
                    StoneSerializers.list(StoneSerializers.string()).serialize(value.usersNotFoundValue, g);
                    g.writeEndObject();
                    return;
                case IRemoteStorage.FOLDER_REMOTE /*8*/:
                    g.writeString("user_must_be_active_to_be_owner");
                    return;
                case IRemoteStorage.WEBDAV_REMOTE /*9*/:
                    g.writeStartObject();
                    writeTag("user_cannot_be_manager_of_company_managed_group", g);
                    g.writeFieldName("user_cannot_be_manager_of_company_managed_group");
                    StoneSerializers.list(StoneSerializers.string()).serialize(value.userCannotBeManagerOfCompanyManagedGroupValue, g);
                    g.writeEndObject();
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public GroupMembersAddError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            GroupMembersAddError value;
            if ("group_not_found".equals(tag)) {
                value = GroupMembersAddError.GROUP_NOT_FOUND;
            } else if ("other".equals(tag)) {
                value = GroupMembersAddError.OTHER;
            } else if ("system_managed_group_disallowed".equals(tag)) {
                value = GroupMembersAddError.SYSTEM_MANAGED_GROUP_DISALLOWED;
            } else if ("duplicate_user".equals(tag)) {
                value = GroupMembersAddError.DUPLICATE_USER;
            } else if ("group_not_in_team".equals(tag)) {
                value = GroupMembersAddError.GROUP_NOT_IN_TEAM;
            } else if ("members_not_in_team".equals(tag)) {
                StoneSerializer.expectField("members_not_in_team", p);
                value = GroupMembersAddError.membersNotInTeam((List) StoneSerializers.list(StoneSerializers.string()).deserialize(p));
            } else if ("users_not_found".equals(tag)) {
                StoneSerializer.expectField("users_not_found", p);
                value = GroupMembersAddError.usersNotFound((List) StoneSerializers.list(StoneSerializers.string()).deserialize(p));
            } else if ("user_must_be_active_to_be_owner".equals(tag)) {
                value = GroupMembersAddError.USER_MUST_BE_ACTIVE_TO_BE_OWNER;
            } else if ("user_cannot_be_manager_of_company_managed_group".equals(tag)) {
                StoneSerializer.expectField("user_cannot_be_manager_of_company_managed_group", p);
                value = GroupMembersAddError.userCannotBeManagerOfCompanyManagedGroup((List) StoneSerializers.list(StoneSerializers.string()).deserialize(p));
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
        GROUP_NOT_FOUND,
        OTHER,
        SYSTEM_MANAGED_GROUP_DISALLOWED,
        DUPLICATE_USER,
        GROUP_NOT_IN_TEAM,
        MEMBERS_NOT_IN_TEAM,
        USERS_NOT_FOUND,
        USER_MUST_BE_ACTIVE_TO_BE_OWNER,
        USER_CANNOT_BE_MANAGER_OF_COMPANY_MANAGED_GROUP
    }

    private GroupMembersAddError(Tag _tag, List<String> membersNotInTeamValue, List<String> usersNotFoundValue, List<String> userCannotBeManagerOfCompanyManagedGroupValue) {
        this._tag = _tag;
        this.membersNotInTeamValue = membersNotInTeamValue;
        this.usersNotFoundValue = usersNotFoundValue;
        this.userCannotBeManagerOfCompanyManagedGroupValue = userCannotBeManagerOfCompanyManagedGroupValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isGroupNotFound() {
        return this._tag == Tag.GROUP_NOT_FOUND;
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public boolean isSystemManagedGroupDisallowed() {
        return this._tag == Tag.SYSTEM_MANAGED_GROUP_DISALLOWED;
    }

    public boolean isDuplicateUser() {
        return this._tag == Tag.DUPLICATE_USER;
    }

    public boolean isGroupNotInTeam() {
        return this._tag == Tag.GROUP_NOT_IN_TEAM;
    }

    public boolean isMembersNotInTeam() {
        return this._tag == Tag.MEMBERS_NOT_IN_TEAM;
    }

    public static GroupMembersAddError membersNotInTeam(List<String> value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        }
        for (String x : value) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list is null");
            }
        }
        return new GroupMembersAddError(Tag.MEMBERS_NOT_IN_TEAM, value, null, null);
    }

    public List<String> getMembersNotInTeamValue() {
        if (this._tag == Tag.MEMBERS_NOT_IN_TEAM) {
            return this.membersNotInTeamValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.MEMBERS_NOT_IN_TEAM, but was Tag." + this._tag.name());
    }

    public boolean isUsersNotFound() {
        return this._tag == Tag.USERS_NOT_FOUND;
    }

    public static GroupMembersAddError usersNotFound(List<String> value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        }
        for (String x : value) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list is null");
            }
        }
        return new GroupMembersAddError(Tag.USERS_NOT_FOUND, null, value, null);
    }

    public List<String> getUsersNotFoundValue() {
        if (this._tag == Tag.USERS_NOT_FOUND) {
            return this.usersNotFoundValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.USERS_NOT_FOUND, but was Tag." + this._tag.name());
    }

    public boolean isUserMustBeActiveToBeOwner() {
        return this._tag == Tag.USER_MUST_BE_ACTIVE_TO_BE_OWNER;
    }

    public boolean isUserCannotBeManagerOfCompanyManagedGroup() {
        return this._tag == Tag.USER_CANNOT_BE_MANAGER_OF_COMPANY_MANAGED_GROUP;
    }

    public static GroupMembersAddError userCannotBeManagerOfCompanyManagedGroup(List<String> value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        }
        for (String x : value) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list is null");
            }
        }
        return new GroupMembersAddError(Tag.USER_CANNOT_BE_MANAGER_OF_COMPANY_MANAGED_GROUP, null, null, value);
    }

    public List<String> getUserCannotBeManagerOfCompanyManagedGroupValue() {
        if (this._tag == Tag.USER_CANNOT_BE_MANAGER_OF_COMPANY_MANAGED_GROUP) {
            return this.userCannotBeManagerOfCompanyManagedGroupValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.USER_CANNOT_BE_MANAGER_OF_COMPANY_MANAGED_GROUP, but was Tag." + this._tag.name());
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.membersNotInTeamValue, this.usersNotFoundValue, this.userCannotBeManagerOfCompanyManagedGroupValue}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GroupMembersAddError)) {
            return false;
        }
        GroupMembersAddError other = (GroupMembersAddError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$GroupMembersAddError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
            case IRemoteStorage.FOLDER_REMOTE /*8*/:
                return true;
            case IRemoteStorage.BOX_REMOTE /*6*/:
                if (this.membersNotInTeamValue == other.membersNotInTeamValue || this.membersNotInTeamValue.equals(other.membersNotInTeamValue)) {
                    z = true;
                }
                return z;
            case IRemoteStorage.PIN /*7*/:
                if (this.usersNotFoundValue == other.usersNotFoundValue || this.usersNotFoundValue.equals(other.usersNotFoundValue)) {
                    z = true;
                }
                return z;
            case IRemoteStorage.WEBDAV_REMOTE /*9*/:
                if (this.userCannotBeManagerOfCompanyManagedGroupValue == other.userCannotBeManagerOfCompanyManagedGroupValue || this.userCannotBeManagerOfCompanyManagedGroupValue.equals(other.userCannotBeManagerOfCompanyManagedGroupValue)) {
                    z = true;
                }
                return z;
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
