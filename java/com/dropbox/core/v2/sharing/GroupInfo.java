package com.dropbox.core.v2.sharing;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.v2.teamcommon.GroupManagementType;
import com.dropbox.core.v2.teamcommon.GroupSummary;
import com.dropbox.core.v2.teamcommon.GroupType;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;

public class GroupInfo extends GroupSummary {
    protected final GroupType groupType;
    protected final boolean isMember;
    protected final boolean isOwner;
    protected final boolean sameTeam;

    public static class Builder extends com.dropbox.core.v2.teamcommon.GroupSummary.Builder {
        protected final GroupType groupType;
        protected final boolean isMember;
        protected final boolean isOwner;
        protected final boolean sameTeam;

        protected Builder(String groupName, String groupId, GroupManagementType groupManagementType, GroupType groupType, boolean isMember, boolean isOwner, boolean sameTeam) {
            super(groupName, groupId, groupManagementType);
            if (groupType == null) {
                throw new IllegalArgumentException("Required value for 'groupType' is null");
            }
            this.groupType = groupType;
            this.isMember = isMember;
            this.isOwner = isOwner;
            this.sameTeam = sameTeam;
        }

        public Builder withGroupExternalId(String groupExternalId) {
            super.withGroupExternalId(groupExternalId);
            return this;
        }

        public Builder withMemberCount(Long memberCount) {
            super.withMemberCount(memberCount);
            return this;
        }

        public GroupInfo build() {
            return new GroupInfo(this.groupName, this.groupId, this.groupManagementType, this.groupType, this.isMember, this.isOwner, this.sameTeam, this.groupExternalId, this.memberCount);
        }
    }

    static class Serializer extends StructSerializer<GroupInfo> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GroupInfo value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("group_name");
            StoneSerializers.string().serialize(value.groupName, g);
            g.writeFieldName("group_id");
            StoneSerializers.string().serialize(value.groupId, g);
            g.writeFieldName("group_management_type");
            com.dropbox.core.v2.teamcommon.GroupManagementType.Serializer.INSTANCE.serialize(value.groupManagementType, g);
            g.writeFieldName("group_type");
            com.dropbox.core.v2.teamcommon.GroupType.Serializer.INSTANCE.serialize(value.groupType, g);
            g.writeFieldName("is_member");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.isMember), g);
            g.writeFieldName("is_owner");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.isOwner), g);
            g.writeFieldName("same_team");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.sameTeam), g);
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

        public GroupInfo deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_groupName = null;
                String f_groupId = null;
                GroupManagementType f_groupManagementType = null;
                GroupType f_groupType = null;
                Boolean f_isMember = null;
                Boolean f_isOwner = null;
                Boolean f_sameTeam = null;
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
                    } else if ("group_type".equals(field)) {
                        f_groupType = com.dropbox.core.v2.teamcommon.GroupType.Serializer.INSTANCE.deserialize(p);
                    } else if ("is_member".equals(field)) {
                        f_isMember = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("is_owner".equals(field)) {
                        f_isOwner = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("same_team".equals(field)) {
                        f_sameTeam = (Boolean) StoneSerializers.boolean_().deserialize(p);
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
                } else if (f_groupType == null) {
                    throw new JsonParseException(p, "Required field \"group_type\" missing.");
                } else if (f_isMember == null) {
                    throw new JsonParseException(p, "Required field \"is_member\" missing.");
                } else if (f_isOwner == null) {
                    throw new JsonParseException(p, "Required field \"is_owner\" missing.");
                } else if (f_sameTeam == null) {
                    throw new JsonParseException(p, "Required field \"same_team\" missing.");
                } else {
                    GroupInfo value = new GroupInfo(f_groupName, f_groupId, f_groupManagementType, f_groupType, f_isMember.booleanValue(), f_isOwner.booleanValue(), f_sameTeam.booleanValue(), f_groupExternalId, f_memberCount);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GroupInfo(String groupName, String groupId, GroupManagementType groupManagementType, GroupType groupType, boolean isMember, boolean isOwner, boolean sameTeam, String groupExternalId, Long memberCount) {
        super(groupName, groupId, groupManagementType, groupExternalId, memberCount);
        if (groupType == null) {
            throw new IllegalArgumentException("Required value for 'groupType' is null");
        }
        this.groupType = groupType;
        this.isMember = isMember;
        this.isOwner = isOwner;
        this.sameTeam = sameTeam;
    }

    public GroupInfo(String groupName, String groupId, GroupManagementType groupManagementType, GroupType groupType, boolean isMember, boolean isOwner, boolean sameTeam) {
        this(groupName, groupId, groupManagementType, groupType, isMember, isOwner, sameTeam, null, null);
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

    public GroupType getGroupType() {
        return this.groupType;
    }

    public boolean getIsMember() {
        return this.isMember;
    }

    public boolean getIsOwner() {
        return this.isOwner;
    }

    public boolean getSameTeam() {
        return this.sameTeam;
    }

    public String getGroupExternalId() {
        return this.groupExternalId;
    }

    public Long getMemberCount() {
        return this.memberCount;
    }

    public static Builder newBuilder(String groupName, String groupId, GroupManagementType groupManagementType, GroupType groupType, boolean isMember, boolean isOwner, boolean sameTeam) {
        return new Builder(groupName, groupId, groupManagementType, groupType, isMember, isOwner, sameTeam);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.groupType, Boolean.valueOf(this.isMember), Boolean.valueOf(this.isOwner), Boolean.valueOf(this.sameTeam)}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GroupInfo other = (GroupInfo) obj;
        if ((this.groupName == other.groupName || this.groupName.equals(other.groupName)) && ((this.groupId == other.groupId || this.groupId.equals(other.groupId)) && ((this.groupManagementType == other.groupManagementType || this.groupManagementType.equals(other.groupManagementType)) && ((this.groupType == other.groupType || this.groupType.equals(other.groupType)) && this.isMember == other.isMember && this.isOwner == other.isOwner && this.sameTeam == other.sameTeam && (this.groupExternalId == other.groupExternalId || (this.groupExternalId != null && this.groupExternalId.equals(other.groupExternalId))))))) {
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
