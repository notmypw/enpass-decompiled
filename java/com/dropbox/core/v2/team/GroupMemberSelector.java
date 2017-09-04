package com.dropbox.core.v2.team;

import com.box.androidsdk.content.models.BoxGroup;
import com.box.androidsdk.content.models.BoxUser;
import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StructSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;

class GroupMemberSelector {
    protected final GroupSelector group;
    protected final UserSelectorArg user;

    private static class Serializer extends StructSerializer<GroupMemberSelector> {
        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {
        }

        public void serialize(GroupMemberSelector value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxGroup.TYPE);
            Serializer.INSTANCE.serialize(value.group, g);
            g.writeFieldName(BoxUser.TYPE);
            Serializer.INSTANCE.serialize(value.user, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GroupMemberSelector deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                GroupSelector f_group = null;
                UserSelectorArg f_user = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxGroup.TYPE.equals(field)) {
                        f_group = Serializer.INSTANCE.deserialize(p);
                    } else if (BoxUser.TYPE.equals(field)) {
                        f_user = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_group == null) {
                    throw new JsonParseException(p, "Required field \"group\" missing.");
                } else if (f_user == null) {
                    throw new JsonParseException(p, "Required field \"user\" missing.");
                } else {
                    GroupMemberSelector value = new GroupMemberSelector(f_group, f_user);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GroupMemberSelector(GroupSelector group, UserSelectorArg user) {
        if (group == null) {
            throw new IllegalArgumentException("Required value for 'group' is null");
        }
        this.group = group;
        if (user == null) {
            throw new IllegalArgumentException("Required value for 'user' is null");
        }
        this.user = user;
    }

    public GroupSelector getGroup() {
        return this.group;
    }

    public UserSelectorArg getUser() {
        return this.user;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.group, this.user});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GroupMemberSelector other = (GroupMemberSelector) obj;
        if ((this.group == other.group || this.group.equals(other.group)) && (this.user == other.user || this.user.equals(other.user))) {
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
