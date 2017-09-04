package com.dropbox.core.v2.sharing;

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
import net.sqlcipher.database.SQLiteDatabase;

public final class FileMemberActionIndividualResult {
    private final Tag _tag;
    private final FileMemberActionError memberErrorValue;
    private final AccessLevel successValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$FileMemberActionIndividualResult$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FileMemberActionIndividualResult$Tag[Tag.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FileMemberActionIndividualResult$Tag[Tag.MEMBER_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<FileMemberActionIndividualResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(FileMemberActionIndividualResult value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$FileMemberActionIndividualResult$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("success", g);
                    g.writeFieldName("success");
                    StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.successValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("member_error", g);
                    g.writeFieldName("member_error");
                    Serializer.INSTANCE.serialize(value.memberErrorValue, g);
                    g.writeEndObject();
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public FileMemberActionIndividualResult deserialize(JsonParser p) throws IOException, JsonParseException {
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
            FileMemberActionIndividualResult value;
            if ("success".equals(tag)) {
                AccessLevel fieldValue = null;
                if (p.getCurrentToken() != JsonToken.END_OBJECT) {
                    StoneSerializer.expectField("success", p);
                    fieldValue = (AccessLevel) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                }
                if (fieldValue == null) {
                    value = FileMemberActionIndividualResult.success();
                } else {
                    value = FileMemberActionIndividualResult.success(fieldValue);
                }
            } else if ("member_error".equals(tag)) {
                StoneSerializer.expectField("member_error", p);
                value = FileMemberActionIndividualResult.memberError(Serializer.INSTANCE.deserialize(p));
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
        SUCCESS,
        MEMBER_ERROR
    }

    private FileMemberActionIndividualResult(Tag _tag, AccessLevel successValue, FileMemberActionError memberErrorValue) {
        this._tag = _tag;
        this.successValue = successValue;
        this.memberErrorValue = memberErrorValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isSuccess() {
        return this._tag == Tag.SUCCESS;
    }

    public static FileMemberActionIndividualResult success(AccessLevel value) {
        return new FileMemberActionIndividualResult(Tag.SUCCESS, value, null);
    }

    public static FileMemberActionIndividualResult success() {
        return success(null);
    }

    public AccessLevel getSuccessValue() {
        if (this._tag == Tag.SUCCESS) {
            return this.successValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.SUCCESS, but was Tag." + this._tag.name());
    }

    public boolean isMemberError() {
        return this._tag == Tag.MEMBER_ERROR;
    }

    public static FileMemberActionIndividualResult memberError(FileMemberActionError value) {
        if (value != null) {
            return new FileMemberActionIndividualResult(Tag.MEMBER_ERROR, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public FileMemberActionError getMemberErrorValue() {
        if (this._tag == Tag.MEMBER_ERROR) {
            return this.memberErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.MEMBER_ERROR, but was Tag." + this._tag.name());
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.successValue, this.memberErrorValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FileMemberActionIndividualResult)) {
            return false;
        }
        FileMemberActionIndividualResult other = (FileMemberActionIndividualResult) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$FileMemberActionIndividualResult$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.successValue == other.successValue || (this.successValue != null && this.successValue.equals(other.successValue))) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.memberErrorValue == other.memberErrorValue || this.memberErrorValue.equals(other.memberErrorValue)) {
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
