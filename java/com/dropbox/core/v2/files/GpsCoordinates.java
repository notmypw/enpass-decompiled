package com.dropbox.core.v2.files;

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

public class GpsCoordinates {
    protected final double latitude;
    protected final double longitude;

    static class Serializer extends StructSerializer<GpsCoordinates> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GpsCoordinates value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("latitude");
            StoneSerializers.float64().serialize(Double.valueOf(value.latitude), g);
            g.writeFieldName("longitude");
            StoneSerializers.float64().serialize(Double.valueOf(value.longitude), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GpsCoordinates deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                Double f_latitude = null;
                Double f_longitude = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("latitude".equals(field)) {
                        f_latitude = (Double) StoneSerializers.float64().deserialize(p);
                    } else if ("longitude".equals(field)) {
                        f_longitude = (Double) StoneSerializers.float64().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_latitude == null) {
                    throw new JsonParseException(p, "Required field \"latitude\" missing.");
                } else if (f_longitude == null) {
                    throw new JsonParseException(p, "Required field \"longitude\" missing.");
                } else {
                    GpsCoordinates value = new GpsCoordinates(f_latitude.doubleValue(), f_longitude.doubleValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GpsCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Double.valueOf(this.latitude), Double.valueOf(this.longitude)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GpsCoordinates other = (GpsCoordinates) obj;
        if (this.latitude == other.latitude && this.longitude == other.longitude) {
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
