package com.dropbox.core.v2.sharing;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.v2.users.Team;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;

public class TeamMemberInfo {
    protected final String displayName;
    protected final String memberId;
    protected final Team teamInfo;

    static class Serializer extends StructSerializer<TeamMemberInfo> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(TeamMemberInfo value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("team_info");
            com.dropbox.core.v2.users.Team.Serializer.INSTANCE.serialize(value.teamInfo, g);
            g.writeFieldName("display_name");
            StoneSerializers.string().serialize(value.displayName, g);
            if (value.memberId != null) {
                g.writeFieldName("member_id");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.memberId, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public TeamMemberInfo deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Team f_teamInfo = null;
                String f_displayName = null;
                String f_memberId = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("team_info".equals(field)) {
                        f_teamInfo = (Team) com.dropbox.core.v2.users.Team.Serializer.INSTANCE.deserialize(p);
                    } else if ("display_name".equals(field)) {
                        f_displayName = (String) StoneSerializers.string().deserialize(p);
                    } else if ("member_id".equals(field)) {
                        f_memberId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_teamInfo == null) {
                    throw new JsonParseException(p, "Required field \"team_info\" missing.");
                } else if (f_displayName == null) {
                    throw new JsonParseException(p, "Required field \"display_name\" missing.");
                } else {
                    TeamMemberInfo value = new TeamMemberInfo(f_teamInfo, f_displayName, f_memberId);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public TeamMemberInfo(Team teamInfo, String displayName, String memberId) {
        if (teamInfo == null) {
            throw new IllegalArgumentException("Required value for 'teamInfo' is null");
        }
        this.teamInfo = teamInfo;
        if (displayName == null) {
            throw new IllegalArgumentException("Required value for 'displayName' is null");
        }
        this.displayName = displayName;
        this.memberId = memberId;
    }

    public TeamMemberInfo(Team teamInfo, String displayName) {
        this(teamInfo, displayName, null);
    }

    public Team getTeamInfo() {
        return this.teamInfo;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getMemberId() {
        return this.memberId;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.teamInfo, this.displayName, this.memberId});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        TeamMemberInfo other = (TeamMemberInfo) obj;
        if ((this.teamInfo == other.teamInfo || this.teamInfo.equals(other.teamInfo)) && (this.displayName == other.displayName || this.displayName.equals(other.displayName))) {
            if (this.memberId == other.memberId) {
                return true;
            }
            if (this.memberId != null && this.memberId.equals(other.memberId)) {
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
