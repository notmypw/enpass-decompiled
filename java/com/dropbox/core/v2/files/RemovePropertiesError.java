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

public final class RemovePropertiesError {
    public static final RemovePropertiesError OTHER = new RemovePropertiesError(Tag.OTHER, null, null, null);
    public static final RemovePropertiesError RESTRICTED_CONTENT = new RemovePropertiesError(Tag.RESTRICTED_CONTENT, null, null, null);
    private final Tag _tag;
    private final LookupError pathValue;
    private final LookUpPropertiesError propertyGroupLookupValue;
    private final String templateNotFoundValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$RemovePropertiesError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$RemovePropertiesError$Tag[Tag.TEMPLATE_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RemovePropertiesError$Tag[Tag.RESTRICTED_CONTENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RemovePropertiesError$Tag[Tag.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RemovePropertiesError$Tag[Tag.PATH.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$RemovePropertiesError$Tag[Tag.PROPERTY_GROUP_LOOKUP.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    static class Serializer extends UnionSerializer<RemovePropertiesError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RemovePropertiesError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$RemovePropertiesError$Tag[value.tag().ordinal()]) {
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
                    g.writeStartObject();
                    writeTag("property_group_lookup", g);
                    g.writeFieldName("property_group_lookup");
                    Serializer.INSTANCE.serialize(value.propertyGroupLookupValue, g);
                    g.writeEndObject();
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public RemovePropertiesError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            RemovePropertiesError value;
            if ("template_not_found".equals(tag)) {
                StoneSerializer.expectField("template_not_found", p);
                value = RemovePropertiesError.templateNotFound((String) StoneSerializers.string().deserialize(p));
            } else if ("restricted_content".equals(tag)) {
                value = RemovePropertiesError.RESTRICTED_CONTENT;
            } else if ("other".equals(tag)) {
                value = RemovePropertiesError.OTHER;
            } else if (BoxMetadataUpdateTask.PATH.equals(tag)) {
                StoneSerializer.expectField(BoxMetadataUpdateTask.PATH, p);
                value = RemovePropertiesError.path(com.dropbox.core.v2.files.LookupError.Serializer.INSTANCE.deserialize(p));
            } else if ("property_group_lookup".equals(tag)) {
                StoneSerializer.expectField("property_group_lookup", p);
                value = RemovePropertiesError.propertyGroupLookup(Serializer.INSTANCE.deserialize(p));
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
        PROPERTY_GROUP_LOOKUP
    }

    private RemovePropertiesError(Tag _tag, String templateNotFoundValue, LookupError pathValue, LookUpPropertiesError propertyGroupLookupValue) {
        this._tag = _tag;
        this.templateNotFoundValue = templateNotFoundValue;
        this.pathValue = pathValue;
        this.propertyGroupLookupValue = propertyGroupLookupValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isTemplateNotFound() {
        return this._tag == Tag.TEMPLATE_NOT_FOUND;
    }

    public static RemovePropertiesError templateNotFound(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() < 1) {
            throw new IllegalArgumentException("String is shorter than 1");
        } else if (Pattern.matches("(/|ptid:).*", value)) {
            return new RemovePropertiesError(Tag.TEMPLATE_NOT_FOUND, value, null, null);
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

    public static RemovePropertiesError path(LookupError value) {
        if (value != null) {
            return new RemovePropertiesError(Tag.PATH, null, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public LookupError getPathValue() {
        if (this._tag == Tag.PATH) {
            return this.pathValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.PATH, but was Tag." + this._tag.name());
    }

    public boolean isPropertyGroupLookup() {
        return this._tag == Tag.PROPERTY_GROUP_LOOKUP;
    }

    public static RemovePropertiesError propertyGroupLookup(LookUpPropertiesError value) {
        if (value != null) {
            return new RemovePropertiesError(Tag.PROPERTY_GROUP_LOOKUP, null, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public LookUpPropertiesError getPropertyGroupLookupValue() {
        if (this._tag == Tag.PROPERTY_GROUP_LOOKUP) {
            return this.propertyGroupLookupValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.PROPERTY_GROUP_LOOKUP, but was Tag." + this._tag.name());
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.templateNotFoundValue, this.pathValue, this.propertyGroupLookupValue}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RemovePropertiesError)) {
            return false;
        }
        RemovePropertiesError other = (RemovePropertiesError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$RemovePropertiesError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.templateNotFoundValue == other.templateNotFoundValue || this.templateNotFoundValue.equals(other.templateNotFoundValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                return true;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                return true;
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                if (this.pathValue == other.pathValue || this.pathValue.equals(other.pathValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                if (this.propertyGroupLookupValue == other.propertyGroupLookupValue || this.propertyGroupLookupValue.equals(other.propertyGroupLookupValue)) {
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
