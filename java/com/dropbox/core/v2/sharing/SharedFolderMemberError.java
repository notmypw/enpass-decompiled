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
import java.util.Arrays;
import net.sqlcipher.database.SQLiteDatabase;

public final class SharedFolderMemberError {
    public static final SharedFolderMemberError INVALID_DROPBOX_ID = new SharedFolderMemberError(Tag.INVALID_DROPBOX_ID, null);
    public static final SharedFolderMemberError NOT_A_MEMBER = new SharedFolderMemberError(Tag.NOT_A_MEMBER, null);
    public static final SharedFolderMemberError OTHER = new SharedFolderMemberError(Tag.OTHER, null);
    private final Tag _tag;
    private final MemberAccessLevelResult noExplicitAccessValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$SharedFolderMemberError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharedFolderMemberError$Tag[Tag.INVALID_DROPBOX_ID.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharedFolderMemberError$Tag[Tag.NOT_A_MEMBER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharedFolderMemberError$Tag[Tag.NO_EXPLICIT_ACCESS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharedFolderMemberError$Tag[Tag.OTHER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static class Serializer extends UnionSerializer<SharedFolderMemberError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(SharedFolderMemberError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$SharedFolderMemberError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("invalid_dropbox_id");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("not_a_member");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeStartObject();
                    writeTag("no_explicit_access", g);
                    Serializer.INSTANCE.serialize(value.noExplicitAccessValue, g, true);
                    g.writeEndObject();
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public SharedFolderMemberError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            SharedFolderMemberError value;
            if ("invalid_dropbox_id".equals(tag)) {
                value = SharedFolderMemberError.INVALID_DROPBOX_ID;
            } else if ("not_a_member".equals(tag)) {
                value = SharedFolderMemberError.NOT_A_MEMBER;
            } else if ("no_explicit_access".equals(tag)) {
                value = SharedFolderMemberError.noExplicitAccess(Serializer.INSTANCE.deserialize(p, true));
            } else {
                value = SharedFolderMemberError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        INVALID_DROPBOX_ID,
        NOT_A_MEMBER,
        NO_EXPLICIT_ACCESS,
        OTHER
    }

    private SharedFolderMemberError(Tag _tag, MemberAccessLevelResult noExplicitAccessValue) {
        this._tag = _tag;
        this.noExplicitAccessValue = noExplicitAccessValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isInvalidDropboxId() {
        return this._tag == Tag.INVALID_DROPBOX_ID;
    }

    public boolean isNotAMember() {
        return this._tag == Tag.NOT_A_MEMBER;
    }

    public boolean isNoExplicitAccess() {
        return this._tag == Tag.NO_EXPLICIT_ACCESS;
    }

    public static SharedFolderMemberError noExplicitAccess(MemberAccessLevelResult value) {
        if (value != null) {
            return new SharedFolderMemberError(Tag.NO_EXPLICIT_ACCESS, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public MemberAccessLevelResult getNoExplicitAccessValue() {
        if (this._tag == Tag.NO_EXPLICIT_ACCESS) {
            return this.noExplicitAccessValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.NO_EXPLICIT_ACCESS, but was Tag." + this._tag.name());
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.noExplicitAccessValue});
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SharedFolderMemberError)) {
            return false;
        }
        SharedFolderMemberError other = (SharedFolderMemberError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$SharedFolderMemberError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                return true;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                if (this.noExplicitAccessValue == other.noExplicitAccessValue || this.noExplicitAccessValue.equals(other.noExplicitAccessValue)) {
                    z = true;
                }
                return z;
            default:
                return false;
        }
    }

    public String toString() {
        return Serializer.INSTANCE.serialize((Object) this, false);
    }

    public String toStringMultiline() {
        return Serializer.INSTANCE.serialize((Object) this, true);
    }
}
