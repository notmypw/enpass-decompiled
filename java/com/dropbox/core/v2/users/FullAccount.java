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

public class FullAccount extends Account {
    protected final AccountType accountType;
    protected final String country;
    protected final boolean isPaired;
    protected final String locale;
    protected final String referralLink;
    protected final FullTeam team;
    protected final String teamMemberId;

    public static class Builder {
        protected final String accountId;
        protected final AccountType accountType;
        protected String country;
        protected final boolean disabled;
        protected final String email;
        protected final boolean emailVerified;
        protected final boolean isPaired;
        protected final String locale;
        protected final Name name;
        protected String profilePhotoUrl;
        protected final String referralLink;
        protected FullTeam team;
        protected String teamMemberId;

        protected Builder(String accountId, Name name, String email, boolean emailVerified, boolean disabled, String locale, String referralLink, boolean isPaired, AccountType accountType) {
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
                if (locale == null) {
                    throw new IllegalArgumentException("Required value for 'locale' is null");
                } else if (locale.length() < 2) {
                    throw new IllegalArgumentException("String 'locale' is shorter than 2");
                } else {
                    this.locale = locale;
                    if (referralLink == null) {
                        throw new IllegalArgumentException("Required value for 'referralLink' is null");
                    }
                    this.referralLink = referralLink;
                    this.isPaired = isPaired;
                    if (accountType == null) {
                        throw new IllegalArgumentException("Required value for 'accountType' is null");
                    }
                    this.accountType = accountType;
                    this.profilePhotoUrl = null;
                    this.country = null;
                    this.team = null;
                    this.teamMemberId = null;
                }
            }
        }

        public Builder withProfilePhotoUrl(String profilePhotoUrl) {
            this.profilePhotoUrl = profilePhotoUrl;
            return this;
        }

        public Builder withCountry(String country) {
            if (country != null) {
                if (country.length() < 2) {
                    throw new IllegalArgumentException("String 'country' is shorter than 2");
                } else if (country.length() > 2) {
                    throw new IllegalArgumentException("String 'country' is longer than 2");
                }
            }
            this.country = country;
            return this;
        }

        public Builder withTeam(FullTeam team) {
            this.team = team;
            return this;
        }

        public Builder withTeamMemberId(String teamMemberId) {
            this.teamMemberId = teamMemberId;
            return this;
        }

        public FullAccount build() {
            return new FullAccount(this.accountId, this.name, this.email, this.emailVerified, this.disabled, this.locale, this.referralLink, this.isPaired, this.accountType, this.profilePhotoUrl, this.country, this.team, this.teamMemberId);
        }
    }

    static class Serializer extends StructSerializer<FullAccount> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(FullAccount value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
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
            g.writeFieldName("locale");
            StoneSerializers.string().serialize(value.locale, g);
            g.writeFieldName("referral_link");
            StoneSerializers.string().serialize(value.referralLink, g);
            g.writeFieldName("is_paired");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.isPaired), g);
            g.writeFieldName("account_type");
            Serializer.INSTANCE.serialize(value.accountType, g);
            if (value.profilePhotoUrl != null) {
                g.writeFieldName("profile_photo_url");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.profilePhotoUrl, g);
            }
            if (value.country != null) {
                g.writeFieldName("country");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.country, g);
            }
            if (value.team != null) {
                g.writeFieldName("team");
                StoneSerializers.nullableStruct(Serializer.INSTANCE).serialize(value.team, g);
            }
            if (value.teamMemberId != null) {
                g.writeFieldName("team_member_id");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.teamMemberId, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public FullAccount deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
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
                String f_locale = null;
                String f_referralLink = null;
                Boolean f_isPaired = null;
                AccountType f_accountType = null;
                String f_profilePhotoUrl = null;
                String f_country = null;
                FullTeam f_team = null;
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
                    } else if ("locale".equals(field)) {
                        f_locale = (String) StoneSerializers.string().deserialize(p);
                    } else if ("referral_link".equals(field)) {
                        f_referralLink = (String) StoneSerializers.string().deserialize(p);
                    } else if ("is_paired".equals(field)) {
                        f_isPaired = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("account_type".equals(field)) {
                        f_accountType = Serializer.INSTANCE.deserialize(p);
                    } else if ("profile_photo_url".equals(field)) {
                        f_profilePhotoUrl = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("country".equals(field)) {
                        f_country = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("team".equals(field)) {
                        f_team = (FullTeam) StoneSerializers.nullableStruct(Serializer.INSTANCE).deserialize(p);
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
                } else if (f_locale == null) {
                    throw new JsonParseException(p, "Required field \"locale\" missing.");
                } else if (f_referralLink == null) {
                    throw new JsonParseException(p, "Required field \"referral_link\" missing.");
                } else if (f_isPaired == null) {
                    throw new JsonParseException(p, "Required field \"is_paired\" missing.");
                } else if (f_accountType == null) {
                    throw new JsonParseException(p, "Required field \"account_type\" missing.");
                } else {
                    FullAccount value = new FullAccount(f_accountId, f_name, f_email, f_emailVerified.booleanValue(), f_disabled.booleanValue(), f_locale, f_referralLink, f_isPaired.booleanValue(), f_accountType, f_profilePhotoUrl, f_country, f_team, f_teamMemberId);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public FullAccount(String accountId, Name name, String email, boolean emailVerified, boolean disabled, String locale, String referralLink, boolean isPaired, AccountType accountType, String profilePhotoUrl, String country, FullTeam team, String teamMemberId) {
        super(accountId, name, email, emailVerified, disabled, profilePhotoUrl);
        if (country != null) {
            if (country.length() < 2) {
                throw new IllegalArgumentException("String 'country' is shorter than 2");
            } else if (country.length() > 2) {
                throw new IllegalArgumentException("String 'country' is longer than 2");
            }
        }
        this.country = country;
        if (locale == null) {
            throw new IllegalArgumentException("Required value for 'locale' is null");
        } else if (locale.length() < 2) {
            throw new IllegalArgumentException("String 'locale' is shorter than 2");
        } else {
            this.locale = locale;
            if (referralLink == null) {
                throw new IllegalArgumentException("Required value for 'referralLink' is null");
            }
            this.referralLink = referralLink;
            this.team = team;
            this.teamMemberId = teamMemberId;
            this.isPaired = isPaired;
            if (accountType == null) {
                throw new IllegalArgumentException("Required value for 'accountType' is null");
            }
            this.accountType = accountType;
        }
    }

    public FullAccount(String accountId, Name name, String email, boolean emailVerified, boolean disabled, String locale, String referralLink, boolean isPaired, AccountType accountType) {
        this(accountId, name, email, emailVerified, disabled, locale, referralLink, isPaired, accountType, null, null, null, null);
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

    public String getLocale() {
        return this.locale;
    }

    public String getReferralLink() {
        return this.referralLink;
    }

    public boolean getIsPaired() {
        return this.isPaired;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public String getProfilePhotoUrl() {
        return this.profilePhotoUrl;
    }

    public String getCountry() {
        return this.country;
    }

    public FullTeam getTeam() {
        return this.team;
    }

    public String getTeamMemberId() {
        return this.teamMemberId;
    }

    public static Builder newBuilder(String accountId, Name name, String email, boolean emailVerified, boolean disabled, String locale, String referralLink, boolean isPaired, AccountType accountType) {
        return new Builder(accountId, name, email, emailVerified, disabled, locale, referralLink, isPaired, accountType);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.country, this.locale, this.referralLink, this.team, this.teamMemberId, Boolean.valueOf(this.isPaired), this.accountType}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        FullAccount other = (FullAccount) obj;
        if ((this.accountId == other.accountId || this.accountId.equals(other.accountId)) && ((this.name == other.name || this.name.equals(other.name)) && ((this.email == other.email || this.email.equals(other.email)) && this.emailVerified == other.emailVerified && this.disabled == other.disabled && ((this.locale == other.locale || this.locale.equals(other.locale)) && ((this.referralLink == other.referralLink || this.referralLink.equals(other.referralLink)) && this.isPaired == other.isPaired && ((this.accountType == other.accountType || this.accountType.equals(other.accountType)) && ((this.profilePhotoUrl == other.profilePhotoUrl || (this.profilePhotoUrl != null && this.profilePhotoUrl.equals(other.profilePhotoUrl))) && ((this.country == other.country || (this.country != null && this.country.equals(other.country))) && (this.team == other.team || (this.team != null && this.team.equals(other.team))))))))))) {
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
