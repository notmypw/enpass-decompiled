package com.dropbox.core.v2.team;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.util.LangUtil;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

class DateRange {
    protected final Date endDate;
    protected final Date startDate;

    public static class Builder {
        protected Date endDate = null;
        protected Date startDate = null;

        protected Builder() {
        }

        public Builder withStartDate(Date startDate) {
            this.startDate = LangUtil.truncateMillis(startDate);
            return this;
        }

        public Builder withEndDate(Date endDate) {
            this.endDate = LangUtil.truncateMillis(endDate);
            return this;
        }

        public DateRange build() {
            return new DateRange(this.startDate, this.endDate);
        }
    }

    static class Serializer extends StructSerializer<DateRange> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(DateRange value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            if (value.startDate != null) {
                g.writeFieldName("start_date");
                StoneSerializers.nullable(StoneSerializers.timestamp()).serialize(value.startDate, g);
            }
            if (value.endDate != null) {
                g.writeFieldName("end_date");
                StoneSerializers.nullable(StoneSerializers.timestamp()).serialize(value.endDate, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public DateRange deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Date f_startDate = null;
                Date f_endDate = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("start_date".equals(field)) {
                        f_startDate = (Date) StoneSerializers.nullable(StoneSerializers.timestamp()).deserialize(p);
                    } else if ("end_date".equals(field)) {
                        f_endDate = (Date) StoneSerializers.nullable(StoneSerializers.timestamp()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                DateRange value = new DateRange(f_startDate, f_endDate);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public DateRange(Date startDate, Date endDate) {
        this.startDate = LangUtil.truncateMillis(startDate);
        this.endDate = LangUtil.truncateMillis(endDate);
    }

    public DateRange() {
        this(null, null);
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.startDate, this.endDate});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        DateRange other = (DateRange) obj;
        if (this.startDate == other.startDate || (this.startDate != null && this.startDate.equals(other.startDate))) {
            if (this.endDate == other.endDate) {
                return true;
            }
            if (this.endDate != null && this.endDate.equals(other.endDate)) {
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
