package com.dropbox.core.v2.team;

import com.box.androidsdk.content.models.BoxGroup;
import com.box.androidsdk.content.models.BoxUser;
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

class GroupMembersSetAccessTypeArg extends GroupMemberSelector {
    protected final GroupAccessType accessType;
    protected final boolean returnMembers;

    static class Serializer extends StructSerializer<GroupMembersSetAccessTypeArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GroupMembersSetAccessTypeArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxGroup.TYPE);
            Serializer.INSTANCE.serialize(value.group, g);
            g.writeFieldName(BoxUser.TYPE);
            Serializer.INSTANCE.serialize(value.user, g);
            g.writeFieldName("access_type");
            Serializer.INSTANCE.serialize(value.accessType, g);
            g.writeFieldName("return_members");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.returnMembers), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GroupMembersSetAccessTypeArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                GroupSelector f_group = null;
                UserSelectorArg f_user = null;
                GroupAccessType f_accessType = null;
                Boolean f_returnMembers = Boolean.valueOf(true);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxGroup.TYPE.equals(field)) {
                        f_group = Serializer.INSTANCE.deserialize(p);
                    } else if (BoxUser.TYPE.equals(field)) {
                        f_user = Serializer.INSTANCE.deserialize(p);
                    } else if ("access_type".equals(field)) {
                        f_accessType = Serializer.INSTANCE.deserialize(p);
                    } else if ("return_members".equals(field)) {
                        f_returnMembers = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_group == null) {
                    throw new JsonParseException(p, "Required field \"group\" missing.");
                } else if (f_user == null) {
                    throw new JsonParseException(p, "Required field \"user\" missing.");
                } else if (f_accessType == null) {
                    throw new JsonParseException(p, "Required field \"access_type\" missing.");
                } else {
                    GroupMembersSetAccessTypeArg value = new GroupMembersSetAccessTypeArg(f_group, f_user, f_accessType, f_returnMembers.booleanValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GroupMembersSetAccessTypeArg(GroupSelector group, UserSelectorArg user, GroupAccessType accessType, boolean returnMembers) {
        super(group, user);
        if (accessType == null) {
            throw new IllegalArgumentException("Required value for 'accessType' is null");
        }
        this.accessType = accessType;
        this.returnMembers = returnMembers;
    }

    public GroupMembersSetAccessTypeArg(GroupSelector group, UserSelectorArg user, GroupAccessType accessType) {
        this(group, user, accessType, true);
    }

    public GroupSelector getGroup() {
        return this.group;
    }

    public UserSelectorArg getUser() {
        return this.user;
    }

    public GroupAccessType getAccessType() {
        return this.accessType;
    }

    public boolean getReturnMembers() {
        return this.returnMembers;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.accessType, Boolean.valueOf(this.returnMembers)}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GroupMembersSetAccessTypeArg other = (GroupMembersSetAccessTypeArg) obj;
        if ((this.group == other.group || this.group.equals(other.group)) && ((this.user == other.user || this.user.equals(other.user)) && ((this.accessType == other.accessType || this.accessType.equals(other.accessType)) && this.returnMembers == other.returnMembers))) {
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
