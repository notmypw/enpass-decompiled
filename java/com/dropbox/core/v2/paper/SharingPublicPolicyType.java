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

public enum SharingPublicPolicyType {
    PEOPLE_WITH_LINK_CAN_EDIT,
    PEOPLE_WITH_LINK_CAN_VIEW_AND_COMMENT,
    INVITE_ONLY,
    DISABLED;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$paper$SharingPublicPolicyType = null;

        static {
            $SwitchMap$com$dropbox$core$v2$paper$SharingPublicPolicyType = new int[SharingPublicPolicyType.values().length];
            try {
                $SwitchMap$com$dropbox$core$v2$paper$SharingPublicPolicyType[SharingPublicPolicyType.PEOPLE_WITH_LINK_CAN_EDIT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$SharingPublicPolicyType[SharingPublicPolicyType.PEOPLE_WITH_LINK_CAN_VIEW_AND_COMMENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$SharingPublicPolicyType[SharingPublicPolicyType.INVITE_ONLY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$paper$SharingPublicPolicyType[SharingPublicPolicyType.DISABLED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static class Serializer extends UnionSerializer<SharingPublicPolicyType> {
        public static final Serializer INSTANCE = null;

        Serializer() {
        }

        static {
            INSTANCE = new Serializer();
        }

        public void serialize(SharingPublicPolicyType value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$paper$SharingPublicPolicyType[value.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("people_with_link_can_edit");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("people_with_link_can_view_and_comment");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("invite_only");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("disabled");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value);
            }
        }

        public SharingPublicPolicyType deserialize(JsonParser p) throws IOException, JsonParseException {
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
            SharingPublicPolicyType value;
            if ("people_with_link_can_edit".equals(tag)) {
                value = SharingPublicPolicyType.PEOPLE_WITH_LINK_CAN_EDIT;
            } else if ("people_with_link_can_view_and_comment".equals(tag)) {
                value = SharingPublicPolicyType.PEOPLE_WITH_LINK_CAN_VIEW_AND_COMMENT;
            } else if ("invite_only".equals(tag)) {
                value = SharingPublicPolicyType.INVITE_ONLY;
            } else if ("disabled".equals(tag)) {
                value = SharingPublicPolicyType.DISABLED;
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
