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

public final class RemoveFileMemberError {
    public static final RemoveFileMemberError OTHER = new RemoveFileMemberError(Tag.OTHER, null, null, null);
    private final Tag _tag;
    private final SharingFileAccessError accessErrorValue;
    private final MemberAccessLevelResult noExplicitAccessValue;
    private final SharingUserError userErrorValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$RemoveFileMemberError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$RemoveFileMemberError$Tag[Tag.USER_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$RemoveFileMemberError$Tag[Tag.ACCESS_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$RemoveFileMemberError$Tag[Tag.NO_EXPLICIT_ACCESS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$RemoveFileMemberError$Tag[Tag.OTHER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static class Serializer extends UnionSerializer<RemoveFileMemberError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RemoveFileMemberError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$RemoveFileMemberError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("user_error", g);
                    g.writeFieldName("user_error");
                    Serializer.INSTANCE.serialize(value.userErrorValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("access_error", g);
                    g.writeFieldName("access_error");
                    Serializer.INSTANCE.serialize(value.accessErrorValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
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

        public RemoveFileMemberError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            RemoveFileMemberError value;
            if ("user_error".equals(tag)) {
                StoneSerializer.expectField("user_error", p);
                value = RemoveFileMemberError.userError(Serializer.INSTANCE.deserialize(p));
            } else if ("access_error".equals(tag)) {
                StoneSerializer.expectField("access_error", p);
                value = RemoveFileMemberError.accessError(Serializer.INSTANCE.deserialize(p));
            } else if ("no_explicit_access".equals(tag)) {
                value = RemoveFileMemberError.noExplicitAccess(Serializer.INSTANCE.deserialize(p, true));
            } else {
                value = RemoveFileMemberError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        USER_ERROR,
        ACCESS_ERROR,
        NO_EXPLICIT_ACCESS,
        OTHER
    }

    private RemoveFileMemberError(Tag _tag, SharingUserError userErrorValue, SharingFileAccessError accessErrorValue, MemberAccessLevelResult noExplicitAccessValue) {
        this._tag = _tag;
        this.userErrorValue = userErrorValue;
        this.accessErrorValue = accessErrorValue;
        this.noExplicitAccessValue = noExplicitAccessValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isUserError() {
        return this._tag == Tag.USER_ERROR;
    }

    public static RemoveFileMemberError userError(SharingUserError value) {
        if (value != null) {
            return new RemoveFileMemberError(Tag.USER_ERROR, value, null, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public SharingUserError getUserErrorValue() {
        if (this._tag == Tag.USER_ERROR) {
            return this.userErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.USER_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isAccessError() {
        return this._tag == Tag.ACCESS_ERROR;
    }

    public static RemoveFileMemberError accessError(SharingFileAccessError value) {
        if (value != null) {
            return new RemoveFileMemberError(Tag.ACCESS_ERROR, null, value, null);
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

    public static RemoveFileMemberError noExplicitAccess(MemberAccessLevelResult value) {
        if (value != null) {
            return new RemoveFileMemberError(Tag.NO_EXPLICIT_ACCESS, null, null, value);
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
        return Arrays.hashCode(new Object[]{this._tag, this.userErrorValue, this.accessErrorValue, this.noExplicitAccessValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RemoveFileMemberError)) {
            return false;
        }
        RemoveFileMemberError other = (RemoveFileMemberError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$RemoveFileMemberError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.userErrorValue == other.userErrorValue || this.userErrorValue.equals(other.userErrorValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.accessErrorValue == other.accessErrorValue || this.accessErrorValue.equals(other.accessErrorValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                if (this.noExplicitAccessValue == other.noExplicitAccessValue || this.noExplicitAccessValue.equals(other.noExplicitAccessValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
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
