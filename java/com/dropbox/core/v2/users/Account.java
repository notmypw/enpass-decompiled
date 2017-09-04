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

public class Account {
    protected final String accountId;
    protected final boolean disabled;
    protected final String email;
    protected final boolean emailVerified;
    protected final Name name;
    protected final String profilePhotoUrl;

    private static class Serializer extends StructSerializer<Account> {
        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {
        }

        public void serialize(Account value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
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
            if (value.profilePhotoUrl != null) {
                g.writeFieldName("profile_photo_url");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.profilePhotoUrl, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public Account deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
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
                String f_profilePhotoUrl = null;
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
                    } else if ("profile_photo_url".equals(field)) {
                        f_profilePhotoUrl = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
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
                } else {
                    Account value = new Account(f_accountId, f_name, f_email, f_emailVerified.booleanValue(), f_disabled.booleanValue(), f_profilePhotoUrl);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public Account(String accountId, Name name, String email, boolean emailVerified, boolean disabled, String profilePhotoUrl) {
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
            this.profilePhotoUrl = profilePhotoUrl;
            this.disabled = disabled;
        }
    }

    public Account(String accountId, Name name, String email, boolean emailVerified, boolean disabled) {
        this(accountId, name, email, emailVerified, disabled, null);
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

    public String getProfilePhotoUrl() {
        return this.profilePhotoUrl;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.accountId, this.name, this.email, Boolean.valueOf(this.emailVerified), this.profilePhotoUrl, Boolean.valueOf(this.disabled)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        Account other = (Account) obj;
        if ((this.accountId == other.accountId || this.accountId.equals(other.accountId)) && ((this.name == other.name || this.name.equals(other.name)) && ((this.email == other.email || this.email.equals(other.email)) && this.emailVerified == other.emailVerified && this.disabled == other.disabled))) {
            if (this.profilePhotoUrl == other.profilePhotoUrl) {
                return true;
            }
            if (this.profilePhotoUrl != null && this.profilePhotoUrl.equals(other.profilePhotoUrl)) {
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
