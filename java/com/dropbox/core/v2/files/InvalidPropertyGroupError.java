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
import java.util.regex.Pattern;
import net.sqlcipher.database.SQLiteDatabase;

public final class InvalidPropertyGroupError {
    public static final InvalidPropertyGroupError DOES_NOT_FIT_TEMPLATE = new InvalidPropertyGroupError(Tag.DOES_NOT_FIT_TEMPLATE, null, null);
    public static final InvalidPropertyGroupError OTHER = new InvalidPropertyGroupError(Tag.OTHER, null, null);
    public static final InvalidPropertyGroupError PROPERTY_FIELD_TOO_LARGE = new InvalidPropertyGroupError(Tag.PROPERTY_FIELD_TOO_LARGE, null, null);
    public static final InvalidPropertyGroupError RESTRICTED_CONTENT = new InvalidPropertyGroupError(Tag.RESTRICTED_CONTENT, null, null);
    private final Tag _tag;
    private final LookupError pathValue;
    private final String templateNotFoundValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$InvalidPropertyGroupError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$InvalidPropertyGroupError$Tag[Tag.TEMPLATE_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$InvalidPropertyGroupError$Tag[Tag.RESTRICTED_CONTENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$InvalidPropertyGroupError$Tag[Tag.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$InvalidPropertyGroupError$Tag[Tag.PATH.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$InvalidPropertyGroupError$Tag[Tag.PROPERTY_FIELD_TOO_LARGE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$InvalidPropertyGroupError$Tag[Tag.DOES_NOT_FIT_TEMPLATE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    static class Serializer extends UnionSerializer<InvalidPropertyGroupError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(InvalidPropertyGroupError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$InvalidPropertyGroupError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("template_not_found", g);
                    g.writeFieldName("template_not_found");
                    StoneSerializers.string().serialize(value.templateNotFoundValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("restricted_content");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("other");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeStartObject();
                    writeTag(BoxMetadataUpdateTask.PATH, g);
                    g.writeFieldName(BoxMetadataUpdateTask.PATH);
                    com.dropbox.core.v2.files.LookupError.Serializer.INSTANCE.serialize(value.pathValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("property_field_too_large");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeString("does_not_fit_template");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public InvalidPropertyGroupError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            InvalidPropertyGroupError value;
            if ("template_not_found".equals(tag)) {
                StoneSerializer.expectField("template_not_found", p);
                value = InvalidPropertyGroupError.templateNotFound((String) StoneSerializers.string().deserialize(p));
            } else if ("restricted_content".equals(tag)) {
                value = InvalidPropertyGroupError.RESTRICTED_CONTENT;
            } else if ("other".equals(tag)) {
                value = InvalidPropertyGroupError.OTHER;
            } else if (BoxMetadataUpdateTask.PATH.equals(tag)) {
                StoneSerializer.expectField(BoxMetadataUpdateTask.PATH, p);
                value = InvalidPropertyGroupError.path(com.dropbox.core.v2.files.LookupError.Serializer.INSTANCE.deserialize(p));
            } else if ("property_field_too_large".equals(tag)) {
                value = InvalidPropertyGroupError.PROPERTY_FIELD_TOO_LARGE;
            } else if ("does_not_fit_template".equals(tag)) {
                value = InvalidPropertyGroupError.DOES_NOT_FIT_TEMPLATE;
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
        TEMPLATE_NOT_FOUND,
        RESTRICTED_CONTENT,
        OTHER,
        PATH,
        PROPERTY_FIELD_TOO_LARGE,
        DOES_NOT_FIT_TEMPLATE
    }

    private InvalidPropertyGroupError(Tag _tag, String templateNotFoundValue, LookupError pathValue) {
        this._tag = _tag;
        this.templateNotFoundValue = templateNotFoundValue;
        this.pathValue = pathValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isTemplateNotFound() {
        return this._tag == Tag.TEMPLATE_NOT_FOUND;
    }

    public static InvalidPropertyGroupError templateNotFound(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() < 1) {
            throw new IllegalArgumentException("String is shorter than 1");
        } else if (Pattern.matches("(/|ptid:).*", value)) {
            return new InvalidPropertyGroupError(Tag.TEMPLATE_NOT_FOUND, value, null);
        } else {
            throw new IllegalArgumentException("String does not match pattern");
        }
    }

    public String getTemplateNotFoundValue() {
        if (this._tag == Tag.TEMPLATE_NOT_FOUND) {
            return this.templateNotFoundValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.TEMPLATE_NOT_FOUND, but was Tag." + this._tag.name());
    }

    public boolean isRestrictedContent() {
        return this._tag == Tag.RESTRICTED_CONTENT;
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public boolean isPath() {
        return this._tag == Tag.PATH;
    }

    public static InvalidPropertyGroupError path(LookupError value) {
        if (value != null) {
            return new InvalidPropertyGroupError(Tag.PATH, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public LookupError getPathValue() {
        if (this._tag == Tag.PATH) {
            return this.pathValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.PATH, but was Tag." + this._tag.name());
    }

    public boolean isPropertyFieldTooLarge() {
        return this._tag == Tag.PROPERTY_FIELD_TOO_LARGE;
    }

    public boolean isDoesNotFitTemplate() {
        return this._tag == Tag.DOES_NOT_FIT_TEMPLATE;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.templateNotFoundValue, this.pathValue}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof InvalidPropertyGroupError)) {
            return false;
        }
        InvalidPropertyGroupError other = (InvalidPropertyGroupError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$InvalidPropertyGroupError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.templateNotFoundValue == other.templateNotFoundValue || this.templateNotFoundValue.equals(other.templateNotFoundValue)) {
                    z = true;
                }
                return z;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
            case IRemoteStorage.BOX_REMOTE /*6*/:
                return true;
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                if (this.pathValue == other.pathValue || this.pathValue.equals(other.pathValue)) {
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
