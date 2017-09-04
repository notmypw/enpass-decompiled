package com.dropbox.core.v2.sharing;

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

class ListFolderMembersCursorArg {
    protected final List<MemberAction> actions;
    protected final long limit;

    public static class Builder {
        protected List<MemberAction> actions = null;
        protected long limit = 1000;

        protected Builder() {
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

        public Builder withLimit(Long limit) {
            if (limit.longValue() < 1) {
                throw new IllegalArgumentException("Number 'limit' is smaller than 1L");
            } else if (limit.longValue() > 1000) {
                throw new IllegalArgumentException("Number 'limit' is larger than 1000L");
            } else {
                if (limit != null) {
                    this.limit = limit.longValue();
                } else {
                    this.limit = 1000;
                }
                return this;
            }
        }

        public ListFolderMembersCursorArg build() {
            return new ListFolderMembersCursorArg(this.actions, this.limit);
        }
    }

    private static class Serializer extends StructSerializer<ListFolderMembersCursorArg> {
        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {
        }

        public void serialize(ListFolderMembersCursorArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            if (value.actions != null) {
                g.writeFieldName("actions");
                StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).serialize(value.actions, g);
            }
            g.writeFieldName(BoxList.FIELD_LIMIT);
            StoneSerializers.uInt32().serialize(Long.valueOf(value.limit), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListFolderMembersCursorArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<MemberAction> f_actions = null;
                Long f_limit = Long.valueOf(1000);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("actions".equals(field)) {
                        f_actions = (List) StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).deserialize(p);
                    } else if (BoxList.FIELD_LIMIT.equals(field)) {
                        f_limit = (Long) StoneSerializers.uInt32().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                ListFolderMembersCursorArg value = new ListFolderMembersCursorArg(f_actions, f_limit.longValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListFolderMembersCursorArg(List<MemberAction> actions, long limit) {
        if (actions != null) {
            for (MemberAction x : actions) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'actions' is null");
                }
            }
        }
        this.actions = actions;
        if (limit < 1) {
            throw new IllegalArgumentException("Number 'limit' is smaller than 1L");
        } else if (limit > 1000) {
            throw new IllegalArgumentException("Number 'limit' is larger than 1000L");
        } else {
            this.limit = limit;
        }
    }

    public ListFolderMembersCursorArg() {
        this(null, 1000);
    }

    public List<MemberAction> getActions() {
        return this.actions;
    }

    public long getLimit() {
        return this.limit;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.actions, Long.valueOf(this.limit)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListFolderMembersCursorArg other = (ListFolderMembersCursorArg) obj;
        if ((this.actions == other.actions || (this.actions != null && this.actions.equals(other.actions))) && this.limit == other.limit) {
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
