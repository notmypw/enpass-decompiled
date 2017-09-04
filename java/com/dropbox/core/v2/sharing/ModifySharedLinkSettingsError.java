package com.dropbox.core.v2.sharing;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.UnionSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import in.sinew.enpass.IRemoteStorage;
import java.io.IOException;
import java.util.Arrays;
import net.sqlcipher.database.SQLiteDatabase;

public final class ModifySharedLinkSettingsError {
    public static final ModifySharedLinkSettingsError EMAIL_NOT_VERIFIED = new ModifySharedLinkSettingsError(Tag.EMAIL_NOT_VERIFIED, null);
    public static final ModifySharedLinkSettingsError OTHER = new ModifySharedLinkSettingsError(Tag.OTHER, null);
    public static final ModifySharedLinkSettingsError SHARED_LINK_ACCESS_DENIED = new ModifySharedLinkSettingsError(Tag.SHARED_LINK_ACCESS_DENIED, null);
    public static final ModifySharedLinkSettingsError SHARED_LINK_NOT_FOUND = new ModifySharedLinkSettingsError(Tag.SHARED_LINK_NOT_FOUND, null);
    public static final ModifySharedLinkSettingsError UNSUPPORTED_LINK_TYPE = new ModifySharedLinkSettingsError(Tag.UNSUPPORTED_LINK_TYPE, null);
    private final Tag _tag;
    private final SharedLinkSettingsError settingsErrorValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$ModifySharedLinkSettingsError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ModifySharedLinkSettingsError$Tag[Tag.SHARED_LINK_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ModifySharedLinkSettingsError$Tag[Tag.SHARED_LINK_ACCESS_DENIED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ModifySharedLinkSettingsError$Tag[Tag.UNSUPPORTED_LINK_TYPE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ModifySharedLinkSettingsError$Tag[Tag.OTHER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ModifySharedLinkSettingsError$Tag[Tag.SETTINGS_ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ModifySharedLinkSettingsError$Tag[Tag.EMAIL_NOT_VERIFIED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    static class Serializer extends UnionSerializer<ModifySharedLinkSettingsError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ModifySharedLinkSettingsError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$ModifySharedLinkSettingsError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("shared_link_not_found");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("shared_link_access_denied");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("unsupported_link_type");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("other");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeStartObject();
                    writeTag("settings_error", g);
                    g.writeFieldName("settings_error");
                    Serializer.INSTANCE.serialize(value.settingsErrorValue, g);
                    g.writeEndObject();
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeString("email_not_verified");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public ModifySharedLinkSettingsError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            ModifySharedLinkSettingsError value;
            if ("shared_link_not_found".equals(tag)) {
                value = ModifySharedLinkSettingsError.SHARED_LINK_NOT_FOUND;
            } else if ("shared_link_access_denied".equals(tag)) {
                value = ModifySharedLinkSettingsError.SHARED_LINK_ACCESS_DENIED;
            } else if ("unsupported_link_type".equals(tag)) {
                value = ModifySharedLinkSettingsError.UNSUPPORTED_LINK_TYPE;
            } else if ("other".equals(tag)) {
                value = ModifySharedLinkSettingsError.OTHER;
            } else if ("settings_error".equals(tag)) {
                StoneSerializer.expectField("settings_error", p);
                value = ModifySharedLinkSettingsError.settingsError(Serializer.INSTANCE.deserialize(p));
            } else if ("email_not_verified".equals(tag)) {
                value = ModifySharedLinkSettingsError.EMAIL_NOT_VERIFIED;
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
        SHARED_LINK_NOT_FOUND,
        SHARED_LINK_ACCESS_DENIED,
        UNSUPPORTED_LINK_TYPE,
        OTHER,
        SETTINGS_ERROR,
        EMAIL_NOT_VERIFIED
    }

    private ModifySharedLinkSettingsError(Tag _tag, SharedLinkSettingsError settingsErrorValue) {
        this._tag = _tag;
        this.settingsErrorValue = settingsErrorValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isSharedLinkNotFound() {
        return this._tag == Tag.SHARED_LINK_NOT_FOUND;
    }

    public boolean isSharedLinkAccessDenied() {
        return this._tag == Tag.SHARED_LINK_ACCESS_DENIED;
    }

    public boolean isUnsupportedLinkType() {
        return this._tag == Tag.UNSUPPORTED_LINK_TYPE;
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public boolean isSettingsError() {
        return this._tag == Tag.SETTINGS_ERROR;
    }

    public static ModifySharedLinkSettingsError settingsError(SharedLinkSettingsError value) {
        if (value != null) {
            return new ModifySharedLinkSettingsError(Tag.SETTINGS_ERROR, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public SharedLinkSettingsError getSettingsErrorValue() {
        if (this._tag == Tag.SETTINGS_ERROR) {
            return this.settingsErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.SETTINGS_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isEmailNotVerified() {
        return this._tag == Tag.EMAIL_NOT_VERIFIED;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.settingsErrorValue}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ModifySharedLinkSettingsError)) {
            return false;
        }
        ModifySharedLinkSettingsError other = (ModifySharedLinkSettingsError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$ModifySharedLinkSettingsError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
            case IRemoteStorage.BOX_REMOTE /*6*/:
                return true;
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                if (this.settingsErrorValue == other.settingsErrorValue || this.settingsErrorValue.equals(other.settingsErrorValue)) {
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
