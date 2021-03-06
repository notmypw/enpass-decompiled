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

public final class DeleteBatchResultEntry {
    private final Tag _tag;
    private final DeleteError failureValue;
    private final DeleteResult successValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$DeleteBatchResultEntry$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$DeleteBatchResultEntry$Tag[Tag.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$DeleteBatchResultEntry$Tag[Tag.FAILURE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<DeleteBatchResultEntry> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(DeleteBatchResultEntry value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$DeleteBatchResultEntry$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("success", g);
                    Serializer.INSTANCE.serialize(value.successValue, g, true);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("failure", g);
                    g.writeFieldName("failure");
                    Serializer.INSTANCE.serialize(value.failureValue, g);
                    g.writeEndObject();
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public DeleteBatchResultEntry deserialize(JsonParser p) throws IOException, JsonParseException {
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
            DeleteBatchResultEntry value;
            if ("success".equals(tag)) {
                value = DeleteBatchResultEntry.success(Serializer.INSTANCE.deserialize(p, true));
            } else if ("failure".equals(tag)) {
                StoneSerializer.expectField("failure", p);
                value = DeleteBatchResultEntry.failure(Serializer.INSTANCE.deserialize(p));
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
        FAILURE
    }

    private DeleteBatchResultEntry(Tag _tag, DeleteResult successValue, DeleteError failureValue) {
        this._tag = _tag;
        this.successValue = successValue;
        this.failureValue = failureValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isSuccess() {
        return this._tag == Tag.SUCCESS;
    }

    public static DeleteBatchResultEntry success(DeleteResult value) {
        if (value != null) {
            return new DeleteBatchResultEntry(Tag.SUCCESS, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public DeleteResult getSuccessValue() {
        if (this._tag == Tag.SUCCESS) {
            return this.successValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.SUCCESS, but was Tag." + this._tag.name());
    }

    public boolean isFailure() {
        return this._tag == Tag.FAILURE;
    }

    public static DeleteBatchResultEntry failure(DeleteError value) {
        if (value != null) {
            return new DeleteBatchResultEntry(Tag.FAILURE, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public DeleteError getFailureValue() {
        if (this._tag == Tag.FAILURE) {
            return this.failureValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.FAILURE, but was Tag." + this._tag.name());
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.successValue, this.failureValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DeleteBatchResultEntry)) {
            return false;
        }
        DeleteBatchResultEntry other = (DeleteBatchResultEntry) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$DeleteBatchResultEntry$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.successValue == other.successValue || this.successValue.equals(other.successValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.failureValue == other.failureValue || this.failureValue.equals(other.failureValue)) {
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
