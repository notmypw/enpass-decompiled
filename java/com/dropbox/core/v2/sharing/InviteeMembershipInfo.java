package com.dropbox.core.v2.sharing;

import com.box.androidsdk.content.models.BoxSharedLink;
import com.box.androidsdk.content.models.BoxUser;
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

public class InviteeMembershipInfo extends MembershipInfo {
    protected final InviteeInfo invitee;
    protected final UserInfo user;

    public static class Builder extends com.dropbox.core.v2.sharing.MembershipInfo.Builder {
        protected final InviteeInfo invitee;
        protected UserInfo user;

        protected Builder(AccessLevel accessType, InviteeInfo invitee) {
            super(accessType);
            if (invitee == null) {
                throw new IllegalArgumentException("Required value for 'invitee' is null");
            }
            this.invitee = invitee;
            this.user = null;
        }

        public Builder withUser(UserInfo user) {
            this.user = user;
            return this;
        }

        public Builder withPermissions(List<MemberPermission> permissions) {
            super.withPermissions(permissions);
            return this;
        }

        public Builder withInitials(String initials) {
            super.withInitials(initials);
            return this;
        }

        public Builder withIsInherited(Boolean isInherited) {
            super.withIsInherited(isInherited);
            return this;
        }

        public InviteeMembershipInfo build() {
            return new InviteeMembershipInfo(this.accessType, this.invitee, this.permissions, this.initials, this.isInherited, this.user);
        }
    }

    static class Serializer extends StructSerializer<InviteeMembershipInfo> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(InviteeMembershipInfo value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("access_type");
            Serializer.INSTANCE.serialize(value.accessType, g);
            g.writeFieldName("invitee");
            com.dropbox.core.v2.sharing.InviteeInfo.Serializer.INSTANCE.serialize(value.invitee, g);
            if (value.permissions != null) {
                g.writeFieldName(BoxSharedLink.FIELD_PERMISSIONS);
                StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).serialize(value.permissions, g);
            }
            if (value.initials != null) {
                g.writeFieldName("initials");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.initials, g);
            }
            g.writeFieldName("is_inherited");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.isInherited), g);
            if (value.user != null) {
                g.writeFieldName(BoxUser.TYPE);
                StoneSerializers.nullableStruct(com.dropbox.core.v2.sharing.UserInfo.Serializer.INSTANCE).serialize(value.user, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public InviteeMembershipInfo deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                AccessLevel f_accessType = null;
                InviteeInfo f_invitee = null;
                List<MemberPermission> f_permissions = null;
                String f_initials = null;
                Boolean f_isInherited = Boolean.valueOf(false);
                UserInfo f_user = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("access_type".equals(field)) {
                        f_accessType = Serializer.INSTANCE.deserialize(p);
                    } else if ("invitee".equals(field)) {
                        f_invitee = com.dropbox.core.v2.sharing.InviteeInfo.Serializer.INSTANCE.deserialize(p);
                    } else if (BoxSharedLink.FIELD_PERMISSIONS.equals(field)) {
                        f_permissions = (List) StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).deserialize(p);
                    } else if ("initials".equals(field)) {
                        f_initials = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("is_inherited".equals(field)) {
                        f_isInherited = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if (BoxUser.TYPE.equals(field)) {
                        f_user = (UserInfo) StoneSerializers.nullableStruct(com.dropbox.core.v2.sharing.UserInfo.Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_accessType == null) {
                    throw new JsonParseException(p, "Required field \"access_type\" missing.");
                } else if (f_invitee == null) {
                    throw new JsonParseException(p, "Required field \"invitee\" missing.");
                } else {
                    InviteeMembershipInfo value = new InviteeMembershipInfo(f_accessType, f_invitee, f_permissions, f_initials, f_isInherited.booleanValue(), f_user);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public InviteeMembershipInfo(AccessLevel accessType, InviteeInfo invitee, List<MemberPermission> permissions, String initials, boolean isInherited, UserInfo user) {
        super(accessType, permissions, initials, isInherited);
        if (invitee == null) {
            throw new IllegalArgumentException("Required value for 'invitee' is null");
        }
        this.invitee = invitee;
        this.user = user;
    }

    public InviteeMembershipInfo(AccessLevel accessType, InviteeInfo invitee) {
        this(accessType, invitee, null, null, false, null);
    }

    public AccessLevel getAccessType() {
        return this.accessType;
    }

    public InviteeInfo getInvitee() {
        return this.invitee;
    }

    public List<MemberPermission> getPermissions() {
        return this.permissions;
    }

    public String getInitials() {
        return this.initials;
    }

    public boolean getIsInherited() {
        return this.isInherited;
    }

    public UserInfo getUser() {
        return this.user;
    }

    public static Builder newBuilder(AccessLevel accessType, InviteeInfo invitee) {
        return new Builder(accessType, invitee);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.invitee, this.user}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        InviteeMembershipInfo other = (InviteeMembershipInfo) obj;
        if ((this.accessType == other.accessType || this.accessType.equals(other.accessType)) && ((this.invitee == other.invitee || this.invitee.equals(other.invitee)) && ((this.permissions == other.permissions || (this.permissions != null && this.permissions.equals(other.permissions))) && ((this.initials == other.initials || (this.initials != null && this.initials.equals(other.initials))) && this.isInherited == other.isInherited)))) {
            if (this.user == other.user) {
                return true;
            }
            if (this.user != null && this.user.equals(other.user)) {
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
