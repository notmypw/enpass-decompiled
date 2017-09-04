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

public final class MountFolderError {
    public static final MountFolderError ALREADY_MOUNTED = new MountFolderError(Tag.ALREADY_MOUNTED, null, null);
    public static final MountFolderError INSIDE_SHARED_FOLDER = new MountFolderError(Tag.INSIDE_SHARED_FOLDER, null, null);
    public static final MountFolderError NOT_MOUNTABLE = new MountFolderError(Tag.NOT_MOUNTABLE, null, null);
    public static final MountFolderError NO_PERMISSION = new MountFolderError(Tag.NO_PERMISSION, null, null);
    public static final MountFolderError OTHER = new MountFolderError(Tag.OTHER, null, null);
    private final Tag _tag;
    private final SharedFolderAccessError accessErrorValue;
    private final InsufficientQuotaAmounts insufficientQuotaValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$MountFolderError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$MountFolderError$Tag[Tag.ACCESS_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$MountFolderError$Tag[Tag.INSIDE_SHARED_FOLDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$MountFolderError$Tag[Tag.INSUFFICIENT_QUOTA.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$MountFolderError$Tag[Tag.ALREADY_MOUNTED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$MountFolderError$Tag[Tag.NO_PERMISSION.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$MountFolderError$Tag[Tag.NOT_MOUNTABLE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$MountFolderError$Tag[Tag.OTHER.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    static class Serializer extends UnionSerializer<MountFolderError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(MountFolderError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$MountFolderError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("access_error", g);
                    g.writeFieldName("access_error");
                    Serializer.INSTANCE.serialize(value.accessErrorValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("inside_shared_folder");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeStartObject();
                    writeTag("insufficient_quota", g);
                    Serializer.INSTANCE.serialize(value.insufficientQuotaValue, g, true);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("already_mounted");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("no_permission");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeString("not_mountable");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public MountFolderError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            MountFolderError value;
            if ("access_error".equals(tag)) {
                StoneSerializer.expectField("access_error", p);
                value = MountFolderError.accessError(Serializer.INSTANCE.deserialize(p));
            } else if ("inside_shared_folder".equals(tag)) {
                value = MountFolderError.INSIDE_SHARED_FOLDER;
            } else if ("insufficient_quota".equals(tag)) {
                value = MountFolderError.insufficientQuota(Serializer.INSTANCE.deserialize(p, true));
            } else if ("already_mounted".equals(tag)) {
                value = MountFolderError.ALREADY_MOUNTED;
            } else if ("no_permission".equals(tag)) {
                value = MountFolderError.NO_PERMISSION;
            } else if ("not_mountable".equals(tag)) {
                value = MountFolderError.NOT_MOUNTABLE;
            } else {
                value = MountFolderError.OTHER;
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
        INSIDE_SHARED_FOLDER,
        INSUFFICIENT_QUOTA,
        ALREADY_MOUNTED,
        NO_PERMISSION,
        NOT_MOUNTABLE,
        OTHER
    }

    private MountFolderError(Tag _tag, SharedFolderAccessError accessErrorValue, InsufficientQuotaAmounts insufficientQuotaValue) {
        this._tag = _tag;
        this.accessErrorValue = accessErrorValue;
        this.insufficientQuotaValue = insufficientQuotaValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isAccessError() {
        return this._tag == Tag.ACCESS_ERROR;
    }

    public static MountFolderError accessError(SharedFolderAccessError value) {
        if (value != null) {
            return new MountFolderError(Tag.ACCESS_ERROR, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public SharedFolderAccessError getAccessErrorValue() {
        if (this._tag == Tag.ACCESS_ERROR) {
            return this.accessErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.ACCESS_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isInsideSharedFolder() {
        return this._tag == Tag.INSIDE_SHARED_FOLDER;
    }

    public boolean isInsufficientQuota() {
        return this._tag == Tag.INSUFFICIENT_QUOTA;
    }

    public static MountFolderError insufficientQuota(InsufficientQuotaAmounts value) {
        if (value != null) {
            return new MountFolderError(Tag.INSUFFICIENT_QUOTA, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public InsufficientQuotaAmounts getInsufficientQuotaValue() {
        if (this._tag == Tag.INSUFFICIENT_QUOTA) {
            return this.insufficientQuotaValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.INSUFFICIENT_QUOTA, but was Tag." + this._tag.name());
    }

    public boolean isAlreadyMounted() {
        return this._tag == Tag.ALREADY_MOUNTED;
    }

    public boolean isNoPermission() {
        return this._tag == Tag.NO_PERMISSION;
    }

    public boolean isNotMountable() {
        return this._tag == Tag.NOT_MOUNTABLE;
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.accessErrorValue, this.insufficientQuotaValue});
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MountFolderError)) {
            return false;
        }
        MountFolderError other = (MountFolderError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$MountFolderError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.accessErrorValue == other.accessErrorValue || this.accessErrorValue.equals(other.accessErrorValue)) {
                    z = true;
                }
                return z;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
            case IRemoteStorage.BOX_REMOTE /*6*/:
            case IRemoteStorage.PIN /*7*/:
                return true;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                if (this.insufficientQuotaValue == other.insufficientQuotaValue || this.insufficientQuotaValue.equals(other.insufficientQuotaValue)) {
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
