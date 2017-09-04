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

public final class UploadSessionFinishError {
    public static final UploadSessionFinishError OTHER = new UploadSessionFinishError(Tag.OTHER, null, null);
    public static final UploadSessionFinishError TOO_MANY_SHARED_FOLDER_TARGETS = new UploadSessionFinishError(Tag.TOO_MANY_SHARED_FOLDER_TARGETS, null, null);
    private final Tag _tag;
    private final UploadSessionLookupError lookupFailedValue;
    private final WriteError pathValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$UploadSessionFinishError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$UploadSessionFinishError$Tag[Tag.LOOKUP_FAILED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$UploadSessionFinishError$Tag[Tag.PATH.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$UploadSessionFinishError$Tag[Tag.TOO_MANY_SHARED_FOLDER_TARGETS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$UploadSessionFinishError$Tag[Tag.OTHER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static class Serializer extends UnionSerializer<UploadSessionFinishError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UploadSessionFinishError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$UploadSessionFinishError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("lookup_failed", g);
                    g.writeFieldName("lookup_failed");
                    Serializer.INSTANCE.serialize(value.lookupFailedValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag(BoxMetadataUpdateTask.PATH, g);
                    g.writeFieldName(BoxMetadataUpdateTask.PATH);
                    Serializer.INSTANCE.serialize(value.pathValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("too_many_shared_folder_targets");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public UploadSessionFinishError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            UploadSessionFinishError value;
            if ("lookup_failed".equals(tag)) {
                StoneSerializer.expectField("lookup_failed", p);
                value = UploadSessionFinishError.lookupFailed(Serializer.INSTANCE.deserialize(p));
            } else if (BoxMetadataUpdateTask.PATH.equals(tag)) {
                StoneSerializer.expectField(BoxMetadataUpdateTask.PATH, p);
                value = UploadSessionFinishError.path(Serializer.INSTANCE.deserialize(p));
            } else if ("too_many_shared_folder_targets".equals(tag)) {
                value = UploadSessionFinishError.TOO_MANY_SHARED_FOLDER_TARGETS;
            } else {
                value = UploadSessionFinishError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        LOOKUP_FAILED,
        PATH,
        TOO_MANY_SHARED_FOLDER_TARGETS,
        OTHER
    }

    private UploadSessionFinishError(Tag _tag, UploadSessionLookupError lookupFailedValue, WriteError pathValue) {
        this._tag = _tag;
        this.lookupFailedValue = lookupFailedValue;
        this.pathValue = pathValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isLookupFailed() {
        return this._tag == Tag.LOOKUP_FAILED;
    }

    public static UploadSessionFinishError lookupFailed(UploadSessionLookupError value) {
        if (value != null) {
            return new UploadSessionFinishError(Tag.LOOKUP_FAILED, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public UploadSessionLookupError getLookupFailedValue() {
        if (this._tag == Tag.LOOKUP_FAILED) {
            return this.lookupFailedValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.LOOKUP_FAILED, but was Tag." + this._tag.name());
    }

    public boolean isPath() {
        return this._tag == Tag.PATH;
    }

    public static UploadSessionFinishError path(WriteError value) {
        if (value != null) {
            return new UploadSessionFinishError(Tag.PATH, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public WriteError getPathValue() {
        if (this._tag == Tag.PATH) {
            return this.pathValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.PATH, but was Tag." + this._tag.name());
    }

    public boolean isTooManySharedFolderTargets() {
        return this._tag == Tag.TOO_MANY_SHARED_FOLDER_TARGETS;
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.lookupFailedValue, this.pathValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UploadSessionFinishError)) {
            return false;
        }
        UploadSessionFinishError other = (UploadSessionFinishError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$UploadSessionFinishError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.lookupFailedValue == other.lookupFailedValue || this.lookupFailedValue.equals(other.lookupFailedValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.pathValue == other.pathValue || this.pathValue.equals(other.pathValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                return true;
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
