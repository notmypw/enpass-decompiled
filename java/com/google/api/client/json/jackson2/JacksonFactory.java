package com.google.api.client.json.jackson2;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonToken;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.JsonParser;
import com.google.api.client.util.Preconditions;
import in.sinew.enpass.IRemoteStorage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import net.sqlcipher.database.SQLiteDatabase;

public final class JacksonFactory extends JsonFactory {
    private final com.fasterxml.jackson.core.JsonFactory factory = new com.fasterxml.jackson.core.JsonFactory();

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$fasterxml$jackson$core$JsonToken = new int[JsonToken.values().length];

        static {
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.END_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.START_ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.END_OBJECT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.START_OBJECT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_FALSE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_TRUE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_NULL.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_STRING.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_NUMBER_FLOAT.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_NUMBER_INT.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.FIELD_NAME.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
        }
    }

    static class InstanceHolder {
        static final JacksonFactory INSTANCE = new JacksonFactory();

        InstanceHolder() {
        }
    }

    public JacksonFactory() {
        this.factory.configure(Feature.AUTO_CLOSE_JSON_CONTENT, false);
    }

    public static JacksonFactory getDefaultInstance() {
        return InstanceHolder.INSTANCE;
    }

    public JsonGenerator createJsonGenerator(OutputStream out, Charset enc) throws IOException {
        return new JacksonGenerator(this, this.factory.createJsonGenerator(out, JsonEncoding.UTF8));
    }

    public JsonGenerator createJsonGenerator(Writer writer) throws IOException {
        return new JacksonGenerator(this, this.factory.createJsonGenerator(writer));
    }

    public JsonParser createJsonParser(Reader reader) throws IOException {
        Preconditions.checkNotNull(reader);
        return new JacksonParser(this, this.factory.createJsonParser(reader));
    }

    public JsonParser createJsonParser(InputStream in) throws IOException {
        Preconditions.checkNotNull(in);
        return new JacksonParser(this, this.factory.createJsonParser(in));
    }

    public JsonParser createJsonParser(InputStream in, Charset charset) throws IOException {
        Preconditions.checkNotNull(in);
        return new JacksonParser(this, this.factory.createJsonParser(in));
    }

    public JsonParser createJsonParser(String value) throws IOException {
        Preconditions.checkNotNull(value);
        return new JacksonParser(this, this.factory.createJsonParser(value));
    }

    static com.google.api.client.json.JsonToken convert(JsonToken token) {
        if (token == null) {
            return null;
        }
        switch (AnonymousClass1.$SwitchMap$com$fasterxml$jackson$core$JsonToken[token.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                return com.google.api.client.json.JsonToken.END_ARRAY;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                return com.google.api.client.json.JsonToken.START_ARRAY;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                return com.google.api.client.json.JsonToken.END_OBJECT;
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                return com.google.api.client.json.JsonToken.START_OBJECT;
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                return com.google.api.client.json.JsonToken.VALUE_FALSE;
            case IRemoteStorage.BOX_REMOTE /*6*/:
                return com.google.api.client.json.JsonToken.VALUE_TRUE;
            case IRemoteStorage.PIN /*7*/:
                return com.google.api.client.json.JsonToken.VALUE_NULL;
            case IRemoteStorage.FOLDER_REMOTE /*8*/:
                return com.google.api.client.json.JsonToken.VALUE_STRING;
            case IRemoteStorage.WEBDAV_REMOTE /*9*/:
                return com.google.api.client.json.JsonToken.VALUE_NUMBER_FLOAT;
            case IRemoteStorage.WEBDAV_REMOTE_USERNAME /*10*/:
                return com.google.api.client.json.JsonToken.VALUE_NUMBER_INT;
            case IRemoteStorage.WEBDAV_REMOTE_PASSWORD /*11*/:
                return com.google.api.client.json.JsonToken.FIELD_NAME;
            default:
                return com.google.api.client.json.JsonToken.NOT_AVAILABLE;
        }
    }
}
