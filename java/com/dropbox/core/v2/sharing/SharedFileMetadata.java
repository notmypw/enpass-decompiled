package com.dropbox.core.v2.sharing;

import com.box.androidsdk.content.models.BoxEntity;
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

public class SharedFileMetadata {
    protected final String id;
    protected final SharedContentLinkMetadata linkMetadata;
    protected final String name;
    protected final Team ownerTeam;
    protected final String parentSharedFolderId;
    protected final String pathDisplay;
    protected final String pathLower;
    protected final List<FilePermission> permissions;
    protected final FolderPolicy policy;
    protected final String previewUrl;
    protected final Date timeInvited;

    public static class Builder {
        protected final String id;
        protected SharedContentLinkMetadata linkMetadata;
        protected final String name;
        protected Team ownerTeam;
        protected String parentSharedFolderId;
        protected String pathDisplay;
        protected String pathLower;
        protected List<FilePermission> permissions;
        protected final FolderPolicy policy;
        protected final String previewUrl;
        protected Date timeInvited;

        protected Builder(FolderPolicy policy, String previewUrl, String name, String id) {
            if (policy == null) {
                throw new IllegalArgumentException("Required value for 'policy' is null");
            }
            this.policy = policy;
            if (previewUrl == null) {
                throw new IllegalArgumentException("Required value for 'previewUrl' is null");
            }
            this.previewUrl = previewUrl;
            if (name == null) {
                throw new IllegalArgumentException("Required value for 'name' is null");
            }
            this.name = name;
            if (id == null) {
                throw new IllegalArgumentException("Required value for 'id' is null");
            } else if (id.length() < 1) {
                throw new IllegalArgumentException("String 'id' is shorter than 1");
            } else if (Pattern.matches("id:.*", id)) {
                this.id = id;
                this.linkMetadata = null;
                this.permissions = null;
                this.ownerTeam = null;
                this.parentSharedFolderId = null;
                this.pathLower = null;
                this.pathDisplay = null;
                this.timeInvited = null;
            } else {
                throw new IllegalArgumentException("String 'id' does not match pattern");
            }
        }

        public Builder withLinkMetadata(SharedContentLinkMetadata linkMetadata) {
            this.linkMetadata = linkMetadata;
            return this;
        }

        public Builder withPermissions(List<FilePermission> permissions) {
            if (permissions != null) {
                for (FilePermission x : permissions) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'permissions' is null");
                    }
                }
            }
            this.permissions = permissions;
            return this;
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

        public Builder withPathDisplay(String pathDisplay) {
            this.pathDisplay = pathDisplay;
            return this;
        }

        public Builder withTimeInvited(Date timeInvited) {
            this.timeInvited = LangUtil.truncateMillis(timeInvited);
            return this;
        }

        public SharedFileMetadata build() {
            return new SharedFileMetadata(this.policy, this.previewUrl, this.name, this.id, this.linkMetadata, this.permissions, this.ownerTeam, this.parentSharedFolderId, this.pathLower, this.pathDisplay, this.timeInvited);
        }
    }

    static class Serializer extends StructSerializer<SharedFileMetadata> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(SharedFileMetadata value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("policy");
            Serializer.INSTANCE.serialize(value.policy, g);
            g.writeFieldName("preview_url");
            StoneSerializers.string().serialize(value.previewUrl, g);
            g.writeFieldName(BoxFileVersion.FIELD_NAME);
            StoneSerializers.string().serialize(value.name, g);
            g.writeFieldName(BoxEntity.FIELD_ID);
            StoneSerializers.string().serialize(value.id, g);
            if (value.linkMetadata != null) {
                g.writeFieldName("link_metadata");
                StoneSerializers.nullableStruct(Serializer.INSTANCE).serialize(value.linkMetadata, g);
            }
            if (value.permissions != null) {
                g.writeFieldName(BoxSharedLink.FIELD_PERMISSIONS);
                StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).serialize(value.permissions, g);
            }
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
            if (value.pathDisplay != null) {
                g.writeFieldName("path_display");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.pathDisplay, g);
            }
            if (value.timeInvited != null) {
                g.writeFieldName("time_invited");
                StoneSerializers.nullable(StoneSerializers.timestamp()).serialize(value.timeInvited, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public SharedFileMetadata deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                FolderPolicy f_policy = null;
                String f_previewUrl = null;
                String f_name = null;
                String f_id = null;
                SharedContentLinkMetadata f_linkMetadata = null;
                List<FilePermission> f_permissions = null;
                Team f_ownerTeam = null;
                String f_parentSharedFolderId = null;
                String f_pathLower = null;
                String f_pathDisplay = null;
                Date f_timeInvited = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("policy".equals(field)) {
                        f_policy = (FolderPolicy) Serializer.INSTANCE.deserialize(p);
                    } else if ("preview_url".equals(field)) {
                        f_previewUrl = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxFileVersion.FIELD_NAME.equals(field)) {
                        f_name = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxEntity.FIELD_ID.equals(field)) {
                        f_id = (String) StoneSerializers.string().deserialize(p);
                    } else if ("link_metadata".equals(field)) {
                        f_linkMetadata = (SharedContentLinkMetadata) StoneSerializers.nullableStruct(Serializer.INSTANCE).deserialize(p);
                    } else if (BoxSharedLink.FIELD_PERMISSIONS.equals(field)) {
                        f_permissions = (List) StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).deserialize(p);
                    } else if ("owner_team".equals(field)) {
                        f_ownerTeam = (Team) StoneSerializers.nullableStruct(com.dropbox.core.v2.users.Team.Serializer.INSTANCE).deserialize(p);
                    } else if ("parent_shared_folder_id".equals(field)) {
                        f_parentSharedFolderId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("path_lower".equals(field)) {
                        f_pathLower = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("path_display".equals(field)) {
                        f_pathDisplay = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("time_invited".equals(field)) {
                        f_timeInvited = (Date) StoneSerializers.nullable(StoneSerializers.timestamp()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_policy == null) {
                    throw new JsonParseException(p, "Required field \"policy\" missing.");
                } else if (f_previewUrl == null) {
                    throw new JsonParseException(p, "Required field \"preview_url\" missing.");
                } else if (f_name == null) {
                    throw new JsonParseException(p, "Required field \"name\" missing.");
                } else if (f_id == null) {
                    throw new JsonParseException(p, "Required field \"id\" missing.");
                } else {
                    SharedFileMetadata value = new SharedFileMetadata(f_policy, f_previewUrl, f_name, f_id, f_linkMetadata, f_permissions, f_ownerTeam, f_parentSharedFolderId, f_pathLower, f_pathDisplay, f_timeInvited);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public SharedFileMetadata(FolderPolicy policy, String previewUrl, String name, String id, SharedContentLinkMetadata linkMetadata, List<FilePermission> permissions, Team ownerTeam, String parentSharedFolderId, String pathLower, String pathDisplay, Date timeInvited) {
        this.linkMetadata = linkMetadata;
        if (policy == null) {
            throw new IllegalArgumentException("Required value for 'policy' is null");
        }
        this.policy = policy;
        if (permissions != null) {
            for (FilePermission x : permissions) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'permissions' is null");
                }
            }
        }
        this.permissions = permissions;
        this.ownerTeam = ownerTeam;
        if (parentSharedFolderId == null || Pattern.matches("[-_0-9a-zA-Z:]+", parentSharedFolderId)) {
            this.parentSharedFolderId = parentSharedFolderId;
            if (previewUrl == null) {
                throw new IllegalArgumentException("Required value for 'previewUrl' is null");
            }
            this.previewUrl = previewUrl;
            this.pathLower = pathLower;
            this.pathDisplay = pathDisplay;
            if (name == null) {
                throw new IllegalArgumentException("Required value for 'name' is null");
            }
            this.name = name;
            if (id == null) {
                throw new IllegalArgumentException("Required value for 'id' is null");
            } else if (id.length() < 1) {
                throw new IllegalArgumentException("String 'id' is shorter than 1");
            } else if (Pattern.matches("id:.*", id)) {
                this.id = id;
                this.timeInvited = LangUtil.truncateMillis(timeInvited);
                return;
            } else {
                throw new IllegalArgumentException("String 'id' does not match pattern");
            }
        }
        throw new IllegalArgumentException("String 'parentSharedFolderId' does not match pattern");
    }

    public SharedFileMetadata(FolderPolicy policy, String previewUrl, String name, String id) {
        this(policy, previewUrl, name, id, null, null, null, null, null, null, null);
    }

    public FolderPolicy getPolicy() {
        return this.policy;
    }

    public String getPreviewUrl() {
        return this.previewUrl;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public SharedContentLinkMetadata getLinkMetadata() {
        return this.linkMetadata;
    }

    public List<FilePermission> getPermissions() {
        return this.permissions;
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

    public String getPathDisplay() {
        return this.pathDisplay;
    }

    public Date getTimeInvited() {
        return this.timeInvited;
    }

    public static Builder newBuilder(FolderPolicy policy, String previewUrl, String name, String id) {
        return new Builder(policy, previewUrl, name, id);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.linkMetadata, this.policy, this.permissions, this.ownerTeam, this.parentSharedFolderId, this.previewUrl, this.pathLower, this.pathDisplay, this.name, this.id, this.timeInvited});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        SharedFileMetadata other = (SharedFileMetadata) obj;
        if ((this.policy == other.policy || this.policy.equals(other.policy)) && ((this.previewUrl == other.previewUrl || this.previewUrl.equals(other.previewUrl)) && ((this.name == other.name || this.name.equals(other.name)) && ((this.id == other.id || this.id.equals(other.id)) && ((this.linkMetadata == other.linkMetadata || (this.linkMetadata != null && this.linkMetadata.equals(other.linkMetadata))) && ((this.permissions == other.permissions || (this.permissions != null && this.permissions.equals(other.permissions))) && ((this.ownerTeam == other.ownerTeam || (this.ownerTeam != null && this.ownerTeam.equals(other.ownerTeam))) && ((this.parentSharedFolderId == other.parentSharedFolderId || (this.parentSharedFolderId != null && this.parentSharedFolderId.equals(other.parentSharedFolderId))) && ((this.pathLower == other.pathLower || (this.pathLower != null && this.pathLower.equals(other.pathLower))) && (this.pathDisplay == other.pathDisplay || (this.pathDisplay != null && this.pathDisplay.equals(other.pathDisplay)))))))))))) {
            if (this.timeInvited == other.timeInvited) {
                return true;
            }
            if (this.timeInvited != null && this.timeInvited.equals(other.timeInvited)) {
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
