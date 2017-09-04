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

public enum ListPaperDocsSortOrder {
    ASCENDING,
    DESCENDING,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$paper$ListPaperDocsSortOrder = null;

        static {
            $SwitchMap$com$dropbox$core$v2$paper$ListPaperDocsSortOrder = new int[ListPaperDocsSortOrder.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$paper$ListPaperDocsSortOrder[ListPaperDocsSortOrder.ASCENDING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$ListPaperDocsSortOrder[ListPaperDocsSortOrder.DESCENDING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<ListPaperDocsSortOrder> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(ListPaperDocsSortOrder value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$paper$ListPaperDocsSortOrder[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("ascending");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("descending");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public ListPaperDocsSortOrder deserialize(JsonParser p) throws IOException, JsonParseException {
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
            ListPaperDocsSortOrder value;
            if ("ascending".equals(tag)) {
                value = ListPaperDocsSortOrder.ASCENDING;
            } else if ("descending".equals(tag)) {
                value = ListPaperDocsSortOrder.DESCENDING;
            } else {
                value = ListPaperDocsSortOrder.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
