package com.google.api.client.json.gson;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.JsonToken;
import com.google.api.client.util.Preconditions;
import com.google.gson.stream.JsonReader;
import in.sinew.enpass.IRemoteStorage;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import net.sqlcipher.database.SQLiteDatabase;

class GsonParser extends JsonParser {
    private List<String> currentNameStack = new ArrayList();
    private String currentText;
    private JsonToken currentToken;
    private final GsonFactory factory;
    private final JsonReader reader;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$api$client$json$JsonToken = new int[JsonToken.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$google$gson$stream$JsonToken = new int[com.google.gson.stream.JsonToken.values().length];

        static {
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[com.google.gson.stream.JsonToken.BEGIN_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[com.google.gson.stream.JsonToken.END_ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[com.google.gson.stream.JsonToken.BEGIN_OBJECT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[com.google.gson.stream.JsonToken.END_OBJECT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[com.google.gson.stream.JsonToken.BOOLEAN.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[com.google.gson.stream.JsonToken.NULL.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[com.google.gson.stream.JsonToken.STRING.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[com.google.gson.stream.JsonToken.NUMBER.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[com.google.gson.stream.JsonToken.NAME.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$google$api$client$json$JsonToken[JsonToken.START_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$google$api$client$json$JsonToken[JsonToken.START_OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError e11) {
            }
        }
    }

    GsonParser(GsonFactory factory, JsonReader reader) {
        this.factory = factory;
        this.reader = reader;
        reader.setLenient(true);
    }

    public void close() throws IOException {
        this.reader.close();
    }

    public String getCurrentName() {
        return this.currentNameStack.isEmpty() ? null : (String) this.currentNameStack.get(this.currentNameStack.size() - 1);
    }

    public JsonToken getCurrentToken() {
        return this.currentToken;
    }

    public JsonFactory getFactory() {
        return this.factory;
    }

    public byte getByteValue() {
        checkNumber();
        return Byte.valueOf(this.currentText).byteValue();
    }

    public short getShortValue() {
        checkNumber();
        return Short.valueOf(this.currentText).shortValue();
    }

    public int getIntValue() {
        checkNumber();
        return Integer.valueOf(this.currentText).intValue();
    }

    public float getFloatValue() {
        checkNumber();
        return Float.valueOf(this.currentText).floatValue();
    }

    public BigInteger getBigIntegerValue() {
        checkNumber();
        return new BigInteger(this.currentText);
    }

    public BigDecimal getDecimalValue() {
        checkNumber();
        return new BigDecimal(this.currentText);
    }

    public double getDoubleValue() {
        checkNumber();
        return Double.valueOf(this.currentText).doubleValue();
    }

    public long getLongValue() {
        checkNumber();
        return Long.valueOf(this.currentText).longValue();
    }

    private void checkNumber() {
        boolean z = this.currentToken == JsonToken.VALUE_NUMBER_INT || this.currentToken == JsonToken.VALUE_NUMBER_FLOAT;
        Preconditions.checkArgument(z);
    }

    public String getText() {
        return this.currentText;
    }

    public JsonToken nextToken() throws IOException {
        com.google.gson.stream.JsonToken peek;
        if (this.currentToken != null) {
            switch (AnonymousClass1.$SwitchMap$com$google$api$client$json$JsonToken[this.currentToken.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    this.reader.beginArray();
                    this.currentNameStack.add(null);
                    break;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    this.reader.beginObject();
                    this.currentNameStack.add(null);
                    break;
            }
        }
        try {
            peek = this.reader.peek();
        } catch (EOFException e) {
            peek = com.google.gson.stream.JsonToken.END_DOCUMENT;
        }
        switch (AnonymousClass1.$SwitchMap$com$google$gson$stream$JsonToken[peek.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                this.currentText = "[";
                this.currentToken = JsonToken.START_ARRAY;
                break;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                this.currentText = "]";
                this.currentToken = JsonToken.END_ARRAY;
                this.currentNameStack.remove(this.currentNameStack.size() - 1);
                this.reader.endArray();
                break;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                this.currentText = "{";
                this.currentToken = JsonToken.START_OBJECT;
                break;
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                this.currentText = "}";
                this.currentToken = JsonToken.END_OBJECT;
                this.currentNameStack.remove(this.currentNameStack.size() - 1);
                this.reader.endObject();
                break;
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                if (!this.reader.nextBoolean()) {
                    this.currentText = "false";
                    this.currentToken = JsonToken.VALUE_FALSE;
                    break;
                }
                this.currentText = "true";
                this.currentToken = JsonToken.VALUE_TRUE;
                break;
            case IRemoteStorage.BOX_REMOTE /*6*/:
                this.currentText = "null";
                this.currentToken = JsonToken.VALUE_NULL;
                this.reader.nextNull();
                break;
            case IRemoteStorage.PIN /*7*/:
                this.currentText = this.reader.nextString();
                this.currentToken = JsonToken.VALUE_STRING;
                break;
            case IRemoteStorage.FOLDER_REMOTE /*8*/:
                this.currentText = this.reader.nextString();
                this.currentToken = this.currentText.indexOf(46) == -1 ? JsonToken.VALUE_NUMBER_INT : JsonToken.VALUE_NUMBER_FLOAT;
                break;
            case IRemoteStorage.WEBDAV_REMOTE /*9*/:
                this.currentText = this.reader.nextName();
                this.currentToken = JsonToken.FIELD_NAME;
                this.currentNameStack.set(this.currentNameStack.size() - 1, this.currentText);
                break;
            default:
                this.currentText = null;
                this.currentToken = null;
                break;
        }
        return this.currentToken;
    }

    public JsonParser skipChildren() throws IOException {
        if (this.currentToken != null) {
            switch (AnonymousClass1.$SwitchMap$com$google$api$client$json$JsonToken[this.currentToken.ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    this.reader.skipValue();
                    this.currentText = "]";
                    this.currentToken = JsonToken.END_ARRAY;
                    break;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    this.reader.skipValue();
                    this.currentText = "}";
                    this.currentToken = JsonToken.END_OBJECT;
                    break;
            }
        }
        return this;
    }
}
