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

public class RevokeDesktopClientArg extends DeviceSessionArg {
    protected final boolean deleteOnUnlink;

    static class Serializer extends StructSerializer<RevokeDesktopClientArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RevokeDesktopClientArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxEvent.FIELD_SESSION_ID);
            StoneSerializers.string().serialize(value.sessionId, g);
            g.writeFieldName("team_member_id");
            StoneSerializers.string().serialize(value.teamMemberId, g);
            g.writeFieldName("delete_on_unlink");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.deleteOnUnlink), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public RevokeDesktopClientArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_sessionId = null;
                String f_teamMemberId = null;
                Boolean f_deleteOnUnlink = Boolean.valueOf(false);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxEvent.FIELD_SESSION_ID.equals(field)) {
                        f_sessionId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("team_member_id".equals(field)) {
                        f_teamMemberId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("delete_on_unlink".equals(field)) {
                        f_deleteOnUnlink = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_sessionId == null) {
                    throw new JsonParseException(p, "Required field \"session_id\" missing.");
                } else if (f_teamMemberId == null) {
                    throw new JsonParseException(p, "Required field \"team_member_id\" missing.");
                } else {
                    RevokeDesktopClientArg value = new RevokeDesktopClientArg(f_sessionId, f_teamMemberId, f_deleteOnUnlink.booleanValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public RevokeDesktopClientArg(String sessionId, String teamMemberId, boolean deleteOnUnlink) {
        super(sessionId, teamMemberId);
        this.deleteOnUnlink = deleteOnUnlink;
    }

    public RevokeDesktopClientArg(String sessionId, String teamMemberId) {
        this(sessionId, teamMemberId, false);
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getTeamMemberId() {
        return this.teamMemberId;
    }

    public boolean getDeleteOnUnlink() {
        return this.deleteOnUnlink;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Boolean.valueOf(this.deleteOnUnlink)}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        RevokeDesktopClientArg other = (RevokeDesktopClientArg) obj;
        if ((this.sessionId == other.sessionId || this.sessionId.equals(other.sessionId)) && ((this.teamMemberId == other.teamMemberId || this.teamMemberId.equals(other.teamMemberId)) && this.deleteOnUnlink == other.deleteOnUnlink)) {
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
