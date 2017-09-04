package com.dropbox.core.v2.users;

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

public final class SpaceAllocation {
    public static final SpaceAllocation OTHER = new SpaceAllocation(Tag.OTHER, null, null);
    private final Tag _tag;
    private final IndividualSpaceAllocation individualValue;
    private final TeamSpaceAllocation teamValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$users$SpaceAllocation$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$users$SpaceAllocation$Tag[Tag.INDIVIDUAL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$users$SpaceAllocation$Tag[Tag.TEAM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$users$SpaceAllocation$Tag[Tag.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<SpaceAllocation> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(SpaceAllocation value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$users$SpaceAllocation$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("individual", g);
                    Serializer.INSTANCE.serialize(value.individualValue, g, true);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("team", g);
                    Serializer.INSTANCE.serialize(value.teamValue, g, true);
                    g.writeEndObject();
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public SpaceAllocation deserialize(JsonParser p) throws IOException, JsonParseException {
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
            SpaceAllocation value;
            if ("individual".equals(tag)) {
                value = SpaceAllocation.individual(Serializer.INSTANCE.deserialize(p, true));
            } else if ("team".equals(tag)) {
                value = SpaceAllocation.team(Serializer.INSTANCE.deserialize(p, true));
            } else {
                value = SpaceAllocation.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        INDIVIDUAL,
        TEAM,
        OTHER
    }

    private SpaceAllocation(Tag _tag, IndividualSpaceAllocation individualValue, TeamSpaceAllocation teamValue) {
        this._tag = _tag;
        this.individualValue = individualValue;
        this.teamValue = teamValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isIndividual() {
        return this._tag == Tag.INDIVIDUAL;
    }

    public static SpaceAllocation individual(IndividualSpaceAllocation value) {
        if (value != null) {
            return new SpaceAllocation(Tag.INDIVIDUAL, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public IndividualSpaceAllocation getIndividualValue() {
        if (this._tag == Tag.INDIVIDUAL) {
            return this.individualValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.INDIVIDUAL, but was Tag." + this._tag.name());
    }

    public boolean isTeam() {
        return this._tag == Tag.TEAM;
    }

    public static SpaceAllocation team(TeamSpaceAllocation value) {
        if (value != null) {
            return new SpaceAllocation(Tag.TEAM, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public TeamSpaceAllocation getTeamValue() {
        if (this._tag == Tag.TEAM) {
            return this.teamValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.TEAM, but was Tag." + this._tag.name());
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.individualValue, this.teamValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SpaceAllocation)) {
            return false;
        }
        SpaceAllocation other = (SpaceAllocation) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$users$SpaceAllocation$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.individualValue == other.individualValue || this.individualValue.equals(other.individualValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.teamValue == other.teamValue || this.teamValue.equals(other.teamValue)) {
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
