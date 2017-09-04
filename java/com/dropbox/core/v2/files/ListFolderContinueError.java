package com.dropbox.core.v2.files;

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

public final class ListFolderContinueError {
    public static final ListFolderContinueError OTHER = new ListFolderContinueError(Tag.OTHER, null);
    public static final ListFolderContinueError RESET = new ListFolderContinueError(Tag.RESET, null);
    private final Tag _tag;
    private final LookupError pathValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$ListFolderContinueError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$ListFolderContinueError$Tag[Tag.PATH.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$ListFolderContinueError$Tag[Tag.RESET.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$ListFolderContinueError$Tag[Tag.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<ListFolderContinueError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListFolderContinueError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$ListFolderContinueError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag(BoxMetadataUpdateTask.PATH, g);
                    g.writeFieldName(BoxMetadataUpdateTask.PATH);
                    com.dropbox.core.v2.files.LookupError.Serializer.INSTANCE.serialize(value.pathValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("reset");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public ListFolderContinueError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            ListFolderContinueError value;
            if (BoxMetadataUpdateTask.PATH.equals(tag)) {
                StoneSerializer.expectField(BoxMetadataUpdateTask.PATH, p);
                value = ListFolderContinueError.path(com.dropbox.core.v2.files.LookupError.Serializer.INSTANCE.deserialize(p));
            } else if ("reset".equals(tag)) {
                value = ListFolderContinueError.RESET;
            } else {
                value = ListFolderContinueError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        PATH,
        RESET,
        OTHER
    }

    private ListFolderContinueError(Tag _tag, LookupError pathValue) {
        this._tag = _tag;
        this.pathValue = pathValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isPath() {
        return this._tag == Tag.PATH;
    }

    public static ListFolderContinueError path(LookupError value) {
        if (value != null) {
            return new ListFolderContinueError(Tag.PATH, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public LookupError getPathValue() {
        if (this._tag == Tag.PATH) {
            return this.pathValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.PATH, but was Tag." + this._tag.name());
    }

    public boolean isReset() {
        return this._tag == Tag.RESET;
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.pathValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ListFolderContinueError)) {
            return false;
        }
        ListFolderContinueError other = (ListFolderContinueError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$ListFolderContinueError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.pathValue == other.pathValue || this.pathValue.equals(other.pathValue)) {
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
