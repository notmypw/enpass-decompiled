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

public class TeamMemberPolicies {
    protected final EmmState emmState;
    protected final TeamSharingPolicies sharing;

    public static class Serializer extends StructSerializer<TeamMemberPolicies> {
        public static final Serializer INSTANCE = new Serializer();

        public void serialize(TeamMemberPolicies value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("sharing");
            com.dropbox.core.v2.teampolicies.TeamSharingPolicies.Serializer.INSTANCE.serialize(value.sharing, g);
            g.writeFieldName("emm_state");
            Serializer.INSTANCE.serialize(value.emmState, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public TeamMemberPolicies deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                TeamSharingPolicies f_sharing = null;
                EmmState f_emmState = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("sharing".equals(field)) {
                        f_sharing = (TeamSharingPolicies) com.dropbox.core.v2.teampolicies.TeamSharingPolicies.Serializer.INSTANCE.deserialize(p);
                    } else if ("emm_state".equals(field)) {
                        f_emmState = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_sharing == null) {
                    throw new JsonParseException(p, "Required field \"sharing\" missing.");
                } else if (f_emmState == null) {
                    throw new JsonParseException(p, "Required field \"emm_state\" missing.");
                } else {
                    TeamMemberPolicies value = new TeamMemberPolicies(f_sharing, f_emmState);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public TeamMemberPolicies(TeamSharingPolicies sharing, EmmState emmState) {
        if (sharing == null) {
            throw new IllegalArgumentException("Required value for 'sharing' is null");
        }
        this.sharing = sharing;
        if (emmState == null) {
            throw new IllegalArgumentException("Required value for 'emmState' is null");
        }
        this.emmState = emmState;
    }

    public TeamSharingPolicies getSharing() {
        return this.sharing;
    }

    public EmmState getEmmState() {
        return this.emmState;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.sharing, this.emmState});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        TeamMemberPolicies other = (TeamMemberPolicies) obj;
        if ((this.sharing == other.sharing || this.sharing.equals(other.sharing)) && (this.emmState == other.emmState || this.emmState.equals(other.emmState))) {
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
