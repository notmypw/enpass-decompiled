package com.dropbox.core.v2.paper;

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

class AddPaperDocUser extends RefPaperDoc {
    protected final String customMessage;
    protected final List<AddMember> members;
    protected final boolean quiet;

    public static class Builder {
        protected String customMessage;
        protected final String docId;
        protected final List<AddMember> members;
        protected boolean quiet;

        protected Builder(String docId, List<AddMember> members) {
            if (docId == null) {
                throw new IllegalArgumentException("Required value for 'docId' is null");
            }
            this.docId = docId;
            if (members == null) {
                throw new IllegalArgumentException("Required value for 'members' is null");
            } else if (members.size() > 20) {
                throw new IllegalArgumentException("List 'members' has more than 20 items");
            } else {
                for (AddMember x : members) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'members' is null");
                    }
                }
                this.members = members;
                this.customMessage = null;
                this.quiet = false;
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

        public AddPaperDocUser build() {
            return new AddPaperDocUser(this.docId, this.members, this.customMessage, this.quiet);
        }
    }

    static class Serializer extends StructSerializer<AddPaperDocUser> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(AddPaperDocUser value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("doc_id");
            StoneSerializers.string().serialize(value.docId, g);
            g.writeFieldName("members");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.members, g);
            if (value.customMessage != null) {
                g.writeFieldName("custom_message");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.customMessage, g);
            }
            g.writeFieldName("quiet");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.quiet), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public AddPaperDocUser deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_docId = null;
                List<AddMember> f_members = null;
                String f_customMessage = null;
                Boolean f_quiet = Boolean.valueOf(false);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("doc_id".equals(field)) {
                        f_docId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("members".equals(field)) {
                        f_members = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else if ("custom_message".equals(field)) {
                        f_customMessage = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("quiet".equals(field)) {
                        f_quiet = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_docId == null) {
                    throw new JsonParseException(p, "Required field \"doc_id\" missing.");
                } else if (f_members == null) {
                    throw new JsonParseException(p, "Required field \"members\" missing.");
                } else {
                    AddPaperDocUser value = new AddPaperDocUser(f_docId, f_members, f_customMessage, f_quiet.booleanValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public AddPaperDocUser(String docId, List<AddMember> members, String customMessage, boolean quiet) {
        super(docId);
        if (members == null) {
            throw new IllegalArgumentException("Required value for 'members' is null");
        } else if (members.size() > 20) {
            throw new IllegalArgumentException("List 'members' has more than 20 items");
        } else {
            for (AddMember x : members) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'members' is null");
                }
            }
            this.members = members;
            this.customMessage = customMessage;
            this.quiet = quiet;
        }
    }

    public AddPaperDocUser(String docId, List<AddMember> members) {
        this(docId, members, null, false);
    }

    public String getDocId() {
        return this.docId;
    }

    public List<AddMember> getMembers() {
        return this.members;
    }

    public String getCustomMessage() {
        return this.customMessage;
    }

    public boolean getQuiet() {
        return this.quiet;
    }

    public static Builder newBuilder(String docId, List<AddMember> members) {
        return new Builder(docId, members);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.members, this.customMessage, Boolean.valueOf(this.quiet)}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        AddPaperDocUser other = (AddPaperDocUser) obj;
        if ((this.docId == other.docId || this.docId.equals(other.docId)) && ((this.members == other.members || this.members.equals(other.members)) && ((this.customMessage == other.customMessage || (this.customMessage != null && this.customMessage.equals(other.customMessage))) && this.quiet == other.quiet))) {
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
