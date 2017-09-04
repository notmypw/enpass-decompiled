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

public final class GroupMembersRemoveError {
    public static final GroupMembersRemoveError GROUP_NOT_FOUND = new GroupMembersRemoveError(Tag.GROUP_NOT_FOUND, null, null);
    public static final GroupMembersRemoveError GROUP_NOT_IN_TEAM = new GroupMembersRemoveError(Tag.GROUP_NOT_IN_TEAM, null, null);
    public static final GroupMembersRemoveError MEMBER_NOT_IN_GROUP = new GroupMembersRemoveError(Tag.MEMBER_NOT_IN_GROUP, null, null);
    public static final GroupMembersRemoveError OTHER = new GroupMembersRemoveError(Tag.OTHER, null, null);
    public static final GroupMembersRemoveError SYSTEM_MANAGED_GROUP_DISALLOWED = new GroupMembersRemoveError(Tag.SYSTEM_MANAGED_GROUP_DISALLOWED, null, null);
    private final Tag _tag;
    private final List<String> membersNotInTeamValue;
    private final List<String> usersNotFoundValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$GroupMembersRemoveError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMembersRemoveError$Tag[Tag.GROUP_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMembersRemoveError$Tag[Tag.OTHER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMembersRemoveError$Tag[Tag.SYSTEM_MANAGED_GROUP_DISALLOWED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMembersRemoveError$Tag[Tag.MEMBER_NOT_IN_GROUP.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMembersRemoveError$Tag[Tag.GROUP_NOT_IN_TEAM.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMembersRemoveError$Tag[Tag.MEMBERS_NOT_IN_TEAM.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupMembersRemoveError$Tag[Tag.USERS_NOT_FOUND.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    static class Serializer extends UnionSerializer<GroupMembersRemoveError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GroupMembersRemoveError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$GroupMembersRemoveError$Tag[value.tag().ordinal()]) {
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
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public GroupMembersRemoveError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            GroupMembersRemoveError value;
            if ("group_not_found".equals(tag)) {
                value = GroupMembersRemoveError.GROUP_NOT_FOUND;
            } else if ("other".equals(tag)) {
                value = GroupMembersRemoveError.OTHER;
            } else if ("system_managed_group_disallowed".equals(tag)) {
                value = GroupMembersRemoveError.SYSTEM_MANAGED_GROUP_DISALLOWED;
            } else if ("member_not_in_group".equals(tag)) {
                value = GroupMembersRemoveError.MEMBER_NOT_IN_GROUP;
            } else if ("group_not_in_team".equals(tag)) {
                value = GroupMembersRemoveError.GROUP_NOT_IN_TEAM;
            } else if ("members_not_in_team".equals(tag)) {
                StoneSerializer.expectField("members_not_in_team", p);
                value = GroupMembersRemoveError.membersNotInTeam((List) StoneSerializers.list(StoneSerializers.string()).deserialize(p));
            } else if ("users_not_found".equals(tag)) {
                StoneSerializer.expectField("users_not_found", p);
                value = GroupMembersRemoveError.usersNotFound((List) StoneSerializers.list(StoneSerializers.string()).deserialize(p));
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
        MEMBER_NOT_IN_GROUP,
        GROUP_NOT_IN_TEAM,
        MEMBERS_NOT_IN_TEAM,
        USERS_NOT_FOUND
    }

    private GroupMembersRemoveError(Tag _tag, List<String> membersNotInTeamValue, List<String> usersNotFoundValue) {
        this._tag = _tag;
        this.membersNotInTeamValue = membersNotInTeamValue;
        this.usersNotFoundValue = usersNotFoundValue;
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

    public boolean isMemberNotInGroup() {
        return this._tag == Tag.MEMBER_NOT_IN_GROUP;
    }

    public boolean isGroupNotInTeam() {
        return this._tag == Tag.GROUP_NOT_IN_TEAM;
    }

    public boolean isMembersNotInTeam() {
        return this._tag == Tag.MEMBERS_NOT_IN_TEAM;
    }

    public static GroupMembersRemoveError membersNotInTeam(List<String> value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        }
        for (String x : value) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list is null");
            }
        }
        return new GroupMembersRemoveError(Tag.MEMBERS_NOT_IN_TEAM, value, null);
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

    public static GroupMembersRemoveError usersNotFound(List<String> value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        }
        for (String x : value) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list is null");
            }
        }
        return new GroupMembersRemoveError(Tag.USERS_NOT_FOUND, null, value);
    }

    public List<String> getUsersNotFoundValue() {
        if (this._tag == Tag.USERS_NOT_FOUND) {
            return this.usersNotFoundValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.USERS_NOT_FOUND, but was Tag." + this._tag.name());
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.membersNotInTeamValue, this.usersNotFoundValue}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GroupMembersRemoveError)) {
            return false;
        }
        GroupMembersRemoveError other = (GroupMembersRemoveError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$GroupMembersRemoveError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
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
