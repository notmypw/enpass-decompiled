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
import java.util.Date;

public class VideoMetadata extends MediaMetadata {
    protected final Long duration;

    public static class Builder extends com.dropbox.core.v2.files.MediaMetadata.Builder {
        protected Long duration = null;

        protected Builder() {
        }

        public Builder withDuration(Long duration) {
            this.duration = duration;
            return this;
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

        public VideoMetadata build() {
            return new VideoMetadata(this.dimensions, this.location, this.timeTaken, this.duration);
        }
    }

    static class Serializer extends StructSerializer<VideoMetadata> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(VideoMetadata value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            writeTag("video", g);
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
            if (value.duration != null) {
                g.writeFieldName("duration");
                StoneSerializers.nullable(StoneSerializers.uInt64()).serialize(value.duration, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public VideoMetadata deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
                if ("video".equals(tag)) {
                    tag = null;
                }
            }
            if (tag == null) {
                Dimensions f_dimensions = null;
                GpsCoordinates f_location = null;
                Date f_timeTaken = null;
                Long f_duration = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("dimensions".equals(field)) {
                        f_dimensions = (Dimensions) StoneSerializers.nullableStruct(Serializer.INSTANCE).deserialize(p);
                    } else if ("location".equals(field)) {
                        f_location = (GpsCoordinates) StoneSerializers.nullableStruct(Serializer.INSTANCE).deserialize(p);
                    } else if ("time_taken".equals(field)) {
                        f_timeTaken = (Date) StoneSerializers.nullable(StoneSerializers.timestamp()).deserialize(p);
                    } else if ("duration".equals(field)) {
                        f_duration = (Long) StoneSerializers.nullable(StoneSerializers.uInt64()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                VideoMetadata value = new VideoMetadata(f_dimensions, f_location, f_timeTaken, f_duration);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public VideoMetadata(Dimensions dimensions, GpsCoordinates location, Date timeTaken, Long duration) {
        super(dimensions, location, timeTaken);
        this.duration = duration;
    }

    public VideoMetadata() {
        this(null, null, null, null);
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

    public Long getDuration() {
        return this.duration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.duration}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        VideoMetadata other = (VideoMetadata) obj;
        if ((this.dimensions == other.dimensions || (this.dimensions != null && this.dimensions.equals(other.dimensions))) && ((this.location == other.location || (this.location != null && this.location.equals(other.location))) && (this.timeTaken == other.timeTaken || (this.timeTaken != null && this.timeTaken.equals(other.timeTaken))))) {
            if (this.duration == other.duration) {
                return true;
            }
            if (this.duration != null && this.duration.equals(other.duration)) {
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
