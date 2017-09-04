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
import java.util.List;

public class GetMembershipReport extends BaseDfbReport {
    protected final List<Long> licenses;
    protected final List<Long> membersJoined;
    protected final List<Long> pendingInvites;
    protected final List<Long> suspendedMembers;
    protected final List<Long> teamSize;

    static class Serializer extends StructSerializer<GetMembershipReport> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GetMembershipReport value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("start_date");
            StoneSerializers.string().serialize(value.startDate, g);
            g.writeFieldName("team_size");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.teamSize, g);
            g.writeFieldName("pending_invites");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.pendingInvites, g);
            g.writeFieldName("members_joined");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.membersJoined, g);
            g.writeFieldName("suspended_members");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.suspendedMembers, g);
            g.writeFieldName("licenses");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.licenses, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GetMembershipReport deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_startDate = null;
                List<Long> f_teamSize = null;
                List<Long> f_pendingInvites = null;
                List<Long> f_membersJoined = null;
                List<Long> f_suspendedMembers = null;
                List<Long> f_licenses = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("start_date".equals(field)) {
                        f_startDate = (String) StoneSerializers.string().deserialize(p);
                    } else if ("team_size".equals(field)) {
                        f_teamSize = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("pending_invites".equals(field)) {
                        f_pendingInvites = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("members_joined".equals(field)) {
                        f_membersJoined = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("suspended_members".equals(field)) {
                        f_suspendedMembers = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("licenses".equals(field)) {
                        f_licenses = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_startDate == null) {
                    throw new JsonParseException(p, "Required field \"start_date\" missing.");
                } else if (f_teamSize == null) {
                    throw new JsonParseException(p, "Required field \"team_size\" missing.");
                } else if (f_pendingInvites == null) {
                    throw new JsonParseException(p, "Required field \"pending_invites\" missing.");
                } else if (f_membersJoined == null) {
                    throw new JsonParseException(p, "Required field \"members_joined\" missing.");
                } else if (f_suspendedMembers == null) {
                    throw new JsonParseException(p, "Required field \"suspended_members\" missing.");
                } else if (f_licenses == null) {
                    throw new JsonParseException(p, "Required field \"licenses\" missing.");
                } else {
                    GetMembershipReport value = new GetMembershipReport(f_startDate, f_teamSize, f_pendingInvites, f_membersJoined, f_suspendedMembers, f_licenses);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GetMembershipReport(String startDate, List<Long> teamSize, List<Long> pendingInvites, List<Long> membersJoined, List<Long> suspendedMembers, List<Long> licenses) {
        super(startDate);
        if (teamSize == null) {
            throw new IllegalArgumentException("Required value for 'teamSize' is null");
        }
        for (Long x : teamSize) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'teamSize' is null");
            }
        }
        this.teamSize = teamSize;
        if (pendingInvites == null) {
            throw new IllegalArgumentException("Required value for 'pendingInvites' is null");
        }
        for (Long x2 : pendingInvites) {
            if (x2 == null) {
                throw new IllegalArgumentException("An item in list 'pendingInvites' is null");
            }
        }
        this.pendingInvites = pendingInvites;
        if (membersJoined == null) {
            throw new IllegalArgumentException("Required value for 'membersJoined' is null");
        }
        for (Long x22 : membersJoined) {
            if (x22 == null) {
                throw new IllegalArgumentException("An item in list 'membersJoined' is null");
            }
        }
        this.membersJoined = membersJoined;
        if (suspendedMembers == null) {
            throw new IllegalArgumentException("Required value for 'suspendedMembers' is null");
        }
        for (Long x222 : suspendedMembers) {
            if (x222 == null) {
                throw new IllegalArgumentException("An item in list 'suspendedMembers' is null");
            }
        }
        this.suspendedMembers = suspendedMembers;
        if (licenses == null) {
            throw new IllegalArgumentException("Required value for 'licenses' is null");
        }
        for (Long x2222 : licenses) {
            if (x2222 == null) {
                throw new IllegalArgumentException("An item in list 'licenses' is null");
            }
        }
        this.licenses = licenses;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public List<Long> getTeamSize() {
        return this.teamSize;
    }

    public List<Long> getPendingInvites() {
        return this.pendingInvites;
    }

    public List<Long> getMembersJoined() {
        return this.membersJoined;
    }

    public List<Long> getSuspendedMembers() {
        return this.suspendedMembers;
    }

    public List<Long> getLicenses() {
        return this.licenses;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.teamSize, this.pendingInvites, this.membersJoined, this.suspendedMembers, this.licenses}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GetMembershipReport other = (GetMembershipReport) obj;
        if ((this.startDate == other.startDate || this.startDate.equals(other.startDate)) && ((this.teamSize == other.teamSize || this.teamSize.equals(other.teamSize)) && ((this.pendingInvites == other.pendingInvites || this.pendingInvites.equals(other.pendingInvites)) && ((this.membersJoined == other.membersJoined || this.membersJoined.equals(other.membersJoined)) && ((this.suspendedMembers == other.suspendedMembers || this.suspendedMembers.equals(other.suspendedMembers)) && (this.licenses == other.licenses || this.licenses.equals(other.licenses))))))) {
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
