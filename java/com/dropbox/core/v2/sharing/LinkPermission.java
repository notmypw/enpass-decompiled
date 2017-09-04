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

public class LinkPermission {
    protected final LinkAction action;
    protected final boolean allow;
    protected final PermissionDeniedReason reason;

    static class Serializer extends StructSerializer<LinkPermission> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(LinkPermission value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("action");
            Serializer.INSTANCE.serialize(value.action, g);
            g.writeFieldName("allow");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.allow), g);
            if (value.reason != null) {
                g.writeFieldName("reason");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.reason, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public LinkPermission deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                LinkAction f_action = null;
                Boolean f_allow = null;
                PermissionDeniedReason f_reason = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("action".equals(field)) {
                        f_action = Serializer.INSTANCE.deserialize(p);
                    } else if ("allow".equals(field)) {
                        f_allow = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("reason".equals(field)) {
                        f_reason = (PermissionDeniedReason) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_action == null) {
                    throw new JsonParseException(p, "Required field \"action\" missing.");
                } else if (f_allow == null) {
                    throw new JsonParseException(p, "Required field \"allow\" missing.");
                } else {
                    LinkPermission value = new LinkPermission(f_action, f_allow.booleanValue(), f_reason);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public LinkPermission(LinkAction action, boolean allow, PermissionDeniedReason reason) {
        if (action == null) {
            throw new IllegalArgumentException("Required value for 'action' is null");
        }
        this.action = action;
        this.allow = allow;
        this.reason = reason;
    }

    public LinkPermission(LinkAction action, boolean allow) {
        this(action, allow, null);
    }

    public LinkAction getAction() {
        return this.action;
    }

    public boolean getAllow() {
        return this.allow;
    }

    public PermissionDeniedReason getReason() {
        return this.reason;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.action, Boolean.valueOf(this.allow), this.reason});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        LinkPermission other = (LinkPermission) obj;
        if ((this.action == other.action || this.action.equals(other.action)) && this.allow == other.allow) {
            if (this.reason == other.reason) {
                return true;
            }
            if (this.reason != null && this.reason.equals(other.reason)) {
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
