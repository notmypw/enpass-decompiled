package com.dropbox.core.v2.files;

import com.box.androidsdk.content.models.BoxEntity;
import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.models.BoxFolder;
import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.v2.properties.PropertyGroup;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class FolderMetadata extends Metadata {
    protected final String id;
    protected final List<PropertyGroup> propertyGroups;
    protected final String sharedFolderId;
    protected final FolderSharingInfo sharingInfo;

    public static class Builder extends com.dropbox.core.v2.files.Metadata.Builder {
        protected final String id;
        protected List<PropertyGroup> propertyGroups;
        protected String sharedFolderId;
        protected FolderSharingInfo sharingInfo;

        protected Builder(String name, String id) {
            super(name);
            if (id == null) {
                throw new IllegalArgumentException("Required value for 'id' is null");
            } else if (id.length() < 1) {
                throw new IllegalArgumentException("String 'id' is shorter than 1");
            } else {
                this.id = id;
                this.sharedFolderId = null;
                this.sharingInfo = null;
                this.propertyGroups = null;
            }
        }

        public Builder withSharedFolderId(String sharedFolderId) {
            if (sharedFolderId == null || Pattern.matches("[-_0-9a-zA-Z:]+", sharedFolderId)) {
                this.sharedFolderId = sharedFolderId;
                return this;
            }
            throw new IllegalArgumentException("String 'sharedFolderId' does not match pattern");
        }

        public Builder withSharingInfo(FolderSharingInfo sharingInfo) {
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

        public FolderMetadata build() {
            return new FolderMetadata(this.name, this.id, this.pathLower, this.pathDisplay, this.parentSharedFolderId, this.sharedFolderId, this.sharingInfo, this.propertyGroups);
        }
    }

    static class Serializer extends StructSerializer<FolderMetadata> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(FolderMetadata value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            writeTag(BoxFolder.TYPE, g);
            g.writeFieldName(BoxFileVersion.FIELD_NAME);
            StoneSerializers.string().serialize(value.name, g);
            g.writeFieldName(BoxEntity.FIELD_ID);
            StoneSerializers.string().serialize(value.id, g);
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
            if (value.sharedFolderId != null) {
                g.writeFieldName("shared_folder_id");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.sharedFolderId, g);
            }
            if (value.sharingInfo != null) {
                g.writeFieldName("sharing_info");
                StoneSerializers.nullableStruct(Serializer.INSTANCE).serialize(value.sharingInfo, g);
            }
            if (value.propertyGroups != null) {
                g.writeFieldName("property_groups");
                StoneSerializers.nullable(StoneSerializers.list(com.dropbox.core.v2.properties.PropertyGroup.Serializer.INSTANCE)).serialize(value.propertyGroups, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public FolderMetadata deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
                if (BoxFolder.TYPE.equals(tag)) {
                    tag = null;
                }
            }
            if (tag == null) {
                String f_name = null;
                String f_id = null;
                String f_pathLower = null;
                String f_pathDisplay = null;
                String f_parentSharedFolderId = null;
                String f_sharedFolderId = null;
                FolderSharingInfo f_sharingInfo = null;
                List<PropertyGroup> f_propertyGroups = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxFileVersion.FIELD_NAME.equals(field)) {
                        f_name = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxEntity.FIELD_ID.equals(field)) {
                        f_id = (String) StoneSerializers.string().deserialize(p);
                    } else if ("path_lower".equals(field)) {
                        f_pathLower = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("path_display".equals(field)) {
                        f_pathDisplay = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("parent_shared_folder_id".equals(field)) {
                        f_parentSharedFolderId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("shared_folder_id".equals(field)) {
                        f_sharedFolderId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("sharing_info".equals(field)) {
                        f_sharingInfo = (FolderSharingInfo) StoneSerializers.nullableStruct(Serializer.INSTANCE).deserialize(p);
                    } else if ("property_groups".equals(field)) {
                        f_propertyGroups = (List) StoneSerializers.nullable(StoneSerializers.list(com.dropbox.core.v2.properties.PropertyGroup.Serializer.INSTANCE)).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_name == null) {
                    throw new JsonParseException(p, "Required field \"name\" missing.");
                } else if (f_id == null) {
                    throw new JsonParseException(p, "Required field \"id\" missing.");
                } else {
                    FolderMetadata value = new FolderMetadata(f_name, f_id, f_pathLower, f_pathDisplay, f_parentSharedFolderId, f_sharedFolderId, f_sharingInfo, f_propertyGroups);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public FolderMetadata(String name, String id, String pathLower, String pathDisplay, String parentSharedFolderId, String sharedFolderId, FolderSharingInfo sharingInfo, List<PropertyGroup> propertyGroups) {
        super(name, pathLower, pathDisplay, parentSharedFolderId);
        if (id == null) {
            throw new IllegalArgumentException("Required value for 'id' is null");
        } else if (id.length() < 1) {
            throw new IllegalArgumentException("String 'id' is shorter than 1");
        } else {
            this.id = id;
            if (sharedFolderId == null || Pattern.matches("[-_0-9a-zA-Z:]+", sharedFolderId)) {
                this.sharedFolderId = sharedFolderId;
                this.sharingInfo = sharingInfo;
                if (propertyGroups != null) {
                    for (PropertyGroup x : propertyGroups) {
                        if (x == null) {
                            throw new IllegalArgumentException("An item in list 'propertyGroups' is null");
                        }
                    }
                }
                this.propertyGroups = propertyGroups;
                return;
            }
            throw new IllegalArgumentException("String 'sharedFolderId' does not match pattern");
        }
    }

    public FolderMetadata(String name, String id) {
        this(name, id, null, null, null, null, null, null);
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
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

    public String getSharedFolderId() {
        return this.sharedFolderId;
    }

    public FolderSharingInfo getSharingInfo() {
        return this.sharingInfo;
    }

    public List<PropertyGroup> getPropertyGroups() {
        return this.propertyGroups;
    }

    public static Builder newBuilder(String name, String id) {
        return new Builder(name, id);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.id, this.sharedFolderId, this.sharingInfo, this.propertyGroups}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        FolderMetadata other = (FolderMetadata) obj;
        if ((this.name == other.name || this.name.equals(other.name)) && ((this.id == other.id || this.id.equals(other.id)) && ((this.pathLower == other.pathLower || (this.pathLower != null && this.pathLower.equals(other.pathLower))) && ((this.pathDisplay == other.pathDisplay || (this.pathDisplay != null && this.pathDisplay.equals(other.pathDisplay))) && ((this.parentSharedFolderId == other.parentSharedFolderId || (this.parentSharedFolderId != null && this.parentSharedFolderId.equals(other.parentSharedFolderId))) && ((this.sharedFolderId == other.sharedFolderId || (this.sharedFolderId != null && this.sharedFolderId.equals(other.sharedFolderId))) && (this.sharingInfo == other.sharingInfo || (this.sharingInfo != null && this.sharingInfo.equals(other.sharingInfo))))))))) {
            if (this.propertyGroups == other.propertyGroups) {
                return true;
            }
            if (this.propertyGroups != null && this.propertyGroups.equals(other.propertyGroups)) {
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
