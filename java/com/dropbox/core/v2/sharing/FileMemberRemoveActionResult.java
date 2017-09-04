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

public final class FileMemberRemoveActionResult {
    public static final FileMemberRemoveActionResult OTHER = new FileMemberRemoveActionResult(Tag.OTHER, null, null);
    private final Tag _tag;
    private final FileMemberActionError memberErrorValue;
    private final MemberAccessLevelResult successValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$FileMemberRemoveActionResult$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FileMemberRemoveActionResult$Tag[Tag.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FileMemberRemoveActionResult$Tag[Tag.MEMBER_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FileMemberRemoveActionResult$Tag[Tag.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<FileMemberRemoveActionResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(FileMemberRemoveActionResult value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$FileMemberRemoveActionResult$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("success", g);
                    Serializer.INSTANCE.serialize(value.successValue, g, true);
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
                    g.writeString("other");
                    return;
            }
        }

        public FileMemberRemoveActionResult deserialize(JsonParser p) throws IOException, JsonParseException {
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
            FileMemberRemoveActionResult value;
            if ("success".equals(tag)) {
                value = FileMemberRemoveActionResult.success(Serializer.INSTANCE.deserialize(p, true));
            } else if ("member_error".equals(tag)) {
                StoneSerializer.expectField("member_error", p);
                value = FileMemberRemoveActionResult.memberError(Serializer.INSTANCE.deserialize(p));
            } else {
                value = FileMemberRemoveActionResult.OTHER;
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
        MEMBER_ERROR,
        OTHER
    }

    private FileMemberRemoveActionResult(Tag _tag, MemberAccessLevelResult successValue, FileMemberActionError memberErrorValue) {
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

    public static FileMemberRemoveActionResult success(MemberAccessLevelResult value) {
        if (value != null) {
            return new FileMemberRemoveActionResult(Tag.SUCCESS, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public MemberAccessLevelResult getSuccessValue() {
        if (this._tag == Tag.SUCCESS) {
            return this.successValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.SUCCESS, but was Tag." + this._tag.name());
    }

    public boolean isMemberError() {
        return this._tag == Tag.MEMBER_ERROR;
    }

    public static FileMemberRemoveActionResult memberError(FileMemberActionError value) {
        if (value != null) {
            return new FileMemberRemoveActionResult(Tag.MEMBER_ERROR, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public FileMemberActionError getMemberErrorValue() {
        if (this._tag == Tag.MEMBER_ERROR) {
            return this.memberErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.MEMBER_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.successValue, this.memberErrorValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FileMemberRemoveActionResult)) {
            return false;
        }
        FileMemberRemoveActionResult other = (FileMemberRemoveActionResult) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$FileMemberRemoveActionResult$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.successValue == other.successValue || this.successValue.equals(other.successValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.memberErrorValue == other.memberErrorValue || this.memberErrorValue.equals(other.memberErrorValue)) {
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
