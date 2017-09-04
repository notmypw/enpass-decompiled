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

class ListMemberDevicesArg {
    protected final boolean includeDesktopClients;
    protected final boolean includeMobileClients;
    protected final boolean includeWebSessions;
    protected final String teamMemberId;

    public static class Builder {
        protected boolean includeDesktopClients;
        protected boolean includeMobileClients;
        protected boolean includeWebSessions;
        protected final String teamMemberId;

        protected Builder(String teamMemberId) {
            if (teamMemberId == null) {
                throw new IllegalArgumentException("Required value for 'teamMemberId' is null");
            }
            this.teamMemberId = teamMemberId;
            this.includeWebSessions = true;
            this.includeDesktopClients = true;
            this.includeMobileClients = true;
        }

        public Builder withIncludeWebSessions(Boolean includeWebSessions) {
            if (includeWebSessions != null) {
                this.includeWebSessions = includeWebSessions.booleanValue();
            } else {
                this.includeWebSessions = true;
            }
            return this;
        }

        public Builder withIncludeDesktopClients(Boolean includeDesktopClients) {
            if (includeDesktopClients != null) {
                this.includeDesktopClients = includeDesktopClients.booleanValue();
            } else {
                this.includeDesktopClients = true;
            }
            return this;
        }

        public Builder withIncludeMobileClients(Boolean includeMobileClients) {
            if (includeMobileClients != null) {
                this.includeMobileClients = includeMobileClients.booleanValue();
            } else {
                this.includeMobileClients = true;
            }
            return this;
        }

        public ListMemberDevicesArg build() {
            return new ListMemberDevicesArg(this.teamMemberId, this.includeWebSessions, this.includeDesktopClients, this.includeMobileClients);
        }
    }

    static class Serializer extends StructSerializer<ListMemberDevicesArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListMemberDevicesArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("team_member_id");
            StoneSerializers.string().serialize(value.teamMemberId, g);
            g.writeFieldName("include_web_sessions");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.includeWebSessions), g);
            g.writeFieldName("include_desktop_clients");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.includeDesktopClients), g);
            g.writeFieldName("include_mobile_clients");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.includeMobileClients), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListMemberDevicesArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_teamMemberId = null;
                Boolean f_includeWebSessions = Boolean.valueOf(true);
                Boolean f_includeDesktopClients = Boolean.valueOf(true);
                Boolean f_includeMobileClients = Boolean.valueOf(true);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("team_member_id".equals(field)) {
                        f_teamMemberId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("include_web_sessions".equals(field)) {
                        f_includeWebSessions = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("include_desktop_clients".equals(field)) {
                        f_includeDesktopClients = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("include_mobile_clients".equals(field)) {
                        f_includeMobileClients = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_teamMemberId == null) {
                    throw new JsonParseException(p, "Required field \"team_member_id\" missing.");
                }
                ListMemberDevicesArg value = new ListMemberDevicesArg(f_teamMemberId, f_includeWebSessions.booleanValue(), f_includeDesktopClients.booleanValue(), f_includeMobileClients.booleanValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListMemberDevicesArg(String teamMemberId, boolean includeWebSessions, boolean includeDesktopClients, boolean includeMobileClients) {
        if (teamMemberId == null) {
            throw new IllegalArgumentException("Required value for 'teamMemberId' is null");
        }
        this.teamMemberId = teamMemberId;
        this.includeWebSessions = includeWebSessions;
        this.includeDesktopClients = includeDesktopClients;
        this.includeMobileClients = includeMobileClients;
    }

    public ListMemberDevicesArg(String teamMemberId) {
        this(teamMemberId, true, true, true);
    }

    public String getTeamMemberId() {
        return this.teamMemberId;
    }

    public boolean getIncludeWebSessions() {
        return this.includeWebSessions;
    }

    public boolean getIncludeDesktopClients() {
        return this.includeDesktopClients;
    }

    public boolean getIncludeMobileClients() {
        return this.includeMobileClients;
    }

    public static Builder newBuilder(String teamMemberId) {
        return new Builder(teamMemberId);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.teamMemberId, Boolean.valueOf(this.includeWebSessions), Boolean.valueOf(this.includeDesktopClients), Boolean.valueOf(this.includeMobileClients)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListMemberDevicesArg other = (ListMemberDevicesArg) obj;
        if ((this.teamMemberId == other.teamMemberId || this.teamMemberId.equals(other.teamMemberId)) && this.includeWebSessions == other.includeWebSessions && this.includeDesktopClients == other.includeDesktopClients && this.includeMobileClients == other.includeMobileClients) {
            return true;
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
