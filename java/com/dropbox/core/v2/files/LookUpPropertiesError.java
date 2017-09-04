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

public enum LookUpPropertiesError {
    PROPERTY_GROUP_NOT_FOUND;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$LookUpPropertiesError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$files$LookUpPropertiesError = new int[LookUpPropertiesError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$files$LookUpPropertiesError[LookUpPropertiesError.PROPERTY_GROUP_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    static class Serializer extends UnionSerializer<LookUpPropertiesError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(LookUpPropertiesError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$LookUpPropertiesError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("property_group_not_found");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value);
            }
        }

        public LookUpPropertiesError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            } else if ("property_group_not_found".equals(tag)) {
                LookUpPropertiesError value = LookUpPropertiesError.PROPERTY_GROUP_NOT_FOUND;
                if (!collapsed) {
                    StoneSerializer.skipFields(p);
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            } else {
                throw new JsonParseException(p, "Unknown tag: " + tag);
            }
        }
    }
}
