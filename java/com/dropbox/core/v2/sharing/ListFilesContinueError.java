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

public final class ListFilesContinueError {
    public static final ListFilesContinueError INVALID_CURSOR = new ListFilesContinueError(Tag.INVALID_CURSOR, null);
    public static final ListFilesContinueError OTHER = new ListFilesContinueError(Tag.OTHER, null);
    private final Tag _tag;
    private final SharingUserError userErrorValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$ListFilesContinueError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ListFilesContinueError$Tag[Tag.USER_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ListFilesContinueError$Tag[Tag.INVALID_CURSOR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ListFilesContinueError$Tag[Tag.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<ListFilesContinueError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListFilesContinueError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$ListFilesContinueError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("user_error", g);
                    g.writeFieldName("user_error");
                    Serializer.INSTANCE.serialize(value.userErrorValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("invalid_cursor");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public ListFilesContinueError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            ListFilesContinueError value;
            if ("user_error".equals(tag)) {
                StoneSerializer.expectField("user_error", p);
                value = ListFilesContinueError.userError(Serializer.INSTANCE.deserialize(p));
            } else if ("invalid_cursor".equals(tag)) {
                value = ListFilesContinueError.INVALID_CURSOR;
            } else {
                value = ListFilesContinueError.OTHER;
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
        INVALID_CURSOR,
        OTHER
    }

    private ListFilesContinueError(Tag _tag, SharingUserError userErrorValue) {
        this._tag = _tag;
        this.userErrorValue = userErrorValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isUserError() {
        return this._tag == Tag.USER_ERROR;
    }

    public static ListFilesContinueError userError(SharingUserError value) {
        if (value != null) {
            return new ListFilesContinueError(Tag.USER_ERROR, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public SharingUserError getUserErrorValue() {
        if (this._tag == Tag.USER_ERROR) {
            return this.userErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.USER_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isInvalidCursor() {
        return this._tag == Tag.INVALID_CURSOR;
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.userErrorValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ListFilesContinueError)) {
            return false;
        }
        ListFilesContinueError other = (ListFilesContinueError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$ListFilesContinueError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.userErrorValue == other.userErrorValue || this.userErrorValue.equals(other.userErrorValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                return true;
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
