package com.eclipsesource.json;

import java.io.IOException;

class JsonString extends JsonValue {
    private final String string;

    JsonString(String string) {
        if (string == null) {
            throw new NullPointerException("string is null");
        }
        this.string = string;
    }

    protected void write(JsonWriter writer) throws IOException {
        writer.writeString(this.string);
    }

    public boolean isString() {
        return true;
    }

    public String asString() {
        return this.string;
    }

    public int hashCode() {
        return this.string.hashCode();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        return this.string.equals(((JsonString) object).string);
    }
}
