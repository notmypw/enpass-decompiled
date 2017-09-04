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

public class MemberLinkedApps {
    protected final List<ApiApp> linkedApiApps;
    protected final String teamMemberId;

    static class Serializer extends StructSerializer<MemberLinkedApps> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(MemberLinkedApps value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("team_member_id");
            StoneSerializers.string().serialize(value.teamMemberId, g);
            g.writeFieldName("linked_api_apps");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.linkedApiApps, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public MemberLinkedApps deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_teamMemberId = null;
                List<ApiApp> f_linkedApiApps = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("team_member_id".equals(field)) {
                        f_teamMemberId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("linked_api_apps".equals(field)) {
                        f_linkedApiApps = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_teamMemberId == null) {
                    throw new JsonParseException(p, "Required field \"team_member_id\" missing.");
                } else if (f_linkedApiApps == null) {
                    throw new JsonParseException(p, "Required field \"linked_api_apps\" missing.");
                } else {
                    MemberLinkedApps value = new MemberLinkedApps(f_teamMemberId, f_linkedApiApps);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public MemberLinkedApps(String teamMemberId, List<ApiApp> linkedApiApps) {
        if (teamMemberId == null) {
            throw new IllegalArgumentException("Required value for 'teamMemberId' is null");
        }
        this.teamMemberId = teamMemberId;
        if (linkedApiApps == null) {
            throw new IllegalArgumentException("Required value for 'linkedApiApps' is null");
        }
        for (ApiApp x : linkedApiApps) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'linkedApiApps' is null");
            }
        }
        this.linkedApiApps = linkedApiApps;
    }

    public String getTeamMemberId() {
        return this.teamMemberId;
    }

    public List<ApiApp> getLinkedApiApps() {
        return this.linkedApiApps;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.teamMemberId, this.linkedApiApps});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        MemberLinkedApps other = (MemberLinkedApps) obj;
        if ((this.teamMemberId == other.teamMemberId || this.teamMemberId.equals(other.teamMemberId)) && (this.linkedApiApps == other.linkedApiApps || this.linkedApiApps.equals(other.linkedApiApps))) {
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
