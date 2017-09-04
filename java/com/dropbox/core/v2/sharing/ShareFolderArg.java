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
import java.util.List;
import java.util.regex.Pattern;

class ShareFolderArg {
    protected final AclUpdatePolicy aclUpdatePolicy;
    protected final List<FolderAction> actions;
    protected final boolean forceAsync;
    protected final LinkSettings linkSettings;
    protected final MemberPolicy memberPolicy;
    protected final String path;
    protected final SharedLinkPolicy sharedLinkPolicy;
    protected final ViewerInfoPolicy viewerInfoPolicy;

    public static class Builder {
        protected AclUpdatePolicy aclUpdatePolicy;
        protected List<FolderAction> actions;
        protected boolean forceAsync;
        protected LinkSettings linkSettings;
        protected MemberPolicy memberPolicy;
        protected final String path;
        protected SharedLinkPolicy sharedLinkPolicy;
        protected ViewerInfoPolicy viewerInfoPolicy;

        protected Builder(String path) {
            if (path == null) {
                throw new IllegalArgumentException("Required value for 'path' is null");
            } else if (Pattern.matches("(/(.|[\\r\\n])*)|(ns:[0-9]+(/.*)?)", path)) {
                this.path = path;
                this.memberPolicy = null;
                this.aclUpdatePolicy = null;
                this.sharedLinkPolicy = null;
                this.forceAsync = false;
                this.actions = null;
                this.linkSettings = null;
                this.viewerInfoPolicy = null;
            } else {
                throw new IllegalArgumentException("String 'path' does not match pattern");
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

        public Builder withSharedLinkPolicy(SharedLinkPolicy sharedLinkPolicy) {
            this.sharedLinkPolicy = sharedLinkPolicy;
            return this;
        }

        public Builder withForceAsync(Boolean forceAsync) {
            if (forceAsync != null) {
                this.forceAsync = forceAsync.booleanValue();
            } else {
                this.forceAsync = false;
            }
            return this;
        }

        public Builder withActions(List<FolderAction> actions) {
            if (actions != null) {
                for (FolderAction x : actions) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'actions' is null");
                    }
                }
            }
            this.actions = actions;
            return this;
        }

        public Builder withLinkSettings(LinkSettings linkSettings) {
            this.linkSettings = linkSettings;
            return this;
        }

        public Builder withViewerInfoPolicy(ViewerInfoPolicy viewerInfoPolicy) {
            this.viewerInfoPolicy = viewerInfoPolicy;
            return this;
        }

        public ShareFolderArg build() {
            return new ShareFolderArg(this.path, this.memberPolicy, this.aclUpdatePolicy, this.sharedLinkPolicy, this.forceAsync, this.actions, this.linkSettings, this.viewerInfoPolicy);
        }
    }

    static class Serializer extends StructSerializer<ShareFolderArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ShareFolderArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxMetadataUpdateTask.PATH);
            StoneSerializers.string().serialize(value.path, g);
            if (value.memberPolicy != null) {
                g.writeFieldName("member_policy");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.memberPolicy, g);
            }
            if (value.aclUpdatePolicy != null) {
                g.writeFieldName("acl_update_policy");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.aclUpdatePolicy, g);
            }
            if (value.sharedLinkPolicy != null) {
                g.writeFieldName("shared_link_policy");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.sharedLinkPolicy, g);
            }
            g.writeFieldName("force_async");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.forceAsync), g);
            if (value.actions != null) {
                g.writeFieldName("actions");
                StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).serialize(value.actions, g);
            }
            if (value.linkSettings != null) {
                g.writeFieldName("link_settings");
                StoneSerializers.nullableStruct(Serializer.INSTANCE).serialize(value.linkSettings, g);
            }
            if (value.viewerInfoPolicy != null) {
                g.writeFieldName("viewer_info_policy");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.viewerInfoPolicy, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ShareFolderArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_path = null;
                MemberPolicy f_memberPolicy = null;
                AclUpdatePolicy f_aclUpdatePolicy = null;
                SharedLinkPolicy f_sharedLinkPolicy = null;
                Boolean f_forceAsync = Boolean.valueOf(false);
                List<FolderAction> f_actions = null;
                LinkSettings f_linkSettings = null;
                ViewerInfoPolicy f_viewerInfoPolicy = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxMetadataUpdateTask.PATH.equals(field)) {
                        f_path = (String) StoneSerializers.string().deserialize(p);
                    } else if ("member_policy".equals(field)) {
                        f_memberPolicy = (MemberPolicy) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else if ("acl_update_policy".equals(field)) {
                        f_aclUpdatePolicy = (AclUpdatePolicy) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else if ("shared_link_policy".equals(field)) {
                        f_sharedLinkPolicy = (SharedLinkPolicy) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else if ("force_async".equals(field)) {
                        f_forceAsync = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("actions".equals(field)) {
                        f_actions = (List) StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).deserialize(p);
                    } else if ("link_settings".equals(field)) {
                        f_linkSettings = (LinkSettings) StoneSerializers.nullableStruct(Serializer.INSTANCE).deserialize(p);
                    } else if ("viewer_info_policy".equals(field)) {
                        f_viewerInfoPolicy = (ViewerInfoPolicy) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_path == null) {
                    throw new JsonParseException(p, "Required field \"path\" missing.");
                }
                ShareFolderArg value = new ShareFolderArg(f_path, f_memberPolicy, f_aclUpdatePolicy, f_sharedLinkPolicy, f_forceAsync.booleanValue(), f_actions, f_linkSettings, f_viewerInfoPolicy);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ShareFolderArg(String path, MemberPolicy memberPolicy, AclUpdatePolicy aclUpdatePolicy, SharedLinkPolicy sharedLinkPolicy, boolean forceAsync, List<FolderAction> actions, LinkSettings linkSettings, ViewerInfoPolicy viewerInfoPolicy) {
        if (path == null) {
            throw new IllegalArgumentException("Required value for 'path' is null");
        } else if (Pattern.matches("(/(.|[\\r\\n])*)|(ns:[0-9]+(/.*)?)", path)) {
            this.path = path;
            this.memberPolicy = memberPolicy;
            this.aclUpdatePolicy = aclUpdatePolicy;
            this.sharedLinkPolicy = sharedLinkPolicy;
            this.forceAsync = forceAsync;
            if (actions != null) {
                for (FolderAction x : actions) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'actions' is null");
                    }
                }
            }
            this.actions = actions;
            this.linkSettings = linkSettings;
            this.viewerInfoPolicy = viewerInfoPolicy;
        } else {
            throw new IllegalArgumentException("String 'path' does not match pattern");
        }
    }

    public ShareFolderArg(String path) {
        this(path, null, null, null, false, null, null, null);
    }

    public String getPath() {
        return this.path;
    }

    public MemberPolicy getMemberPolicy() {
        return this.memberPolicy;
    }

    public AclUpdatePolicy getAclUpdatePolicy() {
        return this.aclUpdatePolicy;
    }

    public SharedLinkPolicy getSharedLinkPolicy() {
        return this.sharedLinkPolicy;
    }

    public boolean getForceAsync() {
        return this.forceAsync;
    }

    public List<FolderAction> getActions() {
        return this.actions;
    }

    public LinkSettings getLinkSettings() {
        return this.linkSettings;
    }

    public ViewerInfoPolicy getViewerInfoPolicy() {
        return this.viewerInfoPolicy;
    }

    public static Builder newBuilder(String path) {
        return new Builder(path);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.path, this.memberPolicy, this.aclUpdatePolicy, this.sharedLinkPolicy, Boolean.valueOf(this.forceAsync), this.actions, this.linkSettings, this.viewerInfoPolicy});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ShareFolderArg other = (ShareFolderArg) obj;
        if ((this.path == other.path || this.path.equals(other.path)) && ((this.memberPolicy == other.memberPolicy || (this.memberPolicy != null && this.memberPolicy.equals(other.memberPolicy))) && ((this.aclUpdatePolicy == other.aclUpdatePolicy || (this.aclUpdatePolicy != null && this.aclUpdatePolicy.equals(other.aclUpdatePolicy))) && ((this.sharedLinkPolicy == other.sharedLinkPolicy || (this.sharedLinkPolicy != null && this.sharedLinkPolicy.equals(other.sharedLinkPolicy))) && this.forceAsync == other.forceAsync && ((this.actions == other.actions || (this.actions != null && this.actions.equals(other.actions))) && (this.linkSettings == other.linkSettings || (this.linkSettings != null && this.linkSettings.equals(other.linkSettings)))))))) {
            if (this.viewerInfoPolicy == other.viewerInfoPolicy) {
                return true;
            }
            if (this.viewerInfoPolicy != null && this.viewerInfoPolicy.equals(other.viewerInfoPolicy)) {
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
