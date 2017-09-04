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
import java.util.regex.Pattern;

class TeamFolderIdListArg {
    protected final List<String> teamFolderIds;

    static class Serializer extends StructSerializer<TeamFolderIdListArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(TeamFolderIdListArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("team_folder_ids");
            StoneSerializers.list(StoneSerializers.string()).serialize(value.teamFolderIds, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public TeamFolderIdListArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<String> f_teamFolderIds = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("team_folder_ids".equals(field)) {
                        f_teamFolderIds = (List) StoneSerializers.list(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_teamFolderIds == null) {
                    throw new JsonParseException(p, "Required field \"team_folder_ids\" missing.");
                }
                TeamFolderIdListArg value = new TeamFolderIdListArg(f_teamFolderIds);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public TeamFolderIdListArg(List<String> teamFolderIds) {
        if (teamFolderIds == null) {
            throw new IllegalArgumentException("Required value for 'teamFolderIds' is null");
        } else if (teamFolderIds.size() < 1) {
            throw new IllegalArgumentException("List 'teamFolderIds' has fewer than 1 items");
        } else {
            for (String x : teamFolderIds) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'teamFolderIds' is null");
                } else if (!Pattern.matches("[-_0-9a-zA-Z:]+", x)) {
                    throw new IllegalArgumentException("Stringan item in list 'teamFolderIds' does not match pattern");
                }
            }
            this.teamFolderIds = teamFolderIds;
        }
    }

    public List<String> getTeamFolderIds() {
        return this.teamFolderIds;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.teamFolderIds});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        TeamFolderIdListArg other = (TeamFolderIdListArg) obj;
        if (this.teamFolderIds == other.teamFolderIds || this.teamFolderIds.equals(other.teamFolderIds)) {
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
