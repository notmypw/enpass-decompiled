package com.dropbox.core.v2.teampolicies;

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

public class TeamSharingPolicies {
    protected final SharedFolderJoinPolicy sharedFolderJoinPolicy;
    protected final SharedFolderMemberPolicy sharedFolderMemberPolicy;
    protected final SharedLinkCreatePolicy sharedLinkCreatePolicy;

    public static class Serializer extends StructSerializer<TeamSharingPolicies> {
        public static final Serializer INSTANCE = new Serializer();

        public void serialize(TeamSharingPolicies value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("shared_folder_member_policy");
            Serializer.INSTANCE.serialize(value.sharedFolderMemberPolicy, g);
            g.writeFieldName("shared_folder_join_policy");
            Serializer.INSTANCE.serialize(value.sharedFolderJoinPolicy, g);
            g.writeFieldName("shared_link_create_policy");
            Serializer.INSTANCE.serialize(value.sharedLinkCreatePolicy, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public TeamSharingPolicies deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                SharedFolderMemberPolicy f_sharedFolderMemberPolicy = null;
                SharedFolderJoinPolicy f_sharedFolderJoinPolicy = null;
                SharedLinkCreatePolicy f_sharedLinkCreatePolicy = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("shared_folder_member_policy".equals(field)) {
                        f_sharedFolderMemberPolicy = Serializer.INSTANCE.deserialize(p);
                    } else if ("shared_folder_join_policy".equals(field)) {
                        f_sharedFolderJoinPolicy = Serializer.INSTANCE.deserialize(p);
                    } else if ("shared_link_create_policy".equals(field)) {
                        f_sharedLinkCreatePolicy = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_sharedFolderMemberPolicy == null) {
                    throw new JsonParseException(p, "Required field \"shared_folder_member_policy\" missing.");
                } else if (f_sharedFolderJoinPolicy == null) {
                    throw new JsonParseException(p, "Required field \"shared_folder_join_policy\" missing.");
                } else if (f_sharedLinkCreatePolicy == null) {
                    throw new JsonParseException(p, "Required field \"shared_link_create_policy\" missing.");
                } else {
                    TeamSharingPolicies value = new TeamSharingPolicies(f_sharedFolderMemberPolicy, f_sharedFolderJoinPolicy, f_sharedLinkCreatePolicy);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public TeamSharingPolicies(SharedFolderMemberPolicy sharedFolderMemberPolicy, SharedFolderJoinPolicy sharedFolderJoinPolicy, SharedLinkCreatePolicy sharedLinkCreatePolicy) {
        if (sharedFolderMemberPolicy == null) {
            throw new IllegalArgumentException("Required value for 'sharedFolderMemberPolicy' is null");
        }
        this.sharedFolderMemberPolicy = sharedFolderMemberPolicy;
        if (sharedFolderJoinPolicy == null) {
            throw new IllegalArgumentException("Required value for 'sharedFolderJoinPolicy' is null");
        }
        this.sharedFolderJoinPolicy = sharedFolderJoinPolicy;
        if (sharedLinkCreatePolicy == null) {
            throw new IllegalArgumentException("Required value for 'sharedLinkCreatePolicy' is null");
        }
        this.sharedLinkCreatePolicy = sharedLinkCreatePolicy;
    }

    public SharedFolderMemberPolicy getSharedFolderMemberPolicy() {
        return this.sharedFolderMemberPolicy;
    }

    public SharedFolderJoinPolicy getSharedFolderJoinPolicy() {
        return this.sharedFolderJoinPolicy;
    }

    public SharedLinkCreatePolicy getSharedLinkCreatePolicy() {
        return this.sharedLinkCreatePolicy;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.sharedFolderMemberPolicy, this.sharedFolderJoinPolicy, this.sharedLinkCreatePolicy});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        TeamSharingPolicies other = (TeamSharingPolicies) obj;
        if ((this.sharedFolderMemberPolicy == other.sharedFolderMemberPolicy || this.sharedFolderMemberPolicy.equals(other.sharedFolderMemberPolicy)) && ((this.sharedFolderJoinPolicy == other.sharedFolderJoinPolicy || this.sharedFolderJoinPolicy.equals(other.sharedFolderJoinPolicy)) && (this.sharedLinkCreatePolicy == other.sharedLinkCreatePolicy || this.sharedLinkCreatePolicy.equals(other.sharedLinkCreatePolicy)))) {
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
