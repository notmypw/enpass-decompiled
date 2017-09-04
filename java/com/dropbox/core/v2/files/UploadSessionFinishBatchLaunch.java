package com.dropbox.core.v2.files;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.UnionSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import net.sqlcipher.database.SQLiteDatabase;

public final class UploadSessionFinishBatchLaunch {
    public static final UploadSessionFinishBatchLaunch OTHER = new UploadSessionFinishBatchLaunch(Tag.OTHER, null, null);
    private final Tag _tag;
    private final String asyncJobIdValue;
    private final UploadSessionFinishBatchResult completeValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$UploadSessionFinishBatchLaunch$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$UploadSessionFinishBatchLaunch$Tag[Tag.ASYNC_JOB_ID.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$UploadSessionFinishBatchLaunch$Tag[Tag.COMPLETE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$UploadSessionFinishBatchLaunch$Tag[Tag.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<UploadSessionFinishBatchLaunch> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UploadSessionFinishBatchLaunch value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$UploadSessionFinishBatchLaunch$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("async_job_id", g);
                    g.writeFieldName("async_job_id");
                    StoneSerializers.string().serialize(value.asyncJobIdValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("complete", g);
                    Serializer.INSTANCE.serialize(value.completeValue, g, true);
                    g.writeEndObject();
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public UploadSessionFinishBatchLaunch deserialize(JsonParser p) throws IOException, JsonParseException {
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
            UploadSessionFinishBatchLaunch value;
            if ("async_job_id".equals(tag)) {
                StoneSerializer.expectField("async_job_id", p);
                value = UploadSessionFinishBatchLaunch.asyncJobId((String) StoneSerializers.string().deserialize(p));
            } else if ("complete".equals(tag)) {
                value = UploadSessionFinishBatchLaunch.complete(Serializer.INSTANCE.deserialize(p, true));
            } else {
                value = UploadSessionFinishBatchLaunch.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        ASYNC_JOB_ID,
        COMPLETE,
        OTHER
    }

    private UploadSessionFinishBatchLaunch(Tag _tag, String asyncJobIdValue, UploadSessionFinishBatchResult completeValue) {
        this._tag = _tag;
        this.asyncJobIdValue = asyncJobIdValue;
        this.completeValue = completeValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isAsyncJobId() {
        return this._tag == Tag.ASYNC_JOB_ID;
    }

    public static UploadSessionFinishBatchLaunch asyncJobId(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() >= 1) {
            return new UploadSessionFinishBatchLaunch(Tag.ASYNC_JOB_ID, value, null);
        } else {
            throw new IllegalArgumentException("String is shorter than 1");
        }
    }

    public String getAsyncJobIdValue() {
        if (this._tag == Tag.ASYNC_JOB_ID) {
            return this.asyncJobIdValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.ASYNC_JOB_ID, but was Tag." + this._tag.name());
    }

    public boolean isComplete() {
        return this._tag == Tag.COMPLETE;
    }

    public static UploadSessionFinishBatchLaunch complete(UploadSessionFinishBatchResult value) {
        if (value != null) {
            return new UploadSessionFinishBatchLaunch(Tag.COMPLETE, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public UploadSessionFinishBatchResult getCompleteValue() {
        if (this._tag == Tag.COMPLETE) {
            return this.completeValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.COMPLETE, but was Tag." + this._tag.name());
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.asyncJobIdValue, this.completeValue}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UploadSessionFinishBatchLaunch)) {
            return false;
        }
        UploadSessionFinishBatchLaunch other = (UploadSessionFinishBatchLaunch) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$UploadSessionFinishBatchLaunch$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.asyncJobIdValue == other.asyncJobIdValue || this.asyncJobIdValue.equals(other.asyncJobIdValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.completeValue == other.completeValue || this.completeValue.equals(other.completeValue)) {
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
