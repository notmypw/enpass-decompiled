package com.dropbox.core;

import com.dropbox.core.json.JsonReadException;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.util.DumpWriter;
import com.dropbox.core.util.Dumpable;
import com.dropbox.core.util.StringUtil;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.github.clans.fab.BuildConfig;
import java.io.IOException;

public class DbxAppInfo extends Dumpable {
    public static final JsonReader<String> KeyReader = new JsonReader<String>() {
        public String read(JsonParser parser) throws IOException, JsonReadException {
            try {
                String v = parser.getText();
                String error = DbxAppInfo.getKeyFormatError(v);
                if (error != null) {
                    throw new JsonReadException("bad format for app key: " + error, parser.getTokenLocation());
                }
                parser.nextToken();
                return v;
            } catch (JsonParseException ex) {
                throw JsonReadException.fromJackson(ex);
            }
        }
    };
    public static final JsonReader<DbxAppInfo> Reader = new JsonReader<DbxAppInfo>() {
        public final DbxAppInfo read(JsonParser parser) throws IOException, JsonReadException {
            JsonLocation top = JsonReader.expectObjectStart(parser);
            String key = null;
            String secret = null;
            DbxHost host = null;
            while (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
                String fieldName = parser.getCurrentName();
                parser.nextToken();
                try {
                    if (fieldName.equals("key")) {
                        key = (String) DbxAppInfo.KeyReader.readField(parser, fieldName, key);
                    } else if (fieldName.equals("secret")) {
                        secret = (String) DbxAppInfo.SecretReader.readField(parser, fieldName, secret);
                    } else if (fieldName.equals("host")) {
                        host = (DbxHost) DbxHost.Reader.readField(parser, fieldName, host);
                    } else {
                        JsonReader.skipValue(parser);
                    }
                } catch (JsonReadException ex) {
                    throw ex.addFieldContext(fieldName);
                }
            }
            JsonReader.expectObjectEnd(parser);
            if (key == null) {
                throw new JsonReadException("missing field \"key\"", top);
            } else if (secret == null) {
                throw new JsonReadException("missing field \"secret\"", top);
            } else {
                if (host == null) {
                    host = DbxHost.DEFAULT;
                }
                return new DbxAppInfo(key, secret, host);
            }
        }
    };
    public static final JsonReader<String> SecretReader = new JsonReader<String>() {
        public String read(JsonParser parser) throws IOException, JsonReadException {
            try {
                String v = parser.getText();
                String error = DbxAppInfo.getKeyFormatError(v);
                if (error != null) {
                    throw new JsonReadException("bad format for app secret: " + error, parser.getTokenLocation());
                }
                parser.nextToken();
                return v;
            } catch (JsonParseException ex) {
                throw JsonReadException.fromJackson(ex);
            }
        }
    };
    private final DbxHost host;
    private final String key;
    private final String secret;

    public DbxAppInfo(String key, String secret) {
        checkKeyArg(key);
        checkSecretArg(secret);
        this.key = key;
        this.secret = secret;
        this.host = DbxHost.DEFAULT;
    }

    public DbxAppInfo(String key, String secret, DbxHost host) {
        checkKeyArg(key);
        checkSecretArg(secret);
        this.key = key;
        this.secret = secret;
        this.host = host;
    }

    public String getKey() {
        return this.key;
    }

    public String getSecret() {
        return this.secret;
    }

    public DbxHost getHost() {
        return this.host;
    }

    protected void dumpFields(DumpWriter out) {
        out.f("key").v(this.key);
        out.f("secret").v(this.secret);
    }

    public static String getKeyFormatError(String key) {
        return getTokenPartError(key);
    }

    public static String getSecretFormatError(String key) {
        return getTokenPartError(key);
    }

    public static String getTokenPartError(String s) {
        if (s == null) {
            return "can't be null";
        }
        if (s.length() == 0) {
            return "can't be empty";
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '!' || c > '~') {
                return "invalid character at index " + i + ": " + StringUtil.jq(BuildConfig.FLAVOR + c);
            }
        }
        return null;
    }

    public static void checkKeyArg(String key) {
        String error = getTokenPartError(key);
        if (error != null) {
            throw new IllegalArgumentException("Bad 'key': " + error);
        }
    }

    public static void checkSecretArg(String secret) {
        String error = getTokenPartError(secret);
        if (error != null) {
            throw new IllegalArgumentException("Bad 'secret': " + error);
        }
    }
}
