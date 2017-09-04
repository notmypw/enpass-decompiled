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

public enum ListPaperDocsFilterBy {
    DOCS_ACCESSED,
    DOCS_CREATED,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$paper$ListPaperDocsFilterBy = null;

        static {
            $SwitchMap$com$dropbox$core$v2$paper$ListPaperDocsFilterBy = new int[ListPaperDocsFilterBy.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$paper$ListPaperDocsFilterBy[ListPaperDocsFilterBy.DOCS_ACCESSED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$ListPaperDocsFilterBy[ListPaperDocsFilterBy.DOCS_CREATED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<ListPaperDocsFilterBy> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(ListPaperDocsFilterBy value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$paper$ListPaperDocsFilterBy[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("docs_accessed");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("docs_created");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public ListPaperDocsFilterBy deserialize(JsonParser p) throws IOException, JsonParseException {
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
            ListPaperDocsFilterBy value;
            if ("docs_accessed".equals(tag)) {
                value = ListPaperDocsFilterBy.DOCS_ACCESSED;
            } else if ("docs_created".equals(tag)) {
                value = ListPaperDocsFilterBy.DOCS_CREATED;
            } else {
                value = ListPaperDocsFilterBy.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
