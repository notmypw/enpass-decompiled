package com.dropbox.core.v2.properties;

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

public class ListPropertyTemplateIds {
    protected final List<String> templateIds;

    public static class Serializer extends StructSerializer<ListPropertyTemplateIds> {
        public static final Serializer INSTANCE = new Serializer();

        public void serialize(ListPropertyTemplateIds value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("template_ids");
            StoneSerializers.list(StoneSerializers.string()).serialize(value.templateIds, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListPropertyTemplateIds deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<String> f_templateIds = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("template_ids".equals(field)) {
                        f_templateIds = (List) StoneSerializers.list(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_templateIds == null) {
                    throw new JsonParseException(p, "Required field \"template_ids\" missing.");
                }
                ListPropertyTemplateIds value = new ListPropertyTemplateIds(f_templateIds);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListPropertyTemplateIds(List<String> templateIds) {
        if (templateIds == null) {
            throw new IllegalArgumentException("Required value for 'templateIds' is null");
        }
        for (String x : templateIds) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'templateIds' is null");
            } else if (x.length() < 1) {
                throw new IllegalArgumentException("Stringan item in list 'templateIds' is shorter than 1");
            } else if (!Pattern.matches("(/|ptid:).*", x)) {
                throw new IllegalArgumentException("Stringan item in list 'templateIds' does not match pattern");
            }
        }
        this.templateIds = templateIds;
    }

    public List<String> getTemplateIds() {
        return this.templateIds;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.templateIds});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListPropertyTemplateIds other = (ListPropertyTemplateIds) obj;
        if (this.templateIds == other.templateIds || this.templateIds.equals(other.templateIds)) {
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
