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

class MembersDeactivateArg {
    protected final UserSelectorArg user;
    protected final boolean wipeData;

    static class Serializer extends StructSerializer<MembersDeactivateArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(MembersDeactivateArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxUser.TYPE);
            Serializer.INSTANCE.serialize(value.user, g);
            g.writeFieldName("wipe_data");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.wipeData), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public MembersDeactivateArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                UserSelectorArg f_user = null;
                Boolean f_wipeData = Boolean.valueOf(true);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxUser.TYPE.equals(field)) {
                        f_user = Serializer.INSTANCE.deserialize(p);
                    } else if ("wipe_data".equals(field)) {
                        f_wipeData = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_user == null) {
                    throw new JsonParseException(p, "Required field \"user\" missing.");
                }
                MembersDeactivateArg value = new MembersDeactivateArg(f_user, f_wipeData.booleanValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public MembersDeactivateArg(UserSelectorArg user, boolean wipeData) {
        if (user == null) {
            throw new IllegalArgumentException("Required value for 'user' is null");
        }
        this.user = user;
        this.wipeData = wipeData;
    }

    public MembersDeactivateArg(UserSelectorArg user) {
        this(user, true);
    }

    public UserSelectorArg getUser() {
        return this.user;
    }

    public boolean getWipeData() {
        return this.wipeData;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.user, Boolean.valueOf(this.wipeData)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        MembersDeactivateArg other = (MembersDeactivateArg) obj;
        if ((this.user == other.user || this.user.equals(other.user)) && this.wipeData == other.wipeData) {
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
