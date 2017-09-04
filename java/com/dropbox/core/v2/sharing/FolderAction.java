package com.dropbox.core.v2.sharing;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.UnionSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.samsung.android.sdk.pass.SpassFingerprint;
import in.sinew.enpass.IRemoteStorage;
import java.io.IOException;
import net.sqlcipher.database.SQLiteDatabase;

public enum FolderAction {
    CHANGE_OPTIONS,
    DISABLE_VIEWER_INFO,
    EDIT_CONTENTS,
    ENABLE_VIEWER_INFO,
    INVITE_EDITOR,
    INVITE_VIEWER,
    INVITE_VIEWER_NO_COMMENT,
    RELINQUISH_MEMBERSHIP,
    UNMOUNT,
    UNSHARE,
    LEAVE_A_COPY,
    SHARE_LINK,
    CREATE_LINK,
    OTHER;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$FolderAction = null;

        static {
            $SwitchMap$com$dropbox$core$v2$sharing$FolderAction = new int[FolderAction.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FolderAction[FolderAction.CHANGE_OPTIONS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FolderAction[FolderAction.DISABLE_VIEWER_INFO.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FolderAction[FolderAction.EDIT_CONTENTS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FolderAction[FolderAction.ENABLE_VIEWER_INFO.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FolderAction[FolderAction.INVITE_EDITOR.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FolderAction[FolderAction.INVITE_VIEWER.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FolderAction[FolderAction.INVITE_VIEWER_NO_COMMENT.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FolderAction[FolderAction.RELINQUISH_MEMBERSHIP.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FolderAction[FolderAction.UNMOUNT.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FolderAction[FolderAction.UNSHARE.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FolderAction[FolderAction.LEAVE_A_COPY.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FolderAction[FolderAction.SHARE_LINK.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$FolderAction[FolderAction.CREATE_LINK.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
        }
    }

    static class Serializer extends UnionSerializer<FolderAction> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(FolderAction value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$FolderAction[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("change_options");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("disable_viewer_info");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("edit_contents");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("enable_viewer_info");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("invite_editor");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeString("invite_viewer");
                    return;
                case IRemoteStorage.PIN /*7*/:
                    g.writeString("invite_viewer_no_comment");
                    return;
                case IRemoteStorage.FOLDER_REMOTE /*8*/:
                    g.writeString("relinquish_membership");
                    return;
                case IRemoteStorage.WEBDAV_REMOTE /*9*/:
                    g.writeString("unmount");
                    return;
                case IRemoteStorage.WEBDAV_REMOTE_USERNAME /*10*/:
                    g.writeString("unshare");
                    return;
                case IRemoteStorage.WEBDAV_REMOTE_PASSWORD /*11*/:
                    g.writeString("leave_a_copy");
                    return;
                case SpassFingerprint.STATUS_QUALITY_FAILED /*12*/:
                    g.writeString("share_link");
                    return;
                case SpassFingerprint.STATUS_USER_CANCELLED_BY_TOUCH_OUTSIDE /*13*/:
                    g.writeString("create_link");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public FolderAction deserialize(JsonParser p) throws IOException, JsonParseException {
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
            FolderAction value;
            if ("change_options".equals(tag)) {
                value = FolderAction.CHANGE_OPTIONS;
            } else if ("disable_viewer_info".equals(tag)) {
                value = FolderAction.DISABLE_VIEWER_INFO;
            } else if ("edit_contents".equals(tag)) {
                value = FolderAction.EDIT_CONTENTS;
            } else if ("enable_viewer_info".equals(tag)) {
                value = FolderAction.ENABLE_VIEWER_INFO;
            } else if ("invite_editor".equals(tag)) {
                value = FolderAction.INVITE_EDITOR;
            } else if ("invite_viewer".equals(tag)) {
                value = FolderAction.INVITE_VIEWER;
            } else if ("invite_viewer_no_comment".equals(tag)) {
                value = FolderAction.INVITE_VIEWER_NO_COMMENT;
            } else if ("relinquish_membership".equals(tag)) {
                value = FolderAction.RELINQUISH_MEMBERSHIP;
            } else if ("unmount".equals(tag)) {
                value = FolderAction.UNMOUNT;
            } else if ("unshare".equals(tag)) {
                value = FolderAction.UNSHARE;
            } else if ("leave_a_copy".equals(tag)) {
                value = FolderAction.LEAVE_A_COPY;
            } else if ("share_link".equals(tag)) {
                value = FolderAction.SHARE_LINK;
            } else if ("create_link".equals(tag)) {
                value = FolderAction.CREATE_LINK;
            } else {
                value = FolderAction.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }
}
