package com.dropbox.core.v2.sharing;

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

public final class FileMemberActionError {
    public static final FileMemberActionError INVALID_MEMBER = new FileMemberActionError(Tag.INVALID_MEMBER, null, null);
    public static final FileMemberActionError NO_PERMISSION = new FileMemberActionError(Tag.NO_PERMISSION, null, null);
    public static final FileMemberActionError OTHER = new FileMemberActionError(Tag.OTHER, null, null);
    private final Tag _tag;
    private final SharingFileAccessError accessErrorValue;
    private final MemberAccessLevelResult noExplicitAccessValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$FileMemberActionError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FileMemberActionError$Tag[Tag.INVALID_MEMBER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FileMemberActionError$Tag[Tag.NO_PERMISSION.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FileMemberActionError$Tag[Tag.ACCESS_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FileMemberActionError$Tag[Tag.NO_EXPLICIT_ACCESS.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FileMemberActionError$Tag[Tag.OTHER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    static class Serializer extends UnionSerializer<FileMemberActionError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(FileMemberActionError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$FileMemberActionError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("invalid_member");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("no_permission");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeStartObject();
                    writeTag("access_error", g);
                    g.writeFieldName("access_error");
                    Serializer.INSTANCE.serialize(value.accessErrorValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeStartObject();
                    writeTag("no_explicit_access", g);
                    Serializer.INSTANCE.serialize(value.noExplicitAccessValue, g, true);
                    g.writeEndObject();
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public FileMemberActionError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            FileMemberActionError value;
            if ("invalid_member".equals(tag)) {
                value = FileMemberActionError.INVALID_MEMBER;
            } else if ("no_permission".equals(tag)) {
                value = FileMemberActionError.NO_PERMISSION;
            } else if ("access_error".equals(tag)) {
                StoneSerializer.expectField("access_error", p);
                value = FileMemberActionError.accessError(Serializer.INSTANCE.deserialize(p));
            } else if ("no_explicit_access".equals(tag)) {
                value = FileMemberActionError.noExplicitAccess(Serializer.INSTANCE.deserialize(p, true));
            } else {
                value = FileMemberActionError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        INVALID_MEMBER,
        NO_PERMISSION,
        ACCESS_ERROR,
        NO_EXPLICIT_ACCESS,
        OTHER
    }

    private FileMemberActionError(Tag _tag, SharingFileAccessError accessErrorValue, MemberAccessLevelResult noExplicitAccessValue) {
        this._tag = _tag;
        this.accessErrorValue = accessErrorValue;
        this.noExplicitAccessValue = noExplicitAccessValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isInvalidMember() {
        return this._tag == Tag.INVALID_MEMBER;
    }

    public boolean isNoPermission() {
        return this._tag == Tag.NO_PERMISSION;
    }

    public boolean isAccessError() {
        return this._tag == Tag.ACCESS_ERROR;
    }

    public static FileMemberActionError accessError(SharingFileAccessError value) {
        if (value != null) {
            return new FileMemberActionError(Tag.ACCESS_ERROR, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public SharingFileAccessError getAccessErrorValue() {
        if (this._tag == Tag.ACCESS_ERROR) {
            return this.accessErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.ACCESS_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isNoExplicitAccess() {
        return this._tag == Tag.NO_EXPLICIT_ACCESS;
    }

    public static FileMemberActionError noExplicitAccess(MemberAccessLevelResult value) {
        if (value != null) {
            return new FileMemberActionError(Tag.NO_EXPLICIT_ACCESS, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public MemberAccessLevelResult getNoExplicitAccessValue() {
        if (this._tag == Tag.NO_EXPLICIT_ACCESS) {
            return this.noExplicitAccessValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.NO_EXPLICIT_ACCESS, but was Tag." + this._tag.name());
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.accessErrorValue, this.noExplicitAccessValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FileMemberActionError)) {
            return false;
        }
        FileMemberActionError other = (FileMemberActionError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$FileMemberActionError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                return true;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                return true;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                if (this.accessErrorValue == other.accessErrorValue || this.accessErrorValue.equals(other.accessErrorValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                if (this.noExplicitAccessValue == other.noExplicitAccessValue || this.noExplicitAccessValue.equals(other.noExplicitAccessValue)) {
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
