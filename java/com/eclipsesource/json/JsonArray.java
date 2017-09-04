package com.eclipsesource.json;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class JsonArray extends JsonValue implements Iterable<JsonValue> {
    private final List<JsonValue> values;

    public JsonArray() {
        this.values = new ArrayList();
    }

    public JsonArray(JsonArray array) {
        this(array, false);
    }

    private JsonArray(JsonArray array, boolean unmodifiable) {
        if (array == null) {
            throw new NullPointerException("array is null");
        } else if (unmodifiable) {
            this.values = Collections.unmodifiableList(array.values);
        } else {
            this.values = new ArrayList(array.values);
        }
    }

    public static JsonArray readFrom(Reader reader) throws IOException {
        return JsonValue.readFrom(reader).asArray();
    }

    public static JsonArray readFrom(String string) {
        return JsonValue.readFrom(string).asArray();
    }

    public static JsonArray unmodifiableArray(JsonArray array) {
        return new JsonArray(array, true);
    }

    public JsonArray add(int value) {
        this.values.add(JsonValue.valueOf(value));
        return this;
    }

    public JsonArray add(long value) {
        this.values.add(JsonValue.valueOf(value));
        return this;
    }

    public JsonArray add(float value) {
        this.values.add(JsonValue.valueOf(value));
        return this;
    }

    public JsonArray add(double value) {
        this.values.add(JsonValue.valueOf(value));
        return this;
    }

    public JsonArray add(boolean value) {
        this.values.add(JsonValue.valueOf(value));
        return this;
    }

    public JsonArray add(String value) {
        this.values.add(JsonValue.valueOf(value));
        return this;
    }

    public JsonArray add(JsonValue value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        this.values.add(value);
        return this;
    }

    public JsonArray set(int index, int value) {
        this.values.set(index, JsonValue.valueOf(value));
        return this;
    }

    public JsonArray set(int index, long value) {
        this.values.set(index, JsonValue.valueOf(value));
        return this;
    }

    public JsonArray set(int index, float value) {
        this.values.set(index, JsonValue.valueOf(value));
        return this;
    }

    public JsonArray set(int index, double value) {
        this.values.set(index, JsonValue.valueOf(value));
        return this;
    }

    public JsonArray set(int index, boolean value) {
        this.values.set(index, JsonValue.valueOf(value));
        return this;
    }

    public JsonArray set(int index, String value) {
        this.values.set(index, JsonValue.valueOf(value));
        return this;
    }

    public JsonArray set(int index, JsonValue value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        this.values.set(index, value);
        return this;
    }

    public JsonArray remove(int index) {
        this.values.remove(index);
        return this;
    }

    public int size() {
        return this.values.size();
    }

    public boolean isEmpty() {
        return this.values.isEmpty();
    }

    public JsonValue get(int index) {
        return (JsonValue) this.values.get(index);
    }

    public List<JsonValue> values() {
        return Collections.unmodifiableList(this.values);
    }

    public Iterator<JsonValue> iterator() {
        final Iterator<JsonValue> iterator = this.values.iterator();
        return new Iterator<JsonValue>() {
            public boolean hasNext() {
                return iterator.hasNext();
            }

            public JsonValue next() {
                return (JsonValue) iterator.next();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    protected void write(JsonWriter writer) throws IOException {
        writer.writeArray(this);
    }

    public boolean isArray() {
        return true;
    }

    public JsonArray asArray() {
        return this;
    }

    public int hashCode() {
        return this.values.hashCode();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        return this.values.equals(((JsonArray) object).values);
    }
}
