package com.dropbox.core.v2.paper;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.v2.sharing.InviteeInfo;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;

public class InviteeInfoWithPermissionLevel {
    protected final InviteeInfo invitee;
    protected final PaperDocPermissionLevel permissionLevel;

    static class Serializer extends StructSerializer<InviteeInfoWithPermissionLevel> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(InviteeInfoWithPermissionLevel value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("invitee");
            com.dropbox.core.v2.sharing.InviteeInfo.Serializer.INSTANCE.serialize(value.invitee, g);
            g.writeFieldName("permission_level");
            Serializer.INSTANCE.serialize(value.permissionLevel, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public InviteeInfoWithPermissionLevel deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                InviteeInfo f_invitee = null;
                PaperDocPermissionLevel f_permissionLevel = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("invitee".equals(field)) {
                        f_invitee = com.dropbox.core.v2.sharing.InviteeInfo.Serializer.INSTANCE.deserialize(p);
                    } else if ("permission_level".equals(field)) {
                        f_permissionLevel = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_invitee == null) {
                    throw new JsonParseException(p, "Required field \"invitee\" missing.");
                } else if (f_permissionLevel == null) {
                    throw new JsonParseException(p, "Required field \"permission_level\" missing.");
                } else {
                    InviteeInfoWithPermissionLevel value = new InviteeInfoWithPermissionLevel(f_invitee, f_permissionLevel);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public InviteeInfoWithPermissionLevel(InviteeInfo invitee, PaperDocPermissionLevel permissionLevel) {
        if (invitee == null) {
            throw new IllegalArgumentException("Required value for 'invitee' is null");
        }
        this.invitee = invitee;
        if (permissionLevel == null) {
            throw new IllegalArgumentException("Required value for 'permissionLevel' is null");
        }
        this.permissionLevel = permissionLevel;
    }

    public InviteeInfo getInvitee() {
        return this.invitee;
    }

    public PaperDocPermissionLevel getPermissionLevel() {
        return this.permissionLevel;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.invitee, this.permissionLevel});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        InviteeInfoWithPermissionLevel other = (InviteeInfoWithPermissionLevel) obj;
        if ((this.invitee == other.invitee || this.invitee.equals(other.invitee)) && (this.permissionLevel == other.permissionLevel || this.permissionLevel.equals(other.permissionLevel))) {
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
