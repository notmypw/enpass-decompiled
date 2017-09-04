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
import java.util.Arrays;
import net.sqlcipher.database.SQLiteDatabase;

public final class TeamFolderPermanentlyDeleteError {
    public static final TeamFolderPermanentlyDeleteError OTHER = new TeamFolderPermanentlyDeleteError(Tag.OTHER, null, null);
    private final Tag _tag;
    private final TeamFolderAccessError accessErrorValue;
    private final TeamFolderInvalidStatusError statusErrorValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$TeamFolderPermanentlyDeleteError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamFolderPermanentlyDeleteError$Tag[Tag.ACCESS_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamFolderPermanentlyDeleteError$Tag[Tag.STATUS_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamFolderPermanentlyDeleteError$Tag[Tag.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<TeamFolderPermanentlyDeleteError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(TeamFolderPermanentlyDeleteError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$TeamFolderPermanentlyDeleteError$Tag[value.tag().ordinal()]) {
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
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public TeamFolderPermanentlyDeleteError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            TeamFolderPermanentlyDeleteError value;
            if ("access_error".equals(tag)) {
                StoneSerializer.expectField("access_error", p);
                value = TeamFolderPermanentlyDeleteError.accessError(Serializer.INSTANCE.deserialize(p));
            } else if ("status_error".equals(tag)) {
                StoneSerializer.expectField("status_error", p);
                value = TeamFolderPermanentlyDeleteError.statusError(Serializer.INSTANCE.deserialize(p));
            } else if ("other".equals(tag)) {
                value = TeamFolderPermanentlyDeleteError.OTHER;
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
        OTHER
    }

    private TeamFolderPermanentlyDeleteError(Tag _tag, TeamFolderAccessError accessErrorValue, TeamFolderInvalidStatusError statusErrorValue) {
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

    public static TeamFolderPermanentlyDeleteError accessError(TeamFolderAccessError value) {
        if (value != null) {
            return new TeamFolderPermanentlyDeleteError(Tag.ACCESS_ERROR, value, null);
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

    public static TeamFolderPermanentlyDeleteError statusError(TeamFolderInvalidStatusError value) {
        if (value != null) {
            return new TeamFolderPermanentlyDeleteError(Tag.STATUS_ERROR, null, value);
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

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.accessErrorValue, this.statusErrorValue}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TeamFolderPermanentlyDeleteError)) {
            return false;
        }
        TeamFolderPermanentlyDeleteError other = (TeamFolderPermanentlyDeleteError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$TeamFolderPermanentlyDeleteError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.accessErrorValue == other.accessErrorValue || this.accessErrorValue.equals(other.accessErrorValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.statusErrorValue == other.statusErrorValue || this.statusErrorValue.equals(other.statusErrorValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
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
