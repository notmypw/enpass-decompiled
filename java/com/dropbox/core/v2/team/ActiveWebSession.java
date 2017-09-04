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
import in.sinew.enpass.AppSettings;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class ActiveWebSession extends DeviceSession {
    protected final String browser;
    protected final String os;
    protected final String userAgent;

    public static class Builder extends com.dropbox.core.v2.team.DeviceSession.Builder {
        protected final String browser;
        protected final String os;
        protected final String userAgent;

        protected Builder(String sessionId, String userAgent, String os, String browser) {
            super(sessionId);
            if (userAgent == null) {
                throw new IllegalArgumentException("Required value for 'userAgent' is null");
            }
            this.userAgent = userAgent;
            if (os == null) {
                throw new IllegalArgumentException("Required value for 'os' is null");
            }
            this.os = os;
            if (browser == null) {
                throw new IllegalArgumentException("Required value for 'browser' is null");
            }
            this.browser = browser;
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

        public ActiveWebSession build() {
            return new ActiveWebSession(this.sessionId, this.userAgent, this.os, this.browser, this.ipAddress, this.country, this.created, this.updated);
        }
    }

    static class Serializer extends StructSerializer<ActiveWebSession> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ActiveWebSession value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxEvent.FIELD_SESSION_ID);
            StoneSerializers.string().serialize(value.sessionId, g);
            g.writeFieldName(AppSettings.BROWSER_USER_AGENT);
            StoneSerializers.string().serialize(value.userAgent, g);
            g.writeFieldName("os");
            StoneSerializers.string().serialize(value.os, g);
            g.writeFieldName("browser");
            StoneSerializers.string().serialize(value.browser, g);
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

        public ActiveWebSession deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_sessionId = null;
                String f_userAgent = null;
                String f_os = null;
                String f_browser = null;
                String f_ipAddress = null;
                String f_country = null;
                Date f_created = null;
                Date f_updated = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxEvent.FIELD_SESSION_ID.equals(field)) {
                        f_sessionId = (String) StoneSerializers.string().deserialize(p);
                    } else if (AppSettings.BROWSER_USER_AGENT.equals(field)) {
                        f_userAgent = (String) StoneSerializers.string().deserialize(p);
                    } else if ("os".equals(field)) {
                        f_os = (String) StoneSerializers.string().deserialize(p);
                    } else if ("browser".equals(field)) {
                        f_browser = (String) StoneSerializers.string().deserialize(p);
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
                } else if (f_userAgent == null) {
                    throw new JsonParseException(p, "Required field \"user_agent\" missing.");
                } else if (f_os == null) {
                    throw new JsonParseException(p, "Required field \"os\" missing.");
                } else if (f_browser == null) {
                    throw new JsonParseException(p, "Required field \"browser\" missing.");
                } else {
                    ActiveWebSession value = new ActiveWebSession(f_sessionId, f_userAgent, f_os, f_browser, f_ipAddress, f_country, f_created, f_updated);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ActiveWebSession(String sessionId, String userAgent, String os, String browser, String ipAddress, String country, Date created, Date updated) {
        super(sessionId, ipAddress, country, created, updated);
        if (userAgent == null) {
            throw new IllegalArgumentException("Required value for 'userAgent' is null");
        }
        this.userAgent = userAgent;
        if (os == null) {
            throw new IllegalArgumentException("Required value for 'os' is null");
        }
        this.os = os;
        if (browser == null) {
            throw new IllegalArgumentException("Required value for 'browser' is null");
        }
        this.browser = browser;
    }

    public ActiveWebSession(String sessionId, String userAgent, String os, String browser) {
        this(sessionId, userAgent, os, browser, null, null, null, null);
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public String getOs() {
        return this.os;
    }

    public String getBrowser() {
        return this.browser;
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

    public static Builder newBuilder(String sessionId, String userAgent, String os, String browser) {
        return new Builder(sessionId, userAgent, os, browser);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.userAgent, this.os, this.browser}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ActiveWebSession other = (ActiveWebSession) obj;
        if ((this.sessionId == other.sessionId || this.sessionId.equals(other.sessionId)) && ((this.userAgent == other.userAgent || this.userAgent.equals(other.userAgent)) && ((this.os == other.os || this.os.equals(other.os)) && ((this.browser == other.browser || this.browser.equals(other.browser)) && ((this.ipAddress == other.ipAddress || (this.ipAddress != null && this.ipAddress.equals(other.ipAddress))) && ((this.country == other.country || (this.country != null && this.country.equals(other.country))) && (this.created == other.created || (this.created != null && this.created.equals(other.created))))))))) {
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
