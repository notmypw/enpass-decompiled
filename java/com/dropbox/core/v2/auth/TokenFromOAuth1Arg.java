package com.dropbox.core.v2.auth;

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

class TokenFromOAuth1Arg {
    protected final String oauth1Token;
    protected final String oauth1TokenSecret;

    static class Serializer extends StructSerializer<TokenFromOAuth1Arg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(TokenFromOAuth1Arg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("oauth1_token");
            StoneSerializers.string().serialize(value.oauth1Token, g);
            g.writeFieldName("oauth1_token_secret");
            StoneSerializers.string().serialize(value.oauth1TokenSecret, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public TokenFromOAuth1Arg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_oauth1Token = null;
                String f_oauth1TokenSecret = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("oauth1_token".equals(field)) {
                        f_oauth1Token = (String) StoneSerializers.string().deserialize(p);
                    } else if ("oauth1_token_secret".equals(field)) {
                        f_oauth1TokenSecret = (String) StoneSerializers.string().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_oauth1Token == null) {
                    throw new JsonParseException(p, "Required field \"oauth1_token\" missing.");
                } else if (f_oauth1TokenSecret == null) {
                    throw new JsonParseException(p, "Required field \"oauth1_token_secret\" missing.");
                } else {
                    TokenFromOAuth1Arg value = new TokenFromOAuth1Arg(f_oauth1Token, f_oauth1TokenSecret);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public TokenFromOAuth1Arg(String oauth1Token, String oauth1TokenSecret) {
        if (oauth1Token == null) {
            throw new IllegalArgumentException("Required value for 'oauth1Token' is null");
        } else if (oauth1Token.length() < 1) {
            throw new IllegalArgumentException("String 'oauth1Token' is shorter than 1");
        } else {
            this.oauth1Token = oauth1Token;
            if (oauth1TokenSecret == null) {
                throw new IllegalArgumentException("Required value for 'oauth1TokenSecret' is null");
            } else if (oauth1TokenSecret.length() < 1) {
                throw new IllegalArgumentException("String 'oauth1TokenSecret' is shorter than 1");
            } else {
                this.oauth1TokenSecret = oauth1TokenSecret;
            }
        }
    }

    public String getOauth1Token() {
        return this.oauth1Token;
    }

    public String getOauth1TokenSecret() {
        return this.oauth1TokenSecret;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.oauth1Token, this.oauth1TokenSecret});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        TokenFromOAuth1Arg other = (TokenFromOAuth1Arg) obj;
        if ((this.oauth1Token == other.oauth1Token || this.oauth1Token.equals(other.oauth1Token)) && (this.oauth1TokenSecret == other.oauth1TokenSecret || this.oauth1TokenSecret.equals(other.oauth1TokenSecret))) {
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
