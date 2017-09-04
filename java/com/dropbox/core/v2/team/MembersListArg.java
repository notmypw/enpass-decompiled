package com.dropbox.core.v2.team;

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

class MembersListArg {
    protected final boolean includeRemoved;
    protected final long limit;

    public static class Builder {
        protected boolean includeRemoved = false;
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

        public Builder withIncludeRemoved(Boolean includeRemoved) {
            if (includeRemoved != null) {
                this.includeRemoved = includeRemoved.booleanValue();
            } else {
                this.includeRemoved = false;
            }
            return this;
        }

        public MembersListArg build() {
            return new MembersListArg(this.limit, this.includeRemoved);
        }
    }

    static class Serializer extends StructSerializer<MembersListArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(MembersListArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxList.FIELD_LIMIT);
            StoneSerializers.uInt32().serialize(Long.valueOf(value.limit), g);
            g.writeFieldName("include_removed");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.includeRemoved), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public MembersListArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Long f_limit = Long.valueOf(1000);
                Boolean f_includeRemoved = Boolean.valueOf(false);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxList.FIELD_LIMIT.equals(field)) {
                        f_limit = (Long) StoneSerializers.uInt32().deserialize(p);
                    } else if ("include_removed".equals(field)) {
                        f_includeRemoved = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                MembersListArg value = new MembersListArg(f_limit.longValue(), f_includeRemoved.booleanValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public MembersListArg(long limit, boolean includeRemoved) {
        if (limit < 1) {
            throw new IllegalArgumentException("Number 'limit' is smaller than 1L");
        } else if (limit > 1000) {
            throw new IllegalArgumentException("Number 'limit' is larger than 1000L");
        } else {
            this.limit = limit;
            this.includeRemoved = includeRemoved;
        }
    }

    public MembersListArg() {
        this(1000, false);
    }

    public long getLimit() {
        return this.limit;
    }

    public boolean getIncludeRemoved() {
        return this.includeRemoved;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.limit), Boolean.valueOf(this.includeRemoved)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        MembersListArg other = (MembersListArg) obj;
        if (this.limit == other.limit && this.includeRemoved == other.includeRemoved) {
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
