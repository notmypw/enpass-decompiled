package com.dropbox.core.v2.sharing;

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
import java.util.Arrays;
import net.sqlcipher.database.SQLiteDatabase;

public final class UpdateFolderPolicyError {
    public static final UpdateFolderPolicyError DISALLOWED_SHARED_LINK_POLICY = new UpdateFolderPolicyError(Tag.DISALLOWED_SHARED_LINK_POLICY, null);
    public static final UpdateFolderPolicyError NOT_ON_TEAM = new UpdateFolderPolicyError(Tag.NOT_ON_TEAM, null);
    public static final UpdateFolderPolicyError NO_PERMISSION = new UpdateFolderPolicyError(Tag.NO_PERMISSION, null);
    public static final UpdateFolderPolicyError OTHER = new UpdateFolderPolicyError(Tag.OTHER, null);
    public static final UpdateFolderPolicyError TEAM_POLICY_DISALLOWS_MEMBER_POLICY = new UpdateFolderPolicyError(Tag.TEAM_POLICY_DISALLOWS_MEMBER_POLICY, null);
    private final Tag _tag;
    private final SharedFolderAccessError accessErrorValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$UpdateFolderPolicyError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$UpdateFolderPolicyError$Tag[Tag.ACCESS_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$UpdateFolderPolicyError$Tag[Tag.NOT_ON_TEAM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$UpdateFolderPolicyError$Tag[Tag.TEAM_POLICY_DISALLOWS_MEMBER_POLICY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$UpdateFolderPolicyError$Tag[Tag.DISALLOWED_SHARED_LINK_POLICY.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$UpdateFolderPolicyError$Tag[Tag.NO_PERMISSION.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$UpdateFolderPolicyError$Tag[Tag.OTHER.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    static class Serializer extends UnionSerializer<UpdateFolderPolicyError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UpdateFolderPolicyError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$UpdateFolderPolicyError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("access_error", g);
                    g.writeFieldName("access_error");
                    Serializer.INSTANCE.serialize(value.accessErrorValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("not_on_team");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("team_policy_disallows_member_policy");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("disallowed_shared_link_policy");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("no_permission");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public UpdateFolderPolicyError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            UpdateFolderPolicyError value;
            if ("access_error".equals(tag)) {
                StoneSerializer.expectField("access_error", p);
                value = UpdateFolderPolicyError.accessError(Serializer.INSTANCE.deserialize(p));
            } else if ("not_on_team".equals(tag)) {
                value = UpdateFolderPolicyError.NOT_ON_TEAM;
            } else if ("team_policy_disallows_member_policy".equals(tag)) {
                value = UpdateFolderPolicyError.TEAM_POLICY_DISALLOWS_MEMBER_POLICY;
            } else if ("disallowed_shared_link_policy".equals(tag)) {
                value = UpdateFolderPolicyError.DISALLOWED_SHARED_LINK_POLICY;
            } else if ("no_permission".equals(tag)) {
                value = UpdateFolderPolicyError.NO_PERMISSION;
            } else {
                value = UpdateFolderPolicyError.OTHER;
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
        NOT_ON_TEAM,
        TEAM_POLICY_DISALLOWS_MEMBER_POLICY,
        DISALLOWED_SHARED_LINK_POLICY,
        NO_PERMISSION,
        OTHER
    }

    private UpdateFolderPolicyError(Tag _tag, SharedFolderAccessError accessErrorValue) {
        this._tag = _tag;
        this.accessErrorValue = accessErrorValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isAccessError() {
        return this._tag == Tag.ACCESS_ERROR;
    }

    public static UpdateFolderPolicyError accessError(SharedFolderAccessError value) {
        if (value != null) {
            return new UpdateFolderPolicyError(Tag.ACCESS_ERROR, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public SharedFolderAccessError getAccessErrorValue() {
        if (this._tag == Tag.ACCESS_ERROR) {
            return this.accessErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.ACCESS_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isNotOnTeam() {
        return this._tag == Tag.NOT_ON_TEAM;
    }

    public boolean isTeamPolicyDisallowsMemberPolicy() {
        return this._tag == Tag.TEAM_POLICY_DISALLOWS_MEMBER_POLICY;
    }

    public boolean isDisallowedSharedLinkPolicy() {
        return this._tag == Tag.DISALLOWED_SHARED_LINK_POLICY;
    }

    public boolean isNoPermission() {
        return this._tag == Tag.NO_PERMISSION;
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.accessErrorValue});
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UpdateFolderPolicyError)) {
            return false;
        }
        UpdateFolderPolicyError other = (UpdateFolderPolicyError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$UpdateFolderPolicyError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.accessErrorValue == other.accessErrorValue || this.accessErrorValue.equals(other.accessErrorValue)) {
                    z = true;
                }
                return z;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
            case IRemoteStorage.BOX_REMOTE /*6*/:
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
