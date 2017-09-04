package com.dropbox.core.v2.paper;

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

public enum ListPaperDocsSortBy {
    ACCESSED,
    MODIFIED,
    CREATED,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$paper$ListPaperDocsSortBy = null;

        static {
            $SwitchMap$com$dropbox$core$v2$paper$ListPaperDocsSortBy = new int[ListPaperDocsSortBy.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$paper$ListPaperDocsSortBy[ListPaperDocsSortBy.ACCESSED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$ListPaperDocsSortBy[ListPaperDocsSortBy.MODIFIED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$ListPaperDocsSortBy[ListPaperDocsSortBy.CREATED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<ListPaperDocsSortBy> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(ListPaperDocsSortBy value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$paper$ListPaperDocsSortBy[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("accessed");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("modified");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("created");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public ListPaperDocsSortBy deserialize(JsonParser p) throws IOException, JsonParseException {
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
            ListPaperDocsSortBy value;
            if ("accessed".equals(tag)) {
                value = ListPaperDocsSortBy.ACCESSED;
            } else if ("modified".equals(tag)) {
                value = ListPaperDocsSortBy.MODIFIED;
            } else if ("created".equals(tag)) {
                value = ListPaperDocsSortBy.CREATED;
            } else {
                value = ListPaperDocsSortBy.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
