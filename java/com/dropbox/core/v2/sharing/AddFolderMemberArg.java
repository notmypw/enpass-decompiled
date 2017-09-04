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
import java.util.List;
import java.util.regex.Pattern;

class AddFolderMemberArg {
    protected final String customMessage;
    protected final List<AddMember> members;
    protected final boolean quiet;
    protected final String sharedFolderId;

    public static class Builder {
        protected String customMessage;
        protected final List<AddMember> members;
        protected boolean quiet;
        protected final String sharedFolderId;

        protected Builder(String sharedFolderId, List<AddMember> members) {
            if (sharedFolderId == null) {
                throw new IllegalArgumentException("Required value for 'sharedFolderId' is null");
            } else if (Pattern.matches("[-_0-9a-zA-Z:]+", sharedFolderId)) {
                this.sharedFolderId = sharedFolderId;
                if (members == null) {
                    throw new IllegalArgumentException("Required value for 'members' is null");
                }
                for (AddMember x : members) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'members' is null");
                    }
                }
                this.members = members;
                this.quiet = false;
                this.customMessage = null;
            } else {
                throw new IllegalArgumentException("String 'sharedFolderId' does not match pattern");
            }
        }

        public Builder withQuiet(Boolean quiet) {
            if (quiet != null) {
                this.quiet = quiet.booleanValue();
            } else {
                this.quiet = false;
            }
            return this;
        }

        public Builder withCustomMessage(String customMessage) {
            if (customMessage == null || customMessage.length() >= 1) {
                this.customMessage = customMessage;
                return this;
            }
            throw new IllegalArgumentException("String 'customMessage' is shorter than 1");
        }

        public AddFolderMemberArg build() {
            return new AddFolderMemberArg(this.sharedFolderId, this.members, this.quiet, this.customMessage);
        }
    }

    static class Serializer extends StructSerializer<AddFolderMemberArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(AddFolderMemberArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("shared_folder_id");
            StoneSerializers.string().serialize(value.sharedFolderId, g);
            g.writeFieldName("members");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.members, g);
            g.writeFieldName("quiet");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.quiet), g);
            if (value.customMessage != null) {
                g.writeFieldName("custom_message");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.customMessage, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public AddFolderMemberArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_sharedFolderId = null;
                List<AddMember> f_members = null;
                Boolean f_quiet = Boolean.valueOf(false);
                String f_customMessage = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("shared_folder_id".equals(field)) {
                        f_sharedFolderId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("members".equals(field)) {
                        f_members = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else if ("quiet".equals(field)) {
                        f_quiet = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("custom_message".equals(field)) {
                        f_customMessage = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_sharedFolderId == null) {
                    throw new JsonParseException(p, "Required field \"shared_folder_id\" missing.");
                } else if (f_members == null) {
                    throw new JsonParseException(p, "Required field \"members\" missing.");
                } else {
                    AddFolderMemberArg value = new AddFolderMemberArg(f_sharedFolderId, f_members, f_quiet.booleanValue(), f_customMessage);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public AddFolderMemberArg(String sharedFolderId, List<AddMember> members, boolean quiet, String customMessage) {
        if (sharedFolderId == null) {
            throw new IllegalArgumentException("Required value for 'sharedFolderId' is null");
        } else if (Pattern.matches("[-_0-9a-zA-Z:]+", sharedFolderId)) {
            this.sharedFolderId = sharedFolderId;
            if (members == null) {
                throw new IllegalArgumentException("Required value for 'members' is null");
            }
            for (AddMember x : members) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'members' is null");
                }
            }
            this.members = members;
            this.quiet = quiet;
            if (customMessage == null || customMessage.length() >= 1) {
                this.customMessage = customMessage;
                return;
            }
            throw new IllegalArgumentException("String 'customMessage' is shorter than 1");
        } else {
            throw new IllegalArgumentException("String 'sharedFolderId' does not match pattern");
        }
    }

    public AddFolderMemberArg(String sharedFolderId, List<AddMember> members) {
        this(sharedFolderId, members, false, null);
    }

    public String getSharedFolderId() {
        return this.sharedFolderId;
    }

    public List<AddMember> getMembers() {
        return this.members;
    }

    public boolean getQuiet() {
        return this.quiet;
    }

    public String getCustomMessage() {
        return this.customMessage;
    }

    public static Builder newBuilder(String sharedFolderId, List<AddMember> members) {
        return new Builder(sharedFolderId, members);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.sharedFolderId, this.members, Boolean.valueOf(this.quiet), this.customMessage});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        AddFolderMemberArg other = (AddFolderMemberArg) obj;
        if ((this.sharedFolderId == other.sharedFolderId || this.sharedFolderId.equals(other.sharedFolderId)) && ((this.members == other.members || this.members.equals(other.members)) && this.quiet == other.quiet)) {
            if (this.customMessage == other.customMessage) {
                return true;
            }
            if (this.customMessage != null && this.customMessage.equals(other.customMessage)) {
                return true;
            }
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
