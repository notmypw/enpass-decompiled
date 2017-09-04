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

public final class AlphaGetMetadataError {
    private final Tag _tag;
    private final LookupError pathValue;
    private final LookUpPropertiesError propertiesErrorValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$AlphaGetMetadataError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$AlphaGetMetadataError$Tag[Tag.PATH.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$AlphaGetMetadataError$Tag[Tag.PROPERTIES_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<AlphaGetMetadataError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(AlphaGetMetadataError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$AlphaGetMetadataError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag(BoxMetadataUpdateTask.PATH, g);
                    g.writeFieldName(BoxMetadataUpdateTask.PATH);
                    com.dropbox.core.v2.files.LookupError.Serializer.INSTANCE.serialize(value.pathValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
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

        public AlphaGetMetadataError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            AlphaGetMetadataError value;
            if (BoxMetadataUpdateTask.PATH.equals(tag)) {
                StoneSerializer.expectField(BoxMetadataUpdateTask.PATH, p);
                value = AlphaGetMetadataError.path(com.dropbox.core.v2.files.LookupError.Serializer.INSTANCE.deserialize(p));
            } else if ("properties_error".equals(tag)) {
                StoneSerializer.expectField("properties_error", p);
                value = AlphaGetMetadataError.propertiesError(Serializer.INSTANCE.deserialize(p));
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
        PROPERTIES_ERROR
    }

    private AlphaGetMetadataError(Tag _tag, LookupError pathValue, LookUpPropertiesError propertiesErrorValue) {
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

    public static AlphaGetMetadataError path(LookupError value) {
        if (value != null) {
            return new AlphaGetMetadataError(Tag.PATH, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public LookupError getPathValue() {
        if (this._tag == Tag.PATH) {
            return this.pathValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.PATH, but was Tag." + this._tag.name());
    }

    public boolean isPropertiesError() {
        return this._tag == Tag.PROPERTIES_ERROR;
    }

    public static AlphaGetMetadataError propertiesError(LookUpPropertiesError value) {
        if (value != null) {
            return new AlphaGetMetadataError(Tag.PROPERTIES_ERROR, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public LookUpPropertiesError getPropertiesErrorValue() {
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
        if (!(obj instanceof AlphaGetMetadataError)) {
            return false;
        }
        AlphaGetMetadataError other = (AlphaGetMetadataError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$AlphaGetMetadataError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.pathValue == other.pathValue || this.pathValue.equals(other.pathValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
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
