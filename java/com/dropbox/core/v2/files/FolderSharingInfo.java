package com.dropbox.core.v2.files;

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

public class FolderSharingInfo extends SharingInfo {
    protected final boolean noAccess;
    protected final String parentSharedFolderId;
    protected final String sharedFolderId;
    protected final boolean traverseOnly;

    public static class Builder {
        protected boolean noAccess = false;
        protected String parentSharedFolderId = null;
        protected final boolean readOnly;
        protected String sharedFolderId = null;
        protected boolean traverseOnly = false;

        protected Builder(boolean readOnly) {
            this.readOnly = readOnly;
        }

        public Builder withParentSharedFolderId(String parentSharedFolderId) {
            if (parentSharedFolderId == null || Pattern.matches("[-_0-9a-zA-Z:]+", parentSharedFolderId)) {
                this.parentSharedFolderId = parentSharedFolderId;
                return this;
            }
            throw new IllegalArgumentException("String 'parentSharedFolderId' does not match pattern");
        }

        public Builder withSharedFolderId(String sharedFolderId) {
            if (sharedFolderId == null || Pattern.matches("[-_0-9a-zA-Z:]+", sharedFolderId)) {
                this.sharedFolderId = sharedFolderId;
                return this;
            }
            throw new IllegalArgumentException("String 'sharedFolderId' does not match pattern");
        }

        public Builder withTraverseOnly(Boolean traverseOnly) {
            if (traverseOnly != null) {
                this.traverseOnly = traverseOnly.booleanValue();
            } else {
                this.traverseOnly = false;
            }
            return this;
        }

        public Builder withNoAccess(Boolean noAccess) {
            if (noAccess != null) {
                this.noAccess = noAccess.booleanValue();
            } else {
                this.noAccess = false;
            }
            return this;
        }

        public FolderSharingInfo build() {
            return new FolderSharingInfo(this.readOnly, this.parentSharedFolderId, this.sharedFolderId, this.traverseOnly, this.noAccess);
        }
    }

    static class Serializer extends StructSerializer<FolderSharingInfo> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(FolderSharingInfo value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("read_only");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.readOnly), g);
            if (value.parentSharedFolderId != null) {
                g.writeFieldName("parent_shared_folder_id");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.parentSharedFolderId, g);
            }
            if (value.sharedFolderId != null) {
                g.writeFieldName("shared_folder_id");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.sharedFolderId, g);
            }
            g.writeFieldName("traverse_only");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.traverseOnly), g);
            g.writeFieldName("no_access");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.noAccess), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public FolderSharingInfo deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Boolean f_readOnly = null;
                String f_parentSharedFolderId = null;
                String f_sharedFolderId = null;
                Boolean f_traverseOnly = Boolean.valueOf(false);
                Boolean f_noAccess = Boolean.valueOf(false);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("read_only".equals(field)) {
                        f_readOnly = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("parent_shared_folder_id".equals(field)) {
                        f_parentSharedFolderId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("shared_folder_id".equals(field)) {
                        f_sharedFolderId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("traverse_only".equals(field)) {
                        f_traverseOnly = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("no_access".equals(field)) {
                        f_noAccess = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_readOnly == null) {
                    throw new JsonParseException(p, "Required field \"read_only\" missing.");
                }
                FolderSharingInfo value = new FolderSharingInfo(f_readOnly.booleanValue(), f_parentSharedFolderId, f_sharedFolderId, f_traverseOnly.booleanValue(), f_noAccess.booleanValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public FolderSharingInfo(boolean readOnly, String parentSharedFolderId, String sharedFolderId, boolean traverseOnly, boolean noAccess) {
        super(readOnly);
        if (parentSharedFolderId == null || Pattern.matches("[-_0-9a-zA-Z:]+", parentSharedFolderId)) {
            this.parentSharedFolderId = parentSharedFolderId;
            if (sharedFolderId == null || Pattern.matches("[-_0-9a-zA-Z:]+", sharedFolderId)) {
                this.sharedFolderId = sharedFolderId;
                this.traverseOnly = traverseOnly;
                this.noAccess = noAccess;
                return;
            }
            throw new IllegalArgumentException("String 'sharedFolderId' does not match pattern");
        }
        throw new IllegalArgumentException("String 'parentSharedFolderId' does not match pattern");
    }

    public FolderSharingInfo(boolean readOnly) {
        this(readOnly, null, null, false, false);
    }

    public boolean getReadOnly() {
        return this.readOnly;
    }

    public String getParentSharedFolderId() {
        return this.parentSharedFolderId;
    }

    public String getSharedFolderId() {
        return this.sharedFolderId;
    }

    public boolean getTraverseOnly() {
        return this.traverseOnly;
    }

    public boolean getNoAccess() {
        return this.noAccess;
    }

    public static Builder newBuilder(boolean readOnly) {
        return new Builder(readOnly);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.parentSharedFolderId, this.sharedFolderId, Boolean.valueOf(this.traverseOnly), Boolean.valueOf(this.noAccess)}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        FolderSharingInfo other = (FolderSharingInfo) obj;
        if (this.readOnly != other.readOnly || ((this.parentSharedFolderId != other.parentSharedFolderId && (this.parentSharedFolderId == null || !this.parentSharedFolderId.equals(other.parentSharedFolderId))) || ((this.sharedFolderId != other.sharedFolderId && (this.sharedFolderId == null || !this.sharedFolderId.equals(other.sharedFolderId))) || this.traverseOnly != other.traverseOnly || this.noAccess != other.noAccess))) {
            return false;
        }
        return true;
    }

    public String toString() {
        return Serializer.INSTANCE.serialize((Object) this, false);
    }

    public String toStringMultiline() {
        return Serializer.INSTANCE.serialize((Object) this, true);
    }
}
