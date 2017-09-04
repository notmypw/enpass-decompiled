package com.dropbox.core.v2.team;

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

class MembersAddArg {
    protected final boolean forceAsync;
    protected final List<MemberAddArg> newMembers;

    static class Serializer extends StructSerializer<MembersAddArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(MembersAddArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("new_members");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.newMembers, g);
            g.writeFieldName("force_async");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.forceAsync), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public MembersAddArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<MemberAddArg> f_newMembers = null;
                Boolean f_forceAsync = Boolean.valueOf(false);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("new_members".equals(field)) {
                        f_newMembers = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else if ("force_async".equals(field)) {
                        f_forceAsync = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_newMembers == null) {
                    throw new JsonParseException(p, "Required field \"new_members\" missing.");
                }
                MembersAddArg value = new MembersAddArg(f_newMembers, f_forceAsync.booleanValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public MembersAddArg(List<MemberAddArg> newMembers, boolean forceAsync) {
        if (newMembers == null) {
            throw new IllegalArgumentException("Required value for 'newMembers' is null");
        }
        for (MemberAddArg x : newMembers) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'newMembers' is null");
            }
        }
        this.newMembers = newMembers;
        this.forceAsync = forceAsync;
    }

    public MembersAddArg(List<MemberAddArg> newMembers) {
        this(newMembers, false);
    }

    public List<MemberAddArg> getNewMembers() {
        return this.newMembers;
    }

    public boolean getForceAsync() {
        return this.forceAsync;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.newMembers, Boolean.valueOf(this.forceAsync)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        MembersAddArg other = (MembersAddArg) obj;
        if ((this.newMembers == other.newMembers || this.newMembers.equals(other.newMembers)) && this.forceAsync == other.forceAsync) {
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
