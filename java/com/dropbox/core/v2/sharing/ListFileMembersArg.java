package com.dropbox.core.v2.sharing;

import com.box.androidsdk.content.models.BoxFile;
import com.box.androidsdk.content.models.BoxList;
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

class ListFileMembersArg {
    protected final List<MemberAction> actions;
    protected final String file;
    protected final boolean includeInherited;
    protected final long limit;

    public static class Builder {
        protected List<MemberAction> actions;
        protected final String file;
        protected boolean includeInherited;
        protected long limit;

        protected Builder(String file) {
            if (file == null) {
                throw new IllegalArgumentException("Required value for 'file' is null");
            } else if (file.length() < 1) {
                throw new IllegalArgumentException("String 'file' is shorter than 1");
            } else if (Pattern.matches("((/|id:).*|nspath:[0-9]+:.*)|ns:[0-9]+(/.*)?", file)) {
                this.file = file;
                this.actions = null;
                this.includeInherited = true;
                this.limit = 100;
            } else {
                throw new IllegalArgumentException("String 'file' does not match pattern");
            }
        }

        public Builder withActions(List<MemberAction> actions) {
            if (actions != null) {
                for (MemberAction x : actions) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'actions' is null");
                    }
                }
            }
            this.actions = actions;
            return this;
        }

        public Builder withIncludeInherited(Boolean includeInherited) {
            if (includeInherited != null) {
                this.includeInherited = includeInherited.booleanValue();
            } else {
                this.includeInherited = true;
            }
            return this;
        }

        public Builder withLimit(Long limit) {
            if (limit.longValue() < 1) {
                throw new IllegalArgumentException("Number 'limit' is smaller than 1L");
            } else if (limit.longValue() > 300) {
                throw new IllegalArgumentException("Number 'limit' is larger than 300L");
            } else {
                if (limit != null) {
                    this.limit = limit.longValue();
                } else {
                    this.limit = 100;
                }
                return this;
            }
        }

        public ListFileMembersArg build() {
            return new ListFileMembersArg(this.file, this.actions, this.includeInherited, this.limit);
        }
    }

    static class Serializer extends StructSerializer<ListFileMembersArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListFileMembersArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxFile.TYPE);
            StoneSerializers.string().serialize(value.file, g);
            if (value.actions != null) {
                g.writeFieldName("actions");
                StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).serialize(value.actions, g);
            }
            g.writeFieldName("include_inherited");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.includeInherited), g);
            g.writeFieldName(BoxList.FIELD_LIMIT);
            StoneSerializers.uInt32().serialize(Long.valueOf(value.limit), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListFileMembersArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_file = null;
                List<MemberAction> f_actions = null;
                Boolean f_includeInherited = Boolean.valueOf(true);
                Long f_limit = Long.valueOf(100);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxFile.TYPE.equals(field)) {
                        f_file = (String) StoneSerializers.string().deserialize(p);
                    } else if ("actions".equals(field)) {
                        f_actions = (List) StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).deserialize(p);
                    } else if ("include_inherited".equals(field)) {
                        f_includeInherited = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if (BoxList.FIELD_LIMIT.equals(field)) {
                        f_limit = (Long) StoneSerializers.uInt32().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_file == null) {
                    throw new JsonParseException(p, "Required field \"file\" missing.");
                }
                ListFileMembersArg value = new ListFileMembersArg(f_file, f_actions, f_includeInherited.booleanValue(), f_limit.longValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListFileMembersArg(String file, List<MemberAction> actions, boolean includeInherited, long limit) {
        if (file == null) {
            throw new IllegalArgumentException("Required value for 'file' is null");
        } else if (file.length() < 1) {
            throw new IllegalArgumentException("String 'file' is shorter than 1");
        } else if (Pattern.matches("((/|id:).*|nspath:[0-9]+:.*)|ns:[0-9]+(/.*)?", file)) {
            this.file = file;
            if (actions != null) {
                for (MemberAction x : actions) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'actions' is null");
                    }
                }
            }
            this.actions = actions;
            this.includeInherited = includeInherited;
            if (limit < 1) {
                throw new IllegalArgumentException("Number 'limit' is smaller than 1L");
            } else if (limit > 300) {
                throw new IllegalArgumentException("Number 'limit' is larger than 300L");
            } else {
                this.limit = limit;
            }
        } else {
            throw new IllegalArgumentException("String 'file' does not match pattern");
        }
    }

    public ListFileMembersArg(String file) {
        this(file, null, true, 100);
    }

    public String getFile() {
        return this.file;
    }

    public List<MemberAction> getActions() {
        return this.actions;
    }

    public boolean getIncludeInherited() {
        return this.includeInherited;
    }

    public long getLimit() {
        return this.limit;
    }

    public static Builder newBuilder(String file) {
        return new Builder(file);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.file, this.actions, Boolean.valueOf(this.includeInherited), Long.valueOf(this.limit)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListFileMembersArg other = (ListFileMembersArg) obj;
        if ((this.file == other.file || this.file.equals(other.file)) && ((this.actions == other.actions || (this.actions != null && this.actions.equals(other.actions))) && this.includeInherited == other.includeInherited && this.limit == other.limit)) {
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
