package com.dropbox.core.v2.sharing;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.v2.users.Team;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class SharedFolderMetadataBase {
    protected final AccessLevel accessType;
    protected final boolean isInsideTeamFolder;
    protected final boolean isTeamFolder;
    protected final Team ownerTeam;
    protected final String parentSharedFolderId;
    protected final String pathLower;

    public static class Builder {
        protected final AccessLevel accessType;
        protected final boolean isInsideTeamFolder;
        protected final boolean isTeamFolder;
        protected Team ownerTeam;
        protected String parentSharedFolderId;
        protected String pathLower;

        protected Builder(AccessLevel accessType, boolean isInsideTeamFolder, boolean isTeamFolder) {
            if (accessType == null) {
                throw new IllegalArgumentException("Required value for 'accessType' is null");
            }
            this.accessType = accessType;
            this.isInsideTeamFolder = isInsideTeamFolder;
            this.isTeamFolder = isTeamFolder;
            this.ownerTeam = null;
            this.parentSharedFolderId = null;
            this.pathLower = null;
        }

        public Builder withOwnerTeam(Team ownerTeam) {
            this.ownerTeam = ownerTeam;
            return this;
        }

        public Builder withParentSharedFolderId(String parentSharedFolderId) {
            if (parentSharedFolderId == null || Pattern.matches("[-_0-9a-zA-Z:]+", parentSharedFolderId)) {
                this.parentSharedFolderId = parentSharedFolderId;
                return this;
            }
            throw new IllegalArgumentException("String 'parentSharedFolderId' does not match pattern");
        }

        public Builder withPathLower(String pathLower) {
            this.pathLower = pathLower;
            return this;
        }

        public SharedFolderMetadataBase build() {
            return new SharedFolderMetadataBase(this.accessType, this.isInsideTeamFolder, this.isTeamFolder, this.ownerTeam, this.parentSharedFolderId, this.pathLower);
        }
    }

    private static class Serializer extends StructSerializer<SharedFolderMetadataBase> {
        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {
        }

        public void serialize(SharedFolderMetadataBase value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("access_type");
            Serializer.INSTANCE.serialize(value.accessType, g);
            g.writeFieldName("is_inside_team_folder");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.isInsideTeamFolder), g);
            g.writeFieldName("is_team_folder");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.isTeamFolder), g);
            if (value.ownerTeam != null) {
                g.writeFieldName("owner_team");
                StoneSerializers.nullableStruct(com.dropbox.core.v2.users.Team.Serializer.INSTANCE).serialize(value.ownerTeam, g);
            }
            if (value.parentSharedFolderId != null) {
                g.writeFieldName("parent_shared_folder_id");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.parentSharedFolderId, g);
            }
            if (value.pathLower != null) {
                g.writeFieldName("path_lower");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.pathLower, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public SharedFolderMetadataBase deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                AccessLevel f_accessType = null;
                Boolean f_isInsideTeamFolder = null;
                Boolean f_isTeamFolder = null;
                Team f_ownerTeam = null;
                String f_parentSharedFolderId = null;
                String f_pathLower = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("access_type".equals(field)) {
                        f_accessType = Serializer.INSTANCE.deserialize(p);
                    } else if ("is_inside_team_folder".equals(field)) {
                        f_isInsideTeamFolder = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("is_team_folder".equals(field)) {
                        f_isTeamFolder = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("owner_team".equals(field)) {
                        f_ownerTeam = (Team) StoneSerializers.nullableStruct(com.dropbox.core.v2.users.Team.Serializer.INSTANCE).deserialize(p);
                    } else if ("parent_shared_folder_id".equals(field)) {
                        f_parentSharedFolderId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("path_lower".equals(field)) {
                        f_pathLower = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_accessType == null) {
                    throw new JsonParseException(p, "Required field \"access_type\" missing.");
                } else if (f_isInsideTeamFolder == null) {
                    throw new JsonParseException(p, "Required field \"is_inside_team_folder\" missing.");
                } else if (f_isTeamFolder == null) {
                    throw new JsonParseException(p, "Required field \"is_team_folder\" missing.");
                } else {
                    SharedFolderMetadataBase value = new SharedFolderMetadataBase(f_accessType, f_isInsideTeamFolder.booleanValue(), f_isTeamFolder.booleanValue(), f_ownerTeam, f_parentSharedFolderId, f_pathLower);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public SharedFolderMetadataBase(AccessLevel accessType, boolean isInsideTeamFolder, boolean isTeamFolder, Team ownerTeam, String parentSharedFolderId, String pathLower) {
        if (accessType == null) {
            throw new IllegalArgumentException("Required value for 'accessType' is null");
        }
        this.accessType = accessType;
        this.isInsideTeamFolder = isInsideTeamFolder;
        this.isTeamFolder = isTeamFolder;
        this.ownerTeam = ownerTeam;
        if (parentSharedFolderId == null || Pattern.matches("[-_0-9a-zA-Z:]+", parentSharedFolderId)) {
            this.parentSharedFolderId = parentSharedFolderId;
            this.pathLower = pathLower;
            return;
        }
        throw new IllegalArgumentException("String 'parentSharedFolderId' does not match pattern");
    }

    public SharedFolderMetadataBase(AccessLevel accessType, boolean isInsideTeamFolder, boolean isTeamFolder) {
        this(accessType, isInsideTeamFolder, isTeamFolder, null, null, null);
    }

    public AccessLevel getAccessType() {
        return this.accessType;
    }

    public boolean getIsInsideTeamFolder() {
        return this.isInsideTeamFolder;
    }

    public boolean getIsTeamFolder() {
        return this.isTeamFolder;
    }

    public Team getOwnerTeam() {
        return this.ownerTeam;
    }

    public String getParentSharedFolderId() {
        return this.parentSharedFolderId;
    }

    public String getPathLower() {
        return this.pathLower;
    }

    public static Builder newBuilder(AccessLevel accessType, boolean isInsideTeamFolder, boolean isTeamFolder) {
        return new Builder(accessType, isInsideTeamFolder, isTeamFolder);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.accessType, Boolean.valueOf(this.isInsideTeamFolder), Boolean.valueOf(this.isTeamFolder), this.ownerTeam, this.parentSharedFolderId, this.pathLower});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        SharedFolderMetadataBase other = (SharedFolderMetadataBase) obj;
        if ((this.accessType == other.accessType || this.accessType.equals(other.accessType)) && this.isInsideTeamFolder == other.isInsideTeamFolder && this.isTeamFolder == other.isTeamFolder && ((this.ownerTeam == other.ownerTeam || (this.ownerTeam != null && this.ownerTeam.equals(other.ownerTeam))) && (this.parentSharedFolderId == other.parentSharedFolderId || (this.parentSharedFolderId != null && this.parentSharedFolderId.equals(other.parentSharedFolderId))))) {
            if (this.pathLower == other.pathLower) {
                return true;
            }
            if (this.pathLower != null && this.pathLower.equals(other.pathLower)) {
                return true;
            }
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
