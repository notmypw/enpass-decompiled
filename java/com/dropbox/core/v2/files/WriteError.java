package com.dropbox.core.v2.files;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
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

public final class WriteError {
    public static final WriteError DISALLOWED_NAME = new WriteError(Tag.DISALLOWED_NAME, null, null);
    public static final WriteError INSUFFICIENT_SPACE = new WriteError(Tag.INSUFFICIENT_SPACE, null, null);
    public static final WriteError NO_WRITE_PERMISSION = new WriteError(Tag.NO_WRITE_PERMISSION, null, null);
    public static final WriteError OTHER = new WriteError(Tag.OTHER, null, null);
    private final Tag _tag;
    private final WriteConflictError conflictValue;
    private final String malformedPathValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$WriteError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$WriteError$Tag[Tag.MALFORMED_PATH.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$WriteError$Tag[Tag.CONFLICT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$WriteError$Tag[Tag.NO_WRITE_PERMISSION.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$WriteError$Tag[Tag.INSUFFICIENT_SPACE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$WriteError$Tag[Tag.DISALLOWED_NAME.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$WriteError$Tag[Tag.OTHER.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    static class Serializer extends UnionSerializer<WriteError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(WriteError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$WriteError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("malformed_path", g);
                    g.writeFieldName("malformed_path");
                    StoneSerializers.nullable(StoneSerializers.string()).serialize(value.malformedPathValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("conflict", g);
                    g.writeFieldName("conflict");
                    Serializer.INSTANCE.serialize(value.conflictValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("no_write_permission");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("insufficient_space");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("disallowed_name");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public WriteError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            WriteError value;
            if ("malformed_path".equals(tag)) {
                String fieldValue = null;
                if (p.getCurrentToken() != JsonToken.END_OBJECT) {
                    StoneSerializer.expectField("malformed_path", p);
                    fieldValue = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                }
                if (fieldValue == null) {
                    value = WriteError.malformedPath();
                } else {
                    value = WriteError.malformedPath(fieldValue);
                }
            } else if ("conflict".equals(tag)) {
                StoneSerializer.expectField("conflict", p);
                value = WriteError.conflict(Serializer.INSTANCE.deserialize(p));
            } else if ("no_write_permission".equals(tag)) {
                value = WriteError.NO_WRITE_PERMISSION;
            } else if ("insufficient_space".equals(tag)) {
                value = WriteError.INSUFFICIENT_SPACE;
            } else if ("disallowed_name".equals(tag)) {
                value = WriteError.DISALLOWED_NAME;
            } else {
                value = WriteError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        MALFORMED_PATH,
        CONFLICT,
        NO_WRITE_PERMISSION,
        INSUFFICIENT_SPACE,
        DISALLOWED_NAME,
        OTHER
    }

    private WriteError(Tag _tag, String malformedPathValue, WriteConflictError conflictValue) {
        this._tag = _tag;
        this.malformedPathValue = malformedPathValue;
        this.conflictValue = conflictValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isMalformedPath() {
        return this._tag == Tag.MALFORMED_PATH;
    }

    public static WriteError malformedPath(String value) {
        return new WriteError(Tag.MALFORMED_PATH, value, null);
    }

    public static WriteError malformedPath() {
        return malformedPath(null);
    }

    public String getMalformedPathValue() {
        if (this._tag == Tag.MALFORMED_PATH) {
            return this.malformedPathValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.MALFORMED_PATH, but was Tag." + this._tag.name());
    }

    public boolean isConflict() {
        return this._tag == Tag.CONFLICT;
    }

    public static WriteError conflict(WriteConflictError value) {
        if (value != null) {
            return new WriteError(Tag.CONFLICT, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public WriteConflictError getConflictValue() {
        if (this._tag == Tag.CONFLICT) {
            return this.conflictValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.CONFLICT, but was Tag." + this._tag.name());
    }

    public boolean isNoWritePermission() {
        return this._tag == Tag.NO_WRITE_PERMISSION;
    }

    public boolean isInsufficientSpace() {
        return this._tag == Tag.INSUFFICIENT_SPACE;
    }

    public boolean isDisallowedName() {
        return this._tag == Tag.DISALLOWED_NAME;
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.malformedPathValue, this.conflictValue});
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof WriteError)) {
            return false;
        }
        WriteError other = (WriteError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$WriteError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.malformedPathValue == other.malformedPathValue || (this.malformedPathValue != null && this.malformedPathValue.equals(other.malformedPathValue))) {
                    z = true;
                }
                return z;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.conflictValue == other.conflictValue || this.conflictValue.equals(other.conflictValue)) {
                    z = true;
                }
                return z;
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
