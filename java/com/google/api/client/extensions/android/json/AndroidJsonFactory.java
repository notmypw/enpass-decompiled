package com.google.api.client.extensions.android.json;

import android.annotation.TargetApi;
import android.util.JsonReader;
import android.util.JsonWriter;
import com.google.api.client.extensions.android.AndroidUtils;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.JsonParser;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Charsets;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.Charset;

@Beta
@TargetApi(11)
public class AndroidJsonFactory extends JsonFactory {

    @Beta
    static class InstanceHolder {
        static final AndroidJsonFactory INSTANCE = new AndroidJsonFactory();

        InstanceHolder() {
        }
    }

    public static AndroidJsonFactory getDefaultInstance() {
        return InstanceHolder.INSTANCE;
    }

    public AndroidJsonFactory() {
        AndroidUtils.checkMinimumSdkLevel(11);
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
        return new AndroidJsonParser(this, new JsonReader(reader));
    }

    public JsonGenerator createJsonGenerator(OutputStream out, Charset enc) {
        return createJsonGenerator(new OutputStreamWriter(out, enc));
    }

    public JsonGenerator createJsonGenerator(Writer writer) {
        return new AndroidJsonGenerator(this, new JsonWriter(writer));
    }
}
