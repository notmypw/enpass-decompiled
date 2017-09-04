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

class GetAccountArg {
    protected final String accountId;

    static class Serializer extends StructSerializer<GetAccountArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GetAccountArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("account_id");
            StoneSerializers.string().serialize(value.accountId, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GetAccountArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_accountId = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("account_id".equals(field)) {
                        f_accountId = (String) StoneSerializers.string().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_accountId == null) {
                    throw new JsonParseException(p, "Required field \"account_id\" missing.");
                }
                GetAccountArg value = new GetAccountArg(f_accountId);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GetAccountArg(String accountId) {
        if (accountId == null) {
            throw new IllegalArgumentException("Required value for 'accountId' is null");
        } else if (accountId.length() < 40) {
            throw new IllegalArgumentException("String 'accountId' is shorter than 40");
        } else if (accountId.length() > 40) {
            throw new IllegalArgumentException("String 'accountId' is longer than 40");
        } else {
            this.accountId = accountId;
        }
    }

    public String getAccountId() {
        return this.accountId;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.accountId});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GetAccountArg other = (GetAccountArg) obj;
        if (this.accountId == other.accountId || this.accountId.equals(other.accountId)) {
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
