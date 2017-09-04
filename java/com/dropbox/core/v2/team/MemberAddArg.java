package com.dropbox.core.v2.team;

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
import java.util.regex.Pattern;

public class MemberAddArg {
    protected final String memberEmail;
    protected final String memberExternalId;
    protected final String memberGivenName;
    protected final String memberPersistentId;
    protected final String memberSurname;
    protected final AdminTier role;
    protected final boolean sendWelcomeEmail;

    public static class Builder {
        protected final String memberEmail;
        protected String memberExternalId;
        protected final String memberGivenName;
        protected String memberPersistentId;
        protected final String memberSurname;
        protected AdminTier role;
        protected boolean sendWelcomeEmail;

        protected Builder(String memberEmail, String memberGivenName, String memberSurname) {
            if (memberEmail == null) {
                throw new IllegalArgumentException("Required value for 'memberEmail' is null");
            } else if (memberEmail.length() > 255) {
                throw new IllegalArgumentException("String 'memberEmail' is longer than 255");
            } else if (Pattern.matches("^['&A-Za-z0-9._%+-]+@[A-Za-z0-9-][A-Za-z0-9.-]*.[A-Za-z]{2,15}$", memberEmail)) {
                this.memberEmail = memberEmail;
                if (memberGivenName == null) {
                    throw new IllegalArgumentException("Required value for 'memberGivenName' is null");
                } else if (memberGivenName.length() < 1) {
                    throw new IllegalArgumentException("String 'memberGivenName' is shorter than 1");
                } else if (memberGivenName.length() > 100) {
                    throw new IllegalArgumentException("String 'memberGivenName' is longer than 100");
                } else if (Pattern.matches("[^/:?*<>\"|]*", memberGivenName)) {
                    this.memberGivenName = memberGivenName;
                    if (memberSurname == null) {
                        throw new IllegalArgumentException("Required value for 'memberSurname' is null");
                    } else if (memberSurname.length() < 1) {
                        throw new IllegalArgumentException("String 'memberSurname' is shorter than 1");
                    } else if (memberSurname.length() > 100) {
                        throw new IllegalArgumentException("String 'memberSurname' is longer than 100");
                    } else if (Pattern.matches("[^/:?*<>\"|]*", memberSurname)) {
                        this.memberSurname = memberSurname;
                        this.memberExternalId = null;
                        this.memberPersistentId = null;
                        this.sendWelcomeEmail = true;
                        this.role = AdminTier.MEMBER_ONLY;
                    } else {
                        throw new IllegalArgumentException("String 'memberSurname' does not match pattern");
                    }
                } else {
                    throw new IllegalArgumentException("String 'memberGivenName' does not match pattern");
                }
            } else {
                throw new IllegalArgumentException("String 'memberEmail' does not match pattern");
            }
        }

        public Builder withMemberExternalId(String memberExternalId) {
            if (memberExternalId == null || memberExternalId.length() <= 64) {
                this.memberExternalId = memberExternalId;
                return this;
            }
            throw new IllegalArgumentException("String 'memberExternalId' is longer than 64");
        }

        public Builder withMemberPersistentId(String memberPersistentId) {
            this.memberPersistentId = memberPersistentId;
            return this;
        }

        public Builder withSendWelcomeEmail(Boolean sendWelcomeEmail) {
            if (sendWelcomeEmail != null) {
                this.sendWelcomeEmail = sendWelcomeEmail.booleanValue();
            } else {
                this.sendWelcomeEmail = true;
            }
            return this;
        }

        public Builder withRole(AdminTier role) {
            if (role != null) {
                this.role = role;
            } else {
                this.role = AdminTier.MEMBER_ONLY;
            }
            return this;
        }

        public MemberAddArg build() {
            return new MemberAddArg(this.memberEmail, this.memberGivenName, this.memberSurname, this.memberExternalId, this.memberPersistentId, this.sendWelcomeEmail, this.role);
        }
    }

    static class Serializer extends StructSerializer<MemberAddArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(MemberAddArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("member_email");
            StoneSerializers.string().serialize(value.memberEmail, g);
            g.writeFieldName("member_given_name");
            StoneSerializers.string().serialize(value.memberGivenName, g);
            g.writeFieldName("member_surname");
            StoneSerializers.string().serialize(value.memberSurname, g);
            if (value.memberExternalId != null) {
                g.writeFieldName("member_external_id");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.memberExternalId, g);
            }
            if (value.memberPersistentId != null) {
                g.writeFieldName("member_persistent_id");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.memberPersistentId, g);
            }
            g.writeFieldName("send_welcome_email");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.sendWelcomeEmail), g);
            g.writeFieldName(BoxUser.FIELD_ROLE);
            Serializer.INSTANCE.serialize(value.role, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public MemberAddArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_memberEmail = null;
                String f_memberGivenName = null;
                String f_memberSurname = null;
                String f_memberExternalId = null;
                String f_memberPersistentId = null;
                Boolean f_sendWelcomeEmail = Boolean.valueOf(true);
                AdminTier f_role = AdminTier.MEMBER_ONLY;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("member_email".equals(field)) {
                        f_memberEmail = (String) StoneSerializers.string().deserialize(p);
                    } else if ("member_given_name".equals(field)) {
                        f_memberGivenName = (String) StoneSerializers.string().deserialize(p);
                    } else if ("member_surname".equals(field)) {
                        f_memberSurname = (String) StoneSerializers.string().deserialize(p);
                    } else if ("member_external_id".equals(field)) {
                        f_memberExternalId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("member_persistent_id".equals(field)) {
                        f_memberPersistentId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("send_welcome_email".equals(field)) {
                        f_sendWelcomeEmail = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if (BoxUser.FIELD_ROLE.equals(field)) {
                        f_role = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_memberEmail == null) {
                    throw new JsonParseException(p, "Required field \"member_email\" missing.");
                } else if (f_memberGivenName == null) {
                    throw new JsonParseException(p, "Required field \"member_given_name\" missing.");
                } else if (f_memberSurname == null) {
                    throw new JsonParseException(p, "Required field \"member_surname\" missing.");
                } else {
                    MemberAddArg value = new MemberAddArg(f_memberEmail, f_memberGivenName, f_memberSurname, f_memberExternalId, f_memberPersistentId, f_sendWelcomeEmail.booleanValue(), f_role);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public MemberAddArg(String memberEmail, String memberGivenName, String memberSurname, String memberExternalId, String memberPersistentId, boolean sendWelcomeEmail, AdminTier role) {
        if (memberEmail == null) {
            throw new IllegalArgumentException("Required value for 'memberEmail' is null");
        } else if (memberEmail.length() > 255) {
            throw new IllegalArgumentException("String 'memberEmail' is longer than 255");
        } else if (Pattern.matches("^['&A-Za-z0-9._%+-]+@[A-Za-z0-9-][A-Za-z0-9.-]*.[A-Za-z]{2,15}$", memberEmail)) {
            this.memberEmail = memberEmail;
            if (memberGivenName == null) {
                throw new IllegalArgumentException("Required value for 'memberGivenName' is null");
            } else if (memberGivenName.length() < 1) {
                throw new IllegalArgumentException("String 'memberGivenName' is shorter than 1");
            } else if (memberGivenName.length() > 100) {
                throw new IllegalArgumentException("String 'memberGivenName' is longer than 100");
            } else if (Pattern.matches("[^/:?*<>\"|]*", memberGivenName)) {
                this.memberGivenName = memberGivenName;
                if (memberSurname == null) {
                    throw new IllegalArgumentException("Required value for 'memberSurname' is null");
                } else if (memberSurname.length() < 1) {
                    throw new IllegalArgumentException("String 'memberSurname' is shorter than 1");
                } else if (memberSurname.length() > 100) {
                    throw new IllegalArgumentException("String 'memberSurname' is longer than 100");
                } else if (Pattern.matches("[^/:?*<>\"|]*", memberSurname)) {
                    this.memberSurname = memberSurname;
                    if (memberExternalId == null || memberExternalId.length() <= 64) {
                        this.memberExternalId = memberExternalId;
                        this.memberPersistentId = memberPersistentId;
                        this.sendWelcomeEmail = sendWelcomeEmail;
                        if (role == null) {
                            throw new IllegalArgumentException("Required value for 'role' is null");
                        }
                        this.role = role;
                        return;
                    }
                    throw new IllegalArgumentException("String 'memberExternalId' is longer than 64");
                } else {
                    throw new IllegalArgumentException("String 'memberSurname' does not match pattern");
                }
            } else {
                throw new IllegalArgumentException("String 'memberGivenName' does not match pattern");
            }
        } else {
            throw new IllegalArgumentException("String 'memberEmail' does not match pattern");
        }
    }

    public MemberAddArg(String memberEmail, String memberGivenName, String memberSurname) {
        this(memberEmail, memberGivenName, memberSurname, null, null, true, AdminTier.MEMBER_ONLY);
    }

    public String getMemberEmail() {
        return this.memberEmail;
    }

    public String getMemberGivenName() {
        return this.memberGivenName;
    }

    public String getMemberSurname() {
        return this.memberSurname;
    }

    public String getMemberExternalId() {
        return this.memberExternalId;
    }

    public String getMemberPersistentId() {
        return this.memberPersistentId;
    }

    public boolean getSendWelcomeEmail() {
        return this.sendWelcomeEmail;
    }

    public AdminTier getRole() {
        return this.role;
    }

    public static Builder newBuilder(String memberEmail, String memberGivenName, String memberSurname) {
        return new Builder(memberEmail, memberGivenName, memberSurname);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.memberEmail, this.memberGivenName, this.memberSurname, this.memberExternalId, this.memberPersistentId, Boolean.valueOf(this.sendWelcomeEmail), this.role});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        MemberAddArg other = (MemberAddArg) obj;
        if ((this.memberEmail == other.memberEmail || this.memberEmail.equals(other.memberEmail)) && ((this.memberGivenName == other.memberGivenName || this.memberGivenName.equals(other.memberGivenName)) && ((this.memberSurname == other.memberSurname || this.memberSurname.equals(other.memberSurname)) && ((this.memberExternalId == other.memberExternalId || (this.memberExternalId != null && this.memberExternalId.equals(other.memberExternalId))) && ((this.memberPersistentId == other.memberPersistentId || (this.memberPersistentId != null && this.memberPersistentId.equals(other.memberPersistentId))) && this.sendWelcomeEmail == other.sendWelcomeEmail && (this.role == other.role || this.role.equals(other.role))))))) {
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
