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

public final class DeleteBatchJobStatus {
    public static final DeleteBatchJobStatus IN_PROGRESS = new DeleteBatchJobStatus(Tag.IN_PROGRESS, null, null);
    public static final DeleteBatchJobStatus OTHER = new DeleteBatchJobStatus(Tag.OTHER, null, null);
    private final Tag _tag;
    private final DeleteBatchResult completeValue;
    private final DeleteBatchError failedValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$DeleteBatchJobStatus$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$DeleteBatchJobStatus$Tag[Tag.IN_PROGRESS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$DeleteBatchJobStatus$Tag[Tag.COMPLETE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$DeleteBatchJobStatus$Tag[Tag.FAILED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$DeleteBatchJobStatus$Tag[Tag.OTHER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static class Serializer extends UnionSerializer<DeleteBatchJobStatus> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(DeleteBatchJobStatus value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$DeleteBatchJobStatus$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("in_progress");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("complete", g);
                    Serializer.INSTANCE.serialize(value.completeValue, g, true);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeStartObject();
                    writeTag("failed", g);
                    g.writeFieldName("failed");
                    Serializer.INSTANCE.serialize(value.failedValue, g);
                    g.writeEndObject();
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public DeleteBatchJobStatus deserialize(JsonParser p) throws IOException, JsonParseException {
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
            DeleteBatchJobStatus value;
            if ("in_progress".equals(tag)) {
                value = DeleteBatchJobStatus.IN_PROGRESS;
            } else if ("complete".equals(tag)) {
                value = DeleteBatchJobStatus.complete(Serializer.INSTANCE.deserialize(p, true));
            } else if ("failed".equals(tag)) {
                StoneSerializer.expectField("failed", p);
                value = DeleteBatchJobStatus.failed(Serializer.INSTANCE.deserialize(p));
            } else {
                value = DeleteBatchJobStatus.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        IN_PROGRESS,
        COMPLETE,
        FAILED,
        OTHER
    }

    private DeleteBatchJobStatus(Tag _tag, DeleteBatchResult completeValue, DeleteBatchError failedValue) {
        this._tag = _tag;
        this.completeValue = completeValue;
        this.failedValue = failedValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isInProgress() {
        return this._tag == Tag.IN_PROGRESS;
    }

    public boolean isComplete() {
        return this._tag == Tag.COMPLETE;
    }

    public static DeleteBatchJobStatus complete(DeleteBatchResult value) {
        if (value != null) {
            return new DeleteBatchJobStatus(Tag.COMPLETE, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public DeleteBatchResult getCompleteValue() {
        if (this._tag == Tag.COMPLETE) {
            return this.completeValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.COMPLETE, but was Tag." + this._tag.name());
    }

    public boolean isFailed() {
        return this._tag == Tag.FAILED;
    }

    public static DeleteBatchJobStatus failed(DeleteBatchError value) {
        if (value != null) {
            return new DeleteBatchJobStatus(Tag.FAILED, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public DeleteBatchError getFailedValue() {
        if (this._tag == Tag.FAILED) {
            return this.failedValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.FAILED, but was Tag." + this._tag.name());
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.completeValue, this.failedValue}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DeleteBatchJobStatus)) {
            return false;
        }
        DeleteBatchJobStatus other = (DeleteBatchJobStatus) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$DeleteBatchJobStatus$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                return true;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.completeValue == other.completeValue || this.completeValue.equals(other.completeValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                if (this.failedValue == other.failedValue || this.failedValue.equals(other.failedValue)) {
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
