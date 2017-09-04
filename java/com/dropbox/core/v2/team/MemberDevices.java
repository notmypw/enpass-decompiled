package com.dropbox.core.v2.team;

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

public class MemberDevices {
    protected final List<DesktopClientSession> desktopClients;
    protected final List<MobileClientSession> mobileClients;
    protected final String teamMemberId;
    protected final List<ActiveWebSession> webSessions;

    public static class Builder {
        protected List<DesktopClientSession> desktopClients;
        protected List<MobileClientSession> mobileClients;
        protected final String teamMemberId;
        protected List<ActiveWebSession> webSessions;

        protected Builder(String teamMemberId) {
            if (teamMemberId == null) {
                throw new IllegalArgumentException("Required value for 'teamMemberId' is null");
            }
            this.teamMemberId = teamMemberId;
            this.webSessions = null;
            this.desktopClients = null;
            this.mobileClients = null;
        }

        public Builder withWebSessions(List<ActiveWebSession> webSessions) {
            if (webSessions != null) {
                for (ActiveWebSession x : webSessions) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'webSessions' is null");
                    }
                }
            }
            this.webSessions = webSessions;
            return this;
        }

        public Builder withDesktopClients(List<DesktopClientSession> desktopClients) {
            if (desktopClients != null) {
                for (DesktopClientSession x : desktopClients) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'desktopClients' is null");
                    }
                }
            }
            this.desktopClients = desktopClients;
            return this;
        }

        public Builder withMobileClients(List<MobileClientSession> mobileClients) {
            if (mobileClients != null) {
                for (MobileClientSession x : mobileClients) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'mobileClients' is null");
                    }
                }
            }
            this.mobileClients = mobileClients;
            return this;
        }

        public MemberDevices build() {
            return new MemberDevices(this.teamMemberId, this.webSessions, this.desktopClients, this.mobileClients);
        }
    }

    static class Serializer extends StructSerializer<MemberDevices> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(MemberDevices value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("team_member_id");
            StoneSerializers.string().serialize(value.teamMemberId, g);
            if (value.webSessions != null) {
                g.writeFieldName("web_sessions");
                StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).serialize(value.webSessions, g);
            }
            if (value.desktopClients != null) {
                g.writeFieldName("desktop_clients");
                StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).serialize(value.desktopClients, g);
            }
            if (value.mobileClients != null) {
                g.writeFieldName("mobile_clients");
                StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).serialize(value.mobileClients, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public MemberDevices deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_teamMemberId = null;
                List<ActiveWebSession> f_webSessions = null;
                List<DesktopClientSession> f_desktopClients = null;
                List<MobileClientSession> f_mobileClients = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("team_member_id".equals(field)) {
                        f_teamMemberId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("web_sessions".equals(field)) {
                        f_webSessions = (List) StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).deserialize(p);
                    } else if ("desktop_clients".equals(field)) {
                        f_desktopClients = (List) StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).deserialize(p);
                    } else if ("mobile_clients".equals(field)) {
                        f_mobileClients = (List) StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_teamMemberId == null) {
                    throw new JsonParseException(p, "Required field \"team_member_id\" missing.");
                }
                MemberDevices value = new MemberDevices(f_teamMemberId, f_webSessions, f_desktopClients, f_mobileClients);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public MemberDevices(String teamMemberId, List<ActiveWebSession> webSessions, List<DesktopClientSession> desktopClients, List<MobileClientSession> mobileClients) {
        if (teamMemberId == null) {
            throw new IllegalArgumentException("Required value for 'teamMemberId' is null");
        }
        this.teamMemberId = teamMemberId;
        if (webSessions != null) {
            for (ActiveWebSession x : webSessions) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'webSessions' is null");
                }
            }
        }
        this.webSessions = webSessions;
        if (desktopClients != null) {
            for (DesktopClientSession x2 : desktopClients) {
                if (x2 == null) {
                    throw new IllegalArgumentException("An item in list 'desktopClients' is null");
                }
            }
        }
        this.desktopClients = desktopClients;
        if (mobileClients != null) {
            for (MobileClientSession x3 : mobileClients) {
                if (x3 == null) {
                    throw new IllegalArgumentException("An item in list 'mobileClients' is null");
                }
            }
        }
        this.mobileClients = mobileClients;
    }

    public MemberDevices(String teamMemberId) {
        this(teamMemberId, null, null, null);
    }

    public String getTeamMemberId() {
        return this.teamMemberId;
    }

    public List<ActiveWebSession> getWebSessions() {
        return this.webSessions;
    }

    public List<DesktopClientSession> getDesktopClients() {
        return this.desktopClients;
    }

    public List<MobileClientSession> getMobileClients() {
        return this.mobileClients;
    }

    public static Builder newBuilder(String teamMemberId) {
        return new Builder(teamMemberId);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.teamMemberId, this.webSessions, this.desktopClients, this.mobileClients});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        MemberDevices other = (MemberDevices) obj;
        if ((this.teamMemberId == other.teamMemberId || this.teamMemberId.equals(other.teamMemberId)) && ((this.webSessions == other.webSessions || (this.webSessions != null && this.webSessions.equals(other.webSessions))) && (this.desktopClients == other.desktopClients || (this.desktopClients != null && this.desktopClients.equals(other.desktopClients))))) {
            if (this.mobileClients == other.mobileClients) {
                return true;
            }
            if (this.mobileClients != null && this.mobileClients.equals(other.mobileClients)) {
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
