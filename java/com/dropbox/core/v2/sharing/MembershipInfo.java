package com.dropbox.core.v2.sharing;

import com.box.androidsdk.content.models.BoxSharedLink;
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

public class MembershipInfo {
    protected final AccessLevel accessType;
    protected final String initials;
    protected final boolean isInherited;
    protected final List<MemberPermission> permissions;

    public static class Builder {
        protected final AccessLevel accessType;
        protected String initials;
        protected boolean isInherited;
        protected List<MemberPermission> permissions;

        protected Builder(AccessLevel accessType) {
            if (accessType == null) {
                throw new IllegalArgumentException("Required value for 'accessType' is null");
            }
            this.accessType = accessType;
            this.permissions = null;
            this.initials = null;
            this.isInherited = false;
        }

        public Builder withPermissions(List<MemberPermission> permissions) {
            if (permissions != null) {
                for (MemberPermission x : permissions) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'permissions' is null");
                    }
                }
            }
            this.permissions = permissions;
            return this;
        }

        public Builder withInitials(String initials) {
            this.initials = initials;
            return this;
        }

        public Builder withIsInherited(Boolean isInherited) {
            if (isInherited != null) {
                this.isInherited = isInherited.booleanValue();
            } else {
                this.isInherited = false;
            }
            return this;
        }

        public MembershipInfo build() {
            return new MembershipInfo(this.accessType, this.permissions, this.initials, this.isInherited);
        }
    }

    private static class Serializer extends StructSerializer<MembershipInfo> {
        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {
        }

        public void serialize(MembershipInfo value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("access_type");
            Serializer.INSTANCE.serialize(value.accessType, g);
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

        public MembershipInfo deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                AccessLevel f_accessType = null;
                List<MemberPermission> f_permissions = null;
                String f_initials = null;
                Boolean f_isInherited = Boolean.valueOf(false);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("access_type".equals(field)) {
                        f_accessType = Serializer.INSTANCE.deserialize(p);
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
                }
                MembershipInfo value = new MembershipInfo(f_accessType, f_permissions, f_initials, f_isInherited.booleanValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public MembershipInfo(AccessLevel accessType, List<MemberPermission> permissions, String initials, boolean isInherited) {
        if (accessType == null) {
            throw new IllegalArgumentException("Required value for 'accessType' is null");
        }
        this.accessType = accessType;
        if (permissions != null) {
            for (MemberPermission x : permissions) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'permissions' is null");
                }
            }
        }
        this.permissions = permissions;
        this.initials = initials;
        this.isInherited = isInherited;
    }

    public MembershipInfo(AccessLevel accessType) {
        this(accessType, null, null, false);
    }

    public AccessLevel getAccessType() {
        return this.accessType;
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

    public static Builder newBuilder(AccessLevel accessType) {
        return new Builder(accessType);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.accessType, this.permissions, this.initials, Boolean.valueOf(this.isInherited)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        MembershipInfo other = (MembershipInfo) obj;
        if ((this.accessType == other.accessType || this.accessType.equals(other.accessType)) && ((this.permissions == other.permissions || (this.permissions != null && this.permissions.equals(other.permissions))) && ((this.initials == other.initials || (this.initials != null && this.initials.equals(other.initials))) && this.isInherited == other.isInherited))) {
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
