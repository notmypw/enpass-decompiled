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

class GroupMembersRemoveArg extends IncludeMembersArg {
    protected final GroupSelector group;
    protected final List<UserSelectorArg> users;

    static class Serializer extends StructSerializer<GroupMembersRemoveArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GroupMembersRemoveArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxGroup.TYPE);
            Serializer.INSTANCE.serialize(value.group, g);
            g.writeFieldName("users");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.users, g);
            g.writeFieldName("return_members");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.returnMembers), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GroupMembersRemoveArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                GroupSelector f_group = null;
                List<UserSelectorArg> f_users = null;
                Boolean f_returnMembers = Boolean.valueOf(true);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxGroup.TYPE.equals(field)) {
                        f_group = Serializer.INSTANCE.deserialize(p);
                    } else if ("users".equals(field)) {
                        f_users = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else if ("return_members".equals(field)) {
                        f_returnMembers = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_group == null) {
                    throw new JsonParseException(p, "Required field \"group\" missing.");
                } else if (f_users == null) {
                    throw new JsonParseException(p, "Required field \"users\" missing.");
                } else {
                    GroupMembersRemoveArg value = new GroupMembersRemoveArg(f_group, f_users, f_returnMembers.booleanValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GroupMembersRemoveArg(GroupSelector group, List<UserSelectorArg> users, boolean returnMembers) {
        super(returnMembers);
        if (group == null) {
            throw new IllegalArgumentException("Required value for 'group' is null");
        }
        this.group = group;
        if (users == null) {
            throw new IllegalArgumentException("Required value for 'users' is null");
        }
        for (UserSelectorArg x : users) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'users' is null");
            }
        }
        this.users = users;
    }

    public GroupMembersRemoveArg(GroupSelector group, List<UserSelectorArg> users) {
        this(group, users, true);
    }

    public GroupSelector getGroup() {
        return this.group;
    }

    public List<UserSelectorArg> getUsers() {
        return this.users;
    }

    public boolean getReturnMembers() {
        return this.returnMembers;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.group, this.users}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GroupMembersRemoveArg other = (GroupMembersRemoveArg) obj;
        if ((this.group == other.group || this.group.equals(other.group)) && ((this.users == other.users || this.users.equals(other.users)) && this.returnMembers == other.returnMembers)) {
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
