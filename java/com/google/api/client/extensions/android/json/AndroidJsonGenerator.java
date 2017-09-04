package com.google.api.client.extensions.android.json;

import android.annotation.TargetApi;
import android.util.JsonWriter;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.util.Beta;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

@Beta
@TargetApi(11)
class AndroidJsonGenerator extends JsonGenerator {
    private final AndroidJsonFactory factory;
    private final JsonWriter writer;

    static final class StringNumber extends Number {
        private static final long serialVersionUID = 1;
        private final String encodedValue;

        StringNumber(String encodedValue) {
            this.encodedValue = encodedValue;
        }

        public double doubleValue() {
            return 0.0d;
        }

        public float floatValue() {
            return 0.0f;
        }

        public int intValue() {
            return 0;
        }

        public long longValue() {
            return 0;
        }

        public String toString() {
            return this.encodedValue;
        }
    }

    AndroidJsonGenerator(AndroidJsonFactory factory, JsonWriter writer) {
        this.factory = factory;
        this.writer = writer;
        writer.setLenient(true);
    }

    public void flush() throws IOException {
        this.writer.flush();
    }

    public void close() throws IOException {
        this.writer.close();
    }

    public JsonFactory getFactory() {
        return this.factory;
    }

    public void writeBoolean(boolean state) throws IOException {
        this.writer.value(state);
    }

    public void writeEndArray() throws IOException {
        this.writer.endArray();
    }

    public void writeEndObject() throws IOException {
        this.writer.endObject();
    }

    public void writeFieldName(String name) throws IOException {
        this.writer.name(name);
    }

    public void writeNull() throws IOException {
        this.writer.nullValue();
    }

    public void writeNumber(int v) throws IOException {
        this.writer.value((long) v);
    }

    public void writeNumber(long v) throws IOException {
        this.writer.value(v);
    }

    public void writeNumber(BigInteger v) throws IOException {
        this.writer.value(v);
    }

    public void writeNumber(double v) throws IOException {
        this.writer.value(v);
    }

    public void writeNumber(float v) throws IOException {
        this.writer.value((double) v);
    }

    public void writeNumber(BigDecimal v) throws IOException {
        this.writer.value(v);
    }

    public void writeNumber(String encodedValue) throws IOException {
        this.writer.value(new StringNumber(encodedValue));
    }

    public void writeStartArray() throws IOException {
        this.writer.beginArray();
    }

    public void writeStartObject() throws IOException {
        this.writer.beginObject();
    }

    public void writeString(String value) throws IOException {
        this.writer.value(value);
    }

    public void enablePrettyPrint() throws IOException {
        this.writer.setIndent("  ");
    }
}
