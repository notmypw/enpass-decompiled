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

public class TokenFromOAuth1Result {
    protected final String oauth2Token;

    static class Serializer extends StructSerializer<TokenFromOAuth1Result> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(TokenFromOAuth1Result value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("oauth2_token");
            StoneSerializers.string().serialize(value.oauth2Token, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public TokenFromOAuth1Result deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_oauth2Token = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("oauth2_token".equals(field)) {
                        f_oauth2Token = (String) StoneSerializers.string().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_oauth2Token == null) {
                    throw new JsonParseException(p, "Required field \"oauth2_token\" missing.");
                }
                TokenFromOAuth1Result value = new TokenFromOAuth1Result(f_oauth2Token);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public TokenFromOAuth1Result(String oauth2Token) {
        if (oauth2Token == null) {
            throw new IllegalArgumentException("Required value for 'oauth2Token' is null");
        } else if (oauth2Token.length() < 1) {
            throw new IllegalArgumentException("String 'oauth2Token' is shorter than 1");
        } else {
            this.oauth2Token = oauth2Token;
        }
    }

    public String getOauth2Token() {
        return this.oauth2Token;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.oauth2Token});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        TokenFromOAuth1Result other = (TokenFromOAuth1Result) obj;
        if (this.oauth2Token == other.oauth2Token || this.oauth2Token.equals(other.oauth2Token)) {
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
