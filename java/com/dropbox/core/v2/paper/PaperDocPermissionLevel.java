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

public enum PaperDocPermissionLevel {
    EDIT,
    VIEW_AND_COMMENT,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$paper$PaperDocPermissionLevel = null;

        static {
            $SwitchMap$com$dropbox$core$v2$paper$PaperDocPermissionLevel = new int[PaperDocPermissionLevel.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$paper$PaperDocPermissionLevel[PaperDocPermissionLevel.EDIT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$PaperDocPermissionLevel[PaperDocPermissionLevel.VIEW_AND_COMMENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<PaperDocPermissionLevel> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(PaperDocPermissionLevel value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$paper$PaperDocPermissionLevel[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("edit");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("view_and_comment");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public PaperDocPermissionLevel deserialize(JsonParser p) throws IOException, JsonParseException {
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
            PaperDocPermissionLevel value;
            if ("edit".equals(tag)) {
                value = PaperDocPermissionLevel.EDIT;
            } else if ("view_and_comment".equals(tag)) {
                value = PaperDocPermissionLevel.VIEW_AND_COMMENT;
            } else {
                value = PaperDocPermissionLevel.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
