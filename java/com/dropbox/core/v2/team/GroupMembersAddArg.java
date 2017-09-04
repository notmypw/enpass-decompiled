package com.dropbox.core.v2.team;

import com.box.androidsdk.content.models.BoxGroup;
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
import java.util.List;

class GroupMembersAddArg extends IncludeMembersArg {
    protected final GroupSelector group;
    protected final List<MemberAccess> members;

    static class Serializer extends StructSerializer<GroupMembersAddArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GroupMembersAddArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxGroup.TYPE);
            Serializer.INSTANCE.serialize(value.group, g);
            g.writeFieldName("members");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.members, g);
            g.writeFieldName("return_members");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.returnMembers), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GroupMembersAddArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                GroupSelector f_group = null;
                List<MemberAccess> f_members = null;
                Boolean f_returnMembers = Boolean.valueOf(true);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxGroup.TYPE.equals(field)) {
                        f_group = Serializer.INSTANCE.deserialize(p);
                    } else if ("members".equals(field)) {
                        f_members = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else if ("return_members".equals(field)) {
                        f_returnMembers = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_group == null) {
                    throw new JsonParseException(p, "Required field \"group\" missing.");
                } else if (f_members == null) {
                    throw new JsonParseException(p, "Required field \"members\" missing.");
                } else {
                    GroupMembersAddArg value = new GroupMembersAddArg(f_group, f_members, f_returnMembers.booleanValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GroupMembersAddArg(GroupSelector group, List<MemberAccess> members, boolean returnMembers) {
        super(returnMembers);
        if (group == null) {
            throw new IllegalArgumentException("Required value for 'group' is null");
        }
        this.group = group;
        if (members == null) {
            throw new IllegalArgumentException("Required value for 'members' is null");
        }
        for (MemberAccess x : members) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'members' is null");
            }
        }
        this.members = members;
    }

    public GroupMembersAddArg(GroupSelector group, List<MemberAccess> members) {
        this(group, members, true);
    }

    public GroupSelector getGroup() {
        return this.group;
    }

    public List<MemberAccess> getMembers() {
        return this.members;
    }

    public boolean getReturnMembers() {
        return this.returnMembers;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.group, this.members}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GroupMembersAddArg other = (GroupMembersAddArg) obj;
        if ((this.group == other.group || this.group.equals(other.group)) && ((this.members == other.members || this.members.equals(other.members)) && this.returnMembers == other.returnMembers)) {
            return true;
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
