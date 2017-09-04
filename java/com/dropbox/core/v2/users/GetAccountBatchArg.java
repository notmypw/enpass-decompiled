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
import java.util.List;

class GetAccountBatchArg {
    protected final List<String> accountIds;

    static class Serializer extends StructSerializer<GetAccountBatchArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GetAccountBatchArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("account_ids");
            StoneSerializers.list(StoneSerializers.string()).serialize(value.accountIds, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GetAccountBatchArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<String> f_accountIds = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("account_ids".equals(field)) {
                        f_accountIds = (List) StoneSerializers.list(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_accountIds == null) {
                    throw new JsonParseException(p, "Required field \"account_ids\" missing.");
                }
                GetAccountBatchArg value = new GetAccountBatchArg(f_accountIds);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GetAccountBatchArg(List<String> accountIds) {
        if (accountIds == null) {
            throw new IllegalArgumentException("Required value for 'accountIds' is null");
        } else if (accountIds.size() < 1) {
            throw new IllegalArgumentException("List 'accountIds' has fewer than 1 items");
        } else {
            for (String x : accountIds) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'accountIds' is null");
                } else if (x.length() < 40) {
                    throw new IllegalArgumentException("Stringan item in list 'accountIds' is shorter than 40");
                } else if (x.length() > 40) {
                    throw new IllegalArgumentException("Stringan item in list 'accountIds' is longer than 40");
                }
            }
            this.accountIds = accountIds;
        }
    }

    public List<String> getAccountIds() {
        return this.accountIds;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.accountIds});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GetAccountBatchArg other = (GetAccountBatchArg) obj;
        if (this.accountIds == other.accountIds || this.accountIds.equals(other.accountIds)) {
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
