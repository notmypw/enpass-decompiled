package com.dropbox.core.v2.team;

import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.models.BoxUploadEmail;
import com.box.androidsdk.content.models.BoxUser;
import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.v2.users.Name;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TeamMemberProfile extends MemberProfile {
    protected final List<String> groups;

    public static class Builder extends com.dropbox.core.v2.team.MemberProfile.Builder {
        protected final List<String> groups;

        protected Builder(String teamMemberId, String email, boolean emailVerified, TeamMemberStatus status, Name name, TeamMembershipType membershipType, List<String> groups) {
            super(teamMemberId, email, emailVerified, status, name, membershipType);
            if (groups == null) {
                throw new IllegalArgumentException("Required value for 'groups' is null");
            }
            for (String x : groups) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'groups' is null");
                }
            }
            this.groups = groups;
        }

        public Builder withExternalId(String externalId) {
            super.withExternalId(externalId);
            return this;
        }

        public Builder withAccountId(String accountId) {
            super.withAccountId(accountId);
            return this;
        }

        public Builder withJoinedOn(Date joinedOn) {
            super.withJoinedOn(joinedOn);
            return this;
        }

        public Builder withPersistentId(String persistentId) {
            super.withPersistentId(persistentId);
            return this;
        }

        public TeamMemberProfile build() {
            return new TeamMemberProfile(this.teamMemberId, this.email, this.emailVerified, this.status, this.name, this.membershipType, this.groups, this.externalId, this.accountId, this.joinedOn, this.persistentId);
        }
    }

    static class Serializer extends StructSerializer<TeamMemberProfile> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(TeamMemberProfile value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("team_member_id");
            StoneSerializers.string().serialize(value.teamMemberId, g);
            g.writeFieldName(BoxUploadEmail.FIELD_EMAIL);
            StoneSerializers.string().serialize(value.email, g);
            g.writeFieldName("email_verified");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.emailVerified), g);
            g.writeFieldName(BoxUser.FIELD_STATUS);
            Serializer.INSTANCE.serialize(value.status, g);
            g.writeFieldName(BoxFileVersion.FIELD_NAME);
            com.dropbox.core.v2.users.Name.Serializer.INSTANCE.serialize(value.name, g);
            g.writeFieldName("membership_type");
            Serializer.INSTANCE.serialize(value.membershipType, g);
            g.writeFieldName("groups");
            StoneSerializers.list(StoneSerializers.string()).serialize(value.groups, g);
            if (value.externalId != null) {
                g.writeFieldName("external_id");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.externalId, g);
            }
            if (value.accountId != null) {
                g.writeFieldName("account_id");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.accountId, g);
            }
            if (value.joinedOn != null) {
                g.writeFieldName("joined_on");
                StoneSerializers.nullable(StoneSerializers.timestamp()).serialize(value.joinedOn, g);
            }
            if (value.persistentId != null) {
                g.writeFieldName("persistent_id");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.persistentId, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public TeamMemberProfile deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_teamMemberId = null;
                String f_email = null;
                Boolean f_emailVerified = null;
                TeamMemberStatus f_status = null;
                Name f_name = null;
                TeamMembershipType f_membershipType = null;
                List<String> f_groups = null;
                String f_externalId = null;
                String f_accountId = null;
                Date f_joinedOn = null;
                String f_persistentId = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("team_member_id".equals(field)) {
                        f_teamMemberId = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxUploadEmail.FIELD_EMAIL.equals(field)) {
                        f_email = (String) StoneSerializers.string().deserialize(p);
                    } else if ("email_verified".equals(field)) {
                        f_emailVerified = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if (BoxUser.FIELD_STATUS.equals(field)) {
                        f_status = Serializer.INSTANCE.deserialize(p);
                    } else if (BoxFileVersion.FIELD_NAME.equals(field)) {
                        f_name = (Name) com.dropbox.core.v2.users.Name.Serializer.INSTANCE.deserialize(p);
                    } else if ("membership_type".equals(field)) {
                        f_membershipType = Serializer.INSTANCE.deserialize(p);
                    } else if ("groups".equals(field)) {
                        f_groups = (List) StoneSerializers.list(StoneSerializers.string()).deserialize(p);
                    } else if ("external_id".equals(field)) {
                        f_externalId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("account_id".equals(field)) {
                        f_accountId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("joined_on".equals(field)) {
                        f_joinedOn = (Date) StoneSerializers.nullable(StoneSerializers.timestamp()).deserialize(p);
                    } else if ("persistent_id".equals(field)) {
                        f_persistentId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_teamMemberId == null) {
                    throw new JsonParseException(p, "Required field \"team_member_id\" missing.");
                } else if (f_email == null) {
                    throw new JsonParseException(p, "Required field \"email\" missing.");
                } else if (f_emailVerified == null) {
                    throw new JsonParseException(p, "Required field \"email_verified\" missing.");
                } else if (f_status == null) {
                    throw new JsonParseException(p, "Required field \"status\" missing.");
                } else if (f_name == null) {
                    throw new JsonParseException(p, "Required field \"name\" missing.");
                } else if (f_membershipType == null) {
                    throw new JsonParseException(p, "Required field \"membership_type\" missing.");
                } else if (f_groups == null) {
                    throw new JsonParseException(p, "Required field \"groups\" missing.");
                } else {
                    TeamMemberProfile value = new TeamMemberProfile(f_teamMemberId, f_email, f_emailVerified.booleanValue(), f_status, f_name, f_membershipType, f_groups, f_externalId, f_accountId, f_joinedOn, f_persistentId);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public TeamMemberProfile(String teamMemberId, String email, boolean emailVerified, TeamMemberStatus status, Name name, TeamMembershipType membershipType, List<String> groups, String externalId, String accountId, Date joinedOn, String persistentId) {
        super(teamMemberId, email, emailVerified, status, name, membershipType, externalId, accountId, joinedOn, persistentId);
        if (groups == null) {
            throw new IllegalArgumentException("Required value for 'groups' is null");
        }
        for (String x : groups) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'groups' is null");
            }
        }
        this.groups = groups;
    }

    public TeamMemberProfile(String teamMemberId, String email, boolean emailVerified, TeamMemberStatus status, Name name, TeamMembershipType membershipType, List<String> groups) {
        this(teamMemberId, email, emailVerified, status, name, membershipType, groups, null, null, null, null);
    }

    public String getTeamMemberId() {
        return this.teamMemberId;
    }

    public String getEmail() {
        return this.email;
    }

    public boolean getEmailVerified() {
        return this.emailVerified;
    }

    public TeamMemberStatus getStatus() {
        return this.status;
    }

    public Name getName() {
        return this.name;
    }

    public TeamMembershipType getMembershipType() {
        return this.membershipType;
    }

    public List<String> getGroups() {
        return this.groups;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public Date getJoinedOn() {
        return this.joinedOn;
    }

    public String getPersistentId() {
        return this.persistentId;
    }

    public static Builder newBuilder(String teamMemberId, String email, boolean emailVerified, TeamMemberStatus status, Name name, TeamMembershipType membershipType, List<String> groups) {
        return new Builder(teamMemberId, email, emailVerified, status, name, membershipType, groups);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.groups}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        TeamMemberProfile other = (TeamMemberProfile) obj;
        if ((this.teamMemberId == other.teamMemberId || this.teamMemberId.equals(other.teamMemberId)) && ((this.email == other.email || this.email.equals(other.email)) && this.emailVerified == other.emailVerified && ((this.status == other.status || this.status.equals(other.status)) && ((this.name == other.name || this.name.equals(other.name)) && ((this.membershipType == other.membershipType || this.membershipType.equals(other.membershipType)) && ((this.groups == other.groups || this.groups.equals(other.groups)) && ((this.externalId == other.externalId || (this.externalId != null && this.externalId.equals(other.externalId))) && ((this.accountId == other.accountId || (this.accountId != null && this.accountId.equals(other.accountId))) && (this.joinedOn == other.joinedOn || (this.joinedOn != null && this.joinedOn.equals(other.joinedOn))))))))))) {
            if (this.persistentId == other.persistentId) {
                return true;
            }
            if (this.persistentId != null && this.persistentId.equals(other.persistentId)) {
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
