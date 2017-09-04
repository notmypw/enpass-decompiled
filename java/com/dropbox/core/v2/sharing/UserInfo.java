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

public class UserInfo {
    protected final String accountId;
    protected final boolean sameTeam;
    protected final String teamMemberId;

    public static class Serializer extends StructSerializer<UserInfo> {
        public static final Serializer INSTANCE = new Serializer();

        public void serialize(UserInfo value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("account_id");
            StoneSerializers.string().serialize(value.accountId, g);
            g.writeFieldName("same_team");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.sameTeam), g);
            if (value.teamMemberId != null) {
                g.writeFieldName("team_member_id");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.teamMemberId, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public UserInfo deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_accountId = null;
                Boolean f_sameTeam = null;
                String f_teamMemberId = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("account_id".equals(field)) {
                        f_accountId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("same_team".equals(field)) {
                        f_sameTeam = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("team_member_id".equals(field)) {
                        f_teamMemberId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_accountId == null) {
                    throw new JsonParseException(p, "Required field \"account_id\" missing.");
                } else if (f_sameTeam == null) {
                    throw new JsonParseException(p, "Required field \"same_team\" missing.");
                } else {
                    UserInfo value = new UserInfo(f_accountId, f_sameTeam.booleanValue(), f_teamMemberId);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public UserInfo(String accountId, boolean sameTeam, String teamMemberId) {
        if (accountId == null) {
            throw new IllegalArgumentException("Required value for 'accountId' is null");
        } else if (accountId.length() < 40) {
            throw new IllegalArgumentException("String 'accountId' is shorter than 40");
        } else if (accountId.length() > 40) {
            throw new IllegalArgumentException("String 'accountId' is longer than 40");
        } else {
            this.accountId = accountId;
            this.sameTeam = sameTeam;
            this.teamMemberId = teamMemberId;
        }
    }

    public UserInfo(String accountId, boolean sameTeam) {
        this(accountId, sameTeam, null);
    }

    public String getAccountId() {
        return this.accountId;
    }

    public boolean getSameTeam() {
        return this.sameTeam;
    }

    public String getTeamMemberId() {
        return this.teamMemberId;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.accountId, Boolean.valueOf(this.sameTeam), this.teamMemberId});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        UserInfo other = (UserInfo) obj;
        if ((this.accountId == other.accountId || this.accountId.equals(other.accountId)) && this.sameTeam == other.sameTeam) {
            if (this.teamMemberId == other.teamMemberId) {
                return true;
            }
            if (this.teamMemberId != null && this.teamMemberId.equals(other.teamMemberId)) {
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
