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

public class ListFileMembersCountResult {
    protected final long memberCount;
    protected final SharedFileMembers members;

    static class Serializer extends StructSerializer<ListFileMembersCountResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListFileMembersCountResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("members");
            Serializer.INSTANCE.serialize(value.members, g);
            g.writeFieldName("member_count");
            StoneSerializers.uInt32().serialize(Long.valueOf(value.memberCount), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListFileMembersCountResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                SharedFileMembers f_members = null;
                Long f_memberCount = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("members".equals(field)) {
                        f_members = (SharedFileMembers) Serializer.INSTANCE.deserialize(p);
                    } else if ("member_count".equals(field)) {
                        f_memberCount = (Long) StoneSerializers.uInt32().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_members == null) {
                    throw new JsonParseException(p, "Required field \"members\" missing.");
                } else if (f_memberCount == null) {
                    throw new JsonParseException(p, "Required field \"member_count\" missing.");
                } else {
                    ListFileMembersCountResult value = new ListFileMembersCountResult(f_members, f_memberCount.longValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListFileMembersCountResult(SharedFileMembers members, long memberCount) {
        if (members == null) {
            throw new IllegalArgumentException("Required value for 'members' is null");
        }
        this.members = members;
        this.memberCount = memberCount;
    }

    public SharedFileMembers getMembers() {
        return this.members;
    }

    public long getMemberCount() {
        return this.memberCount;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.members, Long.valueOf(this.memberCount)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListFileMembersCountResult other = (ListFileMembersCountResult) obj;
        if ((this.members == other.members || this.members.equals(other.members)) && this.memberCount == other.memberCount) {
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
