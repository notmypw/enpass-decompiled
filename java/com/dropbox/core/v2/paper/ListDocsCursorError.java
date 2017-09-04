package com.dropbox.core.v2.paper;

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

public final class ListDocsCursorError {
    public static final ListDocsCursorError OTHER = new ListDocsCursorError(Tag.OTHER, null);
    private final Tag _tag;
    private final PaperApiCursorError cursorErrorValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$paper$ListDocsCursorError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$paper$ListDocsCursorError$Tag[Tag.CURSOR_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$ListDocsCursorError$Tag[Tag.OTHER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<ListDocsCursorError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListDocsCursorError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$paper$ListDocsCursorError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("cursor_error", g);
                    g.writeFieldName("cursor_error");
                    Serializer.INSTANCE.serialize(value.cursorErrorValue, g);
                    g.writeEndObject();
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public ListDocsCursorError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            ListDocsCursorError value;
            if ("cursor_error".equals(tag)) {
                StoneSerializer.expectField("cursor_error", p);
                value = ListDocsCursorError.cursorError(Serializer.INSTANCE.deserialize(p));
            } else {
                value = ListDocsCursorError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        CURSOR_ERROR,
        OTHER
    }

    private ListDocsCursorError(Tag _tag, PaperApiCursorError cursorErrorValue) {
        this._tag = _tag;
        this.cursorErrorValue = cursorErrorValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isCursorError() {
        return this._tag == Tag.CURSOR_ERROR;
    }

    public static ListDocsCursorError cursorError(PaperApiCursorError value) {
        if (value != null) {
            return new ListDocsCursorError(Tag.CURSOR_ERROR, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public PaperApiCursorError getCursorErrorValue() {
        if (this._tag == Tag.CURSOR_ERROR) {
            return this.cursorErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.CURSOR_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.cursorErrorValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ListDocsCursorError)) {
            return false;
        }
        ListDocsCursorError other = (ListDocsCursorError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$paper$ListDocsCursorError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.cursorErrorValue == other.cursorErrorValue || this.cursorErrorValue.equals(other.cursorErrorValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
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
