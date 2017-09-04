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
import java.util.Arrays;
import java.util.regex.Pattern;

public class FileSharingInfo extends SharingInfo {
    protected final String modifiedBy;
    protected final String parentSharedFolderId;

    static class Serializer extends StructSerializer<FileSharingInfo> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(FileSharingInfo value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("read_only");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.readOnly), g);
            g.writeFieldName("parent_shared_folder_id");
            StoneSerializers.string().serialize(value.parentSharedFolderId, g);
            if (value.modifiedBy != null) {
                g.writeFieldName(BoxFileVersion.FIELD_MODIFIED_BY);
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.modifiedBy, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public FileSharingInfo deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Boolean f_readOnly = null;
                String f_parentSharedFolderId = null;
                String f_modifiedBy = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("read_only".equals(field)) {
                        f_readOnly = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("parent_shared_folder_id".equals(field)) {
                        f_parentSharedFolderId = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxFileVersion.FIELD_MODIFIED_BY.equals(field)) {
                        f_modifiedBy = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_readOnly == null) {
                    throw new JsonParseException(p, "Required field \"read_only\" missing.");
                } else if (f_parentSharedFolderId == null) {
                    throw new JsonParseException(p, "Required field \"parent_shared_folder_id\" missing.");
                } else {
                    FileSharingInfo value = new FileSharingInfo(f_readOnly.booleanValue(), f_parentSharedFolderId, f_modifiedBy);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public FileSharingInfo(boolean readOnly, String parentSharedFolderId, String modifiedBy) {
        super(readOnly);
        if (parentSharedFolderId == null) {
            throw new IllegalArgumentException("Required value for 'parentSharedFolderId' is null");
        } else if (Pattern.matches("[-_0-9a-zA-Z:]+", parentSharedFolderId)) {
            this.parentSharedFolderId = parentSharedFolderId;
            if (modifiedBy != null) {
                if (modifiedBy.length() < 40) {
                    throw new IllegalArgumentException("String 'modifiedBy' is shorter than 40");
                } else if (modifiedBy.length() > 40) {
                    throw new IllegalArgumentException("String 'modifiedBy' is longer than 40");
                }
            }
            this.modifiedBy = modifiedBy;
        } else {
            throw new IllegalArgumentException("String 'parentSharedFolderId' does not match pattern");
        }
    }

    public FileSharingInfo(boolean readOnly, String parentSharedFolderId) {
        this(readOnly, parentSharedFolderId, null);
    }

    public boolean getReadOnly() {
        return this.readOnly;
    }

    public String getParentSharedFolderId() {
        return this.parentSharedFolderId;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.parentSharedFolderId, this.modifiedBy}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        FileSharingInfo other = (FileSharingInfo) obj;
        if (this.readOnly == other.readOnly && (this.parentSharedFolderId == other.parentSharedFolderId || this.parentSharedFolderId.equals(other.parentSharedFolderId))) {
            if (this.modifiedBy == other.modifiedBy) {
                return true;
            }
            if (this.modifiedBy != null && this.modifiedBy.equals(other.modifiedBy)) {
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
