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

class MembersSetProfileArg {
    protected final String newEmail;
    protected final String newExternalId;
    protected final String newGivenName;
    protected final String newPersistentId;
    protected final String newSurname;
    protected final UserSelectorArg user;

    public static class Builder {
        protected String newEmail;
        protected String newExternalId;
        protected String newGivenName;
        protected String newPersistentId;
        protected String newSurname;
        protected final UserSelectorArg user;

        protected Builder(UserSelectorArg user) {
            if (user == null) {
                throw new IllegalArgumentException("Required value for 'user' is null");
            }
            this.user = user;
            this.newEmail = null;
            this.newExternalId = null;
            this.newGivenName = null;
            this.newSurname = null;
            this.newPersistentId = null;
        }

        public Builder withNewEmail(String newEmail) {
            if (newEmail != null) {
                if (newEmail.length() > 255) {
                    throw new IllegalArgumentException("String 'newEmail' is longer than 255");
                } else if (!Pattern.matches("^['&A-Za-z0-9._%+-]+@[A-Za-z0-9-][A-Za-z0-9.-]*.[A-Za-z]{2,15}$", newEmail)) {
                    throw new IllegalArgumentException("String 'newEmail' does not match pattern");
                }
            }
            this.newEmail = newEmail;
            return this;
        }

        public Builder withNewExternalId(String newExternalId) {
            if (newExternalId == null || newExternalId.length() <= 64) {
                this.newExternalId = newExternalId;
                return this;
            }
            throw new IllegalArgumentException("String 'newExternalId' is longer than 64");
        }

        public Builder withNewGivenName(String newGivenName) {
            if (newGivenName != null) {
                if (newGivenName.length() < 1) {
                    throw new IllegalArgumentException("String 'newGivenName' is shorter than 1");
                } else if (newGivenName.length() > 100) {
                    throw new IllegalArgumentException("String 'newGivenName' is longer than 100");
                } else if (!Pattern.matches("[^/:?*<>\"|]*", newGivenName)) {
                    throw new IllegalArgumentException("String 'newGivenName' does not match pattern");
                }
            }
            this.newGivenName = newGivenName;
            return this;
        }

        public Builder withNewSurname(String newSurname) {
            if (newSurname != null) {
                if (newSurname.length() < 1) {
                    throw new IllegalArgumentException("String 'newSurname' is shorter than 1");
                } else if (newSurname.length() > 100) {
                    throw new IllegalArgumentException("String 'newSurname' is longer than 100");
                } else if (!Pattern.matches("[^/:?*<>\"|]*", newSurname)) {
                    throw new IllegalArgumentException("String 'newSurname' does not match pattern");
                }
            }
            this.newSurname = newSurname;
            return this;
        }

        public Builder withNewPersistentId(String newPersistentId) {
            this.newPersistentId = newPersistentId;
            return this;
        }

        public MembersSetProfileArg build() {
            return new MembersSetProfileArg(this.user, this.newEmail, this.newExternalId, this.newGivenName, this.newSurname, this.newPersistentId);
        }
    }

    static class Serializer extends StructSerializer<MembersSetProfileArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(MembersSetProfileArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxUser.TYPE);
            Serializer.INSTANCE.serialize(value.user, g);
            if (value.newEmail != null) {
                g.writeFieldName("new_email");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.newEmail, g);
            }
            if (value.newExternalId != null) {
                g.writeFieldName("new_external_id");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.newExternalId, g);
            }
            if (value.newGivenName != null) {
                g.writeFieldName("new_given_name");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.newGivenName, g);
            }
            if (value.newSurname != null) {
                g.writeFieldName("new_surname");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.newSurname, g);
            }
            if (value.newPersistentId != null) {
                g.writeFieldName("new_persistent_id");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.newPersistentId, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public MembersSetProfileArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                UserSelectorArg f_user = null;
                String f_newEmail = null;
                String f_newExternalId = null;
                String f_newGivenName = null;
                String f_newSurname = null;
                String f_newPersistentId = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxUser.TYPE.equals(field)) {
                        f_user = Serializer.INSTANCE.deserialize(p);
                    } else if ("new_email".equals(field)) {
                        f_newEmail = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("new_external_id".equals(field)) {
                        f_newExternalId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("new_given_name".equals(field)) {
                        f_newGivenName = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("new_surname".equals(field)) {
                        f_newSurname = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("new_persistent_id".equals(field)) {
                        f_newPersistentId = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_user == null) {
                    throw new JsonParseException(p, "Required field \"user\" missing.");
                }
                MembersSetProfileArg value = new MembersSetProfileArg(f_user, f_newEmail, f_newExternalId, f_newGivenName, f_newSurname, f_newPersistentId);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public MembersSetProfileArg(UserSelectorArg user, String newEmail, String newExternalId, String newGivenName, String newSurname, String newPersistentId) {
        if (user == null) {
            throw new IllegalArgumentException("Required value for 'user' is null");
        }
        this.user = user;
        if (newEmail != null) {
            if (newEmail.length() > 255) {
                throw new IllegalArgumentException("String 'newEmail' is longer than 255");
            } else if (!Pattern.matches("^['&A-Za-z0-9._%+-]+@[A-Za-z0-9-][A-Za-z0-9.-]*.[A-Za-z]{2,15}$", newEmail)) {
                throw new IllegalArgumentException("String 'newEmail' does not match pattern");
            }
        }
        this.newEmail = newEmail;
        if (newExternalId == null || newExternalId.length() <= 64) {
            this.newExternalId = newExternalId;
            if (newGivenName != null) {
                if (newGivenName.length() < 1) {
                    throw new IllegalArgumentException("String 'newGivenName' is shorter than 1");
                } else if (newGivenName.length() > 100) {
                    throw new IllegalArgumentException("String 'newGivenName' is longer than 100");
                } else if (!Pattern.matches("[^/:?*<>\"|]*", newGivenName)) {
                    throw new IllegalArgumentException("String 'newGivenName' does not match pattern");
                }
            }
            this.newGivenName = newGivenName;
            if (newSurname != null) {
                if (newSurname.length() < 1) {
                    throw new IllegalArgumentException("String 'newSurname' is shorter than 1");
                } else if (newSurname.length() > 100) {
                    throw new IllegalArgumentException("String 'newSurname' is longer than 100");
                } else if (!Pattern.matches("[^/:?*<>\"|]*", newSurname)) {
                    throw new IllegalArgumentException("String 'newSurname' does not match pattern");
                }
            }
            this.newSurname = newSurname;
            this.newPersistentId = newPersistentId;
            return;
        }
        throw new IllegalArgumentException("String 'newExternalId' is longer than 64");
    }

    public MembersSetProfileArg(UserSelectorArg user) {
        this(user, null, null, null, null, null);
    }

    public UserSelectorArg getUser() {
        return this.user;
    }

    public String getNewEmail() {
        return this.newEmail;
    }

    public String getNewExternalId() {
        return this.newExternalId;
    }

    public String getNewGivenName() {
        return this.newGivenName;
    }

    public String getNewSurname() {
        return this.newSurname;
    }

    public String getNewPersistentId() {
        return this.newPersistentId;
    }

    public static Builder newBuilder(UserSelectorArg user) {
        return new Builder(user);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.user, this.newEmail, this.newExternalId, this.newGivenName, this.newSurname, this.newPersistentId});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        MembersSetProfileArg other = (MembersSetProfileArg) obj;
        if ((this.user == other.user || this.user.equals(other.user)) && ((this.newEmail == other.newEmail || (this.newEmail != null && this.newEmail.equals(other.newEmail))) && ((this.newExternalId == other.newExternalId || (this.newExternalId != null && this.newExternalId.equals(other.newExternalId))) && ((this.newGivenName == other.newGivenName || (this.newGivenName != null && this.newGivenName.equals(other.newGivenName))) && (this.newSurname == other.newSurname || (this.newSurname != null && this.newSurname.equals(other.newSurname))))))) {
            if (this.newPersistentId == other.newPersistentId) {
                return true;
            }
            if (this.newPersistentId != null && this.newPersistentId.equals(other.newPersistentId)) {
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
