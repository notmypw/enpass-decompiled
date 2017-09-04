package com.dropbox.core.v2.files;

import com.box.androidsdk.content.models.BoxFile;
import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.models.BoxFolder;
import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.github.clans.fab.BuildConfig;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Metadata {
    protected final String name;
    protected final String parentSharedFolderId;
    protected final String pathDisplay;
    protected final String pathLower;

    public static class Builder {
        protected final String name;
        protected String parentSharedFolderId;
        protected String pathDisplay;
        protected String pathLower;

        protected Builder(String name) {
            if (name == null) {
                throw new IllegalArgumentException("Required value for 'name' is null");
            }
            this.name = name;
            this.pathLower = null;
            this.pathDisplay = null;
            this.parentSharedFolderId = null;
        }

        public Builder withPathLower(String pathLower) {
            this.pathLower = pathLower;
            return this;
        }

        public Builder withPathDisplay(String pathDisplay) {
            this.pathDisplay = pathDisplay;
            return this;
        }

        public Builder withParentSharedFolderId(String parentSharedFolderId) {
            if (parentSharedFolderId == null || Pattern.matches("[-_0-9a-zA-Z:]+", parentSharedFolderId)) {
                this.parentSharedFolderId = parentSharedFolderId;
                return this;
            }
            throw new IllegalArgumentException("String 'parentSharedFolderId' does not match pattern");
        }

        public Metadata build() {
            return new Metadata(this.name, this.pathLower, this.pathDisplay, this.parentSharedFolderId);
        }
    }

    static class Serializer extends StructSerializer<Metadata> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(Metadata value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (value instanceof FileMetadata) {
                Serializer.INSTANCE.serialize((FileMetadata) value, g, collapse);
            } else if (value instanceof FolderMetadata) {
                Serializer.INSTANCE.serialize((FolderMetadata) value, g, collapse);
            } else if (value instanceof DeletedMetadata) {
                Serializer.INSTANCE.serialize((DeletedMetadata) value, g, collapse);
            } else {
                if (!collapse) {
                    g.writeStartObject();
                }
                g.writeFieldName(BoxFileVersion.FIELD_NAME);
                StoneSerializers.string().serialize(value.name, g);
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
                if (!collapse) {
                    g.writeEndObject();
                }
            }
        }

        public Metadata deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            Metadata value;
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
                if (BuildConfig.FLAVOR.equals(tag)) {
                    tag = null;
                }
            }
            if (tag == null) {
                String f_name = null;
                String f_pathLower = null;
                String f_pathDisplay = null;
                String f_parentSharedFolderId = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxFileVersion.FIELD_NAME.equals(field)) {
                        f_name = (String) StoneSerializers.string().deserialize(p);
                    } else if ("path_lower".equals(field)) {
                        f_pathLower = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("path_display".equals(field)) {
                        f_pathDisplay = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("parent_shared_folder_id".equals(field)) {
                        f_parentSharedFolderId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_name == null) {
                    throw new JsonParseException(p, "Required field \"name\" missing.");
                }
                value = new Metadata(f_name, f_pathLower, f_pathDisplay, f_parentSharedFolderId);
            } else if (BuildConfig.FLAVOR.equals(tag)) {
                value = INSTANCE.deserialize(p, true);
            } else if (BoxFile.TYPE.equals(tag)) {
                value = Serializer.INSTANCE.deserialize(p, true);
            } else if (BoxFolder.TYPE.equals(tag)) {
                value = Serializer.INSTANCE.deserialize(p, true);
            } else if ("deleted".equals(tag)) {
                value = Serializer.INSTANCE.deserialize(p, true);
            } else {
                throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
            }
            if (!collapsed) {
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public Metadata(String name, String pathLower, String pathDisplay, String parentSharedFolderId) {
        if (name == null) {
            throw new IllegalArgumentException("Required value for 'name' is null");
        }
        this.name = name;
        this.pathLower = pathLower;
        this.pathDisplay = pathDisplay;
        if (parentSharedFolderId == null || Pattern.matches("[-_0-9a-zA-Z:]+", parentSharedFolderId)) {
            this.parentSharedFolderId = parentSharedFolderId;
            return;
        }
        throw new IllegalArgumentException("String 'parentSharedFolderId' does not match pattern");
    }

    public Metadata(String name) {
        this(name, null, null, null);
    }

    public String getName() {
        return this.name;
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

    public static Builder newBuilder(String name) {
        return new Builder(name);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.name, this.pathLower, this.pathDisplay, this.parentSharedFolderId});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        Metadata other = (Metadata) obj;
        if ((this.name == other.name || this.name.equals(other.name)) && ((this.pathLower == other.pathLower || (this.pathLower != null && this.pathLower.equals(other.pathLower))) && (this.pathDisplay == other.pathDisplay || (this.pathDisplay != null && this.pathDisplay.equals(other.pathDisplay))))) {
            if (this.parentSharedFolderId == other.parentSharedFolderId) {
                return true;
            }
            if (this.parentSharedFolderId != null && this.parentSharedFolderId.equals(other.parentSharedFolderId)) {
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
