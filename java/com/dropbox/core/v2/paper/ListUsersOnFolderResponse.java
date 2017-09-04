package com.dropbox.core.v2.paper;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.v2.sharing.InviteeInfo;
import com.dropbox.core.v2.sharing.UserInfo;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ListUsersOnFolderResponse {
    protected final Cursor cursor;
    protected final boolean hasMore;
    protected final List<InviteeInfo> invitees;
    protected final List<UserInfo> users;

    static class Serializer extends StructSerializer<ListUsersOnFolderResponse> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListUsersOnFolderResponse value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("invitees");
            StoneSerializers.list(com.dropbox.core.v2.sharing.InviteeInfo.Serializer.INSTANCE).serialize(value.invitees, g);
            g.writeFieldName("users");
            StoneSerializers.list(com.dropbox.core.v2.sharing.UserInfo.Serializer.INSTANCE).serialize(value.users, g);
            g.writeFieldName("cursor");
            Serializer.INSTANCE.serialize(value.cursor, g);
            g.writeFieldName("has_more");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.hasMore), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListUsersOnFolderResponse deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<InviteeInfo> f_invitees = null;
                List<UserInfo> f_users = null;
                Cursor f_cursor = null;
                Boolean f_hasMore = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("invitees".equals(field)) {
                        f_invitees = (List) StoneSerializers.list(com.dropbox.core.v2.sharing.InviteeInfo.Serializer.INSTANCE).deserialize(p);
                    } else if ("users".equals(field)) {
                        f_users = (List) StoneSerializers.list(com.dropbox.core.v2.sharing.UserInfo.Serializer.INSTANCE).deserialize(p);
                    } else if ("cursor".equals(field)) {
                        f_cursor = (Cursor) Serializer.INSTANCE.deserialize(p);
                    } else if ("has_more".equals(field)) {
                        f_hasMore = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_invitees == null) {
                    throw new JsonParseException(p, "Required field \"invitees\" missing.");
                } else if (f_users == null) {
                    throw new JsonParseException(p, "Required field \"users\" missing.");
                } else if (f_cursor == null) {
                    throw new JsonParseException(p, "Required field \"cursor\" missing.");
                } else if (f_hasMore == null) {
                    throw new JsonParseException(p, "Required field \"has_more\" missing.");
                } else {
                    ListUsersOnFolderResponse value = new ListUsersOnFolderResponse(f_invitees, f_users, f_cursor, f_hasMore.booleanValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListUsersOnFolderResponse(List<InviteeInfo> invitees, List<UserInfo> users, Cursor cursor, boolean hasMore) {
        if (invitees == null) {
            throw new IllegalArgumentException("Required value for 'invitees' is null");
        }
        for (InviteeInfo x : invitees) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'invitees' is null");
            }
        }
        this.invitees = invitees;
        if (users == null) {
            throw new IllegalArgumentException("Required value for 'users' is null");
        }
        for (UserInfo x2 : users) {
            if (x2 == null) {
                throw new IllegalArgumentException("An item in list 'users' is null");
            }
        }
        this.users = users;
        if (cursor == null) {
            throw new IllegalArgumentException("Required value for 'cursor' is null");
        }
        this.cursor = cursor;
        this.hasMore = hasMore;
    }

    public List<InviteeInfo> getInvitees() {
        return this.invitees;
    }

    public List<UserInfo> getUsers() {
        return this.users;
    }

    public Cursor getCursor() {
        return this.cursor;
    }

    public boolean getHasMore() {
        return this.hasMore;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.invitees, this.users, this.cursor, Boolean.valueOf(this.hasMore)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListUsersOnFolderResponse other = (ListUsersOnFolderResponse) obj;
        if ((this.invitees == other.invitees || this.invitees.equals(other.invitees)) && ((this.users == other.users || this.users.equals(other.users)) && ((this.cursor == other.cursor || this.cursor.equals(other.cursor)) && this.hasMore == other.hasMore))) {
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
