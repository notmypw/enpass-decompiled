package com.dropbox.core.v2.sharing;

import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.models.BoxSharedLink;
import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.util.LangUtil;
import com.dropbox.core.v2.users.Team;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class SharedFolderMetadata extends SharedFolderMetadataBase {
    protected final SharedContentLinkMetadata linkMetadata;
    protected final String name;
    protected final List<FolderPermission> permissions;
    protected final FolderPolicy policy;
    protected final String previewUrl;
    protected final String sharedFolderId;
    protected final Date timeInvited;

    public static class Builder extends com.dropbox.core.v2.sharing.SharedFolderMetadataBase.Builder {
        protected SharedContentLinkMetadata linkMetadata;
        protected final String name;
        protected List<FolderPermission> permissions;
        protected final FolderPolicy policy;
        protected final String previewUrl;
        protected final String sharedFolderId;
        protected final Date timeInvited;

        protected Builder(AccessLevel accessType, boolean isInsideTeamFolder, boolean isTeamFolder, String name, FolderPolicy policy, String previewUrl, String sharedFolderId, Date timeInvited) {
            super(accessType, isInsideTeamFolder, isTeamFolder);
            if (name == null) {
                throw new IllegalArgumentException("Required value for 'name' is null");
            }
            this.name = name;
            if (policy == null) {
                throw new IllegalArgumentException("Required value for 'policy' is null");
            }
            this.policy = policy;
            if (previewUrl == null) {
                throw new IllegalArgumentException("Required value for 'previewUrl' is null");
            }
            this.previewUrl = previewUrl;
            if (sharedFolderId == null) {
                throw new IllegalArgumentException("Required value for 'sharedFolderId' is null");
            } else if (Pattern.matches("[-_0-9a-zA-Z:]+", sharedFolderId)) {
                this.sharedFolderId = sharedFolderId;
                if (timeInvited == null) {
                    throw new IllegalArgumentException("Required value for 'timeInvited' is null");
                }
                this.timeInvited = LangUtil.truncateMillis(timeInvited);
                this.linkMetadata = null;
                this.permissions = null;
            } else {
                throw new IllegalArgumentException("String 'sharedFolderId' does not match pattern");
            }
        }

        public Builder withLinkMetadata(SharedContentLinkMetadata linkMetadata) {
            this.linkMetadata = linkMetadata;
            return this;
        }

        public Builder withPermissions(List<FolderPermission> permissions) {
            if (permissions != null) {
                for (FolderPermission x : permissions) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'permissions' is null");
                    }
                }
            }
            this.permissions = permissions;
            return this;
        }

        public Builder withOwnerTeam(Team ownerTeam) {
            super.withOwnerTeam(ownerTeam);
            return this;
        }

        public Builder withParentSharedFolderId(String parentSharedFolderId) {
            super.withParentSharedFolderId(parentSharedFolderId);
            return this;
        }

        public Builder withPathLower(String pathLower) {
            super.withPathLower(pathLower);
            return this;
        }

        public SharedFolderMetadata build() {
            return new SharedFolderMetadata(this.accessType, this.isInsideTeamFolder, this.isTeamFolder, this.name, this.policy, this.previewUrl, this.sharedFolderId, this.timeInvited, this.ownerTeam, this.parentSharedFolderId, this.pathLower, this.linkMetadata, this.permissions);
        }
    }

    static class Serializer extends StructSerializer<SharedFolderMetadata> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(SharedFolderMetadata value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("access_type");
            Serializer.INSTANCE.serialize(value.accessType, g);
            g.writeFieldName("is_inside_team_folder");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.isInsideTeamFolder), g);
            g.writeFieldName("is_team_folder");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.isTeamFolder), g);
            g.writeFieldName(BoxFileVersion.FIELD_NAME);
            StoneSerializers.string().serialize(value.name, g);
            g.writeFieldName("policy");
            Serializer.INSTANCE.serialize(value.policy, g);
            g.writeFieldName("preview_url");
            StoneSerializers.string().serialize(value.previewUrl, g);
            g.writeFieldName("shared_folder_id");
            StoneSerializers.string().serialize(value.sharedFolderId, g);
            g.writeFieldName("time_invited");
            StoneSerializers.timestamp().serialize(value.timeInvited, g);
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
            if (value.linkMetadata != null) {
                g.writeFieldName("link_metadata");
                StoneSerializers.nullableStruct(Serializer.INSTANCE).serialize(value.linkMetadata, g);
            }
            if (value.permissions != null) {
                g.writeFieldName(BoxSharedLink.FIELD_PERMISSIONS);
                StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).serialize(value.permissions, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public SharedFolderMetadata deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                AccessLevel f_accessType = null;
                Boolean f_isInsideTeamFolder = null;
                Boolean f_isTeamFolder = null;
                String f_name = null;
                FolderPolicy f_policy = null;
                String f_previewUrl = null;
                String f_sharedFolderId = null;
                Date f_timeInvited = null;
                Team f_ownerTeam = null;
                String f_parentSharedFolderId = null;
                String f_pathLower = null;
                SharedContentLinkMetadata f_linkMetadata = null;
                List<FolderPermission> f_permissions = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("access_type".equals(field)) {
                        f_accessType = Serializer.INSTANCE.deserialize(p);
                    } else if ("is_inside_team_folder".equals(field)) {
                        f_isInsideTeamFolder = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("is_team_folder".equals(field)) {
                        f_isTeamFolder = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if (BoxFileVersion.FIELD_NAME.equals(field)) {
                        f_name = (String) StoneSerializers.string().deserialize(p);
                    } else if ("policy".equals(field)) {
                        f_policy = (FolderPolicy) Serializer.INSTANCE.deserialize(p);
                    } else if ("preview_url".equals(field)) {
                        f_previewUrl = (String) StoneSerializers.string().deserialize(p);
                    } else if ("shared_folder_id".equals(field)) {
                        f_sharedFolderId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("time_invited".equals(field)) {
                        f_timeInvited = (Date) StoneSerializers.timestamp().deserialize(p);
                    } else if ("owner_team".equals(field)) {
                        f_ownerTeam = (Team) StoneSerializers.nullableStruct(com.dropbox.core.v2.users.Team.Serializer.INSTANCE).deserialize(p);
                    } else if ("parent_shared_folder_id".equals(field)) {
                        f_parentSharedFolderId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("path_lower".equals(field)) {
                        f_pathLower = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("link_metadata".equals(field)) {
                        f_linkMetadata = (SharedContentLinkMetadata) StoneSerializers.nullableStruct(Serializer.INSTANCE).deserialize(p);
                    } else if (BoxSharedLink.FIELD_PERMISSIONS.equals(field)) {
                        f_permissions = (List) StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).deserialize(p);
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
                } else if (f_name == null) {
                    throw new JsonParseException(p, "Required field \"name\" missing.");
                } else if (f_policy == null) {
                    throw new JsonParseException(p, "Required field \"policy\" missing.");
                } else if (f_previewUrl == null) {
                    throw new JsonParseException(p, "Required field \"preview_url\" missing.");
                } else if (f_sharedFolderId == null) {
                    throw new JsonParseException(p, "Required field \"shared_folder_id\" missing.");
                } else if (f_timeInvited == null) {
                    throw new JsonParseException(p, "Required field \"time_invited\" missing.");
                } else {
                    SharedFolderMetadata value = new SharedFolderMetadata(f_accessType, f_isInsideTeamFolder.booleanValue(), f_isTeamFolder.booleanValue(), f_name, f_policy, f_previewUrl, f_sharedFolderId, f_timeInvited, f_ownerTeam, f_parentSharedFolderId, f_pathLower, f_linkMetadata, f_permissions);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public SharedFolderMetadata(AccessLevel accessType, boolean isInsideTeamFolder, boolean isTeamFolder, String name, FolderPolicy policy, String previewUrl, String sharedFolderId, Date timeInvited, Team ownerTeam, String parentSharedFolderId, String pathLower, SharedContentLinkMetadata linkMetadata, List<FolderPermission> permissions) {
        super(accessType, isInsideTeamFolder, isTeamFolder, ownerTeam, parentSharedFolderId, pathLower);
        this.linkMetadata = linkMetadata;
        if (name == null) {
            throw new IllegalArgumentException("Required value for 'name' is null");
        }
        this.name = name;
        if (permissions != null) {
            for (FolderPermission x : permissions) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'permissions' is null");
                }
            }
        }
        this.permissions = permissions;
        if (policy == null) {
            throw new IllegalArgumentException("Required value for 'policy' is null");
        }
        this.policy = policy;
        if (previewUrl == null) {
            throw new IllegalArgumentException("Required value for 'previewUrl' is null");
        }
        this.previewUrl = previewUrl;
        if (sharedFolderId == null) {
            throw new IllegalArgumentException("Required value for 'sharedFolderId' is null");
        } else if (Pattern.matches("[-_0-9a-zA-Z:]+", sharedFolderId)) {
            this.sharedFolderId = sharedFolderId;
            if (timeInvited == null) {
                throw new IllegalArgumentException("Required value for 'timeInvited' is null");
            }
            this.timeInvited = LangUtil.truncateMillis(timeInvited);
        } else {
            throw new IllegalArgumentException("String 'sharedFolderId' does not match pattern");
        }
    }

    public SharedFolderMetadata(AccessLevel accessType, boolean isInsideTeamFolder, boolean isTeamFolder, String name, FolderPolicy policy, String previewUrl, String sharedFolderId, Date timeInvited) {
        this(accessType, isInsideTeamFolder, isTeamFolder, name, policy, previewUrl, sharedFolderId, timeInvited, null, null, null, null, null);
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

    public String getName() {
        return this.name;
    }

    public FolderPolicy getPolicy() {
        return this.policy;
    }

    public String getPreviewUrl() {
        return this.previewUrl;
    }

    public String getSharedFolderId() {
        return this.sharedFolderId;
    }

    public Date getTimeInvited() {
        return this.timeInvited;
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

    public SharedContentLinkMetadata getLinkMetadata() {
        return this.linkMetadata;
    }

    public List<FolderPermission> getPermissions() {
        return this.permissions;
    }

    public static Builder newBuilder(AccessLevel accessType, boolean isInsideTeamFolder, boolean isTeamFolder, String name, FolderPolicy policy, String previewUrl, String sharedFolderId, Date timeInvited) {
        return new Builder(accessType, isInsideTeamFolder, isTeamFolder, name, policy, previewUrl, sharedFolderId, timeInvited);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.linkMetadata, this.name, this.permissions, this.policy, this.previewUrl, this.sharedFolderId, this.timeInvited}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        SharedFolderMetadata other = (SharedFolderMetadata) obj;
        if ((this.accessType == other.accessType || this.accessType.equals(other.accessType)) && this.isInsideTeamFolder == other.isInsideTeamFolder && this.isTeamFolder == other.isTeamFolder && ((this.name == other.name || this.name.equals(other.name)) && ((this.policy == other.policy || this.policy.equals(other.policy)) && ((this.previewUrl == other.previewUrl || this.previewUrl.equals(other.previewUrl)) && ((this.sharedFolderId == other.sharedFolderId || this.sharedFolderId.equals(other.sharedFolderId)) && ((this.timeInvited == other.timeInvited || this.timeInvited.equals(other.timeInvited)) && ((this.ownerTeam == other.ownerTeam || (this.ownerTeam != null && this.ownerTeam.equals(other.ownerTeam))) && ((this.parentSharedFolderId == other.parentSharedFolderId || (this.parentSharedFolderId != null && this.parentSharedFolderId.equals(other.parentSharedFolderId))) && ((this.pathLower == other.pathLower || (this.pathLower != null && this.pathLower.equals(other.pathLower))) && (this.linkMetadata == other.linkMetadata || (this.linkMetadata != null && this.linkMetadata.equals(other.linkMetadata)))))))))))) {
            if (this.permissions == other.permissions) {
                return true;
            }
            if (this.permissions != null && this.permissions.equals(other.permissions)) {
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
