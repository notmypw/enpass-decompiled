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

public final class GetFileMetadataError {
    public static final GetFileMetadataError OTHER = new GetFileMetadataError(Tag.OTHER, null, null);
    private final Tag _tag;
    private final SharingFileAccessError accessErrorValue;
    private final SharingUserError userErrorValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$GetFileMetadataError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$GetFileMetadataError$Tag[Tag.USER_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$GetFileMetadataError$Tag[Tag.ACCESS_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$GetFileMetadataError$Tag[Tag.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<GetFileMetadataError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GetFileMetadataError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$GetFileMetadataError$Tag[value.tag().ordinal()]) {
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
                default:
                    g.writeString("other");
                    return;
            }
        }

        public GetFileMetadataError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            GetFileMetadataError value;
            if ("user_error".equals(tag)) {
                StoneSerializer.expectField("user_error", p);
                value = GetFileMetadataError.userError(Serializer.INSTANCE.deserialize(p));
            } else if ("access_error".equals(tag)) {
                StoneSerializer.expectField("access_error", p);
                value = GetFileMetadataError.accessError(Serializer.INSTANCE.deserialize(p));
            } else {
                value = GetFileMetadataError.OTHER;
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
        OTHER
    }

    private GetFileMetadataError(Tag _tag, SharingUserError userErrorValue, SharingFileAccessError accessErrorValue) {
        this._tag = _tag;
        this.userErrorValue = userErrorValue;
        this.accessErrorValue = accessErrorValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isUserError() {
        return this._tag == Tag.USER_ERROR;
    }

    public static GetFileMetadataError userError(SharingUserError value) {
        if (value != null) {
            return new GetFileMetadataError(Tag.USER_ERROR, value, null);
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

    public static GetFileMetadataError accessError(SharingFileAccessError value) {
        if (value != null) {
            return new GetFileMetadataError(Tag.ACCESS_ERROR, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public SharingFileAccessError getAccessErrorValue() {
        if (this._tag == Tag.ACCESS_ERROR) {
            return this.accessErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.ACCESS_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.userErrorValue, this.accessErrorValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GetFileMetadataError)) {
            return false;
        }
        GetFileMetadataError other = (GetFileMetadataError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$GetFileMetadataError$Tag[this._tag.ordinal()]) {
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
