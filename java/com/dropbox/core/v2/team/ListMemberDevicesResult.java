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

public class ListMemberDevicesResult {
    protected final List<ActiveWebSession> activeWebSessions;
    protected final List<DesktopClientSession> desktopClientSessions;
    protected final List<MobileClientSession> mobileClientSessions;

    public static class Builder {
        protected List<ActiveWebSession> activeWebSessions = null;
        protected List<DesktopClientSession> desktopClientSessions = null;
        protected List<MobileClientSession> mobileClientSessions = null;

        protected Builder() {
        }

        public Builder withActiveWebSessions(List<ActiveWebSession> activeWebSessions) {
            if (activeWebSessions != null) {
                for (ActiveWebSession x : activeWebSessions) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'activeWebSessions' is null");
                    }
                }
            }
            this.activeWebSessions = activeWebSessions;
            return this;
        }

        public Builder withDesktopClientSessions(List<DesktopClientSession> desktopClientSessions) {
            if (desktopClientSessions != null) {
                for (DesktopClientSession x : desktopClientSessions) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'desktopClientSessions' is null");
                    }
                }
            }
            this.desktopClientSessions = desktopClientSessions;
            return this;
        }

        public Builder withMobileClientSessions(List<MobileClientSession> mobileClientSessions) {
            if (mobileClientSessions != null) {
                for (MobileClientSession x : mobileClientSessions) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'mobileClientSessions' is null");
                    }
                }
            }
            this.mobileClientSessions = mobileClientSessions;
            return this;
        }

        public ListMemberDevicesResult build() {
            return new ListMemberDevicesResult(this.activeWebSessions, this.desktopClientSessions, this.mobileClientSessions);
        }
    }

    static class Serializer extends StructSerializer<ListMemberDevicesResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListMemberDevicesResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            if (value.activeWebSessions != null) {
                g.writeFieldName("active_web_sessions");
                StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).serialize(value.activeWebSessions, g);
            }
            if (value.desktopClientSessions != null) {
                g.writeFieldName("desktop_client_sessions");
                StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).serialize(value.desktopClientSessions, g);
            }
            if (value.mobileClientSessions != null) {
                g.writeFieldName("mobile_client_sessions");
                StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).serialize(value.mobileClientSessions, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListMemberDevicesResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<ActiveWebSession> f_activeWebSessions = null;
                List<DesktopClientSession> f_desktopClientSessions = null;
                List<MobileClientSession> f_mobileClientSessions = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("active_web_sessions".equals(field)) {
                        f_activeWebSessions = (List) StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).deserialize(p);
                    } else if ("desktop_client_sessions".equals(field)) {
                        f_desktopClientSessions = (List) StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).deserialize(p);
                    } else if ("mobile_client_sessions".equals(field)) {
                        f_mobileClientSessions = (List) StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                ListMemberDevicesResult value = new ListMemberDevicesResult(f_activeWebSessions, f_desktopClientSessions, f_mobileClientSessions);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListMemberDevicesResult(List<ActiveWebSession> activeWebSessions, List<DesktopClientSession> desktopClientSessions, List<MobileClientSession> mobileClientSessions) {
        if (activeWebSessions != null) {
            for (ActiveWebSession x : activeWebSessions) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'activeWebSessions' is null");
                }
            }
        }
        this.activeWebSessions = activeWebSessions;
        if (desktopClientSessions != null) {
            for (DesktopClientSession x2 : desktopClientSessions) {
                if (x2 == null) {
                    throw new IllegalArgumentException("An item in list 'desktopClientSessions' is null");
                }
            }
        }
        this.desktopClientSessions = desktopClientSessions;
        if (mobileClientSessions != null) {
            for (MobileClientSession x3 : mobileClientSessions) {
                if (x3 == null) {
                    throw new IllegalArgumentException("An item in list 'mobileClientSessions' is null");
                }
            }
        }
        this.mobileClientSessions = mobileClientSessions;
    }

    public ListMemberDevicesResult() {
        this(null, null, null);
    }

    public List<ActiveWebSession> getActiveWebSessions() {
        return this.activeWebSessions;
    }

    public List<DesktopClientSession> getDesktopClientSessions() {
        return this.desktopClientSessions;
    }

    public List<MobileClientSession> getMobileClientSessions() {
        return this.mobileClientSessions;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.activeWebSessions, this.desktopClientSessions, this.mobileClientSessions});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListMemberDevicesResult other = (ListMemberDevicesResult) obj;
        if ((this.activeWebSessions == other.activeWebSessions || (this.activeWebSessions != null && this.activeWebSessions.equals(other.activeWebSessions))) && (this.desktopClientSessions == other.desktopClientSessions || (this.desktopClientSessions != null && this.desktopClientSessions.equals(other.desktopClientSessions)))) {
            if (this.mobileClientSessions == other.mobileClientSessions) {
                return true;
            }
            if (this.mobileClientSessions != null && this.mobileClientSessions.equals(other.mobileClientSessions)) {
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
