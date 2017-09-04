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

class ListFoldersArgs {
    protected final List<FolderAction> actions;
    protected final long limit;

    public static class Builder {
        protected List<FolderAction> actions = null;
        protected long limit = 1000;

        protected Builder() {
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

        public Builder withActions(List<FolderAction> actions) {
            if (actions != null) {
                for (FolderAction x : actions) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'actions' is null");
                    }
                }
            }
            this.actions = actions;
            return this;
        }

        public ListFoldersArgs build() {
            return new ListFoldersArgs(this.limit, this.actions);
        }
    }

    static class Serializer extends StructSerializer<ListFoldersArgs> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListFoldersArgs value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
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

        public ListFoldersArgs deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Long f_limit = Long.valueOf(1000);
                List<FolderAction> f_actions = null;
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
                ListFoldersArgs value = new ListFoldersArgs(f_limit.longValue(), f_actions);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListFoldersArgs(long limit, List<FolderAction> actions) {
        if (limit < 1) {
            throw new IllegalArgumentException("Number 'limit' is smaller than 1L");
        } else if (limit > 1000) {
            throw new IllegalArgumentException("Number 'limit' is larger than 1000L");
        } else {
            this.limit = limit;
            if (actions != null) {
                for (FolderAction x : actions) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'actions' is null");
                    }
                }
            }
            this.actions = actions;
        }
    }

    public ListFoldersArgs() {
        this(1000, null);
    }

    public long getLimit() {
        return this.limit;
    }

    public List<FolderAction> getActions() {
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
        ListFoldersArgs other = (ListFoldersArgs) obj;
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
