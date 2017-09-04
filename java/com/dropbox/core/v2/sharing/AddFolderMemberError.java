package com.dropbox.core.v2.sharing;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.UnionSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.samsung.android.sdk.pass.SpassFingerprint;
import in.sinew.enpass.IRemoteStorage;
import java.io.IOException;
import java.util.Arrays;
import net.sqlcipher.database.SQLiteDatabase;

public final class AddFolderMemberError {
    public static final AddFolderMemberError CANT_SHARE_OUTSIDE_TEAM = new AddFolderMemberError(Tag.CANT_SHARE_OUTSIDE_TEAM, null, null, null, null);
    public static final AddFolderMemberError EMAIL_UNVERIFIED = new AddFolderMemberError(Tag.EMAIL_UNVERIFIED, null, null, null, null);
    public static final AddFolderMemberError INSUFFICIENT_PLAN = new AddFolderMemberError(Tag.INSUFFICIENT_PLAN, null, null, null, null);
    public static final AddFolderMemberError NO_PERMISSION = new AddFolderMemberError(Tag.NO_PERMISSION, null, null, null, null);
    public static final AddFolderMemberError OTHER = new AddFolderMemberError(Tag.OTHER, null, null, null, null);
    public static final AddFolderMemberError RATE_LIMIT = new AddFolderMemberError(Tag.RATE_LIMIT, null, null, null, null);
    public static final AddFolderMemberError TEAM_FOLDER = new AddFolderMemberError(Tag.TEAM_FOLDER, null, null, null, null);
    public static final AddFolderMemberError TOO_MANY_INVITEES = new AddFolderMemberError(Tag.TOO_MANY_INVITEES, null, null, null, null);
    private final Tag _tag;
    private final SharedFolderAccessError accessErrorValue;
    private final AddMemberSelectorError badMemberValue;
    private final Long tooManyMembersValue;
    private final Long tooManyPendingInvitesValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$AddFolderMemberError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$AddFolderMemberError$Tag[Tag.ACCESS_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$AddFolderMemberError$Tag[Tag.EMAIL_UNVERIFIED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$AddFolderMemberError$Tag[Tag.BAD_MEMBER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$AddFolderMemberError$Tag[Tag.CANT_SHARE_OUTSIDE_TEAM.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$AddFolderMemberError$Tag[Tag.TOO_MANY_MEMBERS.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$AddFolderMemberError$Tag[Tag.TOO_MANY_PENDING_INVITES.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$AddFolderMemberError$Tag[Tag.RATE_LIMIT.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$AddFolderMemberError$Tag[Tag.TOO_MANY_INVITEES.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$AddFolderMemberError$Tag[Tag.INSUFFICIENT_PLAN.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$AddFolderMemberError$Tag[Tag.TEAM_FOLDER.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$AddFolderMemberError$Tag[Tag.NO_PERMISSION.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$AddFolderMemberError$Tag[Tag.OTHER.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
        }
    }

    static class Serializer extends UnionSerializer<AddFolderMemberError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(AddFolderMemberError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$AddFolderMemberError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("access_error", g);
                    g.writeFieldName("access_error");
                    Serializer.INSTANCE.serialize(value.accessErrorValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("email_unverified");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeStartObject();
                    writeTag("bad_member", g);
                    g.writeFieldName("bad_member");
                    Serializer.INSTANCE.serialize(value.badMemberValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("cant_share_outside_team");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeStartObject();
                    writeTag("too_many_members", g);
                    g.writeFieldName("too_many_members");
                    StoneSerializers.uInt64().serialize(value.tooManyMembersValue, g);
                    g.writeEndObject();
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeStartObject();
                    writeTag("too_many_pending_invites", g);
                    g.writeFieldName("too_many_pending_invites");
                    StoneSerializers.uInt64().serialize(value.tooManyPendingInvitesValue, g);
                    g.writeEndObject();
                    return;
                case IRemoteStorage.PIN /*7*/:
                    g.writeString("rate_limit");
                    return;
                case IRemoteStorage.FOLDER_REMOTE /*8*/:
                    g.writeString("too_many_invitees");
                    return;
                case IRemoteStorage.WEBDAV_REMOTE /*9*/:
                    g.writeString("insufficient_plan");
                    return;
                case IRemoteStorage.WEBDAV_REMOTE_USERNAME /*10*/:
                    g.writeString("team_folder");
                    return;
                case IRemoteStorage.WEBDAV_REMOTE_PASSWORD /*11*/:
                    g.writeString("no_permission");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public AddFolderMemberError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            AddFolderMemberError value;
            if ("access_error".equals(tag)) {
                StoneSerializer.expectField("access_error", p);
                value = AddFolderMemberError.accessError(Serializer.INSTANCE.deserialize(p));
            } else if ("email_unverified".equals(tag)) {
                value = AddFolderMemberError.EMAIL_UNVERIFIED;
            } else if ("bad_member".equals(tag)) {
                StoneSerializer.expectField("bad_member", p);
                value = AddFolderMemberError.badMember(Serializer.INSTANCE.deserialize(p));
            } else if ("cant_share_outside_team".equals(tag)) {
                value = AddFolderMemberError.CANT_SHARE_OUTSIDE_TEAM;
            } else if ("too_many_members".equals(tag)) {
                StoneSerializer.expectField("too_many_members", p);
                value = AddFolderMemberError.tooManyMembers(((Long) StoneSerializers.uInt64().deserialize(p)).longValue());
            } else if ("too_many_pending_invites".equals(tag)) {
                StoneSerializer.expectField("too_many_pending_invites", p);
                value = AddFolderMemberError.tooManyPendingInvites(((Long) StoneSerializers.uInt64().deserialize(p)).longValue());
            } else if ("rate_limit".equals(tag)) {
                value = AddFolderMemberError.RATE_LIMIT;
            } else if ("too_many_invitees".equals(tag)) {
                value = AddFolderMemberError.TOO_MANY_INVITEES;
            } else if ("insufficient_plan".equals(tag)) {
                value = AddFolderMemberError.INSUFFICIENT_PLAN;
            } else if ("team_folder".equals(tag)) {
                value = AddFolderMemberError.TEAM_FOLDER;
            } else if ("no_permission".equals(tag)) {
                value = AddFolderMemberError.NO_PERMISSION;
            } else {
                value = AddFolderMemberError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        ACCESS_ERROR,
        EMAIL_UNVERIFIED,
        BAD_MEMBER,
        CANT_SHARE_OUTSIDE_TEAM,
        TOO_MANY_MEMBERS,
        TOO_MANY_PENDING_INVITES,
        RATE_LIMIT,
        TOO_MANY_INVITEES,
        INSUFFICIENT_PLAN,
        TEAM_FOLDER,
        NO_PERMISSION,
        OTHER
    }

    private AddFolderMemberError(Tag _tag, SharedFolderAccessError accessErrorValue, AddMemberSelectorError badMemberValue, Long tooManyMembersValue, Long tooManyPendingInvitesValue) {
        this._tag = _tag;
        this.accessErrorValue = accessErrorValue;
        this.badMemberValue = badMemberValue;
        this.tooManyMembersValue = tooManyMembersValue;
        this.tooManyPendingInvitesValue = tooManyPendingInvitesValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isAccessError() {
        return this._tag == Tag.ACCESS_ERROR;
    }

    public static AddFolderMemberError accessError(SharedFolderAccessError value) {
        if (value != null) {
            return new AddFolderMemberError(Tag.ACCESS_ERROR, value, null, null, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public SharedFolderAccessError getAccessErrorValue() {
        if (this._tag == Tag.ACCESS_ERROR) {
            return this.accessErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.ACCESS_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isEmailUnverified() {
        return this._tag == Tag.EMAIL_UNVERIFIED;
    }

    public boolean isBadMember() {
        return this._tag == Tag.BAD_MEMBER;
    }

    public static AddFolderMemberError badMember(AddMemberSelectorError value) {
        if (value != null) {
            return new AddFolderMemberError(Tag.BAD_MEMBER, null, value, null, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public AddMemberSelectorError getBadMemberValue() {
        if (this._tag == Tag.BAD_MEMBER) {
            return this.badMemberValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.BAD_MEMBER, but was Tag." + this._tag.name());
    }

    public boolean isCantShareOutsideTeam() {
        return this._tag == Tag.CANT_SHARE_OUTSIDE_TEAM;
    }

    public boolean isTooManyMembers() {
        return this._tag == Tag.TOO_MANY_MEMBERS;
    }

    public static AddFolderMemberError tooManyMembers(long value) {
        return new AddFolderMemberError(Tag.TOO_MANY_MEMBERS, null, null, Long.valueOf(value), null);
    }

    public long getTooManyMembersValue() {
        if (this._tag == Tag.TOO_MANY_MEMBERS) {
            return this.tooManyMembersValue.longValue();
        }
        throw new IllegalStateException("Invalid tag: required Tag.TOO_MANY_MEMBERS, but was Tag." + this._tag.name());
    }

    public boolean isTooManyPendingInvites() {
        return this._tag == Tag.TOO_MANY_PENDING_INVITES;
    }

    public static AddFolderMemberError tooManyPendingInvites(long value) {
        return new AddFolderMemberError(Tag.TOO_MANY_PENDING_INVITES, null, null, null, Long.valueOf(value));
    }

    public long getTooManyPendingInvitesValue() {
        if (this._tag == Tag.TOO_MANY_PENDING_INVITES) {
            return this.tooManyPendingInvitesValue.longValue();
        }
        throw new IllegalStateException("Invalid tag: required Tag.TOO_MANY_PENDING_INVITES, but was Tag." + this._tag.name());
    }

    public boolean isRateLimit() {
        return this._tag == Tag.RATE_LIMIT;
    }

    public boolean isTooManyInvitees() {
        return this._tag == Tag.TOO_MANY_INVITEES;
    }

    public boolean isInsufficientPlan() {
        return this._tag == Tag.INSUFFICIENT_PLAN;
    }

    public boolean isTeamFolder() {
        return this._tag == Tag.TEAM_FOLDER;
    }

    public boolean isNoPermission() {
        return this._tag == Tag.NO_PERMISSION;
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.accessErrorValue, this.badMemberValue, this.tooManyMembersValue, this.tooManyPendingInvitesValue});
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AddFolderMemberError)) {
            return false;
        }
        AddFolderMemberError other = (AddFolderMemberError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$AddFolderMemberError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.accessErrorValue == other.accessErrorValue || this.accessErrorValue.equals(other.accessErrorValue)) {
                    z = true;
                }
                return z;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
            case IRemoteStorage.PIN /*7*/:
            case IRemoteStorage.FOLDER_REMOTE /*8*/:
            case IRemoteStorage.WEBDAV_REMOTE /*9*/:
            case IRemoteStorage.WEBDAV_REMOTE_USERNAME /*10*/:
            case IRemoteStorage.WEBDAV_REMOTE_PASSWORD /*11*/:
            case SpassFingerprint.STATUS_QUALITY_FAILED /*12*/:
                return true;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                if (this.badMemberValue == other.badMemberValue || this.badMemberValue.equals(other.badMemberValue)) {
                    z = true;
                }
                return z;
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                if (this.tooManyMembersValue != other.tooManyMembersValue) {
                    return false;
                }
                return true;
            case IRemoteStorage.BOX_REMOTE /*6*/:
                if (this.tooManyPendingInvitesValue != other.tooManyPendingInvitesValue) {
                    return false;
                }
                return true;
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
