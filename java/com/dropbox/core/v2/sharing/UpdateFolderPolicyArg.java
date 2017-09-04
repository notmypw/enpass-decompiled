package com.dropbox.core.v2.sharing;

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

class UpdateFolderPolicyArg {
    protected final AclUpdatePolicy aclUpdatePolicy;
    protected final LinkSettings linkSettings;
    protected final MemberPolicy memberPolicy;
    protected final String sharedFolderId;
    protected final SharedLinkPolicy sharedLinkPolicy;
    protected final ViewerInfoPolicy viewerInfoPolicy;

    public static class Builder {
        protected AclUpdatePolicy aclUpdatePolicy;
        protected LinkSettings linkSettings;
        protected MemberPolicy memberPolicy;
        protected final String sharedFolderId;
        protected SharedLinkPolicy sharedLinkPolicy;
        protected ViewerInfoPolicy viewerInfoPolicy;

        protected Builder(String sharedFolderId) {
            if (sharedFolderId == null) {
                throw new IllegalArgumentException("Required value for 'sharedFolderId' is null");
            } else if (Pattern.matches("[-_0-9a-zA-Z:]+", sharedFolderId)) {
                this.sharedFolderId = sharedFolderId;
                this.memberPolicy = null;
                this.aclUpdatePolicy = null;
                this.viewerInfoPolicy = null;
                this.sharedLinkPolicy = null;
                this.linkSettings = null;
            } else {
                throw new IllegalArgumentException("String 'sharedFolderId' does not match pattern");
            }
        }

        public Builder withMemberPolicy(MemberPolicy memberPolicy) {
            this.memberPolicy = memberPolicy;
            return this;
        }

        public Builder withAclUpdatePolicy(AclUpdatePolicy aclUpdatePolicy) {
            this.aclUpdatePolicy = aclUpdatePolicy;
            return this;
        }

        public Builder withViewerInfoPolicy(ViewerInfoPolicy viewerInfoPolicy) {
            this.viewerInfoPolicy = viewerInfoPolicy;
            return this;
        }

        public Builder withSharedLinkPolicy(SharedLinkPolicy sharedLinkPolicy) {
            this.sharedLinkPolicy = sharedLinkPolicy;
            return this;
        }

        public Builder withLinkSettings(LinkSettings linkSettings) {
            this.linkSettings = linkSettings;
            return this;
        }

        public UpdateFolderPolicyArg build() {
            return new UpdateFolderPolicyArg(this.sharedFolderId, this.memberPolicy, this.aclUpdatePolicy, this.viewerInfoPolicy, this.sharedLinkPolicy, this.linkSettings);
        }
    }

    static class Serializer extends StructSerializer<UpdateFolderPolicyArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UpdateFolderPolicyArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("shared_folder_id");
            StoneSerializers.string().serialize(value.sharedFolderId, g);
            if (value.memberPolicy != null) {
                g.writeFieldName("member_policy");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.memberPolicy, g);
            }
            if (value.aclUpdatePolicy != null) {
                g.writeFieldName("acl_update_policy");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.aclUpdatePolicy, g);
            }
            if (value.viewerInfoPolicy != null) {
                g.writeFieldName("viewer_info_policy");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.viewerInfoPolicy, g);
            }
            if (value.sharedLinkPolicy != null) {
                g.writeFieldName("shared_link_policy");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.sharedLinkPolicy, g);
            }
            if (value.linkSettings != null) {
                g.writeFieldName("link_settings");
                StoneSerializers.nullableStruct(Serializer.INSTANCE).serialize(value.linkSettings, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public UpdateFolderPolicyArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_sharedFolderId = null;
                MemberPolicy f_memberPolicy = null;
                AclUpdatePolicy f_aclUpdatePolicy = null;
                ViewerInfoPolicy f_viewerInfoPolicy = null;
                SharedLinkPolicy f_sharedLinkPolicy = null;
                LinkSettings f_linkSettings = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("shared_folder_id".equals(field)) {
                        f_sharedFolderId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("member_policy".equals(field)) {
                        f_memberPolicy = (MemberPolicy) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else if ("acl_update_policy".equals(field)) {
                        f_aclUpdatePolicy = (AclUpdatePolicy) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else if ("viewer_info_policy".equals(field)) {
                        f_viewerInfoPolicy = (ViewerInfoPolicy) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else if ("shared_link_policy".equals(field)) {
                        f_sharedLinkPolicy = (SharedLinkPolicy) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else if ("link_settings".equals(field)) {
                        f_linkSettings = (LinkSettings) StoneSerializers.nullableStruct(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_sharedFolderId == null) {
                    throw new JsonParseException(p, "Required field \"shared_folder_id\" missing.");
                }
                UpdateFolderPolicyArg value = new UpdateFolderPolicyArg(f_sharedFolderId, f_memberPolicy, f_aclUpdatePolicy, f_viewerInfoPolicy, f_sharedLinkPolicy, f_linkSettings);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public UpdateFolderPolicyArg(String sharedFolderId, MemberPolicy memberPolicy, AclUpdatePolicy aclUpdatePolicy, ViewerInfoPolicy viewerInfoPolicy, SharedLinkPolicy sharedLinkPolicy, LinkSettings linkSettings) {
        if (sharedFolderId == null) {
            throw new IllegalArgumentException("Required value for 'sharedFolderId' is null");
        } else if (Pattern.matches("[-_0-9a-zA-Z:]+", sharedFolderId)) {
            this.sharedFolderId = sharedFolderId;
            this.memberPolicy = memberPolicy;
            this.aclUpdatePolicy = aclUpdatePolicy;
            this.viewerInfoPolicy = viewerInfoPolicy;
            this.sharedLinkPolicy = sharedLinkPolicy;
            this.linkSettings = linkSettings;
        } else {
            throw new IllegalArgumentException("String 'sharedFolderId' does not match pattern");
        }
    }

    public UpdateFolderPolicyArg(String sharedFolderId) {
        this(sharedFolderId, null, null, null, null, null);
    }

    public String getSharedFolderId() {
        return this.sharedFolderId;
    }

    public MemberPolicy getMemberPolicy() {
        return this.memberPolicy;
    }

    public AclUpdatePolicy getAclUpdatePolicy() {
        return this.aclUpdatePolicy;
    }

    public ViewerInfoPolicy getViewerInfoPolicy() {
        return this.viewerInfoPolicy;
    }

    public SharedLinkPolicy getSharedLinkPolicy() {
        return this.sharedLinkPolicy;
    }

    public LinkSettings getLinkSettings() {
        return this.linkSettings;
    }

    public static Builder newBuilder(String sharedFolderId) {
        return new Builder(sharedFolderId);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.sharedFolderId, this.memberPolicy, this.aclUpdatePolicy, this.viewerInfoPolicy, this.sharedLinkPolicy, this.linkSettings});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        UpdateFolderPolicyArg other = (UpdateFolderPolicyArg) obj;
        if ((this.sharedFolderId == other.sharedFolderId || this.sharedFolderId.equals(other.sharedFolderId)) && ((this.memberPolicy == other.memberPolicy || (this.memberPolicy != null && this.memberPolicy.equals(other.memberPolicy))) && ((this.aclUpdatePolicy == other.aclUpdatePolicy || (this.aclUpdatePolicy != null && this.aclUpdatePolicy.equals(other.aclUpdatePolicy))) && ((this.viewerInfoPolicy == other.viewerInfoPolicy || (this.viewerInfoPolicy != null && this.viewerInfoPolicy.equals(other.viewerInfoPolicy))) && (this.sharedLinkPolicy == other.sharedLinkPolicy || (this.sharedLinkPolicy != null && this.sharedLinkPolicy.equals(other.sharedLinkPolicy))))))) {
            if (this.linkSettings == other.linkSettings) {
                return true;
            }
            if (this.linkSettings != null && this.linkSettings.equals(other.linkSettings)) {
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
