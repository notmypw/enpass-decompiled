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

public class TeamFolderListResult {
    protected final List<TeamFolderMetadata> teamFolders;

    static class Serializer extends StructSerializer<TeamFolderListResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(TeamFolderListResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("team_folders");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.teamFolders, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public TeamFolderListResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<TeamFolderMetadata> f_teamFolders = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("team_folders".equals(field)) {
                        f_teamFolders = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_teamFolders == null) {
                    throw new JsonParseException(p, "Required field \"team_folders\" missing.");
                }
                TeamFolderListResult value = new TeamFolderListResult(f_teamFolders);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public TeamFolderListResult(List<TeamFolderMetadata> teamFolders) {
        if (teamFolders == null) {
            throw new IllegalArgumentException("Required value for 'teamFolders' is null");
        }
        for (TeamFolderMetadata x : teamFolders) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'teamFolders' is null");
            }
        }
        this.teamFolders = teamFolders;
    }

    public List<TeamFolderMetadata> getTeamFolders() {
        return this.teamFolders;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.teamFolders});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        TeamFolderListResult other = (TeamFolderListResult) obj;
        if (this.teamFolders == other.teamFolders || this.teamFolders.equals(other.teamFolders)) {
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
