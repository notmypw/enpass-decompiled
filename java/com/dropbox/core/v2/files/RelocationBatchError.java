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

public final class RelocationBatchError {
    public static final RelocationBatchError CANT_COPY_SHARED_FOLDER = new RelocationBatchError(Tag.CANT_COPY_SHARED_FOLDER, null, null, null);
    public static final RelocationBatchError CANT_MOVE_FOLDER_INTO_ITSELF = new RelocationBatchError(Tag.CANT_MOVE_FOLDER_INTO_ITSELF, null, null, null);
    public static final RelocationBatchError CANT_NEST_SHARED_FOLDER = new RelocationBatchError(Tag.CANT_NEST_SHARED_FOLDER, null, null, null);
    public static final RelocationBatchError DUPLICATED_OR_NESTED_PATHS = new RelocationBatchError(Tag.DUPLICATED_OR_NESTED_PATHS, null, null, null);
    public static final RelocationBatchError OTHER = new RelocationBatchError(Tag.OTHER, null, null, null);
    public static final RelocationBatchError TOO_MANY_FILES = new RelocationBatchError(Tag.TOO_MANY_FILES, null, null, null);
    public static final RelocationBatchError TOO_MANY_WRITE_OPERATIONS = new RelocationBatchError(Tag.TOO_MANY_WRITE_OPERATIONS, null, null, null);
    private final Tag _tag;
    private final LookupError fromLookupValue;
    private final WriteError fromWriteValue;
    private final WriteError toValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$RelocationBatchError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$RelocationBatchError$Tag[Tag.FROM_LOOKUP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RelocationBatchError$Tag[Tag.FROM_WRITE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RelocationBatchError$Tag[Tag.TO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RelocationBatchError$Tag[Tag.CANT_COPY_SHARED_FOLDER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RelocationBatchError$Tag[Tag.CANT_NEST_SHARED_FOLDER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RelocationBatchError$Tag[Tag.CANT_MOVE_FOLDER_INTO_ITSELF.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RelocationBatchError$Tag[Tag.TOO_MANY_FILES.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RelocationBatchError$Tag[Tag.DUPLICATED_OR_NESTED_PATHS.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RelocationBatchError$Tag[Tag.OTHER.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RelocationBatchError$Tag[Tag.TOO_MANY_WRITE_OPERATIONS.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    static class Serializer extends UnionSerializer<RelocationBatchError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RelocationBatchError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$RelocationBatchError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("from_lookup", g);
                    g.writeFieldName("from_lookup");
                    com.dropbox.core.v2.files.LookupError.Serializer.INSTANCE.serialize(value.fromLookupValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("from_write", g);
                    g.writeFieldName("from_write");
                    Serializer.INSTANCE.serialize(value.fromWriteValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeStartObject();
                    writeTag("to", g);
                    g.writeFieldName("to");
                    Serializer.INSTANCE.serialize(value.toValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("cant_copy_shared_folder");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("cant_nest_shared_folder");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeString("cant_move_folder_into_itself");
                    return;
                case IRemoteStorage.PIN /*7*/:
                    g.writeString("too_many_files");
                    return;
                case IRemoteStorage.FOLDER_REMOTE /*8*/:
                    g.writeString("duplicated_or_nested_paths");
                    return;
                case IRemoteStorage.WEBDAV_REMOTE /*9*/:
                    g.writeString("other");
                    return;
                case IRemoteStorage.WEBDAV_REMOTE_USERNAME /*10*/:
                    g.writeString("too_many_write_operations");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public RelocationBatchError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            RelocationBatchError value;
            if ("from_lookup".equals(tag)) {
                StoneSerializer.expectField("from_lookup", p);
                value = RelocationBatchError.fromLookup(com.dropbox.core.v2.files.LookupError.Serializer.INSTANCE.deserialize(p));
            } else if ("from_write".equals(tag)) {
                StoneSerializer.expectField("from_write", p);
                value = RelocationBatchError.fromWrite(Serializer.INSTANCE.deserialize(p));
            } else if ("to".equals(tag)) {
                StoneSerializer.expectField("to", p);
                value = RelocationBatchError.to(Serializer.INSTANCE.deserialize(p));
            } else if ("cant_copy_shared_folder".equals(tag)) {
                value = RelocationBatchError.CANT_COPY_SHARED_FOLDER;
            } else if ("cant_nest_shared_folder".equals(tag)) {
                value = RelocationBatchError.CANT_NEST_SHARED_FOLDER;
            } else if ("cant_move_folder_into_itself".equals(tag)) {
                value = RelocationBatchError.CANT_MOVE_FOLDER_INTO_ITSELF;
            } else if ("too_many_files".equals(tag)) {
                value = RelocationBatchError.TOO_MANY_FILES;
            } else if ("duplicated_or_nested_paths".equals(tag)) {
                value = RelocationBatchError.DUPLICATED_OR_NESTED_PATHS;
            } else if ("other".equals(tag)) {
                value = RelocationBatchError.OTHER;
            } else if ("too_many_write_operations".equals(tag)) {
                value = RelocationBatchError.TOO_MANY_WRITE_OPERATIONS;
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
        FROM_LOOKUP,
        FROM_WRITE,
        TO,
        CANT_COPY_SHARED_FOLDER,
        CANT_NEST_SHARED_FOLDER,
        CANT_MOVE_FOLDER_INTO_ITSELF,
        TOO_MANY_FILES,
        DUPLICATED_OR_NESTED_PATHS,
        OTHER,
        TOO_MANY_WRITE_OPERATIONS
    }

    private RelocationBatchError(Tag _tag, LookupError fromLookupValue, WriteError fromWriteValue, WriteError toValue) {
        this._tag = _tag;
        this.fromLookupValue = fromLookupValue;
        this.fromWriteValue = fromWriteValue;
        this.toValue = toValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isFromLookup() {
        return this._tag == Tag.FROM_LOOKUP;
    }

    public static RelocationBatchError fromLookup(LookupError value) {
        if (value != null) {
            return new RelocationBatchError(Tag.FROM_LOOKUP, value, null, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public LookupError getFromLookupValue() {
        if (this._tag == Tag.FROM_LOOKUP) {
            return this.fromLookupValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.FROM_LOOKUP, but was Tag." + this._tag.name());
    }

    public boolean isFromWrite() {
        return this._tag == Tag.FROM_WRITE;
    }

    public static RelocationBatchError fromWrite(WriteError value) {
        if (value != null) {
            return new RelocationBatchError(Tag.FROM_WRITE, null, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public WriteError getFromWriteValue() {
        if (this._tag == Tag.FROM_WRITE) {
            return this.fromWriteValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.FROM_WRITE, but was Tag." + this._tag.name());
    }

    public boolean isTo() {
        return this._tag == Tag.TO;
    }

    public static RelocationBatchError to(WriteError value) {
        if (value != null) {
            return new RelocationBatchError(Tag.TO, null, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public WriteError getToValue() {
        if (this._tag == Tag.TO) {
            return this.toValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.TO, but was Tag." + this._tag.name());
    }

    public boolean isCantCopySharedFolder() {
        return this._tag == Tag.CANT_COPY_SHARED_FOLDER;
    }

    public boolean isCantNestSharedFolder() {
        return this._tag == Tag.CANT_NEST_SHARED_FOLDER;
    }

    public boolean isCantMoveFolderIntoItself() {
        return this._tag == Tag.CANT_MOVE_FOLDER_INTO_ITSELF;
    }

    public boolean isTooManyFiles() {
        return this._tag == Tag.TOO_MANY_FILES;
    }

    public boolean isDuplicatedOrNestedPaths() {
        return this._tag == Tag.DUPLICATED_OR_NESTED_PATHS;
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public boolean isTooManyWriteOperations() {
        return this._tag == Tag.TOO_MANY_WRITE_OPERATIONS;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.fromLookupValue, this.fromWriteValue, this.toValue}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RelocationBatchError)) {
            return false;
        }
        RelocationBatchError other = (RelocationBatchError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$RelocationBatchError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.fromLookupValue == other.fromLookupValue || this.fromLookupValue.equals(other.fromLookupValue)) {
                    z = true;
                }
                return z;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.fromWriteValue == other.fromWriteValue || this.fromWriteValue.equals(other.fromWriteValue)) {
                    z = true;
                }
                return z;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                if (this.toValue == other.toValue || this.toValue.equals(other.toValue)) {
                    z = true;
                }
                return z;
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
            case IRemoteStorage.BOX_REMOTE /*6*/:
            case IRemoteStorage.PIN /*7*/:
            case IRemoteStorage.FOLDER_REMOTE /*8*/:
            case IRemoteStorage.WEBDAV_REMOTE /*9*/:
            case IRemoteStorage.WEBDAV_REMOTE_USERNAME /*10*/:
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
