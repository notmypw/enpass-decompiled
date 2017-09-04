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
import in.sinew.enpass.IRemoteStorage;
import java.io.IOException;
import java.util.Arrays;
import net.sqlcipher.database.SQLiteDatabase;

public final class LookupError {
    public static final LookupError NOT_FILE = new LookupError(Tag.NOT_FILE, null, null);
    public static final LookupError NOT_FOLDER = new LookupError(Tag.NOT_FOLDER, null, null);
    public static final LookupError NOT_FOUND = new LookupError(Tag.NOT_FOUND, null, null);
    public static final LookupError OTHER = new LookupError(Tag.OTHER, null, null);
    public static final LookupError RESTRICTED_CONTENT = new LookupError(Tag.RESTRICTED_CONTENT, null, null);
    private final Tag _tag;
    private final PathRootError invalidPathRootValue;
    private final String malformedPathValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$LookupError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$LookupError$Tag[Tag.MALFORMED_PATH.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$LookupError$Tag[Tag.NOT_FOUND.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$LookupError$Tag[Tag.NOT_FILE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$LookupError$Tag[Tag.NOT_FOLDER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$LookupError$Tag[Tag.RESTRICTED_CONTENT.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$LookupError$Tag[Tag.INVALID_PATH_ROOT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$LookupError$Tag[Tag.OTHER.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public static class Serializer extends UnionSerializer<LookupError> {
        public static final Serializer INSTANCE = new Serializer();

        public void serialize(LookupError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$LookupError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("malformed_path", g);
                    g.writeFieldName("malformed_path");
                    StoneSerializers.nullable(StoneSerializers.string()).serialize(value.malformedPathValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("not_found");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("not_file");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("not_folder");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("restricted_content");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeStartObject();
                    writeTag("invalid_path_root", g);
                    com.dropbox.core.v2.files.PathRootError.Serializer.INSTANCE.serialize(value.invalidPathRootValue, g, true);
                    g.writeEndObject();
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public LookupError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            LookupError value;
            if ("malformed_path".equals(tag)) {
                String fieldValue = null;
                if (p.getCurrentToken() != JsonToken.END_OBJECT) {
                    StoneSerializer.expectField("malformed_path", p);
                    fieldValue = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                }
                if (fieldValue == null) {
                    value = LookupError.malformedPath();
                } else {
                    value = LookupError.malformedPath(fieldValue);
                }
            } else if ("not_found".equals(tag)) {
                value = LookupError.NOT_FOUND;
            } else if ("not_file".equals(tag)) {
                value = LookupError.NOT_FILE;
            } else if ("not_folder".equals(tag)) {
                value = LookupError.NOT_FOLDER;
            } else if ("restricted_content".equals(tag)) {
                value = LookupError.RESTRICTED_CONTENT;
            } else if ("invalid_path_root".equals(tag)) {
                value = LookupError.invalidPathRoot(com.dropbox.core.v2.files.PathRootError.Serializer.INSTANCE.deserialize(p, true));
            } else {
                value = LookupError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        MALFORMED_PATH,
        NOT_FOUND,
        NOT_FILE,
        NOT_FOLDER,
        RESTRICTED_CONTENT,
        INVALID_PATH_ROOT,
        OTHER
    }

    private LookupError(Tag _tag, String malformedPathValue, PathRootError invalidPathRootValue) {
        this._tag = _tag;
        this.malformedPathValue = malformedPathValue;
        this.invalidPathRootValue = invalidPathRootValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isMalformedPath() {
        return this._tag == Tag.MALFORMED_PATH;
    }

    public static LookupError malformedPath(String value) {
        return new LookupError(Tag.MALFORMED_PATH, value, null);
    }

    public static LookupError malformedPath() {
        return malformedPath(null);
    }

    public String getMalformedPathValue() {
        if (this._tag == Tag.MALFORMED_PATH) {
            return this.malformedPathValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.MALFORMED_PATH, but was Tag." + this._tag.name());
    }

    public boolean isNotFound() {
        return this._tag == Tag.NOT_FOUND;
    }

    public boolean isNotFile() {
        return this._tag == Tag.NOT_FILE;
    }

    public boolean isNotFolder() {
        return this._tag == Tag.NOT_FOLDER;
    }

    public boolean isRestrictedContent() {
        return this._tag == Tag.RESTRICTED_CONTENT;
    }

    public boolean isInvalidPathRoot() {
        return this._tag == Tag.INVALID_PATH_ROOT;
    }

    public static LookupError invalidPathRoot(PathRootError value) {
        if (value != null) {
            return new LookupError(Tag.INVALID_PATH_ROOT, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public PathRootError getInvalidPathRootValue() {
        if (this._tag == Tag.INVALID_PATH_ROOT) {
            return this.invalidPathRootValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.INVALID_PATH_ROOT, but was Tag." + this._tag.name());
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.malformedPathValue, this.invalidPathRootValue});
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LookupError)) {
            return false;
        }
        LookupError other = (LookupError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$LookupError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.malformedPathValue == other.malformedPathValue || (this.malformedPathValue != null && this.malformedPathValue.equals(other.malformedPathValue))) {
                    z = true;
                }
                return z;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
            case IRemoteStorage.PIN /*7*/:
                return true;
            case IRemoteStorage.BOX_REMOTE /*6*/:
                if (this.invalidPathRootValue == other.invalidPathRootValue || this.invalidPathRootValue.equals(other.invalidPathRootValue)) {
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
