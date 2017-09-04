package com.dropbox.core.v2.sharing;

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

public class SharedFolderMembers {
    protected final String cursor;
    protected final List<GroupMembershipInfo> groups;
    protected final List<InviteeMembershipInfo> invitees;
    protected final List<UserMembershipInfo> users;

    static class Serializer extends StructSerializer<SharedFolderMembers> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(SharedFolderMembers value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("users");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.users, g);
            g.writeFieldName("groups");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.groups, g);
            g.writeFieldName("invitees");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.invitees, g);
            if (value.cursor != null) {
                g.writeFieldName("cursor");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.cursor, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public SharedFolderMembers deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<UserMembershipInfo> f_users = null;
                List<GroupMembershipInfo> f_groups = null;
                List<InviteeMembershipInfo> f_invitees = null;
                String f_cursor = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("users".equals(field)) {
                        f_users = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else if ("groups".equals(field)) {
                        f_groups = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else if ("invitees".equals(field)) {
                        f_invitees = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else if ("cursor".equals(field)) {
                        f_cursor = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_users == null) {
                    throw new JsonParseException(p, "Required field \"users\" missing.");
                } else if (f_groups == null) {
                    throw new JsonParseException(p, "Required field \"groups\" missing.");
                } else if (f_invitees == null) {
                    throw new JsonParseException(p, "Required field \"invitees\" missing.");
                } else {
                    SharedFolderMembers value = new SharedFolderMembers(f_users, f_groups, f_invitees, f_cursor);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public SharedFolderMembers(List<UserMembershipInfo> users, List<GroupMembershipInfo> groups, List<InviteeMembershipInfo> invitees, String cursor) {
        if (users == null) {
            throw new IllegalArgumentException("Required value for 'users' is null");
        }
        for (UserMembershipInfo x : users) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'users' is null");
            }
        }
        this.users = users;
        if (groups == null) {
            throw new IllegalArgumentException("Required value for 'groups' is null");
        }
        for (GroupMembershipInfo x2 : groups) {
            if (x2 == null) {
                throw new IllegalArgumentException("An item in list 'groups' is null");
            }
        }
        this.groups = groups;
        if (invitees == null) {
            throw new IllegalArgumentException("Required value for 'invitees' is null");
        }
        for (InviteeMembershipInfo x3 : invitees) {
            if (x3 == null) {
                throw new IllegalArgumentException("An item in list 'invitees' is null");
            }
        }
        this.invitees = invitees;
        this.cursor = cursor;
    }

    public SharedFolderMembers(List<UserMembershipInfo> users, List<GroupMembershipInfo> groups, List<InviteeMembershipInfo> invitees) {
        this(users, groups, invitees, null);
    }

    public List<UserMembershipInfo> getUsers() {
        return this.users;
    }

    public List<GroupMembershipInfo> getGroups() {
        return this.groups;
    }

    public List<InviteeMembershipInfo> getInvitees() {
        return this.invitees;
    }

    public String getCursor() {
        return this.cursor;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.users, this.groups, this.invitees, this.cursor});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        SharedFolderMembers other = (SharedFolderMembers) obj;
        if ((this.users == other.users || this.users.equals(other.users)) && ((this.groups == other.groups || this.groups.equals(other.groups)) && (this.invitees == other.invitees || this.invitees.equals(other.invitees)))) {
            if (this.cursor == other.cursor) {
                return true;
            }
            if (this.cursor != null && this.cursor.equals(other.cursor)) {
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
