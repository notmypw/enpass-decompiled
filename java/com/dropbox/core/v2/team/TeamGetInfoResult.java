package com.dropbox.core.v2.team;

import com.box.androidsdk.content.models.BoxFileVersion;
import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.v2.teampolicies.TeamMemberPolicies;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;

public class TeamGetInfoResult {
    protected final String name;
    protected final long numLicensedUsers;
    protected final long numProvisionedUsers;
    protected final TeamMemberPolicies policies;
    protected final String teamId;

    static class Serializer extends StructSerializer<TeamGetInfoResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(TeamGetInfoResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxFileVersion.FIELD_NAME);
            StoneSerializers.string().serialize(value.name, g);
            g.writeFieldName("team_id");
            StoneSerializers.string().serialize(value.teamId, g);
            g.writeFieldName("num_licensed_users");
            StoneSerializers.uInt32().serialize(Long.valueOf(value.numLicensedUsers), g);
            g.writeFieldName("num_provisioned_users");
            StoneSerializers.uInt32().serialize(Long.valueOf(value.numProvisionedUsers), g);
            g.writeFieldName("policies");
            com.dropbox.core.v2.teampolicies.TeamMemberPolicies.Serializer.INSTANCE.serialize(value.policies, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public TeamGetInfoResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_name = null;
                String f_teamId = null;
                Long f_numLicensedUsers = null;
                Long f_numProvisionedUsers = null;
                TeamMemberPolicies f_policies = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxFileVersion.FIELD_NAME.equals(field)) {
                        f_name = (String) StoneSerializers.string().deserialize(p);
                    } else if ("team_id".equals(field)) {
                        f_teamId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("num_licensed_users".equals(field)) {
                        f_numLicensedUsers = (Long) StoneSerializers.uInt32().deserialize(p);
                    } else if ("num_provisioned_users".equals(field)) {
                        f_numProvisionedUsers = (Long) StoneSerializers.uInt32().deserialize(p);
                    } else if ("policies".equals(field)) {
                        f_policies = (TeamMemberPolicies) com.dropbox.core.v2.teampolicies.TeamMemberPolicies.Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_name == null) {
                    throw new JsonParseException(p, "Required field \"name\" missing.");
                } else if (f_teamId == null) {
                    throw new JsonParseException(p, "Required field \"team_id\" missing.");
                } else if (f_numLicensedUsers == null) {
                    throw new JsonParseException(p, "Required field \"num_licensed_users\" missing.");
                } else if (f_numProvisionedUsers == null) {
                    throw new JsonParseException(p, "Required field \"num_provisioned_users\" missing.");
                } else if (f_policies == null) {
                    throw new JsonParseException(p, "Required field \"policies\" missing.");
                } else {
                    TeamGetInfoResult value = new TeamGetInfoResult(f_name, f_teamId, f_numLicensedUsers.longValue(), f_numProvisionedUsers.longValue(), f_policies);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public TeamGetInfoResult(String name, String teamId, long numLicensedUsers, long numProvisionedUsers, TeamMemberPolicies policies) {
        if (name == null) {
            throw new IllegalArgumentException("Required value for 'name' is null");
        }
        this.name = name;
        if (teamId == null) {
            throw new IllegalArgumentException("Required value for 'teamId' is null");
        }
        this.teamId = teamId;
        this.numLicensedUsers = numLicensedUsers;
        this.numProvisionedUsers = numProvisionedUsers;
        if (policies == null) {
            throw new IllegalArgumentException("Required value for 'policies' is null");
        }
        this.policies = policies;
    }

    public String getName() {
        return this.name;
    }

    public String getTeamId() {
        return this.teamId;
    }

    public long getNumLicensedUsers() {
        return this.numLicensedUsers;
    }

    public long getNumProvisionedUsers() {
        return this.numProvisionedUsers;
    }

    public TeamMemberPolicies getPolicies() {
        return this.policies;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.name, this.teamId, Long.valueOf(this.numLicensedUsers), Long.valueOf(this.numProvisionedUsers), this.policies});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        TeamGetInfoResult other = (TeamGetInfoResult) obj;
        if ((this.name == other.name || this.name.equals(other.name)) && ((this.teamId == other.teamId || this.teamId.equals(other.teamId)) && this.numLicensedUsers == other.numLicensedUsers && this.numProvisionedUsers == other.numProvisionedUsers && (this.policies == other.policies || this.policies.equals(other.policies)))) {
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
