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

class RemoveFolderMemberArg {
    protected final boolean leaveACopy;
    protected final MemberSelector member;
    protected final String sharedFolderId;

    static class Serializer extends StructSerializer<RemoveFolderMemberArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RemoveFolderMemberArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("shared_folder_id");
            StoneSerializers.string().serialize(value.sharedFolderId, g);
            g.writeFieldName("member");
            com.dropbox.core.v2.sharing.MemberSelector.Serializer.INSTANCE.serialize(value.member, g);
            g.writeFieldName("leave_a_copy");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.leaveACopy), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public RemoveFolderMemberArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_sharedFolderId = null;
                MemberSelector f_member = null;
                Boolean f_leaveACopy = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("shared_folder_id".equals(field)) {
                        f_sharedFolderId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("member".equals(field)) {
                        f_member = com.dropbox.core.v2.sharing.MemberSelector.Serializer.INSTANCE.deserialize(p);
                    } else if ("leave_a_copy".equals(field)) {
                        f_leaveACopy = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_sharedFolderId == null) {
                    throw new JsonParseException(p, "Required field \"shared_folder_id\" missing.");
                } else if (f_member == null) {
                    throw new JsonParseException(p, "Required field \"member\" missing.");
                } else if (f_leaveACopy == null) {
                    throw new JsonParseException(p, "Required field \"leave_a_copy\" missing.");
                } else {
                    RemoveFolderMemberArg value = new RemoveFolderMemberArg(f_sharedFolderId, f_member, f_leaveACopy.booleanValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public RemoveFolderMemberArg(String sharedFolderId, MemberSelector member, boolean leaveACopy) {
        if (sharedFolderId == null) {
            throw new IllegalArgumentException("Required value for 'sharedFolderId' is null");
        } else if (Pattern.matches("[-_0-9a-zA-Z:]+", sharedFolderId)) {
            this.sharedFolderId = sharedFolderId;
            if (member == null) {
                throw new IllegalArgumentException("Required value for 'member' is null");
            }
            this.member = member;
            this.leaveACopy = leaveACopy;
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

    public boolean getLeaveACopy() {
        return this.leaveACopy;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.sharedFolderId, this.member, Boolean.valueOf(this.leaveACopy)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        RemoveFolderMemberArg other = (RemoveFolderMemberArg) obj;
        if ((this.sharedFolderId == other.sharedFolderId || this.sharedFolderId.equals(other.sharedFolderId)) && ((this.member == other.member || this.member.equals(other.member)) && this.leaveACopy == other.leaveACopy)) {
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
