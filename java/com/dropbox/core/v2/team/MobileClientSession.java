package com.dropbox.core.v2.team;

import com.box.androidsdk.content.BoxConstants;
import com.box.androidsdk.content.models.BoxEnterpriseEvent;
import com.box.androidsdk.content.models.BoxEvent;
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
import java.util.Date;

public class MobileClientSession extends DeviceSession {
    protected final MobileClientPlatform clientType;
    protected final String clientVersion;
    protected final String deviceName;
    protected final String lastCarrier;
    protected final String osVersion;

    public static class Builder extends com.dropbox.core.v2.team.DeviceSession.Builder {
        protected final MobileClientPlatform clientType;
        protected String clientVersion;
        protected final String deviceName;
        protected String lastCarrier;
        protected String osVersion;

        protected Builder(String sessionId, String deviceName, MobileClientPlatform clientType) {
            super(sessionId);
            if (deviceName == null) {
                throw new IllegalArgumentException("Required value for 'deviceName' is null");
            }
            this.deviceName = deviceName;
            if (clientType == null) {
                throw new IllegalArgumentException("Required value for 'clientType' is null");
            }
            this.clientType = clientType;
            this.clientVersion = null;
            this.osVersion = null;
            this.lastCarrier = null;
        }

        public Builder withClientVersion(String clientVersion) {
            this.clientVersion = clientVersion;
            return this;
        }

        public Builder withOsVersion(String osVersion) {
            this.osVersion = osVersion;
            return this;
        }

        public Builder withLastCarrier(String lastCarrier) {
            this.lastCarrier = lastCarrier;
            return this;
        }

        public Builder withIpAddress(String ipAddress) {
            super.withIpAddress(ipAddress);
            return this;
        }

        public Builder withCountry(String country) {
            super.withCountry(country);
            return this;
        }

        public Builder withCreated(Date created) {
            super.withCreated(created);
            return this;
        }

        public Builder withUpdated(Date updated) {
            super.withUpdated(updated);
            return this;
        }

        public MobileClientSession build() {
            return new MobileClientSession(this.sessionId, this.deviceName, this.clientType, this.ipAddress, this.country, this.created, this.updated, this.clientVersion, this.osVersion, this.lastCarrier);
        }
    }

    static class Serializer extends StructSerializer<MobileClientSession> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(MobileClientSession value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxEvent.FIELD_SESSION_ID);
            StoneSerializers.string().serialize(value.sessionId, g);
            g.writeFieldName(BoxConstants.KEY_BOX_DEVICE_NAME);
            StoneSerializers.string().serialize(value.deviceName, g);
            g.writeFieldName("client_type");
            Serializer.INSTANCE.serialize(value.clientType, g);
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
            if (value.clientVersion != null) {
                g.writeFieldName("client_version");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.clientVersion, g);
            }
            if (value.osVersion != null) {
                g.writeFieldName("os_version");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.osVersion, g);
            }
            if (value.lastCarrier != null) {
                g.writeFieldName("last_carrier");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.lastCarrier, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public MobileClientSession deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_sessionId = null;
                String f_deviceName = null;
                MobileClientPlatform f_clientType = null;
                String f_ipAddress = null;
                String f_country = null;
                Date f_created = null;
                Date f_updated = null;
                String f_clientVersion = null;
                String f_osVersion = null;
                String f_lastCarrier = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxEvent.FIELD_SESSION_ID.equals(field)) {
                        f_sessionId = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxConstants.KEY_BOX_DEVICE_NAME.equals(field)) {
                        f_deviceName = (String) StoneSerializers.string().deserialize(p);
                    } else if ("client_type".equals(field)) {
                        f_clientType = Serializer.INSTANCE.deserialize(p);
                    } else if (BoxEnterpriseEvent.FIELD_IP_ADDRESS.equals(field)) {
                        f_ipAddress = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("country".equals(field)) {
                        f_country = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("created".equals(field)) {
                        f_created = (Date) StoneSerializers.nullable(StoneSerializers.timestamp()).deserialize(p);
                    } else if ("updated".equals(field)) {
                        f_updated = (Date) StoneSerializers.nullable(StoneSerializers.timestamp()).deserialize(p);
                    } else if ("client_version".equals(field)) {
                        f_clientVersion = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("os_version".equals(field)) {
                        f_osVersion = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("last_carrier".equals(field)) {
                        f_lastCarrier = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_sessionId == null) {
                    throw new JsonParseException(p, "Required field \"session_id\" missing.");
                } else if (f_deviceName == null) {
                    throw new JsonParseException(p, "Required field \"device_name\" missing.");
                } else if (f_clientType == null) {
                    throw new JsonParseException(p, "Required field \"client_type\" missing.");
                } else {
                    MobileClientSession value = new MobileClientSession(f_sessionId, f_deviceName, f_clientType, f_ipAddress, f_country, f_created, f_updated, f_clientVersion, f_osVersion, f_lastCarrier);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public MobileClientSession(String sessionId, String deviceName, MobileClientPlatform clientType, String ipAddress, String country, Date created, Date updated, String clientVersion, String osVersion, String lastCarrier) {
        super(sessionId, ipAddress, country, created, updated);
        if (deviceName == null) {
            throw new IllegalArgumentException("Required value for 'deviceName' is null");
        }
        this.deviceName = deviceName;
        if (clientType == null) {
            throw new IllegalArgumentException("Required value for 'clientType' is null");
        }
        this.clientType = clientType;
        this.clientVersion = clientVersion;
        this.osVersion = osVersion;
        this.lastCarrier = lastCarrier;
    }

    public MobileClientSession(String sessionId, String deviceName, MobileClientPlatform clientType) {
        this(sessionId, deviceName, clientType, null, null, null, null, null, null, null);
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public MobileClientPlatform getClientType() {
        return this.clientType;
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

    public String getClientVersion() {
        return this.clientVersion;
    }

    public String getOsVersion() {
        return this.osVersion;
    }

    public String getLastCarrier() {
        return this.lastCarrier;
    }

    public static Builder newBuilder(String sessionId, String deviceName, MobileClientPlatform clientType) {
        return new Builder(sessionId, deviceName, clientType);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.deviceName, this.clientType, this.clientVersion, this.osVersion, this.lastCarrier}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        MobileClientSession other = (MobileClientSession) obj;
        if ((this.sessionId == other.sessionId || this.sessionId.equals(other.sessionId)) && ((this.deviceName == other.deviceName || this.deviceName.equals(other.deviceName)) && ((this.clientType == other.clientType || this.clientType.equals(other.clientType)) && ((this.ipAddress == other.ipAddress || (this.ipAddress != null && this.ipAddress.equals(other.ipAddress))) && ((this.country == other.country || (this.country != null && this.country.equals(other.country))) && ((this.created == other.created || (this.created != null && this.created.equals(other.created))) && ((this.updated == other.updated || (this.updated != null && this.updated.equals(other.updated))) && ((this.clientVersion == other.clientVersion || (this.clientVersion != null && this.clientVersion.equals(other.clientVersion))) && (this.osVersion == other.osVersion || (this.osVersion != null && this.osVersion.equals(other.osVersion))))))))))) {
            if (this.lastCarrier == other.lastCarrier) {
                return true;
            }
            if (this.lastCarrier != null && this.lastCarrier.equals(other.lastCarrier)) {
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
