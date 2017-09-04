package com.dropbox.core.v2.sharing;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.UnionSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import net.sqlcipher.database.SQLiteDatabase;

public final class UnshareFolderError {
    public static final UnshareFolderError NO_PERMISSION = new UnshareFolderError(Tag.NO_PERMISSION, null);
    public static final UnshareFolderError OTHER = new UnshareFolderError(Tag.OTHER, null);
    public static final UnshareFolderError TEAM_FOLDER = new UnshareFolderError(Tag.TEAM_FOLDER, null);
    public static final UnshareFolderError TOO_MANY_FILES = new UnshareFolderError(Tag.TOO_MANY_FILES, null);
    private final Tag _tag;
    private final SharedFolderAccessError accessErrorValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$UnshareFolderError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$UnshareFolderError$Tag[Tag.ACCESS_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$UnshareFolderError$Tag[Tag.TEAM_FOLDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$UnshareFolderError$Tag[Tag.NO_PERMISSION.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$UnshareFolderError$Tag[Tag.TOO_MANY_FILES.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$UnshareFolderError$Tag[Tag.OTHER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    static class Serializer extends UnionSerializer<UnshareFolderError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UnshareFolderError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$UnshareFolderError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("access_error", g);
                    g.writeFieldName("access_error");
                    Serializer.INSTANCE.serialize(value.accessErrorValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("team_folder");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("no_permission");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("too_many_files");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public UnshareFolderError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            UnshareFolderError value;
            if ("access_error".equals(tag)) {
                StoneSerializer.expectField("access_error", p);
                value = UnshareFolderError.accessError(Serializer.INSTANCE.deserialize(p));
            } else if ("team_folder".equals(tag)) {
                value = UnshareFolderError.TEAM_FOLDER;
            } else if ("no_permission".equals(tag)) {
                value = UnshareFolderError.NO_PERMISSION;
            } else if ("too_many_files".equals(tag)) {
                value = UnshareFolderError.TOO_MANY_FILES;
            } else {
                value = UnshareFolderError.OTHER;
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
        TEAM_FOLDER,
        NO_PERMISSION,
        TOO_MANY_FILES,
        OTHER
    }

    private UnshareFolderError(Tag _tag, SharedFolderAccessError accessErrorValue) {
        this._tag = _tag;
        this.accessErrorValue = accessErrorValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isAccessError() {
        return this._tag == Tag.ACCESS_ERROR;
    }

    public static UnshareFolderError accessError(SharedFolderAccessError value) {
        if (value != null) {
            return new UnshareFolderError(Tag.ACCESS_ERROR, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public SharedFolderAccessError getAccessErrorValue() {
        if (this._tag == Tag.ACCESS_ERROR) {
            return this.accessErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.ACCESS_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isTeamFolder() {
        return this._tag == Tag.TEAM_FOLDER;
    }

    public boolean isNoPermission() {
        return this._tag == Tag.NO_PERMISSION;
    }

    public boolean isTooManyFiles() {
        return this._tag == Tag.TOO_MANY_FILES;
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
        if (!(obj instanceof UnshareFolderError)) {
            return false;
        }
        UnshareFolderError other = (UnshareFolderError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$UnshareFolderError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.accessErrorValue == other.accessErrorValue || this.accessErrorValue.equals(other.accessErrorValue)) {
                    z = true;
                }
                return z;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
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
