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

public enum SharedFolderAccessError {
    INVALID_ID,
    NOT_A_MEMBER,
    EMAIL_UNVERIFIED,
    UNMOUNTED,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$SharedFolderAccessError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$sharing$SharedFolderAccessError = new int[SharedFolderAccessError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharedFolderAccessError[SharedFolderAccessError.INVALID_ID.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharedFolderAccessError[SharedFolderAccessError.NOT_A_MEMBER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharedFolderAccessError[SharedFolderAccessError.EMAIL_UNVERIFIED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharedFolderAccessError[SharedFolderAccessError.UNMOUNTED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static class Serializer extends UnionSerializer<SharedFolderAccessError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(SharedFolderAccessError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$SharedFolderAccessError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("invalid_id");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("not_a_member");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("email_unverified");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("unmounted");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public SharedFolderAccessError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            SharedFolderAccessError value;
            if ("invalid_id".equals(tag)) {
                value = SharedFolderAccessError.INVALID_ID;
            } else if ("not_a_member".equals(tag)) {
                value = SharedFolderAccessError.NOT_A_MEMBER;
            } else if ("email_unverified".equals(tag)) {
                value = SharedFolderAccessError.EMAIL_UNVERIFIED;
            } else if ("unmounted".equals(tag)) {
                value = SharedFolderAccessError.UNMOUNTED;
            } else {
                value = SharedFolderAccessError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
