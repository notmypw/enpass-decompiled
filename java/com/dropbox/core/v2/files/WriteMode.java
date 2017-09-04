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
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;
import net.sqlcipher.database.SQLiteDatabase;

public final class WriteMode {
    public static final WriteMode ADD = new WriteMode(Tag.ADD, null);
    public static final WriteMode OVERWRITE = new WriteMode(Tag.OVERWRITE, null);
    private final Tag _tag;
    private final String updateValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$WriteMode$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$WriteMode$Tag[Tag.ADD.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$WriteMode$Tag[Tag.OVERWRITE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$WriteMode$Tag[Tag.UPDATE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<WriteMode> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(WriteMode value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$WriteMode$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("add");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("overwrite");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeStartObject();
                    writeTag("update", g);
                    g.writeFieldName("update");
                    StoneSerializers.string().serialize(value.updateValue, g);
                    g.writeEndObject();
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public WriteMode deserialize(JsonParser p) throws IOException, JsonParseException {
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
            WriteMode value;
            if ("add".equals(tag)) {
                value = WriteMode.ADD;
            } else if ("overwrite".equals(tag)) {
                value = WriteMode.OVERWRITE;
            } else if ("update".equals(tag)) {
                StoneSerializer.expectField("update", p);
                value = WriteMode.update((String) StoneSerializers.string().deserialize(p));
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
        ADD,
        OVERWRITE,
        UPDATE
    }

    private WriteMode(Tag _tag, String updateValue) {
        this._tag = _tag;
        this.updateValue = updateValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isAdd() {
        return this._tag == Tag.ADD;
    }

    public boolean isOverwrite() {
        return this._tag == Tag.OVERWRITE;
    }

    public boolean isUpdate() {
        return this._tag == Tag.UPDATE;
    }

    public static WriteMode update(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() < 9) {
            throw new IllegalArgumentException("String is shorter than 9");
        } else if (Pattern.matches("[0-9a-f]+", value)) {
            return new WriteMode(Tag.UPDATE, value);
        } else {
            throw new IllegalArgumentException("String does not match pattern");
        }
    }

    public String getUpdateValue() {
        if (this._tag == Tag.UPDATE) {
            return this.updateValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.UPDATE, but was Tag." + this._tag.name());
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.updateValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof WriteMode)) {
            return false;
        }
        WriteMode other = (WriteMode) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$WriteMode$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                return true;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                return true;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                if (this.updateValue == other.updateValue || this.updateValue.equals(other.updateValue)) {
                    return true;
                }
                return false;
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
