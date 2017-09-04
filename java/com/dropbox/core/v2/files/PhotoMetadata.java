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
import java.util.Date;

public class PhotoMetadata extends MediaMetadata {

    public static class Builder extends com.dropbox.core.v2.files.MediaMetadata.Builder {
        protected Builder() {
        }

        public Builder withDimensions(Dimensions dimensions) {
            super.withDimensions(dimensions);
            return this;
        }

        public Builder withLocation(GpsCoordinates location) {
            super.withLocation(location);
            return this;
        }

        public Builder withTimeTaken(Date timeTaken) {
            super.withTimeTaken(timeTaken);
            return this;
        }

        public PhotoMetadata build() {
            return new PhotoMetadata(this.dimensions, this.location, this.timeTaken);
        }
    }

    static class Serializer extends StructSerializer<PhotoMetadata> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(PhotoMetadata value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            writeTag("photo", g);
            if (value.dimensions != null) {
                g.writeFieldName("dimensions");
                StoneSerializers.nullableStruct(Serializer.INSTANCE).serialize(value.dimensions, g);
            }
            if (value.location != null) {
                g.writeFieldName("location");
                StoneSerializers.nullableStruct(Serializer.INSTANCE).serialize(value.location, g);
            }
            if (value.timeTaken != null) {
                g.writeFieldName("time_taken");
                StoneSerializers.nullable(StoneSerializers.timestamp()).serialize(value.timeTaken, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public PhotoMetadata deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
                if ("photo".equals(tag)) {
                    tag = null;
                }
            }
            if (tag == null) {
                Dimensions f_dimensions = null;
                GpsCoordinates f_location = null;
                Date f_timeTaken = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("dimensions".equals(field)) {
                        f_dimensions = (Dimensions) StoneSerializers.nullableStruct(Serializer.INSTANCE).deserialize(p);
                    } else if ("location".equals(field)) {
                        f_location = (GpsCoordinates) StoneSerializers.nullableStruct(Serializer.INSTANCE).deserialize(p);
                    } else if ("time_taken".equals(field)) {
                        f_timeTaken = (Date) StoneSerializers.nullable(StoneSerializers.timestamp()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                PhotoMetadata value = new PhotoMetadata(f_dimensions, f_location, f_timeTaken);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public PhotoMetadata(Dimensions dimensions, GpsCoordinates location, Date timeTaken) {
        super(dimensions, location, timeTaken);
    }

    public PhotoMetadata() {
        this(null, null, null);
    }

    public Dimensions getDimensions() {
        return this.dimensions;
    }

    public GpsCoordinates getLocation() {
        return this.location;
    }

    public Date getTimeTaken() {
        return this.timeTaken;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int hashCode() {
        return getClass().toString().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        PhotoMetadata other = (PhotoMetadata) obj;
        if ((this.dimensions == other.dimensions || (this.dimensions != null && this.dimensions.equals(other.dimensions))) && (this.location == other.location || (this.location != null && this.location.equals(other.location)))) {
            if (this.timeTaken == other.timeTaken) {
                return true;
            }
            if (this.timeTaken != null && this.timeTaken.equals(other.timeTaken)) {
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
