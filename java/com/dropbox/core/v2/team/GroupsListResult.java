package com.dropbox.core.v2.team;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.v2.teamcommon.GroupSummary;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GroupsListResult {
    protected final String cursor;
    protected final List<GroupSummary> groups;
    protected final boolean hasMore;

    static class Serializer extends StructSerializer<GroupsListResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GroupsListResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("groups");
            StoneSerializers.list(com.dropbox.core.v2.teamcommon.GroupSummary.Serializer.INSTANCE).serialize(value.groups, g);
            g.writeFieldName("cursor");
            StoneSerializers.string().serialize(value.cursor, g);
            g.writeFieldName("has_more");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.hasMore), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GroupsListResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<GroupSummary> f_groups = null;
                String f_cursor = null;
                Boolean f_hasMore = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("groups".equals(field)) {
                        f_groups = (List) StoneSerializers.list(com.dropbox.core.v2.teamcommon.GroupSummary.Serializer.INSTANCE).deserialize(p);
                    } else if ("cursor".equals(field)) {
                        f_cursor = (String) StoneSerializers.string().deserialize(p);
                    } else if ("has_more".equals(field)) {
                        f_hasMore = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_groups == null) {
                    throw new JsonParseException(p, "Required field \"groups\" missing.");
                } else if (f_cursor == null) {
                    throw new JsonParseException(p, "Required field \"cursor\" missing.");
                } else if (f_hasMore == null) {
                    throw new JsonParseException(p, "Required field \"has_more\" missing.");
                } else {
                    GroupsListResult value = new GroupsListResult(f_groups, f_cursor, f_hasMore.booleanValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GroupsListResult(List<GroupSummary> groups, String cursor, boolean hasMore) {
        if (groups == null) {
            throw new IllegalArgumentException("Required value for 'groups' is null");
        }
        for (GroupSummary x : groups) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'groups' is null");
            }
        }
        this.groups = groups;
        if (cursor == null) {
            throw new IllegalArgumentException("Required value for 'cursor' is null");
        }
        this.cursor = cursor;
        this.hasMore = hasMore;
    }

    public List<GroupSummary> getGroups() {
        return this.groups;
    }

    public String getCursor() {
        return this.cursor;
    }

    public boolean getHasMore() {
        return this.hasMore;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.groups, this.cursor, Boolean.valueOf(this.hasMore)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GroupsListResult other = (GroupsListResult) obj;
        if ((this.groups == other.groups || this.groups.equals(other.groups)) && ((this.cursor == other.cursor || this.cursor.equals(other.cursor)) && this.hasMore == other.hasMore)) {
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
