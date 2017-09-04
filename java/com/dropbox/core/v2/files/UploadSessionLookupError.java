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

public final class UploadSessionLookupError {
    public static final UploadSessionLookupError CLOSED = new UploadSessionLookupError(Tag.CLOSED, null);
    public static final UploadSessionLookupError NOT_CLOSED = new UploadSessionLookupError(Tag.NOT_CLOSED, null);
    public static final UploadSessionLookupError NOT_FOUND = new UploadSessionLookupError(Tag.NOT_FOUND, null);
    public static final UploadSessionLookupError OTHER = new UploadSessionLookupError(Tag.OTHER, null);
    private final Tag _tag;
    private final UploadSessionOffsetError incorrectOffsetValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$UploadSessionLookupError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$UploadSessionLookupError$Tag[Tag.NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$UploadSessionLookupError$Tag[Tag.INCORRECT_OFFSET.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$UploadSessionLookupError$Tag[Tag.CLOSED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$UploadSessionLookupError$Tag[Tag.NOT_CLOSED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$UploadSessionLookupError$Tag[Tag.OTHER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    static class Serializer extends UnionSerializer<UploadSessionLookupError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UploadSessionLookupError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$UploadSessionLookupError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("not_found");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("incorrect_offset", g);
                    Serializer.INSTANCE.serialize(value.incorrectOffsetValue, g, true);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("closed");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("not_closed");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public UploadSessionLookupError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            UploadSessionLookupError value;
            if ("not_found".equals(tag)) {
                value = UploadSessionLookupError.NOT_FOUND;
            } else if ("incorrect_offset".equals(tag)) {
                value = UploadSessionLookupError.incorrectOffset(Serializer.INSTANCE.deserialize(p, true));
            } else if ("closed".equals(tag)) {
                value = UploadSessionLookupError.CLOSED;
            } else if ("not_closed".equals(tag)) {
                value = UploadSessionLookupError.NOT_CLOSED;
            } else {
                value = UploadSessionLookupError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        NOT_FOUND,
        INCORRECT_OFFSET,
        CLOSED,
        NOT_CLOSED,
        OTHER
    }

    private UploadSessionLookupError(Tag _tag, UploadSessionOffsetError incorrectOffsetValue) {
        this._tag = _tag;
        this.incorrectOffsetValue = incorrectOffsetValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isNotFound() {
        return this._tag == Tag.NOT_FOUND;
    }

    public boolean isIncorrectOffset() {
        return this._tag == Tag.INCORRECT_OFFSET;
    }

    public static UploadSessionLookupError incorrectOffset(UploadSessionOffsetError value) {
        if (value != null) {
            return new UploadSessionLookupError(Tag.INCORRECT_OFFSET, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public UploadSessionOffsetError getIncorrectOffsetValue() {
        if (this._tag == Tag.INCORRECT_OFFSET) {
            return this.incorrectOffsetValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.INCORRECT_OFFSET, but was Tag." + this._tag.name());
    }

    public boolean isClosed() {
        return this._tag == Tag.CLOSED;
    }

    public boolean isNotClosed() {
        return this._tag == Tag.NOT_CLOSED;
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.incorrectOffsetValue});
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UploadSessionLookupError)) {
            return false;
        }
        UploadSessionLookupError other = (UploadSessionLookupError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$UploadSessionLookupError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                return true;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.incorrectOffsetValue == other.incorrectOffsetValue || this.incorrectOffsetValue.equals(other.incorrectOffsetValue)) {
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
