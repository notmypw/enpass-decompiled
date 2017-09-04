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

public enum FileAction {
    DISABLE_VIEWER_INFO,
    EDIT_CONTENTS,
    ENABLE_VIEWER_INFO,
    INVITE_VIEWER,
    INVITE_VIEWER_NO_COMMENT,
    UNSHARE,
    RELINQUISH_MEMBERSHIP,
    SHARE_LINK,
    CREATE_LINK,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$FileAction = null;

        static {
            $SwitchMap$com$dropbox$core$v2$sharing$FileAction = new int[FileAction.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FileAction[FileAction.DISABLE_VIEWER_INFO.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FileAction[FileAction.EDIT_CONTENTS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FileAction[FileAction.ENABLE_VIEWER_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FileAction[FileAction.INVITE_VIEWER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FileAction[FileAction.INVITE_VIEWER_NO_COMMENT.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FileAction[FileAction.UNSHARE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FileAction[FileAction.RELINQUISH_MEMBERSHIP.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FileAction[FileAction.SHARE_LINK.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FileAction[FileAction.CREATE_LINK.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
        }
    }

    static class Serializer extends UnionSerializer<FileAction> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(FileAction value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$FileAction[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("disable_viewer_info");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("edit_contents");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("enable_viewer_info");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("invite_viewer");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("invite_viewer_no_comment");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeString("unshare");
                    return;
                case IRemoteStorage.PIN /*7*/:
                    g.writeString("relinquish_membership");
                    return;
                case IRemoteStorage.FOLDER_REMOTE /*8*/:
                    g.writeString("share_link");
                    return;
                case IRemoteStorage.WEBDAV_REMOTE /*9*/:
                    g.writeString("create_link");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public FileAction deserialize(JsonParser p) throws IOException, JsonParseException {
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
            FileAction value;
            if ("disable_viewer_info".equals(tag)) {
                value = FileAction.DISABLE_VIEWER_INFO;
            } else if ("edit_contents".equals(tag)) {
                value = FileAction.EDIT_CONTENTS;
            } else if ("enable_viewer_info".equals(tag)) {
                value = FileAction.ENABLE_VIEWER_INFO;
            } else if ("invite_viewer".equals(tag)) {
                value = FileAction.INVITE_VIEWER;
            } else if ("invite_viewer_no_comment".equals(tag)) {
                value = FileAction.INVITE_VIEWER_NO_COMMENT;
            } else if ("unshare".equals(tag)) {
                value = FileAction.UNSHARE;
            } else if ("relinquish_membership".equals(tag)) {
                value = FileAction.RELINQUISH_MEMBERSHIP;
            } else if ("share_link".equals(tag)) {
                value = FileAction.SHARE_LINK;
            } else if ("create_link".equals(tag)) {
                value = FileAction.CREATE_LINK;
            } else {
                value = FileAction.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
