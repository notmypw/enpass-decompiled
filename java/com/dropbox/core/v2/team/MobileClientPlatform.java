package com.dropbox.core.v2.team;

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

public enum MobileClientPlatform {
    IPHONE,
    IPAD,
    ANDROID,
    WINDOWS_PHONE,
    BLACKBERRY,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$MobileClientPlatform = null;

        static {
            $SwitchMap$com$dropbox$core$v2$team$MobileClientPlatform = new int[MobileClientPlatform.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$team$MobileClientPlatform[MobileClientPlatform.IPHONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MobileClientPlatform[MobileClientPlatform.IPAD.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MobileClientPlatform[MobileClientPlatform.ANDROID.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MobileClientPlatform[MobileClientPlatform.WINDOWS_PHONE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$MobileClientPlatform[MobileClientPlatform.BLACKBERRY.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    static class Serializer extends UnionSerializer<MobileClientPlatform> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(MobileClientPlatform value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$MobileClientPlatform[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("iphone");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("ipad");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("android");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("windows_phone");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("blackberry");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public MobileClientPlatform deserialize(JsonParser p) throws IOException, JsonParseException {
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
            MobileClientPlatform value;
            if ("iphone".equals(tag)) {
                value = MobileClientPlatform.IPHONE;
            } else if ("ipad".equals(tag)) {
                value = MobileClientPlatform.IPAD;
            } else if ("android".equals(tag)) {
                value = MobileClientPlatform.ANDROID;
            } else if ("windows_phone".equals(tag)) {
                value = MobileClientPlatform.WINDOWS_PHONE;
            } else if ("blackberry".equals(tag)) {
                value = MobileClientPlatform.BLACKBERRY;
            } else {
                value = MobileClientPlatform.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
