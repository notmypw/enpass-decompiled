package com.dropbox.core.v2.team;

import com.box.androidsdk.content.models.BoxUser;
import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StructSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;

class MembersUnsuspendArg {
    protected final UserSelectorArg user;

    static class Serializer extends StructSerializer<MembersUnsuspendArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(MembersUnsuspendArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxUser.TYPE);
            Serializer.INSTANCE.serialize(value.user, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public MembersUnsuspendArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                UserSelectorArg f_user = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxUser.TYPE.equals(field)) {
                        f_user = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_user == null) {
                    throw new JsonParseException(p, "Required field \"user\" missing.");
                }
                MembersUnsuspendArg value = new MembersUnsuspendArg(f_user);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public MembersUnsuspendArg(UserSelectorArg user) {
        if (user == null) {
            throw new IllegalArgumentException("Required value for 'user' is null");
        }
        this.user = user;
    }

    public UserSelectorArg getUser() {
        return this.user;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.user});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        MembersUnsuspendArg other = (MembersUnsuspendArg) obj;
        if (this.user == other.user || this.user.equals(other.user)) {
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
