package com.dropbox.core.v2.sharing;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.UnionSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import in.sinew.enpass.IRemoteStorage;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;
import net.sqlcipher.database.SQLiteDatabase;

public final class AddMemberSelectorError {
    public static final AddMemberSelectorError AUTOMATIC_GROUP = new AddMemberSelectorError(Tag.AUTOMATIC_GROUP, null, null, null);
    public static final AddMemberSelectorError GROUP_DELETED = new AddMemberSelectorError(Tag.GROUP_DELETED, null, null, null);
    public static final AddMemberSelectorError GROUP_NOT_ON_TEAM = new AddMemberSelectorError(Tag.GROUP_NOT_ON_TEAM, null, null, null);
    public static final AddMemberSelectorError OTHER = new AddMemberSelectorError(Tag.OTHER, null, null, null);
    private final Tag _tag;
    private final String invalidDropboxIdValue;
    private final String invalidEmailValue;
    private final String unverifiedDropboxIdValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$AddMemberSelectorError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$AddMemberSelectorError$Tag[Tag.AUTOMATIC_GROUP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$AddMemberSelectorError$Tag[Tag.INVALID_DROPBOX_ID.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$AddMemberSelectorError$Tag[Tag.INVALID_EMAIL.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$AddMemberSelectorError$Tag[Tag.UNVERIFIED_DROPBOX_ID.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$AddMemberSelectorError$Tag[Tag.GROUP_DELETED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$AddMemberSelectorError$Tag[Tag.GROUP_NOT_ON_TEAM.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$AddMemberSelectorError$Tag[Tag.OTHER.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    static class Serializer extends UnionSerializer<AddMemberSelectorError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(AddMemberSelectorError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$AddMemberSelectorError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("automatic_group");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("invalid_dropbox_id", g);
                    g.writeFieldName("invalid_dropbox_id");
                    StoneSerializers.string().serialize(value.invalidDropboxIdValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeStartObject();
                    writeTag("invalid_email", g);
                    g.writeFieldName("invalid_email");
                    StoneSerializers.string().serialize(value.invalidEmailValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeStartObject();
                    writeTag("unverified_dropbox_id", g);
                    g.writeFieldName("unverified_dropbox_id");
                    StoneSerializers.string().serialize(value.unverifiedDropboxIdValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("group_deleted");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeString("group_not_on_team");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public AddMemberSelectorError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            AddMemberSelectorError value;
            if ("automatic_group".equals(tag)) {
                value = AddMemberSelectorError.AUTOMATIC_GROUP;
            } else if ("invalid_dropbox_id".equals(tag)) {
                StoneSerializer.expectField("invalid_dropbox_id", p);
                value = AddMemberSelectorError.invalidDropboxId((String) StoneSerializers.string().deserialize(p));
            } else if ("invalid_email".equals(tag)) {
                StoneSerializer.expectField("invalid_email", p);
                value = AddMemberSelectorError.invalidEmail((String) StoneSerializers.string().deserialize(p));
            } else if ("unverified_dropbox_id".equals(tag)) {
                StoneSerializer.expectField("unverified_dropbox_id", p);
                value = AddMemberSelectorError.unverifiedDropboxId((String) StoneSerializers.string().deserialize(p));
            } else if ("group_deleted".equals(tag)) {
                value = AddMemberSelectorError.GROUP_DELETED;
            } else if ("group_not_on_team".equals(tag)) {
                value = AddMemberSelectorError.GROUP_NOT_ON_TEAM;
            } else {
                value = AddMemberSelectorError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        AUTOMATIC_GROUP,
        INVALID_DROPBOX_ID,
        INVALID_EMAIL,
        UNVERIFIED_DROPBOX_ID,
        GROUP_DELETED,
        GROUP_NOT_ON_TEAM,
        OTHER
    }

    private AddMemberSelectorError(Tag _tag, String invalidDropboxIdValue, String invalidEmailValue, String unverifiedDropboxIdValue) {
        this._tag = _tag;
        this.invalidDropboxIdValue = invalidDropboxIdValue;
        this.invalidEmailValue = invalidEmailValue;
        this.unverifiedDropboxIdValue = unverifiedDropboxIdValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isAutomaticGroup() {
        return this._tag == Tag.AUTOMATIC_GROUP;
    }

    public boolean isInvalidDropboxId() {
        return this._tag == Tag.INVALID_DROPBOX_ID;
    }

    public static AddMemberSelectorError invalidDropboxId(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() >= 1) {
            return new AddMemberSelectorError(Tag.INVALID_DROPBOX_ID, value, null, null);
        } else {
            throw new IllegalArgumentException("String is shorter than 1");
        }
    }

    public String getInvalidDropboxIdValue() {
        if (this._tag == Tag.INVALID_DROPBOX_ID) {
            return this.invalidDropboxIdValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.INVALID_DROPBOX_ID, but was Tag." + this._tag.name());
    }

    public boolean isInvalidEmail() {
        return this._tag == Tag.INVALID_EMAIL;
    }

    public static AddMemberSelectorError invalidEmail(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() > 255) {
            throw new IllegalArgumentException("String is longer than 255");
        } else if (Pattern.matches("^['&A-Za-z0-9._%+-]+@[A-Za-z0-9-][A-Za-z0-9.-]*.[A-Za-z]{2,15}$", value)) {
            return new AddMemberSelectorError(Tag.INVALID_EMAIL, null, value, null);
        } else {
            throw new IllegalArgumentException("String does not match pattern");
        }
    }

    public String getInvalidEmailValue() {
        if (this._tag == Tag.INVALID_EMAIL) {
            return this.invalidEmailValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.INVALID_EMAIL, but was Tag." + this._tag.name());
    }

    public boolean isUnverifiedDropboxId() {
        return this._tag == Tag.UNVERIFIED_DROPBOX_ID;
    }

    public static AddMemberSelectorError unverifiedDropboxId(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() >= 1) {
            return new AddMemberSelectorError(Tag.UNVERIFIED_DROPBOX_ID, null, null, value);
        } else {
            throw new IllegalArgumentException("String is shorter than 1");
        }
    }

    public String getUnverifiedDropboxIdValue() {
        if (this._tag == Tag.UNVERIFIED_DROPBOX_ID) {
            return this.unverifiedDropboxIdValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.UNVERIFIED_DROPBOX_ID, but was Tag." + this._tag.name());
    }

    public boolean isGroupDeleted() {
        return this._tag == Tag.GROUP_DELETED;
    }

    public boolean isGroupNotOnTeam() {
        return this._tag == Tag.GROUP_NOT_ON_TEAM;
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.invalidDropboxIdValue, this.invalidEmailValue, this.unverifiedDropboxIdValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AddMemberSelectorError)) {
            return false;
        }
        AddMemberSelectorError other = (AddMemberSelectorError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$AddMemberSelectorError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                return true;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.invalidDropboxIdValue == other.invalidDropboxIdValue || this.invalidDropboxIdValue.equals(other.invalidDropboxIdValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                if (this.invalidEmailValue == other.invalidEmailValue || this.invalidEmailValue.equals(other.invalidEmailValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                if (this.unverifiedDropboxIdValue == other.unverifiedDropboxIdValue || this.unverifiedDropboxIdValue.equals(other.unverifiedDropboxIdValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                return true;
            case IRemoteStorage.BOX_REMOTE /*6*/:
                return true;
            case IRemoteStorage.PIN /*7*/:
                return true;
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
