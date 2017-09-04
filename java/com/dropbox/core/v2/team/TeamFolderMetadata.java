package com.dropbox.core.v2.team;

import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.models.BoxUser;
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
import java.util.regex.Pattern;

public class TeamFolderMetadata {
    protected final String name;
    protected final TeamFolderStatus status;
    protected final String teamFolderId;

    static class Serializer extends StructSerializer<TeamFolderMetadata> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(TeamFolderMetadata value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("team_folder_id");
            StoneSerializers.string().serialize(value.teamFolderId, g);
            g.writeFieldName(BoxFileVersion.FIELD_NAME);
            StoneSerializers.string().serialize(value.name, g);
            g.writeFieldName(BoxUser.FIELD_STATUS);
            Serializer.INSTANCE.serialize(value.status, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public TeamFolderMetadata deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_teamFolderId = null;
                String f_name = null;
                TeamFolderStatus f_status = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("team_folder_id".equals(field)) {
                        f_teamFolderId = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxFileVersion.FIELD_NAME.equals(field)) {
                        f_name = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxUser.FIELD_STATUS.equals(field)) {
                        f_status = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_teamFolderId == null) {
                    throw new JsonParseException(p, "Required field \"team_folder_id\" missing.");
                } else if (f_name == null) {
                    throw new JsonParseException(p, "Required field \"name\" missing.");
                } else if (f_status == null) {
                    throw new JsonParseException(p, "Required field \"status\" missing.");
                } else {
                    TeamFolderMetadata value = new TeamFolderMetadata(f_teamFolderId, f_name, f_status);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public TeamFolderMetadata(String teamFolderId, String name, TeamFolderStatus status) {
        if (teamFolderId == null) {
            throw new IllegalArgumentException("Required value for 'teamFolderId' is null");
        } else if (Pattern.matches("[-_0-9a-zA-Z:]+", teamFolderId)) {
            this.teamFolderId = teamFolderId;
            if (name == null) {
                throw new IllegalArgumentException("Required value for 'name' is null");
            }
            this.name = name;
            if (status == null) {
                throw new IllegalArgumentException("Required value for 'status' is null");
            }
            this.status = status;
        } else {
            throw new IllegalArgumentException("String 'teamFolderId' does not match pattern");
        }
    }

    public String getTeamFolderId() {
        return this.teamFolderId;
    }

    public String getName() {
        return this.name;
    }

    public TeamFolderStatus getStatus() {
        return this.status;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.teamFolderId, this.name, this.status});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        TeamFolderMetadata other = (TeamFolderMetadata) obj;
        if ((this.teamFolderId == other.teamFolderId || this.teamFolderId.equals(other.teamFolderId)) && ((this.name == other.name || this.name.equals(other.name)) && (this.status == other.status || this.status.equals(other.status)))) {
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
