package com.dropbox.core.v2.users;

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

public class Name {
    protected final String abbreviatedName;
    protected final String displayName;
    protected final String familiarName;
    protected final String givenName;
    protected final String surname;

    public static class Serializer extends StructSerializer<Name> {
        public static final Serializer INSTANCE = new Serializer();

        public void serialize(Name value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("given_name");
            StoneSerializers.string().serialize(value.givenName, g);
            g.writeFieldName("surname");
            StoneSerializers.string().serialize(value.surname, g);
            g.writeFieldName("familiar_name");
            StoneSerializers.string().serialize(value.familiarName, g);
            g.writeFieldName("display_name");
            StoneSerializers.string().serialize(value.displayName, g);
            g.writeFieldName("abbreviated_name");
            StoneSerializers.string().serialize(value.abbreviatedName, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public Name deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_givenName = null;
                String f_surname = null;
                String f_familiarName = null;
                String f_displayName = null;
                String f_abbreviatedName = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("given_name".equals(field)) {
                        f_givenName = (String) StoneSerializers.string().deserialize(p);
                    } else if ("surname".equals(field)) {
                        f_surname = (String) StoneSerializers.string().deserialize(p);
                    } else if ("familiar_name".equals(field)) {
                        f_familiarName = (String) StoneSerializers.string().deserialize(p);
                    } else if ("display_name".equals(field)) {
                        f_displayName = (String) StoneSerializers.string().deserialize(p);
                    } else if ("abbreviated_name".equals(field)) {
                        f_abbreviatedName = (String) StoneSerializers.string().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_givenName == null) {
                    throw new JsonParseException(p, "Required field \"given_name\" missing.");
                } else if (f_surname == null) {
                    throw new JsonParseException(p, "Required field \"surname\" missing.");
                } else if (f_familiarName == null) {
                    throw new JsonParseException(p, "Required field \"familiar_name\" missing.");
                } else if (f_displayName == null) {
                    throw new JsonParseException(p, "Required field \"display_name\" missing.");
                } else if (f_abbreviatedName == null) {
                    throw new JsonParseException(p, "Required field \"abbreviated_name\" missing.");
                } else {
                    Name value = new Name(f_givenName, f_surname, f_familiarName, f_displayName, f_abbreviatedName);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public Name(String givenName, String surname, String familiarName, String displayName, String abbreviatedName) {
        if (givenName == null) {
            throw new IllegalArgumentException("Required value for 'givenName' is null");
        }
        this.givenName = givenName;
        if (surname == null) {
            throw new IllegalArgumentException("Required value for 'surname' is null");
        }
        this.surname = surname;
        if (familiarName == null) {
            throw new IllegalArgumentException("Required value for 'familiarName' is null");
        }
        this.familiarName = familiarName;
        if (displayName == null) {
            throw new IllegalArgumentException("Required value for 'displayName' is null");
        }
        this.displayName = displayName;
        if (abbreviatedName == null) {
            throw new IllegalArgumentException("Required value for 'abbreviatedName' is null");
        }
        this.abbreviatedName = abbreviatedName;
    }

    public String getGivenName() {
        return this.givenName;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getFamiliarName() {
        return this.familiarName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getAbbreviatedName() {
        return this.abbreviatedName;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.givenName, this.surname, this.familiarName, this.displayName, this.abbreviatedName});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        Name other = (Name) obj;
        if ((this.givenName == other.givenName || this.givenName.equals(other.givenName)) && ((this.surname == other.surname || this.surname.equals(other.surname)) && ((this.familiarName == other.familiarName || this.familiarName.equals(other.familiarName)) && ((this.displayName == other.displayName || this.displayName.equals(other.displayName)) && (this.abbreviatedName == other.abbreviatedName || this.abbreviatedName.equals(other.abbreviatedName)))))) {
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
