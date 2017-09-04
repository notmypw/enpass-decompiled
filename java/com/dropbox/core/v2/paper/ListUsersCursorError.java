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

public final class ListUsersCursorError {
    public static final ListUsersCursorError DOC_NOT_FOUND = new ListUsersCursorError(Tag.DOC_NOT_FOUND, null);
    public static final ListUsersCursorError INSUFFICIENT_PERMISSIONS = new ListUsersCursorError(Tag.INSUFFICIENT_PERMISSIONS, null);
    public static final ListUsersCursorError OTHER = new ListUsersCursorError(Tag.OTHER, null);
    private final Tag _tag;
    private final PaperApiCursorError cursorErrorValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$paper$ListUsersCursorError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$paper$ListUsersCursorError$Tag[Tag.INSUFFICIENT_PERMISSIONS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$ListUsersCursorError$Tag[Tag.OTHER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$ListUsersCursorError$Tag[Tag.DOC_NOT_FOUND.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$ListUsersCursorError$Tag[Tag.CURSOR_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static class Serializer extends UnionSerializer<ListUsersCursorError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListUsersCursorError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$paper$ListUsersCursorError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("insufficient_permissions");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("other");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("doc_not_found");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeStartObject();
                    writeTag("cursor_error", g);
                    g.writeFieldName("cursor_error");
                    Serializer.INSTANCE.serialize(value.cursorErrorValue, g);
                    g.writeEndObject();
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public ListUsersCursorError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            ListUsersCursorError value;
            if ("insufficient_permissions".equals(tag)) {
                value = ListUsersCursorError.INSUFFICIENT_PERMISSIONS;
            } else if ("other".equals(tag)) {
                value = ListUsersCursorError.OTHER;
            } else if ("doc_not_found".equals(tag)) {
                value = ListUsersCursorError.DOC_NOT_FOUND;
            } else if ("cursor_error".equals(tag)) {
                StoneSerializer.expectField("cursor_error", p);
                value = ListUsersCursorError.cursorError(Serializer.INSTANCE.deserialize(p));
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
        INSUFFICIENT_PERMISSIONS,
        OTHER,
        DOC_NOT_FOUND,
        CURSOR_ERROR
    }

    private ListUsersCursorError(Tag _tag, PaperApiCursorError cursorErrorValue) {
        this._tag = _tag;
        this.cursorErrorValue = cursorErrorValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isInsufficientPermissions() {
        return this._tag == Tag.INSUFFICIENT_PERMISSIONS;
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public boolean isDocNotFound() {
        return this._tag == Tag.DOC_NOT_FOUND;
    }

    public boolean isCursorError() {
        return this._tag == Tag.CURSOR_ERROR;
    }

    public static ListUsersCursorError cursorError(PaperApiCursorError value) {
        if (value != null) {
            return new ListUsersCursorError(Tag.CURSOR_ERROR, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public PaperApiCursorError getCursorErrorValue() {
        if (this._tag == Tag.CURSOR_ERROR) {
            return this.cursorErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.CURSOR_ERROR, but was Tag." + this._tag.name());
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.cursorErrorValue}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ListUsersCursorError)) {
            return false;
        }
        ListUsersCursorError other = (ListUsersCursorError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$paper$ListUsersCursorError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                return true;
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                if (this.cursorErrorValue == other.cursorErrorValue || this.cursorErrorValue.equals(other.cursorErrorValue)) {
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
