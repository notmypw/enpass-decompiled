package com.dropbox.core.v2.teamcommon;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;

public class GroupSummary {
    protected final String groupExternalId;
    protected final String groupId;
    protected final GroupManagementType groupManagementType;
    protected final String groupName;
    protected final Long memberCount;

    public static class Builder {
        protected String groupExternalId;
        protected final String groupId;
        protected final GroupManagementType groupManagementType;
        protected final String groupName;
        protected Long memberCount;

        protected Builder(String groupName, String groupId, GroupManagementType groupManagementType) {
            if (groupName == null) {
                throw new IllegalArgumentException("Required value for 'groupName' is null");
            }
            this.groupName = groupName;
            if (groupId == null) {
                throw new IllegalArgumentException("Required value for 'groupId' is null");
            }
            this.groupId = groupId;
            if (groupManagementType == null) {
                throw new IllegalArgumentException("Required value for 'groupManagementType' is null");
            }
            this.groupManagementType = groupManagementType;
            this.groupExternalId = null;
            this.memberCount = null;
        }

        public Builder withGroupExternalId(String groupExternalId) {
            this.groupExternalId = groupExternalId;
            return this;
        }

        public Builder withMemberCount(Long memberCount) {
            this.memberCount = memberCount;
            return this;
        }

        public GroupSummary build() {
            return new GroupSummary(this.groupName, this.groupId, this.groupManagementType, this.groupExternalId, this.memberCount);
        }
    }

    public static class Serializer extends StructSerializer<GroupSummary> {
        public static final Serializer INSTANCE = new Serializer();

        public void serialize(GroupSummary value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("group_name");
            StoneSerializers.string().serialize(value.groupName, g);
            g.writeFieldName("group_id");
            StoneSerializers.string().serialize(value.groupId, g);
            g.writeFieldName("group_management_type");
            com.dropbox.core.v2.teamcommon.GroupManagementType.Serializer.INSTANCE.serialize(value.groupManagementType, g);
            if (value.groupExternalId != null) {
                g.writeFieldName("group_external_id");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.groupExternalId, g);
            }
            if (value.memberCount != null) {
                g.writeFieldName("member_count");
                StoneSerializers.nullable(StoneSerializers.uInt32()).serialize(value.memberCount, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GroupSummary deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_groupName = null;
                String f_groupId = null;
                GroupManagementType f_groupManagementType = null;
                String f_groupExternalId = null;
                Long f_memberCount = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("group_name".equals(field)) {
                        f_groupName = (String) StoneSerializers.string().deserialize(p);
                    } else if ("group_id".equals(field)) {
                        f_groupId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("group_management_type".equals(field)) {
                        f_groupManagementType = com.dropbox.core.v2.teamcommon.GroupManagementType.Serializer.INSTANCE.deserialize(p);
                    } else if ("group_external_id".equals(field)) {
                        f_groupExternalId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("member_count".equals(field)) {
                        f_memberCount = (Long) StoneSerializers.nullable(StoneSerializers.uInt32()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_groupName == null) {
                    throw new JsonParseException(p, "Required field \"group_name\" missing.");
                } else if (f_groupId == null) {
                    throw new JsonParseException(p, "Required field \"group_id\" missing.");
                } else if (f_groupManagementType == null) {
                    throw new JsonParseException(p, "Required field \"group_management_type\" missing.");
                } else {
                    GroupSummary value = new GroupSummary(f_groupName, f_groupId, f_groupManagementType, f_groupExternalId, f_memberCount);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GroupSummary(String groupName, String groupId, GroupManagementType groupManagementType, String groupExternalId, Long memberCount) {
        if (groupName == null) {
            throw new IllegalArgumentException("Required value for 'groupName' is null");
        }
        this.groupName = groupName;
        if (groupId == null) {
            throw new IllegalArgumentException("Required value for 'groupId' is null");
        }
        this.groupId = groupId;
        this.groupExternalId = groupExternalId;
        this.memberCount = memberCount;
        if (groupManagementType == null) {
            throw new IllegalArgumentException("Required value for 'groupManagementType' is null");
        }
        this.groupManagementType = groupManagementType;
    }

    public GroupSummary(String groupName, String groupId, GroupManagementType groupManagementType) {
        this(groupName, groupId, groupManagementType, null, null);
    }

    public String getGroupName() {
        return this.groupName;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public GroupManagementType getGroupManagementType() {
        return this.groupManagementType;
    }

    public String getGroupExternalId() {
        return this.groupExternalId;
    }

    public Long getMemberCount() {
        return this.memberCount;
    }

    public static Builder newBuilder(String groupName, String groupId, GroupManagementType groupManagementType) {
        return new Builder(groupName, groupId, groupManagementType);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.groupName, this.groupId, this.groupExternalId, this.memberCount, this.groupManagementType});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GroupSummary other = (GroupSummary) obj;
        if ((this.groupName == other.groupName || this.groupName.equals(other.groupName)) && ((this.groupId == other.groupId || this.groupId.equals(other.groupId)) && ((this.groupManagementType == other.groupManagementType || this.groupManagementType.equals(other.groupManagementType)) && (this.groupExternalId == other.groupExternalId || (this.groupExternalId != null && this.groupExternalId.equals(other.groupExternalId)))))) {
            if (this.memberCount == other.memberCount) {
                return true;
            }
            if (this.memberCount != null && this.memberCount.equals(other.memberCount)) {
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
