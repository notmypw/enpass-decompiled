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

public final class AddPropertiesError {
    public static final AddPropertiesError DOES_NOT_FIT_TEMPLATE = new AddPropertiesError(Tag.DOES_NOT_FIT_TEMPLATE, null, null);
    public static final AddPropertiesError OTHER = new AddPropertiesError(Tag.OTHER, null, null);
    public static final AddPropertiesError PROPERTY_FIELD_TOO_LARGE = new AddPropertiesError(Tag.PROPERTY_FIELD_TOO_LARGE, null, null);
    public static final AddPropertiesError PROPERTY_GROUP_ALREADY_EXISTS = new AddPropertiesError(Tag.PROPERTY_GROUP_ALREADY_EXISTS, null, null);
    public static final AddPropertiesError RESTRICTED_CONTENT = new AddPropertiesError(Tag.RESTRICTED_CONTENT, null, null);
    private final Tag _tag;
    private final LookupError pathValue;
    private final String templateNotFoundValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$AddPropertiesError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$AddPropertiesError$Tag[Tag.TEMPLATE_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$AddPropertiesError$Tag[Tag.RESTRICTED_CONTENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$AddPropertiesError$Tag[Tag.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$AddPropertiesError$Tag[Tag.PATH.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$AddPropertiesError$Tag[Tag.PROPERTY_FIELD_TOO_LARGE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$AddPropertiesError$Tag[Tag.DOES_NOT_FIT_TEMPLATE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$AddPropertiesError$Tag[Tag.PROPERTY_GROUP_ALREADY_EXISTS.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    static class Serializer extends UnionSerializer<AddPropertiesError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(AddPropertiesError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$AddPropertiesError$Tag[value.tag().ordinal()]) {
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
                case IRemoteStorage.PIN /*7*/:
                    g.writeString("property_group_already_exists");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public AddPropertiesError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            AddPropertiesError value;
            if ("template_not_found".equals(tag)) {
                StoneSerializer.expectField("template_not_found", p);
                value = AddPropertiesError.templateNotFound((String) StoneSerializers.string().deserialize(p));
            } else if ("restricted_content".equals(tag)) {
                value = AddPropertiesError.RESTRICTED_CONTENT;
            } else if ("other".equals(tag)) {
                value = AddPropertiesError.OTHER;
            } else if (BoxMetadataUpdateTask.PATH.equals(tag)) {
                StoneSerializer.expectField(BoxMetadataUpdateTask.PATH, p);
                value = AddPropertiesError.path(com.dropbox.core.v2.files.LookupError.Serializer.INSTANCE.deserialize(p));
            } else if ("property_field_too_large".equals(tag)) {
                value = AddPropertiesError.PROPERTY_FIELD_TOO_LARGE;
            } else if ("does_not_fit_template".equals(tag)) {
                value = AddPropertiesError.DOES_NOT_FIT_TEMPLATE;
            } else if ("property_group_already_exists".equals(tag)) {
                value = AddPropertiesError.PROPERTY_GROUP_ALREADY_EXISTS;
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
        DOES_NOT_FIT_TEMPLATE,
        PROPERTY_GROUP_ALREADY_EXISTS
    }

    private AddPropertiesError(Tag _tag, String templateNotFoundValue, LookupError pathValue) {
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

    public static AddPropertiesError templateNotFound(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() < 1) {
            throw new IllegalArgumentException("String is shorter than 1");
        } else if (Pattern.matches("(/|ptid:).*", value)) {
            return new AddPropertiesError(Tag.TEMPLATE_NOT_FOUND, value, null);
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

    public static AddPropertiesError path(LookupError value) {
        if (value != null) {
            return new AddPropertiesError(Tag.PATH, null, value);
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

    public boolean isPropertyGroupAlreadyExists() {
        return this._tag == Tag.PROPERTY_GROUP_ALREADY_EXISTS;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.templateNotFoundValue, this.pathValue}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AddPropertiesError)) {
            return false;
        }
        AddPropertiesError other = (AddPropertiesError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$AddPropertiesError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.templateNotFoundValue == other.templateNotFoundValue || this.templateNotFoundValue.equals(other.templateNotFoundValue)) {
                    z = true;
                }
                return z;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
            case IRemoteStorage.BOX_REMOTE /*6*/:
            case IRemoteStorage.PIN /*7*/:
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
