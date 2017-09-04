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

public final class UploadError {
    public static final UploadError OTHER = new UploadError(Tag.OTHER, null);
    private final Tag _tag;
    private final UploadWriteFailed pathValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$UploadError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$UploadError$Tag[Tag.PATH.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$UploadError$Tag[Tag.OTHER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<UploadError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UploadError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$UploadError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag(BoxMetadataUpdateTask.PATH, g);
                    Serializer.INSTANCE.serialize(value.pathValue, g, true);
                    g.writeEndObject();
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public UploadError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            UploadError value;
            if (BoxMetadataUpdateTask.PATH.equals(tag)) {
                value = UploadError.path(Serializer.INSTANCE.deserialize(p, true));
            } else {
                value = UploadError.OTHER;
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
        OTHER
    }

    private UploadError(Tag _tag, UploadWriteFailed pathValue) {
        this._tag = _tag;
        this.pathValue = pathValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isPath() {
        return this._tag == Tag.PATH;
    }

    public static UploadError path(UploadWriteFailed value) {
        if (value != null) {
            return new UploadError(Tag.PATH, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public UploadWriteFailed getPathValue() {
        if (this._tag == Tag.PATH) {
            return this.pathValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.PATH, but was Tag." + this._tag.name());
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.pathValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UploadError)) {
            return false;
        }
        UploadError other = (UploadError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$UploadError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.pathValue == other.pathValue || this.pathValue.equals(other.pathValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
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
