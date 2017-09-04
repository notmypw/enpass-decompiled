package com.dropbox.core.v2.paper;

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

public class FoldersContainingPaperDoc {
    protected final FolderSharingPolicyType folderSharingPolicyType;
    protected final List<Folder> folders;

    public static class Builder {
        protected FolderSharingPolicyType folderSharingPolicyType = null;
        protected List<Folder> folders = null;

        protected Builder() {
        }

        public Builder withFolderSharingPolicyType(FolderSharingPolicyType folderSharingPolicyType) {
            this.folderSharingPolicyType = folderSharingPolicyType;
            return this;
        }

        public Builder withFolders(List<Folder> folders) {
            if (folders != null) {
                for (Folder x : folders) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'folders' is null");
                    }
                }
            }
            this.folders = folders;
            return this;
        }

        public FoldersContainingPaperDoc build() {
            return new FoldersContainingPaperDoc(this.folderSharingPolicyType, this.folders);
        }
    }

    static class Serializer extends StructSerializer<FoldersContainingPaperDoc> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(FoldersContainingPaperDoc value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            if (value.folderSharingPolicyType != null) {
                g.writeFieldName("folder_sharing_policy_type");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.folderSharingPolicyType, g);
            }
            if (value.folders != null) {
                g.writeFieldName("folders");
                StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).serialize(value.folders, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public FoldersContainingPaperDoc deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                FolderSharingPolicyType f_folderSharingPolicyType = null;
                List<Folder> f_folders = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("folder_sharing_policy_type".equals(field)) {
                        f_folderSharingPolicyType = (FolderSharingPolicyType) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else if ("folders".equals(field)) {
                        f_folders = (List) StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                FoldersContainingPaperDoc value = new FoldersContainingPaperDoc(f_folderSharingPolicyType, f_folders);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public FoldersContainingPaperDoc(FolderSharingPolicyType folderSharingPolicyType, List<Folder> folders) {
        this.folderSharingPolicyType = folderSharingPolicyType;
        if (folders != null) {
            for (Folder x : folders) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'folders' is null");
                }
            }
        }
        this.folders = folders;
    }

    public FoldersContainingPaperDoc() {
        this(null, null);
    }

    public FolderSharingPolicyType getFolderSharingPolicyType() {
        return this.folderSharingPolicyType;
    }

    public List<Folder> getFolders() {
        return this.folders;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.folderSharingPolicyType, this.folders});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        FoldersContainingPaperDoc other = (FoldersContainingPaperDoc) obj;
        if (this.folderSharingPolicyType == other.folderSharingPolicyType || (this.folderSharingPolicyType != null && this.folderSharingPolicyType.equals(other.folderSharingPolicyType))) {
            if (this.folders == other.folders) {
                return true;
            }
            if (this.folders != null && this.folders.equals(other.folders)) {
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
