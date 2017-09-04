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

public final class RelinquishFolderMembershipError {
    public static final RelinquishFolderMembershipError FOLDER_OWNER = new RelinquishFolderMembershipError(Tag.FOLDER_OWNER, null);
    public static final RelinquishFolderMembershipError GROUP_ACCESS = new RelinquishFolderMembershipError(Tag.GROUP_ACCESS, null);
    public static final RelinquishFolderMembershipError MOUNTED = new RelinquishFolderMembershipError(Tag.MOUNTED, null);
    public static final RelinquishFolderMembershipError NO_EXPLICIT_ACCESS = new RelinquishFolderMembershipError(Tag.NO_EXPLICIT_ACCESS, null);
    public static final RelinquishFolderMembershipError NO_PERMISSION = new RelinquishFolderMembershipError(Tag.NO_PERMISSION, null);
    public static final RelinquishFolderMembershipError OTHER = new RelinquishFolderMembershipError(Tag.OTHER, null);
    public static final RelinquishFolderMembershipError TEAM_FOLDER = new RelinquishFolderMembershipError(Tag.TEAM_FOLDER, null);
    private final Tag _tag;
    private final SharedFolderAccessError accessErrorValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$RelinquishFolderMembershipError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$RelinquishFolderMembershipError$Tag[Tag.ACCESS_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$RelinquishFolderMembershipError$Tag[Tag.FOLDER_OWNER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$RelinquishFolderMembershipError$Tag[Tag.MOUNTED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$RelinquishFolderMembershipError$Tag[Tag.GROUP_ACCESS.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$RelinquishFolderMembershipError$Tag[Tag.TEAM_FOLDER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$RelinquishFolderMembershipError$Tag[Tag.NO_PERMISSION.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$RelinquishFolderMembershipError$Tag[Tag.NO_EXPLICIT_ACCESS.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$RelinquishFolderMembershipError$Tag[Tag.OTHER.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    static class Serializer extends UnionSerializer<RelinquishFolderMembershipError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RelinquishFolderMembershipError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$RelinquishFolderMembershipError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("access_error", g);
                    g.writeFieldName("access_error");
                    Serializer.INSTANCE.serialize(value.accessErrorValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("folder_owner");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("mounted");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("group_access");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("team_folder");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeString("no_permission");
                    return;
                case IRemoteStorage.PIN /*7*/:
                    g.writeString("no_explicit_access");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public RelinquishFolderMembershipError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            RelinquishFolderMembershipError value;
            if ("access_error".equals(tag)) {
                StoneSerializer.expectField("access_error", p);
                value = RelinquishFolderMembershipError.accessError(Serializer.INSTANCE.deserialize(p));
            } else if ("folder_owner".equals(tag)) {
                value = RelinquishFolderMembershipError.FOLDER_OWNER;
            } else if ("mounted".equals(tag)) {
                value = RelinquishFolderMembershipError.MOUNTED;
            } else if ("group_access".equals(tag)) {
                value = RelinquishFolderMembershipError.GROUP_ACCESS;
            } else if ("team_folder".equals(tag)) {
                value = RelinquishFolderMembershipError.TEAM_FOLDER;
            } else if ("no_permission".equals(tag)) {
                value = RelinquishFolderMembershipError.NO_PERMISSION;
            } else if ("no_explicit_access".equals(tag)) {
                value = RelinquishFolderMembershipError.NO_EXPLICIT_ACCESS;
            } else {
                value = RelinquishFolderMembershipError.OTHER;
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
        FOLDER_OWNER,
        MOUNTED,
        GROUP_ACCESS,
        TEAM_FOLDER,
        NO_PERMISSION,
        NO_EXPLICIT_ACCESS,
        OTHER
    }

    private RelinquishFolderMembershipError(Tag _tag, SharedFolderAccessError accessErrorValue) {
        this._tag = _tag;
        this.accessErrorValue = accessErrorValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isAccessError() {
        return this._tag == Tag.ACCESS_ERROR;
    }

    public static RelinquishFolderMembershipError accessError(SharedFolderAccessError value) {
        if (value != null) {
            return new RelinquishFolderMembershipError(Tag.ACCESS_ERROR, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public SharedFolderAccessError getAccessErrorValue() {
        if (this._tag == Tag.ACCESS_ERROR) {
            return this.accessErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.ACCESS_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isFolderOwner() {
        return this._tag == Tag.FOLDER_OWNER;
    }

    public boolean isMounted() {
        return this._tag == Tag.MOUNTED;
    }

    public boolean isGroupAccess() {
        return this._tag == Tag.GROUP_ACCESS;
    }

    public boolean isTeamFolder() {
        return this._tag == Tag.TEAM_FOLDER;
    }

    public boolean isNoPermission() {
        return this._tag == Tag.NO_PERMISSION;
    }

    public boolean isNoExplicitAccess() {
        return this._tag == Tag.NO_EXPLICIT_ACCESS;
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
        if (!(obj instanceof RelinquishFolderMembershipError)) {
            return false;
        }
        RelinquishFolderMembershipError other = (RelinquishFolderMembershipError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$RelinquishFolderMembershipError$Tag[this._tag.ordinal()]) {
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
            case IRemoteStorage.PIN /*7*/:
            case IRemoteStorage.FOLDER_REMOTE /*8*/:
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
