package com.dropbox.core.v2.paper;

import com.box.androidsdk.content.models.BoxUser;
import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.v2.sharing.UserInfo;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;

public class UserInfoWithPermissionLevel {
    protected final PaperDocPermissionLevel permissionLevel;
    protected final UserInfo user;

    static class Serializer extends StructSerializer<UserInfoWithPermissionLevel> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UserInfoWithPermissionLevel value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxUser.TYPE);
            com.dropbox.core.v2.sharing.UserInfo.Serializer.INSTANCE.serialize(value.user, g);
            g.writeFieldName("permission_level");
            Serializer.INSTANCE.serialize(value.permissionLevel, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public UserInfoWithPermissionLevel deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                UserInfo f_user = null;
                PaperDocPermissionLevel f_permissionLevel = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxUser.TYPE.equals(field)) {
                        f_user = (UserInfo) com.dropbox.core.v2.sharing.UserInfo.Serializer.INSTANCE.deserialize(p);
                    } else if ("permission_level".equals(field)) {
                        f_permissionLevel = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_user == null) {
                    throw new JsonParseException(p, "Required field \"user\" missing.");
                } else if (f_permissionLevel == null) {
                    throw new JsonParseException(p, "Required field \"permission_level\" missing.");
                } else {
                    UserInfoWithPermissionLevel value = new UserInfoWithPermissionLevel(f_user, f_permissionLevel);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public UserInfoWithPermissionLevel(UserInfo user, PaperDocPermissionLevel permissionLevel) {
        if (user == null) {
            throw new IllegalArgumentException("Required value for 'user' is null");
        }
        this.user = user;
        if (permissionLevel == null) {
            throw new IllegalArgumentException("Required value for 'permissionLevel' is null");
        }
        this.permissionLevel = permissionLevel;
    }

    public UserInfo getUser() {
        return this.user;
    }

    public PaperDocPermissionLevel getPermissionLevel() {
        return this.permissionLevel;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.user, this.permissionLevel});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        UserInfoWithPermissionLevel other = (UserInfoWithPermissionLevel) obj;
        if ((this.user == other.user || this.user.equals(other.user)) && (this.permissionLevel == other.permissionLevel || this.permissionLevel.equals(other.permissionLevel))) {
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
