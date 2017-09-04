package com.dropbox.core.v2.sharing;

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

class UpdateFolderMemberArg {
    protected final AccessLevel accessLevel;
    protected final MemberSelector member;
    protected final String sharedFolderId;

    static class Serializer extends StructSerializer<UpdateFolderMemberArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UpdateFolderMemberArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("shared_folder_id");
            StoneSerializers.string().serialize(value.sharedFolderId, g);
            g.writeFieldName("member");
            com.dropbox.core.v2.sharing.MemberSelector.Serializer.INSTANCE.serialize(value.member, g);
            g.writeFieldName("access_level");
            Serializer.INSTANCE.serialize(value.accessLevel, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public UpdateFolderMemberArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_sharedFolderId = null;
                MemberSelector f_member = null;
                AccessLevel f_accessLevel = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("shared_folder_id".equals(field)) {
                        f_sharedFolderId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("member".equals(field)) {
                        f_member = com.dropbox.core.v2.sharing.MemberSelector.Serializer.INSTANCE.deserialize(p);
                    } else if ("access_level".equals(field)) {
                        f_accessLevel = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_sharedFolderId == null) {
                    throw new JsonParseException(p, "Required field \"shared_folder_id\" missing.");
                } else if (f_member == null) {
                    throw new JsonParseException(p, "Required field \"member\" missing.");
                } else if (f_accessLevel == null) {
                    throw new JsonParseException(p, "Required field \"access_level\" missing.");
                } else {
                    UpdateFolderMemberArg value = new UpdateFolderMemberArg(f_sharedFolderId, f_member, f_accessLevel);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public UpdateFolderMemberArg(String sharedFolderId, MemberSelector member, AccessLevel accessLevel) {
        if (sharedFolderId == null) {
            throw new IllegalArgumentException("Required value for 'sharedFolderId' is null");
        } else if (Pattern.matches("[-_0-9a-zA-Z:]+", sharedFolderId)) {
            this.sharedFolderId = sharedFolderId;
            if (member == null) {
                throw new IllegalArgumentException("Required value for 'member' is null");
            }
            this.member = member;
            if (accessLevel == null) {
                throw new IllegalArgumentException("Required value for 'accessLevel' is null");
            }
            this.accessLevel = accessLevel;
        } else {
            throw new IllegalArgumentException("String 'sharedFolderId' does not match pattern");
        }
    }

    public String getSharedFolderId() {
        return this.sharedFolderId;
    }

    public MemberSelector getMember() {
        return this.member;
    }

    public AccessLevel getAccessLevel() {
        return this.accessLevel;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.sharedFolderId, this.member, this.accessLevel});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        UpdateFolderMemberArg other = (UpdateFolderMemberArg) obj;
        if ((this.sharedFolderId == other.sharedFolderId || this.sharedFolderId.equals(other.sharedFolderId)) && ((this.member == other.member || this.member.equals(other.member)) && (this.accessLevel == other.accessLevel || this.accessLevel.equals(other.accessLevel)))) {
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
