package com.dropbox.core.v2.users;

import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.models.BoxUploadEmail;
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

public class BasicAccount extends Account {
    protected final boolean isTeammate;
    protected final String teamMemberId;

    public static class Builder {
        protected final String accountId;
        protected final boolean disabled;
        protected final String email;
        protected final boolean emailVerified;
        protected final boolean isTeammate;
        protected final Name name;
        protected String profilePhotoUrl;
        protected String teamMemberId;

        protected Builder(String accountId, Name name, String email, boolean emailVerified, boolean disabled, boolean isTeammate) {
            if (accountId == null) {
                throw new IllegalArgumentException("Required value for 'accountId' is null");
            } else if (accountId.length() < 40) {
                throw new IllegalArgumentException("String 'accountId' is shorter than 40");
            } else if (accountId.length() > 40) {
                throw new IllegalArgumentException("String 'accountId' is longer than 40");
            } else {
                this.accountId = accountId;
                if (name == null) {
                    throw new IllegalArgumentException("Required value for 'name' is null");
                }
                this.name = name;
                if (email == null) {
                    throw new IllegalArgumentException("Required value for 'email' is null");
                }
                this.email = email;
                this.emailVerified = emailVerified;
                this.disabled = disabled;
                this.isTeammate = isTeammate;
                this.profilePhotoUrl = null;
                this.teamMemberId = null;
            }
        }

        public Builder withProfilePhotoUrl(String profilePhotoUrl) {
            this.profilePhotoUrl = profilePhotoUrl;
            return this;
        }

        public Builder withTeamMemberId(String teamMemberId) {
            this.teamMemberId = teamMemberId;
            return this;
        }

        public BasicAccount build() {
            return new BasicAccount(this.accountId, this.name, this.email, this.emailVerified, this.disabled, this.isTeammate, this.profilePhotoUrl, this.teamMemberId);
        }
    }

    static class Serializer extends StructSerializer<BasicAccount> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(BasicAccount value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("account_id");
            StoneSerializers.string().serialize(value.accountId, g);
            g.writeFieldName(BoxFileVersion.FIELD_NAME);
            com.dropbox.core.v2.users.Name.Serializer.INSTANCE.serialize(value.name, g);
            g.writeFieldName(BoxUploadEmail.FIELD_EMAIL);
            StoneSerializers.string().serialize(value.email, g);
            g.writeFieldName("email_verified");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.emailVerified), g);
            g.writeFieldName("disabled");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.disabled), g);
            g.writeFieldName("is_teammate");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.isTeammate), g);
            if (value.profilePhotoUrl != null) {
                g.writeFieldName("profile_photo_url");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.profilePhotoUrl, g);
            }
            if (value.teamMemberId != null) {
                g.writeFieldName("team_member_id");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.teamMemberId, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public BasicAccount deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_accountId = null;
                Name f_name = null;
                String f_email = null;
                Boolean f_emailVerified = null;
                Boolean f_disabled = null;
                Boolean f_isTeammate = null;
                String f_profilePhotoUrl = null;
                String f_teamMemberId = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("account_id".equals(field)) {
                        f_accountId = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxFileVersion.FIELD_NAME.equals(field)) {
                        f_name = (Name) com.dropbox.core.v2.users.Name.Serializer.INSTANCE.deserialize(p);
                    } else if (BoxUploadEmail.FIELD_EMAIL.equals(field)) {
                        f_email = (String) StoneSerializers.string().deserialize(p);
                    } else if ("email_verified".equals(field)) {
                        f_emailVerified = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("disabled".equals(field)) {
                        f_disabled = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("is_teammate".equals(field)) {
                        f_isTeammate = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("profile_photo_url".equals(field)) {
                        f_profilePhotoUrl = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("team_member_id".equals(field)) {
                        f_teamMemberId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_accountId == null) {
                    throw new JsonParseException(p, "Required field \"account_id\" missing.");
                } else if (f_name == null) {
                    throw new JsonParseException(p, "Required field \"name\" missing.");
                } else if (f_email == null) {
                    throw new JsonParseException(p, "Required field \"email\" missing.");
                } else if (f_emailVerified == null) {
                    throw new JsonParseException(p, "Required field \"email_verified\" missing.");
                } else if (f_disabled == null) {
                    throw new JsonParseException(p, "Required field \"disabled\" missing.");
                } else if (f_isTeammate == null) {
                    throw new JsonParseException(p, "Required field \"is_teammate\" missing.");
                } else {
                    BasicAccount value = new BasicAccount(f_accountId, f_name, f_email, f_emailVerified.booleanValue(), f_disabled.booleanValue(), f_isTeammate.booleanValue(), f_profilePhotoUrl, f_teamMemberId);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public BasicAccount(String accountId, Name name, String email, boolean emailVerified, boolean disabled, boolean isTeammate, String profilePhotoUrl, String teamMemberId) {
        super(accountId, name, email, emailVerified, disabled, profilePhotoUrl);
        this.isTeammate = isTeammate;
        this.teamMemberId = teamMemberId;
    }

    public BasicAccount(String accountId, Name name, String email, boolean emailVerified, boolean disabled, boolean isTeammate) {
        this(accountId, name, email, emailVerified, disabled, isTeammate, null, null);
    }

    public String getAccountId() {
        return this.accountId;
    }

    public Name getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public boolean getEmailVerified() {
        return this.emailVerified;
    }

    public boolean getDisabled() {
        return this.disabled;
    }

    public boolean getIsTeammate() {
        return this.isTeammate;
    }

    public String getProfilePhotoUrl() {
        return this.profilePhotoUrl;
    }

    public String getTeamMemberId() {
        return this.teamMemberId;
    }

    public static Builder newBuilder(String accountId, Name name, String email, boolean emailVerified, boolean disabled, boolean isTeammate) {
        return new Builder(accountId, name, email, emailVerified, disabled, isTeammate);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Boolean.valueOf(this.isTeammate), this.teamMemberId}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        BasicAccount other = (BasicAccount) obj;
        if ((this.accountId == other.accountId || this.accountId.equals(other.accountId)) && ((this.name == other.name || this.name.equals(other.name)) && ((this.email == other.email || this.email.equals(other.email)) && this.emailVerified == other.emailVerified && this.disabled == other.disabled && this.isTeammate == other.isTeammate && (this.profilePhotoUrl == other.profilePhotoUrl || (this.profilePhotoUrl != null && this.profilePhotoUrl.equals(other.profilePhotoUrl)))))) {
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
