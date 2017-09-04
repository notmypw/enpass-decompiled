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

public class LinkPermissions {
    protected final boolean canRevoke;
    protected final RequestedVisibility requestedVisibility;
    protected final ResolvedVisibility resolvedVisibility;
    protected final SharedLinkAccessFailureReason revokeFailureReason;

    public static class Builder {
        protected final boolean canRevoke;
        protected RequestedVisibility requestedVisibility = null;
        protected ResolvedVisibility resolvedVisibility = null;
        protected SharedLinkAccessFailureReason revokeFailureReason = null;

        protected Builder(boolean canRevoke) {
            this.canRevoke = canRevoke;
        }

        public Builder withResolvedVisibility(ResolvedVisibility resolvedVisibility) {
            this.resolvedVisibility = resolvedVisibility;
            return this;
        }

        public Builder withRequestedVisibility(RequestedVisibility requestedVisibility) {
            this.requestedVisibility = requestedVisibility;
            return this;
        }

        public Builder withRevokeFailureReason(SharedLinkAccessFailureReason revokeFailureReason) {
            this.revokeFailureReason = revokeFailureReason;
            return this;
        }

        public LinkPermissions build() {
            return new LinkPermissions(this.canRevoke, this.resolvedVisibility, this.requestedVisibility, this.revokeFailureReason);
        }
    }

    static class Serializer extends StructSerializer<LinkPermissions> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(LinkPermissions value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("can_revoke");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.canRevoke), g);
            if (value.resolvedVisibility != null) {
                g.writeFieldName("resolved_visibility");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.resolvedVisibility, g);
            }
            if (value.requestedVisibility != null) {
                g.writeFieldName("requested_visibility");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.requestedVisibility, g);
            }
            if (value.revokeFailureReason != null) {
                g.writeFieldName("revoke_failure_reason");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.revokeFailureReason, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public LinkPermissions deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Boolean f_canRevoke = null;
                ResolvedVisibility f_resolvedVisibility = null;
                RequestedVisibility f_requestedVisibility = null;
                SharedLinkAccessFailureReason f_revokeFailureReason = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("can_revoke".equals(field)) {
                        f_canRevoke = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("resolved_visibility".equals(field)) {
                        f_resolvedVisibility = (ResolvedVisibility) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else if ("requested_visibility".equals(field)) {
                        f_requestedVisibility = (RequestedVisibility) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else if ("revoke_failure_reason".equals(field)) {
                        f_revokeFailureReason = (SharedLinkAccessFailureReason) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_canRevoke == null) {
                    throw new JsonParseException(p, "Required field \"can_revoke\" missing.");
                }
                LinkPermissions value = new LinkPermissions(f_canRevoke.booleanValue(), f_resolvedVisibility, f_requestedVisibility, f_revokeFailureReason);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public LinkPermissions(boolean canRevoke, ResolvedVisibility resolvedVisibility, RequestedVisibility requestedVisibility, SharedLinkAccessFailureReason revokeFailureReason) {
        this.resolvedVisibility = resolvedVisibility;
        this.requestedVisibility = requestedVisibility;
        this.canRevoke = canRevoke;
        this.revokeFailureReason = revokeFailureReason;
    }

    public LinkPermissions(boolean canRevoke) {
        this(canRevoke, null, null, null);
    }

    public boolean getCanRevoke() {
        return this.canRevoke;
    }

    public ResolvedVisibility getResolvedVisibility() {
        return this.resolvedVisibility;
    }

    public RequestedVisibility getRequestedVisibility() {
        return this.requestedVisibility;
    }

    public SharedLinkAccessFailureReason getRevokeFailureReason() {
        return this.revokeFailureReason;
    }

    public static Builder newBuilder(boolean canRevoke) {
        return new Builder(canRevoke);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.resolvedVisibility, this.requestedVisibility, Boolean.valueOf(this.canRevoke), this.revokeFailureReason});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        LinkPermissions other = (LinkPermissions) obj;
        if (this.canRevoke == other.canRevoke && ((this.resolvedVisibility == other.resolvedVisibility || (this.resolvedVisibility != null && this.resolvedVisibility.equals(other.resolvedVisibility))) && (this.requestedVisibility == other.requestedVisibility || (this.requestedVisibility != null && this.requestedVisibility.equals(other.requestedVisibility))))) {
            if (this.revokeFailureReason == other.revokeFailureReason) {
                return true;
            }
            if (this.revokeFailureReason != null && this.revokeFailureReason.equals(other.revokeFailureReason)) {
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
