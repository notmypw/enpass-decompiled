package com.dropbox.core.stone;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public abstract class StoneSerializer<T> {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public abstract T deserialize(JsonParser jsonParser) throws IOException, JsonParseException;

    public abstract void serialize(T t, JsonGenerator jsonGenerator) throws IOException, JsonGenerationException;

    public String serialize(T value) {
        return serialize((Object) value, false);
    }

    public String serialize(T value, boolean pretty) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            serialize(value, out, pretty);
            return new String(out.toByteArray(), UTF8);
        } catch (JsonGenerationException ex) {
            throw new IllegalStateException("Impossible JSON exception", ex);
        } catch (IOException ex2) {
            throw new IllegalStateException("Impossible I/O exception", ex2);
        }
    }

    public void serialize(T value, OutputStream out) throws IOException {
        serialize(value, out, false);
    }

    public void serialize(T value, OutputStream out, boolean pretty) throws IOException {
        JsonGenerator g = Util.JSON.createGenerator(out);
        if (pretty) {
            g.useDefaultPrettyPrinter();
        }
        try {
            serialize((Object) value, g);
            g.flush();
        } catch (JsonGenerationException ex) {
            throw new IllegalStateException("Impossible JSON generation exception", ex);
        }
    }

    public T deserialize(String json) throws JsonParseException {
        try {
            JsonParser p = Util.JSON.createParser(json);
            p.nextToken();
            return deserialize(p);
        } catch (JsonParseException ex) {
            throw ex;
        } catch (IOException ex2) {
            throw new IllegalStateException("Impossible I/O exception", ex2);
        }
    }

    public T deserialize(InputStream json) throws IOException, JsonParseException {
        JsonParser p = Util.JSON.createParser(json);
        p.nextToken();
        return deserialize(p);
    }

    protected static String getStringValue(JsonParser p) throws IOException, JsonParseException {
        if (p.getCurrentToken() == JsonToken.VALUE_STRING) {
            return p.getText();
        }
        throw new JsonParseException(p, "expected string value, but was " + p.getCurrentToken());
    }

    protected static void expectField(String name, JsonParser p) throws IOException, JsonParseException {
        if (p.getCurrentToken() != JsonToken.FIELD_NAME) {
            throw new JsonParseException(p, "expected field name, but was: " + p.getCurrentToken());
        } else if (name.equals(p.getCurrentName())) {
            p.nextToken();
        } else {
            throw new JsonParseException(p, "expected field '" + name + "', but was: '" + p.getCurrentName() + "'");
        }
    }

    protected static void expectStartObject(JsonParser p) throws IOException, JsonParseException {
        if (p.getCurrentToken() != JsonToken.START_OBJECT) {
            throw new JsonParseException(p, "expected object value.");
        }
        p.nextToken();
    }

    protected static void expectEndObject(JsonParser p) throws IOException, JsonParseException {
        if (p.getCurrentToken() != JsonToken.END_OBJECT) {
            throw new JsonParseException(p, "expected end of object value.");
        }
        p.nextToken();
    }

    protected static void expectStartArray(JsonParser p) throws IOException, JsonParseException {
        if (p.getCurrentToken() != JsonToken.START_ARRAY) {
            throw new JsonParseException(p, "expected array value.");
        }
        p.nextToken();
    }

    protected static void expectEndArray(JsonParser p) throws IOException, JsonParseException {
        if (p.getCurrentToken() != JsonToken.END_ARRAY) {
            throw new JsonParseException(p, "expected end of array value.");
        }
        p.nextToken();
    }

    protected static void skipValue(JsonParser p) throws IOException, JsonParseException {
        if (p.getCurrentToken().isStructStart()) {
            p.skipChildren();
            p.nextToken();
        } else if (p.getCurrentToken().isScalarValue()) {
            p.nextToken();
        } else {
            throw new JsonParseException(p, "Can't skip JSON value token: " + p.getCurrentToken());
        }
    }

    protected static void skipFields(JsonParser p) throws IOException, JsonParseException {
        while (p.getCurrentToken() != null && !p.getCurrentToken().isStructEnd()) {
            if (p.getCurrentToken().isStructStart()) {
                p.skipChildren();
            } else if (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                p.nextToken();
            } else if (p.getCurrentToken().isScalarValue()) {
                p.nextToken();
            } else {
                throw new JsonParseException(p, "Can't skip token: " + p.getCurrentToken());
            }
        }
    }
}
