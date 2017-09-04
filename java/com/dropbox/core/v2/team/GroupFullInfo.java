package com.dropbox.core.v2.team;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.v2.teamcommon.GroupManagementType;
import com.dropbox.core.v2.teamcommon.GroupSummary;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GroupFullInfo extends GroupSummary {
    protected final long created;
    protected final List<GroupMemberInfo> members;

    public static class Builder extends com.dropbox.core.v2.teamcommon.GroupSummary.Builder {
        protected final long created;
        protected List<GroupMemberInfo> members = null;

        protected Builder(String groupName, String groupId, GroupManagementType groupManagementType, long created) {
            super(groupName, groupId, groupManagementType);
            this.created = created;
        }

        public Builder withMembers(List<GroupMemberInfo> members) {
            if (members != null) {
                for (GroupMemberInfo x : members) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'members' is null");
                    }
                }
            }
            this.members = members;
            return this;
        }

        public Builder withGroupExternalId(String groupExternalId) {
            super.withGroupExternalId(groupExternalId);
            return this;
        }

        public Builder withMemberCount(Long memberCount) {
            super.withMemberCount(memberCount);
            return this;
        }

        public GroupFullInfo build() {
            return new GroupFullInfo(this.groupName, this.groupId, this.groupManagementType, this.created, this.groupExternalId, this.memberCount, this.members);
        }
    }

    static class Serializer extends StructSerializer<GroupFullInfo> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GroupFullInfo value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("group_name");
            StoneSerializers.string().serialize(value.groupName, g);
            g.writeFieldName("group_id");
            StoneSerializers.string().serialize(value.groupId, g);
            g.writeFieldName("group_management_type");
            com.dropbox.core.v2.teamcommon.GroupManagementType.Serializer.INSTANCE.serialize(value.groupManagementType, g);
            g.writeFieldName("created");
            StoneSerializers.uInt64().serialize(Long.valueOf(value.created), g);
            if (value.groupExternalId != null) {
                g.writeFieldName("group_external_id");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.groupExternalId, g);
            }
            if (value.memberCount != null) {
                g.writeFieldName("member_count");
                StoneSerializers.nullable(StoneSerializers.uInt32()).serialize(value.memberCount, g);
            }
            if (value.members != null) {
                g.writeFieldName("members");
                StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).serialize(value.members, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GroupFullInfo deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_groupName = null;
                String f_groupId = null;
                GroupManagementType f_groupManagementType = null;
                Long f_created = null;
                String f_groupExternalId = null;
                Long f_memberCount = null;
                List<GroupMemberInfo> f_members = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("group_name".equals(field)) {
                        f_groupName = (String) StoneSerializers.string().deserialize(p);
                    } else if ("group_id".equals(field)) {
                        f_groupId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("group_management_type".equals(field)) {
                        f_groupManagementType = com.dropbox.core.v2.teamcommon.GroupManagementType.Serializer.INSTANCE.deserialize(p);
                    } else if ("created".equals(field)) {
                        f_created = (Long) StoneSerializers.uInt64().deserialize(p);
                    } else if ("group_external_id".equals(field)) {
                        f_groupExternalId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("member_count".equals(field)) {
                        f_memberCount = (Long) StoneSerializers.nullable(StoneSerializers.uInt32()).deserialize(p);
                    } else if ("members".equals(field)) {
                        f_members = (List) StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).deserialize(p);
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
                } else if (f_created == null) {
                    throw new JsonParseException(p, "Required field \"created\" missing.");
                } else {
                    GroupFullInfo value = new GroupFullInfo(f_groupName, f_groupId, f_groupManagementType, f_created.longValue(), f_groupExternalId, f_memberCount, f_members);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GroupFullInfo(String groupName, String groupId, GroupManagementType groupManagementType, long created, String groupExternalId, Long memberCount, List<GroupMemberInfo> members) {
        super(groupName, groupId, groupManagementType, groupExternalId, memberCount);
        if (members != null) {
            for (GroupMemberInfo x : members) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'members' is null");
                }
            }
        }
        this.members = members;
        this.created = created;
    }

    public GroupFullInfo(String groupName, String groupId, GroupManagementType groupManagementType, long created) {
        this(groupName, groupId, groupManagementType, created, null, null, null);
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

    public long getCreated() {
        return this.created;
    }

    public String getGroupExternalId() {
        return this.groupExternalId;
    }

    public Long getMemberCount() {
        return this.memberCount;
    }

    public List<GroupMemberInfo> getMembers() {
        return this.members;
    }

    public static Builder newBuilder(String groupName, String groupId, GroupManagementType groupManagementType, long created) {
        return new Builder(groupName, groupId, groupManagementType, created);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.members, Long.valueOf(this.created)}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GroupFullInfo other = (GroupFullInfo) obj;
        if ((this.groupName == other.groupName || this.groupName.equals(other.groupName)) && ((this.groupId == other.groupId || this.groupId.equals(other.groupId)) && ((this.groupManagementType == other.groupManagementType || this.groupManagementType.equals(other.groupManagementType)) && this.created == other.created && ((this.groupExternalId == other.groupExternalId || (this.groupExternalId != null && this.groupExternalId.equals(other.groupExternalId))) && (this.memberCount == other.memberCount || (this.memberCount != null && this.memberCount.equals(other.memberCount))))))) {
            if (this.members == other.members) {
                return true;
            }
            if (this.members != null && this.members.equals(other.members)) {
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
