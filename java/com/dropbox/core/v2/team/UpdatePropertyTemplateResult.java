package com.dropbox.core.v2.team;

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
import java.util.regex.Pattern;

public class UpdatePropertyTemplateResult {
    protected final String templateId;

    static class Serializer extends StructSerializer<UpdatePropertyTemplateResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UpdatePropertyTemplateResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("template_id");
            StoneSerializers.string().serialize(value.templateId, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public UpdatePropertyTemplateResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_templateId = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("template_id".equals(field)) {
                        f_templateId = (String) StoneSerializers.string().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_templateId == null) {
                    throw new JsonParseException(p, "Required field \"template_id\" missing.");
                }
                UpdatePropertyTemplateResult value = new UpdatePropertyTemplateResult(f_templateId);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public UpdatePropertyTemplateResult(String templateId) {
        if (templateId == null) {
            throw new IllegalArgumentException("Required value for 'templateId' is null");
        } else if (templateId.length() < 1) {
            throw new IllegalArgumentException("String 'templateId' is shorter than 1");
        } else if (Pattern.matches("(/|ptid:).*", templateId)) {
            this.templateId = templateId;
        } else {
            throw new IllegalArgumentException("String 'templateId' does not match pattern");
        }
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.templateId});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        UpdatePropertyTemplateResult other = (UpdatePropertyTemplateResult) obj;
        if (this.templateId == other.templateId || this.templateId.equals(other.templateId)) {
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
