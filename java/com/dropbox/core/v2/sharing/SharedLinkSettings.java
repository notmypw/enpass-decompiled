package com.dropbox.core.v2.sharing;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.util.LangUtil;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class SharedLinkSettings {
    protected final Date expires;
    protected final String linkPassword;
    protected final RequestedVisibility requestedVisibility;

    public static class Builder {
        protected Date expires = null;
        protected String linkPassword = null;
        protected RequestedVisibility requestedVisibility = null;

        protected Builder() {
        }

        public Builder withRequestedVisibility(RequestedVisibility requestedVisibility) {
            this.requestedVisibility = requestedVisibility;
            return this;
        }

        public Builder withLinkPassword(String linkPassword) {
            this.linkPassword = linkPassword;
            return this;
        }

        public Builder withExpires(Date expires) {
            this.expires = LangUtil.truncateMillis(expires);
            return this;
        }

        public SharedLinkSettings build() {
            return new SharedLinkSettings(this.requestedVisibility, this.linkPassword, this.expires);
        }
    }

    static class Serializer extends StructSerializer<SharedLinkSettings> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(SharedLinkSettings value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            if (value.requestedVisibility != null) {
                g.writeFieldName("requested_visibility");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.requestedVisibility, g);
            }
            if (value.linkPassword != null) {
                g.writeFieldName("link_password");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.linkPassword, g);
            }
            if (value.expires != null) {
                g.writeFieldName("expires");
                StoneSerializers.nullable(StoneSerializers.timestamp()).serialize(value.expires, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public SharedLinkSettings deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                RequestedVisibility f_requestedVisibility = null;
                String f_linkPassword = null;
                Date f_expires = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("requested_visibility".equals(field)) {
                        f_requestedVisibility = (RequestedVisibility) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else if ("link_password".equals(field)) {
                        f_linkPassword = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("expires".equals(field)) {
                        f_expires = (Date) StoneSerializers.nullable(StoneSerializers.timestamp()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                SharedLinkSettings value = new SharedLinkSettings(f_requestedVisibility, f_linkPassword, f_expires);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public SharedLinkSettings(RequestedVisibility requestedVisibility, String linkPassword, Date expires) {
        this.requestedVisibility = requestedVisibility;
        this.linkPassword = linkPassword;
        this.expires = LangUtil.truncateMillis(expires);
    }

    public SharedLinkSettings() {
        this(null, null, null);
    }

    public RequestedVisibility getRequestedVisibility() {
        return this.requestedVisibility;
    }

    public String getLinkPassword() {
        return this.linkPassword;
    }

    public Date getExpires() {
        return this.expires;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.requestedVisibility, this.linkPassword, this.expires});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        SharedLinkSettings other = (SharedLinkSettings) obj;
        if ((this.requestedVisibility == other.requestedVisibility || (this.requestedVisibility != null && this.requestedVisibility.equals(other.requestedVisibility))) && (this.linkPassword == other.linkPassword || (this.linkPassword != null && this.linkPassword.equals(other.linkPassword)))) {
            if (this.expires == other.expires) {
                return true;
            }
            if (this.expires != null && this.expires.equals(other.expires)) {
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
