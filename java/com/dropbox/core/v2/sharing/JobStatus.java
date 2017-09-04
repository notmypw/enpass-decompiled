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

public final class JobStatus {
    public static final JobStatus COMPLETE = new JobStatus(Tag.COMPLETE, null);
    public static final JobStatus IN_PROGRESS = new JobStatus(Tag.IN_PROGRESS, null);
    private final Tag _tag;
    private final JobError failedValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$JobStatus$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$JobStatus$Tag[Tag.IN_PROGRESS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$JobStatus$Tag[Tag.COMPLETE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$JobStatus$Tag[Tag.FAILED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<JobStatus> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(JobStatus value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$JobStatus$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("in_progress");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("complete");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeStartObject();
                    writeTag("failed", g);
                    g.writeFieldName("failed");
                    Serializer.INSTANCE.serialize(value.failedValue, g);
                    g.writeEndObject();
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public JobStatus deserialize(JsonParser p) throws IOException, JsonParseException {
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
            JobStatus value;
            if ("in_progress".equals(tag)) {
                value = JobStatus.IN_PROGRESS;
            } else if ("complete".equals(tag)) {
                value = JobStatus.COMPLETE;
            } else if ("failed".equals(tag)) {
                StoneSerializer.expectField("failed", p);
                value = JobStatus.failed(Serializer.INSTANCE.deserialize(p));
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
        COMPLETE,
        FAILED
    }

    private JobStatus(Tag _tag, JobError failedValue) {
        this._tag = _tag;
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

    public boolean isFailed() {
        return this._tag == Tag.FAILED;
    }

    public static JobStatus failed(JobError value) {
        if (value != null) {
            return new JobStatus(Tag.FAILED, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public JobError getFailedValue() {
        if (this._tag == Tag.FAILED) {
            return this.failedValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.FAILED, but was Tag." + this._tag.name());
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.failedValue}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof JobStatus)) {
            return false;
        }
        JobStatus other = (JobStatus) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$JobStatus$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                return true;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                return true;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                if (this.failedValue == other.failedValue || this.failedValue.equals(other.failedValue)) {
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
