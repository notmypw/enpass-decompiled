package com.dropbox.core.v2.files;

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
import com.github.clans.fab.BuildConfig;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class MediaMetadata {
    protected final Dimensions dimensions;
    protected final GpsCoordinates location;
    protected final Date timeTaken;

    public static class Builder {
        protected Dimensions dimensions = null;
        protected GpsCoordinates location = null;
        protected Date timeTaken = null;

        protected Builder() {
        }

        public Builder withDimensions(Dimensions dimensions) {
            this.dimensions = dimensions;
            return this;
        }

        public Builder withLocation(GpsCoordinates location) {
            this.location = location;
            return this;
        }

        public Builder withTimeTaken(Date timeTaken) {
            this.timeTaken = LangUtil.truncateMillis(timeTaken);
            return this;
        }

        public MediaMetadata build() {
            return new MediaMetadata(this.dimensions, this.location, this.timeTaken);
        }
    }

    static class Serializer extends StructSerializer<MediaMetadata> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(MediaMetadata value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (value instanceof PhotoMetadata) {
                Serializer.INSTANCE.serialize((PhotoMetadata) value, g, collapse);
            } else if (value instanceof VideoMetadata) {
                Serializer.INSTANCE.serialize((VideoMetadata) value, g, collapse);
            } else {
                if (!collapse) {
                    g.writeStartObject();
                }
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
        }

        public MediaMetadata deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            MediaMetadata value;
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
                if (BuildConfig.FLAVOR.equals(tag)) {
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
                value = new MediaMetadata(f_dimensions, f_location, f_timeTaken);
            } else if (BuildConfig.FLAVOR.equals(tag)) {
                value = INSTANCE.deserialize(p, true);
            } else if ("photo".equals(tag)) {
                value = Serializer.INSTANCE.deserialize(p, true);
            } else if ("video".equals(tag)) {
                value = Serializer.INSTANCE.deserialize(p, true);
            } else {
                throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
            }
            if (!collapsed) {
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public MediaMetadata(Dimensions dimensions, GpsCoordinates location, Date timeTaken) {
        this.dimensions = dimensions;
        this.location = location;
        this.timeTaken = LangUtil.truncateMillis(timeTaken);
    }

    public MediaMetadata() {
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
        return Arrays.hashCode(new Object[]{this.dimensions, this.location, this.timeTaken});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        MediaMetadata other = (MediaMetadata) obj;
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
