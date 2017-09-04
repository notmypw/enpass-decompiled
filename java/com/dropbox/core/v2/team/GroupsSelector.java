package com.dropbox.core.v2.team;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.UnionSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import net.sqlcipher.database.SQLiteDatabase;

public final class GroupsSelector {
    private final Tag _tag;
    private final List<String> groupExternalIdsValue;
    private final List<String> groupIdsValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$GroupsSelector$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupsSelector$Tag[Tag.GROUP_IDS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupsSelector$Tag[Tag.GROUP_EXTERNAL_IDS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<GroupsSelector> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GroupsSelector value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$GroupsSelector$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("group_ids", g);
                    g.writeFieldName("group_ids");
                    StoneSerializers.list(StoneSerializers.string()).serialize(value.groupIdsValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("group_external_ids", g);
                    g.writeFieldName("group_external_ids");
                    StoneSerializers.list(StoneSerializers.string()).serialize(value.groupExternalIdsValue, g);
                    g.writeEndObject();
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public GroupsSelector deserialize(JsonParser p) throws IOException, JsonParseException {
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
            GroupsSelector value;
            if ("group_ids".equals(tag)) {
                StoneSerializer.expectField("group_ids", p);
                value = GroupsSelector.groupIds((List) StoneSerializers.list(StoneSerializers.string()).deserialize(p));
            } else if ("group_external_ids".equals(tag)) {
                StoneSerializer.expectField("group_external_ids", p);
                value = GroupsSelector.groupExternalIds((List) StoneSerializers.list(StoneSerializers.string()).deserialize(p));
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
        GROUP_IDS,
        GROUP_EXTERNAL_IDS
    }

    private GroupsSelector(Tag _tag, List<String> groupIdsValue, List<String> groupExternalIdsValue) {
        this._tag = _tag;
        this.groupIdsValue = groupIdsValue;
        this.groupExternalIdsValue = groupExternalIdsValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isGroupIds() {
        return this._tag == Tag.GROUP_IDS;
    }

    public static GroupsSelector groupIds(List<String> value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        }
        for (String x : value) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list is null");
            }
        }
        return new GroupsSelector(Tag.GROUP_IDS, value, null);
    }

    public List<String> getGroupIdsValue() {
        if (this._tag == Tag.GROUP_IDS) {
            return this.groupIdsValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.GROUP_IDS, but was Tag." + this._tag.name());
    }

    public boolean isGroupExternalIds() {
        return this._tag == Tag.GROUP_EXTERNAL_IDS;
    }

    public static GroupsSelector groupExternalIds(List<String> value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        }
        for (String x : value) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list is null");
            }
        }
        return new GroupsSelector(Tag.GROUP_EXTERNAL_IDS, null, value);
    }

    public List<String> getGroupExternalIdsValue() {
        if (this._tag == Tag.GROUP_EXTERNAL_IDS) {
            return this.groupExternalIdsValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.GROUP_EXTERNAL_IDS, but was Tag." + this._tag.name());
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.groupIdsValue, this.groupExternalIdsValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GroupsSelector)) {
            return false;
        }
        GroupsSelector other = (GroupsSelector) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$GroupsSelector$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.groupIdsValue == other.groupIdsValue || this.groupIdsValue.equals(other.groupIdsValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.groupExternalIdsValue == other.groupExternalIdsValue || this.groupExternalIdsValue.equals(other.groupExternalIdsValue)) {
                    return true;
                }
                return false;
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
