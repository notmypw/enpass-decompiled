package com.dropbox.core.v2.files;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.UnionSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import in.sinew.enpassengine.Attachment;
import java.io.IOException;
import net.sqlcipher.database.SQLiteDatabase;

public enum SearchMode {
    FILENAME,
    FILENAME_AND_CONTENT,
    DELETED_FILENAME;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$SearchMode = null;

        static {
            $SwitchMap$com$dropbox$core$v2$files$SearchMode = new int[SearchMode.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$files$SearchMode[SearchMode.FILENAME.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$SearchMode[SearchMode.FILENAME_AND_CONTENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$SearchMode[SearchMode.DELETED_FILENAME.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<SearchMode> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(SearchMode value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$SearchMode[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString(Attachment.ATTACHMENT_FILENAME);
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("filename_and_content");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("deleted_filename");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value);
            }
        }

        public SearchMode deserialize(JsonParser p) throws IOException, JsonParseException {
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
            SearchMode value;
            if (Attachment.ATTACHMENT_FILENAME.equals(tag)) {
                value = SearchMode.FILENAME;
            } else if ("filename_and_content".equals(tag)) {
                value = SearchMode.FILENAME_AND_CONTENT;
            } else if ("deleted_filename".equals(tag)) {
                value = SearchMode.DELETED_FILENAME;
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
