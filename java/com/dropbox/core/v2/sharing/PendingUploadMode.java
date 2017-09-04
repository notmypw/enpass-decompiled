package com.dropbox.core.v2.sharing;

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

public enum PendingUploadMode {
    FILE,
    FOLDER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$PendingUploadMode = null;

        static {
            $SwitchMap$com$dropbox$core$v2$sharing$PendingUploadMode = new int[PendingUploadMode.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$PendingUploadMode[PendingUploadMode.FILE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$PendingUploadMode[PendingUploadMode.FOLDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<PendingUploadMode> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(PendingUploadMode value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$PendingUploadMode[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString(BoxFile.TYPE);
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString(BoxFolder.TYPE);
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value);
            }
        }

        public PendingUploadMode deserialize(JsonParser p) throws IOException, JsonParseException {
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
            PendingUploadMode value;
            if (BoxFile.TYPE.equals(tag)) {
                value = PendingUploadMode.FILE;
            } else if (BoxFolder.TYPE.equals(tag)) {
                value = PendingUploadMode.FOLDER;
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
}
