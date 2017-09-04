package com.google.api.client.json;

import com.google.api.client.util.Charsets;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

public abstract class JsonFactory {
    public abstract JsonGenerator createJsonGenerator(OutputStream outputStream, Charset charset) throws IOException;

    public abstract JsonGenerator createJsonGenerator(Writer writer) throws IOException;

    public abstract JsonParser createJsonParser(InputStream inputStream) throws IOException;

    public abstract JsonParser createJsonParser(InputStream inputStream, Charset charset) throws IOException;

    public abstract JsonParser createJsonParser(Reader reader) throws IOException;

    public abstract JsonParser createJsonParser(String str) throws IOException;

    public final JsonObjectParser createJsonObjectParser() {
        return new JsonObjectParser(this);
    }

    public final String toString(Object item) throws IOException {
        return toString(item, false);
    }

    public final String toPrettyString(Object item) throws IOException {
        return toString(item, true);
    }

    public final byte[] toByteArray(Object item) throws IOException {
        return toByteStream(item, false).toByteArray();
    }

    private String toString(Object item, boolean pretty) throws IOException {
        return toByteStream(item, pretty).toString("UTF-8");
    }

    private ByteArrayOutputStream toByteStream(Object item, boolean pretty) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        JsonGenerator generator = createJsonGenerator(byteStream, Charsets.UTF_8);
        if (pretty) {
            generator.enablePrettyPrint();
        }
        generator.serialize(item);
        generator.flush();
        return byteStream;
    }

    public final <T> T fromString(String value, Class<T> destinationClass) throws IOException {
        return createJsonParser(value).parse((Class) destinationClass);
    }

    public final <T> T fromInputStream(InputStream inputStream, Class<T> destinationClass) throws IOException {
        return createJsonParser(inputStream).parseAndClose((Class) destinationClass);
    }

    public final <T> T fromInputStream(InputStream inputStream, Charset charset, Class<T> destinationClass) throws IOException {
        return createJsonParser(inputStream, charset).parseAndClose((Class) destinationClass);
    }

    public final <T> T fromReader(Reader reader, Class<T> destinationClass) throws IOException {
        return createJsonParser(reader).parseAndClose((Class) destinationClass);
    }
}
