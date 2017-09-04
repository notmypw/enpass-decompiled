package com.dropbox.core.v2.team;

import com.box.androidsdk.content.models.BoxEvent;
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

public class DeviceSessionArg {
    protected final String sessionId;
    protected final String teamMemberId;

    static class Serializer extends StructSerializer<DeviceSessionArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(DeviceSessionArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxEvent.FIELD_SESSION_ID);
            StoneSerializers.string().serialize(value.sessionId, g);
            g.writeFieldName("team_member_id");
            StoneSerializers.string().serialize(value.teamMemberId, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public DeviceSessionArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_sessionId = null;
                String f_teamMemberId = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxEvent.FIELD_SESSION_ID.equals(field)) {
                        f_sessionId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("team_member_id".equals(field)) {
                        f_teamMemberId = (String) StoneSerializers.string().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_sessionId == null) {
                    throw new JsonParseException(p, "Required field \"session_id\" missing.");
                } else if (f_teamMemberId == null) {
                    throw new JsonParseException(p, "Required field \"team_member_id\" missing.");
                } else {
                    DeviceSessionArg value = new DeviceSessionArg(f_sessionId, f_teamMemberId);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public DeviceSessionArg(String sessionId, String teamMemberId) {
        if (sessionId == null) {
            throw new IllegalArgumentException("Required value for 'sessionId' is null");
        }
        this.sessionId = sessionId;
        if (teamMemberId == null) {
            throw new IllegalArgumentException("Required value for 'teamMemberId' is null");
        }
        this.teamMemberId = teamMemberId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getTeamMemberId() {
        return this.teamMemberId;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.sessionId, this.teamMemberId});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        DeviceSessionArg other = (DeviceSessionArg) obj;
        if ((this.sessionId == other.sessionId || this.sessionId.equals(other.sessionId)) && (this.teamMemberId == other.teamMemberId || this.teamMemberId.equals(other.teamMemberId))) {
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
