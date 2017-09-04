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

public final class TeamMemberStatus {
    public static final TeamMemberStatus ACTIVE = new TeamMemberStatus(Tag.ACTIVE, null);
    public static final TeamMemberStatus INVITED = new TeamMemberStatus(Tag.INVITED, null);
    public static final TeamMemberStatus SUSPENDED = new TeamMemberStatus(Tag.SUSPENDED, null);
    private final Tag _tag;
    private final RemovedStatus removedValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$TeamMemberStatus$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamMemberStatus$Tag[Tag.ACTIVE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamMemberStatus$Tag[Tag.INVITED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamMemberStatus$Tag[Tag.SUSPENDED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$TeamMemberStatus$Tag[Tag.REMOVED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static class Serializer extends UnionSerializer<TeamMemberStatus> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(TeamMemberStatus value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$TeamMemberStatus$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("active");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("invited");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("suspended");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeStartObject();
                    writeTag("removed", g);
                    Serializer.INSTANCE.serialize(value.removedValue, g, true);
                    g.writeEndObject();
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public TeamMemberStatus deserialize(JsonParser p) throws IOException, JsonParseException {
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
            TeamMemberStatus value;
            if ("active".equals(tag)) {
                value = TeamMemberStatus.ACTIVE;
            } else if ("invited".equals(tag)) {
                value = TeamMemberStatus.INVITED;
            } else if ("suspended".equals(tag)) {
                value = TeamMemberStatus.SUSPENDED;
            } else if ("removed".equals(tag)) {
                value = TeamMemberStatus.removed(Serializer.INSTANCE.deserialize(p, true));
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
        ACTIVE,
        INVITED,
        SUSPENDED,
        REMOVED
    }

    private TeamMemberStatus(Tag _tag, RemovedStatus removedValue) {
        this._tag = _tag;
        this.removedValue = removedValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isActive() {
        return this._tag == Tag.ACTIVE;
    }

    public boolean isInvited() {
        return this._tag == Tag.INVITED;
    }

    public boolean isSuspended() {
        return this._tag == Tag.SUSPENDED;
    }

    public boolean isRemoved() {
        return this._tag == Tag.REMOVED;
    }

    public static TeamMemberStatus removed(RemovedStatus value) {
        if (value != null) {
            return new TeamMemberStatus(Tag.REMOVED, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public RemovedStatus getRemovedValue() {
        if (this._tag == Tag.REMOVED) {
            return this.removedValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.REMOVED, but was Tag." + this._tag.name());
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.removedValue});
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TeamMemberStatus)) {
            return false;
        }
        TeamMemberStatus other = (TeamMemberStatus) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$TeamMemberStatus$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                return true;
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                if (this.removedValue == other.removedValue || this.removedValue.equals(other.removedValue)) {
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
