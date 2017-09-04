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

public class RevokeLinkedApiAppArg {
    protected final String appId;
    protected final boolean keepAppFolder;
    protected final String teamMemberId;

    static class Serializer extends StructSerializer<RevokeLinkedApiAppArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RevokeLinkedApiAppArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("app_id");
            StoneSerializers.string().serialize(value.appId, g);
            g.writeFieldName("team_member_id");
            StoneSerializers.string().serialize(value.teamMemberId, g);
            g.writeFieldName("keep_app_folder");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.keepAppFolder), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public RevokeLinkedApiAppArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_appId = null;
                String f_teamMemberId = null;
                Boolean f_keepAppFolder = Boolean.valueOf(true);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("app_id".equals(field)) {
                        f_appId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("team_member_id".equals(field)) {
                        f_teamMemberId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("keep_app_folder".equals(field)) {
                        f_keepAppFolder = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_appId == null) {
                    throw new JsonParseException(p, "Required field \"app_id\" missing.");
                } else if (f_teamMemberId == null) {
                    throw new JsonParseException(p, "Required field \"team_member_id\" missing.");
                } else {
                    RevokeLinkedApiAppArg value = new RevokeLinkedApiAppArg(f_appId, f_teamMemberId, f_keepAppFolder.booleanValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public RevokeLinkedApiAppArg(String appId, String teamMemberId, boolean keepAppFolder) {
        if (appId == null) {
            throw new IllegalArgumentException("Required value for 'appId' is null");
        }
        this.appId = appId;
        if (teamMemberId == null) {
            throw new IllegalArgumentException("Required value for 'teamMemberId' is null");
        }
        this.teamMemberId = teamMemberId;
        this.keepAppFolder = keepAppFolder;
    }

    public RevokeLinkedApiAppArg(String appId, String teamMemberId) {
        this(appId, teamMemberId, true);
    }

    public String getAppId() {
        return this.appId;
    }

    public String getTeamMemberId() {
        return this.teamMemberId;
    }

    public boolean getKeepAppFolder() {
        return this.keepAppFolder;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.appId, this.teamMemberId, Boolean.valueOf(this.keepAppFolder)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        RevokeLinkedApiAppArg other = (RevokeLinkedApiAppArg) obj;
        if ((this.appId == other.appId || this.appId.equals(other.appId)) && ((this.teamMemberId == other.teamMemberId || this.teamMemberId.equals(other.teamMemberId)) && this.keepAppFolder == other.keepAppFolder)) {
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
