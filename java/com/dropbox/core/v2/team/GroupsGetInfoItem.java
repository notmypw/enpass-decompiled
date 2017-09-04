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

public final class GroupsGetInfoItem {
    private final Tag _tag;
    private final GroupFullInfo groupInfoValue;
    private final String idNotFoundValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$GroupsGetInfoItem$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupsGetInfoItem$Tag[Tag.ID_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$GroupsGetInfoItem$Tag[Tag.GROUP_INFO.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<GroupsGetInfoItem> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GroupsGetInfoItem value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$GroupsGetInfoItem$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("id_not_found", g);
                    g.writeFieldName("id_not_found");
                    StoneSerializers.string().serialize(value.idNotFoundValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("group_info", g);
                    Serializer.INSTANCE.serialize(value.groupInfoValue, g, true);
                    g.writeEndObject();
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public GroupsGetInfoItem deserialize(JsonParser p) throws IOException, JsonParseException {
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
            GroupsGetInfoItem value;
            if ("id_not_found".equals(tag)) {
                StoneSerializer.expectField("id_not_found", p);
                value = GroupsGetInfoItem.idNotFound((String) StoneSerializers.string().deserialize(p));
            } else if ("group_info".equals(tag)) {
                value = GroupsGetInfoItem.groupInfo(Serializer.INSTANCE.deserialize(p, true));
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
        ID_NOT_FOUND,
        GROUP_INFO
    }

    private GroupsGetInfoItem(Tag _tag, String idNotFoundValue, GroupFullInfo groupInfoValue) {
        this._tag = _tag;
        this.idNotFoundValue = idNotFoundValue;
        this.groupInfoValue = groupInfoValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isIdNotFound() {
        return this._tag == Tag.ID_NOT_FOUND;
    }

    public static GroupsGetInfoItem idNotFound(String value) {
        if (value != null) {
            return new GroupsGetInfoItem(Tag.ID_NOT_FOUND, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public String getIdNotFoundValue() {
        if (this._tag == Tag.ID_NOT_FOUND) {
            return this.idNotFoundValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.ID_NOT_FOUND, but was Tag." + this._tag.name());
    }

    public boolean isGroupInfo() {
        return this._tag == Tag.GROUP_INFO;
    }

    public static GroupsGetInfoItem groupInfo(GroupFullInfo value) {
        if (value != null) {
            return new GroupsGetInfoItem(Tag.GROUP_INFO, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public GroupFullInfo getGroupInfoValue() {
        if (this._tag == Tag.GROUP_INFO) {
            return this.groupInfoValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.GROUP_INFO, but was Tag." + this._tag.name());
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.idNotFoundValue, this.groupInfoValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GroupsGetInfoItem)) {
            return false;
        }
        GroupsGetInfoItem other = (GroupsGetInfoItem) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$GroupsGetInfoItem$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.idNotFoundValue == other.idNotFoundValue || this.idNotFoundValue.equals(other.idNotFoundValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.groupInfoValue == other.groupInfoValue || this.groupInfoValue.equals(other.groupInfoValue)) {
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
