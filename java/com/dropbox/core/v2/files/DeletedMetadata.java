package com.dropbox.core.v2.files;

import com.box.androidsdk.content.models.BoxFileVersion;
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

public class DeletedMetadata extends Metadata {

    public static class Builder extends com.dropbox.core.v2.files.Metadata.Builder {
        protected Builder(String name) {
            super(name);
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

        public DeletedMetadata build() {
            return new DeletedMetadata(this.name, this.pathLower, this.pathDisplay, this.parentSharedFolderId);
        }
    }

    static class Serializer extends StructSerializer<DeletedMetadata> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(DeletedMetadata value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            writeTag("deleted", g);
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

        public DeletedMetadata deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
                if ("deleted".equals(tag)) {
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
                DeletedMetadata value = new DeletedMetadata(f_name, f_pathLower, f_pathDisplay, f_parentSharedFolderId);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public DeletedMetadata(String name, String pathLower, String pathDisplay, String parentSharedFolderId) {
        super(name, pathLower, pathDisplay, parentSharedFolderId);
    }

    public DeletedMetadata(String name) {
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
        return getClass().toString().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        DeletedMetadata other = (DeletedMetadata) obj;
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
