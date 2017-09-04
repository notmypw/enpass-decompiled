package com.dropbox.core.v2.sharing;

import com.box.androidsdk.content.models.BoxFile;
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

class AddFileMemberArgs {
    protected final AccessLevel accessLevel;
    protected final boolean addMessageAsComment;
    protected final String customMessage;
    protected final String file;
    protected final List<MemberSelector> members;
    protected final boolean quiet;

    public static class Builder {
        protected AccessLevel accessLevel;
        protected boolean addMessageAsComment;
        protected String customMessage;
        protected final String file;
        protected final List<MemberSelector> members;
        protected boolean quiet;

        protected Builder(String file, List<MemberSelector> members) {
            if (file == null) {
                throw new IllegalArgumentException("Required value for 'file' is null");
            } else if (file.length() < 1) {
                throw new IllegalArgumentException("String 'file' is shorter than 1");
            } else if (Pattern.matches("((/|id:).*|nspath:[0-9]+:.*)|ns:[0-9]+(/.*)?", file)) {
                this.file = file;
                if (members == null) {
                    throw new IllegalArgumentException("Required value for 'members' is null");
                }
                for (MemberSelector x : members) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'members' is null");
                    }
                }
                this.members = members;
                this.customMessage = null;
                this.quiet = false;
                this.accessLevel = AccessLevel.VIEWER;
                this.addMessageAsComment = false;
            } else {
                throw new IllegalArgumentException("String 'file' does not match pattern");
            }
        }

        public Builder withCustomMessage(String customMessage) {
            this.customMessage = customMessage;
            return this;
        }

        public Builder withQuiet(Boolean quiet) {
            if (quiet != null) {
                this.quiet = quiet.booleanValue();
            } else {
                this.quiet = false;
            }
            return this;
        }

        public Builder withAccessLevel(AccessLevel accessLevel) {
            if (accessLevel != null) {
                this.accessLevel = accessLevel;
            } else {
                this.accessLevel = AccessLevel.VIEWER;
            }
            return this;
        }

        public Builder withAddMessageAsComment(Boolean addMessageAsComment) {
            if (addMessageAsComment != null) {
                this.addMessageAsComment = addMessageAsComment.booleanValue();
            } else {
                this.addMessageAsComment = false;
            }
            return this;
        }

        public AddFileMemberArgs build() {
            return new AddFileMemberArgs(this.file, this.members, this.customMessage, this.quiet, this.accessLevel, this.addMessageAsComment);
        }
    }

    static class Serializer extends StructSerializer<AddFileMemberArgs> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(AddFileMemberArgs value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxFile.TYPE);
            StoneSerializers.string().serialize(value.file, g);
            g.writeFieldName("members");
            StoneSerializers.list(com.dropbox.core.v2.sharing.MemberSelector.Serializer.INSTANCE).serialize(value.members, g);
            if (value.customMessage != null) {
                g.writeFieldName("custom_message");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.customMessage, g);
            }
            g.writeFieldName("quiet");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.quiet), g);
            g.writeFieldName("access_level");
            Serializer.INSTANCE.serialize(value.accessLevel, g);
            g.writeFieldName("add_message_as_comment");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.addMessageAsComment), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public AddFileMemberArgs deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_file = null;
                List<MemberSelector> f_members = null;
                String f_customMessage = null;
                Boolean f_quiet = Boolean.valueOf(false);
                AccessLevel f_accessLevel = AccessLevel.VIEWER;
                Boolean f_addMessageAsComment = Boolean.valueOf(false);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxFile.TYPE.equals(field)) {
                        f_file = (String) StoneSerializers.string().deserialize(p);
                    } else if ("members".equals(field)) {
                        f_members = (List) StoneSerializers.list(com.dropbox.core.v2.sharing.MemberSelector.Serializer.INSTANCE).deserialize(p);
                    } else if ("custom_message".equals(field)) {
                        f_customMessage = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("quiet".equals(field)) {
                        f_quiet = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("access_level".equals(field)) {
                        f_accessLevel = Serializer.INSTANCE.deserialize(p);
                    } else if ("add_message_as_comment".equals(field)) {
                        f_addMessageAsComment = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_file == null) {
                    throw new JsonParseException(p, "Required field \"file\" missing.");
                } else if (f_members == null) {
                    throw new JsonParseException(p, "Required field \"members\" missing.");
                } else {
                    AddFileMemberArgs value = new AddFileMemberArgs(f_file, f_members, f_customMessage, f_quiet.booleanValue(), f_accessLevel, f_addMessageAsComment.booleanValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public AddFileMemberArgs(String file, List<MemberSelector> members, String customMessage, boolean quiet, AccessLevel accessLevel, boolean addMessageAsComment) {
        if (file == null) {
            throw new IllegalArgumentException("Required value for 'file' is null");
        } else if (file.length() < 1) {
            throw new IllegalArgumentException("String 'file' is shorter than 1");
        } else if (Pattern.matches("((/|id:).*|nspath:[0-9]+:.*)|ns:[0-9]+(/.*)?", file)) {
            this.file = file;
            if (members == null) {
                throw new IllegalArgumentException("Required value for 'members' is null");
            }
            for (MemberSelector x : members) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'members' is null");
                }
            }
            this.members = members;
            this.customMessage = customMessage;
            this.quiet = quiet;
            if (accessLevel == null) {
                throw new IllegalArgumentException("Required value for 'accessLevel' is null");
            }
            this.accessLevel = accessLevel;
            this.addMessageAsComment = addMessageAsComment;
        } else {
            throw new IllegalArgumentException("String 'file' does not match pattern");
        }
    }

    public AddFileMemberArgs(String file, List<MemberSelector> members) {
        this(file, members, null, false, AccessLevel.VIEWER, false);
    }

    public String getFile() {
        return this.file;
    }

    public List<MemberSelector> getMembers() {
        return this.members;
    }

    public String getCustomMessage() {
        return this.customMessage;
    }

    public boolean getQuiet() {
        return this.quiet;
    }

    public AccessLevel getAccessLevel() {
        return this.accessLevel;
    }

    public boolean getAddMessageAsComment() {
        return this.addMessageAsComment;
    }

    public static Builder newBuilder(String file, List<MemberSelector> members) {
        return new Builder(file, members);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.file, this.members, this.customMessage, Boolean.valueOf(this.quiet), this.accessLevel, Boolean.valueOf(this.addMessageAsComment)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        AddFileMemberArgs other = (AddFileMemberArgs) obj;
        if ((this.file == other.file || this.file.equals(other.file)) && ((this.members == other.members || this.members.equals(other.members)) && ((this.customMessage == other.customMessage || (this.customMessage != null && this.customMessage.equals(other.customMessage))) && this.quiet == other.quiet && ((this.accessLevel == other.accessLevel || this.accessLevel.equals(other.accessLevel)) && this.addMessageAsComment == other.addMessageAsComment)))) {
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
