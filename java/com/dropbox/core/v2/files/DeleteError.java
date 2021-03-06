package com.dropbox.core.v2.files;

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

public final class DeleteError {
    public static final DeleteError OTHER = new DeleteError(Tag.OTHER, null, null);
    private final Tag _tag;
    private final LookupError pathLookupValue;
    private final WriteError pathWriteValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$DeleteError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$DeleteError$Tag[Tag.PATH_LOOKUP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$DeleteError$Tag[Tag.PATH_WRITE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$DeleteError$Tag[Tag.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<DeleteError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(DeleteError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$DeleteError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("path_lookup", g);
                    g.writeFieldName("path_lookup");
                    com.dropbox.core.v2.files.LookupError.Serializer.INSTANCE.serialize(value.pathLookupValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("path_write", g);
                    g.writeFieldName("path_write");
                    Serializer.INSTANCE.serialize(value.pathWriteValue, g);
                    g.writeEndObject();
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public DeleteError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            DeleteError value;
            if ("path_lookup".equals(tag)) {
                StoneSerializer.expectField("path_lookup", p);
                value = DeleteError.pathLookup(com.dropbox.core.v2.files.LookupError.Serializer.INSTANCE.deserialize(p));
            } else if ("path_write".equals(tag)) {
                StoneSerializer.expectField("path_write", p);
                value = DeleteError.pathWrite(Serializer.INSTANCE.deserialize(p));
            } else {
                value = DeleteError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        PATH_LOOKUP,
        PATH_WRITE,
        OTHER
    }

    private DeleteError(Tag _tag, LookupError pathLookupValue, WriteError pathWriteValue) {
        this._tag = _tag;
        this.pathLookupValue = pathLookupValue;
        this.pathWriteValue = pathWriteValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isPathLookup() {
        return this._tag == Tag.PATH_LOOKUP;
    }

    public static DeleteError pathLookup(LookupError value) {
        if (value != null) {
            return new DeleteError(Tag.PATH_LOOKUP, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public LookupError getPathLookupValue() {
        if (this._tag == Tag.PATH_LOOKUP) {
            return this.pathLookupValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.PATH_LOOKUP, but was Tag." + this._tag.name());
    }

    public boolean isPathWrite() {
        return this._tag == Tag.PATH_WRITE;
    }

    public static DeleteError pathWrite(WriteError value) {
        if (value != null) {
            return new DeleteError(Tag.PATH_WRITE, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public WriteError getPathWriteValue() {
        if (this._tag == Tag.PATH_WRITE) {
            return this.pathWriteValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.PATH_WRITE, but was Tag." + this._tag.name());
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.pathLookupValue, this.pathWriteValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DeleteError)) {
            return false;
        }
        DeleteError other = (DeleteError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$DeleteError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.pathLookupValue == other.pathLookupValue || this.pathLookupValue.equals(other.pathLookupValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.pathWriteValue == other.pathWriteValue || this.pathWriteValue.equals(other.pathWriteValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
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
