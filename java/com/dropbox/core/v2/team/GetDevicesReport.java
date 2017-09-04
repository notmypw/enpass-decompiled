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

public class GetDevicesReport extends BaseDfbReport {
    protected final DevicesActive active1Day;
    protected final DevicesActive active28Day;
    protected final DevicesActive active7Day;

    static class Serializer extends StructSerializer<GetDevicesReport> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GetDevicesReport value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("start_date");
            StoneSerializers.string().serialize(value.startDate, g);
            g.writeFieldName("active_1_day");
            Serializer.INSTANCE.serialize(value.active1Day, g);
            g.writeFieldName("active_7_day");
            Serializer.INSTANCE.serialize(value.active7Day, g);
            g.writeFieldName("active_28_day");
            Serializer.INSTANCE.serialize(value.active28Day, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GetDevicesReport deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_startDate = null;
                DevicesActive f_active1Day = null;
                DevicesActive f_active7Day = null;
                DevicesActive f_active28Day = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("start_date".equals(field)) {
                        f_startDate = (String) StoneSerializers.string().deserialize(p);
                    } else if ("active_1_day".equals(field)) {
                        f_active1Day = (DevicesActive) Serializer.INSTANCE.deserialize(p);
                    } else if ("active_7_day".equals(field)) {
                        f_active7Day = (DevicesActive) Serializer.INSTANCE.deserialize(p);
                    } else if ("active_28_day".equals(field)) {
                        f_active28Day = (DevicesActive) Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_startDate == null) {
                    throw new JsonParseException(p, "Required field \"start_date\" missing.");
                } else if (f_active1Day == null) {
                    throw new JsonParseException(p, "Required field \"active_1_day\" missing.");
                } else if (f_active7Day == null) {
                    throw new JsonParseException(p, "Required field \"active_7_day\" missing.");
                } else if (f_active28Day == null) {
                    throw new JsonParseException(p, "Required field \"active_28_day\" missing.");
                } else {
                    GetDevicesReport value = new GetDevicesReport(f_startDate, f_active1Day, f_active7Day, f_active28Day);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GetDevicesReport(String startDate, DevicesActive active1Day, DevicesActive active7Day, DevicesActive active28Day) {
        super(startDate);
        if (active1Day == null) {
            throw new IllegalArgumentException("Required value for 'active1Day' is null");
        }
        this.active1Day = active1Day;
        if (active7Day == null) {
            throw new IllegalArgumentException("Required value for 'active7Day' is null");
        }
        this.active7Day = active7Day;
        if (active28Day == null) {
            throw new IllegalArgumentException("Required value for 'active28Day' is null");
        }
        this.active28Day = active28Day;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public DevicesActive getActive1Day() {
        return this.active1Day;
    }

    public DevicesActive getActive7Day() {
        return this.active7Day;
    }

    public DevicesActive getActive28Day() {
        return this.active28Day;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.active1Day, this.active7Day, this.active28Day}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GetDevicesReport other = (GetDevicesReport) obj;
        if ((this.startDate == other.startDate || this.startDate.equals(other.startDate)) && ((this.active1Day == other.active1Day || this.active1Day.equals(other.active1Day)) && ((this.active7Day == other.active7Day || this.active7Day.equals(other.active7Day)) && (this.active28Day == other.active28Day || this.active28Day.equals(other.active28Day))))) {
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
