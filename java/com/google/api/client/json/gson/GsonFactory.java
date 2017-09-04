package com.google.api.client.json.gson;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.JsonParser;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Charsets;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.Charset;

public class GsonFactory extends JsonFactory {

    @Beta
    static class InstanceHolder {
        static final GsonFactory INSTANCE = new GsonFactory();

        InstanceHolder() {
        }
    }

    @Beta
    public static GsonFactory getDefaultInstance() {
        return InstanceHolder.INSTANCE;
    }

    public JsonParser createJsonParser(InputStream in) {
        return createJsonParser(new InputStreamReader(in, Charsets.UTF_8));
    }

    public JsonParser createJsonParser(InputStream in, Charset charset) {
        if (charset == null) {
            return createJsonParser(in);
        }
        return createJsonParser(new InputStreamReader(in, charset));
    }

    public JsonParser createJsonParser(String value) {
        return createJsonParser(new StringReader(value));
    }

    public JsonParser createJsonParser(Reader reader) {
        return new GsonParser(this, new JsonReader(reader));
    }

    public JsonGenerator createJsonGenerator(OutputStream out, Charset enc) {
        return createJsonGenerator(new OutputStreamWriter(out, enc));
    }

    public JsonGenerator createJsonGenerator(Writer writer) {
        return new GsonGenerator(this, new JsonWriter(writer));
    }
}
