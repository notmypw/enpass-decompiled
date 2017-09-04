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

class UnshareFolderArg {
    protected final boolean leaveACopy;
    protected final String sharedFolderId;

    static class Serializer extends StructSerializer<UnshareFolderArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UnshareFolderArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("shared_folder_id");
            StoneSerializers.string().serialize(value.sharedFolderId, g);
            g.writeFieldName("leave_a_copy");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.leaveACopy), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public UnshareFolderArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_sharedFolderId = null;
                Boolean f_leaveACopy = Boolean.valueOf(false);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("shared_folder_id".equals(field)) {
                        f_sharedFolderId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("leave_a_copy".equals(field)) {
                        f_leaveACopy = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_sharedFolderId == null) {
                    throw new JsonParseException(p, "Required field \"shared_folder_id\" missing.");
                }
                UnshareFolderArg value = new UnshareFolderArg(f_sharedFolderId, f_leaveACopy.booleanValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public UnshareFolderArg(String sharedFolderId, boolean leaveACopy) {
        if (sharedFolderId == null) {
            throw new IllegalArgumentException("Required value for 'sharedFolderId' is null");
        } else if (Pattern.matches("[-_0-9a-zA-Z:]+", sharedFolderId)) {
            this.sharedFolderId = sharedFolderId;
            this.leaveACopy = leaveACopy;
        } else {
            throw new IllegalArgumentException("String 'sharedFolderId' does not match pattern");
        }
    }

    public UnshareFolderArg(String sharedFolderId) {
        this(sharedFolderId, false);
    }

    public String getSharedFolderId() {
        return this.sharedFolderId;
    }

    public boolean getLeaveACopy() {
        return this.leaveACopy;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.sharedFolderId, Boolean.valueOf(this.leaveACopy)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        UnshareFolderArg other = (UnshareFolderArg) obj;
        if ((this.sharedFolderId == other.sharedFolderId || this.sharedFolderId.equals(other.sharedFolderId)) && this.leaveACopy == other.leaveACopy) {
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
