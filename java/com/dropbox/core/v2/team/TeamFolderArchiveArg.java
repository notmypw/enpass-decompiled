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

class TeamFolderArchiveArg extends TeamFolderIdArg {
    protected final boolean forceAsyncOff;

    static class Serializer extends StructSerializer<TeamFolderArchiveArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(TeamFolderArchiveArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("team_folder_id");
            StoneSerializers.string().serialize(value.teamFolderId, g);
            g.writeFieldName("force_async_off");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.forceAsyncOff), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public TeamFolderArchiveArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_teamFolderId = null;
                Boolean f_forceAsyncOff = Boolean.valueOf(false);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("team_folder_id".equals(field)) {
                        f_teamFolderId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("force_async_off".equals(field)) {
                        f_forceAsyncOff = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_teamFolderId == null) {
                    throw new JsonParseException(p, "Required field \"team_folder_id\" missing.");
                }
                TeamFolderArchiveArg value = new TeamFolderArchiveArg(f_teamFolderId, f_forceAsyncOff.booleanValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public TeamFolderArchiveArg(String teamFolderId, boolean forceAsyncOff) {
        super(teamFolderId);
        this.forceAsyncOff = forceAsyncOff;
    }

    public TeamFolderArchiveArg(String teamFolderId) {
        this(teamFolderId, false);
    }

    public String getTeamFolderId() {
        return this.teamFolderId;
    }

    public boolean getForceAsyncOff() {
        return this.forceAsyncOff;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Boolean.valueOf(this.forceAsyncOff)}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        TeamFolderArchiveArg other = (TeamFolderArchiveArg) obj;
        if ((this.teamFolderId == other.teamFolderId || this.teamFolderId.equals(other.teamFolderId)) && this.forceAsyncOff == other.forceAsyncOff) {
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
