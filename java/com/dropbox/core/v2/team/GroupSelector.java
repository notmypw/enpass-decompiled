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
import net.sqlcipher.database.SQLiteDatabase;

public final class GroupSelector {
    private final Tag _tag;
    private final String groupExternalIdValue;
    private final String groupIdValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$GroupSelector$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupSelector$Tag[Tag.GROUP_ID.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupSelector$Tag[Tag.GROUP_EXTERNAL_ID.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<GroupSelector> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GroupSelector value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$GroupSelector$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("group_id", g);
                    g.writeFieldName("group_id");
                    StoneSerializers.string().serialize(value.groupIdValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("group_external_id", g);
                    g.writeFieldName("group_external_id");
                    StoneSerializers.string().serialize(value.groupExternalIdValue, g);
                    g.writeEndObject();
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public GroupSelector deserialize(JsonParser p) throws IOException, JsonParseException {
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
            GroupSelector value;
            if ("group_id".equals(tag)) {
                StoneSerializer.expectField("group_id", p);
                value = GroupSelector.groupId((String) StoneSerializers.string().deserialize(p));
            } else if ("group_external_id".equals(tag)) {
                StoneSerializer.expectField("group_external_id", p);
                value = GroupSelector.groupExternalId((String) StoneSerializers.string().deserialize(p));
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
        GROUP_ID,
        GROUP_EXTERNAL_ID
    }

    private GroupSelector(Tag _tag, String groupIdValue, String groupExternalIdValue) {
        this._tag = _tag;
        this.groupIdValue = groupIdValue;
        this.groupExternalIdValue = groupExternalIdValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isGroupId() {
        return this._tag == Tag.GROUP_ID;
    }

    public static GroupSelector groupId(String value) {
        if (value != null) {
            return new GroupSelector(Tag.GROUP_ID, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public String getGroupIdValue() {
        if (this._tag == Tag.GROUP_ID) {
            return this.groupIdValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.GROUP_ID, but was Tag." + this._tag.name());
    }

    public boolean isGroupExternalId() {
        return this._tag == Tag.GROUP_EXTERNAL_ID;
    }

    public static GroupSelector groupExternalId(String value) {
        if (value != null) {
            return new GroupSelector(Tag.GROUP_EXTERNAL_ID, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public String getGroupExternalIdValue() {
        if (this._tag == Tag.GROUP_EXTERNAL_ID) {
            return this.groupExternalIdValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.GROUP_EXTERNAL_ID, but was Tag." + this._tag.name());
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.groupIdValue, this.groupExternalIdValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GroupSelector)) {
            return false;
        }
        GroupSelector other = (GroupSelector) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$GroupSelector$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.groupIdValue == other.groupIdValue || this.groupIdValue.equals(other.groupIdValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.groupExternalIdValue == other.groupExternalIdValue || this.groupExternalIdValue.equals(other.groupExternalIdValue)) {
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
