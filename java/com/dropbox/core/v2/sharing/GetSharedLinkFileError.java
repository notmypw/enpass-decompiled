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

public enum GetSharedLinkFileError {
    SHARED_LINK_NOT_FOUND,
    SHARED_LINK_ACCESS_DENIED,
    UNSUPPORTED_LINK_TYPE,
    OTHER,
    SHARED_LINK_IS_DIRECTORY;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$GetSharedLinkFileError = null;

        static {
            $SwitchMap$com$dropbox$core$v2$sharing$GetSharedLinkFileError = new int[GetSharedLinkFileError.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$GetSharedLinkFileError[GetSharedLinkFileError.SHARED_LINK_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$GetSharedLinkFileError[GetSharedLinkFileError.SHARED_LINK_ACCESS_DENIED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$GetSharedLinkFileError[GetSharedLinkFileError.UNSUPPORTED_LINK_TYPE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$GetSharedLinkFileError[GetSharedLinkFileError.OTHER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$GetSharedLinkFileError[GetSharedLinkFileError.SHARED_LINK_IS_DIRECTORY.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    static class Serializer extends UnionSerializer<GetSharedLinkFileError> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(GetSharedLinkFileError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$GetSharedLinkFileError[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("shared_link_not_found");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("shared_link_access_denied");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("unsupported_link_type");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("other");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("shared_link_is_directory");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value);
            }
        }

        public GetSharedLinkFileError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            GetSharedLinkFileError value;
            if ("shared_link_not_found".equals(tag)) {
                value = GetSharedLinkFileError.SHARED_LINK_NOT_FOUND;
            } else if ("shared_link_access_denied".equals(tag)) {
                value = GetSharedLinkFileError.SHARED_LINK_ACCESS_DENIED;
            } else if ("unsupported_link_type".equals(tag)) {
                value = GetSharedLinkFileError.UNSUPPORTED_LINK_TYPE;
            } else if ("other".equals(tag)) {
                value = GetSharedLinkFileError.OTHER;
            } else if ("shared_link_is_directory".equals(tag)) {
                value = GetSharedLinkFileError.SHARED_LINK_IS_DIRECTORY;
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
