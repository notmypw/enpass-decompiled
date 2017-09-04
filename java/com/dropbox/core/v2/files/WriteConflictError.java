package com.dropbox.core.v2.files;

import com.box.androidsdk.content.models.BoxFile;
import com.box.androidsdk.content.models.BoxFolder;
import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.UnionSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import net.sqlcipher.database.SQLiteDatabase;

public enum WriteConflictError {
    FILE,
    FOLDER,
    FILE_ANCESTOR,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$WriteConflictError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$files$WriteConflictError = new int[WriteConflictError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$files$WriteConflictError[WriteConflictError.FILE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$WriteConflictError[WriteConflictError.FOLDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$WriteConflictError[WriteConflictError.FILE_ANCESTOR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<WriteConflictError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(WriteConflictError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$WriteConflictError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString(BoxFile.TYPE);
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString(BoxFolder.TYPE);
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("file_ancestor");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public WriteConflictError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            WriteConflictError value;
            if (BoxFile.TYPE.equals(tag)) {
                value = WriteConflictError.FILE;
            } else if (BoxFolder.TYPE.equals(tag)) {
                value = WriteConflictError.FOLDER;
            } else if ("file_ancestor".equals(tag)) {
                value = WriteConflictError.FILE_ANCESTOR;
            } else {
                value = WriteConflictError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
