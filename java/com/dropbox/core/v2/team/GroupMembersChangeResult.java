package com.dropbox.core.v2.team;

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

public class GroupMembersChangeResult {
    protected final String asyncJobId;
    protected final GroupFullInfo groupInfo;

    static class Serializer extends StructSerializer<GroupMembersChangeResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GroupMembersChangeResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("group_info");
            Serializer.INSTANCE.serialize(value.groupInfo, g);
            g.writeFieldName("async_job_id");
            StoneSerializers.string().serialize(value.asyncJobId, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GroupMembersChangeResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                GroupFullInfo f_groupInfo = null;
                String f_asyncJobId = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("group_info".equals(field)) {
                        f_groupInfo = (GroupFullInfo) Serializer.INSTANCE.deserialize(p);
                    } else if ("async_job_id".equals(field)) {
                        f_asyncJobId = (String) StoneSerializers.string().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_groupInfo == null) {
                    throw new JsonParseException(p, "Required field \"group_info\" missing.");
                } else if (f_asyncJobId == null) {
                    throw new JsonParseException(p, "Required field \"async_job_id\" missing.");
                } else {
                    GroupMembersChangeResult value = new GroupMembersChangeResult(f_groupInfo, f_asyncJobId);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GroupMembersChangeResult(GroupFullInfo groupInfo, String asyncJobId) {
        if (groupInfo == null) {
            throw new IllegalArgumentException("Required value for 'groupInfo' is null");
        }
        this.groupInfo = groupInfo;
        if (asyncJobId == null) {
            throw new IllegalArgumentException("Required value for 'asyncJobId' is null");
        } else if (asyncJobId.length() < 1) {
            throw new IllegalArgumentException("String 'asyncJobId' is shorter than 1");
        } else {
            this.asyncJobId = asyncJobId;
        }
    }

    public GroupFullInfo getGroupInfo() {
        return this.groupInfo;
    }

    public String getAsyncJobId() {
        return this.asyncJobId;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.groupInfo, this.asyncJobId});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GroupMembersChangeResult other = (GroupMembersChangeResult) obj;
        if ((this.groupInfo == other.groupInfo || this.groupInfo.equals(other.groupInfo)) && (this.asyncJobId == other.asyncJobId || this.asyncJobId.equals(other.asyncJobId))) {
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
