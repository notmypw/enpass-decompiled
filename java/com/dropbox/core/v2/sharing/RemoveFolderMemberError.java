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

public final class RemoveFolderMemberError {
    public static final RemoveFolderMemberError FOLDER_OWNER = new RemoveFolderMemberError(Tag.FOLDER_OWNER, null, null);
    public static final RemoveFolderMemberError GROUP_ACCESS = new RemoveFolderMemberError(Tag.GROUP_ACCESS, null, null);
    public static final RemoveFolderMemberError NO_PERMISSION = new RemoveFolderMemberError(Tag.NO_PERMISSION, null, null);
    public static final RemoveFolderMemberError OTHER = new RemoveFolderMemberError(Tag.OTHER, null, null);
    public static final RemoveFolderMemberError TEAM_FOLDER = new RemoveFolderMemberError(Tag.TEAM_FOLDER, null, null);
    private final Tag _tag;
    private final SharedFolderAccessError accessErrorValue;
    private final SharedFolderMemberError memberErrorValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$RemoveFolderMemberError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$RemoveFolderMemberError$Tag[Tag.ACCESS_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$RemoveFolderMemberError$Tag[Tag.MEMBER_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$RemoveFolderMemberError$Tag[Tag.FOLDER_OWNER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$RemoveFolderMemberError$Tag[Tag.GROUP_ACCESS.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$RemoveFolderMemberError$Tag[Tag.TEAM_FOLDER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$RemoveFolderMemberError$Tag[Tag.NO_PERMISSION.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$RemoveFolderMemberError$Tag[Tag.OTHER.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    static class Serializer extends UnionSerializer<RemoveFolderMemberError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RemoveFolderMemberError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$RemoveFolderMemberError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("access_error", g);
                    g.writeFieldName("access_error");
                    Serializer.INSTANCE.serialize(value.accessErrorValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("member_error", g);
                    g.writeFieldName("member_error");
                    Serializer.INSTANCE.serialize(value.memberErrorValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("folder_owner");
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
                default:
                    g.writeString("other");
                    return;
            }
        }

        public RemoveFolderMemberError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            RemoveFolderMemberError value;
            if ("access_error".equals(tag)) {
                StoneSerializer.expectField("access_error", p);
                value = RemoveFolderMemberError.accessError(Serializer.INSTANCE.deserialize(p));
            } else if ("member_error".equals(tag)) {
                StoneSerializer.expectField("member_error", p);
                value = RemoveFolderMemberError.memberError(Serializer.INSTANCE.deserialize(p));
            } else if ("folder_owner".equals(tag)) {
                value = RemoveFolderMemberError.FOLDER_OWNER;
            } else if ("group_access".equals(tag)) {
                value = RemoveFolderMemberError.GROUP_ACCESS;
            } else if ("team_folder".equals(tag)) {
                value = RemoveFolderMemberError.TEAM_FOLDER;
            } else if ("no_permission".equals(tag)) {
                value = RemoveFolderMemberError.NO_PERMISSION;
            } else {
                value = RemoveFolderMemberError.OTHER;
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
        MEMBER_ERROR,
        FOLDER_OWNER,
        GROUP_ACCESS,
        TEAM_FOLDER,
        NO_PERMISSION,
        OTHER
    }

    private RemoveFolderMemberError(Tag _tag, SharedFolderAccessError accessErrorValue, SharedFolderMemberError memberErrorValue) {
        this._tag = _tag;
        this.accessErrorValue = accessErrorValue;
        this.memberErrorValue = memberErrorValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isAccessError() {
        return this._tag == Tag.ACCESS_ERROR;
    }

    public static RemoveFolderMemberError accessError(SharedFolderAccessError value) {
        if (value != null) {
            return new RemoveFolderMemberError(Tag.ACCESS_ERROR, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public SharedFolderAccessError getAccessErrorValue() {
        if (this._tag == Tag.ACCESS_ERROR) {
            return this.accessErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.ACCESS_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isMemberError() {
        return this._tag == Tag.MEMBER_ERROR;
    }

    public static RemoveFolderMemberError memberError(SharedFolderMemberError value) {
        if (value != null) {
            return new RemoveFolderMemberError(Tag.MEMBER_ERROR, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public SharedFolderMemberError getMemberErrorValue() {
        if (this._tag == Tag.MEMBER_ERROR) {
            return this.memberErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.MEMBER_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isFolderOwner() {
        return this._tag == Tag.FOLDER_OWNER;
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

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.accessErrorValue, this.memberErrorValue});
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RemoveFolderMemberError)) {
            return false;
        }
        RemoveFolderMemberError other = (RemoveFolderMemberError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$RemoveFolderMemberError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.accessErrorValue == other.accessErrorValue || this.accessErrorValue.equals(other.accessErrorValue)) {
                    z = true;
                }
                return z;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.memberErrorValue == other.memberErrorValue || this.memberErrorValue.equals(other.memberErrorValue)) {
                    z = true;
                }
                return z;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
            case IRemoteStorage.BOX_REMOTE /*6*/:
            case IRemoteStorage.PIN /*7*/:
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
