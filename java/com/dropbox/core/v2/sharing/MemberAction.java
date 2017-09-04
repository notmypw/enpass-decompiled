package com.dropbox.core.v2.sharing;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.UnionSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import in.sinew.enpass.IRemoteStorage;
import java.io.IOException;
import net.sqlcipher.database.SQLiteDatabase;

public enum MemberAction {
    LEAVE_A_COPY,
    MAKE_EDITOR,
    MAKE_OWNER,
    MAKE_VIEWER,
    MAKE_VIEWER_NO_COMMENT,
    REMOVE,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$MemberAction = null;

        static {
            $SwitchMap$com$dropbox$core$v2$sharing$MemberAction = new int[MemberAction.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$MemberAction[MemberAction.LEAVE_A_COPY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$MemberAction[MemberAction.MAKE_EDITOR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$MemberAction[MemberAction.MAKE_OWNER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$MemberAction[MemberAction.MAKE_VIEWER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$MemberAction[MemberAction.MAKE_VIEWER_NO_COMMENT.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$MemberAction[MemberAction.REMOVE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    static class Serializer extends UnionSerializer<MemberAction> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(MemberAction value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$MemberAction[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("leave_a_copy");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("make_editor");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("make_owner");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("make_viewer");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("make_viewer_no_comment");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeString("remove");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public MemberAction deserialize(JsonParser p) throws IOException, JsonParseException {
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
            MemberAction value;
            if ("leave_a_copy".equals(tag)) {
                value = MemberAction.LEAVE_A_COPY;
            } else if ("make_editor".equals(tag)) {
                value = MemberAction.MAKE_EDITOR;
            } else if ("make_owner".equals(tag)) {
                value = MemberAction.MAKE_OWNER;
            } else if ("make_viewer".equals(tag)) {
                value = MemberAction.MAKE_VIEWER;
            } else if ("make_viewer_no_comment".equals(tag)) {
                value = MemberAction.MAKE_VIEWER_NO_COMMENT;
            } else if ("remove".equals(tag)) {
                value = MemberAction.REMOVE;
            } else {
                value = MemberAction.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
