package com.dropbox.core.v2.team;

import com.box.androidsdk.content.models.BoxGroup;
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

class GroupUpdateArgs extends IncludeMembersArg {
    protected final GroupSelector group;
    protected final String newGroupExternalId;
    protected final GroupManagementType newGroupManagementType;
    protected final String newGroupName;

    public static class Builder {
        protected final GroupSelector group;
        protected String newGroupExternalId;
        protected GroupManagementType newGroupManagementType;
        protected String newGroupName;
        protected boolean returnMembers;

        protected Builder(GroupSelector group) {
            if (group == null) {
                throw new IllegalArgumentException("Required value for 'group' is null");
            }
            this.group = group;
            this.returnMembers = true;
            this.newGroupName = null;
            this.newGroupExternalId = null;
            this.newGroupManagementType = null;
        }

        public Builder withReturnMembers(Boolean returnMembers) {
            if (returnMembers != null) {
                this.returnMembers = returnMembers.booleanValue();
            } else {
                this.returnMembers = true;
            }
            return this;
        }

        public Builder withNewGroupName(String newGroupName) {
            this.newGroupName = newGroupName;
            return this;
        }

        public Builder withNewGroupExternalId(String newGroupExternalId) {
            this.newGroupExternalId = newGroupExternalId;
            return this;
        }

        public Builder withNewGroupManagementType(GroupManagementType newGroupManagementType) {
            this.newGroupManagementType = newGroupManagementType;
            return this;
        }

        public GroupUpdateArgs build() {
            return new GroupUpdateArgs(this.group, this.returnMembers, this.newGroupName, this.newGroupExternalId, this.newGroupManagementType);
        }
    }

    static class Serializer extends StructSerializer<GroupUpdateArgs> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GroupUpdateArgs value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxGroup.TYPE);
            Serializer.INSTANCE.serialize(value.group, g);
            g.writeFieldName("return_members");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.returnMembers), g);
            if (value.newGroupName != null) {
                g.writeFieldName("new_group_name");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.newGroupName, g);
            }
            if (value.newGroupExternalId != null) {
                g.writeFieldName("new_group_external_id");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.newGroupExternalId, g);
            }
            if (value.newGroupManagementType != null) {
                g.writeFieldName("new_group_management_type");
                StoneSerializers.nullable(com.dropbox.core.v2.teamcommon.GroupManagementType.Serializer.INSTANCE).serialize(value.newGroupManagementType, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GroupUpdateArgs deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                GroupSelector f_group = null;
                Boolean f_returnMembers = Boolean.valueOf(true);
                String f_newGroupName = null;
                String f_newGroupExternalId = null;
                GroupManagementType f_newGroupManagementType = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxGroup.TYPE.equals(field)) {
                        f_group = Serializer.INSTANCE.deserialize(p);
                    } else if ("return_members".equals(field)) {
                        f_returnMembers = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("new_group_name".equals(field)) {
                        f_newGroupName = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("new_group_external_id".equals(field)) {
                        f_newGroupExternalId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("new_group_management_type".equals(field)) {
                        f_newGroupManagementType = (GroupManagementType) StoneSerializers.nullable(com.dropbox.core.v2.teamcommon.GroupManagementType.Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_group == null) {
                    throw new JsonParseException(p, "Required field \"group\" missing.");
                }
                GroupUpdateArgs value = new GroupUpdateArgs(f_group, f_returnMembers.booleanValue(), f_newGroupName, f_newGroupExternalId, f_newGroupManagementType);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GroupUpdateArgs(GroupSelector group, boolean returnMembers, String newGroupName, String newGroupExternalId, GroupManagementType newGroupManagementType) {
        super(returnMembers);
        if (group == null) {
            throw new IllegalArgumentException("Required value for 'group' is null");
        }
        this.group = group;
        this.newGroupName = newGroupName;
        this.newGroupExternalId = newGroupExternalId;
        this.newGroupManagementType = newGroupManagementType;
    }

    public GroupUpdateArgs(GroupSelector group) {
        this(group, true, null, null, null);
    }

    public GroupSelector getGroup() {
        return this.group;
    }

    public boolean getReturnMembers() {
        return this.returnMembers;
    }

    public String getNewGroupName() {
        return this.newGroupName;
    }

    public String getNewGroupExternalId() {
        return this.newGroupExternalId;
    }

    public GroupManagementType getNewGroupManagementType() {
        return this.newGroupManagementType;
    }

    public static Builder newBuilder(GroupSelector group) {
        return new Builder(group);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.group, this.newGroupName, this.newGroupExternalId, this.newGroupManagementType}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GroupUpdateArgs other = (GroupUpdateArgs) obj;
        if ((this.group == other.group || this.group.equals(other.group)) && this.returnMembers == other.returnMembers && ((this.newGroupName == other.newGroupName || (this.newGroupName != null && this.newGroupName.equals(other.newGroupName))) && (this.newGroupExternalId == other.newGroupExternalId || (this.newGroupExternalId != null && this.newGroupExternalId.equals(other.newGroupExternalId))))) {
            if (this.newGroupManagementType == other.newGroupManagementType) {
                return true;
            }
            if (this.newGroupManagementType != null && this.newGroupManagementType.equals(other.newGroupManagementType)) {
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
