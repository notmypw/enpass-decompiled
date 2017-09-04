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

public class BaseDfbReport {
    protected final String startDate;

    private static class Serializer extends StructSerializer<BaseDfbReport> {
        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {
        }

        public void serialize(BaseDfbReport value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("start_date");
            StoneSerializers.string().serialize(value.startDate, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public BaseDfbReport deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_startDate = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("start_date".equals(field)) {
                        f_startDate = (String) StoneSerializers.string().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_startDate == null) {
                    throw new JsonParseException(p, "Required field \"start_date\" missing.");
                }
                BaseDfbReport value = new BaseDfbReport(f_startDate);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public BaseDfbReport(String startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Required value for 'startDate' is null");
        }
        this.startDate = startDate;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.startDate});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        BaseDfbReport other = (BaseDfbReport) obj;
        if (this.startDate == other.startDate || this.startDate.equals(other.startDate)) {
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
