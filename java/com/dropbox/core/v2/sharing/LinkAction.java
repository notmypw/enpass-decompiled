package com.dropbox.core.v2.sharing;

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

public enum LinkAction {
    CHANGE_AUDIENCE,
    REMOVE_EXPIRY,
    REMOVE_PASSWORD,
    SET_EXPIRY,
    SET_PASSWORD,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$LinkAction = null;

        static {
            $SwitchMap$com$dropbox$core$v2$sharing$LinkAction = new int[LinkAction.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$LinkAction[LinkAction.CHANGE_AUDIENCE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$LinkAction[LinkAction.REMOVE_EXPIRY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$LinkAction[LinkAction.REMOVE_PASSWORD.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$LinkAction[LinkAction.SET_EXPIRY.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$LinkAction[LinkAction.SET_PASSWORD.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    static class Serializer extends UnionSerializer<LinkAction> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(LinkAction value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$LinkAction[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("change_audience");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("remove_expiry");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("remove_password");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("set_expiry");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("set_password");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public LinkAction deserialize(JsonParser p) throws IOException, JsonParseException {
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
            LinkAction value;
            if ("change_audience".equals(tag)) {
                value = LinkAction.CHANGE_AUDIENCE;
            } else if ("remove_expiry".equals(tag)) {
                value = LinkAction.REMOVE_EXPIRY;
            } else if ("remove_password".equals(tag)) {
                value = LinkAction.REMOVE_PASSWORD;
            } else if ("set_expiry".equals(tag)) {
                value = LinkAction.SET_EXPIRY;
            } else if ("set_password".equals(tag)) {
                value = LinkAction.SET_PASSWORD;
            } else {
                value = LinkAction.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
