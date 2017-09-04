package com.dropbox.core.v2.properties;

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

public final class ModifyPropertyTemplateError {
    public static final ModifyPropertyTemplateError CONFLICTING_PROPERTY_NAMES = new ModifyPropertyTemplateError(Tag.CONFLICTING_PROPERTY_NAMES, null);
    public static final ModifyPropertyTemplateError OTHER = new ModifyPropertyTemplateError(Tag.OTHER, null);
    public static final ModifyPropertyTemplateError RESTRICTED_CONTENT = new ModifyPropertyTemplateError(Tag.RESTRICTED_CONTENT, null);
    public static final ModifyPropertyTemplateError TEMPLATE_ATTRIBUTE_TOO_LARGE = new ModifyPropertyTemplateError(Tag.TEMPLATE_ATTRIBUTE_TOO_LARGE, null);
    public static final ModifyPropertyTemplateError TOO_MANY_PROPERTIES = new ModifyPropertyTemplateError(Tag.TOO_MANY_PROPERTIES, null);
    public static final ModifyPropertyTemplateError TOO_MANY_TEMPLATES = new ModifyPropertyTemplateError(Tag.TOO_MANY_TEMPLATES, null);
    private final Tag _tag;
    private final String templateNotFoundValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$properties$ModifyPropertyTemplateError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$properties$ModifyPropertyTemplateError$Tag[Tag.TEMPLATE_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$properties$ModifyPropertyTemplateError$Tag[Tag.RESTRICTED_CONTENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$properties$ModifyPropertyTemplateError$Tag[Tag.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$properties$ModifyPropertyTemplateError$Tag[Tag.CONFLICTING_PROPERTY_NAMES.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$properties$ModifyPropertyTemplateError$Tag[Tag.TOO_MANY_PROPERTIES.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$properties$ModifyPropertyTemplateError$Tag[Tag.TOO_MANY_TEMPLATES.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$properties$ModifyPropertyTemplateError$Tag[Tag.TEMPLATE_ATTRIBUTE_TOO_LARGE.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public static class Serializer extends UnionSerializer<ModifyPropertyTemplateError> {
        public static final Serializer INSTANCE = new Serializer();

        public void serialize(ModifyPropertyTemplateError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$properties$ModifyPropertyTemplateError$Tag[value.tag().ordinal()]) {
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
                    g.writeString("conflicting_property_names");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("too_many_properties");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeString("too_many_templates");
                    return;
                case IRemoteStorage.PIN /*7*/:
                    g.writeString("template_attribute_too_large");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public ModifyPropertyTemplateError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            ModifyPropertyTemplateError value;
            if ("template_not_found".equals(tag)) {
                StoneSerializer.expectField("template_not_found", p);
                value = ModifyPropertyTemplateError.templateNotFound((String) StoneSerializers.string().deserialize(p));
            } else if ("restricted_content".equals(tag)) {
                value = ModifyPropertyTemplateError.RESTRICTED_CONTENT;
            } else if ("other".equals(tag)) {
                value = ModifyPropertyTemplateError.OTHER;
            } else if ("conflicting_property_names".equals(tag)) {
                value = ModifyPropertyTemplateError.CONFLICTING_PROPERTY_NAMES;
            } else if ("too_many_properties".equals(tag)) {
                value = ModifyPropertyTemplateError.TOO_MANY_PROPERTIES;
            } else if ("too_many_templates".equals(tag)) {
                value = ModifyPropertyTemplateError.TOO_MANY_TEMPLATES;
            } else if ("template_attribute_too_large".equals(tag)) {
                value = ModifyPropertyTemplateError.TEMPLATE_ATTRIBUTE_TOO_LARGE;
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
        CONFLICTING_PROPERTY_NAMES,
        TOO_MANY_PROPERTIES,
        TOO_MANY_TEMPLATES,
        TEMPLATE_ATTRIBUTE_TOO_LARGE
    }

    private ModifyPropertyTemplateError(Tag _tag, String templateNotFoundValue) {
        this._tag = _tag;
        this.templateNotFoundValue = templateNotFoundValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isTemplateNotFound() {
        return this._tag == Tag.TEMPLATE_NOT_FOUND;
    }

    public static ModifyPropertyTemplateError templateNotFound(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() < 1) {
            throw new IllegalArgumentException("String is shorter than 1");
        } else if (Pattern.matches("(/|ptid:).*", value)) {
            return new ModifyPropertyTemplateError(Tag.TEMPLATE_NOT_FOUND, value);
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

    public boolean isConflictingPropertyNames() {
        return this._tag == Tag.CONFLICTING_PROPERTY_NAMES;
    }

    public boolean isTooManyProperties() {
        return this._tag == Tag.TOO_MANY_PROPERTIES;
    }

    public boolean isTooManyTemplates() {
        return this._tag == Tag.TOO_MANY_TEMPLATES;
    }

    public boolean isTemplateAttributeTooLarge() {
        return this._tag == Tag.TEMPLATE_ATTRIBUTE_TOO_LARGE;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.templateNotFoundValue}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ModifyPropertyTemplateError)) {
            return false;
        }
        ModifyPropertyTemplateError other = (ModifyPropertyTemplateError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$properties$ModifyPropertyTemplateError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.templateNotFoundValue == other.templateNotFoundValue || this.templateNotFoundValue.equals(other.templateNotFoundValue)) {
                    z = true;
                }
                return z;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
            case IRemoteStorage.BOX_REMOTE /*6*/:
            case IRemoteStorage.PIN /*7*/:
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
