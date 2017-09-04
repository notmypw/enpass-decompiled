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

class GroupsListArg {
    protected final long limit;

    static class Serializer extends StructSerializer<GroupsListArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GroupsListArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxList.FIELD_LIMIT);
            StoneSerializers.uInt32().serialize(Long.valueOf(value.limit), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GroupsListArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Long f_limit = Long.valueOf(1000);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxList.FIELD_LIMIT.equals(field)) {
                        f_limit = (Long) StoneSerializers.uInt32().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                GroupsListArg value = new GroupsListArg(f_limit.longValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GroupsListArg(long limit) {
        if (limit < 1) {
            throw new IllegalArgumentException("Number 'limit' is smaller than 1L");
        } else if (limit > 1000) {
            throw new IllegalArgumentException("Number 'limit' is larger than 1000L");
        } else {
            this.limit = limit;
        }
    }

    public GroupsListArg() {
        this(1000);
    }

    public long getLimit() {
        return this.limit;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.limit)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        if (this.limit != ((GroupsListArg) obj).limit) {
            return false;
        }
        return true;
    }

    public String toString() {
        return Serializer.INSTANCE.serialize((Object) this, false);
    }

    public String toStringMultiline() {
        return Serializer.INSTANCE.serialize((Object) this, true);
    }
}
