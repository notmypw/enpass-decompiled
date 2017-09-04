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

public final class ThumbnailError {
    public static final ThumbnailError CONVERSION_ERROR = new ThumbnailError(Tag.CONVERSION_ERROR, null);
    public static final ThumbnailError UNSUPPORTED_EXTENSION = new ThumbnailError(Tag.UNSUPPORTED_EXTENSION, null);
    public static final ThumbnailError UNSUPPORTED_IMAGE = new ThumbnailError(Tag.UNSUPPORTED_IMAGE, null);
    private final Tag _tag;
    private final LookupError pathValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$ThumbnailError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$ThumbnailError$Tag[Tag.PATH.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$ThumbnailError$Tag[Tag.UNSUPPORTED_EXTENSION.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$ThumbnailError$Tag[Tag.UNSUPPORTED_IMAGE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$ThumbnailError$Tag[Tag.CONVERSION_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static class Serializer extends UnionSerializer<ThumbnailError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ThumbnailError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$ThumbnailError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag(BoxMetadataUpdateTask.PATH, g);
                    g.writeFieldName(BoxMetadataUpdateTask.PATH);
                    com.dropbox.core.v2.files.LookupError.Serializer.INSTANCE.serialize(value.pathValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("unsupported_extension");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("unsupported_image");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("conversion_error");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public ThumbnailError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            ThumbnailError value;
            if (BoxMetadataUpdateTask.PATH.equals(tag)) {
                StoneSerializer.expectField(BoxMetadataUpdateTask.PATH, p);
                value = ThumbnailError.path(com.dropbox.core.v2.files.LookupError.Serializer.INSTANCE.deserialize(p));
            } else if ("unsupported_extension".equals(tag)) {
                value = ThumbnailError.UNSUPPORTED_EXTENSION;
            } else if ("unsupported_image".equals(tag)) {
                value = ThumbnailError.UNSUPPORTED_IMAGE;
            } else if ("conversion_error".equals(tag)) {
                value = ThumbnailError.CONVERSION_ERROR;
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
        PATH,
        UNSUPPORTED_EXTENSION,
        UNSUPPORTED_IMAGE,
        CONVERSION_ERROR
    }

    private ThumbnailError(Tag _tag, LookupError pathValue) {
        this._tag = _tag;
        this.pathValue = pathValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isPath() {
        return this._tag == Tag.PATH;
    }

    public static ThumbnailError path(LookupError value) {
        if (value != null) {
            return new ThumbnailError(Tag.PATH, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public LookupError getPathValue() {
        if (this._tag == Tag.PATH) {
            return this.pathValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.PATH, but was Tag." + this._tag.name());
    }

    public boolean isUnsupportedExtension() {
        return this._tag == Tag.UNSUPPORTED_EXTENSION;
    }

    public boolean isUnsupportedImage() {
        return this._tag == Tag.UNSUPPORTED_IMAGE;
    }

    public boolean isConversionError() {
        return this._tag == Tag.CONVERSION_ERROR;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.pathValue});
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ThumbnailError)) {
            return false;
        }
        ThumbnailError other = (ThumbnailError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$ThumbnailError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.pathValue == other.pathValue || this.pathValue.equals(other.pathValue)) {
                    z = true;
                }
                return z;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
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
