package com.dropbox.core.v2.sharing;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.UnionSerializer;
import com.dropbox.core.v2.files.LookupError;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import net.sqlcipher.database.SQLiteDatabase;

public final class CreateSharedLinkWithSettingsError {
    public static final CreateSharedLinkWithSettingsError ACCESS_DENIED = new CreateSharedLinkWithSettingsError(Tag.ACCESS_DENIED, null, null);
    public static final CreateSharedLinkWithSettingsError EMAIL_NOT_VERIFIED = new CreateSharedLinkWithSettingsError(Tag.EMAIL_NOT_VERIFIED, null, null);
    public static final CreateSharedLinkWithSettingsError SHARED_LINK_ALREADY_EXISTS = new CreateSharedLinkWithSettingsError(Tag.SHARED_LINK_ALREADY_EXISTS, null, null);
    private final Tag _tag;
    private final LookupError pathValue;
    private final SharedLinkSettingsError settingsErrorValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$CreateSharedLinkWithSettingsError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$CreateSharedLinkWithSettingsError$Tag[Tag.PATH.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$CreateSharedLinkWithSettingsError$Tag[Tag.EMAIL_NOT_VERIFIED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$CreateSharedLinkWithSettingsError$Tag[Tag.SHARED_LINK_ALREADY_EXISTS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$CreateSharedLinkWithSettingsError$Tag[Tag.SETTINGS_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$CreateSharedLinkWithSettingsError$Tag[Tag.ACCESS_DENIED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    static class Serializer extends UnionSerializer<CreateSharedLinkWithSettingsError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(CreateSharedLinkWithSettingsError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$CreateSharedLinkWithSettingsError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag(BoxMetadataUpdateTask.PATH, g);
                    g.writeFieldName(BoxMetadataUpdateTask.PATH);
                    com.dropbox.core.v2.files.LookupError.Serializer.INSTANCE.serialize(value.pathValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("email_not_verified");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("shared_link_already_exists");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeStartObject();
                    writeTag("settings_error", g);
                    g.writeFieldName("settings_error");
                    Serializer.INSTANCE.serialize(value.settingsErrorValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("access_denied");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public CreateSharedLinkWithSettingsError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            CreateSharedLinkWithSettingsError value;
            if (BoxMetadataUpdateTask.PATH.equals(tag)) {
                StoneSerializer.expectField(BoxMetadataUpdateTask.PATH, p);
                value = CreateSharedLinkWithSettingsError.path(com.dropbox.core.v2.files.LookupError.Serializer.INSTANCE.deserialize(p));
            } else if ("email_not_verified".equals(tag)) {
                value = CreateSharedLinkWithSettingsError.EMAIL_NOT_VERIFIED;
            } else if ("shared_link_already_exists".equals(tag)) {
                value = CreateSharedLinkWithSettingsError.SHARED_LINK_ALREADY_EXISTS;
            } else if ("settings_error".equals(tag)) {
                StoneSerializer.expectField("settings_error", p);
                value = CreateSharedLinkWithSettingsError.settingsError(Serializer.INSTANCE.deserialize(p));
            } else if ("access_denied".equals(tag)) {
                value = CreateSharedLinkWithSettingsError.ACCESS_DENIED;
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
        EMAIL_NOT_VERIFIED,
        SHARED_LINK_ALREADY_EXISTS,
        SETTINGS_ERROR,
        ACCESS_DENIED
    }

    private CreateSharedLinkWithSettingsError(Tag _tag, LookupError pathValue, SharedLinkSettingsError settingsErrorValue) {
        this._tag = _tag;
        this.pathValue = pathValue;
        this.settingsErrorValue = settingsErrorValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isPath() {
        return this._tag == Tag.PATH;
    }

    public static CreateSharedLinkWithSettingsError path(LookupError value) {
        if (value != null) {
            return new CreateSharedLinkWithSettingsError(Tag.PATH, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public LookupError getPathValue() {
        if (this._tag == Tag.PATH) {
            return this.pathValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.PATH, but was Tag." + this._tag.name());
    }

    public boolean isEmailNotVerified() {
        return this._tag == Tag.EMAIL_NOT_VERIFIED;
    }

    public boolean isSharedLinkAlreadyExists() {
        return this._tag == Tag.SHARED_LINK_ALREADY_EXISTS;
    }

    public boolean isSettingsError() {
        return this._tag == Tag.SETTINGS_ERROR;
    }

    public static CreateSharedLinkWithSettingsError settingsError(SharedLinkSettingsError value) {
        if (value != null) {
            return new CreateSharedLinkWithSettingsError(Tag.SETTINGS_ERROR, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public SharedLinkSettingsError getSettingsErrorValue() {
        if (this._tag == Tag.SETTINGS_ERROR) {
            return this.settingsErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.SETTINGS_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isAccessDenied() {
        return this._tag == Tag.ACCESS_DENIED;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.pathValue, this.settingsErrorValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CreateSharedLinkWithSettingsError)) {
            return false;
        }
        CreateSharedLinkWithSettingsError other = (CreateSharedLinkWithSettingsError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$CreateSharedLinkWithSettingsError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.pathValue == other.pathValue || this.pathValue.equals(other.pathValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                return true;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                return true;
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                if (this.settingsErrorValue == other.settingsErrorValue || this.settingsErrorValue.equals(other.settingsErrorValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
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
