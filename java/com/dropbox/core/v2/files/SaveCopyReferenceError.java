package com.dropbox.core.v2.files;

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

public final class SaveCopyReferenceError {
    public static final SaveCopyReferenceError INVALID_COPY_REFERENCE = new SaveCopyReferenceError(Tag.INVALID_COPY_REFERENCE, null);
    public static final SaveCopyReferenceError NOT_FOUND = new SaveCopyReferenceError(Tag.NOT_FOUND, null);
    public static final SaveCopyReferenceError NO_PERMISSION = new SaveCopyReferenceError(Tag.NO_PERMISSION, null);
    public static final SaveCopyReferenceError OTHER = new SaveCopyReferenceError(Tag.OTHER, null);
    public static final SaveCopyReferenceError TOO_MANY_FILES = new SaveCopyReferenceError(Tag.TOO_MANY_FILES, null);
    private final Tag _tag;
    private final WriteError pathValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$SaveCopyReferenceError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$SaveCopyReferenceError$Tag[Tag.PATH.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$SaveCopyReferenceError$Tag[Tag.INVALID_COPY_REFERENCE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$SaveCopyReferenceError$Tag[Tag.NO_PERMISSION.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$SaveCopyReferenceError$Tag[Tag.NOT_FOUND.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$SaveCopyReferenceError$Tag[Tag.TOO_MANY_FILES.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$SaveCopyReferenceError$Tag[Tag.OTHER.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    static class Serializer extends UnionSerializer<SaveCopyReferenceError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(SaveCopyReferenceError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$SaveCopyReferenceError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag(BoxMetadataUpdateTask.PATH, g);
                    g.writeFieldName(BoxMetadataUpdateTask.PATH);
                    Serializer.INSTANCE.serialize(value.pathValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("invalid_copy_reference");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("no_permission");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("not_found");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("too_many_files");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public SaveCopyReferenceError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            SaveCopyReferenceError value;
            if (BoxMetadataUpdateTask.PATH.equals(tag)) {
                StoneSerializer.expectField(BoxMetadataUpdateTask.PATH, p);
                value = SaveCopyReferenceError.path(Serializer.INSTANCE.deserialize(p));
            } else if ("invalid_copy_reference".equals(tag)) {
                value = SaveCopyReferenceError.INVALID_COPY_REFERENCE;
            } else if ("no_permission".equals(tag)) {
                value = SaveCopyReferenceError.NO_PERMISSION;
            } else if ("not_found".equals(tag)) {
                value = SaveCopyReferenceError.NOT_FOUND;
            } else if ("too_many_files".equals(tag)) {
                value = SaveCopyReferenceError.TOO_MANY_FILES;
            } else {
                value = SaveCopyReferenceError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        PATH,
        INVALID_COPY_REFERENCE,
        NO_PERMISSION,
        NOT_FOUND,
        TOO_MANY_FILES,
        OTHER
    }

    private SaveCopyReferenceError(Tag _tag, WriteError pathValue) {
        this._tag = _tag;
        this.pathValue = pathValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isPath() {
        return this._tag == Tag.PATH;
    }

    public static SaveCopyReferenceError path(WriteError value) {
        if (value != null) {
            return new SaveCopyReferenceError(Tag.PATH, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public WriteError getPathValue() {
        if (this._tag == Tag.PATH) {
            return this.pathValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.PATH, but was Tag." + this._tag.name());
    }

    public boolean isInvalidCopyReference() {
        return this._tag == Tag.INVALID_COPY_REFERENCE;
    }

    public boolean isNoPermission() {
        return this._tag == Tag.NO_PERMISSION;
    }

    public boolean isNotFound() {
        return this._tag == Tag.NOT_FOUND;
    }

    public boolean isTooManyFiles() {
        return this._tag == Tag.TOO_MANY_FILES;
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.pathValue});
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SaveCopyReferenceError)) {
            return false;
        }
        SaveCopyReferenceError other = (SaveCopyReferenceError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$SaveCopyReferenceError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.pathValue == other.pathValue || this.pathValue.equals(other.pathValue)) {
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
