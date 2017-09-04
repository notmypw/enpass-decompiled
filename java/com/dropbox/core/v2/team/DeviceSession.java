package com.dropbox.core.v2.team;

import com.box.androidsdk.content.models.BoxEnterpriseEvent;
import com.box.androidsdk.content.models.BoxEvent;
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

public class DeviceSession {
    protected final String country;
    protected final Date created;
    protected final String ipAddress;
    protected final String sessionId;
    protected final Date updated;

    public static class Builder {
        protected String country;
        protected Date created;
        protected String ipAddress;
        protected final String sessionId;
        protected Date updated;

        protected Builder(String sessionId) {
            if (sessionId == null) {
                throw new IllegalArgumentException("Required value for 'sessionId' is null");
            }
            this.sessionId = sessionId;
            this.ipAddress = null;
            this.country = null;
            this.created = null;
            this.updated = null;
        }

        public Builder withIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder withCreated(Date created) {
            this.created = LangUtil.truncateMillis(created);
            return this;
        }

        public Builder withUpdated(Date updated) {
            this.updated = LangUtil.truncateMillis(updated);
            return this;
        }

        public DeviceSession build() {
            return new DeviceSession(this.sessionId, this.ipAddress, this.country, this.created, this.updated);
        }
    }

    private static class Serializer extends StructSerializer<DeviceSession> {
        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {
        }

        public void serialize(DeviceSession value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxEvent.FIELD_SESSION_ID);
            StoneSerializers.string().serialize(value.sessionId, g);
            if (value.ipAddress != null) {
                g.writeFieldName(BoxEnterpriseEvent.FIELD_IP_ADDRESS);
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.ipAddress, g);
            }
            if (value.country != null) {
                g.writeFieldName("country");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.country, g);
            }
            if (value.created != null) {
                g.writeFieldName("created");
                StoneSerializers.nullable(StoneSerializers.timestamp()).serialize(value.created, g);
            }
            if (value.updated != null) {
                g.writeFieldName("updated");
                StoneSerializers.nullable(StoneSerializers.timestamp()).serialize(value.updated, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public DeviceSession deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_sessionId = null;
                String f_ipAddress = null;
                String f_country = null;
                Date f_created = null;
                Date f_updated = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxEvent.FIELD_SESSION_ID.equals(field)) {
                        f_sessionId = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxEnterpriseEvent.FIELD_IP_ADDRESS.equals(field)) {
                        f_ipAddress = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("country".equals(field)) {
                        f_country = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("created".equals(field)) {
                        f_created = (Date) StoneSerializers.nullable(StoneSerializers.timestamp()).deserialize(p);
                    } else if ("updated".equals(field)) {
                        f_updated = (Date) StoneSerializers.nullable(StoneSerializers.timestamp()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_sessionId == null) {
                    throw new JsonParseException(p, "Required field \"session_id\" missing.");
                }
                DeviceSession value = new DeviceSession(f_sessionId, f_ipAddress, f_country, f_created, f_updated);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public DeviceSession(String sessionId, String ipAddress, String country, Date created, Date updated) {
        if (sessionId == null) {
            throw new IllegalArgumentException("Required value for 'sessionId' is null");
        }
        this.sessionId = sessionId;
        this.ipAddress = ipAddress;
        this.country = country;
        this.created = LangUtil.truncateMillis(created);
        this.updated = LangUtil.truncateMillis(updated);
    }

    public DeviceSession(String sessionId) {
        this(sessionId, null, null, null, null);
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public String getCountry() {
        return this.country;
    }

    public Date getCreated() {
        return this.created;
    }

    public Date getUpdated() {
        return this.updated;
    }

    public static Builder newBuilder(String sessionId) {
        return new Builder(sessionId);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.sessionId, this.ipAddress, this.country, this.created, this.updated});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        DeviceSession other = (DeviceSession) obj;
        if ((this.sessionId == other.sessionId || this.sessionId.equals(other.sessionId)) && ((this.ipAddress == other.ipAddress || (this.ipAddress != null && this.ipAddress.equals(other.ipAddress))) && ((this.country == other.country || (this.country != null && this.country.equals(other.country))) && (this.created == other.created || (this.created != null && this.created.equals(other.created)))))) {
            if (this.updated == other.updated) {
                return true;
            }
            if (this.updated != null && this.updated.equals(other.updated)) {
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
