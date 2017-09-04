package com.dropbox.core.json;

import com.dropbox.core.util.IOUtil;
import com.dropbox.core.util.LangUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public abstract class JsonReader<T> {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final JsonReader<byte[]> BinaryReader = new JsonReader<byte[]>() {
        public byte[] read(JsonParser parser) throws IOException, JsonReadException {
            try {
                byte[] v = parser.getBinaryValue();
                parser.nextToken();
                return v;
            } catch (JsonParseException ex) {
                throw JsonReadException.fromJackson(ex);
            }
        }
    };
    public static final JsonReader<Boolean> BooleanReader = new JsonReader<Boolean>() {
        public Boolean read(JsonParser parser) throws IOException, JsonReadException {
            return Boolean.valueOf(JsonReader.readBoolean(parser));
        }
    };
    public static final JsonReader<Float> Float32Reader = new JsonReader<Float>() {
        public Float read(JsonParser parser) throws IOException, JsonReadException {
            float v = parser.getFloatValue();
            parser.nextToken();
            return Float.valueOf(v);
        }
    };
    public static final JsonReader<Double> Float64Reader = new JsonReader<Double>() {
        public Double read(JsonParser parser) throws IOException, JsonReadException {
            double v = parser.getDoubleValue();
            parser.nextToken();
            return Double.valueOf(v);
        }
    };
    public static final JsonReader<Integer> Int32Reader = new JsonReader<Integer>() {
        public Integer read(JsonParser parser) throws IOException, JsonReadException {
            int v = parser.getIntValue();
            parser.nextToken();
            return Integer.valueOf(v);
        }
    };
    public static final JsonReader<Long> Int64Reader = new JsonReader<Long>() {
        public Long read(JsonParser parser) throws IOException, JsonReadException {
            long v = parser.getLongValue();
            parser.nextToken();
            return Long.valueOf(v);
        }
    };
    public static final JsonReader<String> StringReader = new JsonReader<String>() {
        public String read(JsonParser parser) throws IOException, JsonReadException {
            try {
                String v = parser.getText();
                parser.nextToken();
                return v;
            } catch (JsonParseException ex) {
                throw JsonReadException.fromJackson(ex);
            }
        }
    };
    public static final JsonReader<Long> UInt32Reader = new JsonReader<Long>() {
        public Long read(JsonParser parser) throws IOException, JsonReadException {
            long v = JsonReader.readUnsignedLong(parser);
            if (v < 4294967296L) {
                return Long.valueOf(v);
            }
            throw new JsonReadException("expecting a 32-bit unsigned integer, got: " + v, parser.getTokenLocation());
        }
    };
    public static final JsonReader<Long> UInt64Reader = new JsonReader<Long>() {
        public Long read(JsonParser parser) throws IOException, JsonReadException {
            return Long.valueOf(JsonReader.readUnsignedLong(parser));
        }
    };
    public static final JsonReader<Long> UnsignedLongReader = new JsonReader<Long>() {
        public Long read(JsonParser parser) throws IOException, JsonReadException {
            return Long.valueOf(JsonReader.readUnsignedLong(parser));
        }
    };
    public static final JsonReader<Object> VoidReader = new JsonReader<Object>() {
        public Object read(JsonParser parser) throws IOException, JsonReadException {
            JsonReader.skipValue(parser);
            return null;
        }
    };
    static final JsonFactory jsonFactory = new JsonFactory();

    public static final class FieldMapping {
        static final /* synthetic */ boolean $assertionsDisabled = (!JsonReader.class.desiredAssertionStatus());
        public final HashMap<String, Integer> fields;

        public static final class Builder {
            private HashMap<String, Integer> fields = new HashMap();

            public void add(String fieldName, int expectedIndex) {
                if (this.fields == null) {
                    throw new IllegalStateException("already called build(); can't call add() anymore");
                }
                int i = this.fields.size();
                if (expectedIndex != i) {
                    throw new IllegalStateException("expectedIndex = " + expectedIndex + ", actual = " + i);
                } else if (this.fields.put(fieldName, Integer.valueOf(i)) != null) {
                    throw new IllegalStateException("duplicate field name: \"" + fieldName + "\"");
                }
            }

            public FieldMapping build() {
                if (this.fields == null) {
                    throw new IllegalStateException("already called build(); can't call build() again");
                }
                HashMap<String, Integer> f = this.fields;
                this.fields = null;
                return new FieldMapping(f);
            }
        }

        private FieldMapping(HashMap<String, Integer> fields) {
            if ($assertionsDisabled || fields != null) {
                this.fields = fields;
                return;
            }
            throw new AssertionError();
        }

        public int get(String fieldName) {
            Integer i = (Integer) this.fields.get(fieldName);
            if (i == null) {
                return -1;
            }
            return i.intValue();
        }
    }

    public static abstract class FileLoadException extends Exception {
        private static final long serialVersionUID = 0;

        public static final class IOError extends FileLoadException {
            private static final long serialVersionUID = 0;
            public final IOException reason;

            public IOError(File file, IOException reason) {
                super("unable to read file \"" + file.getPath() + "\": " + reason.getMessage());
                this.reason = reason;
            }
        }

        public static final class JsonError extends FileLoadException {
            private static final long serialVersionUID = 0;
            public final JsonReadException reason;

            public JsonError(File file, JsonReadException reason) {
                super(file.getPath() + ": " + reason.getMessage());
                this.reason = reason;
            }
        }

        protected FileLoadException(String message) {
            super(message);
        }
    }

    public abstract T read(JsonParser jsonParser) throws IOException, JsonReadException;

    static {
        boolean z;
        if (JsonReader.class.desiredAssertionStatus()) {
            z = false;
        } else {
            z = true;
        }
        $assertionsDisabled = z;
    }

    public T readFromTags(String[] tags, JsonParser parser) throws IOException, JsonReadException {
        return null;
    }

    public T readFields(JsonParser parser) throws IOException, JsonReadException {
        return null;
    }

    public void validate(T t) {
    }

    public final T readField(JsonParser parser, String fieldName, T v) throws IOException, JsonReadException {
        if (v == null) {
            return read(parser);
        }
        throw new JsonReadException("duplicate field \"" + fieldName + "\"", parser.getTokenLocation());
    }

    public final T readOptional(JsonParser parser) throws IOException, JsonReadException {
        if (parser.getCurrentToken() != JsonToken.VALUE_NULL) {
            return read(parser);
        }
        parser.nextToken();
        return null;
    }

    public static String[] readTags(JsonParser parser) throws IOException, JsonReadException {
        if (parser.getCurrentToken() != JsonToken.FIELD_NAME || !".tag".equals(parser.getCurrentName())) {
            return null;
        }
        parser.nextToken();
        if (parser.getCurrentToken() != JsonToken.VALUE_STRING) {
            throw new JsonReadException("expected a string value for .tag field", parser.getTokenLocation());
        }
        String tag = parser.getText();
        parser.nextToken();
        return tag.split("\\.");
    }

    public static JsonToken nextToken(JsonParser parser) throws IOException, JsonReadException {
        try {
            return parser.nextToken();
        } catch (JsonParseException ex) {
            throw JsonReadException.fromJackson(ex);
        }
    }

    public static JsonLocation expectObjectStart(JsonParser parser) throws IOException, JsonReadException {
        if (parser.getCurrentToken() != JsonToken.START_OBJECT) {
            throw new JsonReadException("expecting the start of an object (\"{\")", parser.getTokenLocation());
        }
        JsonLocation loc = parser.getTokenLocation();
        nextToken(parser);
        return loc;
    }

    public static void expectObjectEnd(JsonParser parser) throws IOException, JsonReadException {
        if (parser.getCurrentToken() != JsonToken.END_OBJECT) {
            throw new JsonReadException("expecting the end of an object (\"}\")", parser.getTokenLocation());
        }
        nextToken(parser);
    }

    public static JsonLocation expectArrayStart(JsonParser parser) throws IOException, JsonReadException {
        if (parser.getCurrentToken() != JsonToken.START_ARRAY) {
            throw new JsonReadException("expecting the start of an array (\"[\")", parser.getTokenLocation());
        }
        JsonLocation loc = parser.getTokenLocation();
        nextToken(parser);
        return loc;
    }

    public static JsonLocation expectArrayEnd(JsonParser parser) throws IOException, JsonReadException {
        if (parser.getCurrentToken() != JsonToken.END_ARRAY) {
            throw new JsonReadException("expecting the end of an array (\"[\")", parser.getTokenLocation());
        }
        JsonLocation loc = parser.getTokenLocation();
        nextToken(parser);
        return loc;
    }

    public static boolean isArrayEnd(JsonParser parser) {
        return parser.getCurrentToken() == JsonToken.END_ARRAY;
    }

    public static boolean isArrayStart(JsonParser parser) {
        return parser.getCurrentToken() == JsonToken.START_ARRAY;
    }

    public static void skipValue(JsonParser parser) throws IOException, JsonReadException {
        try {
            parser.skipChildren();
            parser.nextToken();
        } catch (JsonParseException ex) {
            throw JsonReadException.fromJackson(ex);
        }
    }

    public static long readUnsignedLong(JsonParser parser) throws IOException, JsonReadException {
        try {
            long v = parser.getLongValue();
            if (v < 0) {
                throw new JsonReadException("expecting a non-negative number, got: " + v, parser.getTokenLocation());
            }
            parser.nextToken();
            return v;
        } catch (JsonParseException ex) {
            throw JsonReadException.fromJackson(ex);
        }
    }

    public static long readUnsignedLongField(JsonParser parser, String fieldName, long v) throws IOException, JsonReadException {
        if (v < 0) {
            return readUnsignedLong(parser);
        }
        throw new JsonReadException("duplicate field \"" + fieldName + "\"", parser.getCurrentLocation());
    }

    public static boolean readBoolean(JsonParser parser) throws IOException, JsonReadException {
        try {
            boolean b = parser.getBooleanValue();
            parser.nextToken();
            return b;
        } catch (JsonParseException ex) {
            throw JsonReadException.fromJackson(ex);
        }
    }

    public static double readDouble(JsonParser parser) throws IOException, JsonReadException {
        try {
            double v = parser.getDoubleValue();
            parser.nextToken();
            return v;
        } catch (JsonParseException ex) {
            throw JsonReadException.fromJackson(ex);
        }
    }

    public static <T> T readEnum(JsonParser parser, HashMap<String, T> values, T catch_all) throws IOException, JsonReadException {
        T value;
        String text;
        if (parser.getCurrentToken() == JsonToken.START_OBJECT) {
            parser.nextToken();
            String[] tags = readTags(parser);
            if (tags == null || parser.getCurrentToken() != JsonToken.END_OBJECT) {
                if (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
                    text = parser.getText();
                    if ($assertionsDisabled || tags == null || tags[0].equals(text)) {
                        parser.nextToken();
                        skipValue(parser);
                    } else {
                        throw new AssertionError();
                    }
                }
                throw new JsonReadException("expecting a field name", parser.getTokenLocation());
            } else if ($assertionsDisabled || (tags.length == 1 && tags[0] != null)) {
                text = tags[0];
            } else {
                throw new AssertionError();
            }
            value = values.get(text);
            if (value == null) {
                value = catch_all;
            }
            if (value == null) {
                throw new JsonReadException("Expected one of " + values + ", got: " + text, parser.getTokenLocation());
            }
            expectObjectEnd(parser);
        } else if (parser.getCurrentToken() != JsonToken.VALUE_STRING) {
            throw new JsonReadException("Expected a string value", parser.getTokenLocation());
        } else {
            text = parser.getText();
            value = values.get(text);
            if (value == null) {
                value = catch_all;
            }
            if (value == null) {
                throw new JsonReadException("Expected one of " + values + ", got: " + text, parser.getTokenLocation());
            }
            parser.nextToken();
        }
        return value;
    }

    public T readFully(InputStream utf8Body) throws IOException, JsonReadException {
        try {
            return readFully(jsonFactory.createParser(utf8Body));
        } catch (JsonParseException ex) {
            throw JsonReadException.fromJackson(ex);
        }
    }

    public T readFully(String body) throws JsonReadException {
        JsonParser parser;
        try {
            parser = jsonFactory.createParser(body);
            T readFully = readFully(parser);
            parser.close();
            return readFully;
        } catch (JsonParseException ex) {
            throw JsonReadException.fromJackson(ex);
        } catch (IOException ex2) {
            throw LangUtil.mkAssert("IOException reading from String", ex2);
        } catch (Throwable th) {
            parser.close();
        }
    }

    public T readFully(byte[] utf8Body) throws JsonReadException {
        JsonParser parser;
        try {
            parser = jsonFactory.createParser(utf8Body);
            T readFully = readFully(parser);
            parser.close();
            return readFully;
        } catch (JsonParseException ex) {
            throw JsonReadException.fromJackson(ex);
        } catch (IOException ex2) {
            throw LangUtil.mkAssert("IOException reading from byte[]", ex2);
        } catch (Throwable th) {
            parser.close();
        }
    }

    public T readFromFile(String filePath) throws FileLoadException {
        return readFromFile(new File(filePath));
    }

    public T readFromFile(File file) throws FileLoadException {
        InputStream in;
        try {
            in = new FileInputStream(file);
            T readFully = readFully(in);
            IOUtil.closeInput(in);
            return readFully;
        } catch (JsonReadException ex) {
            throw new JsonError(file, ex);
        } catch (IOException ex2) {
            throw new IOError(file, ex2);
        } catch (Throwable th) {
            IOUtil.closeInput(in);
        }
    }

    public T readFully(JsonParser parser) throws IOException, JsonReadException {
        parser.nextToken();
        T value = read(parser);
        if (parser.getCurrentToken() != null) {
            throw new AssertionError("The JSON library should ensure there's no tokens after the main value: " + parser.getCurrentToken() + "@" + parser.getCurrentLocation());
        }
        validate(value);
        return value;
    }
}
