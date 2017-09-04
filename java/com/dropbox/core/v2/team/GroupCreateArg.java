package com.dropbox.core.v2.team;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.v2.teamcommon.GroupManagementType;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;

class GroupCreateArg {
    protected final String groupExternalId;
    protected final GroupManagementType groupManagementType;
    protected final String groupName;

    public static class Builder {
        protected String groupExternalId;
        protected GroupManagementType groupManagementType;
        protected final String groupName;

        protected Builder(String groupName) {
            if (groupName == null) {
                throw new IllegalArgumentException("Required value for 'groupName' is null");
            }
            this.groupName = groupName;
            this.groupExternalId = null;
            this.groupManagementType = null;
        }

        public Builder withGroupExternalId(String groupExternalId) {
            this.groupExternalId = groupExternalId;
            return this;
        }

        public Builder withGroupManagementType(GroupManagementType groupManagementType) {
            this.groupManagementType = groupManagementType;
            return this;
        }

        public GroupCreateArg build() {
            return new GroupCreateArg(this.groupName, this.groupExternalId, this.groupManagementType);
        }
    }

    static class Serializer extends StructSerializer<GroupCreateArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GroupCreateArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("group_name");
            StoneSerializers.string().serialize(value.groupName, g);
            if (value.groupExternalId != null) {
                g.writeFieldName("group_external_id");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.groupExternalId, g);
            }
            if (value.groupManagementType != null) {
                g.writeFieldName("group_management_type");
                StoneSerializers.nullable(com.dropbox.core.v2.teamcommon.GroupManagementType.Serializer.INSTANCE).serialize(value.groupManagementType, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GroupCreateArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_groupName = null;
                String f_groupExternalId = null;
                GroupManagementType f_groupManagementType = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("group_name".equals(field)) {
                        f_groupName = (String) StoneSerializers.string().deserialize(p);
                    } else if ("group_external_id".equals(field)) {
                        f_groupExternalId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("group_management_type".equals(field)) {
                        f_groupManagementType = (GroupManagementType) StoneSerializers.nullable(com.dropbox.core.v2.teamcommon.GroupManagementType.Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_groupName == null) {
                    throw new JsonParseException(p, "Required field \"group_name\" missing.");
                }
                GroupCreateArg value = new GroupCreateArg(f_groupName, f_groupExternalId, f_groupManagementType);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GroupCreateArg(String groupName, String groupExternalId, GroupManagementType groupManagementType) {
        if (groupName == null) {
            throw new IllegalArgumentException("Required value for 'groupName' is null");
        }
        this.groupName = groupName;
        this.groupExternalId = groupExternalId;
        this.groupManagementType = groupManagementType;
    }

    public GroupCreateArg(String groupName) {
        this(groupName, null, null);
    }

    public String getGroupName() {
        return this.groupName;
    }

    public String getGroupExternalId() {
        return this.groupExternalId;
    }

    public GroupManagementType getGroupManagementType() {
        return this.groupManagementType;
    }

    public static Builder newBuilder(String groupName) {
        return new Builder(groupName);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.groupName, this.groupExternalId, this.groupManagementType});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GroupCreateArg other = (GroupCreateArg) obj;
        if ((this.groupName == other.groupName || this.groupName.equals(other.groupName)) && (this.groupExternalId == other.groupExternalId || (this.groupExternalId != null && this.groupExternalId.equals(other.groupExternalId)))) {
            if (this.groupManagementType == other.groupManagementType) {
                return true;
            }
            if (this.groupManagementType != null && this.groupManagementType.equals(other.groupManagementType)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return Serializer.INSTANCE.serialize((Object) this, false);
    }

    public String toStringMultiline() {
        return Serializer.INSTANCE.serialize((Object) this, true);
    }
}
