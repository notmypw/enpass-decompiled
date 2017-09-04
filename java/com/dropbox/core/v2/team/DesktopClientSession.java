package com.dropbox.core.v2.team;

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

public class DesktopClientSession extends DeviceSession {
    protected final DesktopPlatform clientType;
    protected final String clientVersion;
    protected final String hostName;
    protected final boolean isDeleteOnUnlinkSupported;
    protected final String platform;

    public static class Builder extends com.dropbox.core.v2.team.DeviceSession.Builder {
        protected final DesktopPlatform clientType;
        protected final String clientVersion;
        protected final String hostName;
        protected final boolean isDeleteOnUnlinkSupported;
        protected final String platform;

        protected Builder(String sessionId, String hostName, DesktopPlatform clientType, String clientVersion, String platform, boolean isDeleteOnUnlinkSupported) {
            super(sessionId);
            if (hostName == null) {
                throw new IllegalArgumentException("Required value for 'hostName' is null");
            }
            this.hostName = hostName;
            if (clientType == null) {
                throw new IllegalArgumentException("Required value for 'clientType' is null");
            }
            this.clientType = clientType;
            if (clientVersion == null) {
                throw new IllegalArgumentException("Required value for 'clientVersion' is null");
            }
            this.clientVersion = clientVersion;
            if (platform == null) {
                throw new IllegalArgumentException("Required value for 'platform' is null");
            }
            this.platform = platform;
            this.isDeleteOnUnlinkSupported = isDeleteOnUnlinkSupported;
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

        public DesktopClientSession build() {
            return new DesktopClientSession(this.sessionId, this.hostName, this.clientType, this.clientVersion, this.platform, this.isDeleteOnUnlinkSupported, this.ipAddress, this.country, this.created, this.updated);
        }
    }

    static class Serializer extends StructSerializer<DesktopClientSession> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(DesktopClientSession value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxEvent.FIELD_SESSION_ID);
            StoneSerializers.string().serialize(value.sessionId, g);
            g.writeFieldName("host_name");
            StoneSerializers.string().serialize(value.hostName, g);
            g.writeFieldName("client_type");
            Serializer.INSTANCE.serialize(value.clientType, g);
            g.writeFieldName("client_version");
            StoneSerializers.string().serialize(value.clientVersion, g);
            g.writeFieldName("platform");
            StoneSerializers.string().serialize(value.platform, g);
            g.writeFieldName("is_delete_on_unlink_supported");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.isDeleteOnUnlinkSupported), g);
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

        public DesktopClientSession deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_sessionId = null;
                String f_hostName = null;
                DesktopPlatform f_clientType = null;
                String f_clientVersion = null;
                String f_platform = null;
                Boolean f_isDeleteOnUnlinkSupported = null;
                String f_ipAddress = null;
                String f_country = null;
                Date f_created = null;
                Date f_updated = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxEvent.FIELD_SESSION_ID.equals(field)) {
                        f_sessionId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("host_name".equals(field)) {
                        f_hostName = (String) StoneSerializers.string().deserialize(p);
                    } else if ("client_type".equals(field)) {
                        f_clientType = Serializer.INSTANCE.deserialize(p);
                    } else if ("client_version".equals(field)) {
                        f_clientVersion = (String) StoneSerializers.string().deserialize(p);
                    } else if ("platform".equals(field)) {
                        f_platform = (String) StoneSerializers.string().deserialize(p);
                    } else if ("is_delete_on_unlink_supported".equals(field)) {
                        f_isDeleteOnUnlinkSupported = (Boolean) StoneSerializers.boolean_().deserialize(p);
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
                } else if (f_hostName == null) {
                    throw new JsonParseException(p, "Required field \"host_name\" missing.");
                } else if (f_clientType == null) {
                    throw new JsonParseException(p, "Required field \"client_type\" missing.");
                } else if (f_clientVersion == null) {
                    throw new JsonParseException(p, "Required field \"client_version\" missing.");
                } else if (f_platform == null) {
                    throw new JsonParseException(p, "Required field \"platform\" missing.");
                } else if (f_isDeleteOnUnlinkSupported == null) {
                    throw new JsonParseException(p, "Required field \"is_delete_on_unlink_supported\" missing.");
                } else {
                    DesktopClientSession value = new DesktopClientSession(f_sessionId, f_hostName, f_clientType, f_clientVersion, f_platform, f_isDeleteOnUnlinkSupported.booleanValue(), f_ipAddress, f_country, f_created, f_updated);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public DesktopClientSession(String sessionId, String hostName, DesktopPlatform clientType, String clientVersion, String platform, boolean isDeleteOnUnlinkSupported, String ipAddress, String country, Date created, Date updated) {
        super(sessionId, ipAddress, country, created, updated);
        if (hostName == null) {
            throw new IllegalArgumentException("Required value for 'hostName' is null");
        }
        this.hostName = hostName;
        if (clientType == null) {
            throw new IllegalArgumentException("Required value for 'clientType' is null");
        }
        this.clientType = clientType;
        if (clientVersion == null) {
            throw new IllegalArgumentException("Required value for 'clientVersion' is null");
        }
        this.clientVersion = clientVersion;
        if (platform == null) {
            throw new IllegalArgumentException("Required value for 'platform' is null");
        }
        this.platform = platform;
        this.isDeleteOnUnlinkSupported = isDeleteOnUnlinkSupported;
    }

    public DesktopClientSession(String sessionId, String hostName, DesktopPlatform clientType, String clientVersion, String platform, boolean isDeleteOnUnlinkSupported) {
        this(sessionId, hostName, clientType, clientVersion, platform, isDeleteOnUnlinkSupported, null, null, null, null);
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getHostName() {
        return this.hostName;
    }

    public DesktopPlatform getClientType() {
        return this.clientType;
    }

    public String getClientVersion() {
        return this.clientVersion;
    }

    public String getPlatform() {
        return this.platform;
    }

    public boolean getIsDeleteOnUnlinkSupported() {
        return this.isDeleteOnUnlinkSupported;
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

    public static Builder newBuilder(String sessionId, String hostName, DesktopPlatform clientType, String clientVersion, String platform, boolean isDeleteOnUnlinkSupported) {
        return new Builder(sessionId, hostName, clientType, clientVersion, platform, isDeleteOnUnlinkSupported);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.hostName, this.clientType, this.clientVersion, this.platform, Boolean.valueOf(this.isDeleteOnUnlinkSupported)}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        DesktopClientSession other = (DesktopClientSession) obj;
        if ((this.sessionId == other.sessionId || this.sessionId.equals(other.sessionId)) && ((this.hostName == other.hostName || this.hostName.equals(other.hostName)) && ((this.clientType == other.clientType || this.clientType.equals(other.clientType)) && ((this.clientVersion == other.clientVersion || this.clientVersion.equals(other.clientVersion)) && ((this.platform == other.platform || this.platform.equals(other.platform)) && this.isDeleteOnUnlinkSupported == other.isDeleteOnUnlinkSupported && ((this.ipAddress == other.ipAddress || (this.ipAddress != null && this.ipAddress.equals(other.ipAddress))) && ((this.country == other.country || (this.country != null && this.country.equals(other.country))) && (this.created == other.created || (this.created != null && this.created.equals(other.created)))))))))) {
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
