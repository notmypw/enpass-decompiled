package com.dropbox.core.v2.sharing;

import com.box.androidsdk.content.models.BoxSharedLink;
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

public class ParentFolderAccessInfo {
    protected final String folderName;
    protected final List<MemberPermission> permissions;
    protected final String sharedFolderId;

    static class Serializer extends StructSerializer<ParentFolderAccessInfo> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ParentFolderAccessInfo value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("folder_name");
            StoneSerializers.string().serialize(value.folderName, g);
            g.writeFieldName("shared_folder_id");
            StoneSerializers.string().serialize(value.sharedFolderId, g);
            g.writeFieldName(BoxSharedLink.FIELD_PERMISSIONS);
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.permissions, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ParentFolderAccessInfo deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_folderName = null;
                String f_sharedFolderId = null;
                List<MemberPermission> f_permissions = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("folder_name".equals(field)) {
                        f_folderName = (String) StoneSerializers.string().deserialize(p);
                    } else if ("shared_folder_id".equals(field)) {
                        f_sharedFolderId = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxSharedLink.FIELD_PERMISSIONS.equals(field)) {
                        f_permissions = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_folderName == null) {
                    throw new JsonParseException(p, "Required field \"folder_name\" missing.");
                } else if (f_sharedFolderId == null) {
                    throw new JsonParseException(p, "Required field \"shared_folder_id\" missing.");
                } else if (f_permissions == null) {
                    throw new JsonParseException(p, "Required field \"permissions\" missing.");
                } else {
                    ParentFolderAccessInfo value = new ParentFolderAccessInfo(f_folderName, f_sharedFolderId, f_permissions);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ParentFolderAccessInfo(String folderName, String sharedFolderId, List<MemberPermission> permissions) {
        if (folderName == null) {
            throw new IllegalArgumentException("Required value for 'folderName' is null");
        }
        this.folderName = folderName;
        if (sharedFolderId == null) {
            throw new IllegalArgumentException("Required value for 'sharedFolderId' is null");
        } else if (Pattern.matches("[-_0-9a-zA-Z:]+", sharedFolderId)) {
            this.sharedFolderId = sharedFolderId;
            if (permissions == null) {
                throw new IllegalArgumentException("Required value for 'permissions' is null");
            }
            for (MemberPermission x : permissions) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'permissions' is null");
                }
            }
            this.permissions = permissions;
        } else {
            throw new IllegalArgumentException("String 'sharedFolderId' does not match pattern");
        }
    }

    public String getFolderName() {
        return this.folderName;
    }

    public String getSharedFolderId() {
        return this.sharedFolderId;
    }

    public List<MemberPermission> getPermissions() {
        return this.permissions;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.folderName, this.sharedFolderId, this.permissions});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ParentFolderAccessInfo other = (ParentFolderAccessInfo) obj;
        if ((this.folderName == other.folderName || this.folderName.equals(other.folderName)) && ((this.sharedFolderId == other.sharedFolderId || this.sharedFolderId.equals(other.sharedFolderId)) && (this.permissions == other.permissions || this.permissions.equals(other.permissions)))) {
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
