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

class ListMemberAppsArg {
    protected final String teamMemberId;

    static class Serializer extends StructSerializer<ListMemberAppsArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListMemberAppsArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("team_member_id");
            StoneSerializers.string().serialize(value.teamMemberId, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListMemberAppsArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_teamMemberId = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("team_member_id".equals(field)) {
                        f_teamMemberId = (String) StoneSerializers.string().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_teamMemberId == null) {
                    throw new JsonParseException(p, "Required field \"team_member_id\" missing.");
                }
                ListMemberAppsArg value = new ListMemberAppsArg(f_teamMemberId);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListMemberAppsArg(String teamMemberId) {
        if (teamMemberId == null) {
            throw new IllegalArgumentException("Required value for 'teamMemberId' is null");
        }
        this.teamMemberId = teamMemberId;
    }

    public String getTeamMemberId() {
        return this.teamMemberId;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.teamMemberId});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListMemberAppsArg other = (ListMemberAppsArg) obj;
        if (this.teamMemberId == other.teamMemberId || this.teamMemberId.equals(other.teamMemberId)) {
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
