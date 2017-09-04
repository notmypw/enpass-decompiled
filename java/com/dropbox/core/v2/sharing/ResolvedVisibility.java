package com.dropbox.core.v2.sharing;

import com.box.androidsdk.content.models.BoxSharedLink;
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

public enum ResolvedVisibility {
    PUBLIC,
    TEAM_ONLY,
    PASSWORD,
    TEAM_AND_PASSWORD,
    SHARED_FOLDER_ONLY,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$ResolvedVisibility = null;

        static {
            $SwitchMap$com$dropbox$core$v2$sharing$ResolvedVisibility = new int[ResolvedVisibility.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ResolvedVisibility[ResolvedVisibility.PUBLIC.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ResolvedVisibility[ResolvedVisibility.TEAM_ONLY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ResolvedVisibility[ResolvedVisibility.PASSWORD.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ResolvedVisibility[ResolvedVisibility.TEAM_AND_PASSWORD.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ResolvedVisibility[ResolvedVisibility.SHARED_FOLDER_ONLY.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    static class Serializer extends UnionSerializer<ResolvedVisibility> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(ResolvedVisibility value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$ResolvedVisibility[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("public");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("team_only");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString(BoxSharedLink.FIELD_PASSWORD);
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("team_and_password");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("shared_folder_only");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public ResolvedVisibility deserialize(JsonParser p) throws IOException, JsonParseException {
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
            ResolvedVisibility value;
            if ("public".equals(tag)) {
                value = ResolvedVisibility.PUBLIC;
            } else if ("team_only".equals(tag)) {
                value = ResolvedVisibility.TEAM_ONLY;
            } else if (BoxSharedLink.FIELD_PASSWORD.equals(tag)) {
                value = ResolvedVisibility.PASSWORD;
            } else if ("team_and_password".equals(tag)) {
                value = ResolvedVisibility.TEAM_AND_PASSWORD;
            } else if ("shared_folder_only".equals(tag)) {
                value = ResolvedVisibility.SHARED_FOLDER_ONLY;
            } else {
                value = ResolvedVisibility.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
