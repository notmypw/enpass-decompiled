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

public class UserMembershipInfo extends MembershipInfo {
    protected final UserInfo user;

    public static class Builder extends com.dropbox.core.v2.sharing.MembershipInfo.Builder {
        protected final UserInfo user;

        protected Builder(AccessLevel accessType, UserInfo user) {
            super(accessType);
            if (user == null) {
                throw new IllegalArgumentException("Required value for 'user' is null");
            }
            this.user = user;
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

        public UserMembershipInfo build() {
            return new UserMembershipInfo(this.accessType, this.user, this.permissions, this.initials, this.isInherited);
        }
    }

    static class Serializer extends StructSerializer<UserMembershipInfo> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UserMembershipInfo value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("access_type");
            Serializer.INSTANCE.serialize(value.accessType, g);
            g.writeFieldName(BoxUser.TYPE);
            com.dropbox.core.v2.sharing.UserInfo.Serializer.INSTANCE.serialize(value.user, g);
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
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public UserMembershipInfo deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                AccessLevel f_accessType = null;
                UserInfo f_user = null;
                List<MemberPermission> f_permissions = null;
                String f_initials = null;
                Boolean f_isInherited = Boolean.valueOf(false);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("access_type".equals(field)) {
                        f_accessType = Serializer.INSTANCE.deserialize(p);
                    } else if (BoxUser.TYPE.equals(field)) {
                        f_user = (UserInfo) com.dropbox.core.v2.sharing.UserInfo.Serializer.INSTANCE.deserialize(p);
                    } else if (BoxSharedLink.FIELD_PERMISSIONS.equals(field)) {
                        f_permissions = (List) StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).deserialize(p);
                    } else if ("initials".equals(field)) {
                        f_initials = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("is_inherited".equals(field)) {
                        f_isInherited = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_accessType == null) {
                    throw new JsonParseException(p, "Required field \"access_type\" missing.");
                } else if (f_user == null) {
                    throw new JsonParseException(p, "Required field \"user\" missing.");
                } else {
                    UserMembershipInfo value = new UserMembershipInfo(f_accessType, f_user, f_permissions, f_initials, f_isInherited.booleanValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public UserMembershipInfo(AccessLevel accessType, UserInfo user, List<MemberPermission> permissions, String initials, boolean isInherited) {
        super(accessType, permissions, initials, isInherited);
        if (user == null) {
            throw new IllegalArgumentException("Required value for 'user' is null");
        }
        this.user = user;
    }

    public UserMembershipInfo(AccessLevel accessType, UserInfo user) {
        this(accessType, user, null, null, false);
    }

    public AccessLevel getAccessType() {
        return this.accessType;
    }

    public UserInfo getUser() {
        return this.user;
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

    public static Builder newBuilder(AccessLevel accessType, UserInfo user) {
        return new Builder(accessType, user);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.user}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        UserMembershipInfo other = (UserMembershipInfo) obj;
        if ((this.accessType == other.accessType || this.accessType.equals(other.accessType)) && ((this.user == other.user || this.user.equals(other.user)) && ((this.permissions == other.permissions || (this.permissions != null && this.permissions.equals(other.permissions))) && ((this.initials == other.initials || (this.initials != null && this.initials.equals(other.initials))) && this.isInherited == other.isInherited)))) {
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
