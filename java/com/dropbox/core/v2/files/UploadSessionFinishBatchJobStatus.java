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

public final class UploadSessionFinishBatchJobStatus {
    public static final UploadSessionFinishBatchJobStatus IN_PROGRESS = new UploadSessionFinishBatchJobStatus(Tag.IN_PROGRESS, null);
    private final Tag _tag;
    private final UploadSessionFinishBatchResult completeValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$UploadSessionFinishBatchJobStatus$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$UploadSessionFinishBatchJobStatus$Tag[Tag.IN_PROGRESS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$UploadSessionFinishBatchJobStatus$Tag[Tag.COMPLETE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<UploadSessionFinishBatchJobStatus> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UploadSessionFinishBatchJobStatus value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$UploadSessionFinishBatchJobStatus$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("in_progress");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("complete", g);
                    Serializer.INSTANCE.serialize(value.completeValue, g, true);
                    g.writeEndObject();
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public UploadSessionFinishBatchJobStatus deserialize(JsonParser p) throws IOException, JsonParseException {
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
            UploadSessionFinishBatchJobStatus value;
            if ("in_progress".equals(tag)) {
                value = UploadSessionFinishBatchJobStatus.IN_PROGRESS;
            } else if ("complete".equals(tag)) {
                value = UploadSessionFinishBatchJobStatus.complete(Serializer.INSTANCE.deserialize(p, true));
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
        IN_PROGRESS,
        COMPLETE
    }

    private UploadSessionFinishBatchJobStatus(Tag _tag, UploadSessionFinishBatchResult completeValue) {
        this._tag = _tag;
        this.completeValue = completeValue;
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

    public static UploadSessionFinishBatchJobStatus complete(UploadSessionFinishBatchResult value) {
        if (value != null) {
            return new UploadSessionFinishBatchJobStatus(Tag.COMPLETE, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public UploadSessionFinishBatchResult getCompleteValue() {
        if (this._tag == Tag.COMPLETE) {
            return this.completeValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.COMPLETE, but was Tag." + this._tag.name());
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.completeValue}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UploadSessionFinishBatchJobStatus)) {
            return false;
        }
        UploadSessionFinishBatchJobStatus other = (UploadSessionFinishBatchJobStatus) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$UploadSessionFinishBatchJobStatus$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                return true;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.completeValue == other.completeValue || this.completeValue.equals(other.completeValue)) {
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
