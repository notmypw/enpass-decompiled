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

public class FolderPolicy {
    protected final AclUpdatePolicy aclUpdatePolicy;
    protected final MemberPolicy memberPolicy;
    protected final MemberPolicy resolvedMemberPolicy;
    protected final SharedLinkPolicy sharedLinkPolicy;
    protected final ViewerInfoPolicy viewerInfoPolicy;

    public static class Builder {
        protected final AclUpdatePolicy aclUpdatePolicy;
        protected MemberPolicy memberPolicy;
        protected MemberPolicy resolvedMemberPolicy;
        protected final SharedLinkPolicy sharedLinkPolicy;
        protected ViewerInfoPolicy viewerInfoPolicy;

        protected Builder(AclUpdatePolicy aclUpdatePolicy, SharedLinkPolicy sharedLinkPolicy) {
            if (aclUpdatePolicy == null) {
                throw new IllegalArgumentException("Required value for 'aclUpdatePolicy' is null");
            }
            this.aclUpdatePolicy = aclUpdatePolicy;
            if (sharedLinkPolicy == null) {
                throw new IllegalArgumentException("Required value for 'sharedLinkPolicy' is null");
            }
            this.sharedLinkPolicy = sharedLinkPolicy;
            this.memberPolicy = null;
            this.resolvedMemberPolicy = null;
            this.viewerInfoPolicy = null;
        }

        public Builder withMemberPolicy(MemberPolicy memberPolicy) {
            this.memberPolicy = memberPolicy;
            return this;
        }

        public Builder withResolvedMemberPolicy(MemberPolicy resolvedMemberPolicy) {
            this.resolvedMemberPolicy = resolvedMemberPolicy;
            return this;
        }

        public Builder withViewerInfoPolicy(ViewerInfoPolicy viewerInfoPolicy) {
            this.viewerInfoPolicy = viewerInfoPolicy;
            return this;
        }

        public FolderPolicy build() {
            return new FolderPolicy(this.aclUpdatePolicy, this.sharedLinkPolicy, this.memberPolicy, this.resolvedMemberPolicy, this.viewerInfoPolicy);
        }
    }

    static class Serializer extends StructSerializer<FolderPolicy> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(FolderPolicy value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("acl_update_policy");
            Serializer.INSTANCE.serialize(value.aclUpdatePolicy, g);
            g.writeFieldName("shared_link_policy");
            Serializer.INSTANCE.serialize(value.sharedLinkPolicy, g);
            if (value.memberPolicy != null) {
                g.writeFieldName("member_policy");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.memberPolicy, g);
            }
            if (value.resolvedMemberPolicy != null) {
                g.writeFieldName("resolved_member_policy");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.resolvedMemberPolicy, g);
            }
            if (value.viewerInfoPolicy != null) {
                g.writeFieldName("viewer_info_policy");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.viewerInfoPolicy, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public FolderPolicy deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                AclUpdatePolicy f_aclUpdatePolicy = null;
                SharedLinkPolicy f_sharedLinkPolicy = null;
                MemberPolicy f_memberPolicy = null;
                MemberPolicy f_resolvedMemberPolicy = null;
                ViewerInfoPolicy f_viewerInfoPolicy = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("acl_update_policy".equals(field)) {
                        f_aclUpdatePolicy = Serializer.INSTANCE.deserialize(p);
                    } else if ("shared_link_policy".equals(field)) {
                        f_sharedLinkPolicy = Serializer.INSTANCE.deserialize(p);
                    } else if ("member_policy".equals(field)) {
                        f_memberPolicy = (MemberPolicy) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else if ("resolved_member_policy".equals(field)) {
                        f_resolvedMemberPolicy = (MemberPolicy) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else if ("viewer_info_policy".equals(field)) {
                        f_viewerInfoPolicy = (ViewerInfoPolicy) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_aclUpdatePolicy == null) {
                    throw new JsonParseException(p, "Required field \"acl_update_policy\" missing.");
                } else if (f_sharedLinkPolicy == null) {
                    throw new JsonParseException(p, "Required field \"shared_link_policy\" missing.");
                } else {
                    FolderPolicy value = new FolderPolicy(f_aclUpdatePolicy, f_sharedLinkPolicy, f_memberPolicy, f_resolvedMemberPolicy, f_viewerInfoPolicy);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public FolderPolicy(AclUpdatePolicy aclUpdatePolicy, SharedLinkPolicy sharedLinkPolicy, MemberPolicy memberPolicy, MemberPolicy resolvedMemberPolicy, ViewerInfoPolicy viewerInfoPolicy) {
        this.memberPolicy = memberPolicy;
        this.resolvedMemberPolicy = resolvedMemberPolicy;
        if (aclUpdatePolicy == null) {
            throw new IllegalArgumentException("Required value for 'aclUpdatePolicy' is null");
        }
        this.aclUpdatePolicy = aclUpdatePolicy;
        if (sharedLinkPolicy == null) {
            throw new IllegalArgumentException("Required value for 'sharedLinkPolicy' is null");
        }
        this.sharedLinkPolicy = sharedLinkPolicy;
        this.viewerInfoPolicy = viewerInfoPolicy;
    }

    public FolderPolicy(AclUpdatePolicy aclUpdatePolicy, SharedLinkPolicy sharedLinkPolicy) {
        this(aclUpdatePolicy, sharedLinkPolicy, null, null, null);
    }

    public AclUpdatePolicy getAclUpdatePolicy() {
        return this.aclUpdatePolicy;
    }

    public SharedLinkPolicy getSharedLinkPolicy() {
        return this.sharedLinkPolicy;
    }

    public MemberPolicy getMemberPolicy() {
        return this.memberPolicy;
    }

    public MemberPolicy getResolvedMemberPolicy() {
        return this.resolvedMemberPolicy;
    }

    public ViewerInfoPolicy getViewerInfoPolicy() {
        return this.viewerInfoPolicy;
    }

    public static Builder newBuilder(AclUpdatePolicy aclUpdatePolicy, SharedLinkPolicy sharedLinkPolicy) {
        return new Builder(aclUpdatePolicy, sharedLinkPolicy);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.memberPolicy, this.resolvedMemberPolicy, this.aclUpdatePolicy, this.sharedLinkPolicy, this.viewerInfoPolicy});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        FolderPolicy other = (FolderPolicy) obj;
        if ((this.aclUpdatePolicy == other.aclUpdatePolicy || this.aclUpdatePolicy.equals(other.aclUpdatePolicy)) && ((this.sharedLinkPolicy == other.sharedLinkPolicy || this.sharedLinkPolicy.equals(other.sharedLinkPolicy)) && ((this.memberPolicy == other.memberPolicy || (this.memberPolicy != null && this.memberPolicy.equals(other.memberPolicy))) && (this.resolvedMemberPolicy == other.resolvedMemberPolicy || (this.resolvedMemberPolicy != null && this.resolvedMemberPolicy.equals(other.resolvedMemberPolicy)))))) {
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
