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
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;
import net.sqlcipher.database.SQLiteDatabase;

public final class PropertyTemplateError {
    public static final PropertyTemplateError OTHER = new PropertyTemplateError(Tag.OTHER, null);
    public static final PropertyTemplateError RESTRICTED_CONTENT = new PropertyTemplateError(Tag.RESTRICTED_CONTENT, null);
    private final Tag _tag;
    private final String templateNotFoundValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$properties$PropertyTemplateError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$properties$PropertyTemplateError$Tag[Tag.TEMPLATE_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$properties$PropertyTemplateError$Tag[Tag.RESTRICTED_CONTENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$properties$PropertyTemplateError$Tag[Tag.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public static class Serializer extends UnionSerializer<PropertyTemplateError> {
        public static final Serializer INSTANCE = new Serializer();

        public void serialize(PropertyTemplateError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$properties$PropertyTemplateError$Tag[value.tag().ordinal()]) {
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
                default:
                    g.writeString("other");
                    return;
            }
        }

        public PropertyTemplateError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            PropertyTemplateError value;
            if ("template_not_found".equals(tag)) {
                StoneSerializer.expectField("template_not_found", p);
                value = PropertyTemplateError.templateNotFound((String) StoneSerializers.string().deserialize(p));
            } else if ("restricted_content".equals(tag)) {
                value = PropertyTemplateError.RESTRICTED_CONTENT;
            } else {
                value = PropertyTemplateError.OTHER;
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
        OTHER
    }

    private PropertyTemplateError(Tag _tag, String templateNotFoundValue) {
        this._tag = _tag;
        this.templateNotFoundValue = templateNotFoundValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isTemplateNotFound() {
        return this._tag == Tag.TEMPLATE_NOT_FOUND;
    }

    public static PropertyTemplateError templateNotFound(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() < 1) {
            throw new IllegalArgumentException("String is shorter than 1");
        } else if (Pattern.matches("(/|ptid:).*", value)) {
            return new PropertyTemplateError(Tag.TEMPLATE_NOT_FOUND, value);
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

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.templateNotFoundValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PropertyTemplateError)) {
            return false;
        }
        PropertyTemplateError other = (PropertyTemplateError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$properties$PropertyTemplateError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.templateNotFoundValue == other.templateNotFoundValue || this.templateNotFoundValue.equals(other.templateNotFoundValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                return true;
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
