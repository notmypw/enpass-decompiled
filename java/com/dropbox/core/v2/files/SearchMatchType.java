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

public enum SearchMatchType {
    FILENAME,
    CONTENT,
    BOTH;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$SearchMatchType = null;

        static {
            $SwitchMap$com$dropbox$core$v2$files$SearchMatchType = new int[SearchMatchType.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$files$SearchMatchType[SearchMatchType.FILENAME.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$SearchMatchType[SearchMatchType.CONTENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$SearchMatchType[SearchMatchType.BOTH.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<SearchMatchType> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(SearchMatchType value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$SearchMatchType[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString(Attachment.ATTACHMENT_FILENAME);
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("content");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("both");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value);
            }
        }

        public SearchMatchType deserialize(JsonParser p) throws IOException, JsonParseException {
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
            SearchMatchType value;
            if (Attachment.ATTACHMENT_FILENAME.equals(tag)) {
                value = SearchMatchType.FILENAME;
            } else if ("content".equals(tag)) {
                value = SearchMatchType.CONTENT;
            } else if ("both".equals(tag)) {
                value = SearchMatchType.BOTH;
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
