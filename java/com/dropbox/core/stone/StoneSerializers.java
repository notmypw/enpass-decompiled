package com.dropbox.core.stone;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class StoneSerializers {

    private static final class BooleanSerializer extends StoneSerializer<Boolean> {
        public static final BooleanSerializer INSTANCE = new BooleanSerializer();

        private BooleanSerializer() {
        }

        public void serialize(Boolean value, JsonGenerator g) throws IOException, JsonGenerationException {
            g.writeBoolean(value.booleanValue());
        }

        public Boolean deserialize(JsonParser p) throws IOException, JsonParseException {
            Boolean value = Boolean.valueOf(p.getBooleanValue());
            p.nextToken();
            return value;
        }
    }

    private static final class ByteArraySerializer extends StoneSerializer<byte[]> {
        public static final ByteArraySerializer INSTANCE = new ByteArraySerializer();

        private ByteArraySerializer() {
        }

        public void serialize(byte[] value, JsonGenerator g) throws IOException, JsonGenerationException {
            g.writeBinary(value);
        }

        public byte[] deserialize(JsonParser p) throws IOException, JsonParseException {
            byte[] value = p.getBinaryValue();
            p.nextToken();
            return value;
        }
    }

    private static final class DateSerializer extends StoneSerializer<Date> {
        public static final DateSerializer INSTANCE = new DateSerializer();

        private DateSerializer() {
        }

        public void serialize(Date value, JsonGenerator g) throws IOException, JsonGenerationException {
            g.writeString(Util.formatTimestamp(value));
        }

        public Date deserialize(JsonParser p) throws IOException, JsonParseException {
            String text = StoneSerializer.getStringValue(p);
            p.nextToken();
            try {
                return Util.parseTimestamp(text);
            } catch (ParseException ex) {
                throw new JsonParseException(p, "Malformed timestamp: '" + text + "'", ex);
            }
        }
    }

    private static final class DoubleSerializer extends StoneSerializer<Double> {
        public static final DoubleSerializer INSTANCE = new DoubleSerializer();

        private DoubleSerializer() {
        }

        public void serialize(Double value, JsonGenerator g) throws IOException, JsonGenerationException {
            g.writeNumber(value.doubleValue());
        }

        public Double deserialize(JsonParser p) throws IOException, JsonParseException {
            Double value = Double.valueOf(p.getDoubleValue());
            p.nextToken();
            return value;
        }
    }

    private static final class FloatSerializer extends StoneSerializer<Float> {
        public static final FloatSerializer INSTANCE = new FloatSerializer();

        private FloatSerializer() {
        }

        public void serialize(Float value, JsonGenerator g) throws IOException, JsonGenerationException {
            g.writeNumber(value.floatValue());
        }

        public Float deserialize(JsonParser p) throws IOException, JsonParseException {
            Float value = Float.valueOf(p.getFloatValue());
            p.nextToken();
            return value;
        }
    }

    private static final class IntSerializer extends StoneSerializer<Integer> {
        public static final IntSerializer INSTANCE = new IntSerializer();

        private IntSerializer() {
        }

        public void serialize(Integer value, JsonGenerator g) throws IOException, JsonGenerationException {
            g.writeNumber(value.intValue());
        }

        public Integer deserialize(JsonParser p) throws IOException, JsonParseException {
            Integer value = Integer.valueOf(p.getIntValue());
            p.nextToken();
            return value;
        }
    }

    private static final class ListSerializer<T> extends StoneSerializer<List<T>> {
        private final StoneSerializer<T> underlying;

        public ListSerializer(StoneSerializer<T> underlying) {
            this.underlying = underlying;
        }

        public void serialize(List<T> value, JsonGenerator g) throws IOException, JsonGenerationException {
            g.writeStartArray(value.size());
            for (T elem : value) {
                this.underlying.serialize((Object) elem, g);
            }
            g.writeEndArray();
        }

        public List<T> deserialize(JsonParser p) throws IOException, JsonParseException {
            StoneSerializer.expectStartArray(p);
            List<T> list = new ArrayList();
            while (p.getCurrentToken() != JsonToken.END_ARRAY) {
                list.add(this.underlying.deserialize(p));
            }
            StoneSerializer.expectEndArray(p);
            return list;
        }
    }

    private static final class LongSerializer extends StoneSerializer<Long> {
        public static final LongSerializer INSTANCE = new LongSerializer();

        private LongSerializer() {
        }

        public void serialize(Long value, JsonGenerator g) throws IOException, JsonGenerationException {
            g.writeNumber(value.longValue());
        }

        public Long deserialize(JsonParser p) throws IOException, JsonParseException {
            Long value = Long.valueOf(p.getLongValue());
            p.nextToken();
            return value;
        }
    }

    private static final class NullableSerializer<T> extends StoneSerializer<T> {
        private final StoneSerializer<T> underlying;

        public NullableSerializer(StoneSerializer<T> underlying) {
            this.underlying = underlying;
        }

        public void serialize(T value, JsonGenerator g) throws IOException, JsonGenerationException {
            if (value == null) {
                g.writeNull();
            } else {
                this.underlying.serialize((Object) value, g);
            }
        }

        public T deserialize(JsonParser p) throws IOException, JsonParseException {
            if (p.getCurrentToken() != JsonToken.VALUE_NULL) {
                return this.underlying.deserialize(p);
            }
            p.nextToken();
            return null;
        }
    }

    private static final class NullableStructSerializer<T> extends StructSerializer<T> {
        private final StructSerializer<T> underlying;

        public NullableStructSerializer(StructSerializer<T> underlying) {
            this.underlying = underlying;
        }

        public void serialize(T value, JsonGenerator g) throws IOException {
            if (value == null) {
                g.writeNull();
            } else {
                this.underlying.serialize(value, g);
            }
        }

        public void serialize(T value, JsonGenerator g, boolean collapsed) throws IOException {
            if (value == null) {
                g.writeNull();
            } else {
                this.underlying.serialize(value, g, collapsed);
            }
        }

        public T deserialize(JsonParser p) throws IOException {
            if (p.getCurrentToken() != JsonToken.VALUE_NULL) {
                return this.underlying.deserialize(p);
            }
            p.nextToken();
            return null;
        }

        public T deserialize(JsonParser p, boolean collapsed) throws IOException {
            if (p.getCurrentToken() != JsonToken.VALUE_NULL) {
                return this.underlying.deserialize(p, collapsed);
            }
            p.nextToken();
            return null;
        }
    }

    private static final class StringSerializer extends StoneSerializer<String> {
        public static final StringSerializer INSTANCE = new StringSerializer();

        private StringSerializer() {
        }

        public void serialize(String value, JsonGenerator g) throws IOException, JsonGenerationException {
            g.writeString(value);
        }

        public String deserialize(JsonParser p) throws IOException, JsonParseException {
            String value = StoneSerializer.getStringValue(p);
            p.nextToken();
            return value;
        }
    }

    private static final class VoidSerializer extends StoneSerializer<Void> {
        public static final VoidSerializer INSTANCE = new VoidSerializer();

        private VoidSerializer() {
        }

        public void serialize(Void value, JsonGenerator g) throws IOException, JsonGenerationException {
            g.writeNull();
        }

        public Void deserialize(JsonParser p) throws IOException, JsonParseException {
            StoneSerializer.skipValue(p);
            return null;
        }
    }

    public static StoneSerializer<Long> uInt64() {
        return LongSerializer.INSTANCE;
    }

    public static StoneSerializer<Long> int64() {
        return LongSerializer.INSTANCE;
    }

    public static StoneSerializer<Long> uInt32() {
        return LongSerializer.INSTANCE;
    }

    public static StoneSerializer<Integer> int32() {
        return IntSerializer.INSTANCE;
    }

    public static StoneSerializer<Double> float64() {
        return DoubleSerializer.INSTANCE;
    }

    public static StoneSerializer<Float> float32() {
        return FloatSerializer.INSTANCE;
    }

    public static StoneSerializer<Boolean> boolean_() {
        return BooleanSerializer.INSTANCE;
    }

    public static StoneSerializer<byte[]> binary() {
        return ByteArraySerializer.INSTANCE;
    }

    public static StoneSerializer<String> string() {
        return StringSerializer.INSTANCE;
    }

    public static StoneSerializer<Date> timestamp() {
        return DateSerializer.INSTANCE;
    }

    public static StoneSerializer<Void> void_() {
        return VoidSerializer.INSTANCE;
    }

    public static <T> StoneSerializer<T> nullable(StoneSerializer<T> underlying) {
        return new NullableSerializer(underlying);
    }

    public static <T> StructSerializer<T> nullableStruct(StructSerializer<T> underlying) {
        return new NullableStructSerializer(underlying);
    }

    public static <T> StoneSerializer<List<T>> list(StoneSerializer<T> underlying) {
        return new ListSerializer(underlying);
    }
}
