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

public final class TransferFolderError {
    public static final TransferFolderError INVALID_DROPBOX_ID = new TransferFolderError(Tag.INVALID_DROPBOX_ID, null);
    public static final TransferFolderError NEW_OWNER_EMAIL_UNVERIFIED = new TransferFolderError(Tag.NEW_OWNER_EMAIL_UNVERIFIED, null);
    public static final TransferFolderError NEW_OWNER_NOT_A_MEMBER = new TransferFolderError(Tag.NEW_OWNER_NOT_A_MEMBER, null);
    public static final TransferFolderError NEW_OWNER_UNMOUNTED = new TransferFolderError(Tag.NEW_OWNER_UNMOUNTED, null);
    public static final TransferFolderError NO_PERMISSION = new TransferFolderError(Tag.NO_PERMISSION, null);
    public static final TransferFolderError OTHER = new TransferFolderError(Tag.OTHER, null);
    public static final TransferFolderError TEAM_FOLDER = new TransferFolderError(Tag.TEAM_FOLDER, null);
    private final Tag _tag;
    private final SharedFolderAccessError accessErrorValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$TransferFolderError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$TransferFolderError$Tag[Tag.ACCESS_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$TransferFolderError$Tag[Tag.INVALID_DROPBOX_ID.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$TransferFolderError$Tag[Tag.NEW_OWNER_NOT_A_MEMBER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$TransferFolderError$Tag[Tag.NEW_OWNER_UNMOUNTED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$TransferFolderError$Tag[Tag.NEW_OWNER_EMAIL_UNVERIFIED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$TransferFolderError$Tag[Tag.TEAM_FOLDER.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$TransferFolderError$Tag[Tag.NO_PERMISSION.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$TransferFolderError$Tag[Tag.OTHER.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    static class Serializer extends UnionSerializer<TransferFolderError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(TransferFolderError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$TransferFolderError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("access_error", g);
                    g.writeFieldName("access_error");
                    Serializer.INSTANCE.serialize(value.accessErrorValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("invalid_dropbox_id");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("new_owner_not_a_member");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("new_owner_unmounted");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("new_owner_email_unverified");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeString("team_folder");
                    return;
                case IRemoteStorage.PIN /*7*/:
                    g.writeString("no_permission");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public TransferFolderError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            TransferFolderError value;
            if ("access_error".equals(tag)) {
                StoneSerializer.expectField("access_error", p);
                value = TransferFolderError.accessError(Serializer.INSTANCE.deserialize(p));
            } else if ("invalid_dropbox_id".equals(tag)) {
                value = TransferFolderError.INVALID_DROPBOX_ID;
            } else if ("new_owner_not_a_member".equals(tag)) {
                value = TransferFolderError.NEW_OWNER_NOT_A_MEMBER;
            } else if ("new_owner_unmounted".equals(tag)) {
                value = TransferFolderError.NEW_OWNER_UNMOUNTED;
            } else if ("new_owner_email_unverified".equals(tag)) {
                value = TransferFolderError.NEW_OWNER_EMAIL_UNVERIFIED;
            } else if ("team_folder".equals(tag)) {
                value = TransferFolderError.TEAM_FOLDER;
            } else if ("no_permission".equals(tag)) {
                value = TransferFolderError.NO_PERMISSION;
            } else {
                value = TransferFolderError.OTHER;
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
        INVALID_DROPBOX_ID,
        NEW_OWNER_NOT_A_MEMBER,
        NEW_OWNER_UNMOUNTED,
        NEW_OWNER_EMAIL_UNVERIFIED,
        TEAM_FOLDER,
        NO_PERMISSION,
        OTHER
    }

    private TransferFolderError(Tag _tag, SharedFolderAccessError accessErrorValue) {
        this._tag = _tag;
        this.accessErrorValue = accessErrorValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isAccessError() {
        return this._tag == Tag.ACCESS_ERROR;
    }

    public static TransferFolderError accessError(SharedFolderAccessError value) {
        if (value != null) {
            return new TransferFolderError(Tag.ACCESS_ERROR, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public SharedFolderAccessError getAccessErrorValue() {
        if (this._tag == Tag.ACCESS_ERROR) {
            return this.accessErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.ACCESS_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isInvalidDropboxId() {
        return this._tag == Tag.INVALID_DROPBOX_ID;
    }

    public boolean isNewOwnerNotAMember() {
        return this._tag == Tag.NEW_OWNER_NOT_A_MEMBER;
    }

    public boolean isNewOwnerUnmounted() {
        return this._tag == Tag.NEW_OWNER_UNMOUNTED;
    }

    public boolean isNewOwnerEmailUnverified() {
        return this._tag == Tag.NEW_OWNER_EMAIL_UNVERIFIED;
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
        return Arrays.hashCode(new Object[]{this._tag, this.accessErrorValue});
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TransferFolderError)) {
            return false;
        }
        TransferFolderError other = (TransferFolderError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$TransferFolderError$Tag[this._tag.ordinal()]) {
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
