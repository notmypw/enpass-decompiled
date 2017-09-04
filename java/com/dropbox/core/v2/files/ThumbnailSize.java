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
import net.sqlcipher.database.SQLiteDatabase;

public enum ThumbnailSize {
    W32H32,
    W64H64,
    W128H128,
    W640H480,
    W1024H768;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$ThumbnailSize = null;

        static {
            $SwitchMap$com$dropbox$core$v2$files$ThumbnailSize = new int[ThumbnailSize.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$files$ThumbnailSize[ThumbnailSize.W32H32.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$ThumbnailSize[ThumbnailSize.W64H64.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$ThumbnailSize[ThumbnailSize.W128H128.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$ThumbnailSize[ThumbnailSize.W640H480.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$ThumbnailSize[ThumbnailSize.W1024H768.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    static class Serializer extends UnionSerializer<ThumbnailSize> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(ThumbnailSize value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$ThumbnailSize[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("w32h32");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("w64h64");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("w128h128");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("w640h480");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("w1024h768");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value);
            }
        }

        public ThumbnailSize deserialize(JsonParser p) throws IOException, JsonParseException {
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
            ThumbnailSize value;
            if ("w32h32".equals(tag)) {
                value = ThumbnailSize.W32H32;
            } else if ("w64h64".equals(tag)) {
                value = ThumbnailSize.W64H64;
            } else if ("w128h128".equals(tag)) {
                value = ThumbnailSize.W128H128;
            } else if ("w640h480".equals(tag)) {
                value = ThumbnailSize.W640H480;
            } else if ("w1024h768".equals(tag)) {
                value = ThumbnailSize.W1024H768;
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
