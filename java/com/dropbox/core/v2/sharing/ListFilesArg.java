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

class ListFilesArg {
    protected final List<FileAction> actions;
    protected final long limit;

    public static class Builder {
        protected List<FileAction> actions = null;
        protected long limit = 100;

        protected Builder() {
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

        public Builder withActions(List<FileAction> actions) {
            if (actions != null) {
                for (FileAction x : actions) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'actions' is null");
                    }
                }
            }
            this.actions = actions;
            return this;
        }

        public ListFilesArg build() {
            return new ListFilesArg(this.limit, this.actions);
        }
    }

    static class Serializer extends StructSerializer<ListFilesArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListFilesArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxList.FIELD_LIMIT);
            StoneSerializers.uInt32().serialize(Long.valueOf(value.limit), g);
            if (value.actions != null) {
                g.writeFieldName("actions");
                StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).serialize(value.actions, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListFilesArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Long f_limit = Long.valueOf(100);
                List<FileAction> f_actions = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxList.FIELD_LIMIT.equals(field)) {
                        f_limit = (Long) StoneSerializers.uInt32().deserialize(p);
                    } else if ("actions".equals(field)) {
                        f_actions = (List) StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                ListFilesArg value = new ListFilesArg(f_limit.longValue(), f_actions);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListFilesArg(long limit, List<FileAction> actions) {
        if (limit < 1) {
            throw new IllegalArgumentException("Number 'limit' is smaller than 1L");
        } else if (limit > 300) {
            throw new IllegalArgumentException("Number 'limit' is larger than 300L");
        } else {
            this.limit = limit;
            if (actions != null) {
                for (FileAction x : actions) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'actions' is null");
                    }
                }
            }
            this.actions = actions;
        }
    }

    public ListFilesArg() {
        this(100, null);
    }

    public long getLimit() {
        return this.limit;
    }

    public List<FileAction> getActions() {
        return this.actions;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.limit), this.actions});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListFilesArg other = (ListFilesArg) obj;
        if (this.limit == other.limit) {
            if (this.actions == other.actions) {
                return true;
            }
            if (this.actions != null && this.actions.equals(other.actions)) {
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
