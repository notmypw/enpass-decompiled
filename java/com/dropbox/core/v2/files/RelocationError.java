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

public final class RelocationError {
    public static final RelocationError CANT_COPY_SHARED_FOLDER = new RelocationError(Tag.CANT_COPY_SHARED_FOLDER, null, null, null);
    public static final RelocationError CANT_MOVE_FOLDER_INTO_ITSELF = new RelocationError(Tag.CANT_MOVE_FOLDER_INTO_ITSELF, null, null, null);
    public static final RelocationError CANT_NEST_SHARED_FOLDER = new RelocationError(Tag.CANT_NEST_SHARED_FOLDER, null, null, null);
    public static final RelocationError DUPLICATED_OR_NESTED_PATHS = new RelocationError(Tag.DUPLICATED_OR_NESTED_PATHS, null, null, null);
    public static final RelocationError OTHER = new RelocationError(Tag.OTHER, null, null, null);
    public static final RelocationError TOO_MANY_FILES = new RelocationError(Tag.TOO_MANY_FILES, null, null, null);
    private final Tag _tag;
    private final LookupError fromLookupValue;
    private final WriteError fromWriteValue;
    private final WriteError toValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$RelocationError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$RelocationError$Tag[Tag.FROM_LOOKUP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RelocationError$Tag[Tag.FROM_WRITE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RelocationError$Tag[Tag.TO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RelocationError$Tag[Tag.CANT_COPY_SHARED_FOLDER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RelocationError$Tag[Tag.CANT_NEST_SHARED_FOLDER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RelocationError$Tag[Tag.CANT_MOVE_FOLDER_INTO_ITSELF.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RelocationError$Tag[Tag.TOO_MANY_FILES.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RelocationError$Tag[Tag.DUPLICATED_OR_NESTED_PATHS.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RelocationError$Tag[Tag.OTHER.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
        }
    }

    static class Serializer extends UnionSerializer<RelocationError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RelocationError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$RelocationError$Tag[value.tag().ordinal()]) {
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
                default:
                    g.writeString("other");
                    return;
            }
        }

        public RelocationError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            RelocationError value;
            if ("from_lookup".equals(tag)) {
                StoneSerializer.expectField("from_lookup", p);
                value = RelocationError.fromLookup(com.dropbox.core.v2.files.LookupError.Serializer.INSTANCE.deserialize(p));
            } else if ("from_write".equals(tag)) {
                StoneSerializer.expectField("from_write", p);
                value = RelocationError.fromWrite(Serializer.INSTANCE.deserialize(p));
            } else if ("to".equals(tag)) {
                StoneSerializer.expectField("to", p);
                value = RelocationError.to(Serializer.INSTANCE.deserialize(p));
            } else if ("cant_copy_shared_folder".equals(tag)) {
                value = RelocationError.CANT_COPY_SHARED_FOLDER;
            } else if ("cant_nest_shared_folder".equals(tag)) {
                value = RelocationError.CANT_NEST_SHARED_FOLDER;
            } else if ("cant_move_folder_into_itself".equals(tag)) {
                value = RelocationError.CANT_MOVE_FOLDER_INTO_ITSELF;
            } else if ("too_many_files".equals(tag)) {
                value = RelocationError.TOO_MANY_FILES;
            } else if ("duplicated_or_nested_paths".equals(tag)) {
                value = RelocationError.DUPLICATED_OR_NESTED_PATHS;
            } else {
                value = RelocationError.OTHER;
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
        OTHER
    }

    private RelocationError(Tag _tag, LookupError fromLookupValue, WriteError fromWriteValue, WriteError toValue) {
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

    public static RelocationError fromLookup(LookupError value) {
        if (value != null) {
            return new RelocationError(Tag.FROM_LOOKUP, value, null, null);
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

    public static RelocationError fromWrite(WriteError value) {
        if (value != null) {
            return new RelocationError(Tag.FROM_WRITE, null, value, null);
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

    public static RelocationError to(WriteError value) {
        if (value != null) {
            return new RelocationError(Tag.TO, null, null, value);
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

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.fromLookupValue, this.fromWriteValue, this.toValue});
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RelocationError)) {
            return false;
        }
        RelocationError other = (RelocationError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$RelocationError$Tag[this._tag.ordinal()]) {
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
