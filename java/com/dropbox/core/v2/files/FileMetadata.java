package com.dropbox.core.v2.files;

import com.box.androidsdk.content.models.BoxEntity;
import com.box.androidsdk.content.models.BoxFile;
import com.box.androidsdk.content.models.BoxFileVersion;
import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.util.LangUtil;
import com.dropbox.core.v2.properties.PropertyGroup;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import in.sinew.enpassengine.Attachment;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class FileMetadata extends Metadata {
    protected final Date clientModified;
    protected final String contentHash;
    protected final Boolean hasExplicitSharedMembers;
    protected final String id;
    protected final MediaInfo mediaInfo;
    protected final List<PropertyGroup> propertyGroups;
    protected final String rev;
    protected final Date serverModified;
    protected final FileSharingInfo sharingInfo;
    protected final long size;

    public static class Builder extends com.dropbox.core.v2.files.Metadata.Builder {
        protected final Date clientModified;
        protected String contentHash;
        protected Boolean hasExplicitSharedMembers;
        protected final String id;
        protected MediaInfo mediaInfo;
        protected List<PropertyGroup> propertyGroups;
        protected final String rev;
        protected final Date serverModified;
        protected FileSharingInfo sharingInfo;
        protected final long size;

        protected Builder(String name, String id, Date clientModified, Date serverModified, String rev, long size) {
            super(name);
            if (id == null) {
                throw new IllegalArgumentException("Required value for 'id' is null");
            } else if (id.length() < 1) {
                throw new IllegalArgumentException("String 'id' is shorter than 1");
            } else {
                this.id = id;
                if (clientModified == null) {
                    throw new IllegalArgumentException("Required value for 'clientModified' is null");
                }
                this.clientModified = LangUtil.truncateMillis(clientModified);
                if (serverModified == null) {
                    throw new IllegalArgumentException("Required value for 'serverModified' is null");
                }
                this.serverModified = LangUtil.truncateMillis(serverModified);
                if (rev == null) {
                    throw new IllegalArgumentException("Required value for 'rev' is null");
                } else if (rev.length() < 9) {
                    throw new IllegalArgumentException("String 'rev' is shorter than 9");
                } else if (Pattern.matches("[0-9a-f]+", rev)) {
                    this.rev = rev;
                    this.size = size;
                    this.mediaInfo = null;
                    this.sharingInfo = null;
                    this.propertyGroups = null;
                    this.hasExplicitSharedMembers = null;
                    this.contentHash = null;
                } else {
                    throw new IllegalArgumentException("String 'rev' does not match pattern");
                }
            }
        }

        public Builder withMediaInfo(MediaInfo mediaInfo) {
            this.mediaInfo = mediaInfo;
            return this;
        }

        public Builder withSharingInfo(FileSharingInfo sharingInfo) {
            this.sharingInfo = sharingInfo;
            return this;
        }

        public Builder withPropertyGroups(List<PropertyGroup> propertyGroups) {
            if (propertyGroups != null) {
                for (PropertyGroup x : propertyGroups) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'propertyGroups' is null");
                    }
                }
            }
            this.propertyGroups = propertyGroups;
            return this;
        }

        public Builder withHasExplicitSharedMembers(Boolean hasExplicitSharedMembers) {
            this.hasExplicitSharedMembers = hasExplicitSharedMembers;
            return this;
        }

        public Builder withContentHash(String contentHash) {
            if (contentHash != null) {
                if (contentHash.length() < 64) {
                    throw new IllegalArgumentException("String 'contentHash' is shorter than 64");
                } else if (contentHash.length() > 64) {
                    throw new IllegalArgumentException("String 'contentHash' is longer than 64");
                }
            }
            this.contentHash = contentHash;
            return this;
        }

        public Builder withPathLower(String pathLower) {
            super.withPathLower(pathLower);
            return this;
        }

        public Builder withPathDisplay(String pathDisplay) {
            super.withPathDisplay(pathDisplay);
            return this;
        }

        public Builder withParentSharedFolderId(String parentSharedFolderId) {
            super.withParentSharedFolderId(parentSharedFolderId);
            return this;
        }

        public FileMetadata build() {
            return new FileMetadata(this.name, this.id, this.clientModified, this.serverModified, this.rev, this.size, this.pathLower, this.pathDisplay, this.parentSharedFolderId, this.mediaInfo, this.sharingInfo, this.propertyGroups, this.hasExplicitSharedMembers, this.contentHash);
        }
    }

    static class Serializer extends StructSerializer<FileMetadata> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(FileMetadata value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            writeTag(BoxFile.TYPE, g);
            g.writeFieldName(BoxFileVersion.FIELD_NAME);
            StoneSerializers.string().serialize(value.name, g);
            g.writeFieldName(BoxEntity.FIELD_ID);
            StoneSerializers.string().serialize(value.id, g);
            g.writeFieldName("client_modified");
            StoneSerializers.timestamp().serialize(value.clientModified, g);
            g.writeFieldName("server_modified");
            StoneSerializers.timestamp().serialize(value.serverModified, g);
            g.writeFieldName("rev");
            StoneSerializers.string().serialize(value.rev, g);
            g.writeFieldName(Attachment.ATTACHMENT_SIZE);
            StoneSerializers.uInt64().serialize(Long.valueOf(value.size), g);
            if (value.pathLower != null) {
                g.writeFieldName("path_lower");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.pathLower, g);
            }
            if (value.pathDisplay != null) {
                g.writeFieldName("path_display");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.pathDisplay, g);
            }
            if (value.parentSharedFolderId != null) {
                g.writeFieldName("parent_shared_folder_id");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.parentSharedFolderId, g);
            }
            if (value.mediaInfo != null) {
                g.writeFieldName("media_info");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.mediaInfo, g);
            }
            if (value.sharingInfo != null) {
                g.writeFieldName("sharing_info");
                StoneSerializers.nullableStruct(Serializer.INSTANCE).serialize(value.sharingInfo, g);
            }
            if (value.propertyGroups != null) {
                g.writeFieldName("property_groups");
                StoneSerializers.nullable(StoneSerializers.list(com.dropbox.core.v2.properties.PropertyGroup.Serializer.INSTANCE)).serialize(value.propertyGroups, g);
            }
            if (value.hasExplicitSharedMembers != null) {
                g.writeFieldName("has_explicit_shared_members");
                StoneSerializers.nullable(StoneSerializers.boolean_()).serialize(value.hasExplicitSharedMembers, g);
            }
            if (value.contentHash != null) {
                g.writeFieldName("content_hash");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.contentHash, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public FileMetadata deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
                if (BoxFile.TYPE.equals(tag)) {
                    tag = null;
                }
            }
            if (tag == null) {
                String f_name = null;
                String f_id = null;
                Date f_clientModified = null;
                Date f_serverModified = null;
                String f_rev = null;
                Long f_size = null;
                String f_pathLower = null;
                String f_pathDisplay = null;
                String f_parentSharedFolderId = null;
                MediaInfo f_mediaInfo = null;
                FileSharingInfo f_sharingInfo = null;
                List<PropertyGroup> f_propertyGroups = null;
                Boolean f_hasExplicitSharedMembers = null;
                String f_contentHash = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxFileVersion.FIELD_NAME.equals(field)) {
                        f_name = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxEntity.FIELD_ID.equals(field)) {
                        f_id = (String) StoneSerializers.string().deserialize(p);
                    } else if ("client_modified".equals(field)) {
                        f_clientModified = (Date) StoneSerializers.timestamp().deserialize(p);
                    } else if ("server_modified".equals(field)) {
                        f_serverModified = (Date) StoneSerializers.timestamp().deserialize(p);
                    } else if ("rev".equals(field)) {
                        f_rev = (String) StoneSerializers.string().deserialize(p);
                    } else if (Attachment.ATTACHMENT_SIZE.equals(field)) {
                        f_size = (Long) StoneSerializers.uInt64().deserialize(p);
                    } else if ("path_lower".equals(field)) {
                        f_pathLower = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("path_display".equals(field)) {
                        f_pathDisplay = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("parent_shared_folder_id".equals(field)) {
                        f_parentSharedFolderId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("media_info".equals(field)) {
                        f_mediaInfo = (MediaInfo) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else if ("sharing_info".equals(field)) {
                        f_sharingInfo = (FileSharingInfo) StoneSerializers.nullableStruct(Serializer.INSTANCE).deserialize(p);
                    } else if ("property_groups".equals(field)) {
                        f_propertyGroups = (List) StoneSerializers.nullable(StoneSerializers.list(com.dropbox.core.v2.properties.PropertyGroup.Serializer.INSTANCE)).deserialize(p);
                    } else if ("has_explicit_shared_members".equals(field)) {
                        f_hasExplicitSharedMembers = (Boolean) StoneSerializers.nullable(StoneSerializers.boolean_()).deserialize(p);
                    } else if ("content_hash".equals(field)) {
                        f_contentHash = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_name == null) {
                    throw new JsonParseException(p, "Required field \"name\" missing.");
                } else if (f_id == null) {
                    throw new JsonParseException(p, "Required field \"id\" missing.");
                } else if (f_clientModified == null) {
                    throw new JsonParseException(p, "Required field \"client_modified\" missing.");
                } else if (f_serverModified == null) {
                    throw new JsonParseException(p, "Required field \"server_modified\" missing.");
                } else if (f_rev == null) {
                    throw new JsonParseException(p, "Required field \"rev\" missing.");
                } else if (f_size == null) {
                    throw new JsonParseException(p, "Required field \"size\" missing.");
                } else {
                    FileMetadata value = new FileMetadata(f_name, f_id, f_clientModified, f_serverModified, f_rev, f_size.longValue(), f_pathLower, f_pathDisplay, f_parentSharedFolderId, f_mediaInfo, f_sharingInfo, f_propertyGroups, f_hasExplicitSharedMembers, f_contentHash);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public FileMetadata(String name, String id, Date clientModified, Date serverModified, String rev, long size, String pathLower, String pathDisplay, String parentSharedFolderId, MediaInfo mediaInfo, FileSharingInfo sharingInfo, List<PropertyGroup> propertyGroups, Boolean hasExplicitSharedMembers, String contentHash) {
        super(name, pathLower, pathDisplay, parentSharedFolderId);
        if (id == null) {
            throw new IllegalArgumentException("Required value for 'id' is null");
        } else if (id.length() < 1) {
            throw new IllegalArgumentException("String 'id' is shorter than 1");
        } else {
            this.id = id;
            if (clientModified == null) {
                throw new IllegalArgumentException("Required value for 'clientModified' is null");
            }
            this.clientModified = LangUtil.truncateMillis(clientModified);
            if (serverModified == null) {
                throw new IllegalArgumentException("Required value for 'serverModified' is null");
            }
            this.serverModified = LangUtil.truncateMillis(serverModified);
            if (rev == null) {
                throw new IllegalArgumentException("Required value for 'rev' is null");
            } else if (rev.length() < 9) {
                throw new IllegalArgumentException("String 'rev' is shorter than 9");
            } else if (Pattern.matches("[0-9a-f]+", rev)) {
                this.rev = rev;
                this.size = size;
                this.mediaInfo = mediaInfo;
                this.sharingInfo = sharingInfo;
                if (propertyGroups != null) {
                    for (PropertyGroup x : propertyGroups) {
                        if (x == null) {
                            throw new IllegalArgumentException("An item in list 'propertyGroups' is null");
                        }
                    }
                }
                this.propertyGroups = propertyGroups;
                this.hasExplicitSharedMembers = hasExplicitSharedMembers;
                if (contentHash != null) {
                    if (contentHash.length() < 64) {
                        throw new IllegalArgumentException("String 'contentHash' is shorter than 64");
                    } else if (contentHash.length() > 64) {
                        throw new IllegalArgumentException("String 'contentHash' is longer than 64");
                    }
                }
                this.contentHash = contentHash;
            } else {
                throw new IllegalArgumentException("String 'rev' does not match pattern");
            }
        }
    }

    public FileMetadata(String name, String id, Date clientModified, Date serverModified, String rev, long size) {
        this(name, id, clientModified, serverModified, rev, size, null, null, null, null, null, null, null, null);
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public Date getClientModified() {
        return this.clientModified;
    }

    public Date getServerModified() {
        return this.serverModified;
    }

    public String getRev() {
        return this.rev;
    }

    public long getSize() {
        return this.size;
    }

    public String getPathLower() {
        return this.pathLower;
    }

    public String getPathDisplay() {
        return this.pathDisplay;
    }

    public String getParentSharedFolderId() {
        return this.parentSharedFolderId;
    }

    public MediaInfo getMediaInfo() {
        return this.mediaInfo;
    }

    public FileSharingInfo getSharingInfo() {
        return this.sharingInfo;
    }

    public List<PropertyGroup> getPropertyGroups() {
        return this.propertyGroups;
    }

    public Boolean getHasExplicitSharedMembers() {
        return this.hasExplicitSharedMembers;
    }

    public String getContentHash() {
        return this.contentHash;
    }

    public static Builder newBuilder(String name, String id, Date clientModified, Date serverModified, String rev, long size) {
        return new Builder(name, id, clientModified, serverModified, rev, size);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.id, this.clientModified, this.serverModified, this.rev, Long.valueOf(this.size), this.mediaInfo, this.sharingInfo, this.propertyGroups, this.hasExplicitSharedMembers, this.contentHash}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        FileMetadata other = (FileMetadata) obj;
        if ((this.name == other.name || this.name.equals(other.name)) && ((this.id == other.id || this.id.equals(other.id)) && ((this.clientModified == other.clientModified || this.clientModified.equals(other.clientModified)) && ((this.serverModified == other.serverModified || this.serverModified.equals(other.serverModified)) && ((this.rev == other.rev || this.rev.equals(other.rev)) && this.size == other.size && ((this.pathLower == other.pathLower || (this.pathLower != null && this.pathLower.equals(other.pathLower))) && ((this.pathDisplay == other.pathDisplay || (this.pathDisplay != null && this.pathDisplay.equals(other.pathDisplay))) && ((this.parentSharedFolderId == other.parentSharedFolderId || (this.parentSharedFolderId != null && this.parentSharedFolderId.equals(other.parentSharedFolderId))) && ((this.mediaInfo == other.mediaInfo || (this.mediaInfo != null && this.mediaInfo.equals(other.mediaInfo))) && ((this.sharingInfo == other.sharingInfo || (this.sharingInfo != null && this.sharingInfo.equals(other.sharingInfo))) && ((this.propertyGroups == other.propertyGroups || (this.propertyGroups != null && this.propertyGroups.equals(other.propertyGroups))) && (this.hasExplicitSharedMembers == other.hasExplicitSharedMembers || (this.hasExplicitSharedMembers != null && this.hasExplicitSharedMembers.equals(other.hasExplicitSharedMembers)))))))))))))) {
            if (this.contentHash == other.contentHash) {
                return true;
            }
            if (this.contentHash != null && this.contentHash.equals(other.contentHash)) {
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
