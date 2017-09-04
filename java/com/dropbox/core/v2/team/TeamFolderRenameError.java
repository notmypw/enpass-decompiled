package com.dropbox.core.v2.team;

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
import java.util.Arrays;
import net.sqlcipher.database.SQLiteDatabase;

public final class TeamFolderRenameError {
    public static final TeamFolderRenameError FOLDER_NAME_ALREADY_USED = new TeamFolderRenameError(Tag.FOLDER_NAME_ALREADY_USED, null, null);
    public static final TeamFolderRenameError FOLDER_NAME_RESERVED = new TeamFolderRenameError(Tag.FOLDER_NAME_RESERVED, null, null);
    public static final TeamFolderRenameError INVALID_FOLDER_NAME = new TeamFolderRenameError(Tag.INVALID_FOLDER_NAME, null, null);
    public static final TeamFolderRenameError OTHER = new TeamFolderRenameError(Tag.OTHER, null, null);
    private final Tag _tag;
    private final TeamFolderAccessError accessErrorValue;
    private final TeamFolderInvalidStatusError statusErrorValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$TeamFolderRenameError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamFolderRenameError$Tag[Tag.ACCESS_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamFolderRenameError$Tag[Tag.STATUS_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamFolderRenameError$Tag[Tag.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamFolderRenameError$Tag[Tag.INVALID_FOLDER_NAME.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamFolderRenameError$Tag[Tag.FOLDER_NAME_ALREADY_USED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamFolderRenameError$Tag[Tag.FOLDER_NAME_RESERVED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    static class Serializer extends UnionSerializer<TeamFolderRenameError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(TeamFolderRenameError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$TeamFolderRenameError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("access_error", g);
                    g.writeFieldName("access_error");
                    Serializer.INSTANCE.serialize(value.accessErrorValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("status_error", g);
                    g.writeFieldName("status_error");
                    Serializer.INSTANCE.serialize(value.statusErrorValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("other");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("invalid_folder_name");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("folder_name_already_used");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeString("folder_name_reserved");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public TeamFolderRenameError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            TeamFolderRenameError value;
            if ("access_error".equals(tag)) {
                StoneSerializer.expectField("access_error", p);
                value = TeamFolderRenameError.accessError(Serializer.INSTANCE.deserialize(p));
            } else if ("status_error".equals(tag)) {
                StoneSerializer.expectField("status_error", p);
                value = TeamFolderRenameError.statusError(Serializer.INSTANCE.deserialize(p));
            } else if ("other".equals(tag)) {
                value = TeamFolderRenameError.OTHER;
            } else if ("invalid_folder_name".equals(tag)) {
                value = TeamFolderRenameError.INVALID_FOLDER_NAME;
            } else if ("folder_name_already_used".equals(tag)) {
                value = TeamFolderRenameError.FOLDER_NAME_ALREADY_USED;
            } else if ("folder_name_reserved".equals(tag)) {
                value = TeamFolderRenameError.FOLDER_NAME_RESERVED;
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

    public enum Tag {
        ACCESS_ERROR,
        STATUS_ERROR,
        OTHER,
        INVALID_FOLDER_NAME,
        FOLDER_NAME_ALREADY_USED,
        FOLDER_NAME_RESERVED
    }

    private TeamFolderRenameError(Tag _tag, TeamFolderAccessError accessErrorValue, TeamFolderInvalidStatusError statusErrorValue) {
        this._tag = _tag;
        this.accessErrorValue = accessErrorValue;
        this.statusErrorValue = statusErrorValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isAccessError() {
        return this._tag == Tag.ACCESS_ERROR;
    }

    public static TeamFolderRenameError accessError(TeamFolderAccessError value) {
        if (value != null) {
            return new TeamFolderRenameError(Tag.ACCESS_ERROR, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public TeamFolderAccessError getAccessErrorValue() {
        if (this._tag == Tag.ACCESS_ERROR) {
            return this.accessErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.ACCESS_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isStatusError() {
        return this._tag == Tag.STATUS_ERROR;
    }

    public static TeamFolderRenameError statusError(TeamFolderInvalidStatusError value) {
        if (value != null) {
            return new TeamFolderRenameError(Tag.STATUS_ERROR, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public TeamFolderInvalidStatusError getStatusErrorValue() {
        if (this._tag == Tag.STATUS_ERROR) {
            return this.statusErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.STATUS_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public boolean isInvalidFolderName() {
        return this._tag == Tag.INVALID_FOLDER_NAME;
    }

    public boolean isFolderNameAlreadyUsed() {
        return this._tag == Tag.FOLDER_NAME_ALREADY_USED;
    }

    public boolean isFolderNameReserved() {
        return this._tag == Tag.FOLDER_NAME_RESERVED;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.accessErrorValue, this.statusErrorValue}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TeamFolderRenameError)) {
            return false;
        }
        TeamFolderRenameError other = (TeamFolderRenameError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$TeamFolderRenameError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.accessErrorValue == other.accessErrorValue || this.accessErrorValue.equals(other.accessErrorValue)) {
                    z = true;
                }
                return z;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.statusErrorValue == other.statusErrorValue || this.statusErrorValue.equals(other.statusErrorValue)) {
                    z = true;
                }
                return z;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
            case IRemoteStorage.BOX_REMOTE /*6*/:
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
