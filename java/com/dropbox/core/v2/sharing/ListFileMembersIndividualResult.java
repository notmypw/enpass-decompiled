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

public final class ListFileMembersIndividualResult {
    public static final ListFileMembersIndividualResult OTHER = new ListFileMembersIndividualResult(Tag.OTHER, null, null);
    private final Tag _tag;
    private final SharingFileAccessError accessErrorValue;
    private final ListFileMembersCountResult resultValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$ListFileMembersIndividualResult$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ListFileMembersIndividualResult$Tag[Tag.RESULT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ListFileMembersIndividualResult$Tag[Tag.ACCESS_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ListFileMembersIndividualResult$Tag[Tag.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<ListFileMembersIndividualResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListFileMembersIndividualResult value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$ListFileMembersIndividualResult$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("result", g);
                    Serializer.INSTANCE.serialize(value.resultValue, g, true);
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

        public ListFileMembersIndividualResult deserialize(JsonParser p) throws IOException, JsonParseException {
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
            ListFileMembersIndividualResult value;
            if ("result".equals(tag)) {
                value = ListFileMembersIndividualResult.result(Serializer.INSTANCE.deserialize(p, true));
            } else if ("access_error".equals(tag)) {
                StoneSerializer.expectField("access_error", p);
                value = ListFileMembersIndividualResult.accessError(Serializer.INSTANCE.deserialize(p));
            } else {
                value = ListFileMembersIndividualResult.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        RESULT,
        ACCESS_ERROR,
        OTHER
    }

    private ListFileMembersIndividualResult(Tag _tag, ListFileMembersCountResult resultValue, SharingFileAccessError accessErrorValue) {
        this._tag = _tag;
        this.resultValue = resultValue;
        this.accessErrorValue = accessErrorValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isResult() {
        return this._tag == Tag.RESULT;
    }

    public static ListFileMembersIndividualResult result(ListFileMembersCountResult value) {
        if (value != null) {
            return new ListFileMembersIndividualResult(Tag.RESULT, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public ListFileMembersCountResult getResultValue() {
        if (this._tag == Tag.RESULT) {
            return this.resultValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.RESULT, but was Tag." + this._tag.name());
    }

    public boolean isAccessError() {
        return this._tag == Tag.ACCESS_ERROR;
    }

    public static ListFileMembersIndividualResult accessError(SharingFileAccessError value) {
        if (value != null) {
            return new ListFileMembersIndividualResult(Tag.ACCESS_ERROR, null, value);
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
        return Arrays.hashCode(new Object[]{this._tag, this.resultValue, this.accessErrorValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ListFileMembersIndividualResult)) {
            return false;
        }
        ListFileMembersIndividualResult other = (ListFileMembersIndividualResult) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$ListFileMembersIndividualResult$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.resultValue == other.resultValue || this.resultValue.equals(other.resultValue)) {
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
