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

public class LinkSettings {
    protected final LinkAudience audience;
    protected final LinkExpiry expiry;
    protected final LinkPassword password;

    public static class Builder {
        protected LinkAudience audience = null;
        protected LinkExpiry expiry = null;
        protected LinkPassword password = null;

        protected Builder() {
        }

        public Builder withAudience(LinkAudience audience) {
            this.audience = audience;
            return this;
        }

        public Builder withExpiry(LinkExpiry expiry) {
            this.expiry = expiry;
            return this;
        }

        public Builder withPassword(LinkPassword password) {
            this.password = password;
            return this;
        }

        public LinkSettings build() {
            return new LinkSettings(this.audience, this.expiry, this.password);
        }
    }

    static class Serializer extends StructSerializer<LinkSettings> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(LinkSettings value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            if (value.audience != null) {
                g.writeFieldName("audience");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.audience, g);
            }
            if (value.expiry != null) {
                g.writeFieldName("expiry");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.expiry, g);
            }
            if (value.password != null) {
                g.writeFieldName(BoxSharedLink.FIELD_PASSWORD);
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.password, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public LinkSettings deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                LinkAudience f_audience = null;
                LinkExpiry f_expiry = null;
                LinkPassword f_password = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("audience".equals(field)) {
                        f_audience = (LinkAudience) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else if ("expiry".equals(field)) {
                        f_expiry = (LinkExpiry) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else if (BoxSharedLink.FIELD_PASSWORD.equals(field)) {
                        f_password = (LinkPassword) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                LinkSettings value = new LinkSettings(f_audience, f_expiry, f_password);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public LinkSettings(LinkAudience audience, LinkExpiry expiry, LinkPassword password) {
        this.audience = audience;
        this.expiry = expiry;
        this.password = password;
    }

    public LinkSettings() {
        this(null, null, null);
    }

    public LinkAudience getAudience() {
        return this.audience;
    }

    public LinkExpiry getExpiry() {
        return this.expiry;
    }

    public LinkPassword getPassword() {
        return this.password;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.audience, this.expiry, this.password});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        LinkSettings other = (LinkSettings) obj;
        if ((this.audience == other.audience || (this.audience != null && this.audience.equals(other.audience))) && (this.expiry == other.expiry || (this.expiry != null && this.expiry.equals(other.expiry)))) {
            if (this.password == other.password) {
                return true;
            }
            if (this.password != null && this.password.equals(other.password)) {
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
