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

public final class UploadErrorWithProperties {
    public static final UploadErrorWithProperties OTHER = new UploadErrorWithProperties(Tag.OTHER, null, null);
    private final Tag _tag;
    private final UploadWriteFailed pathValue;
    private final InvalidPropertyGroupError propertiesErrorValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$UploadErrorWithProperties$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$UploadErrorWithProperties$Tag[Tag.PATH.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$UploadErrorWithProperties$Tag[Tag.OTHER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$UploadErrorWithProperties$Tag[Tag.PROPERTIES_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<UploadErrorWithProperties> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UploadErrorWithProperties value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$UploadErrorWithProperties$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag(BoxMetadataUpdateTask.PATH, g);
                    Serializer.INSTANCE.serialize(value.pathValue, g, true);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("other");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeStartObject();
                    writeTag("properties_error", g);
                    g.writeFieldName("properties_error");
                    Serializer.INSTANCE.serialize(value.propertiesErrorValue, g);
                    g.writeEndObject();
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public UploadErrorWithProperties deserialize(JsonParser p) throws IOException, JsonParseException {
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
            UploadErrorWithProperties value;
            if (BoxMetadataUpdateTask.PATH.equals(tag)) {
                value = UploadErrorWithProperties.path(Serializer.INSTANCE.deserialize(p, true));
            } else if ("other".equals(tag)) {
                value = UploadErrorWithProperties.OTHER;
            } else if ("properties_error".equals(tag)) {
                StoneSerializer.expectField("properties_error", p);
                value = UploadErrorWithProperties.propertiesError(Serializer.INSTANCE.deserialize(p));
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
        OTHER,
        PROPERTIES_ERROR
    }

    private UploadErrorWithProperties(Tag _tag, UploadWriteFailed pathValue, InvalidPropertyGroupError propertiesErrorValue) {
        this._tag = _tag;
        this.pathValue = pathValue;
        this.propertiesErrorValue = propertiesErrorValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isPath() {
        return this._tag == Tag.PATH;
    }

    public static UploadErrorWithProperties path(UploadWriteFailed value) {
        if (value != null) {
            return new UploadErrorWithProperties(Tag.PATH, value, null);
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

    public boolean isPropertiesError() {
        return this._tag == Tag.PROPERTIES_ERROR;
    }

    public static UploadErrorWithProperties propertiesError(InvalidPropertyGroupError value) {
        if (value != null) {
            return new UploadErrorWithProperties(Tag.PROPERTIES_ERROR, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public InvalidPropertyGroupError getPropertiesErrorValue() {
        if (this._tag == Tag.PROPERTIES_ERROR) {
            return this.propertiesErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.PROPERTIES_ERROR, but was Tag." + this._tag.name());
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.pathValue, this.propertiesErrorValue}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UploadErrorWithProperties)) {
            return false;
        }
        UploadErrorWithProperties other = (UploadErrorWithProperties) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$UploadErrorWithProperties$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.pathValue == other.pathValue || this.pathValue.equals(other.pathValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                return true;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                if (this.propertiesErrorValue == other.propertiesErrorValue || this.propertiesErrorValue.equals(other.propertiesErrorValue)) {
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
